package com.whaty.platform.entity.web.action.studentStatus.degree;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.SystemApply;
import com.whaty.platform.entity.web.action.SystemApplayAction;

public class PeStudentDegreeAction extends SystemApplayAction {

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false,true);
		this.getGridConfig().setTitle(this.getText("已获得学位学生列表"));
		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("学号"), "peStudent.regNo");
		this.getGridConfig().addColumn(this.getText("姓名"), "peStudent.trueName");
		this.getGridConfig().addColumn(this.getText("学习中心"), "peStudent.peSite.name");
		this.getGridConfig().addColumn(this.getText("年级"), "peStudent.peGrade.name");
		this.getGridConfig().addColumn(this.getText("专业"), "peStudent.peMajor.name");
		this.getGridConfig().addColumn(this.getText("层次"), "peStudent.peEdutype.name");
		this.getGridConfig().addColumn(this.getText("学位外语成绩"),"peStudent.scoreDegreeEnglish");
	//	this.getGridConfig().addColumn(this.getText("毕业论文成绩"),"f");
		this.getGridConfig().addColumn(this.getText("入学时间"), "peStudent.recruitDate");
		this.getGridConfig().addColumn(this.getText("毕业时间"), "peStudent.graduationDate");
		this.getGridConfig().addColumn(this.getText("毕业证编号"), "peStudent.graduationCertificateNo");
		this.getGridConfig().addColumn(this.getText("获得学位时间"), "peStudent.degreeDate");
		this.getGridConfig().addColumn(this.getText("学位证编号"),"peStudent.degreeCertificateNo");
	}

	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(SystemApply.class);
		dc.createAlias("enumConstByApplyType", "enumConstByApplyType")
			.add(Restrictions.eq("enumConstByApplyType.code", "9"))
		.createAlias("enumConstByFlagApplyStatus", "enumConstByFlagApplyStatus")
			.add(Restrictions.eq("enumConstByFlagApplyStatus.code", "1"))
			.createCriteria("peStudent", "peStudent")
			.createAlias("prStudentInfo", "prStudentInfo", DetachedCriteria.LEFT_JOIN)
			.createAlias("peSite", "peSite")
			.createAlias("peMajor", "peMajor")
			.createAlias("peEdutype","peEdutype")
			.createAlias("peGrade", "peGrade")
			.createAlias("enumConstByFlagStudentStatus", "enumConstByFlagStudentStatus")
			.add(Restrictions.ge("enumConstByFlagStudentStatus.code", "5"));
		return dc;
	}
	@Override
	public void setServletPath() {
		this.servletPath = "/entity/studentStatus/peStudentDegree";
	}


}
