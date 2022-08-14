package cn.product.treasuremall.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Id;

public class Voucher implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4720500549313719481L;
	
	@Id
	private String uuid;
	private String title;
	private String discription;
	
	private BigDecimal dAmount;
	private BigDecimal payMin;
	
	private String goodsType;
	private String goods;
	private String starttime;
	private String endtime;
	
	private String status;
	private String creator;
	private Timestamp createtime;
	
	public class VoucherStatus{
		public final static String NORMAL = "normal";
		public final static String DISABLE = "disable";
		public final static String DELETE = "delete";
	}
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDiscription() {
		return discription;
	}

	public void setDiscription(String discription) {
		this.discription = discription;
	}

	public BigDecimal getdAmount() {
		return dAmount;
	}

	public void setdAmount(BigDecimal dAmount) {
		this.dAmount = dAmount;
	}

	public BigDecimal getPayMin() {
		return payMin;
	}

	public void setPayMin(BigDecimal payMin) {
		this.payMin = payMin;
	}

	public String getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}

	public String getGoods() {
		return goods;
	}

	public void setGoods(String goods) {
		this.goods = goods;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
}