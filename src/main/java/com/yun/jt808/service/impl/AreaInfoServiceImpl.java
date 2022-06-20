package com.yun.jt808.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import com.yun.jt808.common.enums.AreaType;
import com.yun.jt808.dao.DaoFactory;
import com.yun.jt808.po.AreaInfo;
import com.yun.jt808.service.AreaInfoService;
import com.yun.jt808.utils.Point;
import com.yun.jt808.utils.SpatialRelationUtil;
import io.netty.util.internal.StringUtil;

/**
* @Description: 区域业务实现类
* @author James
* @date 2021年5月2日 上午10:47:48
 */
public class AreaInfoServiceImpl implements AreaInfoService{

	private Logger logger  = LoggerFactory.getLogger(getClass());
	
	@Override
	public boolean checkAreaPointIn(Point p, AreaType t) {
		if(t == null)
		{
			logger.info("区域类型为空,无法进行点包含检测操作 t = " + t);
			return false;
		}
		if(p == null){
			logger.info("坐标点信息为空，无法进行点包含检测操作");
			return false;
		}
		
		List<AreaInfo> aiList = DaoFactory.getDao().getAreaInfoDao().getAreaInfoListByType(t);
		if(aiList == null || aiList.size() == 0){
			logger.info("区域信息数据暂时没有数据,无法进行点包含检测操作");
			return false;
		}
		// 测试使用
		int randomAreaIndex = new Random().nextInt(aiList.size());
		
		for(int i=0; i<aiList.size(); i++){
			AreaInfo ai = aiList.get(i);
			String coordinatesets = ai.getCoordinateSets();
			//如果是空直接跳过不处理
			if(coordinatesets == null || "".equalsIgnoreCase(coordinatesets)){
				continue;
			}
			
			//将str 转为 List<Point>
			List<Point> PList = JSON.parseArray(coordinatesets, Point.class);
			boolean isInclude = SpatialRelationUtil.isPolygonContainsPoint(PList, p);
			
			//如果当前区域包含则返回,否则继续检测
			if(randomAreaIndex == i){
				System.out.println("" + ai.getName());
				return isInclude;
			}
		}
		return false;
	}

	@Override
	public AreaInfo getAreaInfoByPoint(Point p) {
		List<AreaInfo> areaInfos = DaoFactory.getDao().getAreaInfoDao().getAllAreaInfos();
		if(CollectionUtils.isNotEmpty(areaInfos)){
			AreaInfo reaultAreaInfo = null;
			for(AreaInfo areaInfo : areaInfos){
				String coordinatesets = areaInfo.getCoordinateSets();
				if(!StringUtils.isBlank(coordinatesets)){
					List<Point> pointlist = createPointsFromCoordinatesets(coordinatesets);
					boolean checkResult = SpatialRelationUtil.isPolygonContainsPoint(pointlist, p);
					if(checkResult){
						reaultAreaInfo = areaInfo;
						logger.info("检测到该点包含区域ID = {}, 区域名称 = {}", areaInfo.getId(), areaInfo.getName());
						break;
					}
				}
			}
			return reaultAreaInfo;
		}
		return null;
	}
	
	/**
	 * 创建Point 列表根据区域表coordinatesets属性内容
	 * @param coordinatesets
	 * @return
	 */
	public static List<Point> createPointsFromCoordinatesets(String coordinatesets) {
		JSONArray level1 = JSON.parseArray(coordinatesets);
		JSONArray level2 = JSON.parseArray(level1.getString(0));
		
		List<Point> resultlist = new ArrayList<>();
		for(int i=0; i<level2.size(); i++)
		{
			JSONArray obj = JSON.parseArray(level2.getString(i));
			Point p = new Point();
			p.x = ((BigDecimal) obj.get(0)).floatValue() ;
			p.y = ((BigDecimal) obj.get(1)).floatValue();
			resultlist.add(p);
		}
		return resultlist;
	}

	public static void main(String[] args) {
		
//		String sources = "[[[498955.14859570924,2511136.8531816527,null],[499370.2800083437,2511370.5043904996,null],[499456.11949892086,2511204.8571775965,null],[499044.2934086416,2510978.0848268103,null],[498955.14859570924,2511136.8531816527,null]]]";
//		Point p = new Point();
//		p.y = 2511136.8531816527;
//		//投影坐标正常 lat = 2511373.9312464512, lng = 499351.7670527073
//		p.x = 498955.14859570924;
		
		String sources = "[[[498955.14859570924,2511136.8531816527,null],[499370.2800083437,2511370.5043904996,null],[499456.11949892086,2511204.8571775965,null],[499044.2934086416,2510978.0848268103,null],[498955.14859570924,2511136.8531816527,null]]]";
		Point p = new Point();
		p.y = 2511136.8531816527;
		//投影坐标正常 lat = 2511373.9312464512, lng = 499351.7670527073
		p.x = 499044.2934086416;
		
		List<Point> ps = createPointsFromCoordinatesets(sources);
		
		boolean flag = SpatialRelationUtil.isPolygonContainsPoint(ps, p);
		boolean border = SpatialRelationUtil.isPointInPolygonBoundary(ps, p);
		
		
		String datas = "[{\"日期\":\"2017-02月\",\"正式道路\":0,\"临时道路\":0,\"进场道路\":0,\"应急道路\":0,\"重件道路\":0,\"轻件道路\":0},{\"日期\":\"2017-03月\",\"正式道路\":0,\"临时道路\":0,\"进场道路\":0,\"应急道路\":0,\"重件道路\":0,\"轻件道路\":0},{\"日期\":\"2017-04月\",\"正式道路\":0,\"临时道路\":0,\"进场道路\":0,\"应急道路\":0,\"重件道路\":0,\"轻件道路\":0},{\"日期\":\"2017-05月\",\"正式道路\":0,\"临时道路\":0,\"进场道路\":0,\"应急道路\":0,\"重件道路\":0,\"轻件道路\":0},{\"日期\":\"2017-06月\",\"正式道路\":0,\"临时道路\":0,\"进场道路\":0,\"应急道路\":0,\"重件道路\":0,\"轻件道路\":0},{\"日期\":\"2017-07月\",\"临时道路\":\"1019.42\",\"正式道路\":\"272.00\",\"进场道路\":\"1179.27\",\"应急道路\":\"112.14\",\"轻件道路\":\"159.85\",\"重件道路\":\"1131.56\"},{\"日期\":\"2017-08月\",\"正式道路\":\"489873.09\",\"临时道路\":\"1019.42\",\"进场道路\":\"490780.37\",\"应急道路\":\"112.14\",\"重件道路\":\"490732.66\",\"轻件道路\":\"159.85\"}]";
	}

}
