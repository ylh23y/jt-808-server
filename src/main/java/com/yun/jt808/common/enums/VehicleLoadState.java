package com.yun.jt808.common.enums;

/**
* @Description: 载重的状态定义
* @author James
* @date 2021年5月10日 下午1:41:35
 */
public enum VehicleLoadState {

	
	//消息ID:0x0f0    字节长度1    01表示空   02表示重载   03表示满载
	kongzai(1,"空载"),
	zhongzai(2,"重载"),
	manzai(3,"满载"),
	chaozai(4,"超载");
	//KONGCHE(0, "空车"), BANZAI(1, "半载"), BAOLIU(2, "保留"), MANZAI(3, "满载");
	
	private Integer index;
	private String name;

	private VehicleLoadState(Integer index, String name) {
		this.index = index;
		this.name = name;
	}
	
	/**
	 * 通过index获取对应的类型
	 * @param index
	 * @return
	 */
	public static VehicleLoadState getType(int index){
		for(VehicleLoadState v : VehicleLoadState.values()){
			if(v.index == index){
				return v;
			}
		}
		return null;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
