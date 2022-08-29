package com.yun.jt808.utils;

import java.util.ArrayList;
import java.util.List;
/**
 * 属性测试类
* @Description: TODO 
* @author James
* @date 2021年10月15日 下午1:29:38
 */
public class AttTest {

	
	public static void main(String[] args) {
		byte[] bs = {19, 1, 4, 0, 0, 0, 12, 3, 2, 0, 0, 37, 4, 0, 0, 0, 0, 48, 1, 22, 49, 1, 9, -16, 1, 3};
		
		for(byte b : bs)
		{
			System.out.println(b);
		}
		
		//1、怎么解析随便一个ID？
		//2、怎么找到Id的位置？
		//3、怎么找到ID的值？
		
		
		
		int code = Integer.parseInt("0f0", 16);
		System.out.println(code);
		byte value = (byte) code;
		System.out.println(value);
		
		List<Integer> list = new ArrayList<>();
		
		
		System.out.println(System.currentTimeMillis() - 50 *24 * 60 * 60 * 1000);
		System.out.println(System.currentTimeMillis());
	}
}
