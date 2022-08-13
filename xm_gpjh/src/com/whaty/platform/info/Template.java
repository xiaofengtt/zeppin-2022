package com.whaty.platform.info;

/**
 * ģ�����
 * 
 * @author Terry
 */
public abstract class Template implements com.whaty.platform.Items {

	private String id;

	private String name;

	private String content;

	private String type ;

	private String pub_type;

	private String note ;

	private String mark;

	public abstract Template getTemplateByPub_type(String pub_type);

	/**
	 * ���� name �Ļ�ȡ������
	 * 
	 * @return ���� name ��ֵ��
	 */
	public String getName() {
		return name;
	}

	/**
	 * ���� name �����÷�����
	 * 
	 * @param name
	 *            ���� address ����ֵ��
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * ���� content �Ļ�ȡ������
	 * 
	 * @return ���� content ��ֵ��
	 */
	public String getContent() {
		return content;
	}

	/**
	 * ���� content �����÷�����
	 * 
	 * @param email
	 *            ���� content ����ֵ��
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * ���� type �Ļ�ȡ������
	 * 
	 * @return ���� type ��ֵ��
	 */
	public String getType() {
		return type;
	}

	/**
	 * ���� type �����÷�����
	 * 
	 * @param type
	 *            ���� type ����ֵ��
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * ���� pub_Type �Ļ�ȡ������
	 * 
	 * @return ���� pub_Type ��ֵ��
	 */
	public String getPub_Type() {
		return pub_type;
	}

	/**
	 * ���� pub_Type �����÷�����
	 * 
	 * @param pub_Type
	 *            ���� pub_Type ����ֵ��
	 */
	public void setPub_type(String pub_type) {
		this.pub_type = pub_type;
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
	 * ���� note �����÷�����
	 * 
	 * @param note
	 *            ���� note ����ֵ��
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * ���� id �Ļ�ȡ������
	 * 
	 * @return ���� id ��ֵ��
	 */
	public String getId() {
		return id;
	}

	/**
	 * ���� id �����÷�����
	 * 
	 * @param id
	 *            ���� id ����ֵ��
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * ���� mark �Ļ�ȡ������
	 * 
	 * @return ���� mark ��ֵ��
	 */
	public String getMark() {
		return mark;
	}

	/**
	 * ���� mark �����÷�����
	 * 
	 * @param mark
	 *            ���� mark ����ֵ��
	 */
	public void setMark(String mark) {
		this.mark = mark;
	}

	/**
	 * ��ģ��id�Ƿ����
	 * 
	 * @return 0Ϊ�����ڣ�����0�����
	 */
	public abstract int isIdExist();
}