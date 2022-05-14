package com.yun.jt808.netty.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.DefaultThreadFactory;

/**
* @Description: TODO 
* @author James
* @date 2021年10月15日 下午12:06:06
 */
public class HttpServer{
	private AbstractHttpDisplay abstractHttpDisplay;
	private EventLoopGroup bossGroup;
	private EventLoopGroup workerGroup;
	
    public void setHttpDisplay(AbstractHttpDisplay abstractHttpDisplay) {
		this.abstractHttpDisplay = abstractHttpDisplay;
	}

	public void start(int port){
		abstractHttpDisplay.display();
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup(0,new DefaultThreadFactory("httpGroup", Thread.MAX_PRIORITY));
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.option(ChannelOption.SO_BACKLOG, 1024);
            b.group(bossGroup, workerGroup)
             .channel(NioServerSocketChannel.class)
             .childHandler(new HttpCodecFactory( abstractHttpDisplay ));

            b.bind(port).sync();
        }catch(Exception e){
        	System.out.println("检测到异常进行强制关闭");
        	e.printStackTrace();
        }
    }
	
	public void shutdown() {
		bossGroup.shutdownGracefully();
		workerGroup.shutdownGracefully();
	}
}