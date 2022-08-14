package enfo.crm.intrust;

import java.math.BigDecimal;

import enfo.crm.dao.IBusiFullLocal;

public interface IntrustCapitalAddLocal extends IBusiFullLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 资产信息自定义要素维护 SP_MODI_TCAPITALADD
	 * @IN_SERIAL_NO INT,
	 * @IN_FIELD_CAPTION VARCHAR(64),
	 * @IN_INPUT_MAN INT
	 */
	void save(Integer input_man) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 修改资产的自定义要素信息 SP_MODI_TCAPITALADDINFO
	 * @IN_SERIAL_NO INT,
	 * @IN_FIELD_CONTENT VARCHAR(512),
	 * @IN_INPUT_MAN INT
	 */
	void saveInfo() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_ADD_TCAPITALADDINFO
	 * @IN_CI_SERIAL_NO INT,
	 * @IN_FIELD_CAPTION VARCHAR(64),
	 * @IN_FIELD_CONTENT VARCHAR(512),
	 * @IN_INPUT_MAN INT,
	 * @OUT_SERIAL_NO INT OUTPUT
	 */
	void appendInfo() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_ADD_TCAPITALADD
	 * @IN_BOOK_CODE INT,
	 * @IN_CAPITAL_TYPE INT,
	 * @IN_FIELD_CAPTION VARCHAR(64),
	 * @IN_INPUT_MAN INT
	 */
	void append(Integer input_man) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 资产信息自定义要素维护 SP_DEL_TCAPITALADD
	 * @IN_SERIAL_NO INT,
	 * @IN_INPUT_MAN INT
	 */
	void delete(Integer input_man) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 删除资产的所有自定义要素信息 SP_DEL_TCAPITALALLADDINFO
	 * @IN_CI_SERIAL_NO INT,
	 * @IN_INPUT_MAN INT
	 */
	void deleteAddInfo() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_QUERY_TCAPITALADD
	 * @IN_BOOK_CODE INT,
	 * @IN_SERIAL_NO INT,
	 * @IN_CAPITAL_TYPE INT,
	 * @IN_CAPITAL_TYPE_NAME VARCHAR(60),
	 * @IN_INCLUDE_PARENT INT = 2,
	 * @IN_INPUT_MAN INT
	 */
	void load(Integer input_man) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_QUERY_TCAPITALADDINFO
	 * @IN_CI_SERIAL_NO INT,
	 * @IN_SERIAL_NO INT,
	 * @IN_INPUT_MAN INT,
	 * @IN_FIELD_CAPTION VARCHAR(64) = NULL
	 */
	void listInfo() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * add by tsg 2007-12-18
	 * 
	 * 审核追加财产资本信息SP_QUERY_TCAPITALINFO_ADD SP_CHECK_TCAPITALINFO_ONE
	 * 
	 * @IN_SERIAL_NO INT,
	 * @IN_POST_DATE INT = NULL,
	 * @IN_FLAG INT = 1, --1、前台审核 2、后台审核通过 3、后台审核不通过
	 * @IN_INPUT_MAN INT
	 */
	void checkCapitalAddInfo() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * add by tsg 2007-12-18
	 * 
	 * 查询追加的财产资本
	 * 
	 * SP_QUERY_TCAPITALINFO_ADD
	 * 
	 * @IN_BOOK_CODE INT,
	 * @IN_SERIAL_NO INT,
	 * @IN_PRODUCT_ID INT,
	 * @IN_CHECK_FLAG INT --1 前台审核 2 财务审核
	 */
	void listCapitalAddInfo() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * add by tsg 2007-12-18
	 * 
	 * 或许下一个追加的财产资本信息
	 *  
	 */
	boolean getNextCapitalAddInfo() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return
	 * @throws Exception
	 */
	boolean getNextInfo() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 */
	boolean getNext() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 amount。
	 */
	BigDecimal getAmount();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            amount。
	 */
	void setAmount(BigDecimal amount);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 book_code。
	 */
	Integer getBook_code();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            book_code。
	 */
	void setBook_code(Integer book_code);

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
	 * @return 返回 capital_name。
	 */
	String getCapital_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            capital_name。
	 */
	void setCapital_name(String capital_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 capital_type。
	 */
	Integer getCapital_type();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            capital_type。
	 */
	void setCapital_type(Integer capital_type);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 capital_type_name。
	 */
	String getCapital_type_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            capital_type_name。
	 */
	void setCapital_type_name(String capital_type_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 check_flag。
	 */
	Integer getCheck_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            check_flag。
	 */
	void setCheck_flag(Integer check_flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 ci_serial_no。
	 */
	Integer getCi_serial_no();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            ci_serial_no。
	 */
	void setCi_serial_no(Integer ci_serial_no);

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
	 * @return 返回 end_date。
	 */
	Integer getEnd_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            end_date。
	 */
	void setEnd_date(Integer end_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 field_caption。
	 */
	String getField_caption();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            field_caption。
	 */
	void setField_caption(String field_caption);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 field_content。
	 */
	String getField_content();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            field_content。
	 */
	void setField_content(String field_content);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 include_parent。
	 */
	Integer getInclude_parent();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            include_parent。
	 */
	void setInclude_parent(Integer include_parent);

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
	 * @return 返回 post_date。
	 */
	Integer getPost_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            post_date。
	 */
	void setPost_date(Integer post_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 product_id。
	 */
	Integer getProduct_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            product_id。
	 */
	void setProduct_id(Integer product_id);

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
	 * @return 返回 start_date。
	 */
	Integer getStart_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            start_date。
	 */
	void setStart_date(Integer start_date);

}