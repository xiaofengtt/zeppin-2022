package com.whaty.platform.sso.web.action;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PrPriRole;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.GeneralService;
import com.whaty.platform.entity.web.action.EntityBaseAction;
import com.whaty.platform.sso.web.servlet.UserSession;

public class ManagerMenuAction extends EntityBaseAction{
	
	private String code;
	
	private String leftMenu;
	
	private String topMenu;
	
	private GeneralService generalService;
	
	public GeneralService getGeneralService() {
		return generalService;
	}
	
	public void setGeneralService(GeneralService generalService) {
		this.generalService = generalService;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getLeftMenu(){
		return leftMenu;
	}
	
	public String menu(){
		ActionContext ctx= ActionContext.getContext();
		UserSession userSession = (UserSession)ctx.getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		if (userSession==null) {
			return "menu";
		}
		
		DetachedCriteria dc = DetachedCriteria.forClass(PrPriRole.class);
		dc.createAlias("pePriRole", "pePriRole");
		dc.createCriteria("pePriority", "pePriority").createAlias("pePriCategory", "pePriCategory");
		dc.add(Restrictions.eq("pePriRole.id", userSession.getRoleId()));
		dc.add(Restrictions.eq("flagIsvalid", "1"));
		dc.setProjection(Projections.projectionList().add(Projections.property("pePriCategory.id")));

		List categoryIdList = null;
		try {
			categoryIdList = this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
		StringBuffer ids = new StringBuffer(); 
		for (Object object : categoryIdList) {
			ids.append("'"+(String)object+"',");

		}
		ids.append("''");
		String sql = "select distinct c.acode, nvl(b.code,'0'), c.aname, c.apath " +
						"from pe_pri_category b," +
							"(select distinct a.id           aid," +
											 "a.fk_parent_id afkid," +
											 "a.name         aname," +
											 "a.code         acode," +
											 "a.path         apath " +
							  "from pe_pri_category a " +
							  "where a.flag_left_menu = '1' " +
							  "connect by a.id = prior a.fk_parent_id " +
							  "start with a.id in ("+ids.toString()+")) c " +
						"where b.id(+) = c.afkid " +
						"order by c.acode";
		List leftMenus = null;

		try {
			leftMenus = this.getGeneralService().getBySQL(sql);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
		StringBuffer menuString = new StringBuffer();
		for (int i = 0; i < leftMenus.size(); i++) {
			Object[] menu = (Object[])leftMenus.get(i);
			
			menuString.append("add_item("+(String)menu[0]+","+(String)menu[1]+",\""+(String)menu[2]+"\",");
			if(((String)menu[3]) == null || ((String)menu[3]).equals(""))
				menuString.append("\"/entity/manager/pub/tree/js/tree/close.gif\",\"/entity/manager/pub/tree/js/tree/open.gif\",\"#\",\"\");\n");
			else{
				menuString.append("\"/entity/manager/pub/tree/js/tree/parenticon.gif\",\"/entity/manager/pub/tree/js/tree/parenticon.gif\",");
				menuString.append(((String)menu[3]).equals("to_top_menu")? "\"/sso/managerMenu_buildTopMenu.action?code="+(String)menu[0]+"\",\"menu\",\"/entity/manager/pub/loading.htm\");":"\"\",\"menu\",\""+(String)menu[3]+"\");\n");
			}
		}
		leftMenu = menuString.toString();
		

		return "menu";
	}
	
	public String buildTopMenu() {
		
		List topMenus = null;
		ActionContext ctx= ActionContext.getContext();
		UserSession userSession = (UserSession)ctx.getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		if (userSession==null) {
			return "input";
		}
		DetachedCriteria dc = DetachedCriteria.forClass(PrPriRole.class);
		dc.createAlias("pePriRole", "pePriRole");
		dc.createCriteria("pePriority", "pePriority").createCriteria("pePriCategory", "pePriCategory").createCriteria("pePriCategory", "pePriCategory1");
		dc.add(Restrictions.eq("pePriRole.id", userSession.getRoleId()));
		dc.add(Restrictions.eq("flagIsvalid", "1"));
		dc.add(Restrictions.eq("pePriCategory.flagLeftMenu", "0"));
		dc.add(Restrictions.eq("pePriCategory1.code", code));
		dc.setProjection(Projections.projectionList().add(Projections.distinct(Projections.property("pePriCategory.code"))).add(Projections.property("pePriCategory.name")).add(Projections.property("pePriCategory.path")));
		dc.addOrder(Order.asc("pePriCategory.code"));
		try {
			topMenus = this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		if(topMenus==null||topMenus.isEmpty()){
			topMenu ="您没有访问权限 请关闭或者注销模拟登录！\n";
			topMenu += "<script>top.openPage('/entity/manager/pub/menu.htm','');</script>";
			return "topMenu";
		}
		for (Object object : topMenus) {
			Object[] obj = (Object[])object;
			if(topMenu == null)
				topMenu = "";
			topMenu += "<div id=\""+(String)obj[0]+"\" class=\"tab_menu2\" onClick=\"parent.openPage('"+(String)obj[2]+"',this.id)\" title=\""+(String)obj[1]+"\">"+(String)obj[1]+"</div> \n";
		}
		Object[] obj = (Object[])topMenus.get(0);
		topMenu += "<script>top.openPage('"+(String)obj[2]+"','"+(String)obj[0]+"');</script>";
		
		return "topMenu";
	}

	public String getTopMenu() {
		return topMenu;
	}

	public void setTopMenu(String topMenu) {
		this.topMenu = topMenu;
	}

	
	

}
