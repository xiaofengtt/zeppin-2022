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
	 * @return 返回 contract_zjflag。
	 */
	Integer getContract_zjflag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param contract_zjflag 要设置的 contract_zjflag。
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
	 * @IN_ENTITY_PRICE DECIMAL(16,3),--20050524谭鸿添加
	 * @IN_ENTITY_NAME VARCHAR(50),--20050524谭鸿添加
	 * @IN_ENTITY_TYPE VARCHAR(10),--20050524谭鸿添加
	 * @IN_CONTRACT_SUB_BH VARCHAR(30),--20050524谭鸿添加
	 * @IN_CITY_SERIAL_NO INT,
	 * @OUT_SERIAL_NO INT OUTPUT,
	 * @OUT_CONTRACT_BH VARCHAR(16) OUTPUT,
	 * @IN_ZY_FLAG INT = 1,
	 * @IN_TOUCH_TYPE VARCHAR(40) = NULL, --ADD BY SHENKL 2007/03/06
	 * @IN_TOUCH_TYPE_NAME VARCHAR(30) = NULL, --ADD BY SHENKL 2007/03/06
	 * @IN_GAIN_ACCT VARCHAR(60) = NULL --ADD BY JINXR 2007/4/16 银行帐号户名
	 * @IN_FEE_JK_TYPE INT =1 ----费用缴款方式：1从本金扣，2另外交
	 * 
	 * YUZ 反洗钱添加
	 * @IN_BANK_ACCT_TYPE VARCHAR(10) 银行账户类型(9920)
	 * 
	 * //2009-07-29
	 * @IN_START_DATE INT = NULL,
	 * @IN_END_DATE INT = NULL
	 * @IN_BOUNS_FLAG INT = 1 1、兑付 2、转份额
	 * @IN_SUB_PRODUCT_ID INT = 0 --子产品ID ADD BY JINXR 2009/11/29
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
	 * @IN_GAIN_ACCT VARCHAR(60) = NULL --ADD BY JINXR 2007/4/16 银行帐号户名
	 * @IN_CONTRACT_SUB_BH NVARCHAR(50) = '' -- 实际合同编号
	 * 
	 * YZJ 反洗钱 填加
	 * @IN_BANK_ACCT_TYPE VARCHAR(10) 银行账户类型(9920)
	 * @IN_BONUS_FLAG INT = 1 1、兑付 2、转份额
	 * @IN_SUB_PRODUCT_ID INT = 0 --子产品ID ADD BY JINXR 2009/11/29
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
	 * @IN_ENTITY_PRICE DECIMAL(16,3), --20050524谭鸿添加
	 * @IN_ENTITY_NAME VARCHAR(50), --20050524谭鸿添加
	 * @IN_ENTITY_TYPE VARCHAR(10), --20050524谭鸿添加
	 * @IN_CONTRACT_SUB_BH VARCHAR(30), --20050524谭鸿添加
	 * @IN_CITY_SERIAL_NO INT,
	 * @IN_TOUCH_TYPE VARCHAR(40) = NULL, --ADD BY SHENKL 2007/03/06
	 * @IN_TOUCH_TYPE_NAME VARCHAR(30) = NULL, --ADD BY SHENKL 2007/03/06
	 * @IN_GAIN_ACCT VARCHAR(60) = NULL --ADD BY JINXR 2007/4/16 银行帐号户名
	 * 
	 * @IN_BANK_ACCT_TYPE VARCHAR(10) 银行账户类型(9920)
	 * 
	 * //2009-07-29
	 * @IN_START_DATE INT = NULL,
	 * @IN_END_DATE INT = NULL
	 * @IN_BOUNS_FLAG INT = 1 1、兑付 2、转份额
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
	 * @IN_FLAG INT = 1 --1、通过 2、不通过
	 */
	void checkEntityPZ() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 客户认购合同审核恢复 SP_CHECK_TCONTRACT_BACK
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
	 * 合同挂失 SP_LOSE_TCONTRACT
	 * @IN_PRODUCT_ID INT,
	 * @IN_CONTRACT_BH VARCHAR(16),
	 * @IN_INPUT_MAN INT
	 */
	void appendLose() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 合同解挂 SP_UNLOSE_TCONTRACT
	 * @IN_PRODUCT_ID INT,
	 * @IN_CONTRACT_BH VARCHAR(16),
	 * @IN_INPUT_MAN INT
	 */
	void unLose() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 合同冻结 SP_FROZEN_TCONTRACT
	 * @IN_PRODUCT_ID INT,
	 * @IN_CONTRACT_BH VARCHAR(16),
	 * @IN_INPUT_MAN INT
	 */
	void Frozen() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 合同解冻 SP_UNFROZEN_TCONTRACT
	 * @IN_PRODUCT_ID INT,
	 * @IN_CONTRACT_BH VARCHAR(16),
	 * @IN_INPUT_MAN INT
	 */
	void UnFrozen() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 合同冻结复核 SP_CHECK_FROZEN_TCONTRACT
	 * @IN_PRODUCT_ID INT,
	 * @IN_CONTRACT_BH VARCHAR(16),
	 * @IN_CHECK_FLAG INT,
	 * @IN_INPUT_MAN INT
	 */
	void checkFrozen(int flag) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 合同解冻复核 SP_CHECK_UNFROZEN_TCONTRACT
	 * @IN_PRODUCT_ID INT,
	 * @IN_CONTRACT_BH VARCHAR(16),
	 * @IN_CHECK_FLAG INT,
	 * @IN_INPUT_MAN INT
	 */
	void checkUnFrozen(int flag) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 合同续签 SP_CONTINUE_TCONTRACT
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
	 * 合同中止 SP_STOP_TCONTRACT
	 * @IN_PRODUCT_ID INT,
	 * @IN_CONTRACT_BH VARCHAR(16),
	 * @IN_INPUT_MAN INT,
	 * @IN_TERMINATE_DATE INT
	 */
	void stopContract() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 合同挂失复核 SP_CHECK_LOSE_TCONTRACT
	 * @IN_PRODUCT_ID INT,
	 * @IN_CONTRACT_BH VARCHAR(16),
	 * @IN_CHECK_FLAG INT,
	 * @IN_INPUT_MAN INT
	 */
	void checkLose(int flag) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 合同解挂复核 SP_CHECK_UNLOSE_TCONTRACT
	 * @IN_PRODUCT_ID INT,
	 * @IN_CONTRACT_BH VARCHAR(16),
	 * @IN_CHECK_FLAG INT,
	 * @IN_INPUT_MAN INT
	 */
	void checkUnlose(int flag) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 合同续签复核 SP_CHECK_CONTINUE_TCONTRACT
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
	 * 参数：product_id$product_code$contract_bh
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
	 * @IN_CHECK_FLAG INT = NULL --审核标志(1、未审核 2、已审核 0、全部) add by shenkl
	 *                2007/03/06
	 * @IN_CONTRACT_SUB_BH NVARCHAR(50) = '', -- 合同编号查询  20080104 by wangc
	 * @IN_CONTRACT_ZJFLAG  INT  =0            --合同性质：1 资金合同 2 财产合同 0 全部
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
	 * @IN_CONTRACT_SUB_BH NVARCHAR(50) = '' -- 合同编号
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
	 * @IN_CHECK_FLAG INT, -- 21 合同终止界面用查询
	 * @IN_INPUT_MAN INT
	 * @IN_CONTRACT_SUB_BH NVARCHAR(50) = '' -- 合同编号
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
	 * @IN_CONTRACT_SUB_BH NVARCHAR(50) = '' -- 合同编号
	 */
	//产品编号$合同编号$复核标志（4查出未复核和未科长复核的 0 查出所有）
	void queryLose(String sQuery) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_QUERY_UNLOSE_TCONTRACT
	 * @IN_BOOK_CODE INT,
	 * @IN_PRODUCT_ID INT,
	 * @IN_CONTRACT_BH VARCHAR(16),
	 * @IN_CHECK_FLAG INT,
	 * @IN_INPUT_MAN INT = NULL // 产品编号$合同编号$复核标志（4查出未复核和未科长复核的 0 查出所有）
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

	//flag为冻结标志
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

	//	产品编号$合同编号$复核标志（1查出未复核 0 查出所有）
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

	//	产品编号$合同编号$复核标志（4查出未复核和未科长复核的 0 查出所有）
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

	//	客户认购合同查询
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

	//用在purchase3.jsp里
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
	 * 获得认购查询信息查询的结果集 添加此方法用于自定义显示列
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
	 * 合同变更明细查询返回List集合
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
	 * @IN_CONTRACT_TYPE INT, --合同类型：1,认购合同，2 申购合同 3，普通贷款合同表 4，租赁合同表 5，投资合同
	 * @IN_PRODUCT_ID INT, --产品ID
	 * @IN_CONTRACT_SUB_BH NVARCHAR(50),
	 * @OUT_EXISTS_FLAG INT OUTPUT --1 存在 0 不存在 可用
	 *  
	 */
	Integer isExistSameContractBH(Integer contract_type) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 20040120 By CaiYuan 打印信封页面根据客户打印购买的信托产品的名称
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
	 * 查询
	 * 
	 * @param product_name
	 * @param contract_bh
	 * @param cust_name
	 * @param dealFlag 处理标志。1表示代理销售，2代表代理销售更新
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
	 * @return 返回 acct_user。
	 */
	java.lang.String getAcct_user();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            acct_user。
	 */
	void setAcct_user(java.lang.String acct_user);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 bank_acct。
	 */
	java.lang.String getBank_acct();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            bank_acct。
	 */
	void setBank_acct(java.lang.String bank_acct);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 bank_acct_type。
	 */
	String getBank_acct_type();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            bank_acct_type。
	 */
	void setBank_acct_type(String bank_acct_type);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 bank_id。
	 */
	java.lang.String getBank_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            bank_id。
	 */
	void setBank_id(java.lang.String bank_id);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 bank_name。
	 */
	java.lang.String getBank_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            bank_name。
	 */
	void setBank_name(java.lang.String bank_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 bank_sub_name。
	 */
	java.lang.String getBank_sub_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            bank_sub_name。
	 */
	void setBank_sub_name(java.lang.String bank_sub_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 ben_rec_no。
	 */
	int getBen_rec_no();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            ben_rec_no。
	 */
	void setBen_rec_no(int ben_rec_no);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 benifitor_address。
	 */
	String getBenifitor_address();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            benifitor_address。
	 */
	void setBenifitor_address(String benifitor_address);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 benifitor_card_id。
	 */
	java.lang.String getBenifitor_card_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            benifitor_card_id。
	 */
	void setBenifitor_card_id(java.lang.String benifitor_card_id);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 benifitor_card_type_name。
	 */
	String getBenifitor_card_type_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            benifitor_card_type_name。
	 */
	void setBenifitor_card_type_name(String benifitor_card_type_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 benifitor_contact_methed。
	 */
	String getBenifitor_contact_methed();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            benifitor_contact_methed。
	 */
	void setBenifitor_contact_methed(String benifitor_contact_methed);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 benifitor_cust_type_name。
	 */
	String getBenifitor_cust_type_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            benifitor_cust_type_name。
	 */
	void setBenifitor_cust_type_name(String benifitor_cust_type_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 benifitor_Email。
	 */
	String getBenifitor_Email();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            benifitor_Email。
	 */
	void setBenifitor_Email(String benifitor_Email);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 benifitor_handset_number。
	 */
	String getBenifitor_handset_number();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            benifitor_handset_number。
	 */
	void setBenifitor_handset_number(String benifitor_handset_number);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 benifitor_lpr。
	 */
	String getBenifitor_lpr();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            benifitor_lpr。
	 */
	void setBenifitor_lpr(String benifitor_lpr);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 benifitor_name。
	 */
	java.lang.String getBenifitor_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            benifitor_name。
	 */
	void setBenifitor_name(java.lang.String benifitor_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 benifitor_post。
	 */
	String getBenifitor_post();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            benifitor_post。
	 */
	void setBenifitor_post(String benifitor_post);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 benifitor_telephone。
	 */
	String getBenifitor_telephone();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            benifitor_telephone。
	 */
	void setBenifitor_telephone(String benifitor_telephone);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 bonus_flag。
	 */
	Integer getBonus_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            bonus_flag。
	 */
	void setBonus_flag(Integer bonus_flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 book_code。
	 */
	java.lang.Integer getBook_code();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            book_code。
	 */
	void setBook_code(java.lang.Integer book_code);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 card_id。
	 */
	java.lang.String getCard_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            card_id。
	 */
	void setCard_id(java.lang.String card_id);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 check_flag。
	 */
	java.lang.Integer getCheck_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            check_flag。
	 */
	void setCheck_flag(java.lang.Integer check_flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 check_flag_name。
	 */
	java.lang.String getCheck_flag_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            check_flag_name。
	 */
	void setCheck_flag_name(java.lang.String check_flag_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 check_man。
	 */
	java.lang.Integer getCheck_man();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            check_man。
	 */
	void setCheck_man(java.lang.Integer check_man);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 check_time。
	 */
	java.sql.Timestamp getCheck_time();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            check_time。
	 */
	void setCheck_time(java.sql.Timestamp check_time);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 city_name。
	 */
	String getCity_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            city_name。
	 */
	void setCity_name(String city_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 city_serialno。
	 */
	java.lang.Integer getCity_serialno();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            city_serialno。
	 */
	void setCity_serialno(java.lang.Integer city_serialno);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 contract_bh。
	 */
	java.lang.String getContract_bh();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            contract_bh。
	 */
	void setContract_bh(java.lang.String contract_bh);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 contract_id。
	 */
	java.lang.Integer getContract_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            contract_id。
	 */
	void setContract_id(java.lang.Integer contract_id);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 contract_sub_bh。
	 */
	String getContract_sub_bh();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            contract_sub_bh。
	 */
	void setContract_sub_bh(String contract_sub_bh);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 currency_id。
	 */
	java.lang.String getCurrency_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            currency_id。
	 */
	void setCurrency_id(java.lang.String currency_id);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 currency_name。
	 */
	java.lang.String getCurrency_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            currency_name。
	 */
	void setCurrency_name(java.lang.String currency_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 cust_id。
	 */
	java.lang.Integer getCust_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            cust_id。
	 */
	void setCust_id(java.lang.Integer cust_id);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 cust_name。
	 */
	java.lang.String getCust_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            cust_name。
	 */
	void setCust_name(java.lang.String cust_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 cust_no。
	 */
	java.lang.String getCust_no();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            cust_no。
	 */
	void setCust_no(java.lang.String cust_no);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 cust_type。
	 */
	java.lang.Integer getCust_type();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            cust_type。
	 */
	void setCust_type(java.lang.Integer cust_type);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 df_cust_id。
	 */
	Integer getDf_cust_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            df_cust_id。
	 */
	void setDf_cust_id(Integer df_cust_id);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 end_date。
	 */
	java.lang.Integer getEnd_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            end_date。
	 */
	void setEnd_date(java.lang.Integer end_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 entity_name。
	 */
	String getEntity_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            entity_name。
	 */
	void setEntity_name(String entity_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 entity_price。
	 */
	java.math.BigDecimal getEntity_price();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            entity_price。
	 */
	void setEntity_price(java.math.BigDecimal entity_price);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 entity_type。
	 */
	String getEntity_type();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            entity_type。
	 */
	void setEntity_type(String entity_type);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 entity_type_name。
	 */
	String getEntity_type_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            entity_type_name。
	 */
	void setEntity_type_name(String entity_type_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 fee_jk_type。
	 */
	int getFee_jk_type();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            fee_jk_type。
	 */
	void setFee_jk_type(int fee_jk_type);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 gain_acct。
	 */
	java.lang.String getGain_acct();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            gain_acct。
	 */
	void setGain_acct(java.lang.String gain_acct);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 ht_status。
	 */
	java.lang.String getHt_status();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            ht_status。
	 */
	void setHt_status(java.lang.String ht_status);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 ht_status_name。
	 */
	java.lang.String getHt_status_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            ht_status_name。
	 */
	void setHt_status_name(java.lang.String ht_status_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 input_man。
	 */
	java.lang.Integer getInput_man();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            input_man。
	 */
	void setInput_man(java.lang.Integer input_man);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 input_time。
	 */
	java.sql.Timestamp getInput_time();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            input_time。
	 */
	void setInput_time(java.sql.Timestamp input_time);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 jk_date。
	 */
	java.lang.Integer getJk_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            jk_date。
	 */
	void setJk_date(java.lang.Integer jk_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 jk_status。
	 */
	java.lang.Integer getJk_status();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            jk_status。
	 */
	void setJk_status(java.lang.Integer jk_status);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 jk_total_money。
	 */
	BigDecimal getJk_total_money();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            jk_total_money。
	 */
	void setJk_total_money(BigDecimal jk_total_money);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 jk_type。
	 */
	java.lang.String getJk_type();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            jk_type。
	 */
	void setJk_type(java.lang.String jk_type);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 jk_type_name。
	 */
	java.lang.String getJk_type_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            jk_type_name。
	 */
	void setJk_type_name(java.lang.String jk_type_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 last_trans_date。
	 */
	java.lang.Integer getLast_trans_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            last_trans_date。
	 */
	void setLast_trans_date(java.lang.Integer last_trans_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 link_man。
	 */
	java.lang.Integer getLink_man();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            link_man。
	 */
	void setLink_man(java.lang.Integer link_man);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 list_id。
	 */
	java.lang.Integer getList_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            list_id。
	 */
	void setList_id(java.lang.Integer list_id);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 max_rg_money。
	 */
	java.math.BigDecimal getMax_rg_money();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            max_rg_money。
	 */
	void setMax_rg_money(java.math.BigDecimal max_rg_money);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 min_rg_money。
	 */
	java.math.BigDecimal getMin_rg_money();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            min_rg_money。
	 */
	void setMin_rg_money(java.math.BigDecimal min_rg_money);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 only_thisproduct。
	 */
	java.lang.Integer getOnly_thisproduct();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            only_thisproduct。
	 */
	void setOnly_thisproduct(java.lang.Integer only_thisproduct);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 pre_code。
	 */
	java.lang.String getPre_code();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            pre_code。
	 */
	void setPre_code(java.lang.String pre_code);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 pre_flag。
	 */
	java.lang.Integer getPre_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            pre_flag。
	 */
	void setPre_flag(java.lang.Integer pre_flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 pre_money。
	 */
	java.math.BigDecimal getPre_money();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            pre_money。
	 */
	void setPre_money(java.math.BigDecimal pre_money);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 product_code。
	 */
	java.lang.String getProduct_code();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            product_code。
	 */
	void setProduct_code(java.lang.String product_code);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 product_id。
	 */
	java.lang.Integer getProduct_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            product_id。
	 */
	void setProduct_id(java.lang.Integer product_id);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 product_name。
	 */
	java.lang.String getProduct_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            product_name。
	 */
	void setProduct_name(java.lang.String product_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 property_name。
	 */
	String getProperty_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            property_name。
	 */
	void setProperty_name(String property_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 property_type_name。
	 */
	String getProperty_type_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            property_type_name。
	 */
	void setProperty_type_name(String property_type_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 prov_level。
	 */
	String getProv_level();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            prov_level。
	 */
	void setProv_level(String prov_level);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 pz_flag。
	 */
	java.lang.Integer getPz_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            pz_flag。
	 */
	void setPz_flag(java.lang.Integer pz_flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 qs_date。
	 */
	java.lang.Integer getQs_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            qs_date。
	 */
	void setQs_date(java.lang.Integer qs_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 rg_fee_money。
	 */
	BigDecimal getRg_fee_money();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            rg_fee_money。
	 */
	void setRg_fee_money(BigDecimal rg_fee_money);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 rg_fee_rate。
	 */
	BigDecimal getRg_fee_rate();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            rg_fee_rate。
	 */
	void setRg_fee_rate(BigDecimal rg_fee_rate);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 rg_money。
	 */
	java.math.BigDecimal getRg_money();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            rg_money。
	 */
	void setRg_money(java.math.BigDecimal rg_money);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 rg_money2。
	 */
	BigDecimal getRg_money2();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            rg_money2。
	 */
	void setRg_money2(BigDecimal rg_money2);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 self_ben_flag。
	 */
	int getSelf_ben_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            self_ben_flag。
	 */
	void setSelf_ben_flag(int self_ben_flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 serial_no。
	 */
	java.lang.Integer getSerial_no();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            serial_no。
	 */
	void setSerial_no(java.lang.Integer serial_no);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 service_man。
	 */
	java.lang.Integer getService_man();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            service_man。
	 */
	void setService_man(java.lang.Integer service_man);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 start_date。
	 */
	java.lang.Integer getStart_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            start_date。
	 */
	void setStart_date(java.lang.Integer start_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 str_jk_date。
	 */
	java.lang.String getStr_jk_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            str_jk_date。
	 */
	void setStr_jk_date(java.lang.String str_jk_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 str_qs_date。
	 */
	java.lang.String getStr_qs_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            str_qs_date。
	 */
	void setStr_qs_date(java.lang.String str_qs_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 str_valid_period。
	 */
	java.lang.String getStr_valid_period();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            str_valid_period。
	 */
	void setStr_valid_period(java.lang.String str_valid_period);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 sub_product_id。
	 */
	Integer getSub_product_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            sub_product_id。
	 */
	void setSub_product_id(Integer sub_product_id);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 summary。
	 */
	java.lang.String getSummary();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            summary。
	 */
	void setSummary(java.lang.String summary);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 temp_end_date。
	 */
	java.lang.Integer getTemp_end_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            temp_end_date。
	 */
	void setTemp_end_date(java.lang.Integer temp_end_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 temp_rg_money。
	 */
	java.math.BigDecimal getTemp_rg_money();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            temp_rg_money。
	 */
	void setTemp_rg_money(java.math.BigDecimal temp_rg_money);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 terminate_date。
	 */
	java.lang.Integer getTerminate_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            terminate_date。
	 */
	void setTerminate_date(java.lang.Integer terminate_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 to_amount。
	 */
	java.math.BigDecimal getTo_amount();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            to_amount。
	 */
	void setTo_amount(java.math.BigDecimal to_amount);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 to_money。
	 */
	java.math.BigDecimal getTo_money();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            to_money。
	 */
	void setTo_money(java.math.BigDecimal to_money);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 touch_type。
	 */
	String getTouch_type();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            touch_type。
	 */
	void setTouch_type(String touch_type);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 touch_type_name。
	 */
	String getTouch_type_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            touch_type_name。
	 */
	void setTouch_type_name(String touch_type_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 trans_flag。
	 */
	java.lang.String getTrans_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            trans_flag。
	 */
	void setTrans_flag(java.lang.String trans_flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 trans_flag_name。
	 */
	java.lang.String getTrans_flag_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            trans_flag_name。
	 */
	void setTrans_flag_name(java.lang.String trans_flag_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 valid_period。
	 */
	java.lang.Integer getValid_period();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            valid_period。
	 */
	void setValid_period(java.lang.Integer valid_period);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 wtr__post。
	 */
	String getWtr__post();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            wtr__post。
	 */
	void setWtr__post(String wtr__post);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 wtr_address。
	 */
	String getWtr_address();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            wtr_address。
	 */
	void setWtr_address(String wtr_address);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 wtr_card_type_name。
	 */
	String getWtr_card_type_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            wtr_card_type_name。
	 */
	void setWtr_card_type_name(String wtr_card_type_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 wtr_contact_methed。
	 */
	String getWtr_contact_methed();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            wtr_contact_methed。
	 */
	void setWtr_contact_methed(String wtr_contact_methed);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 wtr_cust_type_name。
	 */
	String getWtr_cust_type_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            wtr_cust_type_name。
	 */
	void setWtr_cust_type_name(String wtr_cust_type_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 wtr_Email。
	 */
	String getWtr_Email();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            wtr_Email。
	 */
	void setWtr_Email(String wtr_Email);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 wtr_handset_number。
	 */
	String getWtr_handset_number();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            wtr_handset_number。
	 */
	void setWtr_handset_number(String wtr_handset_number);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 wtr_lpr。
	 */
	String getWtr_lpr();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            wtr_lpr。
	 */
	void setWtr_lpr(String wtr_lpr);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 wtr_telephone。
	 */
	String getWtr_telephone();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            wtr_telephone。
	 */
	void setWtr_telephone(String wtr_telephone);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 zj_check_flag。
	 */
	java.lang.Integer getZj_check_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            zj_check_flag。
	 */
	void setZj_check_flag(java.lang.Integer zj_check_flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 zj_check_man。
	 */
	java.lang.Integer getZj_check_man();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            zj_check_man。
	 */
	void setZj_check_man(java.lang.Integer zj_check_man);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 with_bank_flag。
	 */
	Integer getWith_bank_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param with_bank_flag 要设置的 with_bank_flag。
	 */
	void setWith_bank_flag(Integer with_bank_flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 ht_bank_id。
	 */
	String getHt_bank_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ht_bank_id 要设置的 ht_bank_id。
	 */
	void setHt_bank_id(String ht_bank_id);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 ht_bank_sub_name。
	 */
	String getHt_bank_sub_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ht_bank_sub_name 要设置的 ht_bank_sub_name。
	 */
	void setHt_bank_sub_name(String ht_bank_sub_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 ht_bank_name。
	 */
	String getHt_bank_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ht_bank_name 要设置的 ht_bank_name。
	 */
	void setHt_bank_name(String ht_bank_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 with_private_flag。
	 */
	Integer getWith_private_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param with_private_flag 要设置的 with_private_flag。
	 */
	void setWith_private_flag(Integer with_private_flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 with_security_flag。
	 */
	Integer getWith_security_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param with_security_flag 要设置的 with_security_flag。
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
	 * @return 返回 bonus_rate。
	 */
	BigDecimal getBonus_rate();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param bonus_rate 要设置的 bonus_rate。
	 */
	void setBonus_rate(BigDecimal bonus_rate);

	/**
	 * @ejb.interface-method view-type = "local"
	 * 受益级别名称
	 */
	String getProv_level_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * 受益级别名称
	 */
	void setProv_level_name(String prov_level_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 prov_flag。
	 */
	Integer getProv_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param prov_flag 要设置的 prov_flag。
	 */
	void setProv_flag(Integer prov_flag);

}