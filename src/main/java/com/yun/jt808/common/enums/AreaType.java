package com.yun.jt808.common.enums;
/**
* @Description: TODO 
* @author James
* @date 2021年10月15日 下午12:32:03
 */
public enum AreaType {
	/**
	 * 
	 */
	kaiwaqu(1,"开挖区"),
	/**
	 * 
	 */
	huitianqu(2,"回填区"),
	/**
	 * 
	 */
	qiliaoqu(3,"弃料区");
	
	private int index;
	private String name;
	
	private AreaType(int index, String name){
		this.index = index;
		this.name = name;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
