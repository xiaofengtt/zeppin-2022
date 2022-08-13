package com.whaty.platform.entity.web.action.teaching.process;

import java.util.Map;

import com.whaty.platform.entity.web.action.MyBaseAction;

/**
 * 核算批改作业费
 * @author Administrator
 *
 */
public class PrCountCheckofWorkFeeAction extends MyBaseAction {

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
		//（作业名称，作业形式（自动批改，手动批改，书面Email作业），作业题量分值（详单），作业人数（已交/应交），批改数（作业详单））
		//TODO 国际化
		this.setServletPath("/entity/teaching/countcheckofworkfee");
		
		this.getGridConfig().setTitle(this.getText("批改作业费"));
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().addMenuScript(this.getText("test.back"), "{window.history.back();}");
		
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("作业名称"), "name", true, true, true, "TextField", false, 50);
		this.getGridConfig().addColumn(this.getText("作业形式"), "type", true, true, true, "TextField", false, 50);
		this.getGridConfig().addColumn(this.getText("作业题量分值(详单)"), "credit", true, true, true, "TextField", false, 50);
		this.getGridConfig().addColumn(this.getText("作业人数(详单)"), "testnum", true, true, true, "TextField", false, 50);
		this.getGridConfig().addColumn(this.getText("批改数(详单)"), "worknum", true, true, true, "TextField", false, 50);
		this.getGridConfig().addColumn(this.getText("备注"), "note", false, true, true, "TextField", false, 50);

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
	public String turnto() {
		// TODO Auto-generated method stub
		return "turnto";
	}
}
