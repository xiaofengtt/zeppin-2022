package enfo.crm.vo;

import java.sql.Timestamp;

/**
 * sendSMSVO ���ڷ��Ͷ��ű���ֶ���Ϣ
 * @author taochen
 *
 */
public class SendSMSVO {
	Integer smsIndex;		//����
	String smsUser;			//�û�����
	String phoneNumber;		//�绰����
	String smsContent;		//��������
	Integer userDefineNo;		//��������,��Ĭ��Ϊ0
	Integer NewFlag;			//���ο���������Ϊ1��������Ĭ��Ϊ1
	Integer sendLevel;			//��ֵԽ�ͣ����ȼ�Խ�ߡ�ϵͳʹ��-2��-1
	String putType;			//�ύ��ʽ����������ʱ��ѭ����
	Integer status;			//�ύ״̬
	String statusName;
	Integer inputOperator; //����Ա���
	String operatorName; //����Ա����
	Integer serial_no;
	Integer serial_no_detail;
	String cust_name;
	Integer check_man;
	String  send_time;
	Integer way_type;
	Integer send_type;
	String plan_time;
	String msg_type;
	String msg_type_name;
	String mobiles;
	String content_templet;
	String templateCode; //ģ�����
	String input_time;
	Integer check_flag;
	String check_time;
	Integer begin_plan_time;
	Integer end_plan_time;
	Integer serial_no_total;
	Integer cust_id;
	String send_result;
	Integer bat_serial_no ; //�����ڲ����
	Integer smstotal; //������������
	String ServiceTitle;
	Integer date_1;
	Integer date_2;
	Integer com_user_id;
	String smtp_user;
	String smtp_password;
	String from_email;
	String jobCMD; //����ƽ̨�е�ҵ���ţ��ɶ���ƽ̨����
	String macroJson; //����ƽ̨�еĺ�����JSON��
	String bats; //���κ�
	boolean isUseSmsPlat=false;
	Integer product_id;
	
	

	/**
	 * @return ���� product_id��
	 */
	public Integer getProduct_id() {
		return product_id;
	}
	/**
	 * @param product_id Ҫ���õ� product_id��
	 */
	public void setProduct_id(Integer product_id) {
		this.product_id = product_id;
	}
	/**
	 * @return ���� isUseSmsPlat��
	 */
	public boolean isUseSmsPlat() {
		return isUseSmsPlat;
	}
	/**
	 * @param isUseSmsPlat Ҫ���õ� isUseSmsPlat��
	 */
	public void setUseSmsPlat(boolean isUseSmsPlat) {
		this.isUseSmsPlat = isUseSmsPlat;
	}
	/**
	 * @return ���� templateCode��
	 */
	public String getTemplateCode() {
		return templateCode;
	}
	/**
	 * @param templateCode Ҫ���õ� templateCode��
	 */
	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}
	/**
	 * @return ���� operatorName��
	 */
	public String getOperatorName() {
		return operatorName;
	}
	/**
	 * @param operatorName Ҫ���õ� operatorName��
	 */
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	
	/**
	 * @return ���� bats��
	 */
	public String getBats() {
		return bats;
	}
	/**
	 * @param bats Ҫ���õ� bats��
	 */
	public void setBats(String bats) {
		this.bats = bats;
	}
	/**
	 * @return ���� macroJson��
	 */
	public String getMacroJson() {
		return macroJson;
	}
	/**
	 * @param macroJson Ҫ���õ� macroJson��
	 */
	public void setMacroJson(String macroJson) {
		this.macroJson = macroJson;
	}
	/**
	 * @return ���� jobCMD��
	 */
	public String getJobCMD() {
		return jobCMD;
	}
	/**
	 * @param jobCMD Ҫ���õ� jobCMD��
	 */
	public void setJobCMD(String jobCMD) {
		this.jobCMD = jobCMD;
	}
	public Integer getDate_1() {
		return date_1;
	}
	public void setDate_1(Integer date_1) {
		this.date_1 = date_1;
	}
	public Integer getDate_2() {
		return date_2;
	}
	public void setDate_2(Integer date_2) {
		this.date_2 = date_2;
	}
	public String getServiceTitle() {
		return ServiceTitle;
	}
	public void setServiceTitle(String serviceTitle) {
		ServiceTitle = serviceTitle;
	}
	/**
	 * @return ���� smstotal��
	 */
	public Integer getSmstotal() {
		return smstotal;
	}
	/**
	 * @param smstotal Ҫ���õ� smstotal��
	 */
	public void setSmstotal(Integer smstotal) {
		this.smstotal = smstotal;
	}
	/**
	 * @return ���� bat_serial_no��
	 */
	public Integer getBat_serial_no() {
		return bat_serial_no;
	}
	/**
	 * @param bat_serial_no Ҫ���õ� bat_serial_no��
	 */
	public void setBat_serial_no(Integer bat_serial_no) {
		this.bat_serial_no = bat_serial_no;
	}
	/**
	 * @return ���� send_result��
	 */
	public String getSend_result() {
		return send_result;
	}
	/**
	 * @param send_result Ҫ���õ� send_result��
	 */
	public void setSend_result(String send_result) {
		this.send_result = send_result;
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
	 * @return ���� serial_no_total��
	 */
	public Integer getSerial_no_total() {
		return serial_no_total;
	}
	/**
	 * @param serial_no_total Ҫ���õ� serial_no_total��
	 */
	public void setSerial_no_total(Integer serial_no_total) {
		this.serial_no_total = serial_no_total;
	}
	/**
	 * @return ���� begin_plan_time��
	 */
	public Integer getBegin_plan_time() {
		return begin_plan_time;
	}
	/**
	 * @param begin_plan_time Ҫ���õ� begin_plan_time��
	 */
	public void setBegin_plan_time(Integer begin_plan_time) {
		this.begin_plan_time = begin_plan_time;
	}
	/**
	 * @return ���� end_plan_time��
	 */
	public Integer getEnd_plan_time() {
		return end_plan_time;
	}
	/**
	 * @param end_plan_time Ҫ���õ� end_plan_time��
	 */
	public void setEnd_plan_time(Integer end_plan_time) {
		this.end_plan_time = end_plan_time;
	}
	/**
	 * @return ���� check_man��
	 */
	public Integer getCheck_man() {
		return check_man;
	}
	/**
	 * @param check_man Ҫ���õ� check_man��
	 */
	public void setCheck_man(Integer check_man) {
		this.check_man = check_man;
	}
	public String getCust_name() {
		return cust_name;
	}
	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
	}
	public Integer getSerial_no() {
		return serial_no;
	}
	public void setSerial_no(Integer serial_no) {
		this.serial_no = serial_no;
	}
	public Integer getSerial_no_detail() {
		return serial_no_detail;
	}
	public void setSerial_no_detail(Integer serial_no_detail) {
		this.serial_no_detail = serial_no_detail;
	}
	public Integer getInputOperator() {
		return inputOperator;
	}
	public void setInputOperator(Integer inputOperator) {
		this.inputOperator = inputOperator;
	}
	public String getSmsUser() {
		return smsUser;
	}
	public void setSmsUser(String smsUser) {
		this.smsUser = smsUser;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getSmsContent() {
		return smsContent;
	}
	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}
	
	public Integer getUserDefineNo() {
		return userDefineNo;
	}
	public void setUserDefineNo(Integer userDefineNo) {
		this.userDefineNo = userDefineNo;
	}
	public Integer getNewFlag() {
		return NewFlag;
	}
	public void setNewFlag(Integer newFlag) {
		NewFlag = newFlag;
	}
	public Integer getSendLevel() {
		return sendLevel;
	}
	public void setSendLevel(Integer sendLevel) {
		this.sendLevel = sendLevel;
	}
	public String getPutType() {
		return putType;
	}
	public void setPutType(String putType) {
		this.putType = putType;
	}
	public Integer getSmsIndex() {
		return smsIndex;
	}
	public void setSmsIndex(Integer smsIndex) {
		this.smsIndex = smsIndex;
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
	/**
	 * @return ���� send_time��
	 */
	public String getSend_time() {
		return send_time;
	}
	/**
	 * @param send_time Ҫ���õ� send_time��
	 */
	public void setSend_time(String send_time) {
		this.send_time = send_time;
	}
	/**
	 * @return ���� check_flag��
	 */
	public Integer getCheck_flag() {
		return check_flag;
	}
	/**
	 * @param check_flag Ҫ���õ� check_flag��
	 */
	public void setCheck_flag(Integer check_flag) {
		this.check_flag = check_flag;
	}
	/**
	 * @return ���� check_time��
	 */
	public String getCheck_time() {
		return check_time;
	}
	/**
	 * @param check_time Ҫ���õ� check_time��
	 */
	public void setCheck_time(String check_time) {
		this.check_time = check_time;
	}
	/**
	 * @return ���� content_templet��
	 */
	public String getContent_templet() {
		return content_templet;
	}
	/**
	 * @param content_templet Ҫ���õ� content_templet��
	 */
	public void setContent_templet(String content_templet) {
		this.content_templet = content_templet;
	}
	/**
	 * @return ���� input_time��
	 */
	public String getInput_time() {
		return input_time;
	}
	/**
	 * @param input_time Ҫ���õ� input_time��
	 */
	public void setInput_time(String input_time) {
		this.input_time = input_time;
	}
	/**
	 * @return ���� mobiles��
	 */
	public String getMobiles() {
		return mobiles;
	}
	/**
	 * @param mobiles Ҫ���õ� mobiles��
	 */
	public void setMobiles(String mobiles) {
		this.mobiles = mobiles;
	}
	/**
	 * @return ���� msg_type��
	 */
	public String getMsg_type() {
		return msg_type;
	}
	/**
	 * @param msg_type Ҫ���õ� msg_type��
	 */
	public void setMsg_type(String msg_type) {
		this.msg_type = msg_type;
	}
	/**
	 * @return ���� msg_type_name��
	 */
	public String getMsg_type_name() {
		return msg_type_name;
	}
	/**
	 * @param msg_type_name Ҫ���õ� msg_type_name��
	 */
	public void setMsg_type_name(String msg_type_name) {
		this.msg_type_name = msg_type_name;
	}
	/**
	 * @return ���� plan_time��
	 */
	public String getPlan_time() {
		return plan_time;
	}
	/**
	 * @param plan_time Ҫ���õ� plan_time��
	 */
	public void setPlan_time(String plan_time) {
		this.plan_time = plan_time;
	}
	/**
	 * @return ���� send_type��
	 */
	public Integer getSend_type() {
		return send_type;
	}
	/**
	 * @param send_type Ҫ���õ� send_type��
	 */
	public void setSend_type(Integer send_type) {
		this.send_type = send_type;
	}
	/**
	 * @return ���� way_type��
	 */
	public Integer getWay_type() {
		return way_type;
	}
	/**
	 * @param way_type Ҫ���õ� way_type��
	 */
	public void setWay_type(Integer way_type) {
		this.way_type = way_type;
	}

	/**
	 * @return ���� smtp_user��
	 */
	public String getSmtp_user() {
		return smtp_user;
	}
	/**
	 * @param smtp_user Ҫ���õ� smtp_user��
	 */
	public void setSmtp_user(String smtp_user) {
		this.smtp_user = smtp_user;
	}
	/**
	 * @return ���� smtp_password��
	 */
	public String getSmtp_password() {
		return smtp_password;
	}
	/**
	 * @param smtp_password Ҫ���õ� smtp_password��
	 */
	public void setSmtp_password(String smtp_password) {
		this.smtp_password = smtp_password;
	}
	/**
	 * @return ���� from_email��
	 */
	public String getFrom_email() {
		return from_email;
	}
	/**
	 * @param from_email Ҫ���õ� from_email��
	 */
	public void setFrom_email(String from_email) {
		this.from_email = from_email;
	}
	/**
	 * @return ���� com_user_id��
	 */
	public Integer getCom_user_id() {
		return com_user_id;
	}
	/**
	 * @param com_user_id Ҫ���õ� com_user_id��
	 */
	public void setCom_user_id(Integer com_user_id) {
		this.com_user_id = com_user_id;
	}
}
