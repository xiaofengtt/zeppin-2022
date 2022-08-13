package com.whaty.platform.entity.web.action.exam.finalExam;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeSemester;
import com.whaty.platform.entity.bean.PrTchStuElective;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;

/**
 * 未预约期末考试学生列表
 * @author Administrator
 *
 */
public class FinalExamNoBookingAction extends MyBaseAction {

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false,false,false);
		this.getGridConfig().setTitle(this.getText("在学考试课程未预约期末考试列表"));
		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("选课学期"), "combobox_peSemester.semester");
		this.getGridConfig().addColumn(this.getText("学习中心"), "combobox_peSite.site");
		this.getGridConfig().addColumn(this.getText("年级"), "combobox_peGrade.grade");
		this.getGridConfig().addColumn(this.getText("专业"), "combobox_peMajor.major");
		this.getGridConfig().addColumn(this.getText("姓名"), "truename");
		this.getGridConfig().addColumn(this.getText("学号"), "reg");
		this.getGridConfig().addColumn(this.getText("手机"), "phone");
		this.getGridConfig().addColumn(this.getText("课程名称"), "combobox_peTchCourse.course");
	}

	@Override
	public void setEntityClass() {
		this.entityClass=PrTchStuElective.class;

	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/exam/finalExamNoBooking";

	}
//	public DetachedCriteria initDetachedCriteria() {
//		String sql = "select prTchStuElective.Id "
//        +"  from pr_exam_booking     prExamBooking, "
//        +"      pr_tch_stu_elective prTchStuElective, "
//        +"      pe_semester         peSemester "
//        +" where prExamBooking.Fk_Semester_Id = peSemester.Id "
//        +"   and prExamBooking.Fk_Tch_Stu_Elective_Id = prTchStuElective.Id "
//        +"   and peSemester.Flag_Active = '1' ";
//		List<String> list = null;
//		try {
//			list = this.getGeneralService().getBySQL(sql);
//		} catch (EntityException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		DetachedCriteria dcSemester = DetachedCriteria.forClass(PeSemester.class);
//		dcSemester.add(Restrictions.eq("flagActive", "1"));
//		List<PeSemester> sem = null;
//		try {
//			sem=this.getGeneralService().getList(dcSemester);
//		} catch (EntityException e) {
//			e.printStackTrace();
//		}
//		DetachedCriteria dc = DetachedCriteria.forClass(PrTchStuElective.class);
//		dc.createCriteria("peStudent", "peStudent").createAlias("prStudentInfo", "prStudentInfo")
//			.createAlias("peGrade", "peGrade").createAlias("peMajor", "peMajor")
//				.createAlias("peSite", "peSite").createAlias("peEdutype", "peEdutype")
//					.createCriteria("enumConstByFlagStudentStatus", "enumConstByFlagStudentStatus").add(Restrictions.eq("code", "4"));
//		DetachedCriteria dcOpencourse  =dc.createCriteria("prTchOpencourse", "prTchOpencourse");
//		dcOpencourse.createCriteria("peSemester", "peSemester").add(Restrictions.le("serialNumber", sem.get(0).getSerialNumber()));
//		dcOpencourse.createCriteria("peTchCourse", "peTchCourse")
//				.add(Restrictions.gt("scoreExamRate", 0d));
//		dc.createCriteria("enumConstByFlagElectiveAdmission", "enumConstByFlagElectiveAdmission").add(Restrictions.eq("code", "1"));
//		if(list!=null&&list.size()>0){
//			dc.add(Restrictions.not(Restrictions.in("id", list)));
//		}
//		dc.add(Restrictions.or(Restrictions.isNull("scoreTotal"), Restrictions.lt("scoreTotal", Double.parseDouble(this.getMyListService().getSysValueByName("creditMustScore")))));
//		return dc;
//	}
	public Page list(){
		StringBuffer sql_temp = new StringBuffer();
		sql_temp.append("  select rownum as id, one.*   																							");										
		sql_temp.append("    from (select semester.name    as semester,                               ");
		sql_temp.append("                 site.name        as site,                                   ");
		sql_temp.append("                 grade.name       as grade,                                  ");
		sql_temp.append("                 major.name       as major,                                  ");
		sql_temp.append("                 stu.true_name    as truename,                                   ");
		sql_temp.append("                 stu.reg_no       as reg,                                    ");
		sql_temp.append("                 info.mobilephone as phone,                                  ");
		sql_temp.append("                 course.name      as course                                  ");
		sql_temp.append("            from pr_tch_stu_elective elec,                                   ");
		sql_temp.append("                 pe_student          stu,                                    ");
		sql_temp.append("                 pr_student_info     info,                                   ");
		sql_temp.append("                 pr_tch_opencourse   open,                                   ");
		sql_temp.append("                 pe_semester         semester,                               ");
		sql_temp.append("                 pe_tch_course       course,                                 ");
		sql_temp.append("                 pe_site             site,                                   ");
		sql_temp.append("                 pe_edutype          edutype,                                ");
		sql_temp.append("                 pe_major            major,                                  ");
		sql_temp.append("                 pe_grade            grade,                                  ");
		sql_temp.append("                 enum_const          status,                                 ");
		sql_temp.append("                 enum_const          admission                               ");
		sql_temp.append("           where elec.fk_stu_id = stu.id                                     ");
		sql_temp.append("             and elec.fk_tch_opencourse_id = open.id                         ");
		sql_temp.append("             and (elec.score_total is null or                                ");
		sql_temp.append("                 elec.score_total <                                          ");
		sql_temp.append("                 (select t.value                                             ");
		sql_temp.append("                     from system_variables t                                 ");
		sql_temp.append("                    where t.name = 'creditMustScore'))                       ");
		sql_temp.append("             and open.fk_semester_id = semester.id                           ");
		sql_temp.append("             and open.fk_course_id = course.id                               ");
		sql_temp.append("             and semester.serial_number <=                                   ");
		sql_temp.append("                 (select s.serial_number                                     ");
		sql_temp.append("                    from pe_semester s                                       ");
		sql_temp.append("                   where s.flag_active = '1')                                ");
		sql_temp.append("             and stu.fk_student_info_id = info.id                            ");
		sql_temp.append("             and stu.fk_site_id = site.id                                    ");
		sql_temp.append("             and stu.fk_major_id = major.id                                  ");
		sql_temp.append("             and stu.fk_grade_id = grade.id                                  ");
		sql_temp.append("             and stu.fk_edutype_id = edutype.id                              ");
		sql_temp.append("             and stu.flag_student_status = status.id                         ");
		sql_temp.append("             and status.namespace = 'FlagStudentStatus'                      ");
		sql_temp.append("             and course.score_exam_rate > 0                                  ");
		sql_temp.append("             and status.code = '4'                                           ");
		sql_temp.append("             and elec.flag_elective_admission = admission.id                 ");
		sql_temp.append("             and admission.namespace = 'FlagElectiveAdmission'               ");
		sql_temp.append("             and admission.code = '1'                                        ");
		sql_temp.append("             and elec.id not in                                              ");
		sql_temp.append("                 (select prTchStuElective.Id                                 ");
		sql_temp.append("                    from pr_exam_booking     prExamBooking,                  ");
		sql_temp.append("                         pr_tch_stu_elective prTchStuElective,               ");
		sql_temp.append("                         pe_semester         peSemester                      ");
		sql_temp.append("                   where prExamBooking.Fk_Semester_Id = peSemester.Id        ");
		sql_temp.append("                     and prExamBooking.Fk_Tch_Stu_Elective_Id =              ");
		sql_temp.append("                         prTchStuElective.Id                                 ");
		sql_temp.append("                     and peSemester.Flag_Active = '1')) one									");	
		 this.setSqlCondition(sql_temp);
			Page page = null;
			try {
	            page = getGeneralService().getByPageSQL(sql_temp.toString(),
	                    Integer.parseInt(this.getLimit()),
	                    Integer.parseInt(this.getStart()));
			} catch (EntityException e) {
				e.printStackTrace();
			}
			return page;
	}
}
