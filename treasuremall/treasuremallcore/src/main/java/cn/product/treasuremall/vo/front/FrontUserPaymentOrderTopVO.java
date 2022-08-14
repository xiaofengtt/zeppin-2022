package cn.product.treasuremall.vo.front;

import java.io.Serializable;
import java.util.Map;

public class FrontUserPaymentOrderTopVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1339950728942880286L;
	
	private Map<String, Object> shafa;
	private Map<String, Object> tuhao;
	private Map<String, Object> baowei;
	
	public FrontUserPaymentOrderTopVO() {
		super();
	}


	public Map<String, Object> getShafa() {
		return shafa;
	}


	public void setShafa(Map<String, Object> shafa) {
		this.shafa = shafa;
	}


	public Map<String, Object> getTuhao() {
		return tuhao;
	}


	public void setTuhao(Map<String, Object> tuhao) {
		this.tuhao = tuhao;
	}


	public Map<String, Object> getBaowei() {
		return baowei;
	}


	public void setBaowei(Map<String, Object> baowei) {
		this.baowei = baowei;
	}

}