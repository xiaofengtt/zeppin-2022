package enfo.crm.intrust;

import java.math.BigDecimal;
import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiFullLocal;

public interface IntrustContractLocal extends IBusiFullLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return
	 */
	String getProduct_list_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param product_list_id
	 */
	void setProduct_list_id(String product_list_id);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� contract_zjflag��
	 */
	Integer getContract_zjflag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param contract_zjflag Ҫ���õ� contract_zjflag��
	 */
	void setContract_zjflag(Integer contract_zjflag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_ADD_TCONTRACT_NOPRE
	 * @IN_BOOK_CODE INT,
	 * @IN_CUST_ID INT,
	 * @IN_LINK_MAN INT,
	 * @IN_PRODUCT_ID INT,
	 * @IN_RG_MONEY DECIMAL(16,3),
	 * @IN_JK_TYPE VARCHAR(10),
	 * @IN_BANK_ID VARCHAR(10),
	 * @IN_BANK_ACCT VARCHAR(30),
	 * @IN_VALID_PERIOD INT,
	 * @IN_SERVICE_MAN INT,
	 * @IN_SUMMARY2 VARCHAR(200),
	 * @IN_INPUT_MAN INT,
	 * @IN_CONTRACT_BH VARCHAR(16),
	 * @IN_QS_DATE INT,
	 * @IN_JK_DATE INT,
	 * @IN_BANK_SUB_NAME VARCHAR(30),
	 * @IN_ENTITY_PRICE DECIMAL(16,3),--20050524̷�����
	 * @IN_ENTITY_NAME VARCHAR(50),--20050524̷�����
	 * @IN_ENTITY_TYPE VARCHAR(10),--20050524̷�����
	 * @IN_CONTRACT_SUB_BH VARCHAR(30),--20050524̷�����
	 * @IN_CITY_SERIAL_NO INT,
	 * @OUT_SERIAL_NO INT OUTPUT,
	 * @OUT_CONTRACT_BH VARCHAR(16) OUTPUT,
	 * @IN_ZY_FLAG INT = 1,
	 * @IN_TOUCH_TYPE VARCHAR(40) = NULL, --ADD BY SHENKL 2007/03/06
	 * @IN_TOUCH_TYPE_NAME VARCHAR(30) = NULL, --ADD BY SHENKL 2007/03/06
	 * @IN_GAIN_ACCT VARCHAR(60) = NULL --ADD BY JINXR 2007/4/16 �����ʺŻ���
	 * @IN_FEE_JK_TYPE INT =1 ----���ýɿʽ��1�ӱ���ۣ�2���⽻
	 * 
	 * YUZ ��ϴǮ���
	 * @IN_BANK_ACCT_TYPE VARCHAR(10) �����˻�����(9920)
	 * 
	 * //2009-07-29
	 * @IN_START_DATE INT = NULL,
	 * @IN_END_DATE INT = NULL
	 * @IN_BOUNS_FLAG INT = 1 1���Ҹ� 2��ת�ݶ�
	 * @IN_SUB_PRODUCT_ID INT = 0 --�Ӳ�ƷID ADD BY JINXR 2009/11/29
	 */
	void appendNoPre() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_ADD_TCONTRACT
	 * @IN_PRODUCT_ID INT,
	 * @IN_PRE_CODE VARCHAR(16),
	 * @IN_RG_MONEY DECIMAL(16,3),
	 * @IN_JK_TYPE VARCHAR(10),
	 * @IN_BANK_ID VARCHAR(10),
	 * @IN_BANK_ACCT VARCHAR(30),
	 * @IN_VALID_PERIOD INT,
	 * @IN_SERVICE_MAN INT,
	 * @IN_SUMMARY VARCHAR(200),
	 * @IN_INPUT_MAN INT,
	 * @IN_CHECK_MAN INT,
	 * @IN_CONTRACT_BH VARCHAR(16),
	 * @IN_QS_DATE INT,
	 * @IN_JK_DATE INT,
	 * @IN_BANK_SUB_NAME VARCHAR(30),
	 * @IN_CITY_SERIAL_NO INT,
	 * @OUT_SERIAL_NO INT OUTPUT,
	 * @OUT_CONTRACT_BH VARCHAR(16) OUTPUT,
	 * @IN_ZY_FLAG INT = 1,
	 * @IN_TOUCH_TYPE VARCHAR(40) = NULL, --ADD BY SHENKL 2007/03/06
	 * @IN_TOUCH_TYPE_NAME VARCHAR(30) = NULL, --ADD BY SHENKL 2007/03/06
	 * @IN_GAIN_ACCT VARCHAR(60) = NULL --ADD BY JINXR 2007/4/16 �����ʺŻ���
	 * @IN_CONTRACT_SUB_BH NVARCHAR(50) = '' -- ʵ�ʺ�ͬ���
	 * 
	 * YZJ ��ϴǮ ���
	 * @IN_BANK_ACCT_TYPE VARCHAR(10) �����˻�����(9920)
	 * @IN_BONUS_FLAG INT = 1 1���Ҹ� 2��ת�ݶ�
	 * @IN_SUB_PRODUCT_ID INT = 0 --�Ӳ�ƷID ADD BY JINXR 2009/11/29
	 */
	void append() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_DEL_TCONTRACT
	 * @IN_SERIAL_NO INT,
	 * @IN_INPUT_MAN INT
	 */
	void delete() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 */
	void load() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_MODI_TCONTRACT
	 * @IN_SERIAL_NO INT,
	 * @IN_RG_MONEY DECIMAL(16,3),
	 * @IN_JK_TYPE VARCHAR(10),
	 * @IN_BANK_ID VARCHAR(10),
	 * @IN_BANK_ACCT VARCHAR(30),
	 * @IN_VALID_PERIOD INT,
	 * @IN_SERVICE_MAN INT,
	 * @IN_SUMMARY VARCHAR(200),
	 * @IN_INPUT_MAN INT,
	 * @IN_CHECK_MAN INT,
	 * @IN_QS_DATE INT,
	 * @IN_JK_DATE INT,
	 * @IN_LINK_MAN INT,
	 * @IN_PRODUCT_ID INT,
	 * @IN_BANK_SUB_NAME VARCHAR(30),
	 * @IN_NEW_CONTRACT_BH VARCHAR(16),
	 * @IN_ENTITY_PRICE DECIMAL(16,3), --20050524̷�����
	 * @IN_ENTITY_NAME VARCHAR(50), --20050524̷�����
	 * @IN_ENTITY_TYPE VARCHAR(10), --20050524̷�����
	 * @IN_CONTRACT_SUB_BH VARCHAR(30), --20050524̷�����
	 * @IN_CITY_SERIAL_NO INT,
	 * @IN_TOUCH_TYPE VARCHAR(40) = NULL, --ADD BY SHENKL 2007/03/06
	 * @IN_TOUCH_TYPE_NAME VARCHAR(30) = NULL, --ADD BY SHENKL 2007/03/06
	 * @IN_GAIN_ACCT VARCHAR(60) = NULL --ADD BY JINXR 2007/4/16 �����ʺŻ���
	 * 
	 * @IN_BANK_ACCT_TYPE VARCHAR(10) �����˻�����(9920)
	 * 
	 * //2009-07-29
	 * @IN_START_DATE INT = NULL,
	 * @IN_END_DATE INT = NULL
	 * @IN_BOUNS_FLAG INT = 1 1���Ҹ� 2��ת�ݶ�
	 */
	void save() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_CHECK_TCONTRACT_ENTIRY
	 * @IN_SERIAL_NO INT,
	 * @IN_INPUT_MAN INT
	 */
	void checkEntity() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_CHECK_TCONTRACT_ENTIRY_PZ
	 * @IN_SERIAL_NO INT,
	 * @IN_SUB_CODE1 VARCHAR(10),
	 * @IN_INPUT_MAN INT,
	 * @IN_POST_DATE INT = NULL,
	 * @IN_FLAG INT = 1 --1��ͨ�� 2����ͨ��
	 */
	void checkEntityPZ() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * �ͻ��Ϲ���ͬ��˻ָ� SP_CHECK_TCONTRACT_BACK
	 * @IN_SERIAL_NO INT,
	 * @IN_INPUT_MAN INT
	 */
	void checkContract() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_QUERY_TCONTRACT_BYCUSTID
	 * @IN_BOOK_CODE INT,
	 * @IN_PRODUCT_ID INT,
	 * @IN_CUST_ID INT
	 */
	void queryContractByCustID() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ��ͬ��ʧ SP_LOSE_TCONTRACT
	 * @IN_PRODUCT_ID INT,
	 * @IN_CONTRACT_BH VARCHAR(16),
	 * @IN_INPUT_MAN INT
	 */
	void appendLose() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ��ͬ��� SP_UNLOSE_TCONTRACT
	 * @IN_PRODUCT_ID INT,
	 * @IN_CONTRACT_BH VARCHAR(16),
	 * @IN_INPUT_MAN INT
	 */
	void unLose() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ��ͬ���� SP_FROZEN_TCONTRACT
	 * @IN_PRODUCT_ID INT,
	 * @IN_CONTRACT_BH VARCHAR(16),
	 * @IN_INPUT_MAN INT
	 */
	void Frozen() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ��ͬ�ⶳ SP_UNFROZEN_TCONTRACT
	 * @IN_PRODUCT_ID INT,
	 * @IN_CONTRACT_BH VARCHAR(16),
	 * @IN_INPUT_MAN INT
	 */
	void UnFrozen() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ��ͬ���Ḵ�� SP_CHECK_FROZEN_TCONTRACT
	 * @IN_PRODUCT_ID INT,
	 * @IN_CONTRACT_BH VARCHAR(16),
	 * @IN_CHECK_FLAG INT,
	 * @IN_INPUT_MAN INT
	 */
	void checkFrozen(int flag) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ��ͬ�ⶳ���� SP_CHECK_UNFROZEN_TCONTRACT
	 * @IN_PRODUCT_ID INT,
	 * @IN_CONTRACT_BH VARCHAR(16),
	 * @IN_CHECK_FLAG INT,
	 * @IN_INPUT_MAN INT
	 */
	void checkUnFrozen(int flag) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ��ͬ��ǩ SP_CONTINUE_TCONTRACT
	 * @IN_PRODUCT_ID INT,
	 * @IN_CONTRACT_BH VARCHAR(16),
	 * @IN_END_DATE INT,
	 * @IN_RG_MONEY DECIMAL(16,3),
	 * @IN_QS_DATE INT,
	 * @IN_SUMMARY VARCHAR(200),
	 * @IN_INPUT_MAN INT,
	 * @IN_CHECK_MAN INT
	 */
	void appendContinueContract() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ��ͬ��ֹ SP_STOP_TCONTRACT
	 * @IN_PRODUCT_ID INT,
	 * @IN_CONTRACT_BH VARCHAR(16),
	 * @IN_INPUT_MAN INT,
	 * @IN_TERMINATE_DATE INT
	 */
	void stopContract() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ��ͬ��ʧ���� SP_CHECK_LOSE_TCONTRACT
	 * @IN_PRODUCT_ID INT,
	 * @IN_CONTRACT_BH VARCHAR(16),
	 * @IN_CHECK_FLAG INT,
	 * @IN_INPUT_MAN INT
	 */
	void checkLose(int flag) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ��ͬ��Ҹ��� SP_CHECK_UNLOSE_TCONTRACT
	 * @IN_PRODUCT_ID INT,
	 * @IN_CONTRACT_BH VARCHAR(16),
	 * @IN_CHECK_FLAG INT,
	 * @IN_INPUT_MAN INT
	 */
	void checkUnlose(int flag) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ��ͬ��ǩ���� SP_CHECK_CONTINUE_TCONTRACT
	 * @IN_PRODUCT_ID INT,
	 * @IN_CONTRACT_BH VARCHAR(16),
	 * @IN_CHECK_FLAG INT,
	 * @IN_INPUT_MAN INT
	 */
	void checkContinueContract(int flag) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_CHECK_STOP_TCONTRACT
	 * @IN_PRODUCT_ID INT,
	 * @IN_CONTRACT_BH VARCHAR(16),
	 * @IN_CHECK_FLAG INT,
	 * @IN_INPUT_MAN INT
	 */
	void checkStopContract(int flag) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ������product_id$product_code$contract_bh
	 *  
	 */
	void list() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_QUERY_TCONTRACT_LOSE
	 * @IN_BOOK_CODE INT,
	 * @IN_PRODUCT_ID INT,
	 * @IN_CONTRACT_BH VARCHAR(16),
	 * @IN_INPUT_MAN INT = NULL,
	 * @IN_CUST_NO VARCHAR(8)= '',
	 * @IN_CUST_NAME VARCHAR(100)= ''
	 * @IN_CONTRACT_SUB_BH VARCHAR(50)
	 */
	void queryLoseValideContract(String sQuery) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_QUERY_TCONTRACT_END
	 * @IN_BOOK_CODE INT,
	 * @IN_PRODUCT_ID INT,
	 * @IN_CONTRACT_BH VARCHAR(13),
	 * @IN_BEGIN_DATE INT,
	 * @IN_END_DATE INT,
	 * @IN_INPUT_MAN INT
	 */
	void queryEndContract(Integer productid, String contractsubbh, Integer startdate, Integer enddate) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param sQuery
	 * @throws Exception
	 */
	void queryNewContract(String sQuery) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param sQuery
	 * @throws Exception
	 */
	void query(String sQuery) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_QUERY_TCONTRACT_COMM
	 * 
	 * @IN_BOOK_CODE INT,
	 * @IN_PRODUCT_ID INT,
	 * @IN_CONTRACT_BH VARCHAR(16),
	 * @IN_CUST_NAME VARCHAR(100),
	 * @IN_CARD_ID VARCHAR(40),
	 * @IN_INPUT_MAN INT,
	 * @IN_PRE_FLAG INT = 1,
	 * @IN_MIN_TO_MONEY DEC(16,3)=0,
	 * @IN_MAX_TO_MONEY DEC(16,3)=0,
	 * @IN_PRODUCT_NAME VARCHAR(100)= NULL,
	 * @IN_CHECK_FLAG INT = NULL --��˱�־(1��δ��� 2������� 0��ȫ��) add by shenkl
	 *                2007/03/06
	 * @IN_CONTRACT_SUB_BH NVARCHAR(50) = '', -- ��ͬ��Ų�ѯ  20080104 by wangc
	 * @IN_CONTRACT_ZJFLAG  INT  =0            --��ͬ���ʣ�1 �ʽ��ͬ 2 �Ʋ���ͬ 0 ȫ��
	 */
	void queryPurchanseContract(String custName, String cardId) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_QUERY_TCONTRACT_ENTITY
	 * @IN_BOOK_CODE INT,
	 * @IN_PRODUCT_ID INT,
	 * @IN_PRODUCT_CODE VARCHAR(6),
	 * @IN_CONTRACT_BH VARCHAR(16),
	 * @IN_CHECK_FLAG INT,
	 * @IN_INPUT_MAN INT,
	 * @IN_ENTITY_TYPE VARCHAR(10),
	 * @IN_CUST_NAME VARCHAR(10)=NULL,
	 * @IN_PZ_FLAG INT=NULL
	 * @IN_CONTRACT_SUB_BH NVARCHAR(50) = '' -- ��ͬ���
	 *  
	 */
	void queryEntityContract() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_QUERY_TCONTRACT
	 * @IN_BOOK_CODE INT,
	 * @IN_PRODUCT_ID INT,
	 * @IN_PRODUCT_CODE VARCHAR(6),
	 * @IN_CONTRACT_BH VARCHAR(16),
	 * @IN_CHECK_FLAG INT, -- 21 ��ͬ��ֹ�����ò�ѯ
	 * @IN_INPUT_MAN INT
	 * @IN_CONTRACT_SUB_BH NVARCHAR(50) = '' -- ��ͬ���
	 *  
	 */
	void queryContract(String sQuery, int flag) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @throws Exception
	 */
	void queryContract() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_QUERY_TCONTRACT_CUST_ID
	 * @IN_CUST_ID INT,
	 * @IN_INPUT_MAN INT = NULL
	 */
	void listByCustId(Integer cust_id) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_QUERY_LOSE_TCONTRACT
	 * @IN_BOOK_CODE INT,
	 * @IN_PRODUCT_ID INT,
	 * @IN_CONTRACT_BH VARCHAR(16),
	 * @IN_CHECK_FLAG INT,
	 * @IN_INPUT_MAN INT = NULL,
	 * @IN_CUST_NO VARCHAR(8)= '',
	 * @IN_CUST_NAME VARCHAR(100)= ''
	 * @IN_CONTRACT_SUB_BH NVARCHAR(50) = '' -- ��ͬ���
	 */
	//��Ʒ���$��ͬ���$���˱�־��4���δ���˺�δ�Ƴ����˵� 0 ������У�
	void queryLose(String sQuery) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_QUERY_UNLOSE_TCONTRACT
	 * @IN_BOOK_CODE INT,
	 * @IN_PRODUCT_ID INT,
	 * @IN_CONTRACT_BH VARCHAR(16),
	 * @IN_CHECK_FLAG INT,
	 * @IN_INPUT_MAN INT = NULL // ��Ʒ���$��ͬ���$���˱�־��4���δ���˺�δ�Ƴ����˵� 0 ������У�
	 */
	void queryUnLose(String sQuery) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_QUERY_TCONTRACT_FROZEN
	 * @IN_BOOK_CODE INT,
	 * @IN_PRODUCT_ID INT,
	 * @IN_CONTRACT_BH VARCHAR(16),
	 * @IN_INPUT_MAN INT = NULL,
	 * @IN_CUST_NO VARCHAR(8)= '',
	 * @IN_CUST_NAME VARCHAR(100)= ''
	 */
	void queryFrozen() throws Exception;

	//flagΪ�����־
	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_QUERY_FROZEN_TCONTRACT
	 * @IN_BOOK_CODE INT,
	 * @IN_PRODUCT_ID INT,
	 * @IN_CONTRACT_BH VARCHAR(16),
	 * @IN_CHECK_FLAG INT,
	 * @IN_INPUT_MAN INT = NULL,
	 * @IN_CUST_NO VARCHAR(8)= '',
	 * @IN_CUST_NAME VARCHAR(100)= ''
	 */
	void queryUnFrozen(int flag) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_QUERY_UNFROZEN_TCONTRACT
	 * @IN_BOOK_CODE INT,
	 * @IN_PRODUCT_ID INT,
	 * @IN_CONTRACT_BH VARCHAR(16),
	 * @IN_CHECK_FLAG INT,
	 * @IN_INPUT_MAN INT = NULL
	 */
	void queryUnFrozenCheck(int flag) throws Exception;

	//	��Ʒ���$��ͬ���$���˱�־��1���δ���� 0 ������У�
	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_QUERY_CONTINUE_TCONTRACT
	 * @IN_BOOK_CODE INT,
	 * @IN_PRODUCT_ID INT,
	 * @IN_CONTRACT_BH VARCHAR(16),
	 * @IN_CHECK_FLAG INT,
	 * @IN_INPUT_MAN INT,
	 * @IN_CUST_NO VARCHAR(8)= '',
	 * @IN_CUST_NAME VARCHAR(100)= ''
	 */
	void queryContinueContract(String sQuery) throws Exception;

	//	��Ʒ���$��ͬ���$���˱�־��4���δ���˺�δ�Ƴ����˵� 0 ������У�
	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_QUERY_STOP_TCONTRACT
	 * @IN_BOOK_CODE INT,
	 * @IN_PRODUCT_ID INT,
	 * @IN_CONTRACT_BH VARCHAR(16),
	 * @IN_CHECK_FLAG INT,
	 * @IN_INPUT_MAN INT = NULL,
	 * @IN_CUST_NO VARCHAR(8)= '',
	 * @IN_CUST_NAME VARCHAR(100)= ''
	 */
	void queryStop(String sQuery) throws Exception;

	//	�ͻ��Ϲ���ͬ��ѯ
	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_QUERY_TCONTRACT_ALL
	 * @IN_BOOK_CODE INT,
	 * @IN_PRODUCT_ID INT,
	 * @IN_CONTRACT_BH VARCHAR(16),
	 * @IN_CUST_NAME VARCHAR(100),
	 * @IN_CARD_ID VARCHAR(40),
	 * @IN_INPUT_MAN INT,
	 * @IN_ONLY_THISPRODUCT INT = NULL,
	 * @IN_CUST_TYPE INT = NULL,
	 * @IN_PROV_LEVEL VARCHAR(10) = NULL,
	 * @IN_CUST_NO VARCHAR(8) = '',
	 * @IN_MIN_RG_MONEY DEC(16,3) = NULL,
	 * @IN_MAX_RG_MONEY DEC(16,3) = NULL,
	 * @IN_PRODUCT_NAME VARCHAR(100)=NULL,
	 * @IN_CITY_NAME VARCHAR(100)=NULL,
	 * @IN_SERVICE_MAN INT=NULL
	 */
	void listAll() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_QUERY_TCONTRACTLIST
	 * @IN_CONTRACT_ID INT
	 */
	void listHistory() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_QUERY_TCONTRACTLIST_DETAIL
	 * @IN_PRODUCT_ID INT,
	 * @IN_CONTRACT_BH VARCHAR(16),
	 * @IN_HT_STATUS VARCHAR(10),
	 * @IN_LIST_ID INT = 0
	 */
	void listContractList() throws Exception;

	//����purchase3.jsp��
	/**
	 * @ejb.interface-method view-type = "local"
	 */
	boolean getNext3() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 */
	boolean getNext() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return
	 * @throws Exception
	 */
	boolean getNextALL() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return
	 * @throws Exception
	 */
	boolean getQueryContractByCustIDNext() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local" 
	 * 
	 * ����Ϲ���ѯ��Ϣ��ѯ�Ľ���� ��Ӵ˷��������Զ�����ʾ��
	 * @throws Exception
	 */
	List getNextALList() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return
	 * @throws Exception
	 */
	boolean getNext1() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return
	 * @throws Exception
	 */
	boolean getNextList() throws Exception;

	/**
	 *  @ejb.interface-method view-type = "local"
	 * 
	 * ��ͬ�����ϸ��ѯ����List����
	 * @return
	 * @throws Exception
	 */
	List getNextLists() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return
	 * @throws Exception
	 */
	boolean getNextList2() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return
	 * @throws Exception
	 */
	boolean getEndContractNext() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_CHECK_TCONTRACT
	 * @IN_SERIAL_NO INT,
	 * @IN_INPUT_MAN INT
	 */
	void check() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_QUERY_TPRECONTRACT_MONEY
	 * @IN_BOOK_CODE int,
	 * @IN_PRODUCT_ID int,
	 * @IN_PRE_CODE varchar(16),
	 * @OUT_PRE_MONEY DECIMAL(16,3) OUTPUT
	 */
	java.math.BigDecimal getPrecontract_premoney(String precode) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_REGEDIT_CONTRACT_BH
	 * @IN_BOOK_CODE INT,
	 * @IN_CONTRACT_TYPE INT, --��ͬ���ͣ�1,�Ϲ���ͬ��2 �깺��ͬ 3����ͨ�����ͬ�� 4�����޺�ͬ�� 5��Ͷ�ʺ�ͬ
	 * @IN_PRODUCT_ID INT, --��ƷID
	 * @IN_CONTRACT_SUB_BH NVARCHAR(50),
	 * @OUT_EXISTS_FLAG INT OUTPUT --1 ���� 0 ������ ����
	 *  
	 */
	Integer isExistSameContractBH(Integer contract_type) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 20040120 By CaiYuan ��ӡ�ŷ�ҳ����ݿͻ���ӡ��������в�Ʒ������
	 * 
	 * @param cust_id
	 * @throws Exception
	 */
	void Print(Integer cust_id) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param input_man
	 * @throws Exception
	 */
	void list2(Integer input_man) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return
	 * @throws Exception
	 */
	boolean getNextPrint() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ��ѯ
	 * 
	 * @param product_name
	 * @param contract_bh
	 * @param cust_name
	 * @param dealFlag �����־��1��ʾ�������ۣ�2����������۸���
	 * @throws Exception
	 */
	void queryOldTemp(String product_name, String contract_bh, String cust_name, int dealFlag) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return
	 * @throws Exception
	 */
	boolean getOldTempNext() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� acct_user��
	 */
	java.lang.String getAcct_user();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            acct_user��
	 */
	void setAcct_user(java.lang.String acct_user);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� bank_acct��
	 */
	java.lang.String getBank_acct();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            bank_acct��
	 */
	void setBank_acct(java.lang.String bank_acct);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� bank_acct_type��
	 */
	String getBank_acct_type();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            bank_acct_type��
	 */
	void setBank_acct_type(String bank_acct_type);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� bank_id��
	 */
	java.lang.String getBank_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            bank_id��
	 */
	void setBank_id(java.lang.String bank_id);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� bank_name��
	 */
	java.lang.String getBank_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            bank_name��
	 */
	void setBank_name(java.lang.String bank_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� bank_sub_name��
	 */
	java.lang.String getBank_sub_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            bank_sub_name��
	 */
	void setBank_sub_name(java.lang.String bank_sub_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� ben_rec_no��
	 */
	int getBen_rec_no();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            ben_rec_no��
	 */
	void setBen_rec_no(int ben_rec_no);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� benifitor_address��
	 */
	String getBenifitor_address();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            benifitor_address��
	 */
	void setBenifitor_address(String benifitor_address);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� benifitor_card_id��
	 */
	java.lang.String getBenifitor_card_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            benifitor_card_id��
	 */
	void setBenifitor_card_id(java.lang.String benifitor_card_id);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� benifitor_card_type_name��
	 */
	String getBenifitor_card_type_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            benifitor_card_type_name��
	 */
	void setBenifitor_card_type_name(String benifitor_card_type_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� benifitor_contact_methed��
	 */
	String getBenifitor_contact_methed();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            benifitor_contact_methed��
	 */
	void setBenifitor_contact_methed(String benifitor_contact_methed);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� benifitor_cust_type_name��
	 */
	String getBenifitor_cust_type_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            benifitor_cust_type_name��
	 */
	void setBenifitor_cust_type_name(String benifitor_cust_type_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� benifitor_Email��
	 */
	String getBenifitor_Email();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            benifitor_Email��
	 */
	void setBenifitor_Email(String benifitor_Email);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� benifitor_handset_number��
	 */
	String getBenifitor_handset_number();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            benifitor_handset_number��
	 */
	void setBenifitor_handset_number(String benifitor_handset_number);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� benifitor_lpr��
	 */
	String getBenifitor_lpr();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            benifitor_lpr��
	 */
	void setBenifitor_lpr(String benifitor_lpr);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� benifitor_name��
	 */
	java.lang.String getBenifitor_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            benifitor_name��
	 */
	void setBenifitor_name(java.lang.String benifitor_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� benifitor_post��
	 */
	String getBenifitor_post();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            benifitor_post��
	 */
	void setBenifitor_post(String benifitor_post);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� benifitor_telephone��
	 */
	String getBenifitor_telephone();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            benifitor_telephone��
	 */
	void setBenifitor_telephone(String benifitor_telephone);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� bonus_flag��
	 */
	Integer getBonus_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            bonus_flag��
	 */
	void setBonus_flag(Integer bonus_flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� book_code��
	 */
	java.lang.Integer getBook_code();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            book_code��
	 */
	void setBook_code(java.lang.Integer book_code);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� card_id��
	 */
	java.lang.String getCard_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            card_id��
	 */
	void setCard_id(java.lang.String card_id);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� check_flag��
	 */
	java.lang.Integer getCheck_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            check_flag��
	 */
	void setCheck_flag(java.lang.Integer check_flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� check_flag_name��
	 */
	java.lang.String getCheck_flag_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            check_flag_name��
	 */
	void setCheck_flag_name(java.lang.String check_flag_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� check_man��
	 */
	java.lang.Integer getCheck_man();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            check_man��
	 */
	void setCheck_man(java.lang.Integer check_man);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� check_time��
	 */
	java.sql.Timestamp getCheck_time();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            check_time��
	 */
	void setCheck_time(java.sql.Timestamp check_time);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� city_name��
	 */
	String getCity_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            city_name��
	 */
	void setCity_name(String city_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� city_serialno��
	 */
	java.lang.Integer getCity_serialno();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            city_serialno��
	 */
	void setCity_serialno(java.lang.Integer city_serialno);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� contract_bh��
	 */
	java.lang.String getContract_bh();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            contract_bh��
	 */
	void setContract_bh(java.lang.String contract_bh);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� contract_id��
	 */
	java.lang.Integer getContract_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            contract_id��
	 */
	void setContract_id(java.lang.Integer contract_id);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� contract_sub_bh��
	 */
	String getContract_sub_bh();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            contract_sub_bh��
	 */
	void setContract_sub_bh(String contract_sub_bh);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� currency_id��
	 */
	java.lang.String getCurrency_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            currency_id��
	 */
	void setCurrency_id(java.lang.String currency_id);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� currency_name��
	 */
	java.lang.String getCurrency_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            currency_name��
	 */
	void setCurrency_name(java.lang.String currency_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� cust_id��
	 */
	java.lang.Integer getCust_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            cust_id��
	 */
	void setCust_id(java.lang.Integer cust_id);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� cust_name��
	 */
	java.lang.String getCust_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            cust_name��
	 */
	void setCust_name(java.lang.String cust_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� cust_no��
	 */
	java.lang.String getCust_no();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            cust_no��
	 */
	void setCust_no(java.lang.String cust_no);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� cust_type��
	 */
	java.lang.Integer getCust_type();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            cust_type��
	 */
	void setCust_type(java.lang.Integer cust_type);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� df_cust_id��
	 */
	Integer getDf_cust_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            df_cust_id��
	 */
	void setDf_cust_id(Integer df_cust_id);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� end_date��
	 */
	java.lang.Integer getEnd_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            end_date��
	 */
	void setEnd_date(java.lang.Integer end_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� entity_name��
	 */
	String getEntity_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            entity_name��
	 */
	void setEntity_name(String entity_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� entity_price��
	 */
	java.math.BigDecimal getEntity_price();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            entity_price��
	 */
	void setEntity_price(java.math.BigDecimal entity_price);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� entity_type��
	 */
	String getEntity_type();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            entity_type��
	 */
	void setEntity_type(String entity_type);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� entity_type_name��
	 */
	String getEntity_type_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            entity_type_name��
	 */
	void setEntity_type_name(String entity_type_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� fee_jk_type��
	 */
	int getFee_jk_type();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            fee_jk_type��
	 */
	void setFee_jk_type(int fee_jk_type);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� gain_acct��
	 */
	java.lang.String getGain_acct();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            gain_acct��
	 */
	void setGain_acct(java.lang.String gain_acct);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� ht_status��
	 */
	java.lang.String getHt_status();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            ht_status��
	 */
	void setHt_status(java.lang.String ht_status);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� ht_status_name��
	 */
	java.lang.String getHt_status_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            ht_status_name��
	 */
	void setHt_status_name(java.lang.String ht_status_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� input_man��
	 */
	java.lang.Integer getInput_man();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            input_man��
	 */
	void setInput_man(java.lang.Integer input_man);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� input_time��
	 */
	java.sql.Timestamp getInput_time();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            input_time��
	 */
	void setInput_time(java.sql.Timestamp input_time);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� jk_date��
	 */
	java.lang.Integer getJk_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            jk_date��
	 */
	void setJk_date(java.lang.Integer jk_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� jk_status��
	 */
	java.lang.Integer getJk_status();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            jk_status��
	 */
	void setJk_status(java.lang.Integer jk_status);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� jk_total_money��
	 */
	BigDecimal getJk_total_money();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            jk_total_money��
	 */
	void setJk_total_money(BigDecimal jk_total_money);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� jk_type��
	 */
	java.lang.String getJk_type();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            jk_type��
	 */
	void setJk_type(java.lang.String jk_type);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� jk_type_name��
	 */
	java.lang.String getJk_type_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            jk_type_name��
	 */
	void setJk_type_name(java.lang.String jk_type_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� last_trans_date��
	 */
	java.lang.Integer getLast_trans_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            last_trans_date��
	 */
	void setLast_trans_date(java.lang.Integer last_trans_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� link_man��
	 */
	java.lang.Integer getLink_man();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            link_man��
	 */
	void setLink_man(java.lang.Integer link_man);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� list_id��
	 */
	java.lang.Integer getList_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            list_id��
	 */
	void setList_id(java.lang.Integer list_id);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� max_rg_money��
	 */
	java.math.BigDecimal getMax_rg_money();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            max_rg_money��
	 */
	void setMax_rg_money(java.math.BigDecimal max_rg_money);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� min_rg_money��
	 */
	java.math.BigDecimal getMin_rg_money();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            min_rg_money��
	 */
	void setMin_rg_money(java.math.BigDecimal min_rg_money);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� only_thisproduct��
	 */
	java.lang.Integer getOnly_thisproduct();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            only_thisproduct��
	 */
	void setOnly_thisproduct(java.lang.Integer only_thisproduct);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� pre_code��
	 */
	java.lang.String getPre_code();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            pre_code��
	 */
	void setPre_code(java.lang.String pre_code);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� pre_flag��
	 */
	java.lang.Integer getPre_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            pre_flag��
	 */
	void setPre_flag(java.lang.Integer pre_flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� pre_money��
	 */
	java.math.BigDecimal getPre_money();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            pre_money��
	 */
	void setPre_money(java.math.BigDecimal pre_money);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� product_code��
	 */
	java.lang.String getProduct_code();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            product_code��
	 */
	void setProduct_code(java.lang.String product_code);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� product_id��
	 */
	java.lang.Integer getProduct_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            product_id��
	 */
	void setProduct_id(java.lang.Integer product_id);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� product_name��
	 */
	java.lang.String getProduct_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            product_name��
	 */
	void setProduct_name(java.lang.String product_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� property_name��
	 */
	String getProperty_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            property_name��
	 */
	void setProperty_name(String property_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� property_type_name��
	 */
	String getProperty_type_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            property_type_name��
	 */
	void setProperty_type_name(String property_type_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� prov_level��
	 */
	String getProv_level();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            prov_level��
	 */
	void setProv_level(String prov_level);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� pz_flag��
	 */
	java.lang.Integer getPz_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            pz_flag��
	 */
	void setPz_flag(java.lang.Integer pz_flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� qs_date��
	 */
	java.lang.Integer getQs_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            qs_date��
	 */
	void setQs_date(java.lang.Integer qs_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� rg_fee_money��
	 */
	BigDecimal getRg_fee_money();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            rg_fee_money��
	 */
	void setRg_fee_money(BigDecimal rg_fee_money);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� rg_fee_rate��
	 */
	BigDecimal getRg_fee_rate();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            rg_fee_rate��
	 */
	void setRg_fee_rate(BigDecimal rg_fee_rate);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� rg_money��
	 */
	java.math.BigDecimal getRg_money();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            rg_money��
	 */
	void setRg_money(java.math.BigDecimal rg_money);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� rg_money2��
	 */
	BigDecimal getRg_money2();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            rg_money2��
	 */
	void setRg_money2(BigDecimal rg_money2);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� self_ben_flag��
	 */
	int getSelf_ben_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            self_ben_flag��
	 */
	void setSelf_ben_flag(int self_ben_flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� serial_no��
	 */
	java.lang.Integer getSerial_no();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            serial_no��
	 */
	void setSerial_no(java.lang.Integer serial_no);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� service_man��
	 */
	java.lang.Integer getService_man();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            service_man��
	 */
	void setService_man(java.lang.Integer service_man);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� start_date��
	 */
	java.lang.Integer getStart_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            start_date��
	 */
	void setStart_date(java.lang.Integer start_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� str_jk_date��
	 */
	java.lang.String getStr_jk_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            str_jk_date��
	 */
	void setStr_jk_date(java.lang.String str_jk_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� str_qs_date��
	 */
	java.lang.String getStr_qs_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            str_qs_date��
	 */
	void setStr_qs_date(java.lang.String str_qs_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� str_valid_period��
	 */
	java.lang.String getStr_valid_period();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            str_valid_period��
	 */
	void setStr_valid_period(java.lang.String str_valid_period);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� sub_product_id��
	 */
	Integer getSub_product_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            sub_product_id��
	 */
	void setSub_product_id(Integer sub_product_id);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� summary��
	 */
	java.lang.String getSummary();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            summary��
	 */
	void setSummary(java.lang.String summary);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� temp_end_date��
	 */
	java.lang.Integer getTemp_end_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            temp_end_date��
	 */
	void setTemp_end_date(java.lang.Integer temp_end_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� temp_rg_money��
	 */
	java.math.BigDecimal getTemp_rg_money();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            temp_rg_money��
	 */
	void setTemp_rg_money(java.math.BigDecimal temp_rg_money);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� terminate_date��
	 */
	java.lang.Integer getTerminate_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            terminate_date��
	 */
	void setTerminate_date(java.lang.Integer terminate_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� to_amount��
	 */
	java.math.BigDecimal getTo_amount();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            to_amount��
	 */
	void setTo_amount(java.math.BigDecimal to_amount);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� to_money��
	 */
	java.math.BigDecimal getTo_money();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            to_money��
	 */
	void setTo_money(java.math.BigDecimal to_money);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� touch_type��
	 */
	String getTouch_type();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            touch_type��
	 */
	void setTouch_type(String touch_type);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� touch_type_name��
	 */
	String getTouch_type_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            touch_type_name��
	 */
	void setTouch_type_name(String touch_type_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� trans_flag��
	 */
	java.lang.String getTrans_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            trans_flag��
	 */
	void setTrans_flag(java.lang.String trans_flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� trans_flag_name��
	 */
	java.lang.String getTrans_flag_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            trans_flag_name��
	 */
	void setTrans_flag_name(java.lang.String trans_flag_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� valid_period��
	 */
	java.lang.Integer getValid_period();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            valid_period��
	 */
	void setValid_period(java.lang.Integer valid_period);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� wtr__post��
	 */
	String getWtr__post();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            wtr__post��
	 */
	void setWtr__post(String wtr__post);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� wtr_address��
	 */
	String getWtr_address();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            wtr_address��
	 */
	void setWtr_address(String wtr_address);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� wtr_card_type_name��
	 */
	String getWtr_card_type_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            wtr_card_type_name��
	 */
	void setWtr_card_type_name(String wtr_card_type_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� wtr_contact_methed��
	 */
	String getWtr_contact_methed();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            wtr_contact_methed��
	 */
	void setWtr_contact_methed(String wtr_contact_methed);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� wtr_cust_type_name��
	 */
	String getWtr_cust_type_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            wtr_cust_type_name��
	 */
	void setWtr_cust_type_name(String wtr_cust_type_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� wtr_Email��
	 */
	String getWtr_Email();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            wtr_Email��
	 */
	void setWtr_Email(String wtr_Email);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� wtr_handset_number��
	 */
	String getWtr_handset_number();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            wtr_handset_number��
	 */
	void setWtr_handset_number(String wtr_handset_number);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� wtr_lpr��
	 */
	String getWtr_lpr();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            wtr_lpr��
	 */
	void setWtr_lpr(String wtr_lpr);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� wtr_telephone��
	 */
	String getWtr_telephone();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            wtr_telephone��
	 */
	void setWtr_telephone(String wtr_telephone);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� zj_check_flag��
	 */
	java.lang.Integer getZj_check_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            zj_check_flag��
	 */
	void setZj_check_flag(java.lang.Integer zj_check_flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� zj_check_man��
	 */
	java.lang.Integer getZj_check_man();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            zj_check_man��
	 */
	void setZj_check_man(java.lang.Integer zj_check_man);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� with_bank_flag��
	 */
	Integer getWith_bank_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param with_bank_flag Ҫ���õ� with_bank_flag��
	 */
	void setWith_bank_flag(Integer with_bank_flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� ht_bank_id��
	 */
	String getHt_bank_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ht_bank_id Ҫ���õ� ht_bank_id��
	 */
	void setHt_bank_id(String ht_bank_id);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� ht_bank_sub_name��
	 */
	String getHt_bank_sub_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ht_bank_sub_name Ҫ���õ� ht_bank_sub_name��
	 */
	void setHt_bank_sub_name(String ht_bank_sub_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� ht_bank_name��
	 */
	String getHt_bank_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ht_bank_name Ҫ���õ� ht_bank_name��
	 */
	void setHt_bank_name(String ht_bank_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� with_private_flag��
	 */
	Integer getWith_private_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param with_private_flag Ҫ���õ� with_private_flag��
	 */
	void setWith_private_flag(Integer with_private_flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� with_security_flag��
	 */
	Integer getWith_security_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param with_security_flag Ҫ���õ� with_security_flag��
	 */
	void setWith_security_flag(Integer with_security_flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return Returns the max_buy_limit.
	 */
	BigDecimal getMax_buy_limit();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param max_buy_limit The max_buy_limit to set.
	 */
	void setMax_buy_limit(BigDecimal max_buy_limit);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return Returns the min_buy_limit.
	 */
	BigDecimal getMin_buy_limit();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param min_buy_limit The min_buy_limit to set.
	 */
	void setMin_buy_limit(BigDecimal min_buy_limit);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� bonus_rate��
	 */
	BigDecimal getBonus_rate();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param bonus_rate Ҫ���õ� bonus_rate��
	 */
	void setBonus_rate(BigDecimal bonus_rate);

	/**
	 * @ejb.interface-method view-type = "local"
	 * ���漶������
	 */
	String getProv_level_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * ���漶������
	 */
	void setProv_level_name(String prov_level_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� prov_flag��
	 */
	Integer getProv_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param prov_flag Ҫ���õ� prov_flag��
	 */
	void setProv_flag(Integer prov_flag);

}