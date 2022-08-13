package com.whaty.platform.entity.web.action.studentStatus.stuInfo;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.bean.PrStuChangeableMajor;
import com.whaty.platform.entity.bean.PrTchStuElective;
import com.whaty.platform.entity.web.action.MyBaseAction;

/**
 * 可转专业管理
 * @author 李冰
 *
 */
public class PrStuChangeableMajorAction extends MyBaseAction {

	@Override
	public void initGrid() {
		this.getGridConfig().setTitle(this.getText("可转专业管理"));
		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("转专业来源"), "peMajorByFkOldMajorId.name");
		this.getGridConfig().addColumn(this.getText("转专业去向"), "peMajorByFkNewMajorId.name");
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PrStuChangeableMajor.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath="/entity/studentStatus/prStuChangeableMajor";
	}
	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PrStuChangeableMajor.class);
		dc.createCriteria("peMajorByFkNewMajorId", "peMajorByFkNewMajorId");
		dc.createCriteria("peMajorByFkOldMajorId", "peMajorByFkOldMajorId");
		return dc;
	}
	
	public void setBean(PrStuChangeableMajor instance) {
		super.superSetBean(instance);
	}
	
	public PrStuChangeableMajor getBean() {
		return (PrStuChangeableMajor)super.superGetBean();
	}

}
