
package enfo.crm.affair;
import java.sql.Types;
import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IPageList;
import enfo.crm.tools.Utility;
import enfo.crm.vo.TmanagerteammembersVO;
import enfo.crm.vo.TmanagerteamsVO;

@Component(value="tmanagerteams")
public class TmanagerteamsBean extends enfo.crm.dao.CrmBusiExBean implements TmanagerteamsLocal {
	
	private static final String sql_add_tmanagerteams = "{?=call SP_ADD_TMANAGERTEAMS(?,?,?,?,?,?,?,?,?,?,?)}";

	private static final String sql_modi_tmanagerteams = "{?=call SP_MODI_TMANAGERTEAMS(?,?,?,?,?,?,?,?,?)}";

	private static final String sql_del_tmanagerteams = "{?=call SP_DEL_TMANAGERTEAMS(?,?)}";

	private static final String sql_query_tmanagerteams = "{call SP_QUERY_TMANAGERTEAMS(?,?,?,?,?,?)}";
	
	private static final String sql_add_tmanagerteammembers = "{?=call SP_ADD_TMANAGERTEAMMEMBERS(?,?,?,?)}";

	private static final String sql_modi_tmanagerteammembers = "{?=call SP_MODI_TMANAGERTEAMMEMBERS(?,?,?,?,?)}";

	private static final String sql_del_tmanagerteammembers = "{?=call SP_DEL_TMANAGERTEAMMEMBERS(?,?)}";

	private static final String sql_query_tmanagerteammembers = "{call SP_QUERY_TMANAGERTEAMMEMBERS(?,?)}";
	
	private static final String sql_query_teams = "{call SP_QUERY_TMANAGERTEAMS_BY_ID(?,?,?,?)}";
	

	/* (non-Javadoc)
	 * @see enfo.crm.affair.TmanagerteamsLocal#append(enfo.crm.vo.TmanagerteamsVO)
	 */
	@Override
	public Integer append(TmanagerteamsVO vo) throws BusiException{
		Object[] params = new Object[10];
		int[] outparam = new int[1];
		int[] outtype = new int[1];

		params[0] = Utility.trimNull(vo.getTeam_no());
		params[1] = Utility.trimNull(vo.getTeam_name());
		params[2] = Utility.parseInt(vo.getCreate_date(),null);
		params[3] = Utility.parseInt(vo.getLeader(),null);
		params[4] = Utility.trimNull(vo.getDescription());
		params[5] = Utility.parseInt(vo.getInput_man(),null);
		params[6] = Utility.parseInt(vo.getMark_flag(),new Integer(2));
		params[7] = Utility.parseInt(vo.getParent_id(),new Integer(0));
		params[8] = Utility.parseInt(vo.getLeader2(),null);	
		params[9] = vo.getLeader3();	
		
		outparam[0] = params.length + 2;
		outtype[0] = Types.INTEGER;
		
		Object[] obj_val = super.cudProc(sql_add_tmanagerteams,params,outparam,outtype);
		return  new Integer(obj_val[0].toString());
	}

	/* (non-Javadoc)
	 * @see enfo.crm.affair.TmanagerteamsLocal#modi(enfo.crm.vo.TmanagerteamsVO)
	 */
	@Override
	public void modi(TmanagerteamsVO vo) throws BusiException{
		Object[] params = new Object[9];

		params[0] = Utility.parseInt(vo.getTeam_id(),new Integer(0));
		params[1] = Utility.trimNull(vo.getTeam_no());
		params[2] = Utility.trimNull(vo.getTeam_name());
		params[3] = Utility.parseInt(vo.getCreate_date(),new Integer(0));
		params[4] = Utility.parseInt(vo.getLeader(),new Integer(0));
		params[5] = Utility.trimNull(vo.getDescription());
		params[6] = Utility.parseInt(vo.getInput_man(),new Integer(0));
		params[7] = Utility.parseInt(vo.getMark_flag(),new Integer(2));
		params[8] = Utility.parseInt(vo.getParent_id(),new Integer(0));
//		params[8] = Utility.parseInt(vo.getLeader2(),new Integer(0));
//		params[9] =vo.getLeader3();
		super.cudProc(sql_modi_tmanagerteams,params);
	}


	/* (non-Javadoc)
	 * @see enfo.crm.affair.TmanagerteamsLocal#delete(enfo.crm.vo.TmanagerteamsVO)
	 */
	@Override
	public void delete(TmanagerteamsVO vo) throws BusiException{
		Object[] params = new Object[2];

		params[0] = Utility.parseInt(vo.getTeam_id(),new Integer(0));
		params[1] = Utility.parseInt(vo.getInput_man(),new Integer(0));

		super.cudProc(sql_del_tmanagerteams,params);	
	}

	/* (non-Javadoc)
	 * @see enfo.crm.affair.TmanagerteamsLocal#pagelist_query(enfo.crm.vo.TmanagerteamsVO, java.lang.String[], int, int)
	 */
	@Override
	public IPageList pagelist_query(TmanagerteamsVO vo,String[] totalColumn,int pageIndex,int pageSize) throws BusiException{
		IPageList rsList = null;		
		Object[] params = new Object[6];

		params[0] = Utility.parseInt(vo.getTeam_id(),new Integer(0));
		params[1] = Utility.trimNull(vo.getTeam_no());
		params[2] = Utility.trimNull(vo.getTeam_name());
		params[3] = Utility.parseInt(vo.getBegin_date(),new Integer(0));
		params[4] = Utility.parseInt(vo.getEnd_date(),new Integer(0));
		params[5] = Utility.trimNull(vo.getLeader_name());
		
		rsList = super.listProcPage(sql_query_tmanagerteams,params,totalColumn,pageIndex,pageSize);		
		return rsList;		
	}
 
	/* (non-Javadoc)
	 * @see enfo.crm.affair.TmanagerteamsLocal#list_query(enfo.crm.vo.TmanagerteamsVO)
	 */
	@Override
	public List list_query(TmanagerteamsVO vo) throws BusiException{
		List list = null;		
		Object[] params = new Object[6];

		params[0] = Utility.parseInt(vo.getTeam_id(),new Integer(0));
		params[1] = Utility.trimNull(vo.getTeam_no());
		params[2] = Utility.trimNull(vo.getTeam_name());
		params[3] = Utility.parseInt(vo.getBegin_date(),new Integer(0));
		params[4] = Utility.parseInt(vo.getEnd_date(),new Integer(0));
		params[5] = Utility.trimNull(vo.getLeader_name());

		list = super.listProcAll(sql_query_tmanagerteams,params);
		
		
		return list;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.affair.TmanagerteamsLocal#append(enfo.crm.vo.TmanagerteammembersVO)
	 */
	@Override
	public void append(TmanagerteammembersVO vo) throws BusiException{
		Object[] params = new Object[4];

		params[0] = Utility.trimNull(vo.getTeam_id());
		params[1] = Utility.trimNull(vo.getManagerid());
		params[2] = Utility.trimNull(vo.getDescription());
		params[3] = Utility.parseInt(vo.getInput_man(),new Integer(0));
//		params[4] = Utility.parseInt(vo.getLeader_query_auth(),new Integer(0));
		
		super.cudProc(sql_add_tmanagerteammembers,params);	
	}

	/* (non-Javadoc)
	 * @see enfo.crm.affair.TmanagerteamsLocal#modi(enfo.crm.vo.TmanagerteammembersVO)
	 */
	@Override
	public void modi(TmanagerteammembersVO vo) throws BusiException{
		Object[] params = new Object[5];

		params[0] = Utility.parseInt(vo.getSerial_no(),new Integer(0));
		params[1] = Utility.trimNull(vo.getTeam_id());
		params[2] = Utility.trimNull(vo.getManagerid());
		params[3] = Utility.trimNull(vo.getDescription());
		params[4] = Utility.parseInt(vo.getInput_man(),new Integer(0));
//		params[5] = Utility.parseInt(vo.getLeader_query_auth(),new Integer(0));

		super.cudProc(sql_modi_tmanagerteammembers,params);
	}


	/* (non-Javadoc)
	 * @see enfo.crm.affair.TmanagerteamsLocal#delete(enfo.crm.vo.TmanagerteammembersVO)
	 */
	@Override
	public void delete(TmanagerteammembersVO vo) throws BusiException{
		Object[] params = new Object[2];

		params[0] = Utility.parseInt(vo.getSerial_no(),new Integer(0));
		params[1] = Utility.parseInt(vo.getInput_man(),new Integer(0));

		super.cudProc(sql_del_tmanagerteammembers,params);	
	}

	/* (non-Javadoc)
	 * @see enfo.crm.affair.TmanagerteamsLocal#pagelist_query(enfo.crm.vo.TmanagerteammembersVO, java.lang.String[], int, int)
	 */
	@Override
	public IPageList pagelist_query(TmanagerteammembersVO vo,String[] totalColumn,int pageIndex,int pageSize) throws BusiException{
		IPageList rsList = null;		
		Object[] params = new Object[2];

		params[0] = Utility.parseInt(vo.getSerial_no(),new Integer(0));
		params[1] = Utility.parseInt(vo.getTeam_id(),new Integer(0));

		rsList = super.listProcPage(sql_query_tmanagerteammembers,params,totalColumn,pageIndex,pageSize);		
		return rsList;		
	}

	/* (non-Javadoc)
	 * @see enfo.crm.affair.TmanagerteamsLocal#list_query(enfo.crm.vo.TmanagerteammembersVO)
	 */
	@Override
	public List list_query(TmanagerteammembersVO vo) throws BusiException{
		List list = null;		
		Object[] params = new Object[2];

		params[0] = Utility.parseInt(vo.getSerial_no(),new Integer(0));
		params[1] = Utility.parseInt(vo.getTeam_id(),new Integer(0));

		list = super.listProcAll(sql_query_tmanagerteammembers,params);
		
		return list;
	}	
	
	/* (non-Javadoc)
	 * @see enfo.crm.affair.TmanagerteamsLocal#list_query1(java.lang.Integer)
	 */
	@Override
	public List list_query1(Integer team_id) throws BusiException{

		List list = null;
		List list1 = null;
		list1 = super.listBySql("SELECT PARENT_ID FROM TManagerTeams WHERE TEAM_ID ="+ team_id);
		if(list1.size()!=0 || !"0".equalsIgnoreCase(list1.get(0).toString())){
			list = super.listBySql("SELECT TEAM_NAME,DESCRIPTION FROM TManagerTeams WHERE PARENT_ID ="+ team_id);
			
		}	
		return list;
	}
	
	
	/* (non-Javadoc)
	 * @see enfo.crm.affair.TmanagerteamsLocal#list_queryOp(java.lang.Integer)
	 */
	@Override
	public List list_queryOp(Integer op_code) throws BusiException{
		List list = null;
		list = super.listBySql("SELECT TEAM_ID,TEAM_NAME FROM TMANAGERTEAMMEMBERS WHERE MANAGERID ="+ op_code);
		return list;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.affair.TmanagerteamsLocal#my_list_query(enfo.crm.vo.TmanagerteammembersVO)
	 */
	@Override
	public List my_list_query(TmanagerteammembersVO vo) throws BusiException{
		
		List list = null;		
		
		Object[] params = new Object[4];
		
		params[0] = Utility.parseInt(vo.getTeam_id(),new Integer(0));
		params[1] = Utility.parseInt(vo.getFlag(),new Integer(0));
		params[2] = Utility.parseInt(vo.getQueryAll(),new Integer(0));
		params[3] = Utility.parseInt(vo.getInput_man(),new Integer(0));
		
		list = super.listProcAll(sql_query_teams,params);
	
		return list;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.affair.TmanagerteamsLocal#quotaMoneyQueryById(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List quotaMoneyQueryById(Integer team_id ,Integer productid) throws BusiException{
		
		List list = null;
		list = super.listBySql("SELECT QUOTAMONEY FROM TTEAMQUOTA WHERE TEAM_ID ="+ team_id +" AND PRODUCT_ID = "+ productid);
		return list;
	}
	

}