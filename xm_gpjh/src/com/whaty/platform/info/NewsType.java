/*
 * NewsType.java
 *
 * Created on 2004��10��14��, ����6:21
 */

package com.whaty.platform.info;

import java.util.List;

import com.whaty.platform.Items;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.util.Page;

/**
 * �������Ͷ���
 * 
 * @author chenjian
 */
public abstract class NewsType implements Items {

	private String id;

	private String name;

	private String status;

	private String parentId;

	private String parentName;

	private String note;

	private String tag;

	/**
	 * ���� id �Ļ�ȡ������
	 * 
	 * @return ���� id ��ֵ��
	 */
	public java.lang.String getId() {
		return id;
	}

	/**
	 * ���� id �����÷�����
	 * 
	 * @param id
	 *            ���� id ����ֵ��
	 */
	public void setId(java.lang.String id) {
		this.id = id;
	}

	/**
	 * ���� name �Ļ�ȡ������
	 * 
	 * @return ���� name ��ֵ��
	 */
	public java.lang.String getName() {
		return name;
	}

	/**
	 * ���� name �����÷�����
	 * 
	 * @param name
	 *            ���� name ����ֵ��
	 */
	public void setName(java.lang.String name) {
		this.name = name;
	}

	/**
	 * ���� note �Ļ�ȡ������
	 * 
	 * @return ���� note ��ֵ��
	 */
	public java.lang.String getNote() {
		return note;
	}

	/**
	 * ���� note �����÷�����
	 * 
	 * @param note
	 *            ���� note ����ֵ��
	 */
	public void setNote(java.lang.String note) {
		this.note = note;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTag() {
		if (tag == null)
			tag = "";
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public abstract List getManagerPrivList() throws PlatformException;

	public abstract void addManagerPriv(List managerPrivList)
			throws PlatformException;

	public abstract void removeManagerPriv(List managerPrivList)
			throws PlatformException;

	public abstract List getNews(Page page, List searchproperty,
			List orderproperty) throws PlatformException;

	public abstract int getNewsNum(List searchproperty)
			throws PlatformException;

	public abstract List getChildNewsType(List searchproperty,
			List orderproperty) throws PlatformException;

	public abstract boolean isRoot() throws PlatformException;

	public abstract void moveTo(NewsType parentNewsType)
			throws PlatformException;

	public abstract NewsType getParent() throws PlatformException;

	public abstract int updateInfoRight(String[] pageManagerId,
			String[] selectedManagerId) throws PlatformException;

	public abstract List getRightUserIds() throws PlatformException;
}
