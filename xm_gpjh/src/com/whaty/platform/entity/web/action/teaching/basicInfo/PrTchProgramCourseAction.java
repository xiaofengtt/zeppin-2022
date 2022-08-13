package com.whaty.platform.entity.web.action.teaching.basicInfo;

import org.hibernate.criterion.DetachedCriteria;

import com.whaty.platform.entity.bean.PeTchProgram;
import com.whaty.platform.entity.bean.PrTchProgramCourse;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class PrTchProgramCourseAction extends MyBaseAction<PrTchProgramCourse> {

	@Override
	public void initGrid() {
		
		String tchProgramName = ((PeTchProgram)this.getMyListService().getById(PeTchProgram.class, this.getBean().getPeTchProgramGroup().getPeTchProgram().getId())).getName();
		
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().setTitle(tchProgramName + "的课程列表");
		this.getGridConfig().addMenuScript(this.getText("test.back"), "{window.history.back();}");

		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("类型名称"), "peTchProgramGroup.peTchCoursegroup.name", true, true, true, "TextField", false, 50);
		this.getGridConfig().addColumn(this.getText("课程编号"), "peTchCourse.code", true, true, true, "TextField", false, 50);
		this.getGridConfig().addColumn(this.getText("课程名称"), "peTchCourse.name", true, true, true, "TextField", false, 50);
		this.getGridConfig().addColumn(this.getText("学分"), "credit", true, true, true,"regex:new RegExp(/^\\d{1,2}(\\.\\d{0,1})?$/),regexText:'学分输入格式：1到2位整数 0到1位小数',");
		this.getGridConfig().addColumn(this.getText("主干课程标记"), "enumConstByFlagIsMainCourse.name", true, true, true, "TextField", false, 50);
		this.getGridConfig().addColumn(this.getText("建议学期"), "unit", true, true, true,"regex:new RegExp(/^\\d{1}?$/),regexText:'输入格式：1位整数',");

	}
	public void setEntityClass() {
		this.entityClass = PrTchProgramCourse.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/teaching/prTchProgramCourse";
	}
	
	public void setBean(PrTchProgramCourse instance) {
		super.superSetBean(instance);
	}
	
	public PrTchProgramCourse getBean() {
		return super.superGetBean();
	}

	@Override
	public DetachedCriteria initDetachedCriteria() {
		
		DetachedCriteria dc = DetachedCriteria.forClass(PrTchProgramCourse.class);
		
		dc.createCriteria("peTchCourse","peTchCourse");
		dc.createCriteria("enumConstByFlagIsMainCourse","enumConstByFlagIsMainCourse");
		dc.createCriteria("peTchProgramGroup","peTchProgramGroup").createAlias("peTchCoursegroup", "peTchCoursegroup").createAlias("peTchProgram", "peTchProgram");

//		dc.add(Restrictions.eq("peTchProgram.id", this.getBean().getPeTchProgramGroup().getPeTchProgram().getId()));
		
		return dc;
	}
}
