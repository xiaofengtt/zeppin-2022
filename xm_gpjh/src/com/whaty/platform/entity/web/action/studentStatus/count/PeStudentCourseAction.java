package com.whaty.platform.entity.web.action.studentStatus.count;

import com.whaty.platform.entity.web.action.MyBaseAction;

public class PeStudentCourseAction extends MyBaseAction {


	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().setTitle(this.getText("学生课程信息列表"));
		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("课程编号"), "a");
		this.getGridConfig().addColumn(this.getText("课程名称"), "v");
		this.getGridConfig().addColumn(this.getText("课程类型"), "f");
		this.getGridConfig().addColumn(this.getText("上课学期"), "e");
		this.getGridConfig().addColumn(this.getText("作业状态"), "g");

	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/studentStatus/peStudentCourse";
	}

}
