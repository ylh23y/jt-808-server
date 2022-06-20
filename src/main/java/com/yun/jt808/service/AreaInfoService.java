package com.yun.jt808.service;

import com.yun.jt808.common.enums.AreaType;
import com.yun.jt808.po.AreaInfo;
import com.yun.jt808.utils.Point;

/**
* @Description: 区域业务接口定义
* @author James
* @date 2021年5月2日 上午10:48:01
 */
public interface AreaInfoService {
	
	/**
	 * 根据点获取对应的区域
	 * @param p
	 * @return
	 */
	public AreaInfo getAreaInfoByPoint(Point p);

	/**
	 * 检测车辆在该点的操作是否合法
	 * <p>
	 * 车辆装载合法性校验
	 * 车辆卸载合法性校验
	 * </p>
	 * @param p 点
	 * @param t 区域类型
	 * @return
	 */
	public boolean checkAreaPointIn(Point p , AreaType t);
}
