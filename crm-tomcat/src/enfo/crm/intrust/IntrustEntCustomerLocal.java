package enfo.crm.intrust;

import java.math.BigDecimal;
import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiFullLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.EntRelationVO;

public interface IntrustEntCustomerLocal extends IBusiFullLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_DEL_TENTCUSTINFO
	 * 
	 * @IN_CUST_ID INT,
	 * @IN_INPUT_MAN INT
	 */
	void deleteEntCustomer() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * update by tsg 2007-11-02
	 * 
	 * 修改企业客户信息
	 * 
	 * SP_MODI_TENTCUSTINFO
	 * 
	 * @IN_CUST_ID INT,
	 * @IN_CUST_NAME VARCHAR(60),
	 * @IN_CUST_CODE VARCHAR(20),
	 * @IN_CARD_ID VARCHAR(20),
	 * @IN_BANK_ID VARCHAR(10),
	 * @IN_BANK_SUB_NAME VARCHAR(30),
	 * @IN_BANK_ACCT VARCHAR(30),
	 * @IN_REG_ADDRESS VARCHAR(60),
	 * @IN_LINK_MAN VARCHAR(10),
	 * @IN_ADDRESS VARCHAR(60),
	 * @IN_TELPHONE VARCHAR(20),
	 * @IN_FAX VARCHAR(20),
	 * @IN_EMAIL VARCHAR(30),
	 * @IN_ENT_TYPE VARCHAR(10),
	 * @IN_SUMMARY VARCHAR(200),
	 * @IN_REG_POSTCODE VARCHAR(6),
	 * @IN_POSTCODE VARCHAR(6),
	 * @IN_SEX INT,
	 * @IN_CUST_TYPE INT,
	 * @IN_CARD_CODE VARCHAR(60),
	 * @IN_CARD_TYPE VARCHAR(10),
	 * @IN_CREDIT_LEVEL VARCHAR(10),
	 * @IN_JT_FLAG INT,
	 * @IN_IS_LINK INT, -- 1 关联方 2 非关联方
	 * @IN_LINK_TYPE INT, -- 关联类型
	 * @IN_LINK_GD_MONEY DECIMAL(16,3), -- 股东投资入股信托公司金额
	 * @IN_INPUT_MAN INT
	 * @IN_VOC_TYPE VARCHAR(10) = '' -- 个人职业/机构行业类别(1142/2142)
	 * 
	 * @IN_WORKADDRESS VARCHAR(60), --目前办公地点
	 * @IN_LEGAL_PERSON VARCHAR(30), --法定代表人
	 * @IN_CONTRI_CAPITAL DEC(16,3), --实收资本
	 * @IN_REGIST_CAPITAL DEC(16,3), --注册资本
	 * @IN_TOTAL_SESET DEC(16,3), --资产总额
	 * @IN_INCORPORATION_DATE VARCHAR(30), --成立时间
	 * @IN_TAX_REGISTRATION_NO VARCHAR(30), --税务登记号
	 * @IN_NET_ASSET DEC(16,3), --净资产
	 * @IN_ANNUAL_INSPECTION INT, --是否年检:1是,2否
	 * @IN_FORMS_TYPE INT, --财务报表类型:1审计类,2非审计类
	 * @IN_SALES_INCOME DEC(16,3), --上年销售收入
	 * @IN_FUNDING_SOURCE VARCHAR(50), --项目来源
	 * @IN_STRONG_STOCKHOLDER VARCHAR(30), --企业实际控制人
	 * @IN_RATIO DEC(5,2), --占股比例
	 * @IN_REQUIRE INT, --企业需求 SELECT TYPE_VALUE FROM TDICTPARAM WHERE TYPE_ID =
	 *             5116
	 * @IN_ITEM_LOCATION VARCHAR(60), --项目所在区域
	 * @IN_SCALE_TERM VARCHAR(60), --项目规模和期限
	 * @IN_FINANCIAL_COST DEC(16,3), --财务成本
	 * @IN_BUSINESS_SCOPE VARCHAR(100),--主营业务
	 * @IN_GUARANTEE_MODE VARCHAR(100) --企业拟提供的担保方式
	 */
	void updateEntCustomer() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * update by tsg 2007-11-02 modify by zhangmy 20100304 增加部分企业信息字段
	 * 
	 * 添加一个新的企业客户 SP_ADD_TENTCUSTINFO
	 * 
	 * @IN_BOOK_CODE INT,
	 * @IN_CUST_NAME VARCHAR(60), -- 客户名称
	 * @IN_CUST_CODE VARCHAR(20), -- 客户代码
	 * @IN_CARD_ID VARCHAR(20), -- 贷款卡号
	 * @IN_BANK_ID VARCHAR(10), -- 银行编号
	 * @IN_BANK_SUB_NAME VARCHAR(30), -- 支行名称
	 * @IN_BANK_ACCT VARCHAR(30), -- 银行帐号
	 * @IN_REG_ADDRESS VARCHAR(60), -- 注册地址
	 * @IN_LINK_MAN VARCHAR(10), -- 联系人
	 * @IN_ADDRESS VARCHAR(60), -- 通讯地址
	 * @IN_TELPHONE VARCHAR(20), -- 联系电话
	 * @IN_FAX VARCHAR(20), -- 传真
	 * @IN_EMAIL VARCHAR(30), -- EMAIL
	 * @IN_ENT_TYPE VARCHAR(10), -- 企业性质
	 * @IN_SUMMARY VARCHAR(200), -- 备注
	 * @IN_REG_POSTCODE VARCHAR(6), -- 注册地邮政编码
	 * @IN_POSTCODE VARCHAR(6), -- 通讯地邮政编码
	 * @IN_SEX INT, -- 性别
	 * @IN_CUST_TYPE INT, -- 客户类别
	 * @IN_CARD_CODE VARCHAR(60), -- 证件号码
	 * @IN_CARD_TYPE VARCHAR(10), -- 证件类型
	 * @IN_CREDIT_LEVEL VARCHAR(10), -- 信用级别
	 * @IN_JT_FLAG INT, -- 集团标志
	 * @IN_IS_LINK INT, -- 1 关联方 2 非关联方
	 * @IN_LINK_TYPE INT, -- 关联类型
	 * @IN_LINK_GD_MONEY DECIMAL(16,3), -- 股东投资入股信托公司金额
	 * @IN_INPUT_MAN INT, -- 操作员
	 * @IN_VOC_TYPE VARCHAR(10) = '', -- 个人职业/机构行业类别(1142/2142)
	 * 
	 * @IN_WORKADDRESS VARCHAR(60), --目前办公地点
	 * @IN_LEGAL_PERSON VARCHAR(30), --法定代表人
	 * @IN_CONTRI_CAPITAL DEC(16,3), --实收资本
	 * @IN_REGIST_CAPITAL DEC(16,3), --注册资本
	 * @IN_TOTAL_SESET DEC(16,3), --资产总额
	 * @IN_INCORPORATION_DATE VARCHAR(30), --成立时间
	 * @IN_TAX_REGISTRATION_NO INT, --税务登记号
	 * @IN_NET_ASSET DEC(16,3), --净资产
	 * @IN_ANNUAL_INSPECTION INT, --是否年检:1是,2否
	 * @IN_FORMS_TYPE INT, --财务报表类型:1审计类,2非审计类
	 * @IN_SALES_INCOME DEC(16,3), --上年销售收入
	 * @IN_FUNDING_SOURCE VARCHAR(50), --项目来源
	 * @IN_STRONG_STOCKHOLDER VARCHAR(30), --企业实际控制人
	 * @IN_RATIO DEC(5,2), --占股比例
	 * @IN_REQUIRE INT, --企业需求 SELECT TYPE_VALUE FROM TDICTPARAM WHERE TYPE_ID =
	 *             5116
	 * @IN_ITEM_LOCATION VARCHAR(60), --项目所在区域
	 * @IN_SCALE_TERM VARCHAR(60), --项目规模和期限
	 * @IN_FINANCIAL_COST DEC(16,3), --财务成本
	 * @IN_BUSINESS_SCOPE VARCHAR(100),--主营业务
	 * @OUT_CUST_ID INT OUTPUT,-- 客户ID
	 * @IN_GUARANTEE_MODE VARCHAR(100) --企业拟提供的担保方式
	 * @IN_GOV_PROV_REGIONAL VARCHAR(10) = '',           --省级行政区域(9999)    20100714  luohh
	 * @IN_GOV_REGIONAL      VARCHAR(10) = '',            --行政区域(9999)        20100714  luohh 
	 * @IN_TYPE_CODE         VARCHAR(5)  =''              --行业类别明细代码      20100714  luohh 
	 */
	void addNewEntCustomer() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * update by tsg 2007-09-30
	 * 
	 * 查询企业信息列表
	 * 
	 * SP_QUERY_TENTCUSTINFO
	 * 
	 * @IN_BOOK_CODE INT,
	 * @IN_CUST_ID INT,
	 * @IN_CUST_TYPE VARCHAR(10),
	 * @IN_CUST_NAME VARCHAR(60),
	 * @IN_CUST_CODE VARCHAR(20)
	 * @IN_IS_LINK INT = 0
	 * @IN_JT_FLAG = NULL,
	 * @IN_CARD_CODE = NULL
	 */
	void list() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 */
	void load() throws Exception;

	/** 
	 * @ejb.interface-method view-type = "local"
	 */
	boolean getNext() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 基本信息查询，自定列的显示
	 * @return
	 * @throws Exception
	 */
	List getNextAllist() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @throws Exception
	 */
	void fixLogic() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 同步客户信息
	 * 
	 * @throws Exception
	 */
	void syncCrmEntCust() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 2008-09-25 YZJ 企业客户信息合并 存储过程名称：SP_HB_TENTCUSTINFO
	 * 
	 * @IN_FROM_CUST_ID INT 源客户ID
	 * @IN_TO_CUST_ID INT 目的客户ID
	 * @IN_INPUT_MAN INT 操作员
	 * 
	 * @throws Exception
	 */
	void unite() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @throws Exception
	 */
	void queryTentCustInfoDetail() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return
	 * @throws Exception
	 */
	boolean getNextTentCustInfoDetail() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * add by jinxr 2007/4/26
	 * 
	 * @throws Exception
	 */
	void queryentcustht() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * add by jinxr 2007/4/26
	 * 
	 * @return
	 * @throws Exception
	 */
	boolean getNextentcustht() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param request
	 * @param prefix
	 * @throws Exception
	 */
	void setProperties(javax.servlet.ServletRequest request, String prefix) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param value
	 * @return
	 * @throws Exception
	 */
	String listTreeBySql(String type_code, String value) throws Exception;

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 *新增客户股东关系
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	void addEntRelation(EntRelationVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * 删除客户股东关系
	 * @param vo
	 * @throws BusiException
	 */
	void delEntRelation(EntRelationVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * 查询客户股东关系
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList queryEntRelation(EntRelationVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 address。
	 */
	java.lang.String getAddress();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param address
	 *            要设置的 address。
	 */
	void setAddress(java.lang.String address);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 annual_inspection。
	 */
	Integer getAnnual_inspection();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param annual_inspection
	 *            要设置的 annual_inspection。
	 */
	void setAnnual_inspection(Integer annual_inspection);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 bank_acct。
	 */
	java.lang.String getBank_acct();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param bank_acct
	 *            要设置的 bank_acct。
	 */
	void setBank_acct(java.lang.String bank_acct);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 bank_id。
	 */
	java.lang.String getBank_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param bank_id
	 *            要设置的 bank_id。
	 */
	void setBank_id(java.lang.String bank_id);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 bank_name。
	 */
	java.lang.String getBank_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param bank_name
	 *            要设置的 bank_name。
	 */
	void setBank_name(java.lang.String bank_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 bank_sub_name。
	 */
	java.lang.String getBank_sub_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param bank_sub_name
	 *            要设置的 bank_sub_name。
	 */
	void setBank_sub_name(java.lang.String bank_sub_name);

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
	 * @return 返回 business_scope。
	 */
	String getBusiness_scope();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param business_scope
	 *            要设置的 business_scope。
	 */
	void setBusiness_scope(String business_scope);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 card_code。
	 */
	java.lang.String getCard_code();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param card_code
	 *            要设置的 card_code。
	 */
	void setCard_code(java.lang.String card_code);

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
	 * @return 返回 contri_capital。
	 */
	BigDecimal getContri_capital();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param contri_capital
	 *            要设置的 contri_capital。
	 */
	void setContri_capital(BigDecimal contri_capital);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 credit_level。
	 */
	java.lang.String getCredit_level();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param credit_level
	 *            要设置的 credit_level。
	 */
	void setCredit_level(java.lang.String credit_level);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 credit_level_name。
	 */
	java.lang.String getCredit_level_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param credit_level_name
	 *            要设置的 credit_level_name。
	 */
	void setCredit_level_name(java.lang.String credit_level_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 cust_code。
	 */
	java.lang.String getCust_code();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param cust_code
	 *            要设置的 cust_code。
	 */
	void setCust_code(java.lang.String cust_code);

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
	 * @return 返回 cust_type。
	 */
	Integer getCust_type();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param cust_type
	 *            要设置的 cust_type。
	 */
	void setCust_type(Integer cust_type);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 email。
	 */
	java.lang.String getEmail();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param email
	 *            要设置的 email。
	 */
	void setEmail(java.lang.String email);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 ent_type。
	 */
	java.lang.String getEnt_type();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ent_type
	 *            要设置的 ent_type。
	 */
	void setEnt_type(java.lang.String ent_type);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 ent_type_name。
	 */
	java.lang.String getEnt_type_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ent_type_name
	 *            要设置的 ent_type_name。
	 */
	void setEnt_type_name(java.lang.String ent_type_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 fax。
	 */
	java.lang.String getFax();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param fax
	 *            要设置的 fax。
	 */
	void setFax(java.lang.String fax);

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
	 * @return 返回 financial_cost。
	 */
	BigDecimal getFinancial_cost();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param financial_cost
	 *            要设置的 financial_cost。
	 */
	void setFinancial_cost(BigDecimal financial_cost);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 forms_type。
	 */
	Integer getForms_type();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param forms_type
	 *            要设置的 forms_type。
	 */
	void setForms_type(Integer forms_type);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 from_cust_id。
	 */
	Integer getFrom_cust_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param from_cust_id
	 *            要设置的 from_cust_id。
	 */
	void setFrom_cust_id(Integer from_cust_id);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 funding_source。
	 */
	String getFunding_source();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param funding_source
	 *            要设置的 funding_source。
	 */
	void setFunding_source(String funding_source);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 guarantee_mode。
	 */
	String getGuarantee_mode();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param guarantee_mode
	 *            要设置的 guarantee_mode。
	 */
	void setGuarantee_mode(String guarantee_mode);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 incorporation_date。
	 */
	String getIncorporation_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param incorporation_date
	 *            要设置的 incorporation_date。
	 */
	void setIncorporation_date(String incorporation_date);

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
	 * @return 返回 item_location。
	 */
	String getItem_location();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param item_location
	 *            要设置的 item_location。
	 */
	void setItem_location(String item_location);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 jt_cust_id。
	 */
	java.lang.Integer getJt_cust_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param jt_cust_id
	 *            要设置的 jt_cust_id。
	 */
	void setJt_cust_id(java.lang.Integer jt_cust_id);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 jt_flag。
	 */
	Integer getJt_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param jt_flag
	 *            要设置的 jt_flag。
	 */
	void setJt_flag(Integer jt_flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 last_modi_time。
	 */
	java.sql.Timestamp getLast_modi_time();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param last_modi_time
	 *            要设置的 last_modi_time。
	 */
	void setLast_modi_time(java.sql.Timestamp last_modi_time);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 legal_person。
	 */
	String getLegal_person();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param legal_person
	 *            要设置的 legal_person。
	 */
	void setLegal_person(String legal_person);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 link_gd_money。
	 */
	BigDecimal getLink_gd_money();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param link_gd_money
	 *            要设置的 link_gd_money。
	 */
	void setLink_gd_money(BigDecimal link_gd_money);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 link_man。
	 */
	java.lang.String getLink_man();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param link_man
	 *            要设置的 link_man。
	 */
	void setLink_man(java.lang.String link_man);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 link_type。
	 */
	java.lang.Integer getLink_type();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param link_type
	 *            要设置的 link_type。
	 */
	void setLink_type(java.lang.Integer link_type);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 modi_man。
	 */
	java.lang.Integer getModi_man();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param modi_man
	 *            要设置的 modi_man。
	 */
	void setModi_man(java.lang.Integer modi_man);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 net_asset。
	 */
	BigDecimal getNet_asset();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param net_asset
	 *            要设置的 net_asset。
	 */
	void setNet_asset(BigDecimal net_asset);

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
	 * @return 返回 postcode。
	 */
	java.lang.String getPostcode();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param postcode
	 *            要设置的 postcode。
	 */
	void setPostcode(java.lang.String postcode);

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
	 * @return 返回 product_name。
	 */
	String getProduct_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param product_name
	 *            要设置的 product_name。
	 */
	void setProduct_name(String product_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 ratio。
	 */
	BigDecimal getRatio();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param ratio
	 *            要设置的 ratio。
	 */
	void setRatio(BigDecimal ratio);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 reg_address。
	 */
	java.lang.String getReg_address();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param reg_address
	 *            要设置的 reg_address。
	 */
	void setReg_address(java.lang.String reg_address);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 reg_postcode。
	 */
	java.lang.String getReg_postcode();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param reg_postcode
	 *            要设置的 reg_postcode。
	 */
	void setReg_postcode(java.lang.String reg_postcode);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 regist_capital。
	 */
	BigDecimal getRegist_capital();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param regist_capital
	 *            要设置的 regist_capital。
	 */
	void setRegist_capital(BigDecimal regist_capital);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 require。
	 */
	Integer getRequire();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param require
	 *            要设置的 require。
	 */
	void setRequire(Integer require);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 sales_income。
	 */
	BigDecimal getSales_income();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param sales_income
	 *            要设置的 sales_income。
	 */
	void setSales_income(BigDecimal sales_income);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 scale_term。
	 */
	String getScale_term();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param scale_term
	 *            要设置的 scale_term。
	 */
	void setScale_term(String scale_term);

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
	 * @return 返回 strong_stockholder。
	 */
	String getStrong_stockholder();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param strong_stockholder
	 *            要设置的 strong_stockholder。
	 */
	void setStrong_stockholder(String strong_stockholder);

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
	 * @return 返回 tax_registration_no。
	 */
	String getTax_registration_no();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param tax_registration_no
	 *            要设置的 tax_registration_no。
	 */
	void setTax_registration_no(String tax_registration_no);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 telphone。
	 */
	java.lang.String getTelphone();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param telphone
	 *            要设置的 telphone。
	 */
	void setTelphone(java.lang.String telphone);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 to_cust_id。
	 */
	Integer getTo_cust_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param to_cust_id
	 *            要设置的 to_cust_id。
	 */
	void setTo_cust_id(Integer to_cust_id);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 total_seset。
	 */
	BigDecimal getTotal_seset();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param total_seset
	 *            要设置的 total_seset。
	 */
	void setTotal_seset(BigDecimal total_seset);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 voc_type。
	 */
	java.lang.String getVoc_type();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param voc_type
	 *            要设置的 voc_type。
	 */
	void setVoc_type(java.lang.String voc_type);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 voc_type_name。
	 */
	java.lang.String getVoc_type_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param voc_type_name
	 *            要设置的 voc_type_name。
	 */
	void setVoc_type_name(java.lang.String voc_type_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 workaddress。
	 */
	String getWorkaddress();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param workaddress
	 *            要设置的 workaddress。
	 */
	void setWorkaddress(String workaddress);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 busi_name。
	 */
	String getBusi_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 contract_id。
	 */
	Integer getContract_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 contract_sub_bh。
	 */
	String getContract_sub_bh();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 cw_money。
	 */
	java.math.BigDecimal getCw_money();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 end_date。
	 */
	Integer getEnd_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 ht_money。
	 */
	java.math.BigDecimal getHt_money();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 r_type_name。
	 */
	String getR_type_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 start_date。
	 */
	Integer getStart_date();

	/**
	 *  @ejb.interface-method view-type = "local"
	 * @return 返回 gov_prov_regioal。
	 */
	String getGov_prov_regioal();

	/**
	 *  @ejb.interface-method view-type = "local"
	 * @param gov_prov_regioal 要设置的 gov_prov_regioal。
	 */
	void setGov_prov_regioal(String gov_prov_regioal);

	/**
	 *  @ejb.interface-method view-type = "local"
	 * @return 返回 gov_regioal。
	 */
	String getGov_regioal();

	/**
	 *  @ejb.interface-method view-type = "local"
	 * @param gov_regioal 要设置的 gov_regioal。
	 */
	void setGov_regioal(String gov_regioal);

	/**
	 *  @ejb.interface-method view-type = "local"
	 * @return 返回 type_code。
	 */
	String getType_code();

	/**
	 *  @ejb.interface-method view-type = "local"
	 * @param type_code 要设置的 type_code。
	 */
	void setType_code(String type_code);

	/**
	 *   @ejb.interface-method view-type = "local"
	 * @return 返回 type_name。
	 */
	String getType_name();

	/**
	 *   @ejb.interface-method view-type = "local"
	 * @param type_name 要设置的 type_name。
	 */
	void setType_name(String type_name);

	/**
	 *    @ejb.interface-method view-type = "local"
	 * @return 返回 gov_prov_regioal_name。
	 */
	String getGov_prov_regioal_name();

	/**
	 *    @ejb.interface-method view-type = "local"
	 * @param gov_prov_regioal_name 要设置的 gov_prov_regioal_name。
	 */
	void setGov_prov_regioal_name(String gov_prov_regioal_name);

	/**
	 *    @ejb.interface-method view-type = "local"
	 * @return 返回 gov_regioal_name。
	 */
	String getGov_regioal_name();

	/**
	 *    @ejb.interface-method view-type = "local"
	 * @param gov_regioal_name 要设置的 gov_regioal_name。
	 */
	void setGov_regioal_name(String gov_regioal_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 country。
	 */
	String getCountry();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param country 要设置的 country。
	 */
	void setCountry(String country);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 country_name。
	 */
	String getCountry_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param country_name 要设置的 country_name。
	 */
	void setCountry_name(String country_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 jkr_type。
	 */
	String getJkr_type();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param jkr_type 要设置的 jkr_type。
	 */
	void setJkr_type(String jkr_type);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 jkr_type_name。
	 */
	String getJkr_type_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param jkr_type_name 要设置的 jkr_type_name。
	 */
	void setJkr_type_name(String jkr_type_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 jkr_type2。
	 */
	String getJkr_type2();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param jkr_type2 要设置的 jkr_type2。
	 */
	void setJkr_type2(String jkr_type2);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 jkr_type2_name。
	 */
	String getJkr_type2_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param jkr_type2_name 要设置的 jkr_type2_name。
	 */
	void setJkr_type2_name(String jkr_type2_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 country_tax_no。
	 */
	String getCountry_tax_no();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param country_tax_no 要设置的 country_tax_no。
	 */
	void setCountry_tax_no(String country_tax_no);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 land_tax_no。
	 */
	String getLand_tax_no();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param land_tax_no 要设置的 land_tax_no。
	 */
	void setLand_tax_no(String land_tax_no);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 license_end_date。
	 */
	Integer getLicense_end_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param license_end_date 要设置的 license_end_date。
	 */
	void setLicense_end_date(Integer license_end_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 reg_date。
	 */
	Integer getReg_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param reg_date 要设置的 reg_date。
	 */
	void setReg_date(Integer reg_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 reg_no。
	 */
	String getReg_no();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param reg_no 要设置的 reg_no。
	 */
	void setReg_no(String reg_no);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 valid_period。
	 */
	Integer getValid_period();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param valid_period 要设置的 valid_period。
	 */
	void setValid_period(Integer valid_period);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 period_unit。
	 */
	Integer getPeriod_unit();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param period_unit 要设置的 period_unit。
	 */
	void setPeriod_unit(Integer period_unit);

	/**
	 *  @ejb.interface-method view-type = "local"
	 * @return Returns the public_flag.
	 */
	Integer getPublic_flag();

	/**
	 *  @ejb.interface-method view-type = "local"
	 * @param public_flag The public_flag to set.
	 */
	void setPublic_flag(Integer public_flag);

	/**
	 *  @ejb.interface-method view-type = "local"
	 * @return Returns the seid_id.
	 */
	String getSeid_id();

	/**
	 *  @ejb.interface-method view-type = "local"
	 * @param seid_id The seid_id to set.
	 */
	void setSeid_id(String seid_id);

	/**
	 *  @ejb.interface-method view-type = "local"
	 * @return Returns the stock_code.
	 */
	String getStock_code();

	/**
	 *  @ejb.interface-method view-type = "local"
	 * @param stock_code The stock_code to set.
	 */
	void setStock_code(String stock_code);

	/**
	 *  @ejb.interface-method view-type = "local"
	 * @return Returns the stock_name.
	 */
	String getStock_name();

	/**
	 *  @ejb.interface-method view-type = "local"
	 * @param stock_name The stock_name to set.
	 */
	void setStock_name(String stock_name);

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
	 * @return 返回 fz_total。
	 */
	String getFz_total();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param fz_total 要设置的 fz_total。
	 */
	void setFz_total(String fz_total);

}