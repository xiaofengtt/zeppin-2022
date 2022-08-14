/*
 * 创建日期 2015-11-19
 */
package enfo.crm.cash;

/**
 * @author Administrator
 */
public class CashVo {
	private Integer productId;//产品ID
	private Integer subProductId;//子产品ID
	private Integer custId;//客户ID
	private Integer beginDate;//产品开始日期
	private Integer endDate;//产品结束日期（产品查询日期）
	private Integer inputMan;//操作员
	private String benAccount;//收益账号
	
	private String custNo;//客户编号
	private String custName;//客户名
	private String cardId;//身份证号
	private Integer custType;//客户类型 1 - 个人  2 - 机构
	private String custTel;//客户电话
	private String address;//客户地址
	private Integer servicemen;//客户经理
	
	private String contractBH;//合同编号
	private Integer checkFlag;//审核标志 1 - 未审核  2 - 已审核
	private Integer serialNo;//赎回记录ID
	/**
	 * @return 返回 address。
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address 要设置的 address。
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return 返回 beginDate。
	 */
	public Integer getBeginDate() {
		return beginDate;
	}
	/**
	 * @param beginDate 要设置的 beginDate。
	 */
	public void setBeginDate(Integer beginDate) {
		this.beginDate = beginDate;
	}
	/**
	 * @return 返回 cardId。
	 */
	public String getCardId() {
		return cardId;
	}
	/**
	 * @param cardId 要设置的 cardId。
	 */
	public void setCardId(String cardId) {
		this.cardId = cardId;
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
	 * @return 返回 custNo。
	 */
	public String getCustNo() {
		return custNo;
	}
	/**
	 * @param custNo 要设置的 custNo。
	 */
	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}
	/**
	 * @return 返回 custTel。
	 */
	public String getCustTel() {
		return custTel;
	}
	/**
	 * @param custTel 要设置的 custTel。
	 */
	public void setCustTel(String custTel) {
		this.custTel = custTel;
	}
	/**
	 * @return 返回 custType。
	 */
	public Integer getCustType() {
		return custType;
	}
	/**
	 * @param custType 要设置的 custType。
	 */
	public void setCustType(Integer custType) {
		this.custType = custType;
	}
	/**
	 * @return 返回 endDate。
	 */
	public Integer getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate 要设置的 endDate。
	 */
	public void setEndDate(Integer endDate) {
		this.endDate = endDate;
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
	 * @return 返回 productId。
	 */
	public Integer getProductId() {
		return productId;
	}
	/**
	 * @param productId 要设置的 productId。
	 */
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	/**
	 * @return 返回 servicemen。
	 */
	public Integer getServicemen() {
		return servicemen;
	}
	/**
	 * @param servicemen 要设置的 servicemen。
	 */
	public void setServicemen(Integer servicemen) {
		this.servicemen = servicemen;
	}
	/**
	 * @return 返回 subProductId。
	 */
	public Integer getSubProductId() {
		return subProductId;
	}
	/**
	 * @param subProductId 要设置的 subProductId。
	 */
	public void setSubProductId(Integer subProductId) {
		this.subProductId = subProductId;
	}
	
	
	/**
	 * @return 返回 benAccount。
	 */
	public String getBenAccount() {
		return benAccount;
	}
	/**
	 * @param benAccount 要设置的 benAccount。
	 */
	public void setBenAccount(String benAccount) {
		this.benAccount = benAccount;
	}
	/**
	 * @return 返回 checkFlag。
	 */
	public Integer getCheckFlag() {
		return checkFlag;
	}
	/**
	 * @param checkFlag 要设置的 checkFlag。
	 */
	public void setCheckFlag(Integer checkFlag) {
		this.checkFlag = checkFlag;
	}
	/**
	 * @return 返回 contractBH。
	 */
	public String getContractBH() {
		return contractBH;
	}
	/**
	 * @param contractBH 要设置的 contractBH。
	 */
	public void setContractBH(String contractBH) {
		this.contractBH = contractBH;
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
}
