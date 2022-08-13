package com.whaty.platform.entity.web.action.recruit.recExam;

import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

import com.whaty.platform.entity.bean.PeRecStudent;
import com.whaty.platform.entity.bean.PrRecExamStuCourse;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class ExamStuTicketActon extends MyBaseAction {

	@Override
	public Map updateColumn() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void initGrid() {
		this.getGridConfig().setTitle(this.getText("打印准考证"));
		this.getGridConfig().setCapability(false, false, false);
		
		this.getGridConfig().addColumn(this.getText("Id"),"id",false);
		this.getGridConfig().addColumn(this.getText("学生姓名"), "name");
		this.getGridConfig().addColumn(this.getText("学习中心"), "prRecPlanMajorSite.peSite.name");
		this.getGridConfig().addColumn(this.getText("层次"), "prRecPlanMajorSite.prRecPlanMajorEdutype.peEdutype.name");
		this.getGridConfig().addColumn(this.getText("专业"), "prRecPlanMajorSite.prRecPlanMajorEdutype.peMajor.name");
		this.getGridConfig().addColumn(this.getText("准考证号"), "examCardNum");
		this.getGridConfig().addColumn(this.getText("考场号"),"peRecRoom.code");
		this.getGridConfig().addColumn(this.getText("座位号"), "seatNum");
		this.getGridConfig().addMenuScript(this.getText("打印准考证"), "{	alert('打印准考证')}");
		
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PeRecStudent.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/recruit/examstuticket";
	}
	
	public void setBean(PeRecStudent instance) {
		super.superSetBean(instance);
		
	}
	
	public PeRecStudent getBean(){
		return (PeRecStudent) super.superGetBean();
	}
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeRecStudent.class);
		dc.createCriteria("peRecRoom", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("prRecPlanMajorSite","prRecPlanMajorSite").createAlias("peSite", "peSite")
										.createCriteria("prRecPlanMajorEdutype","prRecPlanMajorEdutype")
											.createAlias("peEdutype", "peEdutype")
											.createAlias("peMajor", "peMajor");
		return dc;
	}

}
