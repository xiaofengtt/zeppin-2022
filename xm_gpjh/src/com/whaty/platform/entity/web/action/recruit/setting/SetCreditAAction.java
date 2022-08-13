package com.whaty.platform.entity.web.action.recruit.setting;

import org.hibernate.criterion.DetachedCriteria;

import com.whaty.platform.entity.bean.PeSite;
import com.whaty.platform.entity.bean.PrEduMajorSiteFeeLevel;
import com.whaty.platform.entity.web.action.MyBaseAction;

/**
 * 选择学习中心层次专业 来设置学分收费标准
 * 
 * @author 李冰
 * 
 */
public class SetCreditAAction extends MyBaseAction {

	@Override
	public void initGrid() {
		this.getGridConfig().setTitle(this.getText("为学习中心设置收费标准"));
		this.getGridConfig().setCapability(false, false, false, true);
		this.getGridConfig().addColumn(this.getText("Id"), "id", false);
		this.getGridConfig().addColumn(this.getText("学习中心"), "peSite.name");
		this.getGridConfig().addColumn(this.getText("编码"), "peSite.code");
		// this.getGridConfig().addColumn(this.getText("所属地级市"), "city");
		this.getGridConfig().addColumn(this.getText("层次"), "peEdutype.name");
		this.getGridConfig().addColumn(this.getText("专业"), "peMajor.name");
		this.getGridConfig().addColumn(this.getText("收费标准"), "peFeeLevel.name");
		this.getGridConfig().addColumn(this.getText("每学分费用"),
				"peFeeLevel.feePercredit");
		this.getGridConfig().addColumn(this.getText("允许欠费额"),
				"peFeeLevel.oweFeeLimit");
		this.getGridConfig().addMenuFunction(this.getText("为所选的学习中心设置学分标准"),
				"/entity/recruit/setCreditB.action", false, false);
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PrEduMajorSiteFeeLevel.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/recruit/setCreditA";

	}

	public void setBean(PrEduMajorSiteFeeLevel instance) {
		super.superSetBean(instance);

	}

	public PrEduMajorSiteFeeLevel getBean() {
		return (PrEduMajorSiteFeeLevel) super.superGetBean();
	}

	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria
				.forClass(PrEduMajorSiteFeeLevel.class);
		dc.createCriteria("peSite", "peSite");
		dc.createCriteria("peEdutype", "peEdutype");
		dc.createCriteria("peMajor", "peMajor");
		dc.createCriteria("peFeeLevel", "peFeeLevel");
		return dc;
	}

}
