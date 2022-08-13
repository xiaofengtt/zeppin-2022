package com.whaty.platform.entity.web.action.exam.finalExam;

import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.basic.PeSemesterAction;

public class PeFinalExamAction extends PeSemesterAction {

	@Override
	public void initGrid() {
		this.getGridConfig().setTitle(this.getText("期末考试时间设置"));
		this.getGridConfig().setCapability(false,false,true);
		
		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("所属学期"), "name");
		this.getGridConfig().addColumn(this.getText("预约开始时间"), "examBookingStartDate");
		this.getGridConfig().addColumn(this.getText("预约结束时间"), "examBookingEndDate");
		this.getGridConfig().addColumn(this.getText("考试开始时间"), "finalExamStartDate");
		this.getGridConfig().addColumn(this.getText("考试结束时间"), "finalExamEndDate");
		this.getGridConfig().addColumn(this.getText("成绩查询时间"), "finalExamScoreDate");
//		this.getGridConfig().addMenuScript("时间设置后发布公告", "{alert('时间设置公告已发出！')}");
	}

	
	public void setServletPath() {
		this.servletPath = "/entity/exam/peFinalExam";
	}
	public void checkBeforeUpdate() throws EntityException{
		StringBuffer msg = new StringBuffer();
		if(this.getBean().getExamBookingStartDate().after(this.getBean().getExamBookingEndDate())){
			msg.append("预约开始时间不能晚于预约结束时间<br/>");
		}
		if(this.getBean().getExamBookingEndDate().after(this.getBean().getFinalExamStartDate())){
			msg.append("预约结束时间不能晚于考试开始时间<br/>");
		}
		if(this.getBean().getFinalExamStartDate().after(this.getBean().getFinalExamEndDate())){
			msg.append("考试开始时间不能晚于考试结束时间<br/>");
		}
		if(this.getBean().getFinalExamScoreDate().before(this.getBean().getFinalExamEndDate())){
			msg.append("成绩查询时间不能早于考试结束时间<br/>");
		}
		if(msg.length() > 0){
			throw new EntityException(msg.toString());
		}
	}
}
