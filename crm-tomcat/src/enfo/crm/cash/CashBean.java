/*
 * 创建日期 2015-11-18
 */
package enfo.crm.cash;

import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.CrmBusiExBean;
import enfo.crm.dao.IPageList;
import enfo.crm.tools.Utility;

@Component(value="cash")
public class CashBean extends CrmBusiExBean implements CashLocal {

	/* (non-Javadoc)
	 * @see enfo.crm.cash.CashLocal#ProductFundYield(enfo.crm.cash.CashVo)
	 */
	@Override
	public List ProductFundYield(CashVo vo) throws BusiException {

		String sql = "{call SP_QUERY_CASH_GAIN (?,?,?,?,?,?,?)}";

		Object[] params = new Object[7];
		
		params[0] = Utility.parseInt(vo.getProductId(),new Integer(0));
		params[1] = Utility.parseInt(vo.getSubProductId(),new Integer(0));
		params[2] = Utility.parseInt(vo.getCustId(),new Integer(0));
		params[3] = Utility.parseInt(vo.getBeginDate(),new Integer(0));
		params[4] = Utility.parseInt(vo.getEndDate(),new Integer(0));
		params[5] = Utility.parseInt(vo.getInputMan(),new Integer(0));
		params[6] = vo.getBenAccount();
		
		try {
			return super.listBySql(sql, params);
		} catch (Exception e) {
			throw new BusiException("查询现金聚利类产品的产品收益率信息失败: " + e.getMessage());
		}
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.cash.CashLocal#queryCust(enfo.crm.cash.CashVo, int, int)
	 */
	@Override
	public IPageList queryCust(CashVo vo,int pageIndex,int pageSize) throws BusiException {

		String sql = "{call SP_QUERY_CASH_CUST (?,?,?,?,?,?,?,?,?,?,?)}";

		Object[] params = new Object[11];
		
		params[0] = Utility.parseInt(vo.getCustId(),new Integer(0));
		params[1] = vo.getCustNo();
		params[2] = vo.getCustName();
		params[3] = vo.getCardId();
		params[4] = Utility.parseInt(vo.getCustType(),new Integer(0));
		params[5] = vo.getCustTel();
		params[6] = vo.getAddress();
		params[7] = Utility.parseInt(vo.getServicemen(), new Integer(0));
		params[8] = Utility.parseInt(vo.getProductId(),new Integer(0));
		params[9] = Utility.parseInt(vo.getSubProductId(), new Integer(0));
		params[10] = Utility.parseInt(vo.getInputMan(),new Integer(0));

		try {
			return  super.listProcPage(sql, params, pageIndex, pageSize);
		} catch (Exception e) {
			throw new BusiException("查询客户失败: " + e.getMessage());
		}
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.cash.CashLocal#CustFundYield(enfo.crm.cash.CashVo)
	 */
	@Override
	public List CustFundYield(CashVo vo) throws BusiException {
	
		String sql = "{call SP_QUERY_CASH_GAIN (?,?,?,?,?,?,?)}";
		
		Object[] params = new Object[7];
		
		params[0] = Utility.parseInt(vo.getProductId(),new Integer(0));
		params[1] = Utility.parseInt(vo.getSubProductId(),new Integer(0));
		params[2] = Utility.parseInt(vo.getCustId(),new Integer(0));
		params[3] = Utility.parseInt(vo.getBeginDate(),new Integer(0));
		params[4] = Utility.parseInt(vo.getEndDate(),new Integer(0));
		params[5] = Utility.parseInt(vo.getInputMan(),new Integer(0));
		params[6] = vo.getBenAccount();
		
		try {
			return super.listBySql(sql, params);
		} catch (Exception e) {
			throw new BusiException("查询现金聚利类产品的客户收益率信息失败: " + e.getMessage());
		}
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.cash.CashLocal#queryNetValueDisclosures(enfo.crm.cash.CashVo)
	 */
	@Override
	public List queryNetValueDisclosures(CashVo vo) throws BusiException {
		
		String sql = "{call SP_QUERY_CASH_NETVALUE_DISCLOSURES (?,?,?,?,?,?,?)}";
		
		Object[] params = new Object[7];
		
		params[0] = Utility.parseInt(vo.getCustId(),new Integer(0));
		params[1] = Utility.parseInt(vo.getBeginDate(),new Integer(0));
		params[2] = Utility.parseInt(vo.getEndDate(),new Integer(0));
		params[3] = Utility.parseInt(vo.getProductId(),new Integer(0));
		params[4] = Utility.parseInt(vo.getSubProductId(),new Integer(0));
		params[5] = Utility.parseInt(vo.getInputMan(),new Integer(0));
		params[6] = vo.getBenAccount();

		try {
			return super.listBySql(sql, params);
		} catch (Exception e) {
			throw new BusiException("查询现金聚利类产品的单位净值披露信息失败: " + e.getMessage());
		}
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.cash.CashLocal#queryProductRedeemList(enfo.crm.cash.CashVo, int, int)
	 */
	@Override
	public IPageList queryProductRedeemList(CashVo vo,int pageIndex,int pageSize) throws BusiException {
		
		String sql = "{call SP_QUERY_CASH_TREDEEMDETAIL (?,?,?,?,?,?)}";
		
		Object[] params = new Object[6];
		
		params[0] = Utility.parseInt(vo.getCustId(),new Integer(0));
		params[1] = vo.getContractBH();
		params[2] = Utility.parseInt(vo.getCheckFlag(),new Integer(0));
		params[3] = Utility.parseInt(vo.getProductId(),new Integer(0));
		params[4] = Utility.parseInt(vo.getSubProductId(),new Integer(0));
		params[5] = Utility.parseInt(vo.getInputMan(),new Integer(0));

		try {
			return  super.listProcPage(sql, params, pageIndex, pageSize);
		} catch (Exception e) {
			throw new BusiException("查询客户失败: " + e.getMessage());
		}
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.cash.CashLocal#queryRedeemReport(enfo.crm.cash.CashVo)
	 */
	@Override
	public List queryRedeemReport(CashVo vo) throws BusiException{
                                                             
		String sql = "{call SP_QUERY_CASH_TREDEEM_PRINT (?,?)}";
		
		Object[] params = new Object[2];
		
		params[0] = Utility.parseInt(vo.getSerialNo(),new Integer(0));
		params[1] = Utility.parseInt(vo.getInputMan(),new Integer(888));
		
		try {
			return super.listBySql(sql, params);
		} catch (Exception e) {
			throw new BusiException("查询现金聚利类产品的赎回报告信息失败: " + e.getMessage());
		}
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.cash.CashLocal#CustFundYieldsubscribe(enfo.crm.cash.CashVo)
	 */
	@Override
	public List CustFundYieldsubscribe(CashVo vo)throws BusiException{

		String sql="{call SP_QUERY_CASH_SUBSCRIBE (?,?,?,?,?,?,?)}";

		Object[] params = new Object[7];

		params[0] = vo.getCustId();
		params[1] = vo.getBenAccount();
		params[2] = vo.getProductId();
		params[3] = vo.getSubProductId();
		params[4] = vo.getInputMan();
		params[5] = vo.getBeginDate();
		params[6] = vo.getEndDate();

		try {
			return super.listBySql(sql, params);
			//return	super.listProcAll(sql,params);
		} catch (Exception e) {
			throw new BusiException("查询现金聚利类产品的申购信息失败: " + e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see enfo.crm.cash.CashLocal#CustFundflow(enfo.crm.cash.CashVo)
	 */
	@Override
	public List CustFundflow(CashVo vo) throws BusiException{

		String sql="{call SP_QUERY_CASH_FUNDFLOW (?,?,?,?,?,?,?)}";

		Object[] params = new Object[7];

		params[0] = vo.getCustId();
		params[1] = vo.getBenAccount();
		params[2] = vo.getProductId();
		params[3] = vo.getSubProductId();
		params[4] = vo.getInputMan();
		params[5] = vo.getBeginDate();
		params[6] = vo.getEndDate();
		
		try {
			return super.listBySql(sql, params);
			//return	super.listProcAll(sql,params);
		} catch (Exception e) {
			throw new BusiException("查询现金聚利类产品的申购信息失败: " + e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see enfo.crm.cash.CashLocal#getCustBank(enfo.crm.cash.CashVo)
	 */
	@Override
	public List getCustBank(CashVo vo) throws BusiException {

		String listSql = "{call SP_QUERY_CASH_BENACCT (?,?,?)}";

		Object[] params = new Object[3];

		params[0] = vo.getCustId();
		params[1] = new Integer(1);
		params[2] = vo.getInputMan();
		
		try {
			return super.listBySql(listSql, params);
		} catch (Exception e) {
			throw new BusiException("查询客户受益账号失败: " + e.getMessage());
		}
	}
}
