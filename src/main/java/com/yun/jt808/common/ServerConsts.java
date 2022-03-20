package com.yun.jt808.common;

import java.nio.charset.Charset;
/**
* @Description: TODO 
* @author James
* @date 2021年10月15日 上午11:59:54
 */
public class ServerConsts {

	public static final String STRING_ENCODING = "GBK";

	public static final Charset STRING_CHARSET = Charset.forName(STRING_ENCODING);
	/**
	 * 标识位
	 */
	public static final int PKG_DELIMITER = 0x7e;
	/**
	 * 客户端发呆15分钟后,服务器主动断开连接
	 */
	public static int TCP_CLIENT_IDLE_MINUTES = 1;

	/**
	 * 终端通用应答
	 */
	public static final int MSG_ID_TERMINAL_COMMON_RESP = 0x0001;
	/**
	 * 终端心跳
	 */
	public static final int MSG_ID_TERMINAL_HEART_BEAT = 0x0002;
	/**
	 * 终端注册
	 */
	public static final int MSG_ID_TERMINAL_REGISTER = 0x0100;
	/**
	 * 终端注销
	 */
	public static final int MSG_ID_TERMINAL_LOG_OUT = 0x0003;
	/**
	 * 终端鉴权
	 */
	public static final int MSG_ID_TERMINAL_AUTHENTICATION = 0x0102;
	/**
	 * 位置信息汇报
	 */
	public static final int MSG_ID_TERMINAL_LOCATION_INFO_UPLOAD = 0x0200;
	/**
	 * 胎压数据透传
	 */
	public static final int MSG_ID_TERMINAL_TRANSMISSION_TYRE_PRESSURE = 0x0600;
	/**
	 * 查询终端参数应答
	 */
	public static final int MSG_ID_TERMINAL_PARAM_QUERY_RESP = 0x0104;
	/**
	 * 定位数据批量上传数据格式
	 */
	public static final int MSG_ID_BLIND_AREA_BU_BAO = 0x704;

	/**
	 * 平台通用应答
	 */
	public static final int CMD_COMMON_RESP = 0x8001;
	/**
	 * 终端注册应答
	 */
	public static final int CMD_TERMINAL_REGISTER_RESP = 0x8100;
	/**
	 * 设置终端参数
	 */
	public static final int CMD_TERMINAL_PARAM_SETTINGS = 0X8103;
	/**
	 * 查询终端参数
	 */
	public static final int CMD_TERMINAL_PARAM_QUERY = 0x8104;
	
	/**
	 * 查询终端属性
	 */
	public static final int CMD_TERMINAL_ATTR_QUERY = 0x8107;
	/**
	 * 查询终端属性应答
	 */
	public static final int CMD_TERMINAL_PARAM_QUERY_REPLAY = 0x0107;
	
	
	/**
	 * ------------------------ 附加ID ------------------------
	 */
	/**
	 * 车载负载状态信息
	 */
	public static final int LOAD_STATE = 0x0f0;
	
	public static final int N_LOAD_STATE = 0xe3;
	
	public static final int F1 = 0xF1;
	public static final int F2 = 0xF2;
	public static final int F3 = 0xF3;
	public static final int F4 = 0xF4;
	/**
	 * 位置信息查询
	 */
	public static final int LOCATION_QUERY = 0x8201;
	/**
	 * 位置信息查询应答处理
	 */
	public static final int LOCATION_QUERY_REPLY = 0x0201;
	/**
	 * 终端升级结果通知
	 */
	public static final int TERMAINAL_UPGRADE_RESULT_NOTICE = 0x0108;
	/**
	 * 文本信息下发
	 */
	public static final int CMD_SEND_TEXT_TO_TERMINAL = 0x8300;
}
