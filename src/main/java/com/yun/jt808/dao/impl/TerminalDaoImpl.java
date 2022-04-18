package com.yun.jt808.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yun.jt808.common.enums.DeviceRegState;
import com.yun.jt808.dao.TerminalDao;
import com.yun.jt808.utils.ConnectionManager;
import com.yun.jt808.vo.TerminalRegisterMsg.TerminalRegInfo;

/**
* @Description: 终端信息管理DAO实现类
* @author James
* @date 2021年4月19日 上午11:24:42
 */
public class TerminalDaoImpl implements TerminalDao {

	private static final Logger logger = LoggerFactory.getLogger(TerminalDaoImpl.class);
	
	@Override
	public boolean insert(TerminalRegInfo terminalRegInfo) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = ConnectionManager.getAppDBConnection();
			String sql = "insert into terminal_reg_info(terminalPhone, provinceId, cityId, manufacturerId, terminalType, terminalId, licensePlateColor, licensePlate) values(?,?,?,?,?,?,?,?)";
//			
//			String sql = "insert into terminal_reg_info(provinceId,cityId,"
//					+ "manufacturerId,terminalType,"
//					+ "terminalId,licensePlateColor,"
//					+ "licensePlate,terminalPhoneNumber) "
//					+ "values(?,?,?,?,?,?,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, terminalRegInfo.getTerminalPhone());
			ps.setInt(2, terminalRegInfo.getProvinceId());
			ps.setInt(3, terminalRegInfo.getCityId());
			ps.setString(4, terminalRegInfo.getManufacturerId());
			ps.setString(5, terminalRegInfo.getTerminalType());
			ps.setString(6, terminalRegInfo.getTerminalId());
			ps.setInt(7, terminalRegInfo.getLicensePlateColor());
			ps.setString(8, terminalRegInfo.getLicensePlate());
			
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			logger.error("增加终端注册信息异常 e = {}", e);
		} finally {
			ConnectionManager.closeConnection(null,ps,conn,null);
		}
		return false;
	}

	@Override
	public TerminalRegInfo queryTerminal(String terminalId, String licensePlate) {
		Connection conn = null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			TerminalRegInfo trInfo=null;
			conn=ConnectionManager.getAppDBConnection();
			String sql="select DISTINCT  * from terminal_reg_info t where t.terminalId=?  and  licensePlate=?";
			ps=conn.prepareStatement(sql);
			ps.setString(1, terminalId);
			ps.setString(2, licensePlate);
			rs=ps.executeQuery();
			while (rs.next()) {
				trInfo=new TerminalRegInfo();
				trInfo.setCityId(rs.getInt("cityId"));
				trInfo.setLicensePlate(rs.getString("licensePlate"));
				trInfo.setLicensePlateColor(rs.getInt("licensePlateColor"));
				trInfo.setManufacturerId(rs.getString("manufacturerId"));
				trInfo.setProvinceId(rs.getInt("provinceId"));
				trInfo.setTerminalId(rs.getString("terminalId"));
				trInfo.setTerminalType(rs.getString("terminalType"));
			}
		    return trInfo;
		} catch (SQLException e) {
			logger.error("查询终端 ");
		} finally {
			ConnectionManager.closeConnection(null,ps,conn,rs);
		}
		return null;
	}

	@Override
	public TerminalRegInfo queryTerminalBySimPhone(String simPhone) {
		Connection conn = null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			TerminalRegInfo tri=null;
			conn=ConnectionManager.getAppDBConnection();
			String sql="select * from terminal_reg_info where terminalPhoneNumber = ?";
			ps=conn.prepareStatement(sql);
			ps.setString(1, simPhone);
			rs=ps.executeQuery();
			while (rs.next()) {
				tri=new TerminalRegInfo();
				tri.setCityId(rs.getInt("cityId"));
				tri.setLicensePlate(rs.getString("licensePlate"));
				tri.setLicensePlateColor(rs.getInt("licensePlateColor"));
				tri.setManufacturerId(rs.getString("manufacturerId"));
				tri.setProvinceId(rs.getInt("provinceId"));
				tri.setTerminalId(rs.getString("terminalId"));
				tri.setTerminalType(rs.getString("terminalType"));
			}
		    return tri;
		} catch (SQLException e) {
			logger.error("根据Sim卡信息查询终端设备信息异常 e = {}" , e);
		}finally {
			ConnectionManager.closeConnection(null,ps,conn,rs);
		}
		return null;
	}

	@Override
	public TerminalRegInfo queryTerminalRegInfo(String terminalPhone) {
		//定义链接对象
		Connection conn = null;
		//获取查询对象
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			TerminalRegInfo tri=null;
			conn=ConnectionManager.getAppDBConnection();
			String sql="select * from terminal_reg_info where terminalPhone = ?";
			ps=conn.prepareStatement(sql);
			ps.setString(1, terminalPhone);
			rs=ps.executeQuery();
			while (rs.next()) {
				tri=new TerminalRegInfo();
				tri.setTerminalPhone(rs.getString("terminalPhone"));
				tri.setCityId(rs.getInt("cityId"));
				tri.setLicensePlate(rs.getString("licensePlate"));
				tri.setLicensePlateColor(rs.getInt("licensePlateColor"));
				tri.setManufacturerId(rs.getString("manufacturerId"));
				tri.setProvinceId(rs.getInt("provinceId"));
				tri.setTerminalId(rs.getString("terminalId"));
				tri.setTerminalType(rs.getString("terminalType"));
				tri.setState(DeviceRegState.getIndex(rs.getInt("state")));
			}
		    return tri;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			ConnectionManager.closeConnection(null,ps,conn, rs);
		}
		return null;
	}

	@Override
	public boolean update(TerminalRegInfo terminalRegInfo) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = ConnectionManager.getAppDBConnection();
			String sql = "update terminal_reg_info set state = ? , updateTime = ? where terminalPhone = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1,terminalRegInfo.getState().getIndex());
			ps.setDate(2, new Date(System.currentTimeMillis()));
			ps.setString(3, terminalRegInfo.getTerminalPhone());
			
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			logger.error("更新终端注册信息异常 e = {}", e);
		} finally {
			ConnectionManager.closeConnection(null,ps,conn,null);
		}
		return false;
	}
}
