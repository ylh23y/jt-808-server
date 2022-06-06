package com.yun.jt808.po;

/**
 * @Description: 终端注册错误日志表
 * @author James
 * @date 2021年4月25日 上午10:51:34
 */
public class TerminalRegErrorLog {

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
	 * 错误码
	 */
	private int errorCode; 
	/**
	 * 错误原因
	 */
	private String errorSeason; 

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

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorSeason() {
		return errorSeason;
	}

	public void setErrorSeason(String errorSeason) {
		this.errorSeason = errorSeason;
	}

}
