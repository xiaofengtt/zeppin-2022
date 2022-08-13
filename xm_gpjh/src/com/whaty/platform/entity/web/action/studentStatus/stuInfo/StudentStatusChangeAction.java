package com.whaty.platform.entity.web.action.studentStatus.stuInfo;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.web.action.MyBaseAction;
/**
 * 添加学籍变动列表
 * @author 李冰
 *
 */
public class StudentStatusChangeAction extends MyBaseAction {

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false,true);
		this.getGridConfig().setTitle(this.getText("学生信息列表"));
		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("学号"), "regNo");
		this.getGridConfig().addColumn(this.getText("姓名"), "trueName");
		this.getGridConfig().addColumn(this.getText("证件号码"), "prStudentInfo.cardNo");
		this.getGridConfig().addColumn(this.getText("性别"), "prStudentInfo.gender");
		this.getGridConfig().addColumn(this.getText("学习中心"), "peSite.name");
		this.getGridConfig().addColumn(this.getText("年级"), "peGrade.name");
		this.getGridConfig().addColumn(this.getText("专业"), "peMajor.name");
		this.getGridConfig().addColumn(this.getText("层次"), "peEdutype.name");
		this.getGridConfig().addMenuFunction(this.getText("转学习中心"), "/entity/studentStatus/peChangeSite_turntochange.action", true, false);
		this.getGridConfig().addMenuFunction(this.getText("转动专业"), "/entity/studentStatus/peChangeMajor_turntochange.action", true, false);
		this.getGridConfig().addMenuFunction(this.getText("转层次"), "/entity/studentStatus/peChangeType_turntochange.action", true, false);
		
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PeStudent.class;
		
	}

	@Override
	public void setServletPath() {
		this.servletPath="/entity/studentStatus/studentStatus";
	}

	public void setBean(PeStudent instance) {
		super.superSetBean(instance);
	}
	
	public PeStudent getBean(){
		return (PeStudent)super.superGetBean();
	}
	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeStudent.class);
		dc.createAlias("prStudentInfo", "prStudentInfo", DetachedCriteria.LEFT_JOIN)
			.createAlias("peSite", "peSite")
			.createAlias("peMajor", "peMajor")
			.createAlias("peEdutype","peEdutype")
			.createAlias("peGrade", "peGrade")
			.createAlias("enumConstByFlagStudentStatus", "enumConstByFlagStudentStatus")
			.add(Restrictions.eq("enumConstByFlagStudentStatus.code", "4"));
		return dc;
	}
}
