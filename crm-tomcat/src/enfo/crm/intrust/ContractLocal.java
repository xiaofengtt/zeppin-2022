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
	 * 分页查询合同信息
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
	 * 分页查询合同信息 移植到CRM的过程
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
	 * 查询功能
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List queryPurchanseContract(ContractVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询功能
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List queryPurchanseContract_crm(ContractVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 判断合同编号
	 * SP_REGEDIT_CONTRACT_BH @IN_BOOK_CODE        INT,
							@IN_CONTRACT_TYPE    INT,    --合同类型：1,认购合同，2 申购合同 3，普通贷款合同表 4，租赁合同表 5，投资合同
							@IN_PRODUCT_ID       INT,    --产品ID
							@IN_CONTRACT_SUB_BH  NVARCHAR(50),
							@OUT_EXISTS_FLAG     INT OUTPUT  --1 存在 0 不存在 可用
	 */
	Integer isExistSameContractBH(ContractVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 相同合同编号的产品信息
	 * SP_REGEDIT_CONTRACT_BH
							@IN_CONTRACT_TYPE    INT,    --合同类型：1,认购合同，2 申购合同
							@IN_PRODUCT_ID       INT,    --产品ID
							@IN_CONTRACT_SUB_BH  NVARCHAR(50),
	 */
	String getSameBHContractInfo(ContractVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 未预约添加认购信息
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
	                    @IN_ENTITY_PRICE    DECIMAL(16,3),--20050524谭鸿添加
	                    @IN_ENTITY_NAME     VARCHAR(50),--20050524谭鸿添加
	                    @IN_ENTITY_TYPE     VARCHAR(10),--20050524谭鸿添加
	                    @IN_CONTRACT_SUB_BH VARCHAR(30),--20050524谭鸿添加
	                    @IN_CITY_SERIAL_NO  INT,
	                    @OUT_SERIAL_NO      INT OUTPUT,
	                    @OUT_CONTRACT_BH    VARCHAR(16) OUTPUT,
	                    @IN_ZY_FLAG         INT = 1,
	                    @IN_TOUCH_TYPE      VARCHAR(40) = NULL,  --ADD BY SHENKL 2007/03/06
	                    @IN_TOUCH_TYPE_NAME VARCHAR(30) = NULL,  --ADD BY SHENKL 2007/03/06
	                    @IN_GAIN_ACCT       VARCHAR(60) = NULL   --ADD BY JINXR  2007/4/16  银行帐号户名
	                    @IN_FEE_JK_TYPE     INT =1　----费用缴款方式：1从本金扣，2另外交
	                    
	                    反洗钱添加
	                    @IN_BANK_ACCT_TYPE	VARCHAR(10)	银行账户类型(9920)                       
	             
	                    @IN_START_DATE      INT = NULL,
						@IN_END_DATE        INT = NULL
						@IN_BOUNS_FLAG INT = 1                  1、兑付　2、转份额 
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	Object[] appendNoPre(ContractVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询
	 * 
	 *@param cust_name
	 * @return
	 * @throws Exception
	 */
	IPageList queryMoenyImport(String cust_name, String[] totalColumn, int pageIndex, int pageSize) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 已预约添加认购信息
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
	                  @IN_GAIN_ACCT       VARCHAR(60) = NULL   --ADD BY JINXR  2007/4/16  银行帐号户名
	                  @IN_CONTRACT_SUB_BH NVARCHAR(50) = '' --  实际合同编号
	                                        
	                  反洗钱 填加
	                  @IN_BANK_ACCT_TYPE	VARCHAR(10)	银行账户类型(9920)
	                  @IN_BONUS_FLAG INT = 1 1、兑付　2、转份额 
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	Object[] append(ContractVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 修改认购信息
	 * @param vo
	 * @throws BusiException
	 */
	void save(ContractVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 修改认购信息
	 * @param vo
	 * @throws BusiException
	 */
	void save_CRM(ContractVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询单个认购合同信息
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List load(ContractVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 删除认购合同信息
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
	 * 查找合同信息
	 * @param vo
	 * @throws Exception
	 */
	List queryContract(ContractVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
		 * 客户认购合同查询
		 * @author dingyj 2009-12-11
		 * @param vo
		 * @return list
		 * @throws Exception
		 */
	List listAll(ContractVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	     * 客户认购合同查询
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
	 * 合同---查询（分页显示）
	 * 
	 * SP_QUERY_TCONTRACTSG	IN_BOOK_CODE	INT	帐套
							IN_SERIAL_NO	INT	序号
							IN_PRODUCT_ID	INT	产品ID
							IN_PRODUCT_CODE	VARCHAR(6)	产品编号
							IN_CUST_NAME	VARCHAR(100)	客户姓名
							IN_CONTRACT_BH	VARCHAR(16)	合同编号							
							IN_CHECK_FLAG	INT	审核标志1未审核2已审核
							IN_INPUT_MAN	INT	操作员
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
	 * 根据客户ID获得认购的合同信息
	 * @author dingyj
	 * @since 2010-1-6
	 * @param vo
	 * @throws Exception
	 */
	List queryContractByCustID(ContractVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 打印信封页面根据客户打印购买的信托产品的名称
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
	                        @IN_CHECK_FLAG INT,     -- 21 合同终止界面用查询
	                        @IN_INPUT_MAN INT
	                        @IN_CONTRACT_SUB_BH NVARCHAR(50) = ''     -- 合同编号
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
	 * 到账日期查询
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
	 * 查询
	 * 
	 * @param product_name
	 * @param contract_bh
	 * @param cust_name
	 * @throws Exception
	 */
	IPageList queryOldTemp(ContractVO vo, String[] totalColumn, int pageIndex, int pageSize) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询
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
	 * 新增
	 * @param vo
	    @IN_PREPRODUCT_ID           INTEGER,     -- 预发行产品
	    @IN_CUST_ID                 INTEGER,
	    @IN_CONTRACT_SUB_BH         NVARCHAR(80), -- 合同的实际编号                                        
	    @IN_PROV_FLAG               INTEGER         = 1,            --1.优先，2一般，3劣后
	    @IN_PROV_LEVEL              NVARCHAR(10)    = '120401',     --收益级别（1204）
	    @IN_QS_DATE                 INTEGER,
	    @IN_RG_MONEY                DECIMAL(16,3),                                        
	    @IN_JK_TYPE                 NVARCHAR(10),
	    @IN_JK_DATE                 INTEGER,                                        
	    @IN_BANK_ID                 NVARCHAR(10),  --受益银行(1103)
	    @IN_BANK_SUB_NAME           NVARCHAR(60), --收益银行支行
	    @IN_BANK_ACCT               NVARCHAR(30),
	    @IN_BANK_ACCT_TYPE          NVARCHAR(10)    = NULL,         --银行账户类型(9920)
	    @IN_GAIN_ACCT               NVARCHAR(60)    = NULL,         --银行帐号户名                                         
	    @IN_SERVICE_MAN             INTEGER, -- 客户经理
	    @IN_EXPECT_ROR_LOWER        DECIMAL(5,4)    = NULL,         --预期收益率区间
	    @IN_EXPECT_ROR_UPPER        DECIMAL(5,4)    = NULL,         --预期收益率区间
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
	    @IN_PREPRODUCT_ID           INTEGER,     -- 预发行产品
	    @IN_CUST_ID                 INTEGER,
	    @IN_CONTRACT_SUB_BH         NVARCHAR(80), -- 合同的实际编号                                        
	    @IN_PROV_FLAG               INTEGER         = 1,            --1.优先，2一般，3劣后
	    @IN_PROV_LEVEL              NVARCHAR(10)    = '120401',     --收益级别（1204）
	    @IN_QS_DATE                 INTEGER,
	    @IN_RG_MONEY                DECIMAL(16,3),                                        
	    @IN_JK_TYPE                 NVARCHAR(10),
	    @IN_JK_DATE                 INTEGER,                                        
	    @IN_BANK_ID                 NVARCHAR(10),  --受益银行(1103)
	    @IN_BANK_SUB_NAME           NVARCHAR(60), --收益银行支行
	    @IN_BANK_ACCT               NVARCHAR(30),
	    @IN_BANK_ACCT_TYPE          NVARCHAR(10)    = NULL,         --银行账户类型(9920)
	    @IN_GAIN_ACCT               NVARCHAR(60)    = NULL,         --银行帐号户名                                         
	    @IN_SERVICE_MAN             INTEGER, -- 客户经理
	    @IN_EXPECT_ROR_LOWER        DECIMAL(5,4)    = NULL,         --预期收益率区间
	    @IN_EXPECT_ROR_UPPER        DECIMAL(5,4)    = NULL,         --预期收益率区间
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
	    @IN_SERIAL_NO    INT,                    --序号
	    @IN_PREPRODUCT_ID   INT,                    --预发行产品ID
	    @IN_PREPRODUCT_NAME NVARCHAR(200),          --预发行产品名称
	    @IN_CUST_ID         INT ,                   --客户ID
	    @IN_CUST_NAME       NVARCHAR(100),          --客户名称
	    @IN_CONTRACT_SUB_BH NVARCHAR (30) ,         --合同实际编号
	    @IN_QS_DATE1        INTEGER ,               --签署日期(起)
	    @IN_QS_DATE2        INTEGER ,               --签署日期(止)
	    @IN_STATUS          INTEGER ,               --状态：1未转正式认购2已转认购
	    @IN_INPUT_MAN       INT                     --操作员
	 */
	IPageList queryContractUnreal(ContractUnrealVO vo, int pageNo, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 *
	 * @param vo
	 * @throws BusiException
	 * 
	    @IN_SERIAL_NO    INT,                    --序号
	    @IN_PREPRODUCT_ID   INT,                    --预发行产品ID
	    @IN_PREPRODUCT_NAME NVARCHAR(200),          --预发行产品名称
	    @IN_CUST_ID         INT ,                   --客户ID
	    @IN_CUST_NAME       NVARCHAR(100),          --客户名称
	    @IN_CONTRACT_SUB_BH NVARCHAR (30) ,         --合同实际编号
	    @IN_QS_DATE1        INTEGER ,               --签署日期(起)
	    @IN_QS_DATE2        INTEGER ,               --签署日期(止)
	    @IN_STATUS          INTEGER ,               --状态：1未转正式认购2已转认购
	    @IN_INPUT_MAN       INT                     --操作员
	 */
	Map loadContractUnreal(ContractUnrealVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 查询
	 * 
	 * @param product_name
	 * @param contract_bh
	 * @param cust_name
	 * @param dealFlag 处理标志。1表示代理销售，2代表代理销售更新
	 * @param page
	 * @param pagesize
	 * @throws Exception
	 */
	IPageList queryOldTemp(String product_name, String contract_bh, String cust_name, int dealFlag, int page,
			int pagesize) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 万向-认购合同\申购合同\赎回\账户变动\受益情况变动\受益转让\ 数据导出
	 * @author dingyj
	 * @since 2010-1-6
	 * @param vo
	 * @throws Exception
	 */
	List queryAllContractMessage(ContractVO vo, Integer flag) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 查询各经理销售情况
	 * 
	    @IN_PRODUCT_ID    INT,             --产品ID
	    @IN_DATE_START    INT,             --统计时间(起)
	    @IN_DATE_END      INT,             --统计时间(止)
	    @IN_SERVICEMAN_ID INT,             --客户经理ID
	    @IN_INPUT_MAN     INT              --操作员
	 * @throws BusiException
	 */
	List statManagerSales(Integer productId, Integer startDate, Integer endDate, Integer servMan, Integer inputMan)
			throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 客户认购查询
	 * 
	    @IN_CUST_ID   INT,       --客户ID
	    @IN_FLAG      INT = 1    --1正常合同 2全部合同 3已结束合同
	    @IN_PRODUCT_ID INT = 0,
	    @IN_SERVICE_MAN INT = 0
	 * @throws BusiException
	 */
	List statCustContract(Integer custId, Integer flag, Integer productId, Integer servMan) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 统计认购金额小于300万的个人客户合同数
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
	 * @IN_BOOK_CODE           INTEGER,                --账套
	 * @IN_CONTRACT_TYPE       INTEGER  = 0,           -- 1:认购合同；2:申购合同；3:全部
	 * @IN_SERIAL_NO           INTEGER  = 0,    
	 * @IN_PRODUCT_ID          INTEGER,                --产品ID
	 * @IN_PRODUCT_CODE        NVARCHAR(6),            --产品编号
	 * @IN_PRODUCT_NAME        NVARCHAR(60)  = '',   --产品名称
	 * @IN_CONTRACT_SUB_BH     NVARCHAR(50)  = '',     --合同编号查询  
	 * @IN_CUST_NAME           NVARCHAR(60)  = '',   --客户名称
	 * @IN_INPUT_MAN           INTEGER,                --操作员
	 */
	IPageList listContractFor2ndEdit(ContractVO vo, int page, int pagesize) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * @IN_BOOK_CODE           INTEGER,                --账套
	 * @IN_CONTRACT_TYPE       INTEGER  = 0,           -- 1:认购合同；2:申购合同；3:全部
	 * @IN_SERIAL_NO           INTEGER  = 0,    
	 * @IN_PRODUCT_ID          INTEGER,                --产品ID
	 * @IN_PRODUCT_CODE        NVARCHAR(6),            --产品编号
	 * @IN_PRODUCT_NAME        NVARCHAR(60)  = '',   --产品名称
	 * @IN_CONTRACT_SUB_BH     NVARCHAR(50)  = '',     --合同编号查询  
	 * @IN_CUST_NAME           NVARCHAR(60)  = '',   --客户名称
	 * @IN_INPUT_MAN           INTEGER,                --操作员
	 */
	List listContractFor2ndEdit(ContractVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 *  @IN_CONTRACT_TYPE INTEGER,           -- 1:认购合同；2:申购合同；3:全部
	 *  @IN_SERIAL_NO     INTEGER,    
	 *  @IN_PROV_FLAG     INT,                    -- 受益优先级
	 *  @IN_PROV_LEVEL    NVARCHAR(10),           -- 收益级别 
	 *  @IN_CHANNEL_COOPERTYPE  NVARCHAR(10),     --渠道合作方式(5502)  
	 *  @IN_WITH_BANK_FLAG INT = NULL,
	 *  @IN_HT_BANK_ID NVARCHAR(10) = NULL, 
	 *  @IN_HT_BANK_SUB_NAME NVARCHAR(60) = '',
	 *  @IN_WITH_SECURITY_FLAG INT = NULL,
	 *  @IN_WITH_PRIVATE_FALG INT = NULL,  
	 *  @IN_JG_WTRLX2     NVARCHAR(2),            --机构类型分类2：1金融性公司 2政府 3非金融性公司 4境外金融性公司   
	 *  @IN_INPUT_MAN     INT
	 */
	void modiContractFor2ndEdit(ContractVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 代理销售菜单批量修改
	 */
	void updateOld(String sql) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询单个认购合同事后修改信息
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
	 * 导出代理销售更新导入用的模板
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List listContractForPurchaseImportChange(ContractVO vo) throws BusiException;

}