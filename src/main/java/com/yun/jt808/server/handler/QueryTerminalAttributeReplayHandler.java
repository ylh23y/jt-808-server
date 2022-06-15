package com.yun.jt808.server.handler;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.yun.jt808.common.cache.EncacheUtil;
import com.yun.jt808.netty.server.ServerHandler;
import com.yun.jt808.netty.session.Session;
import com.yun.jt808.netty.session.SessionManager;
import com.yun.jt808.server.PackageData;
import com.yun.jt808.service.BaseMsgProcessService;
import com.yun.jt808.utils.BCD8421Operater;
import com.yun.jt808.utils.BitOperator;
import io.netty.channel.ChannelHandlerContext;
/**
 * 
* @Description: 查询设备信息应答类
* @author James
* @date 2021年10月15日 下午1:21:45
 */
public class QueryTerminalAttributeReplayHandler extends BaseMsgProcessService implements ServerHandler{
	
	private static final Logger logger = LoggerFactory.getLogger(QueryTerminalAttributeReplayHandler.class);
	
	@Override
	public void handle(ChannelHandlerContext ctx, PackageData data) {
		Session s = SessionManager.getObject().getSession(data.getChannel().id().toString());
		if(s == null) {
			logger.info("session is null");
			return;	
		}
		
		BitOperator bitOperator = new BitOperator();
		byte[] body = data.getMsgBodyBytes();
		
		int terminalType = bitOperator.byteToInteger(new byte[]{body[0], body[1]});
		String terminalMaker = new String(bitOperator.bytesToSplitBytes(body, 2, 5));
		String terminalNO = new String(bitOperator.bytesToSplitBytes(body, 7, 20));
		String terminalID = new String(bitOperator.bytesToSplitBytes(body, 27, 7));
		BCD8421Operater bcd8421Operater = new BCD8421Operater();
		String terminalPhone = bcd8421Operater.bcd2String(bitOperator.bytesToSplitBytes(body, 34, 10));
		int versionNOLen = body[44];
		
		String versionNO = new String(bitOperator.bytesToSplitBytes(body, 45, versionNOLen + 1));
		int terminalFirmwareVersionNoLen = body[45 + versionNOLen];
		String terminalFirmwareVersionNo = new String(bitOperator.bytesToSplitBytes(body, 46 + versionNOLen, terminalFirmwareVersionNoLen + 1));
		
		Map<String,Object> resultMap = new HashMap<String,Object>(10);
		resultMap.put("terminalType", terminalType);
		resultMap.put("terminalMaker", terminalMaker);
		resultMap.put("terminalNO", terminalNO);
		resultMap.put("terminalID", terminalID);
		resultMap.put("terminalPhone", terminalPhone);
		resultMap.put("versionNOLen", versionNOLen);
		resultMap.put("terminalFirmwareVersionNoLen", terminalFirmwareVersionNoLen);
		resultMap.put("terminalFirmwareVersionNo", terminalFirmwareVersionNo);
		resultMap.put("versionNO", versionNO);
		EncacheUtil.put("QueryTerminalAttributeReplayHandler_" + s.getSimPhone(), resultMap);
	}

	
	
	public static int bytesToInt(byte[] src, int offset) {  
	    int value;    
	    value = (int) ((src[offset] & 0xFF)   
	            | ((src[offset+1] & 0xFF)<<8)   
	            | ((src[offset+2] & 0xFF)<<16)   
	            | ((src[offset+3] & 0xFF)<<24));  
	    return value;  
	}  
}
