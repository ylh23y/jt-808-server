package com.yun.jt808.netty.session;

import io.netty.channel.Channel;

/**
 * 
* @Description: TODO 
* <p>
* 
 * 最开始建立连接session连接的时候，sessionChannelId = a，但是在中途如果车牌号或者手机号码更换了，那么原来车辆设备的信息应该
 * 就是不正确的了。内存中的session信息还是旧的，导致了后面产生的所有数据还是旧的数据。
 * 
 * 解决方案：
 * 1、在做修改绑定信息的时候通知，并修改内存中的session信息
 * 2、每次都检测一次车牌号相关的基本信息在session中是否和当前的是一只的（同一个session会话中）
* </p>
* @author James
* @date 2021年7月28日 上午10:35:05
 */
public class Session {
	
	private String id;
	
	private Channel channel;
	/**
	 * 车牌号
	 */
	private String licensePlate;
	/**
	 * 终端ID
	 */
	private String terminalId;
	/**
	 * sim电话号码
	 */
	private String simPhone;
	
	/**
	 * 设备号码
	 */
	private String terminalPhone;
	/**
	 * 流水号
	 */
	private int flowId;
	
	
	public int getFlowId() {
		return flowId;
	}

	public void setFlowId(int flowId) {
		this.flowId = flowId;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
	
	public String getTerminalPhone() {
		return terminalPhone;
	}

	public void setTerminalPhone(String terminalPhone) {
		this.terminalPhone = terminalPhone;
	}

	public Session(){}
	
	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
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

	public String getSimPhone() {
		return simPhone;
	}

	public void setSimPhone(String simPhone) {
		this.simPhone = simPhone;
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
		return session;
	}
	
}
