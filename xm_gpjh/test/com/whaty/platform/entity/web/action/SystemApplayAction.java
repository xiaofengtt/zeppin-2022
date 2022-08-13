package com.whaty.platform.entity.web.action;

import org.hibernate.criterion.DetachedCriteria;

import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.SystemApply;
import com.whaty.platform.entity.exception.EntityException;

/**
 * 申请表
 * @author Administrator
 *
 */
public class SystemApplayAction extends MyBaseAction {

	@Override
	public void initGrid() {
		this.getGridConfig().setTitle(this.getText("申请表"));
		this.getGridConfig().addColumn(this.getText("id"),"id",false);
		this.getGridConfig().addColumn(this.getText("学生姓名"),"peStudent.name");
		this.getGridConfig().addColumn(this.getText("申请类型"),"enumConstByApplyType.name");
		this.getGridConfig().addColumn(this.getText("申请状态"),"enumConstByFlagApplyStatus.name",true,false,true,"");
		this.getGridConfig().addColumn(this.getText("申请时间"),"applyDate");
		this.getGridConfig().addColumn(this.getText("审核时间"),"checkDate",true,false,true,"");
		this.getGridConfig().addColumn(this.getText("申请内容"),"applyNote");
		this.getGridConfig().addColumn(this.getText("审核备注"),"checkNote",true,false,true,"");
	}

	@Override
	public void setEntityClass() {
		this.entityClass = SystemApply.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/test/systemApplay";
	}
	
	public void setBean(SystemApply instance){
		super.superSetBean(instance);
	}
	
	public SystemApply getBean(){
		return (SystemApply)super.superGetBean();
	}
	
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(SystemApply.class);
		dc.createAlias("peStudent", "peStudent")
			.createAlias("enumConstByApplyType", "enumConstByApplyType")
			.createAlias("enumConstByFlagApplyStatus", "enumConstByFlagApplyStatus");
		
		return dc;
	}

//	/**
//	 * 添加申请记录时，默认都为“待审核”
//	 */
//	public void checkBeforeAdd() throws EntityException{
//		try {
//			EnumConst enumConst = this.getMyListService().getEnumConstByNamespaceCode("FlagApplyStatus", "0");
//			this.getBean().setEnumConstByFlagApplyStatus(enumConst);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
}
