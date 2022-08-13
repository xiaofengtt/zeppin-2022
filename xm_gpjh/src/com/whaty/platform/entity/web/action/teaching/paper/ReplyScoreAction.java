package com.whaty.platform.entity.web.action.teaching.paper;

import java.io.File;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PrTchStuPaper;
import com.whaty.platform.entity.service.teaching.paper.FinalScoreService;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.util.Const;

/**
 * 毕业答辩成绩查看
 * 
 * @author zy
 * 
 */
public class ReplyScoreAction extends MyBaseAction<PrTchStuPaper> {
	private File upload;
	private FinalScoreService finalScoreService;
	public FinalScoreService getFinalScoreService() {
		return finalScoreService;
	}

	public void setFinalScoreService(FinalScoreService finalScoreService) {
		this.finalScoreService = finalScoreService;
	}

	/**
	 * 答辩成绩导入
	 * 
	 */
	public String replyScoreBatch() {
		this.setTogo("back");
		int count = 0;
		try {
			count = this.getFinalScoreService().saveReplyScore(this.getUpload());
		} catch (Exception e) {
			this.setMsg(e.getMessage());
			return "msg";
		}
		this.setMsg("共" + count + "条数据上传成功！");
		return "msg";
	}

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, true);
		this.getGridConfig().setTitle(this.getText("毕业答辩成绩查看"));
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("学号"), "peStudent.regNo");
		this.getGridConfig().addColumn(this.getText("姓名"), "peStudent.trueName",true,false,true,"TextField",false,50);
		this.getGridConfig().addColumn(this.getText("年级"),"peStudent.peGrade.name");
		this.getGridConfig().addColumn(this.getText("学习中心"),"peStudent.peSite.name");
		this.getGridConfig().addColumn(this.getText("层次"),"peStudent.peEdutype.name");
		this.getGridConfig().addColumn(this.getText("专业"),"peStudent.peMajor.name");
		this.getGridConfig().addColumn(this.getText("论文题目"),"prTchPaperTitle.title");
		this.getGridConfig().addColumn(this.getText("指导教师"),"prTchPaperTitle.peTeacher.name");
//		this.getGridConfig().addColumn(this.getText("答辩成绩"), "rejoinScore", true, true, false,"regex:new RegExp(/^\\d{1,3}(\\.\\d{0,1})?$/),regexText:'成绩输入格式：1到2位整数 0到1位小数',");
		this.getGridConfig().addColumn(this.getText("答辩成绩"), "rejoinScore", true, true, false,Const.score_for_extjs);
		this.getGridConfig().addRenderScript("答辩成绩", "{if (${value}=='') return '未录入';return ${value}}", "rejoinScore");
		this.getGridConfig().addMenuScript("导入登分表","{window.location ='/entity/manager/teaching/replyScore_batch.jsp'}");
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PrTchStuPaper.class;

	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/teaching/replyScoreSearch";

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
		dc.add(Restrictions.eq("enumConstByFlagPaperRejoin.code", "1"));
		dc.createCriteria("prTchPaperTitle", "prTchPaperTitle").createAlias("peTeacher", "peTeacher").createAlias("peSemester", "peSemester");
		dc.add(Restrictions.eq("enumConstByFlagStudentStatus.code", "4"));
		dc.add(Restrictions.eq("peSemester.flagActive", "1"));
		return dc;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}
}
