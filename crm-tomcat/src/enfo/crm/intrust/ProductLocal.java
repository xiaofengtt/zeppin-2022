package enfo.crm.intrust;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.GainTotalVO;
import enfo.crm.vo.ProductVO;

public interface ProductLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 *             SP_QUERY_TPRODUCT
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
	 * @IN_AMOUNT_MAX DECIMAL(16,3) = NULL,
	 * @IN_ADMIN_MANAGER INT = 0 -- 执行经理
	 * @IN_INTRUST_FLAG3 INT = NULL, --设立方式：1私募2公募
	 * @IN_INTRUST_FLAG4 INT = NULL, --信托目的：1私益2公益
	 * @IN_EXP_RATE DECIMAL(5,4) = NULL, --预期收益率，返回该值在收益率区间的
	 * @IN_FACT_MONEY DECIMAL(16,3) = NULL,--产品发行金额，返回大于等于该值的
	 * @IN_FACT_MONEY 删除 与 产品规模 重复
	 * @IN_CHECK_FLAG INT, --是否审核
	 * @IN_VALID_PERIOD1 INT, --产品期限，输入以天为单位，0为无期限，负数为所有
	 * @IN_VALID_PERIOD2 INT, --产品期限，输入以天为单位，0为无期限，负数为所有
	 * @IN_FPFS NVARCHAR(10), --受托人报酬提取方式1105
	 * @IN_OPEN_FLAG INT --发行方式 1开放式2封闭式
	 */
	IPageList productList(ProductVO vo, String[] totalColumn, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" 返回单个值或全部值
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List load(ProductVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" 信息披露周期
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List queryPeriodDate1(ProductVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" 查询收益汇总
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	IPageList queryByProductID1(ProductVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_QUERY_TGAINTOTAL
	 * @IN_BOOK_CODE INT,
	 * @IN_PRODUCT_ID INT,
	 * @IN_SY_TYPE VARCHAR(10) = NULL,
	 * @IN_SY_DATE INT = NULL,
	 * @IN_INPUT_MAN INT = NULL
	 */
	IPageList listGainTotal(GainTotalVO vo, int page, int pagesize) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local" 查询运作明细
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List querytz1(ProductVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local" 查询认购
	 * @param vo
	 * @return
	 * @throws BusiException 
	 * @throws BusiException
	 * @throws  
	 */
	List rg_list(ProductVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" 查询受益
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List list_sy(ProductVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" 查询受益变更
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List listStatus1(ProductVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" 查询产品合同变更
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	IPageList list_cb(ProductVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" 查询修改信息
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List list_xg(ProductVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" 查询推介期利率
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List list_tl(ProductVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" 增加推介期
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	Integer adddeletecity1(ProductVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local" 查看推介期
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List querycity1(ProductVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local" 增加产品时间新增
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	void addDeleteDate1(ProductVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local" 查看产品时间信息
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List queryDate1(ProductVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local" 删除管理费
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	void delete(ProductVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local" 查看管理费提取分段配置信息
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List list_gfp(ProductVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local" 新建管理费
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	void append_glf(ProductVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local" 修改管理费
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	void save_glf(ProductVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 产品管理费提取分段信息
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List list_manrateConfig(ProductVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 产品开放日信息查询
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List list_openDate(ProductVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查找子产品
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List listSubProduct(ProductVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查找子产品
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList listSubProductForPage(ProductVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 分层统计
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List StatSubProductByProv(ProductVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 信息披露查询
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List listTask(ProductVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 信息披露查询
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	IPageList listTask(ProductVO vo, String[] totalColumn, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" 
	 * 新建信息披露
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	void appendTask(ProductVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local" 
	 * 修改信息披露
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	void modiTask(ProductVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local" 
	 * 删除信息披露
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	void deleteTask(ProductVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local" 
	 * 修改产品客户经理
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	void modiManager(ProductVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询产品销售设置 SP_QUERY_TPRODUCTLIMIT 
	 * @IN_BOOK_CODE        INTEGER,        --账套
	 * @IN_PRODUCT_ID       INTEGER        --产品ID
	 */
	List queryProductLimit(ProductVO vo) throws Exception;

	/**
	 * 由于信托没有升级，导致不一致 此方法还原到原来
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
	void updateProductLimit(ProductVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local" 
	 * 新增修改CRM中产品表
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	void modiCRMProduct(ProductVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查看CRM产品表中末审核
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	IPageList listPageCrmProduct(ProductVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查看CRM产品表中末审核
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	IPageList listPageCrmSubProduct(ProductVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查看CRM产品表中末审核
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List listCrmProduct(ProductVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" 
	 * 审核CRM中产品表
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	void modiCRMProductCheck(ProductVO vo) throws Exception;

	/**
	 * 发行期设置查询
	 * @ejb.interface-method view-type = "local" 
	 * @param sQuery
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	IPageList query(String sQuery, ProductVO vo, String[] totalColumn, int pageIndex, int pageSize) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param srcQuery
	 * @return
	 */
	String[] parseQuery(String srcQuery);

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 查询产品信息
	 * 
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List searchList(ProductVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" 
	 * 新增产品销售渠道信息
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	void addMarketTrench(ProductVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local" 
	 * 修改产品销售渠道信息
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	void modiMarketTrench(ProductVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local" 
	 * 删除产品销售渠道信息
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	void delMarketTrench(ProductVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local" 
	 * 查询产品销售渠道信息
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List queryMarketTrench(ProductVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * 查询产品表格信息
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List queryProductTable(ProductVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * 查询产品表格信息
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	String queryProductTableJosn(ProductVO vo) throws BusiException;

	/**
	 * 推介地
	 * @ejb.interface-method view-type = "local"
	 * @throws Exception
	 */
	Integer adddeletecity(ProductVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 推荐地修改
	 * SP_MODI_TPRODUCTCITY @IN_SERIAL_NO         INT,
	 *			             @IN_GOV_PROV_REGIONAL VARCHAR(10) = '',           --省级行政区域(9999)
	 *			             @IN_GOV_REGIONAL      VARCHAR(10) = '',           --行政区域(9999)
	 *			             @IN_INPUT_MAN         INT
	 * @throws Exception
	 */
	void modiProductCity(ProductVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local" 
	 * 查询客户经理销售过的哪些产品快到期了
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List queryDueProduct(ProductVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local" 
	 * 修改产品表投资领域
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	void modiProductInvestment(ProductVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local" 
	 * 修改产品是否可预约
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	void modiProductPre(ProductVO vo) throws Exception;

	/**
	 * 预发行产品的受益级别设置查询
	 * @ejb.interface-method view-type = "local" 
	 * @param sQuery
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	IPageList queryPreproductProv(ProductVO vo, int pageIndex, int pageSize) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local" 
	 * 修改预发行产品的受益级别
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	void modiPreproductProv(ProductVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local" 
	 * 修改预发行产品的受益级别的收益率
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	void modiPreproductProvRate(ProductVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local" 
	 * 新建预发行产品的受益级别
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	void addPreproductProv(ProductVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local" 
	 * 删除预发行产品的受益级别
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	void delPreproductProv(ProductVO vo) throws Exception;

}