package com.whaty.platform.entity.service.imp.teaching.examScore;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jxl.Sheet;
import jxl.Workbook;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.bean.PeTchCourse;
import com.whaty.platform.entity.bean.PrPriManagerEdutype;
import com.whaty.platform.entity.bean.PrPriManagerGrade;
import com.whaty.platform.entity.bean.PrPriManagerMajor;
import com.whaty.platform.entity.bean.PrPriManagerSite;
import com.whaty.platform.entity.bean.PrTchStuElective;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.dao.GeneralDao;
import com.whaty.platform.entity.dao.MyListDAO;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.teaching.examScore.ExamScoreBatchService;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;

public class ExamScoreBatchServiceImp implements ExamScoreBatchService {
	private GeneralDao generalDao;

	private MyListDAO myListDAO;

	public GeneralDao getGeneralDao() {
		return generalDao;
	}

	public void setGeneralDao(GeneralDao generalDao) {
		this.generalDao = generalDao;
	}

	public MyListDAO getMyListDAO() {
		return myListDAO;
	}

	public void setMyListDAO(MyListDAO myListDAO) {
		this.myListDAO = myListDAO;
	}

	/**
	 * 保存学生平时成绩
	 * @return
	 * @throws EntityException
	 */
	public int saveUsualScore(File file) throws EntityException{
		StringBuffer msg = new StringBuffer();
		int count = 0;

		Workbook work = null;

		try {
			work = Workbook.getWorkbook(file);
		} catch (Exception e) {
			e.printStackTrace();
			msg.append("Excel表格读取异常！批量添加失败！<br/>");
			throw new EntityException(msg.toString());
		}
		Sheet sheet = work.getSheet(0);
		int rows = sheet.getRows();
		if (rows < 2) {
			msg.append("表格为空！<br/>");
		}
		String temp = "";
		Set<PrTchStuElective> scoreSet = new HashSet();

		//添加横向权限判断。取得用户可以操作的范围				
		UserSession userSession = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		if(userSession==null){
			throw new EntityException("批量上传学生平时成绩失败,无法取得您的身份信息！");
		}
		
		SsoUser ssoUser = (SsoUser)this.getMyListDAO().getById(SsoUser.class, userSession.getSsoUser().getId());
		Set edutypes = ssoUser.getPrPriManagerEdutypes();
		Set sites = ssoUser.getPrPriManagerSites();
		Set grades = ssoUser.getPrPriManagerGrades();
		Set majors = ssoUser.getPrPriManagerMajors();
		
		for (int i = 1; i < rows; i++) {
			try {
				temp = sheet.getCell(0, i).getContents().trim();
				if (temp == null || "".equals(temp)) {
					msg.append("第" + (i + 1) + "行数据，学号为空！<br/>");
					continue;
				}
				
				DetachedCriteria dcPeStudent = DetachedCriteria.forClass(PeStudent.class);
				dcPeStudent.createCriteria("peGrade", "peGrade");
				dcPeStudent.createCriteria("peMajor", "peMajor");
				dcPeStudent.createCriteria("peEdutype", "peEdutype");
				dcPeStudent.createCriteria("peSite", "peSite");
				dcPeStudent.add(Restrictions.eq("regNo", temp));
				
				List<PeStudent> peStudentList = this.getGeneralDao().getList(dcPeStudent);
				
				if (peStudentList==null || peStudentList.isEmpty()) {
					msg.append("第" + (i + 1) + "行数据，学号不存在！<br/>");
					continue;
				}	
				
				PeStudent peStudent =peStudentList.get(0);
				
				temp = sheet.getCell(1, i).getContents().trim();
				if (temp != null && !"".equals(temp)) {
					if (!peStudent.getTrueName().equals(temp)) {
					msg.append("第" + (i + 1) + "行数据，姓名或者学号不正确！<br/>");
					continue;
					}
				}
				
				if (!peStudent.getEnumConstByFlagStudentStatus().getCode().equals("4")){
					msg.append("第" + (i + 1) + "行数据，学生不是在籍状态！<br/>");
					continue;
				}
				
				//横向权限检查。
				if(edutypes!=null&&!edutypes.isEmpty()) {
					boolean checked = false;
					for (Object object : edutypes) {
						if(((PrPriManagerEdutype)object).getPeEdutype().getId().equals(peStudent.getPeEdutype().getId())){
							checked = true;
							break;
						}
					}
					if(!checked){
						msg.append("第" + (i + 1) + "行数据，您对这个层次没有操作权限<br/>");
						continue;
					}
				}
				if(sites!=null&&!sites.isEmpty()) {
					boolean checked = false;
					for (Object object : sites) {
						if(((PrPriManagerSite)object).getPeSite().getId().equals(peStudent.getPeSite().getId())){
							checked = true;
							break;
						}
					}
					if(!checked){
						msg.append("第" + (i + 1) + "行数据，您对这个学习中心没有操作权限<br/>");
						continue;	
					}
				}
				if(grades!=null&&!grades.isEmpty()) {
					boolean checked = false;
					for (Object object : grades) {
						if(((PrPriManagerGrade)object).getPeGrade().getId().equals(peStudent.getPeGrade().getId())){
							checked = true;
							break;
						}
					}
					if(!checked){
						msg.append("第" + (i + 1) + "行数据，您对这个年级没有操作权限<br/>");
						continue;	
					}
				}
				if(majors!=null&&!majors.isEmpty()) {
					boolean checked = false;
					for (Object object : majors) {
						if(((PrPriManagerMajor)object).getPeMajor().getId().equals(peStudent.getPeMajor().getId())){
							checked = true;
							break;
						}
					}
					if(!checked){
						msg.append("第" + (i + 1) + "行数据，您对这个专业没有操作权限<br/>");
						continue;		
					}
				}
				//以上横向权限检查。
				
				temp = sheet.getCell(2, i).getContents().trim();
				if (temp == null || "".equals(temp)) {
					msg.append("第" + (i + 1) + "行数据，课程名称不能为空！<br/>");
					continue;
				}
				
				DetachedCriteria dcPeTchCourse = DetachedCriteria.forClass(PeTchCourse.class);
				dcPeTchCourse.add(Restrictions.eq("name", temp));
				List<PeTchCourse> peTchCourseList = this.getGeneralDao().getList(dcPeTchCourse);
				if (peTchCourseList==null || peTchCourseList.isEmpty()) {
					msg.append("第" + (i + 1) + "行数据，没有这门课程！<br/>");
					continue;
				}
				
				PeTchCourse peTchCourse = peTchCourseList.get(0);
				
				DetachedCriteria dcPrTchStuElective = DetachedCriteria.forClass(PrTchStuElective.class);
				dcPrTchStuElective.add(Restrictions.eq("peStudent", peStudent));
				dcPrTchStuElective.createCriteria("prTchOpencourse", "prTchOpencourse")
						.add(Restrictions.eq("peTchCourse", peTchCourse));
				List<PrTchStuElective> prTchStuElectiveList = this.getGeneralDao().getList(dcPrTchStuElective);
				
				if (prTchStuElectiveList==null || prTchStuElectiveList.isEmpty()) {
					msg.append("第" + (i + 1) + "行数据，学生没有选这门课！<br/>");
					continue;
				}
				
				PrTchStuElective prTchStuElective = prTchStuElectiveList.get(0);
				
				if (!prTchStuElective.getPrTchOpencourse().getPeSemester().getFlagActive().equals("1")) {
					msg.append("第" + (i + 1) + "行数据，学生对这门课的学习不是当前学期！<br/>");
					continue;
				}
					
				temp = sheet.getCell(3, i).getContents().trim();
				if (temp == null || "".equals(temp)) {
					msg.append("第" + (i + 1) + "行数据，成绩不能为空！<br/>");
					continue;
				}
				if (!temp.matches(Const.score)){
					msg.append("第" + (i + 1) + "行数据，成绩格式有误！成绩只能是0至100的整数，0至1位小数<br/>");
					continue;
				}
				
				Double score;
				try {
					score = Double.parseDouble(temp);
				} catch (RuntimeException e) {
					msg.append("第" + (i + 1) + "行数据，成绩格式有误！<br/>");
					continue;
				}
				
				prTchStuElective.setScoreUsual(score);
				
				if (!scoreSet.add(prTchStuElective)) {
					msg.append("第" + (i + 1) + "行数据与文件中前面的数据重复！<br/>");
					continue;
				}
				count++;
			} catch (Exception e) {
				e.printStackTrace();
				msg.append("第" + (i + 1) + "行数据添加失败！<br/>");
				continue;
			}
		}

		if (msg.length() > 0) {
			msg.append("学生平时成绩批量上传失败，请修改以上错误之后重新上传！<br/>");
			throw new EntityException(msg.toString());
		}

		for (PrTchStuElective prTchStuElective : scoreSet) {
			try {
				this.getGeneralDao().save(prTchStuElective);
			} catch (Exception e) {
				e.printStackTrace();
				throw new EntityException("批量上传学生平时成绩失败");
			}
		}
		return count;
	}
	/**
	 * 保存学生作业成绩
	 * @return
	 * @throws EntityException
	 */
	public int saveHomeworkScore(File file) throws EntityException{
		StringBuffer msg = new StringBuffer();
		int count = 0;

		Workbook work = null;

		try {
			work = Workbook.getWorkbook(file);
		} catch (Exception e) {
			e.printStackTrace();
			msg.append("Excel表格读取异常！批量添加失败！<br/>");
			throw new EntityException(msg.toString());
		}
		Sheet sheet = work.getSheet(0);
		int rows = sheet.getRows();
		if (rows < 2) {
			msg.append("表格为空！<br/>");
		}
		String temp = "";
		Set<PrTchStuElective> scoreSet = new HashSet();
		
		//添加横向权限判断。取得用户可以操作的范围				
		UserSession userSession = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		if(userSession==null){
			throw new EntityException("批量上传学生作业成绩失败,无法取得您的身份信息！");
		}
		
		
		SsoUser ssoUser = (SsoUser)this.getMyListDAO().getById(SsoUser.class, userSession.getSsoUser().getId());
		Set edutypes = ssoUser.getPrPriManagerEdutypes();
		Set sites = ssoUser.getPrPriManagerSites();
		Set grades = ssoUser.getPrPriManagerGrades();
		Set majors = ssoUser.getPrPriManagerMajors();

		for (int i = 1; i < rows; i++) {
			try {
				temp = sheet.getCell(0, i).getContents().trim();
				if (temp == null || "".equals(temp)) {
					msg.append("第" + (i + 1) + "行数据，学号为空！<br/>");
					continue;
				}
				
				DetachedCriteria dcPeStudent = DetachedCriteria.forClass(PeStudent.class);
				dcPeStudent.createCriteria("peGrade", "peGrade");
				dcPeStudent.createCriteria("peMajor", "peMajor");
				dcPeStudent.createCriteria("peEdutype", "peEdutype");
				dcPeStudent.createCriteria("peSite", "peSite");
				dcPeStudent.add(Restrictions.eq("regNo", temp));
				
				List<PeStudent> peStudentList = this.getGeneralDao().getList(dcPeStudent);
				
				if (peStudentList==null || peStudentList.isEmpty()) {
					msg.append("第" + (i + 1) + "行数据，学号不存在！<br/>");
					continue;
				}

				
				PeStudent peStudent =peStudentList.get(0);
				
				temp = sheet.getCell(1, i).getContents().trim();
				if (temp != null && !"".equals(temp)) {
					if (!peStudent.getTrueName().equals(temp)) {
					msg.append("第" + (i + 1) + "行数据，姓名或者学号不正确！<br/>");
					continue;
					}
				}
				
				if (!peStudent.getEnumConstByFlagStudentStatus().getCode().equals("4")){
					msg.append("第" + (i + 1) + "行数据，学生不是在籍状态！<br/>");
					continue;
				}
				
				//横向权限检查。
				if(edutypes!=null&&!edutypes.isEmpty()) {
					boolean checked = false;
					for (Object object : edutypes) {
						if(((PrPriManagerEdutype)object).getPeEdutype().getId().equals(peStudent.getPeEdutype().getId())){
							checked = true;
							break;
						}
					}
					if(!checked){
						msg.append("第" + (i + 1) + "行数据，您对这个层次没有操作权限<br/>");
						continue;
					}
				}
				if(sites!=null&&!sites.isEmpty()) {
					boolean checked = false;
					for (Object object : sites) {
						if(((PrPriManagerSite)object).getPeSite().getId().equals(peStudent.getPeSite().getId())){
							checked = true;
							break;
						}
					}
					if(!checked){
						msg.append("第" + (i + 1) + "行数据，您对这个学习中心没有操作权限<br/>");
						continue;	
					}
				}
				if(grades!=null&&!grades.isEmpty()) {
					boolean checked = false;
					for (Object object : grades) {
						if(((PrPriManagerGrade)object).getPeGrade().getId().equals(peStudent.getPeGrade().getId())){
							checked = true;
							break;
						}
					}
					if(!checked){
						msg.append("第" + (i + 1) + "行数据，您对这个年级没有操作权限<br/>");
						continue;	
					}
				}
				if(majors!=null&&!majors.isEmpty()) {
					boolean checked = false;
					for (Object object : majors) {
						if(((PrPriManagerMajor)object).getPeMajor().getId().equals(peStudent.getPeMajor().getId())){
							checked = true;
							break;
						}
					}
					if(!checked){
						msg.append("第" + (i + 1) + "行数据，您对这个专业没有操作权限<br/>");
						continue;		
					}
				}
				//以上横向权限检查。
				
				temp = sheet.getCell(2, i).getContents().trim();
				if (temp == null || "".equals(temp)) {
					msg.append("第" + (i + 1) + "行数据，课程名称不能为空！<br/>");
					continue;
				}
				
				DetachedCriteria dcPeTchCourse = DetachedCriteria.forClass(PeTchCourse.class);
				dcPeTchCourse.add(Restrictions.eq("name", temp));
				List<PeTchCourse> peTchCourseList = this.getGeneralDao().getList(dcPeTchCourse);
				if (peTchCourseList==null || peTchCourseList.isEmpty()) {
					msg.append("第" + (i + 1) + "行数据，没有这门课程！<br/>");
					continue;
				}
				
				PeTchCourse peTchCourse = peTchCourseList.get(0);
				
				DetachedCriteria dcPrTchStuElective = DetachedCriteria.forClass(PrTchStuElective.class);
				dcPrTchStuElective.add(Restrictions.eq("peStudent", peStudent));
				dcPrTchStuElective.createCriteria("prTchOpencourse", "prTchOpencourse")
						.add(Restrictions.eq("peTchCourse", peTchCourse));
				List<PrTchStuElective> prTchStuElectiveList = this.getGeneralDao().getList(dcPrTchStuElective);
				
				if (prTchStuElectiveList==null || prTchStuElectiveList.isEmpty()) {
					msg.append("第" + (i + 1) + "行数据，学生没有选这门课！<br/>");
					continue;
				}
				
				PrTchStuElective prTchStuElective = prTchStuElectiveList.get(0);
				
				if (!prTchStuElective.getPrTchOpencourse().getPeSemester().getFlagActive().equals("1")) {
					msg.append("第" + (i + 1) + "行数据，学生对这门课的学习不是当前学期！<br/>");
					continue;
				}
					
				temp = sheet.getCell(3, i).getContents().trim();
				if (temp == null || "".equals(temp)) {
					msg.append("第" + (i + 1) + "行数据，成绩不能为空！<br/>");
					continue;
				}
				if (!temp.matches(Const.score)){
					msg.append("第" + (i + 1) + "行数据，成绩格式有误！成绩只能是0至100的整数，0至1位小数<br/>");
					continue;
				}
				
				Double score;
				try {
					score = Double.parseDouble(temp);
				} catch (RuntimeException e) {
					msg.append("第" + (i + 1) + "行数据，成绩格式有误！<br/>");
					continue;
				}
				
				prTchStuElective.setScoreHomework(score);
				
				if (!scoreSet.add(prTchStuElective)) {
					msg.append("第" + (i + 1) + "行数据与文件中前面的数据重复！<br/>");
					continue;
				}
				count++;
			} catch (Exception e) {
				e.printStackTrace();
				msg.append("第" + (i + 1) + "行数据添加失败！<br/>");
				continue;
			}
		}

		if (msg.length() > 0) {
			msg.append("学生作业成绩批量上传失败，请修改以上错误之后重新上传！<br/>");
			throw new EntityException(msg.toString());
		}

		for (PrTchStuElective prTchStuElective : scoreSet) {
			try {
				this.getGeneralDao().save(prTchStuElective);
			} catch (Exception e) {
				e.printStackTrace();
				throw new EntityException("批量上传学生作业成绩失败");
			}
		}
		return count;
	}
	
	/**
	 * 保存学位英语成绩
	 * @param file
	 * @return
	 * @throws EntityException
	 */
	public int saveDegreeEnglishScore(File file) throws EntityException{
		StringBuffer msg = new StringBuffer();
		int count = 0;

		Workbook work = null;

		try {
			work = Workbook.getWorkbook(file);
		} catch (Exception e) {
			e.printStackTrace();
			msg.append("Excel表格读取异常！批量添加失败！<br/>");
			throw new EntityException(msg.toString());
		}
		Sheet sheet = work.getSheet(0);
		int rows = sheet.getRows();
		if (rows < 2) {
			msg.append("表格为空！<br/>");
		}
		String temp = "";
		Set<PeStudent> scoreSet = new HashSet();
		
		//添加横向权限判断。取得用户可以操作的范围				
		UserSession userSession = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		if(userSession==null){
			throw new EntityException("批量上传学生作业成绩失败,无法取得您的身份信息！");
		}
		
		
		SsoUser ssoUser = (SsoUser)this.getMyListDAO().getById(SsoUser.class, userSession.getSsoUser().getId());
		Set edutypes = ssoUser.getPrPriManagerEdutypes();
		Set sites = ssoUser.getPrPriManagerSites();
		Set grades = ssoUser.getPrPriManagerGrades();
		Set majors = ssoUser.getPrPriManagerMajors();

		for (int i = 1; i < rows; i++) {
			try {
				temp = sheet.getCell(0, i).getContents().trim();
				if (temp == null || "".equals(temp)) {
					msg.append("第" + (i + 1) + "行数据，学号为空！<br/>");
					continue;
				}
				
				DetachedCriteria dcPeStudent = DetachedCriteria.forClass(PeStudent.class);
				dcPeStudent.createCriteria("peGrade", "peGrade");
				dcPeStudent.createCriteria("peMajor", "peMajor");
				dcPeStudent.createCriteria("peEdutype", "peEdutype");
				dcPeStudent.createCriteria("peSite", "peSite");
				dcPeStudent.add(Restrictions.eq("regNo", temp));
				
				List<PeStudent> peStudentList = this.getGeneralDao().getList(dcPeStudent);
				
				if (peStudentList==null || peStudentList.isEmpty()) {
					msg.append("第" + (i + 1) + "行数据，学号不存在！<br/>");
					continue;
				}

				
				PeStudent peStudent =peStudentList.get(0);
				
				temp = sheet.getCell(1, i).getContents().trim();
				if (temp != null && !"".equals(temp)) {
					if (!peStudent.getTrueName().equals(temp)) {
					msg.append("第" + (i + 1) + "行数据，姓名或者学号不正确！<br/>");
					continue;
					}
				}
				
				if (!peStudent.getEnumConstByFlagStudentStatus().getCode().equals("4")){
					msg.append("第" + (i + 1) + "行数据，学生不是在籍状态！<br/>");
					continue;
				}
				
				if (peStudent.getPeEdutype().getName().indexOf("本")==-1){
					msg.append("第" + (i + 1) + "行数据，学生所在层次没有学位英语考试！<br/>");
					continue;
				}
				
				//横向权限检查。
				if(edutypes!=null&&!edutypes.isEmpty()) {
					boolean checked = false;
					for (Object object : edutypes) {
						if(((PrPriManagerEdutype)object).getPeEdutype().getId().equals(peStudent.getPeEdutype().getId())){
							checked = true;
							break;
						}
					}
					if(!checked){
						msg.append("第" + (i + 1) + "行数据，您对这个层次没有操作权限<br/>");
						continue;
					}
				}
				if(sites!=null&&!sites.isEmpty()) {
					boolean checked = false;
					for (Object object : sites) {
						if(((PrPriManagerSite)object).getPeSite().getId().equals(peStudent.getPeSite().getId())){
							checked = true;
							break;
						}
					}
					if(!checked){
						msg.append("第" + (i + 1) + "行数据，您对这个学习中心没有操作权限<br/>");
						continue;	
					}
				}
				if(grades!=null&&!grades.isEmpty()) {
					boolean checked = false;
					for (Object object : grades) {
						if(((PrPriManagerGrade)object).getPeGrade().getId().equals(peStudent.getPeGrade().getId())){
							checked = true;
							break;
						}
					}
					if(!checked){
						msg.append("第" + (i + 1) + "行数据，您对这个年级没有操作权限<br/>");
						continue;	
					}
				}
				if(majors!=null&&!majors.isEmpty()) {
					boolean checked = false;
					for (Object object : majors) {
						if(((PrPriManagerMajor)object).getPeMajor().getId().equals(peStudent.getPeMajor().getId())){
							checked = true;
							break;
						}
					}
					if(!checked){
						msg.append("第" + (i + 1) + "行数据，您对这个专业没有操作权限<br/>");
						continue;		
					}
				}
				//以上横向权限检查。
				
				
					
				temp = sheet.getCell(2, i).getContents().trim();
				if (temp == null || "".equals(temp)) {
					msg.append("第" + (i + 1) + "行数据，成绩不能为空！<br/>");
					continue;
				}
				if (!temp.matches(Const.score)){
					msg.append("第" + (i + 1) + "行数据，成绩格式有误！成绩只能是0至100的整数，0至1位小数<br/>");
					continue;
				}
				
				Double score;
				try {
					score = Double.parseDouble(temp);
				} catch (RuntimeException e) {
					msg.append("第" + (i + 1) + "行数据，成绩格式有误！<br/>");
					continue;
				}
				peStudent.setScoreDegreeEnglish(score);
				if (!scoreSet.add(peStudent)){
					msg.append("第" + (i + 1) + "行数据与文件中前面的数据重复！<br/>");
					continue;
				}
				count++;
			} catch (Exception e) {
				e.printStackTrace();
				msg.append("第" + (i + 1) + "行数据添加失败！<br/>");
				continue;
			}
		}

		if (msg.length() > 0) {
			msg.append("学生学位英语成绩批量上传失败，请修改以上错误之后重新上传！<br/>");
			throw new EntityException(msg.toString());
		}

		for (PeStudent peStudent : scoreSet) {
			try {
				this.getGeneralDao().save(peStudent);
			} catch (Exception e) {
				e.printStackTrace();
				throw new EntityException("批量上传学生学位英语成绩失败");
			}
		}
		return count;
	}
	
	/**
	 * 保存统考成绩
	 * @param file
	 * @return
	 * @throws EntityException
	 */
	public int saveUnitExamScore(File file) throws EntityException{
		StringBuffer msg = new StringBuffer();
		int count = 0;

		Workbook work = null;

		try {
			work = Workbook.getWorkbook(file);
		} catch (Exception e) {
			e.printStackTrace();
			msg.append("Excel表格读取异常！批量添加失败！<br/>");
			throw new EntityException(msg.toString());
		}
		Sheet sheet = work.getSheet(0);
		int rows = sheet.getRows();
		if (rows < 2) {
			msg.append("表格为空！<br/>");
		}
		String temp = "";
		Set<PeStudent> scoreSet = new HashSet();
		
		//添加横向权限判断。取得用户可以操作的范围				
		UserSession userSession = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		if(userSession==null){
			throw new EntityException("批量上传学生作业成绩失败,无法取得您的身份信息！");
		}
		
		
		SsoUser ssoUser = (SsoUser)this.getMyListDAO().getById(SsoUser.class, userSession.getSsoUser().getId());
		Set edutypes = ssoUser.getPrPriManagerEdutypes();
		Set sites = ssoUser.getPrPriManagerSites();
		Set grades = ssoUser.getPrPriManagerGrades();
		Set majors = ssoUser.getPrPriManagerMajors();

		for (int i = 1; i < rows; i++) {
			try {
				temp = sheet.getCell(0, i).getContents().trim();
				if (temp == null || "".equals(temp)) {
					msg.append("第" + (i + 1) + "行数据，学号为空！<br/>");
					continue;
				}
				
				DetachedCriteria dcPeStudent = DetachedCriteria.forClass(PeStudent.class);
				dcPeStudent.createCriteria("peGrade", "peGrade");
				dcPeStudent.createCriteria("peMajor", "peMajor");
				dcPeStudent.createCriteria("peEdutype", "peEdutype");
				dcPeStudent.createCriteria("peSite", "peSite");
				dcPeStudent.add(Restrictions.eq("regNo", temp));
				
				List<PeStudent> peStudentList = this.getGeneralDao().getList(dcPeStudent);
				
				if (peStudentList==null || peStudentList.isEmpty()) {
					msg.append("第" + (i + 1) + "行数据，学号不存在！<br/>");
					continue;
				}

				
				PeStudent peStudent =peStudentList.get(0);
				
				temp = sheet.getCell(1, i).getContents().trim();
				if (temp != null && !"".equals(temp)) {
					if (!peStudent.getTrueName().equals(temp)) {
					msg.append("第" + (i + 1) + "行数据，姓名或者学号不正确！<br/>");
					continue;
					}
				}
				
				if (!peStudent.getEnumConstByFlagStudentStatus().getCode().equals("4")){
					msg.append("第" + (i + 1) + "行数据，学生不是在籍状态！<br/>");
					continue;
				}
				
				if (peStudent.getPeEdutype().getName().indexOf("本")==-1){
					msg.append("第" + (i + 1) + "行数据，学生所在层次没有统考！<br/>");
					continue;
				}
				
				//横向权限检查。
				if(edutypes!=null&&!edutypes.isEmpty()) {
					boolean checked = false;
					for (Object object : edutypes) {
						if(((PrPriManagerEdutype)object).getPeEdutype().getId().equals(peStudent.getPeEdutype().getId())){
							checked = true;
							break;
						}
					}
					if(!checked){
						msg.append("第" + (i + 1) + "行数据，您对这个层次没有操作权限<br/>");
						continue;
					}
				}
				if(sites!=null&&!sites.isEmpty()) {
					boolean checked = false;
					for (Object object : sites) {
						if(((PrPriManagerSite)object).getPeSite().getId().equals(peStudent.getPeSite().getId())){
							checked = true;
							break;
						}
					}
					if(!checked){
						msg.append("第" + (i + 1) + "行数据，您对这个学习中心没有操作权限<br/>");
						continue;	
					}
				}
				if(grades!=null&&!grades.isEmpty()) {
					boolean checked = false;
					for (Object object : grades) {
						if(((PrPriManagerGrade)object).getPeGrade().getId().equals(peStudent.getPeGrade().getId())){
							checked = true;
							break;
						}
					}
					if(!checked){
						msg.append("第" + (i + 1) + "行数据，您对这个年级没有操作权限<br/>");
						continue;	
					}
				}
				if(majors!=null&&!majors.isEmpty()) {
					boolean checked = false;
					for (Object object : majors) {
						if(((PrPriManagerMajor)object).getPeMajor().getId().equals(peStudent.getPeMajor().getId())){
							checked = true;
							break;
						}
					}
					if(!checked){
						msg.append("第" + (i + 1) + "行数据，您对这个专业没有操作权限<br/>");
						continue;		
					}
				}
				//以上横向权限检查。
				
				
				EnumConst enumConst=null;	
				EnumConst englishA = null;
				EnumConst englishB = null;
				temp = sheet.getCell(2, i).getContents().trim();
				if (temp!= null&& !"".equals(temp)) {
					if(temp.equals("不合格")){
						enumConst=this.getMyListDAO().getEnumConstByNamespaceCode("ScoreUniteEnglishA", "1");
					} else if (temp.equals("合格")) {
						enumConst=this.getMyListDAO().getEnumConstByNamespaceCode("ScoreUniteEnglishA", "0");
					} else if (temp.equals("缺考")) {
						enumConst=this.getMyListDAO().getEnumConstByNamespaceCode("ScoreUniteEnglishA", "4");
					} else if (temp.equals("违纪")) {
						enumConst=this.getMyListDAO().getEnumConstByNamespaceCode("ScoreUniteEnglishA", "2");
					} else if (temp.equals("作弊")) {
						enumConst=this.getMyListDAO().getEnumConstByNamespaceCode("ScoreUniteEnglishA", "3");
					} else {
					msg.append("第" + (i + 1) + "行数据，统考英语A成绩状态错误！<br/>");
					continue;
					}
					englishA = enumConst;
				}
				
				temp = sheet.getCell(3, i).getContents().trim();
				if (temp!= null&& !"".equals(temp)) {
					if(temp.equals("不合格")){
						enumConst=this.getMyListDAO().getEnumConstByNamespaceCode("ScoreUniteEnglishB", "1");
					} else if (temp.equals("合格")) {
						enumConst=this.getMyListDAO().getEnumConstByNamespaceCode("ScoreUniteEnglishB", "0");
					} else if (temp.equals("缺考")) {
						enumConst=this.getMyListDAO().getEnumConstByNamespaceCode("ScoreUniteEnglishB", "4");
					} else if (temp.equals("违纪")) {
						enumConst=this.getMyListDAO().getEnumConstByNamespaceCode("ScoreUniteEnglishB", "2");
					} else if (temp.equals("作弊")) {
						enumConst=this.getMyListDAO().getEnumConstByNamespaceCode("ScoreUniteEnglishB", "3");
					} else {
					msg.append("第" + (i + 1) + "行数据，统考英语B成绩状态错误！<br/>");
					continue;
					}
					englishB = enumConst;
				}
				
				//判断，学生只能有一个英语统考成绩
				if(englishA!=null) {
					if(peStudent.getEnumConstByScoreUniteEnglishB()!=null||englishB!=null){
						msg.append("第" + (i + 1) + "行数据，学生只能录入一个英语统考成绩！<br/>");
						continue;
					}
					peStudent.setEnumConstByScoreUniteEnglishA(englishA);
				} else if(englishB!=null) {
					if(peStudent.getEnumConstByScoreUniteEnglishA()!=null){
						msg.append("第" + (i + 1) + "行数据，学生只能录入一个英语统考成绩！<br/>");
						continue;
					}
					peStudent.setEnumConstByScoreUniteEnglishB(englishB);
				}
				
				temp = sheet.getCell(4, i).getContents().trim();
				if (temp!= null&& !"".equals(temp)) {
					if(temp.equals("不合格")){
						enumConst=this.getMyListDAO().getEnumConstByNamespaceCode("ScoreUniteComputer", "1");
					} else if (temp.equals("合格")) {
						enumConst=this.getMyListDAO().getEnumConstByNamespaceCode("ScoreUniteComputer", "0");
					} else if (temp.equals("缺考")) {
						enumConst=this.getMyListDAO().getEnumConstByNamespaceCode("ScoreUniteComputer", "4");
					} else if (temp.equals("违纪")) {
						enumConst=this.getMyListDAO().getEnumConstByNamespaceCode("ScoreUniteComputer", "2");
					} else if (temp.equals("作弊")) {
						enumConst=this.getMyListDAO().getEnumConstByNamespaceCode("ScoreUniteComputer", "3");
					} else {
					msg.append("第" + (i + 1) + "行数据，统考计算机成绩状态错误！<br/>");
					continue;
					}
					peStudent.setEnumConstByScoreUniteComputer(enumConst);
				} else {
					if (englishA==null&&englishB==null) {
						msg.append("第" + (i + 1) + "行数据，没有填写任何统考成绩！<br/>");
						continue;
					}
				}
				
				if (!scoreSet.add(peStudent)){
					msg.append("第" + (i + 1) + "行数据与文件中前面的数据重复，或者上传的学生成绩状态与学生现有的成绩重复！<br/>");
					continue;
				}
				count++;
			} catch (Exception e) {
				e.printStackTrace();
				msg.append("第" + (i + 1) + "行数据添加失败！<br/>");
				continue;
			}
		}

		if (msg.length() > 0) {
			msg.append("学生统考成绩批量上传失败，请修改以上错误之后重新上传！<br/>");
			throw new EntityException(msg.toString());
		}

		for (PeStudent peStudent : scoreSet) {
			try {
				this.getGeneralDao().save(peStudent);
			} catch (Exception e) {
				e.printStackTrace();
				throw new EntityException("批量上传学生统考成绩失败");
			}
		}
		return count;
	}
}
