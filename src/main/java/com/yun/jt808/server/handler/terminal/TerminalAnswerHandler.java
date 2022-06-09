package com.yun.jt808.server.handler.terminal;

import com.yun.jt808.netty.server.ServerHandler;
import com.yun.jt808.server.PackageData;
import io.netty.channel.ChannelHandlerContext;

/**
* @Description: 终端应答处理
* @author James
* @date 2021年4月18日 下午2:08:26
 */
public class TerminalAnswerHandler implements ServerHandler {

	@Override
	public void handle(ChannelHandlerContext ctx, PackageData data) {
		System.out.println("终端应答处理");
	}

}
