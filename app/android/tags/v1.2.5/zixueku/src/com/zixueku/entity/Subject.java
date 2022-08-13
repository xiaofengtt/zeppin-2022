package com.zixueku.entity;

/**
 * 类说明 学科
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015-2-27 上午10:01:57
 */
public class Subject {
	private Integer id;
	private String name;
	private String shortname;
	private boolean isSelected;
	private short status;

	public Subject() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public String getShortname() {
		return shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	public short getStatus() {
		return status;
	}

	public void setStatus(short status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Subject [id=" + id + ", name=" + name + ", shortname=" + shortname + ", isSelected=" + isSelected + ", status=" + status + "]";
	}

}
