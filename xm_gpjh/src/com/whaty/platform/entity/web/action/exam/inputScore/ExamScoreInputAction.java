package com.whaty.platform.entity.web.action.exam.inputScore;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PrExamBooking;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.util.Const;

public class ExamScoreInputAction extends MyBaseAction {

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, true);
		this.getGridConfig().setTitle(this.getText("期末考试成绩录入"));
		this.getGridConfig().addColumn(this.getText("id"),"id",false);
		this.getGridConfig().addColumn(this.getText("考试学期"),"peSemester.name",true,false,true,"");
		this.getGridConfig().addColumn(this.getText("学号"),"prTchStuElective.peStudent.regNo");
		this.getGridConfig().addColumn(this.getText("学生姓名"),"prTchStuElective.peStudent.trueName");
		this.getGridConfig().addColumn(this.getText("考试课程"),"prTchStuElective.prTchProgramCourse.peTchCourse.name");
		this.getGridConfig().addColumn(this.getText("学习中心"),"prTchStuElective.peStudent.peSite.name");
		this.getGridConfig().addColumn(this.getText("层次"),"prTchStuElective.peStudent.peEdutype.name");
		this.getGridConfig().addColumn(this.getText("专业"),"prTchStuElective.peStudent.peMajor.name");
		this.getGridConfig().addColumn(this.getText("年级"),"prTchStuElective.peStudent.peGrade.name");
		this.getGridConfig().addColumn(this.getText("考试成绩"),"scoreExam",true,true,true,Const.score_for_extjs);
//		this.getGridConfig().addColumn(this.getText("考试成绩状态"),"enumConstByFlagScoreStatus.name",true,true,true,"TextField",true,50);
		ColumnConfig column = new ColumnConfig(this.getText("考试成绩状态"),"enumConstByFlagScoreStatus.name");
		column.setComboSQL("select id,name from enum_const where namespace='FlagScoreStatus' and code<='4'");
		this.getGridConfig().addColumn(column);
		
//		this.getGridConfig().addColumn(this.getText("登分帐户A"),"prTchStuElective.prTchProgramCourse.peTchCourse.peExamScoreInputUserByFkExamScoreInputUseraId.name");
		ColumnConfig c1 = new ColumnConfig(this.getText("登分帐户A"),"prTchStuElective.prTchProgramCourse.peTchCourse.peExamScoreInputUserByFkExamScoreInputUseraId.name");
		c1.setComboSQL("select id,name from pe_exam_score_input_user where name like '%a'");
		this.getGridConfig().addColumn(c1);
		this.getGridConfig().addColumn(this.getText("A录入成绩"),"scoreExamA",true,false,true,Const.score_for_extjs);		
		ColumnConfig columna = new ColumnConfig(this.getText("A录入成绩状态"),"enumConstByFlagScoreStatusa.name");
		columna.setComboSQL("select id,name from enum_const where namespace='FlagScoreStatus' and code<='4'");
		columna.setAdd(false);
		this.getGridConfig().addColumn(columna);
		
//		this.getGridConfig().addColumn(this.getText("登分帐户B"),"prTchStuElective.prTchProgramCourse.peTchCourse.peExamScoreInputUserByFkExamScoreInputUserbId.name");
		ColumnConfig c2 = new ColumnConfig(this.getText("登分帐户B"),"prTchStuElective.prTchProgramCourse.peTchCourse.peExamScoreInputUserByFkExamScoreInputUserbId.name");
		c2.setComboSQL("select id,name from pe_exam_score_input_user where name like '%b'");
		this.getGridConfig().addColumn(c2);
		this.getGridConfig().addColumn(this.getText("B录入成绩"),"scoreExamB",true,false,true,Const.score_for_extjs);	
		ColumnConfig columnb = new ColumnConfig(this.getText("B录入成绩状态"),"enumConstByFlagScoreStatusb.name");
		columnb.setComboSQL("select id,name from enum_const where namespace='FlagScoreStatus' and code<='4'");
		columnb.setAdd(false);
		this.getGridConfig().addColumn(columnb);
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PrExamBooking.class;
	}
	public void setBean(PrExamBooking instance){
		this.superSetBean(instance);
	}
	
	public PrExamBooking getBean(){
		return (PrExamBooking)this.superGetBean();
	}
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PrExamBooking.class);
		dc.createAlias("enumConstByFlagScoreStatusa", "enumConstByFlagScoreStatusa",DetachedCriteria.LEFT_JOIN)
			.createAlias("enumConstByFlagScoreStatusb", "enumConstByFlagScoreStatusb",DetachedCriteria.LEFT_JOIN)
			.createCriteria("peSemester", "peSemester");
		DetachedCriteria dcPrTchStuElective = dc.createCriteria("prTchStuElective", "prTchStuElective");
		dcPrTchStuElective.createCriteria("peStudent", "peStudent")
				.createAlias("enumConstByFlagStudentStatus", "enumConstByFlagStudentStatus")
				.createAlias("peSite", "peSite")
				.createAlias("peEdutype", "peEdutype")
				.createAlias("peMajor", "peMajor")
				.createAlias("peGrade", "peGrade");
		dcPrTchStuElective.createCriteria("prTchProgramCourse", "prTchProgramCourse")
						.createCriteria("peTchCourse", "peTchCourse")
						.createAlias("peExamScoreInputUserByFkExamScoreInputUseraId", "peExamScoreInputUserByFkExamScoreInputUseraId",DetachedCriteria.LEFT_JOIN)
						.createAlias("peExamScoreInputUserByFkExamScoreInputUserbId", "peExamScoreInputUserByFkExamScoreInputUserbId",DetachedCriteria.LEFT_JOIN);
		dc.createAlias("enumConstByFlagScoreStatus", "enumConstByFlagScoreStatus");
		dc.add(Restrictions.eq("enumConstByFlagStudentStatus.code", "4"));
		return dc;
	}
	@Override
	public void setServletPath() {
		this.servletPath = "/entity/exam/examscoreinput";
	}

}
