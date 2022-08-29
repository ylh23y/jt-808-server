package com.yun.jt808.test;

public class MainTest {

	public static void main(String[] args) {

		for(int a=0;a<10000;a++){
			if(a%7==4 && a%5==2 && a%3==0){
				System.out.println(a);
			}
		}

	}
}