package com.yun.jt808.server.web;

import java.util.Map;

import com.alibaba.fastjson.JSON;

import com.yun.jt808.common.ServerConsts;
import com.yun.jt808.common.cache.EncacheUtil;
import com.yun.jt808.netty.codec.MsgEncoder;
import com.yun.jt808.netty.http.AbstractHttpHandler;
import com.yun.jt808.netty.http.Response;
import com.yun.jt808.netty.session.Session;
import com.yun.jt808.netty.session.SessionManager;
import com.yun.jt808.server.PackageData;
import com.yun.jt808.server.PackageData.MsgHeader;
import com.yun.jt808.server.ProtocolServerHandler;
import com.yun.jt808.utils.HexStringUtils;
import com.yun.jt808.vo.ServerCommonRespMsgBody;
/**
 * 设备坐标查询
* @Description: TODO 
* @author James
* @date 2021年10月15日 下午1:32:38
 */
public class LocationQueryHandler extends AbstractHttpHandler{

	@Override
	public Response handle(String request) {
		Map<String,Session> map = SessionManager.getObject().getChannelSessions();
		String terminalPhone = "186648836521";
		Session s = SessionManager.getObject().getSessionBySimPhone(terminalPhone);
		
		ServerCommonRespMsgBody respMsgBody = new ServerCommonRespMsgBody();
		respMsgBody.setReplyCode(ServerCommonRespMsgBody.SUCCESS);
		respMsgBody.setReplyFlowId(ServerConsts.LOCATION_QUERY);
		respMsgBody.setReplyId(ServerConsts.LOCATION_QUERY);
		int flowId = super.getFlowId(s.getChannel());
		
		MsgEncoder msgEncoder = ProtocolServerHandler.getInstance().getEncoder();
		MsgHeader msgHeader = new MsgHeader();
		msgHeader.setHasSubPackage(false);
		msgHeader.setTerminalPhone(terminalPhone);
		msgHeader.setMsgId(ServerConsts.LOCATION_QUERY);
		PackageData data = new PackageData();
		data.setMsgHeader(msgHeader);
		
		Object value = null;
		try {
			byte[] bs = msgEncoder.encodeLocationQueryReplay();
			String hexStr = HexStringUtils.bytesToHexString(bs);
			byte[] results = HexStringUtils.hexStringToBytes("7e82010005186648836521000000e98201009f7e");
			send2Client(s.getChannel(), bs);
			
			long start = System.currentTimeMillis();
			while(true){
				long end = System.currentTimeMillis();
				if(value != null || ((end - start) > 3000)){
					break;
				}
			}
			value = EncacheUtil.get("LocationQueryReply");
		} catch (InterruptedException e) {
//			e.printStackTrace();
		} catch (Exception e) {
//			e.printStackTrace();
		}
		
		if(value != null){
		}
		
		Response resp = new Response();
		resp.setCode(200);
		resp.setDesc("查询定位信息成功");
		resp.setValue(JSON.toJSON(value));
		return resp;
	}

}
