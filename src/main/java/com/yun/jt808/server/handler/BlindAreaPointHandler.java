package com.yun.jt808.server.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSON;
import com.yun.jt808.config.CoreConfig;
import com.yun.jt808.netty.server.ServerHandler;
import com.yun.jt808.netty.session.Session;
import com.yun.jt808.netty.session.SessionManager;
import com.yun.jt808.po.LocationInfoReport;
import com.yun.jt808.server.PackageData;
import com.yun.jt808.service.ServiceFactory;
import com.yun.jt808.utils.BitOperator;
import com.yun.jt808.utils.ByteOperator;
import com.yun.jt808.utils.obj.JavaBeanBuilder;
import io.netty.channel.ChannelHandlerContext;
/**
 * 盲点坐标批处理
* @Description: TODO 
* @author James
* @date 2021年10月15日 下午1:31:59
 */
public class BlindAreaPointHandler implements ServerHandler{
	/**
	 * 日志对象
	 */
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public void handle(ChannelHandlerContext ctx, PackageData data) {
		logger.info("------ 批量坐标数据处理 start ------------");
		Session s = SessionManager.getObject().getSession(data.getChannel().id().toString());
		
		if(s == null) {
			logger.info("session is null");
			return;	
		}
		s.setFlowId(data.getMsgHeader().getFlowId());
		
		BitOperator bitOperator = new BitOperator();
		ByteOperator byteOperator = new ByteOperator(bitOperator);
		//获取数据内容
		byte[] datas = data.getMsgBodyBytes();
		logger.info("原始数据 {}" + Arrays.toString(datas));
		//获取数据项个数
		int dataItemNum = byteOperator.parseIntFromBytes(datas, 0, 2);
		logger.info(" 解析到的数据项个数  {}" , dataItemNum);
		//位置数据类型
		int positionType = byteOperator.parseIntFromBytes(datas, 2, 1);
		logger.info(" 解析到的位置数据类型 {}", positionType);
		//位置汇报数据项
		
		byte[] batchInfos = Arrays.copyOfRange(datas, 3, datas.length);
		logger.info(" 所有的批量定位信息： {}" , batchInfos);
		
		int msgLen = 122;
		
		List<byte[]> infosList = new ArrayList<>();
		for(int i=0; i<batchInfos.length; i++){
			byte value = batchInfos[i];
			if(value == msgLen){
				byte[] values = Arrays.copyOfRange(batchInfos, (i + 1) , (i + msgLen + 1));
				infosList.add(values);
			}
		}
		
		logger.info("最终解析到的定位数据：{}" , JSON.toJSONString(infosList));
		
//		int dataItems = byteOperator.parseIntFromBytes(datas, 3, datas.length);
//		logger.info(" 位置汇报数据项 {}" , dataItems);
//		
//		byte[] ls = Arrays.copyOfRange(datas, 2, datas.length);
//		logger.info(" 定位信息： {}" , ls);
//		
//		int len = byteOperator.parseIntFromBytes(ls, 0, 2);
//		logger.info(" 位置的长度： {}" , len);
//		
//		byte[] dataBody = Arrays.copyOfRange(ls, 2, ls.length - 2);
//		logger.info(" 定位信息： {}" , dataBody);
//		byte[] linfo = {8, 0, 0, 0,
//				 0, 12, 0, 3,
//				 1, 90, 91, -52,
//				 6, -38, 101, 52,
//				 0, 14, 0, 0, 0, -41, 
//				 23, 8, 48, 9, 69, 83,
//				 1, 4, 0, 0, 6, -72, 3, 2, 0, 0, 37, 4, 0, 0, 0, 0, 48, 1, 24, 49, 1, 18, -29, 70, -15, 15, 42, 15, 42, 11, -5, 11, -5, 11, -5, 11, -5, 12, 122, 12, 122, 12, 122, 12, 122, -14, 11, -57, 11, -57, 11, -57, 11, -57, 9, 100, 9, 100, 9, 100, 0, 0, 0, 0, 0, 0, -13, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -12, 5, -36, 13, -84, 16, 104};
//		data.setMsgBodyBytes(linfo);
		for(byte[] b : infosList){
			logger.info("b-----------------len = {}----------dataItemNum = {} ------------= {}", infosList.size(), dataItemNum, b);
			data.setMsgBodyBytes(b);
			//创建持久化对象类PO
			LocationInfoReport locationInfoReport = JavaBeanBuilder.createLocationInfoReport(new LocationInfoReport(), s, data);
			
			//负数排除
			if(locationInfoReport.getLatitude().intValue() < 0 || locationInfoReport.getLongitude().intValue() < 0){
				logger.error(String.format("坐标异常 latitude = %s, longitude = %s", 
						locationInfoReport.getLatitude().intValue(), locationInfoReport.getLongitude().intValue()));
				continue;
			}
			
			//检测排除无效数据
			Double maxX = null;
			Double minX = null;
			Double maxY = null;
			Double minY = null;
			try{
				String maxXStr = CoreConfig.getProperty("maxX");
				maxX = Double.valueOf(maxXStr);
				minX = Double.valueOf(CoreConfig.getProperty("minX"));
				
				maxY = Double.valueOf(CoreConfig.getProperty("maxY"));
				minY = Double.valueOf(CoreConfig.getProperty("minY"));
			}catch(Exception e){
				logger.error("边界检测值获取失败", e);
			}
			
			if(maxX == null || minX == null || maxY == null || minY == null){
				logger.error(String.format("maxX = %s, minX = %s, maxY = %s, minY = %s", maxX, minX, maxY, minY));
			}else{
				
				//检测边界数据
				if(locationInfoReport.getProjectionLatitude() != null 
						&& locationInfoReport.getProjectionLongitude() != null){
					
					if(locationInfoReport.getProjectionLatitude().intValue() < minY ||
							locationInfoReport.getProjectionLatitude().intValue() > maxY || 
							locationInfoReport.getProjectionLongitude().intValue() < minX ||
							locationInfoReport.getProjectionLongitude().intValue() > maxX){
						
						logger.error(String.format("投影坐标异常 ProjectionLatitude = %s, ProjectionLongitude = %s",
								locationInfoReport.getProjectionLatitude(),
								locationInfoReport.getProjectionLongitude()));
						continue;
					}
				}
				logger.info("投影坐标正常 lat = {}, lng = {}", locationInfoReport.getProjectionLatitude(), locationInfoReport.getProjectionLongitude());
			}
			
			ServiceFactory.getServicefactory().getLocationInfoServiceImpl().doTask(locationInfoReport, s);
		}
		
		logger.info("------ 批量坐标数据处理 end ------------");
	}

}
