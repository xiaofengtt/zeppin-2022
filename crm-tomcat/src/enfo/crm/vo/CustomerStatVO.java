/*
 * �������� 2010-3-12
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
package enfo.crm.vo;

/**
 * @author taochen
 *
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
public class CustomerStatVO {
	private Integer inputMan;
	private Integer cust_type;
	private Integer func_id;	
	private String productCode;
	private Integer productId;
	private Integer selectFlag;
	private String orderBy;
	private Integer groupId;
	private Integer classDetailId;
	private Integer changFlag;
	private Integer statScope; // ͳ������ȡ�Եķ�Χ��0ȫ����1���ˣ�2������
	private Integer rgDateStart;	
	private Integer rgDateEnd;	
	
	/**
	 * @return ���� rgDateEnd��
	 */
	public Integer getRgDateEnd() {
		return rgDateEnd;
	}
	/**
	 * @param rgDateEnd Ҫ���õ� rgDateEnd��
	 */
	public void setRgDateEnd(Integer rgDateEnd) {
		this.rgDateEnd = rgDateEnd;
	}
	/**
	 * @return ���� rgDateStart��
	 */
	public Integer getRgDateStart() {
		return rgDateStart;
	}
	/**
	 * @param rgDateStart Ҫ���õ� rgDateStart��
	 */
	public void setRgDateStart(Integer rgDateStart) {
		this.rgDateStart = rgDateStart;
	}
	/**
	 * @return ���� statScope��
	 */
	public Integer getStatScope() {
		return statScope;
	}
	/**
	 * @param statScope Ҫ���õ� statScope��
	 */
	public void setStatScope(Integer statScope) {
		this.statScope = statScope;
	}
	public Integer getChangFlag() {
		return changFlag;
	}

	public void setChangFlag(Integer changFlag) {
		this.changFlag = changFlag;
	}

	private Integer startTime;
	private Integer endTime;

	public Integer getStartTime() {
		return startTime;
	}

	public void setStartTime(Integer startTime) {
		this.startTime = startTime;
	}

	public Integer getEndTime() {
		return endTime;
	}

	public void setEndTime(Integer endTime) {
		this.endTime = endTime;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public Integer getSelectFlag() {
		return selectFlag;
	}

	public void setSelectFlag(Integer selectFlag) {
		this.selectFlag = selectFlag;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public Integer getClassDetailId() {
		return classDetailId;
	}

	public void setClassDetailId(Integer classDetailId) {
		this.classDetailId = classDetailId;
	}

	/**
	 * @return
	 */
	public Integer getCust_type() {
		return cust_type;
	}

	/**
	 * @return
	 */
	public Integer getInputMan() {
		return inputMan;
	}

	/**
	 * @param integer
	 */
	public void setCust_type(Integer integer) {
		cust_type = integer;
	}

	/**
	 * @param integer
	 */
	public void setInputMan(Integer integer) {
		inputMan = integer;
	}

	/**
	 * @return
	 */
	public Integer getFunc_id() {
		return func_id;
	}

	/**
	 * @param integer
	 */
	public void setFunc_id(Integer integer) {
		func_id = integer;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

}
