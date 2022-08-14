package cn.product.treasuremall.vo.front;

import java.io.Serializable;
import java.util.List;

public class FrontUserLuckyNumVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 176220023680011282L;
	
	private Integer luckynum;
	private Integer buyCount;
	private List<LuckyNumVO> listNums;
	
	public FrontUserLuckyNumVO() {
		super();
	}

	public Integer getLuckynum() {
		return luckynum;
	}

	public void setLuckynum(Integer luckynum) {
		this.luckynum = luckynum;
	}

	public Integer getBuyCount() {
		return buyCount;
	}

	public void setBuyCount(Integer buyCount) {
		this.buyCount = buyCount;
	}

	public List<LuckyNumVO> getListNums() {
		return listNums;
	}

	public void setListNums(List<LuckyNumVO> listNums) {
		this.listNums = listNums;
	}
	
}