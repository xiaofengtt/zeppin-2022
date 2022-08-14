package cn.product.treasuremall.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class FrontUserComment implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5490808939891612487L;
	
    private String uuid;
	private String frontUser;
	private Integer frontUserShowId;
	private String orderId;
	private Long orderNum;
	private String winningInfo;
	private String goodsIssue;
	private String image;
	private String video;
	private String detail;
	private String status;
    private Timestamp createtime;
	
	private String operator;
	private Timestamp operattime;
	private String reason;
	
    
    public class FrontUserCommentStatus{
    	public final static String NORMAL = "normal";
    	public final static String CHECKED = "checked";
    	public final static String NOPASS = "nopass";
    	public final static String DELETE = "delete";
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

	public Integer getFrontUserShowId() {
		return frontUserShowId;
	}

	public void setFrontUserShowId(Integer frontUserShowId) {
		this.frontUserShowId = frontUserShowId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getGoodsIssue() {
		return goodsIssue;
	}

	public void setGoodsIssue(String goodsIssue) {
		this.goodsIssue = goodsIssue;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Timestamp getOperattime() {
		return operattime;
	}

	public void setOperattime(Timestamp operattime) {
		this.operattime = operattime;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	
	public Long getOrderNum() {
		return orderNum;
	}
	

	public void setOrderNum(Long orderNum) {
		this.orderNum = orderNum;
	}

	public String getWinningInfo() {
		return winningInfo;
	}

	public void setWinningInfo(String winningInfo) {
		this.winningInfo = winningInfo;
	}

}