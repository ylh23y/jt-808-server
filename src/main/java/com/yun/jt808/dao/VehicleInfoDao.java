package com.yun.jt808.dao;

import com.yun.jt808.po.VehicleInfo;
/**
* @Description: TODO 
* @author James
* @date 2021年10月15日 下午12:35:34
 */
public interface VehicleInfoDao {
	
	/**
	 * 查询车辆信息
	 * @param licensePlate
	 * @param terminalPhone
	 * @return
	 */
	VehicleInfo queryVehicle(String licensePlate, String terminalPhone);
}
