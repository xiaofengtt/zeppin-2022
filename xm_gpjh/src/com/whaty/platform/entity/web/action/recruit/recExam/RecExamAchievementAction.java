package com.whaty.platform.entity.web.action.recruit.recExam;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeRecruitplan;
import com.whaty.platform.entity.bean.PrRecExamStuCourse;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.recruit.recExam.PrRecExamStuCourseService;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.util.Const;

/**
 * 入学考试成绩管理Action 1、考试成绩查询修改 2、考试成绩导入
 * 
 * @author 李冰
 * 
 */
public class RecExamAchievementAction extends MyBaseAction {
	private File upload;
	private PrRecExamStuCourseService prRecExamStuCourseService;

	/**
	 * 转向批量上传页面
	 */
	public String batch() throws EntityException {
		// 首先判断时间是否在成绩录入操作时间范围内
		DetachedCriteria dc = DetachedCriteria.forClass(PeRecruitplan.class);
		dc.add(Restrictions.eq("flagActive", "1"));
		List<PeRecruitplan> peRecruitplan = this.getGeneralService()
				.getList(dc);
		Date start = peRecruitplan.get(0).getScoreStartDate();
		Date end = peRecruitplan.get(0).getScoreEndDate();
		Date now = new Date();
		if (!start.before(now))
			this.setMsg("成绩录入尚未开始，无法操作!");
		if (!Const.compareDate(now, end))
			this.setMsg("成绩录入已经结束，无法操作!");
		
		if (this.getMsg() != null) {
			return "msg";
		} else {
			return "batch";
		}
	}

	/**
	 * 上传处理功能
	 */
	public String batchexe() {
		int count = 0;
		try {
			count = this.getPrRecExamStuCourseService().saveUploadScore(
					this.getUpload());
		} catch (Exception e) {
			this.setMsg(e.getMessage());
			this.setTogo("back");
			return "msg";
		}
		this.setMsg("共" + count + "条数据上传成功！");
		this.setTogo("back");
		return "msg";
	}

	@Override
	public void initGrid() {
		this.getGridConfig().setTitle(this.getText("入学考试成绩列表"));
		this.getGridConfig().setCapability(false, false, true);

		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("学生姓名"),
				"peRecStudent.name", true, false, true, "");
		this.getGridConfig().addColumn(this.getText("准考证号"),
				"peRecStudent.examCardNum", true, false, true, "");
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
		this.getGridConfig().addColumn(this.getText("考试成绩"), "score",
				true, true, true, Const.score_for_extjs);

	}

	/**
	 * 修改之前检查是否允许修改
	 */
	public void checkBeforeUpdate() throws EntityException {
		// 首先判断时间是否在成绩录入操作时间范围内
		DetachedCriteria dc = DetachedCriteria.forClass(PeRecruitplan.class);
		dc.add(Restrictions.eq("flagActive", "1"));
		List<PeRecruitplan> peRecruitplan = this.getGeneralService()
				.getList(dc);
		Date start = peRecruitplan.get(0).getScoreStartDate();
		Date end = peRecruitplan.get(0).getScoreEndDate();
		Date now = new Date();
		if (!start.before(now))
			throw new EntityException("成绩录入尚未开始，无法操作");
		if (!Const.compareDate(now, end))
			throw new EntityException("成绩录入已经结束，无法操作");
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PrRecExamStuCourse.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/recruit/examachievement";
	}

	public void setBean(PrRecExamStuCourse instance) {
		super.superSetBean(instance);

	}

	public PrRecExamStuCourse getBean() {
		return (PrRecExamStuCourse) super.superGetBean();
	}

	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria
				.forClass(PrRecExamStuCourse.class);
		dc.createCriteria("prRecExamCourseTime", "prRecExamCourseTime")
				.createCriteria("peRecExamcourse", "peRecExamcourse");
		DetachedCriteria dcprRecPlanMajorEdutype = dc.createCriteria(
				"peRecStudent", "peRecStudent").createCriteria(
				"prRecPlanMajorSite", "prRecPlanMajorSite").createAlias(
				"peSite", "peSite").createCriteria("prRecPlanMajorEdutype",
				"prRecPlanMajorEdutype");
		dcprRecPlanMajorEdutype
				.createCriteria("peRecruitplan", "peRecruitplan")
				.add(Restrictions.eq("flagActive", "1"));
		dcprRecPlanMajorEdutype.createAlias("peMajor", "peMajor").createAlias(
				"peEdutype", "peEdutype");
		return dc;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public PrRecExamStuCourseService getPrRecExamStuCourseService() {
		return prRecExamStuCourseService;
	}

	public void setPrRecExamStuCourseService(
			PrRecExamStuCourseService prRecExamStuCourseService) {
		this.prRecExamStuCourseService = prRecExamStuCourseService;
	}
}
