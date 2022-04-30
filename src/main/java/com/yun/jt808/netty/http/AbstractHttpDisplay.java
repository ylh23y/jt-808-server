package com.yun.jt808.netty.http;

import java.util.HashMap;
import java.util.Map;
/**
* @Description: Http注册入口
* @author James
* @date 2021年10月15日 下午12:44:00
 */
public abstract class AbstractHttpDisplay {
	
	private Map<Integer, AbstractHttpHandler> map = new HashMap<>();
	
	public AbstractHttpHandler getHttpHandler(int code){
		return map.get(code);
	}
	
	public void display(int code, AbstractHttpHandler handler){
		map.put(code, handler);
	}
	
	/**
	 * 注册方法
	 */
	public abstract void display();
}
