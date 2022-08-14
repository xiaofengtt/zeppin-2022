package enfo.crm.intrust;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.ContractUnrealVO;
import enfo.crm.vo.ContractVO;

public interface ContractLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ��ҳ��ѯ��ͬ��Ϣ
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList queryPurchanseContract(ContractVO vo, String[] totalColumn, int pageIndex, int pageSize)
			throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ��ҳ��ѯ��ͬ��Ϣ ��ֲ��CRM�Ĺ���
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList queryPurchanseContract_crm(ContractVO vo, String[] totalColumn, int pageIndex, int pageSize)
			throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ����
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List queryPurchanseContract(ContractVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ����
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List queryPurchanseContract_crm(ContractVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �жϺ�ͬ���
	 * SP_REGEDIT_CONTRACT_BH @IN_BOOK_CODE        INT,
							@IN_CONTRACT_TYPE    INT,    --��ͬ���ͣ�1,�Ϲ���ͬ��2 �깺��ͬ 3����ͨ�����ͬ�� 4�����޺�ͬ�� 5��Ͷ�ʺ�ͬ
							@IN_PRODUCT_ID       INT,    --��ƷID
							@IN_CONTRACT_SUB_BH  NVARCHAR(50),
							@OUT_EXISTS_FLAG     INT OUTPUT  --1 ���� 0 ������ ����
	 */
	Integer isExistSameContractBH(ContractVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ͬ��ͬ��ŵĲ�Ʒ��Ϣ
	 * SP_REGEDIT_CONTRACT_BH
							@IN_CONTRACT_TYPE    INT,    --��ͬ���ͣ�1,�Ϲ���ͬ��2 �깺��ͬ
							@IN_PRODUCT_ID       INT,    --��ƷID
							@IN_CONTRACT_SUB_BH  NVARCHAR(50),
	 */
	String getSameBHContractInfo(ContractVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * δԤԼ����Ϲ���Ϣ
	 *  SP_ADD_TCONTRACT_NOPRE @IN_BOOK_CODE       INT,
	                    @IN_CUST_ID         INT,
	                    @IN_LINK_MAN        INT,
	                    @IN_PRODUCT_ID      INT,
	                    @IN_RG_MONEY        DECIMAL(16,3),
	                    @IN_JK_TYPE         VARCHAR(10),
	                    @IN_BANK_ID         VARCHAR(10),
	                    @IN_BANK_ACCT       VARCHAR(30),
	                    @IN_VALID_PERIOD    INT,
	                    @IN_SERVICE_MAN     INT,
	                    @IN_SUMMARY2        VARCHAR(200),
	                    @IN_INPUT_MAN       INT,
	                    @IN_CONTRACT_BH     VARCHAR(16),
	                    @IN_QS_DATE         INT,
	                    @IN_JK_DATE         INT,
	                    @IN_BANK_SUB_NAME   VARCHAR(30),
	                    @IN_ENTITY_PRICE    DECIMAL(16,3),--20050524̷�����
	                    @IN_ENTITY_NAME     VARCHAR(50),--20050524̷�����
	                    @IN_ENTITY_TYPE     VARCHAR(10),--20050524̷�����
	                    @IN_CONTRACT_SUB_BH VARCHAR(30),--20050524̷�����
	                    @IN_CITY_SERIAL_NO  INT,
	                    @OUT_SERIAL_NO      INT OUTPUT,
	                    @OUT_CONTRACT_BH    VARCHAR(16) OUTPUT,
	                    @IN_ZY_FLAG         INT = 1,
	                    @IN_TOUCH_TYPE      VARCHAR(40) = NULL,  --ADD BY SHENKL 2007/03/06
	                    @IN_TOUCH_TYPE_NAME VARCHAR(30) = NULL,  --ADD BY SHENKL 2007/03/06
	                    @IN_GAIN_ACCT       VARCHAR(60) = NULL   --ADD BY JINXR  2007/4/16  �����ʺŻ���
	                    @IN_FEE_JK_TYPE     INT =1��----���ýɿʽ��1�ӱ���ۣ�2���⽻
	                    
	                    ��ϴǮ���
	                    @IN_BANK_ACCT_TYPE	VARCHAR(10)	�����˻�����(9920)                       
	             
	                    @IN_START_DATE      INT = NULL,
						@IN_END_DATE        INT = NULL
						@IN_BOUNS_FLAG INT = 1                  1���Ҹ���2��ת�ݶ� 
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	Object[] appendNoPre(ContractVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ
	 * 
	 *@param cust_name
	 * @return
	 * @throws Exception
	 */
	IPageList queryMoenyImport(String cust_name, String[] totalColumn, int pageIndex, int pageSize) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ԤԼ����Ϲ���Ϣ
		 * SP_ADD_TCONTRACT @IN_PRODUCT_ID      INT,
	                  @IN_PRE_CODE        VARCHAR(16),
	                  @IN_RG_MONEY        DECIMAL(16,3),
	                  @IN_JK_TYPE         VARCHAR(10),
	                  @IN_BANK_ID         VARCHAR(10),
	                  @IN_BANK_ACCT       VARCHAR(30),
	                  @IN_VALID_PERIOD    INT,
	                  @IN_SERVICE_MAN     INT,
	                  @IN_SUMMARY         VARCHAR(200),
	                  @IN_INPUT_MAN       INT,
	                  @IN_CHECK_MAN       INT,
	                  @IN_CONTRACT_BH     VARCHAR(16),
	                  @IN_QS_DATE         INT,
	                  @IN_JK_DATE         INT,
	                  @IN_BANK_SUB_NAME   VARCHAR(30),
	                  @IN_CITY_SERIAL_NO  INT,
	                  @OUT_SERIAL_NO      INT OUTPUT,
	                  @OUT_CONTRACT_BH    VARCHAR(16) OUTPUT,
	                  @IN_ZY_FLAG         INT = 1,
	                  @IN_TOUCH_TYPE      VARCHAR(40) = NULL,  --ADD BY SHENKL 2007/03/06
	                  @IN_TOUCH_TYPE_NAME VARCHAR(30) = NULL,  --ADD BY SHENKL 2007/03/06
	                  @IN_GAIN_ACCT       VARCHAR(60) = NULL   --ADD BY JINXR  2007/4/16  �����ʺŻ���
	                  @IN_CONTRACT_SUB_BH NVARCHAR(50) = '' --  ʵ�ʺ�ͬ���
	                                        
	                  ��ϴǮ ���
	                  @IN_BANK_ACCT_TYPE	VARCHAR(10)	�����˻�����(9920)
	                  @IN_BONUS_FLAG INT = 1 1���Ҹ���2��ת�ݶ� 
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	Object[] append(ContractVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �޸��Ϲ���Ϣ
	 * @param vo
	 * @throws BusiException
	 */
	void save(ContractVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �޸��Ϲ���Ϣ
	 * @param vo
	 * @throws BusiException
	 */
	void save_CRM(ContractVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ�����Ϲ���ͬ��Ϣ
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List load(ContractVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ɾ���Ϲ���ͬ��Ϣ
	 * @param vo
	 * @throws BusiException
	 */
	void delete(ContractVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	BigDecimal getPrecontract_premoney(ContractVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ���Һ�ͬ��Ϣ
	 * @param vo
	 * @throws Exception
	 */
	List queryContract(ContractVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
		 * �ͻ��Ϲ���ͬ��ѯ
		 * @author dingyj 2009-12-11
		 * @param vo
		 * @return list
		 * @throws Exception
		 */
	List listAll(ContractVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	     * �ͻ��Ϲ���ͬ��ѯ
	     * @param vo
	     * @param page
	     * @param pagesize
	     * 
	     * @return IPageList
	     * @throws Exception
	     */
	IPageList listAll(ContractVO vo, int page, int pagesize) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ͬ---��ѯ����ҳ��ʾ��
	 * 
	 * SP_QUERY_TCONTRACTSG	IN_BOOK_CODE	INT	����
							IN_SERIAL_NO	INT	���
							IN_PRODUCT_ID	INT	��ƷID
							IN_PRODUCT_CODE	VARCHAR(6)	��Ʒ���
							IN_CUST_NAME	VARCHAR(100)	�ͻ�����
							IN_CONTRACT_BH	VARCHAR(16)	��ͬ���							
							IN_CHECK_FLAG	INT	��˱�־1δ���2�����
							IN_INPUT_MAN	INT	����Ա
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList listAll(ContractVO vo, String[] totalColumn, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_QUERY_TCONTRACTLIST
	 * @IN_CONTRACT_ID INT
	 */
	List listHistory(Integer serial_no) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ���ݿͻ�ID����Ϲ��ĺ�ͬ��Ϣ
	 * @author dingyj
	 * @since 2010-1-6
	 * @param vo
	 * @throws Exception
	 */
	List queryContractByCustID(ContractVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ӡ�ŷ�ҳ����ݿͻ���ӡ��������в�Ʒ������
	 * @author dingyj
	 * @since 2010-1-6
	 * @param cust_id
	 * @throws Exception
	 */
	String getProductNameByCustId(Integer cust_id) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * SP_QUERY_TCONTRACT_CRM 	@IN_BOOK_CODE INT,
	                        @IN_PRODUCT_ID INT,
	                        @IN_PRODUCT_CODE VARCHAR(6),
	                        @IN_CONTRACT_BH VARCHAR(16),
	                        @IN_CHECK_FLAG INT,     -- 21 ��ͬ��ֹ�����ò�ѯ
	                        @IN_INPUT_MAN INT
	                        @IN_CONTRACT_SUB_BH NVARCHAR(50) = ''     -- ��ͬ���
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	IPageList getCheckContract(ContractVO vo, String[] totalColumn, int pageIndex, int pageSize) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @throws Exception
	 */
	void contractCheck(ContractVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @throws Exception
	 */
	void contractCheck_aftercheck(ContractVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	IPageList getRecheckContract(ContractVO vo, String[] totalColumn, int pageIndex, int pageSize) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @throws Exception
	 */
	void recheckContract(ContractVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �������ڲ�ѯ
	 * @param cust_id
	 * @param product_id
	 * @param contract_bh
	 * @return
	 * @throws BusiException 
	 * @throws Exception
	 */
	Integer getToBankDate(Integer cust_id, Integer product_id, String contract_bh) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ
	 * 
	 * @param product_name
	 * @param contract_bh
	 * @param cust_name
	 * @throws Exception
	 */
	IPageList queryOldTemp(ContractVO vo, String[] totalColumn, int pageIndex, int pageSize) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ
	 * 
	 * @param product_name
	 * @param contract_bh
	 * @param cust_name
	 * @throws Exception
	 */
	IPageList queryOldBenifitor(ContractVO vo, String[] totalColumn, int pageIndex, int pageSize) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @throws Exception
	 */
	IPageList queryZhejinTemp(ContractVO vo, String[] totalColumn, int pageIndex, int pageSize) throws Exception;

	//hesl 2011.4.29
	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_CHECK_TCONTRACT_ENTIRY
	 * @IN_SERIAL_NO INT,
	 * @IN_INPUT_MAN INT
	 */
	void checkEntity(ContractVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ����
	 * @param vo
	    @IN_PREPRODUCT_ID           INTEGER,     -- Ԥ���в�Ʒ
	    @IN_CUST_ID                 INTEGER,
	    @IN_CONTRACT_SUB_BH         NVARCHAR(80), -- ��ͬ��ʵ�ʱ��                                        
	    @IN_PROV_FLAG               INTEGER         = 1,            --1.���ȣ�2һ�㣬3�Ӻ�
	    @IN_PROV_LEVEL              NVARCHAR(10)    = '120401',     --���漶��1204��
	    @IN_QS_DATE                 INTEGER,
	    @IN_RG_MONEY                DECIMAL(16,3),                                        
	    @IN_JK_TYPE                 NVARCHAR(10),
	    @IN_JK_DATE                 INTEGER,                                        
	    @IN_BANK_ID                 NVARCHAR(10),  --��������(1103)
	    @IN_BANK_SUB_NAME           NVARCHAR(60), --��������֧��
	    @IN_BANK_ACCT               NVARCHAR(30),
	    @IN_BANK_ACCT_TYPE          NVARCHAR(10)    = NULL,         --�����˻�����(9920)
	    @IN_GAIN_ACCT               NVARCHAR(60)    = NULL,         --�����ʺŻ���                                         
	    @IN_SERVICE_MAN             INTEGER, -- �ͻ�����
	    @IN_EXPECT_ROR_LOWER        DECIMAL(5,4)    = NULL,         --Ԥ������������
	    @IN_EXPECT_ROR_UPPER        DECIMAL(5,4)    = NULL,         --Ԥ������������
	    @IN_INPUT_MAN               INTEGER,
	    @IN_SUMMARY                NVARCHAR(200),                                        
	    @OUT_SERIAL_NO              INTEGER         OUTPUT     
	 */
	void appendContractUnreal(ContractUnrealVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * @param vo
	    @IN_SERIAL_NO               INTEGER           
	    @IN_PREPRODUCT_ID           INTEGER,     -- Ԥ���в�Ʒ
	    @IN_CUST_ID                 INTEGER,
	    @IN_CONTRACT_SUB_BH         NVARCHAR(80), -- ��ͬ��ʵ�ʱ��                                        
	    @IN_PROV_FLAG               INTEGER         = 1,            --1.���ȣ�2һ�㣬3�Ӻ�
	    @IN_PROV_LEVEL              NVARCHAR(10)    = '120401',     --���漶��1204��
	    @IN_QS_DATE                 INTEGER,
	    @IN_RG_MONEY                DECIMAL(16,3),                                        
	    @IN_JK_TYPE                 NVARCHAR(10),
	    @IN_JK_DATE                 INTEGER,                                        
	    @IN_BANK_ID                 NVARCHAR(10),  --��������(1103)
	    @IN_BANK_SUB_NAME           NVARCHAR(60), --��������֧��
	    @IN_BANK_ACCT               NVARCHAR(30),
	    @IN_BANK_ACCT_TYPE          NVARCHAR(10)    = NULL,         --�����˻�����(9920)
	    @IN_GAIN_ACCT               NVARCHAR(60)    = NULL,         --�����ʺŻ���                                         
	    @IN_SERVICE_MAN             INTEGER, -- �ͻ�����
	    @IN_EXPECT_ROR_LOWER        DECIMAL(5,4)    = NULL,         --Ԥ������������
	    @IN_EXPECT_ROR_UPPER        DECIMAL(5,4)    = NULL,         --Ԥ������������
	    @IN_INPUT_MAN               INTEGER,
	    @IN_SUMMARY                NVARCHAR(200)                               
	 */
	void updateContractUnreal(ContractUnrealVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 *
	 * @param vo
	 * @throws BusiException
	 */
	void deleteContractUnreal(ContractUnrealVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 *
	 * @param vo
	 * @throws BusiException
	 */
	void convertContractUnreal(ContractUnrealVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 *
	 * @param vo
	 * @throws BusiException
	 * 
	    @IN_SERIAL_NO    INT,                    --���
	    @IN_PREPRODUCT_ID   INT,                    --Ԥ���в�ƷID
	    @IN_PREPRODUCT_NAME NVARCHAR(200),          --Ԥ���в�Ʒ����
	    @IN_CUST_ID         INT ,                   --�ͻ�ID
	    @IN_CUST_NAME       NVARCHAR(100),          --�ͻ�����
	    @IN_CONTRACT_SUB_BH NVARCHAR (30) ,         --��ͬʵ�ʱ��
	    @IN_QS_DATE1        INTEGER ,               --ǩ������(��)
	    @IN_QS_DATE2        INTEGER ,               --ǩ������(ֹ)
	    @IN_STATUS          INTEGER ,               --״̬��1δת��ʽ�Ϲ�2��ת�Ϲ�
	    @IN_INPUT_MAN       INT                     --����Ա
	 */
	IPageList queryContractUnreal(ContractUnrealVO vo, int pageNo, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 *
	 * @param vo
	 * @throws BusiException
	 * 
	    @IN_SERIAL_NO    INT,                    --���
	    @IN_PREPRODUCT_ID   INT,                    --Ԥ���в�ƷID
	    @IN_PREPRODUCT_NAME NVARCHAR(200),          --Ԥ���в�Ʒ����
	    @IN_CUST_ID         INT ,                   --�ͻ�ID
	    @IN_CUST_NAME       NVARCHAR(100),          --�ͻ�����
	    @IN_CONTRACT_SUB_BH NVARCHAR (30) ,         --��ͬʵ�ʱ��
	    @IN_QS_DATE1        INTEGER ,               --ǩ������(��)
	    @IN_QS_DATE2        INTEGER ,               --ǩ������(ֹ)
	    @IN_STATUS          INTEGER ,               --״̬��1δת��ʽ�Ϲ�2��ת�Ϲ�
	    @IN_INPUT_MAN       INT                     --����Ա
	 */
	Map loadContractUnreal(ContractUnrealVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ��ѯ
	 * 
	 * @param product_name
	 * @param contract_bh
	 * @param cust_name
	 * @param dealFlag �����־��1��ʾ�������ۣ�2����������۸���
	 * @param page
	 * @param pagesize
	 * @throws Exception
	 */
	IPageList queryOldTemp(String product_name, String contract_bh, String cust_name, int dealFlag, int page,
			int pagesize) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ����-�Ϲ���ͬ\�깺��ͬ\���\�˻��䶯\��������䶯\����ת��\ ���ݵ���
	 * @author dingyj
	 * @since 2010-1-6
	 * @param vo
	 * @throws Exception
	 */
	List queryAllContractMessage(ContractVO vo, Integer flag) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ��ѯ�������������
	 * 
	    @IN_PRODUCT_ID    INT,             --��ƷID
	    @IN_DATE_START    INT,             --ͳ��ʱ��(��)
	    @IN_DATE_END      INT,             --ͳ��ʱ��(ֹ)
	    @IN_SERVICEMAN_ID INT,             --�ͻ�����ID
	    @IN_INPUT_MAN     INT              --����Ա
	 * @throws BusiException
	 */
	List statManagerSales(Integer productId, Integer startDate, Integer endDate, Integer servMan, Integer inputMan)
			throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * �ͻ��Ϲ���ѯ
	 * 
	    @IN_CUST_ID   INT,       --�ͻ�ID
	    @IN_FLAG      INT = 1    --1������ͬ 2ȫ����ͬ 3�ѽ�����ͬ
	    @IN_PRODUCT_ID INT = 0,
	    @IN_SERVICE_MAN INT = 0
	 * @throws BusiException
	 */
	List statCustContract(Integer custId, Integer flag, Integer productId, Integer servMan) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * ͳ���Ϲ����С��300��ĸ��˿ͻ���ͬ��
	 * @param productId
	 * @return
	 * @throws BusiException
	 */
	List statLess300Contract(Integer productId) throws BusiException;

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
	IPageList queryEndContract(ContractVO vo, int page, int pagesize) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_QUERY_TCONTRACTLIST_DETAIL
	 * @IN_PRODUCT_ID INT,
	 * @IN_CONTRACT_BH VARCHAR(16),
	 * @IN_HT_STATUS VARCHAR(10),
	 * @IN_LIST_ID INT = 0
	 */
	IPageList listContractList(ContractVO vo, int page, int pagesize) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_QUERY_TCONTRACTLIST_DETAIL
	 * @IN_PRODUCT_ID INT,
	 * @IN_CONTRACT_BH VARCHAR(16),
	 * @IN_HT_STATUS VARCHAR(10),
	 * @IN_LIST_ID INT = 0
	 */
	List listContractList(ContractVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * @IN_BOOK_CODE           INTEGER,                --����
	 * @IN_CONTRACT_TYPE       INTEGER  = 0,           -- 1:�Ϲ���ͬ��2:�깺��ͬ��3:ȫ��
	 * @IN_SERIAL_NO           INTEGER  = 0,    
	 * @IN_PRODUCT_ID          INTEGER,                --��ƷID
	 * @IN_PRODUCT_CODE        NVARCHAR(6),            --��Ʒ���
	 * @IN_PRODUCT_NAME        NVARCHAR(60)  = '',   --��Ʒ����
	 * @IN_CONTRACT_SUB_BH     NVARCHAR(50)  = '',     --��ͬ��Ų�ѯ  
	 * @IN_CUST_NAME           NVARCHAR(60)  = '',   --�ͻ�����
	 * @IN_INPUT_MAN           INTEGER,                --����Ա
	 */
	IPageList listContractFor2ndEdit(ContractVO vo, int page, int pagesize) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * @IN_BOOK_CODE           INTEGER,                --����
	 * @IN_CONTRACT_TYPE       INTEGER  = 0,           -- 1:�Ϲ���ͬ��2:�깺��ͬ��3:ȫ��
	 * @IN_SERIAL_NO           INTEGER  = 0,    
	 * @IN_PRODUCT_ID          INTEGER,                --��ƷID
	 * @IN_PRODUCT_CODE        NVARCHAR(6),            --��Ʒ���
	 * @IN_PRODUCT_NAME        NVARCHAR(60)  = '',   --��Ʒ����
	 * @IN_CONTRACT_SUB_BH     NVARCHAR(50)  = '',     --��ͬ��Ų�ѯ  
	 * @IN_CUST_NAME           NVARCHAR(60)  = '',   --�ͻ�����
	 * @IN_INPUT_MAN           INTEGER,                --����Ա
	 */
	List listContractFor2ndEdit(ContractVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 *  @IN_CONTRACT_TYPE INTEGER,           -- 1:�Ϲ���ͬ��2:�깺��ͬ��3:ȫ��
	 *  @IN_SERIAL_NO     INTEGER,    
	 *  @IN_PROV_FLAG     INT,                    -- �������ȼ�
	 *  @IN_PROV_LEVEL    NVARCHAR(10),           -- ���漶�� 
	 *  @IN_CHANNEL_COOPERTYPE  NVARCHAR(10),     --����������ʽ(5502)  
	 *  @IN_WITH_BANK_FLAG INT = NULL,
	 *  @IN_HT_BANK_ID NVARCHAR(10) = NULL, 
	 *  @IN_HT_BANK_SUB_NAME NVARCHAR(60) = '',
	 *  @IN_WITH_SECURITY_FLAG INT = NULL,
	 *  @IN_WITH_PRIVATE_FALG INT = NULL,  
	 *  @IN_JG_WTRLX2     NVARCHAR(2),            --�������ͷ���2��1�����Թ�˾ 2���� 3�ǽ����Թ�˾ 4��������Թ�˾   
	 *  @IN_INPUT_MAN     INT
	 */
	void modiContractFor2ndEdit(ContractVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * �������۲˵������޸�
	 */
	void updateOld(String sql) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ�����Ϲ���ͬ�º��޸���Ϣ
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List loadContractModi(ContractVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	void checkContractModi(ContractVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 */
	void modiContractModi(ContractVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �����������۸��µ����õ�ģ��
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List listContractForPurchaseImportChange(ContractVO vo) throws BusiException;

}