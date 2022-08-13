package com.whaty.platform.entity.web.action.recruit.setting;

import org.hibernate.criterion.DetachedCriteria;

import com.whaty.platform.entity.bean.PrRecPlanMajorSite;
import com.whaty.platform.entity.bean.PrStuMultiMajor;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class PrStuMultiMajorAction extends MyBaseAction<PrStuMultiMajor> {

	@Override
	public void initGrid() {
		this.getGridConfig().setTitle(this.getText("跨专业管理"));
		this.getGridConfig().setCapability(true, true, true, true, true);
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("专业名称"), "peMajor.name");
		this.getGridConfig().addColumn(this.getText("原专业名称"), "oldMajor");
	}

	@Override
	public void setEntityClass() {
		this.entityClass=PrStuMultiMajor.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/recruit/prStuMultiMajor";
	}
	public void setBean(PrStuMultiMajor instance) {
		super.superSetBean(instance);
		
	}
	
	public PrStuMultiMajor getBean(){
		return  super.superGetBean();
	}
	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PrStuMultiMajor.class);
		dc.createCriteria("peMajor","peMajor");
		return dc;
	}
}
