package enfo.crm.intrust;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.PreContractVO;

public interface PreContractLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"	
	 * 增加预登记
	 * SP_ADD_TPRECONTRACT  @IN_PREPRODUCT_ID  INT,                    --预发行产品ID
							 @IN_CUST_ID        INT,                    --客户ID
							 @IN_PRE_MONEY      DECIMAL(16,3),          --预约金额
							 @IN_LINK_MAN       INT,                    --销售人员（联系人）
							 @IN_VALID_DAYS     INT,                    --预约有效天数                                     
							 @IN_PRE_TYPE       NVARCHAR(10),           --预约方式(1112)
							 @IN_SUMMARY        NVARCHAR(200),
							 @IN_PRE_NUM        INT,                    --预约份数
							 @IN_INPUT_MAN      INT,
							 @IN_PRE_DATE       INT,                    --预约日期
							 @IN_EXP_REG_DATE   INT = NULL,             --预计认购日期
							 @IN_CUST_SOURCE    NVARCHAR(10) = NULL,    --客户来源(1110)     
							                                 
							 @OUT_PRE_CODE      NVARCHAR(16) = NULL OUTPUT , --预约编号                                     
							 @OUT_SERIAL_NO     INT = NULL OUTPUT,
							 @IN_CHANNEL_TYPE   NVARCHAR(10) = NULL,    --渠道类别(5500)
							 
							 @IN_CHANNEL_FARE   DEC(16,3) = NULL,       --渠道费用
							 
							 @IN_PRE_LEVEL      NVARCHAR(10) = NULL,    --预约类别(1182)用于区分预约的级别
							 @IN_PRODUCT_ID     INT=0,                  --正式产品ID
							 @IN_SUB_PRODUCT_ID INT = 0                 --子产品ID
	 */
	Object[] append(PreContractVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 删除 预登记
	 * @param vo
	 * @throws Exception
	 */
	void delete(PreContractVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查找预登记
	 * SP_QUERY_TPRECONTRACT_LOAD @IN_SERIAL_NO INT
	 */
	List load(PreContractVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查找预登记
	 * SP_QUERY_TPRECONTRACT_LOAD @IN_SERIAL_NO INT
	 */
	List loadFromIntrust(PreContractVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 */
	List loadPreproduct(Integer preproduct_id) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 预约
	 * @param vo
	 * @throws Exception
	 */
	void save(PreContractVO vo) throws BusiException;

	//参数:产品ID$预约号$业务员
	/**
	 * @ejb.interface-method view-type = "local"
	 */
	void list() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	List query(PreContractVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 预约查询 移植到CRM 增加客户评级和客户群组的联动查询
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List query_crm(PreContractVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 预约分页查询功能
	 * @throws BusiException 
	 */
	IPageList preContract_page_query(PreContractVO vo, String[] totalColumn, int pageIndex, int pageSize)
			throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 预约查询 移植到CRM 增加客户评级和客户群组的联动查询
	 * @throws BusiException  
	 */
	IPageList preContract_page_query_crm(PreContractVO vo, String[] totalColumn, int pageIndex, int pageSize)
			throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	List queryAll(PreContractVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	IPageList queryListAll(PreContractVO vo, int page, int pagesize) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @param page
	 * @param pagesize
	 * @return
	 * @throws Exception
	 */
	IPageList queryAll(PreContractVO vo, int page, int pagesize) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param input_bookCode
	 * @param product_id
	 * @param cust_name
	 * @param card_id
	 * @param input_man
	 * @return
	 * @throws Exception
	 */
	List queryByCust(Integer input_bookCode, Integer product_id, String cust_name, String card_id, Integer input_man)
			throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	List queryPrecontractByCustID(PreContractVO vo) throws BusiException;

	boolean getNext() throws Exception;

	boolean getNext1() throws Exception;

	boolean getNext2() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 客户预登记管理
	 */
	void append_reginfo(PreContractVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 SP_MODI_TCUSTREGINFO @IN_CUST_ID           INTEGER,
	                                  @IN_REG_MONEY         DECIMAL(16,3),
	                                  @IN_RG_MONEY          DECIMAL(16,3),
	                                  @IN_LAST_RG_DATE      INTEGER,
	                                  @IN_LAST_PRODUCT_ID   INTEGER,
	                                  @IN_LAST_MONEY        DECIMAL(16,3),
	                                  @IN_SUMMARY           NVARCHAR(200),
	                                  @IN_INPUT_MAN         INTEGER,
	                                  @IN_REG_DATE          INTEGER      = NULL,
	                                  @IN_VALID_DAYS        INTEGER      = NULL,
	                                  @IN_INVEST_TYPE       NVARCHAR(80) = NULL,    
	                                  @IN_INVEST_TYPE_NAME  NVARCHAR(200)= NULL,    
	                                  @IN_CUST_SOURCE       NVARCHAR(10) = NULL,    
	                                  @IN_LINK_MAN   
	 **/
	void save_reginfo(PreContractVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * SP_DEL_TCUSTREGINFO @IN_CUST_ID   INT,
							@IN_INPUT_MAN INT
	 */
	void delete_reginfo(PreContractVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return
	 * @throws Exception
	 */
	List reginfo_list() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * SP_QUERY_TCUSTREGINFO @IN_BOOK_CODE INT,
										   @IN_CUST_ID   INT,
										   @IN_CUST_NAME VARCHAR(100),
										   @IN_INPUT_MAN INT,
										   @IN_FLAG      INT = 1
	 */
	List query_reginfo(PreContractVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 预登记查询过程 移到CRM 结合客户群组和客户评级功能
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List query_reginfo_crm(PreContractVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException 
	 * @throws Exception
	 */
	IPageList query_reginfo(PreContractVO vo, String[] totalColumn, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 预登记查询过程 移到CRM 结合客户群组和客户评级功能
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList query_reginfo_crm(PreContractVO vo, String[] totalColumn, int pageIndex, int pageSize)
			throws BusiException;

	boolean getRegInfo_cust_list() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * SP_QUERY_TCUSTREGINFO_COMM @IN_BOOK_CODE INT,
												@IN_CUST_ID   INT,
												@IN_CUST_NAME VARCHAR(100),
												@IN_FLAG      INT
	 */
	List query_refinfo_comm(PreContractVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	List getOutListReginfo(PreContractVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	List getOutExcel_presell(PreContractVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param product_id
	 * @return
	 * @throws Exception
	 */
	String queryPreNum(Integer preproduct_id, Integer product_id) throws Exception;

	/**
	 * 查询小额预约(300W以下)份数
	 * @ejb.interface-method view-type = "local"
	 * @param product_id
	 * @return
	 * @throws Exception
	 */
	String queryMicroPreNum(Integer product_id) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 修改缴款状态
	 * @param vo
	 * @throws Exception
	 */
	void modiMoneyStatus(PreContractVO vo) throws BusiException;

	/**
	 * 查询在售产品预约情况
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	List getProductPreList(PreContractVO vo) throws BusiException;

	/**
	 * 查询在售产品预约情况
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	IPageList getProductPreList(PreContractVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * 交银预约信息导入
	 * @ejb.interface-method view-type = "local"
	 * SP_IMPORT_PRECUSTOMERCON @IN_CUSTOMER_ID		NVARCHAR(10),		--总行客户号
	                            @IN_CUST_NAME       NVARCHAR(100), 		--客户名称
	                            @IN_SEX_NAME		NVARCHAR(10),		--性别
	                            @IN_MOBILE          NVARCHAR(100), 		--手机    
	                            @IN_PRE_MONEY		DEC(16,3),			--预约金额
	                            @IN_PRE_DATE		INTEGER,            --预约日期
	                            @IN_PRODUCT_NAME	NVARCHAR(100),      --预约产品名称
	                            @IN_PRODUCT_CODE	NVARCHAR(10),		--预约产品编号
	                            @IN_ADDRESS         NVARCHAR(60),  		--联系地址                                   
	                            @IN_EMAIL           NVARCHAR(60),  		--EMAIL
	                            @IN_INPUT_MAN		INTEGER				--导入人
	 * @param vo
	 * @throws BusiException
	 */
	void importPreCustCon(PreContractVO vo) throws BusiException;

	/**
	 * 交银预约信息导入查询
	 * @ejb.interface-method view-type = "local"
	 * SP_QUERY_PRECUSTOMERCON  @IN_CUST_NAME     NVARCHAR(60),
	                          	@IN_PRODUCT_IN    INTEGER,
	                          	@IN_PRODUCT_NAME  NVARCHAR(100),
	                          	@IN_INPUT_MAN     INTEGER
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List importByListPreContract(PreContractVO vo) throws BusiException;

	/**
	 * 交银预约信息导入查询
	 * @ejb.interface-method view-type = "local"
	 * SP_QUERY_PRECUSTOMERCON  @IN_CUST_NAME     NVARCHAR(60),
	                          	@IN_PRODUCT_IN    INTEGER,
	                          	@IN_PRODUCT_NAME  NVARCHAR(100),
	                          	@IN_INPUT_MAN     INTEGER
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	IPageList importListProcPagePreContract(PreContractVO vo, String[] totalColumn, int pageIndex, int pageSize)
			throws BusiException;

	/**
	 *  @ejb.interface-method view-type = "local"
	 * 预约转认购后，要修改临时预约表中的状态
	 * @param vo
	 * @throws BusiException
	 */
	void updateImportPreContract(PreContractVO vo) throws BusiException;

	/**
	 *  @ejb.interface-method view-type = "local"
	 * @param vo
	 * @throws BusiException
	 */
	List statPreContract2(PreContractVO vo) throws BusiException;

	/**
	 *  @ejb.interface-method view-type = "local"
	 * @param vo
	 * @throws BusiException
	 */
	void checkPreContract(PreContractVO vo) throws BusiException;

}