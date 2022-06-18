package com.yun.jt808.server;

import com.yun.jt808.common.ServerConsts;
import com.yun.jt808.netty.server.AbstractServerDisplay;
import com.yun.jt808.server.handler.BlindAreaPointHandler;
import com.yun.jt808.server.handler.CommonReplayHandler;
import com.yun.jt808.server.handler.HeartBeatHandler;
import com.yun.jt808.server.handler.LocationInfoReportHandler;
import com.yun.jt808.server.handler.LocationQueryReplyHandler;
import com.yun.jt808.server.handler.QueryTerminalAttributeReplayHandler;
import com.yun.jt808.server.handler.TerminalCommonReplayHandler;
import com.yun.jt808.server.handler.TerminalUpgradeResultNoticeHandler;
import com.yun.jt808.server.handler.terminal.TerminalAuthenticationHandler;
import com.yun.jt808.server.handler.terminal.TerminalLogoutHandler;
import com.yun.jt808.server.handler.terminal.TerminalRegisterHandler;

/**
* @Description: 注册业务
* @author James
* @date 2021年4月18日 下午1:15:34
 */
public class ServerDisplayHandler extends AbstractServerDisplay{

	@Override
	public void display() {
		display(ServerConsts.MSG_ID_TERMINAL_REGISTER,new TerminalRegisterHandler());
		display(ServerConsts.MSG_ID_TERMINAL_LOCATION_INFO_UPLOAD,new LocationInfoReportHandler());
		display(ServerConsts.MSG_ID_TERMINAL_AUTHENTICATION,new TerminalAuthenticationHandler());
		display(ServerConsts.MSG_ID_TERMINAL_HEART_BEAT, new HeartBeatHandler());
		display(ServerConsts.MSG_ID_TERMINAL_LOG_OUT, new TerminalLogoutHandler());
		display(ServerConsts.MSG_ID_BLIND_AREA_BU_BAO, new BlindAreaPointHandler());
		display(ServerConsts.LOCATION_QUERY_REPLY, new LocationQueryReplyHandler());
		display(ServerConsts.TERMAINAL_UPGRADE_RESULT_NOTICE, new TerminalUpgradeResultNoticeHandler());
		display(ServerConsts.CMD_COMMON_RESP, new CommonReplayHandler());
		display(ServerConsts.MSG_ID_TERMINAL_COMMON_RESP, new TerminalCommonReplayHandler());
		display(ServerConsts.CMD_TERMINAL_PARAM_QUERY_REPLAY, new QueryTerminalAttributeReplayHandler());
	}
}
