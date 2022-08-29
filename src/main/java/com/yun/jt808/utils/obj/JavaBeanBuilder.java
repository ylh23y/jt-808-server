package com.yun.jt808.utils.obj;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
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

import com.yun.jt808.common.ServerConsts;
import com.yun.jt808.common.enums.AlarmDetailEnums;
import com.yun.jt808.common.enums.DeviceState;
import com.yun.jt808.common.enums.VehicleLoadState;
import com.yun.jt808.config.CoreConfig;
import com.yun.jt808.netty.session.Session;
import com.yun.jt808.po.AlarmDetailInfo;
import com.yun.jt808.po.DeviceStatusInfo;
import com.yun.jt808.po.LocationInfoReport;
import com.yun.jt808.server.PackageData;
import com.yun.jt808.utils.BCD8421Operater;
import com.yun.jt808.utils.BitOperator;
import com.yun.jt808.utils.ByteOperator;
import com.yun.jt808.utils.DateUtil;

/**
 * 对象创建构建类
* @Description: TODO 
* @author James
* @date 2021年10月15日 下午1:26:58
 */
public class JavaBeanBuilder {

	/**
	 * 日志对象
	 */
	private static final Logger logger = LoggerFactory.getLogger(JavaBeanBuilder.class);
	/**
	 * 阀值最大，阀值最小，满载，半载，空载判断条件
	 */
	private static int maxAdValue = 5000, 
			minAdValue = 200, 
			fullAdValue = 4000, 
			halfAdValue = 3900, 
			nullAdValue = 3000;
	private static BitOperator bitOperator = new BitOperator();
	private static ByteOperator byteOperator = new ByteOperator(bitOperator);
	private static BCD8421Operater bcd8421Operater = new BCD8421Operater();
	
	public static LocationInfoReport createLocationInfoReport(LocationInfoReport locationInfoReport, Session s,PackageData data) {
		locationInfoReport.setLicensePlate(s.getLicensePlate());
		locationInfoReport.setTerminalId(s.getTerminalId());
		locationInfoReport.setTerminalPhone(data.getMsgHeader().getTerminalPhone());
		//消息体数据
		byte[] bodyBytes = data.getMsgBodyBytes();
		// 经度
		locationInfoReport.setLatitude(BigDecimal.valueOf(byteOperator.parseIntFromBytes(bodyBytes, 8, 6) / Math.pow(10,6)));
		// 纬度
		locationInfoReport.setLongitude(BigDecimal.valueOf(byteOperator.parseIntFromBytes(bodyBytes, 12, 6) / Math.pow(10,6)));
		// 高程
		locationInfoReport.setAltitude(byteOperator.parseIntFromBytes(bodyBytes, 16, 2));
		// 速度
		locationInfoReport.setSpeed(byteOperator.parseIntFromBytes(bodyBytes,18,2));
		// 方向
		locationInfoReport.setDirection(byteOperator.parseIntFromBytes(bodyBytes, 20, 1)); 
		//车载坐标
		locationInfoReport.setLatitude(locationInfoReport.getLatitude());
		locationInfoReport.setLongitude(locationInfoReport.getLongitude());
		//投影坐标
		BigDecimal px = covertToProjectionLatX(locationInfoReport.getLatitude());
		BigDecimal py = covertToProjectionLngY(locationInfoReport.getLongitude());
		if(px != null && py != null){
			locationInfoReport.setProjectionLatitude(px);
			locationInfoReport.setProjectionLongitude(py);
		}
		//获取BCD时间
		String dcdStr = bcd8421Operater.parseBcdStringFromBytes(bodyBytes, 22, 6);
		Date locationTime = DateUtil.stringToDate1("yyyyMMddhhmmss", "20" + dcdStr);
		locationInfoReport.setLocationTime(locationTime);
		//获取载重状态
		setVehicleLoadState(bodyBytes, locationInfoReport);
		setWarnInfo(bodyBytes, locationInfoReport);
		return locationInfoReport;
	}

	private static void setWarnInfo(byte[] bodyBytes, LocationInfoReport locationInfoReport) {
		//报警信息
		byte[] warns = bitOperator.bytesToBitArray(bodyBytes, 0, 4);
		// 1:紧急触动报警开关后触发
		locationInfoReport.setUrgentAlarm(warns[0]);
		// 1：超速警报
		locationInfoReport.setSpeeding(warns[1]);
		// 1：疲劳驾驶
		locationInfoReport.setFatigueDriving(warns[2]);
		// 1：预警
		locationInfoReport.setEarlyWarning(warns[3]);
		// 1:GNSS模块发送故障
		locationInfoReport.setGNSSFault(warns[4]);
		// 1：GNSS天线未接或被剪断
		locationInfoReport.setGNSSLine(warns[5]);
		// 1：GNSS天线短路
		locationInfoReport.setGNSSShort(warns[6]);
		// 1：终端主电源欠压
		locationInfoReport.setUnderVoltage(warns[7]);
		// 1：终端主电源掉电
		locationInfoReport.setPowerDown(warns[8]);
		// 1：终端LCD或显示器故障
		locationInfoReport.setLCDFault(warns[9]);
		// 1：TTS模块故障
		locationInfoReport.setTTSFault(warns[10]);
		// 1：摄像头故障
		locationInfoReport.setCameraFault(warns[11]);
		// 1：当天累计驾驶超时
		locationInfoReport.setOverTimeDriver(warns[18]);
		// 1：超时停车
		locationInfoReport.setOverTimeStop(warns[19]);
		// 1：进出区域
		locationInfoReport.setImportArea(warns[20]);
		// 1:进出路线
		locationInfoReport.setImportRoute(warns[21]);
		// 1:路段行驶时间按不足/过长
		locationInfoReport.setRouteDriverToLongOrNotEnough(warns[22]);
		// 1：路线偏移警报
		locationInfoReport.setRoteDeviation(warns[23]);
		// 1：车辆VSS故障
		locationInfoReport.setVSSFault(warns[24]);
		// 1：车辆油量异常
		locationInfoReport.setOilException(warns[25]);
		// 1:车辆被盗
		locationInfoReport.setVehicleTheft(warns[26]);
		// 1：车辆非法点火
		locationInfoReport.setVehicleIllegalIgnition(warns[27]);
		// 1：车辆非法偏移
		locationInfoReport.setVehicleIllegalDeviation(warns[28]);
		//状态信息
		byte[] states = bitOperator.bytesToBitArray(bodyBytes, 4, 4);
		//ACC开关状态
		locationInfoReport.setAcc(states[0]);
		//定位状态
		locationInfoReport.setLocationState(states[1]);
		//运营状态
		locationInfoReport.setOperationState(states[4]);
		//车辆油路
		locationInfoReport.setIsOilNormal(states[10]);
		//车辆电路
		locationInfoReport.setIsCircuitNormal(states[11]);
		//车门锁
		locationInfoReport.setIsLockDoor(states[12]);
	}

	private static void setVehicleLoadState(byte[] bodyBytes, LocationInfoReport locationInfoReport) {
		VehicleLoadState vehicleLoadState = VehicleLoadState.kongzai;
		//解析ad值
		List<Integer> bytef1 = bitOperator.getAdByteByFxtoFx(bodyBytes, ServerConsts.F1, ServerConsts.F2);
		List<Integer> bytef2 = bitOperator.getAdByteByFxtoFx(bodyBytes, ServerConsts.F2, ServerConsts.F3);
		List<Integer> bytef3 = bitOperator.getAdByteByFxtoFx(bodyBytes, ServerConsts.F3, ServerConsts.F4);
//		List<Integer> bytef4 = bitOperator.getBytesToSplitPointsToCovertList(bodyBytes, ServerConsts.F4);
		List<Integer> adList = new ArrayList<>();
		adList.addAll(bytef1);
		adList.addAll(bytef2);
		adList.addAll(bytef3);
		locationInfoReport.setAds(Arrays.toString(adList.toArray()));
		//如果配置文件里面有设置值就不用默认的值
		maxAdValue = setValueFromConfig(CoreConfig.getProperty("MAX_AD_VALUE"), maxAdValue);
		minAdValue = setValueFromConfig(CoreConfig.getProperty("MIN_AD_VALUE"), maxAdValue);
		
		//清除非法数据
		List<Integer> tempList = new ArrayList<Integer>();
		int totalAd = 0;
		for(Integer advalue : adList){
			if(advalue < maxAdValue && advalue > minAdValue){
				tempList.add(advalue);
				totalAd += advalue;
			} 
		}
		
		Collections.sort(tempList, new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return o1.compareTo(o2);
			}
		});
		
		//获取中间值
		int stateValue = 0;
		
		if(!CollectionUtils.isEmpty(tempList)){
//			stateValue = tempList.get(tempList.size() / 2);
			//采用平均值
			stateValue = totalAd / tempList.size();
		}
		/*
		 * 模拟载重值
		 */
		int simulationval = RandomUtils.nextInt(6000);
		stateValue = simulationval;
		locationInfoReport.setAdvalue(stateValue);
		
		
		fullAdValue = setValueFromConfig(CoreConfig.getProperty("FULL_AD_VALUE"), maxAdValue);
		halfAdValue = setValueFromConfig(CoreConfig.getProperty("HALF_AD_VALUE"), maxAdValue);
		nullAdValue = setValueFromConfig(CoreConfig.getProperty("NULL_AD_VALUE"), maxAdValue);
		System.out.println("平均AD值：" + stateValue);
		if(stateValue >= fullAdValue){
			vehicleLoadState = VehicleLoadState.manzai;
		}else if(stateValue > nullAdValue){
			//TODO 空载比较特殊先不处理
			vehicleLoadState = VehicleLoadState.zhongzai;
			logger.info("半载的情况不处理");
		}else{
			vehicleLoadState = VehicleLoadState.kongzai;
		}
		locationInfoReport.vehicleLoadState = vehicleLoadState;
	}
	
	public static void main(String[] args) {
		int value = -1;
		if(value >= 4000){
			System.out.println("满载");
		}else if(value <= 4000 && value > 3000){
			System.out.println("重载");
		}else if(value <= 3000){
			System.out.println("空载");
		}
	}

	private static int setValueFromConfig(String valueStr, int defaultValue) {
		if(StringUtils.isNotEmpty(valueStr)){
			try{
				return Integer.valueOf(valueStr.trim());
			}catch(NumberFormatException e){
				logger.error("配置文件值转换异常", e);
			}
		}
		return defaultValue;
	}

	/**
	 * 将《普通经度》转成投影坐标X
	 * @param longitude
	 * @return
	 */
	private static BigDecimal covertToProjectionLngY(BigDecimal longitude) {
		try {
			BigDecimal rdcx = BigDecimal.valueOf(Double.valueOf(CoreConfig.getProperty("referenceDeviceCoordinateX")));
			BigDecimal cx = BigDecimal.valueOf(Double.valueOf(CoreConfig.getProperty("coefficientX")));
			BigDecimal rpcx = BigDecimal.valueOf(Double.valueOf(CoreConfig.getProperty("referenceProjectionCoordinateX")));
			return longitude.subtract(rdcx).multiply(cx).add(rpcx);
		} catch (NumberFormatException e) {
			System.out.println("############################coreConfig 配置数据异常 ##################################");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将《普通纬度》转成投影坐标Y
	 * @param latitude
	 * @return
	 */
	private static BigDecimal covertToProjectionLatX(BigDecimal latitude) {
		try {
			BigDecimal cdcy = BigDecimal.valueOf(Double.valueOf(CoreConfig.getProperty("referenceDeviceCoordinateY")));
			BigDecimal cy = BigDecimal.valueOf(Double.valueOf(CoreConfig.getProperty("coefficientY")));
			BigDecimal rpcy = BigDecimal.valueOf(Double.valueOf(CoreConfig.getProperty("referenceProjectionCoordinateY")));
			return latitude.subtract(cdcy).multiply(cy).add(rpcy);
		} catch (NumberFormatException e) {
			System.out.println("############################coreConfig 配置数据异常 ##################################");
			e.printStackTrace();
		}
		return null;
	}

	public static DeviceStatusInfo createDeviceStatusInfo(LocationInfoReport locationInfoReport) {
		DeviceStatusInfo deviceStatusInfo = new DeviceStatusInfo();
		deviceStatusInfo.setLicensePlate(locationInfoReport.getLicensePlate());
		deviceStatusInfo.setTerminalId(locationInfoReport.getTerminalId());
		deviceStatusInfo.setTerminalPhone(locationInfoReport.getTerminalPhone());
		deviceStatusInfo.setLat(locationInfoReport.getLatitude());
		deviceStatusInfo.setLng(locationInfoReport.getLongitude());
		deviceStatusInfo.setSpeed(Float.valueOf(locationInfoReport.getSpeed()));
		//设置投影坐标
		deviceStatusInfo.setProjectionLatitude(locationInfoReport.getProjectionLatitude());
		deviceStatusInfo.setProjectionLongitude(locationInfoReport.getProjectionLongitude());
		deviceStatusInfo.setGpsTime(locationInfoReport.getLocationTime());
		//设置设备的在线状态
		deviceStatusInfo.setOnline(DeviceState.ONLINE.getIndex());
		deviceStatusInfo.setAd(locationInfoReport.getAdvalue());
		deviceStatusInfo.setDirection(locationInfoReport.getDirection());
		//设置载重状态为
		Map<String,Object> attrMap = new HashMap<String,Object>();
		attrMap.put("vehicleLoadState", locationInfoReport.vehicleLoadState.getIndex());
		
		deviceStatusInfo.setExpandAttr(JSON.toJSONString(attrMap));
		return deviceStatusInfo;
	}
	
	public static AlarmDetailInfo createAlarmDetailInfo(AlarmDetailEnums alarmDetailEnums, LocationInfoReport locationInfoReport) {
		AlarmDetailInfo alarmDetailInfo = new AlarmDetailInfo();
		// UUID随机不重复ID
//		String id = UUID.randomUUID().toString();
//		ADI.setId(id.replaceAll("-", ""));
		// 当前车牌号
		alarmDetailInfo.setLicensePlate(locationInfoReport.getLicensePlate());
		// 北斗设备编号
		alarmDetailInfo.setTerminalId(locationInfoReport.getTerminalId());
		alarmDetailInfo.setTerminalPhone(locationInfoReport.getTerminalPhone());
		// 警报开始时间
		alarmDetailInfo.setStartTime(new Date(System.currentTimeMillis()));
		// 报警信息
		alarmDetailInfo.setInfo(alarmDetailEnums.getName());
		// 报警描述
		// 警报类型
		alarmDetailInfo.setAlarmType(alarmDetailEnums.getIndex());
		// 报警开始经度
		alarmDetailInfo.setStartLag(locationInfoReport.getLongitude());
		// 报警开始纬度
		alarmDetailInfo.setStartLat(locationInfoReport.getLatitude());
		// 警报开始速度
		alarmDetailInfo.setStartSpeed(Float.valueOf(locationInfoReport.getSpeed()));
		// 警报开始里程
		alarmDetailInfo.setStartMileage(Float.valueOf(0));
		return alarmDetailInfo;
	}
}
