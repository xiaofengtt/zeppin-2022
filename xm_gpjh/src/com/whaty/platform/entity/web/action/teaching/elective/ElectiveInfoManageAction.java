package com.whaty.platform.entity.web.action.teaching.elective;

import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.bean.PrTchStuElective;
import com.whaty.platform.entity.web.action.MyBaseAction;

/**
 * 查看学生选课列表
 * @author zhangyu
 *
 */
public class ElectiveInfoManageAction extends MyBaseAction<PeStudent> {

	@Override
	public void initGrid() {

		this.getGridConfig().setTitle("学生选课管理");
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().addColumn(this.getText("id"),"id",false);
		this.getGridConfig().addColumn(this.getText("姓名 "),"trueName");
		this.getGridConfig().addColumn(this.getText("学号"),"regNo");
		this.getGridConfig().addColumn(this.getText("学习中心"),"peSite.name");
		this.getGridConfig().addColumn(this.getText("层次"),"peEdutype.name");
		this.getGridConfig().addColumn(this.getText("专业"),"peMajor.name");
		this.getGridConfig().addColumn(this.getText("年级"),"peGrade.name");
		this.getGridConfig().addRenderFunction("查看课程",  "<a href=\"/entity/teaching/peStuElectiveCourses.action?bean.peStudent.id=${value}\" target=\"_self\">查看学生已选课程</a>", "id");
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PrTchStuElective.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/teaching/electiveInfoManage";
	}
	public void setBean(PeStudent instance) {
		super.superSetBean(instance);
	}
	
	public PeStudent getBean(){
		return super.superGetBean();
	}

	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeStudent.class);
		dc.createAlias("peSite", "peSite").createAlias("peMajor", "peMajor").createAlias("peGrade", "peGrade").createAlias("peEdutype", "peEdutype");
		dc.createCriteria("enumConstByFlagStudentStatus","enumConstByFlagStudentStatus");
		dc.add(Restrictions.eq("enumConstByFlagStudentStatus.code", "4"));
		return dc;
	}
	
	
}
