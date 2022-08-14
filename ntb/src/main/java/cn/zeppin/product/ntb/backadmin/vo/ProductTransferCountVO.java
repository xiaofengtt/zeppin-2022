package cn.zeppin.product.ntb.backadmin.vo;

import java.math.BigDecimal;

import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 * 用户每日充值提现统计查询
 * @author Administrator
 *
 */
public class ProductTransferCountVO implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7063644119578448205L;
	
	private String product;
	private String type;
	private Integer term;//产品期限
	private BigDecimal returnRate;//目标年化收益率
	private String createtime;
	private BigDecimal price;
	
	public ProductTransferCountVO(){
		
	}
	
	public ProductTransferCountVO(String product, String type, Integer term,
			BigDecimal returnRate, String createtime, BigDecimal price) {
		super();
		this.product = product;
		this.type = type;
		this.term = term;
		this.returnRate = returnRate;
		this.createtime = createtime;
		this.price = price;
	}


	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
	public String getProduct() {
		return product;
	}
	

	public void setProduct(String product) {
		this.product = product;
	}
	

	public Integer getTerm() {
		return term;
	}
	

	public void setTerm(Integer term) {
		this.term = term;
	}
	

	public BigDecimal getReturnRate() {
		return returnRate;
	}
	

	public void setReturnRate(BigDecimal returnRate) {
		this.returnRate = returnRate;
	}
	

	public BigDecimal getPrice() {
		return price;
	}
	

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
}
