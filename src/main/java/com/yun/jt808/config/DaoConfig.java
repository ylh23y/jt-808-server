package com.yun.jt808.config;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jolbox.bonecp.BoneCPConfig;
import com.jolbox.bonecp.BoneCPDataSource;

import com.yun.jt808.netty.server.TcpServer;
import com.yun.jt808.utils.ConnectionManager;
/**
* @Description: TODO 
* @author James
* @date 2021年10月15日 上午11:59:54
 */
public final class DaoConfig {
	/**
	 * @return
	 */
	private static final Logger logger = LoggerFactory.getLogger(DaoConfig.class);
	/**
	 * @return
	 */
	private static final String DB_PATH = "zpytsf_appdb.xml";
	/**
	 * @return
	 */
	public DaoConfig(){
		
	}
	
	/**
	 * @return
	 */
	public static boolean init(){
		if(logger.isInfoEnabled()){
			
			logger.info("DaoConfig init begin ...");
		}
		//注册驱动
		registerDriver();
		
		boolean isSuccess = true;
		try {
			isSuccess &= registerAppSource(DB_PATH);
			if(!isSuccess){
				return false;
			}
			testAppDBConnection();
		} catch (Exception e) {
			logger.error("init db error-->",e);
			return false;
		}
		
		if(logger.isInfoEnabled()){
			
			logger.info("DaoConfig init end ...");
		}
		return true;
	}
	
	/**
	 * 注册驱动
	 * @return
	 */
	private static boolean registerDriver(){
		Enumeration<Driver> drivers = DriverManager.getDrivers();
		while(drivers.hasMoreElements())
		{
			Driver nextElement = drivers.nextElement();
			if(logger.isInfoEnabled()){
				logger.info("已经注册的驱动：{}",nextElement.toString());
			}
		}
		return true;
	}
	
	/**
	 * 注册应用数据源
	 * @param fileName
	 * @return
	 */
	private static boolean registerAppSource(String fileName){
		try {
			InputStream in = TcpServer.class.getClassLoader().getResourceAsStream(fileName);
			BoneCPConfig cp = new BoneCPConfig(in,"default-config");
			cp.setDetectUnclosedStatements(true);
			//设置到DaoManger类 TODO
			ConnectionManager.setAppdbs(new BoneCPDataSource(cp));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static void testAppDBConnection(){
		Connection connection = null;
		try {
			connection = ConnectionManager.getAppDBConnection();
			if (!connection.isClosed()) {
				connection.close();
			}
			if (logger.isInfoEnabled()) {
				logger.info("testAppDBConnection success!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("testAppDBConnection success!but close connection error!",e);
		}
	}
	
	public static void uninit(){
		unregisterProxool();
	}

	private static void unregisterProxool() {
		if(logger.isInfoEnabled())
		{
			logger.info("unregisterProxool ......start clear resource ......");
		}
		
		Enumeration<Driver> drivers = DriverManager.getDrivers();
		while(drivers.hasMoreElements())
		{
			Driver nextElement = drivers.nextElement();
			try{
				DriverManager.deregisterDriver(nextElement);
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
	}
}
