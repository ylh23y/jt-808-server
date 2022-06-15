package com.yun.jt808.server.handler;

import com.yun.jt808.netty.server.ServerHandler;
import com.yun.jt808.server.PackageData;
import io.netty.channel.ChannelHandlerContext;
/**
* @Description: 心跳处理
* @author James
* @date 2021年4月24日 下午4:58:54
 */
public class HeartBeatHandler implements ServerHandler {

	@Override
	public void handle(ChannelHandlerContext ctx, PackageData data) {
		System.out.println("》》》》》》》》》 心跳处理");
	}

}
