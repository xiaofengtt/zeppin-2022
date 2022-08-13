package com.whaty.platform.sso.web.action.admin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.bean.PePriCategory;
import com.whaty.platform.sso.bean.PePriority;
import com.whaty.platform.sso.bean.PrPriRole;
import com.whaty.platform.sso.exception.SsoException;
import com.whaty.platform.sso.service.PePriCategoryService;
import com.whaty.platform.sso.service.PePriorityService;
import com.whaty.platform.sso.service.PrPriRoleService;

public class ChangePriorityManageAction extends MyBaseAction {
	
	private String roleId;
	
	private String roleName;
	
	private PrPriRole bean;
	
	private List rlist;
	
	private List priorityList; //用户拥有权限
	
	private List allList; //该组的所有权限
	
	private PrPriRoleService prPriRoleService;
	
	private PePriorityService pePriorityService;
	
	private PePriCategoryService pePriCategoryService;
	
	private String msg="";
	
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public PePriCategoryService getPePriCategoryService() {
		return pePriCategoryService;
	}

	public void setPePriCategoryService(PePriCategoryService pePriCategoryService) {
		this.pePriCategoryService = pePriCategoryService;
	}

	public PePriorityService getPePriorityService() {
		return pePriorityService;
	}

	public void setPePriorityService(PePriorityService pePriorityService) {
		this.pePriorityService = pePriorityService;
	}

	public List getAllList() {
		return allList;
	}

	public void setAllList(List allList) {
		this.allList = allList;
	}

	public List getPriorityList() {
		return priorityList;
	}

	public void setPriorityList(List priorityList) {
		this.priorityList = priorityList;
	}

	public List getRlist() {
		return rlist;
	}

	public void setRlist(List rlist) {
		this.rlist = rlist;
	}

	public PrPriRole getBean() {
		return bean;
	}

	public void setBean(PrPriRole bean) {
		this.bean = bean;
	}

	public PrPriRoleService getPrPriRoleService() {
		return prPriRoleService;
	}

	public void setPrPriRoleService(PrPriRoleService prPriRoleService) {
		this.prPriRoleService = prPriRoleService;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	@Override
	public Map add() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map delete() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void init() {
		this.setServletPath("/sso/admin/changepriority");
		
	}

	@Override
	public Page list() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map update() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map updateColumn() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 显示更改权限页面
	 */
	public String showPage(){
		
		//显示权限分类列表
		DetachedCriteria dc = DetachedCriteria.forClass(PePriCategory.class);
		dc.add(Restrictions.isNull("pePriCategory"));
		
		List list = getPrPriRoleService().getList(dc);
		setRlist(list);
		
		//权限列表
		DetachedCriteria d = DetachedCriteria.forClass(PrPriRole.class);
		DetachedCriteria d1 = d.createCriteria("pePriRole", "pePriRole");
		DetachedCriteria d2 = d.createCriteria("pePriority", "pePriority");
		DetachedCriteria d3 = d2.createCriteria("pePriCategory","pePriCategory");
		DetachedCriteria d4 = d3.createAlias("pePriCategory", "father");
		d3.addOrder(Order.asc("pePriCategory.name"));
		d2.addOrder(Order.asc("pePriority.name"));
		
		d.add(Restrictions.eq("pePriRole.id", getRoleId()));
//		d.add(Restrictions.eq("father.id","402880c31b7338f5011b733bc5830001"));
		
		List priList = getPrPriRoleService().getList(d);
		setPriorityList(priList);
		
		
		//所有权限 
		DetachedCriteria dd = DetachedCriteria.forClass(PePriority.class);
		dd.createCriteria("pePriCategory","pePriCategory").createCriteria("pePriCategory","fa");
		dd.addOrder(Order.asc("pePriCategory.name"));
//		dd.add(Restrictions.eq("fa.id", "402880c31b7338f5011b733bc5830001"));
		List allList = getPePriorityService().getList(dd);
		setAllList(allList);
		
		HttpServletRequest request = ServletActionContext.getRequest();
		request.getSession().setAttribute("list", list);
		request.getSession().setAttribute("priList", priList);
		request.getSession().setAttribute("allList", allList);
		
		setRoleId(getRoleId());
		setRoleName(getRoleName());
		
		
		
		return "change_group_right";
	}
	
	/**
	* 更改权限
	 */
	
	@SuppressWarnings("unchecked")
	public String changeGroupRight()throws SsoException{
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		DetachedCriteria dc = DetachedCriteria.forClass(PePriCategory.class);
		dc.add(Restrictions.isNull("pePriCategory"));
		
		List list = getPePriCategoryService().getList(dc);
		Iterator t = list.iterator();
		List rightList  = new ArrayList();;
		while(t.hasNext()){
			Set set = ((PePriCategory)t.next()).getPePriCatetories();
			Iterator it = set.iterator();
			PePriCategory priCate = null;
			
			while(it.hasNext()){
				priCate = (PePriCategory)it.next();
				String checkGroup="checkgroup" + priCate.getId();
				String checkright[] = request.getParameterValues(checkGroup);
				if(checkright !=null && checkright.length>0){
					for(String id:checkright){
						rightList.add(id);
					}
				}
			}
		}
		
		
		try{
			getPrPriRoleService().updateChangeRight(rightList, getRoleId());
		}catch(SsoException e){
			throw new SsoException(this.getText("error.sso.errorChangeRight",getRoleId()));
		}
		
		setRoleId(getRoleId());
		setRoleName(getRoleName());
		this.addActionMessage(this.getText("test.update")+this.getText("test.success"));
	return "show_change_right";
	}

	@Override
	public void initGrid() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		
	}
}
