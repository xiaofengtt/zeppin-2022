package com.whaty.platform.sso.web.action.admin;

import org.hibernate.criterion.DetachedCriteria;

import com.whaty.platform.entity.bean.PePriCategory;
import com.whaty.platform.entity.bean.PePriority;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class PePriorityManageAction extends MyBaseAction<PePriority	> {

	
	
	@Override
	public void initGrid() {
		this.getGridConfig().setTitle(this.getText("prioritys.gridtitle"));

		this.getGridConfig().addColumn(this.getText("priority.id"), "id", false);
		this.getGridConfig().addColumn(this.getText("priority.name"), "name");
		this.getGridConfig().addColumn(this.getText("priority.category"), "pePriCategory.name");
		this.getGridConfig().addColumn(this.getText("priority.namespace"), "namespace");
		this.getGridConfig().addColumn(this.getText("priority.action"), "action");
		this.getGridConfig().addColumn(this.getText("priority.method"), "method");
		this.getGridConfig().addMenuScript(this.getText("test.back"), "{window.history.back();}");
		
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PePriority.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/sso/admin/priority";
	}
	
	public void setBean(PePriority instance) {
		super.superSetBean(instance);
	}
	
	public PePriority getBean(){
		return super.superGetBean();
	}

	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(entityClass);
		dc.createAlias("pePriCategory", "pePriCategory");
		return dc;
	}

}
