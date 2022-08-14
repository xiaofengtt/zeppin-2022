/**
 * 
 */
package cn.zeppin.product.ntb.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.zeppin.product.ntb.core.entity.base.BaseEntity;

/**
 * @author hehe 2016年2月8日
 * @description 【数据对象】支付宝转账二维码
 */

@Entity
@Table(name = "ali_qrcode")
public class AliQrcode extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3698148359983723617L;
	
	private String uuid;
	private Integer priceIndex;
	private String priceCN;
	private String priceUrlcode;
	
	@Id
	@Column(name = "uuid", unique = true, nullable = false, length = 36)
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	
	@Column(name = "price_index", nullable = false)
	public Integer getPriceIndex() {
		return priceIndex;
	}
	

	public void setPriceIndex(Integer priceIndex) {
		this.priceIndex = priceIndex;
	}
	
	@Column(name = "price_cn", nullable = false)
	public String getPriceCN() {
		return priceCN;
	}
	

	public void setPriceCN(String priceCN) {
		this.priceCN = priceCN;
	}
	
	@Column(name = "price_urlcode", nullable = false)
	public String getPriceUrlcode() {
		return priceUrlcode;
	}
	

	public void setPriceUrlcode(String priceUrlcode) {
		this.priceUrlcode = priceUrlcode;
	}
	
}
