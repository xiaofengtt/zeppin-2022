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
import com.whaty.platform.entity.bean.PrExamBooking;
import com.whaty.platform.entity.bean.PrExamStuMaincourse;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.exam.mainCourse.MainCourseBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;
import com.whaty.platform.util.JsonUtil;

/**
 * 学生工作室 主干课考试预约查询
 * @author 李冰
 *
 */
public class MainCourseReserverViewAction extends MainCourseBaseAction {
	private List courseList; //保存学生可预约考试的课程
	private PeSemester peSemester;//保存当前学期信息
	private boolean checkDate; //判断是否在操作时间之内
	private String id;


	public String viewMainCourse() {
		if (this.getLoginStudent()==null) {
			this.setMsg("无法取得您的信息请重新登录");
			return "msg";
		}
		
		this.theCourses();
		this.theSemester();
		this.checkApplyDate();
		
		return "viewMainCourse";
	}
	
	public String cancelMaincourse() {
		this.setTogo("/entity/workspaceStudent/mainCourseReserverView_viewMainCourse.action");
		if (this.getId()!=null){
			try {
				this.getGeneralService().delete(this.getMyListService().getById(PrExamStuMaincourse.class, this.getId()));
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
		Date start = this.getPeSemester().getMainCourseStartDate();
		Date end = this.getPeSemester().getMainCourseEndDate();
		if (now.after(start)&&Const.compareDate(now, end)) {
			this.setCheckDate(true);
		} 
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void theCourses(){
		DetachedCriteria dc = DetachedCriteria.forClass(PrExamStuMaincourse.class);
		dc.createCriteria("peStudent", "peStudent")
			.createAlias("peSite", "peSite")
			.createAlias("peEdutype", "peEdutype")
			.createAlias("peMajor", "peMajor")
			.createAlias("peGrade", "peGrade");
		dc.createCriteria("prExamOpenMaincourse","prExamOpenMaincourse")
			.createAlias("peTchCourse", "peTchCourse")
			.createCriteria("peExamMaincourseNo", "peExamMaincourseNo")
			.createAlias("peSemester", "peSemester");
		dc.add(Restrictions.eq("peSemester.flagActive", "1"));
		dc.add(Restrictions.eq("peStudent.id", this.getBean().getId()));
		try {
			List<PrExamStuMaincourse> list = this.getGeneralService().getList(dc);
			System.out.println(list.size());
			this.setCourseList(list);
		} catch (EntityException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void initGrid() {
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PrExamStuMaincourse.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/workspaceStudent/mainCourseReserverView";
	}
	

	

	//取得当前登陆学生peStudent对象
	private PeStudent getLoginStudent(){
		UserSession userSession = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		if (userSession==null) {
			return null;
		}
		DetachedCriteria dc = DetachedCriteria.forClass(PeStudent.class);
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
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
