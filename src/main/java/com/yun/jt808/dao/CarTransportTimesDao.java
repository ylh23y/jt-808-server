package com.yun.jt808.dao;

import com.yun.jt808.po.CarTransportTimes;

/**
* @Description: TODO 
* @author James
* @date 2021年10月15日 下午12:35:29
 */
public interface CarTransportTimesDao {

	/**
	 * 添加车轨迹次数
	 * @param ctt
	 * @return
	 */
	public boolean insert(CarTransportTimes ctt);
}
