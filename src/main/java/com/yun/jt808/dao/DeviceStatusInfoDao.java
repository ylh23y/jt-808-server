package com.yun.jt808.dao;

import com.yun.jt808.common.enums.DeviceState;
import com.yun.jt808.netty.session.Session;
import com.yun.jt808.po.DeviceStatusInfo;

/**
* @Description: 设备状态信息DAO接口定义
* @author James
* @date 2021年4月21日 下午1:25:52
 */
public interface DeviceStatusInfoDao {
	
	/**
	 * 设备状态信息录入
	 * @param deviceStatusInfo 设备信息对象
	 * @return true Or false
	 */
	public boolean insert(DeviceStatusInfo deviceStatusInfo);

	/**
	 * 查询设备状态信息
	 * @param terminalPhone 设备sim号
	 * @return 
	 */
	public DeviceStatusInfo query(String terminalPhone);

	/**
	 * 更新设备状态信息
	 * @param deviceStatusInfo 设备信息对象
	 * @return true OR false
	 */
	public boolean update(DeviceStatusInfo deviceStatusInfo);

	/**
	 * 设备离线
	 * @param s
	 * @param state
	 * @return
	 */
	public boolean updateOnlineState(Session s,DeviceState state);
}
