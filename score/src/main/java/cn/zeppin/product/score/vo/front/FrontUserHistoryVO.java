package cn.zeppin.product.score.vo.front;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import cn.zeppin.product.score.entity.FrontUserHistory;

public class FrontUserHistoryVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -129751181023574983L;
	
	private String uuid;
	private String frontUser;
	private String orderId;
	private String orderNum;
	private String orderType;
	private BigDecimal balance;
	private BigDecimal income;
	private BigDecimal pay;
	private BigDecimal poundage;
	private String transData;
	private String type;
	private String status;
	private Timestamp createtime;
    
	public FrontUserHistoryVO(FrontUserHistory fuh){
		this.uuid = fuh.getUuid();
		this.frontUser = fuh.getFrontUser();
		this.orderId = fuh.getOrderId();
		this.orderNum = fuh.getOrderNum();
		this.orderType = fuh.getOrderType();
		this.balance = fuh.getBalance();
		this.income = fuh.getIncome();
		this.pay = fuh.getPay();
		this.poundage = fuh.getPoundage();
		this.type = fuh.getType();
		this.transData = fuh.getTransData();
		this.status = fuh.getStatus();
		this.createtime = fuh.getCreatetime();
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

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
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

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public BigDecimal getIncome() {
		return income;
	}

	public void setIncome(BigDecimal income) {
		this.income = income;
	}

	public BigDecimal getPay() {
		return pay;
	}

	public void setPay(BigDecimal pay) {
		this.pay = pay;
	}

	public BigDecimal getPoundage() {
		return poundage;
	}

	public void setPoundage(BigDecimal poundage) {
		this.poundage = poundage;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTransData() {
		return transData;
	}

	public void setTransData(String transData) {
		this.transData = transData;
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
}