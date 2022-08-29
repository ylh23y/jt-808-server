package com.yun.jt808.utils;
/**
 * 16进制转换工具类
* @Description: TODO 
* @author James
* @date 2021年10月15日 下午1:38:22
 */
public class HexStringUtils {

	private static final char[] DIGITS_HEX = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	protected static char[] encodeHex(byte[] data) {
		int l = data.length;
		char[] out = new char[l << 1];
		for (int i = 0, j = 0; i < l; i++) {
			out[j++] = DIGITS_HEX[(0xF0 & data[i]) >>> 4];
			out[j++] = DIGITS_HEX[0x0F & data[i]];
		}
		return out;
	}
	
	/**   
	 * 字符串转换成十六进制字符串  
	 * @param String str 待转换的ASCII字符串  
	 * @return String 每个Byte之间空格分隔，如: [61 6C 6B]  
	 */      
	public static String str2HexStr(String str)    
	{      
	  
	    char[] chars = "0123456789ABCDEF".toCharArray();      
	    StringBuilder sb = new StringBuilder("");    
	    byte[] bs = str.getBytes();      
	    int bit;      
	        
	    for (int i = 0; i < bs.length; i++)    
	    {      
	        bit = (bs[i] & 0x0f0) >> 4;      
	        sb.append(chars[bit]);      
	        bit = bs[i] & 0x0f;      
	        sb.append(chars[bit]);    
	        sb.append(' ');    
	    }      
	    return sb.toString().trim();      
	} 

	protected static byte[] decodeHex(char[] data) {
		int len = data.length;
		if ((len & 0x01) != 0) {
			throw new RuntimeException("字符个数应该为偶数");
		}
		byte[] out = new byte[len >> 1];
		for (int i = 0, j = 0; j < len; i++) {
			int f = toDigit(data[j], j) << 4;
			j++;
			f |= toDigit(data[j], j);
			j++;
			out[i] = (byte) (f & 0xFF);
		}
		return out;
	}

	protected static int toDigit(char ch, int index) {
		int digit = Character.digit(ch, 16);
		if (digit == -1) {
			throw new RuntimeException("Illegal hexadecimal character " + ch + " at index " + index);
		}
		return digit;
	}

	public static String toHexString(byte[] bs) {
		return new String(encodeHex(bs));
	}

	public static String hexString2Bytes(String hex) {
		return new String(decodeHex(hex.toCharArray()));
	}

	public static byte[] chars2Bytes(char[] bs) {
		return decodeHex(bs);
	}

	
	 /**
	  * Convert byte[] to hex string.这里我们可以将byte转换成int，然后利用Integer.toHexString(int)来转换成16进制字符串。      
	  * @param src
	  * @return
	  */
	public static String bytesToHexString(byte[] src){  
	    StringBuilder stringBuilder = new StringBuilder("");  
	    if (src == null || src.length <= 0) {  
	        return null;  
	    }  
	    for (int i = 0; i < src.length; i++) {  
	        int v = src[i] & 0xFF;  
	        String hv = Integer.toHexString(v);  
	        if (hv.length() < 2) {  
	            stringBuilder.append(0);  
	        }  
	        stringBuilder.append(hv);  
	    }  
	    return stringBuilder.toString();  
	}  
	/** 

	 * Convert hex string to byte[] 

	 * @param hexString the hex string 

	 * @return byte[] 

	 */  
	public static byte[] hexStringToBytes(String hexString) {  
	    if (hexString == null || hexString.equals("")) {  
	        return null;  
	    }  
	    hexString = hexString.toUpperCase();  
	    int length = hexString.length() / 2;  
	    char[] hexChars = hexString.toCharArray();  
	    byte[] d = new byte[length];  
	    for (int i = 0; i < length; i++) {  
	        int pos = i * 2;  
	        d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));  
	    }  
	    return d;  
	}  
	/** 

	 * Convert char to byte 

	 * @param c char 

	 * @return byte 

	 */  
	 private static byte charToByte(char c) {  
	    return (byte) "0123456789ABCDEF".indexOf(c);  
	} 
	 
	 /** 
	     * 16进制字符串转换为字符串 
	     *  
	     * @param s 
	     * @return 
	     */  
	    public static String hexStringToString(String s) {  
	        if (s == null || s.equals("")) {  
	            return null;  
	        }  
	        s = s.replace(" ", "");  
	        byte[] baKeyword = new byte[s.length() / 2];  
	        for (int i = 0; i < baKeyword.length; i++) {  
	            try {  
	                baKeyword[i] = (byte) (0xff & Integer.parseInt(  
	                        s.substring(i * 2, i * 2 + 2), 16));  
	            } catch (Exception e) {  
	                e.printStackTrace();  
	            }  
	        }  
	        try {  
	            s = new String(baKeyword, "gbk");  
	            new String();  
	        } catch (Exception e1) {  
	            e1.printStackTrace();  
	        }  
	        return s;  
	    }  
}