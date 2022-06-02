package com.yun.jt808.po;

import java.util.Date;

/**
* @Description: 车辆运输次数
* @author James
* @date 2021年10月15日 下午12:55:06
 */
public class CarTransportTimes {

	private Integer id;
	private String licensePlate;
	private String terminalPhone;
	private Date startTime;
	private String startPoint;
	private Integer startAreaId;
	private Date endTime;
	private String endPoint;
	private Integer endAreaId;
	private Date createTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	public String getTerminalPhone() {
		return terminalPhone;
	}

	public void setTerminalPhone(String terminalPhone) {
		this.terminalPhone = terminalPhone;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public String getStartPoint() {
		return startPoint;
	}

	public void setStartPoint(String startPoint) {
		this.startPoint = startPoint;
	}

	public Integer getStartAreaId() {
		return startAreaId;
	}

	public void setStartAreaId(Integer startAreaId) {
		this.startAreaId = startAreaId;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}

	public Integer getEndAreaId() {
		return endAreaId;
	}

	public void setEndAreaId(Integer endAreaId) {
		this.endAreaId = endAreaId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
