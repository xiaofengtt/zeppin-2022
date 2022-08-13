package com.whaty.platform.entity.web.action.recruit.recmanage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeRecExamcourse;
import com.whaty.platform.entity.bean.PeRecStudent;
import com.whaty.platform.entity.bean.PrRecCourseMajorEdutype;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.recruit.recExam.RecruitManageService;
import com.whaty.platform.entity.service.recruit.recmanage.RecruitManageMatriculatService;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.first.PeRecStudentBaseAction;

/**
 * 考试生录取
 * 
 * @author 李冰
 * 
 */
public class RecruitManageExamAction extends PeRecStudentBaseAction {
	private String siteName;
	private String edutypeName;
	private String majorName;
	private List course;
	private PeRecExamcourse peRecExamcourse;// 考试科目信息
	private String courseId;// 考试科目id
	private String score;// 考试科目最低分
	private String totalScore;// 考试科目总分
	private RecruitManageService recruitManageService;
	RecruitManageMatriculatService recruitManageMatriculatService;

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false, true);
		this.getGridConfig().setTitle(this.getText("考试生录取"));
//		this.getGridConfig().addColumn(this.getText("row_num"), "row_num",
//				false, false, false, "");
		this.getGridConfig().addColumn(this.getText("id"), "id", false, false,
				false, "");
		this.getGridConfig().addColumn(this.getText("姓名"), "peRecStudent.name");
		this.getGridConfig().addColumn(this.getText("性别"),
				"peRecStudent.gender");
		this.getGridConfig().addColumn(this.getText("证件类型"),
				"peRecStudent.card_Type");
		this.getGridConfig().addColumn(this.getText("证件号码"),
				"peRecStudent.card_No");
		this.getGridConfig().addColumn(this.getText("准考证号"),
				"peRecStudent.exam_card_num");
		this.getGridConfig().addColumn(this.getText("学生状态"),
				"enumConstByFlagRecStatus.name",false,false,true,"");
		this.getGridConfig().addColumn(this.getText("录取状态"),
				"enumConstByFlagMatriculate.name");
		this.getGridConfig().addColumn(this.getText("学习中心"), "peSite.name");
		this.getGridConfig().addColumn(this.getText("层次"), "peEdutype.name");
		this.getGridConfig().addColumn(this.getText("专业"), "peMajor.name");
		this.getGridConfig().addMenuFunction(
				"录取",
				"enumConstByFlagMatriculate.id",
				this.getMyListService().getEnumConstByNamespaceCode(
						"FlagMatriculate", "1").getId());
		this.getGridConfig().addMenuScript(this.getText("返回"),
				"{window.history.back()}");

	}

	public void checkBeforeUpdateColumn(List idList) throws EntityException {
		if (idList != null && idList.size() > 0) {
			if (!this.checkExamDate()) {
				throw new EntityException("考试生录取操作应在成绩录入开始-录取结束的时间范围内！");
			}
			List<PeRecStudent> list = this.getMyListService().getByIds(
					PeRecStudent.class, idList);
			for (PeRecStudent peRecStudent : list) {
				if (peRecStudent.getEnumConstByFlagMatriculate() != null) {
					String code = peRecStudent.getEnumConstByFlagMatriculate()
							.getCode();
					if (code != null && code.equals("1"))
						throw new EntityException("包含已录取的学生，无法完成操作，请重新选择！");
				}
			}
		} else {
			throw new EntityException("请选择至少一条记录");
		}
	}

	public Map updateColumn() {
		String msg = "";
		Map map = new HashMap();
		if (this.getIds() != null && this.getIds().length() > 0) {
			String[] ids = getIds().split(",");
			List idList = new ArrayList();

			for (int i = 0; i < ids.length; i++) {
				idList.add(ids[i]);
			}

			try {
				checkBeforeUpdateColumn(idList);
			} catch (EntityException e) {
				map.clear();
				map.put("success", "false");
				map.put("info", e.getMessage());
				return map;
			}
			try {
				List<PeRecStudent> peRecStudentList = this.getMyListService()
						.getByIds(PeRecStudent.class, idList);
				msg = this.getRecruitManageMatriculatService().saveMatriculat(
						peRecStudentList, this.getValue());
			} catch (Exception e) {
				e.printStackTrace();
				map.clear();
				map.put("success", "false");
				map.put("info", this.getText("操作失败"));
				return map;
			}
			map.clear();
			map.put("success", "true");
			map.put("info", msg);

		} else {
			map.put("success", "false");
			map.put("info", "parameter value error");
		}
		return map;
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
		if(this.getCourseId()!=null&&this.getCourseId().length()>0){
		try {
			this.getRecruitManageService().saveScoreLine(this.getCourseId(),
					this.getScore());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		}
		// String sql = "select to_char('校本部') as site , '高升本' as edutype ,'计算机'
		// as major , '100' as totalnum , '70' as passnum "
		// + " from dual";
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
	 * 取得可以录取的学生的信息 sql的写法说明： 根据选择的条件作为查询的条件。
	 * 查询出当前招生批次下的考生中有单科成绩小于单科分数线或者为空的学生作为子表score。
	 * 查询出当前招生批次下的考生中总分大于分数线的学生作为子表totalScore。
	 * 查询出当前招生批次下的考生中不在子表score并且在子表totalScore中的学生，就是可以录取的学生。
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
		String str = "";
		if(this.getCourseId()!=null&&this.getCourseId().length()>0){
			str = " prRecExamCourseTime.Score_Line ";
		}else {
			str +=this.getScore();
		}

		String sql = "  select peRecStudent.id                 as id,                                      "
				+ "         peRecStudent.Name               as name,                                    "
				+ "         peRecStudent.Gender             as gender,                                  "
				+ "         peRecStudent.Card_Type          as cardtype,                                "
				+ "         peRecStudent.Card_No            as cardNo,                                  "
				+ "         peRecStudent.exam_card_num      as examCardNo,                                  "
				+ "         enumConstByFlagRecStatus.Name   as enumName,                                "
				+ "         enumConstByFlagMatriculate.Name as matriculate,                             "
				+ "         peSite.Name                     as siteName,                                "
				+ "         peEdutype.Name                  as edutypeName,                             "
				+ "         peMajor.Name                    as majorName                                "
				+ "    from pe_rec_student            peRecStudent                                      "
				+ "         left join enum_const enumConstByFlagMatriculate                             "
				+ "                on enumConstByFlagMatriculate.Id = peRecStudent.Flag_Matriculate,    "
				+ "         pr_rec_plan_major_site    prRecPlanMajorSite,                               "
				+ "         pr_rec_plan_major_edutype prRecPlanMajorEdutype,                            "
				+ "         pe_recruitplan            peRecruitplan,                                    "
				+ "         pe_site                   peSite,                                           "
				+ "         pe_edutype                peEdutype,                                        "
				+ "         pe_major                  peMajor,                                          "
				+ "         enum_const                enumConstByFlagRecStatus                          "
				+ "   where peRecStudent.Fk_Rec_Major_Site_Id = prRecPlanMajorSite.Id                   "
				+ "     and prRecPlanMajorSite.Fk_Rec_Plan_Major_Edutype_Id =                           "
				+ "         prRecPlanMajorEdutype.Id                                                    "
				+ "     and prRecPlanMajorEdutype.Fk_Recruitplan_Id = peRecruitplan.Id                  "
				+ "     and prRecPlanMajorSite.Fk_Site_Id = peSite.Id                                   "
				+ "     and prRecPlanMajorEdutype.Fk_Edutype_Id = peEdutype.Id                          "
				+ "     and prRecPlanMajorEdutype.Fk_Major_Id = peMajor.Id                              "
				+ "     and peRecruitplan.Flag_Active = '1'                                             "
				+ "     and peRecStudent.flag_noexam =                                                  "
				+ "         (select enum.id                                                             "
				+ "            from enum_const enum                                                     "
				+ "           where enum.namespace = 'FlagNoexam'                                       "
				+ "             and enum.code = '0')                                                    "
				+ "     and enumConstByFlagRecStatus.Id = peRecStudent.Flag_Rec_Status                  "
				+ "     and peRecStudent.id in                                                          "
				+ "         (select totalStudent.stuId                                                  "
				+ "            from (select peRecStudent.id stuId                                       "
				+ "                    from pe_rec_student            peRecStudent,                     "
				+ "                         pr_rec_plan_major_site    prRecPlanMajorSite,               "
				+ "                         pr_rec_plan_major_edutype prRecPlanMajorEdutype,            "
				+ "                         pe_site                   peSite,                           "
				+ "                         pe_edutype                peEdutype,                        "
				+ "                         pe_major                  peMajor,                          "
				+ "                         pe_recruitplan            recruitplan                       "
				+ "                   where peRecStudent.fk_rec_major_site_id =                         "
				+ "                         prRecPlanMajorSite.id                                       "
				+ "                     and prRecPlanMajorSite.fk_rec_plan_major_edutype_id =           "
				+ "                         prRecPlanMajorEdutype.id                                    "
				+ "                     and prRecPlanMajorEdutype.fk_recruitplan_id =                   "
				+ "                         recruitplan.id                                              "
				+ "                     and prRecPlanMajorSite.Fk_Site_Id = peSite.Id                   "
				+ "                     and prRecPlanMajorEdutype.Fk_Edutype_Id = peEdutype.Id          "
				+ "                     and prRecPlanMajorEdutype.Fk_Major_Id = peMajor.Id              "
				+ "                     and recruitplan.flag_active = '1'                               "
				+ sql_temp
				+ " 					and peRecStudent.flag_xueli_check in (select enum.id     from enum_const enum "
				 +		"   where enum.namespace = 'FlagXueliCheck'   and (enum.code = '2' or enum.code = '3'))     			"				
				+ "                     and peRecStudent.flag_noexam =                                  "
				+ "                         (select enum.id                                             "
				+ "                            from enum_const enum                                     "
				+ "                           where enum.namespace = 'FlagNoexam'                       "
				+ "                             and enum.code = '0')) totalStudent                      "
				+ "           where totalStudent.stuId not in                                           "
				+ "                 (select peRecStudent.id as stuId                                    "
				+ "                    from pr_rec_exam_stu_course    prRecExamStuCourse,               "
				+ "                         pe_rec_student            peRecStudent,                     "
				+ "                         pr_rec_plan_major_site    prRecPlanMajorSite,               "
				+ "                         pr_rec_plan_major_edutype prRecPlanMajorEdutype,            "
				+ "                         pe_recruitplan            peRecruitplan,                    "
				+ "                         pe_site                   peSite,                           "
				+ "                         pe_edutype                peEdutype,                        "
				+ "                         pe_major                  peMajor,                          "
				+ "                         pr_rec_exam_course_time   prRecExamCourseTime               "
				+ "                   where prRecExamStuCourse.Fk_Rec_Student_Id = peRecStudent.Id      "
				+ "                     and peRecStudent.Fk_Rec_Major_Site_Id =                         "
				+ "                         prRecPlanMajorSite.Id                                       "
				+ "                     and prRecPlanMajorSite.Fk_Rec_Plan_Major_Edutype_Id =           "
				+ "                         prRecPlanMajorEdutype.Id                                    "
				+ "                     and prRecPlanMajorEdutype.Fk_Recruitplan_Id =                   "
				+ "                         peRecruitplan.Id                                            "
				+ "                     and prRecPlanMajorSite.Fk_Site_Id = peSite.Id                   "
				+ "                     and prRecPlanMajorEdutype.Fk_Edutype_Id = peEdutype.Id          "
				+ "                     and prRecPlanMajorEdutype.Fk_Major_Id = peMajor.Id              "
				+ "                     and peRecruitplan.Flag_Active = '1'                             "
				+ "                     and peRecStudent.flag_noexam =                                  "
				+ "                         (select enum.id                                             "
				+ "                            from enum_const enum                                     "
				+ "                           where enum.namespace = 'FlagNoexam'                       "
				+ "                             and enum.code = '0')                                    "
				+ "                     and prRecExamStuCourse.Fk_Rec_Exam_Course_Time_Id =             "
				+ "                         prRecExamCourseTime.Id                                      "
				+ "                     and (prRecExamStuCourse.Score <                                 "
//				+ "                         prRecExamCourseTime.Score_Line or                           "
				+ str + " or "
				+ "                         prRecExamStuCourse.Score is null)                           "
				+ "                   group by prRecExamStuCourse.Fk_Rec_Student_Id,                    "
				+ "                            peRecStudent.Name,                                       "
				+ "                            peSite.Name,                                             "
				+ "                            prRecExamStuCourse.Score,                                "
				+ "                            peRecStudent.id)                                         "
				+ "             and totalStudent.stuId  in                                           "
				+ "                 ((select totalScore.stuId as tostuId                                "
				+ "                     from (select sum(prRecExamStuCourse.Score) as score,            "
				+ "                                  peRecStudent.Id as stuId,                          "
				+ "                                  peEdutype.Name as edutype                          "
				+ "                             from pr_rec_exam_stu_course    prRecExamStuCourse,      "
				+ "                                  pe_rec_student            peRecStudent,            "
				+ "                                  pr_rec_plan_major_site    prRecPlanMajorSite,      "
				+ "                                  pr_rec_plan_major_edutype prRecPlanMajorEdutype,   "
				+ "                                  pe_recruitplan            peRecruitplan,           "
				+ "                                  pe_site                   peSite,                  "
				+ "                                  pe_edutype                peEdutype,               "
				+ "                                  pe_major                  peMajor                  "
				+ "                            where prRecExamStuCourse.Fk_Rec_Student_Id =             "
				+ "                                  peRecStudent.Id                                    "
				+ "                              and peRecStudent.Fk_Rec_Major_Site_Id =                "
				+ "                                  prRecPlanMajorSite.Id                              "
				+ "                              and prRecPlanMajorSite.Fk_Rec_Plan_Major_Edutype_Id =  "
				+ "                                  prRecPlanMajorEdutype.Id                           "
				+ "                              and prRecPlanMajorEdutype.Fk_Recruitplan_Id =          "
				+ "                                  peRecruitplan.Id                                   "
				+ "                              and prRecPlanMajorSite.Fk_Site_Id = peSite.Id          "
				+ "                              and prRecPlanMajorEdutype.Fk_Edutype_Id =              "
				+ "                                  peEdutype.Id                                       "
				+ "                              and prRecPlanMajorEdutype.Fk_Major_Id = peMajor.Id     "
				+ "                              and peRecruitplan.Flag_Active = '1'                    "
				+ "                              and peRecStudent.flag_noexam =                         "
				+ "                                  (select enum.id                                    "
				+ "                                     from enum_const enum                            "
				+ "                                    where enum.namespace = 'FlagNoexam'              "
				+ "                                      and enum.code = '0')                           "
				+ "                            group by prRecExamStuCourse.Fk_Rec_Student_Id,           "
				+ "                                     peRecStudent.Name,                              "
				+ "                                     peSite.Name,                                    "
				+ "                                     peRecStudent.Id,                                "
				+ "                                     peEdutype.Name) totalScore                      "
				+ "                    where (totalScore.score >= "
				+ this.getTotalScore()+ "))))"; //+ " or totalScore.score is null))))		";
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

	@Override
	public void setEntityClass() {
		this.entityClass = PeRecStudent.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/recruit/recruitManageExam";

	}

	public void setBean(PeRecStudent instance) {
		super.superSetBean(instance);

	}

	public PeRecStudent getBean() {
		return (PeRecStudent) super.superGetBean();
	}

	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeRecStudent.class);
		DetachedCriteria dcMajorEdutype = dc.createCriteria(
				"prRecPlanMajorSite", "prRecPlanMajorSite").createAlias(
				"peSite", "peSite").createCriteria("prRecPlanMajorEdutype",
				"prRecPlanMajorEdutype");
		dcMajorEdutype.createAlias("peEdutype", "peEdutype").createAlias(
				"peMajor", "peMajor");
		dcMajorEdutype.createCriteria("peRecruitplan", "peRecruitplan").add(
				Restrictions.eq("flagActive", "1"));
		dc.createCriteria("enumConstByFlagRecStatus",
				"enumConstByFlagRecStatus", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria dcEnumConstByFlagNoexam = dc.createCriteria(
				"enumConstByFlagNoexam", "enumConstByFlagNoexam");
		dc.createCriteria("enumConstByFlagMatriculate",
				"enumConstByFlagMatriculate");
		dcEnumConstByFlagNoexam.add(Restrictions.eq("code", "0"));
		return dc;
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

	public List getCourse() {
		return course;
	}

	public void setCourse(List course) {
		this.course = course;
	}

	public PeRecExamcourse getPeRecExamcourse() {
		return peRecExamcourse;
	}

	public void setPeRecExamcourse(PeRecExamcourse peRecExamcourse) {
		this.peRecExamcourse = peRecExamcourse;
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

	public RecruitManageService getRecruitManageService() {
		return recruitManageService;
	}

	public void setRecruitManageService(
			RecruitManageService recruitManageService) {
		this.recruitManageService = recruitManageService;
	}

	public RecruitManageMatriculatService getRecruitManageMatriculatService() {
		return recruitManageMatriculatService;
	}

	public void setRecruitManageMatriculatService(
			RecruitManageMatriculatService recruitManageMatriculatService) {
		this.recruitManageMatriculatService = recruitManageMatriculatService;
	}
}
