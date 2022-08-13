package com.whaty.platform.entity.web.action.teaching.basicInfo;

import org.hibernate.criterion.DetachedCriteria;

import com.whaty.platform.entity.bean.PeTchBook;
import com.whaty.platform.entity.bean.PrTchCourseBook;
import com.whaty.platform.entity.web.action.MyBaseAction;

/**
 * 课程教材列表
 * @author Administrator
 *
 */
public class PrTchCourseBookAction extends MyBaseAction<PrTchCourseBook> {

	@Override
	public void initGrid() {
		this.getGridConfig().setTitle(this.getText("课程教材列表"));
		this.getGridConfig().setCapability(true, true, true, true, false);
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("课程名称"), "peTchCourse.name");
		this.getGridConfig().addColumn(this.getText("教材名称"), "peTchBook.name");
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PrTchCourseBook.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/teaching/prTchCourseBook";
	}
	public void setBean(PrTchCourseBook instance) {
		super.superSetBean(instance);
	}

	public PrTchCourseBook getBean() {
		return super.superGetBean();
	}

	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PrTchCourseBook.class);
		dc.createCriteria("peTchCourse", "peTchCourse");
		dc.createCriteria("peTchBook", "peTchBook");
		return dc;
	}
}
