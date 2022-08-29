package com.yun.jt808.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
* 
* @Description: TODO 
* @author James
* @date 2021年10月15日 下午1:33:53
 */
public class BCD8421Operater {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public String parseBcdStringFromBytes(byte[] data, int startIndex, int lenth) {
		return this.parseBcdStringFromBytes(data, startIndex, lenth, null);
	}

	private String parseBcdStringFromBytes(byte[] data, int startIndex, int lenth, String defaultVal) {
		try {
			byte[] tmp = new byte[lenth];
			logger.debug("拷贝的数组长度：{},实际拷贝长度：{},开始的未知：{}",data.length,lenth,startIndex);
			System.arraycopy(data, startIndex, tmp, 0, lenth);
			return bcd2String(tmp);
		} catch (Exception e) {
			logger.error("解析BCD(8421码)出错:{}", e.getMessage());
			e.printStackTrace();
			return defaultVal;
		}
	}
	/**
	 * BCD字节数组===>String
	 * 
	 * @param bytes
	 * @return 十进制字符串
	 */
	public String bcd2String(byte[] bytes) {
		StringBuilder temp = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			// 高四位
			temp.append((bytes[i] & 0xf0) >>> 4);
			// 低四位
			temp.append(bytes[i] & 0x0f);
		}
		String result = temp.toString().substring(0, 1).equalsIgnoreCase("0") ? temp.toString().substring(1) : temp.toString();
		return result;
	}
	
	public static String bcd2Str(byte[] bytes) {  
        char temp[] = new char[bytes.length * 2], val;  
  
        for (int i = 0; i < bytes.length; i++) {  
            val = (char) (((bytes[i] & 0xf0) >> 4) & 0x0f);  
            temp[i * 2] = (char) (val > 9 ? val + 'A' - 10 : val + '0');  
  
            val = (char) (bytes[i] & 0x0f);  
            temp[i * 2 + 1] = (char) (val > 9 ? val + 'A' - 10 : val + '0');  
        }  
        return new String(temp);  
    }

	/**
	 * 字符串==>BCD字节数组
	 * 
	 * @param str
	 * @return BCD字节数组
	 */
	public byte[] string2Bcd(String str) {
		// 奇数,前补零
		if ((str.length() & 0x1) == 1) {
			str = "0" + str;
		}

		byte ret[] = new byte[str.length() / 2];
		byte bs[] = str.getBytes();
		for (int i = 0; i < ret.length; i++) {

			byte high = ascII2Bcd(bs[2 * i]);
			byte low = ascII2Bcd(bs[2 * i + 1]);

			// TODO 只遮罩BCD低四位?
			ret[i] = (byte) ((high << 4) | low);
		}
		return ret;
	}

	private byte ascII2Bcd(byte asc) {
		if ((asc >= '0') && (asc <= '9')){
			return (byte) (asc - '0');
			
		}else if ((asc >= 'A') && (asc <= 'F')){
			return (byte) (asc - 'A' + 10);
		}else if ((asc >= 'a') && (asc <= 'f')){
			return (byte) (asc - 'a' + 10);
		}else{
			return (byte) (asc - 48);
		}
	}
	
}
