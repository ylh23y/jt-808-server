package com.yun.jt808.server.handler.terminal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.yun.jt808.common.enums.DeviceRegState;
import com.yun.jt808.dao.DaoFactory;
import com.yun.jt808.dao.TerminalDao;
import com.yun.jt808.netty.codec.MsgEncoder;
import com.yun.jt808.netty.server.ServerHandler;
import com.yun.jt808.netty.session.Session;
import com.yun.jt808.netty.session.SessionManager;
import com.yun.jt808.server.PackageData;
import com.yun.jt808.service.BaseMsgProcessService;
import com.yun.jt808.utils.BitOperator;
import com.yun.jt808.utils.ByteOperator;
import com.yun.jt808.vo.TerminalRegisterMsg;
import com.yun.jt808.vo.TerminalRegisterMsgRespBody;
import com.yun.jt808.vo.TerminalRegisterMsg.TerminalRegInfo;
import io.netty.channel.ChannelHandlerContext;

/**
* @Description: 终端注册业务处理
* @author James
* @date 2021年4月18日 下午1:21:15
 */
public class TerminalRegisterHandler extends BaseMsgProcessService implements ServerHandler{

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public void handle(ChannelHandlerContext ctx, PackageData data) {
		/**
		 * 场景：设备注册时由车载设备发起的注册
		 * 1、检测设备是否已经注册过了，如果已经注册过了就将状态进行更新，否则就进行信息的录入
		 */
		//1、获取到设备号码
		String terminalPhone = data.getMsgHeader().getTerminalPhone();
		logger.info(">>>>>[终端鉴权], terminalPhone={}, flowid={}", terminalPhone , data.getMsgHeader().getFlowId());

		//2、查询已注册的终端信息
		TerminalDao terminalDao = DaoFactory.getDao().getTerminalDao();
		TerminalRegInfo tri = terminalDao.queryTerminalRegInfo(terminalPhone);
		
		//3、判定设备的状态 state 1 已注册 -1已注销
		//解析终端这侧信息
		TerminalRegisterMsg ret = new TerminalRegisterMsg(data);
		byte[] bodyData = ret.getMsgBodyBytes();
		ByteOperator btyeUtil = new ByteOperator(new BitOperator());
		
		//结果码
		//默认成功
		byte resultCode = 0;
		if(tri == null){
			//注册设备信息
			tri = new TerminalRegInfo();
			tri.setTerminalPhone(terminalPhone);
			// 1. byte[0-1] 省域ID(WORD)
			// 设备安装车辆所在的省域，省域ID采用GB/T2260中规定的行政区划代码6位中前两位
			// 0保留，由平台取默认值
			tri.setProvinceId(btyeUtil.parseIntFromBytes(bodyData, 0, 2));

			// 2. byte[2-3] 设备安装车辆所在的市域或县域,市县域ID采用GB/T2260中规定的行 政区划代码6位中后四位
			// 0保留，由平台取默认值
			tri.setCityId(btyeUtil.parseIntFromBytes(bodyData, 2, 2));

			// 3. byte[4-8] 制造商ID(BYTE[5]) 5 个字节，终端制造商编码
			// byte[] tmp = new byte[5];
			tri.setManufacturerId(btyeUtil.parseStringFromBytes(bodyData, 4, 5));

			// 4. byte[9-16] 终端型号(BYTE[8]) 八个字节， 此终端型号 由制造商自行定义 位数不足八位的，补空格。
			tri.setTerminalType(btyeUtil.parseStringFromBytes(bodyData, 9, 20));

			// 5. byte[17-23] 终端ID(BYTE[7]) 七个字节， 由大写字母 和数字组成， 此终端 ID由制造 商自行定义
			//终端ID
			String terminalId = btyeUtil.parseStringFromBytes(bodyData, 29, 7);
			tri.setTerminalId(terminalId);

			// 6. byte[24] 车牌颜色(BYTE) 车牌颜 色按照JT/T415-2006 中5.4.12 的规定
			tri.setLicensePlateColor(btyeUtil.parseIntFromBytes(bodyData, 36, 1));

			// 7. byte[25-x] 车牌(STRING) 公安交 通管理部门颁 发的机动车号牌
			//车牌号
			String licensePlate = btyeUtil.parseStringFromBytes(bodyData, 37, bodyData.length - 37);
			tri.setLicensePlate(licensePlate);
			//车载设备信息入库
			terminalDao.insert(tri);
		}else{
			//修改注册状态为已注册
			if(!tri.isReg()){
				tri.setState(DeviceRegState.Registered);
				terminalDao.update(tri);
			}
		}
		
		
		//TODO 新版Session
		Session s = SessionManager.getObject().getSession(terminalPhone);
		if(s == null){
			s = new Session();
		}
		
		s.setChannel(ctx.channel());
		s.setLicensePlate(tri.getLicensePlate());
		s.setSimPhone(terminalPhone);
		SessionManager.getObject().addSession(s);
		
		TerminalRegisterMsgRespBody respMsgBody = new TerminalRegisterMsgRespBody();
		respMsgBody.setReplyCode(resultCode);
		respMsgBody.setReplyFlowId(data.getMsgHeader().getFlowId());
		/**
		 * 鉴权码只会在成功处理的时候才会产生
		 */
		respMsgBody.setReplyToken(resultCode == 1 ? "DEUWISE_JQM" : "");
		int flowId = super.getFlowId(data.getChannel());
		MsgEncoder msgEncoder = new MsgEncoder();
		try {
			byte[] bs = msgEncoder.encode4TerminalRegisterResp(ret, respMsgBody, flowId);
			send2Client(data.getChannel(), bs);
		} catch (Exception e) {
			logger.error("终端注册编码或者应答时异常 e={}",e);
		}
	}
}
