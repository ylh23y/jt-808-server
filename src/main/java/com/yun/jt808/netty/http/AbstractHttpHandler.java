package com.yun.jt808.netty.http;

import com.yun.jt808.service.BaseMsgProcessService;
import com.yun.jt808.utils.BitOperator;
/**
* @Description: Http 通用接口 
* @author James
* @date 2021年10月15日 下午12:44:39
 */
public abstract class AbstractHttpHandler extends BaseMsgProcessService{
	
	protected Response defaultResponse = new Response();
	protected BitOperator bitOperator = new BitOperator();
	
	/**
	 * 处理方法
	 * @param request
	 * @return
	 */
	public abstract Response handle(String request);
}
