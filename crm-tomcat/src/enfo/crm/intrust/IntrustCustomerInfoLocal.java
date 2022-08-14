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
	 * 客户基本信息增加 SP_ADD_TCUSTOMERINFO
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
	 * @IN_VIP_CARD_ID VARCHAR(20) = NULL, -- VIP卡编号
	 * @IN_VIP_DATE INT = NULL, -- VIP发卡日期
	 * @IN_HGTZR_BH VARCHAR(20) = NULL, -- 合格投资人编号
	 * @IN_VOC_TYPE VARCHAR(10) = '' -- 个人职业/机构行业类别(1142/2142)
	 * @IN_CARD_VALID_DATE INT 客户身份证件有效期限8位日期表示
	 * @IN_COUNTRY VARCHAR(10) 客户国籍(9901)
	 * @IN_JG_CUST_TYPE VARCHAR(10) 机构客户类别(9921)，仅在CUST_TYPE=2机构时有效
	 * @IN_CONTACT_MAN VARCHAR(30) = NULL --机构客户联系人
	 * @IN_LINK_TYPE     INT,            -- 关联类型(1302)
	 * @IN_LINK_GD_MONEY DECIMAL(16,3),  -- 股东投资入股信托公司金额
	 */
	void append() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 客户信息合并处理 SP_HB_TCUSTOMERINFO
	 * 
	 * @IN_FROM_CUST_ID INT,
	 * @IN_TO_CUST_ID INT,
	 * @IN_INPUT_MAN INT,
	 * @IN_HB_RGMEONY INT = 1 --1时合并认购次数，金额，存量金额等字段值
	 */
	void hb() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 删除客户基本信息 SP_DEL_TCUSTOMERINFO
	 * 
	 * @IN_CUST_ID INT,
	 * @IN_INPUT_MAN INT
	 */
	void delete() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 客户信息查询_load一条记录 SP_QUERY_TCUSTOMERINFO_LOAD
	 * 
	 * @IN_CUST_ID INT
	 */
	void load() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 上面load内部调用
	 * 
	 * @return
	 * @throws Exception
	 */
	boolean getNextForLoad() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 修改客户基本信息 SP_MODI_TCUSTOMERINFO
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
	 * @IN_VIP_CARD_ID VARCHAR(20) = NULL, -- VIP卡编号
	 * @IN_VIP_DATE INT = NULL, -- VIP发卡日期
	 * @IN_HGTZR_BH VARCHAR(20) = NULL,
	 * @IN_VOC_TYPE VARCHAR(10) = '' -- 个人职业/机构行业类别(1142/2142)
	 * @IN_CARD_VALID_DATE INT 客户身份证件有效期限8位日期表示
	 * @IN_COUNTRY VARCHAR(10) 客户国籍(9901)
	 * @IN_JG_CUST_TYPE VARCHAR(10) 机构客户类别(9921)，仅在CUST_TYPE=2机构时有效
	 * @IN_CONTACT_MAN VARCHAR(30) = NULL --机构客户联系人
	 * @IN_LINK_TYPE     INT,            -- 关联类型(1302)
	 * @IN_LINK_GD_MONEY DECIMAL(16,3),  -- 股东投资入股信托公司金额
	 */
	void save() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 客户信息审核
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
	 * 参数：CUST_ID$CUST_NO$CUST_NAME$CHECK_FLAG
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
	 * 根据证件号码、客户编号搜索查询用 modi by jinxr 2007/9/13 增加CUST_NAME参数
	 * SP_QUERY_TCUSTOMERINFO_NO
	 * 
	 * @IN_BOOK_CODE INT,
	 * @IN_CUST_NO VARCHAR(8), -- 客户编号
	 * @IN_CARD_ID VARCHAR(20), -- 证件号码
	 * @IN_VIP_CARD_ID VARCHAR(20) = NULL, -- VIP卡编号
	 * @IN_HGTZR_BH VARCHAR(20) = NULL, -- 合格投资人编号
	 * @IN_CUST_NAME VARCHAR(80) = NULL, -- 客户名称
	 * @IN_IS_LINK INT --是否为关联方
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
	 * queryByCustNo查询结果list用
	 * 
	 * @add by jinxr 2007/9/13
	 */
	boolean getNextForList() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * update by tsg 2007-11-13
	 * 
	 * 查询有相同证件号码和名称的客户 SP_QUERY_TCUSTOMERINFO_REPEAT_CARD
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
	 * 上面queryRepeatCustomers的数据结果获取
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
	 * 信封打印用取数据
	 */
	boolean getCustPostNext() throws Exception;

	//	参数：CUST_ID$CUST_NO$CUST_NAME$CUST_SOURCE$CARD_TYPE$CARD_ID$TOUCH_TYPE$MIN_TIMES$MAX_TIMES
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
	 * @IN_ONLYEMAIL INT = NULL, --是否只返回有EMAIL的记录 1-是 0-否
	 * @IN_CUST_LEVEL VARCHAR(10) = NULL,
	 * @IN_MIN_TOTAL_MONEY MONEY = NULL, --认购金额下限
	 * @IN_MAX_TOTAL_MONEY MONEY = NULL, --认购金额上限
	 * @IN_ONLY_THISPRODUCT INT = NULL,
	 * @IN_ORDERBY VARCHAR(100) = NULL,
	 * @IN_BIRTHDAY INT = NULL,
	 * @IN_PRINT_DEPLOY_BILL INT = NULL,
	 * @IN_IS_LINK INT = NULL,
	 * @IN_PROV_LEVEL VARCHAR(10) = NULL,
	 * @IN_BEN_AMOUNT_MIN DECIMAL(16,3) = NULL,
	 * @IN_BEN_AMOUNT_MAX DECIMAL(16,3) = NULL,
	 * @IN_VIP_CARD_ID VARCHAR(20) = NULL, -- VIP卡编号
	 * @IN_HGTZR_BH VARCHAR(20) = NULL -- 合格投资人编号
	 * @IN_WTR_FLAG INTEGER = 0 --委托人标志
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
	 * SP_QUERY_TCUSTOMERINFO2 查询结果集用
	 */
	boolean getNextForQuery2() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 获得客户基本信息查询的结果集
	 * 添加此方法用于自定义显示列
	 * @throws Exception
	 */
	List getNextForQuery3() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 查询只取Email、MOBILE,BP和CUST_ID
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
	 * 根据客户经理查询客户信息
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
	 * 修改客户的客户经理
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
	 * 同步crm客户信息
	 * 
	 * @throws Exception
	 */
	void syncCrmCust() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * add by zhangmy 20100331
	 * 
	 * 修改客户的评级级别
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
	 * 导入客户信息功能
	 * @param vo
	 * @throws BusiException
	 */
	void importCustomer(CustomerInfoVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * add by qmh 2011-4-21
	 * 
	 * 合格投资人查询
	 * 
	 * SP_QUERY_QUALIFIED 
	 * @IN_BOOK_CODE        INTEGER,       --账套
	 * @IN_PRODUCT_ID       INTEGER,       --产品ID
	 * @IN_FLAG             INTEGER        --1.查自然人（300万以下个人），2.查合格投资人（300万以上个人及机构） 
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
	 * @return 返回 age。
	 */
	Integer getAge();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param age
	 *            要设置的 age。
	 */
	void setAge(Integer age);

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
	 * @return 返回 birthday。
	 */
	Integer getBirthday();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param birthday
	 *            要设置的 birthday。
	 */
	void setBirthday(Integer birthday);

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
	 * @return 返回 bp。
	 */
	String getBp();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param bp
	 *            要设置的 bp。
	 */
	void setBp(String bp);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 cancel_man。
	 */
	Integer getCancel_man();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param cancel_man
	 *            要设置的 cancel_man。
	 */
	void setCancel_man(Integer cancel_man);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 cancel_time。
	 */
	Integer getCancel_time();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param cancel_time
	 *            要设置的 cancel_time。
	 */
	void setCancel_time(Integer cancel_time);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 card_id。
	 */
	java.lang.String getCard_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param card_id
	 *            要设置的 card_id。
	 */
	void setCard_id(java.lang.String card_id);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 card_type。
	 */
	java.lang.String getCard_type();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param card_type
	 *            要设置的 card_type。
	 */
	void setCard_type(java.lang.String card_type);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 card_type_name。
	 */
	java.lang.String getCard_type_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param card_type_name
	 *            要设置的 card_type_name。
	 */
	void setCard_type_name(java.lang.String card_type_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 card_valid_date。
	 */
	Integer getCard_valid_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param card_valid_date
	 *            要设置的 card_valid_date。
	 */
	void setCard_valid_date(Integer card_valid_date);

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
	 * @return 返回 contact_man。
	 */
	String getContact_man();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param contact_man
	 *            要设置的 contact_man。
	 */
	void setContact_man(String contact_man);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 contract_bh。
	 */
	String getContract_bh();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param contract_bh
	 *            要设置的 contract_bh。
	 */
	void setContract_bh(String contract_bh);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 country。
	 */
	String getCountry();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param country
	 *            要设置的 country。
	 */
	void setCountry(String country);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 current_money。
	 */
	java.math.BigDecimal getCurrent_money();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param current_money
	 *            要设置的 current_money。
	 */
	void setCurrent_money(java.math.BigDecimal current_money);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 cust_id。
	 */
	java.lang.Integer getCust_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param cust_id
	 *            要设置的 cust_id。
	 */
	void setCust_id(java.lang.Integer cust_id);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 cust_level。
	 */
	java.lang.String getCust_level();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param cust_level
	 *            要设置的 cust_level。
	 */
	void setCust_level(java.lang.String cust_level);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 cust_level_name。
	 */
	java.lang.String getCust_level_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param cust_level_name
	 *            要设置的 cust_level_name。
	 */
	void setCust_level_name(java.lang.String cust_level_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 cust_name。
	 */
	java.lang.String getCust_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param cust_name
	 *            要设置的 cust_name。
	 */
	void setCust_name(java.lang.String cust_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 cust_no。
	 */
	java.lang.String getCust_no();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param cust_no
	 *            要设置的 cust_no。
	 */
	void setCust_no(java.lang.String cust_no);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 cust_source。
	 */
	java.lang.String getCust_source();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param cust_source
	 *            要设置的 cust_source。
	 */
	void setCust_source(java.lang.String cust_source);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 cust_source_name。
	 */
	java.lang.String getCust_source_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param cust_source_name
	 *            要设置的 cust_source_name。
	 */
	void setCust_source_name(java.lang.String cust_source_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 cust_tel。
	 */
	String getCust_tel();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param cust_tel
	 *            要设置的 cust_tel。
	 */
	void setCust_tel(String cust_tel);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 cust_type。
	 */
	java.lang.Integer getCust_type();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param cust_type
	 *            要设置的 cust_type。
	 */
	void setCust_type(java.lang.Integer cust_type);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 cust_type_name。
	 */
	java.lang.String getCust_type_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param cust_type_name
	 *            要设置的 cust_type_name。
	 */
	void setCust_type_name(java.lang.String cust_type_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 e_mail。
	 */
	String getE_mail();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param e_mail
	 *            要设置的 e_mail。
	 */
	void setE_mail(String e_mail);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 fact_controller。
	 */
	String getFact_controller();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param fact_controller
	 *            要设置的 fact_controller。
	 */
	void setFact_controller(String fact_controller);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 fax。
	 */
	String getFax();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param fax
	 *            要设置的 fax。
	 */
	void setFax(String fax);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 grade_level。
	 */
	String getGrade_level();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param grade_level
	 *            要设置的 grade_level。
	 */
	void setGrade_level(String grade_level);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 h_tel。
	 */
	String getH_tel();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param h_tel
	 *            要设置的 h_tel。
	 */
	void setH_tel(String h_tel);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 hgtzr_bh。
	 */
	String getHgtzr_bh();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param hgtzr_bh
	 *            要设置的 hgtzr_bh。
	 */
	void setHgtzr_bh(String hgtzr_bh);

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
	 * @return 返回 is_link。
	 */
	Integer getIs_link();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param is_link
	 *            要设置的 is_link。
	 */
	void setIs_link(Integer is_link);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 jg_cust_type。
	 */
	String getJg_cust_type();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param jg_cust_type
	 *            要设置的 jg_cust_type。
	 */
	void setJg_cust_type(String jg_cust_type);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 last_rg_date。
	 */
	Integer getLast_rg_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param last_rg_date
	 *            要设置的 last_rg_date。
	 */
	void setLast_rg_date(Integer last_rg_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 legal_address。
	 */
	String getLegal_address();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param legal_address
	 *            要设置的 legal_address。
	 */
	void setLegal_address(String legal_address);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 legal_man。
	 */
	String getLegal_man();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param legal_man
	 *            要设置的 legal_man。
	 */
	void setLegal_man(String legal_man);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 mobile。
	 */
	String getMobile();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param mobile
	 *            要设置的 mobile。
	 */
	void setMobile(String mobile);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 modi_flag。
	 */
	int getModi_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param modi_flag
	 *            要设置的 modi_flag。
	 */
	void setModi_flag(int modi_flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 modi_man。
	 */
	Integer getModi_man();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param modi_man
	 *            要设置的 modi_man。
	 */
	void setModi_man(Integer modi_man);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 modi_time。
	 */
	Integer getModi_time();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param modi_time
	 *            要设置的 modi_time。
	 */
	void setModi_time(Integer modi_time);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 money_source。
	 */
	String getMoney_source();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param money_source
	 *            要设置的 money_source。
	 */
	void setMoney_source(String money_source);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 o_tel。
	 */
	String getO_tel();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param o_tel
	 *            要设置的 o_tel。
	 */
	void setO_tel(String o_tel);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 post_address。
	 */
	String getPost_address();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param post_address
	 *            要设置的 post_address。
	 */
	void setPost_address(String post_address);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 post_address2。
	 */
	String getPost_address2();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param post_address2
	 *            要设置的 post_address2。
	 */
	void setPost_address2(String post_address2);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 post_code。
	 */
	String getPost_code();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param post_code
	 *            要设置的 post_code。
	 */
	void setPost_code(String post_code);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 post_code2。
	 */
	String getPost_code2();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param post_code2
	 *            要设置的 post_code2。
	 */
	void setPost_code2(String post_code2);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 print_deploy_bill。
	 */
	Integer getPrint_deploy_bill();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param print_deploy_bill
	 *            要设置的 print_deploy_bill。
	 */
	void setPrint_deploy_bill(Integer print_deploy_bill);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 print_post_info。
	 */
	Integer getPrint_post_info();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param print_post_info
	 *            要设置的 print_post_info。
	 */
	void setPrint_post_info(Integer print_post_info);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 product_id。
	 */
	Integer getProduct_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param product_id
	 *            要设置的 product_id。
	 */
	void setProduct_id(Integer product_id);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 prov_level。
	 */
	String getProv_level();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param prov_level
	 *            要设置的 prov_level。
	 */
	void setProv_level(String prov_level);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 rg_times。
	 */
	Integer getRg_times();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param rg_times
	 *            要设置的 rg_times。
	 */
	void setRg_times(Integer rg_times);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 service_man。
	 */
	Integer getService_man();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param service_man
	 *            要设置的 service_man。
	 */
	void setService_man(Integer service_man);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 sex。
	 */
	Integer getSex();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param sex
	 *            要设置的 sex。
	 */
	void setSex(Integer sex);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 sex_name。
	 */
	String getSex_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param sex_name
	 *            要设置的 sex_name。
	 */
	void setSex_name(String sex_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 status。
	 */
	java.lang.String getStatus();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param status
	 *            要设置的 status。
	 */
	void setStatus(java.lang.String status);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 status_name。
	 */
	java.lang.String getStatus_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param status_name
	 *            要设置的 status_name。
	 */
	void setStatus_name(java.lang.String status_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 summary。
	 */
	String getSummary();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param summary
	 *            要设置的 summary。
	 */
	void setSummary(String summary);

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
	 * @return 返回 touch_type。
	 */
	java.lang.String getTouch_type();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param touch_type
	 *            要设置的 touch_type。
	 */
	void setTouch_type(java.lang.String touch_type);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 touch_type_name。
	 */
	java.lang.String getTouch_type_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param touch_type_name
	 *            要设置的 touch_type_name。
	 */
	void setTouch_type_name(java.lang.String touch_type_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 vip_card_id。
	 */
	String getVip_card_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vip_card_id
	 *            要设置的 vip_card_id。
	 */
	void setVip_card_id(String vip_card_id);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 vip_date。
	 */
	Integer getVip_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vip_date
	 *            要设置的 vip_date。
	 */
	void setVip_date(Integer vip_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 voc_type。
	 */
	String getVoc_type();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param voc_type
	 *            要设置的 voc_type。
	 */
	void setVoc_type(String voc_type);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 voc_type_name。
	 */
	String getVoc_type_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param voc_type_name
	 *            要设置的 voc_type_name。
	 */
	void setVoc_type_name(String voc_type_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 wt_flag。
	 */
	java.lang.Integer getWt_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param wt_flag
	 *            要设置的 wt_flag。
	 */
	void setWt_flag(java.lang.Integer wt_flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 wt_flag_name。
	 */
	java.lang.String getWt_flag_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param wt_flag_name
	 *            要设置的 wt_flag_name。
	 */
	void setWt_flag_name(java.lang.String wt_flag_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 back_amount。
	 */
	java.math.BigDecimal getBack_amount();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 curr_to_amount。
	 */
	java.math.BigDecimal getCurr_to_amount();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 exchange_amount。
	 */
	java.math.BigDecimal getExchange_amount();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 last_product_name。
	 */
	String getLast_product_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param to_cust_id
	 *            要设置的 to_cust_id。
	 */
	void setTo_cust_id(Integer to_cust_id);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 link_gd_money。
	 */
	BigDecimal getLink_gd_money();

	/** 
	 * @ejb.interface-method view-type = "local"
	 * @param link_gd_money 要设置的 link_gd_money。
	 */
	void setLink_gd_money(BigDecimal link_gd_money);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 link_type。
	 */
	Integer getLink_type();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param link_type 要设置的 link_type。
	 */
	void setLink_type(Integer link_type);

	/**
	 *  @ejb.interface-method view-type = "local"
	 * @return 返回 grade_level_name。
	 */
	String getGrade_level_name();

	/**
	 *  @ejb.interface-method view-type = "local"
	 * @param grade_level_name 要设置的 grade_level_name。
	 */
	void setGrade_level_name(String grade_level_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 complete_flag。
	 */
	Integer getComplete_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param complete_flag 要设置的 complete_flag。
	 */
	void setComplete_flag(Integer complete_flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 ben_date。
	 */
	Integer getBen_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ben_date 要设置的 ben_date。
	 */
	void setBen_date(Integer ben_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 ben_end_date。
	 */
	Integer getBen_end_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ben_end_date 要设置的 ben_end_date。
	 */
	void setBen_end_date(Integer ben_end_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 flag。
	 */
	Integer getFlag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param flag 要设置的 flag。
	 */
	void setFlag(Integer flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 product_code。
	 */
	Integer getProduct_code();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param product_code 要设置的 product_code。
	 */
	void setProduct_code(Integer product_code);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 product_name。
	 */
	String getProduct_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param product_name 要设置的 product_name。
	 */
	void setProduct_name(String product_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 rg_money。
	 */
	BigDecimal getRg_money();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param rg_money 要设置的 rg_money。
	 */
	void setRg_money(BigDecimal rg_money);

}