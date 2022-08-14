package cn.product.worldmall.entity;

import java.io.Serializable;

import javax.persistence.Id;

public class AuctionGameGoods implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2650650001188158212L;
	@Id
    private String uuid;
    
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

}