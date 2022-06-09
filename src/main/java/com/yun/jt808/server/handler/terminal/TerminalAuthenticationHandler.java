package com.yun.jt808.server.handler.terminal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.yun.jt808.dao.DaoFactory;
import com.yun.jt808.dao.TerminalDao;
import com.yun.jt808.netty.codec.MsgEncoder;
import com.yun.jt808.netty.server.ServerHandler;
import com.yun.jt808.netty.session.Session;
import com.yun.jt808.netty.session.SessionManager;
import com.yun.jt808.server.PackageData;
import com.yun.jt808.server.ProtocolServerHandler;
import com.yun.jt808.service.BaseMsgProcessService;
import com.yun.jt808.vo.ServerCommonRespMsgBody;
import com.yun.jt808.vo.TerminalRegisterMsg.TerminalRegInfo;
import io.netty.channel.ChannelHandlerContext;

/**
* @Description: 终端设备鉴权
* @author James
* @date 2021年4月18日 下午2:11:22
 */
public class TerminalAuthenticationHandler extends BaseMsgProcessService implements ServerHandler {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public void handle(ChannelHandlerContext ctx, PackageData data) {
		String terminalPhone = data.getMsgHeader().getTerminalPhone();
		logger.info(">>>>>[终端鉴权],phone={},flowid={}", terminalPhone , data.getMsgHeader().getFlowId());
		try {
			
			TerminalDao terminalDao = DaoFactory.getDao().getTerminalDao();
			Session s = SessionManager.getInstance().getSession(ctx.channel().id().toString());
			
			//默认成功
			byte resultCode = ServerCommonRespMsgBody.SUCCESS;
			if(s == null){
				TerminalRegInfo tri = terminalDao.queryTerminalRegInfo(terminalPhone);
				//如果数据库不存在就直接返回
				if(tri == null){
					resultCode = ServerCommonRespMsgBody.WARNNING_MSG_ACK;
				}else{
					s = new Session();
					s.setChannel(ctx.channel());
					s.setLicensePlate(tri.getLicensePlate());
					s.setSimPhone(terminalPhone);
					s.setTerminalId(tri.getTerminalId());
					s.setFlowId(data.getMsgHeader().getFlowId());
					SessionManager.getObject().addSession(s);
				}
			}
			
			

			ServerCommonRespMsgBody respMsgBody = new ServerCommonRespMsgBody();
			respMsgBody.setReplyCode(resultCode);
			respMsgBody.setReplyFlowId(data.getMsgHeader().getFlowId());
			respMsgBody.setReplyId(data.getMsgHeader().getMsgId());
			int flowId = super.getFlowId(data.getChannel());
			
			MsgEncoder msgEncoder = ProtocolServerHandler.getInstance().getEncoder();
			byte[] bs = msgEncoder.encode4ServerCommonRespMsg(data, respMsgBody, flowId);
			send2Client(data.getChannel(), bs);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
