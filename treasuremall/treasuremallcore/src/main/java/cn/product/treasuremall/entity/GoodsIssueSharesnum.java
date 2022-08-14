package cn.product.treasuremall.entity;

import java.io.Serializable;

import javax.persistence.Id;

public class GoodsIssueSharesnum implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1443471208135232173L;
	
	@Id
	private String goodsIssue;
	private String sharenums;
	
	public String getGoodsIssue() {
		return goodsIssue;
	}
	public void setGoodsIssue(String goodsIssue) {
		this.goodsIssue = goodsIssue;
	}
	public String getSharenums() {
		return sharenums;
	}
	public void setSharenums(String sharenums) {
		this.sharenums = sharenums;
	}

}