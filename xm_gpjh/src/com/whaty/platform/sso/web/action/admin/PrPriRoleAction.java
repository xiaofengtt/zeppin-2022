package com.whaty.platform.sso.web.action.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PePriRole;
import com.whaty.platform.entity.bean.PePriority;
import com.whaty.platform.entity.bean.PrPriRole;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class PrPriRoleAction extends MyBaseAction{
	
	private String category;
	private String method;
	private String role;
	
	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false, true);
		this.getGridConfig().setTitle(this.getText("设置权限"));
		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("权限"), "name");
		this.getGridConfig().addColumn(this.getText("权限所属类别"), "pePriCategory.name");
		
		if (method.equals("Category")) {
			this.getGridConfig().addMenuFunction(this.getText("设置权限"),
					"CategorySet");
		}
		if (method.equals("myCategory")) {
			this.getGridConfig().addMenuFunction(this.getText("取消权限"),
					"CategoryDel");
		}
		
		this.getGridConfig().addMenuScript(this.getText("test.back"), "{window.history.back();}");
	}

	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria expcetdc = null;
		ActionContext.getContext().getSession().put("role", role);
		expcetdc = DetachedCriteria.forClass(PrPriRole.class);
		expcetdc.setProjection(Projections.distinct(Property.forName("pePriority.id")));
		expcetdc.createCriteria("pePriRole", "pePriRole");
		expcetdc.createCriteria("pePriority", "pePriority");
		expcetdc.add(Restrictions.eq("pePriRole.id", role));
		
		String sql = "select b.id " +
				"from pe_priority b," +
					"(select a.id aid " +
						"from pe_pri_category a " +
						"connect by prior a.id = a.fk_parent_id " +
						"start with a.id = '"+category+"') c " +
				"where b.fk_pri_cat_id = c.aid";
		List list = null;
		try {
			list = this.getGeneralService().getBySQL(sql);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
		DetachedCriteria dc = DetachedCriteria.forClass(PePriority.class);
		dc.createAlias("pePriCategory", "pePriCategory");
		dc.add(Restrictions.in("id", list));
		if (method.equals("Category")) {
			dc.add(Subqueries.propertyNotIn("id", expcetdc));
		}
		if (method.equals("myCategory")) {
			dc.add(Subqueries.propertyIn("id", expcetdc));
		}
			
		return dc;
	}

	@Override
	public Map updateColumn() {
		Map map = new HashMap();
		String action = this.getColumn();
		String role = ActionContext.getContext().getSession().get("role").toString().trim();
		if (this.getIds() != null && this.getIds().length() > 0) {
			String[] ids = getIds().split(",");
			try {
					DetachedCriteria rodc = DetachedCriteria.forClass(PePriRole.class);
					rodc.add(Restrictions.eq("id", role));
					List<PePriRole> priRolelist = this.getGeneralService().getList(rodc);
					DetachedCriteria peprdc = DetachedCriteria.forClass(PePriority.class);
					peprdc.add(Restrictions.in("id", ids));
					List<PePriority> pePrioritylist = this.getGeneralService().getList(peprdc);
					
					if(priRolelist.size()>0){
						if(pePrioritylist.size()>0){
							if(action.equals("CategorySet")){
								for(int n =0 ;n<pePrioritylist.size();n++){
									PrPriRole priRole = new PrPriRole();
									priRole.setPePriority(pePrioritylist.get(n));
									priRole.setPePriRole(priRolelist.get(0));
									priRole.setFlagIsvalid("1");
									this.getGeneralService().save(priRole);
								}
							}
							if(action.equals("CategoryDel")){
								DetachedCriteria dedc = DetachedCriteria.forClass(PrPriRole.class);
								dedc.createCriteria("pePriority","pePriority");
								dedc.createCriteria("pePriRole","pePriRole");
								dedc.add(Restrictions.eq("pePriRole.id", role));
								dedc.add(Restrictions.in("pePriority.id", ids));
								List<PrPriRole> prilist = this.getGeneralService().getList(dedc);
								if(prilist.size()>0){
									for(int m=0;m<prilist.size();m++){
										this.getGeneralService().delete(prilist.get(m));
									}
								}else{
									map.clear();
									map.put("success", "false");
									map.put("info", "没有此权限");
								}
							}
							map.clear();
							map.put("success", "true");
							map.put("info", ids.length + "条记录操作成功");
							}
					}else{
						map.clear();
						map.put("success", "false");
						map.put("info", "没有当前角色");
					}
			} catch (Exception e) {
				map.clear();
				map.put("success", "false");
				map.put("info", "操作失败");
				return map;
			}
		}else{
			map.clear();
			map.put("success", "false");
			map.put("info", "至少一条数据被选择");
			return map;
		}
		return map;
	}
	
	@Override
	public void setEntityClass() {
		this.entityClass = PePriority.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/sso/admin/prPriRole";
	}
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public PePriority getBean() {
		return (PePriority) super.superGetBean();
	}

	public void setBean(PePriority bean) {
		super.superSetBean(bean);
	}
	
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
