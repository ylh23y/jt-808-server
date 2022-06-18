package com.yun.jt808.server;

import com.yun.jt808.common.ServerConsts;
import com.yun.jt808.common.WebConsts;
import com.yun.jt808.netty.http.AbstractHttpDisplay;
import com.yun.jt808.server.web.LocationQueryHandler;
import com.yun.jt808.server.web.QueryTerminalAttributeHandler;
import com.yun.jt808.server.web.SendTerminalUpgradePackageHandler;
import com.yun.jt808.server.web.SendTextToTerminalHandler;
/**
 * HTTP 消息注册 
* @Description: 
* @author James
* @date 2021年10月15日 下午1:03:58
 */
public class HttpDisplayHandlers extends AbstractHttpDisplay{

	@Override
	public void display() {
		display(WebConsts.LOCATION_QUERY, new LocationQueryHandler());
		display(WebConsts.SEND_TERMINAL_UPGRADE_PACKAGE, new SendTerminalUpgradePackageHandler());
		display(ServerConsts.CMD_SEND_TEXT_TO_TERMINAL, new SendTextToTerminalHandler());
		display(ServerConsts.CMD_TERMINAL_ATTR_QUERY, new QueryTerminalAttributeHandler());
	}

}
