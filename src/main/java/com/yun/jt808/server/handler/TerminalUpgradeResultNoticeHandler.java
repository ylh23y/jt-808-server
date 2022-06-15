package com.yun.jt808.server.handler;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;

import com.yun.jt808.common.enums.TerminalUpgradeNoticeState;
import com.yun.jt808.netty.server.ServerHandler;
import com.yun.jt808.netty.session.Session;
import com.yun.jt808.netty.session.SessionManager;
import com.yun.jt808.server.PackageData;
import com.yun.jt808.server.handler.terminal.TerminalAuthenticationHandler;
import com.yun.jt808.service.BaseMsgProcessService;
import io.netty.channel.ChannelHandlerContext;
/**
 * 
* @Description: 终端升级结果通知 
* @author James
* @date 2021年10月9日 下午5:55:33
 */
public class TerminalUpgradeResultNoticeHandler extends BaseMsgProcessService implements ServerHandler{

	private static final Logger logger = Logger.getLogger(TerminalAuthenticationHandler.class);
	/**
	 *  消息ID：0x0108。
		终端在升级完成并重新连接后使用该命令通知监控中心。终端升级结果通知消息体数
		据格式见表22。
			表 22 终端升级结果通知消息体数据格式
			起始字节 字段 数据类型 描述及要求
			0 升级类型 BYTE 0：终端，12：道路运输证 IC 卡读卡器，52：北斗
			卫星定位模块
			1 升级结果 BYTE 0：成功，1：失败，2：取消 
	 */
	@Override
	public void handle(ChannelHandlerContext ctx, PackageData data) {
		System.out.println("升级通知");
		//获取session
		Session s = SessionManager.getObject().getSession(ctx.channel().id().toString());
		if(s == null){
			logger.info("session is null");
			return;
		}
		
		byte[] body = data.getMsgBodyBytes();
		if(ArrayUtils.isEmpty(body)){
			logger.info("升级通知消息体为空");
			return;
		}
		//获取手机号
		String phone = data.getMsgHeader().getTerminalPhone();
		byte upgradeType = body[0];
		//升级类型
		TerminalUpgradeNoticeState state = TerminalUpgradeNoticeState.getIndex((int)body[1]);
		
		switch(upgradeType){
		case 0:
			logger.info(String.format("终端 SIM：%s 升级结果：%s 升级方式：终端", phone, state.getName()));
			break;
		case 12:
			logger.info(String.format("终端 SIM：%s 升级结果：%s 升级方式：道路运输证 IC 卡读卡器", phone, state.getName()));
			break;
		case 52:
			logger.info(String.format("终端 SIM：%s 升级结果：%s 升级方式：北斗卫星定位模块", phone, state.getName()));
			break;
			default:
				logger.error("未知类型");
		}
		
		//TODO 要根据升级的情况处理，持久化的数据.车辆设备信息表的状态维护 state
		
	}

}
