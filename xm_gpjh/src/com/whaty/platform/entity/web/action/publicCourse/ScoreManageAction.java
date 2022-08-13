package com.whaty.platform.entity.web.action.publicCourse;

import com.whaty.platform.entity.web.action.MyBaseAction;

public class ScoreManageAction extends MyBaseAction {
	
	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, true);
		this.getGridConfig().setTitle(this.getText("成绩管理"));
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("课程名称"), "namee", true, false, true, "",false, 2000);
		this.getGridConfig().addColumn(this.getText("学号"), "trueName", true, false, true, "",false, 2000);
		this.getGridConfig().addColumn(this.getText("姓名"), "trueName1", true, false, true, "",false, 2000);
		this.getGridConfig().addColumn(this.getText("专业"), "trueName2", true, false, true, "",false, 2000);
		this.getGridConfig().addColumn(this.getText("班别"), "trueName3", true, false, true, "",false, 2000);
		this.getGridConfig().addColumn(this.getText("校区"), "trueName4", true, false, true, "",false, 2000);
		this.getGridConfig().addColumn(this.getText("成绩"), "trueName5");
		
	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/publicCourse/scoreManage";
		
	}
	
	public String turnToUpload() {
		return "turnToUpload";
	}

}
