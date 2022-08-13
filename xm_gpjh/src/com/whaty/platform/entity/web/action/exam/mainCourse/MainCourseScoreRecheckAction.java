package com.whaty.platform.entity.web.action.exam.mainCourse;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.SystemApply;
import com.whaty.platform.entity.web.action.studentStatus.register.PrRecPriPayApplyAction;

public class MainCourseScoreRecheckAction extends PrRecPriPayApplyAction{

	
	@Override
	public void initGrid() {
		this.getGridConfig().addMenuFunction("申请通过", "CheckForPass","");
		this.getGridConfig().addMenuFunction("取消申请通过", "CancelForPass", "");
		this.getGridConfig().addMenuFunction("申请不通过", "CheckForNoPass", "");
		this.getGridConfig().addMenuFunction("取消申请不通过", "CancelForNoPass", "");
		
		this.getGridConfig().setCapability(false, false, false,true);
		this.getGridConfig().setTitle(this.getText("主干课成绩复查审核"));
		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("学号"), "peStudent.regNo");
		this.getGridConfig().addColumn(this.getText("peStudent.trueName"), "peStudent.trueName");
		this.getGridConfig().addColumn(this.getText("证件号码"), "peStudent.prStudentInfo.cardNo");
		this.getGridConfig().addColumn(this.getText("peStudent.peGrade.name"), "peStudent.peGrade.name");
		this.getGridConfig().addColumn(this.getText("peStudent.peSite.name"), "peStudent.peSite.name");
		this.getGridConfig().addColumn(this.getText("peStudent.peEdutype.name"), "peStudent.peEdutype.name");
		this.getGridConfig().addColumn(this.getText("peStudent.peMajor.name"), "peStudent.peMajor.name");
		this.getGridConfig().addColumn(this.getText("审核状态"), "enumConstByFlagApplyStatus.name");
		this.getGridConfig().addColumn(this.getText("申请时间"), "applyDate");
		this.getGridConfig().addColumn(this.getText("审核时间"), "checkDate");
		this.getGridConfig().addColumn("申请的课程", "applyNote", false, false,
				false, "TextField", false, 100);
		this.getGridConfig().addRenderScript(this.getText("申请的课程"),
				"{var va=${value};var obj = va.substring(va.indexOf('|')+1,va.length); " +
				" return obj.substring(0,obj.indexOf('|'));}",
				"applyNote");
		this.getGridConfig().addRenderScript(this.getText("申请的原因"),
				"{var va=${value};var obj = va.substring(va.indexOf('|')+1,va.length); " +
				" return obj.substring(obj.indexOf('|')+1,obj.length);}",
				"applyNote");
	}

	
	@Override
	public void setServletPath() {
		this.servletPath="/entity/exam/peMainCourseScoreRecheck";
	}
	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(SystemApply.class);
		dc.createAlias("enumConstByFlagApplyStatus", "enumConstByFlagApplyStatus")
		  .createAlias("enumConstByApplyType", "enumConstByApplyType")
		  .add(Restrictions.and(Restrictions.eq("enumConstByApplyType.namespace", "ApplyType"), Restrictions.eq("enumConstByApplyType.code", "19")))
			.createCriteria("peStudent", "peStudent")
			.createAlias("prStudentInfo", "prStudentInfo").createAlias("peSite", "peSite")
			.createAlias("peMajor", "peMajor")
			.createAlias("peEdutype","peEdutype")
			.createAlias("peGrade", "peGrade")
			.createAlias("enumConstByFlagStudentStatus", "enumConstByFlagStudentStatus");
		return dc;
	}
}
