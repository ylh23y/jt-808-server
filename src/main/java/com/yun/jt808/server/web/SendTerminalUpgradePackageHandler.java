package com.yun.jt808.server.web;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;

import com.yun.jt808.common.ServerConsts;
import com.yun.jt808.common.WebConsts;
import com.yun.jt808.common.cache.EncacheUtil;
import com.yun.jt808.netty.codec.MsgEncoder;
import com.yun.jt808.netty.http.AbstractHttpHandler;
import com.yun.jt808.netty.http.Response;
import com.yun.jt808.netty.session.Session;
import com.yun.jt808.netty.session.SessionManager;
import com.yun.jt808.server.PackageData;
import com.yun.jt808.server.ProtocolServerHandler;
import com.yun.jt808.server.PackageData.MsgHeader;
import com.yun.jt808.utils.HexStringUtils;
import com.yun.jt808.utils.file.FileUtils;
import com.yun.jt808.vo.ServerCommonRespMsgBody;

/**
 * 发送设备升级包
* @Description: TODO 
* @author James
* @date 2021年10月15日 下午1:33:03
 */
public class SendTerminalUpgradePackageHandler extends AbstractHttpHandler {

	@Override
	public Response handle(String request) {
		Map<String,Session> map = SessionManager.getObject().getChannelSessions();
		String terminalPhone = "186648836521";
		Session s = SessionManager.getObject().getSessionBySimPhone(terminalPhone);
		
		MsgEncoder msgEncoder = ProtocolServerHandler.getInstance().getEncoder();
		Object value = null;
		long upgradeStartTime = System.currentTimeMillis();
		try {
			byte[] bs = msgEncoder.encodeSendTerminalUpgradePackage(terminalPhone, FileUtils.toByteArray("F:\\shebei\\HB-R03CDG-17053-V1.0.4_TEST.bin"), s.getFlowId());
			String hexStr = HexStringUtils.bytesToHexString(bs);
			send2Client(s.getChannel(), bs);
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Response resp = new Response();
		resp.setCode(200);
		resp.setDesc("查询定位信息成功");
		resp.setValue("本次发送升级包耗时：" + (System.currentTimeMillis() - upgradeStartTime) + " 毫秒");
		return resp;
	}
	
	@SuppressWarnings("resource")
	public byte[] getVersionData() throws Exception{
		String filePath = "F:/shebei";
		File file = new File(filePath);
		String[] fileStrs = file.list();
		for(String fileStr : fileStrs){
			File temp = new File(filePath + "/" + fileStr);
			FileInputStream in = new FileInputStream(temp);
			
			int len = 0;
			byte[] bytes = new byte[1024];
			ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
			while((len = in.read()) != -1){
				bos.write(bytes, 0, len);
			}
			return bos.toByteArray();
		}
		return null;
	}

	
	public static void main(String[] args) throws Exception{
		byte[] filedata = FileUtils.toByteArray("F:\\shebei\\HB-R03CDG-17053-V1.0.4_TEST.bin");
		
	
	}
}
