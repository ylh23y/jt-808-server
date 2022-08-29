package com.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.junit.Test;

import com.alibaba.fastjson.JSON;

public class CollectionsSortTest {
	
	@Test
	public void testGetMinValue(){
		List<Integer> sources = new ArrayList<>();
		
		for(int i= 0;i<21;i++){
			sources.add(RandomUtils.nextInt(50));
		}
		
		System.out.println(JSON.toJSON(sources));
		Collections.sort(sources, new Comparator<Integer>() {

			@Override
			public int compare(Integer o1, Integer o2) {
				return o1 > o2 ? 1 : -1;
			}
		});
		
		System.out.println((int)Math.ceil(Double.valueOf(sources.size()) / 2));
		System.out.println(JSON.toJSON(sources));
	}
	

	public static void main(String[] args) {
		
		List<Integer> testList = new ArrayList<Integer>();
		for(int i=0; i< 5; i++){
			testList.add(RandomUtils.nextInt(10));
		}
		
		System.out.println("排序前：" + JSON.toJSONString(testList));
		
		//排序
		Collections.sort(testList, new Comparator<Integer>(){
			@Override
			public int compare(Integer o1, Integer o2) {
				return o1 > o2 ? -1 : 1;
			}
		});
		List<Integer> bb = null;
		System.out.println(CollectionUtils.isEmpty(bb));
		
		System.out.println("排序后：" + JSON.toJSONString(testList));
		
		int b = 0;
		//b = 55 / b;
		try {
			
			if(bb.isEmpty())
			{
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
