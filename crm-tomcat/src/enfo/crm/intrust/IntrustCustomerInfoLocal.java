package enfo.crm.intrust;

import java.math.BigDecimal;
import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiFullLocal;
import enfo.crm.vo.CustomerInfoVO;

public interface IntrustCustomerInfoLocal extends IBusiFullLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * �ͻ�������Ϣ���� SP_ADD_TCUSTOMERINFO
	 * 
	 * @IN_BOOK_CODE INT,
	 * @IN_CUST_NAME VARCHAR (100),
	 * @IN_CUST_TEL VARCHAR (20),
	 * @IN_POST_ADDRESS VARCHAR (60),
	 * @IN_POST_CODE VARCHAR(6),
	 * @IN_CARD_TYPE VARCHAR(10),
	 * @IN_CARD_ID VARCHAR (40),
	 * @IN_AGE INT,
	 * @IN_SEX INT,
	 * @IN_O_TEL VARCHAR (20),
	 * @IN_H_TEL VARCHAR (20),
	 * @IN_MOBILE VARCHAR (100),
	 * @IN_BP VARCHAR (20),
	 * @IN_FAX VARCHAR (20),
	 * @IN_E_MAIL VARCHAR (30),
	 * @IN_CUST_TYPE INT,
	 * @IN_TOUCH_TYPE VARCHAR (10),
	 * @IN_CUST_SOURCE VARCHAR (10),
	 * @IN_SUMMARY VARCHAR (200),
	 * @IN_INPUT_MAN INT,
	 * @IN_CUST_NO VARCHAR(8),
	 * @IN_LEGAL_MAN VARCHAR(20),
	 * @IN_LEGAL_ADDRESS VARCHAR(60),
	 * @IN_BIRTHDAY INT,
	 * @IN_POST_ADDRESS2 VARCHAR (60),
	 * @IN_POST_CODE2 VARCHAR(6),
	 * @IN_PRINT_DEPLOY_BILL INT,
	 * @IN_PRINT_POST_INFO INT,
	 * @IN_IS_LINK INT,
	 * @IN_SERVICE_MAN INT = NULL,
	 * @OUT_CUST_ID INT OUTPUT,
	 * @IN_VIP_CARD_ID VARCHAR(20) = NULL, -- VIP�����
	 * @IN_VIP_DATE INT = NULL, -- VIP��������
	 * @IN_HGTZR_BH VARCHAR(20) = NULL, -- �ϸ�Ͷ���˱��
	 * @IN_VOC_TYPE VARCHAR(10) = '' -- ����ְҵ/������ҵ���(1142/2142)
	 * @IN_CARD_VALID_DATE INT �ͻ����֤����Ч����8λ���ڱ�ʾ
	 * @IN_COUNTRY VARCHAR(10) �ͻ�����(9901)
	 * @IN_JG_CUST_TYPE VARCHAR(10) �����ͻ����(9921)������CUST_TYPE=2����ʱ��Ч
	 * @IN_CONTACT_MAN VARCHAR(30) = NULL --�����ͻ���ϵ��
	 * @IN_LINK_TYPE     INT,            -- ��������(1302)
	 * @IN_LINK_GD_MONEY DECIMAL(16,3),  -- �ɶ�Ͷ��������й�˾���
	 */
	void append() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * �ͻ���Ϣ�ϲ����� SP_HB_TCUSTOMERINFO
	 * 
	 * @IN_FROM_CUST_ID INT,
	 * @IN_TO_CUST_ID INT,
	 * @IN_INPUT_MAN INT,
	 * @IN_HB_RGMEONY INT = 1 --1ʱ�ϲ��Ϲ������������������ֶ�ֵ
	 */
	void hb() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ɾ���ͻ�������Ϣ SP_DEL_TCUSTOMERINFO
	 * 
	 * @IN_CUST_ID INT,
	 * @IN_INPUT_MAN INT
	 */
	void delete() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * �ͻ���Ϣ��ѯ_loadһ����¼ SP_QUERY_TCUSTOMERINFO_LOAD
	 * 
	 * @IN_CUST_ID INT
	 */
	void load() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ����load�ڲ�����
	 * 
	 * @return
	 * @throws Exception
	 */
	boolean getNextForLoad() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * �޸Ŀͻ�������Ϣ SP_MODI_TCUSTOMERINFO
	 * 
	 * @IN_CUST_ID INT,
	 * @IN_CUST_NAME VARCHAR(100),
	 * @IN_CUST_TEL VARCHAR(20),
	 * @IN_POST_ADDRESS VARCHAR(60),
	 * @IN_POST_CODE VARCHAR(6),
	 * @IN_CARD_TYPE VARCHAR(10),
	 * @IN_CARD_ID VARCHAR(40),
	 * @IN_AGE INT,
	 * @IN_SEX INT,
	 * @IN_O_TEL VARCHAR(20),
	 * @IN_H_TEL VARCHAR(20),
	 * @IN_MOBILE VARCHAR(100),
	 * @IN_BP VARCHAR(20),
	 * @IN_FAX VARCHAR(20),
	 * @IN_E_MAIL VARCHAR(30),
	 * @IN_CUST_TYPE INT,
	 * @IN_TOUCH_TYPE VARCHAR(10),
	 * @IN_CUST_SOURCE VARCHAR(10),
	 * @IN_SUMMARY VARCHAR(200),
	 * @IN_INPUT_MAN INT,
	 * @IN_CUST_NO VARCHAR(8),
	 * @IN_LEGAL_MAN VARCHAR(20),
	 * @IN_LEGAL_ADDRESS VARCHAR(60),
	 * @IN_BIRTHDAY INT,
	 * @IN_POST_ADDRESS2 VARCHAR(60),
	 * @IN_POST_CODE2 VARCHAR(6),
	 * @IN_PRINT_DEPLOY_BILL INT,
	 * @IN_PRINT_POST_INFO INT,
	 * @IN_IS_LINK INT,
	 * @IN_SERVICE_MAN INT = NULL,
	 * @IN_VIP_CARD_ID VARCHAR(20) = NULL, -- VIP�����
	 * @IN_VIP_DATE INT = NULL, -- VIP��������
	 * @IN_HGTZR_BH VARCHAR(20) = NULL,
	 * @IN_VOC_TYPE VARCHAR(10) = '' -- ����ְҵ/������ҵ���(1142/2142)
	 * @IN_CARD_VALID_DATE INT �ͻ����֤����Ч����8λ���ڱ�ʾ
	 * @IN_COUNTRY VARCHAR(10) �ͻ�����(9901)
	 * @IN_JG_CUST_TYPE VARCHAR(10) �����ͻ����(9921)������CUST_TYPE=2����ʱ��Ч
	 * @IN_CONTACT_MAN VARCHAR(30) = NULL --�����ͻ���ϵ��
	 * @IN_LINK_TYPE     INT,            -- ��������(1302)
	 * @IN_LINK_GD_MONEY DECIMAL(16,3),  -- �ɶ�Ͷ��������й�˾���
	 */
	void save() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * �ͻ���Ϣ���
	 *  
	 */
	void check() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @throws Exception
	 */
	void CheckCardID() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @throws Exception
	 */
	void UpdateCardID() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ������CUST_ID$CUST_NO$CUST_NAME$CHECK_FLAG
	 *  
	 */
	void list() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param custNo
	 * @throws Exception
	 */
	void querybyCustNo(String custNo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param custName
	 * @throws Exception
	 */
	void queryNewCust(String custName) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param sQuery
	 * @throws Exception
	 */
	void query(String sQuery) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ����֤�����롢�ͻ����������ѯ�� modi by jinxr 2007/9/13 ����CUST_NAME����
	 * SP_QUERY_TCUSTOMERINFO_NO
	 * 
	 * @IN_BOOK_CODE INT,
	 * @IN_CUST_NO VARCHAR(8), -- �ͻ����
	 * @IN_CARD_ID VARCHAR(20), -- ֤������
	 * @IN_VIP_CARD_ID VARCHAR(20) = NULL, -- VIP�����
	 * @IN_HGTZR_BH VARCHAR(20) = NULL, -- �ϸ�Ͷ���˱��
	 * @IN_CUST_NAME VARCHAR(80) = NULL, -- �ͻ�����
	 * @IN_IS_LINK INT --�Ƿ�Ϊ������
	 */
	void queryByCustNo() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param flag
	 * @throws Exception
	 */
	void queryCustByName(Integer flag) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * queryByCustNo��ѯ���list��
	 * 
	 * @add by jinxr 2007/9/13
	 */
	boolean getNextForList() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * update by tsg 2007-11-13
	 * 
	 * ��ѯ����֤ͬ����������ƵĿͻ� SP_QUERY_TCUSTOMERINFO_REPEAT_CARD
	 * 
	 * @IN_REPEAT_TIME INT,
	 * @IN_FLAG INT,
	 * @IN_INPUT_MAN INT,
	 * @IN_CARD_ID VARCHAR(100)=NULL
	 * @IN_BOOK_CODE INT = 1
	 *  
	 */
	void queryRepeatCustomers(int repeattime, int flag) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ����queryRepeatCustomers�����ݽ����ȡ
	 */
	boolean getRepeatCustomersNext() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param busi_id
	 * @param start_acct
	 * @param end_acct
	 * @throws Exception
	 */
	void queryCustinfoForPost(String busi_id, String start_acct, String end_acct) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * �ŷ��ӡ��ȡ����
	 */
	boolean getCustPostNext() throws Exception;

	//	������CUST_ID$CUST_NO$CUST_NAME$CUST_SOURCE$CARD_TYPE$CARD_ID$TOUCH_TYPE$MIN_TIMES$MAX_TIMES
	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_QUERY_TCUSTOMERINFO2
	 * @IN_BOOK_CODE INT,
	 * @IN_CUST_ID INT,
	 * @IN_CUST_NO VARCHAR(8),
	 * @IN_CUST_NAME VARCHAR(80),
	 * @IN_CUST_SOURCE VARCHAR(10),
	 * @IN_CARD_TYPE VARCHAR(10),
	 * @IN_CARD_ID VARCHAR(20),
	 * @IN_TOUCH_TYPE VARCHAR(10),
	 * @IN_MIN_TIMES INT,
	 * @IN_MAX_TIMES INT,
	 * @IN_INPUT_MAN INT,
	 * @IN_TEL VARCHAR(20) = NULL,
	 * @IN_ADDRESS VARCHAR(60) = NULL,
	 * @IN_CUST_TYPE INT = NULL,
	 * @IN_PRODUCT_ID INT = NULL,
	 * @IN_FAMILY_NAME VARCHAR(40) = NULL,
	 * @IN_ONLYEMAIL INT = NULL, --�Ƿ�ֻ������EMAIL�ļ�¼ 1-�� 0-��
	 * @IN_CUST_LEVEL VARCHAR(10) = NULL,
	 * @IN_MIN_TOTAL_MONEY MONEY = NULL, --�Ϲ��������
	 * @IN_MAX_TOTAL_MONEY MONEY = NULL, --�Ϲ��������
	 * @IN_ONLY_THISPRODUCT INT = NULL,
	 * @IN_ORDERBY VARCHAR(100) = NULL,
	 * @IN_BIRTHDAY INT = NULL,
	 * @IN_PRINT_DEPLOY_BILL INT = NULL,
	 * @IN_IS_LINK INT = NULL,
	 * @IN_PROV_LEVEL VARCHAR(10) = NULL,
	 * @IN_BEN_AMOUNT_MIN DECIMAL(16,3) = NULL,
	 * @IN_BEN_AMOUNT_MAX DECIMAL(16,3) = NULL,
	 * @IN_VIP_CARD_ID VARCHAR(20) = NULL, -- VIP�����
	 * @IN_HGTZR_BH VARCHAR(20) = NULL -- �ϸ�Ͷ���˱��
	 * @IN_WTR_FLAG INTEGER = 0 --ί���˱�־
	 */
	void advanceQuery(String sQuery) throws Exception;

	//	CREATE PROCEDURE SP_QUERY_TCUSTOMERINFO2 @IN_BOOK_CODE INT,
	//											 @IN_CUST_ID INT,
	//											 @IN_CUST_NO VARCHAR(8),
	//											 @IN_CUST_NAME VARCHAR(100),
	//											 @IN_CUST_SOURCE VARCHAR(10),
	//											 @IN_CARD_TYPE VARCHAR(10),
	//											 @IN_CARD_ID VARCHAR(20),
	//											 @IN_TOUCH_TYPE VARCHAR(10),
	//											 @IN_MIN_TIMES INT,
	//											 @IN_MAX_TIMES INT,
	//											 @IN_INPUT_MAN INT,
	//											 @IN_TEL VARCHAR(20) = NULL,
	//											 @IN_ADDRESS VARCHAR(60) = NULL,
	//											 @IN_CUST_TYPE INT = NULL,
	//											 @IN_PRODUCT_ID INT = NULL,
	/**
	 * @ejb.interface-method view-type = "local"
	 */
	void advanceQuery2() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param sQuery
	 * @return
	 * @throws Exception
	 */
	sun.jdbc.rowset.CachedRowSet advanceQuery_1(String sQuery) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_QUERY_TCUSTOMERINFO2 ��ѯ�������
	 */
	boolean getNextForQuery2() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ��ÿͻ�������Ϣ��ѯ�Ľ����
	 * ��Ӵ˷��������Զ�����ʾ��
	 * @throws Exception
	 */
	List getNextForQuery3() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ��ѯֻȡEmail��MOBILE,BP��CUST_ID
	 */
	boolean getNextForEmail() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 */
	boolean getNext() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param request
	 * @param prefix
	 * @throws Exception
	 */
	void setProperties(javax.servlet.ServletRequest request, String prefix) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * add by tsg 2007-12-09
	 * 
	 * ���ݿͻ������ѯ�ͻ���Ϣ
	 * 
	 * SP_QUERY_TCUSTOMERINFO_SERVICE
	 * 
	 * @IN_BOOK_CODE INT,
	 * @IN_CUST_NO NVARCHAR(8),
	 * @IN_CUST_NAME NVARCHAR(100),
	 * @IN_CARD_ID NVARCHAR(20),
	 * @IN_PRODUCT_ID INT = 0,
	 * @IN_SERVICE_MAN INT
	 *  
	 */

	void listCustByServiceMan() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return
	 * @throws Exception
	 */
	boolean getNextCustByServiceMan() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * add by tsg 2007-12-09
	 * 
	 * �޸Ŀͻ��Ŀͻ�����
	 * 
	 * SP_MODI_TCUSTOMERINFO_SERVICE_MAN
	 * 
	 * @IN_CUST_ID INT,
	 * @IN_SERVICE_MAN INT,
	 * @IN_INPUT_MAN INT
	 */

	void modiServiceManByCustId() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ͬ��crm�ͻ���Ϣ
	 * 
	 * @throws Exception
	 */
	void syncCrmCust() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * add by zhangmy 20100331
	 * 
	 * �޸Ŀͻ�����������
	 * 
	 * SP_MODI_TCUSTOMERINFO_GRADE_LEVEL
	 * 
	 * @IN_CUST_ID INT,
	 * @IN_GRADE_LEVEL VARCHER,
	 * @IN_INPUT_MAN INT
	 */

	void modiGradeLevelByCustId() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ����ͻ���Ϣ����
	 * @param vo
	 * @throws BusiException
	 */
	void importCustomer(CustomerInfoVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * add by qmh 2011-4-21
	 * 
	 * �ϸ�Ͷ���˲�ѯ
	 * 
	 * SP_QUERY_QUALIFIED 
	 * @IN_BOOK_CODE        INTEGER,       --����
	 * @IN_PRODUCT_ID       INTEGER,       --��ƷID
	 * @IN_FLAG             INTEGER        --1.����Ȼ�ˣ�300�����¸��ˣ���2.��ϸ�Ͷ���ˣ�300�����ϸ��˼������� 
	 */
	void queryQualified() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return
	 * @throws Exception
	 */
	boolean getNextQualified() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� age��
	 */
	Integer getAge();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param age
	 *            Ҫ���õ� age��
	 */
	void setAge(Integer age);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� ben_amount��
	 */
	java.math.BigDecimal getBen_amount();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ben_amount
	 *            Ҫ���õ� ben_amount��
	 */
	void setBen_amount(java.math.BigDecimal ben_amount);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� birthday��
	 */
	Integer getBirthday();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param birthday
	 *            Ҫ���õ� birthday��
	 */
	void setBirthday(Integer birthday);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� book_code��
	 */
	java.lang.Integer getBook_code();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param book_code
	 *            Ҫ���õ� book_code��
	 */
	void setBook_code(java.lang.Integer book_code);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� bp��
	 */
	String getBp();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param bp
	 *            Ҫ���õ� bp��
	 */
	void setBp(String bp);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� cancel_man��
	 */
	Integer getCancel_man();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param cancel_man
	 *            Ҫ���õ� cancel_man��
	 */
	void setCancel_man(Integer cancel_man);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� cancel_time��
	 */
	Integer getCancel_time();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param cancel_time
	 *            Ҫ���õ� cancel_time��
	 */
	void setCancel_time(Integer cancel_time);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� card_id��
	 */
	java.lang.String getCard_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param card_id
	 *            Ҫ���õ� card_id��
	 */
	void setCard_id(java.lang.String card_id);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� card_type��
	 */
	java.lang.String getCard_type();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param card_type
	 *            Ҫ���õ� card_type��
	 */
	void setCard_type(java.lang.String card_type);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� card_type_name��
	 */
	java.lang.String getCard_type_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param card_type_name
	 *            Ҫ���õ� card_type_name��
	 */
	void setCard_type_name(java.lang.String card_type_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� card_valid_date��
	 */
	Integer getCard_valid_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param card_valid_date
	 *            Ҫ���õ� card_valid_date��
	 */
	void setCard_valid_date(Integer card_valid_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� check_flag��
	 */
	java.lang.Integer getCheck_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param check_flag
	 *            Ҫ���õ� check_flag��
	 */
	void setCheck_flag(java.lang.Integer check_flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� check_man��
	 */
	Integer getCheck_man();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param check_man
	 *            Ҫ���õ� check_man��
	 */
	void setCheck_man(Integer check_man);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� contact_man��
	 */
	String getContact_man();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param contact_man
	 *            Ҫ���õ� contact_man��
	 */
	void setContact_man(String contact_man);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� contract_bh��
	 */
	String getContract_bh();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param contract_bh
	 *            Ҫ���õ� contract_bh��
	 */
	void setContract_bh(String contract_bh);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� country��
	 */
	String getCountry();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param country
	 *            Ҫ���õ� country��
	 */
	void setCountry(String country);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� current_money��
	 */
	java.math.BigDecimal getCurrent_money();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param current_money
	 *            Ҫ���õ� current_money��
	 */
	void setCurrent_money(java.math.BigDecimal current_money);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� cust_id��
	 */
	java.lang.Integer getCust_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param cust_id
	 *            Ҫ���õ� cust_id��
	 */
	void setCust_id(java.lang.Integer cust_id);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� cust_level��
	 */
	java.lang.String getCust_level();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param cust_level
	 *            Ҫ���õ� cust_level��
	 */
	void setCust_level(java.lang.String cust_level);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� cust_level_name��
	 */
	java.lang.String getCust_level_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param cust_level_name
	 *            Ҫ���õ� cust_level_name��
	 */
	void setCust_level_name(java.lang.String cust_level_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� cust_name��
	 */
	java.lang.String getCust_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param cust_name
	 *            Ҫ���õ� cust_name��
	 */
	void setCust_name(java.lang.String cust_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� cust_no��
	 */
	java.lang.String getCust_no();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param cust_no
	 *            Ҫ���õ� cust_no��
	 */
	void setCust_no(java.lang.String cust_no);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� cust_source��
	 */
	java.lang.String getCust_source();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param cust_source
	 *            Ҫ���õ� cust_source��
	 */
	void setCust_source(java.lang.String cust_source);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� cust_source_name��
	 */
	java.lang.String getCust_source_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param cust_source_name
	 *            Ҫ���õ� cust_source_name��
	 */
	void setCust_source_name(java.lang.String cust_source_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� cust_tel��
	 */
	String getCust_tel();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param cust_tel
	 *            Ҫ���õ� cust_tel��
	 */
	void setCust_tel(String cust_tel);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� cust_type��
	 */
	java.lang.Integer getCust_type();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param cust_type
	 *            Ҫ���õ� cust_type��
	 */
	void setCust_type(java.lang.Integer cust_type);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� cust_type_name��
	 */
	java.lang.String getCust_type_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param cust_type_name
	 *            Ҫ���õ� cust_type_name��
	 */
	void setCust_type_name(java.lang.String cust_type_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� e_mail��
	 */
	String getE_mail();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param e_mail
	 *            Ҫ���õ� e_mail��
	 */
	void setE_mail(String e_mail);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� fact_controller��
	 */
	String getFact_controller();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param fact_controller
	 *            Ҫ���õ� fact_controller��
	 */
	void setFact_controller(String fact_controller);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� fax��
	 */
	String getFax();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param fax
	 *            Ҫ���õ� fax��
	 */
	void setFax(String fax);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� grade_level��
	 */
	String getGrade_level();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param grade_level
	 *            Ҫ���õ� grade_level��
	 */
	void setGrade_level(String grade_level);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� h_tel��
	 */
	String getH_tel();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param h_tel
	 *            Ҫ���õ� h_tel��
	 */
	void setH_tel(String h_tel);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� hgtzr_bh��
	 */
	String getHgtzr_bh();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param hgtzr_bh
	 *            Ҫ���õ� hgtzr_bh��
	 */
	void setHgtzr_bh(String hgtzr_bh);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� input_man��
	 */
	java.lang.Integer getInput_man();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param input_man
	 *            Ҫ���õ� input_man��
	 */
	void setInput_man(java.lang.Integer input_man);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� input_time��
	 */
	java.sql.Timestamp getInput_time();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param input_time
	 *            Ҫ���õ� input_time��
	 */
	void setInput_time(java.sql.Timestamp input_time);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� is_link��
	 */
	Integer getIs_link();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param is_link
	 *            Ҫ���õ� is_link��
	 */
	void setIs_link(Integer is_link);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� jg_cust_type��
	 */
	String getJg_cust_type();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param jg_cust_type
	 *            Ҫ���õ� jg_cust_type��
	 */
	void setJg_cust_type(String jg_cust_type);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� last_rg_date��
	 */
	Integer getLast_rg_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param last_rg_date
	 *            Ҫ���õ� last_rg_date��
	 */
	void setLast_rg_date(Integer last_rg_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� legal_address��
	 */
	String getLegal_address();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param legal_address
	 *            Ҫ���õ� legal_address��
	 */
	void setLegal_address(String legal_address);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� legal_man��
	 */
	String getLegal_man();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param legal_man
	 *            Ҫ���õ� legal_man��
	 */
	void setLegal_man(String legal_man);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� mobile��
	 */
	String getMobile();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param mobile
	 *            Ҫ���õ� mobile��
	 */
	void setMobile(String mobile);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� modi_flag��
	 */
	int getModi_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param modi_flag
	 *            Ҫ���õ� modi_flag��
	 */
	void setModi_flag(int modi_flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� modi_man��
	 */
	Integer getModi_man();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param modi_man
	 *            Ҫ���õ� modi_man��
	 */
	void setModi_man(Integer modi_man);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� modi_time��
	 */
	Integer getModi_time();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param modi_time
	 *            Ҫ���õ� modi_time��
	 */
	void setModi_time(Integer modi_time);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� money_source��
	 */
	String getMoney_source();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param money_source
	 *            Ҫ���õ� money_source��
	 */
	void setMoney_source(String money_source);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� o_tel��
	 */
	String getO_tel();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param o_tel
	 *            Ҫ���õ� o_tel��
	 */
	void setO_tel(String o_tel);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� post_address��
	 */
	String getPost_address();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param post_address
	 *            Ҫ���õ� post_address��
	 */
	void setPost_address(String post_address);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� post_address2��
	 */
	String getPost_address2();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param post_address2
	 *            Ҫ���õ� post_address2��
	 */
	void setPost_address2(String post_address2);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� post_code��
	 */
	String getPost_code();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param post_code
	 *            Ҫ���õ� post_code��
	 */
	void setPost_code(String post_code);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� post_code2��
	 */
	String getPost_code2();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param post_code2
	 *            Ҫ���õ� post_code2��
	 */
	void setPost_code2(String post_code2);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� print_deploy_bill��
	 */
	Integer getPrint_deploy_bill();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param print_deploy_bill
	 *            Ҫ���õ� print_deploy_bill��
	 */
	void setPrint_deploy_bill(Integer print_deploy_bill);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� print_post_info��
	 */
	Integer getPrint_post_info();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param print_post_info
	 *            Ҫ���õ� print_post_info��
	 */
	void setPrint_post_info(Integer print_post_info);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� product_id��
	 */
	Integer getProduct_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param product_id
	 *            Ҫ���õ� product_id��
	 */
	void setProduct_id(Integer product_id);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� prov_level��
	 */
	String getProv_level();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param prov_level
	 *            Ҫ���õ� prov_level��
	 */
	void setProv_level(String prov_level);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� rg_times��
	 */
	Integer getRg_times();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param rg_times
	 *            Ҫ���õ� rg_times��
	 */
	void setRg_times(Integer rg_times);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� service_man��
	 */
	Integer getService_man();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param service_man
	 *            Ҫ���õ� service_man��
	 */
	void setService_man(Integer service_man);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� sex��
	 */
	Integer getSex();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param sex
	 *            Ҫ���õ� sex��
	 */
	void setSex(Integer sex);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� sex_name��
	 */
	String getSex_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param sex_name
	 *            Ҫ���õ� sex_name��
	 */
	void setSex_name(String sex_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� status��
	 */
	java.lang.String getStatus();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param status
	 *            Ҫ���õ� status��
	 */
	void setStatus(java.lang.String status);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� status_name��
	 */
	java.lang.String getStatus_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param status_name
	 *            Ҫ���õ� status_name��
	 */
	void setStatus_name(java.lang.String status_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� summary��
	 */
	String getSummary();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param summary
	 *            Ҫ���õ� summary��
	 */
	void setSummary(String summary);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� total_money��
	 */
	java.math.BigDecimal getTotal_money();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param total_money
	 *            Ҫ���õ� total_money��
	 */
	void setTotal_money(java.math.BigDecimal total_money);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� touch_type��
	 */
	java.lang.String getTouch_type();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param touch_type
	 *            Ҫ���õ� touch_type��
	 */
	void setTouch_type(java.lang.String touch_type);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� touch_type_name��
	 */
	java.lang.String getTouch_type_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param touch_type_name
	 *            Ҫ���õ� touch_type_name��
	 */
	void setTouch_type_name(java.lang.String touch_type_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� vip_card_id��
	 */
	String getVip_card_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vip_card_id
	 *            Ҫ���õ� vip_card_id��
	 */
	void setVip_card_id(String vip_card_id);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� vip_date��
	 */
	Integer getVip_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vip_date
	 *            Ҫ���õ� vip_date��
	 */
	void setVip_date(Integer vip_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� voc_type��
	 */
	String getVoc_type();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param voc_type
	 *            Ҫ���õ� voc_type��
	 */
	void setVoc_type(String voc_type);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� voc_type_name��
	 */
	String getVoc_type_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param voc_type_name
	 *            Ҫ���õ� voc_type_name��
	 */
	void setVoc_type_name(String voc_type_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� wt_flag��
	 */
	java.lang.Integer getWt_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param wt_flag
	 *            Ҫ���õ� wt_flag��
	 */
	void setWt_flag(java.lang.Integer wt_flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� wt_flag_name��
	 */
	java.lang.String getWt_flag_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param wt_flag_name
	 *            Ҫ���õ� wt_flag_name��
	 */
	void setWt_flag_name(java.lang.String wt_flag_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� back_amount��
	 */
	java.math.BigDecimal getBack_amount();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� curr_to_amount��
	 */
	java.math.BigDecimal getCurr_to_amount();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� exchange_amount��
	 */
	java.math.BigDecimal getExchange_amount();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� last_product_name��
	 */
	String getLast_product_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param to_cust_id
	 *            Ҫ���õ� to_cust_id��
	 */
	void setTo_cust_id(Integer to_cust_id);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� link_gd_money��
	 */
	BigDecimal getLink_gd_money();

	/** 
	 * @ejb.interface-method view-type = "local"
	 * @param link_gd_money Ҫ���õ� link_gd_money��
	 */
	void setLink_gd_money(BigDecimal link_gd_money);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� link_type��
	 */
	Integer getLink_type();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param link_type Ҫ���õ� link_type��
	 */
	void setLink_type(Integer link_type);

	/**
	 *  @ejb.interface-method view-type = "local"
	 * @return ���� grade_level_name��
	 */
	String getGrade_level_name();

	/**
	 *  @ejb.interface-method view-type = "local"
	 * @param grade_level_name Ҫ���õ� grade_level_name��
	 */
	void setGrade_level_name(String grade_level_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� complete_flag��
	 */
	Integer getComplete_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param complete_flag Ҫ���õ� complete_flag��
	 */
	void setComplete_flag(Integer complete_flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� ben_date��
	 */
	Integer getBen_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ben_date Ҫ���õ� ben_date��
	 */
	void setBen_date(Integer ben_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� ben_end_date��
	 */
	Integer getBen_end_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ben_end_date Ҫ���õ� ben_end_date��
	 */
	void setBen_end_date(Integer ben_end_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� flag��
	 */
	Integer getFlag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param flag Ҫ���õ� flag��
	 */
	void setFlag(Integer flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� product_code��
	 */
	Integer getProduct_code();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param product_code Ҫ���õ� product_code��
	 */
	void setProduct_code(Integer product_code);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� product_name��
	 */
	String getProduct_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param product_name Ҫ���õ� product_name��
	 */
	void setProduct_name(String product_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� rg_money��
	 */
	BigDecimal getRg_money();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param rg_money Ҫ���õ� rg_money��
	 */
	void setRg_money(BigDecimal rg_money);

}