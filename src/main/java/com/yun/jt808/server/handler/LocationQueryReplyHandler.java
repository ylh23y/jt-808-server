package com.yun.jt808.server.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.yun.jt808.common.cache.EncacheUtil;
import com.yun.jt808.netty.server.ServerHandler;
import com.yun.jt808.netty.session.Session;
import com.yun.jt808.netty.session.SessionManager;
import com.yun.jt808.po.LocationInfoReport;
import com.yun.jt808.server.PackageData;
import com.yun.jt808.service.BaseMsgProcessService;
import com.yun.jt808.utils.obj.JavaBeanBuilder;
import io.netty.channel.ChannelHandlerContext;
/**
* @Description: 定位信息查询应答 
* @author James
* @date 2021年10月15日 下午1:02:59
 */
public class LocationQueryReplyHandler extends BaseMsgProcessService implements ServerHandler{

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private static final String CACHEKEY = "LocationQueryReply";
	
	@Override
	public void handle(ChannelHandlerContext ctx, PackageData data) {
		Session s = SessionManager.getObject().getSession(data.getChannel().id().toString());
		if(s == null) {
			logger.info("session is null");
			return;	
		}
		s.setFlowId(data.getMsgHeader().getFlowId());
		byte[] bodyBytes = data.getMsgBodyBytes();
		int len = bodyBytes.length - 2;
		byte[] datas = new byte[len];
		System.arraycopy(bodyBytes, 2, datas, 0, len);
		
		data.setMsgBodyBytes(datas);
		
		//创建持久化对象类PO
		LocationInfoReport locationInfoReport = JavaBeanBuilder.createLocationInfoReport(new LocationInfoReport(), s, data);
		EncacheUtil.put(CACHEKEY, locationInfoReport);
	}

}
