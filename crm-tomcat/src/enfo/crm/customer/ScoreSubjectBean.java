 
package enfo.crm.customer;
import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IPageList;
import enfo.crm.tools.Utility;
import enfo.crm.vo.RatingVO;

@Component(value="scoreSubject")
public class ScoreSubjectBean extends enfo.crm.dao.CrmBusiExBean implements ScoreSubjectLocal {

	/* (non-Javadoc)
	 * @see enfo.crm.customer.ScoreSubjectLocal#pageList_query(enfo.crm.vo.RatingVO, java.lang.String[], int, int)
	 */
	@Override
	public IPageList pageList_query(RatingVO vo,String[] totalColumn,int pageIndex,int pageSize) throws BusiException{
		String sqlStr = "{call SP_QUERY_TSCORESUBJECT(?,?,?,?)}";
		IPageList rsList = null;		
		Object[] params = new Object[4];
		
		params[0] = Utility.parseInt(Utility.trimNull(vo.getSubject_id()),new Integer(0));
		params[1] = vo.getInput_man();
		params[2] = vo.getSubject_no();
		params[3] = vo.getSubject_name();
			
		rsList = super.listProcPage(sqlStr,params,totalColumn,pageIndex,pageSize);		
		return rsList;				
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.ScoreSubjectLocal#query(enfo.crm.vo.RatingVO)
	 */
	@Override
	public List query(RatingVO vo) throws BusiException{

		String query_tscoresubject = "{call SP_QUERY_TSCORESUBJECT(?,?)}";
		List rsList = null;		
		Object[] params = new Object[2];
		
		params[0] = Utility.parseInt(Utility.trimNull(vo.getSubject_id()),new Integer(0));
		params[1] = vo.getInput_man();
		rsList = super.listProcAll(query_tscoresubject,params);
		return rsList;
	}	
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.ScoreSubjectLocal#append(enfo.crm.vo.RatingVO)
	 */
	@Override
	public void append(RatingVO vo) throws BusiException{
		String add_tscoresubject = "{?=call SP_ADD_TSCORESUBJECT(?,?,?,?)}";
		Object[] params = new Object[4];
		
		params[0] = vo.getSubject_no();
		params[1] = vo.getSubject_name();
		params[2] = vo.getSummary();
		params[3] = vo.getInput_man();	
		
		super.cudProc(add_tscoresubject,params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.ScoreSubjectLocal#modi(enfo.crm.vo.RatingVO)
	 */
	@Override
	public void modi(RatingVO vo) throws BusiException{
		String modi_tscoresubject = "{?=call SP_MODI_TSCORESUBJECT(?,?,?,?,?)}";
		Object[] params = new Object[5];
		
		params[0] = Utility.parseInt(Utility.trimNull(vo.getSubject_id()),new Integer(0));
		params[1] = vo.getSubject_no();
		params[2] = vo.getSubject_name();
		params[3] = vo.getSummary();
		params[4] = vo.getInput_man();
		
		super.cudProc(modi_tscoresubject,params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.ScoreSubjectLocal#delete(enfo.crm.vo.RatingVO)
	 */
	@Override
	public void delete(RatingVO vo) throws BusiException{
		String del_tscoresubject = "{?=call SP_DEL_TSCORESUBJECT(?,?)}";
		Object[] params = new Object[2];
		
		params[0] = Utility.parseInt(Utility.trimNull(vo.getSubject_id()),new Integer(0));
		params[1] = vo.getInput_man();
		
		super.cudProc(del_tscoresubject,params);
	}
}