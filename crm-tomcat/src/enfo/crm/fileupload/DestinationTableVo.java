/*
 * �������� 2012-2-8
 *
 * TODO Ҫ���Ĵ����ɵ��ļ���ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
package enfo.crm.fileupload;

import java.util.HashMap;
import java.util.Map;


/**
 * @author carlos
 *
 * TODO Ҫ���Ĵ����ɵ�����ע�͵�ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
public class DestinationTableVo {
	private Integer serial_no;
	private String project_name;
	private String contract_bh;
	private String customer_name;
	private String certificate_type;
	private String certificate_no;
	private String mobile;
	private String telephone;
	private String fax;
	private String address;
	private String post_code;
	private String customer_mananger;
	private String email;
	private String module_id;
	private Integer batch_id;
	private Integer status;
	private String summary;
	private Integer input_man;
	
	public static Map fieldMapping = new HashMap();
	static{
		fieldMapping.put("��Ŀ����", "setProject_name");
		fieldMapping.put("��ͬ���", "setContract_bh");
		fieldMapping.put("�ͻ�����", "setCustomer_name");
		fieldMapping.put("֤������", "setCertificate_type");
		fieldMapping.put("֤������", "setCertificate_no");
		fieldMapping.put("�ֻ�", "setMobile");
		fieldMapping.put("�̶��绰", "setTelephone");
		fieldMapping.put("����", "setFax");
		fieldMapping.put("��ϵ��ַ", "setAddress");
		fieldMapping.put("�ʱ�", "setPost_code");
		fieldMapping.put("�ͻ�����", "setCustomer_mananger");
		fieldMapping.put("EMAIL", "setEmail");
	}
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
	 * @return ���� batch_id��
	 */
	public Integer getBatch_id() {
		return batch_id;
	}
	/**
	 * @param batch_id Ҫ���õ� batch_id��
	 */
	public void setBatch_id(Integer batch_id) {
		this.batch_id = batch_id;
	}
	/**
	 * @return ���� certificate_no��
	 */
	public String getCertificate_no() {
		return certificate_no;
	}
	/**
	 * @param certificate_no Ҫ���õ� certificate_no��
	 */
	public void setCertificate_no(String certificate_no) {
		this.certificate_no = certificate_no;
	}
	/**
	 * @return ���� certificate_type��
	 */
	public String getCertificate_type() {
		return certificate_type;
	}
	/**
	 * @param certificate_type Ҫ���õ� certificate_type��
	 */
	public void setCertificate_type(String certificate_type) {
		this.certificate_type = certificate_type;
	}
	/**
	 * @return ���� contract_bh��
	 */
	public String getContract_bh() {
		return contract_bh;
	}
	/**
	 * @param contract_bh Ҫ���õ� contract_bh��
	 */
	public void setContract_bh(String contract_bh) {
		this.contract_bh = contract_bh;
	}
	/**
	 * @return ���� customer_mananger��
	 */
	public String getCustomer_mananger() {
		return customer_mananger;
	}
	/**
	 * @param customer_mananger Ҫ���õ� customer_mananger��
	 */
	public void setCustomer_mananger(String customer_mananger) {
		this.customer_mananger = customer_mananger;
	}
	/**
	 * @return ���� customer_name��
	 */
	public String getCustomer_name() {
		return customer_name;
	}
	/**
	 * @param customer_name Ҫ���õ� customer_name��
	 */
	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}
	/**
	 * @return ���� fax��
	 */
	public String getFax() {
		return fax;
	}
	/**
	 * @param fax Ҫ���õ� fax��
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}
	/**
	 * @return ���� mobile��
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * @param mobile Ҫ���õ� mobile��
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * @return ���� module_id��
	 */
	public String getModule_id() {
		return module_id;
	}
	/**
	 * @param module_id Ҫ���õ� module_id��
	 */
	public void setModule_id(String module_id) {
		this.module_id = module_id;
	}
	/**
	 * @return ���� post_code��
	 */
	public String getPost_code() {
		return post_code;
	}
	/**
	 * @param post_code Ҫ���õ� post_code��
	 */
	public void setPost_code(String post_code) {
		this.post_code = post_code;
	}
	/**
	 * @return ���� project_name��
	 */
	public String getProject_name() {
		return project_name;
	}
	/**
	 * @param project_name Ҫ���õ� project_name��
	 */
	public void setProject_name(String project_name) {
		this.project_name = project_name;
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
	 * @return ���� telephone��
	 */
	public String getTelephone() {
		return telephone;
	}
	/**
	 * @param telephone Ҫ���õ� telephone��
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}

