package com.whaty.platform.entity.web.action.recruit.setting;

import java.util.Map;
import java.util.Set;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;

import com.whaty.platform.entity.bean.PrRecPlanMajorEdutype;
import com.whaty.platform.entity.bean.PrRecPlanMajorSite;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;

public class PrAddSiteAction extends MyBaseAction<PrRecPlanMajorSite> {

	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PrRecPlanMajorSite.class);
		dc.createCriteria("peSite","peSite");
		dc.createCriteria("prRecPlanMajorEdutype","prRecPlanMajorEdutype").createAlias("peMajor", "peMajor").
				createCriteria("peEdutype","peEdutype");
		return dc;
	}
	
	public void setBean(PrRecPlanMajorSite instance) {
		super.superSetBean(instance);
		
	}
	
	public PrRecPlanMajorSite getBean(){
		return  super.superGetBean();
	}

	@Override
	public void initGrid() {
		this.getGridConfig().setTitle(this.getText("添加站点"));
        this.getGridConfig().setCapability(true, false, false);
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("站点名称"), "peSite.name");
		this.getGridConfig().addColumn(this.getText("专业"),"prRecPlanMajorEdutype.peMajor.name",false,false,true,"");
		this.getGridConfig().addColumn(this.getText("层次"),"prRecPlanMajorEdutype.peEdutype.name",false,false,true,"");
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PrRecPlanMajorSite.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/recruit/prAddSite";
	}
}
