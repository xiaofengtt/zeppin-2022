 
package enfo.crm.customer;
import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IPageList;
import enfo.crm.tools.Utility;
import enfo.crm.vo.RatingVO;

@Component(value="subjectScoreRating")
public class SubjectScoreRatingBean extends enfo.crm.dao.CrmBusiExBean implements SubjectScoreRatingLocal {
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.SubjectScoreRatingLocal#pageList_subjectScoreRating(enfo.crm.vo.RatingVO, java.lang.String[], int, int)
	 */
	@Override
	public IPageList pageList_subjectScoreRating(RatingVO vo,String[] totalColumn,int pageIndex,int pageSize) throws BusiException{
		String query_tsubjectscorerating = "{call SP_QUERY_TSUBJECTSCORERATING(?,?,?,?,?)}";
		IPageList rsList = null;		
		Object[] params = new Object[5];
		
		params[0] = Utility.parseInt(Utility.trimNull(vo.getRating_id()),new Integer(0));
		params[1] = vo.getInput_man();
		params[2] = Utility.parseInt(Utility.trimNull(vo.getSubject_id()),new Integer(0));
		params[3] = vo.getRating_no();
		params[4] = vo.getRating_name();
			
		rsList = super.listProcPage(query_tsubjectscorerating,params,totalColumn,pageIndex,pageSize);		
		return rsList;				
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.SubjectScoreRatingLocal#list_subjectScoreRating(enfo.crm.vo.RatingVO)
	 */
	@Override
	public List list_subjectScoreRating(RatingVO vo) throws BusiException{

		String query_tsubjectscorerating = "{call SP_QUERY_TSUBJECTSCORERATING(?,?)}";
		List rsList = null;		
		Object[] params = new Object[2];
		
		params[0] = Utility.parseInt(Utility.trimNull(vo.getRating_id()),new Integer(0));
		params[1] = vo.getInput_man();
		rsList = super.listProcAll(query_tsubjectscorerating,params);
		return rsList;
	}	
	
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.SubjectScoreRatingLocal#append_subjectScoreRating(enfo.crm.vo.RatingVO)
	 */
	@Override
	public void append_subjectScoreRating(RatingVO vo) throws BusiException{
		String add_tsubjectscorerating = "{?=call SP_ADD_TSUBJECTSCORERATING(?,?,?,?,?,?,?,?,?)}";
		Object[] params = new Object[9];
		
		params[0] = vo.getRating_no();
		params[1] = vo.getRating_name();
		params[2] = vo.getSubject_id();
		params[3] = vo.getInclude_top();
		params[4] = vo.getScore_lower();
		params[5] = vo.getInclude_end();
		params[6] = vo.getScore_upper();
		params[7] = vo.getSummary();
		params[8] = vo.getInput_man();	
		
		super.cudProc(add_tsubjectscorerating,params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.SubjectScoreRatingLocal#modi_subjectScoreRating(enfo.crm.vo.RatingVO)
	 */
	@Override
	public void modi_subjectScoreRating(RatingVO vo) throws BusiException{
		String modi_tsubjectscorerating = "{?=call SP_MODI_TSUBJECTSCORERATING(?,?,?,?,?,?,?,?,?,?)}";
		Object[] params = new Object[10];
		
		params[0] = vo.getRating_id();
		params[1] = vo.getRating_no();
		params[2] = vo.getRating_name();
		params[3] = vo.getSubject_id();
		params[4] = vo.getInclude_top();
		params[5] = vo.getScore_lower();
		params[6] = vo.getInclude_end();
		params[7] = vo.getScore_upper();
		params[8] = vo.getSummary();
		params[9] = vo.getInput_man();	
		
		super.cudProc(modi_tsubjectscorerating,params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.SubjectScoreRatingLocal#delete_subjectScoreRating(enfo.crm.vo.RatingVO)
	 */
	@Override
	public void delete_subjectScoreRating(RatingVO vo) throws BusiException{
		String del_tsubjectscorerating = "{?=call SP_DEL_TSUBJECTSCORERATING(?,?)}";
		Object[] params = new Object[2];
		
		params[0] = vo.getRating_id();
		params[1] = vo.getInput_man();
		
		super.cudProc(del_tsubjectscorerating,params);
	}
}