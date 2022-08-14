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
 * @description 【数据对象】应用内消息
 */

@Entity
@Table(name = "investor_coupon_history")
public class InvestorCouponHistory extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7103067181358004425L;
	private String uuid;
	private String investorProductBuy;
	private String coupon;
	private BigDecimal price;
	
	private Timestamp createtime;
	private String creator;
	private String status;
	
	private String investorAccountHistory;
	private BigDecimal dividend;
	
	public class InvestorCouponHistoryStatus {
		public final static  String NORMAL = "normal";
	}
	
	@Id
	@Column(name = "uuid", unique = true, nullable = false, length = 36)
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Column(name = "createtime", nullable = false)
	public Timestamp getCreatetime() {
		return createtime;
	}
	
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	
	@Column(name = "creator", length = 36, nullable = false)
	public String getCreator() {
		return creator;
	}
	
	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	@Column(name = "status", length = 10, nullable = false)
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "investor_product_buy", length = 36, nullable = false)
	public String getInvestorProductBuy() {
		return investorProductBuy;
	}

	public void setInvestorProductBuy(String investorProductBuy) {
		this.investorProductBuy = investorProductBuy;
	}

	@Column(name = "coupon", length = 36, nullable = false)
	public String getCoupon() {
		return coupon;
	}

	public void setCoupon(String coupon) {
		this.coupon = coupon;
	}

	@Column(name = "price", nullable = false)
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@Column(name = "investor_account_history", length = 36, nullable = false)
	public String getInvestorAccountHistory() {
		return investorAccountHistory;
	}

	public void setInvestorAccountHistory(String investorAccountHistory) {
		this.investorAccountHistory = investorAccountHistory;
	}
	
	@Column(name = "dividend")
	public BigDecimal getDividend() {
		return dividend;
	}

	public void setDividend(BigDecimal dividend) {
		this.dividend = dividend;
	}
	
}
