package com.whaty.platform.test.paper;

import java.util.List;

import com.whaty.platform.Exception.PlatformException;

public abstract class HomeworkPaper extends Paper {
	private String startDate = "";

	private String endDate = "";

	private String comments = "";

	/**
	 * ���� comment �Ļ�ȡ������
	 * 
	 * @return ���� comment ��ֵ��
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * ���� comment �����÷�����
	 * 
	 * @param comment
	 *            ���� comment ����ֵ��
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}

	/**
	 * ���� endDate �Ļ�ȡ������
	 * 
	 * @return ���� endDate ��ֵ��
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * ���� endDate �����÷�����
	 * 
	 * @param endDate
	 *            ���� endDate ����ֵ��
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * ��ҵ�Ծ����Ŀ�Ļ�ȡ������
	 * 
	 * @return List��
	 */
	public List getPaperQuestion() throws PlatformException {
		// TODO Auto-generated method stub
		return null;
	}

	public List getPaperQuestion(String userid) throws PlatformException {
		return null;
	}
	
	/**
	 * ��ҵ�Ծ����Ŀ����ӷ�����
	 * 
	 * @param PaperQuestion
	 */
	public void addPaperQuestion(List PaperQuestion) throws PlatformException {
		// TODO Auto-generated method stub

	}

	/**
	 * ��ҵ�Ծ����Ŀ���Ƴ��
	 * 
	 * @param PaperQuestion
	 */
	public void removePaperQuestion(List PaperQuestion)
			throws PlatformException {
		// TODO Auto-generated method stub

	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

}
