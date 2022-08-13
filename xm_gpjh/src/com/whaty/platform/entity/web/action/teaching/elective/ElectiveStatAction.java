package com.whaty.platform.entity.web.action.teaching.elective;

import com.whaty.platform.entity.bean.PrTchStuElective;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class ElectiveStatAction extends MyBaseAction<PrTchStuElective> {

	@Override
	public void initGrid() {

		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().setTitle(this.getText("选课人数统计"));
		
//		this.getGridConfig().addColumn(this.getText("s"), "rownum", false,
//				false, false, "");
		
		this.getGridConfig().addColumn(this.getText("id"), "id",false,false,false,"");
		this.getGridConfig().addColumn(this.getText("课程名称"), "combobox_peTchCourse.courseName");
		if(this.getMsg()!=null&&this.getMsg().equals("peSite")){
			this.getGridConfig().addColumn(this.getText("学习中心"), "combobox_peSite.sitename");
		}
		this.getGridConfig().addColumn(this.getText("选课总人数"), "total",false);
		this.getGridConfig().addColumn(this.getText("公选课人数"), "publicNum",false);
		this.getGridConfig().addColumn(this.getText("非公选课人数"), "other",false);
		if(this.getMsg()!=null&&this.getMsg().equals("peSite")){
			if(this.getGridConfig().checkBeforeAddMenu("/entity/teaching/electiveStat.action"))
				this.getGridConfig().addMenuScript("统计总体选课人数", "{window.location='/entity/teaching/electiveStat.action?'}");
		}else {
			if(this.getGridConfig().checkBeforeAddMenu("/entity/teaching/electiveStat.action"))
				this.getGridConfig().addMenuScript("统计学习中心选课人数", "{window.location='/entity/teaching/electiveStat.action?msg=peSite'}");
		}
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PrTchStuElective.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/teaching/electiveStat";
	}

	@Override
	public Page list() {
		StringBuffer sql_temp = new StringBuffer();
		if(this.getMsg()!=null&&this.getMsg().equals("peSite")){
			sql_temp.append(  "      select rownum as id,                 																																	");			
			sql_temp.append(  "             one.courseName,                                                                                 ");
			sql_temp.append(  "             one.sitename,                                                                                   ");
			sql_temp.append(  "             one.total,                                                                                      ");
			sql_temp.append(  "             one.publicNum,                                                                                  ");
			sql_temp.append(  "             one.other                                                                                       ");
			sql_temp.append(  "        from (select course1.courseName as courseName,                                                       ");
			sql_temp.append(  "                     course1.sitename,                                                                       ");
			sql_temp.append(  "                     course1.stuCount as total,                                                              ");
			sql_temp.append(  "                     course2.stuCount as publicNum,                                                          ");
			sql_temp.append(  "                     course1.stuCount - course2.stuCount as other                                            ");
			sql_temp.append(  "                from (select peTchCourse.id as id,                                                           ");
			sql_temp.append(  "                             peTchCourse.Name as courseName,                                                 ");
			sql_temp.append(  "                             o.sitename as sitename,                                                         ");
			sql_temp.append(  "                             nvl(i.electiveCount, 0) as stuCount                                             ");
			sql_temp.append(  "                        from (select o.id,                                                                   ");
			sql_temp.append(  "                                     o.fk_course_id,                                                         ");
			sql_temp.append(  "                                     o.fk_semester_id,                                                       ");
			sql_temp.append(  "                                     o.advice_exam_no,                                                       ");
			sql_temp.append(  "                                     o.course_time,                                                          ");
			sql_temp.append(  "                                     o.flag_exam_course,                                                     ");
			sql_temp.append(  "                                     o.flag_bak,                                                             ");
			sql_temp.append(  "                                     site.name as sitename                                                   ");
			sql_temp.append(  "                                from pe_site site, pr_tch_opencourse o) o                                    ");
			sql_temp.append(  "                       inner join pe_tch_course peTchCourse on o.fk_course_id =                              ");
			sql_temp.append(  "                                                               peTchCourse.id                                ");
			sql_temp.append(  "                       inner join pe_semester s on o.fk_semester_id = s.id                                   ");
			sql_temp.append(  "                        left join (select o.id,                                                              ");
			sql_temp.append(  "                                         site.name as sitename,                                              ");
			sql_temp.append(  "                                         count(*) electiveCount                                              ");
			sql_temp.append(  "                                    from pe_student          student,                                        ");
			sql_temp.append(  "                                         pe_site             site,                                           ");
			sql_temp.append(  "                                         pr_tch_stu_elective e                                               ");
			sql_temp.append(  "                                   inner join pr_tch_opencourse o on e.fk_tch_opencourse_id = o.id           ");
			sql_temp.append(  "                                   inner join pe_tch_course c on o.fk_course_id = c.id                       ");
			sql_temp.append(  "                                   inner join pe_semester s on o.fk_semester_id = s.id                       ");
			sql_temp.append(  "                                   where s.flag_next_active = '1'                                            ");
			sql_temp.append(  "                                     and e.fk_stu_id = student.id                                            ");
			sql_temp.append(  "                                     and student.fk_site_id = site.id                                        ");
			sql_temp.append(  "                                   group by o.id, site.name) i on (i.id = o.id and                           ");
			sql_temp.append(  "                                                                  i.sitename =                               ");
			sql_temp.append(  "                                                                  o.sitename)                                ");
			sql_temp.append(  "                       where s.flag_next_active = '1'                                                        ");
			sql_temp.append(  "                       group by peTchCourse.id,                                                              ");
			sql_temp.append(  "                                i.electiveCount,                                                             ");
			sql_temp.append(  "                                peTchCourse.Name,                                                            ");
			sql_temp.append(  "                                o.sitename) course1,                                                         ");
			sql_temp.append(  "                     (select peTchCourse.id as id,                                                           ");
			sql_temp.append(  "                             peTchCourse.Name as courseName,                                                 ");
			sql_temp.append(  "                             o.sitename as sitename,                                                         ");
			sql_temp.append(  "                             nvl(i.electiveCount, 0) as stuCount                                             ");
			sql_temp.append(  "                        from (select o.id,                                                                   ");
			sql_temp.append(  "                                     o.fk_course_id,                                                         ");
			sql_temp.append(  "                                     o.fk_semester_id,                                                       ");
			sql_temp.append(  "                                     o.advice_exam_no,                                                       ");
			sql_temp.append(  "                                     o.course_time,                                                          ");
			sql_temp.append(  "                                     o.flag_exam_course,                                                     ");
			sql_temp.append(  "                                     o.flag_bak,                                                             ");
			sql_temp.append(  "                                     site.name as sitename                                                   ");
			sql_temp.append(  "                                from pe_site site, pr_tch_opencourse o) o                                    ");
			sql_temp.append(  "                       inner join pe_tch_course peTchCourse on o.fk_course_id =                              ");
			sql_temp.append(  "                                                               peTchCourse.id                                ");
			sql_temp.append(  "                       inner join pe_semester s on o.fk_semester_id = s.id                                   ");
			sql_temp.append(  "                        left join (select o.id,                                                              ");
			sql_temp.append(  "                                         site.name as sitename,                                              ");
			sql_temp.append(  "                                         count(*) electiveCount                                              ");
			sql_temp.append(  "                                    from pe_student            student,                                      ");
			sql_temp.append(  "                                         pe_site               site,                                         ");
			sql_temp.append(  "                                         PR_TCH_PROGRAM_COURSE programCourse,                                ");
			sql_temp.append(  "                                         PE_TCH_PROGRAM_GROUP  programGroup,                                 ");
			sql_temp.append(  "                                         pr_tch_stu_elective   e                                             ");
			sql_temp.append(  "                                   inner join pr_tch_opencourse o on e.fk_tch_opencourse_id = o.id           ");
			sql_temp.append(  "                                   inner join pe_tch_course c on o.fk_course_id = c.id                       ");
			sql_temp.append(  "                                   inner join pe_semester s on o.fk_semester_id = s.id                       ");
			sql_temp.append(  "                                   where s.flag_next_active = '1'                                            ");
			sql_temp.append(  "                                     and e.fk_tch_program_course = programCourse.Id                          ");
			sql_temp.append(  "                                     and programCourse.Fk_Programgroup_Id =                                  ");
			sql_temp.append(  "                                         programGroup.Id                                                     ");
			sql_temp.append(  "                                     and e.fk_stu_id = student.id                                            ");
			sql_temp.append(  "                                     and student.fk_site_id = site.id                                        ");
			sql_temp.append(  "                                     and programGroup.Fk_Coursegroup_Id = '_4'                               ");
			sql_temp.append(  "                                   group by o.id, site.name) i on (i.id = o.id and                           ");
			sql_temp.append(  "                                                                  i.sitename =                               ");
			sql_temp.append(  "                                                                  o.sitename)                                ");
			sql_temp.append(  "                       where s.flag_next_active = '1'                                                        ");
			sql_temp.append(  "                       group by peTchCourse.id,                                                              ");
			sql_temp.append(  "                                i.electiveCount,                                                             ");
			sql_temp.append(  "                                peTchCourse.Name,                                                            ");
			sql_temp.append(  "                                o.sitename) course2,                                                         ");
			sql_temp.append(  "                     pe_site site                                                                            ");
			sql_temp.append(  "               where course1.id = course2.id                                                                 ");
			sql_temp.append(  "                 and course1.sitename = course2.sitename                                                     ");
			sql_temp.append(  "               group by course1.courseName,                                                                  ");
			sql_temp.append(  "                        course1.sitename,                                                                    ");
			sql_temp.append(  "                        course1.stuCount,                                                                    ");
			sql_temp.append(  "                        course2.stuCount) one																																");	
		}else {
			sql_temp.append("        select course1.id as id,   													");				
			sql_temp.append("               course1.courseName as courseName,                                             ");
			sql_temp.append("               course1.stuCount as total,                                                    ");
			sql_temp.append("               course2.stuCount as publicNum,                                                ");
			sql_temp.append("               (course1.stuCount - course2.stuCount) as other                                ");
			sql_temp.append("          from (select peTchCourse.id as id,                                                 ");
			sql_temp.append("                       peTchCourse.Name as courseName,                                       ");
			sql_temp.append("                       nvl(i.electiveCount, 0) as stuCount                                   ");
			sql_temp.append("                  from pr_tch_opencourse o                                                   ");
			sql_temp.append("                 inner join pe_tch_course peTchCourse on o.fk_course_id =                    ");
			sql_temp.append("                                                         peTchCourse.id                      ");
			sql_temp.append("                 inner join pe_semester s on o.fk_semester_id = s.id                         ");
			sql_temp.append("                  left join (select o.id, count(*) electiveCount                               ");
			sql_temp.append("                              from pr_tch_stu_elective e                                       ");
			sql_temp.append("                             inner join pr_tch_opencourse o on e.fk_tch_opencourse_id = o.id   ");
			sql_temp.append("                             inner join pe_tch_course c on o.fk_course_id = c.id               ");
			sql_temp.append("                             inner join pe_semester s on o.fk_semester_id = s.id               ");
			sql_temp.append("                             where s.flag_next_active = '1'                                    ");
			sql_temp.append("                             group by o.id) i on i.id = o.id                                   ");
			sql_temp.append("                 where s.flag_next_active = '1'                                                ");
			sql_temp.append("                 group by peTchCourse.id, i.electiveCount, peTchCourse.Name) course1,          ");
			sql_temp.append("               (select peTchCourse.id as id,                                                   ");
			sql_temp.append("                       peTchCourse.Name as courseName,                                         ");
			sql_temp.append("                       nvl(i.electiveCount, 0) as stuCount                                     ");
			sql_temp.append("                  from pr_tch_opencourse o                                                     ");
			sql_temp.append("                 inner join pe_tch_course peTchCourse on o.fk_course_id =                      ");
			sql_temp.append("                                                         peTchCourse.id                        ");
			sql_temp.append("                 inner join pe_semester s on o.fk_semester_id = s.id                           ");
			sql_temp.append("                  left join (select o.id, count(*) electiveCount                               ");
			sql_temp.append("                              from PR_TCH_PROGRAM_COURSE programCourse,                        ");
			sql_temp.append("                                   PE_TCH_PROGRAM_GROUP  programGroup,                         ");
			sql_temp.append("                                   pr_tch_stu_elective   e                                     ");
			sql_temp.append("                             inner join pr_tch_opencourse o on e.fk_tch_opencourse_id = o.id   ");
			sql_temp.append("                             inner join pe_tch_course c on o.fk_course_id = c.id               ");
			sql_temp.append("                             inner join pe_semester s on o.fk_semester_id = s.id               ");
			sql_temp.append("                             where s.flag_next_active = '1'                                    ");
			sql_temp.append("                               and e.fk_tch_program_course = programCourse.Id                  ");
			sql_temp.append("                               and programCourse.Fk_Programgroup_Id =                          ");
			sql_temp.append("                                   programGroup.Id                                             ");
			sql_temp.append("                               and programGroup.Fk_Coursegroup_Id = '_4'                       ");
			sql_temp.append("                             group by o.id) i on i.id = o.id                                   ");
			sql_temp.append("                 where s.flag_next_active = '1'                                                ");
			sql_temp.append("                 group by peTchCourse.id, i.electiveCount, peTchCourse.Name) course2           ");
			sql_temp.append("         where course1.id = course2.id															");	
		}

		this.setSqlCondition(sql_temp);
		Page page = null;
//		StringBuffer sb = new StringBuffer(sql);
		try {
			page = this.getGeneralService().getByPageSQL(sql_temp.toString(),
					Integer.parseInt(this.getLimit()),
					Integer.parseInt(this.getStart()));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}

}
