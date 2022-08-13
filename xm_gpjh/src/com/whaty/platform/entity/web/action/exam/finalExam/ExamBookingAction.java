package com.whaty.platform.entity.web.action.exam.finalExam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.whaty.platform.entity.bean.PrTchStuElective;
import com.whaty.platform.entity.service.exam.finalExam.PrExamBookingService;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
/**
 * 代学生预约考试Acton
 * @author zhangyu
 *
 */
public class ExamBookingAction extends MyBaseAction {

	private PrExamBookingService prExamBookingService;
	
	@Override
	public Map updateColumn() {

		Map map = new HashMap();
		List ids = new ArrayList();
		if(this.getIds() != null && this.getIds().length() > 0){
			String [] tempids = this.getIds().split(",");
			for(String id:tempids){
				ids.add(id);
			}
		}
		if(this.getColumn() != null && "bookingExam".equals(this.getColumn())){
			String temp = this.convertIds(ids);
			StringBuffer courseNames = new StringBuffer();
			StringBuffer studentNames = new StringBuffer();
			if(isOpenCourse(temp, courseNames)){
				map.put("success",false);
				map.put("info", "预约考试失败<br/>课程“" + courseNames.toString() + "”在本学期没有开课，暂不能为其预约考试");
				return map;
			}
			if(isConflictToChoose(temp, studentNames)){
				map.put("success",false);
				map.put("info", "预约考试失败<br/>学生所预约的考试课程最多只能有两门课程同属于一个考试场次，<br/>而为学生“" + studentNames.toString() + "”<br/>所预约的考试存在多个这样的冲突");
				return map;
			}
			if(isConflictToAll(temp, studentNames)){
				map.put("success",false);
				map.put("info", "预约考试失败<br/>学生所预约的考试课程最多只能有两门课程同属于一个考试场次，<br/>而本次为学生“" + studentNames.toString() + "”<br/>所预约的考试课程与学生在本学期已经预约过的考试课程存在多个这样的冲突");
				return map;
			}
			
			try{
				this.getPrExamBookingService().save_BookingData(ids);
				map.put("success",true);
				map.put("info", "预约考试成功");
			}catch(Exception e){
				e.printStackTrace();
				map.put("success",false);
				map.put("info", e.getMessage());
			}
			
		}else{
			return super.updateColumn();
		}
		
		return map;
	}

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false,false,false);
		this.getGridConfig().setTitle("代学生预约考试");
//		this.getGridConfig().addColumn(this.getText("row_num"),"row_num",false,false,false,"");
		this.getGridConfig().addColumn(this.getText("id"),"id",false,false,false,"");
		this.getGridConfig().addColumn(this.getText("姓名"),"student_name");
		this.getGridConfig().addColumn(this.getText("学号"),"reg_no");
		this.getGridConfig().addColumn(this.getText("考试课程"),"Combobox_PeTchCourse.course_name");
		this.getGridConfig().addColumn(this.getText("所属考试场次"),"examno",false);
		this.getGridConfig().addColumn(this.getText("学习中心"),"Combobox_PeSite.site_name");
		this.getGridConfig().addColumn(this.getText("层次"),"Combobox_PeEdutype.edutype_name");
		this.getGridConfig().addColumn(this.getText("专业"),"Combobox_PeMajor.major_name");
		this.getGridConfig().addColumn(this.getText("年级"),"Combobox_PeGrade.grade_name");
		this.getGridConfig().addMenuFunction(this.getText("为所选中的学生预约考试"), "bookingExam", "");
//		this.getGridConfig().addMenuScript("为选中的学生预约考试", "{window.location='/entity/exam/examCourses.action'}");
	}
	public Page list(){
		
//		String sql_temp = "";
		
//		if(this.getBean() != null){
//			if(this.getBean().getPeStudent().getName() != null && !"".equals(this.getBean().getPeStudent().getName())){
//				sql_temp += " and peStudent.True_Name like '%"+this.getBean().getPeStudent().getName()+"%' ";
//			}
//			if(this.getBean().getPeStudent().getRegNo() != null && !"".equals(this.getBean().getPeStudent().getRegNo())){
//				sql_temp += " and peStudent.reg_no like '%"+this.getBean().getPeStudent().getRegNo()+"%' ";
//			}
//			if(this.getBean().getPeStudent().getPeSite().getName() != null &&
//					!"".equals(this.getBean().getPeStudent().getPeSite().getName())){
//				sql_temp += " and peSite.name like '%"+this.getBean().getPeStudent().getPeSite().getName()+"%' ";
//			}
//			if(this.getBean().getPeStudent().getPeEdutype().getName() != null && 
//					!"".equals(this.getBean().getPeStudent().getPeEdutype().getName())){
//				sql_temp += " and peEdutype.name like '%"+this.getBean().getPeStudent().getPeEdutype().getName()+"%' ";
//			}
//			if(this.getBean().getPeStudent().getPeGrade().getName() != null && 
//					!"".equals(this.getBean().getPeStudent().getPeGrade().getName())){
//				sql_temp += " and peGrade.name like '%"+this.getBean().getPeStudent().getPeGrade().getName()+"%' ";
//			}
//			if(this.getBean().getPeStudent().getPeMajor().getName() != null && 
//					!"".equals(this.getBean().getPeStudent().getPeMajor().getName())){
//				sql_temp += " and peMajor.name like '%"+this.getBean().getPeStudent().getPeMajor().getName()+"%' ";
//			}
//		}
		/**
		 * 查询条件：未参加过预约考试的（学生）课程列表，曾参加过考试，但考试没过（缺考、违纪、作弊或考试不及格）并且在当前考试学期还没有预约考试的（学生）课程列表
		 */

//		String sql = "          select elective.id                    as id,								                  " + 
//		"                 peStudent.True_Name            as student_name,                       " +
//		"                 peStudent.Reg_No               as reg_no,                             " +
//		"                 peTchCourse.Name               as course_name,                        " +
//		"                 prTchOpencourse.Advice_Exam_No as examno,                             " +
//		"                 peSite.Name                    as site_name,                          " +
//		"                 peEdutype.Name                 as edutype_name,                       " +
//		"                 peMajor.Name                   as major_name,                         " +
//		"                 peGrade.Name                   as grade_name                          " +
//		"            from pr_tch_stu_elective   elective,                                       " +
//		"                 pe_student            peStudent,                                      " +
//		"                 pe_site               peSite,                                         " +
//		"                 pe_edutype            peEdutype,                                      " +
//		"                 pe_major              peMajor,                                        " +
//		"                 pe_grade              peGrade,                                        " +
//		"                 pr_tch_program_course programcourse,                                  " +
//		"                 pe_tch_course         peTchCourse,                                    " +
//		"                 pr_tch_opencourse     prTchOpencourse,                                " +
//		"                 pe_semester           semester,                                       " +
//		"                 enum_const            enum_stu_status,                                " +
//		"                 enum_const            enumAdmission,                                  " +
//		"                 enum_const            enumScoreStatus                                 " +
//		"           where elective.fk_stu_id = peStudent.id                                     " +
//		"             and peStudent.fk_site_id = peSite.id                                      " +
//		"             and peStudent.fk_major_id = peMajor.id                                    " +
//		"             and peStudent.fk_grade_id = peGrade.id                                    " +
//		"             and peStudent.fk_edutype_id = peEdutype.id                                " +
//		"             and peStudent.flag_student_status = enum_stu_status.id                    " +
//		"             and enum_stu_status.code = '4'                                            " +
//		"             and prTchOpencourse.Fk_Course_Id(+) = peTchCourse.Id                      " +
//		"             and prTchOpencourse.Fk_Semester_Id = semester.id                          " +
//		"                and semester.flag_active = '1'                                         " +
//		"             and elective.fk_tch_program_course = programcourse.id                     " +
//		"             and programcourse.fk_course_id = peTchCourse.id                           " +
//		"             and elective.flag_elective_admission = enumAdmission.id                   " +
//		"             and enumAdmission.code = '1'                                              " +
//		"             and elective.flag_score_status = enumScoreStatus.id                       " +
//		"             and (enumScoreStatus.code != '1' or elective.score_total < " +
//		this.getMyListService().getSysValueByName("creditMustScore") +
//		"              or         elective.score_total is null)                                 " +
//		"             and peTchCourse.Score_Exam_Rate != 0                                      " +
//		"          minus                                                                        " +
//		"          select elective.id,                                                          " +
//		"                 peStudent.True_Name,                                                  " +
//		"                 peStudent.Reg_No,                                                     " +
//		"                 peTchCourse.Name,                                                     " +
//		"                 prTchOpencourse.Advice_Exam_No,                                       " +
//		"                 peSite.Name,                                                          " +
//		"                 peEdutype.Name,                                                       " +
//		"                peMajor.Name,                                                          " +
//		"                 peGrade.Name                                                          " +
//		"            from pr_exam_booking       booking,                                        " +
//		"                 pe_semester           semester,                                       " +
//		"                 pr_tch_stu_elective   elective,                                       " +
//		"                 pe_student            peStudent,                                      " +
//		"                 pe_site               peSite,                                         " +
//		"                 pe_edutype            peEdutype,                                      " +
//		"                 pe_major              peMajor,                                        " +
//		"                 pe_grade              peGrade,                                        " +
//		"                 pr_tch_program_course programcourse,                                  " +
//		"                 pe_tch_course         peTchCourse,                                    " +
//		"                 pr_tch_opencourse     prTchOpencourse                                 " +
//		"           where booking.fk_semester_id = semester.id                                  " +
//		"             and semester.flag_active = '1'                                            " +
//		"             and booking.fk_tch_stu_elective_id = elective.id                          " +
//		"             and elective.fk_stu_id = peStudent.Id                                     " +
//		"                and prTchOpencourse.Fk_Course_Id(+) = peTchCourse.Id                   " +
//		"             and prTchOpencourse.Fk_Semester_Id = semester.id                          " +
//		"             and elective.fk_tch_program_course=programcourse.id                       " +
//		"             and programcourse.fk_course_id= peTchCourse.Id                            " +
//		"             and peStudent.Fk_Site_Id = peSite.Id                                      " +
//		"             and peStudent.Fk_Major_Id = peMajor.Id                                    " +
//		"             and peStudent.Fk_Grade_Id = peGrade.Id                                    " +
//		"             and peStudent.Fk_Edutype_Id = peEdutype.Id                                " ;
		StringBuffer sql_temp = new StringBuffer();
		sql_temp.append(  "          select elective.id                    as id,								                 " ); 
		sql_temp.append("                 peStudent.True_Name            as student_name,                       "  );
		sql_temp.append("                 peStudent.Reg_No               as reg_no,                             "  );
		sql_temp.append("                 peTchCourse.Name               as course_name,                        "  );
		sql_temp.append("                 prTchOpencourse.Advice_Exam_No as examno,                             "  );
		sql_temp.append("                 peSite.Name                    as site_name,                          "  );
		sql_temp.append("                 peEdutype.Name                 as edutype_name,                       "  );
		sql_temp.append("                 peMajor.Name                   as major_name,                         "  );
		sql_temp.append("                 peGrade.Name                   as grade_name                          "  );
		sql_temp.append("            from pr_tch_stu_elective   elective,                                       "  );
		sql_temp.append("                 pe_student            peStudent,                                      "  );
		sql_temp.append("                 pe_site               peSite,                                         "  );
		sql_temp.append("                 pe_edutype            peEdutype,                                      "  );
		sql_temp.append("                 pe_major              peMajor,                                        "  );
		sql_temp.append("                 pe_grade              peGrade,                                        "  );
		sql_temp.append("                 pr_tch_program_course programcourse,                                  "  );
		sql_temp.append("                 pe_tch_course         peTchCourse,                                    "  );
		sql_temp.append("                 pr_tch_opencourse     prTchOpencourse,                                "  );
		sql_temp.append("                 pe_semester           semester,                                       "  );
		sql_temp.append("                 enum_const            enum_stu_status,                                "  );
		sql_temp.append("                 enum_const            enumAdmission,                                  "  );
		sql_temp.append("                 enum_const            enumScoreStatus                                 "  );
		sql_temp.append("           where elective.fk_stu_id = peStudent.id                                     "  );
		sql_temp.append("             and peStudent.fk_site_id = peSite.id                                      "  );
		sql_temp.append("             and peStudent.fk_major_id = peMajor.id                                    "  );
		sql_temp.append("             and peStudent.fk_grade_id = peGrade.id                                    "  );
		sql_temp.append("             and peStudent.fk_edutype_id = peEdutype.id                                "  );
		sql_temp.append("             and peStudent.flag_student_status = enum_stu_status.id                    "  );
		sql_temp.append("             and enum_stu_status.code = '4'                                            "  );
		sql_temp.append("             and prTchOpencourse.Fk_Course_Id(+) = peTchCourse.Id                      "  );
		sql_temp.append("             and prTchOpencourse.Fk_Semester_Id = semester.id                          "  );
		sql_temp.append("                and semester.flag_active = '1'                                         "  );
		sql_temp.append("             and elective.fk_tch_program_course = programcourse.id                     "  );
		sql_temp.append("             and programcourse.fk_course_id = peTchCourse.id                           "  );
		sql_temp.append("             and elective.flag_elective_admission = enumAdmission.id                   "  );
		sql_temp.append("             and enumAdmission.code = '1'                                              "  );
		sql_temp.append("             and elective.flag_score_status = enumScoreStatus.id                       "  );
		sql_temp.append("             and ((enumScoreStatus.code != '1' and enumScoreStatus.code != '5') or elective.score_total < "               )  ;
		sql_temp.append(this.getMyListService().getSysValueByName("creditMustScore")                            )   ;
		sql_temp.append("              or         elective.score_total is null)                                 "  );
		sql_temp.append("             and peTchCourse.Score_Exam_Rate != 0                                      "  );
		sql_temp.append("          minus                                                                        "  );
		sql_temp.append("          select elective.id,                                                          "  );
		sql_temp.append("                 peStudent.True_Name,                                                  "  );
		sql_temp.append("                 peStudent.Reg_No,                                                     "  );
		sql_temp.append("                 peTchCourse.Name,                                                     "  );
		sql_temp.append("                 prTchOpencourse.Advice_Exam_No,                                       "  );
		sql_temp.append("                 peSite.Name,                                                          "  );
		sql_temp.append("                 peEdutype.Name,                                                       "  );
		sql_temp.append("                peMajor.Name,                                                          "  );
		sql_temp.append("                 peGrade.Name                                                          "  );
		sql_temp.append("            from pr_exam_booking       booking,                                        "  );
		sql_temp.append("                 pe_semester           semester,                                       "  );
		sql_temp.append("                 pr_tch_stu_elective   elective,                                       "  );
		sql_temp.append("                 pe_student            peStudent,                                      "  );
		sql_temp.append("                 pe_site               peSite,                                         "  );
		sql_temp.append("                 pe_edutype            peEdutype,                                      "  );
		sql_temp.append("                 pe_major              peMajor,                                        "  );
		sql_temp.append("                 pe_grade              peGrade,                                        "  );
		sql_temp.append("                 pr_tch_program_course programcourse,                                  "  );
		sql_temp.append("                 pe_tch_course         peTchCourse,                                    "  );
		sql_temp.append("                 pr_tch_opencourse     prTchOpencourse                                 "  );
		sql_temp.append("           where booking.fk_semester_id = semester.id                                  "  );
		sql_temp.append("             and semester.flag_active = '1'                                            "  );
		sql_temp.append("             and booking.fk_tch_stu_elective_id = elective.id                          "  );
		sql_temp.append("             and elective.fk_stu_id = peStudent.Id                                     "  );
		sql_temp.append("                and prTchOpencourse.Fk_Course_Id(+) = peTchCourse.Id                   "  );
		sql_temp.append("             and prTchOpencourse.Fk_Semester_Id = semester.id                          "  );
		sql_temp.append("             and elective.fk_tch_program_course=programcourse.id                       "  );
		sql_temp.append("             and programcourse.fk_course_id= peTchCourse.Id                            "  );
		sql_temp.append("             and peStudent.Fk_Site_Id = peSite.Id                                      "  );
		sql_temp.append("             and peStudent.Fk_Major_Id = peMajor.Id                                    "  );
		sql_temp.append("             and peStudent.Fk_Grade_Id = peGrade.Id                                    "  );
		sql_temp.append("             and peStudent.Fk_Edutype_Id = peEdutype.Id                                "  );
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
		this.entityClass = PrTchStuElective.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/exam/examBooking";
	}

	public void setBean(PrTchStuElective instance){
		this.superSetBean(instance);
	}
	
	public PrTchStuElective getBean(){
		return (PrTchStuElective)this.superGetBean();
	}
	
	/**
	 * 用于判断即将进行预约考试的学生所要考试的科目在当前学期是否开课
	 * 如果当前学期没有开这门课，不能进行这门课程的预约
	 * @param ids
	 * @param courseNames
	 * @return
	 */
	private boolean isOpenCourse(String ids,StringBuffer courseNames){
		boolean result = false;
		
		String sql_check = " select distinct course.name                                  " + 
		"   from pr_tch_stu_elective   elective,                       " +
		"        pr_tch_program_course programcourse,                  " +
		"        pe_tch_course         course                          " +
		"  where elective.fk_tch_program_course = programcourse.id     " +
		"    and programcourse.fk_course_id = course.id                " +
		"    and elective.id in 									   " + ids +
		" minus                                                        " +
		" select distinct course.name                                  " +
		"   from pr_tch_stu_elective   elective,                       " +
		"        pr_tch_program_course programcourse,                  " +
		"        pe_tch_course         course,                         " +
		"        pr_tch_opencourse     opencourse,                     " +
		"        pe_semester           semester                        " +
		"  where elective.fk_tch_program_course = programcourse.id     " +
		"    and programcourse.fk_course_id = course.id                " +
		"    and opencourse.fk_course_id = course.id                   " +
		"    and opencourse.fk_semester_id = semester.id               " +
		"    and semester.flag_active = '1'                            " +
		"    and elective.id in 									   " + ids;
		
		List list = new ArrayList();
		
		try{
			list = this.getGeneralService().getBySQL(sql_check);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		if(list.size() > 0){
			courseNames.append(list.get(0).toString());
			for(int i = 1; i < list.size(); i++){
				courseNames.append("、" + list.get(i).toString());
			}
			result = true;
		}
		
		return result;
	}
	
	/**
	 * 为学生预约考试有一个限定条件：就是学生预约考试的时候允许在一个考试场次下选择两门课程来考试，但
	 * 只允许有两门这样冲突的课程，如果有多个冲突的，则提示选课失败，为所选择的***学生预约考试的时候只
	 * 允许有两门考试课程冲突。（只对所选择的记录进行判断）
	 * @param ids
	 * @param studentNames
	 * @return
	 */
	private boolean isConflictToChoose(String ids,StringBuffer studentNames){
		boolean result = false;
		
		String sql_check = " select two.studentName                                                     " +
		"   from (select one.studentName as studentName, sum(one.nums - 1) as nums   " +
		"           from (select count(elective.id) as nums,                         " +
		"                        student.name as studentName,                        " +
		"                        opencourse.advice_exam_no as examno                 " +
		"                   from pr_tch_stu_elective   elective,                     " +
		"                        pe_student            student,                      " +
		"                        enum_const			   enum_stu_status,              " +
		"                        pr_tch_program_course programcourse,                " +
		"                        pe_tch_course         course,                       " +
		"                        pr_tch_opencourse     opencourse,                   " +
		"                        pe_semester           semester                      " +
		"                  where elective.fk_stu_id = student.id                     " +
		"                    and student.flag_student_status = enum_stu_status.id    " +
		"                    and enum_stu_status.code = '4'                          " +
		"                    and elective.fk_tch_program_course = programcourse.id   " +
		"                    and programcourse.fk_course_id = course.id              " +
		"                    and opencourse.fk_course_id = course.id                 " +
		"                    and opencourse.fk_semester_id = semester.id             " +
		"                    and semester.flag_active = '1'                          " +
		"                    and elective.id in                                      " + ids +
		"                  group by student.name, opencourse.advice_exam_no          " +
		"                 having count(elective.id) > 1) one                         " +
		"          group by one.studentName                                          " +
		"         having sum(one.nums - 1) > 1) two                                  " ;
		
		List list = new ArrayList();
		try{
			list = this.getGeneralService().getBySQL(sql_check);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		if(list.size() > 0){
			studentNames.append(list.get(0).toString());
			for(int i = 1; i < list.size(); i++){
				studentNames.append("、" + list.get(i).toString());
			}
			result = true;
		}
		
		return result;
	}
	
	/**
	 * 对所选择记录进行判断包括所选记录中学生已经预约的其他考试课程，如果有多于两门课程的冲突，预约考试失败
	 * 并给出页面提示信息
	 * @param ids
	 * @param studentNames
	 * @return
	 */
	private boolean isConflictToAll(String ids, StringBuffer studentNames){
		boolean result = false;
		
		String sql_check = " select three.student_name                                                          " + 
		"   from (select two.student_name as student_name, sum(nums - 1) as num              " +
		"           from (select one.student_name as student_name,                           " +
		"                        one.examno as examno,                                       " +
		"                        count(one.id) as nums                                       " +
		"                   from (select elective.id               as id,                    " +
		"                                student.name              as student_name,          " +
		"                                opencourse.advice_exam_no as examno                 " +
		"                           from pr_exam_booking booking,                            " +
		"                                pr_tch_stu_elective elective,                       " +
		"                                pe_student student,                                 " +
		"                                enum_const			   enum_stu_status,              " +
		"                                pr_tch_program_course programcourse,                " +
		"                                pe_tch_course course,                               " +
		"                                pr_tch_opencourse opencourse,                       " +
		"                                pe_semester semester_for_course,                    " +
		"                                pe_semester semester_for_booking,                   " +
		"                                (select elective.fk_stu_id as student_id            " +
		"                                   from pr_tch_stu_elective elective                " +
		"                                  where elective.id in                              " + ids +
		"                                  ) stuid                                           " +
		"                          where booking.fk_semester_id =                            " +
		"                                semester_for_booking.id                             " +
		"                            and semester_for_booking.flag_active = '1'              " +
		"                            and booking.fk_tch_stu_elective_id = elective.id        " +
		"                            and elective.fk_tch_program_course =                    " +
		"                                programcourse.id                                    " +
		"                            and programcourse.fk_course_id = course.id              " +
		"                            and opencourse.fk_course_id = course.id                 " +
		"                            and opencourse.fk_semester_id =                         " +
		"                                semester_for_course.id                              " +
		"                            and semester_for_course.flag_active = '1'               " +
		"                            and elective.fk_stu_id = student.id                     " +
		"                            and student.id = stuid.student_id                       " +
		"                            and student.flag_student_status = enum_stu_status.id    " +
		"                            and enum_stu_status.code = '4'                          " +
		"                         union                                                      " +
		"                         select elective.id               as id,                    " +
		"                                student.name              as student_name,          " +
		"                                opencourse.advice_exam_no as examno                 " +
		"                           from pr_tch_stu_elective   elective,                     " +
		"                                pe_student            student,                      " +
		"                                pr_tch_program_course programcourse,                " +
		"                                pe_tch_course         course,                       " +
		"                                pr_tch_opencourse     opencourse,                   " +
		"                                enum_const			   enum_stu_status,              " +
		"                                pe_semester           semester                      " +
		"                          where elective.fk_tch_program_course =                    " +
		"                                programcourse.id                                    " +
		"                            and elective.fk_stu_id = student.id                     " +
		"                            and student.flag_student_status = enum_stu_status.id    " +
		"                            and enum_stu_status.code = '4'                          " +
		"                            and programcourse.fk_course_id = course.id              " +
		"                            and opencourse.fk_course_id = course.id                 " +
		"                            and opencourse.fk_semester_id = semester.id             " +
		"                            and semester.flag_active = '1'                          " +
		"                            and elective.id in                                      " + ids +
		"                            ) one                                                   " +
		"                  group by one.student_name, one.examno                             " +
		"                 having count(one.id) > 1) two                                      " +
		"          group by two.student_name                                                 " +
		"         having sum(nums - 1) > 1) three                                            " ;
		
		List list = new ArrayList();
		try{
			list = this.getGeneralService().getBySQL(sql_check);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		if(list.size() > 0){
			studentNames.append(list.get(0).toString());
			for(int i = 1; i < list.size(); i++){
				studentNames.append("、" + list.get(i).toString());
			}
			result = true;
		}
		
		
		return result;
	}
	
	/**
	 * 转换ids
	 * @return
	 */
	private String convertIds(List ids){
		String temp = "";
		
		temp += "(''";
		for(int i = 0; i < ids.size(); i++){
			temp += ",'"+ids.get(i).toString()+"'";
		}
		temp += ")";
		
		return temp;
	}

	public PrExamBookingService getPrExamBookingService() {
		return prExamBookingService;
	}

	public void setPrExamBookingService(PrExamBookingService prExamBookingService) {
		this.prExamBookingService = prExamBookingService;
	}
	
	
}
