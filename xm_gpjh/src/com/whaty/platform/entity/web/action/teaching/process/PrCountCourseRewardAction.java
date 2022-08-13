package com.whaty.platform.entity.web.action.teaching.process;

import java.util.Map;

import com.whaty.platform.entity.web.action.MyBaseAction;

/**
 * 核算课酬类
 * @author Administrator
 *
 */
public class PrCountCourseRewardAction extends MyBaseAction {

	private static final long serialVersionUID = 8522436939402955022L;

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
		// TODO 国际化
		//教师姓名，编号，卫星导学课程费用，网络指导费，网络费，其他（值机答疑）。详单统计课程表
		this.setServletPath("/entity/teaching/countcoursereward");
		
		this.getGridConfig().setTitle(this.getText("教师课程酬金"));
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().addMenuScript(this.getText("test.back"), "{window.history.back();}");
		
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("教师编号"), "tea_id", true, true, true, "TextField", false, 50);
		this.getGridConfig().addColumn(this.getText("教师姓名"), "tea_name", true, true, true, "TextField", false, 50);
		this.getGridConfig().addColumn(this.getText("导学课程费用"), "edutype", true, true, true, "TextField", false, 50);
		this.getGridConfig().addColumn(this.getText("网络指导费用"), "major", true, true, true, "TextField", false, 50);
		this.getGridConfig().addColumn(this.getText("其他费用"), "note", false, true, true, "TextField", false, 50);
		this.getGridConfig().addColumn(this.getText("总费用"), "note", false, true, true, "TextField", false, 50);
		this.getGridConfig().addRenderFunction(this.getText("详单"), "<a href=\"countcoursereward_getdetail.action?bean.id=${value}\" target=\"_blank\">查看详细信息</a>", "id");
		
	

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
	public String getdetail() {
		// TODO Auto-generated method stub
		return "detail";
	}
	public String turnto() {
		// TODO Auto-generated method stub
		return "turnto";
	}

}
