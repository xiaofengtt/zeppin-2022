package com.whaty.platform.entity.web.action.exam.finalExam;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeSemester;
import com.whaty.platform.entity.bean.PrExamBooking;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.exam.finalExam.PrExamBookingService;
import com.whaty.platform.entity.web.action.MyBaseAction;
/**
 * 学生考场安排信息查询，自动分配考场
 * @author zqf
 *
 */
public class ExamInfoSearchAction extends MyBaseAction {
	private PrExamBookingService prExamBookingService;

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false,false,false);
		this.getGridConfig().setTitle(this.getText("学生考场安排表"));
		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("考试学期"),"peSemester.name");
		this.getGridConfig().addColumn(this.getText("学生姓名"), "prTchStuElective.peStudent.trueName");
		this.getGridConfig().addColumn(this.getText("学号"), "prTchStuElective.peStudent.regNo");
		this.getGridConfig().addColumn(this.getText("学习中心"),"prTchStuElective.peStudent.peSite.name");
		this.getGridConfig().addColumn(this.getText("层次"),"prTchStuElective.peStudent.peEdutype.name");
		this.getGridConfig().addColumn(this.getText("专业"),"prTchStuElective.peStudent.peMajor.name");
		this.getGridConfig().addColumn(this.getText("考试课程"), "prTchStuElective.prTchProgramCourse.peTchCourse.name");
		this.getGridConfig().addColumn(this.getText("试卷类型"), "peExamNo.paperType");
		this.getGridConfig().addColumn(this.getText("考试开始时间"), "peExamNo.startDatetime");
		this.getGridConfig().addColumn(this.getText("考试结束时间"),"peExamNo.endDatetime");
//		this.getGridConfig().addColumn(this.getText("考场号"), "peExamRoom.code");
		this.getGridConfig().addColumn(this.getText("教室"), "peExamRoom.classroom");
		this.getGridConfig().addColumn(this.getText("座位号"), "seatNo");
		this.getGridConfig().addColumn(this.getText("考试场次"),"peExamNo.name");
	}
	
	/**
	 * 自动分配考场
	 */
	public String autoExamRoom(){
		this.setTogo("back");
		try {
			DetachedCriteria dc = DetachedCriteria.forClass(PeSemester.class);
			dc.add(Restrictions.eq("flagActive", "1"));
			List<PeSemester> peSemesterList =this.getGeneralService().getList(dc);
			if(peSemesterList!=null&&peSemesterList.size()>0){
				PeSemester peSemester = peSemesterList.get(0);
				Date now = new Date();
				if(peSemester.getFinalExamStartDate()==null){
					this.setMsg("请先设置本学期的考试开始时间");
					return "msg";
				}
				if(now.after(peSemester.getFinalExamStartDate())){
					this.setMsg("分配考场必须在考试开始之前进行");
					return "msg";
				}
			}
			
			this.setMsg(this.getPrExamBookingService().saveAutoExamRoom());
			this.getPrExamBookingService().saveModifyRoomNo();
		} catch (Exception e) {
			
			//判断异常类型
			if(e.getClass().toString().indexOf("EntityException")>=0){
				this.setMsg(e.getMessage());
			} else{
				e.printStackTrace();
				this.setMsg("自动分配考场失败，请在 考场安排查询 检查数据是否正确。可能的原因：考场的学期是否和考生的学期不一致，学生的场次座位号等信息不完整。");
			}
		}
		return "msg";
	}
	
	@Override
	public void setEntityClass() {
		this.entityClass = PrExamBooking.class;
		}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/exam/examInfoSearch";
	}
	
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PrExamBooking.class);
		dc.createCriteria("peSemester", "peSemester");
		dc.createCriteria("peExamNo", "peExamNo");
		dc.createCriteria("peExamRoom", "peExamRoom");
		DetachedCriteria dcPrTchStuElective = dc.createCriteria("prTchStuElective", "prTchStuElective");
		dcPrTchStuElective.createCriteria("peStudent", "peStudent")
			.createAlias("peSite", "peSite")
			.createAlias("peEdutype", "peEdutype")
			.createAlias("peMajor", "peMajor");
		dcPrTchStuElective.createCriteria("prTchProgramCourse", "prTchProgramCourse")
			.createCriteria("peTchCourse", "peTchCourse");
		return dc;
	}
	
	
	public PrExamBookingService getPrExamBookingService() {
		return prExamBookingService;
	}

	public void setPrExamBookingService(PrExamBookingService prExamBookingService) {
		this.prExamBookingService = prExamBookingService;
	}

}
