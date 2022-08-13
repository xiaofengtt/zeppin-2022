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
	 * 系统ID
	 */
	private String id;

	/**
	 * 退费申请单所属者
	 */
	private String userId;

	/**
	 * 退费申请金额
	 */
	private double amount;
	
	/**
	 * 申请时间
	 */
	private String applyTime;
	
	/**
	 * 申请单是否被审核
	 */
	private String isChecked;
	
	/**
	 * 审核申请时间
	 */
	private String checkTime;
	
	/**
	 * 费用是否已退
	 */
	private String isReturned;
	
	/**
	 * 退费时间
	 */
	private String returnTime;
	
	/**
	 * 备注说明
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
