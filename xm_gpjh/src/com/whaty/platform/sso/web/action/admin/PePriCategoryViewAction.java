package com.whaty.platform.sso.web.action.admin;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PePriCategory;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class PePriCategoryViewAction extends MyBaseAction {

	private String role;

	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().setTitle(this.getText("设置权限-权限分类"));
		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("类别名称"), "name");
		this.getGridConfig().addRenderFunction(this.getText("未置权限"), "<a href='prPriRole.action?category=${value}&role="+role+"&method=Category' target='_self'>查看</a>", "id");
		this.getGridConfig().addRenderFunction(this.getText("已有权限"), "<a href='prPriRole.action?category=${value}&role="+role+"&method=myCategory' target='_self'>查看</a>", "id");
		this.getGridConfig().addMenuScript(this.getText("test.back"),
				"{window.history.back();}");
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public void setEntityClass() {
		this.entityClass = PePriCategory.class;
	}

	public void setServletPath() {
		this.servletPath = "/sso/admin/pePriCategoryView";
	}

	public DetachedCriteria initDetachedCriteria() {
		if (this.getSort() == null || this.getSort().equals("id")) {
			this.setSort("code");
			this.setDir("asc");
		}
		DetachedCriteria dc = DetachedCriteria.forClass(PePriCategory.class);
		dc.createAlias("pePriCategory", "pePriCategory",DetachedCriteria.LEFT_JOIN);
		dc.add(Restrictions.isNull("pePriCategory.id"));
		dc.add(Restrictions.sqlRestriction("length({alias}.code) < 3"));
		return dc;
	}

}
