package cn.zeppin.product.itic.core.controller.base;

import cn.zeppin.product.itic.core.entity.base.Entity;


public class WarningData implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2539568807643475823L;
	
	private String index;
	private String data;
	
	public WarningData(String index, String data){
		this.index = index;
		this.data = data;
	}
	
	public String getIndex() {
		return index;
	}
	
	public void setIndex(String index) {
		this.index = index;
	}
	
	public String getData() {
		return data;
	}
	
	public void setData(String data) {
		this.data = data;
	}
}