package com.yun.jt808.netty.server;

import com.yun.jt808.server.PackageData;
import io.netty.channel.ChannelHandlerContext;

/**
* @Description: 业务处理通用接口(观察者接口)
* @author James
* @date 2021年4月18日 下午1:09:01
 */
public interface ServerHandler {
	
	/**
	 * 处理方法
	 * @param ctx
	 * @param data
	 */
	public void handle(ChannelHandlerContext ctx, PackageData data);
}
