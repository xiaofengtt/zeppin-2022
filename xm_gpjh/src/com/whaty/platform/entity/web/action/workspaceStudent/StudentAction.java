package com.whaty.platform.entity.web.action.workspaceStudent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.entity.PlatformFactory;
import com.whaty.platform.entity.PlatformManage;
import com.whaty.platform.entity.bean.PeEdutype;
import com.whaty.platform.entity.bean.PeGrade;
import com.whaty.platform.entity.bean.PeMajor;
import com.whaty.platform.entity.bean.PeSemester;
import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.bean.PeTchBook;
import com.whaty.platform.entity.bean.PeTchProgram;
import com.whaty.platform.entity.bean.PrTchCourseTeacher;
import com.whaty.platform.entity.bean.PrTchOpencourse;
import com.whaty.platform.entity.bean.PrTchProgramCourse;
import com.whaty.platform.entity.bean.PrTchStuElective;
import com.whaty.platform.entity.bean.PrTchStuPaper;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.bean.SystemApply;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.user.EntityUser;
import com.whaty.platform.entity.user.StudentPriv;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

public class StudentAction extends MyBaseAction {

	@Override
	public void initGrid() {
	}

	@Override
	public void setEntityClass() {
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/workspaceStudent/student";
	}

	private PeStudent peStudent;
	private String birthday;
	private String operateresult;
	private String password_old;
	private String password_new;
	private String opencourseid;
	private String opencoursename;
	private PrTchStuPaper prTchStuPaper;
	private List learningCourses;
	private List learntCourses;
	private List notLearnCourses;
	
	public List getNotLearnCourses() {
		return notLearnCourses;
	}

	public void setNotLearnCourses(List notLearnCourses) {
		this.notLearnCourses = notLearnCourses;
	}

	public List getLearntCourses() {
		return learntCourses;
	}

	public void setLearntCourses(List learntCourses) {
		this.learntCourses = learntCourses;
	}

	public List getLearningCourses() {
		return learningCourses;
	}

	public void setLearningCourses(List learningCourses) {
		this.learningCourses = learningCourses;
	}

	public PrTchStuPaper getPrTchStuPaper() {
		return prTchStuPaper;
	}

	public void setPrTchStuPaper(PrTchStuPaper prTchStuPaper) {
		this.prTchStuPaper = prTchStuPaper;
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

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public PeStudent getPeStudent() {
		return peStudent;
	}

	public void setPeStudent(PeStudent peStudent) {
		this.peStudent = peStudent;
	}

	public String viewInfo() {
		
		this.initStudent();
		
		return "viewInfo";
	}
	
	public String toEdit() {
		
		this.initStudent();
		
		return "toEdit";
	}
	
	public String editexe() {
		
		UserSession userSession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		SsoUser ssoUser = userSession.getSsoUser();
		DetachedCriteria peStudentDC = DetachedCriteria.forClass(PeStudent.class);
		peStudentDC.add(Restrictions.eq("ssoUser", ssoUser));
		List peStudentList = new ArrayList();
		try {
			peStudentList = this.getGeneralService().getList(peStudentDC);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		PeStudent instance = (PeStudent) peStudentList.get(0);
		instance.getPrStudentInfo().setAddress(this.getPeStudent().getPrStudentInfo().getAddress());
		instance.getPrStudentInfo().setZip(this.getPeStudent().getPrStudentInfo().getZip());
		instance.getPrStudentInfo().setMobilephone(this.getPeStudent().getPrStudentInfo().getMobilephone());
		instance.getPrStudentInfo().setWorkplace(this.getPeStudent().getPrStudentInfo().getWorkplace());
		instance.getPrStudentInfo().setPhone(this.getPeStudent().getPrStudentInfo().getPhone());
		instance.getPrStudentInfo().setEmail(this.getPeStudent().getPrStudentInfo().getEmail());
		
		try {
			this.getGeneralService().save(instance);
			this.setOperateresult("学生信息修改成功。");
		} catch (EntityException e) {
			e.printStackTrace();
			this.setOperateresult("学生信息修改失败。");
		}
		this.setPeStudent(instance);
		
		return "editexe";
	}
	
	public String toPassword() {
		
		this.initStudent();
		
		return "toPassword";
	}
	
	public String passwordexe() {
		
		UserSession userSession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		SsoUser ssoUser = userSession.getSsoUser();
		DetachedCriteria peStudentDC = DetachedCriteria.forClass(PeStudent.class);
		peStudentDC.add(Restrictions.eq("ssoUser", ssoUser));
		List peStudentList = new ArrayList();
		try {
			peStudentList = this.getGeneralService().getList(peStudentDC);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		PeStudent instance = (PeStudent) peStudentList.get(0);
		
		if (!instance.getSsoUser().getPassword().equals(this.getPassword_old())) {
			this.setOperateresult("原始密码不正确。");
			return "editexe";
		}
		
		instance.getSsoUser().setPassword(this.getPassword_new());
		try {
			this.getGeneralService().save(instance);
			this.setOperateresult("修改密码成功。");
		} catch (EntityException e) {
			e.printStackTrace();
			this.setOperateresult("修改密码失败。");
		}
		this.setPeStudent(instance);
		
		return "editexe";
	}
	
	public String loginInfo() {
		
		this.initStudent();
		
		return "loginInfo";
	}
	
	private void initStudent() {
		UserSession userSession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		SsoUser ssoUser = userSession.getSsoUser();
		DetachedCriteria peStudentDC = DetachedCriteria.forClass(PeStudent.class);
		peStudentDC.add(Restrictions.eq("ssoUser", ssoUser));
		List peStudentList = new ArrayList();
		try {
			peStudentList = this.getGeneralService().getList(peStudentDC);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		this.setPeStudent((PeStudent)peStudentList.get(0));
		if (this.getPeStudent().getPrStudentInfo().getBirthday() != null
				&& !"".equals(this.getPeStudent().getPrStudentInfo().getBirthday())) {
			SimpleDateFormat time = new SimpleDateFormat(
					"yyyy-MM-dd");
			this.setBirthday(time.format(this.getPeStudent().getPrStudentInfo().getBirthday()));
		} 
	}
	
	public String toPaperOperate() {
		
		 //判断学生是否选了毕业论文
		this.initStudent();
		DetachedCriteria paperDC = DetachedCriteria.forClass(PrTchStuElective.class);
		paperDC.createCriteria("enumConstByFlagElectiveAdmission", "enumConstByFlagElectiveAdmission")
			.add(Restrictions.eq("code", "1"));
		paperDC.createCriteria("peStudent", "peStudent");
		paperDC.createCriteria("prTchOpencourse", "prTchOpencourse").createCriteria("peTchCourse", "peTchCourse");
		paperDC.add(Restrictions.eq("peStudent", this.getPeStudent()));
		paperDC.add(Restrictions.eq("peTchCourse.name", "毕业论文"));
		List electivePaperList = new ArrayList();
		try {
			electivePaperList = this.getGeneralService().getList(paperDC);
		} catch (EntityException e3) {
			e3.printStackTrace();
		}
		
		
		//查询申请表是否有毕业论文重修审核通过的申请。
		DetachedCriteria applyDC = DetachedCriteria.forClass(SystemApply.class);
		applyDC.createCriteria("enumConstByApplyType", "enumConstByApplyType");
		applyDC.createCriteria("enumConstByFlagApplyStatus", "enumConstByFlagApplyStatus");
		applyDC.createCriteria("peStudent", "peStudent");
		applyDC.add(Restrictions.eq("peStudent", this.getPeStudent()));
		applyDC.add(Restrictions.eq("enumConstByApplyType.code", "10"));
		applyDC.add(Restrictions.eq("enumConstByFlagApplyStatus.code", "1"));
		List applyList = new ArrayList();
		//是不是在上学期出成绩后到当前学期选题开始之间申请的
		boolean isReapply = false;
		try {
			applyList = this.getGeneralService().getList(applyDC);
			if (applyList.size() > 0) {
//				SystemApply sa = (SystemApply) applyList.get(0);
//				
//				DetachedCriteria peSemesterDC1 = DetachedCriteria.forClass(PeSemester.class);
//				peSemesterDC1.add(Restrictions.eq("flagActive", "1"));
//				PeSemester thisSemester = (PeSemester) this.getGeneralService().getList(peSemesterDC1).get(0);
//				DetachedCriteria peSemesterDC2 = DetachedCriteria.forClass(PeSemester.class);
//				peSemesterDC2.add(Restrictions.eq("serialNumber", thisSemester.getSerialNumber()-1));
//				PeSemester lastSemester = (PeSemester) this.getGeneralService().getList(peSemesterDC2).get(0);
//				if (lastSemester.getPaperRejoinEndDate()==null) {
//					this.setOperateresult("上个学期的毕业论文时间没有设置完全，请与管理员联系。");
//					return "editexe";
//				}
//				if (thisSemester.getPaperStartDate()==null||thisSemester.getPaperEndDate()==null) {
//					this.setOperateresult("本学期的毕业论文时间还没有确定。");
//					return "editexe";
//				}
//				if (sa.getApplyDate().before(thisSemester.getPaperStartDate()) && sa.getApplyDate().after(lastSemester.getPaperRejoinEndDate())) {
//					isReapply = true;
//				}
				isReapply = true;
			}
		} catch (EntityException e2) {
			e2.printStackTrace();
		}
		
		//1、判断是否已选过毕业论文，2、判断毕业论文是否有成绩并且申请表是否有审核通过的申请,3、判断申请日期是否在当前学期。
		if (electivePaperList.size() == 0) {
			this.setOperateresult("您还没有选毕业论文课程。");
			return "editexe";
		} else if((((PrTchStuElective)electivePaperList.get(0)).getScoreTotal()!=null && applyList.size() < 1) || (applyList.size()>0 && !isReapply) ) {
			this.setOperateresult("您现在不能做毕业论文。可能的原因:<br>原因1、您已经完成毕业论文写作。<br>原因2、您的毕业论文重修申请没有审核通过。<br>原因3、毕业论文重修次数只能为一次。");
			return "editexe";
		}
		
		//判断是否在论文时间。
		DetachedCriteria dc = DetachedCriteria.forClass(PeSemester.class);
		dc.add(Restrictions.eq("flagActive", "1"));
		try {
			PeSemester peSemester = (PeSemester) this.getGeneralService().getList(dc).get(0);
			Date today = new Date();
			
			if (today.getTime()<peSemester.getPaperStartDate().getTime() || today.getTime()>peSemester.getPaperEndDate().getTime()) {
				this.setOperateresult("现在不是毕业论文时间。");
				return "editexe";
			}
		} catch (EntityException e) {
			e.printStackTrace();
		} catch (Exception e) {
			this.setOperateresult("本学期的论文写作时间还没有确定，请与管理员联系。");
			return "editexe";
		}
		
		return "toPaperOperate";
	}
	
	public String toPaperStepSelect() {
		
		this.initStudent();
		
		DetachedCriteria dc = DetachedCriteria.forClass(PrTchStuPaper.class);
		dc.createCriteria("prTchPaperTitle", "prTchPaperTitle").createAlias("peSemester", "peSemester");
		dc.add(Restrictions.eq("peStudent", this.getPeStudent()));
		dc.add(Restrictions.eq("peSemester.flagActive", "1"));
		List paperList = new ArrayList();
		try {
			paperList = this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		if (paperList.size()<1) {
			this.setOperateresult("您还没有选题");
			return "editexe";
		}
		this.setPrTchStuPaper((PrTchStuPaper) paperList.get(0));
		
		return "toPaperStepSelect";
	}
	
	public String toLearningCourses() {
		
		this.initStudent();
		
		DetachedCriteria dc1 = DetachedCriteria.forClass(PrTchStuElective.class);
		dc1.createCriteria("enumConstByFlagElectiveAdmission", "enumConstByFlagElectiveAdmission");
		dc1.createCriteria("peStudent", "peStudent");
		dc1.createCriteria("prTchOpencourse", "prTchOpencourse").createAlias("peSemester", "peSemester");
//		dc1.add(Restrictions.eq("enumConstByFlagElectiveAdmission.code", "1"));
		dc1.add(Restrictions.eq("peStudent", this.getPeStudent()));
		dc1.add(Restrictions.or(Restrictions.eq("peSemester.flagActive", "1"), Restrictions.lt("scoreTotal", new Double(60))));
		
		try {
			this.setLearningCourses(this.getGeneralService().getList(dc1));
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
//		UserSession userSession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
//		SsoUser ssoUser = userSession.getSsoUser();
		try {
			this.setM4Session(this.getPeStudent(), "0");
		} catch (PlatformException e) {
			e.printStackTrace();
		}
		return "toLearningCourses";
	}
	
	public String toLearntCourses() {
		
		this.initStudent();
		
		Double creditMustScore = new Double(this.getMyListService().getSysValueByName("creditMustScore"));
		
		
		DetachedCriteria dc = DetachedCriteria.forClass(PrTchStuElective.class);
		dc.createCriteria("enumConstByFlagElectiveAdmission", "enumConstByFlagElectiveAdmission");
		dc.createCriteria("prTchOpencourse", "prTchOpencourse").createAlias("peSemester", "peSemester");
		dc.add(Restrictions.eq("peStudent", this.getPeStudent()));
		dc.add(Restrictions.eq("enumConstByFlagElectiveAdmission.code", "1"));
		dc.add(Restrictions.ge("scoreTotal", creditMustScore));
		dc.add(Restrictions.not(Restrictions.eq("peSemester.flagActive", "1")));
		dc.addOrder(Order.asc("prTchOpencourse.peSemester"));
		
		try {
			this.setLearntCourses(this.getGeneralService().getList(dc));
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
		return "toLearntCourses";
	}
	
	public String toNotLearnCourses() {
		
		this.initStudent();
		
		PeMajor major = this.getPeStudent().getPeMajor();
		PeEdutype edutype = this.getPeStudent().getPeEdutype();
		PeGrade grade = this.getPeStudent().getPeGrade();
		
		DetachedCriteria chosenDC = DetachedCriteria.forClass(PrTchStuElective.class);
		chosenDC.createCriteria("enumConstByFlagElectiveAdmission", "enumConstByFlagElectiveAdmission");
		chosenDC.add(Restrictions.eq("peStudent", this.getPeStudent()));
		chosenDC.add(Restrictions.eq("enumConstByFlagElectiveAdmission.code", "1"));
		try {
			this.setLearntCourses(this.getGeneralService().getList(chosenDC));
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
		List leantPrTchProgramCourseList = new ArrayList();
		for (Iterator iter = this.getLearntCourses().iterator(); iter.hasNext();) {
			PrTchStuElective instance = (PrTchStuElective) iter.next();
			leantPrTchProgramCourseList.add(instance.getPrTchProgramCourse().getId());
		}
		
		DetachedCriteria dcPeTchProgram = DetachedCriteria.forClass(PeTchProgram.class);
		dcPeTchProgram.add(Restrictions.eq("peMajor", major));
		dcPeTchProgram.add(Restrictions.eq("peEdutype", edutype));
		dcPeTchProgram.add(Restrictions.eq("peGrade", grade));
		dcPeTchProgram.add(Restrictions.eq("enumConstByFlagMajorType", this.getPeStudent().getEnumConstByFlagMajorType()));
		PeTchProgram program = null;
		try {
			program = (PeTchProgram)(this.getGeneralService().getList(dcPeTchProgram).get(0));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		DetachedCriteria dc = DetachedCriteria.forClass(PrTchProgramCourse.class);
		dc.createCriteria("peTchProgramGroup", "peTchProgramGroup")
			.createAlias("peTchProgram", "peTchProgram").createAlias("peTchCoursegroup", "peTchCoursegroup");
		dc.add(Restrictions.eq("peTchProgramGroup.peTchProgram", program));
		if (leantPrTchProgramCourseList.size()!=0) {
			dc.add(Restrictions.not(Restrictions.in("id", leantPrTchProgramCourseList)));
		}
		dc.addOrder(Order.asc("peTchProgramGroup.peTchCoursegroup"));
		try {
			this.setNotLearnCourses(this.getGeneralService().getList(dc));
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
		return "toNotLearnCourses";
	}
	
	private List applyList;
	
	public List getApplyList() {
		return applyList;
	}

	public void setApplyList(List applyList) {
		this.applyList = applyList;
	}

	public String toChangeStudent() {
		
		this.initStudent();
		DetachedCriteria dc = DetachedCriteria.forClass(SystemApply.class);
		dc.createCriteria("enumConstByApplyType", "enumConstByApplyType");
		dc.add(Restrictions.eq("peStudent", this.getPeStudent()));
		dc.add(Restrictions.or(Restrictions.eq("enumConstByApplyType.code", "12"), Restrictions.or(Restrictions.eq("enumConstByApplyType.code", "13"), Restrictions.eq("enumConstByApplyType.code", "14"))));
		try {
			this.setApplyList(this.getGeneralService().getList(dc));
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "toChangeStudent";
	}
	
	public String courseTeachers(String courseId) {
		
		StringBuffer zhujiang = new StringBuffer("主讲教师：");
		StringBuffer zeren = new StringBuffer("责任教师：");
		
		DetachedCriteria dc = DetachedCriteria.forClass(PrTchCourseTeacher.class);
		dc.createCriteria("peTchCourse", "peTchCourse");
		dc.add(Restrictions.eq("peTchCourse.id", courseId));
		List courseTeacherList = new ArrayList();
		try {
			courseTeacherList = this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		for (Iterator iter = courseTeacherList.iterator(); iter.hasNext();) {
			PrTchCourseTeacher instance = (PrTchCourseTeacher) iter.next();
			if (instance.getEnumConstByFlagTeacherType().getCode().equals("1")) {
				zhujiang.append(instance.getPeTeacher().getTrueName() + " ");
			} else {
				zeren.append(instance.getPeTeacher().getTrueName() + " ");
			}
		}
		
		return zhujiang.toString() + "<br>"+ zeren.toString();
	}
	
	public String books(String peTchCourseId) {
		
		DetachedCriteria peTchBookDC = DetachedCriteria.forClass(PeTchBook.class);
		peTchBookDC.createCriteria("peTchCourse", "peTchCourse");
		peTchBookDC.createCriteria("enumConstByFlagIsvalid", "enumConstByFlagIsvalid");
		peTchBookDC.add(Restrictions.eq("peTchCourse.id", peTchCourseId));
		peTchBookDC.add(Restrictions.eq("enumConstByFlagIsvalid.code", "1"));
		List books = new ArrayList();
		try {
			books = this.getGeneralService().getList(peTchBookDC);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		StringBuffer sb = new StringBuffer();
		for (Iterator iter = books.iterator(); iter.hasNext();) {
			PeTchBook instance = (PeTchBook) iter.next();
			sb.append(instance.getName() + " " + instance.getPublisher() + " " +instance.getAuthor() + "<br>");
		}
		
		return sb.toString();
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
	
	public void setM4Session(PeStudent user, String loginType) throws PlatformException{
		HttpServletRequest request = ServletActionContext.getRequest();
		if(SsoConstant.SSO_STUDENT.equals(loginType)){
			
			PlatformFactory factory=PlatformFactory.getInstance();
			PlatformManage platformManage=factory.createPlatformManage();
			EntityUser euser=platformManage.getEntityUser(user.getId(),"student");
			request.getSession().setAttribute("eduplatform_user",euser);
	  	  	StudentPriv studentPriv=factory.getStudentPriv(user.getId());
	  	  	request.getSession().setAttribute("eduplatform_priv",studentPriv);
		}
	}
}
