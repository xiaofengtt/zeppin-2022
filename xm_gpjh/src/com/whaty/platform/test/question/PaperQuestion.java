package com.whaty.platform.test.question;

public class PaperQuestion extends TestQuestion{
	private String score="";
	private String index="";
	private String testPaperId;
	private String userId;
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return Returns the testPaperId.
	 */
	public String getTestPaperId() {
		return testPaperId;
	}

	/**
	 * @param testPaperId The testPaperId to set.
	 */
	public void setTestPaperId(String testPaperId) {
		this.testPaperId = testPaperId;
	}

	/**
	 * ���� index �Ļ�ȡ������
	 * 
	 * @return ���� index ��ֵ��
	 */
	public String getIndex() {
		return index;
	}
	
	/**
	 * ���� index �����÷�����
	 * 
	 * @param index
	 * ���� index ����ֵ��
	 */
	public void setIndex(String index) {
		this.index = index;
	}
	
	/**
	 * ���� score �Ļ�ȡ������
	 * 
	 * @return ���� score ��ֵ��
	 */
	public String getScore() {
		return score;
	}
	
	/**
	 * ���� score �����÷�����
	 * 
	 * @param score
	 * ���� score ����ֵ��
	 */
	public void setScore(String score) {
		this.score = score;
	}
}
