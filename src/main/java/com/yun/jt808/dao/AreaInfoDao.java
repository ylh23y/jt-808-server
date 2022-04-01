package com.yun.jt808.dao;

import java.util.List;

import com.yun.jt808.common.enums.AreaType;
import com.yun.jt808.po.AreaInfo;

/**
* @Description: 区域信息表DAO接口定义 
* @author James
* @date 2021年5月2日 上午10:31:39
 */
public interface AreaInfoDao {
	
	/**
	 * 获取所有的区域信息
	 * @return List<T>
	 */
	public List<AreaInfo> getAllAreaInfos();

	/**
	 * 根据区域类型过滤区域列表
	 * @param t 区域类型
	 * @return List<T>
	 */
	public List<AreaInfo> getAreaInfoListByType(AreaType t);

	/**
	 * 获取区域列表
	 * @return
	 */
	public List<AreaInfo> getAreaInfoList();
}
