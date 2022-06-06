package com.yun.jt808.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yun.jt808.common.enums.VehicleLoadState;

/**
 * 
 * @Description: 位置信息上报表
 * @author James
 * @date 2021年4月18日 下午3:35:52
 */
public class LocationInfoReport implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	/**
	 * 车牌号
	 */
	private String licensePlate;
	/**
	 * 北斗设备id
	 */
	private String terminalId;
	/**
	 * 设备sim号
	 */
	private String terminalPhone;
	/**
	 * 纬度
	 */
	private BigDecimal latitude;
	/**
	 * 经度
	 */
	private BigDecimal longitude;
	/**
	 * 投影纬度
	 */
	private BigDecimal projectionLatitude;
	/**
	 * 投影经度
	 */
	private BigDecimal projectionLongitude;
	/**
	 * 高程
	 */
	private Integer altitude;
	/**
	 * 速度
	 */
	private Integer speed;
	/**
	 * 方向
	 */
	private Integer direction;
	/**
	 * 定位时间
	 */
	private Date locationTime;
	/**
	 * 1:紧急触动报警开关后触发
	 */
	private byte urgentAlarm;
	/**
	 * 1：超速警报
	 */
	private byte speeding;
	/**
	 *  1：疲劳驾驶
	 */
	private byte fatigueDriving;
	/**
	 * 1：预警
	 */
	private byte earlyWarning; 
	/**
	 * 1:GNSS模块发送故障
	 */
	private byte GNSSFault;
	/**
	 * 1：GNSS天线未接或被剪断
	 */
	private byte GNSSLine;
	/**
	 * 1：GNSS天线短路
	 */
	private byte GNSSShort;
	/**
	 * 1：终端主电源欠压
	 */
	private byte underVoltage;
	/**
	 *  1：终端主电源掉电
	 */
	private byte powerDown;
	/**
	 * 1：终端LCD或显示器故障
	 */
	private byte LCDFault;
	/**
	 * 1：TTS模块故障
	 */
	private byte TTSFault;
	/**
	 * 1：摄像头故障
	 */
	private byte cameraFault;
	/**
	 * 1：当天累计驾驶超时
	 */
	private byte overTimeDriver;
	/**
	 * 1：超时停车
	 */
	private byte overTimeStop;
	/**
	 * 1：进出区域
	 */
	private byte importArea;
	/**
	 * 1:进出路线
	 */
	private byte importRoute;
	/**
	 * 1:路段行驶时间按不足/过长
	 */
	private byte routeDriverToLongOrNotEnough;
	/**
	 * 1：路线偏移警报
	 */
	private byte roteDeviation;
	/**
	 * 1：车辆VSS故障
	 */
	private byte VSSFault;
	/**
	 * 1：车辆油量异常
	 */
	private byte oilException;
	/**
	 * 1:车辆被盗
	 */
	private byte vehicleTheft;
	/**
	 * 1：车辆非法点火
	 */
	private byte vehicleIllegalIgnition;
	/**
	 * 1：车辆非法偏移
	 */
	private byte vehicleIllegalDeviation;
	/**
	 * 0 ACC关 1 ACC开
	 */
	private byte acc;
	/**
	 * 0为定位 1定位
	 */
	private byte locationState;
	/**
	 * 0运营状态 1停运状态
	 */
	private byte operationState;
	/**
	 * 经纬度是否加密 0否1是
	 */
	private byte isEncryption;
	/**
	 * 油路是否正常 0正常 1断开
	 */
	private byte isOilNormal;
	/**
	 * 电路是否正常 0正常 1断开
	 */
	private byte isCircuitNormal;
	/**
	 * 0车门解锁 1车门加锁
	 */
	private byte isLockDoor;
	private float weight;
	/**
	 * 载重原始电压数据
	 */
	private String ads;
	/**
	 * 载重值
	 */
	private int advalue;
	/**
	 * 载重状态
	 */
	private String loadState;
	
	public String getLoadState() {
		return loadState;
	}

	public void setLoadState(String loadState) {
		this.loadState = loadState;
	}

	public int getAdvalue() {
		return advalue;
	}

	public void setAdvalue(int advalue) {
		this.advalue = advalue;
	}

	public String getAds() {
		return ads;
	}

	public void setAds(String ads) {
		this.ads = ads;
	}

	public VehicleLoadState vehicleLoadState = null;

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	Logger logger = LoggerFactory.getLogger(getClass());

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

	public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	public Integer getAltitude() {
		return altitude;
	}

	public void setAltitude(Integer altitude) {
		this.altitude = altitude;
	}

	public Integer getSpeed() {
		return speed;
	}

	public void setSpeed(Integer speed) {
		this.speed = speed;
	}

	public Integer getDirection() {
		return direction;
	}

	public void setDirection(Integer direction) {
		this.direction = direction;
	}

	public byte getUrgentAlarm() {
		return urgentAlarm;
	}

	public Date getLocationTime() {
		return locationTime;
	}

	public void setLocationTime(Date locationTime) {
		this.locationTime = locationTime;
	}

	public void setUrgentAlarm(byte urgentAlarm) {
		this.urgentAlarm = urgentAlarm;
	}

	public byte getSpeeding() {
		return speeding;
	}

	public void setSpeeding(byte speeding) {
		this.speeding = speeding;
	}

	public byte getFatigueDriving() {
		return fatigueDriving;
	}

	public void setFatigueDriving(byte fatigueDriving) {
		this.fatigueDriving = fatigueDriving;
	}

	public byte getEarlyWarning() {
		return earlyWarning;
	}

	public void setEarlyWarning(byte earlyWarning) {
		this.earlyWarning = earlyWarning;
	}

	public byte getGNSSFault() {
		return GNSSFault;
	}

	public void setGNSSFault(byte gNSSFault) {
		GNSSFault = gNSSFault;
	}

	public byte getGNSSLine() {
		return GNSSLine;
	}

	public void setGNSSLine(byte gNSSLine) {
		GNSSLine = gNSSLine;
	}

	public byte getGNSSShort() {
		return GNSSShort;
	}

	public void setGNSSShort(byte gNSSShort) {
		GNSSShort = gNSSShort;
	}

	public byte getUnderVoltage() {
		return underVoltage;
	}

	public void setUnderVoltage(byte underVoltage) {
		this.underVoltage = underVoltage;
	}

	public byte getPowerDown() {
		return powerDown;
	}

	public void setPowerDown(byte powerDown) {
		this.powerDown = powerDown;
	}

	public byte getLCDFault() {
		return LCDFault;
	}

	public void setLCDFault(byte lCDFault) {
		LCDFault = lCDFault;
	}

	public byte getTTSFault() {
		return TTSFault;
	}

	public void setTTSFault(byte tTSFault) {
		TTSFault = tTSFault;
	}

	public byte getCameraFault() {
		return cameraFault;
	}

	public void setCameraFault(byte cameraFault) {
		this.cameraFault = cameraFault;
	}

	public byte getOverTimeDriver() {
		return overTimeDriver;
	}

	public void setOverTimeDriver(byte overTimeDriver) {
		this.overTimeDriver = overTimeDriver;
	}

	public byte getOverTimeStop() {
		return overTimeStop;
	}

	public void setOverTimeStop(byte overTimeStop) {
		this.overTimeStop = overTimeStop;
	}

	public byte getImportArea() {
		return importArea;
	}

	public void setImportArea(byte importArea) {
		this.importArea = importArea;
	}

	public byte getImportRoute() {
		return importRoute;
	}

	public void setImportRoute(byte importRoute) {
		this.importRoute = importRoute;
	}

	public byte getRouteDriverToLongOrNotEnough() {
		return routeDriverToLongOrNotEnough;
	}

	public void setRouteDriverToLongOrNotEnough(byte routeDriverToLongOrNotEnough) {
		this.routeDriverToLongOrNotEnough = routeDriverToLongOrNotEnough;
	}

	public byte getRoteDeviation() {
		return roteDeviation;
	}

	public void setRoteDeviation(byte roteDeviation) {
		this.roteDeviation = roteDeviation;
	}

	public byte getVSSFault() {
		return VSSFault;
	}

	public void setVSSFault(byte vSSFault) {
		VSSFault = vSSFault;
	}

	public byte getOilException() {
		return oilException;
	}

	public void setOilException(byte oilException) {
		this.oilException = oilException;
	}

	public byte getVehicleTheft() {
		return vehicleTheft;
	}

	public void setVehicleTheft(byte vehicleTheft) {
		this.vehicleTheft = vehicleTheft;
	}

	public byte getVehicleIllegalIgnition() {
		return vehicleIllegalIgnition;
	}

	public void setVehicleIllegalIgnition(byte vehicleIllegalIgnition) {
		this.vehicleIllegalIgnition = vehicleIllegalIgnition;
	}

	public byte getVehicleIllegalDeviation() {
		return vehicleIllegalDeviation;
	}

	public void setVehicleIllegalDeviation(byte vehicleIllegalDeviation) {
		this.vehicleIllegalDeviation = vehicleIllegalDeviation;
	}

	public byte getAcc() {
		return acc;
	}

	public void setAcc(byte acc) {
		this.acc = acc;
	}

	public byte getLocationState() {
		return locationState;
	}

	public void setLocationState(byte locationState) {
		this.locationState = locationState;
	}

	public byte getOperationState() {
		return operationState;
	}

	public void setOperationState(byte operationState) {
		this.operationState = operationState;
	}

	public byte getIsEncryption() {
		return isEncryption;
	}

	public void setIsEncryption(byte isEncryption) {
		this.isEncryption = isEncryption;
	}

	public byte getIsOilNormal() {
		return isOilNormal;
	}

	public void setIsOilNormal(byte isOilNormal) {
		this.isOilNormal = isOilNormal;
	}

	public byte getIsCircuitNormal() {
		return isCircuitNormal;
	}

	public void setIsCircuitNormal(byte isCircuitNormal) {
		this.isCircuitNormal = isCircuitNormal;
	}

	public byte getIsLockDoor() {
		return isLockDoor;
	}

	public void setIsLockDoor(byte isLockDoor) {
		this.isLockDoor = isLockDoor;
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

	public String getTerminalPhone() {
		return terminalPhone;
	}

	public void setTerminalPhone(String terminalPhone) {
		this.terminalPhone = terminalPhone;
	}
}
