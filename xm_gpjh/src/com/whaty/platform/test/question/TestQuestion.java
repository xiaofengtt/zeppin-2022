package com.whaty.platform.test.question;

import com.whaty.platform.resource.basic.Resource;

public class TestQuestion extends Resource implements java.io.Serializable{

	private String id="";
	private String diff="";
	private String title="";
	private String lore="";
	private String questionCore="";
	private String creatUser="";
	private String creatDate="";
	private String cognizeType="";
	private String purpose="";
	private String referenceScore="";
	private String referenceTime="";
	private String studentNote="";
	private String teacherNote="";
	private String type = "";
	
	/**
	 * @return Returns the type.
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type The type to set.
	 */
	public void setType(String type) {
		this.type = type;
	}

	public String getStudentNote() {
		return studentNote;
	}

	public void setStudentNote(String studentNote) {
		this.studentNote = studentNote;
	}

	public String getTeacherNote() {
		return teacherNote;
	}

	public void setTeacherNote(String teacherNote) {
		this.teacherNote = teacherNote;
	}

	public String getReferenceTime() {
		return referenceTime;
	}

	public void setReferenceTime(String referenceTime) {
		this.referenceTime = referenceTime;
	}

	public String getCognizeType() {
		return cognizeType;
	}

	public void setCognizeType(String cognizeType) {
		this.cognizeType = cognizeType;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getReferenceScore() {
		return referenceScore;
	}

	public void setReferenceScore(String referenceScore) {
		this.referenceScore = referenceScore;
	}

	/**
	 * ���� creatDate �Ļ�ȡ������
	 * 
	 * @return ���� creatDate ��ֵ��
	 */
	public String getCreatDate() {
		return creatDate;
	}
	
	/**
	 * ���� creatDate �����÷�����
	 * 
	 * @param creatDate
	 * ���� creatDate ����ֵ��
	 */
	public void setCreatDate(String creatDate) {
		this.creatDate = creatDate;
	}
	
	/**
	 * ���� creatUser �Ļ�ȡ������
	 * 
	 * @return ���� creatUser ��ֵ��
	 */
	public String getCreatUser() {
		return creatUser;
	}
	
	/**
	 * ���� creatUser �����÷�����
	 * 
	 * @param creatUser
	 * ���� time ����ֵ��
	 */
	public void setCreatUser(String creatUser) {
		this.creatUser = creatUser;
	}
	
	/**
	 * ���� diff �Ļ�ȡ������
	 * 
	 * @return ���� diff ��ֵ��
	 */
	public String getDiff() {
		return diff;
	}
	
	/**
	 * ���� diff �����÷�����
	 * 
	 * @param diff
	 * ���� diff ����ֵ��
	 */
	public void setDiff(String diff) {
		this.diff = diff;
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
	 * ���� id ����ֵ��
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * ���� lore �Ļ�ȡ������
	 * 
	 * @return ���� lore ��ֵ��
	 */
	public String getLore() {
		return lore;
	}
	
	/**
	 * ���� lore �����÷�����
	 * 
	 * @param lore
	 * ���� lorelist ����ֵ��
	 */
	public void setLore(String lore) {
		this.lore = lore;
	}
	
	/**
	 * ���� questionCore �Ļ�ȡ������
	 * 
	 * @return ���� questionCore ��ֵ��
	 */
	public String getQuestionCore() {
		return questionCore;
	}
	
	/**
	 * ���� questionCore �����÷�����
	 * 
	 * @param questionCore
	 * ���� questionCore ����ֵ��
	 */
	public void setQuestionCore(String questionCore) {
		this.questionCore = questionCore;
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
	 * ���� title �����÷�����
	 * 
	 * @param title
	 * ���� title ����ֵ��
	 */
	public void setTitle(String title) {
		this.title = title;
	}
}
