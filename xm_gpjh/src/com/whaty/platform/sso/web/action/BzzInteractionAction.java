package com.whaty.platform.sso.web.action;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.standard.entity.activity.OracleOpenCourse;
import com.whaty.platform.database.oracle.standard.entity.user.OracleStudentPriv;
import com.whaty.platform.entity.PlatformFactory;
import com.whaty.platform.entity.PlatformManage;
import com.whaty.platform.entity.StudentOperationManage;
import com.whaty.platform.entity.TeacherOperationManage;
import com.whaty.platform.entity.activity.OpenCourse;
import com.whaty.platform.entity.bean.PeBzzTchCourse;
import com.whaty.platform.entity.bean.PeTeacher;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.user.EntityUser;
import com.whaty.platform.entity.user.ManagerPriv;
import com.whaty.platform.entity.user.Student;
import com.whaty.platform.entity.user.StudentPriv;
import com.whaty.platform.entity.user.TeacherPriv;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.interaction.InteractionUserPriv;
import com.whaty.platform.sso.web.servlet.UserSession;

/**
 * @param
 * @version 创建时间：2009-7-1 下午01:35:21
 * @return
 * @throws PlatformException
 * 类说明
 */
public class BzzInteractionAction extends MyBaseAction {
	
	private String course_id;
	private String teacher_id;
	private String teachclass_id;
	private String opencourseId;   //进入课件学习使用
	private String firstPage;  //进入学习首个页面

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
	
	public String InteractioManage() throws PlatformException {
		
		HttpServletRequest request =  ServletActionContext.getRequest();
		
		UserSession userSession = (UserSession) ActionContext.getContext()
		.getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		if(userSession==null)
		{
			return "input";
		}
		
		String course_id = request.getParameter("course_id");
		String teacher_id = request.getParameter("teacher_id");
		this.setCourse_id(course_id);
		this.setTeacher_id(teacher_id);
		
		PeTeacher peTeacher=new PeTeacher();
		try {
			peTeacher=this.getTeacher();
		} catch (EntityException e1) {
			e1.printStackTrace();
		}
		
		try {
			this.setM4Session(peTeacher.getSsoUser(), "1","teacher");
		} catch (PlatformException e) {
			e.printStackTrace();
		}
		
		this.setTeachclass_id(course_id);
		
		
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
		// BasicSequence examSequence = new OracleBasicSequence();
		
		openCourse.setId(this.getTeachclass_id());
		
		try {
			openCourse.setBzzCourse(this.getPeBzzCourse(course_id));
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
		request.getSession().setAttribute("courseId", course_id);
		request.getSession().setAttribute("openCourse",openCourse);
		request.getSession().setAttribute("userType", "teacher");
		request.getSession().setAttribute("courseware_priv",teacherOperationManage.getCoursewareManagerPriv());
		
		this.setTeachclass_id(teachclass_id);
		
		return "show_index";
	}
	

	public String InteractionStuManage() throws PlatformException,
			UnsupportedEncodingException {

		HttpServletRequest request = ServletActionContext.getRequest();
		
		UserSession userSession = (UserSession) ActionContext.getContext()
		.getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		if(userSession==null)
		{
			return "input";
		}

		course_id = request.getParameter("course_id");
		opencourseId= request.getParameter("opencourseId");
		firstPage=request.getParameter("firstPage");
		this.setCourse_id(course_id);
		this.setOpencourseId(opencourseId);
		this.setFirstPage(firstPage);

		this.setTeachclass_id(course_id);
		
		try {
			this.setM4Session(userSession.getSsoUser(), "0","student");
		} catch (PlatformException e) {
			e.printStackTrace();
		}
		//System.out.println("id:"+userSession.getSsoUser().getId());

		Object o1 = request.getSession().getAttribute(
				"student_eduplatform_user");
		Object o2 = request.getSession().getAttribute(
				"student_eduplatform_priv");
		Student session_student;
		StudentPriv studentPriv;
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

		ManagerPriv includePriv = studentOperationManage
				.getNormalEntityManagePriv();

		InteractionUserPriv interactionUserPriv = studentOperationManage
				.getInteractionUserPriv(studentPriv.getStudentId(), this
						.getTeachclass_id());
		request.getSession().setAttribute("interactionUserPriv",
				interactionUserPriv);

		OpenCourse openCourse = new OracleOpenCourse();

		openCourse.setId(this.getTeachclass_id());

		try {
			openCourse.setBzzCourse(this.getPeBzzCourse(course_id));
		} catch (EntityException e) {
			e.printStackTrace();
			this.setMsg(e.getMessage());
			return "msg";
		}
		request.getSession().removeAttribute("courseId");
		request.getSession().setAttribute("courseId", course_id);

		// 登录次数
//		DetachedCriteria peStudentDC = DetachedCriteria
//				.forClass(PeBzzStudent.class);
//		peStudentDC.add(Restrictions.eq("ssoUser", userSession.getSsoUser()));
//		List peStudentList = new ArrayList();
//		try {
//			peStudentList = this.getGeneralService().getList(peStudentDC);
//		} catch (EntityException e) {
//			e.printStackTrace();
//		}
		/*
		 * PeStudent peStudent = (PeStudent) peStudentList.get(0);
		 * 
		 * DetachedCriteria electiveDC =
		 * DetachedCriteria.forClass(PrTchStuElective.class);
		 * electiveDC.createCriteria("peCourse", "peCourse");
		 * electiveDC.add(Restrictions.eq("peStudent", peStudent));
		 * electiveDC.add(Restrictions.eq("peTchCourse.id", course_id));
		 * electiveDC.add(Restrictions.eq("peSemester", peSemester)); List
		 * electiveList = new ArrayList(); try { electiveList =
		 * this.getGeneralService().getList(electiveDC); } catch
		 * (EntityException e) { e.printStackTrace(); }
		 * 
		 * if (electiveList != null) { PrTchStuElective stuElective =
		 * (PrTchStuElective) electiveList.get(0); if(stuElective.getEnterTimes ==
		 * null) { stuElective.setEnterTimes(new Long(1)); } else {
		 * stuElective.setEnterTimes(stuElective.getEnterTimes() + 1); } try {
		 * this.getGeneralService().save(stuElective); } catch (EntityException
		 * e) { e.printStackTrace(); } }
		 */
		
		//统计时段开始计时
		//Date now  = new Date();
		
		request.getSession().removeAttribute("openCourse");
		request.getSession().setAttribute("openCourse", openCourse);
		//request.getSession().setAttribute("now", now);
		request.getSession().setAttribute("userType", "student");
		request.getSession().setAttribute("First", "1");
		
		request.getSession().removeAttribute("opencourseId");
		request.getSession().setAttribute("opencourseId", opencourseId);  //进入学习使用
		
		request.getSession().removeAttribute("firstPage");
		request.getSession().setAttribute("firstPage", firstPage);

		return "show_stuindex";
	}
	

	private PeTeacher getTeacher() throws EntityException {

		DetachedCriteria peTeacherDC = DetachedCriteria
				.forClass(PeTeacher.class);
		peTeacherDC.add(Restrictions.eq("loginId", this.getTeacher_id()));
		try {
			return (PeTeacher) this.getGeneralService().getList(peTeacherDC)
					.get(0);
		} catch (EntityException e) {
			e.printStackTrace();
			throw new EntityException("没有该教师。");
		}
	}
	

	private PeBzzTchCourse getPeBzzCourse(String courseId) throws EntityException {

		DetachedCriteria peCourseDC = DetachedCriteria
				.forClass(PeBzzTchCourse.class);
		peCourseDC.add(Restrictions.eq("id", courseId));
		try {
			return (PeBzzTchCourse) this.getGeneralService().getList(peCourseDC)
					.get(0);
		} catch (EntityException e) {
			e.printStackTrace();
			throw new EntityException("没有所选的课程");
		}
	}
	
	public void setM4Session(SsoUser user, String loginType, String type)
			throws PlatformException {
		HttpServletRequest request = ServletActionContext.getRequest();
		if (SsoConstant.SSO_TEACHER.equals(loginType)) {

			PlatformFactory factory = PlatformFactory.getInstance();
			PlatformManage platformManage = factory.createPlatformManage();
			EntityUser euser = platformManage.getEntityUser(user.getId(),
					type);
			request.getSession().setAttribute("eduplatform_user", euser);
			TeacherPriv teacherPriv = factory.getTeacherPriv(user.getId());
			request.getSession().setAttribute("eduplatform_priv", teacherPriv);
		}
		else if(SsoConstant.SSO_STUDENT.equals(loginType)){
				
			PlatformFactory factory=PlatformFactory.getInstance();
			PlatformManage platformManage=factory.createPlatformManage();
			EntityUser euser=platformManage.getEntityUser(user.getId(),type);
			request.getSession().setAttribute("eduplatform_user",euser);
	  	  	StudentPriv studentPriv=factory.getStudentPriv(user.getId());
	  	  	request.getSession().setAttribute("eduplatform_priv",studentPriv);
		}
	}

	public String getTeacher_id() {
		return teacher_id;
	}

	public void setTeacher_id(String teacher_id) {
		this.teacher_id = teacher_id;
	}

	public String getTeachclass_id() {
		return teachclass_id;
	}

	public void setTeachclass_id(String teachclass_id) {
		this.teachclass_id = teachclass_id;
	}

	public String getOpencourseId() {
		return opencourseId;
	}

	public void setOpencourseId(String opencourseId) {
		this.opencourseId = opencourseId;
	}

	public String getFirstPage() {
		return firstPage;
	}

	public void setFirstPage(String firstPage) {
		this.firstPage = firstPage;
	}

}
