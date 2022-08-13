package com.whaty.platform.entity.web.action.exam.mainCourse;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeSemester;
import com.whaty.platform.entity.bean.PrExamStuMaincourse;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.util.JsonUtil;
/**
 * 主干而课程预约查询、删除操作
 * @author zqf
 *
 */
public class MaincourseElectiveManageAction extends MainCourseBaseAction {

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, true, false);
		this.getGridConfig().setTitle(this.getText(this.getSemesterName() + "主干课程考试预约情况"));
		this.getGridConfig().addColumn(this.getText("id"),"id",false);
		this.getGridConfig().addColumn(this.getText("学生姓名"),"peStudent.trueName");
		this.getGridConfig().addColumn(this.getText("学号"),"peStudent.regNo");
		this.getGridConfig().addColumn(this.getText("学习中心"),"peStudent.peSite.name");
		this.getGridConfig().addColumn(this.getText("层次"),"peStudent.peEdutype.name");
		this.getGridConfig().addColumn(this.getText("专业"),"peStudent.peMajor.name");
		this.getGridConfig().addColumn(this.getText("年级"),"peStudent.peGrade.name");
		this.getGridConfig().addColumn(this.getText("考试课程"),"prExamOpenMaincourse.peTchCourse.name");
		this.getGridConfig().addColumn(this.getText("考试开始时间"),"prExamOpenMaincourse.peExamMaincourseNo.startDatetime");
		this.getGridConfig().addColumn(this.getText("考试结束时间"),"prExamOpenMaincourse.peExamMaincourseNo.endDatetime");
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PrExamStuMaincourse.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/exam/maincourseElectiveManage";
	}
	
	public void checkBeforeDelete(List idList) throws EntityException{
		Date currentDate = new Date();
		DetachedCriteria dcPeSemester = DetachedCriteria.forClass(PeSemester.class);
		dcPeSemester.add(Restrictions.eq("flagActive", "1"));
		List<PeSemester> list = new ArrayList();
		try {
			list = this.getGeneralService().getList(dcPeSemester);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(list.size() > 0){
			PeSemester temp = list.get(0);
			if(temp.getMainCourseStartDate() != null && temp.getMainCourseEndDate() != null
					&& (currentDate.before(temp.getMainCourseStartDate()) || currentDate.after(temp.getMainCourseEndDate()))){
				throw new EntityException("不在主干课程预约报名时间之内，不能删除学生预约记录");
			}
		}
		
	}
	public DetachedCriteria initDetachedCriteria() {
		JsonUtil.setDateformat("yyyy-MM-dd HH:mm:ss");
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
		return dc;
	}
}
