package com.whaty.platform.entity.web.action.basic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

import com.whaty.platform.entity.bean.PeEdutype;
import com.whaty.platform.entity.bean.PeMajor;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.PeMjorService;
import com.whaty.platform.entity.service.basic.PrEduMajorSiteFeeLevelService;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class PeMajorAction extends MyBaseAction<PeMajor> {
	PrEduMajorSiteFeeLevelService prEduMajorSiteFeeLevelService;

	@Override
	public void initGrid() {

		this.getGridConfig().setTitle(this.getText("专业管理"));
		this.getGridConfig().setCapability(true, true, true, true);

		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("名称"), "name");
		this.getGridConfig().addColumn(this.getText("代码"), "code");
		this.getGridConfig().addColumn(this.getText("别名"), "alias");
		this.getGridConfig().addColumn(this.getText("是否教育专业"),
				"enumConstByFlagIsEducation.name");
	}

	public Map add() {
		Map map = new HashMap();
		try {
			this.setBean((PeMajor)setSubIds(this.getBean()));
			this.getPrEduMajorSiteFeeLevelService().saveMajor(this.getBean());
		} catch (EntityException e) {
			map.put("success", "false");
			map.put("info", e.getMessage());
			return map;
		} catch (Exception e) {
			return super.checkAlternateKey(e, "添加");
		}
		map.put("success", "true");
		map.put("info", "添加成功");
		return map;
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PeMajor.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/basic/peMajor";
	}

	public void setBean(PeMajor instance) {
		super.superSetBean(instance);
	}

	public PeMajor getBean() {
		return (PeMajor) super.superGetBean();
	}

	public PrEduMajorSiteFeeLevelService getPrEduMajorSiteFeeLevelService() {
		return prEduMajorSiteFeeLevelService;
	}

	public void setPrEduMajorSiteFeeLevelService(
			PrEduMajorSiteFeeLevelService prEduMajorSiteFeeLevelService) {
		this.prEduMajorSiteFeeLevelService = prEduMajorSiteFeeLevelService;
	}

	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeMajor.class);
		dc.createCriteria("enumConstByFlagIsEducation",
				"enumConstByFlagIsEducation" , DetachedCriteria.LEFT_JOIN);
		return dc;
	}
}
