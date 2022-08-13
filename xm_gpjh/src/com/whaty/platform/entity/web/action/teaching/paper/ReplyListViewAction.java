package com.whaty.platform.entity.web.action.teaching.paper;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PrTchStuPaper;
import com.whaty.platform.entity.service.sms.PeSmsInfoService;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class ReplyListViewAction extends MyBaseAction<PrTchStuPaper> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5665592011841335551L;

	private PeSmsInfoService peSmsInfoService;
	
	public PeSmsInfoService getPeSmsInfoService() {
		return peSmsInfoService;
	}

	public void setPeSmsInfoService(PeSmsInfoService peSmsInfoService) {
		this.peSmsInfoService = peSmsInfoService;
	}

	/**
	 * 邮件，短信通知学生答辩时间地点
	 * 
	 */
	public void exe() {
		
	}

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false);

		this.getGridConfig().setTitle(this.getText("毕业答辩名单查看"));
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("学号"), "peStudent.regNo");
		this.getGridConfig().addColumn(this.getText("姓名"), "peStudent.trueName");
		this.getGridConfig().addColumn(this.getText("年级"),
				"peStudent.peGrade.name");
		this.getGridConfig().addColumn(this.getText("学习中心"),
				"peStudent.peSite.name");
		this.getGridConfig().addColumn(this.getText("层次"),
				"peStudent.peEdutype.name");
		this.getGridConfig().addColumn(this.getText("专业"),
				"peStudent.peMajor.name");
		this.getGridConfig().addColumn(this.getText("指导教师"),"prTchPaperTitle.peTeacher.name");
		this.getGridConfig().addColumn(this.getText("终稿分数"), "finalScore");

		EnumConst enumConstByFlagPaperRejoin = this.getMyListService()
				.getEnumConstByNamespaceCode("FlagPaperRejoin", "0");
		this.getGridConfig().addMenuFunction("取消参加答辩",
				"enumConstByFlagPaperRejoin",
				enumConstByFlagPaperRejoin.getId());

	}

	@Override
	public void setEntityClass() {
		this.entityClass = PrTchStuPaper.class;

	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/teaching/replyListView";

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
				"peGrade", "peGrade").createAlias("peMajor", "peMajor");
		dc.createCriteria("enumConstByFlagPaperRejoin", "enumConstByFlagPaperRejoin");
		dc.add(Restrictions.eq("enumConstByFlagPaperRejoin.code", "1"));
		dc.createCriteria("prTchPaperTitle", "prTchPaperTitle").createAlias("peTeacher", "peTeacher").createAlias("peSemester", "peSemester");
		dc.add(Restrictions.eq("enumConstByFlagStudentStatus.code", "4"));
		dc.add(Restrictions.eq("peSemester.flagActive", "1"));
		return dc;
	}

}
