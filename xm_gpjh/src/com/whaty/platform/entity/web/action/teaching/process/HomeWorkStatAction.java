package com.whaty.platform.entity.web.action.teaching.process;

import java.util.Map;

import com.whaty.platform.entity.web.action.MyBaseAction;

public class HomeWorkStatAction extends MyBaseAction {

	@Override
	public Map add() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map delete() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void init() {
		
		this.setServletPath("/entity/teaching/homeWorkStat");
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().setTitle(this.getText("作业统计"));
		this.getGridConfig().addColumn(this.getText("id"),"id",false);
		this.getGridConfig().addColumn(this.getText("课程"),"name");
		this.getGridConfig().addColumn(this.getText("开课人数"),"stuNum");
		this.getGridConfig().addColumn(this.getText("交作业人数"),"num");
	}

	@Override
	public Map update() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map updateColumn() {
		// TODO Auto-generated method stub
		return null;
	}

}
