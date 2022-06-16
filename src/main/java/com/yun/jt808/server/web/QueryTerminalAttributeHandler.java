package com.yun.jt808.server.web;

import java.io.Serializable;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import com.alibaba.fastjson.JSON;
import com.yun.jt808.common.ServerConsts;
import com.yun.jt808.common.cache.EncacheUtil;
import com.yun.jt808.netty.codec.MsgEncoder;
import com.yun.jt808.netty.http.AbstractHttpHandler;
import com.yun.jt808.netty.http.Response;
import com.yun.jt808.netty.session.Session;
import com.yun.jt808.netty.session.SessionManager;
import com.yun.jt808.server.ProtocolServerHandler;

/**
 * 查询设备属性
* @Description: TODO 
* @author James
* @date 2021年10月15日 下午1:13:28
 */
public class QueryTerminalAttributeHandler extends AbstractHttpHandler{
	
	private static final Logger logger = Logger.getLogger(QueryTerminalAttributeHandler.class);
	
	@Override
	public Response handle(String request) {
		Response resp = new Response();
		if(StringUtils.isBlank(request)){
			resp.setDesc("没有接收到内容");
			return resp;
		}
		QueryTerminalAttributeReq req;
		try {
			req = JSON.parseObject(request, QueryTerminalAttributeReq.class);
		} catch (Exception e1) {
			logger.error("数据格式转换错误", e1);
			resp.setDesc("数据格式转换错误");
			return resp;
		}
		
		String terminalPhone = req.getTerminalPhone();
		if(StringUtils.isBlank(terminalPhone)){
			resp.setDesc("设备SIM卡号不能为空");
			return resp;
		}
		
		Session s = SessionManager.getObject().getSessionBySimPhone(terminalPhone);
		
		if(s == null){
			resp.setDesc("设备不在线无法正常下发指令内容");
			return resp;
		}
		MsgEncoder msgEncoder = ProtocolServerHandler.getInstance().getEncoder();
		try {
			byte[] body = new byte[1];
			byte[] bs = msgEncoder.encodeHttpCommonRespMsg(ServerConsts.CMD_TERMINAL_ATTR_QUERY, body, terminalPhone, s.getFlowId());
			send2Client(s.getChannel(), bs);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Object value = EncacheUtil.get("QueryTerminalAttributeReplayHandler_" + s.getSimPhone());
		long start = System.currentTimeMillis();
		while(true){
			long end = System.currentTimeMillis();
			if(value != null || ((end - start) > 5000)){
				break;
			}
		}
		String key = "QueryTerminalAttributeReplayHandler_" + s.getSimPhone();
		value = EncacheUtil.get(key);
		resp.setDesc("查询终端属性成功");
		resp.setValue(value);
		
		// 清除缓存
		EncacheUtil.put(key, null);
		return resp;
	}

}

class QueryTerminalAttributeReq implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getTerminalPhone() {
		return terminalPhone;
	}
	public void setTerminalPhone(String terminalPhone) {
		this.terminalPhone = terminalPhone;
	}
	private int code;
	private String terminalPhone;
	
}
