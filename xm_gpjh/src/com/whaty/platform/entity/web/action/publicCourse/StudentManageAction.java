package com.whaty.platform.entity.web.action.publicCourse;

import com.whaty.platform.entity.web.action.MyBaseAction;

public class StudentManageAction extends MyBaseAction {

	@Override
	public void initGrid() {
		this.getGridConfig().setTitle(this.getText("学生注册情况"));
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("课程名称"), "namee");
		this.getGridConfig().addColumn(this.getText("学号"), "trueName");
		this.getGridConfig().addColumn(this.getText("姓名"), "trueName1");
		this.getGridConfig().addColumn(this.getText("专业"), "trueName2");
		this.getGridConfig().addColumn(this.getText("班别"), "trueName3");
		this.getGridConfig().addColumn(this.getText("校区"), "trueName4");
		this.getGridConfig().addColumn(this.getText("手机号"), "trueName5");
		this.getGridConfig().addColumn(this.getText("注册情况"), "trueText", true, false, true, "",false, 2000);
		
	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/publicCourse/studentManage";
		
	}
	
	public String turnToUpload() {
		return "turnToUpload";
	}
	
}
