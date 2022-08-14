package cn.product.worldmall.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Id;

public class FrontUserVoucher implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7690642817555184350L;
	
	@Id
	private String uuid;
	private String frontUser;
	private String voucher;
	private String title;
	private String discription;
	
	private BigDecimal dAmount;
	private BigDecimal payMin;
	
	private String goodsType;
	private String goods;
	private Timestamp starttime;
	private Timestamp endtime;
	private Timestamp createtime;
	private String creator;
	
	private String status;
	private Timestamp operattime;
	
	private String orderId;
	
    public class FrontUserVoucherStatus{
    	public final static String UNSTART = "unstart";
    	public final static String NORMAL = "normal";
    	public final static String USED = "used";
    	public final static String OVERTIME = "overtime";
    }
    
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getFrontUser() {
		return frontUser;
	}

	public void setFrontUser(String frontUser) {
		this.frontUser = frontUser;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	public String getVoucher() {
		return voucher;
	}

	public void setVoucher(String voucher) {
		this.voucher = voucher;
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

	public Timestamp getStarttime() {
		return starttime;
	}

	public void setStarttime(Timestamp starttime) {
		this.starttime = starttime;
	}

	public Timestamp getEndtime() {
		return endtime;
	}

	public void setEndtime(Timestamp endtime) {
		this.endtime = endtime;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getOperattime() {
		return operattime;
	}

	public void setOperattime(Timestamp operattime) {
		this.operattime = operattime;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
}