package com.yun.jt808.dao;

import com.yun.jt808.po.TerminalRegErrorLog;

/**
* @Description: 设备注册错误日志表DAO接口定义
* @author James
* @date 2021年4月25日 上午10:53:23
 */
public interface TerminalRegErrorLogDao {

	/**
	 * 增加设备注册错误日志
	 * @param trel 日志对象
	 * @return
	 */
	public boolean insert(TerminalRegErrorLog trel);

	/**
	 * 检车是否存在
	 * @param licensePlate 车牌号
	 * @param terminalId 设备ID
	 * @return 存在 true 否则 false
	 */
	public boolean checkExits(String licensePlate, String terminalId);
}
