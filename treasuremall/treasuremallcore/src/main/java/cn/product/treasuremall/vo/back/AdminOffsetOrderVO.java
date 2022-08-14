package cn.product.treasuremall.vo.back;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import cn.product.treasuremall.entity.AdminOffsetOrder;

public class AdminOffsetOrderVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3426888336638712438L;
	
	private String uuid;
	private String frontUser;
	private Integer frontUserShowId;
	private String frontUserNickname;
	private String frontUserImageURL;
	private String frontUserMobile;
	
	private BigDecimal frontUserBalance;
	private BigDecimal frontUserBalanceLock;
	private BigDecimal balanceAfter;
	private BigDecimal balanceBefore;
	
	private String orderNum;
	private String orderType;
	private String type;
	private BigDecimal dAmount;
	private String remark;
	
	private String operator;
	private String operatorName;
	private Timestamp operattime;
	private Timestamp createtime;
	private String status;
	
	public AdminOffsetOrderVO(AdminOffsetOrder aoo){
		this.uuid = aoo.getUuid();
		this.frontUser = aoo.getFrontUser();
		this.orderNum = String.valueOf(aoo.getOrderNum());
		this.orderType = aoo.getOrderType();
		this.type = aoo.getType();
		this.dAmount = aoo.getdAmount();
		this.remark = aoo.getRemark();
		
		this.operator = aoo.getOperator();
		this.operattime = aoo.getOperattime();
		this.status = aoo.getStatus();
		this.createtime = aoo.getCreatetime();
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

	public BigDecimal getFrontUserBalance() {
		return frontUserBalance;
	}

	public void setFrontUserBalance(BigDecimal frontUserBalance) {
		this.frontUserBalance = frontUserBalance;
	}

	public BigDecimal getFrontUserBalanceLock() {
		return frontUserBalanceLock;
	}

	public void setFrontUserBalanceLock(BigDecimal frontUserBalanceLock) {
		this.frontUserBalanceLock = frontUserBalanceLock;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BigDecimal getdAmount() {
		return dAmount;
	}

	public void setdAmount(BigDecimal dAmount) {
		this.dAmount = dAmount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public Timestamp getOperattime() {
		return operattime;
	}

	public void setOperattime(Timestamp operattime) {
		this.operattime = operattime;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getBalanceAfter() {
		return balanceAfter;
	}

	public void setBalanceAfter(BigDecimal balanceAfter) {
		this.balanceAfter = balanceAfter;
	}

	public BigDecimal getBalanceBefore() {
		return balanceBefore;
	}

	public void setBalanceBefore(BigDecimal balanceBefore) {
		this.balanceBefore = balanceBefore;
	}

	public String getFrontUserMobile() {
		return frontUserMobile;
	}

	public void setFrontUserMobile(String frontUserMobile) {
		this.frontUserMobile = frontUserMobile;
	}
}