package com.yun.jt808.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yun.jt808.common.enums.DeviceState;
import com.yun.jt808.dao.DeviceStatusInfoDao;
import com.yun.jt808.netty.session.Session;
import com.yun.jt808.po.DeviceStatusInfo;
import com.yun.jt808.utils.ConnectionManager;

/**
 * @Description: 设备状态信息DAO接口实现类
 * @author James
 * @date 2021年4月21日 下午1:28:07
 */
public class DeviceStatusInfoDaoImpl implements DeviceStatusInfoDao {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public boolean insert(DeviceStatusInfo deviceStatusInfo) {
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "INSERT INTO device_status_info(licensePlate,terminalId,terminalPhone,lng,lat,factoryType,"
				+ "speed,online,gpsTime,protocolType,diskType," + "net,s1,s2,s3,direction,"
				+ "parkingDuration,mileage,oilMass,projectionLatitude,projectionLongitude,expandAttr) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		try {
			conn = ConnectionManager.getAppDBConnection();
			ps = conn.prepareStatement(sql);
			int index = 1;
			ps.setObject(index++, deviceStatusInfo.getLicensePlate());
			ps.setObject(index++, deviceStatusInfo.getTerminalId());
			ps.setObject(index++, deviceStatusInfo.getTerminalPhone());
			ps.setObject(index++, deviceStatusInfo.getLng());
			ps.setObject(index++, deviceStatusInfo.getLat());
			ps.setObject(index++, deviceStatusInfo.getFactoryType());
			ps.setObject(index++, deviceStatusInfo.getSpeed());
			ps.setObject(index++, deviceStatusInfo.getOnline());
			ps.setObject(index++, new Timestamp(deviceStatusInfo.getGpsTime().getTime()));
			ps.setObject(index++, deviceStatusInfo.getProtocolType());
			ps.setObject(index++, deviceStatusInfo.getDiskType());
			ps.setObject(index++, deviceStatusInfo.getNet());
			ps.setObject(index++, deviceStatusInfo.getS1());
			ps.setObject(index++, deviceStatusInfo.getS2());
			ps.setObject(index++, deviceStatusInfo.getS3());
			ps.setObject(index++, deviceStatusInfo.getDirection());
			ps.setObject(index++, deviceStatusInfo.getParkingDuration());
			ps.setObject(index++, deviceStatusInfo.getMileage());
			ps.setObject(index++, deviceStatusInfo.getOilMass());
			ps.setObject(index++, deviceStatusInfo.getProjectionLatitude());
			ps.setObject(index++, deviceStatusInfo.getProjectionLongitude());
			ps.setString(index++, deviceStatusInfo.getExpandAttr());
			
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			logger.error("录入设备状态信息插入异常 e =", e);
		} finally {
			ConnectionManager.closeConnection(null, ps, conn, null);
		}
		return false;
	}

	@Override
	public DeviceStatusInfo query(String terminalPhone) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ConnectionManager.getAppDBConnection();
			DeviceStatusInfo deviceStatusInfo = null;
			String sql = "select DISTINCT  *  from device_status_info where terminalPhone=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, terminalPhone);
			rs = ps.executeQuery();
			while (rs.next()) {
				deviceStatusInfo = new DeviceStatusInfo();
				deviceStatusInfo.setLicensePlate(rs.getString("licensePlate"));
				deviceStatusInfo.setTerminalId(rs.getString("terminalId"));
				deviceStatusInfo.setLng(rs.getBigDecimal("lng"));
				deviceStatusInfo.setLat(rs.getBigDecimal("lat"));
				deviceStatusInfo.setFactoryType(rs.getInt("factoryType"));

				deviceStatusInfo.setSpeed(rs.getFloat("speed"));
				deviceStatusInfo.setOnline(rs.getInt("online"));
				deviceStatusInfo.setGpsTime(rs.getDate("gpsTime"));
				deviceStatusInfo.setProtocolType(rs.getInt("protocolType"));
				deviceStatusInfo.setDiskType(rs.getInt("diskType"));

				deviceStatusInfo.setNet(rs.getInt("net"));
				deviceStatusInfo.setS1(rs.getInt("s1"));
				deviceStatusInfo.setS2(rs.getInt("s2"));
				deviceStatusInfo.setS3(rs.getInt("s3"));
				deviceStatusInfo.setDirection(rs.getInt("direction"));

				deviceStatusInfo.setParkingDuration(rs.getInt("parkingDuration"));
				deviceStatusInfo.setMileage(rs.getInt("mileage"));
				deviceStatusInfo.setOilMass(rs.getInt("oilMass"));
				deviceStatusInfo.setTerminalPhone(rs.getString("terminalPhone"));
			}
			return deviceStatusInfo;
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("录入设备状态查询信息异常 e =", e);
		} finally {
			ConnectionManager.closeConnection(null, ps, conn, null);
		}
		return null;
	}
	
	@Override
	public boolean update(DeviceStatusInfo deviceStatusInfo) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = ConnectionManager.getAppDBConnection();
			String sql = "update device_status_info set "
					+ "lng=?,lat=?,factoryType=?," 
					+ "speed=?,online=?,gpsTime=?,"
					+ "protocolType=?,diskType=?,net=?,"
					+ "s1=?,s2=?,s3=?,"
					+ "direction=?,parkingDuration=?,mileage=?," 
					+ "oilMass=?,projectionLatitude = ? ,projectionLongitude = ?, expandAttr = ? where terminalPhone=?";
			ps = conn.prepareStatement(sql);
			
			int index = 1;
			ps.setBigDecimal(index++, deviceStatusInfo.getLng());
			ps.setBigDecimal(index++, deviceStatusInfo.getLat());
			ps.setInt(index++, deviceStatusInfo.getFactoryType());
			System.out.println("车辆当前的速度：" + deviceStatusInfo.getSpeed());
			ps.setFloat(index++, deviceStatusInfo.getSpeed());
			ps.setInt(index++, deviceStatusInfo.getOnline());
			
			ps.setTimestamp(index++, new Timestamp(deviceStatusInfo.getGpsTime().getTime()));
			ps.setInt(index++, deviceStatusInfo.getProtocolType());
			ps.setInt(index++, deviceStatusInfo.getDiskType());
			ps.setInt(index++, deviceStatusInfo.getNet());
			ps.setInt(index++, deviceStatusInfo.getS1());
			
			ps.setInt(index++, deviceStatusInfo.getS2());
			ps.setInt(index++, deviceStatusInfo.getS3());
			ps.setInt(index++, deviceStatusInfo.getDirection());
			ps.setInt(index++, deviceStatusInfo.getParkingDuration());
			ps.setInt(index++, deviceStatusInfo.getMileage());
			
			ps.setInt(index++, deviceStatusInfo.getOilMass());
			ps.setBigDecimal(index++, deviceStatusInfo.getProjectionLatitude());
			ps.setBigDecimal(index++, deviceStatusInfo.getProjectionLongitude());
			ps.setString(index++, deviceStatusInfo.getExpandAttr());
			ps.setString(index++, deviceStatusInfo.getTerminalPhone());

			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			logger.error("录入设备状态信息更新异常", e);
		} finally {
			ConnectionManager.closeConnection(null, ps, conn, null);
		}
		return false;
	}

	/**
	 * 离线更新
	 */
	@Override
	public boolean updateOnlineState(Session session,DeviceState deviceState) {
		if(session == null){
			return false;
		}
		Connection conn = null;
		String sql = "update device_status_info set online = ? where licensePlate=? and terminalId=?";
		PreparedStatement ps = null;
		try {
			conn = ConnectionManager.getAppDBConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, deviceState.getIndex());
			ps.setString(2, session.getLicensePlate());
			ps.setString(3, session.getTerminalPhone());
			
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			logger.error("更新在线状态信息异常 ",e);
		}finally{
			ConnectionManager.closeConnection(null, ps, conn, null);
		}
		return false;
	}

}
