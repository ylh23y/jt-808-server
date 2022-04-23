package com.yun.jt808.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yun.jt808.dao.TerminalRegErrorLogDao;
import com.yun.jt808.po.TerminalRegErrorLog;
import com.yun.jt808.utils.ConnectionManager;

/**
 * 
* @Description: 终端注册错误日志DAO接口实现类
* @author James
* @date 2021年4月25日 上午10:55:01
 */
public class TerminalRegErrorLogDaoImpl implements TerminalRegErrorLogDao {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public boolean insert(TerminalRegErrorLog trel) {
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "insert into terminal_reg_error_log("
				+ "licensePlate,"
				+ "terminalId,"
				+ "errorCode,"
				+ "errorReason)"
				+ "VALUES (?,?,?,?)";
		
		try {
			conn = ConnectionManager.getAppDBConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, trel.getLicensePlate());
			ps.setString(2, trel.getTerminalId());
			ps.setInt(3, trel.getErrorCode());
			ps.setString(4, trel.getErrorSeason());
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			logger.error("设备注册错误日志录入异常 e＝ {}",e);
		} finally{
			ConnectionManager.closeConnection(null, ps, conn, null);
		}
		return false;
	}

	@Override
	public boolean checkExits(String licensePlate, String terminalId) {
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "select * from terminal_reg_error_log where licensePlate = ? and terminalId = ?";
		ResultSet rs = null;
		try {
			conn = ConnectionManager.getAppDBConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, licensePlate);
			ps.setString(2, terminalId);
			rs = ps.executeQuery();
			if(rs.next()){
				return true;
			}
		} catch (SQLException e) {
			logger.error("设备检测异常 e＝ {}",e);
		} finally{
			ConnectionManager.closeConnection(null, ps, conn, rs);
		}
		return false;
	}

}
