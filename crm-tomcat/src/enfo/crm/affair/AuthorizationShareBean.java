 
package enfo.crm.affair;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.axis2.context.SessionContext;
import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IPageList;
import enfo.crm.tools.Utility;
import enfo.crm.vo.AuthorizationShareVO;

@Component(value="authorizationShare")
public class AuthorizationShareBean extends enfo.crm.dao.CrmBusiExBean implements AuthorizationShareLocal {
	private SessionContext mySessionCtx;
	private static final String sql_add_TAuthorizationShare = "{?=call SP_ADD_TAuthorizationShare(?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String sql_query_TAuthorizationShare = "{call SP_QUERY_TAuthorizationShare(?,?,?,?,?,?,?,?,?)}";
	private static final String sql_del_TAuthorizationShare = "{?=call SP_DEL_TAuthorizationShare(?,?)}";
	private static final String sql_modi_TAuthorizationShare_Status = "{?=call SP_MODI_TAuthorization_Status(?,?,?)}";
	private static final String sql_modi_TAuthorizationShare = "{?=call SP_MODI_TAuthorizationShare(?,?,?,?,?,?,?,?,?)}";

	/* (non-Javadoc)
	 * @see enfo.crm.affair.AuthorizationShareLocal#load(java.lang.Integer)
	 */
	@Override
	public List load(Integer serial_no) throws BusiException {
	    String sqlLoad = "{call SP_QUERY_TAuthorizationShare(?,?,?,?,?,?,?,?)}";
		List list = new ArrayList();
		Object[] params = new Object[8];
		params[0] = serial_no;
		list = super.listBySql(sqlLoad, params);
		return list;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.affair.AuthorizationShareLocal#append(enfo.crm.vo.AuthorizationShareVO)
	 */
	@Override
	public Integer append(AuthorizationShareVO vo) throws BusiException{
		Object[] params = new Object[10];
		int[] outparam = new int[1];
		int[] outtype = new int[1];

		params[0] = Utility.parseInt(vo.getShareType(),null);
		params[1] = Utility.trimNull(vo.getShareDescription());
		params[2] = Utility.parseInt(vo.getStatus(),new Integer(2));
		params[3] = Utility.parseInt(vo.getSourceManagerID(),new Integer(0));
		params[4] = Utility.parseInt(vo.getCa_id(),new Integer(0));
		params[5] = Utility.parseInt(vo.getManagerID(),new Integer(0));
		params[6] = Utility.parseInt(vo.getInput_man(),new Integer(0));
		params[7] = Utility.parseInt(vo.getCust_id(),new Integer(0));
		params[8] = vo.getInvalid_time();
		params[9] = vo.getStart_time();

		outparam[0] = 10;
		outtype[0] = Types.INTEGER;
		
		Object[] obj_val = super.cudProc(sql_add_TAuthorizationShare,params,outparam,outtype);
		return  new Integer(obj_val[0].toString());
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.affair.AuthorizationShareLocal#pagelist_query_authorize(enfo.crm.vo.AuthorizationShareVO, java.lang.String[], int, int)
	 */
	@Override
	public IPageList pagelist_query_authorize(AuthorizationShareVO vo,String[] totalColumn,int pageIndex,int pageSize) throws BusiException{
		IPageList rsList = null;		
		Object[] params = new Object[9];

		params[0] = Utility.parseInt(vo.getSerial_no(),new Integer(0));
		params[1] = new Integer(2);
		params[2] = Utility.trimNull(vo.getShareDescription());
		params[3] = Utility.parseInt(vo.getStatus(),new Integer(0));
		params[4] = Utility.parseInt(vo.getSourceManagerID(),new Integer(0));
		params[5] = Utility.parseInt(vo.getCa_id(),new Integer(0));
		params[6] = Utility.parseInt(vo.getManagerID(),new Integer(0));
		params[7] = Utility.parseInt(vo.getInput_man(),new Integer(0));
		rsList = super.listProcPage(sql_query_TAuthorizationShare,params,totalColumn,pageIndex,pageSize);		
		return rsList;		
	}
	/* (non-Javadoc)
	 * @see enfo.crm.affair.AuthorizationShareLocal#pagelist_query_share(enfo.crm.vo.AuthorizationShareVO, java.lang.String[], int, int)
	 */
	@Override
	public IPageList pagelist_query_share(AuthorizationShareVO vo,String[] totalColumn,int pageIndex,int pageSize) throws BusiException{
		IPageList rsList = null;		
		Object[] params = new Object[9];

		params[0] = Utility.parseInt(vo.getSerial_no(),new Integer(0));
		params[1] = Utility.parseInt(vo.getShareType(),new Integer(0));
		params[2] = Utility.trimNull(vo.getShareDescription());
		params[3] = Utility.parseInt(vo.getStatus(),new Integer(0));
		params[4] = Utility.parseInt(vo.getSourceManagerID(),new Integer(0));
		params[5] = Utility.parseInt(vo.getCa_id(),new Integer(0));
		params[6] = Utility.parseInt(vo.getManagerID(),new Integer(0));
		params[7] = Utility.parseInt(vo.getInput_man(),new Integer(0));
		params[8] = vo.getInvalid_time();
		rsList = super.listProcPage(sql_query_TAuthorizationShare,params,totalColumn,pageIndex,pageSize);		
		return rsList;		
	}
	/* (non-Javadoc)
	 * @see enfo.crm.affair.AuthorizationShareLocal#delete(enfo.crm.vo.AuthorizationShareVO)
	 */
	@Override
	public void delete(AuthorizationShareVO vo) throws BusiException{
		Object[] params = new Object[2];

		params[0] = Utility.parseInt(vo.getSerial_no(),new Integer(0));
		params[1] = Utility.parseInt(vo.getInput_man(),new Integer(0));

		super.cudProc(sql_del_TAuthorizationShare,params);	
	}

	/* (non-Javadoc)
	 * @see enfo.crm.affair.AuthorizationShareLocal#modiStatus(enfo.crm.vo.AuthorizationShareVO)
	 */
	@Override
	public void modiStatus(AuthorizationShareVO vo) throws BusiException{
		Object[] params = new Object[3];

		params[0] = Utility.parseInt(vo.getSerial_no(),new Integer(0));
		params[1] = Utility.parseInt(vo.getStatus(),new Integer(2));
		params[2] = Utility.parseInt(vo.getInput_man(),new Integer(0));

		super.cudProc(sql_modi_TAuthorizationShare_Status,params);	
	}
	/* (non-Javadoc)
	 * @see enfo.crm.affair.AuthorizationShareLocal#modi(enfo.crm.vo.AuthorizationShareVO)
	 */
	@Override
	public void modi(AuthorizationShareVO vo) throws BusiException{
		Object[] params = new Object[9];

		params[0] = Utility.parseInt(vo.getSerial_no(),new Integer(0));
		params[1] = Utility.trimNull(vo.getShareDescription());
		params[2] = Utility.parseInt(vo.getSourceManagerID(),null);
		params[3] = Utility.parseInt(vo.getCa_id(),null);
		params[4] = Utility.parseInt(vo.getManagerID(),null);
		params[5] = Utility.parseInt(vo.getInput_man(),null);
		params[6] = Utility.parseInt(vo.getCust_id(),null);
		params[7] = vo.getInvalid_time();
		params[8] = vo.getStart_time();

		super.cudProc(sql_modi_TAuthorizationShare,params);
	}

}