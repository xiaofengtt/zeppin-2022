/*
 * �������� 2012-3-19
 *
 * TODO Ҫ���Ĵ����ɵ��ļ���ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
package enfo.crm.vo;

/**
 * @author Administrator
 * 
 * TODO Ҫ���Ĵ����ɵ�����ע�͵�ģ�壬��ת�� ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
public class CustomerConnectionVO {

	private Integer serial_no; //���

	private Integer cust_id; //ԤԼ������(EFCRM..TPRECONTRACT.SERIAL_NO)
	
	private String cust_name;
	
	private Integer input_man; //����Ա
	
	private Integer team_id;
	
	private String cust_tel;
	
	private String mobile;
	
	private String o_tel;
	
	private String h_tel;
	
	private String bp;
	
	private Integer status;
	
	private String statusName;
	
	private String checktime;
	
	private Integer flagCheck;//�Ƿ������Ȩ��
	
	private String apply_reason;//��������
	
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

	public String getCust_tel() {
		return cust_tel;
	}

	public void setCust_tel(String cust_tel) {
		this.cust_tel = cust_tel;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getO_tel() {
		return o_tel;
	}

	public void setO_tel(String o_tel) {
		this.o_tel = o_tel;
	}

	public String getH_tel() {
		return h_tel;
	}

	public void setH_tel(String h_tel) {
		this.h_tel = h_tel;
	}

	public String getBp() {
		return bp;
	}

	public void setBp(String bp) {
		this.bp = bp;
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

	public Integer getFlagCheck() {
		return flagCheck;
	}

	public void setFlagCheck(Integer flagCheck) {
		this.flagCheck = flagCheck;
	}

	public String getApply_reason() {
		return apply_reason;
	}

	public void setApply_reason(String apply_reason) {
		this.apply_reason = apply_reason;
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