package cn.product.treasuremall.vo.front;

import java.io.Serializable;

public class LuckyNumVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3109916544033118933L;
	private Integer luckynum;
	private Boolean isLuck;
	
	public LuckyNumVO() {
		super();
	}

	public Integer getLuckynum() {
		return luckynum;
	}

	public void setLuckynum(Integer luckynum) {
		this.luckynum = luckynum;
	}

	public Boolean getIsLuck() {
		return isLuck;
	}

	public void setIsLuck(Boolean isLuck) {
		this.isLuck = isLuck;
	}
	
}