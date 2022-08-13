package com.whaty.platform.entity.web.action.fee.reduceReturnSpecial;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.bean.PrFeeDetail;
import com.whaty.platform.entity.web.action.MyBaseAction;
/**
 * 教师已减免费用列表
 * @author 李冰
 *
 */
public class TeacherReduceDetailAction extends MyBaseAction {


	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false,true);
		this.getGridConfig().setTitle(this.getText("教师已减免费用列表"));
		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("学号"),"peStudent.regNo");
		this.getGridConfig().addColumn(this.getText("姓名"), "peStudent.trueName");
		this.getGridConfig().addColumn(this.getText("年级"), "peStudent.peGrade.name");
		this.getGridConfig().addColumn(this.getText("学习中心"), "peStudent.peSite.name");
		this.getGridConfig().addColumn(this.getText("层次"), "peStudent.peEdutype.name");
		this.getGridConfig().addColumn(this.getText("专业"), "peStudent.peMajor.name");
		this.getGridConfig().addColumn(this.getText("学生状态"), "peStudent.enumConstByFlagStudentStatus.name");
		this.getGridConfig().addColumn(this.getText("减免金额"), "feeAmount");
		this.getGridConfig().addColumn(this.getText("减免日期"), "inputDate");
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PrFeeDetail.class;

	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/fee/teacherReduceDetail";

	}
	public void setBean(PrFeeDetail instance) {
		super.superSetBean(instance);

	}
	public PrFeeDetail getBean() {
		return (PrFeeDetail) super.superGetBean();
	}
	@Override
	public DetachedCriteria initDetachedCriteria() {
		
		DetachedCriteria dc = DetachedCriteria.forClass(PrFeeDetail.class);
		dc.createCriteria("enumConstByFlagFeeType", "enumConstByFlagFeeType").add(Restrictions.eq("code", "7"));
		DetachedCriteria dcPeStudent = dc.createCriteria("peStudent", "peStudent");
		dcPeStudent.createAlias("prStudentInfo", "prStudentInfo").createAlias("peSite", "peSite")
		.createAlias("peMajor", "peMajor")
		.createAlias("peEdutype","peEdutype")
		.createAlias("peGrade", "peGrade")
		.createAlias("enumConstByFlagStudentStatus", "enumConstByFlagStudentStatus");
		return dc;
	}

}
