package enfo.crm.intrust;

import enfo.crm.dao.IBusiFullLocal;

public interface OpertypeLocal extends IBusiFullLocal {

	/**
	 * @ejb.interface-method view-type = "local" 
	 * @param input_man
	 * @throws Exception
	 */
	void save(Integer input_man) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param input_man
	 * @throws Exception
	 */
	void append(Integer input_man) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param input_man
	 * @throws Exception
	 */
	void delete(Integer input_man) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param input_man
	 * @throws Exception
	 */
	void load(Integer input_man) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 */
	boolean getNext() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_QUERY_TCAPITALOPERTYPE_TREE
	 * @IN_SERIAL_NO INT
	 * @param serial_no
	 * @throws Exception
	 */
	void listTree(Integer serial_no) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ���json�Ĳ���
	 * @return
	 * @throws Exception
	 */
	String queryCapitaltypeJosn(Integer serial_no, java.util.Locale clientLocale) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� bottom_flag��
	 */
	Integer getBottom_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            bottom_flag��
	 */
	void setBottom_flag(Integer bottom_flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� caption��
	 */
	String getCaption();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            caption��
	 */
	void setCaption(String caption);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� parent_caption��
	 */
	String getParent_caption();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            parent_caption��
	 */
	void setParent_caption(String parent_caption);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� parent_id��
	 */
	Integer getParent_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            parent_id��
	 */
	void setParent_id(Integer parent_id);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� serial_no��
	 */
	Integer getSerial_no();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            serial_no��
	 */
	void setSerial_no(Integer serial_no);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            bookcode��
	 */
	void setBookcode(Integer bookcode);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            input_man��
	 */
	void setInput_man(Integer input_man);

}