package com.whaty.platform.entity.web.action.teaching.elective;

import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PrTchStuElective;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class ConfirmedCourseInfoAction extends MyBaseAction<PrTchStuElective> {

	@Override
	public void initGrid() {

		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().setTitle(this.getText("已开课详情查询"));

		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		
		this.getGridConfig().addColumn(this.getText("学期"), "prTchOpencourse.peSemester.name");
		
		this.getGridConfig().addColumn(this.getText("姓名"), "peStudent.trueName");
		this.getGridConfig().addColumn(this.getText("学号"), "peStudent.regNo");
		this.getGridConfig().addColumn(this.getText("学习中心"),
				"peStudent.peSite.name");
		this.getGridConfig().addColumn(this.getText("专业"),
				"peStudent.peMajor.name");
		this.getGridConfig().addColumn(this.getText("年级"),
				"peStudent.peGrade.name");
		this.getGridConfig().addColumn(this.getText("层次"),
				"peStudent.peEdutype.name");
		this.getGridConfig().addColumn(this.getText("课程"),"prTchOpencourse.peTchCourse.name");
		// this.getGridConfig().addRenderFunction(this.getText("查看详细信息"), "<a
		// href='/entity/teaching/confirmedCourseDetail.action'
		// target='_blank'>已开课课程列表</a>", "id");
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PrTchStuElective.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/teaching/confirmedCourseInfo";
	}

	public void setBean(PrTchStuElective instance) {
		super.superSetBean(instance);
	}

	public PrTchStuElective getBean() {
		return super.superGetBean();
	}

	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PrTchStuElective.class);
		dc.createCriteria("peStudent", "peStudent").createAlias("peSite",
				"peSite").createAlias("peMajor", "peMajor").createAlias(
				"peGrade", "peGrade").createAlias("peEdutype", "peEdutype");
		dc.createCriteria("enumConstByFlagElectiveAdmission", "enumConstByFlagElectiveAdmission");
		dc.add(Restrictions.eq("enumConstByFlagElectiveAdmission.code", "1"));
		dc.createCriteria("prTchOpencourse", "prTchOpencourse").createAlias("peSemester", "peSemester").createAlias("peTchCourse", "peTchCourse");
//		dc.add(Restrictions.eq("peSemester.flagNextActive", "1"));
		return dc;
	}

}
