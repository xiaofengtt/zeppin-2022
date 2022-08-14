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
	 * 搜索单个受益人
	 * @param vo
	 * @return
	 * @throws BusiException
	 * 
	 */
	List load(BenifitorVO vo) throws BusiException;

	/**
	 * @IN_BOOK_CODE        INTEGER,                  --帐套
	                                     @IN_PRODUCT_ID       INTEGER,                  --产品ID
	                                     @IN_CONTRACT_BH      NVARCHAR(16),             --认购合同编号
	                                     @IN_INPUT_MAN        INTEGER       = NULL,     --操作员
	                                     @IN_SUB_PRODUCT_ID   INTEGER       = 0,        --子产品ID
	                                     @IN_CUST_NAME        NVARCHAR(120) = NULL,     --客户名称
	                                     @IN_CARD_ID          NVARCHAR(60)  = NULL,     --证件号
	                                     @IN_CONTRACT_SUB_BH  NVARCHAR(80)  = '',       --实际合同编号
	                                     @IN_IS_ZERO          INTEGER       = 0         --包含受益份额为零 1是 0否
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
	                                 @IN_EXPORT_FLAG      INT= 0,       --导出标记：0非导出查询 1导出受益人信息
	                                 @IN_EXPORT_SUMMARY   NVARCHAR(900) = 0,  --导出备注
	                                 @IN_SUB_PRODUCT_ID   INT=0,        --子产品ID
	                                 @IN_CXSY_FLAG        INT=0,         --是否包括已过期受益人：1包括2不包括
	                                 @IN_GOV_PROV_REGIONAL NVARCHAR(60),
	                                 @IN_GOV_REGIONAL      NVARCHAR(60)
	 * 从CRM中搜索单个受益人
	 */
	List loadFromCRM(BenifitorVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @return
	 * @throws BusiException
	 * 从CRM中变更明细
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
	   搜素受益人                              
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List query(BenifitorVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 新增受益人信息
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
	                               @IN_BANK_ACCT_TYPE	VARCHAR(10)	银行账户类型(9920)
	                               @IN_BONUS_FLAG INT = 1 1、兑付　2、转份额 
	 * @param vo
	 * @throws BusiException
	 */
	void append(BenifitorVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 修改受益人信息
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
	                                @IN_BANK_ACCT_TYPE	VARCHAR(10)	银行账户类型(9920)
	                                @IN_BOUNS_FLAG INT = 1 1、兑付　2、转份额 
	 * @param vo
	 * @throws BusiException
	 */
	void save(BenifitorVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 修改受益人信息
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
	                                @IN_BANK_ACCT_TYPE	VARCHAR(10)	银行账户类型(9920)
	                                @IN_BOUNS_FLAG INT = 1 1、兑付　2、转份额 
	 * @param vo
	 * @throws BusiException
	 */
	void saveByCrm(BenifitorVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 删除受益人信息
	 * SP_DEL_TBENIFITOR @IN_SERIAL_NO INT,
	                     @IN_INPUT_MAN INT
	 * @param vo
	 * @throws BusiException
	 */
	void delete(BenifitorVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 	查询受益人账户 受益账户修改用
	 *  SP_QUERY_TBENIFITOR_MODIUNCHECK @IN_FUNCTION_ID INT,   --100帐号修改时调用返回受益人信息，@IN_CONTRACT_BH或@IN_CUST_NO必输入一项
	                                                                    --200审核时调用，返回修改未审核记录，@IN_CONTRACT_BH和@IN_CUST_NO无效
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
	 * 	查询受益人账户 受益账户修改用
	 *  SP_QUERY_TBENIFITOR_MODIUNCHECK @IN_FUNCTION_ID INT,   --100帐号修改时调用返回受益人信息，@IN_CONTRACT_BH或@IN_CUST_NO必输入一项
	                                                                    --200审核时调用，返回修改未审核记录，@IN_CONTRACT_BH和@IN_CUST_NO无效
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
	 * 受益账户转让
	 * SP_MODI_TBENIFITOR_BANK @IN_SERIAL_NO INT,
	                                     @IN_BANK_ID VARCHAR(10),
	                                     @IN_BANK_SUB_NAME VARCHAR(30),
	                                     @IN_BANK_ACCT VARCHAR(30),
	                                     @IN_INPUT_MAN INT,
	                                     @IN_ACCT_NAME VARCHAR(60) = NULL,
	                                     @IN_MODI_DATE INT=NULL
	                                     @IN_BANK_ACCT_TYPE	VARCHAR(10)	银行账户类型(9920)
	                                     @IN_BONUS_FLAG     INT = 1 1、兑付　2、转份额 
	 * @param vo
	 * @throws BusiException
	 */
	void save1(BenifitorVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询客户受益信息
	 * @author dingyj
	 * @since 2010-1-7
	 * @param vo
	 * @return list
	 * @throws BusiException
	 */
	List QueryBenifitor(BenifitorVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 受益人份额变更流水查询
	 * @author dingyj
	 * @since 2010-1-7
	 * @param vo
	 * @return list
	 * @throws BusiException
	 */
	List listChangeDetail(BenifitorVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 获得客户受益详细信息
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
	 * 修改受益期限、到期日期
	 * @param vo
	 * @throws Exception
	 */
	void modBenifitorValidPeriod(BenifitorVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询客户单个产品的受益份额
	 * @param cust_id
	 * @param prodct_id
	 * @return
	 * @throws Exception
	 */
	BigDecimal getTotalBenAmount(Integer cust_id, Integer product_id) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 受益人帐户修改审核
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
	 * 查找打印受权证书信息
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
	 * 修改 收益级别 PROV_LEVEL
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