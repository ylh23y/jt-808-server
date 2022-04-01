package com.yun.jt808.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yun.jt808.dao.AlarmDetailInfoDao;
import com.yun.jt808.po.AlarmDetailInfo;
import com.yun.jt808.utils.ConnectionManager;

/**
 * 
 * @ClassName: AlarmDetailInfoDaoImpl
 * @Description: 警报记录
 * @author Gary
 * @date 2021年4月21日 下午5:04:40
 */
public class AlarmDetailInfoDaoImpl implements AlarmDetailInfoDao {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public boolean insert(AlarmDetailInfo alarmDetailInfo) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = ConnectionManager.getAppDBConnection();
			String sql = "insert into alarm_detail_info("
					+ "licensePlate,"
					+ "terminalId,"
					+ "terminalPhone,"
					+ "startTime,"
					+ "endTime,"
					+ "info,"
					
					+ "description,"
					+ "alarmType,"
					+ "startLag," 
					+ "startLat,"
					+ "endLag,"
					
					+ "endLat,"
					+ "startSpeed,"
					+ "endSpeed,"
					+ "startMileage,"
					+ "endMileage,"
					
					+ "userId,"
					+ "userName,"
					+ "handleContent) "
					+ "values(?,?,"
					+ "?,?,?,?,?,"
					+ "?,?,?,?,?,"
					+ "?,?,?,?,?,"
					+ "?,?)";
			
			ps = conn.prepareStatement(sql);
			ps.setString(1, alarmDetailInfo.getLicensePlate());
			ps.setString(2, alarmDetailInfo.getTerminalId());
			ps.setString(3, alarmDetailInfo.getTerminalPhone());
			ps.setTimestamp(4, new Timestamp(alarmDetailInfo.getStartTime().getTime()));
			ps.setTimestamp(5, new Timestamp(alarmDetailInfo.getEndTime().getTime()));

			ps.setString(6, alarmDetailInfo.getInfo());
			ps.setString(7, alarmDetailInfo.getDescription());
			ps.setInt(8, alarmDetailInfo.getAlarmType());
			ps.setBigDecimal(9, alarmDetailInfo.getStartLag());

			ps.setBigDecimal(10, alarmDetailInfo.getStartLat());
			ps.setBigDecimal(11, alarmDetailInfo.getEndLag());
			ps.setBigDecimal(12, alarmDetailInfo.getEndLat());
			ps.setFloat(13, alarmDetailInfo.getStartSpeed());

			ps.setFloat(14, alarmDetailInfo.getEndSpeed());
			ps.setFloat(15, alarmDetailInfo.getStartMileage());
			ps.setFloat(16, alarmDetailInfo.getEndMileage());
			ps.setString(17, alarmDetailInfo.getUserId());

			ps.setString(18, alarmDetailInfo.getUserName());
			ps.setString(19, alarmDetailInfo.getHandleContent());
			
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			logger.error("警报记录信息上报异常 e= {} , id = {}", e , alarmDetailInfo.getId());
		} finally {
			ConnectionManager.closeConnection(null, ps, conn, null);
		}
		return false;
	}

}
