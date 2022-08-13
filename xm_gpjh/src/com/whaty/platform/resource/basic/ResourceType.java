package com.whaty.platform.resource.basic;

import java.util.List;

public abstract class ResourceType {

	private String id = "";
	private String name = "";
	
	private String note = "";
	private String status = "";
	
	private List resourceFieldList = null;

	/**
	 * 属性 id 的获取方法。
	 * 
	 * @return 属性 id 的值。
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * 属性 name 的获取方法。
	 * 
	 * @return 属性 name 的值。
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 属性 note 的获取方法。
	 * 
	 * @return 属性 note 的值。
	 */
	public String getNote() {
		return note;
	}
	
	/**
	 * 属性 status 的获取方法。
	 * 
	 * @return 属性 status 的值。
	 */
	public String getStatus() {
		return status;
	}
	
	/**
	 * 属性 id 的设置方法。
	 * 
	 * @param 属性 id 的值。
	 */
	public void setId(String id) {
		this.id=id;
	}
	
	/**
	 * 属性 name 的设置方法。
	 * 
	 * @param 属性 name 的值。
	 */
	public void setName(String name) {
		this.name=name;
	}
	
	/**
	 * 属性 note 的设置方法。
	 * 
	 * @param 属性 note 的值。
	 */
	public void setNote(String note) {
		this.note=note;
	}
	
	/**
	 * 属性 status 的设置方法。
	 * 
	 * @param 属性 status 的值。
	 */
	public void setStatus(String status) {
		this.status=status;
	}

	/**
	 * @return Returns the resourceFieldList.
	 */
	public List getResourceFieldList() {
		return resourceFieldList;
	}

	/**
	 * @param resourceFieldList The resourceFieldList to set.
	 */
	public void setResourceFieldList(List resourceFieldList) {
		this.resourceFieldList = resourceFieldList;
	}
}
