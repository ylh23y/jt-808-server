package com.yun.jt808.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yun.jt808.common.ServerConsts;
/**
 * byte 数值工具类
* @Description: TODO 
* @author James
* @date 2021年10月15日 下午1:28:59
 */
public class ByteOperator {

	private Logger log = LoggerFactory.getLogger(getClass());
	private BitOperator bitOperator;
	
	
	public ByteOperator(BitOperator bitOperator){
		this.bitOperator = bitOperator;
	}
	
	public String parseStringFromBytes(byte[] data, int startIndex, int lenth) {
		return this.parseStringFromBytes(data, startIndex, lenth, null);
	}

	private String parseStringFromBytes(byte[] data, int startIndex, int lenth, String defaultVal) {
		try {
			byte[] tmp = new byte[lenth];
			System.arraycopy(data, startIndex, tmp, 0, lenth);
			return new String(tmp, ServerConsts.STRING_CHARSET);
		} catch (Exception e) {
			log.error("解析字符串出错:{}", e.getMessage());
			e.printStackTrace();
			return defaultVal;
		}
	}
	
	public int parseIntFromBytes(byte[] data, int startIndex, int length) {
		return this.parseIntFromBytes(data, startIndex, length, 0);
	}

	private int parseIntFromBytes(byte[] data, int startIndex, int length, int defaultVal) {
		try {
			// 字节数大于4,从起始索引开始向后处理4个字节,其余超出部分丢弃
			final int len = length > 4 ? 4 : length;
			byte[] tmp = new byte[len];
			System.arraycopy(data, startIndex, tmp, 0, len);
			return bitOperator.byteToInteger(tmp);
		} catch (Exception e) {
			log.error("解析整数出错:{}", e.getMessage());
			e.printStackTrace();
			return defaultVal;
		}
	}
}
