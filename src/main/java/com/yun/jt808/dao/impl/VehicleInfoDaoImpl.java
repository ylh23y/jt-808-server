package com.yun.jt808.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yun.jt808.dao.VehicleInfoDao;
import com.yun.jt808.po.VehicleInfo;
import com.yun.jt808.utils.ConnectionManager;

/**
* @Description: 车辆信息DAO接口实现类
* @author James
* @date 2021年5月15日 下午2:03:56
 */
public class VehicleInfoDaoImpl implements VehicleInfoDao {
	
	private static final Logger logger = LoggerFactory.getLogger(VehicleInfoDaoImpl.class);

	@Override
	public VehicleInfo queryVehicle(String licensePlate, String terminalPhone) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		VehicleInfo vInfo = null;
		try {
			conn = ConnectionManager.getAppDBConnection();
			String sql = "select vehicleVolume, ratedLoad from vehicle_info where licensePlate = ? and terminalPhone = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, licensePlate);
			ps.setString(2, terminalPhone);
			rs = ps.executeQuery();
			while (rs.next()) {
				vInfo = new VehicleInfo();
				vInfo.setLicensePlate(licensePlate);
				vInfo.setTerminalPhone(terminalPhone);
				vInfo.setRatedLoad(rs.getFloat("ratedLoad"));
				vInfo.setVehicleVolume(rs.getFloat("vehicleVolume"));
			}
			return vInfo;

		} catch (SQLException e) {
			logger.error("查询终端= {}", e);
		} finally {
			ConnectionManager.closeConnection(null,ps,conn,rs);
		}
		return null;
	}

}
