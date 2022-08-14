/*
 * �������� 2009-11-27
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
package enfo.crm.vo;

import java.sql.Timestamp;

/**
 * �ͻ���Ϣ��ϵ�˶����ӦCustomerContactVO����
 * @author dingyj
 * @since 2009-11-27
 * @version 1.0
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
public class CustomerContactVO {
	
	private Integer contactId;//��ϵ��ID
	private Integer cust_id;//�ͻ�ID
	private String contactor;//��ϵ������
	private String phoneHome;//��ͥ�绰
	private String phoneOffice;//�칫�绰
	private String moblie;//�ֻ�
	private String fax;//����
	private String email;//�����ʼ�
	private String address;//ͨѶ��ַ
	private String zipCode;///��������
	private Integer sex;///�Ա�
	private Integer birthday;//����
	private Integer anniversary;//������
	private Integer isMarried;//����״��
	private String spouse;//��ż����
	private Integer boos;//����
	private String department;//����
	private String duty;//ְ��
	private String country;//����
	private String province;//ʡ/��/������
	private String city;//��/��
	private Integer role;//��ɫ
	private String assistant;//����
	private String assistantTelphone;//����绰
	private String manager;//����
	private String managerTelphone;//����绰
	private String contactWay;//��ѡ��ϵ��ʽ
	private Integer preferredDate;//���ܷ�����ѡ��
	private Integer preferredTime;//���ܷ�����ѡʱ�䣨1����2����3���ϣ�
	private Integer receiveContactWay;//���ܵ���ϵ��ʽ
	private Integer receiveService;//��ǰ�ͻ����յķ������
	private Timestamp insertT;//����ʱ��
	private Integer insertMan;//����Ա
	private Integer ContactID;
	private Integer contactType;
	
	/**
	 * @return
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @return
	 */
	public Integer getAnniversary() {
		return anniversary;
	}

	/**
	 * @return
	 */
	public String getAssistant() {
		return assistant;
	}

	/**
	 * @return
	 */
	public String getAssistantTelphone() {
		return assistantTelphone;
	}

	/**
	 * @return
	 */
	public Integer getBirthday() {
		return birthday;
	}

	/**
	 * @return
	 */
	public Integer getBoos() {
		return boos;
	}

	/**
	 * @return
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @return
	 */
	public Integer getContactId() {
		return contactId;
	}

	/**
	 * @return
	 */
	public String getContactor() {
		return contactor;
	}

	/**
	 * @return
	 */
	public String getContactWay() {
		return contactWay;
	}

	/**
	 * @return
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @return
	 */
	public Integer getCust_id() {
		return cust_id;
	}

	/**
	 * @return
	 */
	public String getDepartment() {
		return department;
	}

	/**
	 * @return
	 */
	public String getDuty() {
		return duty;
	}

	/**
	 * @return
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @return
	 */
	public String getFax() {
		return fax;
	}

	/**
	 * @return
	 */
	public Integer getInsertMan() {
		return insertMan;
	}

	/**
	 * @return
	 */
	public Timestamp getInsertT() {
		return insertT;
	}

	/**
	 * @return
	 */
	public Integer getIsMarried() {
		return isMarried;
	}

	/**
	 * @return
	 */
	public String getManager() {
		return manager;
	}

	/**
	 * @return
	 */
	public String getManagerTelphone() {
		return managerTelphone;
	}

	/**
	 * @return
	 */
	public String getMoblie() {
		return moblie;
	}

	/**
	 * @return
	 */
	public String getPhoneHome() {
		return phoneHome;
	}

	/**
	 * @return
	 */
	public String getPhoneOffice() {
		return phoneOffice;
	}

	/**
	 * @return
	 */
	public Integer getPreferredDate() {
		return preferredDate;
	}

	/**
	 * @return
	 */
	public Integer getPreferredTime() {
		return preferredTime;
	}

	/**
	 * @return
	 */
	public String getProvince() {
		return province;
	}

	/**
	 * @return
	 */
	public Integer getReceiveContactWay() {
		return receiveContactWay;
	}

	/**
	 * @return
	 */
	public Integer getReceiveService() {
		return receiveService;
	}

	/**
	 * @return
	 */
	public Integer getRole() {
		return role;
	}

	/**
	 * @return
	 */
	public Integer getSex() {
		return sex;
	}

	/**
	 * @return
	 */
	public String getSpouse() {
		return spouse;
	}

	/**
	 * @return
	 */
	public String getZipCode() {
		return zipCode;
	}

	/**
	 * @param string
	 */
	public void setAddress(String string) {
		address = string;
	}

	/**
	 * @param integer
	 */
	public void setAnniversary(Integer integer) {
		anniversary = integer;
	}

	/**
	 * @param string
	 */
	public void setAssistant(String string) {
		assistant = string;
	}

	/**
	 * @param string
	 */
	public void setAssistantTelphone(String string) {
		assistantTelphone = string;
	}

	/**
	 * @param integer
	 */
	public void setBirthday(Integer integer) {
		birthday = integer;
	}

	/**
	 * @param integer
	 */
	public void setBoos(Integer integer) {
		boos = integer;
	}

	/**
	 * @param string
	 */
	public void setCity(String string) {
		city = string;
	}

	/**
	 * @param integer
	 */
	public void setContactId(Integer integer) {
		contactId = integer;
	}

	/**
	 * @param string
	 */
	public void setContactor(String string) {
		contactor = string;
	}

	/**
	 * @param string
	 */
	public void setContactWay(String string) {
		contactWay = string;
	}

	/**
	 * @param string
	 */
	public void setCountry(String string) {
		country = string;
	}

	/**
	 * @param integer
	 */
	public void setCust_id(Integer integer) {
		cust_id = integer;
	}

	/**
	 * @param string
	 */
	public void setDepartment(String string) {
		department = string;
	}

	/**
	 * @param string
	 */
	public void setDuty(String string) {
		duty = string;
	}

	/**
	 * @param string
	 */
	public void setEmail(String string) {
		email = string;
	}

	/**
	 * @param string
	 */
	public void setFax(String string) {
		fax = string;
	}

	/**
	 * @param integer
	 */
	public void setInsertMan(Integer integer) {
		insertMan = integer;
	}

	/**
	 * @param timestamp
	 */
	public void setInsertT(Timestamp timestamp) {
		insertT = timestamp;
	}

	/**
	 * @param integer
	 */
	public void setIsMarried(Integer integer) {
		isMarried = integer;
	}

	/**
	 * @param string
	 */
	public void setManager(String string) {
		manager = string;
	}

	/**
	 * @param string
	 */
	public void setManagerTelphone(String string) {
		managerTelphone = string;
	}

	/**
	 * @param string
	 */
	public void setMoblie(String string) {
		moblie = string;
	}

	/**
	 * @param string
	 */
	public void setPhoneHome(String string) {
		phoneHome = string;
	}

	/**
	 * @param string
	 */
	public void setPhoneOffice(String string) {
		phoneOffice = string;
	}

	/**
	 * @param integer
	 */
	public void setPreferredDate(Integer integer) {
		preferredDate = integer;
	}

	/**
	 * @param integer
	 */
	public void setPreferredTime(Integer integer) {
		preferredTime = integer;
	}

	/**
	 * @param string
	 */
	public void setProvince(String string) {
		province = string;
	}

	/**
	 * @param integer
	 */
	public void setReceiveContactWay(Integer integer) {
		receiveContactWay = integer;
	}

	/**
	 * @param integer
	 */
	public void setReceiveService(Integer integer) {
		receiveService = integer;
	}

	/**
	 * @param integer
	 */
	public void setRole(Integer integer) {
		role = integer;
	}

	/**
	 * @param integer
	 */
	public void setSex(Integer integer) {
		sex = integer;
	}

	/**
	 * @param string
	 */
	public void setSpouse(String string) {
		spouse = string;
	}

	/**
	 * @param string
	 */
	public void setZipCode(String string) {
		zipCode = string;
	}

	/**
	 * @return
	 */
	public Integer getContactID() {
		return ContactID;
	}

	/**
	 * @param integer
	 */
	public void setContactID(Integer integer) {
		ContactID = integer;
	}

	/**
	 * @return
	 */
	public Integer getContactType() {
		return contactType;
	}

	/**
	 * @param integer
	 */
	public void setContactType(Integer integer) {
		contactType = integer;
	}

}
