package com.whaty.platform.entity.web.action.teaching.basicInfo;

import org.hibernate.criterion.DetachedCriteria;

import com.whaty.platform.entity.bean.PeTchCourse;
import com.whaty.platform.entity.bean.PrTchCourseTeacher;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class PrTchCourseTeacherAction extends MyBaseAction<PrTchCourseTeacher> {

	@Override
	public void initGrid() {
		
		String courseName = ((PeTchCourse)this.getMyListService().getById(PeTchCourse.class, this.getBean().getPeTchCourse().getId())).getName();
		
		this.getGridConfig().setTitle("设置 "+courseName+"  的课程教师");
		
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);		
//		20090413 只显示有效状态的教师。 (龚妮娜)
		ColumnConfig column = new ColumnConfig(this.getText("教师名"),"peTeacher.name");		
		column.setComboSQL("select t.id,t.name, t.FLAG_ACTIVE from PE_TEACHER t ,enum_const m where  m.namespace = 'FlagActive' and t.flag_active=m.id and m.code=1 ");
		this.getGridConfig().addColumn(column);
		//this.getGridConfig().addColumn(this.getText("教师名"),"peTeacher.name");
		this.getGridConfig().addColumn(this.getText("类型"),"enumConstByFlagTeacherType.name");
		
		this.getGridConfig().addMenuScript(this.getText("返回"), "{window.history.back();}");
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PrTchCourseTeacher.class;

	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/teaching/prTchCourseTeacher";

	}
	
	public void setBean(PrTchCourseTeacher instance) {
		super.superSetBean(instance);
		
	}
	
	public PrTchCourseTeacher getBean(){
		return super.superGetBean();
	}

	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PrTchCourseTeacher.class);
		dc.createCriteria("peTeacher", "peTeacher");
		dc.createCriteria("enumConstByFlagTeacherType", "enumConstByFlagTeacherType");
		return dc;
	}
	
	

}
