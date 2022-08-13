package com.whaty.platform.entity.web.action.exam.finalExam;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.SystemApply;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.studentStatus.register.PrRecPriPayApplyAction;

public class ExamScoreReCheckAction extends PrRecPriPayApplyAction {

	
	@Override
	public void initGrid() {
		this.getGridConfig().addMenuFunction("申请通过", "CheckForPass","");
		this.getGridConfig().addMenuFunction("取消申请通过", "CancelForPass", "");
		this.getGridConfig().addMenuFunction("申请不通过", "CheckForNoPass", "");
		this.getGridConfig().addMenuFunction("取消申请不通过", "CancelForNoPass", "");
		
		this.getGridConfig().setCapability(false, false, false,true);
		this.getGridConfig().setTitle(this.getText("考试成绩复查审核"));
		this.getGridConfig().addColumn(this.getText("id"), "id", false);
//		this.getGridConfig().addColumn(this.getText("学号"), "peStudent.regNo");
//		this.getGridConfig().addColumn(this.getText("peStudent.trueName"), "peStudent.trueName");
//		this.getGridConfig().addColumn(this.getText("证件号码"), "peStudent.prStudentInfo.cardNo");
//		this.getGridConfig().addColumn(this.getText("peStudent.peGrade.name"), "peStudent.peGrade.name");
//		this.getGridConfig().addColumn(this.getText("peStudent.peSite.name"), "peStudent.peSite.name");
//		this.getGridConfig().addColumn(this.getText("peStudent.peEdutype.name"), "peStudent.peEdutype.name");
//		this.getGridConfig().addColumn(this.getText("peStudent.peMajor.name"), "peStudent.peMajor.name");
//		this.getGridConfig().addColumn(this.getText("审核状态"), "enumConstByFlagApplyStatus.name");
		this.getGridConfig().addColumn(this.getText("学期"), "combobox_peSemester.semester");
		this.getGridConfig().addColumn(this.getText("学号"), "regNo");
		this.getGridConfig().addColumn(this.getText("姓名"), "trueName");
		this.getGridConfig().addColumn(this.getText("证件号码"), "cardNo");
		this.getGridConfig().addColumn(this.getText("年级"), "combobox_peGrade.grade");
		this.getGridConfig().addColumn(this.getText("学习中心"), "combobox_peSite.site");
		this.getGridConfig().addColumn(this.getText("层次"), "combobox_peEdutype.edutype");
		this.getGridConfig().addColumn(this.getText("专业"), "combobox_peMajor.major");
		this.getGridConfig().addColumn(this.getText("课程名称"), "combobox_peTchCourse.course");		
		this.getGridConfig().addColumn(this.getText("试卷类型"), "examPaper");	
		this.getGridConfig().addColumn(this.getText("考场"), "classroom");
		this.getGridConfig().addColumn(this.getText("座位号"), "seat");
		
		this.getGridConfig().addColumn(this.getText("审核状态"), "combobox_enumConstByFlagApplyStatus.constName");
		
		
		this.getGridConfig().addColumn(this.getText("申请时间"), "applyDate");
		this.getGridConfig().addColumn(this.getText("审核时间"), "checkDate");
		this.getGridConfig().addColumn("申请的课程", "applyNote", false, false,
				false, "TextField", false, 100);
//		this.getGridConfig().addRenderScript(this.getText("申请的课程"),
//				"{var va=${value};var obj = va.substring(va.indexOf('|')+1,va.length); " +
//				" return obj.substring(0,obj.indexOf('|'));}",
//				"applyNote");
		this.getGridConfig().addRenderScript(this.getText("申请的原因"),
				"{var va=${value};var obj = va.substring(va.indexOf('|')+1,va.length); " +
				" return obj.substring(obj.indexOf('|')+1,obj.length);}",
				"applyNote");
	}


	public Page list() {
		StringBuffer sql_temp = new StringBuffer();
		sql_temp.append("   select DISTINCT apply.id          as id,                 			");	
		sql_temp.append("                   semester.name     as semester,                                ");
		sql_temp.append("                   student.reg_no    as regNo,                                   ");
		sql_temp.append("                   student.true_name as trueName,                                ");
		sql_temp.append("                   stuInfo.Card_No   as cardNo,                                  ");
		sql_temp.append("                   grade.name        as grade,                                   ");
		sql_temp.append("                   site.name         as site,                                    ");
		sql_temp.append("                   edutype.name      as edutype,                                 ");
		sql_temp.append("                   major.name        as major,                                   ");
		sql_temp.append("                   course.name       as course,                                  ");
		sql_temp.append("                   examNo.Paper_Type as examPaper,                               ");
		sql_temp.append("                   room.classroom    as classroom,                               ");
		sql_temp.append("                   booking.seat_no   as seat,                                    ");
		sql_temp.append("                   const2.name       as constName,                                   ");
		sql_temp.append("                   apply.apply_date  as applyDate,                               ");
		sql_temp.append("                   apply.check_date  as checkDate,                               ");
		sql_temp.append("                   apply.apply_note  as applyNote                                ");
		sql_temp.append("     from system_apply        apply,                                             ");
		sql_temp.append("          enum_const          const1,                                            ");
		sql_temp.append("          enum_const          const2,                                            ");
		sql_temp.append("          pe_student          student,                                           ");
		sql_temp.append("          pr_student_info     stuInfo,                                           ");
		sql_temp.append("          pe_grade            grade,                                             ");
		sql_temp.append("          pe_site             site,                                              ");
		sql_temp.append("          pe_edutype          edutype,                                           ");
		sql_temp.append("          pe_major            major,                                             ");
		sql_temp.append("          pe_tch_course       course,                                            ");
		sql_temp.append("          pr_exam_booking     booking,                                           ");
		sql_temp.append("          pr_tch_stu_elective elective,                                          ");
		sql_temp.append("          pr_tch_opencourse   opencourse,                                        ");
		sql_temp.append("          pe_semester         semester,                                          ");
		sql_temp.append("          PE_EXAM_ROOM        room,                                              ");
		sql_temp.append("          PE_EXAM_NO          examNo                                             ");
		sql_temp.append("    where apply.apply_type = const1.id                                           ");
		sql_temp.append("      and const1.code = '18'                                                     ");
		sql_temp.append("      and apply.flag_apply_status = const2.id                                    ");
		sql_temp.append("      and student.fk_student_info_id = stuInfo.Id                                ");
		sql_temp.append("      and student.fk_site_id = site.id                                           ");
		sql_temp.append("      and student.fk_major_id = major.id                                         ");
		sql_temp.append("      and student.fk_grade_id = grade.id                                         ");
		sql_temp.append("      and student.fk_edutype_id = edutype.id                                     ");
		sql_temp.append("      and apply.fk_student_id = student.id                                       ");
		sql_temp.append("      and booking.id =                                                           ");
		sql_temp.append("          substr(apply.apply_note, '0', instr(apply.apply_note, '|') - 1)        ");
		sql_temp.append("      and booking.fk_semester_id = semester.id                                   ");
		sql_temp.append("      and booking.fk_exam_room_id = room.id(+)                                   ");
		sql_temp.append("      and booking.fk_tch_stu_elective_id = elective.id                           ");
		sql_temp.append("      and elective.fk_tch_opencourse_id = opencourse.id                          ");
		sql_temp.append("      and opencourse.fk_course_id = course.id                                    ");
		sql_temp.append("      and room.fk_exam_no = examNo.Id(+)									");	
		this.setSqlCondition(sql_temp);
		Page page = null;
		try {
			page = getGeneralService().getByPageSQL(sql_temp.toString(),
					Integer.parseInt(this.getLimit()),
					Integer.parseInt(this.getStart()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}
	@Override
	public void setServletPath() {
		this.servletPath="/entity/exam/examScoreReCheck";
	}
	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(SystemApply.class);
		dc.createAlias("enumConstByFlagApplyStatus", "enumConstByFlagApplyStatus")
		  .createAlias("enumConstByApplyType", "enumConstByApplyType")
		  .add(Restrictions.and(Restrictions.eq("enumConstByApplyType.namespace", "ApplyType"), Restrictions.eq("enumConstByApplyType.code", "18")))
			.createCriteria("peStudent", "peStudent")
			.createAlias("prStudentInfo", "prStudentInfo").createAlias("peSite", "peSite")
			.createAlias("peMajor", "peMajor")
			.createAlias("peEdutype","peEdutype")
			.createAlias("peGrade", "peGrade")
			.createAlias("enumConstByFlagStudentStatus", "enumConstByFlagStudentStatus");
		return dc;
	}
}
