/*
 * �������� 2015-11-19
 */
package enfo.crm.cash;

/**
 * @author Administrator
 */
public class CashVo {
	private Integer productId;//��ƷID
	private Integer subProductId;//�Ӳ�ƷID
	private Integer custId;//�ͻ�ID
	private Integer beginDate;//��Ʒ��ʼ����
	private Integer endDate;//��Ʒ�������ڣ���Ʒ��ѯ���ڣ�
	private Integer inputMan;//����Ա
	private String benAccount;//�����˺�
	
	private String custNo;//�ͻ����
	private String custName;//�ͻ���
	private String cardId;//���֤��
	private Integer custType;//�ͻ����� 1 - ����  2 - ����
	private String custTel;//�ͻ��绰
	private String address;//�ͻ���ַ
	private Integer servicemen;//�ͻ�����
	
	private String contractBH;//��ͬ���
	private Integer checkFlag;//��˱�־ 1 - δ���  2 - �����
	private Integer serialNo;//��ؼ�¼ID
	/**
	 * @return ���� address��
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address Ҫ���õ� address��
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return ���� beginDate��
	 */
	public Integer getBeginDate() {
		return beginDate;
	}
	/**
	 * @param beginDate Ҫ���õ� beginDate��
	 */
	public void setBeginDate(Integer beginDate) {
		this.beginDate = beginDate;
	}
	/**
	 * @return ���� cardId��
	 */
	public String getCardId() {
		return cardId;
	}
	/**
	 * @param cardId Ҫ���õ� cardId��
	 */
	public void setCardId(String cardId) {
		this.cardId = cardId;
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
	 * @return ���� custNo��
	 */
	public String getCustNo() {
		return custNo;
	}
	/**
	 * @param custNo Ҫ���õ� custNo��
	 */
	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}
	/**
	 * @return ���� custTel��
	 */
	public String getCustTel() {
		return custTel;
	}
	/**
	 * @param custTel Ҫ���õ� custTel��
	 */
	public void setCustTel(String custTel) {
		this.custTel = custTel;
	}
	/**
	 * @return ���� custType��
	 */
	public Integer getCustType() {
		return custType;
	}
	/**
	 * @param custType Ҫ���õ� custType��
	 */
	public void setCustType(Integer custType) {
		this.custType = custType;
	}
	/**
	 * @return ���� endDate��
	 */
	public Integer getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate Ҫ���õ� endDate��
	 */
	public void setEndDate(Integer endDate) {
		this.endDate = endDate;
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
	 * @return ���� productId��
	 */
	public Integer getProductId() {
		return productId;
	}
	/**
	 * @param productId Ҫ���õ� productId��
	 */
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	/**
	 * @return ���� servicemen��
	 */
	public Integer getServicemen() {
		return servicemen;
	}
	/**
	 * @param servicemen Ҫ���õ� servicemen��
	 */
	public void setServicemen(Integer servicemen) {
		this.servicemen = servicemen;
	}
	/**
	 * @return ���� subProductId��
	 */
	public Integer getSubProductId() {
		return subProductId;
	}
	/**
	 * @param subProductId Ҫ���õ� subProductId��
	 */
	public void setSubProductId(Integer subProductId) {
		this.subProductId = subProductId;
	}
	
	
	/**
	 * @return ���� benAccount��
	 */
	public String getBenAccount() {
		return benAccount;
	}
	/**
	 * @param benAccount Ҫ���õ� benAccount��
	 */
	public void setBenAccount(String benAccount) {
		this.benAccount = benAccount;
	}
	/**
	 * @return ���� checkFlag��
	 */
	public Integer getCheckFlag() {
		return checkFlag;
	}
	/**
	 * @param checkFlag Ҫ���õ� checkFlag��
	 */
	public void setCheckFlag(Integer checkFlag) {
		this.checkFlag = checkFlag;
	}
	/**
	 * @return ���� contractBH��
	 */
	public String getContractBH() {
		return contractBH;
	}
	/**
	 * @param contractBH Ҫ���õ� contractBH��
	 */
	public void setContractBH(String contractBH) {
		this.contractBH = contractBH;
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
}
