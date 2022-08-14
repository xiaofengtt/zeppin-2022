package cn.product.treasuremall.entity;

import java.io.Serializable;

import javax.persistence.Id;

public class FrontUserPaidNumber implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8817954466209367957L;
	
	@Id
	private String orderId;
	private String frontUser;
	private String issueGoods;
	
	private String paidSharenums;

	public String getFrontUser() {
		return frontUser;
	}

	public void setFrontUser(String frontUser) {
		this.frontUser = frontUser;
	}

	public String getIssueGoods() {
		return issueGoods;
	}

	public void setIssueGoods(String issueGoods) {
		this.issueGoods = issueGoods;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getPaidSharenums() {
		return paidSharenums;
	}

	public void setPaidSharenums(String paidSharenums) {
		this.paidSharenums = paidSharenums;
	}

}