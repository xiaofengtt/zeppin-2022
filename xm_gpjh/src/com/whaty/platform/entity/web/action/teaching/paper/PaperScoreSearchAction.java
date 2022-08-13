package com.whaty.platform.entity.web.action.teaching.paper;


import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PrTchStuPaper;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.util.Const;

public class PaperScoreSearchAction extends MyBaseAction<PrTchStuPaper> {
	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, true);

		this.getGridConfig().setTitle(this.getText("毕业论文写作成绩查看"));
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("学号"), "peStudent.regNo");
		this.getGridConfig().addColumn(this.getText("姓名"), "peStudent.trueName",true,false,true,"TextField",false,50);
		this.getGridConfig().addColumn(this.getText("年级"),"peStudent.peGrade.name");
		this.getGridConfig().addColumn(this.getText("学习中心"),"peStudent.peSite.name");
		this.getGridConfig().addColumn(this.getText("层次"),"peStudent.peEdutype.name");
		this.getGridConfig().addColumn(this.getText("专业"),"peStudent.peMajor.name");
		this.getGridConfig().addColumn(this.getText("论文题目"),"prTchPaperTitle.title");
		this.getGridConfig().addColumn(this.getText("指导教师"),"prTchPaperTitle.peTeacher.name");
//		this.getGridConfig().addColumn(this.getText("终稿成绩"), "finalScore");
		this.getGridConfig().addColumn(this.getText("终稿成绩"), "finalScore", true, true, false,Const.score_for_extjs);
		this.getGridConfig().addRenderScript("终稿成绩", "{if (${value}=='') return '未录入';return ${value}}", "finalScore");
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PrTchStuPaper.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/teaching/paperScoreSearch";
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
		dc.createCriteria("peStudent", "peStudent").createAlias("enumConstByFlagStudentStatus", "enumConstByFlagStudentStatus").createAlias("peSite",
				"peSite").createAlias("peEdutype", "peEdutype").createAlias(
				"peMajor", "peMajor").createAlias("peGrade", "peGrade");
		dc.createCriteria("enumConstByFlagPaperRejoin", "enumConstByFlagPaperRejoin");
		dc.createCriteria("prTchPaperTitle", "prTchPaperTitle").createAlias("peTeacher", "peTeacher").createAlias("peSemester", "peSemester");
		dc.add(Restrictions.eq("enumConstByFlagStudentStatus.code", "4"));
		dc.add(Restrictions.eq("peSemester.flagActive", "1"));
		return dc;
	}
}
