package com.whaty.platform.entity.web.action.teaching.elective;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PrTchStuElective;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.teaching.elective.ManualOpenCourseService;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class ManualConfirmCourseAction extends MyBaseAction<PrTchStuElective> {

	private ManualOpenCourseService manualOpenCourseService;
	
	
	public ManualOpenCourseService getManualOpenCourseService() {
		return manualOpenCourseService;
	}

	public void setManualOpenCourseService(
			ManualOpenCourseService manualOpenCourseService) {
		this.manualOpenCourseService = manualOpenCourseService;
	}

	@Override
	public void initGrid() {

		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().setTitle("手动开课");

		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("姓名"), "peStudent.trueName");
		this.getGridConfig().addColumn(this.getText("学号"), "peStudent.regNo");
		this.getGridConfig().addColumn(this.getText("学习中心"),
				"peStudent.peSite.name");
		this.getGridConfig().addColumn(this.getText("层次"),
				"peStudent.peEdutype.name");
		this.getGridConfig().addColumn(this.getText("专业"),
				"peStudent.peMajor.name");
		this.getGridConfig().addColumn(this.getText("年级"),
				"peStudent.peGrade.name");
		this.getGridConfig().addColumn(this.getText("课程"),
				"prTchOpencourse.peTchCourse.name");
//		this.getGridConfig().addColumn(this.getText("优先级"), "pri");

		this.getGridConfig().addMenuFunction("开课", "openCourse", "");
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PrTchStuElective.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/teaching/manualConfirmCourse";

	}

	public void setBean(PrTchStuElective instance) {
		super.superSetBean(instance);
	}

	public PrTchStuElective getBean() {
		return super.superGetBean();
	}

	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PrTchStuElective.class);
		dc.createCriteria("peStudent", "peStudent").createAlias(
				"enumConstByFlagStudentStatus", "enumConstByFlagStudentStatus")
				.createAlias("peSite", "peSite").createAlias("peEdutype",
						"peEdutype").createAlias("peMajor", "peMajor")
				.createAlias("peGrade", "peGrade");
		dc.createCriteria("prTchOpencourse", "prTchOpencourse").createAlias(
				"peTchCourse", "peTchCourse").createAlias("peSemester", "peSemester");
		dc.add(Restrictions.eq("enumConstByFlagStudentStatus.code",
				"4"));
		dc.add(Restrictions.eq("peSemester.flagActive", "1"));
		dc.createCriteria("enumConstByFlagElectiveAdmission", "enumConstByFlagElectiveAdmission");
		dc.add(Restrictions.eq("enumConstByFlagElectiveAdmission.code", "0"));
		return dc;
	}

	@Override
	public Map updateColumn() {
		if (this.getColumn().equals("openCourse")) {
			Map map = new HashMap();
			String[] ids = getIds().split(",");
			List idList = new ArrayList();
			
			for (int i = 0; i < ids.length; i++) {
				idList.add(ids[i]);
			}
			try {
				this.getManualOpenCourseService().updateOpenCourse(idList);
			} catch (EntityException e) {
				map.clear();
				map.put("success", "false");
				map.put("info", "开课失败" + e.getMessage());
				return map;
			}
			map.clear();
			map.put("success", "true");
			map.put("info", this.getText("操作成功"));
			return map;
		} else {
			return super.updateColumn();
		}
	}

}
