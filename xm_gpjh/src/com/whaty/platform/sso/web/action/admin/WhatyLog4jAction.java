package com.whaty.platform.sso.web.action.admin;

import com.whaty.platform.entity.bean.WhatyuserLog4j;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class WhatyLog4jAction extends MyBaseAction<WhatyuserLog4j>{

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().setTitle(this.getText("查看日志"));

		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("操作人"), "userid");
		this.getGridConfig().addColumn(this.getText("操作时间"), "operateTime");
		this.getGridConfig().addColumn(this.getText("动作"), "behavior");
		this.getGridConfig().addColumn(this.getText("状态"), "status");
		this.getGridConfig().addColumn(this.getText("操作人角色"), "logtype");
		
	}

	@Override
	public void setEntityClass() {
		this.entityClass = WhatyuserLog4j.class;
		
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/sso/admin/whatyLog4j";
		
	}
	
	public void setBean(WhatyuserLog4j instance) {
		super.superSetBean(instance);
	}
	
	public WhatyuserLog4j getBean(){
		return super.superGetBean();
	}

}
