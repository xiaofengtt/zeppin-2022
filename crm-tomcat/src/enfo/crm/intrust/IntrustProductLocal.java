package enfo.crm.intrust;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiFullLocal;
import sun.jdbc.rowset.CachedRowSet;

public interface IntrustProductLocal extends IBusiFullLocal {

	/**
	 * @throws Exception 
	 * @ejb.interface-method view-type = "local"
	 * 
	 * add by tangshg 2009-11-28 �Ӳ�Ʒ��Ϣ��ѯ SP_QUERY_TSUBPRODUCT
	 * @IN_PRODUCT_ID INT,
	 * @IN_SUB_PRODUCT_ID INT = 0,
	 * @IN_LIST_ID INT = 0
	 * @IN_LIST_NAME NVARCHAR(60) ='', --�Ӳ�Ʒ����
	 * @IN_PRODUCT_CODE VARCHAR(6) ='', --��Ʒ���
	 * @IN_PRODUCT_NAME NVARCHAR(60) ='' --��Ʒ����
	 */
	void listSubProduct() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return
	 * @throws Exception
	 */
	boolean getNextSubProduct() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * add by tangshg 2009-11-28 �����Ӳ�Ʒ SP_ADD_TSUBPRODUCT
	 * 
	 * @IN_PRODUCT_ID INT,
	 * @IN_LIST_NAME NVARCHAR(60),
	 * @IN_PROV_LEVEL NVARCHAR(10),
	 * @IN_PRE_NUM INT,
	 * @IN_PRE_MONEY DECIMAL(16,3),
	 * @IN_MIN_MONEY DECIMAL(16,3),
	 * @IN_EXP_RATE1 DECIMAL(7,6),
	 * @IN_EXP_RATE2 DECIMAL(7,6),
	 * @IN_VALID_PERIOD INT,
	 * @IN_PERIOD_UNIT INT = 2,
	 * @IN_INPUT_MAN INT
	 * 
	 * @IN_RESULT_STANDARD DECIMAL(9,8) =0, --ҵ����׼
	 * @IN_START_DATE INTEGER =0 -- ��ʼ����
	 * 
	 *  
	 */
	void addSubProduct() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * add by tangshg 2009-11-28 �޸��Ӳ�Ʒ SP_MODI_TSUBPRODUCT
	 * 
	 * @IN_SUB_PRODUCT_ID INT
	 * @IN_LIST_NAME NVARCHAR(60),
	 * @IN_PROV_LEVEL NVARCHAR(10),
	 * @IN_PRE_NUM INT,
	 * @IN_PRE_MONEY DECIMAL(16,3),
	 * @IN_MIN_MONEY DECIMAL(16,3),
	 * @IN_EXP_RATE1 DECIMAL(7,6),
	 * @IN_EXP_RATE2 DECIMAL(7,6),
	 * @IN_VALID_PERIOD INT,
	 * @IN_PERIOD_UNIT INT = 2,
	 * @IN_INPUT_MAN INT
	 * 
	 * 
	 *  
	 */
	void modiSubProduct() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * add by tangshg 2009-11-28 ɾ���ֲ�Ʒ��Ϣ SP_DEL_TSUBPRODUCT
	 * 
	 * @IN_SUB_PRODUCT_ID INT,
	 * @IN_INPUT_MAN INT
	 *  
	 */
	void delSubProduct() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * �½���Ӫ���ײ���
	 * 
	 * @throws Exception
	 */
	void appendSelf() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ������Ӫ���ײ���
	 */
	void updateSelf() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ������Ϣ��ȡ��Ӫ������Ϣ
	 * 
	 * @throws Exception
	 */
	void loadSelf() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ��ѯ��Ӫ���� 2008.05.06
	 * 
	 * @param sQuery
	 * @throws Exception
	 */
	void querySelf(String sQuery) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @throws Exception
	 */
	void listSelf() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ��Ӫ���׽����
	 * 
	 * @return
	 * @throws Exception
	 */
	boolean getNextSelf() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local" * SP_ADD_TPRODUCT
	 * @IN_BOOK_CODE INT,
	 * @IN_PRODUCT_NAME VARCHAR (60),
	 * @IN_PRODUCT_JC VARCHAR (30),
	 * @IN_ITEM_ID INT,
	 * @IN_PRE_NUM INT,
	 * @IN_PRE_MONEY DECIMAL(16,3),
	 * @IN_MIN_MONEY DECIMAL(16,3),
	 * @IN_PRE_MAX_RATE DECIMAL (5, 4),
	 * @IN_PRE_START_DATE INT,
	 * @IN_PRE_END_DATE INT,
	 * @IN_VALID_PERIOD INT,
	 * @IN_PRE_CODE VARCHAR (10),
	 * @IN_INFO_PERIOD INT,
	 * @IN_OPEN_FLAG INT,
	 * @IN_TG_BANK_ID VARCHAR (10),
	 * @IN_TG_BANK_ACCT VARCHAR(30),
	 * @IN_EXTEND_FLAG INT,
	 * @IN_DEPART_ID INT,
	 * @IN_FPFS VARCHAR (10) ,
	 * @IN_MANAGE_FEE DECIMAL(16,3),
	 * @IN_MANAGE_RATE DECIMAL (5, 4),
	 * @IN_SUMMARY VARCHAR (200),
	 * @IN_INPUT_MAN INT,
	 * @IN_TAX_RATE DECIMAL(5,4),
	 * @IN_TG_BANK_SUB_NAME VARCHAR(30),
	 * @IN_BEN_PERIOD INT,
	 * @IN_IS_LOCAL INT,
	 * @IN_PERIOD_UNIT INT = 2,
	 * @IN_ADMIN_MANAGER INT = NULL,
	 * @IN_ADMIN_MANAGER2 INT = NULL,
	 * @IN_MATAIN_MANAGER INT = NULL,
	 * @IN_CHANGE_WT_FLAG INT = 0,
	 * @OUT_PRODUCT_ID INT OUTPUT //2008-07-30
	 * @IN_BG_BANK_ID VARCHAR(10) //20090429 add by nizh
	 * @IN_CHECK_MAN INT =0 --ָ����Ʒ��Ϣ�����
	 * 
	 * @IN_END_DATE INT = NULL, --20090728 DONGYG �������ڿ��Դӽ������룬������������Ϊ׼
	 * @IN_TG_ACCT_NAME NVARCHAR(100) = NULL --�й������ʻ����� 20090728 DONGYG ADD
	 * 
	 * ADD BY tangshg 2009-11-28
	 * @IN_SUB_FLAG BIT = 0, -- �Ӳ�Ʒ��־: 1 �� 0 û�� ADD BY JINXR 2009/11/23
	 * @IN_NAV_FLOAT_NUM TINYINT = 10 -- ��ֵ����
	 * @IN_TRADE_TAX_RATE DECIMAL(5,4) =0 --Ӫҵ˰�� ADD BY LUOHH
	 * @IN_TRUST_CONTRACT_NAME NVARCHAR(100) -- ��ͬ����
	 * 
	 * @IN_SHARE_FLAG INT  --�ݶ���㷽ʽ��������ֵ���� 2���ʽ����
	 * @IN_TRUST_FEE_PERIOD --���б�����ȡ����
	 */
	void append() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_MODI_TPRODUCT
	 * @IN_PRODUCT_ID INT,
	 * @IN_PRODUCT_NAME VARCHAR(60),
	 * @IN_PRODUCT_JC VARCHAR(30),
	 * @IN_PRE_NUM INT,
	 * @IN_PRE_MONEY DECIMAL(16,3),
	 * @IN_MIN_MONEY DECIMAL(16,3),
	 * @IN_PRE_MAX_RATE DECIMAL(5,4),
	 * @IN_PRE_START_DATE INT,
	 * @IN_PRE_END_DATE INT,
	 * @IN_VALID_PERIOD INT,
	 * @IN_PRE_CODE VARCHAR (10),
	 * @IN_INFO_PERIOD INT,
	 * @IN_OPEN_FLAG INT,
	 * @IN_TG_BANK_ID VARCHAR (10),
	 * @IN_TG_BANK_ACCT VARCHAR (30),
	 * @IN_EXTEND_FLAG INT,
	 * @IN_DEPART_ID INT,
	 * @IN_FPFS VARCHAR(10) ,
	 * @IN_MANAGE_FEE DECIMAL(16,3),
	 * @IN_MANAGE_RATE DECIMAL (5, 4),
	 * @IN_SUMMARY VARCHAR (200),
	 * @IN_INPUT_MAN INT,
	 * @IN_TAX_RATE DECIMAL(5,4),
	 * @IN_TG_BANK_SUB_NAME VARCHAR(30),
	 * @IN_BEN_PERIOD INT,
	 * @IN_IS_LOCAL INT,
	 * @IN_PERIOD_UNIT INT = 2,
	 * @IN_ADMIN_MANAGER INT = NULL,
	 * @IN_ADMIN_MANAGER2 INT = NULL,
	 * @IN_MATAIN_MANAGER INT = NULL,
	 * @IN_CHANGE_WT_FLAG INT = 0,
	 * @IN_LAST_POST_DATE INT = 0
	 * @IN_BG_BANK_ID //20090429 add by nizh
	 * @IN_CHECK_MAN INT =0
	 * 
	 * @IN_END_DATE INT = NULL ������ʱ������Ϊ׼��������ʱ��ԭ����������ʼ����+���ޣ�
	 * @IN_TG_ACCT_NAME NVARCHAR(100) = NULL ADD BY tangshg 2009-11-28
	 * @IN_SUB_FLAG BIT = 0, -- �Ӳ�Ʒ��־: 1 �� 0 û�� ADD BY JINXR 2009/11/23
	 * @IN_NAV_FLOAT_NUM TINYINT = 10 -- ��ֵ����
	 * @IN_TRADE_TAX_RATE DECIMAL(5,4) =0 --Ӫҵ˰�� ADD BY LUOHH
	 * @IN_TRUST_FEE_PERIOD INT = 0 -- ���б�����ȡ����
	 */
	void save() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_MODI_TPRODUCT1
	 * add by liug 20101213 ��Ʒ��Ϣ�޸���Ҫ�������ʱ
	 */
	void save1() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ɾ����Ʒ��Ϣ SP_DEL_TPRODUCT
	 * @IN_PRODUCT_ID INT,
	 * @IN_INPUT_MAN INT
	 */
	void delete() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_CHECK_TPRODUCT
	 * @IN_BOOK_CODE INT,
	 * @IN_PRODUCT_ID INT,
	 * @IN_CHECK_FLAG INT,
	 * @IN_INPUT_MAN INT
	 */
	void checkProduct() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * @author administrator ���ܣ��б���ʾδ��˲�Ʒ Bean implementation class for
	 *         Enterprise Bean: Product SP_QUERY_TPRODUCT_UNCHECK
	 * @IN_BOOK_CODE INT,
	 * @IN_PRODUCT_CODE VARCHAR(6),
	 * @IN_PRODUCT_NAME VARCHAR(60),
	 * @IN_INPUT_MAN INT
	 */
	void uncheckProduct() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ��Ʒ��Ϣ�޸ļ�¼δ��˵Ĳ�Ʒ
	 * add by liug 20101213 
	 */
	void uncheckProductDetailList() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_MODI_TPRODUCT_PERIOD
	 * @IN_PRODUCT_ID INT,
	 * @IN_END_DATE INT,
	 * @IN_INPUT_MAN INT
	 */
	void updatePeriod() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_MODI_TPRODUCT_QUALITY
	 * @IN_PRODUCT_ID INT,
	 * @IN_QUALITY_LEVEL VARCHAR(10),
	 * @IN_INPUT_MAN INT
	 */
	void updateLevel() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * update by tsg 2007-11-13
	 */
	void load() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @throws Exception
	 */
	void listall2() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @throws Exception
	 */
	void load2() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_MODI_TPRODUCT_ADMIN_MANAGER
	 * @IN_PRODUCT_ID INT,
	 * @IN_ADMIN_MANAGER INT,
	 * @IN_INPUT_MAN INT
	 * @IN_ADMIN_MANAGER2 INT = NULL,
	 * @IN_MATAIN_MANAGER INT = NULL
	 */
	void saveAdmin_manager() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ��Ʒ���������� SP_CANCEL_TPRODUCT
	 * 
	 * @IN_PRODUCT_ID INT,
	 * @IN_END_DATE INT,
	 * @IN_INPUT_MAN INT
	 */
	void cancel() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_START_TPRODUCT
	 * @IN_PRODUCT_ID INT,
	 * @IN_START_DATE INT,
	 * @IN_FX_FEE DECIMAL(16,3),
	 * @IN_INPUT_MAN INT
	 */
	void start() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * @throws Exception
	 */
	void disStart() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 */
	void list() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @throws Exception
	 */
	void list1() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_QUERY_TPRODUCTDETAIL1
	 * @IN_PRODUCT_ID INT
	 */
	void listModiDetail1() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_QUERY_TPRODUCTDETAIL
	 * @IN_PRODUCT_ID INT
	 */
	void listModiDetail() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ��˲�Ʒ��Ϣ�޸ļ�¼
	 * add by liug 20101213
	 */
	void checkModiDetail() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @throws Exception
	 */
	void beforeFirst() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return
	 * @throws Exception
	 */
	boolean getNextModiDetail() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param DBdriver
	 * @return
	 * @throws Exception
	 */
	boolean getNext(String DBdriver) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param DBdriver
	 * @return
	 * @throws Exception
	 */
	boolean getNextLevel(String DBdriver) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param DBdriver
	 * @return
	 * @throws Exception
	 */
	boolean getNextInfo2(String DBdriver) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return
	 * @throws Exception
	 */
	boolean getNext_kemu() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return
	 * @throws Exception
	 */
	boolean getNextmore() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return
	 * @throws Exception
	 */
	boolean getNextmore2() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return
	 * @throws Exception
	 */
	boolean getNextOnce() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local" 
	 * 
	 * ��Ʒ��ѯ���Զ�������ʾ
	 * @return
	 * @throws Exception
	 */
	List getNextOnceAllList() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return
	 * @throws Exception
	 */
	boolean getNextOnce1() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param srcQuery
	 * @return
	 */
	String[] parseQuery(String srcQuery);

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_QUERY_TPRODUCT_VALID
	 * 
	 * @IN_BOOK_CODE INT,
	 * @IN_PRODUCT_CODE VARCHAR(6),
	 * @IN_ITEM_ID INT,
	 * @IN_PRODUCT_STATUS VARCHAR(10),
	 * @IN_INTRUST_TYPE VARCHAR(10),
	 * @IN_CURRENCY_ID VARCHAR(2),
	 * @IN_INPUT_MAN INT ,
	 * @IN_PRODUCT_NAME VARCHAR(60) = NULL,
	 * @IN_IS_LOCAL INT = NULL
	 * 
	 * @IN_DEPART_ID INT =0, --��Ʋ��� add by luohh 20090617
	 * @IN_OPEN_FLAG INT =0 --����ʽ/���ʽ��־��1����ʽ2���ʽ 0 ȫ�� add by luohh 20091026
	 *  
	 */
	void query(String sQuery) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_QUERY_TPRODUCT_FOR_FINACHECK
	 * @IN_BOOK_CODE INT,
	 * @IN_PRODUCT_ID INT,
	 * @IN_PRODUCT_CODE VARCHAR(10) = NULL,
	 * @IN_PRODUCT_NAME VARCHAR(60) = NULL,
	 * @IN_INPUT_MAN INT,
	 * @IN_FLAG INT = 1 -- 1��������ѯ�� 2����������ѯ�� 3��ѯ����������˻ָ�
	 */
	void listPublishCheckProduct() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_CHECK_START_TPRODUCT
	 * @IN_PRODUCT_ID INT,
	 * @IN_START_DATE INT,
	 * @IN_FX_FEE DECIMAL(16,3),
	 * @IN_INPUT_MAN INT,
	 * @IN_FLAG INT = 1 -- 1 ��Ʒ���г��� 2 ��Ʒ���в�����,11������˲�ͨ��
	 */
	void checkPublishProduct() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_UNCHECK_START_TPRODUCT
	 * @IN_PRODUCT_ID INT,
	 * @IN_INPUT_MAN INT,
	 * @IN_FLAG INT = 1 -- ��Ʒ���г���������˻ָ�
	 */
	void unCheckPublishProduct() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_QUERY_TPRODUCT
	 * @IN_BOOK_CODE INT,
	 * @IN_PRODUCT_CODE VARCHAR(6),
	 * @IN_ITEM_ID INT,
	 * @IN_PRODUCT_STATUS VARCHAR(10),
	 * @IN_INTRUST_TYPE VARCHAR(10),
	 * @IN_CURRENCY_ID VARCHAR(2),
	 * @IN_INPUT_MAN INT,
	 * @IN_PRODUCT_NAME VARCHAR(60) = NULL,
	 * @IN_IS_LOCAL INT = NULL,
	 * @IN_INTRUST_FLAG1 INT = NULL,
	 * @IN_INTRUST_FLAG2 INT = NULL,
	 * @IN_INTRUST_TYPE1 VARCHAR(10) = NULL,
	 * @IN_INTRUST_TYPE2 VARCHAR(10) = NULL,
	 * @IN_START_DATE INT = NULL,
	 * @IN_END_DATE INT = NULL,
	 * @IN_AMOUNT_MIN DECIMAL(16,3) = NULL,
	 * @IN_AMOUNT_MAX DECIMAL(16,3) = NULL
	 */
	void query2(String item_id2) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * SP_QUERY_TPRODUCT_SPECIAL_REVERSE
	 * 
	 * ��ѯ��������������ָ��Ĳ�Ʒ add by liug 20100818
	 */
	void query_toreverse(Integer book_code1, Integer input_man1) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * SP_START_TPRODUCT_SPECIAL_REVERSE
	 * 
	 * ��Ʒ��������ָ��洢���� add by liug 20100818
	 */
	void start_reverse() throws BusiException;

	/**
	 * add by jinxr 2007/4/26 ��Ʒ����Ȩ�����ò�ѯ��
	 * 
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_QUERY_TPRODUCT_RIGHT
	 * @IN_BOOK_CODE INT,
	 * @IN_PRODUCT_CODE VARCHAR(6),
	 * @IN_PRODUCT_NAME VARCHAR(60) = NULL,
	 * @IN_INPUT_MAN INT
	 */
	void queryright() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * add by jinxr 2007/4/26 ��Ʒ����Ȩ�����ò�ѯ��
	 * 
	 * @return
	 * @throws Exception
	 */
	boolean getNextright() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_QUERY_TPRODUCT_ID
	 * @IN_PRODUCT_ID int
	 */
	void listall() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return
	 * @throws Exception
	 */
	boolean getNextdate() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * update by tsg 2007-11-01
	 * 
	 * <pre>
	 * SP_QUERY_TPRODUCT @IN_BOOK_CODE INT,
	 *              @IN_PRODUCT_CODE VARCHAR(6),
	 *              @IN_ITEM_ID INT,
	 *              @IN_PRODUCT_STATUS VARCHAR(10),
	 *              @IN_INTRUST_TYPE VARCHAR(10),
	 *              @IN_CURRENCY_ID VARCHAR(2),
	 *              @IN_INPUT_MAN INT,
	 *              @IN_PRODUCT_NAME VARCHAR(60) = NULL,
	 *              @IN_IS_LOCAL INT = NULL,
	 *              @IN_INTRUST_FLAG1 INT = NULL,
	 *              @IN_INTRUST_FLAG2 INT = NULL,
	 *              @IN_INTRUST_TYPE1 VARCHAR(10) = NULL,
	 *              @IN_INTRUST_TYPE2 VARCHAR(10) = NULL,
	 *              @IN_START_DATE INT = NULL,
	 *              @IN_END_DATE INT = NULL,
	 *              @IN_AMOUNT_MIN DECIMAL(16,3) = NULL,
	 *              @IN_AMOUNT_MAX DECIMAL(16,3) = NULL,
	 *              @IN_ADMIN_MANAGER   INT = 0    -- ���о���
	 *              
	 *              2009-07-31 ADD
	 *              @IN_INTRUST_FLAG3    INT = NULL, --������ʽ��1˽ļ2��ļ
	 *              @IN_INTRUST_FLAG4    INT = NULL, --����Ŀ�ģ�1˽��2����
	 *              @IN_EXP_RATE         DECIMAL(5,4) = NULL, --Ԥ�������ʣ����ظ�ֵ�������������									    
	 *              @IN_FACT_MONEY       DECIMAL(16,3) = NULL,--��Ʒ���н����ش��ڵ��ڸ�ֵ��
	 *              --20090921 @IN_FACT_MONEY ɾ�� �� ��Ʒ��ģ �ظ�									    
	 *              @IN_CHECK_FLAG       INT, --�Ƿ����
	 *              @IN_VALID_PERIOD1     INT, --��Ʒ���ޣ���������Ϊ��λ��0Ϊ�����ޣ�����Ϊ����
	 *              @IN_VALID_PERIOD2     INT, --��Ʒ���ޣ���������Ϊ��λ��0Ϊ�����ޣ�����Ϊ����
	 *              @IN_FPFS             NVARCHAR(10), --�����˱�����ȡ��ʽ1105
	 *              @IN_OPEN_FLAG        INT  --���з�ʽ 1����ʽ2���ʽ
	 *              
	 *   			@IN_MANAGERTYPE      INT           = 0,  --1�������� 2�������� 20100308 DONGYG ADD
	 *              @IN_COPERATE_TYPE    INT           = 0   --������ʽ��1:���ź���;2:֤�ź���;3:˽ļ�������;4:��������       
	 *          
	 * </pre>
	 *  
	 */
	void query3(String product_code2) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param product_code2
	 * @throws Exception
	 */
	void query4(String product_code2) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @throws Exception
	 */
	void queryEnd() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @throws Exception
	 */
	void queryEndUnCheck() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @throws Exception
	 */
	void queryPost() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @throws Exception
	 */
	void queryUnPost() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return
	 * @throws Exception
	 */
	boolean getUnpostNext() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @throws Exception
	 */
	void queryTrans() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * �޸�SP_END_TPRODUCT�������������@IN_END_DATE������NULL��0ʱ�����ֶ�����Ϊ��Ʒ��END_DATE��
	 * 
	 * SP_END_TPRODUCT
	 * @IN_PRODUCT_ID INT,
	 * @IN_INPUT_MAN INT,
	 * @IN_END_DATE INT = NULL
	 */
	void end() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @throws Exception
	 */
	void endUnCheck() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ����Ͷ20050111
	 * 
	 * @param project_flag
	 * @return
	 * @throws Exception
	 */
	void queryProvLevel() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * �ж��Ƿ�������
	 * 
	 * SP_IFEXISTS_PROJECTS
	 * @IN_PROJECT_FLAG INT --�����־��1 ��Ŀ������2 ��Ʒչ������ ��3 ��Ʒ����������
	 * 
	 * @OUT_ISEXISTS_FLAG INT OUTPUT --�Ƿ���ڱ�־��1 �ǣ�0 �� ��@OUT_ISEXISTS_FLAG =1 ʱ
	 *                    ��ʾ��ʾ���ύ��������ť��������ʾ
	 *  
	 */

	Integer ifExistProject(Integer project_flag, Integer book_code) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_IFEXISTS_TPROBLEMS
	 * @IN_PRODUCT_ID INT, --��ƷID
	 * @IN_PROJECT_FLAG INT, --�����־��1 ��Ʒչ������ ��2 ��Ʒ����������
	 * @OUT_ISEXISTS_FLAG INT OUTPUT --�Ƿ����ύ��ť��־��1 �ǣ�0 �� --�Ƿ���ڱ�־��1 �ǣ�0 ��
	 *                    ��@OUT_ISEXISTS_FLAG =1 ʱ ��ʾ��ʾ��ť��������ʾ
	 *  
	 */

	Integer ifExistProBlem(Integer project_flag) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_ADD_TPRODUCTPERIOD_TPROBLEMS
	 * @IN_PRODUCT_ID INT, --��ĿID
	 * @IN_INPUT_MAN INT --��Ӳ�Ʒչ�ڵ��ύ����
	 */
	void appendUpdateperiodSP() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_ADD_TPRODUCTQUALITY_TPROBLEMS
	 * @IN_PRODUCT_ID INT, --��ƷID
	 * @IN_INPUT_MAN INT --��Ӳ�Ʒ��������������ύ����
	 */
	void appendProductQualitySP() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return
	 * @throws Exception
	 */
	boolean getNextProvLevel() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @throws Exception
	 */
	void listProudctForTrans() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * add by lk 2010-2-2 ����ƽ׼���б��ѯ
	 * 
	 * @throws Exception
	 */
	void listProudctForTrans4003() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return
	 * @throws Exception
	 */
	boolean getNextProudctForTrans() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @throws Exception
	 */
	void listcheck() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ���ú�����ʱ�Ĳ�ѯ
	 * 
	 * @throws Exception
	 */
	void listcheckAcc() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @throws Exception
	 */
	void check() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return
	 * @throws Exception
	 */
	boolean getNextCell() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @throws Exception
	 */
	void querycity() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return
	 * @throws Exception
	 */
	boolean getNextcity() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_QUERY_TPRODUCTGAINDATE
	 * @IN_PRODUCT_ID INT,
	 * @IN_TASK_TYPE INT =102 --����־��102 ����������� 103 ��Ϣ��¶����
	 */
	void queryPeriodDate(Integer task_type) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return
	 * @throws Exception
	 */
	boolean getNextPeriodDate() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @author nizh 20090331
	 * 
	 * ��������������ע�͵�ģ��Ϊ ���� > ��ѡ�� > Java > �������� > �����ע�� SP_QUERY_PRODUCT_PURPOSE
	 * @IN_PRODUCT_ID INT, --��ƷID
	 * @IN_INVEST_FLAG INT --Ͷ�ʱ�־��1 �����ͬ��2 ���޺�ͬ��3 Ͷ�ʺ�ͬ
	 */
	void queryProductPurpose(Integer invest_flag) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return
	 * @throws Exception
	 */
	boolean getNextProductPurpose() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param invest_flag
	 * @return
	 * @throws Exception
	 */
	String getProductPurpose(Integer invest_flag) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @throws Exception
	 */
	void adddeletecity() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * add by jinxr 2007/4/26 ��ѯ��Ʒ��ĿͶ��������ϸ
	 * 
	 * @throws Exception
	 */
	void querytz() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * add by jinxr 2007/4/26 ��ѯ��Ʒ��ĿͶ��������ϸ
	 * 
	 * 
	 * @return
	 * @throws Exception
	 */
	boolean getNexttz() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @throws Exception
	 */
	void queryDate() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @throws Exception
	 */
	void addDeleteDate() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return
	 * @throws Exception
	 */
	boolean getNextProductDate() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ��Ʒչ��
	 * 
	 * @throws Exception
	 */
	void modiPeriod() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ��Ʒչ�����
	 * 
	 * @throws Exception
	 */
	void checkPeriod() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ��ѯ��Ʒչ����Ϣ
	 * 
	 * @throws Exception
	 */
	void queryPeriod() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @throws Exception
	 */
	void loadPeriod() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return
	 * @throws Exception
	 */
	boolean getNextPeriod() throws Exception;

	/**
	 * ��ѯ�ƻ�ҵ���������
	 * 
	 * @ejb.interface-method view-type = "local"
	 * @throws Exception
	 */
	void queryBusinessEnd() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * �ƻ�ҵ�����
	 * 
	 * @throws Exception
	 */
	void endBusiness() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * �ƻ������ָ�
	 * 
	 * 
	 * @throws Exception
	 */
	void endUnBusinessCheck() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 *                                           @IN_PRODUCT_STATUS      VARCHAR(10) ='110203', --��Ʒ״̬(1102)              add by luohh 20100621
	                                      @IN_INTRUST_TYPE        VARCHAR(10)  ='',  --���÷�ʽ(1104)
	                                      @IN_IS_LOCAL            INT = 0,           --����ر�־��1 ���� 2���  add by luohh 20100621
	                                      @IN_INTRUST_FLAG1       INT = 0,           --��һ���ϣ�1 ��һ 2 ����   add by luohh 20100621
	                                      @IN_INTRUST_FLAG4       INT = 0,           --����Ŀ�ģ�1˽��2����      add by luohh 20100621
	                                      @IN_INTRUST_TYPE1       VARCHAR(10) = '',  --�������(1138)��          add by luohh 20100621
	                                      @IN_INTRUST_TYPE2       VARCHAR(10) = '',  --����Ͷ������(1139)        add by luohh 20100621
	                                      @IN_DEPART_ID           INT = 0,           --��Ʋ���                  add by luohh 20100621
	                                      @IN_FPFS                NVARCHAR(10)  = '', --�����˱�����ȡ��ʽ(1105) add by luohh 20100621
	                                      @IN_OPEN_FLAG           INT           = 0,  --���з�ʽ 1����ʽ2���ʽ  add by luohh 20100621
	                                      @IN_MANAGERTYPE         INT           = 0   --1�������� 2��������      add by luohh 20100621
	 * ��ѯ���в�Ʒ��ϸ
	 * 
	 * @throws Exception
	 */
	void queryProductDetail() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ������Ʒ��ϸ-Excel
	 * 
	 * @return
	 * @throws Exception
	 */
	CachedRowSet queryProductMessage() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * @return
	 * @throws Exception
	 */
	boolean getNextProductDetail() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @throws Exception
	 *             ��ѯ���в�Ʒʱ����ϸ
	 */
	void queryProductTimeDetail() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return
	 * @throws Exception
	 */
	boolean getNextProductTimeDetail() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ���ú����� SP_MODI_TPRODUCT_SUB_MAN
	 * 
	 * @IN_PRODUCT_ID INT,
	 * @IN_SUB_MAN INT,
	 * @IN_INPUT_MAN INT
	 */
	void saveSub_man() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ��ѯ��Ʒ��־
	 * 
	 * @throws Exception
	 */
	void queryProductLog() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return
	 * @throws Exception
	 */
	boolean getNextProductLog() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �Ƽ����޸�
	 * SP_MODI_TPRODUCTCITY @IN_SERIAL_NO         INT,
	 *			             @IN_GOV_PROV_REGIONAL VARCHAR(10) = '',           --ʡ����������(9999)
	 *			             @IN_GOV_REGIONAL      VARCHAR(10) = '',           --��������(9999)
	 *			             @IN_INPUT_MAN         INT
	 * @throws Exception
	 */
	void modiProductCity() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �Ƽ���ɾ��
	 * SP_DEL_TPRODUCTCITY  @IN_SERIAL_NO         INT,
	 *			            @IN_INPUT_MAN         INT
	 * @throws Exception
	 */
	void deleteProductCity() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ��ѯ��Ʒ��ֵ
	 * 
	 * @throws Exception
	 */
	void queryProductNav() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return
	 * @throws Exception
	 */
	boolean getNextProductNav() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ��ѯ��Ʒ��ֵ�Ƚ���Ϣ
	 * 
	 * @throws Exception
	 */
	void queryProductNav2() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return
	 * @throws Exception
	 */
	boolean getNextProductNav2() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_QUERY_TPRODUCT_CUST
	 * @IN_BOOK_CODE INT,
	 * @IN_PRODUCT_ID INT,
	 * @IN_INPUT_MAN INT = NULL ��ѯ�� INTRUST_FLAG1 ��һ���ϱ�־��1 ��һ 2 ���� FACT_MONEY
	 *               ʵ�ʷ����ܶ� ��INTRUST_FLAG1 =2 ��Ϊ���ϲ�Ʒʱ ҳ���жϴ����ͬ����Ƿ񳬹���Ʒʵ�ʷ����ܶ��50%
	 */
	void queryProductCust() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return
	 * @throws Exception
	 */
	boolean getNextProductCust() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_STAT_PRODUCTSCALE
	 * 
	 * @IN_FUNCTION_ID INT, --���ܺţ�101��"��Ŀ����"ͳ�����ܹ�ģ�ͱ��깱�� -- 102����һ/����ͳ�ƹ�ģ
	 * @IN_BEGIN_DATE INT, --��ʼ����
	 * @IN_END_DATE INT, --��������
	 * @IN_INPUT_MAN INT
	 * @throws Exception
	 */
	void queryChart() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ������ʾ��ͬͼ�� ȡ�ò�ͬ�Ĳ���
	 * 
	 * @param flag
	 * @return
	 * @throws Exception
	 */
	boolean getChartMessage(Integer flag) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ����product_idȡ��ֵ(����ֵ��ҵ��ֵ�� auto by lk 2009-10-22
	 * 
	 * @throws Exception
	 */
	void listForBusiNavPrice() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * auto by lk 2009-10-22
	 */
	void loadForBusiNavPrice() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @throws Exception
	 */
	void queryTrade_tax_rate() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ��ѯ��Ʒ����������Ϣ
	 * 
	 * @author lk 2009-11-30 ��������������ע�͵�ģ��Ϊ ���� > ��ѡ�� > Java > �������� > �����ע��
	 */

	void listProductNotice() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ��ȡ��Ʒ������������
	 * 
	 * @author lk 2009-11-30
	 * @return
	 * @throws Exception
	 */
	boolean getNextNotice() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ��ѯ���ù���������
	 * @throws Exception
	 */
	void queryProductOp() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ɾ�����ù���������
	 * @throws Exception
	 */
	void delProductOp() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ָ����Ʒ������
	 * @throws Exception
	 */
	void addProductOp() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ��ѯ�ò�Ʒ��ӵ�е����ù���������
	 * @throws Exception
	 */
	void queryOpPosses() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ��øò�Ʒ��ӵ�е����ù���������
	 * @return
	 * @throws Exception
	 */
	boolean getOpNext() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * �Ӳ�Ʒ����
	 * SP_CHECK_TSUBPRODUCT @IN_SUB_PRODUCT_ID INT,
	 *                      @IN_INPUT_MAN      INT 
	 * @throws Exception
	 */
	void subProductCheck() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 */
	void product_zjcl() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ��Ʒ�������� SP_QUERY_TPRODUCTLIMIT 
	 * @IN_BOOK_CODE        INTEGER,        --����
	 * @IN_PRODUCT_ID       INTEGER        --��ƷID
	 */
	void queryProductLimit() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 */
	boolean getNextProductLimit() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �����޸Ĳ�Ʒ�������� SP_ADD_TPRODUCTLIMIT 
	 * @IN_BOOK_CODE        INTEGER,        --����
	 * @IN_PRODUCT_ID       INTEGER,        --��ƷID
	 * @IN_QUALIFIED_FLAG   INTEGER,        --�ϸ�Ͷ���˿��ƣ�1.���ƣ�2.������
	 * @IN_QUALIFIED_NUM    INTEGER,        --�Ǻϸ�Ͷ���˺�ͬ����
	 * @IN_QUALIFIED_MONEY  DECIMAL(16,2),  --�ϸ�Ͷ���˺�ͬ�������
	 * @IN_ASFUND_FLAG      INTEGER,        --�Ƿ�Ϊ������Ʒ��1�ǣ�2��
	 * @IN_INPUT_MAN        INTEGER         --����Ա
	 */
	void updateProductLimit() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� acct_name��
	 */
	String getAcct_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param acct_name
	 *            Ҫ���õ� acct_name��
	 */
	void setAcct_name(String acct_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� admin_manager��
	 */
	java.lang.Integer getAdmin_manager();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param admin_manager
	 *            Ҫ���õ� admin_manager��
	 */
	void setAdmin_manager(java.lang.Integer admin_manager);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� admin_manager2��
	 */
	java.lang.Integer getAdmin_manager2();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param admin_manager2
	 *            Ҫ���õ� admin_manager2��
	 */
	void setAdmin_manager2(java.lang.Integer admin_manager2);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� all_flag��
	 */
	java.lang.Integer getAll_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param all_flag
	 *            Ҫ���õ� all_flag��
	 */
	void setAll_flag(java.lang.Integer all_flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� amount_max��
	 */
	java.math.BigDecimal getAmount_max();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param amount_max
	 *            Ҫ���õ� amount_max��
	 */
	void setAmount_max(java.math.BigDecimal amount_max);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� amount_min��
	 */
	java.math.BigDecimal getAmount_min();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param amount_min
	 *            Ҫ���õ� amount_min��
	 */
	void setAmount_min(java.math.BigDecimal amount_min);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� balance_3101��
	 */
	BigDecimal getBalance_3101();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param balance_3101
	 *            Ҫ���õ� balance_3101��
	 */
	void setBalance_3101(BigDecimal balance_3101);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� balance_all��
	 */
	BigDecimal getBalance_all();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param balance_all
	 *            Ҫ���õ� balance_all��
	 */
	void setBalance_all(BigDecimal balance_all);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� balance_lr��
	 */
	BigDecimal getBalance_lr();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param balance_lr
	 *            Ҫ���õ� balance_lr��
	 */
	void setBalance_lr(BigDecimal balance_lr);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� bank_acct��
	 */
	String getBank_acct();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param bank_acct
	 *            Ҫ���õ� bank_acct��
	 */
	void setBank_acct(String bank_acct);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� bank_name��
	 */
	String getBank_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param bank_name
	 *            Ҫ���õ� bank_name��
	 */
	void setBank_name(String bank_name);

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
	 * @return ���� ben_money��
	 */
	java.math.BigDecimal getBen_money();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ben_money
	 *            Ҫ���õ� ben_money��
	 */
	void setBen_money(java.math.BigDecimal ben_money);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� ben_num��
	 */
	java.lang.Integer getBen_num();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ben_num
	 *            Ҫ���õ� ben_num��
	 */
	void setBen_num(java.lang.Integer ben_num);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� ben_period��
	 */
	java.lang.Integer getBen_period();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ben_period
	 *            Ҫ���õ� ben_period��
	 */
	void setBen_period(java.lang.Integer ben_period);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� ben_period_temp��
	 */
	int getBen_period_temp();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ben_period_temp
	 *            Ҫ���õ� ben_period_temp��
	 */
	void setBen_period_temp(int ben_period_temp);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� bg_bank_id��
	 */
	String getBg_bank_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param bg_bank_id
	 *            Ҫ���õ� bg_bank_id��
	 */
	void setBg_bank_id(String bg_bank_id);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� bg_bank_name��
	 */
	String getBg_bank_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param bg_bank_name
	 *            Ҫ���õ� bg_bank_name��
	 */
	void setBg_bank_name(String bg_bank_name);

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
	 * @return ���� busi_flag��
	 */
	Integer getBusi_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param busi_flag
	 *            Ҫ���õ� busi_flag��
	 */
	void setBusi_flag(Integer busi_flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� busi_name��
	 */
	String getBusi_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param busi_name
	 *            Ҫ���õ� busi_name��
	 */
	void setBusi_name(String busi_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� busi_nav_price��
	 */
	java.math.BigDecimal getBusi_nav_price();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param busi_nav_price
	 *            Ҫ���õ� busi_nav_price��
	 */
	void setBusi_nav_price(java.math.BigDecimal busi_nav_price);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� business_end_date��
	 */
	Integer getBusiness_end_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param business_end_date
	 *            Ҫ���õ� business_end_date��
	 */
	void setBusiness_end_date(Integer business_end_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� business_end_flag��
	 */
	Integer getBusiness_end_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param business_end_flag
	 *            Ҫ���õ� business_end_flag��
	 */
	void setBusiness_end_flag(Integer business_end_flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� change_wt_flag��
	 */
	java.lang.Integer getChange_wt_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param change_wt_flag
	 *            Ҫ���õ� change_wt_flag��
	 */
	void setChange_wt_flag(java.lang.Integer change_wt_flag);

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
	 * @return ���� city_name��
	 */
	java.lang.String getCity_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param city_name
	 *            Ҫ���õ� city_name��
	 */
	void setCity_name(java.lang.String city_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� contract_num��
	 */
	java.lang.Integer getContract_num();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param contract_num
	 *            Ҫ���õ� contract_num��
	 */
	void setContract_num(java.lang.Integer contract_num);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� contract_sub_bh��
	 */
	String getContract_sub_bh();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param contract_sub_bh
	 *            Ҫ���õ� contract_sub_bh��
	 */
	void setContract_sub_bh(String contract_sub_bh);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� curr_fact_money��
	 */
	BigDecimal getCurr_fact_money();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param curr_fact_money
	 *            Ҫ���õ� curr_fact_money��
	 */
	void setCurr_fact_money(BigDecimal curr_fact_money);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� currency_id��
	 */
	java.lang.String getCurrency_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param currency_id
	 *            Ҫ���õ� currency_id��
	 */
	void setCurrency_id(java.lang.String currency_id);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� current_month��
	 */
	Integer getCurrent_month();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param current_month
	 *            Ҫ���õ� current_month��
	 */
	void setCurrent_month(Integer current_month);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� cust_name��
	 */
	String getCust_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param cust_name
	 *            Ҫ���õ� cust_name��
	 */
	void setCust_name(String cust_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� cw_money��
	 */
	java.math.BigDecimal getCw_money();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param cw_money
	 *            Ҫ���õ� cw_money��
	 */
	void setCw_money(java.math.BigDecimal cw_money);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� dBdriver��
	 */
	String getDBdriver();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param bdriver
	 *            Ҫ���õ� dBdriver��
	 */
	void setDBdriver(String bdriver);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� deal_type��
	 */
	java.lang.String getDeal_type();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param deal_type
	 *            Ҫ���õ� deal_type��
	 */
	void setDeal_type(java.lang.String deal_type);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� deal_type_name��
	 */
	java.lang.String getDeal_type_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param deal_type_name
	 *            Ҫ���õ� deal_type_name��
	 */
	void setDeal_type_name(java.lang.String deal_type_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� depart_id��
	 */
	java.lang.Integer getDepart_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param depart_id
	 *            Ҫ���õ� depart_id��
	 */
	void setDepart_id(java.lang.Integer depart_id);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� description��
	 */
	java.lang.String getDescription();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param description
	 *            Ҫ���õ� description��
	 */
	void setDescription(java.lang.String description);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� end_date��
	 */
	java.lang.Integer getEnd_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param end_date
	 *            Ҫ���õ� end_date��
	 */
	void setEnd_date(java.lang.Integer end_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� end_date2��
	 */
	java.lang.Integer getEnd_date2();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param end_date2
	 *            Ҫ���õ� end_date2��
	 */
	void setEnd_date2(java.lang.Integer end_date2);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� end_flag��
	 */
	Integer getEnd_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param end_flag
	 *            Ҫ���õ� end_flag��
	 */
	void setEnd_flag(Integer end_flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� entity_type��
	 */
	java.lang.String getEntity_type();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param entity_type
	 *            Ҫ���õ� entity_type��
	 */
	void setEntity_type(java.lang.String entity_type);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� entity_type_name��
	 */
	java.lang.String getEntity_type_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param entity_type_name
	 *            Ҫ���õ� entity_type_name��
	 */
	void setEntity_type_name(java.lang.String entity_type_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� exp_rate1��
	 */
	java.math.BigDecimal getExp_rate1();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param exp_rate1
	 *            Ҫ���õ� exp_rate1��
	 */
	void setExp_rate1(java.math.BigDecimal exp_rate1);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� exp_rate2��
	 */
	java.math.BigDecimal getExp_rate2();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param exp_rate2
	 *            Ҫ���õ� exp_rate2��
	 */
	void setExp_rate2(java.math.BigDecimal exp_rate2);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� ext_rate1��
	 */
	BigDecimal getExt_rate1();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ext_rate1
	 *            Ҫ���õ� ext_rate1��
	 */
	void setExt_rate1(BigDecimal ext_rate1);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� ext_rate2��
	 */
	BigDecimal getExt_rate2();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ext_rate2
	 *            Ҫ���õ� ext_rate2��
	 */
	void setExt_rate2(BigDecimal ext_rate2);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� extend_flag��
	 */
	java.lang.Integer getExtend_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param extend_flag
	 *            Ҫ���õ� extend_flag��
	 */
	void setExtend_flag(java.lang.Integer extend_flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� fact_end_date��
	 */
	Integer getFact_end_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param fact_end_date
	 *            Ҫ���õ� fact_end_date��
	 */
	void setFact_end_date(Integer fact_end_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� fact_money��
	 */
	java.math.BigDecimal getFact_money();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param fact_money
	 *            Ҫ���õ� fact_money��
	 */
	void setFact_money(java.math.BigDecimal fact_money);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� fact_num��
	 */
	java.lang.Integer getFact_num();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param fact_num
	 *            Ҫ���õ� fact_num��
	 */
	void setFact_num(java.lang.Integer fact_num);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� fact_person_num��
	 */
	java.lang.Integer getFact_person_num();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param fact_person_num
	 *            Ҫ���õ� fact_person_num��
	 */
	void setFact_person_num(java.lang.Integer fact_person_num);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� fact_pre_money��
	 */
	java.math.BigDecimal getFact_pre_money();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param fact_pre_money
	 *            Ҫ���õ� fact_pre_money��
	 */
	void setFact_pre_money(java.math.BigDecimal fact_pre_money);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� fact_pre_num��
	 */
	java.lang.Integer getFact_pre_num();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param fact_pre_num
	 *            Ҫ���õ� fact_pre_num��
	 */
	void setFact_pre_num(java.lang.Integer fact_pre_num);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� fee��
	 */
	BigDecimal getFee();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param fee
	 *            Ҫ���õ� fee��
	 */
	void setFee(BigDecimal fee);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� field_cn_name��
	 */
	java.lang.String getField_cn_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param field_cn_name
	 *            Ҫ���õ� field_cn_name��
	 */
	void setField_cn_name(java.lang.String field_cn_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� field_name��
	 */
	java.lang.String getField_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param field_name
	 *            Ҫ���õ� field_name��
	 */
	void setField_name(java.lang.String field_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� flag��
	 */
	java.lang.Integer getFlag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param flag
	 *            Ҫ���õ� flag��
	 */
	void setFlag(java.lang.Integer flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� fpfs��
	 */
	java.lang.String getFpfs();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param fpfs
	 *            Ҫ���õ� fpfs��
	 */
	void setFpfs(java.lang.String fpfs);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� fpfs_name��
	 */
	java.lang.String getFpfs_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param fpfs_name
	 *            Ҫ���õ� fpfs_name��
	 */
	void setFpfs_name(java.lang.String fpfs_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� fx_fee��
	 */
	java.math.BigDecimal getFx_fee();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param fx_fee
	 *            Ҫ���õ� fx_fee��
	 */
	void setFx_fee(java.math.BigDecimal fx_fee);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� gain_money��
	 */
	java.math.BigDecimal getGain_money();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param gain_money
	 *            Ҫ���õ� gain_money��
	 */
	void setGain_money(java.math.BigDecimal gain_money);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� gr_amount��
	 */
	java.math.BigDecimal getGr_amount();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param gr_amount
	 *            Ҫ���õ� gr_amount��
	 */
	void setGr_amount(java.math.BigDecimal gr_amount);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� gr_count��
	 */
	java.lang.Integer getGr_count();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param gr_count
	 *            Ҫ���õ� gr_count��
	 */
	void setGr_count(java.lang.Integer gr_count);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� gr_money��
	 */
	java.math.BigDecimal getGr_money();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param gr_money
	 *            Ҫ���õ� gr_money��
	 */
	void setGr_money(java.math.BigDecimal gr_money);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� gr_num��
	 */
	Integer getGr_num();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param gr_num
	 *            Ҫ���õ� gr_num��
	 */
	void setGr_num(Integer gr_num);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� hq_date��
	 */
	Integer getHq_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param hq_date
	 *            Ҫ���õ� hq_date��
	 */
	void setHq_date(Integer hq_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� ht_money��
	 */
	java.math.BigDecimal getHt_money();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ht_money
	 *            Ҫ���õ� ht_money��
	 */
	void setHt_money(java.math.BigDecimal ht_money);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� info_period��
	 */
	java.lang.Integer getInfo_period();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param info_period
	 *            Ҫ���õ� info_period��
	 */
	void setInfo_period(java.lang.Integer info_period);

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
	 * @return ���� intrust_flag1��
	 */
	java.lang.Integer getIntrust_flag1();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param intrust_flag1
	 *            Ҫ���õ� intrust_flag1��
	 */
	void setIntrust_flag1(java.lang.Integer intrust_flag1);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� intrust_flag1_name��
	 */
	String getIntrust_flag1_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param intrust_flag1_name
	 *            Ҫ���õ� intrust_flag1_name��
	 */
	void setIntrust_flag1_name(String intrust_flag1_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� intrust_flag2��
	 */
	java.lang.Integer getIntrust_flag2();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param intrust_flag2
	 *            Ҫ���õ� intrust_flag2��
	 */
	void setIntrust_flag2(java.lang.Integer intrust_flag2);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� intrust_flag3��
	 */
	java.lang.Integer getIntrust_flag3();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param intrust_flag3
	 *            Ҫ���õ� intrust_flag3��
	 */
	void setIntrust_flag3(java.lang.Integer intrust_flag3);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� intrust_flag4��
	 */
	java.lang.Integer getIntrust_flag4();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param intrust_flag4
	 *            Ҫ���õ� intrust_flag4��
	 */
	void setIntrust_flag4(java.lang.Integer intrust_flag4);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� intrust_type��
	 */
	java.lang.String getIntrust_type();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param intrust_type
	 *            Ҫ���õ� intrust_type��
	 */
	void setIntrust_type(java.lang.String intrust_type);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� intrust_type_name��
	 */
	java.lang.String getIntrust_type_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param intrust_type_name
	 *            Ҫ���õ� intrust_type_name��
	 */
	void setIntrust_type_name(java.lang.String intrust_type_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� intrust_type1��
	 */
	java.lang.String getIntrust_type1();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param intrust_type1
	 *            Ҫ���õ� intrust_type1��
	 */
	void setIntrust_type1(java.lang.String intrust_type1);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� intrust_type1_name��
	 */
	java.lang.String getIntrust_type1_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param intrust_type1_name
	 *            Ҫ���õ� intrust_type1_name��
	 */
	void setIntrust_type1_name(java.lang.String intrust_type1_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� intrust_type2��
	 */
	java.lang.String getIntrust_type2();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param intrust_type2
	 *            Ҫ���õ� intrust_type2��
	 */
	void setIntrust_type2(java.lang.String intrust_type2);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� intrust_type2_name��
	 */
	java.lang.String getIntrust_type2_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param intrust_type2_name
	 *            Ҫ���õ� intrust_type2_name��
	 */
	void setIntrust_type2_name(java.lang.String intrust_type2_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� invest_type��
	 */
	String getInvest_type();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param invest_type
	 *            Ҫ���õ� invest_type��
	 */
	void setInvest_type(String invest_type);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� item_code��
	 */
	java.lang.String getItem_code();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param item_code
	 *            Ҫ���õ� item_code��
	 */
	void setItem_code(java.lang.String item_code);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� item_id��
	 */
	java.lang.Integer getItem_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param item_id
	 *            Ҫ���õ� item_id��
	 */
	void setItem_id(java.lang.Integer item_id);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� jg_amount��
	 */
	java.math.BigDecimal getJg_amount();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param jg_amount
	 *            Ҫ���õ� jg_amount��
	 */
	void setJg_amount(java.math.BigDecimal jg_amount);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� jg_count��
	 */
	java.lang.Integer getJg_count();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param jg_count
	 *            Ҫ���õ� jg_count��
	 */
	void setJg_count(java.lang.Integer jg_count);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� jg_money��
	 */
	java.math.BigDecimal getJg_money();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param jg_money
	 *            Ҫ���õ� jg_money��
	 */
	void setJg_money(java.math.BigDecimal jg_money);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� jg_num��
	 */
	Integer getJg_num();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param jg_num
	 *            Ҫ���õ� jg_num��
	 */
	void setJg_num(Integer jg_num);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� last_post_date��
	 */
	java.lang.Integer getLast_post_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param last_post_date
	 *            Ҫ���õ� last_post_date��
	 */
	void setLast_post_date(java.lang.Integer last_post_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� list_id��
	 */
	Integer getList_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param list_id
	 *            Ҫ���õ� list_id��
	 */
	void setList_id(Integer list_id);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� list_name��
	 */
	String getList_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param list_name
	 *            Ҫ���õ� list_name��
	 */
	void setList_name(String list_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� manage_fee��
	 */
	java.math.BigDecimal getManage_fee();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param manage_fee
	 *            Ҫ���õ� manage_fee��
	 */
	void setManage_fee(java.math.BigDecimal manage_fee);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� manage_rate��
	 */
	java.math.BigDecimal getManage_rate();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param manage_rate
	 *            Ҫ���õ� manage_rate��
	 */
	void setManage_rate(java.math.BigDecimal manage_rate);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� manager_type��
	 */
	Integer getManager_type();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param manager_type
	 *            Ҫ���õ� manager_type��
	 */
	void setManager_type(Integer manager_type);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� matain_manager��
	 */
	java.lang.Integer getMatain_manager();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param matain_manager
	 *            Ҫ���õ� matain_manager��
	 */
	void setMatain_manager(java.lang.Integer matain_manager);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� min_money��
	 */
	java.math.BigDecimal getMin_money();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param min_money
	 *            Ҫ���õ� min_money��
	 */
	void setMin_money(java.math.BigDecimal min_money);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� nav_float_num��
	 */
	Integer getNav_float_num();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param nav_float_num
	 *            Ҫ���õ� nav_float_num��
	 */
	void setNav_float_num(Integer nav_float_num);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� nav_price��
	 */
	java.math.BigDecimal getNav_price();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param nav_price
	 *            Ҫ���õ� nav_price��
	 */
	void setNav_price(java.math.BigDecimal nav_price);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� nav_price1��
	 */
	BigDecimal getNav_price1();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param nav_price1
	 *            Ҫ���õ� nav_price1��
	 */
	void setNav_price1(BigDecimal nav_price1);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� nav_price2��
	 */
	BigDecimal getNav_price2();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param nav_price2
	 *            Ҫ���õ� nav_price2��
	 */
	void setNav_price2(BigDecimal nav_price2);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� new_end_date��
	 */
	Integer getNew_end_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param new_end_date
	 *            Ҫ���õ� new_end_date��
	 */
	void setNew_end_date(Integer new_end_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� new_field_info��
	 */
	java.lang.String getNew_field_info();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param new_field_info
	 *            Ҫ���õ� new_field_info��
	 */
	void setNew_field_info(java.lang.String new_field_info);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� old_end_date��
	 */
	Integer getOld_end_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param old_end_date
	 *            Ҫ���õ� old_end_date��
	 */
	void setOld_end_date(Integer old_end_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� old_field_info��
	 */
	java.lang.String getOld_field_info();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param old_field_info
	 *            Ҫ���õ� old_field_info��
	 */
	void setOld_field_info(java.lang.String old_field_info);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� op_code��
	 */
	Integer getOp_code();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param op_code
	 *            Ҫ���õ� op_code��
	 */
	void setOp_code(Integer op_code);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� op_name��
	 */
	String getOp_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param op_name
	 *            Ҫ���õ� op_name��
	 */
	void setOp_name(String op_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� open_flag��
	 */
	java.lang.Integer getOpen_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param open_flag
	 *            Ҫ���õ� open_flag��
	 */
	void setOpen_flag(java.lang.Integer open_flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� open_flag_name��
	 */
	java.lang.String getOpen_flag_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param open_flag_name
	 *            Ҫ���õ� open_flag_name��
	 */
	void setOpen_flag_name(java.lang.String open_flag_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� org_count��
	 */
	Integer getOrg_count();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param org_count
	 *            Ҫ���õ� org_count��
	 */
	void setOrg_count(Integer org_count);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� period_unit��
	 */
	Integer getPeriod_unit();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param period_unit
	 *            Ҫ���õ� period_unit��
	 */
	void setPeriod_unit(Integer period_unit);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� person_count��
	 */
	Integer getPerson_count();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param person_count
	 *            Ҫ���õ� person_count��
	 */
	void setPerson_count(Integer person_count);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� pre_code��
	 */
	java.lang.String getPre_code();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param pre_code
	 *            Ҫ���õ� pre_code��
	 */
	void setPre_code(java.lang.String pre_code);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� pre_end_date��
	 */
	java.lang.Integer getPre_end_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param pre_end_date
	 *            Ҫ���õ� pre_end_date��
	 */
	void setPre_end_date(java.lang.Integer pre_end_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� pre_max_money��
	 */
	java.math.BigDecimal getPre_max_money();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param pre_max_money
	 *            Ҫ���õ� pre_max_money��
	 */
	void setPre_max_money(java.math.BigDecimal pre_max_money);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� pre_max_num��
	 */
	java.lang.Integer getPre_max_num();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param pre_max_num
	 *            Ҫ���õ� pre_max_num��
	 */
	void setPre_max_num(java.lang.Integer pre_max_num);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� pre_max_rate��
	 */
	java.math.BigDecimal getPre_max_rate();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param pre_max_rate
	 *            Ҫ���õ� pre_max_rate��
	 */
	void setPre_max_rate(java.math.BigDecimal pre_max_rate);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� pre_money��
	 */
	java.math.BigDecimal getPre_money();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param pre_money
	 *            Ҫ���õ� pre_money��
	 */
	void setPre_money(java.math.BigDecimal pre_money);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� pre_num��
	 */
	java.lang.Integer getPre_num();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param pre_num
	 *            Ҫ���õ� pre_num��
	 */
	void setPre_num(java.lang.Integer pre_num);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� pre_start_date��
	 */
	java.lang.Integer getPre_start_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param pre_start_date
	 *            Ҫ���õ� pre_start_date��
	 */
	void setPre_start_date(java.lang.Integer pre_start_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� product_code��
	 */
	java.lang.String getProduct_code();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param product_code
	 *            Ҫ���õ� product_code��
	 */
	void setProduct_code(java.lang.String product_code);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� product_from��
	 */
	java.lang.Integer getProduct_from();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param product_from
	 *            Ҫ���õ� product_from��
	 */
	void setProduct_from(java.lang.Integer product_from);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� product_id��
	 */
	java.lang.Integer getProduct_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param product_id
	 *            Ҫ���õ� product_id��
	 */
	void setProduct_id(java.lang.Integer product_id);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� product_info��
	 */
	java.lang.String getProduct_info();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param product_info
	 *            Ҫ���õ� product_info��
	 */
	void setProduct_info(java.lang.String product_info);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� product_jc��
	 */
	java.lang.String getProduct_jc();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param product_jc
	 *            Ҫ���õ� product_jc��
	 */
	void setProduct_jc(java.lang.String product_jc);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� product_name��
	 */
	java.lang.String getProduct_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param product_name
	 *            Ҫ���õ� product_name��
	 */
	void setProduct_name(java.lang.String product_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� product_status��
	 */
	java.lang.String getProduct_status();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param product_status
	 *            Ҫ���õ� product_status��
	 */
	void setProduct_status(java.lang.String product_status);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� product_status_name��
	 */
	java.lang.String getProduct_status_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param product_status_name
	 *            Ҫ���õ� product_status_name��
	 */
	void setProduct_status_name(java.lang.String product_status_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� productstatusName��
	 */
	String getProductstatusName();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param productstatusName
	 *            Ҫ���õ� productstatusName��
	 */
	void setProductstatusName(String productstatusName);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� prov_level��
	 */
	java.lang.String getProv_level();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param prov_level
	 *            Ҫ���õ� prov_level��
	 */
	void setProv_level(java.lang.String prov_level);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� prov_level_a_money��
	 */
	java.math.BigDecimal getProv_level_a_money();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param prov_level_a_money
	 *            Ҫ���õ� prov_level_a_money��
	 */
	void setProv_level_a_money(java.math.BigDecimal prov_level_a_money);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� prov_level_b_money��
	 */
	java.math.BigDecimal getProv_level_b_money();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param prov_level_b_money
	 *            Ҫ���õ� prov_level_b_money��
	 */
	void setProv_level_b_money(java.math.BigDecimal prov_level_b_money);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� prov_level_name��
	 */
	java.lang.String getProv_level_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param prov_level_name
	 *            Ҫ���õ� prov_level_name��
	 */
	void setProv_level_name(java.lang.String prov_level_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� quality_level��
	 */
	java.lang.String getQuality_level();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param quality_level
	 *            Ҫ���õ� quality_level��
	 */
	void setQuality_level(java.lang.String quality_level);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� quality_level_name��
	 */
	java.lang.String getQuality_level_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param quality_level_name
	 *            Ҫ���õ� quality_level_name��
	 */
	void setQuality_level_name(java.lang.String quality_level_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� sAdmin_man��
	 */
	String getSAdmin_man();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param admin_man
	 *            Ҫ���õ� sAdmin_man��
	 */
	void setSAdmin_man(String admin_man);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� sAdmin_man2��
	 */
	String getSAdmin_man2();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param admin_man2
	 *            Ҫ���õ� sAdmin_man2��
	 */
	void setSAdmin_man2(String admin_man2);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� serial_no��
	 */
	java.lang.Integer getSerial_no();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param serial_no
	 *            Ҫ���õ� serial_no��
	 */
	void setSerial_no(java.lang.Integer serial_no);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� sl_flag��
	 */
	java.lang.Integer getSl_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param sl_flag
	 *            Ҫ���õ� sl_flag��
	 */
	void setSl_flag(java.lang.Integer sl_flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� sMatain_man��
	 */
	String getSMatain_man();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param matain_man
	 *            Ҫ���õ� sMatain_man��
	 */
	void setSMatain_man(String matain_man);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� start_date��
	 */
	java.lang.Integer getStart_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param start_date
	 *            Ҫ���õ� start_date��
	 */
	void setStart_date(java.lang.Integer start_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� start_date2��
	 */
	java.lang.Integer getStart_date2();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param start_date2
	 *            Ҫ���õ� start_date2��
	 */
	void setStart_date2(java.lang.Integer start_date2);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� sub_check_flag��
	 */
	java.lang.Integer getSub_check_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param sub_check_flag
	 *            Ҫ���õ� sub_check_flag��
	 */
	void setSub_check_flag(java.lang.Integer sub_check_flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� sub_flag��
	 */
	Integer getSub_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param sub_flag
	 *            Ҫ���õ� sub_flag��
	 */
	void setSub_flag(Integer sub_flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� sub_man��
	 */
	Integer getSub_man();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param sub_man
	 *            Ҫ���õ� sub_man��
	 */
	void setSub_man(Integer sub_man);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� sub_man_name��
	 */
	String getSub_man_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param sub_man_name
	 *            Ҫ���õ� sub_man_name��
	 */
	void setSub_man_name(String sub_man_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� sub_product_id��
	 */
	Integer getSub_product_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param sub_product_id
	 *            Ҫ���õ� sub_product_id��
	 */
	void setSub_product_id(Integer sub_product_id);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� summary��
	 */
	java.lang.String getSummary();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param summary
	 *            Ҫ���õ� summary��
	 */
	void setSummary(java.lang.String summary);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� task_date��
	 */
	Integer getTask_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param task_date
	 *            Ҫ���õ� task_date��
	 */
	void setTask_date(Integer task_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� tax_rate��
	 */
	BigDecimal getTax_rate();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param tax_rate
	 *            Ҫ���õ� tax_rate��
	 */
	void setTax_rate(BigDecimal tax_rate);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� tg_acct_name��
	 */
	String getTg_acct_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param tg_acct_name
	 *            Ҫ���õ� tg_acct_name��
	 */
	void setTg_acct_name(String tg_acct_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� tg_bank_acct��
	 */
	java.lang.String getTg_bank_acct();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param tg_bank_acct
	 *            Ҫ���õ� tg_bank_acct��
	 */
	void setTg_bank_acct(java.lang.String tg_bank_acct);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� tg_bank_id��
	 */
	java.lang.String getTg_bank_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param tg_bank_id
	 *            Ҫ���õ� tg_bank_id��
	 */
	void setTg_bank_id(java.lang.String tg_bank_id);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� tg_bank_name��
	 */
	java.lang.String getTg_bank_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param tg_bank_name
	 *            Ҫ���õ� tg_bank_name��
	 */
	void setTg_bank_name(java.lang.String tg_bank_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� tg_bank_sub_id��
	 */
	java.lang.String getTg_bank_sub_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param tg_bank_sub_id
	 *            Ҫ���õ� tg_bank_sub_id��
	 */
	void setTg_bank_sub_id(java.lang.String tg_bank_sub_id);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� tg_bank_sub_name��
	 */
	java.lang.String getTg_bank_sub_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param tg_bank_sub_name
	 *            Ҫ���õ� tg_bank_sub_name��
	 */
	void setTg_bank_sub_name(java.lang.String tg_bank_sub_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� time_flag��
	 */
	Integer getTime_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param time_flag
	 *            Ҫ���õ� time_flag��
	 */
	void setTime_flag(Integer time_flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� total_amount��
	 */
	java.math.BigDecimal getTotal_amount();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param total_amount
	 *            Ҫ���õ� total_amount��
	 */
	void setTotal_amount(java.math.BigDecimal total_amount);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� total_fact_money��
	 */
	String[] getTotal_fact_money();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param total_fact_money
	 *            Ҫ���õ� total_fact_money��
	 */
	void setTotal_fact_money(String[] total_fact_money);

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
	 * @return ���� trade_date��
	 */
	java.lang.Integer getTrade_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param trade_date
	 *            Ҫ���õ� trade_date��
	 */
	void setTrade_date(java.lang.Integer trade_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� trade_rate��
	 */
	java.math.BigDecimal getTrade_rate();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param trade_rate
	 *            Ҫ���õ� trade_rate��
	 */
	void setTrade_rate(java.math.BigDecimal trade_rate);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� trade_tax_rate��
	 */
	BigDecimal getTrade_tax_rate();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param trade_tax_rate
	 *            Ҫ���õ� trade_tax_rate��
	 */
	void setTrade_tax_rate(BigDecimal trade_tax_rate);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� trade_time��
	 */
	Timestamp getTrade_time();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param trade_time
	 *            Ҫ���õ� trade_time��
	 */
	void setTrade_time(Timestamp trade_time);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� trade_type��
	 */
	java.lang.String getTrade_type();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param trade_type
	 *            Ҫ���õ� trade_type��
	 */
	void setTrade_type(java.lang.String trade_type);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� trade_type_name��
	 */
	java.lang.String getTrade_type_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param trade_type_name
	 *            Ҫ���õ� trade_type_name��
	 */
	void setTrade_type_name(java.lang.String trade_type_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� trust_contract_name��
	 */
	String getTrust_contract_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param trust_contract_name
	 *            Ҫ���õ� trust_contract_name��
	 */
	void setTrust_contract_name(String trust_contract_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� valid_period��
	 */
	java.lang.Integer getValid_period();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param valid_period
	 *            Ҫ���õ� valid_period��
	 */
	void setValid_period(java.lang.Integer valid_period);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� valid_period2��
	 */
	Integer getValid_period2();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param valid_period2
	 *            Ҫ���õ� valid_period2��
	 */
	void setValid_period2(Integer valid_period2);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� zjye��
	 */
	java.math.BigDecimal getZjye();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param zjye
	 *            Ҫ���õ� zjye��
	 */
	void setZjye(java.math.BigDecimal zjye);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� managetype_name��
	 */
	String getManagetype_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� result_standard��
	 */
	BigDecimal getResult_standard();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ����
	 *            result_standard��
	 */
	void setResult_standard(BigDecimal result_standard);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� share_flag��
	 */
	Integer getShare_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param share_flag Ҫ���õ� share_flag��
	 */
	void setShare_flag(Integer share_flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� coperate_type��
	 */
	Integer getCoperate_type();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param coperate_type Ҫ���õ� coperate_type��
	 */
	void setCoperate_type(Integer coperate_type);

	/**
	 *  @ejb.interface-method view-type = "local"
	 * @return ���� gov_pegional��
	 */
	String getGov_pegional();

	/**
	 *  @ejb.interface-method view-type = "local"
	 * @param gov_pegional Ҫ���õ� gov_pegional��
	 */
	void setGov_pegional(String gov_pegional);

	/**
	 *  @ejb.interface-method view-type = "local"
	 * @return ���� gov_prov_pegional��
	 */
	String getGov_prov_pegional();

	/**
	 *  @ejb.interface-method view-type = "local"
	 * @param gov_prov_pegional Ҫ���õ� gov_prov_pegional��
	 */
	void setGov_prov_pegional(String gov_prov_pegional);

	/**
	 *  @ejb.interface-method view-type = "local"
	 * @return ���� org_money��
	 */
	java.math.BigDecimal getOrg_money();

	/**
	 *  @ejb.interface-method view-type = "local"
	 * @param org_money Ҫ���õ� org_money��
	 */
	void setOrg_money(java.math.BigDecimal org_money);

	/**
	 *  @ejb.interface-method view-type = "local"
	 * @return ���� person_money��
	 */
	java.math.BigDecimal getPerson_money();

	/**
	 *  @ejb.interface-method view-type = "local"
	 * @param person_money Ҫ���õ� person_money��
	 */
	void setPerson_money(java.math.BigDecimal person_money);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� sub_product_code��
	 */
	String getSub_product_code();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param sub_product_code Ҫ���õ� sub_product_code��
	 */
	void setSub_product_code(String sub_product_code);

	/**
	 * @ejb.interface-method view-type = "local"
	 */
	java.math.BigDecimal getOriginal_money();

	/**
	 * @ejb.interface-method view-type = "local"
	 */
	void setOriginal_money(java.math.BigDecimal originalMoney);

	/**
	 *@ejb.interface-method view-type = "local"
	 */
	Integer getWith_bank_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 */
	void setWith_bank_flag(Integer with_bank_flag);

	/**
	 *  @ejb.interface-method view-type = "local"
	 */
	Integer getSub_fund_flag();

	/**
	 *  @ejb.interface-method view-type = "local"
	 */
	void setSub_fund_flag(Integer sub_fund_flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 */
	Integer getProv_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 */
	void setProv_flag(Integer prov_flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� deal_flag��
	 */
	Integer getDeal_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param deal_flag Ҫ���õ� deal_flag��
	 */
	void setDeal_flag(Integer deal_flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 */
	java.math.BigDecimal getQualified_count();

	/**
	 * @ejb.interface-method view-type = "local"
	 */
	void setQualified_count(java.math.BigDecimal qualified_count);

	/**
	 * @ejb.interface-method view-type = "local"
	 */
	java.math.BigDecimal getQualified_money();

	/**
	 * @ejb.interface-method view-type = "local"
	 */
	void setQualified_money(java.math.BigDecimal qualified_money);

	/**
	 * @ejb.interface-method view-type = "local"
	 */
	java.math.BigDecimal getQualified_amount();

	/**
	 * @ejb.interface-method view-type = "local"
	 */
	void setQualified_amount(java.math.BigDecimal qualified_amount);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return Returns the max_buy_limit.
	 */
	java.math.BigDecimal getMax_buy_limit();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param max_buy_limit The max_buy_limit to set.
	 */
	void setMax_buy_limit(java.math.BigDecimal max_buy_limit);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return Returns the min_buy_limit.
	 */
	java.math.BigDecimal getMin_buy_limit();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param min_buy_limit The min_buy_limit to set.
	 */
	void setMin_buy_limit(java.math.BigDecimal min_buy_limit);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� trust_fee_period��
	 */
	Integer getTrust_fee_period();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param trust_fee_period Ҫ���õ� trust_fee_period��
	 */
	void setTrust_fee_period(Integer trust_fee_period);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return Returns the depart_name.
	 */
	String getDepart_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param depart_name The depart_name to set.
	 */
	void setDepart_name(String depart_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� asfund_flag��
	 */
	Integer getAsfund_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param asfund_flag Ҫ���õ� asfund_flag��
	 */
	void setAsfund_flag(Integer asfund_flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� qualified_flag��
	 */
	Integer getQualified_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param qualified_flag Ҫ���õ� qualified_flag��
	 */
	void setQualified_flag(Integer qualified_flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� qualified_num��
	 */
	Integer getQualified_num();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param qualified_num Ҫ���õ� qualified_num��
	 */
	void setQualified_num(Integer qualified_num);

}