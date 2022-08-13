package com.whaty.platform.entity.web.action.recruit.stat;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeRecStudent;
import com.whaty.platform.entity.bean.PrRecExamStuCourse;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class ExamScoreSearchAction extends MyBaseAction<PrRecExamStuCourse> {

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false);

		this.getGridConfig().setTitle(this.getText("入学测试成绩查询"));
		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this
				.getGridConfig()
				.addColumn(this.getText("招生批次"),
						"peRecStudent.prRecPlanMajorSite.prRecPlanMajorEdutype.peRecruitplan.name");
		this.getGridConfig().addColumn(this.getText("姓名"), "peRecStudent.name");
		this.getGridConfig().addColumn(this.getText("证件号码"),
				"peRecStudent.cardNo");
		this.getGridConfig().addColumn(this.getText("准考证号"),
		"peRecStudent.examCardNum");
		this.getGridConfig().addColumn(this.getText("学习中心"),
				"peRecStudent.prRecPlanMajorSite.peSite.name");
		this
				.getGridConfig()
				.addColumn(this.getText("层次"),
						"peRecStudent.prRecPlanMajorSite.prRecPlanMajorEdutype.peEdutype.name");
		this
				.getGridConfig()
				.addColumn(this.getText("专业"),
						"peRecStudent.prRecPlanMajorSite.prRecPlanMajorEdutype.peMajor.name");
		this.getGridConfig().addColumn(this.getText("考试科目"),
				"prRecExamCourseTime.peRecExamcourse.name");
		this.getGridConfig().addColumn(this.getText("成绩"), "score");
		this.getGridConfig().addColumn(this.getText("录取状态"),
				"peRecStudent.enumConstByFlagMatriculate.name");
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PrRecExamStuCourse.class;

	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/recruit/examScoreSearch";
	}

	public void setBean(PrRecExamStuCourse instance) {
		super.superSetBean(instance);

	}

	public PrRecExamStuCourse getBean() {
		return (PrRecExamStuCourse) super.superGetBean();
	}

	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dcPrRecExamStuCourse = DetachedCriteria
				.forClass(PrRecExamStuCourse.class);
		DetachedCriteria dc = dcPrRecExamStuCourse.createCriteria(
				"peRecStudent", "peRecStudent");
		dcPrRecExamStuCourse.createCriteria("prRecExamCourseTime",
				"prRecExamCourseTime").createCriteria("peRecExamcourse",
				"peRecExamcourse");
		DetachedCriteria dcMajorEdutype = dc.createCriteria(
				"prRecPlanMajorSite", "prRecPlanMajorSite").createAlias("peSite", "peSite")
				.createCriteria("prRecPlanMajorEdutype",
						"prRecPlanMajorEdutype");
		dcMajorEdutype.createAlias("peEdutype", "peEdutype").createAlias(
				"peMajor", "peMajor");
		dcMajorEdutype.createCriteria("peRecruitplan", "peRecruitplan");
		dc.createCriteria("enumConstByFlagRecStatus",
				"enumConstByFlagRecStatus");
		dc.createCriteria("enumConstByFlagMatriculate",
				"enumConstByFlagMatriculate");
		return dcPrRecExamStuCourse;
	}

}
