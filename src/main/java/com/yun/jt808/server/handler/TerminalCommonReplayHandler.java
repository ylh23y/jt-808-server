package com.yun.jt808.server.handler;

import org.apache.log4j.Logger;

import com.yun.jt808.netty.server.ServerHandler;
import com.yun.jt808.server.PackageData;
import io.netty.channel.ChannelHandlerContext;

/**
* @Description: 终端通用应答
* @author James
* @date 2021年10月8日 下午4:00:41
 */
public class TerminalCommonReplayHandler implements ServerHandler {

	private static final Logger logger = Logger.getLogger(TerminalCommonReplayHandler.class);
	
	@Override
	public void handle(ChannelHandlerContext ctx, PackageData data) {
		// TODO Auto-generated method stub
		logger.info("这里是终端通用应答处理");
	}

}
