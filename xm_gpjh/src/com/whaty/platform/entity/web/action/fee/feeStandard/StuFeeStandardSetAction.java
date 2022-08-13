package com.whaty.platform.entity.web.action.fee.feeStandard;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeFeeLevel;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class StuFeeStandardSetAction extends MyBaseAction {

	private static final long serialVersionUID = 260728643506109768L;
	
	@Override
	public void initGrid() {		
		this.getGridConfig().setCapability(false, false, false,true);
		
		this.getGridConfig().setTitle(this.getText("请选择收费标准"));
		
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("feeStandard.name"), "name");
		this.getGridConfig().addColumn(this.getText("feeStandard.feePercredit"), "feePercredit", false, false, true, "");
		this.getGridConfig().addColumn(this.getText("feeStandard.oweFeeLimit"), "oweFeeLimit", false, false, true, "");
		this.getGridConfig().addColumn(this.getText("feeStandard.note"), "note");
		this.getGridConfig().addMenuFunction(this.getText("listStudentForFeeSet.change"), "/entity/fee/listStudentForFeeSet_updateStudentFeeLevelByBatch.action", true, false);
		this.getGridConfig().addMenuScript(this.getText("test.back"), "{window.history.go(-2);}");

	}
	@Override
	public void setServletPath() {
		this.servletPath="/entity/fee/stuFeeStandardSet";
	}
	
	
	
	@Override
	public String execute() {
		if (this.getIds() != null && this.getIds().length() > 0) {
			ActionContext axt = ActionContext.getContext();
			axt.getSession().put("studentIds", this.getIds());
		}
		return super.execute();
	}
	
	@Override
	public void setEntityClass() {
		this.entityClass=PeFeeLevel.class;
	}
	

}
