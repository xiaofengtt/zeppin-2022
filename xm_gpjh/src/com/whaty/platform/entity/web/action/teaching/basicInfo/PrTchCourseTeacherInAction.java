package com.whaty.platform.entity.web.action.teaching.basicInfo;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeTchCourse;
import com.whaty.platform.entity.bean.PrTchCourseTeacher;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class PrTchCourseTeacherInAction extends MyBaseAction {

	@Override
	public void initGrid() {
		String courseName=this.getCourseName();
		this.getGridConfig().setTitle("为"+courseName+"课程管理培训教师");
		this.getGridConfig().setCapability(false, true, false, true);
		this.getGridConfig().addColumn("","id",false);
		this.getGridConfig().addColumn(this.getText("教师姓名"),"peTeacher.trueName");
		this.getGridConfig().addColumn(this.getText("出生日期"),"peTeacher.birthDate", false);
		this.getGridConfig().addColumn(this.getText("工作单位"),"peTeacher.workplace", false);
		
	}

	private String getCourseName() {
		String name=null;
		PeTchCourse course=null;
		try {
			this.getGeneralService().getGeneralDao().setEntityClass(PeTchCourse.class);
			course = (PeTchCourse) this.getGeneralService().getById(this.getBean().getPeTchCourse().getId());
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		name=course.getName();
		return name;
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PrTchCourseTeacher.class;

	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/teaching/prTchCourseTeacherInAction";

	}
	
	public void setBean(PrTchCourseTeacher instance) {
		super.superSetBean(instance);
		
	}
	
	public PrTchCourseTeacher getBean(){
		return (PrTchCourseTeacher) super.superGetBean();
	}

	@Override
	public DetachedCriteria initDetachedCriteria() {
		this.getGeneralService().getGeneralDao().setEntityClass(PrTchCourseTeacher.class);
		DetachedCriteria dc = DetachedCriteria.forClass(PrTchCourseTeacher.class);
		dc.createCriteria("peTeacher", "peTeacher");
		dc.createCriteria("peTchCourse", "peTchCourse");
		return dc;
	}
	
	

}
