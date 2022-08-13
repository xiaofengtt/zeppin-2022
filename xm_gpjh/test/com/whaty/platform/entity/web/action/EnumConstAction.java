package com.whaty.platform.entity.web.action;

import com.whaty.platform.entity.bean.EnumConst;

/**
 * 使用ExtJS封装类实现EnumConst基本增删查改功能
 * @author ZLB
 * 审核人：
 *********************************************
 */
public class EnumConstAction extends MyBaseAction<EnumConst> {

	@Override
	public void initGrid() {
		this.getGridConfig().setTitle(this.getText("ENUM_CONST表管理"));

		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn("ID", "id", true, false, true, "");
		this.getGridConfig().addColumn(this.getText("NAME"), "name");
		this.getGridConfig().addColumn(this.getText("CODE"), "code");
		this.getGridConfig().addColumn(this.getText("NAMESPACE"), "namespace");
		this.getGridConfig().addColumn("IS_DEFAULT", "isDefault");
		this.getGridConfig().addColumn("CREATE_DATE", "createDate");
		this.getGridConfig().addColumn("NOTE", "note");
	}

	@Override
	public void setEntityClass() {
		this.entityClass = EnumConst.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/test/enumConst";
	}
	
	public void setBean(EnumConst instance) {
		super.superSetBean(instance);
	}
	
	public EnumConst getBean(){
		return super.superGetBean();
	}
}
