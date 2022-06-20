package com.yun.jt808.service;

import com.yun.jt808.service.impl.AreaInfoServiceImpl;
import com.yun.jt808.service.impl.LocationInfoServiceImpl;

/**
* @Description: 业务实现对象管理工厂
* @author James
* @date 2021年5月2日 上午10:47:14
 */
public class ServiceFactory {
	
	public static final ServiceFactory SERVICEFACTORY = new ServiceFactory();
	
	private final AreaInfoService areaInfoService = new AreaInfoServiceImpl();
	private final LocationInfoServiceImpl locationInfoServiceImpl = new LocationInfoServiceImpl();

	public static ServiceFactory getServicefactory() {
		return SERVICEFACTORY;
	}

	public AreaInfoService getAreaInfoService() {
		return areaInfoService;
	}

	public LocationInfoServiceImpl getLocationInfoServiceImpl() {
		return locationInfoServiceImpl;
	}
	
	
}
