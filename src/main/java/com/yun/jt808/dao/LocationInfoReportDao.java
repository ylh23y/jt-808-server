package com.yun.jt808.dao;

import com.yun.jt808.po.LocationInfoReport;

/**
* @Description: 位置信息上报DAO接口定义
* @author James
* @date 2021年4月18日 下午3:38:09
 */
public interface LocationInfoReportDao {

	/**
	 * 保存位置上班信息
	 * @param infoReport
	 * @return
	 */
	public boolean insert(LocationInfoReport infoReport);
	
}
