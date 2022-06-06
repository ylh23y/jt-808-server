package com.yun.jt808.po;
/**
 * 
* @Description: 车辆信息
* @author James
* @date 2021年7月31日 下午4:54:22
 */
public class VehicleInfo {

	private Integer id;
	private String licensePlate;
	private String terminalPhone;
	private float vehicleVolume;
	private float ratedLoad;

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

	public float getVehicleVolume() {
		return vehicleVolume;
	}

	public void setVehicleVolume(float vehicleVolume) {
		this.vehicleVolume = vehicleVolume;
	}

	public float getRatedLoad() {
		return ratedLoad;
	}

	public void setRatedLoad(float ratedLoad) {
		this.ratedLoad = ratedLoad;
	}

}
