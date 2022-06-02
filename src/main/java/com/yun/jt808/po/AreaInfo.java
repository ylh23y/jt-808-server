package com.yun.jt808.po;

import java.util.Date;

/**
 * @Description: 区域信息表
 * @author James
 * @date 2021年5月2日 上午10:23:23
 */
public class AreaInfo {

	@Override
	public String toString() {
		return "AreaInfo [id=" + id + ", number=" + number + ", name=" + name + ", type=" + type + ", coordinateSets="
				+ coordinateSets + ", acreage=" + acreage + ", operator=" + operator + ", personLiable=" + personLiable
				+ ", phone=" + phone + ", updateTime=" + updateTime + ", createTime=" + createTime + ", state=" + state
				+ "]";
	}
	/**
	 * 区域自增长编号
	 */
	private int id; 
	/**
	 * 区域编号
	 */
	private String number; 
	/**
	 * 区域名称
	 */
	private String name; 
	/**
	 * 区域类型
	 */
	private int type;
	/**
	 *  区域位置信息,[{x,y},{x,y},{x,y},{x,y}]
	 */
	private String coordinateSets;
	/**
	 * 区域面积 单位/平方米
	 */
	private float acreage;
	/**
	 * 录入操作者
	 */
	private String operator; 
	/**
	 * 区域负责人
	 */
	private String personLiable; 
	/**
	 * 联系电话
	 */
	private String phone; 
	/**
	 * 更新时间
	 */
	private Date updateTime;
	/**
	 * 创建时间
	 */
	private Date createTime; 
	/**
	 * 状态信息
	 */
	private int state; 

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getCoordinateSets() {
		return coordinateSets;
	}

	public void setCoordinateSets(String coordinateSets) {
		this.coordinateSets = coordinateSets;
	}

	public float getAcreage() {
		return acreage;
	}

	public void setAcreage(float acreage) {
		this.acreage = acreage;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getPersonLiable() {
		return personLiable;
	}

	public void setPersonLiable(String personLiable) {
		this.personLiable = personLiable;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

}
