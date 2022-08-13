package com.whaty.platform.entity.web.action.teaching.process;

import java.util.Map;

import com.whaty.platform.entity.web.action.MyBaseAction;

public class TeachingStatAction extends MyBaseAction {

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

		this.setServletPath("/entity/teaching/teachingStat");
		this.getGridConfig().setTitle(this.getText("教师教学统计"));
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().addColumn(this.getText("id"),"id",false);
		this.getGridConfig().addColumn(this.getText("教师姓名"),"name");
		this.getGridConfig().addColumn(this.getText("平台登陆"),"loginNum");
		this.getGridConfig().addColumn(this.getText("BBS登陆"),"BBSNum");
		this.getGridConfig().addColumn(this.getText("发帖数"),"postNum");
		this.getGridConfig().addColumn(this.getText("回帖数"),"replay");
		this.getGridConfig().addColumn(this.getText("整理精华帖子数"),"paperNum");
		this.getGridConfig().addColumn(this.getText("未回复帖子数"),"unreplay");
		this.getGridConfig().addColumn(this.getText("上传导学资料数"),"upload");
		this.getGridConfig().addColumn(this.getText("登陆聊天室次数"),"logint");
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
