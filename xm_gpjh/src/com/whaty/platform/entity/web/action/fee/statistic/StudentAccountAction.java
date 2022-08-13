package com.whaty.platform.entity.web.action.fee.statistic;

import com.whaty.platform.entity.web.action.studentStatus.stuInfo.PeStudentInfoAction;

public class StudentAccountAction extends PeStudentInfoAction {

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false,true);
		this.getGridConfig().setTitle(this.getText("学生个人帐户查询结果"));
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("学号"), "regNo");
		this.getGridConfig().addColumn(this.getText("姓名"), "trueName");
		this.getGridConfig().addColumn(this.getText("证件号码"), "prStudentInfo.cardNo");
		this.getGridConfig().addColumn(this.getText("学习中心"), "peSite.name");
		this.getGridConfig().addColumn(this.getText("年级"), "peGrade.name");
		this.getGridConfig().addColumn(this.getText("专业"), "peMajor.name");
		this.getGridConfig().addColumn(this.getText("层次"), "peEdutype.name");
		this.getGridConfig().addColumn(this.getText("学生状态"), "enumConstByFlagStudentStatus.name");
		this.getGridConfig().addColumn(this.getText("余额"), "feeBalance",false,false,true,"");
		this.getGridConfig().addRenderFunction(this.getText("费用明细"), "<a href=\"/entity/fee/prFeeDetailByStudent.action?bean.peStudent.id=${value}\" target=\"_blank\"><u><font color=blue>查看</font></u></a>", "id");
	}


	@Override
	public void setServletPath() {
		this.servletPath = "/entity/fee/studentAccount";
	}

}
