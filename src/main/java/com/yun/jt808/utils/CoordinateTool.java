package com.yun.jt808.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

/**
 * @Description: 坐标工具
 * @author James
 * @date 2021年7月28日 上午10:51:09
 */
public class CoordinateTool {

	//private Logger logger = LoggerFactory.getLogger(CoordinateTool.class);
	
	/**
	 * 求连点的轨迹点
	 * @param source
	 * @param tagert
	 * @param pointNum
	 * @return
	 */
	public static List<PointO> genPaToPb(PointO source, PointO tagert, int pointNum) {
		// x 判断是+还是-
		boolean sourceXAdd = source.x - tagert.x > 0;
		// y 判断是+还是-
		boolean sourceYAdd = source.y - tagert.y > 0;

		// 求x随机浮动值
		int xcha = (int) Math.abs(source.x - tagert.x);
		int ycha = (int) Math.abs(source.y - tagert.y);

		System.out.println(String.format("ycha = %s, xcha = %s", ycha, xcha));
		CoordinateTool util = new CoordinateTool();  
        System.out.println(util.splitRedPackets(xcha, pointNum)); 
        System.out.println(util.splitRedPackets(ycha, pointNum)); 
        
        List<PointO> polist = new ArrayList<>();
        List<Float> xflist = util.splitRedPackets(xcha, pointNum);
        List<Float> yflist = util.splitRedPackets(ycha, pointNum);
        for(int i=0; i<pointNum; i++)
        {
        	PointO p = new PointO(source.x, source.y);
        	if(polist == null || polist.size() == 0){
        		
        	}else{
        		p.x = polist.get(polist.size() -1).x;
        		p.y = polist.get(polist.size() -1).y;
        	}
        	
        	if(!sourceXAdd){
        		p.x = p.x + xflist.get(i);
        	}else{
        		p.x = p.x - xflist.get(i);
        	}
        	
        	if(!sourceYAdd){
        		p.y = p.y + yflist.get(i);
        	}else{
        		p.y = p.y - yflist.get(i);
        	}
        	polist.add(p);
        	
        }
        
        //随机浮动
        for(PointO p : polist.subList(1, polist.size() -3)){
        	p.x += new Random().nextFloat() * 5;
        }
        
        System.out.println(JSON.toJSON(polist));
		return null;
	}

	public static void main(String[] args) {
		PointO p = new PointO(84.845431, 40.784559);
		PointO p2 = new PointO(121.1249, 24.410226);

		genPaToPb(p, p2, 24);
		
//		CoordinateTool util = new CoordinateTool();  
//        System.out.println(util.splitRedPackets(200, 100)); 
	}

	private static final float TIMES = 2.1f;
	private static final float MINMONEY = 0.01f;
	private static final float MAXMONEY = 200f;

	private boolean isRight(float money, int count) {
		double avg = money / count;
		if (avg < MINMONEY) {
			return false;
		} else if (avg > MAXMONEY) {
			return false;
		}
		return true;
	}

	public List<Float> splitRedPackets(float money, int count) {
		if (!isRight(money, count)) {
			return null;
		}
		List<Float> list = new ArrayList<Float>();
		float max = (float) (money * TIMES / count);

		max = max > MAXMONEY ? MAXMONEY : max;
		for (int i = 0; i < count; i++) {
			float one = randomRedPacket(money, MINMONEY, max, count - i);
			list.add(one);
			money -= one;
		}
		return list;
	}

	private float randomRedPacket(float money, float mins, float maxs, int count) {
		if (count == 1) {
			return (float) (Math.round(money * 100)) / 100;
		}
		if (mins == maxs) {
			// 如果最大值和最小值一样，就返回mins
			return mins;
		}
		float max = maxs > money ? money : maxs;
		float one = ((float) Math.random() * (max - mins) + mins);
		one = (float) (Math.round(one * 100)) / 100;
		float moneyOther = money - one;
		if (isRight(moneyOther, count - 1)) {
			return one;
		} else {
			// 重新分配
			float avg = moneyOther / (count - 1);
			if (avg < MINMONEY) {
				return randomRedPacket(money, mins, one, count);
			} else if (avg > MAXMONEY) {
				return randomRedPacket(money, one, maxs, count);
			}
		}
		return one;
	}
}

class PointO {
	
	public PointO(){}

	public PointO(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double x;
	public double y;
}