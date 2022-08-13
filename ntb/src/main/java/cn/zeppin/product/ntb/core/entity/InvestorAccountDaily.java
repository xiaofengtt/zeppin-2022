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
 * @author Clark.R 2016年9月19日
 *
 * 尝试不用Hibernate的外键对象原因（所有外键字段全部以基本型数据而不是对象体现）
 * 1、外键对象全部读取效率低，高效率的lazy load配置复杂
 * 2、利于构建基于Uuid的缓存层的实现（全部用dao调用实现关联对象读取）
 * 3、对象Json序列化涉及到对象循环引用引起的死循环的问题
 * 4、Control层返回对象基本不用多级对象直接生成的Json格式，Json返回值格式以扁平化结构为佳
 * 5、复杂查询（尤其是含有统计数据的返回值列表）一般都是自定义的返回值格式
 * 
 * 
 * @description 【数据对象】前台投资人每日账户情况
 */

@Entity
@Table(name = "investor_account_daily")
public class InvestorAccountDaily extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2579882874513481593L;
	
	private String uuid;
	private String investor;
	private Timestamp date;
	
	private BigDecimal totalInvestAmount;
	private BigDecimal totalRecailmAmount;
	private BigDecimal totalReturns;
	private BigDecimal totalReturnsRate;
	
	private Timestamp statistime;
	
	
	@Id
	@Column(name = "uuid", unique = true, nullable = false, length = 36)
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Column(name = "investor", nullable = false, length = 36)
	public String getInvestor() {
		return investor;
	}

	public void setInvestor(String investor) {
		this.investor = investor;
	}

	@Column(name = "date", nullable = false)
	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	@Column(name = "total_invest_amount", nullable = false, length = 20)
	public BigDecimal getTotalInvestAmount() {
		return totalInvestAmount;
	}

	public void setTotalInvestAmount(BigDecimal totalInvestAmount) {
		this.totalInvestAmount = totalInvestAmount;
	}

	@Column(name = "total_recailm_amount", nullable = false, length = 20)
	public BigDecimal getTotalRecailmAmount() {
		return totalRecailmAmount;
	}

	public void setTotalRecailmAmount(BigDecimal totalRecailmAmount) {
		this.totalRecailmAmount = totalRecailmAmount;
	}

	@Column(name = "total_returns", nullable = false, length = 20)
	public BigDecimal getTotalReturns() {
		return totalReturns;
	}

	public void setTotalReturns(BigDecimal totalReturns) {
		this.totalReturns = totalReturns;
	}

	@Column(name = "total_returns_rate", nullable = false, length = 16)
	public BigDecimal getTotalReturnsRate() {
		return totalReturnsRate;
	}

	public void setTotalReturnsRate(BigDecimal totalReturnsRate) {
		this.totalReturnsRate = totalReturnsRate;
	}

	@Column(name = "statistime", nullable = false, length = 16)
	public Timestamp getStatistime() {
		return statistime;
	}

	public void setStatistime(Timestamp statistime) {
		this.statistime = statistime;
	}
	
}
