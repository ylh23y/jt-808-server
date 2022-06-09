package com.yun.jt808.server.handler.terminal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yun.jt808.common.enums.DeviceRegState;
import com.yun.jt808.dao.DaoFactory;
import com.yun.jt808.dao.TerminalDao;
import com.yun.jt808.netty.codec.MsgEncoder;
import com.yun.jt808.netty.server.ServerHandler;
import com.yun.jt808.server.PackageData;
import com.yun.jt808.server.PackageData.MsgHeader;
import com.yun.jt808.service.BaseMsgProcessService;
import com.yun.jt808.vo.ServerCommonRespMsgBody;
import com.yun.jt808.vo.TerminalRegisterMsg.TerminalRegInfo;
import io.netty.channel.ChannelHandlerContext;

/**
* @Description: 终端注销处理
* @author James
* @date 2021年4月25日 上午10:29:37
 */
public class TerminalLogoutHandler extends BaseMsgProcessService implements ServerHandler{

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public void handle(ChannelHandlerContext ctx, PackageData data) {
		String terminalPhone = data.getMsgHeader().getTerminalPhone();
		if(logger.isDebugEnabled())
		{
			logger.debug(">>>>>>>>>>>>>>>>>>终端注销：phoneNumber = {} , flowId = {}" , terminalPhone,data.getMsgHeader().getFlowId());
		}
		
		final MsgHeader reqHeader = data.getMsgHeader();
		ServerCommonRespMsgBody respMsgBody = new ServerCommonRespMsgBody(reqHeader.getFlowId(), reqHeader.getMsgId(),
				ServerCommonRespMsgBody.SUCCESS);
		int flowId = data.getMsgHeader().getFlowId();
		MsgEncoder msgEncoder = new MsgEncoder();
		try {
			//注销要更新状态
			TerminalDao terminalDao = DaoFactory.getDao().getTerminalDao();
			TerminalRegInfo tri = terminalDao.queryTerminalRegInfo(terminalPhone);
			if(tri != null){
				tri.setState(DeviceRegState.Canceled);
				terminalDao.update(tri);
				logger.info(String.format("注销=%s 更新设备信息的状态", terminalPhone));
			}
			
			byte[] bs = msgEncoder.encode4ServerCommonRespMsg(data, respMsgBody, flowId);
			
			send2Client(data.getChannel(), bs);
		} catch (Exception e) {
			if(logger.isDebugEnabled())
			{
				logger.debug("终端设备注销异常 e=" , e);
			}
		}
	}

}
