/*
 * �������� 2013-4-11
 *
 * TODO Ҫ���Ĵ����ɵ��ļ���ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
package enfo.crm.vo;

import java.math.BigDecimal;

/**
 * @author Administrator
 *
 * TODO Ҫ���Ĵ����ɵ�����ע�͵�ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
public class ContractUnrealVO {
    private Integer serialNo;
	private Integer preproductId; // Ԥ���в�Ʒ
    
    private Integer custId;
    private Integer serviceMan; // �ͻ�����    
    private String contractSubBh; // ��ͬ��ʵ�ʱ��
    
    private BigDecimal rgMoney;
    
    private BigDecimal expectRorLower; // Ԥ������������
    private BigDecimal expectRorUpper;
    
    private Integer qsDate;
    private Integer jkDate;
    private String jkType; // 
    
    private Integer provFlag; // 1.���ȣ�2һ�㣬3�Ӻ�
    private String provLevel; // ���漶��1204��
    
    private String bankId; // ��������(1103)
    private String bankSubName; // ��������֧��
    private String bankAcct; // �����˻�
    private String bankAcctType;  // �����˻�����(9920)
    private String gainAcct; // �����˺Ż���
    
    private Integer inputMan;
    private String summary; // ��ע
    private Integer Status;
    
    private String preproductName;
    private String custName;
    private Integer qsDate1;
    private Integer qsDate2;
    
	/**
	 * @return ���� bankAcct��
	 */
	public String getBankAcct() {
		return bankAcct;
	}
	/**
	 * @param bankAcct Ҫ���õ� bankAcct��
	 */
	public void setBankAcct(String bankAcct) {
		this.bankAcct = bankAcct;
	}
	/**
	 * @return ���� bankAcctType��
	 */
	public String getBankAcctType() {
		return bankAcctType;
	}
	/**
	 * @param bankAcctType Ҫ���õ� bankAcctType��
	 */
	public void setBankAcctType(String bankAcctType) {
		this.bankAcctType = bankAcctType;
	}
	/**
	 * @return ���� bankId��
	 */
	public String getBankId() {
		return bankId;
	}
	/**
	 * @param bankId Ҫ���õ� bankId��
	 */
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	/**
	 * @return ���� bankSubName��
	 */
	public String getBankSubName() {
		return bankSubName;
	}
	/**
	 * @param bankSubName Ҫ���õ� bankSubName��
	 */
	public void setBankSubName(String bankSubName) {
		this.bankSubName = bankSubName;
	}
	/**
	 * @return ���� contractSubBh��
	 */
	public String getContractSubBh() {
		return contractSubBh;
	}
	/**
	 * @param contractSubBh Ҫ���õ� contractSubBh��
	 */
	public void setContractSubBh(String contractSubBh) {
		this.contractSubBh = contractSubBh;
	}
	/**
	 * @return ���� custId��
	 */
	public Integer getCustId() {
		return custId;
	}
	/**
	 * @param custId Ҫ���õ� custId��
	 */
	public void setCustId(Integer custId) {
		this.custId = custId;
	}
	/**
	 * @return ���� expectRorLower��
	 */
	public BigDecimal getExpectRorLower() {
		return expectRorLower;
	}
	/**
	 * @param expectRorLower Ҫ���õ� expectRorLower��
	 */
	public void setExpectRorLower(BigDecimal expectRorLower) {
		this.expectRorLower = expectRorLower;
	}
	/**
	 * @return ���� expectRorUpper��
	 */
	public BigDecimal getExpectRorUpper() {
		return expectRorUpper;
	}
	/**
	 * @param expectRorUpper Ҫ���õ� expectRorUpper��
	 */
	public void setExpectRorUpper(BigDecimal expectRorUpper) {
		this.expectRorUpper = expectRorUpper;
	}
	/**
	 * @return ���� gainAcct��
	 */
	public String getGainAcct() {
		return gainAcct;
	}
	/**
	 * @param gainAcct Ҫ���õ� gainAcct��
	 */
	public void setGainAcct(String gainAcct) {
		this.gainAcct = gainAcct;
	}
	/**
	 * @return ���� inputMan��
	 */
	public Integer getInputMan() {
		return inputMan;
	}
	/**
	 * @param inputMan Ҫ���õ� inputMan��
	 */
	public void setInputMan(Integer inputMan) {
		this.inputMan = inputMan;
	}
	/**
	 * @return ���� jkDate��
	 */
	public Integer getJkDate() {
		return jkDate;
	}
	/**
	 * @param jkDate Ҫ���õ� jkDate��
	 */
	public void setJkDate(Integer jkDate) {
		this.jkDate = jkDate;
	}
	/**
	 * @return ���� jkType��
	 */
	public String getJkType() {
		return jkType;
	}
	/**
	 * @param jkType Ҫ���õ� jkType��
	 */
	public void setJkType(String jkType) {
		this.jkType = jkType;
	}
	/**
	 * @return ���� preproductId��
	 */
	public Integer getPreproductId() {
		return preproductId;
	}
	/**
	 * @param preproductId Ҫ���õ� preproductId��
	 */
	public void setPreproductId(Integer preproductId) {
		this.preproductId = preproductId;
	}
	/**
	 * @return ���� provFlag��
	 */
	public Integer getProvFlag() {
		return provFlag;
	}
	/**
	 * @param provFlag Ҫ���õ� provFlag��
	 */
	public void setProvFlag(Integer provFlag) {
		this.provFlag = provFlag;
	}
	/**
	 * @return ���� provLevel��
	 */
	public String getProvLevel() {
		return provLevel;
	}
	/**
	 * @param provLevel Ҫ���õ� provLevel��
	 */
	public void setProvLevel(String provLevel) {
		this.provLevel = provLevel;
	}
	/**
	 * @return ���� qsDate��
	 */
	public Integer getQsDate() {
		return qsDate;
	}
	/**
	 * @param qsDate Ҫ���õ� qsDate��
	 */
	public void setQsDate(Integer qsDate) {
		this.qsDate = qsDate;
	}
	/**
	 * @return ���� rgMoney��
	 */
	public BigDecimal getRgMoney() {
		return rgMoney;
	}
	/**
	 * @param rgMoney Ҫ���õ� rgMoney��
	 */
	public void setRgMoney(BigDecimal rgMoney) {
		this.rgMoney = rgMoney;
	}
	/**
	 * @return ���� serialNo��
	 */
	public Integer getSerialNo() {
		return serialNo;
	}
	/**
	 * @param serialNo Ҫ���õ� serialNo��
	 */
	public void setSerialNo(Integer serialNo) {
		this.serialNo = serialNo;
	}
	/**
	 * @return ���� serviceMan��
	 */
	public Integer getServiceMan() {
		return serviceMan;
	}
	/**
	 * @param serviceMan Ҫ���õ� serviceMan��
	 */
	public void setServiceMan(Integer serviceMan) {
		this.serviceMan = serviceMan;
	}
	/**
	 * @return ���� summary��
	 */
	public String getSummary() {
		return summary;
	}
	/**
	 * @param summary Ҫ���õ� summary��
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}        
    
	/**
	 * @return ���� status��
	 */
	public Integer getStatus() {
		return Status;
	}
	/**
	 * @param status Ҫ���õ� status��
	 */
	public void setStatus(Integer status) {
		Status = status;
	}
	/**
	 * @return ���� custName��
	 */
	public String getCustName() {
		return custName;
	}
	/**
	 * @param custName Ҫ���õ� custName��
	 */
	public void setCustName(String custName) {
		this.custName = custName;
	}
	/**
	 * @return ���� preproductName��
	 */
	public String getPreproductName() {
		return preproductName;
	}
	/**
	 * @param preproductName Ҫ���õ� preproductName��
	 */
	public void setPreproductName(String preproductName) {
		this.preproductName = preproductName;
	}
	/**
	 * @return ���� qsDate1��
	 */
	public Integer getQsDate1() {
		return qsDate1;
	}
	/**
	 * @param qsDate1 Ҫ���õ� qsDate1��
	 */
	public void setQsDate1(Integer qsDate1) {
		this.qsDate1 = qsDate1;
	}
	/**
	 * @return ���� qsDate2��
	 */
	public Integer getQsDate2() {
		return qsDate2;
	}
	/**
	 * @param qsDate2 Ҫ���õ� qsDate2��
	 */
	public void setQsDate2(Integer qsDate2) {
		this.qsDate2 = qsDate2;
	}
}
