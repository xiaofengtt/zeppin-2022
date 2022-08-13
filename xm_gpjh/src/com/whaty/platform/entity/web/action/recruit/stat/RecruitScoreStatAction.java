package com.whaty.platform.entity.web.action.recruit.stat;

import com.whaty.platform.entity.bean.PeRecStudent;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class RecruitScoreStatAction extends MyBaseAction {
	private String checkBox;
	private String peRecruitplanId;

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().setTitle(this.getText("入学测试成绩统计"));
//		this.getGridConfig().addColumn(this.getText("id"), "id", false);
//		this.getGridConfig().addColumn(this.getText("row_num"),"row_num",false,false,false,"");
		this.getGridConfig().addColumn(this.getText("id"),"id",false,false,false,"");
		this.getGridConfig().addColumn(this.getText("招生批次"),"combobox_peRecruitplan.peRecruitplan_name");
		this.getGridConfig().addColumn(this.getText("考试课程名称"),"combobox_peRecExamcourse.peRecExamcourse_name");
		if (this.getCheckBox() != null && !"".equals(this.getCheckBox())) {
			if (this.getCheckBox().contains("site")) {
				this.getGridConfig().addColumn(this.getText("学习中心"),"combobox_peSite.peSite_name");
			}
			if (this.getCheckBox().contains("edutype")) {
				this.getGridConfig().addColumn(this.getText("层次"),"combobox_peEdutype.peEdutype_name");
			}
			if (this.getCheckBox().contains("major")) {
				this.getGridConfig().addColumn(this.getText("专业"),"combobox_peMajor.peMajor_name");
			}
			if (this.getCheckBox().contains("piece")) {
				this.getGridConfig().addColumn(this.getText("小于60分"), "lt60", false);
				this.getGridConfig().addColumn(this.getText("大于等于60小于70"), "ge60lt70", false);
				this.getGridConfig().addColumn(this.getText("大于等于70小于80"), "ge70lt80", false);
				this.getGridConfig().addColumn(this.getText("大于等于80小于90"), "ge80lt90", false);
				this.getGridConfig().addColumn(this.getText("大于等于90"), "ge90", false);
			}
			if (this.getCheckBox().contains("ratio")) {
				this.getGridConfig().addColumn(this.getText("及格率"), "pass", false);
				this.getGridConfig().addColumn(this.getText("良好率"), "good", false);
				this.getGridConfig().addColumn(this.getText("优秀率"), "excellent", false);
			}
		}
		this.getGridConfig().addColumn(this.getText("总人数"), "total", false);
		this.getGridConfig().addMenuScript(this.getText("返回"),"{window.history.back()}");
	}
	public Page list() {
		// 根据所选择的统计条件动态拼接查询语句
		String displayItems = "";// 显示的列
		String temp_where = "";// where条件
		String temp_groupBy = "";// group by 条件
		if (this.getCheckBox() != null && !"".equals(this.getCheckBox())) {
			if (this.getCheckBox().contains("site")) {
				displayItems += " peSite.Name as peSite_name,  ";
//				temp_where += " and prRecPlanMajorSite.Fk_Site_Id = peSite.Id ";
				temp_groupBy += " peSite.Name , ";
			}
			if (this.getCheckBox().contains("edutype")) {
				displayItems += "  peEdutype.Name as peEdutype_name, ";
//				temp_where += "   and prRecPlanMajorEdutype.Fk_Edutype_Id = peEdutype.Id  ";
				temp_groupBy += " peEdutype.name ,";
			}
			if (this.getCheckBox().contains("major")) {
				displayItems += "  peMajor.Name as peMajor_name,  ";
				temp_groupBy += " peMajor.name , ";
			}
			if (this.getCheckBox().contains("piece")) {

				StringBuffer sql_displayItems = new StringBuffer();
				sql_displayItems.append("          SUM(CASE                                                           " );
				sql_displayItems.append("                WHEN nvl(prRecExamstuCourse.Score, 0) < 60 THEN              " );
				sql_displayItems.append("                 1                                                           " );
				sql_displayItems.append("                ELSE                                                         " );
				sql_displayItems.append("                 0                                                           " );
				sql_displayItems.append("              END) AS lt60,                                                  " );
				sql_displayItems.append("          SUM(CASE                                                           " );
				sql_displayItems.append("                WHEN (prRecExamstuCourse.Score >= 60 AND                     " );
				sql_displayItems.append("                     prRecExamstuCourse.Score < 70) THEN                     " );
				sql_displayItems.append("                 1                                                           " );
				sql_displayItems.append("                ELSE                                                         " );
				sql_displayItems.append("                 0                                                           " );
				sql_displayItems.append("              END) AS ge60lt70,                                              " );
				sql_displayItems.append("          SUM(CASE                                                           " );
				sql_displayItems.append("                WHEN (prRecExamstuCourse.Score >= 70 AND                     " );
				sql_displayItems.append("                     prRecExamstuCourse.Score < 80) THEN                     " );
				sql_displayItems.append("                 1                                                           " );
				sql_displayItems.append("                ELSE                                                         " );
				sql_displayItems.append("                 0                                                           " );
				sql_displayItems.append("              END) AS ge70lt80,                                              " );
				sql_displayItems.append("          SUM(CASE                                                           " );
				sql_displayItems.append("                WHEN (prRecExamstuCourse.Score >= 80 AND                     " );
				sql_displayItems.append("                     prRecExamstuCourse.Score < 90) THEN                     " );
				sql_displayItems.append("                 1                                                           " );
				sql_displayItems.append("                ELSE                                                         " );
				sql_displayItems.append("                 0                                                           " );
				sql_displayItems.append("              END) AS ge80lt90,                                              " );
				sql_displayItems.append("          SUM(CASE                                                           " );
				sql_displayItems.append("                WHEN prRecExamstuCourse.Score >= 90 THEN                     " );
				sql_displayItems.append("                 1                                                           " );
				sql_displayItems.append("                ELSE                                                         " );
				sql_displayItems.append("                 0                                                           " );
				sql_displayItems.append("              END) AS ge90,                                                  " );
				displayItems += sql_displayItems.toString();
			}
			if (this.getCheckBox().contains("ratio")) {
				StringBuffer sql_displayItems = new StringBuffer();
				sql_displayItems.append("           round ( SUM(CASE                                                           " );
				sql_displayItems.append("                WHEN prRecExamstuCourse.Score >= 60 THEN                     " );
				sql_displayItems.append("                 1                                                           " );
				sql_displayItems.append("                ELSE                                                         " );
				sql_displayItems.append("                 0                                                           " );
				sql_displayItems.append("              END) / count(*)*100,2)||'%' as pass,                                       " );
				sql_displayItems.append("         round ( SUM(CASE                                                           " );
				sql_displayItems.append("                WHEN prRecExamstuCourse.Score >= 80 THEN                     " );
				sql_displayItems.append("                 1                                                           " );
				sql_displayItems.append("                ELSE                                                         " );
				sql_displayItems.append("                 0                                                           " );
				sql_displayItems.append("              END) / count(*)*100,2)||'%' as good,                                       " );
				sql_displayItems.append("           round (  SUM(CASE                                                           " );
				sql_displayItems.append("                WHEN prRecExamstuCourse.Score >= 90 THEN                     " );
				sql_displayItems.append("                 1                                                           " );
				sql_displayItems.append("                ELSE                                                         " );
				sql_displayItems.append("                 0                                                           " );
				sql_displayItems.append("              END) / count(*)*100,2)||'%' as excellent,                                   " );
				displayItems += sql_displayItems.toString();
			}
		}
		if(!this.getPeRecruitplanId().equals("全部招生考试批次")){
			temp_where +=" and  peRecruitplan.Name = '" + this.getPeRecruitplanId() + "' ";
		}
		
		StringBuffer sql_temp = new StringBuffer();
		sql_temp.append(" select rownum as id,one.*  from(  select  peRecruitplan.Name as peRecruitplan_name,                    			" );
		sql_temp.append("          peRecExamcourse.name as peRecExamcourse_name,                      " );
//		sql_temp.append("          peSite.Name as peSite_name,                                        " );
//		sql_temp.append("          peEdutype.Name as peEdutype_name,                                  " );
//		sql_temp.append("          peMajor.Name as peMajor_name,                                      " );
//		sql_temp.append("          SUM(CASE                                                           " );
//		sql_temp.append("                WHEN nvl(prRecExamstuCourse.Score, 0) < 60 THEN              " );
//		sql_temp.append("                 1                                                           " );
//		sql_temp.append("                ELSE                                                         " );
//		sql_temp.append("                 0                                                           " );
//		sql_temp.append("              END) AS lt60,                                                  " );
//		sql_temp.append("          SUM(CASE                                                           " );
//		sql_temp.append("                WHEN (prRecExamstuCourse.Score >= 60 AND                     " );
//		sql_temp.append("                     prRecExamstuCourse.Score < 70) THEN                     " );
//		sql_temp.append("                 1                                                           " );
//		sql_temp.append("                ELSE                                                         " );
//		sql_temp.append("                 0                                                           " );
//		sql_temp.append("              END) AS ge60lt70,                                              " );
//		sql_temp.append("          SUM(CASE                                                           " );
//		sql_temp.append("                WHEN (prRecExamstuCourse.Score >= 70 AND                     " );
//		sql_temp.append("                     prRecExamstuCourse.Score < 80) THEN                     " );
//		sql_temp.append("                 1                                                           " );
//		sql_temp.append("                ELSE                                                         " );
//		sql_temp.append("                 0                                                           " );
//		sql_temp.append("              END) AS ge70lt80,                                              " );
//		sql_temp.append("          SUM(CASE                                                           " );
//		sql_temp.append("                WHEN (prRecExamstuCourse.Score >= 80 AND                     " );
//		sql_temp.append("                     prRecExamstuCourse.Score < 90) THEN                     " );
//		sql_temp.append("                 1                                                           " );
//		sql_temp.append("                ELSE                                                         " );
//		sql_temp.append("                 0                                                           " );
//		sql_temp.append("              END) AS ge80lt90,                                              " );
//		sql_temp.append("          SUM(CASE                                                           " );
//		sql_temp.append("                WHEN prRecExamstuCourse.Score >= 90 THEN                     " );
//		sql_temp.append("                 1                                                           " );
//		sql_temp.append("                ELSE                                                         " );
//		sql_temp.append("                 0                                                           " );
//		sql_temp.append("              END) AS ge90,                                                  " );
//		sql_temp.append("          SUM(CASE                                                           " );
//		sql_temp.append("                WHEN prRecExamstuCourse.Score >= 60 THEN                     " );
//		sql_temp.append("                 1                                                           " );
//		sql_temp.append("                ELSE                                                         " );
//		sql_temp.append("                 0                                                           " );
//		sql_temp.append("              END) / count(*) as pass,                                       " );
//		sql_temp.append("          SUM(CASE                                                           " );
//		sql_temp.append("                WHEN prRecExamstuCourse.Score >= 80 THEN                     " );
//		sql_temp.append("                 1                                                           " );
//		sql_temp.append("                ELSE                                                         " );
//		sql_temp.append("                 0                                                           " );
//		sql_temp.append("              END) / count(*) as good,                                       " );
//		sql_temp.append("          SUM(CASE                                                           " );
//		sql_temp.append("                WHEN prRecExamstuCourse.Score >= 90 THEN                     " );
//		sql_temp.append("                 1                                                           " );
//		sql_temp.append("                ELSE                                                         " );
//		sql_temp.append("                 0                                                           " );
//		sql_temp.append("              END) / count(*) as excellent,                                   " );
		sql_temp.append(displayItems);
		sql_temp.append("          count(*) as total                                                 " );
		sql_temp.append("                                                                             " );
		sql_temp.append("     from pe_rec_student            peRecStudent,                            " );
		sql_temp.append("          pr_rec_plan_major_site    prRecPlanMajorSite,                      " );
		sql_temp.append("          pr_rec_plan_major_edutype prRecPlanMajorEdutype,                   " );
		sql_temp.append("          pe_recruitplan            peRecruitplan,                           " );
		sql_temp.append("          pe_site                   peSite,                                  " );
		sql_temp.append("          pe_edutype                peEdutype,                               " );
		sql_temp.append("          pe_major                  peMajor,                                 " );
		sql_temp.append("          pr_rec_exam_stu_course    prRecExamstuCourse,                      " );
		sql_temp.append("          pe_rec_examcourse         peRecExamcourse,                         " );
		sql_temp.append("          pr_rec_exam_course_time   prRecExamCoursetime                      " );
		sql_temp.append("    where peRecStudent.Fk_Rec_Major_Site_Id = prRecPlanMajorSite.Id          " );
		sql_temp.append("      and prRecPlanMajorSite.Fk_Rec_Plan_Major_Edutype_Id =                  " );
		sql_temp.append("          prRecPlanMajorEdutype.Id                                           " );
		sql_temp.append("      and prRecPlanMajorEdutype.Fk_Recruitplan_Id = peRecruitplan.Id         " );
		sql_temp.append("      and prRecPlanMajorSite.Fk_Site_Id = peSite.Id                          " );
		sql_temp.append("      and prRecPlanMajorEdutype.Fk_Edutype_Id = peEdutype.Id                 " );
		sql_temp.append("      and prRecPlanMajorEdutype.Fk_Major_Id = peMajor.Id                     " );
		sql_temp.append("      and prRecExamstuCourse.Fk_Rec_Exam_Course_Time_Id =                    " );
		sql_temp.append("          prRecExamCoursetime.id                                             " );
		sql_temp.append("      and prRecExamCoursetime.Fk_Rec_Examcourse_Id = peRecExamcourse.id      " );
		sql_temp.append("      and prRecExamstuCourse.Fk_Rec_Student_Id = peRecStudent.Id             " );
		sql_temp.append(temp_where);
		sql_temp.append("    group by peRecruitplan.id,peRecruitplan.Name,                                             " );
		sql_temp.append(temp_groupBy);
//		sql_temp.append("             peSite.Name,                                                    " );
//		sql_temp.append("             peEdutype.Name,                                                 " );
//		sql_temp.append("           peMajor.Name,                                                     " );
		sql_temp.append("             peRecExamcourse.name	) one																					"	);	
		this.setSqlCondition(sql_temp);
		Page page = null;
		try {
			page = getGeneralService().getByPageSQL(sql_temp.toString(),
					Integer.parseInt(this.getLimit()),
					Integer.parseInt(this.getStart()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}
	@Override
	public void setEntityClass() {
		this.entityClass = PeRecStudent.class;

	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/recruit/recruitScoreStat";

	}
	public String getCheckBox() {
		return checkBox;
	}

	public void setCheckBox(String checkBox) {
		this.checkBox = checkBox;
	}
	public String getPeRecruitplanId() {
		return peRecruitplanId;
	}

	public void setPeRecruitplanId(String peRecruitplanId) {
		this.peRecruitplanId = peRecruitplanId;
	}

}
