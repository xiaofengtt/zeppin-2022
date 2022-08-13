package com.whaty.platform.entity.web.action.teaching.result;

import java.io.File;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.service.teaching.examScore.ExamScoreBatchService;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.util.Const;

public class DegreeEnglishScoreAvtion extends MyBaseAction<PeStudent> {
	ExamScoreBatchService examScoreBatchService;
	private File upload;
	public ExamScoreBatchService getExamScoreBatchService() {
		return examScoreBatchService;
	}

	public void setExamScoreBatchService(ExamScoreBatchService examScoreBatchService) {
		this.examScoreBatchService = examScoreBatchService;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, true);
		this.getGridConfig().setTitle(this.getText("学位英语成绩管理"));
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("学号"), "regNo", true, false, true, "TextField",
				false, 50);
		this.getGridConfig().addColumn(this.getText("姓名"), "trueName", true, false, true, "TextField",
				false, 50);
		this.getGridConfig().addColumn(this.getText("身份证号"),
				"prStudentInfo.cardNo");
		this.getGridConfig().addColumn(this.getText("学习中心"), "peSite.name", true, false, true, "TextField",
				false, 50);
		this.getGridConfig().addColumn(this.getText("专业"), "peMajor.name", true, false, true, "TextField",
				false, 50);
		this.getGridConfig().addColumn(this.getText("层次"), "peEdutype.name", true, false, true, "TextField",
				false, 50);
		this.getGridConfig().addColumn(this.getText("学位英语成绩"),"scoreDegreeEnglish",true,true,false,Const.score_for_extjs);
		this.getGridConfig().addRenderScript("学位英语成绩", "{if (${value}=='') return '未录入';return ${value}}", "scoreDegreeEnglish");

	}
	/**
	 * 批量上传学位英语成绩
	 * @return
	 */
	public String degreeEnglishScoreBatch(){
		this.setTogo("back");
		int count = 0;
		try {
			count = this.getExamScoreBatchService().saveDegreeEnglishScore(this.getUpload());
		} catch (Exception e) {
			this.setMsg(e.getMessage());
			return "msg";
		}
		this.setMsg("共" + count + "条数据上传成功！");
		return "msg";
	}
	
	@Override
	public void setEntityClass() {
		this.entityClass = PeStudent.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/teaching/degreeEnglishScore";

	}

	public void setBean(PeStudent instance) {
		super.superSetBean(instance);
	}

	public PeStudent getBean() {
		return super.superGetBean();
	}

	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeStudent.class);
		dc.createCriteria("prStudentInfo","prStudentInfo");
		dc.createCriteria("peSite","peSite");
		dc.createCriteria("peMajor","peMajor");
		dc.createCriteria("peEdutype","peEdutype").add(Restrictions.like("name", "本", MatchMode.ANYWHERE));
		dc.createCriteria("enumConstByFlagStudentStatus", "enumConstByFlagStudentStatus");
		dc.add(Restrictions.eq("enumConstByFlagStudentStatus.code", "4"));
		return dc;
	}
}
