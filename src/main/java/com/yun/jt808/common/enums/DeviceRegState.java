package com.yun.jt808.common.enums;
/**
* @Description: 设备注册、注销状态
* @author James
* @date 2021年7月14日 下午2:45:06
 */
public enum DeviceRegState {
	/**
	 * 
	 */
	Registered(1,"在线"),
	/**
	 * 
	 */
	Canceled(-1,"离线");
	
	private String name;
	private Integer index;
	
	private DeviceRegState(Integer index,String name){
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

	public static DeviceRegState getIndex(int value) {
		for(DeviceRegState dr : DeviceRegState.values())
		{
			if(dr.index == value){
				return dr;
			}
		}
		return null;
	}
}
