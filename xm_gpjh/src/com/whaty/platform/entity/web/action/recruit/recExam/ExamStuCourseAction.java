package com.whaty.platform.entity.web.action.recruit.recExam;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeRecRoom;
import com.whaty.platform.entity.bean.PeRecStudent;
import com.whaty.platform.entity.bean.PeRecruitplan;
import com.whaty.platform.entity.bean.PeSite;
import com.whaty.platform.entity.bean.PrRecExamStuCourse;
import com.whaty.platform.entity.bean.PrRecPlanMajorEdutype;
import com.whaty.platform.entity.bean.PrRecPlanMajorSite;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.recruit.recExam.PrRecExamStuCourseService;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.util.JsonUtil;

/**
 * 入学考试考试明细，包括自动分配课程和自动分配考场
 * 
 * @author 李冰
 * 
 */
public class ExamStuCourseAction extends MyBaseAction {
	private PrRecExamStuCourseService prRecExamStuCourseService;
	private String result;// 用来储存操作结果

	/**
	 * 自动分配课程
	 * 
	 */
	public String autoAllotCourse() {
		try {
			// 首先判断时间，允许操作时间是在招生考试批次开始之后和入学考试开始之前
			this.setTogo("back");
			DetachedCriteria dc = DetachedCriteria
					.forClass(PeRecruitplan.class);
			dc.add(Restrictions.eq("flagActive", "1"));
			List<PeRecruitplan> peRecruitplan = this.getGeneralService()
					.getList(dc);
			Date start = peRecruitplan.get(0).getStartDate();
			Date end = peRecruitplan.get(0).getExamStartDate();
			Date examEnd = peRecruitplan.get(0).getExamEndDate();
			Date now = new Date();
			if (!start.before(now)) {
				this.setMsg("招生考试批次还未开始，无法分配课程");
				return "msg";
			}
			if (!now.before(examEnd)) {
				this.setMsg("考试已经结束，无法分配课程");
				return "msg";
			}
			if (!now.before(end)) {
				this.setMsg("考试已经开始，无法分配课程");
				return "msg";
			}
			// 分配课程之前检查原有记录是否已经有考试成绩
			List scoreList = this.checkScore();
			if (scoreList.size() > 0) {
				this.setMsg("有" + scoreList.size() + "条记录已经有成绩，无法重新分配课程");
				return "msg";
			}
			List list = this.getDataList();
			// 调用service的方法，向PrRecExamStuCourse表中插入数据
			this.setMsg(this.getPrRecExamStuCourseService().save(list));
		} catch (Exception e) {
			this.setMsg("操作失败！");
			e.printStackTrace();
		}
		return "msg";
	}

	/**
	 * 查询出来学生和考试课程关系的list
	 * 
	 * @return
	 */
	private List getDataList() throws EntityException {
		String sql = "			select student.id  stu_id, course_time.id  course_id 		"
				+ "			  from pe_rec_student              student,						"
				+ "			       pr_rec_plan_major_site      plan_site,					"
				+ "			       pr_rec_plan_major_edutype   plan_edutype,				"
				+ "			       pe_recruitplan              recruitplan,					"
				+ "			       pe_site                     site,						"
				+ "			       pe_edutype                  edutype,						"
				+ "			       pe_major                    major,						"
				+ "			       pr_rec_course_major_edutype cme,							"
				+ "			       pe_rec_examcourse           course,						"
				+ "			       pr_rec_exam_course_time     course_time					"
				+ "			 where student.fk_rec_major_site_id = plan_site.id				"
				+ "			   and plan_site.fk_rec_plan_major_edutype_id = plan_edutype.id	"
				+ "			   and plan_edutype.fk_recruitplan_id = recruitplan.id			"
				+ "			   and recruitplan.flag_active = '1'							"
				+ "			   and plan_site.fk_site_id = site.id							"
				+ "			   and plan_edutype.fk_major_id = major.id						"
				+ "			   and plan_edutype.fk_edutype_id = edutype.id					"
				+ "			   and major.id = cme.fk_major_id								"
				+ "			   and edutype.id = cme.fk_edutype_id							"
				+ "			   and course.id = cme.fk_rec_examcourse_id						"
				+ "			   and course.id = course_time.fk_rec_examcourse_id				"
				+ "			   and course_time.fk_recruitplan_id = recruitplan.id			"
				+ "			   and student.flag_noexam = (select enum.id					"
				+ "			                                from enum_const enum			"
				+ "			                               where enum.namespace = 'FlagNoexam'"
				+ "			                                 and enum.code = '0')			"
				+ "			   and student.flag_rec_status = (select enum.id					"
				+ "			                                from enum_const enum			"
				+ "			                               where enum.namespace = 'FlagRecStatus'"
				+ "			                                 and enum.code = '3')			"
				+ "			 group by student.id, course_time.id							";
		List list = new ArrayList();
		list = this.getGeneralService().getBySQL(sql);
		return list;
	}

	/**
	 * 查询已经有考试成绩的记录
	 * 
	 * @return
	 * @throws EntityException
	 */
	private List checkScore() throws EntityException {
		String sql = "			select fk_rec_student_id from pr_rec_exam_stu_course							"
				+ "				 where fk_rec_student_id in							"
				+ "				       (select student.id							"
				+ "				          from pe_rec_student            student,							"
				+ "				               pr_rec_plan_major_site    plan_site,							"
				+ "				               pr_rec_plan_major_edutype plan_edutype,							"
				+ "				               pe_recruitplan            recruitplan							"
				+ "				         where student.fk_rec_major_site_id = plan_site.id							"
				+ "				           and plan_site.fk_rec_plan_major_edutype_id = plan_edutype.id							"
				+ "				           and plan_edutype.fk_recruitplan_id = recruitplan.id							"
				+ "				           and recruitplan.flag_active = '1')							"
				+ "				           and score is not null										";

		List list = new ArrayList();
		list = this.getGeneralService().getBySQL(sql);
		return list;
	}

	/**
	 * 自动分配考场
	 * 
	 */
	public String autoallotroom() {
		try {
		// 首先判断时间，允许操作时间是在招生考试批次开始之后和入学考试开始之前
		this.setTogo("back");
		DetachedCriteria dc = DetachedCriteria
				.forClass(PeRecruitplan.class);
		dc.add(Restrictions.eq("flagActive", "1"));
		List<PeRecruitplan> peRecruitplan = this.getGeneralService()
				.getList(dc);
		Date start = peRecruitplan.get(0).getStartDate();
		Date end = peRecruitplan.get(0).getExamStartDate();
		Date examEnd = peRecruitplan.get(0).getExamEndDate();
		Date now = new Date();
		if (!start.before(now)) {
			this.setMsg("招生考试批次还未开始，无法分配考场");
			return "msg";
		}
		if (!now.before(examEnd)) {
			this.setMsg("考试已经结束，无法分配考场");
			return "msg";
		}
		if (!now.before(end)) {
			this.setMsg("考试已经开始，无法分配考场");
			return "msg";
		}
		/*
		 * 分配考场不再生成准考证号，因此不用判断免试生审核情况。
		DetachedCriteria dcNoexamRecStudent = DetachedCriteria.forClass(PeRecStudent.class);
		DetachedCriteria dcNoexamRecPlanMajorSite = dcNoexamRecStudent
					.createCriteria("prRecPlanMajorSite", "prRecPlanMajorSite");
		dcNoexamRecPlanMajorSite.createCriteria("prRecPlanMajorEdutype", "prRecPlanMajorEdutype")
						.createCriteria("peRecruitplan", "peRecruitplan")
						.add(Restrictions.eq("flagActive", "1"));
		DetachedCriteria dcNoexamEnumConstByFlagNoexam = dcNoexamRecStudent
					.createCriteria("enumConstByFlagNoexam", "enumConstByFlagNoexam")
					.add(Restrictions.eq("code", "1"));
		DetachedCriteria dcEnumConstByFlagNoexamStatus = dcNoexamRecStudent
					.createCriteria("enumConstByFlagNoexamStatus", "enumConstByFlagNoexamStatus")
					.add(Restrictions.eq("code", "2"));
		List<PeRecStudent> peRecStudentList = this.getGeneralService().getList(dcNoexamRecStudent);
		if (peRecStudentList.size()>0) {
			this.setMsg("还有" + peRecStudentList.size() + "个免试生未审核，请审核完之后再分配考场");
			return "msg";
		}
		*/
		String str = this.getPrRecExamStuCourseService().saveExamRoom();
		this.setMsg(str);
	} catch (Exception e) {
		this.setMsg("操作失败！");
		e.printStackTrace();
	}
	return "msg";
	}

	@Override
	public void initGrid() {
		this.getGridConfig().setTitle(this.getText("考生考试课程明细"));
		this.getGridConfig().setCapability(false, false, false);

		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("学生姓名"),
				"peRecStudent.name");
		this
				.getGridConfig()
				.addColumn(this.getText("招生考试批次"),
						"peRecStudent.prRecPlanMajorSite.prRecPlanMajorEdutype.peRecruitplan.name");
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
		this.getGridConfig().addColumn(this.getText("考试开始时间"),
				"prRecExamCourseTime.startDatetime");
		this.getGridConfig().addColumn(this.getText("考试结束时间"),
				"prRecExamCourseTime.endDatetime");
		this.getGridConfig().addColumn(this.getText("考场名"),"peRecStudent.peRecRoom.name");
		this.getGridConfig().addColumn(this.getText("考场号"),"peRecStudent.peRecRoom.code");
		this.getGridConfig().addColumn(this.getText("准考证号"),
				"peRecStudent.examCardNum");
		this.getGridConfig().addColumn(this.getText("座位号"),
				"peRecStudent.seatNum");
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PrRecExamStuCourse.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/recruit/examstucourse";
	}

	public void setBean(PrRecExamStuCourse instance) {
		super.superSetBean(instance);

	}

	public PrRecExamStuCourse getBean() {
		return (PrRecExamStuCourse) super.superGetBean();
	}

	public DetachedCriteria initDetachedCriteria() {
		JsonUtil.setDateformat("yyyy-MM-dd HH:mm:ss");
		DetachedCriteria dc = DetachedCriteria
				.forClass(PrRecExamStuCourse.class);
		dc.createCriteria("prRecExamCourseTime", "prRecExamCourseTime",DetachedCriteria.LEFT_JOIN)
				.createCriteria("peRecExamcourse", "peRecExamcourse",DetachedCriteria.LEFT_JOIN);
		DetachedCriteria dcprRecPlanMajorEdutype = dc.createCriteria(
				"peRecStudent", "peRecStudent").createAlias("peRecRoom", "peRecRoom").createCriteria(
				"prRecPlanMajorSite", "prRecPlanMajorSite").createAlias(
				"peSite", "peSite").createCriteria("prRecPlanMajorEdutype",
				"prRecPlanMajorEdutype");
		dcprRecPlanMajorEdutype
				.createCriteria("peRecruitplan", "peRecruitplan");
		dcprRecPlanMajorEdutype.createAlias("peMajor", "peMajor").createAlias(
				"peEdutype", "peEdutype");
		return dc;
	}

	public PrRecExamStuCourseService getPrRecExamStuCourseService() {
		return prRecExamStuCourseService;
	}

	public void setPrRecExamStuCourseService(
			PrRecExamStuCourseService prRecExamStuCourseService) {
		this.prRecExamStuCourseService = prRecExamStuCourseService;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}
