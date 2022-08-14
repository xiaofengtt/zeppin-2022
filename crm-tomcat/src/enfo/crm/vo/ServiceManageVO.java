/*
 * �������� 2009-11-26
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
package enfo.crm.vo;

/**
 * @author enfo
 *
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
public class ServiceManageVO {
	
	private Integer serial_no;
	private Integer serviceType; //��������
	private String serviceTypeName;
	private Integer offsetDays; //��ƫ����
	private String serviceWay;	//����;��
	private String description; //������Ϣ
	private String noticeCaption; // ��ʾ����
	private Integer isValid; //�Ƿ���Ч
	private Integer executor;	
	private Integer autoFlag;
	private Integer tempID;
	private String tempTilte;
	private Integer inputMan;
	
	
	/**
	 * @return ���� autoFlag��
	 */
	public Integer getAutoFlag() {
		return autoFlag;
	}
	/**
	 * @param autoFlag Ҫ���õ� autoFlag��
	 */
	public void setAutoFlag(Integer autoFlag) {
		this.autoFlag = autoFlag;
	}
	/**
	 * @return ���� tempID��
	 */
	public Integer getTempID() {
		return tempID;
	}
	/**
	 * @param tempID Ҫ���õ� tempID��
	 */
	public void setTempID(Integer tempID) {
		this.tempID = tempID;
	}
	/**
	 * @return ���� tempTilte��
	 */
	public String getTempTilte() {
		return tempTilte;
	}
	/**
	 * @param tempTilte Ҫ���õ� tempTilte��
	 */
	public void setTempTilte(String tempTilte) {
		this.tempTilte = tempTilte;
	}
	
	
	/**
	 * @return
	 */
	public String getDescription() {
		return description;
	}


	/**
	 * @return
	 */
	public Integer getInputMan() {
		return inputMan;
	}

	/**
	 * @return
	 */
	public Integer getServiceType() {
		return serviceType;
	}

	/**
	 * @return
	 */
	public String getServiceTypeName() {
		return serviceTypeName;
	}

	/**
	 * @return
	 */
	public String getServiceWay() {
		return serviceWay;
	}

	/**
	 * @param string
	 */
	public void setDescription(String string) {
		description = string;
	}

	/**
	 * @param integer
	 */
	public void setInputMan(Integer integer) {
		inputMan = integer;
	}


	/**
	 * @param integer
	 */
	public void setServiceType(Integer integer) {
		serviceType = integer;
	}

	/**
	 * @param string
	 */
	public void setServiceTypeName(String string) {
		serviceTypeName = string;
	}

	/**
	 * @param string
	 */
	public void setServiceWay(String string) {
		serviceWay = string;
	}

	/**
	 * @return
	 */
	public Integer getIsValid() {
		return isValid;
	}

	/**
	 * @param integer
	 */
	public void setIsValid(Integer integer) {
		isValid = integer;
	}

	/**
	 * @return
	 */
	public Integer getSerial_no() {
		return serial_no;
	}

	/**
	 * @param integer
	 */
	public void setSerial_no(Integer integer) {
		serial_no = integer;
	}

	/**
	 * @return
	 */
	public Integer getOffsetDays() {
		return offsetDays;
	}

	/**
	 * @param integer
	 */
	public void setOffsetDays(Integer integer) {
		offsetDays = integer;
	}

	/**
	 * @return
	 */
	public String getNoticeCaption() {
		return noticeCaption;
	}

	/**
	 * @param string
	 */
	public void setNoticeCaption(String string) {
		noticeCaption = string;
	}

	/**
	 * @return
	 */
	public Integer getExecutor() {
		return executor;
	}

	/**
	 * @param integer
	 */
	public void setExecutor(Integer integer) {
		executor = integer;
	}

}
