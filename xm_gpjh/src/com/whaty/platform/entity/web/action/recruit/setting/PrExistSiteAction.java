package com.whaty.platform.entity.web.action.recruit.setting;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PrRecPlanMajorSite;
import com.whaty.platform.entity.web.action.MyBaseAction;

/**
 * 查看已有站点信息action
 * 
 * @author zhangyu
 * 
 */
public class PrExistSiteAction extends MyBaseAction<PrRecPlanMajorSite> {

	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria
				.forClass(PrRecPlanMajorSite.class);
		dc.createCriteria("peSite","peSite");
		dc.createCriteria("prRecPlanMajorEdutype","prRecPlanMajorEdutype").createAlias("peMajor",
				"peMajor").createCriteria("peEdutype","peEdutype");
		return dc;
	}

	public void setBean(PrRecPlanMajorSite instance) {
		super.superSetBean(instance);

	}

	public PrRecPlanMajorSite getBean() {
		return super.superGetBean();
	}

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(true, true, false);
		this.getGridConfig().setTitle(this.getText("站点招生计划"));
		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("站点名称"), "peSite.name");
		this.getGridConfig().addColumn(this.getText("专业"),
				"prRecPlanMajorEdutype.peMajor.name");
		this.getGridConfig().addColumn(this.getText("层次"),
				"prRecPlanMajorEdutype.peEdutype.name");
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PrRecPlanMajorSite.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/recruit/prExistSite";
	}

}
