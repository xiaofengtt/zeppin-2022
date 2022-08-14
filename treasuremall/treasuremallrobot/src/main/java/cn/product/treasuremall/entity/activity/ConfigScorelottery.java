package cn.product.treasuremall.entity.activity;

import java.io.Serializable;

public class ConfigScorelottery implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3017184492048779618L;
	
	private Integer timesLine;
	
	private String scoreLine;

	public Integer getTimesLine() {
		return timesLine;
	}

	public void setTimesLine(Integer timesLine) {
		this.timesLine = timesLine;
	}

	public String getScoreLine() { 
		return scoreLine;
	}

	public void setScoreLine(String scoreLine) {
		this.scoreLine = scoreLine;
	}
}