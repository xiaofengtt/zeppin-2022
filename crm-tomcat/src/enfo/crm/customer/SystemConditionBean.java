 
package enfo.crm.customer;
import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.vo.RatingVO;

@Component(value="systemCondition")
public class SystemConditionBean extends enfo.crm.dao.CrmBusiExBean implements SystemConditionLocal {
	
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.SystemConditionLocal#list_tsystemcondition(enfo.crm.vo.RatingVO)
	 */
	@Override
	public List list_tsystemcondition(RatingVO vo) throws BusiException{

		String query_tsystemcondition = "{call SP_QUERY_TSYSTEMCONDITION(?,?)}";
		List rsList = null;		
		Object[] params = new Object[2];
		
		params[0] = vo.getOperand_v_id();
		params[1] = vo.getInput_man();
		rsList = super.listProcAll(query_tsystemcondition,params);
		return rsList;
	}	
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.SystemConditionLocal#modi_tsystemcondition(enfo.crm.vo.RatingVO)
	 */
	@Override
	public void modi_tsystemcondition(RatingVO vo) throws BusiException{
		String modi_tsystemcondition = "{?=call SP_MODI_TSYSTEMCONDITION(?,?,?,?,?,?,?,?,?,?)}";
		Object[] params = new Object[10];
		
		params[0] = vo.getOperand_v_id();
		params[1] = vo.getSource_table();
		params[2] = vo.getSource_field();
		params[3] = vo.getInclude_top();
		params[4] = vo.getTop_threshold();	
		params[5] = vo.getInclude_end();
		params[6] = vo.getEnd_threshold();
		params[7] = vo.getTrue_false_value();
		params[8] = vo.getSummary();
		params[9] = vo.getInput_man();
		
		super.cudProc(modi_tsystemcondition,params);
	}

}