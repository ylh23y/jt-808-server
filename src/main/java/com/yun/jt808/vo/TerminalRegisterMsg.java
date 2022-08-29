package com.yun.jt808.vo;

import java.util.Arrays;
import java.util.Date;

import com.yun.jt808.common.enums.DeviceRegState;
import com.yun.jt808.server.PackageData;


/**
 * 终端注册消息
 * 
 * @author hylexus
 *
 */
public class TerminalRegisterMsg extends PackageData {

	private TerminalRegInfo terminalRegInfo;

	public TerminalRegisterMsg() {
	}

	public TerminalRegisterMsg(PackageData packageData) {
		this();
		this.channel = packageData.getChannel();
		this.checkSum = packageData.getCheckSum();
		this.msgBodyBytes = packageData.getMsgBodyBytes();
		this.msgHeader = packageData.getMsgHeader();
	}

	public TerminalRegInfo getTerminalRegInfo() {
		return terminalRegInfo;
	}

	public void setTerminalRegInfo(TerminalRegInfo msgBody) {
		this.terminalRegInfo = msgBody;
	}

	@Override
	public String toString() {
		return "TerminalRegisterMsg [terminalRegInfo=" + terminalRegInfo + ", msgHeader=" + msgHeader
				+ ", msgBodyBytes=" + Arrays.toString(msgBodyBytes) + ", checkSum=" + checkSum + ", channel=" + channel
				+ "]";
	}

	public static class TerminalRegInfo {
		/**
		 * 设备手机号(唯一)
		 */
		private String terminalPhone;
		/**
		 * 省域ID(WORD),设备安装车辆所在的省域，省域ID采用GB/T2260中规定的行政区划代码6位中前两位
		// 0保留，由平台取默认值
		 */
		private int provinceId;
		/**
		 * // 市县域ID(WORD) 设备安装车辆所在的市域或县域,市县域ID采用GB/T2260中规定的行 政区划代码6位中后四位
		// 0保留，由平台取默认值
		 */
		private int cityId;
		/**
		 * 制造商ID(BYTE[5]) 5 个字节，终端制造商编码
		 */
		private String manufacturerId;
		/**
		 * 终端型号(BYTE[8]) 八个字节， 此终端型号 由制造商自行定义 位数不足八位的，补空格。
		 */
		private String terminalType;
		/**
		 * 终端ID(BYTE[7]) 七个字节， 由大写字母 和数字组成， 此终端 ID由制造 商自行定义
		 */
		private String terminalId;
		/**
		 * 
		 * 车牌颜色(BYTE) 车牌颜色，按照 JT/T415-2006 的 5.4.12 未上牌时，取值为0<br>
		 * 0===未上车牌<br>
		 * 1===蓝色<br>
		 * 2===黄色<br>
		 * 3===黑色<br>
		 * 4===白色<br>
		 * 9===其他
		 */
		private int licensePlateColor;
		/**
		 * 车牌(STRING) 公安交 通管理部门颁 发的机动车号牌
		 */
		private String licensePlate;
		
		/**
		 * 更新时间
		 */
		private Date updateTime;
		/**
		 * 创建时间
		 */
		private Date createTime;
		
		/**
		 * 状态 0 已注册 -1已注销
		 */
		private DeviceRegState state;
		
		public TerminalRegInfo(String terminalPhone, int provinceId, int cityId, String manufacturerId,
				String terminalType, String terminalId, int licensePlateColor, String licensePlate,
				String terminalPhoneNumber, Date updateTime, Date createTime, DeviceRegState state) {
			super();
			this.terminalPhone = terminalPhone;
			this.provinceId = provinceId;
			this.cityId = cityId;
			this.manufacturerId = manufacturerId;
			this.terminalType = terminalType;
			this.terminalId = terminalId;
			this.licensePlateColor = licensePlateColor;
			this.licensePlate = licensePlate;
			this.updateTime = updateTime;
			this.createTime = createTime;
			this.state = state;
		}
		public DeviceRegState getState() {
			return state;
		}
		public void setState(DeviceRegState state) {
			this.state = state;
		}
		public String getTerminalPhone() {
			return terminalPhone;
		}
		public void setTerminalPhone(String terminalPhone) {
			this.terminalPhone = terminalPhone;
		}
		public int getProvinceId() {
			return provinceId;
		}
		public void setProvinceId(int provinceId) {
			this.provinceId = provinceId;
		}
		public int getCityId() {
			return cityId;
		}
		public void setCityId(int cityId) {
			this.cityId = cityId;
		}
		public String getManufacturerId() {
			return manufacturerId;
		}
		public void setManufacturerId(String manufacturerId) {
			this.manufacturerId = manufacturerId;
		}
		public String getTerminalType() {
			return terminalType;
		}
		public void setTerminalType(String terminalType) {
			this.terminalType = terminalType;
		}
		public String getTerminalId() {
			return terminalId;
		}
		public void setTerminalId(String terminalId) {
			this.terminalId = terminalId;
		}
		public int getLicensePlateColor() {
			return licensePlateColor;
		}
		public void setLicensePlateColor(int licensePlateColor) {
			this.licensePlateColor = licensePlateColor;
		}
		public String getLicensePlate() {
			return licensePlate;
		}
		public void setLicensePlate(String licensePlate) {
			this.licensePlate = licensePlate;
		}
		public Date getUpdateTime() {
			return updateTime;
		}
		public void setUpdateTime(Date updateTime) {
			this.updateTime = updateTime;
		}
		public Date getCreateTime() {
			return createTime;
		}
		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		}
		public TerminalRegInfo(){}
		
		/**
		 * 是否已注册
		 * @return
		 */
		public boolean isReg() {
			return state == DeviceRegState.Registered;
		}

	}
}
