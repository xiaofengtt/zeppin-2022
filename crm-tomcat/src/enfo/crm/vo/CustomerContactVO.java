/*
 * 创建日期 2009-11-27
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package enfo.crm.vo;

import java.sql.Timestamp;

/**
 * 客户信息联系人对象对应CustomerContactVO对象
 * @author dingyj
 * @since 2009-11-27
 * @version 1.0
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class CustomerContactVO {
	
	private Integer contactId;//联系人ID
	private Integer cust_id;//客户ID
	private String contactor;//联系人名称
	private String phoneHome;//家庭电话
	private String phoneOffice;//办公电话
	private String moblie;//手机
	private String fax;//传真
	private String email;//电子邮件
	private String address;//通讯地址
	private String zipCode;///邮政编码
	private Integer sex;///性别
	private Integer birthday;//生日
	private Integer anniversary;//纪念日
	private Integer isMarried;//婚姻状况
	private String spouse;//配偶姓名
	private Integer boos;//上市
	private String department;//部门
	private String duty;//职务
	private String country;//国家
	private String province;//省/市/自治区
	private String city;//市/县
	private Integer role;//角色
	private String assistant;//助理
	private String assistantTelphone;//助理电话
	private String manager;//经理
	private String managerTelphone;//经理电话
	private String contactWay;//首选联系方式
	private Integer preferredDate;//接受服务首选日
	private Integer preferredTime;//接受服务首选时间（1上午2下午3晚上）
	private Integer receiveContactWay;//接受的联系方式
	private Integer receiveService;//当前客户接收的服务类别
	private Timestamp insertT;//操作时间
	private Integer insertMan;//操作员
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
