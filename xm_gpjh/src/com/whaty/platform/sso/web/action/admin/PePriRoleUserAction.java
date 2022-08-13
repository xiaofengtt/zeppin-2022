package com.whaty.platform.sso.web.action.admin;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeManager;
import com.whaty.platform.entity.bean.PeTrainee;
import com.whaty.platform.entity.bean.PeValuaExpert;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class PePriRoleUserAction extends MyBaseAction {
	
	private String type;
	private String id;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false, true);
		this.getGridConfig().setTitle(this.getText("查看用户"));
		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("姓名"), "name");
		this.getGridConfig().addColumn(this.getText("用户名"), "ssoUser.loginId");
		this.getGridConfig().addMenuScript(this.getText("test.back"), "{window.history.back();}");
	}

	@Override
	public void setEntityClass() {
		
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/sso/admin/pePriRoleUser";
		
	}
	
	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc ;
		if("0".equals(type)){
			dc = DetachedCriteria.forClass(PeTrainee.class);//学员
		}else if("1".equals(type)){
			dc = DetachedCriteria.forClass(PeValuaExpert.class);//评审专家
		}else {
			dc = DetachedCriteria.forClass(PeManager.class);//承办单位 项目执行办  师范司执行办
		}
		dc.createCriteria("ssoUser", "ssoUser").createAlias("pePriRole", "pePriRole");
		dc.add(Restrictions.eq("pePriRole.id", id));
		return dc;
	}


}
