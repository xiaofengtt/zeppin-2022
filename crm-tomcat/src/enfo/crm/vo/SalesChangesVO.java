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
public class SalesChangesVO {

	private Integer serial_no; //���

	private Integer cust_id; //ԤԼ������(EFCRM..TPRECONTRACT.SERIAL_NO)
	
	private String cust_name;
	
	private Integer input_man; //����Ա
	
	private Integer team_id;
	
	private Integer pre_serial_no; //ԤԼ������(EFCRM..TPRECONTRACT.SERIAL_NO)
	
	private Integer dz_date; //��������

	private BigDecimal dz_money; //���ʽ��
	
	private Integer from_service_man;
	
	private Integer to_service_man;
	
	private BigDecimal zr_money; //ת�ý��
	
	private String change_reason;
	
	private Integer status;
	
	private String statusName;
	
	private String checktime;
	
	private Integer pre_product_id; //��ƷID
	
	private String check_reason;//ȷ�����
	
	private String re_check_reason;//������
	
	public Integer getSerial_no() {
		return serial_no;
	}

	public void setSerial_no(Integer serial_no) {
		this.serial_no = serial_no;
	}

	public Integer getCust_id() {
		return cust_id;
	}

	public void setCust_id(Integer cust_id) {
		this.cust_id = cust_id;
	}

	public String getCust_name() {
		return cust_name;
	}

	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
	}

	public Integer getInput_man() {
		return input_man;
	}

	public void setInput_man(Integer input_man) {
		this.input_man = input_man;
	}

	public Integer getTeam_id() {
		return team_id;
	}

	public void setTeam_id(Integer team_id) {
		this.team_id = team_id;
	}

	public Integer getPre_serial_no() {
		return pre_serial_no;
	}

	public void setPre_serial_no(Integer pre_serial_no) {
		this.pre_serial_no = pre_serial_no;
	}

	public Integer getDz_date() {
		return dz_date;
	}

	public void setDz_date(Integer dz_date) {
		this.dz_date = dz_date;
	}

	public BigDecimal getDz_money() {
		return dz_money;
	}

	public void setDz_money(BigDecimal dz_money) {
		this.dz_money = dz_money;
	}

	public Integer getFrom_service_man() {
		return from_service_man;
	}

	public void setFrom_service_man(Integer from_service_man) {
		this.from_service_man = from_service_man;
	}

	public Integer getTo_service_man() {
		return to_service_man;
	}

	public void setTo_service_man(Integer to_service_man) {
		this.to_service_man = to_service_man;
	}

	public BigDecimal getZr_money() {
		return zr_money;
	}

	public void setZr_money(BigDecimal zr_money) {
		this.zr_money = zr_money;
	}

	public String getChange_reason() {
		return change_reason;
	}

	public void setChange_reason(String change_reason) {
		this.change_reason = change_reason;
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

	public String getChecktime() {
		return checktime;
	}

	public void setChecktime(String checktime) {
		this.checktime = checktime;
	}

	public Integer getPre_product_id() {
		return pre_product_id;
	}

	public void setPre_product_id(Integer pre_product_id) {
		this.pre_product_id = pre_product_id;
	}

	public String getCheck_reason() {
		return check_reason;
	}

	public void setCheck_reason(String check_reason) {
		this.check_reason = check_reason;
	}

	public String getRe_check_reason() {
		return re_check_reason;
	}

	public void setRe_check_reason(String re_check_reason) {
		this.re_check_reason = re_check_reason;
	}

}