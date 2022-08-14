package enfo.crm.cash;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IPageList;

public interface CashLocal {

	/**
	 * 查询10日内的产品收益率
	 * @param vo
	 * @return
	 */
	List ProductFundYield(CashVo vo) throws BusiException;

	/**
	 * 分页查客户
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList queryCust(CashVo vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * 查询10日内的客户产品收益率         
	 * @param vo
	 * @return
	 */
	List CustFundYield(CashVo vo) throws BusiException;

	/**
	 * 查询客户产品资金明细--单位净值披露
	 * @param vo
	 * @return
	 */
	List queryNetValueDisclosures(CashVo vo) throws BusiException;

	/**
	 * 分页查询产品赎回记录
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList queryProductRedeemList(CashVo vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * 查询赎回报告信息
	 * @param vo
	 * @return
	 */
	List queryRedeemReport(CashVo vo) throws BusiException;

	/**
	 * 查询现金聚利类产品的申购信息  
	 * @param vo
	 * @return
	 * @IN_CUST_ID        INT,            --客户ID
	                     @IN_BEN_ACCOUNT    NVARCHAR(8),    --客户受益账号
	                     @IN_PRODUCT_ID     INT,            --产品ID
	                     @IN_SUB_PRODUCT_ID INT,            --子产品ID
	                     @IN_INPUT_MAN      INT             --操作员
	 */
	List CustFundYieldsubscribe(CashVo vo) throws BusiException;

	/**
	 * 查询现金聚利类产品的客户资金流水
	 * @param vo
	 * @return
	 * @IN_CUST_ID        INT,            --客户ID
	                     @IN_BEN_ACCOUNT    NVARCHAR(8),    --客户受益账号
	                     @IN_PRODUCT_ID     INT,            --产品ID
	                     @IN_SUB_PRODUCT_ID INT,            --子产品ID
	                     @IN_INPUT_MAN      INT   
	 */
	List CustFundflow(CashVo vo) throws BusiException;

	/**
	 * 查询客户受益账号
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List getCustBank(CashVo vo) throws BusiException;

}