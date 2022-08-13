package com.whaty.platform.entity.web.action.teaching.elective;

import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.bean.PrTchOpencourse;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class PeStuBeginCourseAction extends MyBaseAction<PrTchOpencourse> {
//TODO
	@Override
	public void initGrid() {
		
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().setTitle("选择课程");
		
		this.getGridConfig().addColumn(this.getText("id"),"id",false);
		this.getGridConfig().addColumn(this.getText("课程号"),"peTchCourse.code");
		this.getGridConfig().addColumn(this.getText("课程名"),"peTchCourse.name");
		this.getGridConfig().addMenuScript("选择", "{alert('选择课程已开')}");
		this.getGridConfig().addMenuScript("返回", "{window.history.back()}");

	}

	@Override
	public void setEntityClass() {
		this.entityClass = PrTchOpencourse.class;
		
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/teaching/peStuBeginCourse";
		
	}
	
	public void setBean(PrTchOpencourse instance) {
		super.superSetBean(instance);
	}
	
	public PrTchOpencourse getBean() {
		return super.superGetBean();
	}
	
	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PrTchOpencourse.class);
		dc.createCriteria("peTchCourse","peTchCourse");
		return dc;
	}


}
