package enfo.crm.intrust;

import enfo.crm.dao.IBusiFullLocal;

public interface IntrustCapitalTypeLocal extends IBusiFullLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_MODI_TCAPITALTYPE
	 * @IN_SERIAL_NO INT,
	 * @IN_CAPTION VARCHAR(60),
	 * @IN_INPUT_MAN INT
	 */
	void save(Integer input_man) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * �����ʲ���� SP_ADD_TCAPITALTYPE
	 * @IN_BOOK_CODE INT,
	 * @IN_ZC_FLAG INT, --1�����ʲ�2�������ʲ�
	 * @IN_ZCLB_BH VARCHAR(6),
	 * @IN_CAPTION VARCHAR(60),
	 * @IN_PARENT_ID INT,
	 * @IN_INPUT_MAN INT
	 */
	void append(Integer input_man) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_DEL_TCAPITALTYPE
	 * @IN_SERIAL_NO INT,
	 * @IN_INPUT_MAN INT
	 */
	void delete(Integer input_man) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_QUERY_TCAPITALTYPE
	 * @IN_BOOK_CODE INT,
	 * @IN_SERIAL_NO INT,
	 * @IN_CAPTION VARCHAR(60),
	 * @IN_PARENT_ID INT,
	 * @IN_BOTTOM_FLAG INT,
	 * @IN_INPUT_MAN INT
	 */
	void load(Integer input_man) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 */
	boolean getNext() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_QUERY_TCAPITALTYPE_TREE
	 * @IN_BOOK_CODE INT,
	 * @IN_PARENT_ID INT,
	 * @IN_ZC_FLAG INT,
	 * @IN_INPUT_MAN INT = NULL
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
	 * @return ���� bookcode��
	 */
	Integer getBookcode();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            bookcode��
	 */
	void setBookcode(Integer bookcode);

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
	 * @return ���� zc_flag��
	 */
	Integer getZc_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            zc_flag��
	 */
	void setZc_flag(Integer zc_flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� zclb_bh��
	 */
	String getZclb_bh();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            zclb_bh��
	 */
	void setZclb_bh(String zclb_bh);

	/**
	 *  @ejb.interface-method view-type = "local"
	 * @return ���� dywlb��
	 */
	String getDywlb();

	/**
	 *  @ejb.interface-method view-type = "local"
	 * @param dywlb Ҫ���õ� dywlb��
	 */
	void setDywlb(String dywlb);

	/**
	 *  @ejb.interface-method view-type = "local"
	 * @return ���� zywlb��
	 */
	String getZywlb();

	/**
	 *  @ejb.interface-method view-type = "local"
	 * @param zywlb Ҫ���õ� zywlb��
	 */
	void setZywlb(String zywlb);

}