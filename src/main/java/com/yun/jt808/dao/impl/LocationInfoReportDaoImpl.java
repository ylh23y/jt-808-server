package com.yun.jt808.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yun.jt808.dao.LocationInfoReportDao;
import com.yun.jt808.po.LocationInfoReport;
import com.yun.jt808.utils.ConnectionManager;
/**
* @Description: 位置信息上报DAO实现类 
* @author James
* @date 2021年4月18日 下午3:41:28
 */
public class LocationInfoReportDaoImpl implements LocationInfoReportDao {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public boolean insert(LocationInfoReport infoReport) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = ConnectionManager.getAppDBConnection();
			String sql = "insert into location_info_report("
					
					+ "licensePlate,"
					+ "terminalId,"
					+ "terminalPhone,"
					+ "latitude,"
					+ "longitude,"
					
					+ "altitude,"
					+ "speed,"
					+ "direction,"
					+ "locationTime,"
					+ "urgentAlarm,"
					
					+ "speeding,"
					+ "fatigueDriving,"
					+ "earlyWarning,"
					+ "GNSSFault,"
					+ "GNSSLine,"
					
					+ "GNSSShort,"
					+ "underVoltage,"
					+ "powerDown,"
					+ "LCDFault,"
					+ "TTSFault,"
					
					+ "cameraFault,"
					+ "overTimeDriver,"
					+ "overTimeStop,"
					+ "importArea,"
					+ "importRoute,"
					
					+ "routeDriverToLongOrNotEnough,"
					+ "roteDeviation,"
					+ "VSSFault,"
					+ "oilException,"
					+ "vehicleTheft,"
					
					+ "vehicleIllegalIgnition,"
					+ "vehicleIllegalDeviation,"
					+ "acc,"
					+ "locationState,"
					+ "operationState,"
					
					+ "isEncryption,"
					+ "isOilNormal,"
					+ "isCircuitNormal,"
					+ "isLockDoor,"
					+ "weight,"
					
					+ "projectionLatitude,"
					+ "projectionLongitude,"
					+ "ads,"
					+ "loadState) values("
					+ "?,?,?,?,?,"
					+ "?,?,?,?,?,"
					
					+ "?,?,?,?,?,"
					+ "?,?,?,?,?,"
					
					+ "?,?,?,?,?,"
					+ "?,?,?,?,?,"
					
					+ "?,?,?,?,?,"
					+ "?,?,?,?,?,"
					
					+ "?,?,?,?"
					+ ")";
			
			ps = conn.prepareStatement(sql);
			int index = 1;
			//设置参数
			ps.setString(index++, infoReport.getLicensePlate());
			ps.setString(index++, infoReport.getTerminalId());
			ps.setString(index++, infoReport.getTerminalPhone());
			ps.setBigDecimal(index++, infoReport.getLatitude());
			ps.setBigDecimal(index++, infoReport.getLongitude());
			
			ps.setInt(index++, infoReport.getAltitude());
			ps.setInt(index++, infoReport.getSpeed());
			ps.setInt(index++, infoReport.getDirection());
			ps.setTimestamp(index++, new Timestamp(infoReport.getLocationTime().getTime()));
			ps.setByte(index++, infoReport.getUrgentAlarm());
			
			ps.setByte(index++, infoReport.getSpeeding());
			ps.setByte(index++, infoReport.getFatigueDriving());
			ps.setByte(index++, infoReport.getEarlyWarning());
			ps.setByte(index++, infoReport.getGNSSFault());
			ps.setByte(index++, infoReport.getGNSSLine());
			
			ps.setByte(index++, infoReport.getGNSSShort());
			ps.setByte(index++, infoReport.getUnderVoltage());
			ps.setByte(index++, infoReport.getPowerDown());
			ps.setByte(index++, infoReport.getLCDFault());
			ps.setByte(index++, infoReport.getTTSFault());
			
			ps.setByte(index++, infoReport.getCameraFault());
			ps.setByte(index++, infoReport.getOverTimeDriver());
			ps.setByte(index++, infoReport.getOverTimeStop());
			ps.setByte(index++, infoReport.getImportArea());
			ps.setByte(index++, infoReport.getImportRoute());
			
			ps.setByte(index++, infoReport.getRouteDriverToLongOrNotEnough());
			ps.setByte(index++, infoReport.getRoteDeviation());
			ps.setByte(index++, infoReport.getVSSFault());
			ps.setByte(index++, infoReport.getOilException());
			ps.setByte(index++, infoReport.getVehicleTheft());
			
			ps.setByte(index++, infoReport.getVehicleIllegalIgnition());
			ps.setByte(index++, infoReport.getVehicleIllegalDeviation());
			ps.setByte(index++, infoReport.getAcc());
			ps.setByte(index++, infoReport.getLocationState());
			ps.setByte(index++, infoReport.getOperationState());
			
			ps.setByte(index++, infoReport.getIsEncryption());
			ps.setByte(index++, infoReport.getIsOilNormal());
			ps.setByte(index++, infoReport.getIsCircuitNormal());
			ps.setByte(index++, infoReport.getIsLockDoor());
			ps.setFloat(index++, infoReport.getWeight());
			
			ps.setBigDecimal(index++, infoReport.getProjectionLatitude());
			ps.setBigDecimal(index++, infoReport.getProjectionLongitude());
			ps.setString(index++, infoReport.getAds());
			ps.setString(index++, infoReport.vehicleLoadState.getName());
			
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			logger.error("保存位置信息上报异常",e);
		}finally {
			ConnectionManager.closeConnection(null,ps,conn,null);
		}
		return false;
	}

}
