package com.whaty.platform.entity.web.action.studentStatus.graduate;

import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.basic.PeSemesterAction;

public class PeGraduateAction extends PeSemesterAction {

	private static final long serialVersionUID = 465854310701837291L;

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false,false,true,true);
		this.getGridConfig().setTitle(this.getText("毕业批次列表"));
		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("学期"), "name",true,false,true,"");
		this.getGridConfig().addColumn(this.getText("学期开始时间"), "startDate",true,false,true,"Date",false,50);
		this.getGridConfig().addColumn(this.getText("毕业申请开始时间"), "graduateStartDate",false,true,true,"Date",false,50);
		this.getGridConfig().addColumn(this.getText("毕业申请截止时间"), "graduateEndDate",false,true,true,"Date",false,50);
		this.getGridConfig().addColumn(this.getText("学期结束时间"), "endDate",true,false,true,"Date",false,50);
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/studentStatus/peGraduate";
	}
	
	public void checkBeforeUpdate() throws EntityException {
		if (this.getBean().getStartDate().after(this.getBean().getGraduateStartDate())) {
			throw new EntityException("毕业申请开始时间不能早于学期的开始时间...");
		}
		if (this.getBean().getGraduateStartDate().after(this.getBean().getGraduateEndDate())) {
			throw new EntityException("毕业申请开始时间不能晚于毕业申请截止时间...");			
		}
		if (this.getBean().getGraduateEndDate().after(this.getBean().getEndDate())) {
			throw new EntityException("毕业申请截止时间不能晚于学期结束时间...");			
		}
	}
	
}
