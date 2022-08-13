package com.whaty.platform.entity.web.action.exam.mainCourse;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeRecruitplan;
import com.whaty.platform.entity.bean.PrExamStuMaincourse;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.exam.mainCourse.PrExamOpenMaincourseService;
/**
 * 考场分配结果查询
 * @author zqf
 *
 */
public class PeMainCourseExamRoomAction extends MainCourseBaseAction {
	private  PrExamOpenMaincourseService prExamOpenMaincourseService;
	public PrExamOpenMaincourseService getPrExamOpenMaincourseService() {
		return prExamOpenMaincourseService;
	}

	public void setPrExamOpenMaincourseService(
			PrExamOpenMaincourseService prExamOpenMaincourseService) {
		this.prExamOpenMaincourseService = prExamOpenMaincourseService;
	}

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false,false,true);
		this.getGridConfig().setTitle(this.getText(this.getSemesterName() + "主干课程考场分配结果"));
		this.getGridConfig().addColumn(this.getText("id"),"id",false);
		this.getGridConfig().addColumn(this.getText("学生姓名"),"peStudent.trueName", true, false, true, "", false, 0);
		this.getGridConfig().addColumn(this.getText("学号"),"peStudent.regNo", true, false, true, "", false, 0);
		this.getGridConfig().addColumn(this.getText("学习中心"),"peStudent.peSite.name");
		this.getGridConfig().addColumn(this.getText("层次"),"peStudent.peEdutype.name");
		this.getGridConfig().addColumn(this.getText("专业"),"peStudent.peMajor.name");
		this.getGridConfig().addColumn(this.getText("年级"),"peStudent.peGrade.name");
		this.getGridConfig().addColumn(this.getText("考试课程"),"prExamOpenMaincourse.peTchCourse.name");
//		ColumnConfig c_name = new ColumnConfig(this.getText("考场号"), "peExamMaincourseRoom.name");
//		c_name.setComboSQL("select t.id,t.name  from pe_pri_role t, enum_const e where t.flag_role_type=e.id and e.code='3' and e.namespace='FlagRoleType' order by t.name");
//		this.getGridConfig().addColumn(c_name);
		this.getGridConfig().addColumn(this.getText("考场名"),"peExamMaincourseRoom.name", true, true, true, "TextField", false, 50);
		this.getGridConfig().addColumn(this.getText("考场编号"),"peExamMaincourseRoom.code", true, false, true, "TextField", false, 0);
		this.getGridConfig().addColumn(this.getText("座位号"),"seatNo", true, true, true, "regex:new RegExp(/^\\d{3}$/),regexText:'座位号输入格式：3位整数',");
		this.getGridConfig().addColumn(this.getText("考试开始时间"),"prExamOpenMaincourse.peExamMaincourseNo.startDatetime");
		this.getGridConfig().addColumn(this.getText("考试结束时间"),"prExamOpenMaincourse.peExamMaincourseNo.endDatetime");
//		this.getGridConfig().addColumn(this.getText("考场地址"),"peExamMaincourseRoom.examPlace");
	}
	public void checkBeforeUpdate() throws EntityException {
		int seat = 0;
		try {
		 seat = Integer.parseInt(this.getBean().getSeatNo());

		} catch (Exception e) {
			e.printStackTrace();
			throw new EntityException("座位号错误！");
		}
		if(seat>30){
			throw new EntityException("座位号不能大于30！");
		}
	}
	public String autoMainExamRoom(){
		this.setTogo("back");
		try {
			this.setMsg(this.getPrExamOpenMaincourseService().saveMainExamRoom());
		} catch (EntityException e) {
			e.printStackTrace();
			this.setMsg(e.getMessage());
		}
		return "msg";
	}
	@Override
	public void setEntityClass() {
		this.entityClass = PrExamStuMaincourse.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/exam/peMainCourseExamRoom";
	}

	public void setBean(PrExamStuMaincourse bean){
		super.superSetBean(bean);
	}
	public PrExamStuMaincourse getBean(){
		return (PrExamStuMaincourse)super.superGetBean();
	}
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PrExamStuMaincourse.class);
		dc.createCriteria("peStudent", "peStudent")
			.createAlias("enumConstByFlagStudentStatus", "enumConstByFlagStudentStatus")
			.createAlias("peSite", "peSite")
			.createAlias("peEdutype", "peEdutype")
			.createAlias("peMajor", "peMajor")
			.createAlias("peGrade", "peGrade");
		dc.createCriteria("prExamOpenMaincourse","prExamOpenMaincourse")
				.createAlias("peTchCourse", "peTchCourse")
				.createCriteria("peExamMaincourseNo", "peExamMaincourseNo")
				.createAlias("peSemester", "peSemester");
		dc.createCriteria("peExamMaincourseRoom", "peExamMaincourseRoom", DetachedCriteria.LEFT_JOIN);
		dc.add(Restrictions.eq("peSemester.flagActive", "1"));
		dc.add(Restrictions.eq("enumConstByFlagStudentStatus.code", "4"));
		return dc;
	}
}
