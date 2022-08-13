package com.whaty.platform.entity.web.action.teaching.paper;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PrTchStuPaper;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.teaching.paper.FinalScoreService;
import com.whaty.platform.entity.web.action.MyBaseAction;

/**
 * 抽取答辩名单Action
 * 
 * @author zy
 * 
 */
public class ReplyListSelectAction extends MyBaseAction<PrTchStuPaper> {
	private FinalScoreService finalScoreService;
	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false,false,false);
		this.getGridConfig().setTitle(this.getText("毕业答辩名单抽取"));
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("学号"), "peStudent.regNo");
		this.getGridConfig().addColumn(this.getText("姓名"), "peStudent.trueName");
		this.getGridConfig().addColumn(this.getText("年级"),"peStudent.peGrade.name");
		this.getGridConfig().addColumn(this.getText("学习中心"),
				"peStudent.peSite.name");
		this.getGridConfig().addColumn(this.getText("层次"),
				"peStudent.peEdutype.name");
		this.getGridConfig().addColumn(this.getText("专业"),
				"peStudent.peMajor.name");
		this.getGridConfig().addColumn(this.getText("指导教师"),"prTchPaperTitle.peTeacher.name");
		this.getGridConfig().addColumn(this.getText("终稿分数"), "finalScore");
		this.getGridConfig().addColumn(this.getText("是否答辩"), "enumConstByFlagPaperRejoin.name", true, true, true, "TextField", true, 50);
		
		EnumConst enumConstByFlagPaperRejoin = this.getMyListService().getEnumConstByNamespaceCode("FlagPaperRejoin", "1");
		
		this.getGridConfig().addMenuFunction("抽取答辩", "enumConstByFlagPaperRejoin", enumConstByFlagPaperRejoin.getId());

		enumConstByFlagPaperRejoin = this.getMyListService().getEnumConstByNamespaceCode("FlagPaperRejoin", "0");
		
		this.getGridConfig().addMenuFunction("取消抽取", "enumConstByFlagPaperRejoin", enumConstByFlagPaperRejoin.getId());
		if(this.getGridConfig().checkBeforeAddMenu("/entity/teaching/replyListSelect_autoSetPaperRejoin.action")){
			this.getGridConfig().addMenuScript("随机抽取", "{window.location='/entity/teaching/replyListSelect_autoSetPaperRejoin.action'}");
		}
	}

	/**
	 * 随机抽取答辩学生
	 * @return
	 */
	public String autoSetPaperRejoin(){
		this.setTogo("back");
		try {
			this.setMsg(this.getFinalScoreService().savePaperRejoin());
		} catch (EntityException e) {
			e.printStackTrace();
			this.setMsg(e.getMessage());
		}
		
		return "msg";
	}
	@Override
	public void setEntityClass() {
		this.entityClass = PrTchStuPaper.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/teaching/replyListSelect";
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
		dc.add(Restrictions.eq("enumConstByFlagStudentStatus.code", "4"));
		dc.add(Restrictions.eq("peSemester.flagActive", "1"));
		return dc;
	}

	public FinalScoreService getFinalScoreService() {
		return finalScoreService;
	}

	public void setFinalScoreService(FinalScoreService finalScoreService) {
		this.finalScoreService = finalScoreService;
	}
	
}
