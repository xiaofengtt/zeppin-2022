package cn.product.worldmall.entity;

import java.io.Serializable;



public class GoodsIssueSharesnum implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1443471208135232173L;
	
	
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