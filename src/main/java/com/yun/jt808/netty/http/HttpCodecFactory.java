package com.yun.jt808.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
* @Description: 初始化解码器 
* @author James
* @date 2021年10月15日 下午12:47:17
 */
public class HttpCodecFactory extends ChannelInitializer<SocketChannel>{
	
	private AbstractHttpDisplay abstractHttpDisplay;

	public HttpCodecFactory(AbstractHttpDisplay abstractHttpDisplay) {
		this.abstractHttpDisplay = abstractHttpDisplay;
	}

	@Override
    public void initChannel(SocketChannel ch) throws Exception {
	    ChannelPipeline p = ch.pipeline();
        p.addLast("codec", new HttpServerCodec());
        p.addLast("handler", new HttpServerHandler(abstractHttpDisplay));
        //p.addLast("handler", new HttpHandlert());
    }
}