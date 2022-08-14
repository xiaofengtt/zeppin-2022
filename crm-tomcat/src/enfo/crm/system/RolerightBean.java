 
package enfo.crm.system;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.CrmDBManager;
import enfo.crm.dao.IPageList;
import enfo.crm.tools.Utility;
import enfo.crm.vo.RolerightVO;
import sun.jdbc.rowset.CachedRowSet;

@Component(value="roleright")
public class RolerightBean extends enfo.crm.dao.CrmBusiExBean implements RolerightLocal {
	
	private static final String roleRightSql = "{call SP_QUERY_TROLERIGHT(?)}";//查询TROLERIGHT中信息
	private static final String menu_infoSql = "{call SP_QUERY_TMENUINFO(?,?)}";//查询MENUINFO中的信息
	private static final String func_typeSql = "{call SP_QUERY_TFUNCTYPE(?,?)}";//查询TFUNCTYPE中的信息
	
	private Hashtable menuTable = new Hashtable();
	private Hashtable funcTable = new Hashtable();
	private CachedRowSet functype_rowset;
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.RolerightLocal#listRoleRight(java.lang.Integer)
	 */
	@Override
	public List listRoleRight(Integer role_id) throws Exception{	
		String menu_id;
		Integer func_id;
		List list = null;
		Object[] params = new Object[1];
		params[0] = Utility.parseInt(role_id,null);
		list = super.listBySql(roleRightSql,params);
		menuTable.clear();
		funcTable.clear();
		
		for(int i=0;i<list.size();i++)
		{
			Map map = (Map)list.get(i);
			menu_id = Utility.trimNull(map.get("MENU_ID"));
			func_id = Utility.parseInt(Utility.trimNull(map.get("FUNC_ID")),null);
			menuTable.put(menu_id, menu_id);
			funcTable.put(menu_id + func_id.toString(), menu_id + func_id.toString());
		}
		return list;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.RolerightLocal#listRole(java.lang.Integer)
	 */
	@Override
	public List listRole(Integer role_id)throws BusiException{
		List list = null;
		Object[] param = new Object[1];
		param[0] = role_id;
		list = super.listBySql(roleRightSql,param);
		return list;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.RolerightLocal#listProcAll(enfo.crm.vo.RolerightVO, int, int)
	 */
	@Override
	public IPageList listProcAll(RolerightVO vo,int pageIndex,int pageSize)throws BusiException{
		IPageList pageList = null;
		Object[] params = new Object[2];
		params[0] = Utility.trimNull(vo.getMenu_id());
		params[1] = Utility.trimNull(vo.getParent_id());
		pageList = super.listProcPage(menu_infoSql,params,pageIndex,pageSize);
		return pageList;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.RolerightLocal#listFunc_type(java.lang.String)
	 */
	@Override
	public List listFunc_type(String menu_id)throws BusiException{
		List list = null;
		Object[] params = new Object[2];
		params[0] = null;
		params[1] = Utility.trimNull(menu_id);
		list = super.listBySql(func_typeSql,params);
		return list;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.RolerightLocal#hasMenu(java.lang.String)
	 */
	@Override
	public boolean hasMenu(String menu_id)
	{
		if (menu_id == null)
			return false;
		if (menuTable == null)
			return false;
		return menuTable.contains(menu_id);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.RolerightLocal#hasFunc(java.lang.String, java.lang.Integer)
	 */
	@Override
	public boolean hasFunc(String menu_id, Integer func_id)
	{
		if (menu_id == null)
			return false;
		if (menuTable == null)
			return false;
		if (func_id == null)
			return false;
		if (funcTable == null)
			return false;
		return funcTable.contains(menu_id + func_id);
	}
	
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.RolerightLocal#update(java.lang.Integer, java.lang.String, java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */	
		@Override
		public void update(Integer roleid, String menuid, Integer funcid,Integer input_name, Integer flag) throws Exception
		{
			int ret;
				
			try
			{
				Connection conn = CrmDBManager.getConnection();
				CallableStatement stmt = null;
				switch (flag.intValue())
				{
					case 0 :
						stmt = conn.prepareCall( "{call SP_DEL_TROLERIGHT(?,?,?,?)}");
						break;
					case 1 :
						stmt = conn.prepareCall("{call SP_ADD_TROLERIGHT(?,?,?,?)}");
						break;

					default :
						throw new BusiException("无此操作！");
				}
				stmt.setInt(1, roleid.intValue());
				stmt.setString(2, menuid);
				stmt.setInt(3, funcid.intValue());
				stmt.setInt(4, input_name.intValue());
				stmt.executeUpdate();
			}
			catch (Exception e)
			{
				throw new BusiException("权限更新失败！"+e.getMessage());
			}
		}
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.RolerightLocal#listFuncType(java.lang.String)
	 */
	@Override
	public void listFuncType(String menuid) throws Exception{
			Connection conn = CrmDBManager.getConnection();
			CallableStatement stmt =
				conn.prepareCall("{call SP_QUERY_TFUNCTYPE(?,?)}", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			stmt.setInt(1, 0);
			stmt.setString(2, menuid);

			ResultSet rslist = stmt.executeQuery();
			try
			{
				functype_rowset.close();
				functype_rowset.populate(rslist);
			}
			finally
			{
				rslist.close();
				stmt.close();
				conn.close();
			}
		}
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.RolerightLocal#listAll2(enfo.crm.vo.RolerightVO, int, int)
	 */
	@Override
	public IPageList listAll2(RolerightVO vo,int pageIndex,int pageSize)throws BusiException{
		IPageList pageList = null;
		Object[] params = new Object[2];
		params[0] = Utility.trimNull(vo.getMenu_id());
		params[1] = Utility.trimNull(vo.getMenu_name());
		pageList = super.listProcPage("{call SP_QUERY_ALLCHILDREN_TMENUINFO(?,?)}"
									,params
									,pageIndex
									,pageSize);
		return pageList;
	}
}