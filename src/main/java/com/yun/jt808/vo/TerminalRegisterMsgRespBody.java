package com.yun.jt808.vo;

/**
 * 终端注册返回
* @Description: TODO 
* @author James
* @date 2021年10月15日 下午1:41:30
 */
public class TerminalRegisterMsgRespBody {
	/**
	 * 成功
	 */
	public static final byte SUCCESS = 0; 
	/**
	 * 车辆已被注册
	 */
	public static final byte CAR_ALREADY_REGISTERED = 1;
	/**
	 * 数据库中无该车辆
	 */
	public static final byte CAR_NOT_FOUND = 2;
	/**
	 * 终端已被注册
	 */
	public static final byte TERMINAL_ALREADY_REGISTERED = 3; 
	/**
	 * 数据库中无该终端
	 */
	public static final byte TERMINAL_NOT_FOUND = 4;
	/**
	 * byte[0-1] 应答流水号(WORD) 对应的终端注册消息的流水号
	 */
	private int replyFlowId;
	/***
	 * byte[2] 结果(BYTE) <br>
	 * 0：成功<br>
	 * 1：车辆已被注册<br>
	 * 2：数据库中无该车辆<br>
	 **/
	private byte replyCode;
	/**
	 * byte[3-x] 鉴权码(STRING) 只有在成功后才有该字段
	 */
	private String replyToken;

	public TerminalRegisterMsgRespBody() {
	}

	public int getReplyFlowId() {
		return replyFlowId;
	}

	public void setReplyFlowId(int flowId) {
		this.replyFlowId = flowId;
	}

	public byte getReplyCode() {
		return replyCode;
	}

	public void setReplyCode(byte code) {
		this.replyCode = code;
	}

	public String getReplyToken() {
		return replyToken;
	}

	public void setReplyToken(String token) {
		this.replyToken = token;
	}

	@Override
	public String toString() {
		return "TerminalRegisterMsgResp [replyFlowId=" + replyFlowId + ", replyCode=" + replyCode + ", replyToken="
				+ replyToken + "]";
	}

}
