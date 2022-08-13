package com.whaty.platform.entity.web.action.teaching.basicInfo;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PrTchOpencourseBook;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class CourseBookHisAction extends MyBaseAction {

	@Override
	public void initGrid() {
		// TODO Auto-generated method stub
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("课程名称"), "prTchOpencourse.peTchCourse.name", false);
		this.getGridConfig().addColumn(this.getText("学期"), "prTchOpencourse.peSemester.name");
		this.getGridConfig().addColumn(this.getText("教材名称"), "peTchBook.name");
	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		this.servletPath = "/entity/teaching/courseBookHis";
	}
	

	@Override
	public DetachedCriteria initDetachedCriteria() {
		// TODO Auto-generated method stub
		DetachedCriteria dc = DetachedCriteria.forClass(PrTchOpencourseBook.class);
		dc.createAlias("peTchBook", "peTchBook");
		DetachedCriteria dc_opcourse  = dc.createCriteria("prTchOpencourse","prTchOpencourse");
		dc_opcourse.createAlias("peSemester","peSemester");
		dc_opcourse.createCriteria("peTchCourse", "peTchCourse").add(Restrictions.eq("id", this.getIds()));
		return dc;
	}
	
}
