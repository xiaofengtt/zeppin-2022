package com.whaty.platform.entity.web.action.exam.finalExam;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PrExamBooking;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.util.Const;
/**
 * 人工调整考场安排
 * @author zqf
 *
 */
public class PrExamBookingAction extends MyBaseAction {

	@Override
	public void initGrid() {
		this.getGridConfig().setTitle("人工考场安排调整");
		this.getGridConfig().setCapability(false, false, true);
		this.getGridConfig().addColumn(this.getText("id"),"id",false);
		this.getGridConfig().addColumn(this.getText("考试学期"),"peSemester.name",false,false,true,"");
		this.getGridConfig().addColumn(this.getText("学生姓名"),"prTchStuElective.peStudent.trueName");
		this.getGridConfig().addColumn(this.getText("学号"),"prTchStuElective.peStudent.regNo");
		this.getGridConfig().addColumn(this.getText("考试课程"),"prTchStuElective.prTchProgramCourse.peTchCourse.name");
		this.getGridConfig().addColumn(this.getText("学习中心"),"prTchStuElective.peStudent.peSite.name");
		this.getGridConfig().addColumn(this.getText("层次"),"prTchStuElective.peStudent.peEdutype.name");
		this.getGridConfig().addColumn(this.getText("专业"),"prTchStuElective.peStudent.peMajor.name");
		this.getGridConfig().addColumn(this.getText("年级"),"prTchStuElective.peStudent.peGrade.name");
//		this.getGridConfig().addColumn(this.getText("考场"),"peExamRoom.name");
		ColumnConfig column1 = new ColumnConfig(this.getText("考场"),"peExamRoom.name");
		column1.setComboSQL(" select room.id, room.name     from pe_semester semester, pe_exam_room room, pe_exam_no examNo   "+
				" where examNo.fk_semester_id = semester.id      and semester.flag_active = '1'     and room.fk_exam_no = examNo.Id         ");
		this.getGridConfig().addColumn(column1);
		this.getGridConfig().addColumn(this.getText("教室"),"peExamRoom.classroom");
//		this.getGridConfig().addColumn(this.getText("考场编号"),"peExamRoom.code");
		this.getGridConfig().addColumn(this.getText("座位号"),"seatNo",true, true, true, Const.number_for_extjs);
//		this.getGridConfig().addColumn(this.getText("考试场次"),"peExamNo.name");
		ColumnConfig column2 = new ColumnConfig(this.getText("考试场次"),"peExamNo.name");
		column2.setComboSQL(" select examNo.id, examNo.name     from pe_semester semester, pe_exam_no examNo   "+
				" where examNo.fk_semester_id = semester.id      and semester.flag_active = '1' ");
		this.getGridConfig().addColumn(column2);
	}
	public void setBean(PrExamBooking instance){
		this.superSetBean(instance);
	}
	
	public PrExamBooking getBean(){
		return (PrExamBooking)this.superGetBean();
	}
	@Override
	public void setEntityClass() {
		this.entityClass = PrExamBooking.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/exam/prExamBooking";
	}

	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PrExamBooking.class);
		dc.createCriteria("peSemester", "peSemester").add(Restrictions.eq("flagActive", "1"));
		dc.createCriteria("peExamNo", "peExamNo",DetachedCriteria.LEFT_JOIN);
		DetachedCriteria dcPrTchStuElective = dc.createCriteria("prTchStuElective", "prTchStuElective");
		dcPrTchStuElective.createCriteria("peStudent", "peStudent")
				.createAlias("peSite", "peSite")
				.createAlias("peEdutype", "peEdutype")
				.createAlias("peMajor", "peMajor")
				.createAlias("peGrade", "peGrade");
		dcPrTchStuElective.createCriteria("prTchProgramCourse", "prTchProgramCourse")
							.createCriteria("peTchCourse", "peTchCourse");
		dc.createCriteria("peExamRoom", "peExamRoom", DetachedCriteria.LEFT_JOIN);
		return dc;
	}
	@Override
	public void checkBeforeUpdate() throws EntityException {
		String  sql = "";
		List list = new ArrayList();
		try {
			sql = " select booking.id                                     " + 
			"   from pr_exam_booking booking, pe_semester semester  " +
			"  where booking.fk_semester_id = semester.id           " +
			"    and semester.flag_active = '1'                     " +
			"    and booking.id = '"+this.getBean().getId()+"'                               " ;
			
			list = this.getGeneralService().getBySQL(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(list.size() == 0){
			throw new EntityException("操作失败<br/>只能操作当前活动考试学期内的信息");
		}
		try {

			sql = " select room.*, examNo.*, semester.name           " + 
			"     from pe_semester semester, pe_exam_room room, pe_exam_no examNo           " + 
			"    where examNo.fk_semester_id = semester.id           " + 
			"      and semester.flag_active = '1'           " + 
			"      and room.fk_exam_no = examNo.Id           " + 
			"      and room.name ='"+this.getBean().getPeExamRoom().getName()+"'                               " +
			"      and examNo.Name ='"+this.getBean().getPeExamNo().getName()+"'                               " ;
			
			list = this.getGeneralService().getBySQL(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(list.size() == 0){
			throw new EntityException("操作失败<br/>只能操作当前活动考试学期内的信息,并且考场的所在场次应该与所选的考试场次一致。");
		}
		try {
			sql = " select booking.id    " + 
				"     from pr_exam_booking booking, pe_exam_room room    " + 
				"     where booking.fk_exam_room_id = room.id    " + 
				"      and booking.id != '"+this.getBean().getId()+"'                               " +
				"      and room.name ='"+this.getBean().getPeExamRoom().getName()+"'                               " +
				"      and booking.seat_no ='"+this.getBean().getSeatNo()+"'                              " ;
			
			list = this.getGeneralService().getBySQL(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(list.size() > 0){
			throw new EntityException("操作失败<br/>考场："+this.getBean().getPeExamRoom().getName()+"，座位号："+this.getBean().getSeatNo()+"，已经被使用。");
		}
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
	
	
	
}
