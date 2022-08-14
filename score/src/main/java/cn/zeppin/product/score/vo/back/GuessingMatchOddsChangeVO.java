package cn.zeppin.product.score.vo.back;

import java.io.Serializable;

import cn.zeppin.product.score.entity.GuessingMatchOdds;

public class GuessingMatchOddsChangeVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7507048513019987962L;
	
	private GuessingMatchOdds oldData;
	private GuessingMatchOdds newData;
	
	public GuessingMatchOdds getOldData() {
		return oldData;
	}
	
	public void setOldData(GuessingMatchOdds oldData) {
		this.oldData = oldData;
	}
	
	public GuessingMatchOdds getNewData() {
		return newData;
	}
	
	public void setNewData(GuessingMatchOdds newData) {
		this.newData = newData;
	}
	
}