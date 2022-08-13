package com.whaty.platform.entity.web.action.workspaceTeacher;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.entity.PlatformFactory;
import com.whaty.platform.entity.PlatformManage;
import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.bean.PeTeacher;
import com.whaty.platform.entity.bean.PrTchCourseTeacher;
import com.whaty.platform.entity.bean.PrTchOpencourse;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.user.EntityUser;
import com.whaty.platform.entity.user.TeacherPriv;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

public class TeacherAction extends MyBaseAction {

	@Override
	public void initGrid() {
	}

	@Override
	public void setEntityClass() {
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/workspaceTeacher/teacher";
	}
	
	private PeTeacher peTeacher;
	private String flagPaper;
	private String operateresult;
	private String password_old;
	private String password_new;
	private String opencourseid;
	private String opencoursename;
	private List teachingCourses;
	private String backAction;
	
	public String getBackAction() {
		return backAction;
	}

	public void setBackAction(String backAction) {
		this.backAction = backAction;
	}

	public List getTeachingCourses() {
		return teachingCourses;
	}

	public void setTeachingCourses(List teachingCourses) {
		this.teachingCourses = teachingCourses;
	}

	public String getPassword_new() {
		return password_new;
	}

	public void setPassword_new(String password_new) {
		this.password_new = password_new;
	}

	public String getPassword_old() {
		return password_old;
	}

	public void setPassword_old(String password_old) {
		this.password_old = password_old;
	}

	public String getOperateresult() {
		return operateresult;
	}

	public void setOperateresult(String operateresult) {
		this.operateresult = operateresult;
	}

	public String getFlagPaper() {
		return flagPaper;
	}

	public void setFlagPaper(String flagPaper) {
		this.flagPaper = flagPaper;
	}

	public PeTeacher getPeTeacher() {
		return peTeacher;
	}

	public void setPeTeacher(PeTeacher peTeacher) {
		this.peTeacher = peTeacher;
	}
	
	public String toLeftMenu() {
		
		UserSession userSession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		if(userSession==null) {
			return "toLeftMenu";
		}
		
		DetachedCriteria peTeacherDC = DetachedCriteria.forClass(PeTeacher.class);
		peTeacherDC.add(Restrictions.eq("ssoUser", userSession.getSsoUser()));
		List teacherList = new ArrayList();
		try {
			teacherList = this.getGeneralService().getList(peTeacherDC);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		PeTeacher peTeacher = (PeTeacher) teacherList.get(0);
		if (peTeacher.getEnumConstByFlagIsvalid().getCode().equals("1")) {
			this.setFlagPaper("isPaperTeacher");
		} else {
			this.setFlagPaper("notPaperTeacher");
		}
		
		
		return "toLeftMenu";
	}
	
	public String viewInfo() {
		
		this.initTeacher();
		
		return "viewInfo";
	}
	
	public String toEdit() {
		
		this.initTeacher();
		
		return "toEdit";
	}
	
	public String editexe() {
		
		UserSession userSession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		SsoUser ssoUser = userSession.getSsoUser();
		DetachedCriteria peTeacherDC = DetachedCriteria.forClass(PeTeacher.class);
		peTeacherDC.add(Restrictions.eq("ssoUser", ssoUser));
		List teacherList = new ArrayList();
		try {
			teacherList = this.getGeneralService().getList(peTeacherDC);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		PeTeacher instance = (PeTeacher) teacherList.get(0);
		
		instance.setWorkplace(peTeacher.getWorkplace());
		instance.setMobilephone(peTeacher.getMobilephone());
		instance.setPhoneOffice(peTeacher.getPhoneOffice());
		instance.setEmail(peTeacher.getEmail());
		instance.setNote(peTeacher.getNote());
		
		try {
			this.getGeneralService().save(instance);
			this.setOperateresult("教师信息修改成功。");
		} catch (EntityException e) {
			e.printStackTrace();
			this.setOperateresult("教师信息修改失败。");
		}
		this.setPeTeacher(instance);
		this.setBackAction("/entity/workspaceTeacher/teacher_viewInfo.action");
		
		return "operateResult";
	}

	public String toPassword() {
		
		this.initTeacher();
		
		return "toPassword";
	}
	
	public String passwordexe() {
		
		UserSession userSession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		SsoUser ssoUser = userSession.getSsoUser();
		DetachedCriteria peTeacherDC = DetachedCriteria.forClass(PeTeacher.class);
		peTeacherDC.add(Restrictions.eq("ssoUser", ssoUser));
		List peTeacherList = new ArrayList();
		try {
			peTeacherList = this.getGeneralService().getList(peTeacherDC);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		PeTeacher instance = (PeTeacher) peTeacherList.get(0);
		
		if (!instance.getSsoUser().getPassword().equals(this.getPassword_old())) {
			this.setOperateresult("原始密码不正确。");
			return "operateResult";
		}
		
		instance.getSsoUser().setPassword(this.getPassword_new());
		try {
			this.getGeneralService().save(instance);
			this.setOperateresult("修改密码成功。");
		} catch (EntityException e) {
			e.printStackTrace();
			this.setOperateresult("修改密码失败。");
		}
		this.setPeTeacher(instance);
		this.setBackAction("/entity/workspaceTeacher/teacher_viewInfo.action");
		
		return "operateResult";
	}
	
	public String toTeachingCourses() {
		
		UserSession userSession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		DetachedCriteria peTeacherDC = DetachedCriteria.forClass(PeTeacher.class);
		peTeacherDC.add(Restrictions.eq("ssoUser", userSession.getSsoUser()));
		List teacherList = new ArrayList();
		try {
			teacherList = this.getGeneralService().getList(peTeacherDC);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		try {
			this.setM4Session(userSession.getSsoUser(), "1");
		} catch (PlatformException e) {
			e.printStackTrace();
		}
		PeTeacher peTeacher = (PeTeacher) teacherList.get(0);

//		查询当前学期开课的课程
		DetachedCriteria openCourseDC = DetachedCriteria.forClass(PrTchOpencourse.class);
		openCourseDC.createCriteria("peSemester","peSemester");
		openCourseDC.add(Restrictions.eq("peSemester.flagActive", "1"));
		List openCourseList = new ArrayList();
		try {
			openCourseList = this.getGeneralService().getList(openCourseDC);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		List courseList = new ArrayList();
		for (Iterator iter = openCourseList.iterator(); iter.hasNext();) {
			PrTchOpencourse instance = (PrTchOpencourse) iter.next();
			courseList.add(instance.getPeTchCourse());
		}
		
		//查询所有主讲身份的记录
		DetachedCriteria prTchCourseTeacherZhuJiangDC = DetachedCriteria.forClass(PrTchCourseTeacher.class);
		prTchCourseTeacherZhuJiangDC.createCriteria("enumConstByFlagTeacherType", "enumConstByFlagTeacherType");
		prTchCourseTeacherZhuJiangDC.add(Restrictions.eq("peTeacher", peTeacher));
		prTchCourseTeacherZhuJiangDC.add(Restrictions.in("peTchCourse", courseList));
		prTchCourseTeacherZhuJiangDC.add(Restrictions.eq("enumConstByFlagTeacherType.code", "1"));
		List teachingcoursesZhuJiangList = new ArrayList();
		try {
			teachingcoursesZhuJiangList = this.getGeneralService().getList(prTchCourseTeacherZhuJiangDC);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
		//查询所有责任身份的记录
		DetachedCriteria prTchCourseTeacherZeRenDC = DetachedCriteria.forClass(PrTchCourseTeacher.class);
		prTchCourseTeacherZeRenDC.createCriteria("enumConstByFlagTeacherType", "enumConstByFlagTeacherType");
		prTchCourseTeacherZeRenDC.add(Restrictions.eq("peTeacher", peTeacher));
		prTchCourseTeacherZeRenDC.add(Restrictions.in("peTchCourse", courseList));
		prTchCourseTeacherZeRenDC.add(Restrictions.eq("enumConstByFlagTeacherType.code", "2"));
		List teachingcoursesZeRenList = new ArrayList();
		try {
			teachingcoursesZeRenList = this.getGeneralService().getList(prTchCourseTeacherZeRenDC);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
		for (Iterator iter = teachingcoursesZeRenList.iterator(); iter
				.hasNext();) {
			PrTchCourseTeacher instanceZeRen = (PrTchCourseTeacher) iter.next();
			for (Iterator iterator = teachingcoursesZhuJiangList.iterator(); iterator
					.hasNext();) {
				PrTchCourseTeacher instanceZhuJiang = (PrTchCourseTeacher) iterator.next();
				if (instanceZhuJiang.getPeTchCourse().equals(instanceZeRen.getPeTchCourse())) {
					iterator.remove();
				}
			}
		}
		teachingcoursesZhuJiangList.addAll(teachingcoursesZeRenList);
		this.setTeachingCourses(teachingcoursesZhuJiangList);
		
		
		return "toTeachingCourses";
	}
	
	public String toPaperStep() {
		return "toPaperStep";
	}

	private void initTeacher() {
		UserSession userSession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		DetachedCriteria peTeacherDC = DetachedCriteria.forClass(PeTeacher.class);
		peTeacherDC.add(Restrictions.eq("ssoUser", userSession.getSsoUser()));
		List teacherList = new ArrayList();
		try {
			teacherList = this.getGeneralService().getList(peTeacherDC);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		PeTeacher peTeacher = (PeTeacher) teacherList.get(0);
		this.setPeTeacher(peTeacher);
	}
	
	public String toCourseforum() {
		DetachedCriteria dc = DetachedCriteria.forClass(PrTchOpencourse.class);
		dc.add(Restrictions.eq("id", this.getOpencourseid()));
		List opencourse = new ArrayList();
		try {
			opencourse = this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		if(opencourse.size() == 1){
			this.setOpencoursename(((PrTchOpencourse)opencourse.get(0)).getPeTchCourse().getName());
		}
		return "toCourseforum";
		
	}
	
	public String getPrTchOpencourse(String courseid) {
		DetachedCriteria dc= DetachedCriteria.forClass(PrTchOpencourse.class);
		dc.createAlias("peTchCourse", "peTchCourse").createAlias("peSemester", "peSemester");
		dc.add(Restrictions.eq("peTchCourse.id", courseid)).add(Restrictions.eq("peSemester.flagActive", "1"));
		List opencourses = new ArrayList();
		try {
			opencourses = this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		if(opencourses.size() == 1){
			return ((PrTchOpencourse)opencourses.get(0)).getId();
		}
		return "0";
		
	}

	public String getOpencourseid() {
		return opencourseid;
	}

	public void setOpencourseid(String opencourseid) {
		this.opencourseid = opencourseid;
	}

	public String getOpencoursename() {
		return opencoursename;
	}

	public void setOpencoursename(String opencoursename) {
		this.opencoursename = opencoursename;
	}
	
	public void setM4Session(SsoUser user, String loginType) throws PlatformException{
		HttpServletRequest request = ServletActionContext.getRequest();
		if(SsoConstant.SSO_TEACHER.equals(loginType)){
			
			PlatformFactory factory=PlatformFactory.getInstance();
			PlatformManage platformManage=factory.createPlatformManage();
			EntityUser euser=platformManage.getEntityUser(user.getId(),"teacher");
			request.getSession().setAttribute("eduplatform_user",euser);
			TeacherPriv teacherPriv=factory.getTeacherPriv(user.getId());
		  	request.getSession().setAttribute("eduplatform_priv",teacherPriv);
		}
	}
}
