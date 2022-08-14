 
package enfo.crm.system;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IPageList;
import enfo.crm.tools.Utility;
import enfo.crm.vo.MenuInfoVO;

@Component(value="menuInfo")
public class MenuInfoBean extends enfo.crm.dao.CrmBusiExBean implements MenuInfoLocal {
	private static final String listSql = "{call SP_QUERY_TMENUINFO(?,?)}";//查询菜单
	private static final String modiSql = "{?=call SP_MODI_TMENUINFO(?,?,?,?,?)}";//修改菜单
	private static final String listMenuRight = "{call SP_QUERY_MENURIGHT (?,?,?,?)}";//查询菜单树
	private static final String listMenuInfo = "{call SP_QUERY_TMENUINFO_LOAD(?,?)}";//查询载入的菜单
	private static final String listMenuView = "{call SP_QUERY_TFIELDSDIM(?)}";
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.MenuInfoLocal#listBySig(java.lang.String, java.lang.String)
	 */
	@Override
	public List listBySig(String menu_id,String parent_id) throws BusiException
	{
		List rsList = null;
		Object[] params = new Object[2];
		params[0] = Utility.trimNull(menu_id);
		params[1] = Utility.trimNull(parent_id);
		rsList = super.listProcAll(listSql,params);
		return rsList;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.system.MenuInfoLocal#listByMul(enfo.crm.vo.MenuInfoVO, java.lang.String[], int, int)
	 */
	@Override
	public IPageList listByMul(MenuInfoVO vo,String[] totalColumn,int pageIndex,int pageSize) throws BusiException
	{
		IPageList rsList = null;
		Object[] params = new Object[2];
		params[0] = Utility.trimNull(vo.getMenu_id());
		params[1] = Utility.trimNull(vo.getParent_id());
		rsList = super.listProcPage(listSql,params,totalColumn,pageIndex,pageSize);
		return rsList;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.system.MenuInfoLocal#modi(enfo.crm.vo.MenuInfoVO, java.lang.Integer)
	 */
	@Override
	public void modi(MenuInfoVO vo,Integer input_operatorCode) throws BusiException
	{
		Object[] params = new Object[5];
		
		params[0] = Utility.trimNull(vo.getMenu_id());
		params[1] = Utility.trimNull(vo.getMenu_name());
		params[2] = Utility.trimNull(vo.getMenu_info());
		params[3] = Utility.trimNull(vo.getUrl());
		params[4] = Utility.parseInt(input_operatorCode,new Integer(0));
		
		super.cudProc(modiSql,params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.MenuInfoLocal#listMenuRight(java.lang.Integer, java.lang.String, java.lang.String, java.lang.Integer)
	 */
	@Override
	public List listMenuRight(Integer input_opCode,String menu_id,String parent_id,Integer languageType) throws BusiException
	{
		List rsList = null;
		Object[] params = new Object[4];
		params[0] = Utility.parseInt(input_opCode,null);
		params[1] = Utility.trimNull(menu_id);
		params[2] = Utility.trimNull(parent_id);
		params[3] = Utility.parseInt(languageType, Integer.valueOf("1"));
		rsList = super.listProcAll(listMenuRight,params);
		return rsList;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.MenuInfoLocal#listMenuInfo(java.lang.String, java.lang.Integer)
	 */
	@Override
	public List listMenuInfo(String menu_id,Integer languageType) throws BusiException
	{
		List rsList = null;
		Object[] params = new Object[2];
		params[0] = Utility.trimNull(menu_id);
		params[1] = languageType;
		rsList = super.listProcAll(listMenuInfo,params);
		return rsList;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.MenuInfoLocal#listMenuViews(java.lang.String, java.lang.String)
	 */
	@Override
	public List listMenuViews(String menu_id,String viewStr) throws BusiException{
		List rsList = null;
		Object[] params = new Object[1];
		String temp = "";
		Map rowMap = null;
		Map viewMap = new HashMap();
		params[0] = menu_id;

		rsList = super.listProcAll(listMenuView,params);
		return rsList;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.MenuInfoLocal#listMenuView(java.lang.String, java.lang.String)
	 */
	@Override
	public Map listMenuView(String menu_id,String viewStr) throws BusiException{
		
		List rsList = null;
		Object[] params = new Object[1];
		String temp = "";
		Map rowMap = null;
		Map viewMap = new HashMap();
		params[0] = menu_id;
		
		rsList = super.listProcAll(listMenuView,params);
		Iterator it = rsList.iterator();
		while(it.hasNext()){
			rowMap = (Map)it.next();
			temp = Utility.trimNull(rowMap.get("FIELD"));
			if(viewStr.equals("")||viewStr.indexOf(temp)!=-1){
				viewMap.put(Utility.trimNull(rowMap.get("FIELD")),rowMap);
			}
		}

		return viewMap;
	}
}