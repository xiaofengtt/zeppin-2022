package com.whaty.platform.test.paper;

import java.util.List;

import com.whaty.platform.Exception.PlatformException;

public abstract class ExamPaper extends Paper {
	private String time = "";

	/**
	 * ���� time �Ļ�ȡ������
	 * 
	 * @return ���� time ��ֵ��
	 */
	public String getTime() {
		return time;
	}

	/**
	 * ���� time �����÷�����
	 * 
	 * @param time
	 *            ���� time ����ֵ��
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * ��ҵ�Ծ����Ŀ�Ļ�ȡ������
	 * 
	 * @return List��
	 */
	public abstract List getPaperQuestion() throws PlatformException;

	public abstract int removePaperQuestions() throws PlatformException;

	/**
	 * ��ҵ�Ծ����Ŀ����ӷ�����
	 * 
	 * @param PaperQuestion
	 */
	public void addPaperQuestion(List PaperQuestion) throws PlatformException {
		// TODO Auto-generated method stub

	}

	/**
	 * ��ҵ�Ծ����Ŀ���Ƴ�������
	 * 
	 * @param PaperQuestion
	 */
	public void removePaperQuestion(List PaperQuestion)
			throws PlatformException {
		// TODO Auto-generated method stub

	}

}
