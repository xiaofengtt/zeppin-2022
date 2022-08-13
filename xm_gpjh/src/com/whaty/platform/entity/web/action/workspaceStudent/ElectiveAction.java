package com.whaty.platform.entity.web.action.workspaceStudent;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeEdutype;
import com.whaty.platform.entity.bean.PeGrade;
import com.whaty.platform.entity.bean.PeMajor;
import com.whaty.platform.entity.bean.PeSemester;
import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.bean.PeTchBook;
import com.whaty.platform.entity.bean.PeTchProgram;
import com.whaty.platform.entity.bean.PeTchProgramGroup;
import com.whaty.platform.entity.bean.PrTchOpencourse;
import com.whaty.platform.entity.bean.PrTchProgramCourse;
import com.whaty.platform.entity.bean.PrTchStuElective;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.teaching.elective.StudentElectiveService;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;

public class ElectiveAction extends MyBaseAction {

	private PeTchProgram program;
	private PeSemester peSemester;
	private String peSemesterId;
	private String[] courseid;
	private String operateresult;
	private PeStudent peStudent;
	private StudentElectiveService studentElectiveService;
	
	public StudentElectiveService getStudentElectiveService() {
		return studentElectiveService;
	}

	public void setStudentElectiveService(
			StudentElectiveService studentElectiveService) {
		this.studentElectiveService = studentElectiveService;
	}

	public PeStudent getPeStudent() {
		return peStudent;
	}

	public void setPeStudent(PeStudent peStudent) {
		this.peStudent = peStudent;
	}

	public String getOperateresult() {
		return operateresult;
	}

	public void setOperateresult(String operateresult) {
		this.operateresult = operateresult;
	}

	public String[] getCourseid() {
		return courseid;
	}

	public void setCourseid(String[] courseid) {
		this.courseid = courseid;
	}

	public String getPeSemesterId() {
		return peSemesterId;
	}

	public void setPeSemesterId(String peSemesterId) {
		this.peSemesterId = peSemesterId;
	}

	public PeSemester getPeSemester() {
		return peSemester;
	}

	public void setPeSemester(PeSemester peSemester) {
		this.peSemester = peSemester;
	}

	@Override
	public void initGrid() {
	}

	@Override
	public void setEntityClass() {
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/workspaceStudent/studentElective";
	}
	
	public List getGGBX() {
		return this.getProgramCourses("_1");
	}
	
	public List getGGXX() {
		return this.getProgramCourses("_4");
	}
	
	public List getZYBX() {
		return this.getProgramCourses("_2");
	}
	
	public List getZYXX() {
		return this.getProgramCourses("_3");
	}
	
	public int getGGBXsize() {
		return this.getGGBX().size()==0 ? 1 : this.getGGBX().size();
	}
	
	public int getGGXXsize() {
		return this.getGGXX().size()==0 ? 1 : this.getGGXX().size();
	}
	
	public int getZYBXsize() {
		return this.getZYBX().size()==0 ? 1 : this.getZYBX().size();
	}
	
	public int getZYXXsize() {
		return this.getZYXX().size()==0 ? 1 : this.getZYXX().size();
	}
	
	public Long getGGBXcredit() {
		return this.getProgramGroupCredit("_1");
	}
	
	public Long getGGXXcredit() {
		return this.getProgramGroupCredit("_4");
	}
	
	public Long getZYBXcredit() {
		return this.getProgramGroupCredit("_2");
	}
	
	public Long getZYXXcredit() {
		return this.getProgramGroupCredit("_3");
	}
	
	private Long getProgramGroupCredit(String id) {
		Long credit = 0L;
		Set programGroupSet = program.getPeTchProgramGroups();
		for (Iterator iter = programGroupSet.iterator(); iter.hasNext();) {
			PeTchProgramGroup programGroup = (PeTchProgramGroup) iter.next();
			if (!programGroup.getPeTchCoursegroup().getId().equals(id)) {
				continue;
			}
			credit = programGroup.getMinCredit();
			break;
		}
		return credit;
	}
	
	private List getProgramCourses(String id) {
		List programCourseList = new ArrayList();
		DetachedCriteria dc = DetachedCriteria.forClass(PrTchProgramCourse.class);
		dc.createCriteria("peTchProgramGroup", "peTchProgramGroup").createAlias("peTchProgram", "peTchProgram").createAlias("peTchCoursegroup", "peTchCoursegroup");
		dc.add(Restrictions.eq("peTchProgramGroup.peTchProgram", this.getProgram()));
		dc.add(Restrictions.eq("peTchCoursegroup.id", id));
		try {
			programCourseList = this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
		return programCourseList;
	}
	
	public PeTchProgram getProgram() {
		return program;
	}

	public void setProgram(PeTchProgram program) {
		this.program = program;
	}

	/**
	 * 取得学生
	 * @return
	 * @throws EntityException
	 */
	private PeStudent theStudent() throws EntityException{
		UserSession us = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		SsoUser ssoUser = us.getSsoUser();

		DetachedCriteria dcPeStudent = DetachedCriteria.forClass(PeStudent.class);
		dcPeStudent.add(Restrictions.eq("ssoUser", ssoUser));
		dcPeStudent.createCriteria("peGrade", "peGrade");
		dcPeStudent.createCriteria("peMajor", "peMajor");
		dcPeStudent.createCriteria("peEdutype", "peEdutype");
		PeStudent student = null;
			student = (PeStudent)(this.getGeneralService().getList(dcPeStudent).get(0));
		return student;
	}
	public String main() {
		
		DetachedCriteria dc = DetachedCriteria.forClass(PeSemester.class);
		dc.add(Restrictions.eq("flagActive", "1"));
		
//		UserSession us = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
//		SsoUser ssoUser = us.getSsoUser();
//
//		DetachedCriteria dcPeStudent = DetachedCriteria.forClass(PeStudent.class);
//		dcPeStudent.add(Restrictions.eq("ssoUser", ssoUser));
//		dcPeStudent.createCriteria("peGrade", "peGrade");
//		dcPeStudent.createCriteria("peMajor", "peMajor");
//		dcPeStudent.createCriteria("peEdutype", "peEdutype");
		PeStudent student = null;
		
		try {
			PeSemester peSemester = (PeSemester) this.getGeneralService().getList(dc).get(0);
//			student = (PeStudent)(this.getGeneralService().getList(dcPeStudent).get(0));
			student = this.theStudent();
			this.setPeStudent(student);
			PeGrade peGrade = student.getPeGrade();
			
			Date today = new Date();
			if(peGrade.getSerialNumber().longValue() == peSemester.getSerialNumber().longValue()){//年级和学期相同表示是新生
				if (today.getTime()<peSemester.getElectiveFreshStartDate().getTime() || today.getTime()>peSemester.getElectiveFreshEndDate().getTime()) {
					this.setOperateresult("现在不是新生选课时间。");
					return "operateResult";
				}
			}else{
				if (today.getTime()<peSemester.getElectiveStartDate().getTime() || today.getTime()>peSemester.getElectiveEndDate().getTime()) {
					this.setOperateresult("现在不是选课时间。");
					return "operateResult";
				}
			}
			
			this.setPeSemester(peSemester);
		} catch (EntityException e) {
			e.printStackTrace();
		} catch (Exception e) {
			this.setOperateresult("选课时间还未指定，请与管理员联系。");
			return "operateResult";
		}
		
		PeMajor major = student.getPeMajor();
		PeEdutype edutype = student.getPeEdutype();
		PeGrade grade = student.getPeGrade();
		
		DetachedCriteria dcPeTchProgram = DetachedCriteria.forClass(PeTchProgram.class);
		dcPeTchProgram.add(Restrictions.eq("peMajor", major));
		dcPeTchProgram.add(Restrictions.eq("peEdutype", edutype));
		dcPeTchProgram.add(Restrictions.eq("peGrade", grade));
		dcPeTchProgram.add(Restrictions.eq("enumConstByFlagMajorType", student.getEnumConstByFlagMajorType()));
		PeTchProgram program = null;
		try {
			List<PeTchProgram> listPeTchProgram = this.getGeneralService().getList(dcPeTchProgram);
			if(listPeTchProgram!=null&&listPeTchProgram.size()>0){
				program = listPeTchProgram.get(0);
			}else{
				this.setOperateresult("对不起，您还没有教学计划，请与管理员联系。");
				return "operateResult";
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		this.setProgram(program);
		
//		DetachedCriteria peSemesterDC = DetachedCriteria.forClass(PeSemester.class);
//		peSemesterDC.add(Restrictions.eq("flagActive", "1"));
//		try {
//			this.setPeSemester((PeSemester) this.getGeneralService().getList(peSemesterDC).get(0));
//		} catch (EntityException e) {
//			e.printStackTrace();
//		}
		
		return "main";
	}
	
	public String toProgram() {
		
//		UserSession us = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
//		SsoUser ssoUser = us.getSsoUser();
//
//		DetachedCriteria dcPeStudent = DetachedCriteria.forClass(PeStudent.class);
//		dcPeStudent.add(Restrictions.eq("ssoUser", ssoUser));
		PeStudent student = null;
		try {
//			student = (PeStudent)(this.getGeneralService().getList(dcPeStudent).get(0));
			student = this.theStudent();
			this.setPeStudent(student);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		PeMajor major = student.getPeMajor();
		PeEdutype edutype = student.getPeEdutype();
		PeGrade grade = student.getPeGrade();
		
		DetachedCriteria dcPeTchProgram = DetachedCriteria.forClass(PeTchProgram.class);
		dcPeTchProgram.add(Restrictions.eq("peMajor", major));
		dcPeTchProgram.add(Restrictions.eq("peEdutype", edutype));
		dcPeTchProgram.add(Restrictions.eq("peGrade", grade));
		dcPeTchProgram.add(Restrictions.eq("enumConstByFlagMajorType", student.getEnumConstByFlagMajorType()));
		PeTchProgram program = null;
		try {
			program = (PeTchProgram)(this.getGeneralService().getList(dcPeTchProgram).get(0));
		} catch (EntityException e) {
			e.printStackTrace();
		} catch (Exception e) {
			this.setOperateresult("对不起，您还没有教学计划，请与管理员联系。");
			return "operateResult";
		}
		this.setProgram(program);
		
		return "toProgram";
	}
	
	public List mainCourses() {
		List mainCoursesList = new ArrayList();
		
		DetachedCriteria dc = DetachedCriteria.forClass(PrTchProgramCourse.class);
		dc.createCriteria("enumConstByFlagIsMainCourse", "enumConstByFlagIsMainCourse");
		dc.createCriteria("peTchProgramGroup", "peTchProgramGroup").createAlias("peTchProgram", "peTchProgram");
		dc.add(Restrictions.eq("enumConstByFlagIsMainCourse.code", "1"));
		dc.add(Restrictions.eq("peTchProgramGroup.peTchProgram", this.getProgram()));
		try {
			mainCoursesList = this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return mainCoursesList;
	}

	public String examNo(String peTchCourseId) {
		
		DetachedCriteria openCourseDC = DetachedCriteria.forClass(PrTchOpencourse.class);
		openCourseDC.createCriteria("peTchCourse", "peTchCourse");
		openCourseDC.add(Restrictions.eq("peTchCourse.id", peTchCourseId));
		openCourseDC.add(Restrictions.eq("peSemester", this.getPeSemester()));
		String examNo = "";
		try {
			examNo = ((PrTchOpencourse)this.getGeneralService().getList(openCourseDC).get(0)).getAdviceExamNo();
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
		return examNo;
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
	
	public boolean isLearnt(String courseID) {
		
		//非当前学期已选，已修
		DetachedCriteria electiveDC = DetachedCriteria.forClass(PrTchStuElective.class);
		electiveDC.createCriteria("prTchProgramCourse", "prTchProgramCourse");
		electiveDC.createCriteria("enumConstByFlagElectiveAdmission", "enumConstByFlagElectiveAdmission");
		electiveDC.add(Restrictions.eq("peStudent", this.getPeStudent()));
		electiveDC.add(Restrictions.eq("enumConstByFlagElectiveAdmission.code", "1"));
		List chosenList = new ArrayList();
		try {
			chosenList = this.getGeneralService().getList(electiveDC);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		for (Iterator iter = chosenList.iterator(); iter.hasNext();) {
			PrTchStuElective instance = (PrTchStuElective) iter.next();
			if (instance.getPrTchProgramCourse().getId().equals(courseID)) {
				//已修
				return true;
			}
		}
		
		return false;
	}
	
	public boolean isChosen(String courseID) {
		
		//当前学期已选
		DetachedCriteria electiveDC = DetachedCriteria.forClass(PrTchStuElective.class);
		electiveDC.createCriteria("prTchProgramCourse", "prTchProgramCourse");
		electiveDC.createCriteria("enumConstByFlagElectiveAdmission", "enumConstByFlagElectiveAdmission");
		electiveDC.add(Restrictions.eq("peStudent", this.getPeStudent()));
		electiveDC.add(Restrictions.eq("enumConstByFlagElectiveAdmission.code", "0"));
		List chosenList = new ArrayList();
		try {
			chosenList = this.getGeneralService().getList(electiveDC);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		for (Iterator iter = chosenList.iterator(); iter.hasNext();) {
			PrTchStuElective instance = (PrTchStuElective) iter.next();
			if (instance.getPrTchProgramCourse().getId().equals(courseID)) {
				//已选
				return true;
			}
		}
		
		return false;
	}

	public Double alreadyGGBX() {
		
		return this.alreadyCredit("_1");
	}
	
	public Double alreadyGGXX() {
		
		return this.alreadyCredit("_4");
	}

	public Double alreadyZYBX() {
	
		return this.alreadyCredit("_2");
	}

	public Double alreadyZYXX() {
	
		return this.alreadyCredit("_3");
	}
	
	private Double alreadyCredit(String id) {
		Double credit = 0.0;
		
		PeStudent student = this.getPeStudent();
		
		DetachedCriteria electiveDC = DetachedCriteria.forClass(PrTchStuElective.class);
		electiveDC.createCriteria("enumConstByFlagElectiveAdmission", "enumConstByFlagElectiveAdmission");
		electiveDC.createCriteria("prTchProgramCourse", "prTchProgramCourse")
					.createCriteria("peTchProgramGroup", "peTchProgramGroup")
					.createAlias("peTchCoursegroup", "peTchCoursegroup");
		electiveDC.add(Restrictions.eq("enumConstByFlagElectiveAdmission.code", "1"));
		electiveDC.add(Restrictions.ge("scoreTotal", new Double(this.getMyListService().getSysValueByName("creditMustScore"))));
		electiveDC.add(Restrictions.eq("peTchCoursegroup.id", id));
		electiveDC.add(Restrictions.eq("peStudent", student));
		
		try {
			List list = this.getGeneralService().getList(electiveDC);
			for (Iterator iter = list.iterator(); iter.hasNext();) {
				PrTchStuElective instance = (PrTchStuElective) iter.next();
				credit += instance.getPrTchProgramCourse().getCredit();
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
		return credit;
	}
	
	public String elective() {
		
		
//		UserSession us = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
//		SsoUser ssoUser = us.getSsoUser();
//
//		DetachedCriteria dcPeStudent = DetachedCriteria.forClass(PeStudent.class);
//		dcPeStudent.add(Restrictions.eq("ssoUser", ssoUser));
		PeStudent student = null;
		try {
//			student = (PeStudent)(this.getGeneralService().getList(dcPeStudent).get(0));
			student = this.theStudent();
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
		//当前登录学生的教学计划，得到每学期最多选课数
		PeMajor major = student.getPeMajor();
		PeEdutype edutype = student.getPeEdutype();
		PeGrade grade = student.getPeGrade();
		
		DetachedCriteria dcPeTchProgram = DetachedCriteria.forClass(PeTchProgram.class);
		dcPeTchProgram.add(Restrictions.eq("peMajor", major));
		dcPeTchProgram.add(Restrictions.eq("peEdutype", edutype));
		dcPeTchProgram.add(Restrictions.eq("peGrade", grade));
		dcPeTchProgram.add(Restrictions.eq("enumConstByFlagMajorType", student.getEnumConstByFlagMajorType()));
		PeTchProgram program = null;
		try {
			program = (PeTchProgram)(this.getGeneralService().getList(dcPeTchProgram).get(0));
		} catch (EntityException e) {
			e.printStackTrace();
		}
		Long maxElective = program.getMaxElective();
		
		int courseCount = 0;
		if (this.getCourseid() != null) {
			courseCount = this.getCourseid().length;
		}
		
		if (courseCount > maxElective) {
			this.setOperateresult("您每学期最多可以选" + maxElective + "门课程。");
			return "operateResult";
		}
		
		//当前学期
		DetachedCriteria peSemesterDC = DetachedCriteria.forClass(PeSemester.class);
		peSemesterDC.add(Restrictions.eq("flagActive", "1"));
		try {
			this.setPeSemester((PeSemester) this.getGeneralService().getList(peSemesterDC).get(0));
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
		try {
			this.setOperateresult(this.getStudentElectiveService().saveElective(student, this.getPeSemester(), courseid));
		} catch (EntityException e) {
			e.printStackTrace();
			this.setOperateresult(e.getMessage());
		}
		
		return "operateResult";
	}
	
}