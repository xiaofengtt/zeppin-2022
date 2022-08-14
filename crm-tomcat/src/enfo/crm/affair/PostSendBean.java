 
package enfo.crm.affair;
import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IPageList;
import enfo.crm.tools.Utility;
import enfo.crm.vo.PostSendVO;

@Component(value="postSend")
public class PostSendBean extends enfo.crm.dao.CrmBusiExBean implements PostSendLocal {
	private static final String sql_add_tpostsend = "{?=call SP_ADD_TPOSTSEND(?,?,?,?,?,?,?)}";
    private static final String sql_modi_tpostsend = "{?=call SP_MODI_TPOSTSEND(?,?,?,?,?,?,?,?)}";
    private static final String sql_batch_modi_tpostsend = "{?=call SP_MODI_TPOSTSEND(?,?,?,?,?,?,?,?)}";
    private static final String sql_query_tpostsend = "{call SP_QUERY_TPOSTSEND(?,?,?,?,?)}";
    private static final String sql_query = "{call SP_QUERY_TPOSTSEND_PRODUCT(?,?,?)}";
    
    /* (non-Javadoc)
	 * @see enfo.crm.affair.PostSendLocal#append(enfo.crm.vo.PostSendVO)
	 */
    @Override
	public void append(PostSendVO vo) throws BusiException{
        Object[] params = new Object[7];
        params[0] = Utility.parseInt(vo.getInput_date(),null);
        params[1] = Utility.parseInt(vo.getProduct_id(),null);
        params[2] = Utility.trimNull(vo.getContract_sub_bh()).trim();
        params[3] = Utility.trimNull(vo.getPost_no()).trim();
        params[4] = Utility.trimNull(vo.getPost_content()).trim();
        params[5] = Utility.trimNull(vo.getSummary()).trim();
        params[6] = Utility.parseInt(vo.getInput_man(),null);
        super.cudProc(sql_add_tpostsend,params);    
    }

    /* (non-Javadoc)
	 * @see enfo.crm.affair.PostSendLocal#modi(enfo.crm.vo.PostSendVO)
	 */
    @Override
	public void modi(PostSendVO vo) throws BusiException{
        Object[] params = new Object[8];
        params[0] = Utility.parseInt(vo.getInput_date(),null);
        params[1] = Utility.parseInt(vo.getProduct_id(),null);
        params[2] = Utility.trimNull(vo.getContract_sub_bh()).trim();
        params[3] = Utility.trimNull(vo.getPost_no()).trim();
        params[4] = Utility.trimNull(vo.getPost_content()).trim();
        params[5] = Utility.trimNull(vo.getSummary()).trim();
        params[6] = Utility.parseInt(vo.getInput_man(),null);
        params[7] = Utility.parseInt(vo.getSerial_no(),null);
        super.cudProc(sql_modi_tpostsend,params);
    }


    /* (non-Javadoc)
	 * @see enfo.crm.affair.PostSendLocal#pagelist_query(enfo.crm.vo.PostSendVO, java.lang.String[], int, int)
	 */
    @Override
	public IPageList pagelist_query(PostSendVO vo,String[] totalColumn,int pageIndex,int pageSize) throws BusiException{
        Object[] params = new Object[5];
        params[0] = vo.getInput_date();
        params[1] = vo.getProduct_id();
        params[2] = Utility.trimNull(vo.getContract_sub_bh()).trim();
        params[3] = Utility.trimNull(vo.getPost_no()).trim();
        params[4] = Utility.trimNull(vo.getPost_content()).trim();
        return super.listProcPage(sql_query_tpostsend,params,totalColumn,pageIndex,pageSize);           
    }

    /* (non-Javadoc)
	 * @see enfo.crm.affair.PostSendLocal#list_query(enfo.crm.vo.PostSendVO)
	 */
    @Override
	public List list_query(PostSendVO vo) throws BusiException{ 
        Object[] params = new Object[5];
        params[0] = Utility.parseInt(vo.getInput_date(),null);
        params[1] = Utility.parseInt(vo.getProduct_id(),null);
        params[2] = Utility.trimNull(vo.getContract_sub_bh()).trim();
        params[3] = Utility.trimNull(vo.getPost_no()).trim();
        params[4] = Utility.trimNull(vo.getPost_content()).trim();
        return super.listProcAll(sql_query_tpostsend,params);
    }
    
    /* (non-Javadoc)
	 * @see enfo.crm.affair.PostSendLocal#query(enfo.crm.vo.PostSendVO, java.lang.String[], int, int)
	 */
    @Override
	public IPageList query(PostSendVO vo,String[] totalColumn,int pageIndex,int pageSize)throws BusiException{
    	Object[] params = new Object[3];
        params[0] = vo.getProduct_id();
        params[1] = vo.getCust_name();
        params[2] = vo.getContract_sub_bh();
        return super.listProcPage(sql_query,params,totalColumn,pageIndex,pageSize);
    }
    
    /* (non-Javadoc)
	 * @see enfo.crm.affair.PostSendLocal#batchModi(enfo.crm.vo.PostSendVO)
	 */
    @Override
	public void batchModi(PostSendVO vo) throws BusiException{
        Object[] params = new Object[8];
        params[0] = Utility.parseInt(vo.getInput_date(),null);
        params[1] = Utility.parseInt(vo.getProduct_id(),null);
        params[2] = Utility.trimNull(vo.getContract_sub_bh()).trim();
        params[3] = Utility.trimNull(vo.getPost_no()).trim();
        params[4] = Utility.trimNull(vo.getPost_content()).trim();
        params[5] = Utility.trimNull(vo.getSummary()).trim();
        params[6] = Utility.parseInt(vo.getInput_man(),null);
        params[7] = Utility.parseInt(vo.getSerial_no(),null);
        super.cudProc(sql_batch_modi_tpostsend,params);
    }
}