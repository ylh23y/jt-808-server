package com.yun.jt808.utils.gpTool;

import java.util.Random;

/**
* @Description: 生成随机点
* @author James
* @date 2021年7月26日 下午2:14:08
 */
public class GenRandomPos {
	/**
    tsf3dIntiExtent: {
        xmin: 498324.43620979454,
        ymin: 2509520.5840133773,
        xmax: 502555.83251070254,
        ymax: 2513043.5413002055,
        spatialReference: 2383
    },
    zp3dIntiExtent:{
        xmin:500002.3320602687,
        ymin: 2391612.196992279,
        xmax:  509357.3320602687,
        ymax: 2400117.1969922795,
        spatialReference:2383
    }
	 */
	private final static double XMAX = 502555.832510;
	private final static double XMIN = 498324.436209;
	private final static double YMAX = 2513043.541300;
	private final static double YMIN = 2509520.58401;

	/**
	 * 生成x
	 * @return
	 */
	public static double genX(){
		return XMIN + new Random().nextInt((int) (XMAX - XMIN));
	}
	
	/**
	 * 生成y
	 * @return
	 */
	public static double genY(){
		return YMIN + new Random().nextInt((int) (YMAX - YMIN));
	}
}
