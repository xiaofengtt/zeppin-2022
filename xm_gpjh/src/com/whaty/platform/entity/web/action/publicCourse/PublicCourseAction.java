package com.whaty.platform.entity.web.action.publicCourse;

import com.whaty.platform.entity.web.action.MyBaseAction;

public class PublicCourseAction extends MyBaseAction{
	
	@Override
	public void initGrid() {
		this.getGridConfig().setTitle(this.getText("课程管理"));
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("课程名称"), "namee");
		this.getGridConfig().addColumn(this.getText("指导教师"), "trueName");
		this.getGridConfig().addColumn(this.getText("简介"), "trueText", false, true, false, "TextArea",false, 2000);
		
	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/publicCourse/publicCourse";
		
	}

}
