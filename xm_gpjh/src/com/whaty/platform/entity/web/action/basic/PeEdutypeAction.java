package com.whaty.platform.entity.web.action.basic;

import java.util.HashMap;
import java.util.Map;

import com.whaty.platform.entity.bean.PeEdutype;
import com.whaty.platform.entity.bean.PeSite;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.basic.PrEduMajorSiteFeeLevelService;
import com.whaty.platform.entity.web.action.MyBaseAction;

/**
 * 层次管理
 * 
 * @author 李冰
 * 
 */
public class PeEdutypeAction extends MyBaseAction<PeEdutype> {
	PrEduMajorSiteFeeLevelService prEduMajorSiteFeeLevelService;

	@Override
	public void initGrid() {
		this.getGridConfig().setTitle(this.getText("层次管理"));
		this.getGridConfig().setCapability(true, true, true, true);

		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("名称"), "name", true, true,
				true, "TextField", false, 50);
		this.getGridConfig().addColumn(this.getText("代码"), "code", true, true,
				true, "TextField", false, 50);
	}

	public Map add() {
		Map map = new HashMap();
		try {
			this.getPrEduMajorSiteFeeLevelService().saveEdutype(this.getBean());
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
		this.entityClass = PeEdutype.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/basic/peEdutype";
	}

	public void setBean(PeEdutype instance) {
		super.superSetBean(instance);
	}

	public PeEdutype getBean() {
		return super.superGetBean();
	}

	public PrEduMajorSiteFeeLevelService getPrEduMajorSiteFeeLevelService() {
		return prEduMajorSiteFeeLevelService;
	}

	public void setPrEduMajorSiteFeeLevelService(
			PrEduMajorSiteFeeLevelService prEduMajorSiteFeeLevelService) {
		this.prEduMajorSiteFeeLevelService = prEduMajorSiteFeeLevelService;
	}
}
