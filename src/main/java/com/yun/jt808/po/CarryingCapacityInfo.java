package com.yun.jt808.po;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.yun.jt808.common.enums.VehicleLoadState;

/**
 * @Description: 载重信息表
 * @author James
 * @date 2021年4月21日 上午11:42:22
 */
public class CarryingCapacityInfo {
	/**
	 * 自增长编号ID
	 */
	private int id;
	/**
	 * 车牌号
	 */
	private String licensePlate;
	/**
	 * 设备ID
	 */
	private String terminalId;
	/**
	 * 设备sim号
	 */
	private String terminalPhone;
	/**
	 * 载重开始时间
	 */
	private Date startTime;
	/**
	 * 载重结束时间
	 */
	private Date endTime; 
	/**
	 * 载重开始经度
	 */
	private BigDecimal startLng;
	/**
	 * 载重开始纬度
	 */
	private BigDecimal startLat;
	/**
	 * 载重结束经度
	 */
	private BigDecimal endLng;
	/**
	 * 载重结束纬度
	 */
	private BigDecimal endLat;
	/**
	 * 载重运输开始时重量 单位：千克
	 */
	private float startWeight;
	/**
	 * 载重运输结束时重量 单位：千克
	 */
	private float endWeight;
	/**
	 * 本次运输载重重量 单位：吨
	 */
	private float weight;
	/**
	 * 运载的方量 立方米
	 */
	private float volume;
	private Integer loadAreaId;
	private String loadAreaName;
	private Integer unLoadAreaId;
	private String unLoadAreaName;
	/**
	 * 本躺运输持续时间 单位：秒
	 */
	private int durationTime;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 载重的类型
	 */
	private VehicleLoadState vehicleLoadState;
	
	//任务的状态定义
	/**
	 * 准备完成
	 */
	public boolean startOK = false;
	/**
	 * 准备校验次数
	 */
	public int startCheckTime = 3;
	/**
	 * 运行完成
	 */
	public boolean endOK = false;
	/**
	 * 运行完成校验次数
	 */
	public int endCheckTime = 0;
	
	public List<String> loglist = new ArrayList<>();
	
	
	/**
	 * 确认有效装载检验次数
	 */
	private int beforeCheckTimes = 0;
	private boolean beforeCheckPass = false;
	
	private int afterCheckTimes = 0;
	private boolean afterCheckPass = false;
	
	
	public Integer getLoadAreaId() {
		return loadAreaId;
	}

	public void setLoadAreaId(Integer loadAreaId) {
		this.loadAreaId = loadAreaId;
	}

	public String getLoadAreaName() {
		return loadAreaName;
	}

	public void setLoadAreaName(String loadAreaName) {
		this.loadAreaName = loadAreaName;
	}

	public Integer getUnLoadAreaId() {
		return unLoadAreaId;
	}

	public void setUnLoadAreaId(Integer unLoadAreaId) {
		this.unLoadAreaId = unLoadAreaId;
	}

	public String getUnLoadAreaName() {
		return unLoadAreaName;
	}

	public void setUnLoadAreaName(String unLoadAreaName) {
		this.unLoadAreaName = unLoadAreaName;
	}

	public String getTerminalPhone() {
		return terminalPhone;
	}

	public void setTerminalPhone(String terminalPhone) {
		this.terminalPhone = terminalPhone;
	}
	
	public float getVolume() {
		return volume;
	}

	public void setVolume(float volume) {
		this.volume = volume;
	}

	public VehicleLoadState getVehicleLoadState() {
		return vehicleLoadState;
	}

	public void setVehicleLoadState(VehicleLoadState vehicleLoadState) {
		this.vehicleLoadState = vehicleLoadState;
	}

	public int getBeforeCheckTimes() {
		return beforeCheckTimes;
	}

	public void setBeforeCheckTimes(int beforeCheckTimes) {
		this.beforeCheckTimes = beforeCheckTimes;
	}

	public boolean isBeforeCheckPass() {
		return beforeCheckPass;
	}

	public void setBeforeCheckPass(boolean beforeCheckPass) {
		this.beforeCheckPass = beforeCheckPass;
	}

	public int getAfterCheckTimes() {
		return afterCheckTimes;
	}

	public void setAfterCheckTimes(int afterCheckTimes) {
		this.afterCheckTimes = afterCheckTimes;
	}

	public boolean isAfterCheckPass() {
		return afterCheckPass;
	}

	public void setAfterCheckPass(boolean afterCheckPass) {
		this.afterCheckPass = afterCheckPass;
	}

	/**
	 * 是否已经装载
	 * @return
	 */
	public boolean isLoaded(){
		return startWeight > 0;
	}
	
	/**
	 * 该载重正在运输中
	 * @return
	 */
	public boolean isRuning(){
		
		return false;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public BigDecimal getStartLng() {
		return startLng;
	}

	public void setStartLng(BigDecimal startLng) {
		this.startLng = startLng;
	}

	public BigDecimal getStartLat() {
		return startLat;
	}

	public void setStartLat(BigDecimal startLat) {
		this.startLat = startLat;
	}

	public BigDecimal getEndLng() {
		return endLng;
	}

	public void setEndLng(BigDecimal endLng) {
		this.endLng = endLng;
	}

	public BigDecimal getEndLat() {
		return endLat;
	}

	public void setEndLat(BigDecimal endLat) {
		this.endLat = endLat;
	}

	public float getStartWeight() {
		return startWeight;
	}

	public void setStartWeight(float startWeight) {
		this.startWeight = startWeight;
	}

	public float getEndWeight() {
		return endWeight;
	}

	public void setEndWeight(float endWeight) {
		this.endWeight = endWeight;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public int getDurationTime() {
		return durationTime;
	}

	public void setDurationTime(int durationTime) {
		this.durationTime = durationTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
