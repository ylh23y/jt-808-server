package com.yun.jt808.vo;

import java.net.SocketAddress;

import io.netty.channel.Channel;

/**
 * 
* @Description: TODO 
* @author James
* @date 2021年10月15日 下午1:55:11
 */
public class Session {

	private String id;
	private String terminalPhone;
	private Channel channel = null;
	/**
	 * 消息流水号 word(16) 按发送顺序从 0 开始循环累加
	 */
	private int currentFlowId = 0;
	/**
	 *  private ChannelGroup channelGroup = new
	// DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	// 客户端上次的连接时间，该值改变的情况:
	// 1. terminal --> server 心跳包
	// 2. terminal --> server 数据包
	 */
	private long lastCommunicateTimeStamp = 0L;
	
	/**
	 * 车牌号
	 */
	private String licensePlate;
	/**
	 * 终端ID
	 */
	private String terminalId;

	public Session() {
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public String getTerminalPhone() {
		return terminalPhone;
	}

	public void setTerminalPhone(String terminalPhone) {
		this.terminalPhone = terminalPhone;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public static String buildId(Channel channel) {
		return channel.id().asLongText();
	}

	public static Session buildSession(Channel channel) {
		return buildSession(channel, null);
	}

	public static Session buildSession(Channel channel, String phone) {
		Session session = new Session();
		session.setChannel(channel);
		session.setId(buildId(channel));
		session.setTerminalPhone(phone);
		session.setLastCommunicateTimeStamp(System.currentTimeMillis());
		return session;
	}

	public long getLastCommunicateTimeStamp() {
		return lastCommunicateTimeStamp;
	}

	public void setLastCommunicateTimeStamp(long lastCommunicateTimeStamp) {
		this.lastCommunicateTimeStamp = lastCommunicateTimeStamp;
	}

	public SocketAddress getRemoteAddr() {
		return this.channel.remoteAddress();
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj){
			return true;
		}
		if (obj == null){
			return false;
		}
		if (getClass() != obj.getClass()){
			return false;
		}
		Session other = (Session) obj;
		if (id == null) {
			if (other.id != null){
				return false;
			}
		} else if (!id.equals(other.id)){
			return false;
		}
		return true;
	}


	public synchronized int currentFlowId() {
		if (currentFlowId >= 0xffff){
			currentFlowId = 0;
		}
		return currentFlowId++;
	}

	public int getCurrentFlowId() {
		return currentFlowId;
	}

	public void setCurrentFlowId(int currentFlowId) {
		this.currentFlowId = currentFlowId;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}
	

}