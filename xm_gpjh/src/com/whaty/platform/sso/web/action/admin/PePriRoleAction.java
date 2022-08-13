package com.whaty.platform.sso.web.action.admin;

import org.hibernate.criterion.DetachedCriteria;

import com.whaty.platform.entity.bean.PePriRole;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class PePriRoleAction extends MyBaseAction<PePriRole> {

	public PePriRole getBean() {
		return super.superGetBean();
	}

	public void setBean(PePriRole bean) {
		this.superSetBean(bean);
	}

	@Override
	public void initGrid() {
		this.getGridConfig().setTitle(this.getText("角色管理"));

		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("角色名称"), "name");
		this.getGridConfig().addColumn(this.getText("所属工作室"), "enumConstByFlagRoleType.name");
		this.getGridConfig().addColumn(this.getText("所属工作室"), "enumConstByFlagRoleType.code", false, false, false, "", false, 0);
		this.getGridConfig().addRenderFunction(this.getText("已有管理员用户"), "<a href='pePriRoleUser.action?id=\"+record.data['id']+\"&type=\"+record.data['enumConstByFlagRoleType.code']+\"' target='_self'>查看</a>");
		this.getGridConfig().addRenderFunction(this.getText("权限管理"), "<a href='pePriCategoryView.action?role=${value}' target='_self'>设置</a>", "id");
		this.getGridConfig().addMenuScript(this.getText("test.back"), "{window.history.back();}");
		
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PePriRole.class;
		 
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/sso/admin/pePriRole";
		
	}

	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PePriRole.class);
		dc.createAlias("enumConstByFlagRoleType", "enumConstByFlagRoleType");
		return dc;
	}
	

}
