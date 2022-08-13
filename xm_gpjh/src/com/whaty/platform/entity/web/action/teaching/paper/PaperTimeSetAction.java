package com.whaty.platform.entity.web.action.teaching.paper;

import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.basic.PeSemesterAction;
import com.whaty.platform.util.Const;

public class PaperTimeSetAction extends PeSemesterAction {

	@Override
	public void initGrid() {
		this.getGridConfig().setTitle(this.getText("毕业论文时间设置"));
		this.getGridConfig().setCapability(false, false, true,true);

		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("学期"), "name");
		this.getGridConfig().addColumn(this.getText("开始日期"), "paperStartDate");
		this.getGridConfig().addColumn(this.getText("结束日期"), "paperEndDate");
		this.getGridConfig().addColumn(this.getText("选题截止日期"), "paperTitleEndDate");
		this.getGridConfig().addColumn(this.getText("开题报告截止"), "paperSyllabusEndDate");
		this.getGridConfig().addColumn(this.getText("初稿截止日期"), "paperDraftEndDate");
		this.getGridConfig().addColumn(this.getText("终搞截止日期"), "paperFinalEndDate");
		this.getGridConfig().addColumn(this.getText("答辩截止日期"), "paperRejoinEndDate");
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/teaching/paperTime";
	}

	@Override
	public void checkBeforeUpdate() throws EntityException {
		super.checkBeforeUpdate();
		if (this.getBean().getPaperStartDate().getTime() >= this.getBean().getPaperEndDate().getTime()){
			throw new EntityException("毕业论文的开始时间不能晚于结束时间");
		}
		if (this.getBean().getPaperStartDate().getTime() < this.getBean().getStartDate().getTime()) {
			throw new EntityException("毕业论文开始时间不能早于学期开始时间");
		}
		if (this.getBean().getPaperEndDate().getTime() > this.getBean().getEndDate().getTime()) {
			throw new EntityException("毕业论文结束时间不能晚于学期结束时间");
		}
		if (this.getBean().getPaperTitleEndDate().getTime() < this.getBean().getPaperStartDate().getTime()) {
			throw new EntityException("选题截止日期不能早于毕业论文的开始时间");
		}
		if (this.getBean().getPaperTitleEndDate().getTime() > this.getBean().getPaperSyllabusEndDate().getTime()) {
			throw new EntityException("选题截止日期不能晚于开题报告截至日期");
		}
		if (this.getBean().getPaperDraftEndDate().getTime() < this.getBean().getPaperSyllabusEndDate().getTime()) {
			throw new EntityException("开题报告截至时间不能晚于初稿截至时间");
		}
		if (this.getBean().getPaperDraftEndDate().getTime() > this.getBean().getPaperFinalEndDate().getTime()) {
			throw new EntityException("初稿截至时间不能晚于终稿截至时间");
		}
		if (this.getBean().getPaperFinalEndDate().getTime() > this.getBean().getPaperRejoinEndDate().getTime()) {
			throw new EntityException("终稿截至时间不能晚于答辩截至时间");
		}
		if (this.getBean().getPaperRejoinEndDate().getTime() > this.getBean().getPaperEndDate().getTime()) {
			throw new EntityException("答辩截至时间不能晚于毕业答辩结束时间");
		}
	}
	
	
}
