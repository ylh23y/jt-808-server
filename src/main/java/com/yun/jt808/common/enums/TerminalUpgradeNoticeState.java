package com.yun.jt808.common.enums;
/**
* @Description: 设备更新通知 
* @author James
* @date 2021年10月15日 下午12:35:23
 */
public enum TerminalUpgradeNoticeState {
	/**
	 * 
	 */
	cancel(2, "取消"),
	/**
	 * 
	 */
	success(0, "成功"),
	/**
	 * 
	 */
	fail(1,"失败");
	
	private int index;
	private String name;
	
	private TerminalUpgradeNoticeState(int index, String name){
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

	public static TerminalUpgradeNoticeState getIndex(int index) {
		for(TerminalUpgradeNoticeState tun : TerminalUpgradeNoticeState.values()){
			if(tun.getIndex() == index){
				return tun;
			}
		}
		return null;
	}
}
