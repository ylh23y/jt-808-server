package com.yun.jt808.vo;

/**
 * 
* @Description: TODO 
* @author James
* @date 2021年10月15日 下午1:39:08
 */
public class ServerCommonRespMsgBody {

	public static final byte SUCCESS = 0;
	public static final byte FAILURE = 1;
	public static final byte MSG_ERROR = 2;
	public static final byte UNSUPPORTED = 3;
	/**
	 * 数据库中无终端数据
	 */
	public static final byte WARNNING_MSG_ACK = 4;

	/**
	 * byte[0-1] 应答流水号 对应的终端消息的流水号
	 */
	private int replyFlowId;
	/**
	 * byte[2-3] 应答ID 对应的终端消息的ID
	 */
	private int replyId;
	/**
	 * 0：成功∕确认<br>
	 * 1：失败<br>
	 * 2：消息有误<br>
	 * 3：不支持<br>
	 * 4：报警处理确认<br>
	 */
	private byte replyCode;

	public ServerCommonRespMsgBody() {
	}

	public ServerCommonRespMsgBody(int replyFlowId, int replyId, byte replyCode) {
		super();
		this.replyFlowId = replyFlowId;
		this.replyId = replyId;
		this.replyCode = replyCode;
	}

	public int getReplyFlowId() {
		return replyFlowId;
	}

	public void setReplyFlowId(int flowId) {
		this.replyFlowId = flowId;
	}

	public int getReplyId() {
		return replyId;
	}

	public void setReplyId(int msgId) {
		this.replyId = msgId;
	}

	public byte getReplyCode() {
		return replyCode;
	}

	public void setReplyCode(byte code) {
		this.replyCode = code;
	}

	@Override
	public String toString() {
		return "ServerCommonRespMsg [replyFlowId=" + replyFlowId + ", replyId=" + replyId + ", replyCode=" + replyCode
				+ "]";
	}

}
