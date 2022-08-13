package com.whaty.platform.entity.web.action.publicCourse;

import com.whaty.platform.entity.web.action.MyBaseAction;

public class ExamManageAction extends MyBaseAction {
	
	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, true);
		this.getGridConfig().setTitle(this.getText("考场安排"));
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("课程名称"), "namee");
		this.getGridConfig().addColumn(this.getText("学号"), "trueName");
		this.getGridConfig().addColumn(this.getText("姓名"), "trueName1");
		this.getGridConfig().addColumn(this.getText("专业"), "trueName2");
		this.getGridConfig().addColumn(this.getText("班别"), "trueName3");
		this.getGridConfig().addColumn(this.getText("校区"), "trueName4");
		this.getGridConfig().addColumn(this.getText("考场号"), "trueName5");
		this.getGridConfig().addColumn(this.getText("座位号"), "trueName5");
		
	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/publicCourse/examManage";
		
	}
	
	public String turnToAutoAllotRoom() {
		return "turnToAutoAllotRoom";
	}

}
