package com.whaty.platform.resource.basic;

import java.util.List;

public class Resource {

	private String id;
	private String title;
	private String language;
	private String discription;
	private String keyWords;
	private String creatUser;
	private String detail;
	private ResourceDir resourceDir;
	private ResourceType resourceType;
	private List resourceContentList;
	private String status;
	/**
	 * ���� id �Ļ�ȡ������
	 * 
	 * @return ���� id ��ֵ��
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * ���� title �Ļ�ȡ������
	 * 
	 * @return ���� title ��ֵ��
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * ���� language �Ļ�ȡ������
	 * 
	 * @return ���� language ��ֵ��
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * ���� discription �Ļ�ȡ������
	 * 
	 * @return ���� discription ��ֵ��
	 */
	public String getDiscription() {
		return discription;
	}
	
	/**
	 * ���� keyWords �Ļ�ȡ������
	 * 
	 * @return ���� keyWords ��ֵ��
	 */
	public String getKeyWords() {
		return keyWords;
	}
	
	/**
	 * ���� creatUser �Ļ�ȡ������
	 * 
	 * @return ���� creatUser ��ֵ��
	 */
	public String getCreatUser() {
		return creatUser;
	}
	
	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	/**
	 * ���� id �����÷�����
	 * 
	 * @param id
	 * 
	 */
	public void setId(String id) {
		this.id=id;
	}
	
	/**
	 * ���� title �����÷�����
	 *  
	 * @param title
	 * 
	 */
	public void setTitle(String title) {
		this.title=title;
	}
	
	/**
	 * ���� language �����÷�����
	 * 
	 * @param language
	 */
	public void setLanguage(String language) {
		this.language=language;
	}

	/**
	 * ���� discription �����÷�����
	 * 
	 * @param discription
	 */
	public void setDiscription(String discription) {
		this.discription=discription;
	}
	
	/**
	 * ���� keyWords �����÷�����
	 * 
	 * @param keyWords
	 */
	public void setKeyWords(String keyWords) {
		this.keyWords=keyWords;
	}
	
	/**
	 * ���� creatUser �����÷�����
	 * 
	 * @param creatUser
	 */
	public void setCreatUser(String creatUser) {
		this.creatUser=creatUser;
	}

	/**
	 * @return Returns the resourceType.
	 */
	public ResourceType getResourceType() {
		return resourceType;
	}

	/**
	 * @param resourceType The resourceType to set.
	 */
	public void setResourceType(ResourceType resourceType) {
		this.resourceType = resourceType;
	}

	/**
	 * @return Returns the resourceDir.
	 */
	public ResourceDir getResourceDir() {
		return resourceDir;
	}

	/**
	 * @param resourceDir The resourceDir to set.
	 */
	public void setResourceDir(ResourceDir resourceDir) {
		this.resourceDir = resourceDir;
	}
	
	/**
	 * @param ������Դ����
	 */
	public List getResourceContentList() {
		return resourceContentList;
	}	
	
	/**
	 * @param ��Դ���ݸ�ֵ
	 */
	public void setResourceContentList(List resourceContentList) {
		this.resourceContentList = resourceContentList;
	}

	/**
	 * @return
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
}
