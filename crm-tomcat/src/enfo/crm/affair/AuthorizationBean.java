 
package enfo.crm.affair;
import java.sql.Types;
import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IPageList;
import enfo.crm.tools.Utility;
import enfo.crm.vo.AuthorizationCustsVO;
import enfo.crm.vo.AuthorizationVO;

@Component(value="authorization")
public class AuthorizationBean extends enfo.crm.dao.CrmBusiExBean implements AuthorizationLocal {
	private static final String sql_add_TAuthorization = "{?=call SP_ADD_TAuthorization(?,?,?,?,?)}";

	private static final String sql_modi_TAuthorization = "{?=call SP_MODI_TAuthorization(?,?,?,?,?)}";

	private static final String sql_del_TAuthorization = "{?=call SP_DEL_TAuthorization(?,?)}";

	private static final String sql_query_TAuthorization = "{call SP_QUERY_TAuthorization(?,?,?,?,?)}";
	
	private static final String sql_add_TAuthorizationCusts = "{?=call SP_ADD_TAuthorizationCusts(?,?,?,?)}";

	private static final String sql_modi_TAuthorizationCusts = "{?=call SP_MODI_TAuthorizationCusts(?,?,?,?,?)}";

	private static final String sql_del_TAuthorizationCusts = "{?=call SP_DEL_TAuthorizationCusts(?,?)}";

	private static final String sql_query_TAuthorizationCusts = "{call SP_QUERY_TAuthorizationCusts(?)}";
	

	/* (non-Javadoc)
	 * @see enfo.crm.affair.AuthorizationLocal#append(enfo.crm.vo.AuthorizationVO)
	 */
	@Override
	public Integer append(AuthorizationVO vo) throws BusiException{
		Object[] params = new Object[4];
		int[] outparam = new int[1];
		int[] outtype = new int[1];

		params[0] = Utility.trimNull(vo.getCa_name());
		params[1] = Utility.trimNull(vo.getCa_description());
		params[2] = Utility.parseInt(vo.getManagerID(),null);
		params[3] = Utility.parseInt(vo.getInput_man(),null);

		outparam[0] = params.length + 2;
		outtype[0] = Types.INTEGER;
		
		Object[] obj_val = super.cudProc(sql_add_TAuthorization,params,outparam,outtype);
		return  new Integer(obj_val[0].toString());
	}
	//改成无返回值的
//	public void append(AuthorizationVO vo) throws BusiException{
//		Object[] params = new Object[4];
//
//		params[0] = Utility.trimNull(vo.getCa_name());
//		params[1] = Utility.trimNull(vo.getCa_description());
//		params[2] = Utility.parseInt(vo.getManagerID(),null);
//		params[3] = Utility.parseInt(vo.getInput_man(),null);
//		
//		super.cudProc(sql_add_TAuthorization,params);
//	}

	/* (non-Javadoc)
	 * @see enfo.crm.affair.AuthorizationLocal#modi(enfo.crm.vo.AuthorizationVO)
	 */
	@Override
	public void modi(AuthorizationVO vo) throws BusiException{
		Object[] params = new Object[5];

		params[0] = Utility.parseInt(vo.getCa_id(),new Integer(0));
		params[1] = Utility.trimNull(vo.getCa_name());
		params[2] = Utility.trimNull(vo.getCa_description());
		params[3] = Utility.parseInt(vo.getManagerID(),null);
		params[4] = Utility.parseInt(vo.getInput_man(),null);

		super.cudProc(sql_modi_TAuthorization,params);
	}


	/* (non-Javadoc)
	 * @see enfo.crm.affair.AuthorizationLocal#delete(enfo.crm.vo.AuthorizationVO)
	 */
	@Override
	public void delete(AuthorizationVO vo) throws BusiException{
		Object[] params = new Object[2];

		params[0] = Utility.parseInt(vo.getCa_id(),new Integer(0));
		params[1] = Utility.parseInt(vo.getInput_man(),new Integer(0));

		super.cudProc(sql_del_TAuthorization,params);	
	}

	/* (non-Javadoc)
	 * @see enfo.crm.affair.AuthorizationLocal#pagelist_query(enfo.crm.vo.AuthorizationVO, java.lang.String[], int, int)
	 */
	@Override
	public IPageList pagelist_query(AuthorizationVO vo,String[] totalColumn,int pageIndex,int pageSize) throws BusiException{
		IPageList rsList = null;		
		Object[] params = new Object[5];

		params[0] = Utility.parseInt(vo.getCa_id(),new Integer(0));
		params[1] = Utility.trimNull(vo.getCa_name());
		params[2] = Utility.trimNull(vo.getCa_description());
		params[3] = Utility.parseInt(vo.getManagerID(),new Integer(0));
		params[4] = Utility.parseInt(vo.getInput_man(),new Integer(0));
		rsList = super.listProcPage(sql_query_TAuthorization,params,totalColumn,pageIndex,pageSize);		
		return rsList;		
	}

	/* (non-Javadoc)
	 * @see enfo.crm.affair.AuthorizationLocal#list_query(enfo.crm.vo.AuthorizationVO)
	 */
	@Override
	public List list_query(AuthorizationVO vo) throws BusiException{
		List list = null;		
		Object[] params = new Object[5];

		params[0] = Utility.parseInt(vo.getCa_id(),new Integer(0));
		params[1] = Utility.trimNull(vo.getCa_name());
		params[2] = Utility.trimNull(vo.getCa_description());
		params[3] = Utility.parseInt(vo.getManagerID(),new Integer(0));
		params[4] = Utility.parseInt(vo.getInput_man(),new Integer(0));

		list = super.listProcAll(sql_query_TAuthorization,params);
		return list;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.affair.AuthorizationLocal#append(enfo.crm.vo.AuthorizationCustsVO)
	 */
	@Override
	public void append(AuthorizationCustsVO vo) throws BusiException{
		Object[] params = new Object[4];

		params[0] = Utility.parseInt(vo.getCa_id(),new Integer(0));
		params[1] = Utility.parseInt(vo.getCust_id(),new Integer(0));
		params[2] = Utility.parseInt(vo.getInput_man(),new Integer(0));
		params[3] = Utility.parseInt(vo.getAuth_flag(), new Integer(0));
		super.cudProc(sql_add_TAuthorizationCusts,params);	
	}

	/**
	 * @ejb.interface-method view-type = "local"
	 * 修改客户授权集-成员管理
	 * @param vo
	 * 	@IN_SERIAL_NO	int
		@IN_TEAM_ID	nvarchar
		@IN_MANAGERID	nvarchar
		@IN_DESCRIPTION	nvarchar
		@IN_INPUT_MAN	int
	 */
//	public void modi(AuthorizationCustsVO vo) throws BusiException{
//		Object[] params = new Object[5];
//
//		params[0] = Utility.parseInt(vo.getSerial_no(),new Integer(0));
//		params[1] = Utility.trimNull(vo.getTeam_id());
//		params[2] = Utility.trimNull(vo.getManagerid());
//		params[3] = Utility.trimNull(vo.getDescription());
//		params[4] = Utility.parseInt(vo.getInput_man(),new Integer(0));
//
//		super.cudProc(sql_modi_TAuthorizationCusts,params);
//	}


	/* (non-Javadoc)
	 * @see enfo.crm.affair.AuthorizationLocal#delete(enfo.crm.vo.AuthorizationCustsVO)
	 */
	@Override
	public void delete(AuthorizationCustsVO vo) throws BusiException{
		Object[] params = new Object[2];

		params[0] = Utility.parseInt(vo.getSerial_no(),new Integer(0));
		params[1] = Utility.parseInt(vo.getInput_man(),new Integer(0));

		super.cudProc(sql_del_TAuthorizationCusts,params);	
	}

	/* (non-Javadoc)
	 * @see enfo.crm.affair.AuthorizationLocal#pagelist_query(enfo.crm.vo.AuthorizationCustsVO, java.lang.String[], int, int)
	 */
	@Override
	public IPageList pagelist_query(AuthorizationCustsVO vo,String[] totalColumn,int pageIndex,int pageSize) throws BusiException{
		IPageList rsList = null;		
		Object[] params = new Object[1];

		params[0] = Utility.parseInt(vo.getCa_id(),new Integer(0));

		rsList = super.listProcPage(sql_query_TAuthorizationCusts,params,totalColumn,pageIndex,pageSize);		
		return rsList;		
	}

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询客户授权集-成员管理
		@IN_SERIAL_NO	int
		@IN_TEAM_ID	int
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
//	public List list_query(AuthorizationCustsVO vo) throws BusiException{
//		List list = null;		
//		Object[] params = new Object[2];
//
//		params[0] = Utility.parseInt(vo.getSerial_no(),new Integer(0));
//		params[1] = Utility.parseInt(vo.getTeam_id(),new Integer(0));
//
//		list = super.listProcAll(sql_query_TAuthorizationCusts,params);
//		return list;
//	}	
}