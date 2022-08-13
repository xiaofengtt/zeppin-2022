package com.whaty.platform.entity.web.action.workspaceTeacher;

import java.util.List;

import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;

/**
 * 教师交流园地中统计学生成绩
 * @author 李冰
 *
 */
public class StuScoreStatAction extends MyBaseAction {

	private String courseId;
	private List list;
	

	public String stuScore(){
		StringBuffer sql_temp = new StringBuffer();
		sql_temp.append("  select semester.Name as semester_name,          																			");				
//		sql_temp.append("         course.name as course_name,                                                   ");
		sql_temp.append("         count(*) as total,                                                            ");
		sql_temp.append("         SUM(CASE                                                                      ");
		sql_temp.append("               WHEN nvl(elective.score_total, 0) < 60 THEN                             ");
		sql_temp.append("                1                                                                      ");
		sql_temp.append("               ELSE                                                                    ");
		sql_temp.append("                0                                                                      ");
		sql_temp.append("             END) AS lt60,                                                             ");
		sql_temp.append("         SUM(CASE                                                                      ");
		sql_temp.append("               WHEN (elective.score_total >= 60 AND elective.score_total < 70) THEN    ");
		sql_temp.append("                1                                                                      ");
		sql_temp.append("               ELSE                                                                    ");
		sql_temp.append("                0                                                                      ");
		sql_temp.append("             END) AS ge60lt70,                                                         ");
		sql_temp.append("         SUM(CASE                                                                      ");
		sql_temp.append("               WHEN (elective.score_total >= 70 AND elective.score_total < 80) THEN    ");
		sql_temp.append("                1                                                                      ");
		sql_temp.append("               ELSE                                                                    ");
		sql_temp.append("                0                                                                      ");
		sql_temp.append("             END) AS ge70lt80,                                                         ");
		sql_temp.append("         SUM(CASE                                                                      ");
		sql_temp.append("               WHEN (elective.score_total >= 80 AND elective.score_total < 90) THEN    ");
		sql_temp.append("                1                                                                      ");
		sql_temp.append("               ELSE                                                                    ");
		sql_temp.append("                0                                                                      ");
		sql_temp.append("             END) AS ge80lt90,                                                         ");
		sql_temp.append("         SUM(CASE                                                                      ");
		sql_temp.append("               WHEN elective.score_total >= 90 THEN                                    ");
		sql_temp.append("                1                                                                      ");
		sql_temp.append("               ELSE                                                                    ");
		sql_temp.append("                0                                                                      ");
		sql_temp.append("             END) AS ge90,                                                             ");
		sql_temp.append("         round(SUM(CASE                                                                ");
		sql_temp.append("                     WHEN elective.score_total >= 60 THEN                              ");
		sql_temp.append("                      1                                                                ");
		sql_temp.append("                     ELSE                                                              ");
		sql_temp.append("                      0                                                                ");
		sql_temp.append("                   END) / count(*) * 100,                                              ");
		sql_temp.append("               2) as pass,                                                             ");
		sql_temp.append("         round(SUM(CASE                                                                ");
		sql_temp.append("                     WHEN elective.score_total >= 80 THEN                              ");
		sql_temp.append("                      1                                                                ");
		sql_temp.append("                     ELSE                                                              ");
		sql_temp.append("                      0                                                                ");
		sql_temp.append("                   END) / count(*) * 100,                                              ");
		sql_temp.append("               2) as good,                                                             ");
		sql_temp.append("         round(SUM(CASE                                                                ");
		sql_temp.append("                     WHEN elective.score_total >= 90 THEN                              ");
		sql_temp.append("                      1                                                                ");
		sql_temp.append("                     ELSE                                                              ");
		sql_temp.append("                      0                                                                ");
		sql_temp.append("                   END) / count(*) * 100,                                              ");
		sql_temp.append("               2) as excellent,                                                        ");
		sql_temp.append("         semester.serial_number                                                        ");
		sql_temp.append("    from pr_tch_stu_elective elective,                                                 ");
		sql_temp.append("         pe_student          student,                                                  ");
		sql_temp.append("         pr_tch_opencourse   opencourse,                                               ");
		sql_temp.append("         pe_tch_course       course,                                                   ");
		sql_temp.append("         pe_semester         semester,                                                 ");
		sql_temp.append("         enum_const          enumConst                                                 ");
		sql_temp.append("   where elective.fk_tch_opencourse_id = opencourse.id                                 ");
		sql_temp.append("     and enumConst.Code = '1'                                                          ");
		sql_temp.append("     and elective.flag_elective_admission = enumConst.Id                               ");
		sql_temp.append("     and opencourse.fk_course_id = course.id                                           ");
		sql_temp.append("     and elective.fk_stu_id = student.id                                               ");
		sql_temp.append("     and opencourse.fk_semester_id = semester.id      and course.id='" + this.getCourseId() + "'                                 ");
		sql_temp.append("   group by semester.Name, semester.serial_number                         ");
		sql_temp.append("   order by semester.serial_number desc												");	
		try {
			List list = this.getGeneralService().getBySQL(sql_temp.toString());
			this.setList(list);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "scoreStat";
	}
	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	
	public List getList() {
		return list;
	}
	public void setList(List list) {
		this.list = list;
	}
	
	@Override
	public void initGrid() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/workspaceTeacher/stuScoreStat";
	}

}
