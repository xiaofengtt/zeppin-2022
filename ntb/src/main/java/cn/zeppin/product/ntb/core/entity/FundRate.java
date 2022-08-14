/**
 * 
 */
package cn.zeppin.product.ntb.core.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.zeppin.product.ntb.core.entity.base.BaseEntity;

/**
 * @description 【数据对象】活期理财费率信息
 */

@Entity
@Table(name = "fund_rate")
public class FundRate extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3698148359983723617L;
	
	private String uuid;
	private String fund;
	private String type;
	private BigDecimal upperlimit;
	private BigDecimal lowlimit;
	private BigDecimal rate;
	private BigDecimal openrate;
	
	public class FundRateTypes{
		public final static String APPLY = "apply";//申购费率
		public final static String BUY = "buy";//认购费率
		public final static String REDEEM = "redeem";//赎回费率
	}
	
	@Id
	@Column(name = "uuid", unique = true, nullable = false, length = 36)
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	@Column(name = "fund", nullable = false, length = 36)
	public String getFund() {
		return fund;
	}
	
	public void setFund(String fund) {
		this.fund = fund;
	}
	
	@Column(name = "type", nullable = false, length = 10)
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	@Column(name = "upperlimit", nullable = false, length = 20)
	public BigDecimal getUpperlimit() {
		return upperlimit;
	}
	
	public void setUpperlimit(BigDecimal upperlimit) {
		this.upperlimit = upperlimit;
	}
	
	@Column(name = "lowlimit", nullable = false, length = 20)
	public BigDecimal getLowlimit() {
		return lowlimit;
	}
	
	public void setLowlimit(BigDecimal lowlimit) {
		this.lowlimit = lowlimit;
	}
	
	@Column(name = "rate", nullable = false, length = 20)
	public BigDecimal getRate() {
		return rate;
	}
	
	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	@Column(name = "openrate", nullable = false, length = 20)
	public BigDecimal getOpenrate() {
		return openrate;
	}
	
	public void setOpenrate(BigDecimal openrate) {
		this.openrate = openrate;
	}
}
