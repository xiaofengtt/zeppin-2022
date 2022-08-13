package com.whaty.platform.entity.web.action.workspaceStudent;

import com.whaty.platform.entity.bean.PrTchStuElective;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class PeLearntCourseScoreAction extends MyBaseAction<PrTchStuElective> {

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false);
		
		this.getGridConfig().setTitle(this.getText("已修课程查询"));
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("课程名"), "prTchOpencourse.peTchCourse.name");
		this.getGridConfig().addColumn(this.getText("课程类型"),"courseType");
		this.getGridConfig().addColumn(this.getText("学分"), "xuefen");
		this.getGridConfig().addColumn(this.getText("学习学期"), "studyTerm");
		this.getGridConfig().addColumn(this.getText("成绩"),"score");

	}

	@Override
	public void setEntityClass() {
		this.entityClass = PrTchStuElective.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/workspaceStudent/peLearntCourseScore";
	}

}
