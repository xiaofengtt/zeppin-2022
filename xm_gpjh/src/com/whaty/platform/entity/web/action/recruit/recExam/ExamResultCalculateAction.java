package com.whaty.platform.entity.web.action.recruit.recExam;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeRecExamcourse;
import com.whaty.platform.entity.bean.PrRecCourseMajorEdutype;
import com.whaty.platform.entity.bean.PrRecExamCourseTime;
import com.whaty.platform.entity.bean.PrRecExamStuCourse;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.recruit.recExam.RecruitManageService;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;

/**
 * 入学考试成绩统计录取人数
 * 
 * @author 李冰
 * 
 */
public class ExamResultCalculateAction extends MyBaseAction {

	private String siteName;
	private String edutypeName;
	private String majorName;
	private List course;// 根据层次专业查询出来的课程
	private PeRecExamcourse peRecExamcourse;// 考试科目信息
	private String courseId;// 考试科目id
	private String score;// 考试科目最低分
	private String totalScore;// 考试科目总分
	private RecruitManageService recruitManageService;

	public RecruitManageService getRecruitManageService() {
		return recruitManageService;
	}

	public void setRecruitManageService(
			RecruitManageService recruitManageService) {
		this.recruitManageService = recruitManageService;
	}

	public PeRecExamcourse getPeRecExamcourse() {
		return peRecExamcourse;
	}

	public void setPeRecExamcourse(PeRecExamcourse peRecExamcourse) {
		this.peRecExamcourse = peRecExamcourse;
	}

	public List getCourse() {
		return course;
	}

	public void setCourse(List course) {
		this.course = course;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getEdutypeName() {
		return edutypeName;
	}

	public void setEdutypeName(String edutypeName) {
		this.edutypeName = edutypeName;
	}

	public String getMajorName() {
		return majorName;
	}

	public void setMajorName(String majorName) {
		this.majorName = majorName;
	}

	@Override
	public void initGrid() {
		this.reviseAttribute();
		// 在标题显示做选择的条件
		String title = "入学考试成绩统计";
		if (this.getSiteName() != null && !"".equals(this.getSiteName())) {
			title += "--" + this.getSiteName();
		}
		if (this.getMajorName() != null && !"".equals(this.getMajorName())) {
			title += "--" + this.getEdutypeName();
		}
		if (this.getEdutypeName() != null && !"".equals(this.getEdutypeName())) {
			title += "--" + this.getMajorName();
		}

		this.getGridConfig().setTitle(this.getText(title));
		this.getGridConfig().setCapability(false, false, false, false);
		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("考生总人数"), "totalnum");
		this.getGridConfig().addColumn(this.getText("未通过人数"), "num");
		this.getGridConfig().addColumn(this.getText("通过人数"), "passnum");
		this.getGridConfig().addMenuScript(this.getText("返回"),
				"{window.history.back()}");
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PrRecExamStuCourse.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/recruit/examresultcalculate";
	}

	/**
	 * 转向课程分数和总分数条件设置页面
	 * 
	 * @return
	 */
	public String turnToConditionSet() {

		DetachedCriteria dcPrRecCourseMajorEdutyp = DetachedCriteria
				.forClass(PrRecCourseMajorEdutype.class);
		dcPrRecCourseMajorEdutyp.createCriteria("peMajor", "peMajor");
		dcPrRecCourseMajorEdutyp.createCriteria("peEdutype", "peEdutype");
		dcPrRecCourseMajorEdutyp.createCriteria("peRecExamcourse",
				"peRecExamcourse");

		if (this.getMajorName() != null && !"".equals(this.getMajorName())) {
			dcPrRecCourseMajorEdutyp.add(Restrictions.eq("peMajor.name", this
					.getMajorName()));
		}

		if (this.getEdutypeName() != null && !"".equals(this.getEdutypeName())) {
			dcPrRecCourseMajorEdutyp.add(Restrictions.eq("peEdutype.name", this
					.getEdutypeName()));
		}
		List<PrRecCourseMajorEdutype> list = new ArrayList();
		try {
			list = this.getGeneralService().getList(dcPrRecCourseMajorEdutyp);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		List data = new ArrayList();
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				data.add(list.get(i).getPeRecExamcourse());
			}
		}
		this.setCourse(data);
		return "turnToConditionSet";
	}

	public Page list() {
		this.reviseAttribute();
		try {
			this.getRecruitManageService().saveScoreLine(this.getCourseId(),
					this.getScore());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		String sql = this.getSQL();
		StringBuffer sql_temp = new StringBuffer(sql);
		this.setSqlCondition(sql_temp);
		Page page = null;
		try {
			page = this.getGeneralService().getByPageSQL(sql_temp.toString(),
					Integer.parseInt(this.getLimit()),
					Integer.parseInt(this.getStart()));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return page;
	}

	/**
	 * 统计录取人数的sql sql的写法说明： 根据选择的条件作为查询的条件。
	 * 查询出当前招生批次下的考生中有单科成绩小于单科分数线的学生作为子表score。
	 * 查询出当前招生批次下的考生中总分大于分数线的学生作为子表totalScore。
	 * 查询出当前招生批次下的考生中既不在子表score在子表totalScore中的学生，就是能录取的学生。
	 * 查询出当前招生批次下的考生总人数。总人数 - 能录取的人数 = 不可以录取人数
	 * 
	 * @return
	 */

	private String getSQL() {
		String sql_temp = "";
		String sql_site_temp = "";
		if (this.getSiteName() != null && !"".equals(this.getSiteName())) {
			sql_temp += " and peSite.name = '" + this.getSiteName() + "' ";
			sql_site_temp = "peSite.name";
		}
		if (this.getMajorName() != null && !"".equals(this.getMajorName())) {
			sql_temp += " and peMajor.name = '" + this.getMajorName() + "' ";
		}
		if (this.getEdutypeName() != null && !"".equals(this.getEdutypeName())) {
			sql_temp += " and peEdutype.name = '" + this.getEdutypeName()
					+ "' ";
		}

		String sql = "  select rownum as id, one.* from (  select total.num as totalnum,																															"
				+ "         total.num - tongguo.thenum as num    ,                                       "
				+ "         tongguo.thenum as passnum                                                     "

				+ "    from (select count(peRecStudent.id) as num                                               "
				+ "            from pe_rec_student            peRecStudent,                                     "
				+ "                 pr_rec_plan_major_site    prRecPlanMajorSite,                               "
				+ "                 pr_rec_plan_major_edutype prRecPlanMajorEdutype,                            "
				+ "                 pe_recruitplan            recruitplan,                                      "
				+ "                 pe_site                   peSite,                                           "
				+ "                 pe_edutype                peEdutype,                                        "
				+ "                 pe_major                  peMajor," +
						"			enum_const                 enum                          "
				+ "           where peRecStudent.fk_rec_major_site_id = prRecPlanMajorSite.id                   "
				+ "             and prRecPlanMajorSite.fk_rec_plan_major_edutype_id =                           "
				+ "                 prRecPlanMajorEdutype.id                                                    "
				+ "             and prRecPlanMajorEdutype.fk_recruitplan_id = recruitplan.id                    "
				+ "             and prRecPlanMajorSite.Fk_Site_Id = peSite.Id                                   "
				+ "             and prRecPlanMajorEdutype.Fk_Edutype_Id = peEdutype.Id                          "
				+ "             and prRecPlanMajorEdutype.Fk_Major_Id = peMajor.Id                              "
				+ "             and recruitplan.flag_active = '1'      "
				+ "             and peRecStudent.Flag_Rec_Status = enum.id                                   "
				+ "             and enum.namespace = 'FlagRecStatus'                          "
				+ "             and enum.code = '3'                              "
				+ sql_temp
			       +"                       and peRecStudent.flag_xueli_check in						"	
			       +"                           (select enum.id                             "
			       +"                              from enum_const enum                     "
			       +"                             where enum.namespace = 'FlagXueliCheck'   "
			       +"                               and (enum.code = '2' or                 "
			       +"                                   enum.code = '3'))										"					
				+ "             and peRecStudent.flag_noexam =                                                  "
				+ "                 (select enum.id                                                             "
				+ "                    from enum_const enum                                                     "
				+ "                   where enum.namespace = 'FlagNoexam'                                       "
				+ "                     and enum.code = '0')) total,                                            "
				+ "         (select count(totalStudent.stuId) as thenum                                         "
				+ "            from (select peRecStudent.id stuId                                               "
				+ "                    from pe_rec_student            peRecStudent,                             "
				+ "                         pr_rec_plan_major_site    prRecPlanMajorSite,                       "
				+ "                         pr_rec_plan_major_edutype prRecPlanMajorEdutype,                    "
				+ "                         pe_recruitplan            recruitplan,                              "
				+ "                         pe_site                   peSite,                                   "
				+ "                         pe_edutype                peEdutype,                                "
				+ "                         pe_major                  peMajor                                   "
				+ "                   where peRecStudent.fk_rec_major_site_id =                                 "
				+ "                         prRecPlanMajorSite.id                                               "
				+ "                     and prRecPlanMajorSite.fk_rec_plan_major_edutype_id =                   "
				+ "                         prRecPlanMajorEdutype.id                                            "
				+ "                     and prRecPlanMajorEdutype.fk_recruitplan_id =                           "
				+ "                         recruitplan.id                                                      "
				+ "                     and prRecPlanMajorSite.Fk_Site_Id = peSite.Id                           "
				+ "                     and prRecPlanMajorEdutype.Fk_Edutype_Id = peEdutype.Id                  "
				+ "                     and prRecPlanMajorEdutype.Fk_Major_Id = peMajor.Id                      "
				+ "                     and recruitplan.flag_active = '1'                                       "
				+ sql_temp
			       +"                       and peRecStudent.flag_xueli_check in						"	
			       +"                           (select enum.id                             "
			       +"                              from enum_const enum                     "
			       +"                             where enum.namespace = 'FlagXueliCheck'   "
			       +"                               and (enum.code = '2' or                 "
			       +"                                   enum.code = '3'))										"	
				+ "                     and peRecStudent.flag_noexam =                                          "
				+ "                         (select enum.id                                                     "
				+ "                            from enum_const enum                                             "
				+ "                           where enum.namespace = 'FlagNoexam'                               "
				+ "                             and enum.code = '0')) totalStudent                              "
				+ "           where totalStudent.stuId not in                                                       "
				+ "                 (select peRecStudent.id as stuId                                            "
				+ "                    from pr_rec_exam_stu_course    prRecExamStuCourse,                       "
				+ "                         pe_rec_student            peRecStudent,                             "
				+ "                         pr_rec_plan_major_site    prRecPlanMajorSite,                       "
				+ "                         pr_rec_plan_major_edutype prRecPlanMajorEdutype,                    "
				+ "                         pe_recruitplan            peRecruitplan,                            "
				+ "                         pe_site                   peSite,                                   "
				+ "                         pe_edutype                peEdutype,                                "
				+ "                         pe_major                  peMajor,                                  "
				+ "                         pr_rec_exam_course_time   prRecExamCourseTime                       "
				+ "                   where prRecExamStuCourse.Fk_Rec_Student_Id = peRecStudent.Id              "
				+ "                     and peRecStudent.Fk_Rec_Major_Site_Id =                                 "
				+ "                         prRecPlanMajorSite.Id                                               "
				+ "                     and prRecPlanMajorSite.Fk_Rec_Plan_Major_Edutype_Id =                   "
				+ "                         prRecPlanMajorEdutype.Id                                            "
				+ "                     and prRecPlanMajorEdutype.Fk_Recruitplan_Id =                           "
				+ "                         peRecruitplan.Id                                                    "
				+ "                     and prRecPlanMajorSite.Fk_Site_Id = peSite.Id                           "
				+ "                     and prRecPlanMajorEdutype.Fk_Edutype_Id = peEdutype.Id                  "
				+ "                     and prRecPlanMajorEdutype.Fk_Major_Id = peMajor.Id                      "
				+ "                     and peRecruitplan.Flag_Active = '1'                                     "
				+ sql_temp
				+ "                     and peRecStudent.flag_noexam =                                          "
				+ "                         (select enum.id                                                     "
				+ "                            from enum_const enum                                             "
				+ "                           where enum.namespace = 'FlagNoexam'                               "
				+ "                             and enum.code = '0')                                            "
				+ "                     and prRecExamStuCourse.Fk_Rec_Exam_Course_Time_Id =                     "
				+ "                         prRecExamCourseTime.Id                                              "
				+ "                     and (prRecExamStuCourse.Score <                                         "
				+ "                         prRecExamCourseTime.Score_Line  or                                   "
				+ "                         prRecExamStuCourse.Score is null)                                   "
				+ "                   group by prRecExamStuCourse.Fk_Rec_Student_Id,                            "
				+ "                            peRecStudent.Name,                                               "
				+ "                            peSite.Name,                                                     "
				+ "                            prRecExamStuCourse.Score,                                        "
				+ "                            peRecStudent.id)                                                 "
				+ "              and totalStudent.stuId in                                                       "
				+ "                 ((select totalScore.stuId as tostuId                                        "
				+ "                     from (select sum(prRecExamStuCourse.Score) as score,                    "
				+ "                                  peRecStudent.Id as stuId,                                  "
				+ "                                  peEdutype.Name as edutype                                  "
				+ "                             from pr_rec_exam_stu_course    prRecExamStuCourse,              "
				+ "                                  pe_rec_student            peRecStudent,                    "
				+ "                                  pr_rec_plan_major_site    prRecPlanMajorSite,              "
				+ "                                  pr_rec_plan_major_edutype prRecPlanMajorEdutype,           "
				+ "                                  pe_recruitplan            peRecruitplan,                   "
				+ "                                  pe_site                   peSite,                          "
				+ "                                  pe_edutype                peEdutype,                       "
				+ "                                  pe_major                  peMajor                          "
				+ "                            where prRecExamStuCourse.Fk_Rec_Student_Id =                     "
				+ "                                  peRecStudent.Id                                            "
				+ "                              and peRecStudent.Fk_Rec_Major_Site_Id =                        "
				+ "                                  prRecPlanMajorSite.Id                                      "
				+ "                              and prRecPlanMajorSite.Fk_Rec_Plan_Major_Edutype_Id =          "
				+ "                                  prRecPlanMajorEdutype.Id                                   "
				+ "                              and prRecPlanMajorEdutype.Fk_Recruitplan_Id =                  "
				+ "                                  peRecruitplan.Id                                           "
				+ "                              and prRecPlanMajorSite.Fk_Site_Id = peSite.Id                  "
				+ "                              and prRecPlanMajorEdutype.Fk_Edutype_Id =                      "
				+ "                                  peEdutype.Id                                               "
				+ "                              and prRecPlanMajorEdutype.Fk_Major_Id = peMajor.Id             "
				+ "                              and peRecruitplan.Flag_Active = '1'                            "
				+ sql_temp
				+ "                              and peRecStudent.flag_noexam =                                 "
				+ "                                  (select enum.id                                            "
				+ "                                     from enum_const enum                                    "
				+ "                                    where enum.namespace = 'FlagNoexam'                      "
				+ "                                      and enum.code = '0')                                   "
				+ "                            group by prRecExamStuCourse.Fk_Rec_Student_Id,                   "
				+ "                                     peRecStudent.Name,                                      "
				+ "                                     peSite.Name,                                            "
				+ "                                     peRecStudent.Id,                                        "
				+ "                                     peEdutype.Name) totalScore                              "
				+ "                    where (totalScore.score >= "
				+ this.getTotalScore() + ")))) tongguo )one";
				//+ " or totalScore.score is null)))) weitongguo ";
		return sql;
	}

	/**
	 * 去掉所传入参数的空格
	 */
	private void reviseAttribute() {
		if (this.getSiteName() != null && !"".equals(this.getSiteName())) {
			this.setSiteName(this.getSiteName().trim());
		}
		if (this.getMajorName() != null && !"".equals(this.getMajorName())) {
			this.setMajorName(this.getMajorName().trim());
		}
		if (this.getEdutypeName() != null && !"".equals(this.getEdutypeName())) {
			this.setEdutypeName(this.getEdutypeName().trim());
		}
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(String totalScore) {
		this.totalScore = totalScore;
	}
}
