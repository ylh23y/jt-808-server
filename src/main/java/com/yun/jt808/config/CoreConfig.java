package com.yun.jt808.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
* @Description: 配置信息文件加载
* @author James
* @date 2021年4月25日 下午5:14:33
 */
public class CoreConfig {
	
	private static Properties p = new Properties();
	private static final Logger logger = LoggerFactory.getLogger(CoreConfig.class);
	private static String FILE_PATH = "/core-config.properties";
	
	public CoreConfig(){}
	
	public static void load(){
		logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>> load core-config.properties <<<<<<<<<<<<<<<<<<<<<<<");
		InputStream in = CoreConfig.class.getResourceAsStream(FILE_PATH);
		try {
			p.load(in);
			logger.info(">>>>>>>>>>> load success <<<<<<<<<<<<<<");
		} catch (IOException e) {
			logger.info(">>>>>>>>>> 加载文件 = {} ",e);
		}
	}
	
	/**
	 * 获取属性值
	 * @param key 键
	 * @return value 值
	 */
	public static String getProperty(String key){
		return p.getProperty(key);
	}

	public static int getIntValue(String key) {
		String value = p.getProperty(key);
		
		if(StringUtils.isNotBlank(value)){
			try{
				return Integer.parseInt(value);
			}catch(NumberFormatException e){logger.error("数字转换异常", e);}
		}
		return 0;
	}
}
