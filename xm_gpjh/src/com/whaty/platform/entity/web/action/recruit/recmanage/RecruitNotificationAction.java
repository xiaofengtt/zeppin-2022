package com.whaty.platform.entity.web.action.recruit.recmanage;

import java.util.Map;

import com.whaty.platform.entity.web.action.MyBaseAction;

public class RecruitNotificationAction extends MyBaseAction {

	@Override
	public void init() {
		this.setServletPath("/entity/recruit/recruitNotification");
		this.getGridConfig().setCapability(true, false, true);
		this.getGridConfig().setTitle(this.getText("打印录取通知书"));
		this.getGridConfig().addColumn(this.getText("姓名"),"name");
		this.getGridConfig().addColumn(this.getText("学习中心"), "site");
		this.getGridConfig().addColumn(this.getText("层次"),"type");
		this.getGridConfig().addColumn(this.getText("专业") ,"major");
		this.getGridConfig().addColumn(this.getText("录取状态"),"status");
		this.getGridConfig().addColumn("录取号","notiNo");
		this.getGridConfig().addMenuScript(this.getText("打印录取通知书"), "{	alert('打印录取通知书')}");
	}

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
