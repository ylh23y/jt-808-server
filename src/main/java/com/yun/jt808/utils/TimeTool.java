package com.yun.jt808.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * 时间处理工具
* @Description: TODO 
* @author James
* @date 2021年10月15日 下午1:54:14
 */
public class TimeTool {

	/**
	 * 获取月第一天 00:00:00
	 * @param time
	 * @return
	 */
	public static Date getFirstDayOfMonth(Date time){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		calendar.add(Calendar.MONTH, 0);  
		calendar.set(Calendar.DAY_OF_MONTH, 1); 
		calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.get(Calendar.HOUR_OF_DAY);
		return calendar.getTime();
	}
	
	public static Date getLastDayOfMonth(Date time){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		calendar.add(Calendar.MONTH, 1);  
		calendar.set(Calendar.DAY_OF_MONTH, 0);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
		return calendar.getTime();
	}
	
	
}
