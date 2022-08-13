package com.whaty.platform.entity.web.action.publicCourse;

import com.whaty.platform.entity.web.action.MyBaseAction;

public class EnrolStatAction extends MyBaseAction {
	
	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().setTitle(this.getText("注册统计"));
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("学期"), "namee");
		this.getGridConfig().addColumn(this.getText("课程"), "trueName");
		this.getGridConfig().addColumn(this.getText("选课人数"), "trueDat");
		this.getGridConfig().addColumn(this.getText("已注册人数"), "trueText");
		this.getGridConfig().addColumn(this.getText("未注册人数"), "trueDat");
	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/publicCourse/enrolStat";
		
	}

}
