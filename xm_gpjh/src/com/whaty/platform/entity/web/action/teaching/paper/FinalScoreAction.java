package com.whaty.platform.entity.web.action.teaching.paper;


import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PrTchStuPaper;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.teaching.paper.FinalScoreService;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.util.Const;

public class FinalScoreAction extends MyBaseAction<PrTchStuPaper> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2531431740950984702L;
	private FinalScoreService finalScoreService;
	
	public FinalScoreService getFinalScoreService() {
		return finalScoreService;
	}

	public void setFinalScoreService(FinalScoreService finalScoreService) {
		this.finalScoreService = finalScoreService;
	}

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false);

		this.getGridConfig().setTitle(this.getText("论文总成绩查看"));
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("学号"), "peStudent.regNo");
		this.getGridConfig().addColumn(this.getText("姓名"), "peStudent.trueName");
		this.getGridConfig().addColumn(this.getText("年级"),"peStudent.peGrade.name");
		this.getGridConfig().addColumn(this.getText("学习中心"),"peStudent.peSite.name");
		this.getGridConfig().addColumn(this.getText("层次"),"peStudent.peEdutype.name");
		this.getGridConfig().addColumn(this.getText("专业"),"peStudent.peMajor.name");
		this.getGridConfig().addColumn(this.getText("论文题目"),"prTchPaperTitle.title");
		this.getGridConfig().addColumn(this.getText("指导教师"),"prTchPaperTitle.peTeacher.name");
		
//		this.getGridConfig().addColumn(this.getText("终稿成绩"),"finalScore");
		
		this.getGridConfig().addColumn(this.getText("终稿成绩"), "finalScore", true, true, false,Const.score_for_extjs);
		this.getGridConfig().addRenderScript("终稿成绩", "{if (${value}=='') return '未录入';return ${value}}", "finalScore");

		this.getGridConfig().addColumn("是否参加答辩", "enumConstByFlagPaperRejoin.name",false,false,false,"TextField",true,50);
		
		this.getGridConfig().addColumn(this.getText("答辩成绩"), "rejoinScore",false,false,false,"TextField",true,50);
		this.getGridConfig().addRenderScript("答辩成绩", "{if (record.data['enumConstByFlagPaperRejoin.name']=='不参加答辩') return '不参加答辩';else if (record.data['rejoinScore']=='') return '未录入';return record.data['rejoinScore']}", null);
//		this.getGridConfig().addRenderScript("答辩成绩", "{alert(record.data['enumConstByFlagPaperRejoin.name']);}", null);
		
		this.getGridConfig().addColumn(this.getText("总成绩"), "scoreTotal",true,true,false,Const.score_for_extjs);
		this.getGridConfig().addRenderScript("总成绩", "{if (${value}=='') return '未录入';return ${value}}", "scoreTotal");
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PrTchStuPaper.class;

	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/teaching/finalScore";
	}

	public void setBean(PrTchStuPaper instance) {
		super.superSetBean(instance);
	}

	public PrTchStuPaper getBean() {
		return super.superGetBean();
	}

	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PrTchStuPaper.class);
		dc.createCriteria("peStudent", "peStudent").createAlias("enumConstByFlagStudentStatus", "enumConstByFlagStudentStatus").createAlias("peSite",
				"peSite").createAlias("peEdutype", "peEdutype").createAlias(
				"peMajor", "peMajor").createAlias("peGrade", "peGrade");
		dc.createCriteria("enumConstByFlagPaperRejoin", "enumConstByFlagPaperRejoin");
		dc.createCriteria("prTchPaperTitle", "prTchPaperTitle").createAlias("peTeacher", "peTeacher").createAlias("peSemester", "peSemester");
		dc.add(Restrictions.eq("peSemester.flagActive", "1"));
		dc.add(Restrictions.eq("enumConstByFlagStudentStatus.code", "4"));
		return dc;
	}
	
	public String compose() {
		String result = "";
		try {
			result = this.getFinalScoreService().update_compose();
			this.setMsg(result);
		} catch (Exception e) {
			e.printStackTrace();
			this.setMsg("合成失败。");
		}
		this.setTogo("back");
		return "msg";
	}
}
