package com.yun.jt808.dao;

import com.yun.jt808.vo.TerminalRegisterMsg.TerminalRegInfo;

/**
 * 
* @Description: 终端注册信息DAO操作接口定义
* @author James
* @date 2021年4月19日 上午11:23:59
 */
public interface TerminalDao {
	
	/**
	 * 添加终端注册信息
	 * @param terminalRegInfo
	 * @return
	 */
	public boolean insert(TerminalRegInfo terminalRegInfo);

	/**
	 * 根据终端ID和车牌号获取终端信息
	 * @param terminalId 终端ID
	 * @param licensePlate 车牌号
	 * @return
	 */
	public TerminalRegInfo queryTerminal(String terminalId, String licensePlate);
	
	/**
	 * 根据Sim卡信息查询终端设备信息
	 * @param simPhone sim卡
	 * @return
	 */
	public TerminalRegInfo queryTerminalBySimPhone(String simPhone);

	/**
	 * 根据设备号码查询终端设备信息
	 * @param terminalPhone 设备号码
	 * @return
	 */
	public TerminalRegInfo queryTerminalRegInfo(String terminalPhone);

	/**
	 * 更新注册设备信息
	 * @param tri 设备对象
	 * @return
	 */
	public boolean update(TerminalRegInfo tri);
}
