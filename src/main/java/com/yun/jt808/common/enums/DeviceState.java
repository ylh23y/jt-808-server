package com.yun.jt808.common.enums;
/**
* @Description: 设备状态信息
* @author James
* @date 2021年4月24日 下午7:15:02
 */
public enum DeviceState {
	/**
	 * 
	 */
	ONLINE(1,"在线"),
	/**
	 * 
	 */
	OFFLINE(0,"离线");
	
	private String name;
	private Integer index;
	
	private DeviceState(Integer index,String name){
		this.index = index;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}
}
