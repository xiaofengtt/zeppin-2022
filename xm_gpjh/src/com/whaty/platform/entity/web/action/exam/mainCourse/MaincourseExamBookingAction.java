package com.whaty.platform.entity.web.action.exam.mainCourse;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeStudent;
/**
 * 主干课程预约考试管理
 * @author zqf
 *
 */
public class MaincourseExamBookingAction extends MainCourseBaseAction {

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().setTitle(this.getText(this.getSemesterName() + "主干课程考试预约管理"));
		this.getGridConfig().addColumn(this.getText("id"),"id",false);
		this.getGridConfig().addColumn(this.getText("学生姓名"),"trueName");
		this.getGridConfig().addColumn(this.getText("学号"),"regNo");
		this.getGridConfig().addColumn(this.getText("学习中心"),"peSite.name");
		this.getGridConfig().addColumn(this.getText("层次"),"peEdutype.name");
		this.getGridConfig().addColumn(this.getText("专业"),"peMajor.name");
		this.getGridConfig().addColumn(this.getText("年级"),"peGrade.name");
		this.getGridConfig().addMenuFunction(this.getText("为所选学生预约考试"), "/entity/exam/maincourseElectivecourse.action", false, false);
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PeStudent.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/exam/maincourseExamBooking";
	}

	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeStudent.class);
		dc.createAlias("peSite", "peSite")
			.createAlias("peEdutype", "peEdutype")
			.createAlias("peMajor", "peMajor")
			.createAlias("peGrade", "peGrade")
			.createAlias("enumConstByFlagStudentStatus", "enumConstByFlagStudentStatus");
		dc.add(Restrictions.eq("enumConstByFlagStudentStatus.code", "4"));	//code=4:已注册在籍学生
		dc.add(Restrictions.like("peEdutype.name", "本", MatchMode.ANYWHERE));
		return dc;
	}
	
}
