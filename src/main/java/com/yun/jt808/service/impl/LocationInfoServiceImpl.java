package com.yun.jt808.service.impl;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import com.yun.jt808.common.enums.AlarmDetailEnums;
import com.yun.jt808.common.enums.DeviceState;
import com.yun.jt808.common.enums.VehicleLoadState;
import com.yun.jt808.config.CoreConfig;
import com.yun.jt808.dao.DaoFactory;
import com.yun.jt808.dao.DeviceStatusInfoDao;
import com.yun.jt808.dao.LocationInfoReportDao;
import com.yun.jt808.netty.session.Session;
import com.yun.jt808.netty.session.SessionManager;
import com.yun.jt808.po.AlarmDetailInfo;
import com.yun.jt808.po.AreaInfo;
import com.yun.jt808.po.CarTransportTimes;
import com.yun.jt808.po.CarryingCapacityInfo;
import com.yun.jt808.po.DeviceStatusInfo;
import com.yun.jt808.po.LocationInfoReport;
import com.yun.jt808.po.VehicleInfo;
import com.yun.jt808.service.LocationInfoService;
import com.yun.jt808.service.ServiceFactory;
import com.yun.jt808.utils.HttpUtils;
import com.yun.jt808.utils.Point;
import com.yun.jt808.utils.obj.JavaBeanBuilder;

/**
* @Description: 定位信息业务实现类
* @author James
* @date 2021年10月15日 下午1:05:04
 */
public class LocationInfoServiceImpl implements LocationInfoService {

	private static int default_low_load_start_ad = 3300;
	private static int default_low_load_end_ad = 3000;
	

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private static final String DATA_CHECK_TIME = "data_check_time";
	private static final String OVERSPEED_THRESHOLD = "overspeed_threshold";
	/**
	 * 速度转换系数，实际的速度是当前速度*10
	 */
	private static final int SPEED_CONVERSION_COEFFICIENT = 10;

	/**
	 * 超载临界值
	 */
	private static final String CHAOZAI = "CHAOZAI";
	/**
	 * 默认的超载值
	 */
	private static final int delfault_over_load_value = 4800;
	
	/**
	 * 默认的超速边界值
	 */
	private static final int default_over_speed_value = 400;
	
	
	/**
	 * 短信通知URL
	 */
	private static final String SMS_NOTICE_URL = "SMS_NOTICE_URL";

	private static final String SMS_FOR_OVER_LOAD_CONTENT = "SMS_FOR_OVER_LOAD_CONTENT";

	private static final String SMS_FOR_OVER_SPEED_CONTENT = "SMS_FOR_OVER_SPEED_CONTENT";
	
	private static final String SMS_FOR_LOW_LOAD_CONTENT = "SMS_FOR_LOW_LOAD_CONTENT";

	private static final String OVER_SPEED = "OVER_SPEED";
	private static final String LOW_LOAD_START_AD = "LOW_LOAD_START_AD";
	private static final String LOW_LOAD_END_AD = "LOW_LOAD_END_AD";
	/**
	 * 短信通知服务开关
	 */
	private static final String SMS_SERVER_STATE = "SMS_SERVER_STATE";
	
	/**
	 * 产生各种数据
	 * <p>
	 * 1、产生轨迹原始数据
	 * 2、更新设备状态数据
	 * 
	 * 3、产生载重数据
	 * 4、产生违规数据
	 * </p>
	 * @param vehicleLoadState
	 */
	public void doTask(LocationInfoReport locationInfoReport , Session s){
		LocationInfoReportDao locationInfoReportDao = DaoFactory.getDao().getLocationInfoReportDao();
		//检测并启动载重任务
		checkLoadTask(locationInfoReport);
		//检测车辆违规情况
		checkAlarmDetail(locationInfoReport, s);
//		doRecordCarTransportTimes(locationInfoReport);
		//保存设备状态信息
		saveDeviceStatusInfo(locationInfoReport);
		//产生轨迹原始数据
		locationInfoReportDao.insert(locationInfoReport);
	}
	
	/**
	 * 记录车辆运输区域记录
	 * @param locationInfoReport
	 */
	public void doRecordCarTransportTimes(LocationInfoReport locationInfoReport){
		String key = "doRecordCarTransportTimes" + getLoadTaskKey(locationInfoReport);
		//位置检测 
		Point p1 = new Point();
		p1.x = locationInfoReport.getProjectionLongitude().floatValue();
		p1.y = locationInfoReport.getProjectionLatitude().floatValue();
		AreaInfo areaInfo1 = ServiceFactory.getServicefactory().getAreaInfoService().getAreaInfoByPoint(p1);
		if(null != areaInfo1){
			logger.info("doRecordCarTransportTimes id = {}， 区域施工点名称 = {}" , areaInfo1.getId(), areaInfo1.getName());
			CarTransportTimes ctt =  SessionManager.carTransportTimesMap.get(key);
			if(ctt == null){
				logger.info("创建车辆运输任务");
				ctt = new CarTransportTimes();
				Date time = new Date();
				ctt.setCreateTime(time);
				ctt.setLicensePlate(locationInfoReport.getLicensePlate());
				ctt.setTerminalPhone(locationInfoReport.getTerminalPhone());
				ctt.setStartTime(time);
				ctt.setStartAreaId(areaInfo1.getId());
				ctt.setStartPoint(locationInfoReport.getProjectionLatitude().floatValue() + "," + locationInfoReport.getProjectionLongitude().floatValue());
				SessionManager.carTransportTimesMap.put(key, ctt);
			}else if(ctt.getStartAreaId() != areaInfo1.getId()){
				ctt.setEndTime(new Date());
				ctt.setEndAreaId(areaInfo1.getId());
				ctt.setEndPoint(locationInfoReport.getProjectionLatitude().floatValue() + "," + locationInfoReport.getProjectionLongitude().floatValue());
				DaoFactory.getDao().getCarTransportTimesDao().insert(ctt);
				SessionManager.carTransportTimesMap.remove(key);
				logger.info("保存车辆运输任务");
			}
		}else{
			logger.info("位置检测不到");
		}
	}
	
	private void saveDeviceStatusInfo(LocationInfoReport locationInfoReport) {
		//2、更新设备状态数据
		//DeviceStatusInfo deviceStatusInfo = JavaBeanBuilder.newDeviceStatusInfo(locationInfoReport);
		DeviceStatusInfoDao deviceStatusInfoDao = DaoFactory.getDao().getDeviceStatusInfoDao();
		DeviceStatusInfo deviceStatusInfo = deviceStatusInfoDao.query(locationInfoReport.getTerminalPhone());
		
		if(deviceStatusInfo != null){
			deviceStatusInfo.setLat(locationInfoReport.getLatitude());
			deviceStatusInfo.setLng(locationInfoReport.getLongitude());
			
			deviceStatusInfo.setSpeed(Float.valueOf(locationInfoReport.getSpeed()) / 10f);
			//DIS.setOnline(0); 这个采用心跳的机制进行检测处理
			//设置投影坐标
			deviceStatusInfo.setProjectionLatitude(locationInfoReport.getProjectionLatitude());
			deviceStatusInfo.setProjectionLongitude(locationInfoReport.getProjectionLongitude());
			
			deviceStatusInfo.setGpsTime(locationInfoReport.getLocationTime());
			//设置设备的在线状态
			deviceStatusInfo.setOnline(DeviceState.ONLINE.getIndex());
			
			deviceStatusInfo.setDirection(locationInfoReport.getDirection());
			//设置载重状态为
			Map<String,Object> attrMap = new HashMap<String,Object>();
			attrMap.put("vehicleLoadState", locationInfoReport.vehicleLoadState.getIndex());
			
			deviceStatusInfo.setExpandAttr(JSON.toJSONString(attrMap));
			deviceStatusInfoDao.update(deviceStatusInfo);
		}else{
			deviceStatusInfo = JavaBeanBuilder.createDeviceStatusInfo(locationInfoReport);
			deviceStatusInfoDao.insert(deviceStatusInfo);
		}
	}

	private void checkLoadTask(LocationInfoReport locationInfoReport){
		String taskKey = String.format("_LOAD_TASK_%s_", locationInfoReport.getTerminalPhone());
		CarryingCapacityInfo carryingCapacityInfo = SessionManager.loadTaskMap.get(taskKey);
		//位置检测 
		Point pointInfo = new Point();
		pointInfo.x = locationInfoReport.getProjectionLongitude().floatValue();
		pointInfo.y = locationInfoReport.getProjectionLatitude().floatValue();
		AreaInfo areaInfo = ServiceFactory.getServicefactory().getAreaInfoService().getAreaInfoByPoint(pointInfo);
		System.out.println("车牌号：" + locationInfoReport.getLicensePlate());
		System.out.println("当前任务KEY：" + taskKey);
		System.out.println("载重状态：" + locationInfoReport.vehicleLoadState.getName());
		if(areaInfo != null){
			System.out.println("当前区域名称：" + areaInfo.getName());
		}else{
			System.out.println("不再工作区域内容...");
		}
		if(carryingCapacityInfo != null){
//			String logCon = String.format("时间：%s, 载重状态：%s", DateUtil.dateToString(DateUtil.datePattern_3, new Date(System.currentTimeMillis())));
			System.out.println("运输任务信息：" + JSON.toJSONString(carryingCapacityInfo));
		}
		
		if(carryingCapacityInfo == null && locationInfoReport.vehicleLoadState == VehicleLoadState.manzai){
			//直接生成一条载重对象信息
			logger.info("++++++++++++++++++++ 创建并初始化载重记录信息    ++++++++++++++++++++");
			//首次创建对象，这时候的对象并未执行完成准备工作
			carryingCapacityInfo = new CarryingCapacityInfo();
			// 车牌号
			carryingCapacityInfo.setLicensePlate(locationInfoReport.getLicensePlate());
			// 设备ID
			carryingCapacityInfo.setTerminalId(locationInfoReport.getTerminalId());
			carryingCapacityInfo.setTerminalPhone(locationInfoReport.getTerminalPhone());
			//设置载重状态
			carryingCapacityInfo.setVehicleLoadState(locationInfoReport.vehicleLoadState);
			carryingCapacityInfo.setStartTime(new Date(System.currentTimeMillis()));
			// 载重开始经度.
			carryingCapacityInfo.setStartLat(locationInfoReport.getProjectionLatitude());
			// 载重开始纬度
			carryingCapacityInfo.setStartLng(locationInfoReport.getProjectionLongitude());
			if(areaInfo != null){
				carryingCapacityInfo.setLoadAreaId(areaInfo.getId());
				carryingCapacityInfo.setLoadAreaName(areaInfo.getName());
				logger.info("载重开始的区域ID={}， 区域名称 = {}", areaInfo.getId(), areaInfo.getName());
			}
			SessionManager.loadTaskMap.put(taskKey, carryingCapacityInfo);
		}else if(carryingCapacityInfo != null && locationInfoReport.vehicleLoadState == VehicleLoadState.kongzai){
			carryingCapacityInfo = SessionManager.loadTaskMap.remove(taskKey);
			//更新到数据库中
			logger.info(" 》》》》》》》 载重任务完成，数据准备入库处理  车牌 ={}， 检测次数 = {}", carryingCapacityInfo.getLicensePlate(), carryingCapacityInfo.endCheckTime);
			// 载重结束时间
			carryingCapacityInfo.setEndTime(new Date(System.currentTimeMillis()));
			// 载重结束经度
			carryingCapacityInfo.setEndLat(locationInfoReport.getProjectionLatitude());
			// 载重结束纬度
			carryingCapacityInfo.setEndLng(locationInfoReport.getProjectionLongitude());
			// 载重运输结束时重量 单位：千克 TODO 
			carryingCapacityInfo.setEndWeight(carryingCapacityInfo.getStartWeight());
			// 本次运输载重重量 单位：千克    待定
			if(carryingCapacityInfo.getEndTime() != null && carryingCapacityInfo.getStartTime() != null)
			{
				int useTime = (int) ((carryingCapacityInfo.getEndTime().getTime() - carryingCapacityInfo.getStartTime().getTime()) / 1000);
				// 本躺运输持续时间 单位：秒
				carryingCapacityInfo.setDurationTime(useTime);
			}
			if(areaInfo != null){
				carryingCapacityInfo.setUnLoadAreaId(areaInfo.getId());
				carryingCapacityInfo.setUnLoadAreaName(areaInfo.getName());
				logger.info("载重结束的区域ID={}， 区域名称 = {}", areaInfo.getId(), areaInfo.getName());
			}
			boolean flag = DaoFactory.getDao().getCarryingCapacityInfoDao().insert(carryingCapacityInfo);
			if(flag){
				logger.info("++++++++++++++++++++载重信息入库成功 ++++++++++++++++++++");
			}else{
				logger.info("++++++++++++++++++++载重信息入库失败 ++++++++++++++++++++");
			}
		}
		
		/**
		 * 载重信息为空，而且处于符合载重录入的条件
		 */
//		if(null == CCI && (LIR.VLS == VehicleLoadState.zhongzai || LIR.VLS == VehicleLoadState.manzai)){
//			//直接生成一条载重对象信息
//			logger.info("++++++++++++++++++++ 创建并初始化载重记录信息    ++++++++++++++++++++");
//			//首次创建对象，这时候的对象并未执行完成准备工作
//			CCI = new CarryingCapacityInfo();
//			CCI.setLicensePlate(LIR.getLicensePlate());// 车牌号
//			CCI.setTerminalId(LIR.getTerminalId());// 设备ID
//			CCI.setTerminalPhone(LIR.getTerminalPhone());
//			CCI.setVehicleLoadState(LIR.VLS);//设置载重状态
//			CCI.startOK = false;
//			CCI.startCheckTime = 1;//创建也算是数据有效校验一次
//			SessionManager.loadTaskMap.put(taskKey, CCI);
//		}else if(null != CCI && (LIR.VLS == VehicleLoadState.zhongzai || LIR.VLS == VehicleLoadState.manzai)){
//			CCI.startCheckTime++;
//			if((!CCI.startOK) && (VehicleLoadState.checkTime <= CCI.startCheckTime)){//满足条件，准备完成 true
//				logger.info("+++++++++++ 载重任务还在执行中-------------");
//				CCI.startOK = true;
//				// 载重开始时间
//				CCI.setStartTime(new Date(System.currentTimeMillis()));
//				// 载重开始经度.
//				CCI.setStartLat(LIR.getProjectionLatitude());
//				// 载重开始纬度
//				CCI.setStartLng(LIR.getProjectionLongitude());
//				//获取车辆信息：
//				VehicleInfo vehicleInfo = DaoFactory.getDao().getVehicleInfoDao().queryVehicle(CCI.getLicensePlate(), CCI.getTerminalPhone());
//				if(null != vehicleInfo){
//					logger.info("正在执行载重的车牌 = {}， 设备SIM号 = {}" , CCI.getLicensePlate(), CCI.getTerminalPhone());
//					completeLoadWeightAndVolume(vehicleInfo, LIR.VLS, CCI);
//				}
//				
//				if(areaInfo != null){
//					CCI.setLoadAreaId(areaInfo.getId());
//					CCI.setLoadAreaName(areaInfo.getName());
//					logger.info("载重开始的区域ID={}， 区域名称 = {}", areaInfo.getId(), areaInfo.getName());
//				}
//				
////				//少载,判断速度大于0，而且载重状态为半载 ...
////				if(CCI.endOK && LIR.getSpeed() > 0 && (VehicleLoadState.chaozai == VLS))
////				{
////					AlarmDetailInfo alarmDetailInfo = JavaBeanBuilder.newAlarmDetailInfo(AlarmDetailEnums.weiguixiehuo,LIR);
////					doSmallLoadAlarm(alarmDetailInfo, LIR);
////				}
//			}else{
//				logger.info("++++++++++++++++++++ 载重任务执行中 startOK = {}, startCheckTime = {} +++++++++++++++++++" , CCI.startOK , CCI.startCheckTime);
//			}
//		}else if(null != CCI && LIR.VLS == VehicleLoadState.kongzai && CCI.startOK){
//			logger.info("+++++++++++ 载重结束入库记录-------------");
//			CCI.endCheckTime++;
//			if(VehicleLoadState.checkTime <= CCI.endCheckTime){//满足条件，运行完成 true
//				CCI.endOK = true;
//				CCI = SessionManager.loadTaskMap.remove(taskKey);
//				//更新到数据库中
//				logger.info(" 》》》》》》》 载重任务完成，数据准备入库处理  车牌 ={}， 检测次数 = {}", CCI.getLicensePlate(), CCI.endCheckTime);
//				// 载重结束时间
//				CCI.setEndTime(new Date(System.currentTimeMillis()));
//				// 载重结束经度
//				CCI.setEndLat(LIR.getProjectionLatitude());
//				// 载重结束纬度
//				CCI.setEndLng(LIR.getProjectionLongitude());
//				CCI.setEndWeight(CCI.getStartWeight());
//				
//				//private Float weight;// 本次运输载重重量 单位：千克    待定
//				if(CCI.getEndTime() != null && CCI.getStartTime() != null)
//				{
//					int useTime = (int) ((CCI.getEndTime().getTime() - CCI.getStartTime().getTime()) / 1000);
//					// 本躺运输持续时间 单位：秒
//					CCI.setDurationTime(useTime);
//				}
//				
//				if(areaInfo != null){
//					CCI.setUnLoadAreaId(areaInfo.getId());
//					CCI.setUnLoadAreaName(areaInfo.getName());
//					logger.info("载重任务完成区域   = {}", areaInfo.getName());
//					System.out.println("-----" + areaInfo.getName());
//				}else{
//					//违规卸货
//					AlarmDetailInfo alarmDetailInfo = JavaBeanBuilder.createAlarmDetailInfo(AlarmDetailEnums.weiguixiehuo,LIR);
//					doSmallLoadAlarm(alarmDetailInfo, LIR);
//				}
//				
//				logger.info("++++++++++++++++++++载重信息入库 ++++++++++++l={},v={}++++++++", CCI.getVolume(), CCI.getWeight());
//				boolean flag = DaoFactory.getDao().getCarryingCapacityInfoDao().insert(CCI);
//				if(flag){
//					logger.info("++++++++++++++++++++载重信息入库成功 ++++++++++++++++++++");
//				}else{
//					logger.info("++++++++++++++++++++载重信息入库失败 ++++++++++++++++++++");
//				}
//			}
//		}
	}

	/**
	 * 计算载重重量和方量
	 * @param vi
	 * @param vls
	 * @param cci
	 */
	@SuppressWarnings("incomplete-switch")
	private void completeLoadWeightAndVolume(VehicleInfo vi, VehicleLoadState vls, CarryingCapacityInfo cci) {
		//车辆的额定载重和方量
		float rateLoad = vi.getRatedLoad();
		float vehicleVolume = vi.getVehicleVolume();
		
		switch(vls){
		case zhongzai:
			cci.setWeight(rateLoad * 0.5f);
			cci.setVolume(vehicleVolume * 0.5f);
			break;
		case manzai:
			cci.setWeight(rateLoad);
			cci.setVolume(vehicleVolume);
			break;
			default:
		}
	}

	private String getLoadTaskKey(LocationInfoReport LIR) {
		return String.format("_%s_%s", LIR.getTerminalPhone(), LIR.getLicensePlate());
	}

	/**
	 * 检测警告信息
	 * @param locationInfoReport 
	 * @param vLS
	 */
	private void checkAlarmDetail(LocationInfoReport locationInfoReport, Session s){
		String cacheKey = getDeviceKey(locationInfoReport);
		//不如没有载重就不需要检测了
		CarryingCapacityInfo carryingCapacityInfo = SessionManager.loadTaskMap.get(cacheKey);
//		if(carryingCapacityInfo == null){
//			return;
//		}
		
		//doHandleAbnormalUnLoading(device_key, LIR, vLS);//违规卸货
		/*
		 * //超速
		recordOverSpeed(cacheKey, locationInfoReport, s);
		//少载
		recordLowLoad(cacheKey, locationInfoReport, s);
		//超重违规检测
		recordOverLoad(cacheKey, locationInfoReport, s);
		 */
		/**
		 * 该方案可能不符合具体的业务需求
		 * 1、实际的情况是，当前一个点是违规的。然后下一个点如果不违规就记录这段时间为违规时间，如果下一个继续违规就继续记录
		 * 下一个违规的时间。
		 */
		recordAlarmToOVERSPEED(locationInfoReport, s);
		recordAlarmToOVERLOAD(locationInfoReport, s);
	}
	
	private void recordAlarmToOVERLOAD(LocationInfoReport locationInfoReport, Session s) {
		int OLV = 5000;
		int speed = locationInfoReport.getSpeed();
		System.out.println("当前车次的车速：" + speed);
		speed = speed + RandomUtils.nextInt(350);
		System.out.println("干扰后的车速：" + speed);
		locationInfoReport.setLocationTime(new Date(System.currentTimeMillis()));
		
		StringBuilder sb = new StringBuilder();
		SimpleDateFormat sdf = new SimpleDateFormat("_yyyy_MM_DD_");
		sb.append(locationInfoReport.getLicensePlate()).append(sdf.format(new Date(System.currentTimeMillis()))).append("recordAlarmToOVERLOAD");
		String key =  sb.toString();
		Map<String, List<LocationInfoReport>> tempAlarmInfoMap = SessionManager.getInstance().getTempAlarmInfoMap();
		if(locationInfoReport.getAdvalue() > OLV){
			List<LocationInfoReport> ccilist = tempAlarmInfoMap.get(key);
			if(ccilist == null){
				ccilist = new ArrayList<>();
				tempAlarmInfoMap.put(key, ccilist);
			}
			ccilist.add(locationInfoReport);
			System.out.println("记录的error lenth：" + ccilist.size());
		}else{
			List<LocationInfoReport> lirlist = tempAlarmInfoMap.remove(key);
			if(lirlist != null){
				System.out.println("记录违规记录：时长为：" + lirlist.size() * 30 );
				genAlarmInfo(lirlist, AlarmDetailEnums.chaozhong);
			}
		}
		logger.info("记录违规的Key值：" + key);
	}

	private void recordAlarmToOVERSPEED(LocationInfoReport locationInfoReport, Session s){
		int overSpeed = 200;
		int speed = locationInfoReport.getSpeed();
		System.out.println("当前车次的车速：" + speed);
		speed = speed + RandomUtils.nextInt(350);
		System.out.println("干扰后的车速：" + speed);
		locationInfoReport.setLocationTime(new Date(System.currentTimeMillis()));
		
		StringBuilder sb = new StringBuilder();
		SimpleDateFormat sdf = new SimpleDateFormat("_yyyy_MM_DD_");
		sb.append(locationInfoReport.getLicensePlate()).append(sdf.format(new Date(System.currentTimeMillis()))).append("recordAlarmToOVERSPEED");
		String key =  sb.toString();
		Map<String, List<LocationInfoReport>> tempAlarmInfoMap = SessionManager.getInstance().getTempAlarmInfoMap();
		if(speed > overSpeed){
			List<LocationInfoReport> ccilist = tempAlarmInfoMap.get(key);
			if(ccilist == null){
				ccilist = new ArrayList<>();
				tempAlarmInfoMap.put(key, ccilist);
			}
			ccilist.add(locationInfoReport);
			System.out.println("记录的error lenth：" + ccilist.size());
		}else{
			List<LocationInfoReport> lirlist = tempAlarmInfoMap.remove(key);
			if(lirlist != null){
				System.out.println("记录违规记录：时长为：" + lirlist.size() * 30 );
				genAlarmInfo(lirlist, AlarmDetailEnums.chaosu);
			}
		}
		logger.info("记录违规的Key值：" + key);
	}
	
	
	private void genAlarmInfo(List<LocationInfoReport> lirlist, AlarmDetailEnums alarmDetailEnums) {
		if(CollectionUtils.isEmpty(lirlist)){
			logger.warn("没有违规数据需要处理");
			return;
		}
		
		String licensePlate = "";//车牌号
		Date startTime = null;//违规开始时间
		Date endTime = null;//违规结束时间
		Float startSpeed = 0f;//开始速度
		Float endSpeed = 0f;//结束速度
		
		for(int i=0;i<lirlist.size();i++){
			LocationInfoReport info = lirlist.get(i);
			if(StringUtils.isBlank(licensePlate)){
				licensePlate = info.getLicensePlate();
				startSpeed = info.getSpeed() / 10f;
			}
			if(startTime == null){
				startTime = info.getLocationTime();
			}
			
			if(i == (lirlist.size() -1)){//最后一个点
				endTime = info.getLocationTime();
				endSpeed = info.getSpeed() / 10f;
				if(startTime.getTime() >= endTime.getTime()){
					logger.warn("开始时间小于或者等于结束时间属于不合理的行为，所以这时候进行认为时间计算");
					startTime = new Date(System.currentTimeMillis() + 30 * lirlist.size() * 1000);//增加时间
				}
			}
		}
		
		//如果时间小于0就不要了
		if(startTime.after(endTime)){
			logger.warn("这种数据不需要处理时间小于零，实际时间是：" + (endTime.getTime() - startTime.getTime()));
			return;
		}
		
		AlarmDetailInfo detailInfo = new AlarmDetailInfo();
		detailInfo.setLicensePlate(licensePlate);
		detailInfo.setStartTime(startTime);
		detailInfo.setEndTime(endTime);
		detailInfo.setAlarmType(alarmDetailEnums.getIndex());
		detailInfo.setStartSpeed(startSpeed);
		detailInfo.setEndSpeed(endSpeed);
		
		logger.info("---------------------------");
		logger.info(JSON.toJSONString(detailInfo));
		logger.info("---------------------------");
		DaoFactory.getDao().getAlarmDetailInfoDao().insert(detailInfo);
	}

	/**
	 * 记录超载信息
	 * @param deviceKey
	 * @param locationInfoReport
	 * @param vLS
	 */
	private static void recordOverLoad(String deviceKey, LocationInfoReport locationInfoReport, Session s) {
		int low_load_start_ad = default_low_load_start_ad ;
		int low_load_end_ad = default_low_load_end_ad;
		
		if(CoreConfig.getIntValue(LOW_LOAD_START_AD) > 0){
			low_load_start_ad = CoreConfig.getIntValue(LOW_LOAD_START_AD);
		}
		
		if(CoreConfig.getIntValue(LOW_LOAD_END_AD) > 0){
			low_load_end_ad = CoreConfig.getIntValue(LOW_LOAD_END_AD);
		}
		
		int currentLowValue = 5000;//locationInfoReport.getAdvalue();
		
		if(currentLowValue > low_load_end_ad && currentLowValue < low_load_start_ad){
			//检测次数控制 TODO
			
			String licensePlate = "";
			if(s != null && StringUtils.isNotBlank(s.getLicensePlate())){
				licensePlate = s.getLicensePlate();
			}
			builidAndSendSMS(s, licensePlate, SMS_FOR_OVER_LOAD_CONTENT);
		}
		
		//-------------------------------------之前是想直接在这产生违规数据，这种方式目前不采用-------------------------------
		//获取到所有的违规进度记录
//		Map<AlarmDetailEnums, AlarmDetailInfo> map = SessionManager.alarmDetailMap.get(deviceKey);
//		//如果map不存在就创建一个map
//		if(map == null){
//			map = new HashMap<AlarmDetailEnums,AlarmDetailInfo>(10);
//			SessionManager.alarmDetailMap.put(deviceKey, map);
//		}
//		if(VehicleLoadState.chaozai == locationInfoReport.vehicleLoadState){
//			AlarmDetailInfo alarmDetailInfo = map.get(AlarmDetailEnums.chaozhong);
//			if(alarmDetailInfo == null){
//				alarmDetailInfo = JavaBeanBuilder.createAlarmDetailInfo(AlarmDetailEnums.chaozhong,locationInfoReport);
//				map.put(AlarmDetailEnums.chaozhong, alarmDetailInfo);
//			}
//			
//			alarmDetailInfo.startCheckTime++;
//			logger.info(String.format("超载记录开始累加 %s", alarmDetailInfo.startCheckTime));
//			//累计记录次数，当达到指定的次数这条记录被定义为违规
//			if(!alarmDetailInfo.startComplete){
//				if(alarmDetailInfo.startCheckTime >= Integer.valueOf(CoreConfig.getProperty(DATA_CHECK_TIME))){
//					logger.info(String.format("超载记录有效确认超载"));
//					//完成
//					alarmDetailInfo.startComplete = true;
//				}
//			}else{
//				logger.info(String.format("车辆 = %s, 超载中", locationInfoReport.getLicensePlate()));
//			}
//		}else{
//			//检测是否有超载记录
//			AlarmDetailInfo alarmDetailInfo = map.get(AlarmDetailEnums.chaozhong);
//			if(alarmDetailInfo != null){
//				if(alarmDetailInfo.startComplete){
//					alarmDetailInfo.endCheckTime++;
//					logger.info(String.format("车辆 %s, 超载记录结束累加 %s", locationInfoReport.getLicensePlate(), alarmDetailInfo.endCheckTime));
//					if(alarmDetailInfo.endCheckTime >= Integer.valueOf(CoreConfig.getProperty(DATA_CHECK_TIME))){
//						logger.info(String.format("车辆 %s， 超载结束 准备入库", locationInfoReport.getLicensePlate()));
//						//4、结束入库
//						// 警报结束时间 //这个只有在任务结束的时候才会产生
//						alarmDetailInfo.setEndTime(new Date(System.currentTimeMillis()));
//						// 警报结束经度
//						alarmDetailInfo.setEndLag(locationInfoReport.getLongitude());
//						// 警报结束纬度
//						alarmDetailInfo.setEndLat(locationInfoReport.getLatitude());
//						// 警报结束速度
//						alarmDetailInfo.setEndSpeed(Float.valueOf(locationInfoReport.getSpeed()));
//						DaoFactory.getDao().getAlarmDetailInfoDao().insert(alarmDetailInfo);
//						//5、清空当条记录
//						map.remove(AlarmDetailEnums.chaozhong);
//					}
//				}else{//直接清除记录信息
//					map.remove(AlarmDetailEnums.chaozhong);
//				}
//			}
//		}
	}
	
	private static void builidAndSendSMS(Session s, String licensePlate, String contentKey) {
		// TODO Auto-generated method stub
		String content = CoreConfig.getProperty(contentKey);
		
		String sms_server_state = CoreConfig.getProperty(SMS_SERVER_STATE);
		if(StringUtils.isNotBlank(sms_server_state)){
			sms_server_state = sms_server_state.trim();
			sms_server_state = sms_server_state.replaceAll(" ", "");
			//让服务开关生效
			if(sms_server_state.equals("close")){
				return;
			}
		}
		
		//=================短信的内容=================
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("empno", "AB58214");//这个如果没有就天空
		params.put("phoneno", s.getTerminalPhone());
		params.put("content", "abc");
		
		String smsNoticeUrl = CoreConfig.getProperty(SMS_NOTICE_URL);
		if(StringUtils.isNotBlank(smsNoticeUrl)){
			HttpUtils.postJson(smsNoticeUrl, params);
		}
	}

	/**
	 * 少载处理 
	 * <p> 少载的判定是 车辆的状态为重载时，就判定为少载<p>
	 * @param deviceKey 唯一标识KEY
	 * @param locationInfoReport 位置上报信息
	 * @param vLS 载重状态
	 */
	private void recordLowLoad(String deviceKey, LocationInfoReport locationInfoReport, Session s) {
		
		//获取违规临界值
		int over_speed_value_from_default = default_over_speed_value;
		CoreConfig.load();
		int over_speed_value_from_config = CoreConfig.getIntValue(OVER_SPEED);
		
		if(over_speed_value_from_config > 0){
			//如果配置文件中有有效值，就是用配置文件中的值
			over_speed_value_from_default = over_speed_value_from_config;
		}
		
		int currentSpeed = 5000;//locationInfoReport.getAdvalue();
		if(currentSpeed > over_speed_value_from_default){
			//检测次数控制 TODO
			String licensePlate = "";
			if(s != null && StringUtils.isNotBlank(s.getLicensePlate())){
				licensePlate = s.getLicensePlate();
			}
			builidAndSendSMS(s, licensePlate, SMS_FOR_OVER_SPEED_CONTENT);
		}
		
//		//1、获取记录进度 (根据什么条件获取记录进度????)	
//		//获取到所有的违规进度记录
//		Map<AlarmDetailEnums, AlarmDetailInfo> map = SessionManager.alarmDetailMap.get(deviceKey);
//		//如果map不存在就创建一个map
//		if(map == null){
//			map = new HashMap<AlarmDetailEnums,AlarmDetailInfo>(10);
//			SessionManager.alarmDetailMap.put(deviceKey, map);
//		}
//		if(VehicleLoadState.zhongzai == locationInfoReport.vehicleLoadState){
//			AlarmDetailInfo alarmDetailInfo = map.get(AlarmDetailEnums.shaozai);
//			if(alarmDetailInfo == null){
//				alarmDetailInfo = JavaBeanBuilder.createAlarmDetailInfo(AlarmDetailEnums.shaozai,locationInfoReport);
//				map.put(AlarmDetailEnums.shaozai, alarmDetailInfo);
//			}
//			
//			alarmDetailInfo.startCheckTime++;
//			logger.info(String.format("少载记录开始累加 %s", alarmDetailInfo.startCheckTime));
//			//累计记录次数，当达到指定的次数这条记录被定义为违规
//			if(!alarmDetailInfo.startComplete){
//				if(alarmDetailInfo.startCheckTime >= Integer.valueOf(CoreConfig.getProperty(DATA_CHECK_TIME))){
//					logger.info(String.format("少载记录有效确认少载"));
//					//完成
//					alarmDetailInfo.startComplete = true;
//				}
//			}else{
//				logger.info(String.format("车辆 = %s, 少载中", locationInfoReport.getLicensePlate()));
//			}
//		}else{
//			//检测是否有少载记录
//			AlarmDetailInfo alarmDetailInfo = map.get(AlarmDetailEnums.shaozai);
//			if(alarmDetailInfo != null){
//				if(alarmDetailInfo.startComplete){
//					alarmDetailInfo.endCheckTime++;
//					logger.info(String.format("车辆 %s, 少载记录结束累加 %s", locationInfoReport.getLicensePlate(), alarmDetailInfo.endCheckTime));
//					if(alarmDetailInfo.endCheckTime >= Integer.valueOf(CoreConfig.getProperty(DATA_CHECK_TIME))){
//						logger.info(String.format("车辆 %s， 少载结束 准备入库", locationInfoReport.getLicensePlate()));
//						//4、结束入库
//						// 警报结束时间 //这个只有在任务结束的时候才会产生
//						alarmDetailInfo.setEndTime(new Date(System.currentTimeMillis()));
//						// 警报结束经度
//						alarmDetailInfo.setEndLag(locationInfoReport.getLongitude());
//						// 警报结束纬度
//						alarmDetailInfo.setEndLat(locationInfoReport.getLatitude());
//						// 警报结束速度
//						alarmDetailInfo.setEndSpeed(Float.valueOf(locationInfoReport.getSpeed()));
//						DaoFactory.getDao().getAlarmDetailInfoDao().insert(alarmDetailInfo);
//						//5、清空当条记录
//						map.remove(AlarmDetailEnums.shaozai);
//					}
//				}else{//直接清除记录信息
//					map.remove(AlarmDetailEnums.shaozai);
//				}
//			}
//		}
	}
	
	/**
	 * 获取设备唯一的Key
	 * @param lIR 内容条件
	 * @return string key value
	 */
	private String getDeviceKey(LocationInfoReport locationInfoReport) {
		return new StringBuffer().append(locationInfoReport.getLicensePlate()).append("DEUAPP").append(locationInfoReport.getTerminalId()).toString();
	}
}

class Papers implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Date outTime;
	public String desc;
	private int value;
	
	
	
	public Papers(Date outTime, String desc, int value) {
		super();
		this.outTime = outTime;
		this.desc = desc;
		this.value = value;
	}
	public Object getNewObject() throws CloneNotSupportedException {
//		return (Papers) this.clone();
		return clone();
	}
	public Date getOutTime() {
		return outTime;
	}
	public void setOutTime(Date outTime) {
		this.outTime = outTime;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	
	
}
