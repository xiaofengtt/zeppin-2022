package com.whaty.platform.resource.basic;

import java.util.List;

public abstract class ResourceType {

	private String id = "";
	private String name = "";
	
	private String note = "";
	private String status = "";
	
	private List resourceFieldList = null;

	/**
	 * ���� id �Ļ�ȡ������
	 * 
	 * @return ���� id ��ֵ��
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * ���� name �Ļ�ȡ������
	 * 
	 * @return ���� name ��ֵ��
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * ���� note �Ļ�ȡ������
	 * 
	 * @return ���� note ��ֵ��
	 */
	public String getNote() {
		return note;
	}
	
	/**
	 * ���� status �Ļ�ȡ������
	 * 
	 * @return ���� status ��ֵ��
	 */
	public String getStatus() {
		return status;
	}
	
	/**
	 * ���� id �����÷�����
	 * 
	 * @param ���� id ��ֵ��
	 */
	public void setId(String id) {
		this.id=id;
	}
	
	/**
	 * ���� name �����÷�����
	 * 
	 * @param ���� name ��ֵ��
	 */
	public void setName(String name) {
		this.name=name;
	}
	
	/**
	 * ���� note �����÷�����
	 * 
	 * @param ���� note ��ֵ��
	 */
	public void setNote(String note) {
		this.note=note;
	}
	
	/**
	 * ���� status �����÷�����
	 * 
	 * @param ���� status ��ֵ��
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
