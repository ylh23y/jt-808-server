package com.yun.jt808.dao;

import com.yun.jt808.po.CarryingCapacityInfo;

/**
* @Description: 载重数据表DAO接口定义
* @author James
* @date 2021年4月27日 上午9:37:08
 */
public interface CarryingCapacityInfoDao {

	/**
	 * 新增载重数据
	 * @param carryingCapacityInfo
	 * @return
	 */
	public boolean insert(CarryingCapacityInfo carryingCapacityInfo);
}
