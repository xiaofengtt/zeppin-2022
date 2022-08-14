 
package enfo.crm.customer;
import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IPageList;
import enfo.crm.tools.Utility;
import enfo.crm.vo.ProductVO;
import enfo.crm.vo.RatingVO;

@Component(value="rating")
public class RatingBean extends enfo.crm.dao.CrmBusiExBean implements RatingLocal {

	/* (non-Javadoc)
	 * @see enfo.crm.customer.RatingLocal#pageList_tcustscoredetail(enfo.crm.vo.RatingVO, java.lang.String[], int, int)
	 */
	@Override
	public IPageList pageList_tcustscoredetail(RatingVO vo,String[] totalColumn,int pageIndex,int pageSize) throws BusiException{
		String query_tcustscoredetail = "{call SP_QUERY_TCUSTSCOREDETAIL(?,?,?,?,?,?)}";
		IPageList rsList = null;		
		Object[] params = new Object[6];
		
		params[0] = vo.getCust_no();
		params[1] = vo.getCust_name();
		params[2] = vo.getSubject_id();
		params[3] = vo.getOperand_id();
		params[4] = vo.getScoring_date();
		params[5] = vo.getInput_man();
			
		rsList = super.listProcPage(query_tcustscoredetail,params,totalColumn,pageIndex,pageSize);		
		return rsList;				
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.RatingLocal#pageList_tcustscore(enfo.crm.vo.RatingVO, java.lang.String[], int, int)
	 */
	@Override
	public IPageList pageList_tcustscore(RatingVO vo,String[] totalColumn,int pageIndex,int pageSize) throws BusiException{
		String query_tcustscore = "{call SP_QUERY_TCUSTSCORE(?,?,?,?,?)}";
		IPageList rsList = null;		
		Object[] params = new Object[5];
		
		params[0] = vo.getCust_no();
		params[1] = vo.getCust_name();
		params[2] = vo.getSubject_id();
		params[3] = vo.getCurrnet_date();
		params[4] = vo.getInput_man();
			
		rsList = super.listProcPage(query_tcustscore,params,totalColumn,pageIndex,pageSize);		
		return rsList;				
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.RatingLocal#pageList_tcustrating(enfo.crm.vo.RatingVO, java.lang.String[], int, int)
	 */
	@Override
	public IPageList pageList_tcustrating(RatingVO vo,String[] totalColumn,int pageIndex,int pageSize) throws BusiException{
		String query_tcustrating = "{call SP_QUERY_TCUSTRATING(?,?,?,?,?,?,?)}";
		IPageList rsList = null;		
		Object[] params = new Object[7];
		
		params[0] = vo.getCust_name();
		params[1] = vo.getCust_type();
		params[2] = vo.getSubject_id();
		params[3] = vo.getRating_no();
		params[4] = vo.getRating_name();
		//params[5] = vo.getRating_date();
		params[5] = vo.getInput_man();
		params[6] = vo.getPx_name();
			
		rsList = super.listProcPage(query_tcustrating,params,totalColumn,pageIndex,pageSize);		
		return rsList;				
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.RatingLocal#list_tcustscoredetail(enfo.crm.vo.RatingVO)
	 */
	@Override
	public List list_tcustscoredetail(RatingVO vo) throws BusiException{
		String query_tcustscoredetail = "{call SP_QUERY_TCUSTSCOREDETAIL(?,?,?,?,?,?)}";
		List rsList = null;		
		Object[] params = new Object[6];
		
		params[0] = vo.getCust_no();
		params[1] = vo.getCust_name();
		params[2] = vo.getSubject_id();
		params[3] = vo.getOperand_id();
		params[4] = vo.getScoring_date();
		params[5] = vo.getInput_man();
			
		rsList = super.listProcAll(query_tcustscoredetail,params);		
		return rsList;				
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.RatingLocal#custRating(enfo.crm.vo.RatingVO)
	 */
	@Override
	public void custRating(RatingVO vo) throws BusiException{
		String str = "{?=call SP_ADD_TCUSTRATING(?,?,?,?,?,?)}";
		Object[] params = new Object[6];
		
		params[0] = vo.getSubject_id();
		params[1] = vo.getOperand_id();
		params[2] = vo.getCust_id();
		params[3] = vo.getRating_date();
		params[4] = vo.getScore();
		params[5] = vo.getInput_man();	
		
		super.cudProc(str,params);
	}
	
	 /* (non-Javadoc)
	 * @see enfo.crm.customer.RatingLocal#modiLevelScore(enfo.crm.vo.ProductVO)
	 */
    @Override
	public void modiLevelScore(ProductVO vo)throws BusiException{
    	Object[] params = new Object[4];
    	params[0] = Utility.parseInt(vo.getProduct_id(), new Integer(0));
    	params[1] = Utility.trimNull(vo.getRisk_level());
    	params[2] = Utility.parseInt(vo.getRisk_level_score(), new Integer(0));
    	params[3] = Utility.parseInt(vo.getInput_man(), new Integer(0));
    	super.cudProc("{?= call SP_MODI_LEVELSCORE(?,?,?,?)}", params);
    }
    
    /* (non-Javadoc)
	 * @see enfo.crm.customer.RatingLocal#queryLevelScore(java.lang.Integer)
	 */
    @Override
	public List queryLevelScore(Integer product_id)throws BusiException{
    	return super.listBySql("SELECT * FROM TPRODUCT WHERE PRODUCT_ID = "+product_id);
    }
    
    /* (non-Javadoc)
	 * @see enfo.crm.customer.RatingLocal#queryChangeCustomerScore(enfo.crm.vo.RatingVO, java.lang.String, int, int)
	 */
    @Override
	public List queryChangeCustomerScore(RatingVO vo,String cust_id,int pageIndex,int pageSize)throws BusiException{
    	Object[] params = new Object[8];
    	List pageList = null;
		params[0] = vo.getCust_name();
		params[1] = vo.getCust_type();
		params[2] = vo.getSubject_id();
		params[3] = vo.getRating_no();
		params[4] = vo.getRating_name();
		params[5] = vo.getInput_man();
		params[6] = vo.getPx_name();
		params[7] = Utility.trimNull(cust_id);
		pageList = super.listBySql("{call SP_QUERY_CHANGECUSTOMER_SROCRE(?,?,?,?,?,?,?,?)}", params);
		return pageList;
    }
}