/**
 * 
 */
package cn.zeppin.product.ntb.core.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.zeppin.product.ntb.core.entity.base.BaseEntity;

/**
 * @description 【数据对象】银行理财产品每日净值信息
 */

@Entity
@Table(name = "bank_financial_product_daily")
public class BankFinancialProductDaily extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2031602500293126638L;

	private String uuid;
	private String bankFinancialProduct;
	private BigDecimal netValue;
	private String creator;
	private Timestamp createtime;
	private Timestamp statistime;
	
	@Id
	@Column(name = "uuid", unique = true, nullable = false, length = 36)
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	@Column(name = "bank_financial_product", nullable = false, length = 36)
	public String getBankFinancialProduct() {
		return bankFinancialProduct;
	}
	
	public void setBankFinancialProduct(String bankFinancialProduct) {
		this.bankFinancialProduct = bankFinancialProduct;
	}
	
	@Column(name = "netvalue", nullable = false, length = 16)
	public BigDecimal getNetValue() {
		return netValue;
	}
	
	public void setNetValue(BigDecimal netValue) {
		this.netValue = netValue;
	}
	
	@Column(name = "creator", nullable = false, length = 36)
	public String getCreator() {
		return creator;
	}
	
	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	@Column(name = "createtime", nullable = false)
	public Timestamp getCreatetime() {
		return createtime;
	}
	
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	@Column(name = "statistime", nullable = false)
	public Timestamp getStatistime() {
		return statistime;
	}

	public void setStatistime(Timestamp statistime) {
		this.statistime = statistime;
	}
	
	
}
