package enfo.crm.intrust;

import java.math.BigDecimal;
import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.BenifitorVO;

public interface BenifitorLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"	
	 * SP_QUERY_TBENIFITOR @IN_BOOK_CODE INT,
						 @IN_SERIAL_NO INT,
						 @IN_PRODUCT_ID INT,
						 @IN_CONTRACT_BH VARCHAR(16),
						 @IN_CUST_ID INT,
						 @IN_INPUT_MAN INT = NULL,
						 @IN_CUST_NAME VARCHAR(60)=NULL,
						 @IN_PROV_LEVEL VARCHAR(10)=NULL,
						 @IN_CARD_ID VARCHAR(30)=NULL,
						 @IN_LIST_ID INT=NULL,
						 @IN_BEN_STATUS VARCHAR(10)=NULL		
	 * ��������������
	 * @param vo
	 * @return
	 * @throws BusiException
	 * 
	 */
	List load(BenifitorVO vo) throws BusiException;

	/**
	 * @IN_BOOK_CODE        INTEGER,                  --����
	                                     @IN_PRODUCT_ID       INTEGER,                  --��ƷID
	                                     @IN_CONTRACT_BH      NVARCHAR(16),             --�Ϲ���ͬ���
	                                     @IN_INPUT_MAN        INTEGER       = NULL,     --����Ա
	                                     @IN_SUB_PRODUCT_ID   INTEGER       = 0,        --�Ӳ�ƷID
	                                     @IN_CUST_NAME        NVARCHAR(120) = NULL,     --�ͻ�����
	                                     @IN_CARD_ID          NVARCHAR(60)  = NULL,     --֤����
	                                     @IN_CONTRACT_SUB_BH  NVARCHAR(80)  = '',       --ʵ�ʺ�ͬ���
	                                     @IN_IS_ZERO          INTEGER       = 0         --��������ݶ�Ϊ�� 1�� 0��
	 * @ejb.interface-method view-type = "local"	
	 * @param vo
	 * @return
	 * @throws BusiException
	 * 
	 */
	List getBenchange(BenifitorVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @return
	 * @throws BusiException
	 * 
	                                 @IN_EXPORT_FLAG      INT= 0,       --������ǣ�0�ǵ�����ѯ 1������������Ϣ
	                                 @IN_EXPORT_SUMMARY   NVARCHAR(900) = 0,  --������ע
	                                 @IN_SUB_PRODUCT_ID   INT=0,        --�Ӳ�ƷID
	                                 @IN_CXSY_FLAG        INT=0,         --�Ƿ�����ѹ��������ˣ�1����2������
	                                 @IN_GOV_PROV_REGIONAL NVARCHAR(60),
	                                 @IN_GOV_REGIONAL      NVARCHAR(60)
	 * ��CRM����������������
	 */
	List loadFromCRM(BenifitorVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @return
	 * @throws BusiException
	 * ��CRM�б����ϸ
	 */
	List loadBenacct(BenifitorVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * SP_QUERY_TBENIFITOR @IN_BOOK_CODE INT,
	                                 @IN_SERIAL_NO INT,
	                                 @IN_PRODUCT_ID INT,
	                                 @IN_CONTRACT_BH VARCHAR(16),
	                                 @IN_CUST_ID INT,
	                                 @IN_INPUT_MAN INT = NULL,
	                                 @IN_CUST_NAME VARCHAR(60)=NULL,
	                                 @IN_PROV_LEVEL VARCHAR(10)=NULL,
	                                 @IN_CARD_ID VARCHAR(30)=NULL,
	                                 @IN_LIST_ID INT=NULL,
	                                 @IN_BEN_STATUS VARCHAR(10)=NULL   --ADD BY SHENKL 2006/12/08
	   ����������                              
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List query(BenifitorVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ������������Ϣ
	 * SP_ADD_TBENIFITOR @IN_BOOK_CODE INT,
	                               @IN_PRODUCT_ID INT,
	                               @IN_CONTRACT_BH VARCHAR(16),
	                               @IN_CUST_ID INT,
	                               @IN_BEN_AMOUNT DECIMAL(16,3),
	                               @IN_PROV_LEVEL VARCHAR(10),
	                               @IN_JK_TYPE VARCHAR(10),
	                               @IN_BANK_ID VARCHAR(10),
	                               @IN_BANK_ACCT VARCHAR(30),
	                               @IN_INPUT_MAN INT,
	                               @IN_BANK_SUB_NAME VARCHAR(30) = '',
	                               @IN_VALID_PERIOD INT = NULL,
	                               @IN_BEN_DATE INT = NULL,
	                               @IN_ACCT_NAME VARCHAR(60) = NULL
	                               @IN_BANK_ACCT_TYPE	VARCHAR(10)	�����˻�����(9920)
	                               @IN_BONUS_FLAG INT = 1 1���Ҹ���2��ת�ݶ� 
	 * @param vo
	 * @throws BusiException
	 */
	void append(BenifitorVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �޸���������Ϣ
		 * SP_MODI_TBENIFITOR @IN_SERIAL_NO INT,
	                                @IN_BEN_AMOUNT DECIMAL(16,3),
	                                @IN_PROV_LEVEL VARCHAR(10),
	                                @IN_BANK_ID VARCHAR(10),
	                                @IN_BANK_ACCT VARCHAR(30),
	                                @IN_JK_TYPE VARCHAR(10),
	                                @IN_INPUT_MAN INT,
	                                @IN_BANK_SUB_NAME VARCHAR(30) = '',
	                                @IN_VALID_PERIOD INT = NULL,
	                                @IN_BEN_DATE INT = NULL,
	                                @IN_ACCT_NAME VARCHAR(60) = NULL
	                                @IN_BANK_ACCT_TYPE	VARCHAR(10)	�����˻�����(9920)
	                                @IN_BOUNS_FLAG INT = 1 1���Ҹ���2��ת�ݶ� 
	 * @param vo
	 * @throws BusiException
	 */
	void save(BenifitorVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �޸���������Ϣ
		 * SP_MODI_TBENIFITOR @IN_SERIAL_NO INT,
	                                @IN_BEN_AMOUNT DECIMAL(16,3),
	                                @IN_PROV_LEVEL VARCHAR(10),
	                                @IN_BANK_ID VARCHAR(10),
	                                @IN_BANK_ACCT VARCHAR(30),
	                                @IN_JK_TYPE VARCHAR(10),
	                                @IN_INPUT_MAN INT,
	                                @IN_BANK_SUB_NAME VARCHAR(30) = '',
	                                @IN_VALID_PERIOD INT = NULL,
	                                @IN_BEN_DATE INT = NULL,
	                                @IN_ACCT_NAME VARCHAR(60) = NULL
	                                @IN_BANK_ACCT_TYPE	VARCHAR(10)	�����˻�����(9920)
	                                @IN_BOUNS_FLAG INT = 1 1���Ҹ���2��ת�ݶ� 
	 * @param vo
	 * @throws BusiException
	 */
	void saveByCrm(BenifitorVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ɾ����������Ϣ
	 * SP_DEL_TBENIFITOR @IN_SERIAL_NO INT,
	                     @IN_INPUT_MAN INT
	 * @param vo
	 * @throws BusiException
	 */
	void delete(BenifitorVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 	��ѯ�������˻� �����˻��޸���
	 *  SP_QUERY_TBENIFITOR_MODIUNCHECK @IN_FUNCTION_ID INT,   --100�ʺ��޸�ʱ���÷�����������Ϣ��@IN_CONTRACT_BH��@IN_CUST_NO������һ��
	                                                                    --200���ʱ���ã������޸�δ��˼�¼��@IN_CONTRACT_BH��@IN_CUST_NO��Ч
	                                 @IN_BOOK_CODE INT,
	                                 @IN_SERIAL_NO INT,
	                                 @IN_CONTRACT_BH VARCHAR(16),
	                                 @IN_CUST_NO VARCHAR(8),
	                                 @IN_PRODUCT_ID INT = NULL,
	                                 @IN_PRODUCT_NAME VARCHAR(60) = NULL,
	                                 @IN_INPUT_MAN INT = NULL,
	                                 @IN_CUST_NAME VARCHAR(60) = NULL
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	IPageList queryModi(BenifitorVO vo, String[] totalColumn, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 	��ѯ�������˻� �����˻��޸���
	 *  SP_QUERY_TBENIFITOR_MODIUNCHECK @IN_FUNCTION_ID INT,   --100�ʺ��޸�ʱ���÷�����������Ϣ��@IN_CONTRACT_BH��@IN_CUST_NO������һ��
	                                                                    --200���ʱ���ã������޸�δ��˼�¼��@IN_CONTRACT_BH��@IN_CUST_NO��Ч
	                                 @IN_BOOK_CODE INT,
	                                 @IN_SERIAL_NO INT,
	                                 @IN_CONTRACT_BH VARCHAR(16),
	                                 @IN_CUST_NO VARCHAR(8),
	                                 @IN_PRODUCT_ID INT = NULL,
	                                 @IN_PRODUCT_NAME VARCHAR(60) = NULL,
	                                 @IN_INPUT_MAN INT = NULL,
	                                 @IN_CUST_NAME VARCHAR(60) = NULL
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List queryModi1(BenifitorVO vo) throws BusiException;

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
	IPageList queryModiAcctDetail(BenifitorVO vo, int page, int pagesize) throws Exception;

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
	List queryModiAcctDetail(BenifitorVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �����˻�ת��
	 * SP_MODI_TBENIFITOR_BANK @IN_SERIAL_NO INT,
	                                     @IN_BANK_ID VARCHAR(10),
	                                     @IN_BANK_SUB_NAME VARCHAR(30),
	                                     @IN_BANK_ACCT VARCHAR(30),
	                                     @IN_INPUT_MAN INT,
	                                     @IN_ACCT_NAME VARCHAR(60) = NULL,
	                                     @IN_MODI_DATE INT=NULL
	                                     @IN_BANK_ACCT_TYPE	VARCHAR(10)	�����˻�����(9920)
	                                     @IN_BONUS_FLAG     INT = 1 1���Ҹ���2��ת�ݶ� 
	 * @param vo
	 * @throws BusiException
	 */
	void save1(BenifitorVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ�ͻ�������Ϣ
	 * @author dingyj
	 * @since 2010-1-7
	 * @param vo
	 * @return list
	 * @throws BusiException
	 */
	List QueryBenifitor(BenifitorVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �����˷ݶ�����ˮ��ѯ
	 * @author dingyj
	 * @since 2010-1-7
	 * @param vo
	 * @return list
	 * @throws BusiException
	 */
	List listChangeDetail(BenifitorVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ÿͻ�������ϸ��Ϣ
	 * @author dingyj
	 * @since 2010-1-12
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List queryDetail(BenifitorVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	IPageList listLostDetail(BenifitorVO vo, String[] totalColumn, int pageIndex, int pageSize) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @throws Exception
	 */
	void modBenifitorLevel(BenifitorVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �޸��������ޡ���������
	 * @param vo
	 * @throws Exception
	 */
	void modBenifitorValidPeriod(BenifitorVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ�ͻ�������Ʒ������ݶ�
	 * @param cust_id
	 * @param prodct_id
	 * @return
	 * @throws Exception
	 */
	BigDecimal getTotalBenAmount(Integer cust_id, Integer product_id) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �������ʻ��޸����
	 * SP_CHECK_TBENIFITOR_ACCT @IN_SERIAL_NO INT,
	                            @IN_CHECK_FALG INT,
	                            @IN_INPUT_MAN INT
	 * @param vo
	 * @throws BusiException
	 */
	void check1(BenifitorVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @throws Exception
	 * ���Ҵ�ӡ��Ȩ֤����Ϣ
	 */
	IPageList query(BenifitorVO vo, String[] totalColumn, int pageIndex, int pageSize) throws Exception;

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

	List load1(BenifitorVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * �޸� ���漶�� PROV_LEVEL
	 * 
	 * @throws Exception
	 */
	void modi_prov_level(BenifitorVO vo) throws Exception;

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

	IPageList QueryBenifitorProv(BenifitorVO vo, int pageIndex, int pageSize) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_CHECK_TBENIFITOR_LEVEL
	 * @return
	 * @throws Exception
	 */
	void checkBenifitorProv(BenifitorVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_MODI_TBENIFITOR_BANK_SYFPFS
	 * @return
	 * @throws Exception
	 */
	void changeBenBatch(BenifitorVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_QUERY_TBENIFITOR_BYHT
	 * @IN_PRODUCT_ID INT,
	 * @IN_CONTRACT_BH VARCHAR(16)
	 */
	List listBenifitorbyht(BenifitorVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	List queryBenifitorbyhtPrint(BenifitorVO vo) throws Exception;

}