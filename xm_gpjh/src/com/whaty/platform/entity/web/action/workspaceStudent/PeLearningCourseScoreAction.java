package com.whaty.platform.entity.web.action.workspaceStudent;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeSemester;
import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.bean.PeTchCoursegroup;
import com.whaty.platform.entity.bean.PeTchProgram;
import com.whaty.platform.entity.bean.PrExamStuMaincourse;
import com.whaty.platform.entity.bean.PrTchCourseTeacher;
import com.whaty.platform.entity.bean.PrTchProgramCourse;
import com.whaty.platform.entity.bean.PrTchStuElective;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;

/**
 * 学生工作室 成绩查询
 * @author 李冰
 *
 */
public class PeLearningCourseScoreAction extends MyBaseAction {
	private List<PrTchStuElective> oldCourseScoreList; //保存学生已修课程，用于页面显示
	private List<PrTchStuElective> nowCourseScoreList; //保存学生在修课程，用于页面显示
	private PeStudent peStudent; //保存学生信息
	private List<PrExamStuMaincourse> mainCourseScoreList;//保存学生主干课信息
	private List<String> uniteCourse; //保存学生统考科目信息
	private String edutype;//保存学生层次
	private boolean scoreDate;//是否到查询考试成绩时间，true 是，false 否
	
	public boolean isScoreDate() {
		return scoreDate;
	}

	public void setScoreDate(boolean scoreDate) {
		this.scoreDate = scoreDate;
	}

	public String getEdutype() {
		return edutype;
	}

	public void setEdutype(String edutype) {
		this.edutype = edutype;
	}

	@Override
	public void initGrid() {
	}
	
	/**
	 * 转向菜单页面
	 * @return
	 */
	public String checkAndTurnMenu(){
		String id = this.getUserPeStudentId();
		return "scoreMenu";
	}
	/**
	 * 查看已修课程的成绩
	 * @return
	 */
	public String toOldCourseScore() {
		String id = this.getUserPeStudentId();
		if (id == null) {
			this.setMsg("无法取得您的登陆信息，请重新登陆！");
			return "msg";
		}
		DetachedCriteria dc = DetachedCriteria.forClass(PrTchStuElective.class);
		DetachedCriteria dcPeStudent = dc.createCriteria("peStudent", "peStudent");
		DetachedCriteria dcPrTchProgramCourse = dc.createCriteria("prTchProgramCourse", "prTchProgramCourse"); 
		DetachedCriteria dcPrTchOpencourse = dc.createCriteria("prTchOpencourse", "prTchOpencourse");
		DetachedCriteria dcPeTchCourse = dcPrTchOpencourse.createCriteria("peTchCourse", "peTchCourse");
		dcPeStudent.add(Restrictions.eq("id", id));
		dcPrTchOpencourse.createCriteria("peSemester", "peSemester").add(Restrictions.not(Restrictions.eq("flagActive", "1")));
		DetachedCriteria dcFlagElectiveAdmission = dc.createCriteria("enumConstByFlagElectiveAdmission", "enumConstByFlagElectiveAdmission");
		dcFlagElectiveAdmission.add(Restrictions.eq("code", "1"));
		dc.addOrder(Order.desc("electiveDate"));
		List<PrTchStuElective> prTchStuElectiveList ;
			try {
				prTchStuElectiveList = this.getGeneralService().getList(dc);

			} catch (EntityException e) {
				e.printStackTrace();
				this.setMsg("查询失败！");
				return "msg";
			}
		//设置已修课程
		this.setOldCourseScoreList(prTchStuElectiveList);
		this. ifCanSeeScore();
		if (this.isScoreDate()){
		//设置在修课程
		this.nowCourse(id);
		this.setNowCourseScoreList(this.getNowCourseScoreList());
		}
		return "oldCourseScore";
	}
	
	private void nowCourse(String id) {
		DetachedCriteria dc = DetachedCriteria.forClass(PrTchStuElective.class);
		DetachedCriteria dcPeStudent = dc.createCriteria("peStudent", "peStudent");
		DetachedCriteria dcPrTchProgramCourse = dc.createCriteria("prTchProgramCourse", "prTchProgramCourse"); 
		DetachedCriteria dcPrTchOpencourse = dc.createCriteria("prTchOpencourse", "prTchOpencourse");
		DetachedCriteria dcPeTchCourse = dcPrTchOpencourse.createCriteria("peTchCourse", "peTchCourse");
		dcPeStudent.add(Restrictions.eq("id", id));
		dcPrTchOpencourse.createCriteria("peSemester", "peSemester").add(Restrictions.eq("flagActive", "1"));
		DetachedCriteria dcFlagElectiveAdmission = dc.createCriteria("enumConstByFlagElectiveAdmission", "enumConstByFlagElectiveAdmission");
		dcFlagElectiveAdmission.add(Restrictions.eq("code", "1"));
		dc.addOrder(Order.desc("electiveDate"));
		List<PrTchStuElective> prTchStuElectiveList = null ;
			try {
				prTchStuElectiveList = this.getGeneralService().getList(dc);

			} catch (EntityException e) {
				e.printStackTrace();
			}
			this.setNowCourseScoreList(prTchStuElectiveList);
	}
	
	/**
	 * 取得课程分组
	 * @param id
	 * @return
	 */
	public String courseGroup(String id){
		PrTchStuElective prTchStuElective = (PrTchStuElective)this.getMyListService().getById(PrTchStuElective.class, id);
		if(!prTchStuElective.getPrTchProgramCourse().getPeTchProgramGroup().getPeTchProgram().getPeMajor().getName().equals(this.getPeStudent().getPeMajor().getName())
				||!prTchStuElective.getPrTchProgramCourse().getPeTchProgramGroup().getPeTchProgram().getPeEdutype().getName().equals(this.getPeStudent().getPeEdutype().getName())){
			DetachedCriteria dcPrTchProgramCourse = DetachedCriteria.forClass(PrTchProgramCourse.class);
			DetachedCriteria dcPeTchProgramGroup = dcPrTchProgramCourse.createCriteria("peTchProgramGroup", "peTchProgramGroup");
			DetachedCriteria dcPeTchProgram =dcPeTchProgramGroup.createCriteria("peTchProgram", "peTchProgram");
			DetachedCriteria dcPeTchCoursegroup = dcPeTchProgramGroup.createCriteria("peTchCoursegroup", "peTchCoursegroup");
			dcPrTchProgramCourse.add(Restrictions.eq("peTchCourse", prTchStuElective.getPrTchOpencourse().getPeTchCourse()));
			dcPeTchProgram.add(Restrictions.eq("peGrade", this.getPeStudent().getPeGrade()));
			dcPeTchProgram.add(Restrictions.eq("peMajor", this.getPeStudent().getPeMajor()));
			dcPeTchProgram.add(Restrictions.eq("peEdutype", this.getPeStudent().getPeEdutype()));
			dcPeTchProgram.add(Restrictions.eq("enumConstByFlagMajorType", this.getPeStudent().getEnumConstByFlagMajorType()));
			List<PrTchProgramCourse> prTchProgramCourseList = null;
			try {
				prTchProgramCourseList=this.getGeneralService().getList(dcPrTchProgramCourse);
				//如果课程不在教学计划中，则设置类型为公共选修课
				if(prTchProgramCourseList==null||prTchProgramCourseList.isEmpty()){
					return "公共选修课";
				}//如果课程在教学计划中,则设置为现在的教学计划
				else {
					return prTchStuElective.getPrTchProgramCourse().getPeTchProgramGroup().getPeTchCoursegroup().getName();
				}
			} catch (EntityException e) {
				e.printStackTrace();
			}
		}
		return prTchStuElective.getPrTchProgramCourse().getPeTchProgramGroup().getPeTchCoursegroup().getName();
	}
	
	/**
	 * 取得教师信息
	 * @param id
	 * @return
	 */
	public String getCourseTeacher(String id){
		PrTchStuElective prTchStuElective = (PrTchStuElective)this.getMyListService().getById(PrTchStuElective.class, id);
		DetachedCriteria dc = DetachedCriteria.forClass(PrTchCourseTeacher.class);
		dc.add(Restrictions.eq("peTchCourse", prTchStuElective.getPrTchOpencourse().getPeTchCourse()));
		try {
			List<PrTchCourseTeacher> list = this.getGeneralService().getList(dc);
			if(list == null || list.isEmpty()) {
				return "暂无教师信息";
			}
			String main = "主讲教师："; //主讲教师
			String other = "责任教师："; //责任教师
			for (PrTchCourseTeacher prTchCourseTeacher : list) {
				if (prTchCourseTeacher.getEnumConstByFlagTeacherType().getCode().equals("1")) {
					main +=  prTchCourseTeacher.getPeTeacher().getTrueName() + "  ";
				}else {
					other +=  prTchCourseTeacher.getPeTeacher().getTrueName() + "  ";
				}
			}
			main +="</br>";
			return main+other;
		} catch (EntityException e) {
			e.printStackTrace();
			return "暂无教师信息";
		}
	}
	/**
	 * 根据课程取得主讲教师和责任教师
	 */
	private List<PrTchStuElective> courseTeacher(List<PrTchStuElective> prTchStuElectiveList) {
		List<PrTchStuElective> teacher =new ArrayList<PrTchStuElective>();
		PeTchCoursegroup peTchCoursegroup = (PeTchCoursegroup)this.getMyListService().getById(PeTchCoursegroup.class, "_4");
		for (PrTchStuElective prTchStuElective : prTchStuElectiveList) {
			//判断学生的层次专业是否与教学计划相符
			if(!prTchStuElective.getPrTchProgramCourse().getPeTchProgramGroup().getPeTchProgram().getPeMajor().getName().equals(this.getPeStudent().getPeMajor().getName())
					||!prTchStuElective.getPrTchProgramCourse().getPeTchProgramGroup().getPeTchProgram().getPeEdutype().getName().equals(this.getPeStudent().getPeEdutype().getName())){
				DetachedCriteria dcPrTchProgramCourse = DetachedCriteria.forClass(PrTchProgramCourse.class);
				DetachedCriteria dcPeTchProgramGroup = dcPrTchProgramCourse.createCriteria("peTchProgramGroup", "peTchProgramGroup");
				DetachedCriteria dcPeTchProgram =dcPeTchProgramGroup.createCriteria("peTchProgram", "peTchProgram");
				DetachedCriteria dcPeTchCoursegroup = dcPeTchProgramGroup.createCriteria("peTchCoursegroup", "peTchCoursegroup");
				dcPrTchProgramCourse.add(Restrictions.eq("peTchCourse", prTchStuElective.getPrTchOpencourse().getPeTchCourse()));
				dcPeTchProgram.add(Restrictions.eq("peGrade", this.getPeStudent().getPeGrade()));
				dcPeTchProgram.add(Restrictions.eq("peMajor", this.getPeStudent().getPeMajor()));
				dcPeTchProgram.add(Restrictions.eq("peEdutype", this.getPeStudent().getPeEdutype()));
				dcPeTchProgram.add(Restrictions.eq("enumConstByFlagMajorType", this.getPeStudent().getEnumConstByFlagMajorType()));
				List<PrTchProgramCourse> prTchProgramCourseList = null;
				try {
					prTchProgramCourseList=this.getGeneralService().getList(dcPrTchProgramCourse);
					//如果课程不在教学计划中，则设置类型为公共选修课
					if(prTchProgramCourseList==null||prTchProgramCourseList.isEmpty()){
						prTchStuElective.getPrTchProgramCourse().getPeTchProgramGroup().setPeTchCoursegroup(peTchCoursegroup);
					}//如果课程在教学计划中,则设置为现在的教学计划
					else {
						prTchStuElective.setPrTchProgramCourse(prTchProgramCourseList.get(0));
					}
				} catch (EntityException e) {
					e.printStackTrace();
				}
			}
			DetachedCriteria dc = DetachedCriteria.forClass(PrTchCourseTeacher.class);
			dc.add(Restrictions.eq("peTchCourse", prTchStuElective.getPrTchOpencourse().getPeTchCourse()));
			try {
				List<PrTchCourseTeacher> list = this.getGeneralService().getList(dc);
				if(list == null || list.isEmpty()) {
					prTchStuElective.setPri("暂无教师信息");
					teacher.add(prTchStuElective);
					continue;
				}
				String main = "主讲教师："; //主讲教师
				String other = "责任教师："; //责任教师
				for (PrTchCourseTeacher prTchCourseTeacher : list) {
					if (prTchCourseTeacher.getEnumConstByFlagTeacherType().getCode().equals("1")) {
						main +=  prTchCourseTeacher.getPeTeacher().getTrueName() + "  ";
					}else {
						other +=  prTchCourseTeacher.getPeTeacher().getTrueName() + "  ";
					}
				}
				main +="</br>";
				prTchStuElective.setPri(main+other);
			} catch (EntityException e) {
				e.printStackTrace();
				prTchStuElective.setPri("暂无教师信息");
			}
			teacher.add(prTchStuElective);
			
		}
		return teacher;
	}
	
	/**
	 * 设置期末考试成绩查询时间是否已经到
	 */
	private void ifCanSeeScore(){
		DetachedCriteria dc = DetachedCriteria.forClass(PeSemester.class);
		dc.add(Restrictions.eq("flagActive", "1"));
		try {
			List<PeSemester> list = this.getGeneralService().getList(dc);
			if(list!=null&&list.size()>0){
				PeSemester peSemester=list.get(0);
				Date now = new Date();
				if(Const.compareDate(peSemester.getFinalExamScoreDate(),now)){
					this.setScoreDate(true);
				}
				else {
					this.setScoreDate(false);
				}
			}else{
				this.setScoreDate(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.setScoreDate(false);
		}
	}
	
	/**
	 * 学生其他成绩查询
	 * @return
	 */
	public String toOtherCourseScore() {
		String id = this.getUserPeStudentId();
		if (id == null) {
			this.setMsg("无法取得您的登陆信息，请重新登陆！");
			return "msg";
		}

		this.mainCourseScore();
		this.theUniteCourse();
		return "otherCourseScore";
	}
	/**
	 * 取得学生主干课信息
	 */
	public void mainCourseScore() {
		DetachedCriteria dc = DetachedCriteria.forClass(PrExamStuMaincourse.class);
		dc.add(Restrictions.eq("peStudent", this.getPeStudent()));
		try {
			this.setMainCourseScoreList(this.getGeneralService().getList(dc));
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 取得教学计划中的统考课程
	 */
	private void theUniteCourse(){
		DetachedCriteria dc = DetachedCriteria.forClass(PeTchProgram.class);
		dc.add(Restrictions.eq("peMajor", this.getPeStudent().getPeMajor()));
		dc.add(Restrictions.eq("peGrade", this.getPeStudent().getPeGrade()));
		dc.add(Restrictions.eq("peEdutype", this.getPeStudent().getPeEdutype()));
		dc.add(Restrictions.eq("enumConstByFlagMajorType", this.getPeStudent().getEnumConstByFlagMajorType()));
		try {
			List<PeTchProgram> list = this.getGeneralService().getList(dc);
			if(list!=null&&list.size()>0){
				List<String> program = new ArrayList<String>();
				String code = "";
				String temp = "";
				if(list.get(0).getEnumConstByFlagUniteA() != null){
					code = list.get(0).getEnumConstByFlagUniteA().getCode();
					temp = list.get(0).getEnumConstByFlagUniteA().getName();
					temp += "|" + this.stuUniteScore(code);
					program.add(temp);
				}
				if(list.get(0).getEnumConstByFlagUniteB() != null){
					code = list.get(0).getEnumConstByFlagUniteB().getCode();
					temp = list.get(0).getEnumConstByFlagUniteB().getName();
					temp += "|" + this.stuUniteScore(code);
					program.add(temp);
					
				}
				this.setUniteCourse(program);
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
	}
	
	private String stuUniteScore(String code){
		if(code.equals("0")){
			if(this.getPeStudent().getEnumConstByScoreUniteEnglishA()!=null){
				return this.getPeStudent().getEnumConstByScoreUniteEnglishA().getName();
			} else {
				return "";
			}
		}else if(code.equals("1")){
			if(this.getPeStudent().getEnumConstByScoreUniteEnglishB()!=null){
				return this.getPeStudent().getEnumConstByScoreUniteEnglishB().getName();
			} else {
				return "";
			}
		}else if(code.equals("2")){
			if(this.getPeStudent().getEnumConstByScoreUniteEnglishC()!=null){
				return this.getPeStudent().getEnumConstByScoreUniteEnglishC().getName();
			} else {
				return "";
			}
		}else if(code.equals("3")){
			if(this.getPeStudent().getEnumConstByScoreUniteComputer()!=null){
				return this.getPeStudent().getEnumConstByScoreUniteComputer().getName();
			} else {
				return "";
			}
		}else if(code.equals("4")){
			if(this.getPeStudent().getEnumConstByScoreUniteYuwen()!=null){
				return this.getPeStudent().getEnumConstByScoreUniteYuwen().getName();
			} else {
				return "";
			}
		}else if(code.equals("5")){
			if(this.getPeStudent().getEnumConstByScoreUniteShuxue()!=null){
				return this.getPeStudent().getEnumConstByScoreUniteShuxue().getName();
			} else {
				return "";
			}
		}
		return "";
	}
	
	@Override
	public void setEntityClass() {
		this.entityClass = PrTchStuElective.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/workspaceStudent/peLearningCourseScore";
	}
	
	public void setBean(PrTchStuElective instance){
		this.superSetBean(instance);
	}
	
	public PrTchStuElective getBean(){
		return (PrTchStuElective)this.superGetBean();
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
		dc.createCriteria("peGrade", "peGrade");
		dc.createCriteria("peMajor", "peMajor");
		dc.createCriteria("peEdutype", "peEdutype");
		List student = null;
		try {
			student = this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		if(student != null && !student.isEmpty()) {
			PeStudent stu = (PeStudent)student.get(0);
			if (stu.getPeEdutype().getName().indexOf("本")>0){
				this.setEdutype("edutype");
			}
			this.setPeStudent(stu);
			return stu.getId();
		}
		return null;
	}
	
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PrTchStuElective.class);
		DetachedCriteria dcPeStudent = dc.createCriteria("peStudent", "peStudent");
		DetachedCriteria dcPrTchProgramCourse = dc.createCriteria("prTchProgramCourse", "prTchProgramCourse"); 
		DetachedCriteria dcPrTchOpencourse = dc.createCriteria("prTchOpencourse", "prTchOpencourse");
		DetachedCriteria dcPeTchCourse = dcPrTchOpencourse.createCriteria("peTchCourse", "peTchCourse");
		dcPrTchOpencourse.createAlias("peSemester", "peSemester");
		dcPrTchOpencourse.createAlias("enumConstByFlagExamCourse", "enumConstByFlagExamCourse",DetachedCriteria.LEFT_JOIN);
		DetachedCriteria dcFlagElectiveAdmission = dc.createCriteria("enumConstByFlagElectiveAdmission", "enumConstByFlagElectiveAdmission");
		dcFlagElectiveAdmission.add(Restrictions.eq("code", "1"));
		dcPeStudent.add(Restrictions.eq("id", this.getUserPeStudentId()));
		dc.createAlias("enumConstByFlagScoreStatus", "enumConstByFlagScoreStatus")
		.createAlias("enumConstByFlagScorePub", "enumConstByFlagScorePub");
		return dc;
	}
	

	public List<PrTchStuElective> getOldCourseScoreList() {
		return oldCourseScoreList;
	}

	public void setOldCourseScoreList(List<PrTchStuElective> oldCourseScoreList) {
		this.oldCourseScoreList = oldCourseScoreList;
	}

	public PeStudent getPeStudent() {
		return peStudent;
	}

	public void setPeStudent(PeStudent peStudent) {
		this.peStudent = peStudent;
	}

	public List<PrExamStuMaincourse> getMainCourseScoreList() {
		return mainCourseScoreList;
	}

	public void setMainCourseScoreList(List<PrExamStuMaincourse> mainCourseScoreList) {
		this.mainCourseScoreList = mainCourseScoreList;
	}

	public List<PrTchStuElective> getNowCourseScoreList() {
		return nowCourseScoreList;
	}

	public void setNowCourseScoreList(List<PrTchStuElective> nowCourseScoreList) {
		this.nowCourseScoreList = nowCourseScoreList;
	}

	public List<String> getUniteCourse() {
		return uniteCourse;
	}

	public void setUniteCourse(List<String> uniteCourse) {
		this.uniteCourse = uniteCourse;
	}

}
