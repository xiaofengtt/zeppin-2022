package com.whaty.platform.sms;

public abstract class SmsManagerPriv {
	private String managerId;

	public String getManagerId() {
		return managerId;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}

	/***************************************************************************
	 * ���Ź���Ȩ�� *
	 **************************************************************************/
	public int sendSms = 0;

	public int getSms = 0;

	public int updateSms = 0;

	public int deleteSms = 0;

	public int checkSms = 0;

	public int addSms = 0;

	public int batchImportMobiles = 0; // �Ƿ�����������ƶ�����

	public int rejectSms = 0;// ���ض���

	public int getSmsStatistic = 0;// ������ͳ��

	// ϵͳ���ŵ����
	public int manageSmsSystemPoint = 0;

}
