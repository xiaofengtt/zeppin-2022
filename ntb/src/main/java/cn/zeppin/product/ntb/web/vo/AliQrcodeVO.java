/**
 * 
 */
package cn.zeppin.product.ntb.web.vo;

import cn.zeppin.product.ntb.core.entity.AliQrcode;
import cn.zeppin.product.ntb.core.entity.base.BaseEntity;

/**
 * @author hehe 2016年2月8日
 * @description 【数据对象】支付宝转账二维码
 */

public class AliQrcodeVO extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3698148359983723617L;
	
	private String uuid;
	private Integer priceIndex;
	private String priceCN;
	private String priceUrlcode;
	private String priceUrlcodeCN;
	
	
	public AliQrcodeVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AliQrcodeVO(AliQrcode aq) {
		this.uuid = aq.getUuid();
		this.priceIndex = aq.getPriceIndex();
		this.priceCN = aq.getPriceCN();
		this.priceUrlcode = aq.getPriceUrlcode();
		this.priceUrlcodeCN = aq.getPriceUrlcode().replace("HTTPS://QR.ALIPAY.COM/", "");
	}

	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public Integer getPriceIndex() {
		return priceIndex;
	}
	
	public void setPriceIndex(Integer priceIndex) {
		this.priceIndex = priceIndex;
	}
	
	public String getPriceCN() {
		return priceCN;
	}
	
	public void setPriceCN(String priceCN) {
		this.priceCN = priceCN;
	}
	
	public String getPriceUrlcode() {
		return priceUrlcode;
	}
	
	public void setPriceUrlcode(String priceUrlcode) {
		this.priceUrlcode = priceUrlcode;
		this.priceUrlcodeCN = priceUrlcode.replace("HTTPS://QR.ALIPAY.COM/", "");
	}
	
	public String getPriceUrlcodeCN() {
		return priceUrlcodeCN;
	}
	
	public void setPriceUrlcodeCN(String priceUrlcodeCN) {
		this.priceUrlcodeCN = priceUrlcodeCN;
	}
	
}
