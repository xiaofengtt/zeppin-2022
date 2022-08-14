package cn.zeppin.product.ntb.qcb.vo;

import java.sql.Timestamp;
import java.util.List;

import cn.zeppin.product.ntb.core.entity.QcbCompanyBankcard;
import cn.zeppin.product.ntb.core.entity.QcbCompanyBankcard.QcbCompanyBankcardType;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.utility.Utlity;

public class QcbCompanyBankcardVO implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7063644119578448205L;
	
	private String uuid;
	private String qcbCompany;
	private String qcbCompanyName;
	private String bank;
	private String bankName;
	private String bankIconUrl;
	private String branchBank;
	private String branchBankName;
	private String bkArea;
	private List<String> bkAreaName;
	
	private String bindingCardCardholder;
	private String bindingBankCard;
	private String bindingCardPhone;
	private String bindingCardType;
	private String bindingCardTypeCN;
	
	private String status;
	private String creator;
	private Timestamp createtime;
	private String createtimeCN;
	
 	public QcbCompanyBankcardVO(){
		
	}
	
	public QcbCompanyBankcardVO(QcbCompanyBankcard qcb){
		this.uuid = qcb.getUuid();
		this.qcbCompany = qcb.getQcbCompany();
		this.bank = qcb.getBank();
		this.branchBank = qcb.getBranchBank();
		this.bkArea = qcb.getBkArea();
		this.bindingCardCardholder = qcb.getBindingCardCardholder();
		this.bindingBankCard = qcb.getBindingBankCard();
		this.bindingCardPhone = qcb.getBindingCardPhone();
		this.bindingCardType = qcb.getBindingCardType();
		if(QcbCompanyBankcardType.COMPANY.equals(qcb.getBindingCardType())){
			this.bindingCardTypeCN = "基本户";
		}else if(QcbCompanyBankcardType.PERSONAL.equals(qcb.getBindingCardType())){
			this.bindingCardTypeCN = "一般户";
		}
		this.status = qcb.getStatus();
		this.createtime = qcb.getCreatetime();
		this.createtimeCN = Utlity.timeSpanToString(qcb.getCreatetime());
		this.creator = qcb.getCreator();
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getQcbCompany() {
		return qcbCompany;
	}

	public void setQcbCompany(String qcbCompany) {
		this.qcbCompany = qcbCompany;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getBranchBank() {
		return branchBank;
	}

	public void setBranchBank(String branchBank) {
		this.branchBank = branchBank;
	}

	public String getBkArea() {
		return bkArea;
	}

	public void setBkArea(String bkArea) {
		this.bkArea = bkArea;
	}

	public String getBindingCardCardholder() {
		return bindingCardCardholder;
	}

	public void setBindingCardCardholder(String bindingCardCardholder) {
		this.bindingCardCardholder = bindingCardCardholder;
	}

	public String getBindingBankCard() {
		return bindingBankCard;
	}

	public void setBindingBankCard(String bindingBankCard) {
		this.bindingBankCard = bindingBankCard;
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
	
	public String getCreatetimeCN() {
		return createtimeCN;
	}

	public void setCreatetimeCN(String createtimeCN) {
		this.createtimeCN = createtimeCN;
	}
	
	public String getBindingCardPhone() {
		return bindingCardPhone;
	}

	public void setBindingCardPhone(String bindingCardPhone) {
		this.bindingCardPhone = bindingCardPhone;
	}

	public String getBindingCardType() {
		return bindingCardType;
	}

	public void setBindingCardType(String bindingCardType) {
		this.bindingCardType = bindingCardType;
	}

	public String getBindingCardTypeCN() {
		return bindingCardTypeCN;
	}

	public void setBindingCardTypeCN(String bindingCardTypeCN) {
		this.bindingCardTypeCN = bindingCardTypeCN;
	}

	public String getQcbCompanyName() {
		return qcbCompanyName;
	}

	public void setQcbCompanyName(String qcbCompanyName) {
		this.qcbCompanyName = qcbCompanyName;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankIconUrl() {
		return bankIconUrl;
	}

	public void setBankIconUrl(String bankIconUrl) {
		this.bankIconUrl = bankIconUrl;
	}

	public String getBranchBankName() {
		return branchBankName;
	}

	public void setBranchBankName(String branchBankName) {
		this.branchBankName = branchBankName;
	}

	public List<String> getBkAreaName() {
		return bkAreaName;
	}

	public void setBkAreaName(List<String> bkAreaName) {
		this.bkAreaName = bkAreaName;
	}
	
}
