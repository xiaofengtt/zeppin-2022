 
package enfo.crm.system;
import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IPageList;
import enfo.crm.tools.Utility;
import enfo.crm.vo.RoleVO;

@Component(value="role")
public class RoleBean extends enfo.crm.dao.CrmBusiExBean implements RoleLocal {
	
	private String listSql = "{call SP_QUERY_TROLE(?,?)}";//查询角色信息表
	private String appendSql = "{?=call SP_ADD_TROLE(?,?,?,?,?,?)}";//新增角色信息表
	private String modiSql = "{?=call SP_MODI_TROLE(?,?,?,?,?,?)}";//修改角色信息表
	private String deleteSql = "{?=call SP_DEL_TROLE(?,?)}";//删除角色信息表
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.RoleLocal#listBySql(enfo.crm.vo.RoleVO)
	 */
	@Override
	public List listBySql(RoleVO role)throws BusiException{
		List list = null;
		Object[] params = new Object[2];
		params[0] = Utility.parseInt(role.getRole_id(),new Integer(0));
		params[1] = Utility.trimNull(role.getRole_name());
		list = super.listBySql(listSql,params);
		return list;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.RoleLocal#append(enfo.crm.vo.RoleVO, java.lang.Integer)
	 */
	@Override
	public void append(RoleVO role,Integer input_operatorCode)throws BusiException{
		Object[]param = new Object[6];
		param[0] = Utility.parseInt(role.getRole_id(),new Integer(0));
		param[1] = Utility.trimNull(role.getRole_name());
		param[2] = Utility.trimNull(role.getSummary());
		param[3] = Utility.parseInt(role.getFlag_access_all(),new Integer(0));
		param[4] = Utility.parseInt(role.getFlag_encryption(),new Integer(0));
		param[5] = Utility.parseInt(input_operatorCode,new Integer(0));
		super.cudProc(appendSql,param);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.RoleLocal#modi(enfo.crm.vo.RoleVO, java.lang.Integer)
	 */
	@Override
	public void modi(RoleVO role,Integer input_operatorCode)throws BusiException{
		Object[]param = new Object[6];
		param[0] = Utility.parseInt(role.getRole_id(),new Integer(0));
		param[1] = Utility.trimNull(role.getRole_name());
		param[2] = Utility.trimNull(role.getSummary());	
		param[3] = Utility.parseInt(role.getFlag_access_all(),new Integer(0));
		param[4] = Utility.parseInt(role.getFlag_encryption(),new Integer(0));
		param[5] = Utility.parseInt(input_operatorCode,new Integer(0));
		super.cudProc(modiSql,param);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.RoleLocal#delete(enfo.crm.vo.RoleVO, java.lang.Integer)
	 */
	@Override
	public void delete(RoleVO role,Integer input_operatorCode)throws BusiException{
		Object[]params = new Object[2];
		params[0] = Utility.parseInt(role.getRole_id(),new Integer(0));
		params[1] =Utility.parseInt(input_operatorCode,new Integer(0));
		super.cudProc(deleteSql,params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.RoleLocal#listByMul(enfo.crm.vo.RoleVO, java.lang.String[], int, int)
	 */
	@Override
	public IPageList listByMul(RoleVO vo,String[] totalColumn,int pageIndex,int pageSize) throws BusiException{
			IPageList rsList = null;
			Object[] params = new Object[2];
			params[0] = Utility.parseInt(vo.getRole_id(),null);
			params[1] = Utility.trimNull(vo.getRole_name());
			rsList = super.listProcPage(listSql,params,totalColumn,pageIndex,pageSize);
			return rsList;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.RoleLocal#updateRights(enfo.crm.vo.RoleVO, java.lang.String, int, java.lang.String)
	 */
	@Override
	public void updateRights(RoleVO vo,String menu_id, int flag,String menu_name) throws Exception{
			Object[] oparams = new Object[5];
			oparams[0] = Utility.parseInt(vo.getRole_id(),null);
			oparams[1] = Utility.trimNull(menu_id);
			oparams[2] = Utility.trimNull(menu_name);
			oparams[3] = new Integer(flag);
			oparams[4] = Utility.parseInt(vo.getInput_man(),null);
			super.cudProc("{?=call SP_ADD_TROLERIGHT_ALL(?,?,?,?,?,?)}",oparams,7,java.sql.Types.INTEGER);
	}	
}