package com.whaty.platform.entity.web.action.studentStatus.stuInfo;

import org.hibernate.criterion.DetachedCriteria;

import com.whaty.platform.entity.bean.PeStudent;

public class PeYearReportAction extends PeStudentInfoAction {

	private static final long serialVersionUID = -9164781528466130527L;

	//	姓名、学号、身份证号、性别、出生日期、民族、录取方式、学制、层次、专业、学习中心代码、
//	学习中心名称、入学日期（如2006年7月17日为20060717）、入学前最高学历层次、专科毕业学校代码、
//	专科毕业学校名称、专科毕业年份（只需年，不需月日）、专科毕业证书编号、电子邮箱、联系电话、联系地址、
//	邮编、离校时间（已毕业的）、毕业日期（已毕业的，如2006年2月28日为20060228）、毕业证书编号（已毕业的）、是否获得学位证等基本内容。
	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false,true);
		this.getGridConfig().setTitle(this.getText("年报年检信息表"));
		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("姓名"), "trueName");
		this.getGridConfig().addColumn(this.getText("学号"), "regNo");
		this.getGridConfig().addColumn(this.getText("身份证号"), "prStudentInfo.cardNo");
		this.getGridConfig().addColumn(this.getText("性别"), "prStudentInfo.gender");
		this.getGridConfig().addColumn(this.getText("出生日期"), "prStudentInfo.birthday");
		this.getGridConfig().addColumn(this.getText("民族"), "prStudentInfo.fork");
		this.getGridConfig().addColumn(this.getText("录取方式"), "regNo");
		this.getGridConfig().addColumn(this.getText("学制"), "prStudentInfo.xuezhi");
		this.getGridConfig().addColumn(this.getText("层次"), "peEdutype.name");
		this.getGridConfig().addColumn(this.getText("专业"), "peMajor.name");
		this.getGridConfig().addColumn(this.getText("学习中心代码"), "peSite.code");
		this.getGridConfig().addColumn(this.getText("学习中心名称"), "peSite.name");
		this.getGridConfig().addColumn(this.getText("入学日期"), "recruitDate");
		this.getGridConfig().addColumn(this.getText("入学前最高学历层次"), "prStudentInfo.xueli");
		this.getGridConfig().addColumn(this.getText("专科毕业学校代码"), "prStudentInfo.graduateSchoolCode");
		this.getGridConfig().addColumn(this.getText("专科毕业学校名称"), "prStudentInfo.graduateSchool");
		this.getGridConfig().addColumn(this.getText("专科毕业年份"), "prStudentInfo.graduateYear");
		this.getGridConfig().addColumn(this.getText("专科毕业证书编号"), "prStudentInfo.graduateCode");
		this.getGridConfig().addColumn(this.getText("电子邮箱"), "email");
		this.getGridConfig().addColumn(this.getText("联系电话"), "mobilephone");
		this.getGridConfig().addColumn(this.getText("联系地址"), "address");
		this.getGridConfig().addColumn(this.getText("离校时间"), "regNo");
		this.getGridConfig().addColumn(this.getText("毕业日期"), "graduationDate");
		this.getGridConfig().addColumn(this.getText("毕业证书编号"), "graduationCertificateNo");
		this.getGridConfig().addColumn(this.getText("是否获得学位证"), "degreeCertificateNo");
		
	}

	@Override
	public void setServletPath() {
		this.servletPath="/entity/studentStatus/peYearReport";
	}
	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeStudent.class);
		dc.createAlias("prStudentInfo", "prStudentInfo").createAlias("peSite", "peSite")
			.createAlias("peMajor", "peMajor")
			.createAlias("peEdutype","peEdutype")
			.createAlias("peGrade", "peGrade");
		return dc;
	}
}
