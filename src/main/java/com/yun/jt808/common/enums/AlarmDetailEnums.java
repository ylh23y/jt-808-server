package com.yun.jt808.common.enums;
/**
* @Description: TODO 
* @author James
* @date 2021年10月15日 下午12:31:57
 */
public enum AlarmDetailEnums {

//	SPEEDING(1,"超速"),
//	OVERTIME_PARKING(2,"超时停车(怠工)"),
//	OVERLOAD(3,"超载"),
//	ABNORMALUNLOADING(4,"非正常卸载(驶出热区外卸货)"),
//	LOWLOAD(5,"少载");
	
	chaosu(1, "超速"), 
	chaozhong(2, "超重"), 
	weiguixiehuo(3, "违规卸货"), 
	shaozai(4, "少载");

	
	private String name;
	private Integer index;
	
	private AlarmDetailEnums(Integer index,String name){
		this.name = name;
		this.index = index;
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
