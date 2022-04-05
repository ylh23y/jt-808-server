package com.yun.jt808.dao;

import com.yun.jt808.dao.impl.AlarmDetailInfoDaoImpl;
import com.yun.jt808.dao.impl.AreaInfoDaoImpl;
import com.yun.jt808.dao.impl.CarTransportTimesDaoImpl;
import com.yun.jt808.dao.impl.CarryingCapacityInfoDaoImpl;
import com.yun.jt808.dao.impl.DeviceStatusInfoDaoImpl;
import com.yun.jt808.dao.impl.LocationInfoReportDaoImpl;
import com.yun.jt808.dao.impl.TerminalDaoImpl;
import com.yun.jt808.dao.impl.TerminalRegErrorLogDaoImpl;
import com.yun.jt808.dao.impl.VehicleInfoDaoImpl;

/**
 * @Description: DAO实例工厂类
 * @author James
 * @date 2021年4月18日 下午4:12:32
 */
public class DaoFactory {

	private static final DaoFactory DAO_ = new DaoFactory();
	private final LocationInfoReportDao locationInfoReportDao = new LocationInfoReportDaoImpl();
	private final TerminalDao terminalDao = new TerminalDaoImpl();
	private final VehicleInfoDao vehicleInfoDao = new VehicleInfoDaoImpl();
	private final DeviceStatusInfoDao deviceStatusInfoDao = new DeviceStatusInfoDaoImpl();
	private final AlarmDetailInfoDao alarmDetailInfoDao = new AlarmDetailInfoDaoImpl();

	private final TerminalRegErrorLogDao terminalRegErrorLogDao = new TerminalRegErrorLogDaoImpl();
	private final CarryingCapacityInfoDao carryingCapacityInfoDao = new CarryingCapacityInfoDaoImpl();
	private final CarTransportTimesDao carTransportTimesDao = new CarTransportTimesDaoImpl();
	
	public CarTransportTimesDao getCarTransportTimesDao() {
		return carTransportTimesDao;
	}

	private final AreaInfoDao areaInfoDao = new AreaInfoDaoImpl();

	public static DaoFactory getDao() {
		return DAO_;
	}
	
	
	public AreaInfoDao getAreaInfoDao() {
		return areaInfoDao;
	}

	public CarryingCapacityInfoDao getCarryingCapacityInfoDao() {
		return carryingCapacityInfoDao;
	}

	public LocationInfoReportDao getLocationInfoReportDao() {
		return locationInfoReportDao;
	}

	public TerminalDao getTerminalDao() {
		return terminalDao;
	}

	public VehicleInfoDao getVehicleInfoDao() {
		return vehicleInfoDao;
	}

	public DeviceStatusInfoDao getDeviceStatusInfoDao() {
		return deviceStatusInfoDao;
	}

	public AlarmDetailInfoDao getAlarmDetailInfoDao() {
		return alarmDetailInfoDao;
	}

	public TerminalRegErrorLogDao getTerminalRegErrorLogDao() {
		return terminalRegErrorLogDao;
	}

}
