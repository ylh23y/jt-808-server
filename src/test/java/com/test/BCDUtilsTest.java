package com.test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.yun.jt808.utils.BCD8421Operater;
import com.yun.jt808.utils.DateUtil;

public class BCDUtilsTest {
	
	@Test
	public void testme(){
		String date = "2017-06-01 00:00:00";
		String format = "yyyy-MM-dd HH:mm:ss";
		
		Date start = DateUtil.parseStringToDate(date, DateUtil.datePattern_3);
		System.out.println(">>>>>");
		Calendar cal = Calendar.getInstance();
		cal.setTime(start);
		for(int i=0;i<90;i++){
			cal.add(Calendar.DAY_OF_MONTH, 1);
			System.out.println(DateUtil.dateFormat(cal.getTime(), DateUtil.datePattern_3));
		}
	}
	
	public void testFor(){
		List<Integer> list = new ArrayList<Integer>();
//		for(int i=0,item ;)
	}
	
	public static void main(String[] args) {
		
		
		
		
		
		
		
	}

	public void testByteTo() {
		byte[] bcdArrays = {23, 9, 4, 20, 17, 36};
		
		BCD8421Operater bcd8421Operater = new BCD8421Operater();
		System.out.println(bcd8421Operater.bcd2String(bcdArrays));
		
		StringBuilder sb = new StringBuilder();
		for (byte b : bcdArrays) {
			sb.append(Integer.toHexString(b));
		}
		System.out.println(sb.toString());
		System.out.println(DateUtil.dateFormat(new Date(1794141124), "yyMMddHHmmss"));
	}

	static public String decodeWeirdDate(final byte a[]) {
		StringBuffer buf = new StringBuffer();
		for (byte b : a)
			buf.append(String.format("%02X", b));
		return buf.toString();
	}
	
	/**  
	    * byte数组中取int数值，本方法适用于(低位在前，高位在后)的顺序，和和intToBytes（）配套使用 
	    *   
	    * @param src  
	    *            byte数组  
	    * @param offset  
	    *            从数组的第offset位开始  
	    * @return int数值  
	    */    
	public static int bytesToInt(byte[] src, int offset) {  
	    int value;    
	    value = (int) ((src[offset] & 0xFF)   
	            | ((src[offset+1] & 0xFF)<<8)   
	            | ((src[offset+2] & 0xFF)<<16)   
	            | ((src[offset+3] & 0xFF)<<24));  
	    return value;  
	} 
}
