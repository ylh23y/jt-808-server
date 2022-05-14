package com.yun.jt808.netty.server;

import java.util.HashMap;
import java.util.Map;

/**
* @Description: Socket 请求注册抽象类
* @author James
* @date 2021年10月15日 下午12:45:15
 */
public abstract class AbstractServerDisplay {
	
	private Map<Integer, ServerHandler> map = new HashMap<>();

	public ServerHandler getHttpHandler(int code) {
		return map.get(code);
	}

	public void display(int code, ServerHandler handler) {
		map.put(code, handler);
	}

	/**
	 * 注册入口
	 */
	public abstract void display();
}
