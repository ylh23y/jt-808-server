package com.yun.jt808.server.web;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import com.yun.jt808.common.ServerConsts;
import com.yun.jt808.netty.codec.MsgEncoder;
import com.yun.jt808.netty.http.AbstractHttpHandler;
import com.yun.jt808.netty.http.Response;
import com.yun.jt808.netty.session.Session;
import com.yun.jt808.netty.session.SessionManager;
import com.yun.jt808.server.ProtocolServerHandler;
import com.yun.jt808.server.handler.terminal.TerminalAuthenticationHandler;
import com.yun.jt808.utils.HexStringUtils;

/**
* @Description: 发送文本DAO
* @author James
* @date 2021年10月8日 下午5:09:45
 */
public class SendTextToTerminalHandler extends AbstractHttpHandler {

	private static final Logger logger = Logger.getLogger(SendTextToTerminalHandler.class);
	
	@Override
	public Response handle(String request) {
		Response resp = new Response();
		if(StringUtils.isBlank(request)){
			resp.setDesc("没有接收到内容");
			return resp;
		}
		SendTextToTerminalReq req;
		try {
			req = JSON.parseObject(request, SendTextToTerminalReq.class);
		} catch (Exception e1) {
			logger.error("数据格式转换错误", e1);
			resp.setDesc("数据格式转换错误");
			return resp;
		}
		
		String terminalPhone = req.getTerminalPhone();
		String cmdContent = req.getCmdContent();
		if(StringUtils.isBlank(terminalPhone)){
			resp.setDesc("设备SIM卡号不能为空");
			return resp;
		}
		
		if(StringUtils.isBlank(cmdContent)){
			resp.setDesc("下发指令内容不能为空");
			return resp;
		}
		
		Session s = SessionManager.getObject().getSessionBySimPhone(terminalPhone);
		
		if(s == null){
			resp.setDesc("设备不在线无法正常下发指令内容");
			return resp;
		}
		
		MsgEncoder msgEncoder = ProtocolServerHandler.getInstance().getEncoder();
		long start = System.currentTimeMillis();
		
		try {
			ByteArrayOutputStream bodyOut = new ByteArrayOutputStream();
			//标记
			byte[] flag = {29};
			//文本信息
			String text = "#000000,UPDA:END #000000,UPDA:,183.62.139.91,21,HB-DV06-16091-V110162.sw #000000,UPDA:HBV000,112.95.227.15,8837,HB-R03SDG-17019-V1.0.7.bin";
			text = "#000000,REST";
//			text = "#000000,STVC:蒙J88888";
			text = "#000000,RDST";
			text = "#000000,UPDA:HBV000,112.95.227.15 ,8837,HB-R03SDG-17019-V1.0.7.bin";
			text = "#000000,UPDA:HBV000,112.95.227.15,8837,HB-R03CDG-17053-V1.0.4.bin";
			text = cmdContent;
			bodyOut.write(bitOperator.concatAll(flag, text.getBytes("GBK")));
			byte[] body = bodyOut.toByteArray();
			
			byte[] bs = msgEncoder.encodeSendTextToTerminal(ServerConsts.CMD_SEND_TEXT_TO_TERMINAL, terminalPhone, body, s.getFlowId());
			
			//正确的数据格式
//			bs = HexStringUtils.hexStringToBytes("7E8300001100150352184800161D233030303030302C555044413A454E44F77E");
			
//			bs = HexStringUtils.hexStringToBytes("7e8300000618664883652100381d233030303030302C555044413A454E447c7e");
			send2Client(s.getChannel(), bs);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.setDesc("平台下发指定设备指令报文成功");
		return resp;
	}

}

class SendTextToTerminalReq implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String terminalPhone;
	private String cmdContent;
	public String getTerminalPhone() {
		return terminalPhone;
	}
	public void setTerminalPhone(String terminalPhone) {
		this.terminalPhone = terminalPhone;
	}
	public String getCmdContent() {
		return cmdContent;
	}
	public void setCmdContent(String cmdContent) {
		this.cmdContent = cmdContent;
	}
	
	
}
