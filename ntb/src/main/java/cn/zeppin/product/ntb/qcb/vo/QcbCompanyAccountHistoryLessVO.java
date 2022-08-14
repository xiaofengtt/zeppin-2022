package cn.zeppin.product.ntb.qcb.vo;

import java.math.BigDecimal;
import java.sql.Timestamp;

import cn.zeppin.product.ntb.core.entity.QcbCompanyAccountHistory;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccountHistory.QcbCompanyAccountHistoryType;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.utility.Utlity;

public class QcbCompanyAccountHistoryLessVO implements Entity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4740659141418123237L;
	
	private String uuid;
	private String orderNum;
	
	private BigDecimal accountBalance;
	private String accountBalanceCN;
	
	private Boolean priceflag;
	private String price;
	
	private String type;
	private String typeCN;
	
	private String companyAccount;
	private String bankName;
	private String cardNum;
	
	private String remark;
	
	private Timestamp createtime;
	
	private String createtimeCN;
	
	private String product;
	private String productCN;
	private String productType;
	
	
	public QcbCompanyAccountHistoryLessVO(){
		
	}
	
	public QcbCompanyAccountHistoryLessVO(QcbCompanyAccountHistory cah){
		this.uuid = cah.getUuid();
		this.orderNum = cah.getOrderNum();
		
		this.accountBalance = cah.getAccountBalance();
		this.accountBalanceCN = cah.getAccountBalance().setScale(2, BigDecimal.ROUND_HALF_UP).toString();
		
		
		if(cah.getIncome().compareTo(BigDecimal.valueOf(0)) == 1){
			this.price = cah.getIncome().setScale(2, BigDecimal.ROUND_HALF_UP).toString();
		} else if (cah.getPay().compareTo(BigDecimal.valueOf(0)) == 1) {
			this.price = cah.getPay().setScale(2, BigDecimal.ROUND_HALF_UP).toString();
		} else {
			this.price = "0.00";
		}
		
		this.priceflag = true;
		this.type = cah.getType();
		if(QcbCompanyAccountHistoryType.WITHDRAW.equals(cah.getType())){
			this.typeCN = "提现";
			this.remark = "账户提现";
			this.priceflag = false;
		} else if (QcbCompanyAccountHistoryType.INCOME.equals(cah.getType())) {
			this.typeCN = "充值";
			this.remark = "账户充值";
			this.priceflag = true;
		} else if (QcbCompanyAccountHistoryType.BUY.equals(cah.getType())) {
			this.typeCN = "投资";
			this.remark = "投资";
			this.priceflag = false;
		} else if (QcbCompanyAccountHistoryType.DIVIDEND.equals(cah.getType())) {
			this.typeCN = "收益";
			this.remark = "理财收益";
			this.priceflag = true;
		} else if (QcbCompanyAccountHistoryType.RETURN.equals(cah.getType())) {
			this.typeCN = "返还本金";
			this.remark = "返还本金";
			this.priceflag = true;
		} else if (QcbCompanyAccountHistoryType.PAYROLL.equals(cah.getType())) {
			this.typeCN = "福利发放";
			this.remark = "福利发放";
			this.priceflag = false;
		} else if (QcbCompanyAccountHistoryType.EXPEND.equals(cah.getType())) {
			this.typeCN = "支出";
			this.remark = "支出";
			this.priceflag = false;
		} else {
			this.typeCN = "";
		}
		
		this.companyAccount = cah.getCompanyAccount();
		
		if(!Utlity.checkStringNull(cah.getRemark())){
			this.remark = cah.getRemark();
		}
		
		this.createtime = cah.getCreatetime();
		this.createtimeCN = Utlity.timeSpanToString(cah.getCreatetime());
		
		this.product = cah.getProduct() == null ? "" : cah.getProduct();
		this.productCN = "";
		this.productType = cah.getProductType() == null ? "" : cah.getProductType();
		
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	
	public String getOrderNum() {
		return orderNum;
	}
	

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	


	public BigDecimal getAccountBalance() {
		return accountBalance;
	}
	

	public void setAccountBalance(BigDecimal accountBalance) {
		this.accountBalance = accountBalance;
	}
	

	public String getAccountBalanceCN() {
		return accountBalanceCN;
	}
	

	public void setAccountBalanceCN(String accountBalanceCN) {
		this.accountBalanceCN = accountBalanceCN;
	}
	

	public Boolean getPriceflag() {
		return priceflag;
	}
	

	public void setPriceflag(Boolean priceflag) {
		this.priceflag = priceflag;
	}
	

	public String getPrice() {
		return price;
	}
	

	public void setPrice(String price) {
		this.price = price;
	}
	

	public String getType() {
		return type;
	}
	

	public void setType(String type) {
		this.type = type;
	}
	

	public String getTypeCN() {
		return typeCN;
	}
	

	public void setTypeCN(String typeCN) {
		this.typeCN = typeCN;
	}
	

	public String getCompanyAccount() {
		return companyAccount;
	}
	

	public void setCompanyAccount(String companyAccount) {
		this.companyAccount = companyAccount;
	}
	

	public String getRemark() {
		return remark;
	}
	

	public void setRemark(String remark) {
		this.remark = remark;
	}
	

	public Timestamp getCreatetime() {
		return createtime;
	}
	

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	

	public String getCreatetimeCN() {
		return createtimeCN;
	}
	

	public void setCreatetimeCN(String createtimeCN) {
		this.createtimeCN = createtimeCN;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getProductCN() {
		return productCN;
	}

	public void setProductCN(String productCN) {
		this.productCN = productCN;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}
}
