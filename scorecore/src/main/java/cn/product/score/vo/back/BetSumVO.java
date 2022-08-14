package cn.product.score.vo.back;

import java.io.Serializable;
import java.math.BigDecimal;

public class BetSumVO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2143734668156613260L;
	
	private String guessingMatchType;
	private String spread;
	private String result;
	private BigDecimal sum;
	
	public BetSumVO(){
		
	}

	public String getGuessingMatchType() {
		return guessingMatchType;
	}

	public void setGuessingMatchType(String guessingMatchType) {
		this.guessingMatchType = guessingMatchType;
	}

	public String getSpread() {
		return spread;
	}

	public void setSpread(String spread) {
		this.spread = spread;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public BigDecimal getSum() {
		return sum;
	}

	public void setSum(BigDecimal sum) {
		this.sum = sum;
	}
}
