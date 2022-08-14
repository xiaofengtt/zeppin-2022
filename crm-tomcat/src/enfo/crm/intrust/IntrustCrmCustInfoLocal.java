package enfo.crm.intrust;

import enfo.crm.dao.IBusiFullLocal;

public interface IntrustCrmCustInfoLocal extends IBusiFullLocal {

	//	��ѯcrm�ͻ���Ϣ
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
	 * @return ���� card_id��
	 */
	String getCard_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            card_id��
	 */
	void setCard_id(String card_id);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� card_type��
	 */
	String getCard_type();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            card_type��
	 */
	void setCard_type(String card_type);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� contact_man��
	 */
	String getContact_man();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            contact_man��
	 */
	void setContact_man(String contact_man);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� cust_name��
	 */
	String getCust_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            cust_name��
	 */
	void setCust_name(String cust_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� cust_no��
	 */
	String getCust_no();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            cust_no��
	 */
	void setCust_no(String cust_no);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� cust_type��
	 */
	Integer getCust_type();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            cust_type��
	 */
	void setCust_type(Integer cust_type);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� e_mail��
	 */
	String getE_mail();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            e_mail��
	 */
	void setE_mail(String e_mail);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� input_man��
	 */
	Integer getInput_man();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            input_man��
	 */
	void setInput_man(Integer input_man);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� legal_man��
	 */
	String getLegal_man();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            legal_man��
	 */
	void setLegal_man(String legal_man);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� mobile��
	 */
	String getMobile();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            mobile��
	 */
	void setMobile(String mobile);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� post_address��
	 */
	String getPost_address();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            post_address��
	 */
	void setPost_address(String post_address);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� post_code��
	 */
	String getPost_code();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            post_code��
	 */
	void setPost_code(String post_code);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� service_man��
	 */
	Integer getService_man();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            service_man��
	 */
	void setService_man(Integer service_man);

}