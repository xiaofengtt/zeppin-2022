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
	 * add by tangshg 2009-11-28 子产品信息查询 SP_QUERY_TSUBPRODUCT
	 * @IN_PRODUCT_ID INT,
	 * @IN_SUB_PRODUCT_ID INT = 0,
	 * @IN_LIST_ID INT = 0
	 * @IN_LIST_NAME NVARCHAR(60) ='', --子产品名称
	 * @IN_PRODUCT_CODE VARCHAR(6) ='', --产品编号
	 * @IN_PRODUCT_NAME NVARCHAR(60) ='' --产品名称
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
	 * add by tangshg 2009-11-28 新增子产品 SP_ADD_TSUBPRODUCT
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
	 * @IN_RESULT_STANDARD DECIMAL(9,8) =0, --业绩基准
	 * @IN_START_DATE INTEGER =0 -- 开始日期
	 * 
	 *  
	 */
	void addSubProduct() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * add by tangshg 2009-11-28 修改子产品 SP_MODI_TSUBPRODUCT
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
	 * add by tangshg 2009-11-28 删除字产品信息 SP_DEL_TSUBPRODUCT
	 * 
	 * @IN_SUB_PRODUCT_ID INT,
	 * @IN_INPUT_MAN INT
	 *  
	 */
	void delSubProduct() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 新建自营帐套操作
	 * 
	 * @throws Exception
	 */
	void appendSelf() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 更新自营帐套操作
	 */
	void updateSelf() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 根据信息读取自营帐套信息
	 * 
	 * @throws Exception
	 */
	void loadSelf() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 查询自营帐套 2008.05.06
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
	 * 自营帐套结果集
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
	 * @IN_CHECK_MAN INT =0 --指定产品信息审核人
	 * 
	 * @IN_END_DATE INT = NULL, --20090728 DONGYG 结束日期可以从界面输入，有输入以输入为准
	 * @IN_TG_ACCT_NAME NVARCHAR(100) = NULL --托管银行帐户名称 20090728 DONGYG ADD
	 * 
	 * ADD BY tangshg 2009-11-28
	 * @IN_SUB_FLAG BIT = 0, -- 子产品标志: 1 有 0 没有 ADD BY JINXR 2009/11/23
	 * @IN_NAV_FLOAT_NUM TINYINT = 10 -- 净值精度
	 * @IN_TRADE_TAX_RATE DECIMAL(5,4) =0 --营业税率 ADD BY LUOHH
	 * @IN_TRUST_CONTRACT_NAME NVARCHAR(100) -- 合同名称
	 * 
	 * @IN_SHARE_FLAG INT  --份额结算方式：１、净值结算 2、资金结算
	 * @IN_TRUST_FEE_PERIOD --信托报酬提取周期
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
	 * @IN_END_DATE INT = NULL 有输入时以输入为准，无输入时按原方法处理（起始日期+期限）
	 * @IN_TG_ACCT_NAME NVARCHAR(100) = NULL ADD BY tangshg 2009-11-28
	 * @IN_SUB_FLAG BIT = 0, -- 子产品标志: 1 有 0 没有 ADD BY JINXR 2009/11/23
	 * @IN_NAV_FLOAT_NUM TINYINT = 10 -- 净值精度
	 * @IN_TRADE_TAX_RATE DECIMAL(5,4) =0 --营业税率 ADD BY LUOHH
	 * @IN_TRUST_FEE_PERIOD INT = 0 -- 信托报酬提取周期
	 */
	void save() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_MODI_TPRODUCT1
	 * add by liug 20101213 产品信息修改需要审核流程时
	 */
	void save1() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 删除产品信息 SP_DEL_TPRODUCT
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
	 * @author administrator 功能：列表显示未审核产品 Bean implementation class for
	 *         Enterprise Bean: Product SP_QUERY_TPRODUCT_UNCHECK
	 * @IN_BOOK_CODE INT,
	 * @IN_PRODUCT_CODE VARCHAR(6),
	 * @IN_PRODUCT_NAME VARCHAR(60),
	 * @IN_INPUT_MAN INT
	 */
	void uncheckProduct() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询产品信息修改记录未审核的产品
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
	 * 产品不成立处理 SP_CANCEL_TPRODUCT
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
	 * 审核产品信息修改记录
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
	 * 产品查询，自定义列显示
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
	 * @IN_DEPART_ID INT =0, --设计部门 add by luohh 20090617
	 * @IN_OPEN_FLAG INT =0 --开放式/封闭式标志：1开放式2封闭式 0 全部 add by luohh 20091026
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
	 * @IN_FLAG INT = 1 -- 1：成立查询用 2：不成立查询用 3查询成立财务审核恢复
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
	 * @IN_FLAG INT = 1 -- 1 产品发行成立 2 产品发行不成立,11成立审核不通过
	 */
	void checkPublishProduct() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_UNCHECK_START_TPRODUCT
	 * @IN_PRODUCT_ID INT,
	 * @IN_INPUT_MAN INT,
	 * @IN_FLAG INT = 1 -- 产品发行成立财务审核恢复
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
	 * 查询可以做特殊成立恢复的产品 add by liug 20100818
	 */
	void query_toreverse(Integer book_code1, Integer input_man1) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * SP_START_TPRODUCT_SPECIAL_REVERSE
	 * 
	 * 产品特殊成立恢复存储过程 add by liug 20100818
	 */
	void start_reverse() throws BusiException;

	/**
	 * add by jinxr 2007/4/26 产品访问权限设置查询用
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
	 * add by jinxr 2007/4/26 产品访问权限设置查询用
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
	 *              @IN_ADMIN_MANAGER   INT = 0    -- 信托经理
	 *              
	 *              2009-07-31 ADD
	 *              @IN_INTRUST_FLAG3    INT = NULL, --设立方式：1私募2公募
	 *              @IN_INTRUST_FLAG4    INT = NULL, --信托目的：1私益2公益
	 *              @IN_EXP_RATE         DECIMAL(5,4) = NULL, --预期收益率，返回该值在收益率区间的									    
	 *              @IN_FACT_MONEY       DECIMAL(16,3) = NULL,--产品发行金额，返回大于等于该值的
	 *              --20090921 @IN_FACT_MONEY 删除 与 产品规模 重复									    
	 *              @IN_CHECK_FLAG       INT, --是否审核
	 *              @IN_VALID_PERIOD1     INT, --产品期限，输入以天为单位，0为无期限，负数为所有
	 *              @IN_VALID_PERIOD2     INT, --产品期限，输入以天为单位，0为无期限，负数为所有
	 *              @IN_FPFS             NVARCHAR(10), --受托人报酬提取方式1105
	 *              @IN_OPEN_FLAG        INT  --发行方式 1开放式2封闭式
	 *              
	 *   			@IN_MANAGERTYPE      INT           = 0,  --1主动管理 2被动管理 20100308 DONGYG ADD
	 *              @IN_COPERATE_TYPE    INT           = 0   --合作方式：1:银信合作;2:证信合作;3:私募基金合作;4:信政合作       
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
	 * 修改SP_END_TPRODUCT，增加输入参数@IN_END_DATE，输入NULL或0时，该字段设置为产品的END_DATE；
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
	 * 北国投20050111
	 * 
	 * @param project_flag
	 * @return
	 * @throws Exception
	 */
	void queryProvLevel() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 判断是否有事务
	 * 
	 * SP_IFEXISTS_PROJECTS
	 * @IN_PROJECT_FLAG INT --事务标志：1 项目审批，2 产品展期审批 ，3 产品级别变更审批
	 * 
	 * @OUT_ISEXISTS_FLAG INT OUTPUT --是否存在标志：1 是，0 否 当@OUT_ISEXISTS_FLAG =1 时
	 *                    显示显示‘提交审批’按钮，否则不显示
	 *  
	 */

	Integer ifExistProject(Integer project_flag, Integer book_code) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_IFEXISTS_TPROBLEMS
	 * @IN_PRODUCT_ID INT, --产品ID
	 * @IN_PROJECT_FLAG INT, --事务标志：1 产品展期审批 ，2 产品级别变更审批
	 * @OUT_ISEXISTS_FLAG INT OUTPUT --是否有提交按钮标志：1 是，0 否 --是否存在标志：1 是，0 否
	 *                    当@OUT_ISEXISTS_FLAG =1 时 显示显示按钮，否则不显示
	 *  
	 */

	Integer ifExistProBlem(Integer project_flag) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_ADD_TPRODUCTPERIOD_TPROBLEMS
	 * @IN_PRODUCT_ID INT, --项目ID
	 * @IN_INPUT_MAN INT --添加产品展期的提交审批
	 */
	void appendUpdateperiodSP() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_ADD_TPRODUCTQUALITY_TPROBLEMS
	 * @IN_PRODUCT_ID INT, --产品ID
	 * @IN_INPUT_MAN INT --添加产品质量级别调整的提交审批
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
	 * add by lk 2010-2-2 损益平准金列表查询
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
	 * 设置核算会计时的查询
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
	 * @IN_TASK_TYPE INT =102 --类别标志：102 收益分配日期 103 信息披露日期
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
	 * 更改所生成类型注释的模板为 窗口 > 首选项 > Java > 代码生成 > 代码和注释 SP_QUERY_PRODUCT_PURPOSE
	 * @IN_PRODUCT_ID INT, --产品ID
	 * @IN_INVEST_FLAG INT --投资标志：1 贷款合同，2 租赁合同，3 投资合同
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
	 * add by jinxr 2007/4/26 查询产品项目投资运作明细
	 * 
	 * @throws Exception
	 */
	void querytz() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * add by jinxr 2007/4/26 查询产品项目投资运作明细
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
	 * 产品展期
	 * 
	 * @throws Exception
	 */
	void modiPeriod() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 产品展期审核
	 * 
	 * @throws Exception
	 */
	void checkPeriod() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 查询产品展期信息
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
	 * 查询计划业务结束过程
	 * 
	 * @ejb.interface-method view-type = "local"
	 * @throws Exception
	 */
	void queryBusinessEnd() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 计划业务结束
	 * 
	 * @throws Exception
	 */
	void endBusiness() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 计划结束恢复
	 * 
	 * 
	 * @throws Exception
	 */
	void endUnBusinessCheck() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 *                                           @IN_PRODUCT_STATUS      VARCHAR(10) ='110203', --产品状态(1102)              add by luohh 20100621
	                                      @IN_INTRUST_TYPE        VARCHAR(10)  ='',  --运用方式(1104)
	                                      @IN_IS_LOCAL            INT = 0,           --本异地标志：1 本地 2异地  add by luohh 20100621
	                                      @IN_INTRUST_FLAG1       INT = 0,           --单一集合：1 单一 2 集合   add by luohh 20100621
	                                      @IN_INTRUST_FLAG4       INT = 0,           --信托目的：1私益2公益      add by luohh 20100621
	                                      @IN_INTRUST_TYPE1       VARCHAR(10) = '',  --信托类别(1138)：          add by luohh 20100621
	                                      @IN_INTRUST_TYPE2       VARCHAR(10) = '',  --信托投资领域(1139)        add by luohh 20100621
	                                      @IN_DEPART_ID           INT = 0,           --设计部门                  add by luohh 20100621
	                                      @IN_FPFS                NVARCHAR(10)  = '', --受托人报酬提取方式(1105) add by luohh 20100621
	                                      @IN_OPEN_FLAG           INT           = 0,  --发行方式 1开放式2封闭式  add by luohh 20100621
	                                      @IN_MANAGERTYPE         INT           = 0   --1主动管理 2被动管理      add by luohh 20100621
	 * 查询受托产品明细
	 * 
	 * @throws Exception
	 */
	void queryProductDetail() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 导出产品明细-Excel
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
	 *             查询受托产品时间明细
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
	 * 设置核算会计 SP_MODI_TPRODUCT_SUB_MAN
	 * 
	 * @IN_PRODUCT_ID INT,
	 * @IN_SUB_MAN INT,
	 * @IN_INPUT_MAN INT
	 */
	void saveSub_man() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 查询产品日志
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
	 * 推荐地修改
	 * SP_MODI_TPRODUCTCITY @IN_SERIAL_NO         INT,
	 *			             @IN_GOV_PROV_REGIONAL VARCHAR(10) = '',           --省级行政区域(9999)
	 *			             @IN_GOV_REGIONAL      VARCHAR(10) = '',           --行政区域(9999)
	 *			             @IN_INPUT_MAN         INT
	 * @throws Exception
	 */
	void modiProductCity() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 推荐地删除
	 * SP_DEL_TPRODUCTCITY  @IN_SERIAL_NO         INT,
	 *			            @IN_INPUT_MAN         INT
	 * @throws Exception
	 */
	void deleteProductCity() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 查询产品净值
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
	 * 查询产品净值比较信息
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
	 * @IN_INPUT_MAN INT = NULL 查询出 INTRUST_FLAG1 单一集合标志：1 单一 2 集合 FACT_MONEY
	 *               实际发行总额 当INTRUST_FLAG1 =2 即为集合产品时 页面判断贷款合同金额是否超过产品实际发行总额的50%
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
	 * @IN_FUNCTION_ID INT, --功能号：101按"项目经理"统计其总规模和本年贡献 -- 102按单一/集合统计规模
	 * @IN_BEGIN_DATE INT, --起始日期
	 * @IN_END_DATE INT, --结束日期
	 * @IN_INPUT_MAN INT
	 * @throws Exception
	 */
	void queryChart() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 根据显示不同图形 取得不同的参数
	 * 
	 * @param flag
	 * @return
	 * @throws Exception
	 */
	boolean getChartMessage(Integer flag) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 根据product_id取净值(财务净值、业务净值） auto by lk 2009-10-22
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
	 * 查询产品成立公告信息
	 * 
	 * @author lk 2009-11-30 更改所生成类型注释的模板为 窗口 > 首选项 > Java > 代码生成 > 代码和注释
	 */

	void listProductNotice() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 获取产品成立公告内容
	 * 
	 * @author lk 2009-11-30
	 * @return
	 * @throws Exception
	 */
	boolean getNextNotice() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 查询运用管理责任人
	 * @throws Exception
	 */
	void queryProductOp() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 删除运用管理责任人
	 * @throws Exception
	 */
	void delProductOp() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 指定产品责任人
	 * @throws Exception
	 */
	void addProductOp() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 查询该产品已拥有的运用管理责任人
	 * @throws Exception
	 */
	void queryOpPosses() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 获得该产品已拥有的运用管理责任人
	 * @return
	 * @throws Exception
	 */
	boolean getOpNext() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 子产品复核
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
	 * 查询产品销售设置 SP_QUERY_TPRODUCTLIMIT 
	 * @IN_BOOK_CODE        INTEGER,        --账套
	 * @IN_PRODUCT_ID       INTEGER        --产品ID
	 */
	void queryProductLimit() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 */
	boolean getNextProductLimit() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 新增修改产品销售设置 SP_ADD_TPRODUCTLIMIT 
	 * @IN_BOOK_CODE        INTEGER,        --账套
	 * @IN_PRODUCT_ID       INTEGER,        --产品ID
	 * @IN_QUALIFIED_FLAG   INTEGER,        --合格投资人控制：1.控制，2.不控制
	 * @IN_QUALIFIED_NUM    INTEGER,        --非合格投资人合同份数
	 * @IN_QUALIFIED_MONEY  DECIMAL(16,2),  --合格投资人合同金额下限
	 * @IN_ASFUND_FLAG      INTEGER,        --是否为类基金产品：1是，2否
	 * @IN_INPUT_MAN        INTEGER         --操作员
	 */
	void updateProductLimit() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 acct_name。
	 */
	String getAcct_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param acct_name
	 *            要设置的 acct_name。
	 */
	void setAcct_name(String acct_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 admin_manager。
	 */
	java.lang.Integer getAdmin_manager();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param admin_manager
	 *            要设置的 admin_manager。
	 */
	void setAdmin_manager(java.lang.Integer admin_manager);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 admin_manager2。
	 */
	java.lang.Integer getAdmin_manager2();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param admin_manager2
	 *            要设置的 admin_manager2。
	 */
	void setAdmin_manager2(java.lang.Integer admin_manager2);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 all_flag。
	 */
	java.lang.Integer getAll_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param all_flag
	 *            要设置的 all_flag。
	 */
	void setAll_flag(java.lang.Integer all_flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 amount_max。
	 */
	java.math.BigDecimal getAmount_max();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param amount_max
	 *            要设置的 amount_max。
	 */
	void setAmount_max(java.math.BigDecimal amount_max);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 amount_min。
	 */
	java.math.BigDecimal getAmount_min();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param amount_min
	 *            要设置的 amount_min。
	 */
	void setAmount_min(java.math.BigDecimal amount_min);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 balance_3101。
	 */
	BigDecimal getBalance_3101();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param balance_3101
	 *            要设置的 balance_3101。
	 */
	void setBalance_3101(BigDecimal balance_3101);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 balance_all。
	 */
	BigDecimal getBalance_all();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param balance_all
	 *            要设置的 balance_all。
	 */
	void setBalance_all(BigDecimal balance_all);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 balance_lr。
	 */
	BigDecimal getBalance_lr();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param balance_lr
	 *            要设置的 balance_lr。
	 */
	void setBalance_lr(BigDecimal balance_lr);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 bank_acct。
	 */
	String getBank_acct();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param bank_acct
	 *            要设置的 bank_acct。
	 */
	void setBank_acct(String bank_acct);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 bank_name。
	 */
	String getBank_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param bank_name
	 *            要设置的 bank_name。
	 */
	void setBank_name(String bank_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 ben_amount。
	 */
	java.math.BigDecimal getBen_amount();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ben_amount
	 *            要设置的 ben_amount。
	 */
	void setBen_amount(java.math.BigDecimal ben_amount);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 ben_money。
	 */
	java.math.BigDecimal getBen_money();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ben_money
	 *            要设置的 ben_money。
	 */
	void setBen_money(java.math.BigDecimal ben_money);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 ben_num。
	 */
	java.lang.Integer getBen_num();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ben_num
	 *            要设置的 ben_num。
	 */
	void setBen_num(java.lang.Integer ben_num);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 ben_period。
	 */
	java.lang.Integer getBen_period();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ben_period
	 *            要设置的 ben_period。
	 */
	void setBen_period(java.lang.Integer ben_period);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 ben_period_temp。
	 */
	int getBen_period_temp();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ben_period_temp
	 *            要设置的 ben_period_temp。
	 */
	void setBen_period_temp(int ben_period_temp);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 bg_bank_id。
	 */
	String getBg_bank_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param bg_bank_id
	 *            要设置的 bg_bank_id。
	 */
	void setBg_bank_id(String bg_bank_id);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 bg_bank_name。
	 */
	String getBg_bank_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param bg_bank_name
	 *            要设置的 bg_bank_name。
	 */
	void setBg_bank_name(String bg_bank_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 book_code。
	 */
	java.lang.Integer getBook_code();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param book_code
	 *            要设置的 book_code。
	 */
	void setBook_code(java.lang.Integer book_code);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 busi_flag。
	 */
	Integer getBusi_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param busi_flag
	 *            要设置的 busi_flag。
	 */
	void setBusi_flag(Integer busi_flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 busi_name。
	 */
	String getBusi_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param busi_name
	 *            要设置的 busi_name。
	 */
	void setBusi_name(String busi_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 busi_nav_price。
	 */
	java.math.BigDecimal getBusi_nav_price();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param busi_nav_price
	 *            要设置的 busi_nav_price。
	 */
	void setBusi_nav_price(java.math.BigDecimal busi_nav_price);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 business_end_date。
	 */
	Integer getBusiness_end_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param business_end_date
	 *            要设置的 business_end_date。
	 */
	void setBusiness_end_date(Integer business_end_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 business_end_flag。
	 */
	Integer getBusiness_end_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param business_end_flag
	 *            要设置的 business_end_flag。
	 */
	void setBusiness_end_flag(Integer business_end_flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 change_wt_flag。
	 */
	java.lang.Integer getChange_wt_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param change_wt_flag
	 *            要设置的 change_wt_flag。
	 */
	void setChange_wt_flag(java.lang.Integer change_wt_flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 check_flag。
	 */
	java.lang.Integer getCheck_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param check_flag
	 *            要设置的 check_flag。
	 */
	void setCheck_flag(java.lang.Integer check_flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 check_man。
	 */
	Integer getCheck_man();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param check_man
	 *            要设置的 check_man。
	 */
	void setCheck_man(Integer check_man);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 city_name。
	 */
	java.lang.String getCity_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param city_name
	 *            要设置的 city_name。
	 */
	void setCity_name(java.lang.String city_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 contract_num。
	 */
	java.lang.Integer getContract_num();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param contract_num
	 *            要设置的 contract_num。
	 */
	void setContract_num(java.lang.Integer contract_num);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 contract_sub_bh。
	 */
	String getContract_sub_bh();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param contract_sub_bh
	 *            要设置的 contract_sub_bh。
	 */
	void setContract_sub_bh(String contract_sub_bh);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 curr_fact_money。
	 */
	BigDecimal getCurr_fact_money();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param curr_fact_money
	 *            要设置的 curr_fact_money。
	 */
	void setCurr_fact_money(BigDecimal curr_fact_money);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 currency_id。
	 */
	java.lang.String getCurrency_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param currency_id
	 *            要设置的 currency_id。
	 */
	void setCurrency_id(java.lang.String currency_id);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 current_month。
	 */
	Integer getCurrent_month();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param current_month
	 *            要设置的 current_month。
	 */
	void setCurrent_month(Integer current_month);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 cust_name。
	 */
	String getCust_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param cust_name
	 *            要设置的 cust_name。
	 */
	void setCust_name(String cust_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 cw_money。
	 */
	java.math.BigDecimal getCw_money();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param cw_money
	 *            要设置的 cw_money。
	 */
	void setCw_money(java.math.BigDecimal cw_money);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 dBdriver。
	 */
	String getDBdriver();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param bdriver
	 *            要设置的 dBdriver。
	 */
	void setDBdriver(String bdriver);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 deal_type。
	 */
	java.lang.String getDeal_type();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param deal_type
	 *            要设置的 deal_type。
	 */
	void setDeal_type(java.lang.String deal_type);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 deal_type_name。
	 */
	java.lang.String getDeal_type_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param deal_type_name
	 *            要设置的 deal_type_name。
	 */
	void setDeal_type_name(java.lang.String deal_type_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 depart_id。
	 */
	java.lang.Integer getDepart_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param depart_id
	 *            要设置的 depart_id。
	 */
	void setDepart_id(java.lang.Integer depart_id);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 description。
	 */
	java.lang.String getDescription();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param description
	 *            要设置的 description。
	 */
	void setDescription(java.lang.String description);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 end_date。
	 */
	java.lang.Integer getEnd_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param end_date
	 *            要设置的 end_date。
	 */
	void setEnd_date(java.lang.Integer end_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 end_date2。
	 */
	java.lang.Integer getEnd_date2();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param end_date2
	 *            要设置的 end_date2。
	 */
	void setEnd_date2(java.lang.Integer end_date2);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 end_flag。
	 */
	Integer getEnd_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param end_flag
	 *            要设置的 end_flag。
	 */
	void setEnd_flag(Integer end_flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 entity_type。
	 */
	java.lang.String getEntity_type();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param entity_type
	 *            要设置的 entity_type。
	 */
	void setEntity_type(java.lang.String entity_type);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 entity_type_name。
	 */
	java.lang.String getEntity_type_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param entity_type_name
	 *            要设置的 entity_type_name。
	 */
	void setEntity_type_name(java.lang.String entity_type_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 exp_rate1。
	 */
	java.math.BigDecimal getExp_rate1();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param exp_rate1
	 *            要设置的 exp_rate1。
	 */
	void setExp_rate1(java.math.BigDecimal exp_rate1);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 exp_rate2。
	 */
	java.math.BigDecimal getExp_rate2();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param exp_rate2
	 *            要设置的 exp_rate2。
	 */
	void setExp_rate2(java.math.BigDecimal exp_rate2);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 ext_rate1。
	 */
	BigDecimal getExt_rate1();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ext_rate1
	 *            要设置的 ext_rate1。
	 */
	void setExt_rate1(BigDecimal ext_rate1);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 ext_rate2。
	 */
	BigDecimal getExt_rate2();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ext_rate2
	 *            要设置的 ext_rate2。
	 */
	void setExt_rate2(BigDecimal ext_rate2);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 extend_flag。
	 */
	java.lang.Integer getExtend_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param extend_flag
	 *            要设置的 extend_flag。
	 */
	void setExtend_flag(java.lang.Integer extend_flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 fact_end_date。
	 */
	Integer getFact_end_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param fact_end_date
	 *            要设置的 fact_end_date。
	 */
	void setFact_end_date(Integer fact_end_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 fact_money。
	 */
	java.math.BigDecimal getFact_money();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param fact_money
	 *            要设置的 fact_money。
	 */
	void setFact_money(java.math.BigDecimal fact_money);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 fact_num。
	 */
	java.lang.Integer getFact_num();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param fact_num
	 *            要设置的 fact_num。
	 */
	void setFact_num(java.lang.Integer fact_num);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 fact_person_num。
	 */
	java.lang.Integer getFact_person_num();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param fact_person_num
	 *            要设置的 fact_person_num。
	 */
	void setFact_person_num(java.lang.Integer fact_person_num);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 fact_pre_money。
	 */
	java.math.BigDecimal getFact_pre_money();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param fact_pre_money
	 *            要设置的 fact_pre_money。
	 */
	void setFact_pre_money(java.math.BigDecimal fact_pre_money);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 fact_pre_num。
	 */
	java.lang.Integer getFact_pre_num();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param fact_pre_num
	 *            要设置的 fact_pre_num。
	 */
	void setFact_pre_num(java.lang.Integer fact_pre_num);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 fee。
	 */
	BigDecimal getFee();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param fee
	 *            要设置的 fee。
	 */
	void setFee(BigDecimal fee);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 field_cn_name。
	 */
	java.lang.String getField_cn_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param field_cn_name
	 *            要设置的 field_cn_name。
	 */
	void setField_cn_name(java.lang.String field_cn_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 field_name。
	 */
	java.lang.String getField_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param field_name
	 *            要设置的 field_name。
	 */
	void setField_name(java.lang.String field_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 flag。
	 */
	java.lang.Integer getFlag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param flag
	 *            要设置的 flag。
	 */
	void setFlag(java.lang.Integer flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 fpfs。
	 */
	java.lang.String getFpfs();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param fpfs
	 *            要设置的 fpfs。
	 */
	void setFpfs(java.lang.String fpfs);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 fpfs_name。
	 */
	java.lang.String getFpfs_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param fpfs_name
	 *            要设置的 fpfs_name。
	 */
	void setFpfs_name(java.lang.String fpfs_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 fx_fee。
	 */
	java.math.BigDecimal getFx_fee();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param fx_fee
	 *            要设置的 fx_fee。
	 */
	void setFx_fee(java.math.BigDecimal fx_fee);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 gain_money。
	 */
	java.math.BigDecimal getGain_money();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param gain_money
	 *            要设置的 gain_money。
	 */
	void setGain_money(java.math.BigDecimal gain_money);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 gr_amount。
	 */
	java.math.BigDecimal getGr_amount();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param gr_amount
	 *            要设置的 gr_amount。
	 */
	void setGr_amount(java.math.BigDecimal gr_amount);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 gr_count。
	 */
	java.lang.Integer getGr_count();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param gr_count
	 *            要设置的 gr_count。
	 */
	void setGr_count(java.lang.Integer gr_count);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 gr_money。
	 */
	java.math.BigDecimal getGr_money();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param gr_money
	 *            要设置的 gr_money。
	 */
	void setGr_money(java.math.BigDecimal gr_money);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 gr_num。
	 */
	Integer getGr_num();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param gr_num
	 *            要设置的 gr_num。
	 */
	void setGr_num(Integer gr_num);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 hq_date。
	 */
	Integer getHq_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param hq_date
	 *            要设置的 hq_date。
	 */
	void setHq_date(Integer hq_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 ht_money。
	 */
	java.math.BigDecimal getHt_money();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ht_money
	 *            要设置的 ht_money。
	 */
	void setHt_money(java.math.BigDecimal ht_money);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 info_period。
	 */
	java.lang.Integer getInfo_period();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param info_period
	 *            要设置的 info_period。
	 */
	void setInfo_period(java.lang.Integer info_period);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 input_man。
	 */
	java.lang.Integer getInput_man();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param input_man
	 *            要设置的 input_man。
	 */
	void setInput_man(java.lang.Integer input_man);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 input_time。
	 */
	java.sql.Timestamp getInput_time();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param input_time
	 *            要设置的 input_time。
	 */
	void setInput_time(java.sql.Timestamp input_time);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 intrust_flag1。
	 */
	java.lang.Integer getIntrust_flag1();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param intrust_flag1
	 *            要设置的 intrust_flag1。
	 */
	void setIntrust_flag1(java.lang.Integer intrust_flag1);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 intrust_flag1_name。
	 */
	String getIntrust_flag1_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param intrust_flag1_name
	 *            要设置的 intrust_flag1_name。
	 */
	void setIntrust_flag1_name(String intrust_flag1_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 intrust_flag2。
	 */
	java.lang.Integer getIntrust_flag2();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param intrust_flag2
	 *            要设置的 intrust_flag2。
	 */
	void setIntrust_flag2(java.lang.Integer intrust_flag2);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 intrust_flag3。
	 */
	java.lang.Integer getIntrust_flag3();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param intrust_flag3
	 *            要设置的 intrust_flag3。
	 */
	void setIntrust_flag3(java.lang.Integer intrust_flag3);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 intrust_flag4。
	 */
	java.lang.Integer getIntrust_flag4();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param intrust_flag4
	 *            要设置的 intrust_flag4。
	 */
	void setIntrust_flag4(java.lang.Integer intrust_flag4);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 intrust_type。
	 */
	java.lang.String getIntrust_type();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param intrust_type
	 *            要设置的 intrust_type。
	 */
	void setIntrust_type(java.lang.String intrust_type);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 intrust_type_name。
	 */
	java.lang.String getIntrust_type_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param intrust_type_name
	 *            要设置的 intrust_type_name。
	 */
	void setIntrust_type_name(java.lang.String intrust_type_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 intrust_type1。
	 */
	java.lang.String getIntrust_type1();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param intrust_type1
	 *            要设置的 intrust_type1。
	 */
	void setIntrust_type1(java.lang.String intrust_type1);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 intrust_type1_name。
	 */
	java.lang.String getIntrust_type1_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param intrust_type1_name
	 *            要设置的 intrust_type1_name。
	 */
	void setIntrust_type1_name(java.lang.String intrust_type1_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 intrust_type2。
	 */
	java.lang.String getIntrust_type2();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param intrust_type2
	 *            要设置的 intrust_type2。
	 */
	void setIntrust_type2(java.lang.String intrust_type2);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 intrust_type2_name。
	 */
	java.lang.String getIntrust_type2_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param intrust_type2_name
	 *            要设置的 intrust_type2_name。
	 */
	void setIntrust_type2_name(java.lang.String intrust_type2_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 invest_type。
	 */
	String getInvest_type();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param invest_type
	 *            要设置的 invest_type。
	 */
	void setInvest_type(String invest_type);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 item_code。
	 */
	java.lang.String getItem_code();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param item_code
	 *            要设置的 item_code。
	 */
	void setItem_code(java.lang.String item_code);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 item_id。
	 */
	java.lang.Integer getItem_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param item_id
	 *            要设置的 item_id。
	 */
	void setItem_id(java.lang.Integer item_id);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 jg_amount。
	 */
	java.math.BigDecimal getJg_amount();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param jg_amount
	 *            要设置的 jg_amount。
	 */
	void setJg_amount(java.math.BigDecimal jg_amount);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 jg_count。
	 */
	java.lang.Integer getJg_count();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param jg_count
	 *            要设置的 jg_count。
	 */
	void setJg_count(java.lang.Integer jg_count);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 jg_money。
	 */
	java.math.BigDecimal getJg_money();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param jg_money
	 *            要设置的 jg_money。
	 */
	void setJg_money(java.math.BigDecimal jg_money);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 jg_num。
	 */
	Integer getJg_num();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param jg_num
	 *            要设置的 jg_num。
	 */
	void setJg_num(Integer jg_num);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 last_post_date。
	 */
	java.lang.Integer getLast_post_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param last_post_date
	 *            要设置的 last_post_date。
	 */
	void setLast_post_date(java.lang.Integer last_post_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 list_id。
	 */
	Integer getList_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param list_id
	 *            要设置的 list_id。
	 */
	void setList_id(Integer list_id);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 list_name。
	 */
	String getList_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param list_name
	 *            要设置的 list_name。
	 */
	void setList_name(String list_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 manage_fee。
	 */
	java.math.BigDecimal getManage_fee();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param manage_fee
	 *            要设置的 manage_fee。
	 */
	void setManage_fee(java.math.BigDecimal manage_fee);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 manage_rate。
	 */
	java.math.BigDecimal getManage_rate();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param manage_rate
	 *            要设置的 manage_rate。
	 */
	void setManage_rate(java.math.BigDecimal manage_rate);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 manager_type。
	 */
	Integer getManager_type();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param manager_type
	 *            要设置的 manager_type。
	 */
	void setManager_type(Integer manager_type);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 matain_manager。
	 */
	java.lang.Integer getMatain_manager();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param matain_manager
	 *            要设置的 matain_manager。
	 */
	void setMatain_manager(java.lang.Integer matain_manager);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 min_money。
	 */
	java.math.BigDecimal getMin_money();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param min_money
	 *            要设置的 min_money。
	 */
	void setMin_money(java.math.BigDecimal min_money);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 nav_float_num。
	 */
	Integer getNav_float_num();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param nav_float_num
	 *            要设置的 nav_float_num。
	 */
	void setNav_float_num(Integer nav_float_num);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 nav_price。
	 */
	java.math.BigDecimal getNav_price();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param nav_price
	 *            要设置的 nav_price。
	 */
	void setNav_price(java.math.BigDecimal nav_price);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 nav_price1。
	 */
	BigDecimal getNav_price1();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param nav_price1
	 *            要设置的 nav_price1。
	 */
	void setNav_price1(BigDecimal nav_price1);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 nav_price2。
	 */
	BigDecimal getNav_price2();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param nav_price2
	 *            要设置的 nav_price2。
	 */
	void setNav_price2(BigDecimal nav_price2);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 new_end_date。
	 */
	Integer getNew_end_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param new_end_date
	 *            要设置的 new_end_date。
	 */
	void setNew_end_date(Integer new_end_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 new_field_info。
	 */
	java.lang.String getNew_field_info();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param new_field_info
	 *            要设置的 new_field_info。
	 */
	void setNew_field_info(java.lang.String new_field_info);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 old_end_date。
	 */
	Integer getOld_end_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param old_end_date
	 *            要设置的 old_end_date。
	 */
	void setOld_end_date(Integer old_end_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 old_field_info。
	 */
	java.lang.String getOld_field_info();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param old_field_info
	 *            要设置的 old_field_info。
	 */
	void setOld_field_info(java.lang.String old_field_info);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 op_code。
	 */
	Integer getOp_code();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param op_code
	 *            要设置的 op_code。
	 */
	void setOp_code(Integer op_code);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 op_name。
	 */
	String getOp_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param op_name
	 *            要设置的 op_name。
	 */
	void setOp_name(String op_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 open_flag。
	 */
	java.lang.Integer getOpen_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param open_flag
	 *            要设置的 open_flag。
	 */
	void setOpen_flag(java.lang.Integer open_flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 open_flag_name。
	 */
	java.lang.String getOpen_flag_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param open_flag_name
	 *            要设置的 open_flag_name。
	 */
	void setOpen_flag_name(java.lang.String open_flag_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 org_count。
	 */
	Integer getOrg_count();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param org_count
	 *            要设置的 org_count。
	 */
	void setOrg_count(Integer org_count);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 period_unit。
	 */
	Integer getPeriod_unit();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param period_unit
	 *            要设置的 period_unit。
	 */
	void setPeriod_unit(Integer period_unit);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 person_count。
	 */
	Integer getPerson_count();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param person_count
	 *            要设置的 person_count。
	 */
	void setPerson_count(Integer person_count);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 pre_code。
	 */
	java.lang.String getPre_code();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param pre_code
	 *            要设置的 pre_code。
	 */
	void setPre_code(java.lang.String pre_code);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 pre_end_date。
	 */
	java.lang.Integer getPre_end_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param pre_end_date
	 *            要设置的 pre_end_date。
	 */
	void setPre_end_date(java.lang.Integer pre_end_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 pre_max_money。
	 */
	java.math.BigDecimal getPre_max_money();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param pre_max_money
	 *            要设置的 pre_max_money。
	 */
	void setPre_max_money(java.math.BigDecimal pre_max_money);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 pre_max_num。
	 */
	java.lang.Integer getPre_max_num();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param pre_max_num
	 *            要设置的 pre_max_num。
	 */
	void setPre_max_num(java.lang.Integer pre_max_num);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 pre_max_rate。
	 */
	java.math.BigDecimal getPre_max_rate();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param pre_max_rate
	 *            要设置的 pre_max_rate。
	 */
	void setPre_max_rate(java.math.BigDecimal pre_max_rate);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 pre_money。
	 */
	java.math.BigDecimal getPre_money();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param pre_money
	 *            要设置的 pre_money。
	 */
	void setPre_money(java.math.BigDecimal pre_money);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 pre_num。
	 */
	java.lang.Integer getPre_num();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param pre_num
	 *            要设置的 pre_num。
	 */
	void setPre_num(java.lang.Integer pre_num);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 pre_start_date。
	 */
	java.lang.Integer getPre_start_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param pre_start_date
	 *            要设置的 pre_start_date。
	 */
	void setPre_start_date(java.lang.Integer pre_start_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 product_code。
	 */
	java.lang.String getProduct_code();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param product_code
	 *            要设置的 product_code。
	 */
	void setProduct_code(java.lang.String product_code);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 product_from。
	 */
	java.lang.Integer getProduct_from();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param product_from
	 *            要设置的 product_from。
	 */
	void setProduct_from(java.lang.Integer product_from);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 product_id。
	 */
	java.lang.Integer getProduct_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param product_id
	 *            要设置的 product_id。
	 */
	void setProduct_id(java.lang.Integer product_id);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 product_info。
	 */
	java.lang.String getProduct_info();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param product_info
	 *            要设置的 product_info。
	 */
	void setProduct_info(java.lang.String product_info);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 product_jc。
	 */
	java.lang.String getProduct_jc();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param product_jc
	 *            要设置的 product_jc。
	 */
	void setProduct_jc(java.lang.String product_jc);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 product_name。
	 */
	java.lang.String getProduct_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param product_name
	 *            要设置的 product_name。
	 */
	void setProduct_name(java.lang.String product_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 product_status。
	 */
	java.lang.String getProduct_status();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param product_status
	 *            要设置的 product_status。
	 */
	void setProduct_status(java.lang.String product_status);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 product_status_name。
	 */
	java.lang.String getProduct_status_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param product_status_name
	 *            要设置的 product_status_name。
	 */
	void setProduct_status_name(java.lang.String product_status_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 productstatusName。
	 */
	String getProductstatusName();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param productstatusName
	 *            要设置的 productstatusName。
	 */
	void setProductstatusName(String productstatusName);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 prov_level。
	 */
	java.lang.String getProv_level();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param prov_level
	 *            要设置的 prov_level。
	 */
	void setProv_level(java.lang.String prov_level);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 prov_level_a_money。
	 */
	java.math.BigDecimal getProv_level_a_money();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param prov_level_a_money
	 *            要设置的 prov_level_a_money。
	 */
	void setProv_level_a_money(java.math.BigDecimal prov_level_a_money);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 prov_level_b_money。
	 */
	java.math.BigDecimal getProv_level_b_money();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param prov_level_b_money
	 *            要设置的 prov_level_b_money。
	 */
	void setProv_level_b_money(java.math.BigDecimal prov_level_b_money);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 prov_level_name。
	 */
	java.lang.String getProv_level_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param prov_level_name
	 *            要设置的 prov_level_name。
	 */
	void setProv_level_name(java.lang.String prov_level_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 quality_level。
	 */
	java.lang.String getQuality_level();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param quality_level
	 *            要设置的 quality_level。
	 */
	void setQuality_level(java.lang.String quality_level);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 quality_level_name。
	 */
	java.lang.String getQuality_level_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param quality_level_name
	 *            要设置的 quality_level_name。
	 */
	void setQuality_level_name(java.lang.String quality_level_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 sAdmin_man。
	 */
	String getSAdmin_man();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param admin_man
	 *            要设置的 sAdmin_man。
	 */
	void setSAdmin_man(String admin_man);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 sAdmin_man2。
	 */
	String getSAdmin_man2();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param admin_man2
	 *            要设置的 sAdmin_man2。
	 */
	void setSAdmin_man2(String admin_man2);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 serial_no。
	 */
	java.lang.Integer getSerial_no();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param serial_no
	 *            要设置的 serial_no。
	 */
	void setSerial_no(java.lang.Integer serial_no);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 sl_flag。
	 */
	java.lang.Integer getSl_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param sl_flag
	 *            要设置的 sl_flag。
	 */
	void setSl_flag(java.lang.Integer sl_flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 sMatain_man。
	 */
	String getSMatain_man();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param matain_man
	 *            要设置的 sMatain_man。
	 */
	void setSMatain_man(String matain_man);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 start_date。
	 */
	java.lang.Integer getStart_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param start_date
	 *            要设置的 start_date。
	 */
	void setStart_date(java.lang.Integer start_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 start_date2。
	 */
	java.lang.Integer getStart_date2();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param start_date2
	 *            要设置的 start_date2。
	 */
	void setStart_date2(java.lang.Integer start_date2);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 sub_check_flag。
	 */
	java.lang.Integer getSub_check_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param sub_check_flag
	 *            要设置的 sub_check_flag。
	 */
	void setSub_check_flag(java.lang.Integer sub_check_flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 sub_flag。
	 */
	Integer getSub_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param sub_flag
	 *            要设置的 sub_flag。
	 */
	void setSub_flag(Integer sub_flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 sub_man。
	 */
	Integer getSub_man();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param sub_man
	 *            要设置的 sub_man。
	 */
	void setSub_man(Integer sub_man);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 sub_man_name。
	 */
	String getSub_man_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param sub_man_name
	 *            要设置的 sub_man_name。
	 */
	void setSub_man_name(String sub_man_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 sub_product_id。
	 */
	Integer getSub_product_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param sub_product_id
	 *            要设置的 sub_product_id。
	 */
	void setSub_product_id(Integer sub_product_id);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 summary。
	 */
	java.lang.String getSummary();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param summary
	 *            要设置的 summary。
	 */
	void setSummary(java.lang.String summary);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 task_date。
	 */
	Integer getTask_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param task_date
	 *            要设置的 task_date。
	 */
	void setTask_date(Integer task_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 tax_rate。
	 */
	BigDecimal getTax_rate();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param tax_rate
	 *            要设置的 tax_rate。
	 */
	void setTax_rate(BigDecimal tax_rate);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 tg_acct_name。
	 */
	String getTg_acct_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param tg_acct_name
	 *            要设置的 tg_acct_name。
	 */
	void setTg_acct_name(String tg_acct_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 tg_bank_acct。
	 */
	java.lang.String getTg_bank_acct();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param tg_bank_acct
	 *            要设置的 tg_bank_acct。
	 */
	void setTg_bank_acct(java.lang.String tg_bank_acct);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 tg_bank_id。
	 */
	java.lang.String getTg_bank_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param tg_bank_id
	 *            要设置的 tg_bank_id。
	 */
	void setTg_bank_id(java.lang.String tg_bank_id);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 tg_bank_name。
	 */
	java.lang.String getTg_bank_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param tg_bank_name
	 *            要设置的 tg_bank_name。
	 */
	void setTg_bank_name(java.lang.String tg_bank_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 tg_bank_sub_id。
	 */
	java.lang.String getTg_bank_sub_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param tg_bank_sub_id
	 *            要设置的 tg_bank_sub_id。
	 */
	void setTg_bank_sub_id(java.lang.String tg_bank_sub_id);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 tg_bank_sub_name。
	 */
	java.lang.String getTg_bank_sub_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param tg_bank_sub_name
	 *            要设置的 tg_bank_sub_name。
	 */
	void setTg_bank_sub_name(java.lang.String tg_bank_sub_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 time_flag。
	 */
	Integer getTime_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param time_flag
	 *            要设置的 time_flag。
	 */
	void setTime_flag(Integer time_flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 total_amount。
	 */
	java.math.BigDecimal getTotal_amount();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param total_amount
	 *            要设置的 total_amount。
	 */
	void setTotal_amount(java.math.BigDecimal total_amount);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 total_fact_money。
	 */
	String[] getTotal_fact_money();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param total_fact_money
	 *            要设置的 total_fact_money。
	 */
	void setTotal_fact_money(String[] total_fact_money);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 total_money。
	 */
	java.math.BigDecimal getTotal_money();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param total_money
	 *            要设置的 total_money。
	 */
	void setTotal_money(java.math.BigDecimal total_money);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 trade_date。
	 */
	java.lang.Integer getTrade_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param trade_date
	 *            要设置的 trade_date。
	 */
	void setTrade_date(java.lang.Integer trade_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 trade_rate。
	 */
	java.math.BigDecimal getTrade_rate();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param trade_rate
	 *            要设置的 trade_rate。
	 */
	void setTrade_rate(java.math.BigDecimal trade_rate);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 trade_tax_rate。
	 */
	BigDecimal getTrade_tax_rate();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param trade_tax_rate
	 *            要设置的 trade_tax_rate。
	 */
	void setTrade_tax_rate(BigDecimal trade_tax_rate);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 trade_time。
	 */
	Timestamp getTrade_time();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param trade_time
	 *            要设置的 trade_time。
	 */
	void setTrade_time(Timestamp trade_time);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 trade_type。
	 */
	java.lang.String getTrade_type();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param trade_type
	 *            要设置的 trade_type。
	 */
	void setTrade_type(java.lang.String trade_type);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 trade_type_name。
	 */
	java.lang.String getTrade_type_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param trade_type_name
	 *            要设置的 trade_type_name。
	 */
	void setTrade_type_name(java.lang.String trade_type_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 trust_contract_name。
	 */
	String getTrust_contract_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param trust_contract_name
	 *            要设置的 trust_contract_name。
	 */
	void setTrust_contract_name(String trust_contract_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 valid_period。
	 */
	java.lang.Integer getValid_period();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param valid_period
	 *            要设置的 valid_period。
	 */
	void setValid_period(java.lang.Integer valid_period);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 valid_period2。
	 */
	Integer getValid_period2();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param valid_period2
	 *            要设置的 valid_period2。
	 */
	void setValid_period2(Integer valid_period2);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 zjye。
	 */
	java.math.BigDecimal getZjye();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param zjye
	 *            要设置的 zjye。
	 */
	void setZjye(java.math.BigDecimal zjye);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 managetype_name。
	 */
	String getManagetype_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 result_standard。
	 */
	BigDecimal getResult_standard();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            result_standard。
	 */
	void setResult_standard(BigDecimal result_standard);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 share_flag。
	 */
	Integer getShare_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param share_flag 要设置的 share_flag。
	 */
	void setShare_flag(Integer share_flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 coperate_type。
	 */
	Integer getCoperate_type();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param coperate_type 要设置的 coperate_type。
	 */
	void setCoperate_type(Integer coperate_type);

	/**
	 *  @ejb.interface-method view-type = "local"
	 * @return 返回 gov_pegional。
	 */
	String getGov_pegional();

	/**
	 *  @ejb.interface-method view-type = "local"
	 * @param gov_pegional 要设置的 gov_pegional。
	 */
	void setGov_pegional(String gov_pegional);

	/**
	 *  @ejb.interface-method view-type = "local"
	 * @return 返回 gov_prov_pegional。
	 */
	String getGov_prov_pegional();

	/**
	 *  @ejb.interface-method view-type = "local"
	 * @param gov_prov_pegional 要设置的 gov_prov_pegional。
	 */
	void setGov_prov_pegional(String gov_prov_pegional);

	/**
	 *  @ejb.interface-method view-type = "local"
	 * @return 返回 org_money。
	 */
	java.math.BigDecimal getOrg_money();

	/**
	 *  @ejb.interface-method view-type = "local"
	 * @param org_money 要设置的 org_money。
	 */
	void setOrg_money(java.math.BigDecimal org_money);

	/**
	 *  @ejb.interface-method view-type = "local"
	 * @return 返回 person_money。
	 */
	java.math.BigDecimal getPerson_money();

	/**
	 *  @ejb.interface-method view-type = "local"
	 * @param person_money 要设置的 person_money。
	 */
	void setPerson_money(java.math.BigDecimal person_money);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 sub_product_code。
	 */
	String getSub_product_code();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param sub_product_code 要设置的 sub_product_code。
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
	 * @return 返回 deal_flag。
	 */
	Integer getDeal_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param deal_flag 要设置的 deal_flag。
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
	 * @return 返回 trust_fee_period。
	 */
	Integer getTrust_fee_period();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param trust_fee_period 要设置的 trust_fee_period。
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
	 * @return 返回 asfund_flag。
	 */
	Integer getAsfund_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param asfund_flag 要设置的 asfund_flag。
	 */
	void setAsfund_flag(Integer asfund_flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 qualified_flag。
	 */
	Integer getQualified_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param qualified_flag 要设置的 qualified_flag。
	 */
	void setQualified_flag(Integer qualified_flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 qualified_num。
	 */
	Integer getQualified_num();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param qualified_num 要设置的 qualified_num。
	 */
	void setQualified_num(Integer qualified_num);

}