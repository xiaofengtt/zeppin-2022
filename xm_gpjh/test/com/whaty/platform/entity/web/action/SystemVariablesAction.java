package com.whaty.platform.entity.web.action;
import java.util.regex.Pattern;

import org.hibernate.criterion.DetachedCriteria;

import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.SystemVariables;
import com.whaty.platform.entity.exception.EntityException;


public class SystemVariablesAction extends MyBaseAction<SystemVariables> {

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(true, true, true);
		
		this.getGridConfig().setTitle(this.getText("SystemVariables管理"));
		this.getGridConfig().addColumn(this.getText("id"), "id");
		this.getGridConfig().addColumn(this.getText("名称"), "name");
		this.getGridConfig().addColumn(this.getText("变量值"),"value");
		this.getGridConfig().addColumn(this.getText("正则"),"pattern");
//		this.getGridConfig().addColumn(this.getText("所属模块"),"enumConstByFlagPlatformSection.name");
	}
	
	@Override
	public void setEntityClass() {
		this.entityClass = SystemVariables.class;

	}

	@Override
	public void setServletPath() {
		this.servletPath = "/test/systemVariables";

	}

	public void setBean(SystemVariables instance) {
		super.superSetBean(instance);
	}
	
	public SystemVariables getBean(){
		return super.superGetBean();
	}
	
	@Override
	public void checkBeforeAdd() throws EntityException {
		//TODO: 检查代码未实现。张羽 2008-11-22
		/* 
		String regEx = this.getBean().getPattern();
		if (Pattern.compile(regEx).matcher(this.getBean().getValue()).find()) {
			throw new EntityException("");
		}
		*/
	}

	@Override
	public void checkBeforeUpdate() throws EntityException {
		this.checkBeforeAdd();
	}

	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(SystemVariables.class);
//		dc.createCriteria("enumConstByFlagPlatformSection", "enumConstByFlagPlatformSection");
		return dc;
	}

}
