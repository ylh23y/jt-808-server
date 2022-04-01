package com.yun.jt808.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yun.jt808.dao.CarryingCapacityInfoDao;
import com.yun.jt808.po.CarryingCapacityInfo;
import com.yun.jt808.utils.ConnectionManager;

/**
* @Description: 载重数据表DAO接口的实现
* @author James
* @date 2021年4月27日 上午9:38:12
 */
public class CarryingCapacityInfoDaoImpl implements CarryingCapacityInfoDao{

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public boolean insert(CarryingCapacityInfo carryingCapacityInfo) {
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "insert into carrying_capacity_info("
				+ "licensePlate,"
				+ "terminalId,"
				+ "startTime,"
				+ "endTime,"
				+ "startLng,"
				
				+ "startLat,"
				+ "endLng,"
				+ "endLat,"
				+ "startWeight,"
				+ "endWeight,"
				
				+ "weight,"
				+ "durationTime,"
				+ "terminalPhone,volume,"
				+ "loadAreaId,loadAreaName,"
				+ "unLoadAreaId,unLoadAreaName) "
				
				+ "values("
				+ "?,?,?,?,?,"
				+ "?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,?"
				+ ")";
		try {
			conn = ConnectionManager.getAppDBConnection();
			ps = conn.prepareStatement(sql);
			
			ps.setString(1, carryingCapacityInfo.getLicensePlate());
			ps.setString(2, carryingCapacityInfo.getTerminalId());
			Date startTime = carryingCapacityInfo.getStartTime();
			Date endTime = carryingCapacityInfo.getEndTime();
			if(startTime == null || endTime == null)
			{
				long currentTime = System.currentTimeMillis();
				startTime = new Date(currentTime);
				endTime = new Date(currentTime);
			}
			ps.setTimestamp(3, new Timestamp(startTime.getTime()));
			ps.setTimestamp(4, new Timestamp(endTime.getTime()));
			ps.setBigDecimal(5, carryingCapacityInfo.getStartLng());
			
			ps.setBigDecimal(6, carryingCapacityInfo.getStartLat());
			ps.setBigDecimal(7, carryingCapacityInfo.getEndLng());
			ps.setBigDecimal(8, carryingCapacityInfo.getEndLat());
			ps.setFloat(9, carryingCapacityInfo.getStartWeight());
			ps.setFloat(10, carryingCapacityInfo.getEndWeight());
			
			ps.setFloat(11, carryingCapacityInfo.getWeight());
			ps.setInt(12, carryingCapacityInfo.getDurationTime());
			ps.setString(13, carryingCapacityInfo.getTerminalPhone());
			ps.setFloat(14, carryingCapacityInfo.getVolume());
			
			ps.setObject(15, carryingCapacityInfo.getLoadAreaId());
			ps.setString(16, carryingCapacityInfo.getLoadAreaName());
			
			ps.setObject(17, carryingCapacityInfo.getUnLoadAreaId());
			ps.setString(18, carryingCapacityInfo.getUnLoadAreaName());
			
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			logger.error("载重数据录入异常 e = {}" , e);
		}finally{
			ConnectionManager.closeConnection(null, ps, conn, null);
		}
		return false;
	}

}
