/*
 * �������� 2012-3-19
 *
 * TODO Ҫ���Ĵ����ɵ��ļ���ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
package enfo.crm.vo;

import java.math.BigDecimal;

/**
 * @author Administrator
 * 
 * TODO Ҫ���Ĵ����ɵ�����ע�͵�ģ�壬��ת�� ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
public class PreDoubleRecordInfoVO {

	private Integer serial_no; //���

	private Integer pre_serial_no; //ԤԼ������(EFCRM..TPRECONTRACT.SERIAL_NO)

	private Integer dz_date; //��������

	private BigDecimal dz_money; //���ʽ��

	private Integer refund_date; //�˿�����

	private BigDecimal refund_money; //�˿���

	private String sl_type; //�ɿʽ(1114)

	private String sl_type_name; //�ɿʽ����
	
	private Integer pre_product_id; //��ƷID

	private Integer input_man; //����Ա
	
	private Integer start_date;
	
	private Integer end_date;
	
	private String cust_name;
	
	private String pre_status;
	
	private String pre_level;
	
	private String pre_level_name;
	
	private Integer team_id;
	
	private Integer is_onway; // ��;�ʽ��־:1��
	
	private String sl_time; // ����ʱ��,��ȷ����
	
	private Integer sbf_serial_no; //���������˺����
	
	private Integer modiquota; //�Ƿ��Զ��޸�ԤԼ���ͷ����
	
	private Integer status;
	
	private String statusName;
	
	/**
	 * @return ���� modiquota��
	 */
	public Integer getModiquota() {
		return modiquota;
	}
	/**
	 * @param modiquota Ҫ���õ� modiquota��
	 */
	public void setModiquota(Integer modiquota) {
		this.modiquota = modiquota;
	}
	/**
	 * @return ���� sbf_serial_no��
	 */
	public Integer getSbf_serial_no() {
		return sbf_serial_no;
	}
	/**
	 * @param sbf_serial_no Ҫ���õ� sbf_serial_no��
	 */
	public void setSbf_serial_no(Integer sbf_serial_no) {
		this.sbf_serial_no = sbf_serial_no;
	}
	/**
	 * @return ���� pre_status��
	 */
	public String getPre_status() {
		return pre_status;
	}
	/**
	 * @param pre_status Ҫ���õ� pre_status��
	 */
	public void setPre_status(String pre_status) {
		this.pre_status = pre_status;
	}
	/**
	 * @return ���� cust_name��
	 */
	public String getCust_name() {
		return cust_name;
	}
	/**
	 * @param cust_name Ҫ���õ� cust_name��
	 */
	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
	}
	/**
	 * @return ���� end_date��
	 */
	public Integer getEnd_date() {
		return end_date;
	}
	/**
	 * @param end_date Ҫ���õ� end_date��
	 */
	public void setEnd_date(Integer end_date) {
		this.end_date = end_date;
	}
	/**
	 * @return ���� start_date��
	 */
	public Integer getStart_date() {
		return start_date;
	}
	/**
	 * @param start_date Ҫ���õ� start_date��
	 */
	public void setStart_date(Integer start_date) {
		this.start_date = start_date;
	}
	/**
	 * @return ���� dz_date��
	 */
	public Integer getDz_date() {
		return dz_date;
	}
	/**
	 * @param dz_date Ҫ���õ� dz_date��
	 */
	public void setDz_date(Integer dz_date) {
		this.dz_date = dz_date;
	}
	/**
	 * @return ���� dz_money��
	 */
	public BigDecimal getDz_money() {
		return dz_money;
	}
	/**
	 * @param dz_money Ҫ���õ� dz_money��
	 */
	public void setDz_money(BigDecimal dz_money) {
		this.dz_money = dz_money;
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
	 * @return ���� pre_serial_no��
	 */
	public Integer getPre_serial_no() {
		return pre_serial_no;
	}
	/**
	 * @param pre_serial_no Ҫ���õ� pre_serial_no��
	 */
	public void setPre_serial_no(Integer pre_serial_no) {
		this.pre_serial_no = pre_serial_no;
	}
	/**
	 * @return ���� refund_date��
	 */
	public Integer getRefund_date() {
		return refund_date;
	}
	/**
	 * @param refund_date Ҫ���õ� refund_date��
	 */
	public void setRefund_date(Integer refund_date) {
		this.refund_date = refund_date;
	}
	/**
	 * @return ���� refund_money��
	 */
	public BigDecimal getRefund_money() {
		return refund_money;
	}
	/**
	 * @param refund_money Ҫ���õ� refund_money��
	 */
	public void setRefund_money(BigDecimal refund_money) {
		this.refund_money = refund_money;
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
	 * @return ���� pre_product_id��
	 */
	public Integer getPre_product_id() {
		return pre_product_id;
	}
	/**
	 * @param pre_product_id Ҫ���õ� pre_product_id��
	 */
	public void setPre_product_id(Integer pre_product_id) {
		this.pre_product_id = pre_product_id;
	}

    /**
     * @return ���� pre_level��
     */
    public String getPre_level() {
        return pre_level;
    }
    /**
     * @param pre_level Ҫ���õ� pre_level��
     */
    public void setPre_level(String pre_level) {
        this.pre_level = pre_level;
    }
    /**
     * @return ���� pre_level_name��
     */
    public String getPre_level_name() {
        return pre_level_name;
    }
    /**
     * @param pre_level_name Ҫ���õ� pre_level_name��
     */
    public void setPre_level_name(String pre_level_name) {
        this.pre_level_name = pre_level_name;
    }
	/**
	 * @return ���� team_id��
	 */
	public Integer getTeam_id() {
		return team_id;
	}
	/**
	 * @param team_id Ҫ���õ� team_id��
	 */
	public void setTeam_id(Integer team_id) {
		this.team_id = team_id;
	}
	/**
	 * @return ���� is_onway��
	 */
	public Integer getIs_onway() {
		return is_onway;
	}
	/**
	 * @param is_onway Ҫ���õ� is_onway��
	 */
	public void setIs_onway(Integer is_onway) {
		this.is_onway = is_onway;
	}
	
	public String getSl_type() {
		return sl_type;
	}
	
	public void setSl_type(String sl_type) {
		this.sl_type = sl_type;
	}
	
	public String getSl_type_name() {
		return sl_type_name;
	}
	
	public void setSl_type_name(String sl_type_name) {
		this.sl_type_name = sl_type_name;
	}
	
	public String getSl_time() {
		return sl_time;
	}
	
	public void setSl_time(String sl_time) {
		this.sl_time = sl_time;
	}
	
	public Integer getStatus() {
		return status;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public String getStatusName() {
		return statusName;
	}
	
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	
}