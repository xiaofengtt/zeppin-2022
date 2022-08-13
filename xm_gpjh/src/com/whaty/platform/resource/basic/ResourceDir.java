package com.whaty.platform.resource.basic;

import java.util.List;

public abstract class ResourceDir {

	private String id;

	private String name;

	private String parent;

	private String note;

	private String status;

	private String isInherit;
	
	private String keyId;			//���ⲿϵͳʹ�õ�Ψһ��ʶ

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
	 * ���� parent �Ļ�ȡ������
	 * 
	 * @return ���� parent ��ֵ��
	 */
	public String getParent() {
		return parent;
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
	 * ���� isInherit �Ļ�ȡ������
	 * 
	 * @return ���� isInherit ��ֵ��
	 */
	public String getIsInherit() {
		return isInherit;
	}

	/**
	 * ���� id �����÷�����
	 * 
	 * @param ����
	 *            id ��ֵ��
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * ���� name �����÷�����
	 * 
	 * @param ����
	 *            name ��ֵ��
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * ���� parent �����÷�����
	 * 
	 * @param ����
	 *            parent ��ֵ��
	 */
	public void setParent(String parent) {
		this.parent = parent;
	}

	/**
	 * ���� note �����÷�����
	 * 
	 * @param ����
	 *            note ��ֵ��
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * ���� status �����÷�����
	 * 
	 * @param ����
	 *            status ��ֵ��
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * ���� isInherit �����÷�����
	 * 
	 * @param ����
	 *            isInherit ��ֵ��
	 */
	public void setIsInherit(String isInherit) {
		this.isInherit = isInherit;
	}
	
	

	public String getKeyId() {
		return keyId;
	}

	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}

	/**
	 * ��ȡ��ǰ��ԴĿ¼�������¼���Ŀ¼
	 * @return
	 */
	public abstract List getSubDir();

	/**
	 * ��ȡ��ǰ��ԴĿ¼�µ�������Դ
	 * @return
	 */
	public abstract List getResourceList();
}
