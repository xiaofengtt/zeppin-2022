package com.whaty.platform.entity.web.action.teaching.elective;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeSemester;
import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.bean.PeTchCourse;
import com.whaty.platform.entity.bean.PrTchOpencourse;
import com.whaty.platform.entity.bean.PrTchProgramCourse;
import com.whaty.platform.entity.bean.PrTchStuElective;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

/**
 * 代学生选课进入二级页面Action zhangyu
 * 
 * @author Administrator
 * 
 */
public class PeStuElectiveAction extends MyBaseAction {

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false);

		this.getGridConfig().setTitle("选择选修课");

		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("课程编号"),
				"peTchCourse.code", true, true, true, "TextField", false, 50);
		this.getGridConfig().addColumn(this.getText("课程名称"),
				"peTchCourse.name", true, true, true, "TextField", false, 50);
		this
				.getGridConfig()
				.addColumn(
						this.getText("学分"),
						"credit",
						true,
						true,
						true,
						"regex:new RegExp(/^\\d{1,2}(\\.\\d{0,1})?$/),regexText:'学分输入格式：1到2位整数 0到1位小数',");
		this.getGridConfig().addColumn(this.getText("主干课程标记"),
				"enumConstByFlagIsMainCourse.name", true, true, true,
				"TextField", false, 50);
		this.getGridConfig().addColumn(this.getText("建议学期"), "unit", true,
				true, true,
				"regex:new RegExp(/^\\d{1}?$/),regexText:'输入格式：1位整数',");

		this.getGridConfig().addMenuFunction("选择",
				"/entity/teaching/peStuElective_select.action", false, false);
		this.getGridConfig().addMenuScript("返回", "{window.history.go(-2)}");
	}

	/**
	 * 选课操作
	 * 
	 */
	public String select() {
		StringBuffer sb = new StringBuffer();
		StringBuffer sb1 = new StringBuffer();
		String[] courseIds = this.getIds().split(",");
		String[] stuIds = ((String) ActionContext.getContext().getSession()
				.get("electiveForStudentIds")).split(",");
		for (int i = 0; i < stuIds.length; i++) {
			PeStudent stu = (PeStudent) this.getMyListService().getById(
					PeStudent.class, stuIds[i]);
//			String electiveDate = new Date().toString();
			for (int j = 0; j < courseIds.length; j++) {

				PrTchProgramCourse stuProgramCourse = (PrTchProgramCourse) this
						.getMyListService().getById(PrTchProgramCourse.class,
								courseIds[j]);
				PeTchCourse course = (PeTchCourse) this.getMyListService()
						.getById(PeTchCourse.class,
								stuProgramCourse.getPeTchCourse().getId());
				PrTchProgramCourse prTchProgramCourse = (PrTchProgramCourse) this
						.getMyListService().getById(PrTchProgramCourse.class,
								courseIds[j]);

				PrTchStuElective stuElective = new PrTchStuElective();
				stuElective.setElectiveDate(new Date());
				stuElective.setPeStudent(stu);
				stuElective.setPrTchProgramCourse(prTchProgramCourse);
				// stuElective.setPri("0");
				// 初始为 未开课
				EnumConst flagElectiveAdmission = this.getMyListService()
						.getEnumConstByNamespaceCode("FlagElectiveAdmission",
								"0");
				stuElective
						.setEnumConstByFlagElectiveAdmission(flagElectiveAdmission);
				// 初始为 未录入
				EnumConst flagScoreStatus = this.getMyListService()
						.getEnumConstByNamespaceCode("FlagScoreStatus", "0");
				stuElective.setEnumConstByFlagScoreStatus(flagScoreStatus);
				// 初始为 未发布
				EnumConst flagScorePub = this.getMyListService()
						.getEnumConstByNamespaceCode("FlagScorePub", "0");
				stuElective.setEnumConstByFlagScorePub(flagScorePub);

				// 设置 opencourse

				String courseId = "";
				PrTchProgramCourse stuPrTchProgramCourse = (PrTchProgramCourse) this
						.getMyListService().getById(PrTchProgramCourse.class,
								courseIds[j]);
				courseId = stuPrTchProgramCourse.getPeTchCourse().getId();

				DetachedCriteria dcSemester = DetachedCriteria
						.forClass(PeSemester.class);
				dcSemester.add(Restrictions.eq("flagNextActive", "1"));
				String semesterId = "";
				try {
					List semesterList = this.getGeneralService().getList(
							dcSemester);
					PeSemester semester = (PeSemester) semesterList.get(0);
					semesterId = semester.getId();
				} catch (EntityException e) {
					e.printStackTrace();
				}
				DetachedCriteria dcOpenCourse = DetachedCriteria
						.forClass(PrTchOpencourse.class);
				dcOpenCourse.add(Restrictions.eq("peSemester.id", semesterId));
				dcOpenCourse.add(Restrictions.eq("peTchCourse.id", courseId));
				PrTchOpencourse prTchOpencourse = new PrTchOpencourse();
				try {
					List prTchOpencourseList = this.getGeneralService()
							.getList(dcOpenCourse);
					prTchOpencourse = (PrTchOpencourse) prTchOpencourseList
							.get(0);
				} catch (Exception e) {
					e.printStackTrace();
					sb.append("课程 " + course.getName() + " 没有开课！");
					continue;
				}
				stuElective.setPrTchOpencourse(prTchOpencourse);
				stuElective
						.setSsoUser(((UserSession) ActionContext.getContext()
								.getSession().get(
										SsoConstant.SSO_USER_SESSION_KEY))
								.getSsoUser());

				try {
					this.getGeneralService().save(stuElective);
					sb1.append("代学生 " + stu.getTrueName() + "选课 " + course.getName() + "选课成功。<br>");
				} catch (Exception e) {
					sb.append("学生 " + stu.getTrueName() + " 已选过课程 "
							+ course.getName() + "。" + "<br>");
					continue;
				}
			}
		}
		
		this.setMsg(sb.toString() + sb1.toString());
		return "msg";
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PrTchOpencourse.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/teaching/peStuElective";

	}

	public void setBean(PrTchProgramCourse instance) {
		super.superSetBean(instance);
	}

	public PrTchProgramCourse getBean() {
		return (PrTchProgramCourse) super.superGetBean();
	}

	@Override
	public String execute() {
		ActionContext.getContext().getSession().put("electiveForStudentIds",
				this.getIds());
		return super.execute();
	}

	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc1 = DetachedCriteria
				.forClass(PrTchProgramCourse.class);
		DetachedCriteria dc2 = DetachedCriteria.forClass(PeStudent.class);

		String[] stuIds = ((String) ActionContext.getContext().getSession()
				.get("electiveForStudentIds")).split(",");
		dc2.add(Restrictions.eq("id", stuIds[0]));
		List stuList = new ArrayList();
		try {
			stuList = this.getGeneralService().getList(dc2);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		PeStudent studentExample = (PeStudent) stuList.get(0);

		dc1.createCriteria("peTchCourse", "peTchCourse");
		dc1.createCriteria("enumConstByFlagIsMainCourse",
				"enumConstByFlagIsMainCourse");
		dc1.createCriteria("peTchProgramGroup", "peTchProgramGroup")
				.createCriteria("peTchProgram", "peTchProgram").createAlias(
						"peEdutype", "peEdutype").createAlias("peGrade",
						"peGrade").createAlias("peMajor", "peMajor")
						.createAlias("enumConstByFlagMajorType", "enumConstByFlagMajorType");

		dc1.add(Restrictions.eq("peEdutype.id", studentExample.getPeEdutype()
				.getId()));
		dc1.add(Restrictions.eq("peGrade.id", studentExample.getPeGrade()
				.getId()));
		dc1.add(Restrictions.eq("peMajor.id", studentExample.getPeMajor()
				.getId()));
		dc1.add(Restrictions.eq("enumConstByFlagMajorType", studentExample.getEnumConstByFlagMajorType()));
		
		return dc1;
	}

}
