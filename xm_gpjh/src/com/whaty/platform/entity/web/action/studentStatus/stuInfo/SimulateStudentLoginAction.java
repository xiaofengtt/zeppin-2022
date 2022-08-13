package com.whaty.platform.entity.web.action.studentStatus.stuInfo;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeStudent;

/**
 * 学生模拟登录管理
 * @author Administrator
 *
 */
public class SimulateStudentLoginAction extends PeStudentInfoAction {

	private static final long serialVersionUID = -1558451365221146074L;
	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false,true);
		this.getGridConfig().setTitle(this.getText("学生信息列表"));
		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("学号"), "regNo");
		this.getGridConfig().addColumn(this.getText("姓名"), "trueName");
		this.getGridConfig().addColumn(this.getText("密码"), "ssoUser.password",false);
		//this.getGridConfig().addColumn(this.getText("出身日期"), "prStudentInfo.birthday");
		this.getGridConfig().addColumn(this.getText("证件号码"), "prStudentInfo.cardNo");
		this.getGridConfig().addColumn(this.getText("性别"), "prStudentInfo.gender");
		this.getGridConfig().addColumn(this.getText("学习中心"), "peSite.name");
		this.getGridConfig().addColumn(this.getText("年级"), "peGrade.name");
		this.getGridConfig().addColumn(this.getText("专业"), "peMajor.name");
		this.getGridConfig().addColumn(this.getText("层次"), "peEdutype.name");
			
		this.getGridConfig().addRenderFunction(this.getText("模拟登录"), "<a href='/sso/login_simulate.action?loginId=\"+record.data['regNo']+\"' target='_blank'>模拟登录</a>");
	}
	@Override
	public void setServletPath() {
		this.servletPath="/entity/studentStatus/simulateStudentLogin";
	}
	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeStudent.class);
		dc.createAlias("prStudentInfo", "prStudentInfo").createAlias("peSite", "peSite")
			.createAlias("peMajor", "peMajor")
			.createAlias("peEdutype","peEdutype")
			.createAlias("ssoUser","ssoUser")
			.createAlias("peGrade", "peGrade")
			.createAlias("enumConstByFlagStudentStatus", "enumConstByFlagStudentStatus")
			.add(Restrictions.eq("enumConstByFlagStudentStatus.code", "4"));
		return dc;
	}
	
}
