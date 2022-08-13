package com.whaty.platform.entity.web.action.workspaceStudent;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeSemester;
import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.bean.PrTchProgramCourse;
import com.whaty.platform.entity.bean.SystemApply;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;

/**
 * 免修申请
 * @author 李冰
 *
 */
public class ExamAvoidAction extends MyBaseAction {
	private List courseList; //保存学生可申请的课程
	private String tchProgramId;; //学生所申请的课程 教学计划ID
	private List applyCourse; //保存学生已经申请过的课程
	private PeSemester peSemester;//保存当前学期信息
	boolean checkDate; //判断是否在申请时间之内

	/**
	 * 转向免修申请页面
	 * @return
	 */
	public String toAvoidExam() {
		if (this.getLoginStudent()==null) {
			this.setMsg("无法取得您的信息请重新登录");
			return "msg";
		}
		if (!this.checkStatus()) {
			this.setMsg("您的学籍状态不是在籍状态，无操作权限");
			return "msg";
		}
		this.theSemester();
		this.checkApplyDate();
		List<String> theOpenCourse = this.theOpenCourse();
		List<String> theStuElective = this.theStuElective();
		List<String> applyCourse = this.applyCourse();
		this.setCourseList(this.theTchProgram(theOpenCourse, theStuElective , applyCourse));
		
		this.alreadyApply();
		System.out.println(theOpenCourse.size());
		return "avoidExam";
	}
	
	/**
	 * 免修申请处理页面
	 * @return
	 */
	public String avoid() {
		if (this.getLoginStudent()==null) {
			this.setMsg("无法取得您的信息请重新登录");
			return "msg";
		}
		
		this.setTogo("/entity/workspaceStudent/examAvoid_toAvoidExam.action");
		
		PrTchProgramCourse program = null;
		try {
			program = (PrTchProgramCourse)this.getMyListService().getById(PrTchProgramCourse.class, this.getTchProgramId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (program == null) {
			this.setMsg("无法取得所申请的课程信息，请重新选择！");
			return "msg";
		}
		
		List<String> theStuElective = this.theStuElective();
		
		if (theStuElective!=null && theStuElective.size()>0) {
			if (theStuElective.contains(program.getPeTchCourse().getId())) {
				this.setMsg("您已经选过该课程，请重新选择！");
				return "msg";
			}
		}
		
		DetachedCriteria dc = DetachedCriteria.forClass(SystemApply.class);
		dc.createAlias("enumConstByApplyType", "enumConstByApplyType");
		dc.add(Restrictions.eq("peStudent", this.getBean()));
		dc.add(Restrictions.eq("enumConstByApplyType.code", "15"));
		dc.add(Restrictions.like("applyNote", program.getId()+",", MatchMode.ANYWHERE));
		
		try {
			List apply = this.getGeneralService().getList(dc);
			if (apply != null && apply.size()>0) {
				this.setMsg("您已经申请过该课程，请重新选择！");
				return "msg";
			}
		} catch (EntityException e1) {
			e1.printStackTrace();
		}
		
		SystemApply systemApply = new SystemApply();
		systemApply.setPeStudent(this.getBean());
		systemApply.setApplyDate(new Date());
		systemApply.setApplyNote(program.getId() + "," + program.getPeTchCourse().getName());
		systemApply.setEnumConstByApplyType(
				this.getMyListService().getEnumConstByNamespaceCode("ApplyType", "15"));
		systemApply.setEnumConstByFlagApplyStatus(
				this.getMyListService().getEnumConstByNamespaceCode("FlagApplyStatus", "0"));
		try {
			this.getGeneralService().save(systemApply);
			this.setMsg("申请成功："+program.getPeTchCourse().getName());
		} catch (EntityException e) {
			e.printStackTrace();
			this.setMsg("申请失败："+program.getPeTchCourse().getName());
		}

		return "msg";
	}
	
	/**
	 * 取得学生的教学计划课程
	 * @param list 学生不能申请的课
	 * @return
	 */
	private List<PrTchProgramCourse> theTchProgram(List<String> theOpenCourse,List<String> theStuElective,List<String> applyCourse) {
		
		DetachedCriteria dc = DetachedCriteria.forClass(PrTchProgramCourse.class);
		
		DetachedCriteria dcPeTchCourse = dc.createCriteria("peTchCourse","peTchCourse");
		dc.createCriteria("peTchProgramGroup","peTchProgramGroup")
			.createCriteria("peTchProgram", "peTchProgram")
			.add(Restrictions.eq("peGrade", this.getBean().getPeGrade()))
			.add(Restrictions.eq("peMajor", this.getBean().getPeMajor()))
			.add(Restrictions.eq("peEdutype", this.getBean().getPeEdutype()))
			.add(Restrictions.eq("enumConstByFlagMajorType", this.getBean().getEnumConstByFlagMajorType()));
		if (applyCourse!=null&&applyCourse.size()>0) {
			dc.add(Restrictions.not(Restrictions.in("id", applyCourse)));
		}
	/**
	 * 可以免考的范围定义为教学计划中的且还没有选过的课程,因此不必判断下学期的开课表
	 */	
//		if (theOpenCourse==null || theOpenCourse.size()==0) {
//			return null;
//		}
//		if (theStuElective!=null && theStuElective.size()>0) {
//			dcPeTchCourse.add(Restrictions.and(
//					Restrictions.not(Restrictions.in("id", theStuElective)),
//					Restrictions.in("id", theOpenCourse)));
//		} else {
//			dcPeTchCourse.add(Restrictions.in("id", theOpenCourse));
//		}
		if (theStuElective!=null && theStuElective.size()>0) {
			dcPeTchCourse.add(Restrictions.not(Restrictions.in("id", theStuElective)));
		} 
		dc.addOrder(Order.asc("peTchProgramGroup"));
		List<PrTchProgramCourse> prTchProgramCourseList = null;
		try {
			prTchProgramCourseList =this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return prTchProgramCourseList;
	}
	
	/**
	 * 取得下学期开课的课程
	 * @return
	 */
	private List<String> theOpenCourse() {
		String sql = " select opencourse.fk_course_id															"	
		+ "   from pr_tch_opencourse opencourse, pe_semester semester   "
		+ "  where opencourse.fk_semester_id = semester.id              "
		+ "    and semester.flag_next_active = '1'                      ";
		List<String> list = null;
		try {
			list = this.getGeneralService().getBySQL(sql);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 判断是否在申请时间之内
	 */
	private void checkApplyDate() {
		if (this.getPeSemester().getElectiveStartDate()==null||
				this.getPeSemester().getElectiveEndDate()==null) {
			this.setCheckDate(false);
		} else {
		Date now = new Date();
		Date start = this.getPeSemester().getElectiveStartDate();
		Date end = this.getPeSemester().getElectiveEndDate();
		if (now.after(start)&&Const.compareDate(now, end)) {
			this.setCheckDate(true);
		} else {
			this.setCheckDate(false);
		}
		}
	}
	
	/**
	 * 这个学生已经选过的课程
	 * @return
	 */
	private List<String> theStuElective() {
		String sql = "  select course.id																						"	
		+ "    from pr_tch_stu_elective elective,                       "
		+ "         pe_student          student,                        "
		+ "         pr_tch_opencourse   opencourse,                     "
		+ "         pe_tch_course       course                          "
		+ "   where elective.fk_tch_opencourse_id = opencourse.id       "
		+ "     and opencourse.fk_course_id = course.id                 "
		+ "     and elective.fk_stu_id = student.id                     "
		+ "     and student.id = " + "'" + this.getBean().getId() + "'";
		List<String> list = null;
		try {
			list = this.getGeneralService().getBySQL(sql);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 这个学生已经申请过的课程的教学计划Id
	 * @return
	 */
	private List<String> applyCourse() {
		DetachedCriteria dc = DetachedCriteria.forClass(SystemApply.class);
		dc.createAlias("enumConstByApplyType", "enumConstByApplyType");
		dc.add(Restrictions.eq("peStudent", this.getBean()));
		dc.add(Restrictions.eq("enumConstByApplyType.code", "15"));
		List<String> list = new ArrayList<String>();
		List<SystemApply> apply = null;
		try {
		 apply = this.getGeneralService().getList(dc);
			if (apply != null && apply.size()>0) {
				for (SystemApply systemApply : apply) {
					String note = systemApply.getApplyNote();
					if (note!=null&& note.length()>0){
						String[] strs = note.split(",");
						if (strs.length>0) {
							list.add(strs[0].trim());
						}
					}
				}
				
			}
		} catch (EntityException e1) {
			e1.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 设置学生已经申请过的课程
	 */
	private void alreadyApply() {
		DetachedCriteria dc = DetachedCriteria.forClass(SystemApply.class);
		dc.createAlias("enumConstByApplyType", "enumConstByApplyType");
		dc.add(Restrictions.eq("peStudent", this.getBean()));
		dc.add(Restrictions.eq("enumConstByApplyType.code", "15"));
		List list = new ArrayList();
		List<SystemApply> apply = null;
		try {
		 apply = this.getGeneralService().getList(dc);
			if (apply != null && apply.size()>0) {
				for (SystemApply systemApply : apply) {
					String note = systemApply.getApplyNote();
					if (note!=null&& note.length()>0){
						String[] strs = note.split(",");
						if (strs.length>0) {
							PrTchProgramCourse program = null;
							program = (PrTchProgramCourse)this.getMyListService()
								.getById(PrTchProgramCourse.class, strs[0].trim());
							if (program!=null) {
//								program.setId(systemApply.getEnumConstByFlagApplyStatus().getName());
								list.add(program);
							}
						}
					}
				}
				
			}
		} catch (EntityException e1) {
			e1.printStackTrace();
		}
		this.setApplyCourse(list);
	}
	
	/**
	 * 取得审核状态
	 * @param courseId
	 * @return
	 */
	public String applyCheckType(String courseId){
		DetachedCriteria dc = DetachedCriteria.forClass(SystemApply.class);
		dc.createAlias("enumConstByApplyType", "enumConstByApplyType");
		dc.add(Restrictions.eq("peStudent", this.getBean()));
		dc.add(Restrictions.eq("enumConstByApplyType.code", "15"));
		dc.add(Restrictions.like("applyNote", courseId, MatchMode.START));
		List<SystemApply> apply = null;
		try {
		 apply = this.getGeneralService().getList(dc);
			if (apply != null && apply.size()>0) {
				return apply.get(0).getEnumConstByFlagApplyStatus().getName();
			}
		} catch (EntityException e1) {
			e1.printStackTrace();
		}
		return "";
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
	
	public List getApplyCourse() {
		return applyCourse;
	}

	public void setApplyCourse(List applyCourse) {
		this.applyCourse = applyCourse;
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
	
	//取得当前登陆学生peStudent对象
	private PeStudent getLoginStudent(){
		UserSession userSession = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		if (userSession==null) {
			return null;
		}
		DetachedCriteria dc = DetachedCriteria.forClass(PeStudent.class);
		dc.createAlias("peGrade", "peGrade").createAlias("peMajor", "peMajor").createAlias("peEdutype", "peEdutype");
		dc.add(Restrictions.eq("ssoUser", userSession.getSsoUser()));
		List<PeStudent> student = null;
		try {
			student = this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		if(student != null && !student.isEmpty()) {
			this.setBean(student.get(0));
			return (PeStudent)student.get(0);
		}
		return null;
	}
	
	@Override
	public void initGrid() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setEntityClass() {
		this.entityClass = PeStudent.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/workspaceStudent/examAvoid.action";
	}
	
	public void setBean(PeStudent bean) {
		super.superSetBean(bean);
	}
	
	public PeStudent getBean() {
		return (PeStudent)super.superGetBean();
	}


	public List getCourseList() {
		return courseList;
	}


	public void setCourseList(List courseList) {
		this.courseList = courseList;
	}

	public String getTchProgramId() {
		return tchProgramId;
	}

	public void setTchProgramId(String tchProgramId) {
		this.tchProgramId = tchProgramId;
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
	

}
