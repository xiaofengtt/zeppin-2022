package enfo.crm.customer;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.fileupload.DestinationTableVo;
import enfo.crm.vo.AmCustInfoVO;
import enfo.crm.vo.CustLevelVO;
import enfo.crm.vo.CustomerInfoVO;
import enfo.crm.vo.CustomerVO;

public interface CustomerLocal extends IBusiExLocal{

	/**
	 * @ejb.interface-method view-type = "local"
	 * 客户基本信息增加
	 * @param vo 
	 * @throws BusiException          
	 */
	Integer append(CustomerVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 客户基本信息增加 非委托人 无分布式同步
	 * @param vo 
	 * @throws BusiException          
	 */
	Integer append2(CustomerVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 客户基本信息增加 非委托人 无分布式同步
	 * @param vo 
	 * @throws BusiException          
	 */
	Integer append_jh(CustomerVO vo) throws BusiException;

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
	 * @IN_LINK_TYPE INT, -- 关联类型(1302)
	 * @IN_LINK_GD_MONEY DECIMAL(16,3), -- 股东投资入股信托公司金额
	 */
	void save_jh(CustomerVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 添加客户基本信息
	 * @param procSql
	 * @param params
	 * @param outParamPos
	 * @param outParamType
	 * @return ret
	 */
	Object cudProcAdd(String procSql, Object[] params, int outParamPos, int outParamType, InputStream inputStream)
			throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 修改客户基本信息
	 * @param procSql
	 * @param params
	 * @param outParamPos
	 * @param outParamType
	 * @return ret
	 */
	Object cudProcEdit(String procSql, Object[] params, InputStream inputStream) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 修改客户基本信息
	 * @param procSql
	 * @param params
	 * @param outParamPos
	 * @param outParamType
	 * @return ret
	 */
	Object cudProcEdit1(CustomerVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 客户信息合并处理
	 * @param vo
	 * @throws BusiException
	 */
	void unite(CustomerVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 用于认购,申购等合同录入时候 选择客户的查询用
	 * SP_QUERY_TCustomers_LOAD_2   @IN_CUST_ID 		INT,
								  @IN_CUST_NO    	NVARCHAR(8),
								  @IN_CUST_NAME  	NVARCHAR(80),
								  @IN_VIP_CARD_ID	NVARCHAR(20)   = NULL,  --VIP卡编号
								  @IN_CARD_ID       NVARCHAR(20),
								  @IN_HGTZR_BH      NVARCHAR(20)   = NULL,  --合格投资人编号
								  @IN_IS_DEAL    	INT 		   =0,
								  @IN_IS_LINK       INT            = NULL,
								  @IN_INPUT_MAN 	INT 		   = NULL
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List queryAllCustomer(CustomerVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询证件快到期的客户信息（分页显示）
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @throws BusiException
	 * @return rsList
	 */
	IPageList queryCardValidDate(CustomerVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 删除客户基本信息
	 * @param vo 
	 * @throws BusiException
	 */
	void delete(CustomerVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 修改客户基本信息
	 * @param vo
	 * @throws BusiException
	 */
	void modify(CustomerVO vo) throws BusiException;
	void modify_m(CustomerVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 修改客户基本信息 无分布式同步
	 * @param vo
	 * @throws BusiException
	 */
	void modify2(CustomerVO vo) throws BusiException;
	void modify2_m(CustomerVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 根据证件号码、客户编号搜索查询用
	 * @param vo
	 * @throws BusiException
	 * @return list
	 */
	List queryByCustNo(CustomerVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询客户资料的基本信息——分页显示
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @param sQuery
	 * @throws BusiException
	 * @return rsList
	*/
	IPageList listProcAll(CustomerVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询新增客户资料的基本信息——分页显示
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @throws BusiException
	 * @return rsList
	 */
	IPageList listNewCustAll(CustomerVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询客户资料的基本信息——列表显示
	 * @param vo
	 * @throws BusiException
	 * @return list
	 */
	List listProcAll(CustomerVO vo) throws BusiException;
	List listProcAll_m(CustomerVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 获得客户图片
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	InputStream getInputStream(CustomerVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询客户资料的基本信息——分页显示(多参数)
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @param sQuery
	 * @throws BusiException
	 * @return rsList
	 */
	IPageList listProcAllExt(CustomerVO vo, int pageIndex, int pageSize) throws BusiException;
	
	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询客户资料的基本信息——分页显示(多参数)
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @param sQuery
	 * @throws BusiException
	 * @return rsList
	 */
	IPageList listProcAllExt_C(CustomerVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @param totalValue
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList listProcAllExt(CustomerVO vo, String[] totalValue, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询客户资料的基本信息——列表显示(多参数)
	 * @param vo
	 * @return list
	 * @throws BusiException
	 */
	List listProcAllExt(CustomerVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 获得多参数查询参数
	 * @param vo
	 * @return
	 */
	Object[] getParmas(CustomerVO vo);

	/**
	 * @ejb.interface-method view-type = "local"
	 * 客户信息查询单条记录
	 * @param vo(cust_id, input_man)
	 * @throws BusiException
	 * @return list
	 */
	List listByControl(CustomerVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询客户信息返回多项值  
	 * @param vo
	 * @throws BusiException
	 * @return list
	 */
	List listCustomerLoad(CustomerVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 客户合并查询
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @throws BusiException
	 * @return list
	 */
	IPageList queryRepeatCustomers(CustomerVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @throws Exception
	 */
	void addCustLevel(CustLevelVO vo) throws Exception;


	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @throws Exception
	 */
	void delCustLevel(CustLevelVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local" 
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList listCustLevel(CustLevelVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 导入功能
	 * @param vo
	 * @throws BusiException
	 */
	void importCustomer(CustomerVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList getCustomerMaintenanceRecord(CustomerVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 */
	List getCustomerMaintenanceRecord(CustomerVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * update by guifeng 2008-08-25 修改客户信息 SP_AML_MODI_CUST
	 * @IN_CUST_ID
	 * @IN_POST_ADDRESS
	 * @IN_CUST_TEL
	 * @IN_POST_ADDRESS2
	 * @IN_CARD_TYPE
	 * @IN_CARD_ID
	 * @IN_VOC_TYPE
	 * @IN_CARD_VALID_DATE
	 * @IN_COUNTRY
	 * @IN_JG_CUST_TYPE
	 * @IN_INPUT_MAN,
	 */
	void modi2(AmCustInfoVO amvo, CustomerInfoVO vo) throws Exception;

	//按年龄来统计 1.购买金额
	/**
	 * @ejb.interface-method view-type = "local"
	 */
	List queryChart(Integer product_id, String cust_id, int flag, String arrayField) throws Exception;

	//由系统来自定规则
	/**
	 * @ejb.interface-method view-type = "local"
	 */
	List queryChart2(Integer product_id, String cust_id, int x_flag, int y_flag, int cell_flag, Integer cell_id)
			throws BusiException;

	//按购买金额来统计 个数
	/**
	 * @ejb.interface-method view-type = "local"
	 */
	List queryChart3(Integer product_id, String cust_id, int flag, String arrayField) throws Exception;

	//由系统来自定规则--曲线
	/**
	 * @ejb.interface-method view-type = "local"
	 */
	List queryChart4(Integer product_id, String cust_id, int query_flag, int table_flag, Integer start_date,
			Integer end_date) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 */
	IPageList queryImportData(DestinationTableVo vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 */
	void prepareImportData(DestinationTableVo vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 */
	void confirmImportData(DestinationTableVo vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 */
	void deleteImportData() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 修改客户经理
	 * @param vo
	 * @throws BusiException
	 */
	void modiManager(CustomerVO vo) throws BusiException;

	/**
	 * 快速检索--分页
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList quickSearch(CustomerVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * 快速检索
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	List quickSearchByAll(CustomerVO vo) throws BusiException;

	/**
	 * 查询修改记录
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList listCustUpdateProcPage(CustomerVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * 查询客户修改记录
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList listCustUpdateProcPage(CustomerVO vo, Integer check_flag, int pageIndex, int pageSize)
			throws BusiException;

	/**
	 * 用于验证客户
	 * @ejb.interface-method view-type = "local"
	 * @return
	 * @throws BusiException
	 */
	String listByAll(int cust_type) throws BusiException;

	/**
	 * 用于验证客户
	 * @ejb.interface-method view-type = "local"
	 * @return
	 * @throws BusiException
	 */
	List listSameNameCustomers(String custName) throws BusiException;

	/**
	 * 通过客户ID查客户证件的核查结果
	 * @ejb.interface-method view-type = "local"
	 * @param cust_id
	 * @return
	 * @throws BusiException
	 */
	List listCustCardInfo(Integer cust_id) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 修改客户基本信息-读卡后的修改
	 * @param vo
	 * @throws BusiException
	 */
	void modify3(CustomerVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * * 更新证件审核信息
	 * @param name 证件名 ，id 证件号码,state证件审核状态代码 ,summary,证件审核描述
	 * @throws BusiException
	 */
	void updateCheckStatus(String name, String id, String state, String summary) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询客户投诉信息
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList getCustomerComplaint(Object[] params, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 核查客户投诉信息
	 * @param vo
	 * @throws BusiException
	 */
	void updateCustomerComplaint(CustomerVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 增加客户投诉信息
	 * @param vo
	 * @throws BusiException
	 */
	void addCustomerComplaint(CustomerVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 增加邮件群发信息
	 * @param vo
	 * @throws BusiException
	 */
	void addEmail(Integer cust_id, String Email, String title, String content, Integer input_man) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询邮件群发信息
	 * @param vo
	 * @throws BusiException
	 */
	List queryEmail(Integer cust_id, Integer send_flag) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 修改邮件群发发送状态
	 * @param vo
	 * @throws BusiException
	 */
	void modiEmail(Integer cust_id, Integer input_man) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 审核客户信息修改
	 * @param vo
	 * @throws BusiException
	 */
	void checkCustMsg(Integer cust_id, Integer check_flag, Integer input_man) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询已注销客户信息
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList getDelCustomer(Object[] params, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 恢复已注销客户
	 * @param 
	 * @throws BusiException
	 */
	boolean modiDelCustomer(Integer cust_id, Integer input_man) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询客户各方面的信息。flag: 0基本信息1客户现有合同2客户已结束合同3客户其他合同4客户受益信息
	 * @throws BusiException
	 */
	List queryCustAllInfo(Integer cust_id, Integer flag, Integer input_man) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 新添一条客户合并记录，等待审核
	 * @throws BusiException
	 */
	void merge(Integer from_cust_id, Integer to_cust_id, Integer input_man) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 审核一条客户合并记录。check_flag: 2审核通过 3审核否决
	 * @throws BusiException
	 */
	void checkMerge(Integer serial_no, Integer check_flag, Integer input_man) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 认为客户合并失误，恢复合并涉及的数据为合并前的状态 
	 * @throws BusiException
	 */
	void recoverMerge(Integer serial_no, Integer input_man) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询已注销客户信息
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList listMerge(CustomerVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 更新机构客户的相关证件信息
	 * @throws BusiException
	 */
	void updateEntCustInfo(CustomerVO vo) throws BusiException;
	void updateEntCustInfo_m(CustomerVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询机构客户的相关证件信息
	 * @throws BusiException
	 */
	List loadEntCustInfo(CustomerVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 更改客户的真实性标记
	 * @throws BusiException
	 */
	void modiCustTrueFlag(CustomerVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 批量修改客户信息
	 * @throws BusiException
	 */
	void batchModiCustInfo(CustomerVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 修改客户联系信息
	 * @throws BusiException
	 */
	void maintainCustContactInfo(CustomerVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * @throws BusiException
	 */
	List getCustContactInfo(CustomerVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 修改保存客户信托财产调查表
	 * @param vo
	 * @throws BusiException
	 */
	void modiPropertySurvey(enfo.crm.vo.PropertySurveyVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 根据客户id查询失败
	 * @param vo
	 * @throws BusiException
	 * @return list
	 */
	List querySurveyByCustId(Integer cust_id) throws BusiException;

	/**
	 * 修改隐藏号码
	 * @param vo
	 * @throws BusiException
	 */
	void modiCustYc_Mobile(Integer cust_id) throws BusiException;
	
	/**
	 * 查询客户信息
	 * @param cust_id
	 * @return
	 * @throws BusiException
	 */
	Map queryCustomersByCustId(Integer cust_id) throws BusiException;
	
	/**
	 * 变更手机
	 * @param vo
	 * @throws BusiException
	 */
	void modiCustomerMobile(CustomerVO vo) throws BusiException;
	
	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询客户资料的基本信息——分页显示(多参数)
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @param sQuery
	 * @throws BusiException
	 * @return rsList
	 */
	public IPageList SearchByCustomers(CustomerVO vo,int pageIndex,int pageSize) throws BusiException;	
	
	
	/** 
	 */
	public void addGifi(enfo.crm.vo.CustomerVO vo) throws java.lang.Exception;

	/** 
	 */
	public java.lang.Integer addGifiMove(enfo.crm.vo.CustomerVO vo)
			throws java.lang.Exception;

	/** 
	 */
	public void addGifitoCust(enfo.crm.vo.CustomerVO vo)
			throws java.lang.Exception;
	
	/** 
	 */
	public void modiCustLevel(enfo.crm.vo.CustLevelVO vo)
			throws java.lang.Exception;

	/** 
	 */
	public void updateGifi(enfo.crm.vo.CustomerVO vo)
			throws java.lang.Exception;

	/** 
	 */
	public void modiGiftMove(enfo.crm.vo.CustomerVO vo)
			throws java.lang.Exception;
	
	/** 
	 */
	public enfo.crm.dao.IPageList listGifiAll(enfo.crm.vo.CustomerVO vo,
			int pageIndex, int pageSize) throws enfo.crm.dao.BusiException;

	/** 
	 */
	public java.util.List listGifiAll(enfo.crm.vo.CustomerVO vo)
			throws enfo.crm.dao.BusiException;

	/** 
	 */
	public java.util.List listGifimoveoutAll(enfo.crm.vo.CustomerVO vo)
			throws enfo.crm.dao.BusiException;

	/** 
	 */
	public java.util.List listGifiToCust(enfo.crm.vo.CustomerVO vo)
			throws enfo.crm.dao.BusiException;

	/** 
	 */
	public void checkGiftMoveout(enfo.crm.vo.CustomerVO vo)
			throws java.lang.Exception;

	/** 
	 */
	public enfo.crm.dao.IPageList listGifimoveoutAll(enfo.crm.vo.CustomerVO vo,
			int pageIndex, int pageSize) throws enfo.crm.dao.BusiException;

	/** 
	 */
	public enfo.crm.dao.IPageList listGifiPutin(enfo.crm.vo.CustomerVO vo,
			int pageIndex, int pageSize) throws enfo.crm.dao.BusiException;
	
	/** 
	 */
	public enfo.crm.dao.IPageList getlistGainCust(enfo.crm.vo.CustomerVO vo,
			java.lang.String[] totalValue, int pageIndex, int pageSize)
			throws enfo.crm.dao.BusiException;
	/** 
	 */
	public void deleteGifi(enfo.crm.vo.CustomerVO vo)
			throws java.lang.Exception;

	/** 
	 */
	public void deleteGifimove(enfo.crm.vo.CustomerVO vo)
			throws java.lang.Exception;

	/** 
	 */
	public java.util.List queryCustByMobile(enfo.crm.vo.CustomerVO vo)
			throws enfo.crm.dao.BusiException;
	
}