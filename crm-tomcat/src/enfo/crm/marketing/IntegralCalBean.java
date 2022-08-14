 
package enfo.crm.marketing;
import java.sql.Types;
import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.CrmDBManager;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.IntegralCalVO;

@Component(value="integralCal")
public class IntegralCalBean extends enfo.crm.dao.CrmBusiExBean implements IntegralCalLocal {
	/* (non-Javadoc)
	 * @see enfo.crm.marketing.IntegralCalLocal#integralCal(enfo.crm.vo.IntegralCalVO)
	 */
	@Override
	public Object[] integralCal(IntegralCalVO vo) throws BusiException{
		String sqlStr = "{?=call SP_CAL_INTEGRAL(?,?,?,?,?,?)}";
		 Object[] params = new Object[3];
		 Object[] object = null;
		 
		 params[0] = vo.getRuleID();
		 params[1] = vo.getOrderNo();
		 params[2] = vo.getAmount();
		 
		 int[] outParamPos = new int[] {5,6,7};
		 int[] outParamType = new int[] { Types.INTEGER, Types.INTEGER, Types.DECIMAL };
		 object = CrmDBManager.cudProc(sqlStr, params, outParamPos, outParamType);
		 
		 return object;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.marketing.IntegralCalLocal#queryIntegralLog(enfo.crm.vo.IntegralCalVO, int, int)
	 */
	@Override
	public IPageList queryIntegralLog(IntegralCalVO vo,int pageIndex,int pageSize) throws BusiException{
		String sqlStr = "{call SP_QUERY_IMRULELOG(?,?,?,?,?)}";
		IPageList rsList = null;		
		Object[] params = new Object[5];
		
		params[0] = vo.getStartDate();
		params[1] = vo.getEndDate();
		params[2] = vo.getCust_name();
		params[3] = vo.getLog_type();
		params[4] = vo.getRuleID();
		
		rsList = super.listProcPage(sqlStr,params,pageIndex,pageSize);		
		return rsList;		
	}
	/* (non-Javadoc)
	 * @see enfo.crm.marketing.IntegralCalLocal#queryRule(enfo.crm.vo.IntegralCalVO)
	 */
	@Override
	public List queryRule(IntegralCalVO vo) throws BusiException{
		List rsList = null;
		String sqlStr = "SELECT * FROM IM_RULE " +
				"WHERE (RULE_ID = " + vo.getRuleID() +" OR " + vo.getRuleID() + " IS NULL OR " + vo.getRuleID() + " = 0)";
		
		rsList = super.listBySql(sqlStr);
		
		return rsList;		
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.marketing.IntegralCalLocal#queryRuleDtl(enfo.crm.vo.IntegralCalVO)
	 */
	@Override
	public List queryRuleDtl(IntegralCalVO vo) throws BusiException{
		List rsList = null;
		String sqlStr = "SELECT A.*,B.RULE_NAME FROM IM_RULE_DTL A RIGHT JOIN IM_RULE B ON A.RULE_ID = B.RULE_ID " +
				"WHERE (A.RULE_ID = " + vo.getRuleID() +" OR " + vo.getRuleID() + " IS NULL OR " + vo.getRuleID() + " = 0)";
		
		rsList = super.listBySql(sqlStr);
		
		return rsList;		
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.marketing.IntegralCalLocal#queryRuleAmount(enfo.crm.vo.IntegralCalVO)
	 */
	@Override
	public List queryRuleAmount(IntegralCalVO vo) throws BusiException{
		List rsList = null;
		String sqlStr = "SELECT A.*,B.Remark AS DTL_REMARK,C.RULE_NAME FROM IM_RULE_AMOUNT A RIGHT JOIN IM_RULE_DTL B ON A.RULE_ID = B.RULE_ID RIGHT JOIN IM_RULE C ON A.RULE_ID = C.RULE_ID " +
				"WHERE (A.RULE_ID = " + vo.getRuleID() +" OR " + vo.getRuleID() + " IS NULL OR " + vo.getRuleID() + " = 0)";
		
		rsList = super.listBySql(sqlStr);
		
		return rsList;		
	}
}