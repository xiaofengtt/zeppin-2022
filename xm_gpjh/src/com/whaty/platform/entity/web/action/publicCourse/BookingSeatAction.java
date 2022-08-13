package com.whaty.platform.entity.web.action.publicCourse;

import com.whaty.platform.entity.web.action.MyBaseAction;

public class BookingSeatAction extends MyBaseAction {
	
	@Override
	public void initGrid() {
		this.getGridConfig().setTitle(this.getText("订座信息"));
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("课程名称"), "namee");
		this.getGridConfig().addColumn(this.getText("上课时间"), "trueDatetime");
		this.getGridConfig().addColumn(this.getText("开始预定时间"), "trueName1Datetime");
		this.getGridConfig().addColumn(this.getText("预定结束时间"), "trueName2Datetime");
		this.getGridConfig().addColumn(this.getText("上课地点"), "trueName3");
		this.getGridConfig().addColumn(this.getText("状态"), "trueName4");
		this.getGridConfig().addColumn(this.getText("限定人数"), "trueName5");
		this.getGridConfig().addColumn(this.getText("已报人数"), "trueText", true, false, true, "",false, 2000);
		this.getGridConfig().addColumn(this.getText("查看"), "trueName5");
	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/publicCourse/bookingSeat";
		
	}

}
