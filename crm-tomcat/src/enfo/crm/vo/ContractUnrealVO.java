/*
 * 创建日期 2013-4-11
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
package enfo.crm.vo;

import java.math.BigDecimal;

/**
 * @author Administrator
 *
 * TODO 要更改此生成的类型注释的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
public class ContractUnrealVO {
    private Integer serialNo;
	private Integer preproductId; // 预发行产品
    
    private Integer custId;
    private Integer serviceMan; // 客户经理    
    private String contractSubBh; // 合同的实际编号
    
    private BigDecimal rgMoney;
    
    private BigDecimal expectRorLower; // 预期收益率区间
    private BigDecimal expectRorUpper;
    
    private Integer qsDate;
    private Integer jkDate;
    private String jkType; // 
    
    private Integer provFlag; // 1.优先，2一般，3劣后
    private String provLevel; // 收益级别（1204）
    
    private String bankId; // 受益银行(1103)
    private String bankSubName; // 受益银行支行
    private String bankAcct; // 银行账户
    private String bankAcctType;  // 银行账户类型(9920)
    private String gainAcct; // 银行账号户名
    
    private Integer inputMan;
    private String summary; // 备注
    private Integer Status;
    
    private String preproductName;
    private String custName;
    private Integer qsDate1;
    private Integer qsDate2;
    
	/**
	 * @return 返回 bankAcct。
	 */
	public String getBankAcct() {
		return bankAcct;
	}
	/**
	 * @param bankAcct 要设置的 bankAcct。
	 */
	public void setBankAcct(String bankAcct) {
		this.bankAcct = bankAcct;
	}
	/**
	 * @return 返回 bankAcctType。
	 */
	public String getBankAcctType() {
		return bankAcctType;
	}
	/**
	 * @param bankAcctType 要设置的 bankAcctType。
	 */
	public void setBankAcctType(String bankAcctType) {
		this.bankAcctType = bankAcctType;
	}
	/**
	 * @return 返回 bankId。
	 */
	public String getBankId() {
		return bankId;
	}
	/**
	 * @param bankId 要设置的 bankId。
	 */
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	/**
	 * @return 返回 bankSubName。
	 */
	public String getBankSubName() {
		return bankSubName;
	}
	/**
	 * @param bankSubName 要设置的 bankSubName。
	 */
	public void setBankSubName(String bankSubName) {
		this.bankSubName = bankSubName;
	}
	/**
	 * @return 返回 contractSubBh。
	 */
	public String getContractSubBh() {
		return contractSubBh;
	}
	/**
	 * @param contractSubBh 要设置的 contractSubBh。
	 */
	public void setContractSubBh(String contractSubBh) {
		this.contractSubBh = contractSubBh;
	}
	/**
	 * @return 返回 custId。
	 */
	public Integer getCustId() {
		return custId;
	}
	/**
	 * @param custId 要设置的 custId。
	 */
	public void setCustId(Integer custId) {
		this.custId = custId;
	}
	/**
	 * @return 返回 expectRorLower。
	 */
	public BigDecimal getExpectRorLower() {
		return expectRorLower;
	}
	/**
	 * @param expectRorLower 要设置的 expectRorLower。
	 */
	public void setExpectRorLower(BigDecimal expectRorLower) {
		this.expectRorLower = expectRorLower;
	}
	/**
	 * @return 返回 expectRorUpper。
	 */
	public BigDecimal getExpectRorUpper() {
		return expectRorUpper;
	}
	/**
	 * @param expectRorUpper 要设置的 expectRorUpper。
	 */
	public void setExpectRorUpper(BigDecimal expectRorUpper) {
		this.expectRorUpper = expectRorUpper;
	}
	/**
	 * @return 返回 gainAcct。
	 */
	public String getGainAcct() {
		return gainAcct;
	}
	/**
	 * @param gainAcct 要设置的 gainAcct。
	 */
	public void setGainAcct(String gainAcct) {
		this.gainAcct = gainAcct;
	}
	/**
	 * @return 返回 inputMan。
	 */
	public Integer getInputMan() {
		return inputMan;
	}
	/**
	 * @param inputMan 要设置的 inputMan。
	 */
	public void setInputMan(Integer inputMan) {
		this.inputMan = inputMan;
	}
	/**
	 * @return 返回 jkDate。
	 */
	public Integer getJkDate() {
		return jkDate;
	}
	/**
	 * @param jkDate 要设置的 jkDate。
	 */
	public void setJkDate(Integer jkDate) {
		this.jkDate = jkDate;
	}
	/**
	 * @return 返回 jkType。
	 */
	public String getJkType() {
		return jkType;
	}
	/**
	 * @param jkType 要设置的 jkType。
	 */
	public void setJkType(String jkType) {
		this.jkType = jkType;
	}
	/**
	 * @return 返回 preproductId。
	 */
	public Integer getPreproductId() {
		return preproductId;
	}
	/**
	 * @param preproductId 要设置的 preproductId。
	 */
	public void setPreproductId(Integer preproductId) {
		this.preproductId = preproductId;
	}
	/**
	 * @return 返回 provFlag。
	 */
	public Integer getProvFlag() {
		return provFlag;
	}
	/**
	 * @param provFlag 要设置的 provFlag。
	 */
	public void setProvFlag(Integer provFlag) {
		this.provFlag = provFlag;
	}
	/**
	 * @return 返回 provLevel。
	 */
	public String getProvLevel() {
		return provLevel;
	}
	/**
	 * @param provLevel 要设置的 provLevel。
	 */
	public void setProvLevel(String provLevel) {
		this.provLevel = provLevel;
	}
	/**
	 * @return 返回 qsDate。
	 */
	public Integer getQsDate() {
		return qsDate;
	}
	/**
	 * @param qsDate 要设置的 qsDate。
	 */
	public void setQsDate(Integer qsDate) {
		this.qsDate = qsDate;
	}
	/**
	 * @return 返回 rgMoney。
	 */
	public BigDecimal getRgMoney() {
		return rgMoney;
	}
	/**
	 * @param rgMoney 要设置的 rgMoney。
	 */
	public void setRgMoney(BigDecimal rgMoney) {
		this.rgMoney = rgMoney;
	}
	/**
	 * @return 返回 serialNo。
	 */
	public Integer getSerialNo() {
		return serialNo;
	}
	/**
	 * @param serialNo 要设置的 serialNo。
	 */
	public void setSerialNo(Integer serialNo) {
		this.serialNo = serialNo;
	}
	/**
	 * @return 返回 serviceMan。
	 */
	public Integer getServiceMan() {
		return serviceMan;
	}
	/**
	 * @param serviceMan 要设置的 serviceMan。
	 */
	public void setServiceMan(Integer serviceMan) {
		this.serviceMan = serviceMan;
	}
	/**
	 * @return 返回 summary。
	 */
	public String getSummary() {
		return summary;
	}
	/**
	 * @param summary 要设置的 summary。
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}        
    
	/**
	 * @return 返回 status。
	 */
	public Integer getStatus() {
		return Status;
	}
	/**
	 * @param status 要设置的 status。
	 */
	public void setStatus(Integer status) {
		Status = status;
	}
	/**
	 * @return 返回 custName。
	 */
	public String getCustName() {
		return custName;
	}
	/**
	 * @param custName 要设置的 custName。
	 */
	public void setCustName(String custName) {
		this.custName = custName;
	}
	/**
	 * @return 返回 preproductName。
	 */
	public String getPreproductName() {
		return preproductName;
	}
	/**
	 * @param preproductName 要设置的 preproductName。
	 */
	public void setPreproductName(String preproductName) {
		this.preproductName = preproductName;
	}
	/**
	 * @return 返回 qsDate1。
	 */
	public Integer getQsDate1() {
		return qsDate1;
	}
	/**
	 * @param qsDate1 要设置的 qsDate1。
	 */
	public void setQsDate1(Integer qsDate1) {
		this.qsDate1 = qsDate1;
	}
	/**
	 * @return 返回 qsDate2。
	 */
	public Integer getQsDate2() {
		return qsDate2;
	}
	/**
	 * @param qsDate2 要设置的 qsDate2。
	 */
	public void setQsDate2(Integer qsDate2) {
		this.qsDate2 = qsDate2;
	}
}
