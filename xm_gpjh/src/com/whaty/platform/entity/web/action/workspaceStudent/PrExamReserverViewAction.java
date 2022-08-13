package com.whaty.platform.entity.web.action.workspaceStudent;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeSemester;
import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.bean.PrExamBooking;
import com.whaty.platform.entity.bean.PrTchStuElective;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.exam.finalExam.PrExamBookingService;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;
/**
 * 学生工作室 考试预约查看
 * @author 李冰
 *
 */
public class PrExamReserverViewAction extends MyBaseAction {
	private List courseList; //保存学生可预约考试的课程
	private PeSemester peSemester;//保存当前学期信息
	private boolean checkDate; //判断是否在操作时间之内
	private String id;//取消预约的id


	/**
	 * 转向预约查询页面
	 * @return
	 */
	public String courseList() {
		if (this.getUserPeStudentId()==null) {
			this.setMsg("无法取得您的信息请重新登录");
			return "msg";
		}
		
		this.theCourses();
		this.theSemester();
		this.checkApplyDate();
		return "viewExamCourseList";
	}
	
	/**
	 * 学生取消预约操作
	 * @return
	 */
	public String cancelReserver() {
		this.setTogo("/entity/workspaceStudent/prExamReserverView_courseList.action");
		if (this.getId()!=null){
			try {
				this.getGeneralService().delete(this.getMyListService().getById(PrExamBooking.class, this.getId()));
				this.setMsg("取消预约操作成功");
				return "msg";
			} catch (EntityException e) {
				e.printStackTrace();
			}
		}
		this.setMsg("取消预约操作失败");
		return "msg";
	}
	/**
	 * 设置学生预约的课程
	 */
	private void theCourses() {
		if (this.getUserPeStudentId() !=null) {

		/**
		 * 查询条件：未参加过预约考试的（学生）课程列表，曾参加过考试，但考试没过（缺考、违纪、作弊或考试不及格）并且在当前考试学期还没有预约考试的（学生）课程列表
		 */
//			String sql = " select booking.id as id                                             " + 
////			"        peStudent.True_Name as student_name,                               " +
////			"        peStudent.Reg_No as reg_no,                                   " +
////			"        peTchCourse.Name as course_name,                              " +
////			"        prTchOpencourse.Advice_Exam_No as exam_no,                    " +
////			"        peSite.Name as site_name,                                     " +
////			"        peEdutype.Name as edutype_name,                               " +
////			"        peMajor.Name as major_name,                                   " +
////			"        peGrade.Name as grade_name,                                   " +
////			"        to_char(booking.booking_date, 'yyyy-MM-dd') as booking_date   " +
//			"   from pr_exam_booking       booking,                                " +
//			"        pe_semester           semester,                               " +
//			"        pr_tch_stu_elective   elective,                               " +
//			"        pe_student            peStudent,                              " +
//			"        pe_site               peSite,                                 " +
//			"        pe_edutype            peEdutype,                              " +
//			"        pe_major              peMajor,                                " +
//			"        pe_grade              peGrade,                                " +
//			"        pr_tch_program_course programcourse,                          " +
//			"        pe_tch_course         peTchCourse,                            " +
//			"        pr_tch_opencourse     prTchOpencourse                        " +
////			"        pe_semester           semeter_for_course                      " +
//			"  where booking.fk_semester_id = semester.id                          " +
//			"    and booking.fk_tch_stu_elective_id = elective.id                  " +
//			"    and elective.fk_stu_id = peStudent.id                             " +
//			"    and peStudent.Fk_Site_Id = peSite.id                              " +
//			// =============以下一行是添加当前用户的id
//			"    and peStudent.id = '" + this.getUserPeStudentId() + "'            " +  		
//			// ================		
//			"    and peStudent.Fk_Major_Id = peMajor.Id                            " +
//			"    and peStudent.Fk_Grade_Id = peGrade.Id                            " +
//			"    and peStudent.Fk_Edutype_Id = peEdutype.Id                        " +
//			"    and elective.fk_tch_program_course = programcourse.id             " +
//			"    and programcourse.fk_course_id = peTchCourse.Id                   " +
//			"    and prTchOpencourse.Fk_Course_Id = peTchCourse.Id                 " +
//			"    and semester.flag_active = '1'                ";
////			"    and prTchOpencourse.Fk_Semester_Id = semeter_for_course.id        " +
////			"    and semeter_for_course.flag_active = '1'                          " ;
			StringBuffer sql_temp = new StringBuffer();
			sql_temp.append("   select booking.id as id,																								");		                                      
			sql_temp.append("          peTchCourse.code as code,                                        ");
			sql_temp.append("	        peTchCourse.Name as course_name,                                  ");
			sql_temp.append("	        prTchOpencourse.Advice_Exam_No as exam_no,                         ");         
			sql_temp.append("	        to_char(booking.booking_date, 'yyyy-MM-dd') as booking_date       ");
			sql_temp.append("	   from pr_exam_booking       booking,                                    ");
			sql_temp.append("	        pe_semester           semester,                                   ");
			sql_temp.append("	        pr_tch_stu_elective   elective,                                   ");
			sql_temp.append("	        pe_student            peStudent,                                  ");                         
			sql_temp.append("	        pr_tch_program_course programcourse,                              ");
			sql_temp.append("	        pe_tch_course         peTchCourse,                                ");
			sql_temp.append("	        pr_tch_opencourse     prTchOpencourse,                            ");
			sql_temp.append("	        pe_semester           semeter_for_course                          ");
			sql_temp.append("	  where booking.fk_semester_id = semester.id                              ");
			sql_temp.append("    and semester.flag_active = '1'                                    " );
			sql_temp.append("	    and booking.fk_tch_stu_elective_id = elective.id                      ");
			sql_temp.append("    and peStudent.id = '" + this.getUserPeStudentId() + "'            " );
			sql_temp.append("	    and elective.fk_stu_id = peStudent.id                                 ");               
			sql_temp.append("	    and elective.fk_tch_program_course = programcourse.id                 ");
			sql_temp.append("	    and programcourse.fk_course_id = peTchCourse.Id                       ");
			sql_temp.append("	    and prTchOpencourse.Fk_Course_Id = peTchCourse.Id                     ");
			sql_temp.append("	    and prTchOpencourse.Fk_Semester_Id = semeter_for_course.id            ");
			sql_temp.append("	    and semeter_for_course.flag_active = '1'  														");					                        
			String sql = sql_temp.toString();
			try {
//			List<String> listIds = this.getGeneralService().getBySQL(sql);
//			if (listIds!=null&&listIds.size()>0) {
//			List<PrExamBooking> list = this.getMyListService().getByIds(PrExamBooking.class, listIds);
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
	public List getCourseList() {
		return courseList;
	}

	public void setCourseList(List courseList) {
		this.courseList = courseList;
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

	@Override
	public void initGrid() {
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
			this.setBean(stu);
			return stu.getId();
		}
		return null;
	}
	/**
	 * 判断学生是否是在籍状态
	 * @return
	 */
	private boolean checkStatus() {
		if (this.getBean().getEnumConstByFlagStudentStatus().getCode().equals("4")) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public void setEntityClass() {
		this.entityClass = PrExamBooking.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/workspaceStudent/prExamReserverView";
	}
	public void setBean(PeStudent bean) {
		super.superSetBean(bean);
	}
	
	public PeStudent getBean() {
		return (PeStudent)super.superGetBean();
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
