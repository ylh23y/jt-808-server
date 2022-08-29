package com.yun.jt808.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
* @Description: 连接管理
* @author James
* @date 2021年4月11日 上午11:34:35
 */
public final class ConnectionManager {

	/**
	 * 日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionManager.class.getName());

	/**
	 * 应用数据源
	 */
	private static DataSource appdbs;

	/**
	 * 
	 * @return 从应用数据源中获得一个连接.
	 * @throws SQLException
	 */
	public static Connection getAppDBConnection() throws SQLException {
		Connection conn = getAppdbs().getConnection();
		if (conn == null) {
			throw new SQLException("Not found connection");
		}
		return conn;

	}


	/**
	 * 将连接释放到连接池中.
	 * @param stmt
	 * @param connection
	 * @param rs
	 */
	public static void closeConnection(Statement stmt,PreparedStatement ps,  Connection connection,
			ResultSet rs) {
		try {
			if (rs != null&&!rs.isClosed()) {
				rs.close();
			}
			if (stmt != null&&!stmt.isClosed()) {
				
				stmt.close();				
				
				//LOGGER.debug("close stmt end:"+stmt);
				
			}
			if(ps != null && !ps.isClosed()){
				ps.close();
			}
			if (connection != null&&!connection.isClosed()) {
				connection.close();
				//LOGGER.debug("close connection");
			}

		} catch (SQLException e) {
			LOGGER.error("Close PreparedStatement or Connection failed.", e);
			
		}

	}

	public static DataSource getAppdbs() {
		return appdbs;
	}

	public static void setAppdbs(DataSource appdbs) {
		ConnectionManager.appdbs = appdbs;
	}

}