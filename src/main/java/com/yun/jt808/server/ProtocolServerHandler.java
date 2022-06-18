package com.yun.jt808.server;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.yun.jt808.netty.codec.MsgDecoder;
import com.yun.jt808.netty.codec.MsgEncoder;
import com.yun.jt808.netty.server.AbstractServerDisplay;
import com.yun.jt808.netty.server.ServerHandler;
import com.yun.jt808.netty.session.SessionManager;
import com.yun.jt808.utils.HexStringUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;

/**
* @Description: 协议解析处理类
* @author James
* @date 2021年4月18日 下午1:06:42
 */
@Sharable
public class ProtocolServerHandler extends ChannelHandlerAdapter{
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * 编码器
	 */
	private MsgEncoder encoder;
	/**
	 * 解码器
	 */
	private MsgDecoder decoder;
	/**
	 * 业务集合
	 */
	private AbstractServerDisplay abstractServerDisplay;
	public List<String> channelList = new ArrayList<>();
	
	private static ProtocolServerHandler instance;
	
	public static ProtocolServerHandler getInstance() {
		return instance;
	}

	public ProtocolServerHandler(MsgEncoder encoder, MsgDecoder decoder, AbstractServerDisplay abstractServerDisplay){
		this.encoder = encoder;
		this.decoder = decoder;
		this.abstractServerDisplay = abstractServerDisplay;
		instance = this;
	}

	/*
	 *	channelRegistered, ChannelHandlerContext的Channel被注册到EventLoop；
		channelUnregistered, ChannelHandlerContext的Channel从EventLoop中注销；
		channelActive, ChannelHandlerContext的Channel已激活
		channelInactive, ChannelHanderContext的Channel结束生命周期
		channelRead, 从当前Channel的对端读取消息
		channelReadComplete, 消息读取完成后执行
		userEventTriggered, 一个用户事件被处罚
		channelWritabilityChanged, 改变通道的可写状态, 可以使用Channel.isWriteable()检查
		exceptionCaught, 重写父类ChannelHandler的方法，处理异常 
	 */
	/**
	 * 客户端激活链接
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		logger.info("channelActive 客户端 {} 连接上线",ctx.channel().id());
		channelList.add(ctx.channel().id().toString());
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		SessionManager.getObject().offline(ctx.channel());
		ctx.close();
		ctx.channel().close();
	}
	
	/**
	 * 通道注册
	 */
	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		logger.info("channelRegistered 通道注册 id = {}",ctx.channel().id());
	}

	/**
	 *  通道未注册
	 */
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		logger.info("channelUnregistered 通道未注册 id = {}",ctx.channel().id());
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		logger.info("handlerRemoved >>>> id = " , ctx.channel().id());
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		try {
			ByteBuf buf = (ByteBuf) msg;
			if (buf.readableBytes() <= 0) {
				return;
			}

			byte[] bs = new byte[buf.readableBytes()];
			buf.readBytes(bs);
			
			String hexRes = HexStringUtils.bytesToHexString(bs);
			
			//处理转义
			List<Byte> list = new ArrayList<Byte>();
			for(int i=0; i < bs.length; i++){
				if(i-1 > 0 && bs[i] == 0x01 && bs[i-1] == 0x7d){
					 continue;
				}
				list.add(bs[i]);
			}
			
			int len = list.size();
			byte[] okBytes = new byte[len];
			for(int i=0; i < len; i++){
				okBytes[i] = list.get(i);
			}
			
//			String afterHexRes = HexStringUtils.toHexString(okBytes);
//			System.out.println("原始数据：" + hexRes);
//			System.out.println("转义后：" + afterHexRes);
			// 字节数据转换为针对于808消息结构的实体类
			PackageData data = decoder.bytes2PackageData(okBytes);
			// 引用channel,以便回送数据给硬件
			data.setChannel(ctx.channel());
			
			//消息code
			Integer msgId = data.getMsgHeader().getMsgId();
//			if(msgId == TPMSConsts.msg_id_terminal_location_info_upload){
//				File file = new File("F:\\车载定位原始数据.txt");
//				FileWriter fw = new FileWriter(file, true);
//				
//				System.out.println("原始数据：" + hexRes);
//				
//				fw.write(String.format("7e%s7e", hexRes) + "\r\n");
//				fw.close();
//			}
			
			File file = new File("./车载定位原始数据.txt");
			FileWriter fw = new FileWriter(file, true);
			
			
			fw.write(String.format("7e%s7e", hexRes) + "\r\n");
			fw.close();
			
			logger.info("--------------import msg--------------[in processs msg id value = {}]", msgId);
			ServerHandler serverHandler = abstractServerDisplay.getHttpHandler(msgId);
			if(serverHandler != null)
			{
				serverHandler.handle(ctx, data);
			}else{
				logger.error("--------------import msg--------------[not found handle class msgid = {}]",msgId);
			}
		} finally {
			release(msg);
		}
	}
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		super.exceptionCaught(ctx, cause);
	}

	private void release(Object msg) {
		try {
			ReferenceCountUtil.release(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		
		logger.info("Idle state channel Id = {}",ctx.channel().id());
		logger.info("============userEventTriggered==============");
		logger.info("当前session数量：{}" + SessionManager.getObject().getChannelSessions().size());
		logger.info("============userEventTriggered==============");
		logger.info("当前的客户端数量：{}",SessionManager.currentChannelNums.getAndIncrement());
		/**
		 * 处理断电、断网的情况下的session问题
		 */
		if (IdleStateEvent.class.isAssignableFrom(evt.getClass())) {  
            IdleStateEvent event = (IdleStateEvent) evt;  
            if (event.state() == IdleState.READER_IDLE)  
            {
            	logger.info("****************** Read IDLE = " + ctx.channel().id() + " read idle");  
            	SessionManager.getObject().offline(ctx.channel());
                ctx.close();
                ctx.channel().close();
            }
            
            if (event.state() == IdleState.WRITER_IDLE)  
            {
            	logger.info("****************** write IDLE = " + ctx.channel().id() + " write idle");  
            }
            
            if (event.state() == IdleState.ALL_IDLE)  
            {
            	logger.info("****************** all IDLE = " + ctx.channel().id() + " all idle");  
            }
        }  
	}

	public MsgEncoder getEncoder() {
		return encoder;
	}

	public void setEncoder(MsgEncoder encoder) {
		this.encoder = encoder;
	}

	public MsgDecoder getDecoder() {
		return decoder;
	}

	public void setDecoder(MsgDecoder decoder) {
		this.decoder = decoder;
	}

	

}
