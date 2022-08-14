 
package enfo.crm.contractManage;
import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.CrmBusiExBean;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.TcoTeamMemberVO;

@Component(value="tcoTeamMember")
public class TcoTeamMemberBean extends CrmBusiExBean implements TcoTeamMemberLocal {
	
	private static String addSql = "{?=call SP_ADD_TTEAMMEMBER(?,?,?,?)}";
	
	private static String querySql = "{call SP_QUERY_TTEAMMEMBER(?,?)}";
	
	private static String modiSql = "{?= call SP_MODI_TTEAMMEMBER(?,?,?)}";
	
	private static String deleteSql = "{?= call SP_DEL_TTEAMMEMBER(?,?)}";
	
	
	/* (non-Javadoc)
	 * @see enfo.crm.contractManage.TcoTeamMemberLocal#append(enfo.crm.vo.TcoTeamMemberVO)
	 */
	@Override
	public void append(TcoTeamMemberVO vo) throws BusiException{
		Object[] params = new Object[4];
		
		params[0] = vo.getTeam_id();
		params[1] = vo.getTeam_member();
		params[2] = vo.getTeam_position();
		params[3] = vo.getInput_man();		
		super.cudProc(addSql, params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.contractManage.TcoTeamMemberLocal#queryByPageList(enfo.crm.vo.TcoTeamMemberVO, int, int)
	 */
	@Override
	public IPageList queryByPageList(TcoTeamMemberVO vo, int pageIndex, int pageSize)
		throws BusiException{
		Object[] params = new Object[2];
		params[0] = vo.getSerial_no();
		params[1] = vo.getTeam_id();
		return super.listProcPage(querySql,params,pageIndex,pageSize);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.contractManage.TcoTeamMemberLocal#queryByList(enfo.crm.vo.TcoTeamMemberVO)
	 */
	@Override
	public List queryByList(TcoTeamMemberVO vo)throws BusiException{
		Object[] params = new Object[2];
		params[0] = vo.getSerial_no();
		params[1] = vo.getTeam_id();
		return super.listBySql(querySql,params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.contractManage.TcoTeamMemberLocal#modi(enfo.crm.vo.TcoTeamMemberVO)
	 */      
	@Override
	public void modi(TcoTeamMemberVO vo)throws BusiException{
		Object[] params = new Object[3];
		params[0] = vo.getSerial_no();
		params[1] = vo.getTeam_position();
		params[2] = vo.getInput_man();		
		super.cudProc(modiSql,params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.contractManage.TcoTeamMemberLocal#delete(enfo.crm.vo.TcoTeamMemberVO)
	 */
	@Override
	public void delete(TcoTeamMemberVO vo)throws BusiException{
		Object[] params = new Object[2];
		params[0] = vo.getSerial_no();
		params[1] = vo.getInput_man();
		super.cudProc(deleteSql,params);	
	}
	
}