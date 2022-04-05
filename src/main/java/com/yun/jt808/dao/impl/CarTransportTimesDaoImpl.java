package com.yun.jt808.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

import com.yun.jt808.dao.CarTransportTimesDao;
import com.yun.jt808.po.CarTransportTimes;
import com.yun.jt808.utils.ConnectionManager;
/**
 * 
* @Description: TODO 
* @author James
* @date 2021年10月15日 下午12:35:40
 */
public class CarTransportTimesDaoImpl implements CarTransportTimesDao {

	@Override
	public boolean insert(CarTransportTimes ctt) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = ConnectionManager.getAppDBConnection();
			String sql = "insert into car_transport_times("
					+ "licensePlate,"
					+ "terminalPhone,"
					+ "startTime,"
					+ "startAreaId,"
					+ "startPoint,"
					
					+ "endTime,"
					+ "endAreaId,"
					+ "endPoint," 
					+ "createTime"
					+ ") "
					+ "values(?,?,?,?,?,?,?,?)";
			
			ps = conn.prepareStatement(sql);
			ps.setString(1, ctt.getLicensePlate());
			ps.setString(2, ctt.getTerminalPhone());
			ps.setTimestamp(3, new Timestamp(ctt.getStartTime().getTime()));
			ps.setInt(4, ctt.getStartAreaId());

			ps.setString(5, ctt.getStartPoint());
			ps.setTimestamp(6, new Timestamp(ctt.getEndTime().getTime()));
			ps.setInt(7, ctt.getEndAreaId());
			ps.setString(8, ctt.getEndPoint());

			ps.setTimestamp(9, new Timestamp(ctt.getCreateTime().getTime()));
			
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			
		} finally {
			ConnectionManager.closeConnection(null, ps, conn, null);
		}
		return false;
	}

}
