package com.yun.jt808.po;

import java.math.BigDecimal;
/**
* @Description: 设备状态信息
* @author James
* @date 2021年4月21日 上午11:33:36
 */
import java.util.Date;

public class DeviceStatusInfo {
	/**
	 * 车牌号
	 */
	private String licensePlate;
	/**
	 * 设备号
	 */
	private String terminalId; 
	/**
	 * 设备sim号
	 */
	private String terminalPhone;
	
	/**
	 * 经度
	 */
	private BigDecimal lng; 
	/**
	 *  纬度
	 */
	private BigDecimal lat;
	
	/**
	 * 投影纬度
	 */
	private BigDecimal projectionLatitude;
	/**
	 * 投影经度
	 */
	private BigDecimal projectionLongitude;
	/**
	 * 厂家类型
	 */
	private int factoryType;
	/**
	 * 速度
	 */
	private float speed; 
	/**
	 * 在线状态 1表示在线,否则不在线
	 */
	private int online;
	/**
	 * GPS上传时间
	 */
	private Date gpsTime;
	/**
	 * 通讯协议类型
	 */
	private int protocolType; 
	/**
	 * 硬盘类型
	 */
	private int diskType; 
	/**
	 * 网络类型 1标识3G 2标识WIFI
	 */
	private int net;
	/**
	 * ad值 载重的电压值
	 */
	private int ad;
	/**
	 * 状态1
	 */
	private int s1;
	/**
	 * 状态2
	 */
	private int s2;
	/**
	 * 状态3
	 */
	private int s3; 
	/**
	 * 方向 正北方向为0度，顺时针方向增大，最大值360度
	 */
	private int direction; 
	/**
	 * 停车时长 单位：秒
	 */
	private int parkingDuration;
	/**
	 * 里程 单位：米
	 */
	private int mileage;
	/**
	 * 油量 单位：升
	 */
	private int oilMass;
	/**
	 * 创建时间
	 */
	private Date createTime; 
	/**
	 * 拓展属性字段 内容以Key-value的方式呈现
	 */
	private String expandAttr;

	public String getExpandAttr() {
		return expandAttr;
	}

	public void setExpandAttr(String expandAttr) {
		this.expandAttr = expandAttr;
	}

	public int getAd() {
		return ad;
	}

	public void setAd(int ad) {
		this.ad = ad;
	}
	
	public String getTerminalPhone() {
		return terminalPhone;
	}

	public void setTerminalPhone(String terminalPhone) {
		this.terminalPhone = terminalPhone;
	}
	
	public BigDecimal getProjectionLatitude() {
		return projectionLatitude;
	}

	public void setProjectionLatitude(BigDecimal projectionLatitude) {
		this.projectionLatitude = projectionLatitude;
	}

	public BigDecimal getProjectionLongitude() {
		return projectionLongitude;
	}

	public void setProjectionLongitude(BigDecimal projectionLongitude) {
		this.projectionLongitude = projectionLongitude;
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

	public BigDecimal getLng() {
		return lng;
	}

	public void setLng(BigDecimal lng) {
		this.lng = lng;
	}

	public BigDecimal getLat() {
		return lat;
	}

	public void setLat(BigDecimal lat) {
		this.lat = lat;
	}

	public int getFactoryType() {
		return factoryType;
	}

	public void setFactoryType(int factoryType) {
		this.factoryType = factoryType;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public int getOnline() {
		return online;
	}

	public void setOnline(int online) {
		this.online = online;
	}

	public Date getGpsTime() {
		return gpsTime;
	}

	public void setGpsTime(Date gpsTime) {
		this.gpsTime = gpsTime;
	}

	public int getProtocolType() {
		return protocolType;
	}

	public void setProtocolType(int protocolType) {
		this.protocolType = protocolType;
	}

	public int getDiskType() {
		return diskType;
	}

	public void setDiskType(int diskType) {
		this.diskType = diskType;
	}

	public int getNet() {
		return net;
	}

	public void setNet(int net) {
		this.net = net;
	}

	public int getS1() {
		return s1;
	}

	public void setS1(int s1) {
		this.s1 = s1;
	}

	public int getS2() {
		return s2;
	}

	public void setS2(int s2) {
		this.s2 = s2;
	}

	public int getS3() {
		return s3;
	}

	public void setS3(int s3) {
		this.s3 = s3;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public int getParkingDuration() {
		return parkingDuration;
	}

	public void setParkingDuration(int parkingDuration) {
		this.parkingDuration = parkingDuration;
	}

	public int getMileage() {
		return mileage;
	}

	public void setMileage(int mileage) {
		this.mileage = mileage;
	}

	public int getOilMass() {
		return oilMass;
	}

	public void setOilMass(int oilMass) {
		this.oilMass = oilMass;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
