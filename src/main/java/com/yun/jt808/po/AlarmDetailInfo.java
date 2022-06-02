package com.yun.jt808.po;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description: 警报信息表
 * @author James
 * @date 2021年4月21日 上午11:19:10
 */
public class AlarmDetailInfo {
	/**
	 * UUID随机不重复ID
	 */
	private int id; 
	/**
	 * 当前车牌号
	 */
	private String licensePlate;
	/**
	 * 北斗设备编号
	 */
	private String terminalId; 
	/**
	 * 设备sim号
	 */
	private String terminalPhone;
	/**
	 * 警报开始时间
	 */
	private Date startTime; 
	/**
	 * 警报结束时间
	 */
	private Date endTime; 
	/**
	 * 报警信息
	 */
	private String info;
	/**
	 * 报警描述
	 */
	private String description; 
	/**
	 * 警报类型
	 */
	private int alarmType;
	/**
	 * 报警开始经度
	 */
	private BigDecimal startLag;
	/**
	 * 报警开始纬度
	 */
	private BigDecimal startLat;
	/**
	 * 警报结束经度
	 */
	private BigDecimal endLag;
	/**
	 * 警报结束纬度
	 */
	private BigDecimal endLat; 
	/**
	 * 警报开始速度
	 */
	private float startSpeed; 
	/**
	 * 警报结束速度
	 */
	private float endSpeed;
	/**
	 * 警报开始里程
	 */
	private float startMileage; 
	/**
	 * 警报结束里程
	 */
	private float endMileage;
	/**
	 * 警报处理用户ID
	 */
	private String userId;
	/**
	 * 警报处理用户名称
	 */
	private String userName;
	/**
	 * 警报处理内容
	 */
	private String handleContent;
	/**
	 * 警报处理时间
	 */
	private Date handleTime;
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 准备完成
	 */
	public boolean startComplete = false;
	/**
	 * 准备校验次数
	 */
	public int startCheckTime = 0;
	/**
	 * 运行完成
	 */
	public boolean endComplete = false;
	/**
	 * 运行完成校验次数
	 */
	public int endCheckTime = 0;
	
	
	public static void add(int i){
		i+= 1000;
	}
	
	public AlarmDetailInfo() {
	}

	public String getTerminalPhone() {
		return terminalPhone;
	}

	public void setTerminalPhone(String terminalPhone) {
		this.terminalPhone = terminalPhone;
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

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getAlarmType() {
		return alarmType;
	}

	public void setAlarmType(int alarmType) {
		this.alarmType = alarmType;
	}

	public BigDecimal getStartLag() {
		return startLag;
	}

	public void setStartLag(BigDecimal startLag) {
		this.startLag = startLag;
	}

	public BigDecimal getStartLat() {
		return startLat;
	}

	public void setStartLat(BigDecimal startLat) {
		this.startLat = startLat;
	}

	public BigDecimal getEndLag() {
		return endLag;
	}

	public void setEndLag(BigDecimal endLag) {
		this.endLag = endLag;
	}

	public BigDecimal getEndLat() {
		return endLat;
	}

	public void setEndLat(BigDecimal endLat) {
		this.endLat = endLat;
	}

	public float getStartSpeed() {
		return startSpeed;
	}

	public void setStartSpeed(float startSpeed) {
		this.startSpeed = startSpeed;
	}

	public float getEndSpeed() {
		return endSpeed;
	}

	public void setEndSpeed(float endSpeed) {
		this.endSpeed = endSpeed;
	}

	public float getStartMileage() {
		return startMileage;
	}

	public void setStartMileage(float startMileage) {
		this.startMileage = startMileage;
	}

	public float getEndMileage() {
		return endMileage;
	}

	public void setEndMileage(float endMileage) {
		this.endMileage = endMileage;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getHandleContent() {
		return handleContent;
	}

	public void setHandleContent(String handleContent) {
		this.handleContent = handleContent;
	}

	public Date getHandleTime() {
		return handleTime;
	}

	public void setHandleTime(Date handleTime) {
		this.handleTime = handleTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
