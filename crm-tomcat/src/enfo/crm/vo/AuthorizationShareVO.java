/*
 * �������� 2011-05-21
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
package enfo.crm.vo;

/**
 * @author wanghf
 *
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
public class AuthorizationShareVO {
	private Integer serial_no;
	private Integer shareType;
	private String  shareDescription;
	private Integer status;
	private Integer sourceManagerID;
	private Integer ca_id;
	private Integer managerID;
	private Integer input_man;
	private Integer cust_id;
	private String  invalid_time;
	private String  start_time;
	
	/**
	 * @return ���� ca_id��
	 */
	public Integer getCa_id() {
		return ca_id;
	}
	/**
	 * @param ca_id Ҫ���õ� ca_id��
	 */
	public void setCa_id(Integer ca_id) {
		this.ca_id = ca_id;
	}
	/**
	 * @return ���� input_man��
	 */
	public Integer getInput_man() {
		return input_man;
	}
	/**
	 * @param input_man Ҫ���õ� input_man��
	 */
	public void setInput_man(Integer input_man) {
		this.input_man = input_man;
	}
	/**
	 * @return ���� managerID��
	 */
	public Integer getManagerID() {
		return managerID;
	}
	/**
	 * @param managerID Ҫ���õ� managerID��
	 */
	public void setManagerID(Integer managerID) {
		this.managerID = managerID;
	}
	/**
	 * @return ���� serial_no��
	 */
	public Integer getSerial_no() {
		return serial_no;
	}
	/**
	 * @param serial_no Ҫ���õ� serial_no��
	 */
	public void setSerial_no(Integer serial_no) {
		this.serial_no = serial_no;
	}
	/**
	 * @return ���� shareDescription��
	 */
	public String getShareDescription() {
		return shareDescription;
	}
	/**
	 * @param shareDescription Ҫ���õ� shareDescription��
	 */
	public void setShareDescription(String shareDescription) {
		this.shareDescription = shareDescription;
	}
	/**
	 * @return ���� shareType��
	 */
	public Integer getShareType() {
		return shareType;
	}
	/**
	 * @param shareType Ҫ���õ� shareType��
	 */
	public void setShareType(Integer shareType) {
		this.shareType = shareType;
	}
	/**
	 * @return ���� sourceManagerID��
	 */
	public Integer getSourceManagerID() {
		return sourceManagerID;
	}
	/**
	 * @param sourceManagerID Ҫ���õ� sourceManagerID��
	 */
	public void setSourceManagerID(Integer sourceManagerID) {
		this.sourceManagerID = sourceManagerID;
	}
	/**
	 * @return ���� status��
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * @param status Ҫ���õ� status��
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
    /**
     * @return ���� cust_id��
     */
    public Integer getCust_id() {
        return cust_id;
    }
    /**
     * @param cust_id Ҫ���õ� cust_id��
     */
    public void setCust_id(Integer cust_id) {
        this.cust_id = cust_id;
    }
    /**
     * @return ���� invalid_time��
     */
    public String getInvalid_time() {
        return invalid_time;
    }
    /**
     * @param invalid_time Ҫ���õ� invalid_time��
     */
    public void setInvalid_time(String invalid_time) {
        this.invalid_time = invalid_time;
    }
    /**
     * @return ���� start_time��
     */
    public String getStart_time() {
        return start_time;
    }
    /**
     * @param start_time Ҫ���õ� start_time��
     */
    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }
}
