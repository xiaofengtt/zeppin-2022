package com.whaty.platform.entity.web.action.teaching.process;

import com.whaty.platform.entity.web.action.MyBaseAction;

public class PeCountCourseCoefficientAction extends MyBaseAction {

	@Override
	public void init() {
		// TODO 国际化
		this.setServletPath("/entity/teaching/countcoursecoefficient");

		this.getGridConfig().setTitle(this.getText("系数列表"));
		this.getGridConfig().setCapability(false, false, true);
		
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("说明"), "name", true, true, true, "TextField", false, 50);
		this.getGridConfig().addColumn(this.getText("系数"), "grade", true, true, true, "TextField", false, 50);
	

	}

}
