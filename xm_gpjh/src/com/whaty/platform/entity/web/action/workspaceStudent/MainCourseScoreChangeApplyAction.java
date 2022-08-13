package com.whaty.platform.entity.web.action.workspaceStudent;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.bean.PeTchCourse;
import com.whaty.platform.entity.bean.PrExamStuMaincourse;
import com.whaty.platform.entity.bean.SystemApply;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

public class MainCourseScoreChangeApplyAction  extends MyBaseAction{
	private boolean apply; //是否可以申请
	private List<SystemApply> applys;//学生已经提交过的申请
	private List<PrExamStuMaincourse>  courseList;
	private String courseId;
	private String reason;

	/**
	 * 转向申请页面
	 * @return
	 */
	public String toMainCourseChangeApply(){
		this.setApply(false);
		this.setBean(getLoginStudent());
		if (this.getBean()==null) {
			this.setMsg("无法取得您的信息请重新登录");
			return "msg";
		}
		if (!this.getBean().getEnumConstByFlagStudentStatus().getCode().equals("4")) {
			this.setMsg("您未注册在籍，无法提交申请！");
			return "msg";	
		}
//		this.ifCanApply();
		this.stuCourse();
		this.oldApply();
		return "toApply";
	}
	/**
	 * 提交申请
	 * @return
	 */
	public String maincourseScoreChangeApply() {
		this.setTogo("/entity/workspaceStudent/mainCourseScoreChangeApply_toMainCourseChangeApply.action");
		try {
			this.setBean(getLoginStudent());
			// 检查用户是否第一次提交
			if(!this.checkFirst()) {
				this.setMsg("您已提交过该课程的申请了");
				this.setTogo("back");
			} else {
			PeTchCourse peTchCourse = (PeTchCourse)this.getMyListService().getById(PeTchCourse.class, this.getCourseId());
			SystemApply systemApply = new SystemApply();
			systemApply.setPeStudent(this.getBean());
			systemApply.setApplyDate(new Date());
			systemApply.setApplyNote(this.getCourseId()+"|"+peTchCourse.getName()+"|"+this.getReason());
			systemApply.setEnumConstByApplyType(
					this.getMyListService().getEnumConstByNamespaceCode("ApplyType", "19"));
			systemApply.setEnumConstByFlagApplyStatus(
					this.getMyListService().getEnumConstByNamespaceCode("FlagApplyStatus", "0"));
			this.getGeneralService().save(systemApply);
			this.setMsg("主干课考试成绩复查申请成功");
			}
		} catch (EntityException e) {
			this.setMsg("主干课考试成绩复查申请失败");
			this.setTogo("back");
			e.printStackTrace();
		}
		return "msg";
	}
	/**
	 * 检查用户是否第一次提交
	 * @return
	 */
	private boolean checkFirst() {
		DetachedCriteria dc1 = DetachedCriteria.forClass(SystemApply.class);
		dc1.createAlias("enumConstByApplyType", "enumConstByApplyType");
		dc1.add(Restrictions.eq("peStudent", this.getBean()));
		dc1.add(Restrictions.eq("enumConstByApplyType.code", "19"));
		dc1.add(Restrictions.ilike("applyNote", this.getCourseId(), MatchMode.START));
		List list = null;
		
		try {
			list = this.getGeneralService().getList(dc1);
		} catch (EntityException e1) {
			e1.printStackTrace();
		}
		if(list != null && !list.isEmpty()){
			return false;
		}
		return true;
	}
	/**
	 * 设置学生当前学期的考试课程
	 */
	private void stuCourse(){
		DetachedCriteria dc = DetachedCriteria.forClass(PrExamStuMaincourse.class);
		DetachedCriteria dcPrExamOpenMaincourse = dc.createCriteria("prExamOpenMaincourse", "prExamOpenMaincourse");
		DetachedCriteria dcPeExamMaincourseNo = dcPrExamOpenMaincourse.createCriteria("peExamMaincourseNo", "peExamMaincourseNo");
		DetachedCriteria dcPeTchCourse = dcPrExamOpenMaincourse.createCriteria("peTchCourse", "peTchCourse");
		DetachedCriteria dcPeSemester = dcPeExamMaincourseNo.createCriteria("peSemester", "peSemester");
		dc.add(Restrictions.isNotNull("score"));
		dcPeSemester.add(Restrictions.eq("flagActive", "1"));
		dc.add(Restrictions.eq("peStudent", this.getBean()));
		try {
			this.setCourseList(this.getGeneralService().getList(dc));
		} catch (EntityException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 取得学生已经提交的申请
	 */
	private void oldApply(){
		DetachedCriteria dc1 = DetachedCriteria.forClass(SystemApply.class);
		dc1.createAlias("enumConstByApplyType", "enumConstByApplyType");
		dc1.add(Restrictions.eq("peStudent", this.getBean()));
		dc1.add(Restrictions.eq("enumConstByApplyType.code", "19"));
		List list = null;
		try {
			 list = this.getGeneralService().getList(dc1);
			 this.setApplys(list);
		} catch (EntityException e) {
			e.printStackTrace();
		}

	}
	//取得当前登陆学生peStudent对象
	private PeStudent getLoginStudent(){
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
			return (PeStudent)student.get(0);
		}
		return null;
	}
	
	@Override
	public void setEntityClass() {
		this.entityClass = PeStudent.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/workspaceStudent/mainCourseScoreChangeApply.action";
	}
	
	public void setBean(PeStudent bean) {
		super.superSetBean(bean);
	}
	
	public PeStudent getBean() {
		return (PeStudent)super.superGetBean();
	}
	@Override
	public void initGrid() {
		// TODO Auto-generated method stub
		
	}

	public List<SystemApply> getApplys() {
		return applys;
	}

	public void setApplys(List<SystemApply> applys) {
		this.applys = applys;
	}
	
	public boolean isApply() {
		return apply;
	}

	public void setApply(boolean apply) {
		this.apply = apply;
	}

	public List<PrExamStuMaincourse> getCourseList() {
		return courseList;
	}

	public void setCourseList(List<PrExamStuMaincourse> courseList) {
		this.courseList = courseList;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
}
