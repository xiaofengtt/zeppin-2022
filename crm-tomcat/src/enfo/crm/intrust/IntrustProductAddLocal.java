package enfo.crm.intrust;

import enfo.crm.dao.IBusiFullLocal;

public interface IntrustProductAddLocal extends IBusiFullLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 *
	 * SP_MODI_TPRODUCT_INFO
	 * @IN_PRODUCT_ID INT,
	 * @IN_PRODUCT_INFO NTEXT,
	 * @IN_INPUT_MAN INT
	 */
	void saveProduct_info() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 *
	 * SP_MODI_TPRODUCTADD
	 * @IN_SERIAL_NO INT,
	 * @IN_FIELD_CAPTION VARCHAR(24), IN_IS_CHIOSE INT ��ѡ��־1����2����
	 *
	 * @IN_INPUT_MAN INT
	 */
	void save() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 *
	 * SP_MODI_TPRODUCTADDINFO
	 * @IN_SERIAL_NO INT,
	 * @IN_FIELD_VALUE VARCHAR(256),
	 * @IN_INPUT_MAN INT
	 */
	void saveInfo() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 *
	 * SP_ADD_TPRODUCTADDINFO
	 * @IN_PRODUCT_ID INT, IN_DF_SERIAL_NO INT ��ӦҪ�ر�ID
	 * @IN_FIELD_CAPTION VARCHAR(64),
	 * @IN_FIELD_VALUE VARCHAR(256),
	 * @IN_INPUT_MAN INT,
	 * @IN_TB_FLAG INT = 1
	 */
	void appendInfo() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 *
	 * SP_ADD_TPRODUCTADD
	 * @IN_BOOK_CODE INT,
	 * @IN_FIELD_CAPTION VARCHAR(48),
	 * @IN_INPUT_MAN INT,
	 * @IN_TB_FLAG INT = 1
	 *
	 * @IN_IS_CHIOSE INT ��ѡ��־1����2����
	 *
	 * @IN_SUMMARY NVARCHAR(255) = ''//2007/12/10�ֶ�˵�� lzhd
	 */
	void append() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 *
	 * SP_DEL_TPRODUCTADD
	 * @IN_SERIAL_NO INT,
	 * @IN_INPUT_MAN INT
	 */
	void delete() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 *
	 * SP_DEL_TPRODUCTADDINFO
	 * @IN_PRODUCT_ID INT,
	 * @IN_INPUT_MAN INT,
	 * @IN_TB_FLAG INT = 1
	 */
	void deleteAddInfo() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 *
	 * SP_QUERY_TPRODUCTADD
	 * @IN_SERIAL_NO INT,
	 * @IN_BOOK_CODE INT,
	 * @IN_FIELD_CAPTION VARCHAR(64),
	 * @IN_TB_FLAG INT = 1
	 */
	void list() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 *
	 * SP_QUERY_TPRODUCTADDINFO
	 * @IN_SERIAL_NO INT,
	 * @IN_PRODUCT_ID INT,
	 * @IN_FIELD_CAPTION VARCHAR(64),
	 * @IN_TB_FLAG INT = 1
	 */
	void listInfo() throws Exception;

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
	 * @return ���� df_serial_no��
	 */
	Integer getDf_serial_no();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            df_serial_no��
	 */
	void setDf_serial_no(Integer df_serial_no);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� field_caption��
	 */
	String getField_caption();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            field_caption��
	 */
	void setField_caption(String field_caption);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� field_value��
	 */
	String getField_value();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            field_value��
	 */
	void setField_value(String field_value);

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
	 * @return ���� is_chiose��
	 */
	Integer getIs_chiose();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            is_chiose��
	 */
	void setIs_chiose(Integer is_chiose);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� product_id��
	 */
	Integer getProduct_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            product_id��
	 */
	void setProduct_id(Integer product_id);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� product_info��
	 */
	String getProduct_info();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            product_info��
	 */
	void setProduct_info(String product_info);

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
	 * @return ���� summary��
	 */
	String getSummary();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            summary��
	 */
	void setSummary(String summary);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� tb_flag��
	 */
	Integer getTb_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            tb_flag��
	 */
	void setTb_flag(Integer tb_flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� tb_name��
	 */
	String getTb_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            tb_name��
	 */
	void setTb_name(String tb_name);

}