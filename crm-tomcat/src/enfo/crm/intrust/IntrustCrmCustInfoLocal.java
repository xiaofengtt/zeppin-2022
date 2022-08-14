package enfo.crm.intrust;

import enfo.crm.dao.IBusiFullLocal;

public interface IntrustCrmCustInfoLocal extends IBusiFullLocal {

	//	查询crm客户信息
	/**
	 * @ejb.interface-method view-type = "local"
	 */
	void listCrmCust() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @throws Exception
	 */
	void modiFlag() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 */
	void load() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 */
	boolean getNext() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 card_id。
	 */
	String getCard_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            card_id。
	 */
	void setCard_id(String card_id);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 card_type。
	 */
	String getCard_type();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            card_type。
	 */
	void setCard_type(String card_type);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 contact_man。
	 */
	String getContact_man();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            contact_man。
	 */
	void setContact_man(String contact_man);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 cust_name。
	 */
	String getCust_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            cust_name。
	 */
	void setCust_name(String cust_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 cust_no。
	 */
	String getCust_no();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            cust_no。
	 */
	void setCust_no(String cust_no);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 cust_type。
	 */
	Integer getCust_type();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            cust_type。
	 */
	void setCust_type(Integer cust_type);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 e_mail。
	 */
	String getE_mail();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            e_mail。
	 */
	void setE_mail(String e_mail);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 input_man。
	 */
	Integer getInput_man();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            input_man。
	 */
	void setInput_man(Integer input_man);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 legal_man。
	 */
	String getLegal_man();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            legal_man。
	 */
	void setLegal_man(String legal_man);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 mobile。
	 */
	String getMobile();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            mobile。
	 */
	void setMobile(String mobile);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 post_address。
	 */
	String getPost_address();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            post_address。
	 */
	void setPost_address(String post_address);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 post_code。
	 */
	String getPost_code();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            post_code。
	 */
	void setPost_code(String post_code);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 service_man。
	 */
	Integer getService_man();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            service_man。
	 */
	void setService_man(Integer service_man);

}