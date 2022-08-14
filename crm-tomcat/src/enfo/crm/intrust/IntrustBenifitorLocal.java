package enfo.crm.intrust;

import java.math.BigDecimal;
import java.util.List;

import enfo.crm.dao.IBusiFullLocal;

public interface IntrustBenifitorLocal extends IBusiFullLocal {

	void setProv_level_name_temp(String prov_level_name_temp);

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_ADD_TBENIFITOR
	 * @IN_BOOK_CODE INT,
	 * @IN_PRODUCT_ID INT,
	 * @IN_CONTRACT_BH VARCHAR(16),
	 * @IN_CUST_ID INT,
	 * @IN_BEN_AMOUNT DECIMAL(16,3),
	 * @IN_PROV_LEVEL VARCHAR(10),
	 * @IN_JK_TYPE VARCHAR(10),
	 * @IN_BANK_ID VARCHAR(10),
	 * @IN_BANK_ACCT VARCHAR(30),
	 * @IN_INPUT_MAN INT,
	 * @IN_BANK_SUB_NAME VARCHAR(30) = '',
	 * @IN_VALID_PERIOD INT = NULL,
	 * @IN_BEN_DATE INT = NULL,
	 * @IN_ACCT_NAME VARCHAR(60) = NULL
	 * @IN_BANK_ACCT_TYPE VARCHAR(10) �����˻�����(9920)
	 * @IN_BONUS_FLAG INT = 1 1���Ҹ� 2��ת�ݶ�
	 */
	void append() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * �������ʻ��޸���� SP_CHECK_TBENIFITOR_ACCT
	 * @IN_SERIAL_NO INT,
	 * @IN_CHECK_FALG INT,
	 * @IN_INPUT_MAN INT
	 */
	void check1() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_CHECK_TBENIFITOR_FROZEN_RIGHT
	 * @IN_BOOK_CODE INT,
	 * @IN_PRODUCT_ID INT,
	 * @IN_CONTRACT_BH VARCHAR(16),
	 * @IN_LIST_ID INT = 0,
	 * @IN_CHECK_FLAG INT = 2,
	 * @IN_INPUT_MAN INT
	 */
	void checkFrozenBenRight() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_DEL_TBENIFITOR
	 * @IN_SERIAL_NO INT,
	 * @IN_INPUT_MAN INT
	 */
	void delete() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * û�����������
	 */
	void frozenBenRight(int flag) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * �����˲�ѯ SP_QUERY_TBENIFITOR_QUERY
	 * @IN_BOOK_CODE INT,
	 * @IN_SERIAL_NO INT,
	 * @IN_PRODUCT_ID INT,
	 * @IN_CONTRACT_BH VARCHAR(16),
	 * @IN_CONTRACT_SUB_BH VARCHAR(50),
	 * @IN_SY_CUST_NO VARCHAR(8),
	 * @IN_SY_CARD_ID VARCHAR(40),
	 * @IN_SY_CUST_NAME VARCHAR(100),
	 * @IN_CUST_TYPE INT,
	 * @IN_PROV_LEVEL VARCHAR(10),
	 * @IN_BEN_STATUS VARCHAR(10),
	 * @IN_INPUT_MAN INT
	 * @throws Exception
	 */
	void listbySqlSYRAll() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return
	 * @throws Exception
	 */
	boolean getNextSYRMessage() throws Exception;

	/**
	 *  @ejb.interface-method view-type = "local"
	 * 
	 * ��������Ϣ��ѯ����List����
	 * @return
	 * @throws Exception
	 */
	List getNextSYRMessageList() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ��������Ϣ��ѯ����List����
	 * add by liugang 20110119
	 * @return
	 * @throws Exception
	 */
	List getNextSYRMessageList1() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * add by tsg 2008-03-12 ��ѯ����ת��N-1 �б�
	 * @return �������е�serial_nos��
	 */
	String transForNto1() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 */
	void list() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_QUERY_TBENIFITOR_BYHT
	 * @IN_PRODUCT_ID INT,
	 * @IN_CONTRACT_BH VARCHAR(16)
	 */
	void listBenifitorbyht() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_QUERY_TBENIFITOR
	 * @IN_BOOK_CODE INT,
	 * @IN_SERIAL_NO INT,
	 * @IN_PRODUCT_ID INT,
	 * @IN_CONTRACT_BH VARCHAR(16),
	 * @IN_CUST_ID INT,
	 * @IN_INPUT_MAN INT = NULL,
	 * @IN_CUST_NAME VARCHAR(60)=NULL,
	 * @IN_PROV_LEVEL VARCHAR(10)=NULL,
	 * @IN_CARD_ID VARCHAR(30)=NULL,
	 * @IN_LIST_ID INT=NULL,
	 * @IN_BEN_STATUS VARCHAR(10)=NULL --ADD BY SHENKL 2006/12/08
	 */
	void listBenifitors() throws Exception;

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
	 * �����˷ݶ�����ˮ��ѯ
	 * 
	 * @param book_code
	 * @param cust_id
	 * @param product_id
	 * @throws Exception
	 */
	void listChangeDetail(Integer book_code, Integer cust_id, Integer product_id) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * UPDATE BY TSG 2008-03-10 SP_QUERY_TBENIFITOR_DETAIL
	 * @IN_BOOK_CODE INT,
	 * @IN_PRODUCT_ID INT,
	 * @IN_CONTRACT_BH VARCHAR(16),
	 * @IN_LIST_ID INT = 0,
	 * @IN_SY_CARD_ID VARCHAR(40),
	 * @IN_SY_CUST_NAME VARCHAR(100),
	 * @IN_PROV_LEVEL VARCHAR(10),
	 * @IN_INPUT_MAN INT = NULL,
	 * @IN_CUST_ID INT = NULL,
	 * @IN_SY_CUST_NO VARCHAR(8) = '',
	 * @IN_BEN_STATUS VARCHAR(10) = '', --ADD BY SHENKL 2006/11/23
	 * @IN_FLAG INT = NULL --1ֻ������2����BEN_STATUS--ADD BY SHENKL 2006/12/22
	 * @IN_CONTRACT_SUB_BH VARCHAR(50)= '', -- ��ͬ��Ų�ѯ 20080104 by wangc
	 * @IN_CUST_TYPE INT = NULL
	 */
	void listDetail(int in_flag) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_QUERY_TBENIFITOR_LOST_DETAIL
	 * @IN_BOOK_CODE INT,
	 * @IN_PRODUCT_ID INT,
	 * @IN_CONTRACT_BH VARCHAR(16),
	 * @IN_LIST_ID INT = 0,
	 * @IN_BEN_CARD_NO VARCHAR(40)= NULL,
	 * @IN_SY_CUST_NAME VARCHAR(100)= NULL,
	 * @IN_PROV_LEVEL VARCHAR(10)= NULL,
	 * @IN_INPUT_MAN INT = NULL,
	 * @IN_CUST_ID INT = NULL,
	 * @IN_FUNC_ID INT = 0,--1������֤�����ʧ2�տ�֤����ʧ
	 * @IN_CUST_NO VARCHAR(8) = '',
	 * @IN_BEN_LOST_FLAG INT = NULL
	 */
	void listLostDetail(int lostflag) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_QUERY_TBENIFITOR_STATUS
	 * @IN_BOOK_CODE INT,
	 * @IN_PRODUCT_ID INT
	 */
	void listStatus() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_QUERY_TBENIFITOR
	 * @IN_BOOK_CODE INT,
	 * @IN_SERIAL_NO INT,
	 * @IN_PRODUCT_ID INT,
	 * @IN_CONTRACT_BH VARCHAR(16),
	 * @IN_CUST_ID INT,
	 * @IN_INPUT_MAN INT = NULL,
	 * @IN_CUST_NAME VARCHAR(60)=NULL,
	 * @IN_PROV_LEVEL VARCHAR(10)=NULL,
	 * @IN_CARD_ID VARCHAR(30)=NULL,
	 * @IN_LIST_ID INT=NULL,
	 * @IN_BEN_STATUS VARCHAR(10)=NULL
	 */

	void load() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * add by liug 2011-01-07
	 */

	void loadModiBenAcctDetial() throws Exception;

	/**
	 *  @ejb.interface-method view-type = "local"
	 * 
	 * �����˻��޸Ĳ�ѯ����List����
	 * add by liug 2011-01-07
	 * @throws Exception
	 */
	List getNextModiBenDetailList() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_TBENIFITOR_LOST_RIGHT
	 * @IN_BOOK_CODE INT,
	 * @IN_PRODUCT_ID INT,
	 * @IN_CONTRACT_BH VARCHAR(16),
	 * @IN_LIST_ID INT = 0,
	 * @IN_STATUS VARCHAR(10),
	 * @IN_INPUT_MAN INT,
	 * @IN_BEN_LOST_FLAG INT = 0,
	 * @IN_BEN_LOST_DATE INT = 0,
	 * @IN_FUNC_ID INT = 0, --1������֤�����ʧ0�տ�֤����ʧ
	 * @IN_BEN_CARD_NO VARCHAR(20)=NULL
	 */
	void lostBenInfo(int flag) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * �޸� ���漶�� PROV_LEVEL
	 * 
	 * @throws Exception
	 */

	void modi_prov_level() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @throws Exception
	 */
	void query() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_QUERY_TBENIFITOR
	 * @IN_BOOK_CODE INT,
	 * @IN_SERIAL_NO INT,
	 * @IN_PRODUCT_ID INT,
	 * @IN_CONTRACT_BH VARCHAR(16),
	 * @IN_CUST_ID INT,
	 * @IN_INPUT_MAN INT = NULL,
	 * @IN_CUST_NAME VARCHAR(60)=NULL,
	 * @IN_PROV_LEVEL VARCHAR(10)=NULL,
	 * @IN_CARD_ID VARCHAR(30)=NULL,
	 * @IN_LIST_ID INT=NULL,
	 * @IN_BEN_STATUS VARCHAR(10)=NULL --ADD BY SHENKL 2006/12/08
	 */
	void query(String sQuery) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * �����˷�����ϸ SP_QUERY_BENIFITOR_FEEDETAIL
	 * @IN_BEN_SERIAL_NO INT,
	 * @IN_INPUT_MAN INT
	 */
	void query_syfymx() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return
	 * @throws Exception
	 */
	boolean getQ_S() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_QUERY_TBENIFITOR_CUST_ID
	 * @IN_BOOK_CODE INT,
	 * @IN_PRODUCT_ID INT,
	 * @IN_CUST_ID INT
	 */
	void QueryBenifitor() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_QUERY_TBENIFITOR_MODIUNCHECK
	 * @IN_FUNCTION_ID INT, --100�ʺ��޸�ʱ���÷�����������Ϣ��@IN_CONTRACT_BH��@IN_CUST_NO������һ��
	 *                 --200���ʱ���ã������޸�δ��˼�¼��@IN_CONTRACT_BH��@IN_CUST_NO��Ч
	 * @IN_BOOK_CODE INT,
	 * @IN_SERIAL_NO INT,
	 * @IN_CONTRACT_BH VARCHAR(16),
	 * @IN_CUST_NO VARCHAR(8),
	 * @IN_PRODUCT_ID INT = NULL,
	 * @IN_PRODUCT_NAME VARCHAR(60) = NULL,
	 * @IN_INPUT_MAN INT = NULL,
	 * @IN_CUST_NAME VARCHAR(60) = NULL
	 */
	void queryModi() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_QUERY_TMODIBENACCTDETAIL
	 * @IN_BOOK_CODE INT,
	 * @IN_PRODUCT_ID INT = NULL,
	 * @IN_CONTRACT_BH VARCHAR(16) = NULL,
	 * @IN_CUST_NAME VARCHAR(60) = NULL,
	 * @IN_OLD_BANK_ID VARCHAR(10) = NULL,
	 * @IN_OLD_BANK_ACCT VARCHAR(30) = NULL,
	 * @IN_NEW_BANK_ID VARCHAR(10) = NULL,
	 * @IN_NEW_BANK_ACCT VARCHAR(30) = NULL,
	 * @IN_INPUT_MAN INT = NULL,
	 * @IN_CUST_NO VARCHAR(8) = ''
	 */
	void queryModiAcctDetail() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * û���ֹ���
	 */
	void queryModiStatusDetail() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * �޸���������Ϣ SP_MODI_TBENIFITOR
	 * @IN_SERIAL_NO INT,
	 * @IN_BEN_AMOUNT DECIMAL(16,3),
	 * @IN_PROV_LEVEL VARCHAR(10),
	 * @IN_BANK_ID VARCHAR(10),
	 * @IN_BANK_ACCT VARCHAR(30),
	 * @IN_JK_TYPE VARCHAR(10),
	 * @IN_INPUT_MAN INT,
	 * @IN_BANK_SUB_NAME VARCHAR(30) = '',
	 * @IN_VALID_PERIOD INT = NULL,
	 * @IN_BEN_DATE INT = NULL,
	 * @IN_ACCT_NAME VARCHAR(60) = NULL
	 * @IN_BANK_ACCT_TYPE VARCHAR(10) �����˻�����(9920)
	 * @IN_BOUNS_FLAG INT = 1 1���Ҹ� 2��ת�ݶ�
	 */
	void save() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_MODI_TBENIFITOR_BANK
	 * @IN_SERIAL_NO INT,
	 * @IN_BANK_ID VARCHAR(10),
	 * @IN_BANK_SUB_NAME VARCHAR(30),
	 * @IN_BANK_ACCT VARCHAR(30),
	 * @IN_INPUT_MAN INT,
	 * @IN_ACCT_NAME VARCHAR(60) = NULL,
	 * @IN_MODI_DATE INT=NULL
	 * @IN_BANK_ACCT_TYPE VARCHAR(10) �����˻�����(9920)
	 * @IN_BONUS_FLAG INT = 1 1���Ҹ� 2��ת�ݶ�
	 */
	void save1() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 */
	boolean getNext() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return
	 * @throws Exception
	 */
	boolean getNext_ChangeDetail() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return
	 * @throws Exception
	 */
	boolean getNext_cust() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return
	 * @throws Exception
	 */
	boolean getNext2() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return
	 * @throws Exception
	 */
	boolean getNext3() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return
	 * @throws Exception
	 */
	boolean getNextbyht() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return
	 * @throws Exception
	 */
	boolean getNextDetail() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return
	 * @throws Exception
	 */
	boolean getNextDetail1() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return
	 * @throws Exception
	 */
	boolean getNextLost() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return
	 * @throws Exception
	 */
	boolean getNextModi() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return
	 * @throws Exception
	 */
	boolean getNextModiDetail() throws Exception;

	/**
	 *  @ejb.interface-method view-type = "local"
	 * 
	 * �����˻��޸Ĳ�ѯ����List����
	 * @return
	 * @throws Exception
	 */
	List getNextModiDetailList() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return
	 * @throws Exception
	 */
	boolean getNextModiStatusDetail() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * add by tsg 2008-03-12 ����ת��N-1
	 *  
	 */
	boolean getNextNew3() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return
	 * @throws Exception
	 */
	boolean getNextStatus() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_QUERY_TBENIFITOR_PROV
	 * @IN_BOOK_CODE INT,
	 * @IN_PRODUCT_ID INT,
	 * @IN_CONTRACT_SUB_BH VARCHAR(16),
	 * @IN_CUST_NO VARCHAR(40),
	 * @IN_CUST_NAME VARCHAR(40),
	 * @IN_SY_CUST_NAME VARCHAR(100)= NULL,
	 * @IN_PROV_FLAG INT = NULL,
	 * @IN_PROV_LEVEL VARCHAR(40)
	 * @IN_CHECK_FLAG INT = NULL
	 */

	void QueryBenifitorProv() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return
	 * @throws Exception
	 */
	boolean getNextBenifitorProv() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_CHECK_TBENIFITOR_LEVEL
	 * @return
	 * @throws Exception
	 */
	void checkBenifitorProv() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ��ѯ��Ӧ�����˵���ʷ�ݶ� SP_QUERY_HBENIFITOR
	 * @IN_BEN_SERIAL_NO    INTEGER,        --�����˼�¼��
	 * @IN_BOOK_CODE        INTEGER,        --����
	 * @IN_PRODUCT_ID       INTEGER,        --��ƷID
	 * @IN_SUB_PRODUCT_ID   INTEGER,        --�Ӳ�ƷID
	 * @IN_CONTRACT_BH      NVARCHAR(16),   --��ͬ���
	 * @IN_CUST_NAME        NVARCHAR(60)    --�ͻ�����
	 * @return
	 * @throws Exception
	 */
	void getHbenifitor() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return
	 * @throws Exception
	 */
	boolean getNextHbenifitor() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� acct_chg_reason��
	 */
	String getAcct_chg_reason();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            acct_chg_reason��
	 */
	void setAcct_chg_reason(String acct_chg_reason);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� acct_user_name��
	 */
	String getAcct_user_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            acct_user_name��
	 */
	void setAcct_user_name(String acct_user_name);

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
	 * @return ���� ben_amount��
	 */
	java.math.BigDecimal getBen_amount();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            ben_amount��
	 */
	void setBen_amount(java.math.BigDecimal ben_amount);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� ben_card_no��
	 */
	String getBen_card_no();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            ben_card_no��
	 */
	void setBen_card_no(String ben_card_no);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� ben_count��
	 */
	Integer getBen_count();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            ben_count��
	 */
	void setBen_count(Integer ben_count);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� ben_date��
	 */
	java.lang.Integer getBen_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            ben_date��
	 */
	void setBen_date(java.lang.Integer ben_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� ben_end_date��
	 */
	java.lang.Integer getBen_end_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            ben_end_date��
	 */
	void setBen_end_date(java.lang.Integer ben_end_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� ben_lost_date��
	 */
	Integer getBen_lost_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            ben_lost_date��
	 */
	void setBen_lost_date(Integer ben_lost_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� ben_lost_flag��
	 */
	Integer getBen_lost_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            ben_lost_flag��
	 */
	void setBen_lost_flag(Integer ben_lost_flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� ben_money��
	 */
	BigDecimal getBen_money();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            ben_money��
	 */
	void setBen_money(BigDecimal ben_money);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� ben_serial_no��
	 */
	Integer getBen_serial_no();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            ben_serial_no��
	 */
	void setBen_serial_no(Integer ben_serial_no);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� ben_status��
	 */
	String getBen_status();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            ben_status��
	 */
	void setBen_status(String ben_status);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� ben_status_name��
	 */
	String getBen_status_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            ben_status_name��
	 */
	void setBen_status_name(String ben_status_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� ben_unfrozen_date��
	 */
	Integer getBen_unfrozen_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            ben_unfrozen_date��
	 */
	void setBen_unfrozen_date(Integer ben_unfrozen_date);

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
	 * @return ���� card_lost_date��
	 */
	Integer getCard_lost_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            card_lost_date��
	 */
	void setCard_lost_date(Integer card_lost_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� card_lost_flag��
	 */
	Integer getCard_lost_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            card_lost_flag��
	 */
	void setCard_lost_flag(Integer card_lost_flag);

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
	 * @return ���� contract_sub_bh��
	 */
	java.lang.String getContract_sub_bh();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            contract_sub_bh��
	 */
	void setContract_sub_bh(java.lang.String contract_sub_bh);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� cust_acct_name��
	 */
	String getCust_acct_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            cust_acct_name��
	 */
	void setCust_acct_name(String cust_acct_name);

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
	 * @return ���� fee_date��
	 */
	java.lang.Integer getFee_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            fee_date��
	 */
	void setFee_date(java.lang.Integer fee_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� fee_money��
	 */
	java.math.BigDecimal getFee_money();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            fee_money��
	 */
	void setFee_money(java.math.BigDecimal fee_money);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� fee_tpye��
	 */
	java.lang.Integer getFee_tpye();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            fee_tpye��
	 */
	void setFee_tpye(java.lang.Integer fee_tpye);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� fee_tpye_name��
	 */
	String getFee_tpye_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            fee_tpye_name��
	 */
	void setFee_tpye_name(String fee_tpye_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� firbid_flag��
	 */
	Integer getFirbid_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            firbid_flag��
	 */
	void setFirbid_flag(Integer firbid_flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� flag��
	 */
	Integer getFlag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            flag��
	 */
	void setFlag(Integer flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� frozen_money��
	 */
	BigDecimal getFrozen_money();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            frozen_money��
	 */
	void setFrozen_money(BigDecimal frozen_money);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� frozen_tmp��
	 */
	java.math.BigDecimal getFrozen_tmp();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            frozen_tmp��
	 */
	void setFrozen_tmp(java.math.BigDecimal frozen_tmp);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� ht_status_name��
	 */
	String getHt_status_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            ht_status_name��
	 */
	void setHt_status_name(String ht_status_name);

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
	 * @return ���� is_transferential��
	 */
	Integer getIs_transferential();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            is_transferential��
	 */
	void setIs_transferential(Integer is_transferential);

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
	 * @return ���� modi_bank_date��
	 */
	Integer getModi_bank_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            modi_bank_date��
	 */
	void setModi_bank_date(Integer modi_bank_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� modi_check_man��
	 */
	Integer getModi_check_man();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            modi_check_man��
	 */
	void setModi_check_man(Integer modi_check_man);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� modi_check_time��
	 */
	java.sql.Timestamp getModi_check_time();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            modi_check_time��
	 */
	void setModi_check_time(java.sql.Timestamp modi_check_time);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� modi_man��
	 */
	Integer getModi_man();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            modi_man��
	 */
	void setModi_man(Integer modi_man);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� modi_time��
	 */
	java.sql.Timestamp getModi_time();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            modi_time��
	 */
	void setModi_time(java.sql.Timestamp modi_time);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� new_bank_acct��
	 */
	String getNew_bank_acct();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            new_bank_acct��
	 */
	void setNew_bank_acct(String new_bank_acct);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� new_bank_id��
	 */
	String getNew_bank_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            new_bank_id��
	 */
	void setNew_bank_id(String new_bank_id);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� new_bank_sub_name��
	 */
	String getNew_bank_sub_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            new_bank_sub_name��
	 */
	void setNew_bank_sub_name(String new_bank_sub_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� old_bank_acct��
	 */
	String getOld_bank_acct();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            old_bank_acct��
	 */
	void setOld_bank_acct(String old_bank_acct);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� old_bank_id��
	 */
	String getOld_bank_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            old_bank_id��
	 */
	void setOld_bank_id(String old_bank_id);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� old_bank_sub_name��
	 */
	String getOld_bank_sub_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            old_bank_sub_name��
	 */
	void setOld_bank_sub_name(String old_bank_sub_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� period_unit��
	 */
	Integer getPeriod_unit();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            period_unit��
	 */
	void setPeriod_unit(Integer period_unit);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� product_code��
	 */
	String getProduct_code();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            product_code��
	 */
	void setProduct_code(String product_code);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� product_exp_rate��
	 */
	String getProduct_exp_rate();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            product_exp_rate��
	 */
	void setProduct_exp_rate(String product_exp_rate);

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
	String getProduct_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            product_name��
	 */
	void setProduct_name(String product_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� product_period��
	 */
	Integer getProduct_period();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            product_period��
	 */
	void setProduct_period(Integer product_period);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� product_qx��
	 */
	String getProduct_qx();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            product_qx��
	 */
	void setProduct_qx(String product_qx);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� prov_level��
	 */
	java.lang.String getProv_level();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            prov_level��
	 */
	void setProv_level(java.lang.String prov_level);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� prov_level_name��
	 */
	java.lang.String getProv_level_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            prov_level_name��
	 */
	void setProv_level_name(java.lang.String prov_level_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� qs_date��
	 */
	Integer getQs_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            qs_date��
	 */
	void setQs_date(Integer qs_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� remain_amount��
	 */
	java.math.BigDecimal getRemain_amount();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            remain_amount��
	 */
	void setRemain_amount(java.math.BigDecimal remain_amount);

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
	 * @return ���� st_chg_reason��
	 */
	String getSt_chg_reason();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            st_chg_reason��
	 */
	void setSt_chg_reason(String st_chg_reason);

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
	 * @return ���� sy_address��
	 */
	String getSy_address();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            sy_address��
	 */
	void setSy_address(String sy_address);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� sy_card_id��
	 */
	String getSy_card_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            sy_card_id��
	 */
	void setSy_card_id(String sy_card_id);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� sy_card_type_name��
	 */
	String getSy_card_type_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            sy_card_type_name��
	 */
	void setSy_card_type_name(String sy_card_type_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� sy_cust_name��
	 */
	String getSy_cust_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            sy_cust_name��
	 */
	void setSy_cust_name(String sy_cust_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� sy_cust_no��
	 */
	String getSy_cust_no();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            sy_cust_no��
	 */
	void setSy_cust_no(String sy_cust_no);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� temp_acct_name��
	 */
	String getTemp_acct_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            temp_acct_name��
	 */
	void setTemp_acct_name(String temp_acct_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� temp_bonus_flag��
	 */
	Integer getTemp_bonus_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            temp_bonus_flag��
	 */
	void setTemp_bonus_flag(Integer temp_bonus_flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� temp_status��
	 */
	java.lang.String getTemp_status();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            temp_status��
	 */
	void setTemp_status(java.lang.String temp_status);

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
	 * @return ���� trans_type��
	 */
	String getTrans_type();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            trans_type��
	 */
	void setTrans_type(String trans_type);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� transfer_flag��
	 */
	Integer getTransfer_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            transfer_flag��
	 */
	void setTransfer_flag(Integer transfer_flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� valid_period��
	 */
	Integer getValid_period();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            valid_period��
	 */
	void setValid_period(Integer valid_period);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� wt_address��
	 */
	String getWt_address();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            wt_address��
	 */
	void setWt_address(String wt_address);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� wt_card_id��
	 */
	String getWt_card_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            wt_card_id��
	 */
	void setWt_card_id(String wt_card_id);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� wt_card_type_name��
	 */
	String getWt_card_type_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            wt_card_type_name��
	 */
	void setWt_card_type_name(String wt_card_type_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� wt_cust_name��
	 */
	String getWt_cust_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            wt_cust_name��
	 */
	void setWt_cust_name(String wt_cust_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� back_amount��
	 */
	java.math.BigDecimal getBack_amount();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� ben_amount2��
	 */
	java.math.BigDecimal getBen_amount2();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� exchange_amount��
	 */
	java.math.BigDecimal getExchange_amount();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� temp_bank_acct��
	 */
	java.lang.String getTemp_bank_acct();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� temp_bank_id��
	 */
	java.lang.String getTemp_bank_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� temp_bank_sub_name��
	 */
	java.lang.String getTemp_bank_sub_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            function_id��
	 */
	void setFunction_id(java.lang.Integer function_id);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� to_money��
	 */
	BigDecimal getTo_money();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ���� to_money��
	 */
	void setTo_money(BigDecimal to_money);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� cust_tel��
	 */
	String getCust_tel();

	/**@ejb.interface-method view-type = "local"
	 * 
	 * @param cust_tel Ҫ���õ� cust_tel��
	 */
	void setCust_tel(String cust_tel);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� mobile��
	 */
	String getMobile();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param mobile Ҫ���õ� mobile��
	 */
	void setMobile(String mobile);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� modi_acct_check_flag��
	 */
	Integer getModi_acct_check_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param modi_acct_check_flag Ҫ���õ� modi_acct_check_flag��
	 */
	void setModi_acct_check_flag(Integer modi_acct_check_flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� bonus_rate��
	 */
	java.math.BigDecimal getBonus_rate();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param temp_bonus_rate Ҫ���õ� temp_bonus_rate��
	 */
	void setTemp_bonus_rate(java.math.BigDecimal temp_bonus_rate);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� temp_bonus_rate��
	 */
	java.math.BigDecimal getTemp_bonus_rate();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param bonus_rate Ҫ���õ� bonus_rate��
	 */
	void setBonus_rate(java.math.BigDecimal bonus_rate);

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

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� prov_level_name_temp��
	 */
	String getProv_level_name_temp();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param prov_level_name_temp Ҫ���õ� prov_level_name_temp��
	 */

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� add_fe��
	 */
	java.math.BigDecimal getAdd_fe();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param add_fe Ҫ���õ� add_fe��
	 */
	void setAdd_fe(java.math.BigDecimal add_fe);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� add_je��
	 */
	java.math.BigDecimal getAdd_je();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param add_je Ҫ���õ� add_je��
	 */
	void setAdd_je(java.math.BigDecimal add_je);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� dec_fe��
	 */
	java.math.BigDecimal getDec_fe();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param dec_fe Ҫ���õ� dec_fe��
	 */
	void setDec_fe(java.math.BigDecimal dec_fe);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� dec_je��
	 */
	java.math.BigDecimal getDec_je();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param dec_je Ҫ���õ� dec_je��
	 */
	void setDec_je(java.math.BigDecimal dec_je);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� qc_fe��
	 */
	java.math.BigDecimal getQc_fe();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param qc_fe Ҫ���õ� qc_fe��
	 */
	void setQc_fe(java.math.BigDecimal qc_fe);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� qc_je��
	 */
	java.math.BigDecimal getQc_je();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param qc_je Ҫ���õ� qc_je��
	 */
	void setQc_je(java.math.BigDecimal qc_je);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� qm_fe��
	 */
	java.math.BigDecimal getQm_fe();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param qm_fe Ҫ���õ� qm_fe��
	 */
	void setQm_fe(java.math.BigDecimal qm_fe);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� qm_je��
	 */
	java.math.BigDecimal getQm_je();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param qm_je Ҫ���õ� qm_je��
	 */
	void setQm_je(java.math.BigDecimal qm_je);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� sub_product_id��
	 */
	java.lang.Integer getSub_product_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param sub_product_id Ҫ���õ� sub_product_id��
	 */
	void setSub_product_id(java.lang.Integer sub_product_id);

}