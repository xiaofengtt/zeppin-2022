package com.whaty.platform.sso.web.action;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.standard.entity.activity.OracleOpenCourse;
import com.whaty.platform.database.oracle.standard.sso.OracleSsoUserPriv;
import com.whaty.platform.database.oracle.standard.test.exam.OracleBasicSequence;
import com.whaty.platform.entity.PlatformFactory;
import com.whaty.platform.entity.PlatformManage;
import com.whaty.platform.entity.StudentOperationManage;
import com.whaty.platform.entity.TeacherOperationManage;
import com.whaty.platform.entity.activity.OpenCourse;
import com.whaty.platform.entity.basic.Course;
import com.whaty.platform.entity.bean.PeSemester;
import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.bean.PeTchCourse;
import com.whaty.platform.entity.bean.PeTchCourseware;
import com.whaty.platform.entity.bean.PeTeacher;
import com.whaty.platform.entity.bean.PrTchCourseTeacher;
import com.whaty.platform.entity.bean.PrTchOpencourse;
import com.whaty.platform.entity.bean.PrTchOpencourseCourseware;
import com.whaty.platform.entity.bean.PrTchStuElective;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.user.EntityUser;
import com.whaty.platform.entity.user.ManagerPriv;
import com.whaty.platform.entity.user.Student;
import com.whaty.platform.entity.user.StudentPriv;
import com.whaty.platform.entity.user.TeacherPriv;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.interaction.InteractionUserPriv;
import com.whaty.platform.sso.SsoUserPriv;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.test.exam.BasicSequence;
import com.whaty.platform.util.JsonUtil;

public class InteractionAction extends MyBaseAction {

	private String teachclass_id;
	private String course_id;
	private String courseware_id;
	private List coursewwareList;
	
	public List getCoursewwareList() {
		return coursewwareList;
	}

	public void setCoursewwareList(List coursewwareList) {
		this.coursewwareList = coursewwareList;
	}

	private PeSemester getActiveSemester() throws EntityException{
		
		DetachedCriteria peSemesterDC = DetachedCriteria.forClass(PeSemester.class);
		peSemesterDC.add(Restrictions.eq("flagActive", "1"));
		try {
			return (PeSemester) this.getGeneralService().getList(peSemesterDC).get(0);
		} catch (EntityException e) {
			e.printStackTrace();
			throw new EntityException("没有活动的当前学期，请与管理员联系。");
		}
	}	

	/**
	 * 教师进入交流园地
	 */
	/*
	public String InteractioManage() throws PlatformException {

		HttpServletRequest request = ServletActionContext.getRequest();
		
		String courseId = request.getParameter("open_course_id");
		DetachedCriteria dcOpencourse = DetachedCriteria.forClass(PrTchOpencourse.class);
		dcOpencourse.createAlias("peTchCourse", "peTchCourse")
		.createAlias("peSemester", "peSemester");
		dcOpencourse.add(Restrictions.eq("peTchCourse.id", courseId));
		List list = new ArrayList();
		String open_course_id = "";
		try {
			list = this.getGeneralService().getList(dcOpencourse);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		OpenCourse openCourse =  new OracleOpenCourse();
		BasicSequence examSequence = new OracleBasicSequence();
		if(list != null && !list.isEmpty()){
			PrTchOpencourse open = (PrTchOpencourse)list.get(0);
			openCourse.setId(open.getId());
			open_course_id = open.getId();
			openCourse.setCourse(open.getPeTchCourse());
			openCourse.setSemester(open.getPeSemester());
			
			examSequence.setId(open.getAdviceExamNo());
			examSequence.setNote(open.getAdviceExamNo());
			examSequence.setTitle(open.getAdviceExamNo());
			openCourse.setExamSequence(examSequence);
		}
		request.getSession().setAttribute("openCourse", openCourse);
		
		
		String teachclass_id = open_course_id;
		
		UserSession userSession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		SsoUser ssoUser = userSession.getSsoUser();
		
		DetachedCriteria dcTeacher = DetachedCriteria.forClass(PeTeacher.class);
		dcTeacher.createCriteria("ssoUser", "ssoUser");
		dcTeacher.add(Restrictions.eq("ssoUser", ssoUser));
		List teacherList = new ArrayList();
		String teacherId = "";
		try {
			teacherList = this.getGeneralService().getList(dcTeacher);
			PeTeacher peTeacher = (PeTeacher) teacherList.get(0);
			teacherId = peTeacher.getId();
		} catch (EntityException e1) {
			e1.printStackTrace();
		}
		//////////
		PlatformFactory factory=PlatformFactory.getInstance();
		PlatformManage platformManage=factory.createPlatformManage();
		EntityUser euser=platformManage.getEntityUser(teacherId,"teacher");
		
		
		request.getSession().setAttribute("eduplatform_user",euser);
  	  	TeacherPriv teacherPriv=factory.getTeacherPriv(ssoUser.getLoginId());
  	  	request.getSession().setAttribute("eduplatform_priv",teacherPriv);
  	  	request.getSession().removeAttribute("mock_login");
		//////////
		

		Object o2 = request.getSession().getAttribute(
				"teacher_eduplatform_priv");
		TeacherPriv session_teacherPriv;
		if (o2 != null)
			session_teacherPriv = (TeacherPriv) o2;
		else
			session_teacherPriv = (TeacherPriv) (request.getSession()
					.getAttribute("eduplatform_priv"));

		PlatformFactory platformFactory = PlatformFactory.getInstance();
		TeacherOperationManage teacherOperationManage = platformFactory
				.creatTeacherOperationManage(session_teacherPriv);
		InteractionUserPriv interactionUserPriv = teacherOperationManage
				.getInteractionUserPriv(session_teacherPriv.getTeacherId(),
						teachclass_id);
		request.getSession().setAttribute("interactionUserPriv",
				interactionUserPriv);

		request.getSession().setAttribute("userType", "teacher");
		request.getSession().setAttribute("courseware_priv",
				teacherOperationManage.getCoursewareManagerPriv());

		this.setTeachclass_id(teachclass_id);
		this.setCourseware_id(courseware_id);

		return "show_index";
	}
*/

	/**
	 * 学生进入交流园地
	 * 
	 * @return
	 */
	/*
	public String InteractionStuManage() throws PlatformException,
			UnsupportedEncodingException {

		HttpServletRequest request = ServletActionContext.getRequest();
		//////
		UserSession userSession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		SsoUser ssoUser = userSession.getSsoUser();
		
		DetachedCriteria dcStudent = DetachedCriteria.forClass(PeStudent.class);
		dcStudent.createCriteria("ssoUser", "ssoUser");
		dcStudent.add(Restrictions.eq("ssoUser", ssoUser));
		List studentList = new ArrayList();
		String studentId = "";
		try {
			studentList = this.getGeneralService().getList(dcStudent);
			PeStudent peStudent = (PeStudent) studentList.get(0);
			studentId = peStudent.getId();
		} catch (EntityException e1) {
			e1.printStackTrace();
		}
		
		
		//SsoUserPriv ssoUserPriv= new OracleSsoUserPriv(ssoUser.getLoginId());
		PlatformFactory factory=PlatformFactory.getInstance();
		PlatformManage platformManage=factory.createPlatformManage();
		EntityUser euser=platformManage.getEntityUser(studentId,"student");
		
		
		request.getSession().setAttribute("eduplatform_user",euser);
  	  	StudentPriv studentPriv=factory.getStudentPriv(ssoUser.getLoginId());
  	  	request.getSession().setAttribute("eduplatform_priv",studentPriv);
  	  	request.getSession().removeAttribute("mock_login");
		
		///////
		String open_course_id = request.getParameter("open_course_id");
		String teachclass_id = open_course_id;

		Object o1 = request.getSession().getAttribute(
				"student_eduplatform_user");
		Object o2 = request.getSession().getAttribute(
				"student_eduplatform_priv");
		Student session_student;
		
		if (o1 != null)
			session_student = (Student) o1;
		else
			session_student = (Student) (request.getSession()
					.getAttribute("eduplatform_user"));

		if (o2 != null)
			studentPriv = (StudentPriv) o2;
		else
			studentPriv = (StudentPriv) (request.getSession()
					.getAttribute("eduplatform_priv"));



		PlatformFactory platformFactory = PlatformFactory.getInstance();
		StudentOperationManage studentOperationManage = platformFactory
				.creatStudentOperationManage(studentPriv);
//		Course course = (Course) (request.getSession().getAttribute("course"));

		ManagerPriv includePriv = studentOperationManage
				.getNormalEntityManagePriv();

		InteractionUserPriv interactionUserPriv = studentOperationManage
				.getInteractionUserPriv(studentPriv.getStudentId(),
						teachclass_id);
		request.getSession().setAttribute("interactionUserPriv",
				interactionUserPriv);

		// 构造opencourse
		DetachedCriteria dc = DetachedCriteria.forClass(PrTchOpencourse.class)
		.createAlias("peTchCourse", "peTchCourse")
		.createAlias("peSemester", "peSemester");
		dc.add(Restrictions.eq("id", open_course_id));
		List list = new ArrayList();

		try {
			list = this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
//		if (list != null && !list.isEmpty()) {
//			PrTchOpencourse open = (PrTchOpencourse) list.get(0);
//			request.getSession().setAttribute("openCourse", open);
//		}
		
		///////
		OpenCourse openCourse =  new OracleOpenCourse();
		BasicSequence examSequence = new OracleBasicSequence();
		if(list != null && !list.isEmpty()){
			PrTchOpencourse open = (PrTchOpencourse)list.get(0);
			openCourse.setId(open.getId());
			
			openCourse.setCourse(open.getPeTchCourse());
			openCourse.setSemester(open.getPeSemester());
			
			examSequence.setId(open.getAdviceExamNo());
			examSequence.setNote(open.getAdviceExamNo());
			examSequence.setTitle(open.getAdviceExamNo());
			openCourse.setExamSequence(examSequence);
		}
		request.getSession().setAttribute("openCourse", openCourse);
		///////

		request.getSession().setAttribute("userType", "student");
		request.getSession().setAttribute("First", "1");
		//		
		return "show_stuindex";
	}
	*/
	public String InteractioManage() throws PlatformException {
		
		HttpServletRequest request =  ServletActionContext.getRequest();
		
		String course_id = request.getParameter("course_id");
		this.setCourse_id(course_id);
		
		PeSemester peSemester = new PeSemester();
		try {
			peSemester = this.getActiveSemester();
		} catch (EntityException e1) {
			e1.printStackTrace();
			this.setMsg(e1.getMessage());
			return "msg";
		}
		DetachedCriteria prTchOpencourseDC = DetachedCriteria.forClass(PrTchOpencourse.class);
		prTchOpencourseDC.createCriteria("peTchCourse", "peTchCourse");
		prTchOpencourseDC.add(Restrictions.eq("peTchCourse.id", course_id));
		prTchOpencourseDC.add(Restrictions.eq("peSemester", peSemester));
		
		List prTchOpencourseList = new ArrayList();
		PrTchOpencourse prTchOpencourse = new PrTchOpencourse();
		try {
			prTchOpencourseList = this.getGeneralService().getList(prTchOpencourseDC);
			prTchOpencourse = (PrTchOpencourse) prTchOpencourseList.get(0);
		} catch (EntityException e1) {
			this.setMsg("取该课程的开课表记录出错。");
			e1.printStackTrace();
			return "msg";
		}
		
		this.setTeachclass_id(prTchOpencourse.getId());
		
	  	Object o2 = request.getSession().getAttribute("teacher_eduplatform_priv");
		TeacherPriv session_teacherPriv;
		if(o2 != null)
			session_teacherPriv = (TeacherPriv)o2;
		else
			session_teacherPriv = (TeacherPriv)(request.getSession().getAttribute("eduplatform_priv"));
		

		PlatformFactory platformFactory = PlatformFactory.getInstance();
		TeacherOperationManage teacherOperationManage = platformFactory.creatTeacherOperationManage(session_teacherPriv);
		InteractionUserPriv interactionUserPriv = teacherOperationManage.getInteractionUserPriv(session_teacherPriv.getTeacherId(), teachclass_id);
		request.getSession().setAttribute("interactionUserPriv",interactionUserPriv);
		
		//构造opencourse
		
		OpenCourse openCourse =  new OracleOpenCourse();
		 BasicSequence examSequence = new OracleBasicSequence();
		
		openCourse.setId(this.getTeachclass_id());
		
		try {
			openCourse.setCourse(this.getPeCourse(course_id));
		} catch (EntityException e) {
			e.printStackTrace();
		}
		openCourse.setSemester(peSemester);
		
		examSequence.setId("");
		examSequence.setNote("");
		examSequence.setTitle("");
		
		openCourse.setExamSequence(examSequence);
			
		
		request.getSession().setAttribute("courseId", openCourse.getCourse().getId());
		request.getSession().setAttribute("semesterId", peSemester.getId());
		request.getSession().setAttribute("openCourse",openCourse);
		request.getSession().setAttribute("userType", "teacher");
		request.getSession().setAttribute("courseware_priv",teacherOperationManage.getCoursewareManagerPriv());
		
		this.setTeachclass_id(teachclass_id);
			
		return "show_index";
	}
	
	
	public String InteractionStuManage()throws PlatformException,UnsupportedEncodingException{
		
		HttpServletRequest request =  ServletActionContext.getRequest();
		
		course_id = request.getParameter("course_id");
		this.setCourse_id(course_id);
		
		PeSemester peSemester = new PeSemester();
		try {
			peSemester = this.getActiveSemester();
		} catch (EntityException e1) {
			e1.printStackTrace();
			this.setMsg(e1.getMessage());
			return "msg";
		}
		
		DetachedCriteria prTchOpencourseDC = DetachedCriteria.forClass(PrTchOpencourse.class);
		prTchOpencourseDC.createCriteria("peTchCourse", "peTchCourse");
		prTchOpencourseDC.add(Restrictions.eq("peTchCourse.id", course_id));
		prTchOpencourseDC.add(Restrictions.eq("peSemester", peSemester));
		
		List prTchOpencourseList = new ArrayList();
		PrTchOpencourse prTchOpencourse = new PrTchOpencourse();
		try {
			prTchOpencourseList = this.getGeneralService().getList(prTchOpencourseDC);
			prTchOpencourse = (PrTchOpencourse) prTchOpencourseList.get(0);
		} catch (EntityException e1) {
			this.setMsg("取该课程的开课表记录出错。");
			e1.printStackTrace();
			return "msg";
		}
		
		this.setTeachclass_id(prTchOpencourse.getId());
		
		Object o1 = request.getSession().getAttribute("student_eduplatform_user");
		Object o2 = request.getSession().getAttribute("student_eduplatform_priv");
		Student session_student;
		StudentPriv studentPriv;
		if(o1 != null)
			session_student = (Student)o1;
		else
			session_student = (Student)(request.getSession().getAttribute("eduplatform_user"));
			
		if(o2 != null)
			studentPriv = (StudentPriv)o2;
		else
			studentPriv = (StudentPriv)(request.getSession().getAttribute("eduplatform_priv"));

		String student_id = session_student.getId();
		String student_reg_no = session_student.getStudentInfo().getReg_no();
		String student_name = session_student.getName();
		String student_login_id = session_student.getLoginId();

		PlatformFactory platformFactory = PlatformFactory.getInstance();
		StudentOperationManage studentOperationManage = platformFactory.creatStudentOperationManage(studentPriv);

		ManagerPriv includePriv = studentOperationManage.getNormalEntityManagePriv();
		
		InteractionUserPriv interactionUserPriv = studentOperationManage.getInteractionUserPriv(studentPriv.getStudentId(), this.getTeachclass_id());
		request.getSession().setAttribute("interactionUserPriv",interactionUserPriv);
		
		OpenCourse openCourse =  new OracleOpenCourse();
		BasicSequence examSequence = new OracleBasicSequence();
		
		openCourse.setId(this.getTeachclass_id());
		
		try {
			openCourse.setCourse(this.getPeCourse(course_id));
		} catch (EntityException e) {
			e.printStackTrace();
			this.setMsg(e.getMessage());
			return "msg";
		}
		openCourse.setSemester(peSemester);
		request.getSession().setAttribute("courseId", openCourse.getCourse().getId());
		examSequence.setId("");
		examSequence.setNote("");
		examSequence.setTitle("");
		
		openCourse.setExamSequence(examSequence);
			
	//	登录次数
		UserSession userSession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		DetachedCriteria peStudentDC = DetachedCriteria.forClass(PeStudent.class);
		peStudentDC.add(Restrictions.eq("ssoUser", userSession.getSsoUser()));
		List peStudentList = new ArrayList();
		try {
			peStudentList = this.getGeneralService().getList(peStudentDC);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		/*
		PeStudent peStudent = (PeStudent) peStudentList.get(0);
		
		DetachedCriteria electiveDC = DetachedCriteria.forClass(PrTchStuElective.class);
		electiveDC.createCriteria("peCourse", "peCourse");
		electiveDC.add(Restrictions.eq("peStudent", peStudent));
		electiveDC.add(Restrictions.eq("peTchCourse.id", course_id));
		electiveDC.add(Restrictions.eq("peSemester", peSemester));
		List electiveList = new ArrayList();
		try {
			electiveList = this.getGeneralService().getList(electiveDC);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
		if (electiveList != null) {
			PrTchStuElective stuElective = (PrTchStuElective) electiveList.get(0);
			if(stuElective.getEnterTimes == null) {
				stuElective.setEnterTimes(new Long(1));
			} else {
				stuElective.setEnterTimes(stuElective.getEnterTimes() + 1);
			}
			try {
				this.getGeneralService().save(stuElective);
			} catch (EntityException e) {
				e.printStackTrace();
			}
		}*/
	
		request.getSession().setAttribute("openCourse",openCourse);
		request.getSession().setAttribute("userType", "student");
		request.getSession().setAttribute("First", "1");
		
		return "show_stuindex";
	}

	public String getCourseware_id() {
		return courseware_id;
	}

	public void setCourseware_id(String courseware_id) {
		this.courseware_id = courseware_id;
	}

	public String getTeachclass_id() {
		return teachclass_id;
	}

	public void setTeachclass_id(String teachclass_id) {
		this.teachclass_id = teachclass_id;
	}

	public String getcoursewareList() {
		/*HttpServletRequest request = ServletActionContext.getRequest();
		String open_course_id = request.getParameter("open_course_id");
		String course_id = "";

		DetachedCriteria dc = DetachedCriteria
				.forClass(PrTchOpencourseCourseware.class);
		dc.createCriteria("prTchOpencourse","prTchOpencourse");
		dc.add(Restrictions.eq("prTchOpencourse.id", open_course_id));
		List opencourseCoursewareList = new ArrayList();
		try {
			opencourseCoursewareList = this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List list = new ArrayList();
		for (Iterator iter = opencourseCoursewareList.iterator(); iter.hasNext();) {
			PrTchOpencourseCourseware instance = (PrTchOpencourseCourseware) iter.next();
			list.add(instance.getPeTchCourseware());
		}
		Map map = new HashMap();
		map.put("list", list);
		this.setJsonString(JsonUtil.toJSONString(map));
		return "listjson";*/
		
/*		public String getcoursewareList(){
			HttpServletRequest request =  ServletActionContext.getRequest();		
			String open_course_id = request.getParameter("open_course_id");
			String course_id="";
			
			DetachedCriteria dctemp = DetachedCriteria.forClass(PrTchOpencourse.class);
			dctemp.createCriteria("peTchCourse","peTchCourse");
			dctemp.add(Restrictions.eq("id", open_course_id));
			List listtemp = getPrTchOpencourseService().getList(dctemp);
			if(listtemp!=null&&listtemp.size()>0)
				course_id=((PrTchOpencourse)listtemp.get(0)).getPeTchCourse().getId();
			else
				return null;
			
			DetachedCriteria dc = DetachedCriteria.forClass(PeTchCourseware.class);	

			dc.createCriteria("peTchCourse","peTchCourse").add(Restrictions.eq("id", course_id));
			this.setCoursewareList1(getPrTchOpencourseService().getList(dc));
//			Map map = new HashMap();
//			map.put("list", list);
//			this.setJsonString(JsonUtil.toJSONString(map));
			return "KJLL";
		}
*/		
		HttpServletRequest request =  ServletActionContext.getRequest();
		course_id = request.getParameter("course_id");
		DetachedCriteria prTchOpencourseCoursewareDC = DetachedCriteria.forClass(PrTchOpencourseCourseware.class);
		prTchOpencourseCoursewareDC.createCriteria("prTchOpencourse", "prTchOpencourse").createCriteria("peTchCourse", "peTchCourse");
		prTchOpencourseCoursewareDC.add(Restrictions.eq("peTchCourse.id", course_id));
		
		try {
			this.setCoursewwareList(this.getGeneralService().getList(prTchOpencourseCoursewareDC));
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
		return "KJLL";
	}

	@Override
	public void initGrid() {
	}

	@Override
	public void setEntityClass() {
	}

	@Override
	public void setServletPath() {
	}

	public String getCourse_id() {
		return course_id;
	}

	public void setCourse_id(String course_id) {
		this.course_id = course_id;
	}
	
	private PeTchCourse getPeCourse(String courseId) throws EntityException {
		
		DetachedCriteria peCourseDC = DetachedCriteria.forClass(PeTchCourse.class);
		peCourseDC.add(Restrictions.eq("id", courseId));
		try {
			return (PeTchCourse) this.getGeneralService().getList(peCourseDC).get(0);
		} catch (EntityException e) {
			e.printStackTrace();
			throw new EntityException("没有所选的课程");
		}
	}

}
