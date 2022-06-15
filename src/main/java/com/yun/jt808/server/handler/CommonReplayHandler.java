package com.yun.jt808.server.handler;

import org.apache.log4j.Logger;

import com.yun.jt808.netty.server.ServerHandler;
import com.yun.jt808.server.PackageData;
import io.netty.channel.ChannelHandlerContext;

/**
* @Description: 平台通用应答
* @author James
* @date 2021年10月8日 下午3:47:22
 */
public class CommonReplayHandler implements ServerHandler{

	private static final Logger logger = Logger.getLogger(CommonReplayHandler.class);
	
	@Override
	public void handle(ChannelHandlerContext ctx, PackageData data) {
		// TODO Auto-generated method stub
		logger.info("您好这里是平台通用应答处理处，别来了。。。");
		
	}

}
