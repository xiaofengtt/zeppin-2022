 
package enfo.crm.customer;
import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.RatingVO;

@Component(value="scoreOperand")
public class ScoreOperandBean extends enfo.crm.dao.CrmBusiExBean implements ScoreOperandLocal {
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.ScoreOperandLocal#pageList_tscoreoperand(enfo.crm.vo.RatingVO, java.lang.String[], int, int)
	 */
	@Override
	public IPageList pageList_tscoreoperand(RatingVO vo,String[] totalColumn,int pageIndex,int pageSize) throws BusiException{
		String query_tscoreoperand = "{call SP_QUERY_TSCOREOPERAND(?,?,?,?,?,?,?)}";
		IPageList rsList = null;		
		Object[] params = new Object[7];
		
		params[0] = vo.getOperand_id();
		params[1] = vo.getInput_man();
		params[2] = vo.getSubject_id();
		params[3] = vo.getOperand_no();
		params[4] = vo.getOperand_name();
		params[5] = vo.getScoring();
		params[6] = vo.getSource();
			
		rsList = super.listProcPage(query_tscoreoperand,params,totalColumn,pageIndex,pageSize);		
		return rsList;				
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.ScoreOperandLocal#listBySqltscoreoperand(enfo.crm.vo.RatingVO)
	 */
	@Override
	public List listBySqltscoreoperand(RatingVO vo) throws BusiException{
		String query_tscoreoperand = "{call SP_QUERY_TSCOREOPERAND(?,?,?,?,?,?,?)}";
		List rsList = null;		
		Object[] params = new Object[7];		
		params[0] = vo.getOperand_id();
		params[1] = vo.getInput_man();
		params[2] = vo.getSubject_id();
		params[3] = vo.getOperand_no();
		params[4] = vo.getOperand_name();
		params[5] = vo.getScoring();
		params[6] = vo.getSource();
			
		rsList = super.listBySql(query_tscoreoperand,params);		
		return rsList;				
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.ScoreOperandLocal#list_tscoreoperand(enfo.crm.vo.RatingVO)
	 */
	@Override
	public List list_tscoreoperand(RatingVO vo) throws BusiException{

		String query_tscoreoperand = "{call SP_QUERY_TSCOREOPERAND(?,?)}";
		List rsList = null;		
		Object[] params = new Object[2];		
		params[0] = vo.getOperand_id();
		params[1] = vo.getInput_man();
		rsList = super.listProcAll(query_tscoreoperand,params);
		return rsList;
	}	
	
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.ScoreOperandLocal#append_tscoreoperand(enfo.crm.vo.RatingVO)
	 */
	@Override
	public void append_tscoreoperand(RatingVO vo) throws BusiException{
		String add_tscoreoperand = "{?=call SP_ADD_TSCOREOPERAND(?,?,?,?,?,?,?)}";
		Object[] params = new Object[7];
		
		params[0] = vo.getSubject_id();
		params[1] = vo.getOperand_no();
		params[2] = vo.getOperand_name();
		params[3] = vo.getScoring();
		params[4] = vo.getSource();
		params[5] = vo.getSummary();
		params[6] = vo.getInput_man();	
		
		super.cudProc(add_tscoreoperand,params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.ScoreOperandLocal#modi_tscoreoperand(enfo.crm.vo.RatingVO)
	 */
	@Override
	public void modi_tscoreoperand(RatingVO vo) throws BusiException{
		String modi_tscoreoperand = "{?=call SP_MODI_TSCOREOPERAND(?,?,?,?,?,?,?,?)}";
		Object[] params = new Object[8];
		
		params[0] = vo.getOperand_id();
		params[1] = vo.getSubject_id();
		params[2] = vo.getOperand_no();
		params[3] = vo.getOperand_name();
		params[4] = vo.getScoring();
		params[5] = vo.getSource();
		params[6] = vo.getSummary();
		params[7] = vo.getInput_man();	
		
		super.cudProc(modi_tscoreoperand,params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.ScoreOperandLocal#delete_tscoreoperand(enfo.crm.vo.RatingVO)
	 */
	@Override
	public void delete_tscoreoperand(RatingVO vo) throws BusiException{
		String del_tscoreoperand = "{?=call SP_DEL_TSCOREOPERAND(?,?)}";
		Object[] params = new Object[2];
		
		params[0] = vo.getOperand_id();
		params[1] = vo.getInput_man();
		
		super.cudProc(del_tscoreoperand,params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.ScoreOperandLocal#list_subjectoperand(enfo.crm.vo.RatingVO)
	 */
	@Override
	public List list_subjectoperand(RatingVO vo) throws BusiException{

		String query_tscoreoperand = "{call SP_QUERY_TSCOREOPERAND(?,?,?)}";
		List rsList = null;		
		Object[] params = new Object[3];
		
		params[0] = vo.getOperand_id();
		params[1] = vo.getInput_man();
		params[2] = vo.getSubject_id();
		rsList = super.listProcAll(query_tscoreoperand,params);
		return rsList;
	}	
	
}