package cn.zeppin.product.ntb.qcb.vo;

import java.math.BigDecimal;
import java.sql.Timestamp;

import cn.zeppin.product.ntb.core.entity.QcbCompanyAccountHistory;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccountHistory.QcbCompanyAccountHistoryType;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.utility.Utlity;

public class QcbCompanyTransferVO implements Entity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4740659141418123237L;
	
	private String uuid;
	private String orderNum;
	
	private Boolean priceflag;
	private String price;
	private String poundage;
	
	private String type;
	private String typeCN;
	
	private String companyAccount;
	private String companyAccountName;
	private String companyAccountNum;
	
	private String qcbCompanyId;
	private String qcbCompanyBankcard;
	private String qcbCompanyBankcardNum;
	private String qcbCompanyBankcardName;
	private String qcbCompanyBankcardAddress;
	
	private Timestamp createtime;
	private String createtimeCN;
	private String reachtimeCN;
	
	private String creator;
	private String creatorName;
	
	public QcbCompanyTransferVO(){
		
	}
	
	public QcbCompanyTransferVO(QcbCompanyAccountHistory cah){
		this.uuid = cah.getUuid();
		this.orderNum = cah.getOrderNum();
		
		
		if(cah.getIncome().compareTo(BigDecimal.valueOf(0)) == 1){
			this.price = Utlity.numFormat4UnitDetail(cah.getIncome());
			
		} else if (cah.getPay().compareTo(BigDecimal.valueOf(0)) == 1) {
			
			this.price = Utlity.numFormat4UnitDetail(cah.getPay());
		} else {
			this.price = "0.00";
		}
		this.poundage = Utlity.numFormat4UnitDetail(cah.getPoundage());
		
		this.priceflag = true;
		this.type = cah.getType();
		if(QcbCompanyAccountHistoryType.WITHDRAW.equals(cah.getType())){
			this.typeCN = "提现";
			this.priceflag = false;
		} else if (QcbCompanyAccountHistoryType.INCOME.equals(cah.getType())) {
			this.typeCN = "充值";
			this.priceflag = true;
		} else if (QcbCompanyAccountHistoryType.BUY.equals(cah.getType())) {
			this.typeCN = "投资";
			this.priceflag = false;
		} else if (QcbCompanyAccountHistoryType.DIVIDEND.equals(cah.getType())) {
			this.typeCN = "收益";
			this.priceflag = true;
		} else if (QcbCompanyAccountHistoryType.RETURN.equals(cah.getType())) {
			this.typeCN = "返还本金";
			this.priceflag = true;
		} else if (QcbCompanyAccountHistoryType.PAYROLL.equals(cah.getType())) {
			this.typeCN = "福利发放";
			this.priceflag = false;
		} else if (QcbCompanyAccountHistoryType.EXPEND.equals(cah.getType())) {
			this.typeCN = "支出";
			this.priceflag = false;
		} else {
			this.typeCN = "";
		}
		
		this.companyAccount = cah.getCompanyAccount();
		this.qcbCompanyBankcard = cah.getBankcard();
		
		this.createtime = cah.getCreatetime();
		this.createtimeCN = Utlity.timeSpanToChinaString(cah.getCreatetime());
		
		Timestamp reachtime = new Timestamp(cah.getCreatetime().getTime() + 86400000);
		this.reachtimeCN = Utlity.timeSpanToChinaString(reachtime);
		this.creator = cah.getCreator();
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

	public String getPoundage() {
		return poundage;
	}

	public void setPoundage(String poundage) {
		this.poundage = poundage;
	}

	public String getCompanyAccountName() {
		return companyAccountName;
	}

	public void setCompanyAccountName(String companyAccountName) {
		this.companyAccountName = companyAccountName;
	}

	public String getCompanyAccountNum() {
		return companyAccountNum;
	}

	public void setCompanyAccountNum(String companyAccountNum) {
		this.companyAccountNum = companyAccountNum;
	}

	public String getQcbCompanyId() {
		return qcbCompanyId;
	}

	public void setQcbCompanyId(String qcbCompanyId) {
		this.qcbCompanyId = qcbCompanyId;
	}

	public String getQcbCompanyBankcard() {
		return qcbCompanyBankcard;
	}

	public void setQcbCompanyBankcard(String qcbCompanyBankcard) {
		this.qcbCompanyBankcard = qcbCompanyBankcard;
	}

	public String getQcbCompanyBankcardNum() {
		return qcbCompanyBankcardNum;
	}

	public void setQcbCompanyBankcardNum(String qcbCompanyBankcardNum) {
		this.qcbCompanyBankcardNum = qcbCompanyBankcardNum;
	}

	public String getQcbCompanyBankcardName() {
		return qcbCompanyBankcardName;
	}

	public void setQcbCompanyBankcardName(String qcbCompanyBankcardName) {
		this.qcbCompanyBankcardName = qcbCompanyBankcardName;
	}

	public String getQcbCompanyBankcardAddress() {
		return qcbCompanyBankcardAddress;
	}

	public void setQcbCompanyBankcardAddress(String qcbCompanyBankcardAddress) {
		this.qcbCompanyBankcardAddress = qcbCompanyBankcardAddress;
	}

	public String getReachtimeCN() {
		return reachtimeCN;
	}

	public void setReachtimeCN(String reachtimeCN) {
		this.reachtimeCN = reachtimeCN;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
}
