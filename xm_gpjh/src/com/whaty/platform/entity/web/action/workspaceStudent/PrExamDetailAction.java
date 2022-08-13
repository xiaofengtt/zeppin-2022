package com.whaty.platform.entity.web.action.workspaceStudent;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.bean.PrExamBooking;
import com.whaty.platform.entity.bean.PrExamStuMaincourse;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
/**
 * 学生工作室 查看考试信息
 * @author 李冰
 *
 */
public class PrExamDetailAction extends MyBaseAction {
	private PeStudent peStudent;
	private List<PrExamBooking> prExamBookingList;//期末考试信息
	private List<PrExamStuMaincourse> prExamStuMaincourseList; //主干课考试信息
	private String edutype;
	public String toExamDetail() {
		if (this.getUserPeStudentId()==null) {
			this.setMsg("无法取得您的信息请重新登录");
			return "msg";
		}
		if (peStudent.getPeEdutype().getName().indexOf("本") > 0) {
			this.setEdutype("edutype");
		} 
		this.examInfo();
		
		this.mainCourseExamInfo();
		return "examDetail";
	}
	
	/**
	 * 设置期末考试信息
	 */
	private void examInfo() {
		DetachedCriteria dc = DetachedCriteria.forClass(PrExamBooking.class);
		dc.createAlias("peExamRoom", "peExamRoom");
		dc.createAlias("peExamNo", "peExamNo");
		DetachedCriteria dcPrTchStuElective = dc.createCriteria("prTchStuElective", "prTchStuElective");
		dc.createCriteria("peSemester", "peSemester").add(Restrictions.eq("flagActive", "1"));
		dcPrTchStuElective.add(Restrictions.eq("peStudent", this.getPeStudent()));
//		dc.addOrder(Order.desc("peExamNo"));
		List<PrExamBooking> examList = null;
		try {
			examList = this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		if(examList != null && !examList.isEmpty()) {
			List<PrExamBooking> list = new ArrayList<PrExamBooking>();
			Date now = new Date();
			for (PrExamBooking prExamBooking : examList) {
				//只显示考试批次结束时间在当前时间之后的
				if (prExamBooking.getPeExamNo().getEndDatetime().after(now)) {
					list.add(prExamBooking);
				}
			}
			this.setPrExamBookingList(list);
		}
		
	}
	
	/**
	 * 设置主干课考试信息
	 */
	private void mainCourseExamInfo() {
		DetachedCriteria dc = DetachedCriteria.forClass(PrExamStuMaincourse.class);
		DetachedCriteria dcPrExamOpenMaincourse = dc.createCriteria("prExamOpenMaincourse", "prExamOpenMaincourse");
		dc.createAlias("peExamMaincourseRoom", "peExamMaincourseRoom");
		dcPrExamOpenMaincourse.createCriteria("peExamMaincourseNo", "peExamMaincourseNo")
		.createCriteria("peSemester", "peSemester").add(Restrictions.eq("flagActive", "1"));
		dc.add(Restrictions.eq("peStudent", this.getPeStudent()));
		List<PrExamStuMaincourse> mainCourses = null;
		try {
			mainCourses = this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		if(mainCourses != null && !mainCourses.isEmpty()) {
			List<PrExamStuMaincourse> list = new ArrayList<PrExamStuMaincourse>();
			Date now = new Date();
			for (PrExamStuMaincourse prExamStuMaincourse : mainCourses) {
				//只显示考试批次结束时间在当前时间之后的
				if (prExamStuMaincourse.getPrExamOpenMaincourse()
						.getPeExamMaincourseNo().getEndDatetime().after(now)) {
					list.add(prExamStuMaincourse);
				}
			}
			this.setPrExamStuMaincourseList(list);
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
			this.setPeStudent(stu);
			return stu.getId();
		}
		return null;
	}
	@Override
	public void initGrid() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub

	}


	
	public PeStudent getPeStudent() {
		return peStudent;
	}

	public void setPeStudent(PeStudent peStudent) {
		this.peStudent = peStudent;
	}
	public List<PrExamBooking> getPrExamBookingList() {
		return prExamBookingList;
	}

	public void setPrExamBookingList(List<PrExamBooking> prExamBookingList) {
		this.prExamBookingList = prExamBookingList;
	}

	public List<PrExamStuMaincourse> getPrExamStuMaincourseList() {
		return prExamStuMaincourseList;
	}

	public void setPrExamStuMaincourseList(
			List<PrExamStuMaincourse> prExamStuMaincourseList) {
		this.prExamStuMaincourseList = prExamStuMaincourseList;
	}

	public String getEdutype() {
		return edutype;
	}

	public void setEdutype(String edutype) {
		this.edutype = edutype;
	}

}
