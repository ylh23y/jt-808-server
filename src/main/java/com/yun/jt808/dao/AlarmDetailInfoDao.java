package com.yun.jt808.dao;

import com.yun.jt808.po.AlarmDetailInfo;

/**
 * 
 * @ClassName: AlarmDetailInfoDao
 * @Description: 警报记录
 * @author Gary
 * @date 2021年4月21日 下午2:19:24
 */
public interface AlarmDetailInfoDao {

	/**
	 * 插入警报信息
	 * @param alarmDetailInfo
	 * @return
	 */
	public boolean insert(AlarmDetailInfo alarmDetailInfo);
}
