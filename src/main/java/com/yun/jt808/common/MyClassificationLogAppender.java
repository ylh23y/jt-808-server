package com.yun.jt808.common;

import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Priority;
/**
* @Description:    
* @author James
* @date 2021年10月15日 下午12:35:08
 */
public class MyClassificationLogAppender extends DailyRollingFileAppender {

	@Override
	public boolean isAsSevereAsThreshold(Priority priority) {
		return this.getThreshold().equals(priority);
	}
}
