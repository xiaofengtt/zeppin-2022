package com.whaty.platform.entity.web.action.workspaceStudent;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeSemester;
import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.bean.PrTchStuElective;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.exam.finalExam.PrExamBookingService;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;

/**
 * 学生工作室学生考试预约
 * @author 李冰
 *
 */
public class PrExamReserverAction extends MyBaseAction {
	private List courseList; //保存学生可预约考试的课程
	private PrExamBookingService prExamBookingService;
	private String ids;
	private PeSemester peSemester;//保存当前学期信息
	private boolean checkDate; //判断是否在申请时间之内
	private String edutype;
	
	public String getEdutype() {
		return edutype;
	}

	public void setEdutype(String edutype) {
		this.edutype = edutype;
	}

	public PeSemester getPeSemester() {
		return peSemester;
	}

	public void setPeSemester(PeSemester peSemester) {
		this.peSemester = peSemester;
	}

	public boolean isCheckDate() {
		return checkDate;
	}

	public void setCheckDate(boolean checkDate) {
		this.checkDate = checkDate;
	}
	
	/**
	 * 转向菜单页面
	 * @return
	 */
	public String toExamReserverMenu(){
		UserSession userSession = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		if (userSession!=null) {
			DetachedCriteria dc = DetachedCriteria.forClass(PeStudent.class);
			dc.add(Restrictions.eq("ssoUser", userSession.getSsoUser()));
			List student = null;
			try {
				student = this.getGeneralService().getList(dc);
			} catch (EntityException e) {
				e.printStackTrace();
			}
			if(student != null && !student.isEmpty()) {
				PeStudent stu = (PeStudent)student.get(0);
				if (stu.getPeEdutype().getName().indexOf("本") > 0) {
					this.setEdutype("edutype");
				} 
			}
		}

		return "examReserverMenu";
	}
	/**
	 * 转向预约页面
	 * @return
	 */
	public String courseList() {
		if (this.getUserPeStudentId()==null) {
			this.setMsg("无法取得您的信息请重新登录");
			return "msg";
		}
		if (!this.checkStatus()) {
			this.setMsg("您的学籍状态不是在籍状态，无操作权限");
			return "msg";
		}
		this.theCourses();
		this.theSemester();
		this.checkApplyDate();
		return "examCourseList";
	}
	/**
	 * 判断学生是否是在籍状态
	 * @return
	 */
	private boolean checkStatus() {
		UserSession userSession = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		if (userSession==null) {
			return false;
		}
		DetachedCriteria dc = DetachedCriteria.forClass(PeStudent.class);
		dc.add(Restrictions.eq("ssoUser", userSession.getSsoUser()));
		List student = null;
		try {
			student = this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		if(student != null && !student.isEmpty()) {
			PeStudent stu = (PeStudent)student.get(0);
			if(stu.getEnumConstByFlagStudentStatus().getCode().equals("4")){
				return true;
			}
		}
		return false;
	}
	/**
	 * 预约操作
	 * @return
	 */
	public String examReserver() {
		
		List ids = new ArrayList();
		if(this.getIds() != null && this.getIds().length() > 0){
			String [] tempids = this.getIds().split(",");
			for(String id:tempids){
				ids.add(id.trim());
			}
		}
		this.setTogo("/entity/workspaceStudent/prExamReserver_courseList.action");
		
		//限制预约考试最多只能是六门
		if(ids.size()>6){
			this.setMsg("预约失败,最多只能预约6门");
			return "msg";
		}else{
			StringBuffer sql_temp = new StringBuffer();
			sql_temp.append("   select count(*)         												  ");
			sql_temp.append("	   from pr_exam_booking       booking,                                    ");
			sql_temp.append("	        pe_semester           semester,                                   ");
			sql_temp.append("	        pr_tch_stu_elective   elective,                                   ");
			sql_temp.append("	        pe_student            peStudent,                                  ");                         
			sql_temp.append("	        pr_tch_program_course programcourse,                              ");
			sql_temp.append("	        pe_tch_course         peTchCourse,                                ");
			sql_temp.append("	        pr_tch_opencourse     prTchOpencourse,                            ");
			sql_temp.append("	        pe_semester           semeter_for_course                          ");
			sql_temp.append("	  where booking.fk_semester_id = semester.id                              ");
			sql_temp.append("    and semester.flag_active = '1'                                           ");
			sql_temp.append("	    and booking.fk_tch_stu_elective_id = elective.id                      ");
			sql_temp.append("    and peStudent.id = '" + this.getUserPeStudentId() + "'                   ");
			sql_temp.append("	    and elective.fk_stu_id = peStudent.id                                 ");               
			sql_temp.append("	    and elective.fk_tch_program_course = programcourse.id                 ");
			sql_temp.append("	    and programcourse.fk_course_id = peTchCourse.Id                       ");
			sql_temp.append("	    and prTchOpencourse.Fk_Course_Id = peTchCourse.Id                     ");
			sql_temp.append("	    and prTchOpencourse.Fk_Semester_Id = semeter_for_course.id            ");
			sql_temp.append("	    and semeter_for_course.flag_active = '1'  							  ");		
			try {
				List list = this.getGeneralService().getBySQL(sql_temp.toString());
				int order = Integer.parseInt(list.get(0).toString());
				if(order+ids.size()>6){
					this.setMsg("预约失败,您已预约了"+order+"门课程,最多还能预约"+(6-order)+"门课程");
					return "msg";
				}
			} catch (EntityException e) {
				e.printStackTrace();
				this.setMsg("操作失败");
				return "msg";
			}
			
		}
			
			String temp = this.convertIds(ids);
			StringBuffer courseNames = new StringBuffer();
			StringBuffer studentNames = new StringBuffer();
			if(isOpenCourse(temp, courseNames)){
				this.setMsg( "预约考试失败<br/>课程“" + courseNames.toString() + "”在本学期没有开课，暂不能为其预约考试");
				return "msg";
			}
			if(isConflictToChoose(temp, studentNames)){
				this.setMsg("预约考试失败<br/>学生所预约的考试课程最多只能有两门课程同属于一个考试场次，<br/>而为您所预约的考试存在多个这样的冲突");
				return "msg";
			}
			if(isConflictToAll(temp, studentNames)){
				this.setMsg("预约考试失败<br/>学生所预约的考试课程最多只能有两门课程同属于一个考试场次，<br/>而本次所预约的考试课程与您在本学期已经预约过的考试课程存在多个这样的冲突");
				return "msg";
			}
			
			try{
				this.getPrExamBookingService().save_BookingData(ids);
				this.setMsg( "预约考试成功");
			}catch(Exception e){
				e.printStackTrace();
				this.setMsg(e.getMessage());
			}
			System.out.println(this.getIds().length());
			return "msg";
	}
	
	/**
	 * 设置学生可以预约的课程
	 */
	private void theCourses() {
		if (this.getUserPeStudentId() !=null) {

		/**
		 * 查询条件：未参加过预约考试的（学生）课程列表，曾参加过考试，但考试没过（缺考、违纪、作弊或考试不及格）并且在当前考试学期还没有预约考试的（学生）课程列表
		 */
//		String sql = " select elective.id                    as id                           " + 
////		"        peStudent.True_Name            as student_name,                 " +
////		"        peStudent.Reg_No               as reg_no,                       " +
////		"        peTchCourse.Name               as course_name,                  " +
////		"        prTchOpencourse.Advice_Exam_No as examno,                       " +
////		"        peSite.Name                    as site_name,                    " +
////		"        peEdutype.Name                 as edutype_name,                 " +
////		"        peMajor.Name                   as major_name,                   " +
////		"        peGrade.Name                   as grade_name                    " +
//		"   from pr_tch_stu_elective   elective,                                 " +
//		"        pe_student            peStudent,                                " +
//		"        pr_tch_program_course programcourse,                            " +
//		"        pe_tch_course         peTchCourse,                              " +
//		"        pr_tch_opencourse     prTchOpencourse,                          " +
//		"        pe_semester           semester,                                 " +
//		"        enum_const			   enum_stu_status,                          " +
//		"        enum_const            enumAdmission,                            " +
//		"        enum_const            enumScoreStatus                           " +
//		"  where elective.fk_stu_id = peStudent.id                               " +
//		// =============以下一行是添加当前用户的id
//		"    and peStudent.id = '" + this.getUserPeStudentId() + "'              " +  		
//		// ================		"    and peStudent.flag_student_status = enum_stu_status.id              " +
//		"    and enum_stu_status.code = '4'                                      " +
//		"    and prTchOpencourse.Fk_Course_Id(+) = peTchCourse.Id                " +
//		"    and prTchOpencourse.Fk_Semester_Id = semester.id                    " +
////		"    and semester.flag_active = '1'                                      " +
//		"    and elective.fk_tch_program_course = programcourse.id               " +
//		"    and programcourse.fk_course_id = peTchCourse.id                     " +
//		"    and elective.flag_elective_admission = enumAdmission.id             " +
//		"    and enumAdmission.code = '1'                                        " +
//		"    and elective.flag_score_status = enumScoreStatus.id                 " +
////		"    and (enumScoreStatus.code != '1' or elective.score_total < 60.0)    " + 
//		"    and (enumScoreStatus.code != '1' or elective.score_total < " + 
//		this.getMyListService().getSysValueByName("creditMustScore") + " " +
//		"	 or elective.score_total is null)    " + 
//		"     and peTchCourse.Score_Exam_Rate != 0								"+
////		"    and elective.id not in                                              " +
//		"   minus   select booking.fk_tch_stu_elective_id                          " +
//		"           from pr_exam_booking booking, pe_semester semester           " +
//		"          where booking.fk_semester_id = semester.id                    " +
//		"            and semester.flag_active = '1'                            " ;
		StringBuffer sql_temp = new StringBuffer();
		sql_temp.append("          select elective.id                    as id,													");	
		sql_temp.append("                 peTchCourse.code							  as code,                      ");                 
		sql_temp.append("                 peTchCourse.Name               as course_name,                ");        
		sql_temp.append("                 prTchOpencourse.Advice_Exam_No as examno                      ");           
		sql_temp.append("            from pr_tch_stu_elective   elective,                               ");        
		sql_temp.append("                 pe_student            peStudent,                              ");        
		sql_temp.append("                 pr_tch_program_course programcourse,                          ");        
		sql_temp.append("                 pe_tch_course         peTchCourse,                            ");        
		sql_temp.append("                 pr_tch_opencourse     prTchOpencourse,                        ");        
		sql_temp.append("                 pe_semester           semester,                               ");        
		sql_temp.append("                 enum_const            enum_stu_status,                        ");        
		sql_temp.append("                 enum_const            enumAdmission,                          ");        
		sql_temp.append("                 enum_const            enumScoreStatus                         ");        
		sql_temp.append("           where elective.fk_stu_id = peStudent.id                             ");
		sql_temp.append("    		  and peStudent.id = '" + this.getUserPeStudentId() + "'            ");
		sql_temp.append("             and peStudent.flag_student_status = enum_stu_status.id            ");        
		sql_temp.append("             and enum_stu_status.code = '4'                                    ");        
		sql_temp.append("             and prTchOpencourse.Fk_Course_Id(+) = peTchCourse.Id              ");        
		sql_temp.append("             and prTchOpencourse.Fk_Semester_Id = semester.id                  ");        
		sql_temp.append("                and semester.flag_active = '1'                                 ");        
		sql_temp.append("             and elective.fk_tch_program_course = programcourse.id             ");        
		sql_temp.append("             and programcourse.fk_course_id = peTchCourse.id                   ");        
		sql_temp.append("             and elective.flag_elective_admission = enumAdmission.id           ");        
		sql_temp.append("             and enumAdmission.code = '1'                                      ");        
		sql_temp.append("             and elective.flag_score_status = enumScoreStatus.id               ");        
		sql_temp.append("             and ((enumScoreStatus.code != '1' and enumScoreStatus.code != '5')  or elective.score_total < "+this.getMyListService().getSysValueByName("creditMustScore"));         
		sql_temp.append("              or         elective.score_total is null)                         ");        
		sql_temp.append("             and peTchCourse.Score_Exam_Rate != 0                              ");        
		sql_temp.append("          minus                                                                ");        
		sql_temp.append("          select elective.id,                                                  ");
		sql_temp.append("                 peTchCourse.code							  as code,                      ");                                                                               
		sql_temp.append("                 peTchCourse.Name,                                             ");        
		sql_temp.append("                 prTchOpencourse.Advice_Exam_No                                ");                        
		sql_temp.append("            from pr_exam_booking       booking,                                ");        
		sql_temp.append("                 pe_semester           semester,                               ");        
		sql_temp.append("                 pr_tch_stu_elective   elective,                               ");        
		sql_temp.append("                 pe_student            peStudent,                              ");        
		sql_temp.append("                 pr_tch_program_course programcourse,                          ");        
		sql_temp.append("                 pe_tch_course         peTchCourse,                            ");        
		sql_temp.append("                 pr_tch_opencourse     prTchOpencourse                         ");        
		sql_temp.append("           where booking.fk_semester_id = semester.id                          ");        
		sql_temp.append("             and semester.flag_active = '1'                                    ");        
		sql_temp.append("             and booking.fk_tch_stu_elective_id = elective.id                  "); 
		sql_temp.append("    		  and peStudent.id = '" + this.getUserPeStudentId() + "'            ");
		sql_temp.append("             and elective.fk_stu_id = peStudent.Id                             ");        
		sql_temp.append("                and prTchOpencourse.Fk_Course_Id(+) = peTchCourse.Id           ");        
		sql_temp.append("             and prTchOpencourse.Fk_Semester_Id = semester.id                  ");        
		sql_temp.append("             and elective.fk_tch_program_course=programcourse.id               ");        
		sql_temp.append("             and programcourse.fk_course_id= peTchCourse.Id                    ");        
		String sql=sql_temp.toString();
		try {
//			List<String> listIds = this.getGeneralService().getBySQL(sql);
//			if (listIds!=null&&listIds.size()>0) {
//			List<PrTchStuElective> list = this.getMyListService().getByIds(PrTchStuElective.class, listIds);
//			if (list!=null) {
//				this.setCourseList(list);
//			}
//			}
			List<Object[]> list = this.getGeneralService().getBySQL(sql);
			if(list!=null){
				this.setCourseList(list);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
	}

	/**
	 * 取得用户的PeStudentId
	 * @return
	 */
	private String getUserPeStudentId() {
		UserSession userSession = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		if (userSession==null) {
			return null;
		}
		DetachedCriteria dc = DetachedCriteria.forClass(PeStudent.class);
		dc.add(Restrictions.eq("ssoUser", userSession.getSsoUser()));
		List student = null;
		try {
			student = this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		if(student != null && !student.isEmpty()) {
			PeStudent stu = (PeStudent)student.get(0);
			return stu.getId();
		}
		return null;
	}
	
	/**
	 * 保存当前学期信息
	 */
	private void theSemester() {
		DetachedCriteria dc =DetachedCriteria.forClass(PeSemester.class);
		dc.add(Restrictions.eq("flagActive", "1"));
		List<PeSemester> list = null;
		try {
			list = this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		this.setPeSemester(list.get(0));
	}
	
	
	/**
	 * 判断是否在申请时间之内
	 */
	private void checkApplyDate() {
		this.setCheckDate(false);
		try {
		Date now = new Date();
		Date start = this.getPeSemester().getExamBookingStartDate();
		Date end = this.getPeSemester().getExamBookingEndDate();
		if (now.after(start)&&Const.compareDate(now, end)) {
			this.setCheckDate(true);
		} 
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	@Override
	public void setEntityClass() {
		this.entityClass = PrTchStuElective.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/workspaceStudent/prExamReserver";
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
	
	
	public List getCourseList() {
		return courseList;
	}

	public void setCourseList(List courseList) {
		this.courseList = courseList;
	}
	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	@Override
	public void initGrid() {
		// TODO Auto-generated method stub
		
	}

}
