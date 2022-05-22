package com.yun.jt808.netty.server;

import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.yun.jt808.common.ServerConsts;
import com.yun.jt808.netty.codec.MsgDecoder;
import com.yun.jt808.netty.codec.MsgEncoder;
import com.yun.jt808.server.ProtocolServerHandler;
import com.yun.jt808.server.ServerDisplayHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.Future;

/**
* @Description: 程序入口监听类
* @author James
* @date 2021年7月10日 上午10:48:05
 */
public class TcpServer {

	/**
	 * 创建日志打印类
	 */
	private static final Logger log = LoggerFactory.getLogger(TcpServer.class);
	/**
	 * 启动运行标记
	 */
	private volatile boolean isRunning = false;

	/**
	 * 事件监听轮询组
	 */
	private EventLoopGroup bossGroup = null;
	private EventLoopGroup workerGroup = null;
	private AbstractServerDisplay abstractServerDisplay;
	/**
	 * 应用启动端口
	 */
	private int port;
	
	public TcpServer(AbstractServerDisplay abstractServerDisplay, int port) {
		this.port = port;
		this.abstractServerDisplay = abstractServerDisplay;
	}

	/**
	 * 绑定方法
	 * @throws Exception
	 */
	private void bind() throws Exception {
		abstractServerDisplay.display();
		this.bossGroup = new NioEventLoopGroup();
		this.workerGroup = new NioEventLoopGroup();
		ServerBootstrap serverBootstrap = new ServerBootstrap();
		
		serverBootstrap.group(bossGroup, workerGroup)
				.channel(NioServerSocketChannel.class) 
				.childHandler(new ChannelInitializer<SocketChannel>() { 
					@Override
					public void initChannel(SocketChannel ch) throws Exception {
						/**
						 * 处理空闲状态的Handler，检测包括了读空闲、写空闲、All空闲这三种状态
						 */
						ch.pipeline().addLast("idleStateHandler",
								new IdleStateHandler(ServerConsts.TCP_CLIENT_IDLE_MINUTES, 0, 0, TimeUnit.MINUTES));
//						ch.pipeline().addLast(new Decoder4LoggingOnly());
						// 1024表示单条消息的最大长度，解码器在查找分隔符的时候，达到该长度还没找到的话会抛异常
						//TEST 分割符
//						ByteBuf _3 = Unpooled.copiedBuffer("##".getBytes());
//						ByteBuf _4 = Unpooled.copiedBuffer("$$".getBytes());
//						ch.pipeline().addLast(
//								new DelimiterBasedFrameDecoder(10,_3));
//						ch.pipeline().addLast(new StringDecoder());
						ch.pipeline().addLast(
								new DelimiterBasedFrameDecoder(1024, Unpooled.copiedBuffer(new byte[] { 0x7e })));
						//new byte[] { 0x7e, 0x7e }
//						ch.pipeline().addLast(new PackageDataDecoder());
						ProtocolServerHandler ps = new ProtocolServerHandler(new MsgEncoder(), new MsgDecoder(), abstractServerDisplay);
						ch.pipeline().addLast(ps);
					}
				}).option(ChannelOption.SO_BACKLOG, 1080) 
				.childOption(ChannelOption.SO_KEEPALIVE, true);
		
		ChannelFuture channelFuture = serverBootstrap.bind(port).sync();

		channelFuture.channel().closeFuture().sync();
	}

	/**
	 * 启动服务方法
	 */
	public synchronized void startServer() {
		if (this.isRunning) {
			throw new IllegalStateException(this.getName() + " is already started .");
		}
		this.isRunning = true;

		new Thread(() -> {
			try {
				this.bind();
			} catch (Exception e) {
				log.info("TCP服务启动出错:{}", e.getMessage());
				e.printStackTrace();
			}
		}, this.getName()).start();
	}

	/**
	 * 停止服务
	 */
	public synchronized void stopServer() {
		if (!this.isRunning) {
			throw new IllegalStateException(this.getName() + " is not yet started .");
		}
		this.isRunning = false;

		try {
			Future<?> future = this.workerGroup.shutdownGracefully().await();
			if (!future.isSuccess()) {
				log.error("workerGroup 无法正常停止:{}", future.cause());
			}

			future = this.bossGroup.shutdownGracefully().await();
			if (!future.isSuccess()) {
				log.error("bossGroup 无法正常停止:{}", future.cause());
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		log.info("TCP服务已经停止...");
	}

	private String getName() {
		return "TCP-Server";
	}
}