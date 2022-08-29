package com.yun.jt808.utils;

import java.util.List;
/**
* @Description: TODO 
* @author James
* @date 2021年4月28日 上午9:34:51
* 
 */
public class SpatialRelationUtil {
	
	private SpatialRelationUtil() {
	}

	/**
	 * 返回一个点是否在一个多边形区域内
	 * 
	 * @param mPoints
	 *            多边形坐标点列表
	 * @param point
	 *            待判断点
	 * @return true 多边形包含这个点,false 多边形未包含这个点。
	 */
	public static boolean isPolygonContainsPoint(List<Point> mPoints, Point point) {
		int nCross = 0;
		for (int i = 0; i < mPoints.size(); i++) {
			Point p1 = mPoints.get(i);
			Point p2 = mPoints.get((i + 1) % mPoints.size());
			// 取多边形任意一个边,做点point的水平延长线,求解与当前边的交点个数
			// p1p2是水平线段,要么没有交点,要么有无限个交点
			if (p1.y == p2.y){
				continue;
			}
				
			// point 在p1p2 底部 --> 无交点
			if (point.y < Math.min(p1.y, p2.y)){
				continue;
			}
			// point 在p1p2 顶部 --> 无交点
			if (point.y >= Math.max(p1.y, p2.y)){
				continue;
			}
			// 求解 point点水平线与当前p1p2边的交点的 X 坐标
			double x = (point.y - p1.y) * (p2.x - p1.x) / (p2.y - p1.y) + p1.x;
			if (x > point.x){
				nCross++;
			} // 当x=point.x时,说明point在p1p2线段上
				 // 只统计单边交点
		}
		 // 单边交点为偶数，点在多边形之外  
	    boolean resultFlag = (nCross%2==1);  
	    return resultFlag;
	}
	
	/** 
	 * 检查多边形是否包含了某点~ 
	 * @param point 
	 * @return 
	 */  
	public static boolean containsPoint(List<Point> mPoints, Point point) {  
	    int verticesCount = mPoints.size();  
	    int nCross = 0;  
	    for (int i = 0; i < verticesCount; ++ i) {  
	    	Point p1 = mPoints.get(i);
	    	int s = (i + 1) % verticesCount;
	    	Point p2 = mPoints.get(s);  
	      
	        // 求解 y=p.y 与 p1 p2 的交点  
	    	// p1p2 与 y=p0.y平行  
	        if ( p1.y == p2.y ) {   
	            continue;  
	        }  
	        // 交点在p1p2延长线上 
	        if ( point.y < Math.min(p1.y, p2.y) ) {   
	            continue;  
	        }  
	        // 交点在p1p2延长线上   
	        if ( point.y >= Math.max(p1.y, p2.y) ) { 
	            continue;  
	        }  
	        // 求交点的 X 坐标  
	        double x = (point.y - p1.y) * (p2.x - p1.x)   
	                    / (p2.y - p1.y) + p1.x;  
	        // 只统计单边交点
	        if ( x > point.x ) {   
	            nCross++;  
	        }  
	    }  
	    // 单边交点为偶数，点在多边形之外  
	    boolean resultFlag = (nCross%2==1);  
	    return resultFlag;
	} 

	/**
	 * 返回一个点是否在一个多边形边界上
	 * 
	 * @param mPoints
	 *            多边形坐标点列表
	 * @param point
	 *            待判断点
	 * @return true 点在多边形边上,false 点不在多边形边上。
	 */
	public static boolean isPointInPolygonBoundary(List<Point> mPoints, Point point) {
		for (int i = 0; i < mPoints.size(); i++) {
			Point p1 = mPoints.get(i);
			Point p2 = mPoints.get((i + 1) % mPoints.size());
			// 取多边形任意一个边,做点point的水平延长线,求解与当前边的交点个数

			// point 在p1p2 底部 --> 无交点
			if (point.y < Math.min(p1.y, p2.y)){
				continue;
			}
			// point 在p1p2 顶部 --> 无交点
			if (point.y > Math.max(p1.y, p2.y)){
				continue;
			}

			// p1p2是水平线段,要么没有交点,要么有无限个交点
			if (p1.y == p2.y) {
				double minX = Math.min(p1.x, p2.x);
				double maxX = Math.max(p1.x, p2.x);
				// point在水平线段p1p2上,直接return true
				boolean flag = (point.y == p1.y) && (point.x >= minX && point.x <= maxX);
				if (flag) {
					return true;
				}
			} else { // 求解交点
				double x = (point.y - p1.y) * (p2.x - p1.x) / (p2.y - p1.y) + p1.x;
				// 当x=point.x时,说明point在p1p2线段上
				if (x == point.x){ 
					return true;
				}
			}
		}
		return false;
	}
	
}


