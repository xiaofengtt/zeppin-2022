package com.whaty.platform.sso.web.action.admin;



import org.hibernate.criterion.DetachedCriteria;

import com.whaty.platform.entity.bean.PeEdutype;
import com.whaty.platform.entity.bean.PePriCategory;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class PePriCategoryManageAction extends MyBaseAction<PePriCategory> {
	
	
	@Override
	public void initGrid() {
		this.getGridConfig().setTitle(this.getText("pricatetory.gridtitle"));

		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("类别名称"), "name");
		this.getGridConfig().addColumn(this.getText("类别编号"), "code");
		this.getGridConfig().addColumn(this.getText("路径"), "path", true, true, true, "TextField", true, 500);
		this.getGridConfig().addColumn(this.getText("是否为左菜单"), "flagLeftMenu", true, true, false, "", false, 1);
		this.getGridConfig().addRenderScript("是否为左菜单", "{if(${value}=='1') return '是'; else return '否';}", "flagLeftMenu");
		this.getGridConfig().addColumn(this.getText("所属分类"), "pePriCategory.name",true,true,true,"",true,30);
		this.getGridConfig().addMenuScript(this.getText("test.back"), "{window.history.back();}");
		
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PePriCategory.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/sso/admin/pricatetory";
	}

	public void setBean(PePriCategory instance) {
		super.superSetBean(instance);
	}
	
	public PePriCategory getBean(){
		return super.superGetBean();
	}

	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(entityClass);
		dc.createCriteria("pePriCategory", "pePriCategory", DetachedCriteria.LEFT_JOIN);
		return dc;
	}
}
