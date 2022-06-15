package com.yun.jt808.server.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.yun.jt808.config.CoreConfig;
import com.yun.jt808.netty.server.ServerHandler;
import com.yun.jt808.netty.session.Session;
import com.yun.jt808.netty.session.SessionManager;
import com.yun.jt808.po.LocationInfoReport;
import com.yun.jt808.server.PackageData;
import com.yun.jt808.service.BaseMsgProcessService;
import com.yun.jt808.service.ServiceFactory;
import com.yun.jt808.utils.obj.JavaBeanBuilder;
import io.netty.channel.ChannelHandlerContext;

/**
* @Description: 定位信息上报
* @author James
* @date 2021年4月18日 下午2:06:34
 */
public class LocationInfoReportHandler extends BaseMsgProcessService implements ServerHandler{

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public void handle(ChannelHandlerContext ctx, PackageData data) {
		logger.info(">>>>>>>>>>>>>>>>>>>>>> 记录 定位信息上报  start >>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		logger.debug(">>>>>>定位数据处理begin ：phone = [{}] , flowId = [{}]" , data.getMsgHeader().getTerminalPhone() ,data.getMsgHeader().getFlowId());
		
		Session s = SessionManager.getObject().getSession(data.getChannel().id().toString());
		
		if(s == null) {
			logger.info("session is null");
			return;	
		}
		s.setFlowId(data.getMsgHeader().getFlowId());
		//创建持久化对象类PO
		LocationInfoReport LIR = JavaBeanBuilder.createLocationInfoReport(new LocationInfoReport(), s, data);
		//负数排除
		if(LIR.getLatitude().intValue() < 0 || LIR.getLongitude().intValue() < 0){
			logger.error(String.format("坐标异常 latitude = %s, longitude = %s", LIR.getLatitude().intValue(), LIR.getLongitude().intValue()));
			return;
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
			if(LIR.getProjectionLatitude() != null && LIR.getProjectionLongitude() != null && (LIR.getProjectionLatitude().intValue() < minY ||
			LIR.getProjectionLatitude().intValue() > maxY || LIR.getProjectionLongitude().intValue() < minX ||
			LIR.getProjectionLongitude().intValue() > maxX)){
				
					logger.error(String.format("投影坐标异常 ProjectionLatitude = %s, ProjectionLongitude = %s",LIR.getProjectionLatitude(),LIR.getProjectionLongitude()));
					return;
			}
			logger.info("投影坐标正常 lat = {}, lng = {}", LIR.getProjectionLatitude(), LIR.getProjectionLongitude());
		}
		
		ServiceFactory.getServicefactory().getLocationInfoServiceImpl().doTask(LIR, s);
		//执行任务
		logger.info(">>>>>>>>>>>>>>>>>>>>>> 记录 定位信息上报  end >>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}
}
