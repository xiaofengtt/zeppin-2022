/*
 * 创建日期 2012-2-8
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
package enfo.crm.fileupload;

import java.util.HashMap;
import java.util.Map;


/**
 * @author carlos
 *
 * TODO 要更改此生成的类型注释的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
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
		fieldMapping.put("项目名称", "setProject_name");
		fieldMapping.put("合同序号", "setContract_bh");
		fieldMapping.put("客户姓名", "setCustomer_name");
		fieldMapping.put("证件类型", "setCertificate_type");
		fieldMapping.put("证件号码", "setCertificate_no");
		fieldMapping.put("手机", "setMobile");
		fieldMapping.put("固定电话", "setTelephone");
		fieldMapping.put("传真", "setFax");
		fieldMapping.put("联系地址", "setAddress");
		fieldMapping.put("邮编", "setPost_code");
		fieldMapping.put("客户经理", "setCustomer_mananger");
		fieldMapping.put("EMAIL", "setEmail");
	}
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
	 * @return 返回 batch_id。
	 */
	public Integer getBatch_id() {
		return batch_id;
	}
	/**
	 * @param batch_id 要设置的 batch_id。
	 */
	public void setBatch_id(Integer batch_id) {
		this.batch_id = batch_id;
	}
	/**
	 * @return 返回 certificate_no。
	 */
	public String getCertificate_no() {
		return certificate_no;
	}
	/**
	 * @param certificate_no 要设置的 certificate_no。
	 */
	public void setCertificate_no(String certificate_no) {
		this.certificate_no = certificate_no;
	}
	/**
	 * @return 返回 certificate_type。
	 */
	public String getCertificate_type() {
		return certificate_type;
	}
	/**
	 * @param certificate_type 要设置的 certificate_type。
	 */
	public void setCertificate_type(String certificate_type) {
		this.certificate_type = certificate_type;
	}
	/**
	 * @return 返回 contract_bh。
	 */
	public String getContract_bh() {
		return contract_bh;
	}
	/**
	 * @param contract_bh 要设置的 contract_bh。
	 */
	public void setContract_bh(String contract_bh) {
		this.contract_bh = contract_bh;
	}
	/**
	 * @return 返回 customer_mananger。
	 */
	public String getCustomer_mananger() {
		return customer_mananger;
	}
	/**
	 * @param customer_mananger 要设置的 customer_mananger。
	 */
	public void setCustomer_mananger(String customer_mananger) {
		this.customer_mananger = customer_mananger;
	}
	/**
	 * @return 返回 customer_name。
	 */
	public String getCustomer_name() {
		return customer_name;
	}
	/**
	 * @param customer_name 要设置的 customer_name。
	 */
	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}
	/**
	 * @return 返回 fax。
	 */
	public String getFax() {
		return fax;
	}
	/**
	 * @param fax 要设置的 fax。
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}
	/**
	 * @return 返回 mobile。
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * @param mobile 要设置的 mobile。
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * @return 返回 module_id。
	 */
	public String getModule_id() {
		return module_id;
	}
	/**
	 * @param module_id 要设置的 module_id。
	 */
	public void setModule_id(String module_id) {
		this.module_id = module_id;
	}
	/**
	 * @return 返回 post_code。
	 */
	public String getPost_code() {
		return post_code;
	}
	/**
	 * @param post_code 要设置的 post_code。
	 */
	public void setPost_code(String post_code) {
		this.post_code = post_code;
	}
	/**
	 * @return 返回 project_name。
	 */
	public String getProject_name() {
		return project_name;
	}
	/**
	 * @param project_name 要设置的 project_name。
	 */
	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}
	/**
	 * @return 返回 serial_no。
	 */
	public Integer getSerial_no() {
		return serial_no;
	}
	/**
	 * @param serial_no 要设置的 serial_no。
	 */
	public void setSerial_no(Integer serial_no) {
		this.serial_no = serial_no;
	}
	/**
	 * @return 返回 status。
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * @param status 要设置的 status。
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * @return 返回 summary。
	 */
	public String getSummary() {
		return summary;
	}
	/**
	 * @param summary 要设置的 summary。
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}
	/**
	 * @return 返回 telephone。
	 */
	public String getTelephone() {
		return telephone;
	}
	/**
	 * @param telephone 要设置的 telephone。
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	/**
	 * @return 返回 input_man。
	 */
	public Integer getInput_man() {
		return input_man;
	}
	/**
	 * @param input_man 要设置的 input_man。
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

