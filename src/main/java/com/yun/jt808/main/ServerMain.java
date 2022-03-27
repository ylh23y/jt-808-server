package com.yun.jt808.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.yun.jt808.common.cache.EncacheUtil;
import com.yun.jt808.config.CoreConfig;
import com.yun.jt808.config.DaoConfig;
import com.yun.jt808.netty.http.HttpServer;
import com.yun.jt808.netty.server.TcpServer;
import com.yun.jt808.server.HttpDisplayHandlers;
import com.yun.jt808.server.ServerDisplayHandler;

/**
* @Description: 入口函数
* @author James
* @date 2021年4月17日 下午2:50:00
 */
public class ServerMain {
	
	/**
	 * 日志对象
	 */
	private static final Logger logger = LoggerFactory.getLogger(ServerMain.class);
	
	public static void main(String[] args) {
		logger.info("------------------start server begin----------");
		//加载配置文件
		CoreConfig.load();
		
		//启动tcp协议服务
		Integer serverPort = CoreConfig.getProperty("tcp_port") == null 
				? null : Integer.valueOf(CoreConfig.getProperty("tcp_port"));
		if(serverPort == null)
		{
			logger.error("-------TCP 服务监听端口不合法 port[{}]-------", serverPort);
			return;
		}
		TcpServer server = new TcpServer(new ServerDisplayHandler(),serverPort);
		//初始化配置
		DaoConfig.init();
		//初始化Encache
		EncacheUtil.init();
		//启动服务
		server.startServer();
		logger.info("TCP 启动成功 监听端口为 port={}", serverPort);
		//启动http协议服务
		logger.info("------HTTP Start Begin ------");
		HttpServer httpServer = new HttpServer();
		httpServer.setHttpDisplay(new HttpDisplayHandlers());
		httpServer.start(8777);
		logger.info("------HTTP Start End ------");
		logger.info("------------------start server end----------");
	}
}
