package com.whaty.platform.entity.web.action.teaching.elective;

import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeSemester;
import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.web.action.MyBaseAction;
/**
 * 代学生选课
 * @author Administrator
 *
 */
public class ElectiveManageAction extends MyBaseAction<PeStudent> {


	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false);
		
		this.getGridConfig().setTitle("代学生选课");
		this.getGridConfig().addColumn(this.getText("id"),"id",false);
		this.getGridConfig().addColumn(this.getText("姓名"),"trueName");
		this.getGridConfig().addColumn(this.getText("学号"),"regNo");
		this.getGridConfig().addColumn(this.getText("学习中心"),"peSite.name");
		this.getGridConfig().addColumn(this.getText("层次"),"peEdutype.name");
		this.getGridConfig().addColumn(this.getText("专业"),"peMajor.name");
		this.getGridConfig().addColumn(this.getText("年级"),"peGrade.name");

		this.getGridConfig().addMenuFunction("为选中的学生选课", "/entity/teaching/peStuElective.action", false, false);
		this.getGridConfig().addMenuScript("返回", "{window.history.back()}");
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PeStudent.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/teaching/elelctiveManage";
		
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
		dc.createCriteria("peSite","peSite");
		dc.createCriteria("peEdutype", "peEdutype");
		dc.createCriteria("peMajor", "peMajor");
		dc.createCriteria("peGrade", "peGrade");
		dc.createCriteria("enumConstByFlagStudentStatus","enumConstByFlagStudentStatus");
		dc.add(Restrictions.eq("enumConstByFlagStudentStatus.code", "4"));
		if(this.getBean()!=null && this.getBean().getRegNo()!=null){
			dc.add(Restrictions.eq("regNo", this.getBean().getRegNo()));
		}
		return dc;
	}
	
	public String turntoSearch() {
		return "turntoSearch";
	}

	
	
}
