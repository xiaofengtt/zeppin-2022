 
package enfo.crm.marketing;
import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.CrmDBManager;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.CustIntegralVO;

@Component(value="custIntegral")
public class CustIntegralBean extends enfo.crm.dao.CrmBusiExBean implements CustIntegralLocal {
	
	/* (non-Javadoc)
	 * @see enfo.crm.marketing.CustIntegralLocal#queryCustIntegral(enfo.crm.vo.CustIntegralVO, int, int)
	 */
	@Override
	public IPageList queryCustIntegral(CustIntegralVO vo,int pageIndex,int pageSize) throws BusiException{
		String sqlStr = "{call SP_QUERY_IM_CUST_INTEGRAL(?,?)}";
		IPageList rsList = null;		
		Object[] params = new Object[2];
		
		params[0] = vo.getCust_id();
		params[1] = vo.getCust_name();
		
		rsList = super.listProcPage(sqlStr,params,pageIndex,pageSize);		
		return rsList;	
	}
	/* (non-Javadoc)
	 * @see enfo.crm.marketing.CustIntegralLocal#addCustIntegral(enfo.crm.vo.CustIntegralVO)
	 */
	@Override
	public void addCustIntegral(CustIntegralVO vo) throws BusiException{
		String sqlStr = "{?=call SP_ADD_IM_CUST_INTEGRAL_CARD(?,?,?,?,?,?,?,?,?,?)}";
		Object[] params = new Object[10];
		
		params[0] = vo.getCust_id();
		params[1] = vo.getBusi_type();
		params[2] = vo.getBusi_id();
		params[3] = vo.getAmount();
		params[4] = vo.getAd_integral();
		params[5] = vo.getRule_id();
		params[6] = vo.getRdtl_id();
		params[7] = vo.getRamount_id();
		params[8] = vo.getProduct_id();
		params[9] = vo.getInput_man();
		
		CrmDBManager.cudProc(sqlStr, params);
	}
	/* (non-Javadoc)
	 * @see enfo.crm.marketing.CustIntegralLocal#addCustIntegralByHand(enfo.crm.vo.CustIntegralVO)
	 */
	@Override
	public void addCustIntegralByHand(CustIntegralVO vo) throws BusiException{
		String sqlStr = "{?=call SP_ADD_IM_CUST_INTEGRAL_CARD_ByHand(?,?,?,?,?,?)}";
		Object[] params = new Object[6];
		
		params[0] = vo.getCust_id();
		params[1] = vo.getBusi_type();
		params[2] = vo.getAd_integral();
		params[3] = vo.getProduct_id();
		params[4] = vo.getRemark();
		params[5] = vo.getInput_man();
		
		CrmDBManager.cudProc(sqlStr, params);
	}
}