package com.yun.jt808.netty.codec;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import com.yun.jt808.common.ServerConsts;
import com.yun.jt808.common.WebConsts;
import com.yun.jt808.server.PackageData;
import com.yun.jt808.utils.BitOperator;
import com.yun.jt808.utils.JT808ProtocolUtils;
import com.yun.jt808.vo.ServerCommonRespMsgBody;
import com.yun.jt808.vo.Session;
import com.yun.jt808.vo.TerminalRegisterMsg;
import com.yun.jt808.vo.TerminalRegisterMsgRespBody;
/**
* @Description: TODO 
* @author James
* @date 2021年10月15日 下午12:03:07
 */
public class MsgEncoder {
	private BitOperator bitOperator;
	private JT808ProtocolUtils jt808ProtocolUtils;

	public MsgEncoder() {
		this.bitOperator = new BitOperator();
		this.jt808ProtocolUtils = new JT808ProtocolUtils();
	}
	
	public byte[] encodeHttpCommonRespMsg(int msgID, byte[] msgBody, String terminalPhone, int flowId) throws Exception{
		// 消息头
		int msgBodyProps = this.jt808ProtocolUtils.generateMsgBodyProps(msgBody.length, 0b000, false, 0);
		byte[] msgHeader = this.jt808ProtocolUtils.generateMsgHeader(terminalPhone, msgID, msgBody, msgBodyProps, flowId);
		byte[] headerAndBody = this.bitOperator.concatAll(msgHeader, msgBody);
		
		// 校验码
		int checkSum = this.bitOperator.getCheckSum4JT808(headerAndBody, 0, headerAndBody.length - 1);
		
		// 连接并且转义
		return this.doEncode(headerAndBody, checkSum);
	}
	
	//消息体的组成结构
	// 标识位 0x7e
	// 消息头
	// 消息体
	// 校验码
	
	public byte[] encodeSendTextToTerminal(int msgId, String phone,byte[] msgBody, int flowId) throws Exception{
		// 消息头
		int msgBodyProps = this.jt808ProtocolUtils.generateMsgBodyProps(msgBody.length, 0b000, false, 0);
		byte[] msgHeader = this.jt808ProtocolUtils.generateMsgHeader(phone, msgId, msgBody, msgBodyProps, flowId);
		byte[] headerAndBody = this.bitOperator.concatAll(msgHeader, msgBody);
		
		// 校验码
		int checkSum = this.bitOperator.getCheckSum4JT808(headerAndBody, 0, headerAndBody.length - 1);
		
		// 连接并且转义
		return this.doEncode(headerAndBody, checkSum);
	}
	
	public byte[] encodeSendTerminalUpgradePackage(String phone, byte[] body, int flowId) throws Exception{
		//升级类型 1 byte
		byte[] level = {0};
		//制造商ID 5 byte
		byte[] makeId = "10086".getBytes();
		//版本号
		byte[] versionNo = "updateV1.0.8".getBytes();
		//版本号长度 n byte
		byte[] versionNoLen = {(byte) versionNo.length};
		//升级数据包长度
		byte[] upgradeLen = {(byte) body.length};
		//body;//升级数据包
		byte[] upgradeData = {1,2};
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		out.write(level);
		out.write(makeId);
		out.write(versionNoLen);
		out.write(versionNo);
		out.write(upgradeLen);
		out.write(upgradeData);
		
		byte[] msgBody = out.toByteArray();
		// 消息头
		int msgBodyProps = this.jt808ProtocolUtils.generateMsgBodyProps(msgBody.length, 0b000, false, 0);
		byte[] msgHeader = this.jt808ProtocolUtils.generateMsgHeader(phone,
				WebConsts.SEND_TERMINAL_UPGRADE_PACKAGE, msgBody, msgBodyProps, flowId);
		byte[] headerAndBody = this.bitOperator.concatAll(msgHeader, msgBody);

		// 校验码
		int checkSum = this.bitOperator.getCheckSum4JT808(headerAndBody, 0, headerAndBody.length - 1);
		// 连接并且转义
		return this.doEncode(headerAndBody, checkSum);
	}
	
	public byte[] encodeLocationQueryReplay() throws Exception{
		byte[] msgBody = new byte[1];
		// 消息头
		int msgBodyProps = this.jt808ProtocolUtils.generateMsgBodyProps(msgBody.length, 0b000, false, 0);
		byte[] msgHeader = this.jt808ProtocolUtils.generateMsgHeader("",
				ServerConsts.LOCATION_QUERY, msgBody, msgBodyProps, 1584);
		byte[] headerAndBody = this.bitOperator.concatAll(msgHeader, msgBody);

		// 校验码
		int checkSum = this.bitOperator.getCheckSum4JT808(headerAndBody, 0, headerAndBody.length - 1);
		// 连接并且转义
		return this.doEncode(headerAndBody, checkSum);
	}

	public byte[] encode4TerminalRegisterResp(TerminalRegisterMsg req, TerminalRegisterMsgRespBody respMsgBody,
			int flowId) throws Exception {
		// 消息体字节数组
		byte[] msgBody = null;
		// 鉴权码(STRING) 只有在成功后才有该字段
		if (respMsgBody.getReplyCode() == TerminalRegisterMsgRespBody.SUCCESS) {
			msgBody = this.bitOperator.concatAll(Arrays.asList(
					// 流水号(2)
					bitOperator.integerTo2Bytes(respMsgBody.getReplyFlowId()), 
					// 结果
					new byte[] { respMsgBody.getReplyCode() }, 
					// 鉴权码(STRING)
					respMsgBody.getReplyToken().getBytes(ServerConsts.STRING_CHARSET)
			));
		} else {
			msgBody = this.bitOperator.concatAll(Arrays.asList(
					// 流水号(2)
					bitOperator.integerTo2Bytes(respMsgBody.getReplyFlowId()), 
					// 错误代码
					new byte[] { respMsgBody.getReplyCode() }
			));
		}

		// 消息头
		int msgBodyProps = this.jt808ProtocolUtils.generateMsgBodyProps(msgBody.length, 0b000, false, 0);
		byte[] msgHeader = this.jt808ProtocolUtils.generateMsgHeader(req.getMsgHeader().getTerminalPhone(),
				ServerConsts.CMD_TERMINAL_REGISTER_RESP, msgBody, msgBodyProps, flowId);
		byte[] headerAndBody = this.bitOperator.concatAll(msgHeader, msgBody);

		// 校验码
		int checkSum = this.bitOperator.getCheckSum4JT808(headerAndBody, 0, headerAndBody.length - 1);
		// 连接并且转义
		return this.doEncode(headerAndBody, checkSum);
	}

	public byte[] encode4ServerCommonRespMsg(PackageData req, ServerCommonRespMsgBody respMsgBody, int flowId)
			throws Exception {
		byte[] msgBody = this.bitOperator.concatAll(Arrays.asList(
				// 应答流水号
				bitOperator.integerTo2Bytes(respMsgBody.getReplyFlowId()), 
				// 应答ID,对应的终端消息的ID
				bitOperator.integerTo2Bytes(respMsgBody.getReplyId()), 
				// 结果
				new byte[] { respMsgBody.getReplyCode() }
		));

		// 消息头
		int msgBodyProps = this.jt808ProtocolUtils.generateMsgBodyProps(msgBody.length, 0b000, false, 0);
		byte[] msgHeader = this.jt808ProtocolUtils.generateMsgHeader(req.getMsgHeader().getTerminalPhone(),
				ServerConsts.CMD_COMMON_RESP, msgBody, msgBodyProps, flowId);
		byte[] headerAndBody = this.bitOperator.concatAll(msgHeader, msgBody);
		// 校验码
		int checkSum = this.bitOperator.getCheckSum4JT808(headerAndBody, 0, headerAndBody.length - 1);
		// 连接并且转义
		return this.doEncode(headerAndBody, checkSum);
	}

	public byte[] encode4ParamSetting(byte[] msgBodyBytes, Session session) throws Exception {
		// 消息头
		int msgBodyProps = this.jt808ProtocolUtils.generateMsgBodyProps(msgBodyBytes.length, 0b000, false, 0);
		byte[] msgHeader = this.jt808ProtocolUtils.generateMsgHeader(session.getTerminalPhone(),
				ServerConsts.CMD_TERMINAL_PARAM_SETTINGS, msgBodyBytes, msgBodyProps, session.currentFlowId());
		// 连接消息头和消息体
		byte[] headerAndBody = this.bitOperator.concatAll(msgHeader, msgBodyBytes);
		// 校验码
		int checkSum = this.bitOperator.getCheckSum4JT808(headerAndBody, 0, headerAndBody.length - 1);
		// 连接并且转义
		return this.doEncode(headerAndBody, checkSum);
	}

	private byte[] doEncode(byte[] headerAndBody, int checkSum) throws Exception {
		byte[] noEscapedBytes = this.bitOperator.concatAll(Arrays.asList(
				 // 0x7e
				new byte[] { ServerConsts.PKG_DELIMITER },
				 // 消息头+ 消息体
				headerAndBody,
				 // 校验码
				bitOperator.integerTo1Bytes(checkSum),
				// 0x7e
				new byte[] { ServerConsts.PKG_DELIMITER }
		));
		// 转义
		return jt808ProtocolUtils.doEscape4Send(noEscapedBytes, 1, noEscapedBytes.length - 2);
	}
}
