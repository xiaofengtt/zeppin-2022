 
package enfo.crm.customer;
import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.RatingVO;

@Component(value="manualScoring")
public class ManualScoringBean extends enfo.crm.dao.CrmBusiExBean implements ManualScoringLocal {
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.ManualScoringLocal#pageList_tmanualscoring(enfo.crm.vo.RatingVO, java.lang.String[], int, int)
	 */
	@Override
	public IPageList pageList_tmanualscoring(RatingVO vo,String[] totalColumn,int pageIndex,int pageSize) throws BusiException{
		String query_tmanualscoring = "{call SP_QUERY_TMANUALSCORING(?,?,?,?)}";
		IPageList rsList = null;		
		Object[] params = new Object[4];
		
		params[0] = vo.getManual_id();
		params[1] = vo.getInput_man();
		params[2] = vo.getOperand_no();
		params[3] = vo.getOperand_name();
			
		rsList = super.listProcPage(query_tmanualscoring,params,totalColumn,pageIndex,pageSize);		
		return rsList;				
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.ManualScoringLocal#list_tmanualscoring(enfo.crm.vo.RatingVO)
	 */
	@Override
	public List list_tmanualscoring(RatingVO vo) throws BusiException{

		String query_tmanualscoring = "{call SP_QUERY_TMANUALSCORING(?,?)}";
		List rsList = null;		
		Object[] params = new Object[2];
		
		params[0] = vo.getManual_id();
		params[1] = vo.getInput_man();
		rsList = super.listProcAll(query_tmanualscoring,params);
		return rsList;
	}	
	
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.ManualScoringLocal#append_tmanualscoring(enfo.crm.vo.RatingVO)
	 */
	@Override
	public void append_tmanualscoring(RatingVO vo) throws BusiException{
		String add_tmanualscoring = "{?=call SP_ADD_TMANUALSCORING(?,?,?,?)}";
		Object[] params = new Object[4];
		
		params[0] = vo.getOperand_id();
		params[1] = vo.getOperation_value();
		params[2] = vo.getScore();
		params[3] = vo.getInput_man();	
		
		super.cudProc(add_tmanualscoring,params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.ManualScoringLocal#modi_tmanualscoring(enfo.crm.vo.RatingVO)
	 */
	@Override
	public void modi_tmanualscoring(RatingVO vo) throws BusiException{
		String modi_tmanualscoring = "{?=call SP_MODI_TMANUALSCORING(?,?,?,?,?)}";
		Object[] params = new Object[5];
		
		params[0] = vo.getManual_id();
		params[1] = vo.getOperand_id();
		params[2] = vo.getOperation_value();
		params[3] = vo.getScore();
		params[4] = vo.getInput_man();	
		
		super.cudProc(modi_tmanualscoring,params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.ManualScoringLocal#delete_tmanualscoring(enfo.crm.vo.RatingVO)
	 */
	@Override
	public void delete_tmanualscoring(RatingVO vo) throws BusiException{
		String del_tmanualscoring = "{?=call SP_DEL_TMANUALSCORING(?,?)}";
		Object[] params = new Object[2];
		
		params[0] = vo.getManual_id();
		params[1] = vo.getInput_man();
		
		super.cudProc(del_tmanualscoring,params);
	}
	
}