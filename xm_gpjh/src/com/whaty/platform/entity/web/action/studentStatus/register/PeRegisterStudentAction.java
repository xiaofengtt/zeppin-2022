package com.whaty.platform.entity.web.action.studentStatus.register;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.web.action.studentStatus.stuInfo.PeStudentInfoAction;

/**
 * 已注册学生
 * @author Administrator
 *
 */
public class PeRegisterStudentAction extends PeStudentInfoAction {

	private static final long serialVersionUID = 4992270397572827061L;

	@Override
	public void initGrid() {

		this.getGridConfig().setCapability(false, false, false,true);
		this.getGridConfig().setTitle(this.getText("已注册学生列表"));
		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("招生考试批次"), "peRecStudent.prRecPlanMajorSite.prRecPlanMajorEdutype.peRecruitplan.name");
		this.getGridConfig().addColumn(this.getText("peStudent.regNo"), "regNo");
		this.getGridConfig().addColumn(this.getText("peStudent.trueName"), "trueName");
		this.getGridConfig().addColumn(this.getText("peStudent.prStudentInfo.cardNo"), "prStudentInfo.cardNo");
		this.getGridConfig().addColumn(this.getText("peStudent.prStudentInfo.gender"), "prStudentInfo.gender");
		this.getGridConfig().addColumn(this.getText("peStudent.peSite.name"), "peSite.name");
		this.getGridConfig().addColumn(this.getText("peStudent.peGrade.name"), "peGrade.name");
		this.getGridConfig().addColumn(this.getText("peStudent.peMajor.name"), "peMajor.name");
		this.getGridConfig().addColumn(this.getText("peStudent.peEdutype.name"), "peEdutype.name");
		ColumnConfig column = new ColumnConfig(this.getText("学生状态"),"enumConstByFlagStudentStatus.name");
		column.setComboSQL("select id,name from enum_const where namespace='FlagStudentStatus' and code>='4'");
		this.getGridConfig().addColumn(column);
		//this.getGridConfig().addColumn(this.getText("学生状态"), "enumConstByFlagStudentStatus.name",false,false,true,"");
		
	}


	@Override
	public void setServletPath() {
		this.servletPath = "/entity/studentStatus/peRegisterStudent";
	}
	
	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeStudent.class);
		dc.createAlias("prStudentInfo", "prStudentInfo").createAlias("peSite", "peSite")
			.createAlias("peMajor", "peMajor")
			.createAlias("peEdutype","peEdutype")
			.createAlias("peGrade", "peGrade")
			.createAlias("enumConstByFlagStudentStatus", "enumConstByFlagStudentStatus")
			.add(Restrictions.eq("enumConstByFlagStudentStatus.code", "4"))
			.createCriteria("peRecStudent", "peRecStudent", DetachedCriteria.LEFT_JOIN)
				//添加考试批次关联信息
				.createCriteria("prRecPlanMajorSite","prRecPlanMajorSite", DetachedCriteria.LEFT_JOIN)
				.createCriteria("prRecPlanMajorEdutype", "prRecPlanMajorEdutype",DetachedCriteria.LEFT_JOIN)
					.createCriteria("peRecruitplan", "peRecruitplan", DetachedCriteria.LEFT_JOIN)
				;
		return dc;
	}

}
