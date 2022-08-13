package com.whaty.platform.entity.web.action.exam.finalExam;

import java.util.Map;

import com.whaty.platform.entity.web.action.MyBaseAction;

public class PrExamPaperCountAction extends MyBaseAction {

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
		// TODO Auto-generated method stub
		this.getGridConfig().setCapability(false,false,false,false);
		this.setServletPath("/entity/exam/prExamPaperCount");
		this.getGridConfig().setTitle(this.getText("试卷派发表"));
		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("考试批次"), "batchNo");
		this.getGridConfig().addColumn(this.getText("学习中心"), "a");
		this.getGridConfig().addColumn(this.getText("课程名称"), "b");
		this.getGridConfig().addColumn(this.getText("试卷袋数"), "c");
		this.getGridConfig().addColumn(this.getText("试卷数"), "c");
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
