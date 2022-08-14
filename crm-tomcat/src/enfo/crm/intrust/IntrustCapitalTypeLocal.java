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
	 * 增加资产类别 SP_ADD_TCAPITALTYPE
	 * @IN_BOOK_CODE INT,
	 * @IN_ZC_FLAG INT, --1信托资产2非信托资产
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
	 * 获得json的部门
	 * @return
	 * @throws Exception
	 */
	String queryCapitaltypeJosn(Integer serial_no, java.util.Locale clientLocale) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 bookcode。
	 */
	Integer getBookcode();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            bookcode。
	 */
	void setBookcode(Integer bookcode);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 bottom_flag。
	 */
	Integer getBottom_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            bottom_flag。
	 */
	void setBottom_flag(Integer bottom_flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 caption。
	 */
	String getCaption();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            caption。
	 */
	void setCaption(String caption);

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
	 * @return 返回 parent_caption。
	 */
	String getParent_caption();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            parent_caption。
	 */
	void setParent_caption(String parent_caption);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 parent_id。
	 */
	Integer getParent_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            parent_id。
	 */
	void setParent_id(Integer parent_id);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 serial_no。
	 */
	Integer getSerial_no();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            serial_no。
	 */
	void setSerial_no(Integer serial_no);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 zc_flag。
	 */
	Integer getZc_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            zc_flag。
	 */
	void setZc_flag(Integer zc_flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 zclb_bh。
	 */
	String getZclb_bh();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            zclb_bh。
	 */
	void setZclb_bh(String zclb_bh);

	/**
	 *  @ejb.interface-method view-type = "local"
	 * @return 返回 dywlb。
	 */
	String getDywlb();

	/**
	 *  @ejb.interface-method view-type = "local"
	 * @param dywlb 要设置的 dywlb。
	 */
	void setDywlb(String dywlb);

	/**
	 *  @ejb.interface-method view-type = "local"
	 * @return 返回 zywlb。
	 */
	String getZywlb();

	/**
	 *  @ejb.interface-method view-type = "local"
	 * @param zywlb 要设置的 zywlb。
	 */
	void setZywlb(String zywlb);

}