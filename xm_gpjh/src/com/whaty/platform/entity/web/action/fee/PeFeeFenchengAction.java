package com.whaty.platform.entity.web.action.fee;

import com.whaty.platform.entity.web.action.MyBaseAction;

public class PeFeeFenchengAction extends MyBaseAction {


	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(true, true, true);
		this.getGridConfig().setTitle(this.getText("学费分成统计"));
		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("学习中心"),"name");
		this.getGridConfig().addColumn(this.getText("分成百分比"), "feePercent",false,true,true,"TextField",true,100);
		this.getGridConfig().addColumn(this.getText("备注"),"percentNote",false,true,false,"TextArea",true,100);
	
	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/fee/peFeeFencheng";
	}


}
