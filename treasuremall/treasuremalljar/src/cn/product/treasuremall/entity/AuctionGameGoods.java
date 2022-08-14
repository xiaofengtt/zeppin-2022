package cn.product.treasuremall.entity;

import java.io.Serializable;



public class AuctionGameGoods implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2650650001188158212L;
	
    private String uuid;
    
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

}