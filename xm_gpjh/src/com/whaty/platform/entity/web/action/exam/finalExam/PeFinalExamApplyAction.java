package com.whaty.platform.entity.web.action.exam.finalExam;

import java.util.Map;

import com.whaty.platform.entity.bean.PrExamBooking;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class PeFinalExamApplyAction extends MyBaseAction {

	@Override
	public Map updateColumn() {
		return null;
	}

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false,true,false);
		this.getGridConfig().setTitle(this.getText("期末考试预约列表"));
//		this.getGridConfig().addColumn(this.getText("row_num"),"row_num",false);
		this.getGridConfig().addColumn(this.getText("id"), "id", false,false,false,"");
		this.getGridConfig().addColumn(this.getText("姓名"), "peStudent.True_Name");
		this.getGridConfig().addColumn(this.getText("学号"), "peStudent.reg_no");
		this.getGridConfig().addColumn(this.getText("考试课程"), "peTchCourse.name");
		this.getGridConfig().addColumn(this.getText("所属建议考试批次"),"prTchOpencourse.advice_exam_no",false);
		this.getGridConfig().addColumn(this.getText("学习中心"),"peSite.name");
		this.getGridConfig().addColumn(this.getText("层次"),"peEdutype.name");
		this.getGridConfig().addColumn(this.getText("专业"),"peMajor.name");
		this.getGridConfig().addColumn(this.getText("年级"),"peGrade.name");
		this.getGridConfig().addColumn(this.getText("预约时间"),"booking_date",false);
	}

	public Page list(){
		
		String sql = " select booking.id as id,                                             " + 
		"        peStudent.True_Name as student_name,                               " +
		"        peStudent.Reg_No as reg_no,                                   " +
		"        peTchCourse.Name as course_name,                              " +
		"        prTchOpencourse.Advice_Exam_No as exam_no,                    " +
		"        peSite.Name as site_name,                                     " +
		"        peEdutype.Name as edutype_name,                               " +
		"        peMajor.Name as major_name,                                   " +
		"        peGrade.Name as grade_name,                                   " +
		"        to_char(booking.booking_date, 'yyyy-MM-dd') as booking_date   " +
		"   from pr_exam_booking       booking,                                " +
		"        pe_semester           semester,                               " +
		"        pr_tch_stu_elective   elective,                               " +
		"        pe_student            peStudent,                              " +
		"        pe_site               peSite,                                 " +
		"        pe_edutype            peEdutype,                              " +
		"        pe_major              peMajor,                                " +
		"        pe_grade              peGrade,                                " +
		"        pr_tch_program_course programcourse,                          " +
		"        pe_tch_course         peTchCourse,                            " +
		"        pr_tch_opencourse     prTchOpencourse,                        " +
		"        pe_semester           semeter_for_course                      " +
		"  where booking.fk_semester_id = semester.id                          " +
		"    and semester.flag_active = '1'                                    " +
		"    and booking.fk_tch_stu_elective_id = elective.id                  " +
		"    and elective.fk_stu_id = peStudent.id                             " +
		"    and peStudent.Fk_Site_Id = peSite.id                              " +
		"    and peStudent.Fk_Major_Id = peMajor.Id                            " +
		"    and peStudent.Fk_Grade_Id = peGrade.Id                            " +
		"    and peStudent.Fk_Edutype_Id = peEdutype.Id                        " +
		"    and elective.fk_tch_program_course = programcourse.id             " +
		"    and programcourse.fk_course_id = peTchCourse.Id                   " +
		"    and prTchOpencourse.Fk_Course_Id = peTchCourse.Id                 " +
		"    and prTchOpencourse.Fk_Semester_Id = semeter_for_course.id        " +
		"    and semeter_for_course.flag_active = '1'                          " ;

		StringBuffer sql_temp = new StringBuffer(sql);
		this.setSqlCondition(sql_temp);
		Page page = null;
		try {
			page = getGeneralService().getByPageSQL(sql_temp.toString(), Integer.parseInt(this.getLimit()), Integer.parseInt(this.getStart()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return page;
	}
	
	@Override
	public void setEntityClass() {
		this.entityClass = PrExamBooking.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/exam/peFinalExamApply";
	}

	

}
