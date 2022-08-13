package com.whaty.platform.entity.web.action.teaching.paper;

import org.hibernate.criterion.DetachedCriteria;

import com.whaty.platform.entity.bean.PrTchStuPaper;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class StudentTopicAction extends MyBaseAction<PrTchStuPaper> {

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().setTitle(this.getText("学生选题"));

		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("学期"),"prTchPaperTitle.peSemester.name");
		this.getGridConfig().addColumn(this.getText("学号"),"peStudent.regNo");
		this.getGridConfig().addColumn(this.getText("姓名"), "peStudent.trueName");
		this.getGridConfig().addColumn(this.getText("学习中心"),"peStudent.peSite.name");
		this.getGridConfig().addColumn(this.getText("层次"),"peStudent.peEdutype.name");
		this.getGridConfig().addColumn(this.getText("专业"),"peStudent.peMajor.name");
		this.getGridConfig().addColumn(this.getText("年级"),"peStudent.peGrade.name");
		this.getGridConfig().addColumn(this.getText("指导教师"),"prTchPaperTitle.peTeacher.name");
		this.getGridConfig().addColumn(this.getText("毕业论文题目"),"prTchPaperTitle.title");
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PrTchStuPaper.class;

	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/teaching/studentTopic";
	}

	public void setBean(PrTchStuPaper instance) {
		super.superSetBean(instance);
	}

	public PrTchStuPaper getBean() {
		return super.superGetBean();
	}

	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PrTchStuPaper.class);
		dc.createCriteria("peStudent","peStudent").createAlias("peSite", "peSite")
				.createAlias("peEdutype", "peEdutype").createAlias("peMajor","peMajor").createAlias("peGrade", "peGrade");
		dc.createCriteria("prTchPaperTitle","prTchPaperTitle").createAlias("peTeacher", "peTeacher").createAlias("peSemester", "peSemester");
		return dc;
	}
}
