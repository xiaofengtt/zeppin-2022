/**
 * 
 */
package com.whaty.platform.entity.fee.deal;

/**
 * @author wangqiang
 * 
 */
public abstract class UserFeeReturnApply {
	/**
	 * ϵͳID
	 */
	private String id;

	/**
	 * �˷����뵥������
	 */
	private String userId;

	/**
	 * �˷�������
	 */
	private double amount;
	
	/**
	 * ����ʱ��
	 */
	private String applyTime;
	
	/**
	 * ���뵥�Ƿ����
	 */
	private String isChecked;
	
	/**
	 * �������ʱ��
	 */
	private String checkTime;
	
	/**
	 * �����Ƿ�����
	 */
	private String isReturned;
	
	/**
	 * �˷�ʱ��
	 */
	private String returnTime;
	
	/**
	 * ��ע˵��
	 */
	private String note;

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(String isChecked) {
		this.isChecked = isChecked;
	}

	public String getIsReturned() {
		return isReturned;
	}

	public void setIsReturned(String isReturned) {
		this.isReturned = isReturned;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}

	public String getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(String checkTime) {
		this.checkTime = checkTime;
	}

	public String getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(String returnTime) {
		this.returnTime = returnTime;
	}
}
