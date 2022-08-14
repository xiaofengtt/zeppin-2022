 
package enfo.crm.contractManage;
import java.sql.Types;
import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.CrmBusiExBean;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.TcoTeamInfoVO;

@Component(value="tcoTeamInfo")
public class TcoTeamInfoBean extends CrmBusiExBean implements TcoTeamInfoLocal {
	
	private static String addSql = "{?=call SP_ADD_TTEAMINFO(?,?,?,?,?)}";
	
	private static String querySql = "{call SP_QUERY_TTEAMINFO(?,?,?,?,?)}";
	
	private static String modiSql = "{?= call SP_MODI_TTEAMINFO(?,?,?,?,?)}";
	
	private static String deleteSql = "{?= call SP_DEL_TTEAMINFO(?,?)}";
	
	/* (non-Javadoc)
	 * @see enfo.crm.contractManage.TcoTeamInfoLocal#append(enfo.crm.vo.TcoTeamInfoVO)
	 */
	@Override
	public Integer append(TcoTeamInfoVO vo) throws BusiException{
		Object[] params = new Object[4];
		
		params[0] = vo.getTeam_name();
		params[1] = vo.getTeam_summary();
		params[2] = vo.getTeam_admin();
		params[3] = vo.getInput_man();		
		return (Integer)super.cudProc(addSql, params, 6, Types.INTEGER);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.contractManage.TcoTeamInfoLocal#queryByPageList(enfo.crm.vo.TcoTeamInfoVO, int, int)
	 */ 
	@Override
	public IPageList queryByPageList(TcoTeamInfoVO vo, int pageIndex, int pageSize)
		throws BusiException{
		Object[] params = new Object[5];
		params[0] = vo.getTeam_id();
		params[1] = vo.getTeam_name();
		params[2] = vo.getTeam_summary();
		params[3] = vo.getTeam_admin_name();
		params[4] = vo.getInput_man();
		return super.listProcPage(querySql,params,pageIndex,pageSize);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.contractManage.TcoTeamInfoLocal#queryByList(enfo.crm.vo.TcoTeamInfoVO)
	 */
	@Override
	public List queryByList(TcoTeamInfoVO vo)throws BusiException{
		Object[] params = new Object[5];
		params[0] = vo.getTeam_id();
		params[1] = vo.getTeam_name();
		params[2] = vo.getTeam_summary();
		params[3] = vo.getTeam_admin_name();
		params[4] = vo.getInput_man();
		return super.listBySql(querySql,params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.contractManage.TcoTeamInfoLocal#modi(enfo.crm.vo.TcoTeamInfoVO)
	 */       
	@Override
	public void modi(TcoTeamInfoVO vo)throws BusiException{
		Object[] params = new Object[5];
		params[0] = vo.getTeam_id();
		params[1] = vo.getTeam_name();
		params[2] = vo.getTeam_summary();
		params[3] = vo.getTeam_admin();
		params[4] = vo.getInput_man();	
		super.cudProc(modiSql,params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.contractManage.TcoTeamInfoLocal#delete(enfo.crm.vo.TcoTeamInfoVO)
	 */
	@Override
	public void delete(TcoTeamInfoVO vo)throws BusiException{
		Object[] params = new Object[2];
		params[0] = vo.getTeam_id();
		params[1] = vo.getInput_man();
		super.cudProc(deleteSql,params);	
	}
}