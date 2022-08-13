package com.whaty.platform.entity.web.action.teaching.elective;

import java.util.HashMap;
import java.util.Map;

import com.whaty.platform.entity.bean.PeSemester;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.basic.PeSemesterAction;

public class ElectiveTimeManageAction extends PeSemesterAction {

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, true);

		this.getGridConfig().setTitle(this.getText("选课时间设置"));
		
		this.getGridConfig().addColumn(this.getText("id"),"id",false);
		this.getGridConfig().addColumn(this.getText("所属选课学期"),"name");
		this.getGridConfig().addColumn(this.getText("选课开始时间"),"electiveStartDate");
		this.getGridConfig().addColumn(this.getText("选课结束时间"),"electiveEndDate");
		
		this.getGridConfig().addColumn(this.getText("新生选课开始时间"),"electiveFreshStartDate");
		this.getGridConfig().addColumn(this.getText("新生选课结束时间"),"electiveFreshEndDate");
		
		
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/teaching/electiveTimeManage";
	}

	@Override
	public void checkBeforeUpdate() throws EntityException {
		super.checkBeforeUpdate();
//		if(this.getBean().getCancelElectiveEndDate().getTime() < this.getBean().getCancelElectiveStartDate().getTime()){
//			throw new EntityException("更新失败,退课结束时间不能早于退课开始时间");
//		}
//		if(this.getBean().getElectiveEndDate().getTime()<this.getBean().getElectiveStartDate().getTime()){
//			throw new EntityException("更新失败,选课结束时间不能早于选课开始时间");
//		}
		if(this.getBean().getElectiveFreshEndDate().getTime()<this.getBean().getElectiveFreshStartDate().getTime()){
			throw new EntityException("更新失败,新生选课结束时间不能早于新生选课开始时间");
		}
//		if(this.getBean().getCancelElectiveStartDate().getTime()< this.getBean().getElectiveStartDate().getTime()){
//			throw new EntityException("更新失败,退课开始时间不能早于选课开始时间");
//		}
//		if(this.getBean().getCancelElectiveEndDate().getTime()>this.getBean().getElectiveEndDate().getTime()){
//			throw new EntityException("更新失败,退课结束时间不能晚于选课结束时间");
//		}
		if(this.getBean().getStartDate().getTime()>this.getBean().getElectiveEndDate().getTime()){
			throw new EntityException("更新失败,选课开始时间不能早于学期开始时间");
		}
		if(this.getBean().getEndDate().getTime()<this.getBean().getElectiveEndDate().getTime()){
			throw new EntityException("更新失败,选课结束时间不能晚于学期结束时间");
		}
	}
	
}
