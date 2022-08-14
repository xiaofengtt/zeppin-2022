package cn.product.treasuremall.vo.back;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import cn.product.treasuremall.entity.FrontUserVoucher;

public class FrontUserVoucherVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 579344895380395080L;
	
	private String uuid;
	private String frontUser;
	private Integer frontUserShowId;
	private String frontUserNickname;
	private String frontUserImageURL;
	private String frontUserMobile;
	private String voucher;
	private String title;
	private String discription;
	
	private BigDecimal dAmount;
	private BigDecimal payMin;
	
	private Timestamp starttime;
	private Timestamp endtime;
	private Timestamp createtime;
	private String creator;
	
	private String status;
	private Timestamp operattime;
	
	private String orderId;
	
	//已使用的优惠券 商品信息
	private String orderNum;
	private String code;//商品编码
	private Integer issueNum;//当前期数
	
	public FrontUserVoucherVO(FrontUserVoucher fuv) {
		this.uuid = fuv.getUuid();
		this.frontUser = fuv.getFrontUser();
		this.voucher = fuv.getVoucher();
		this.title = fuv.getTitle();
		this.discription = fuv.getDiscription();
		this.dAmount = fuv.getdAmount();
		this.payMin = fuv.getPayMin();
		this.starttime = fuv.getStarttime();
		this.endtime = fuv.getEndtime();
		this.createtime = fuv.getCreatetime();
		this.status = fuv.getStatus();
		this.orderId = fuv.getOrderId();
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

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getIssueNum() {
		return issueNum;
	}

	public void setIssueNum(Integer issueNum) {
		this.issueNum = issueNum;
	}



	public String getOrderId() {
		return orderId;
	}


	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}


	public Integer getFrontUserShowId() {
		return frontUserShowId;
	}


	public void setFrontUserShowId(Integer frontUserShowId) {
		this.frontUserShowId = frontUserShowId;
	}


	public String getFrontUserNickname() {
		return frontUserNickname;
	}


	public void setFrontUserNickname(String frontUserNickname) {
		this.frontUserNickname = frontUserNickname;
	}


	public String getFrontUserImageURL() {
		return frontUserImageURL;
	}


	public void setFrontUserImageURL(String frontUserImageURL) {
		this.frontUserImageURL = frontUserImageURL;
	}


	public String getFrontUserMobile() {
		return frontUserMobile;
	}


	public void setFrontUserMobile(String frontUserMobile) {
		this.frontUserMobile = frontUserMobile;
	}
}