package com.whaty.platform.entity.web.action.exam.inputScore;

import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class ExamScoreInputStatisticAction extends MyBaseAction {

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false,true);
		this.getGridConfig().setTitle(this.getText("课程登分帐户数据信息"));
		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("课程编号"), "peTchCourseCode");
		this.getGridConfig().addColumn(this.getText("课程"), "combobox_peTchCourse.peTchCourseName");
		this.getGridConfig().addColumn(this.getText("考试总人数"), "cnum");
		ColumnConfig c1 = new ColumnConfig(this.getText("登分帐户A"),"combobox_PeExamScoreInputUser.aName");
		c1.setComboSQL("select id,name from pe_exam_score_input_user where name like '%a'");
		this.getGridConfig().addColumn(c1);
		//this.getGridConfig().addColumn(this.getText("登分帐户A"), "combobox_PeExamScoreInputUser.aName");		
		this.getGridConfig().addColumn(this.getText("A已录入人数"), "aPassword",false);
		ColumnConfig c2 = new ColumnConfig(this.getText("登分帐户B"),"combobox_PeExamScoreInputUser.bName");
		c2.setComboSQL("select id,name from pe_exam_score_input_user where name like '%b'");
		this.getGridConfig().addColumn(c2);
		//this.getGridConfig().addColumn(this.getText("登分帐户B"), "combobox_PeExamScoreInputUser.bName");
		this.getGridConfig().addColumn(this.getText("B已录入人数"), "bPassword",false);
	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setServletPath() {
		this.servletPath="/entity/exam/examscoreinputstatistic";
	}

	private StringBuffer getSQL(){
		StringBuffer sql = new StringBuffer();
		sql.append("select peTchCourse.Id,                                                 ");
		sql.append("       peTchCourse.Code peTchCourseCode,                               ");
		sql.append("       peTchCourse.Name peTchCourseName,                               ");
		sql.append("       prTchCourseSumofStu.cnum,                                       ");
		sql.append("       peExamScoreInputa.Name aName,                                   ");
		sql.append("       prTchCourseSumofStu.cnuma cnuma,                           ");
		sql.append("       peExamScoreInputb.Name bName,                                   ");
		sql.append("       prTchCourseSumofStu.cnumb cnumb                            ");
		sql.append("from pe_tch_course peTchCourse,                                        ");
		sql.append("     pe_exam_score_input_user peExamScoreInputa,                       ");
		sql.append("     pe_exam_score_input_user peExamScoreInputb,                       ");
	//	sql.append("     --统计课程考试人数                                                ");
		sql.append("(select prTchOpencourse.Fk_Course_Id cid,                              ");
		sql.append("          count(prExamBooking.id) cnum,                     ");
		sql.append("          count(prExamBooking.Score_Exam_a) cnuma,                     ");
		sql.append("          count(prExamBooking.Score_Exam_b) cnumb                     ");
		sql.append("  from  pe_exam_no peExamNo,                                           ");
		sql.append("        pe_exam_room peExamRoom,                                       ");
		sql.append("        pr_exam_booking prExamBooking,                                 ");
		sql.append("        pe_semester peSemester,                                        ");
		sql.append("        pr_tch_stu_elective prTchStuElective,                          ");
		sql.append("        pr_tch_opencourse prTchOpencourse                              ");
		sql.append("  where peSemester.Flag_Active='1' and                                 ");
	//	sql.append("        --考试场次表与学期表                                           ");
		sql.append("        peExamNo.Fk_Semester_Id=peSemester.Id and                      ");
	//	sql.append("        --考试预约表与学生选课表                                       ");
		sql.append("        prExamBooking.Fk_Tch_Stu_Elective_Id=prTchStuElective.Id and   ");
	//	sql.append("          --学生选课表和开课表                                         ");
		sql.append("          prTchStuElective.Fk_Tch_Opencourse_Id=prTchOpencourse.Id and ");         
	//	sql.append("        --考试预约表和考场表                                           ");
		sql.append("        prExamBooking.Fk_Exam_Room_Id=peExamRoom.Id and                ");
	//	sql.append("          --考场表和考试场次表                                         ");
		sql.append("          peExamRoom.Fk_Exam_No=peExamNo.Id                            ");
		sql.append("  group by prTchOpencourse.Fk_Course_Id ) prTchCourseSumofStu          ");
		sql.append("where prTchCourseSumofStu.cid=peTchCourse.Id and                       ");
	//	sql.append("      --课程表和登分人A                                                ");
		sql.append("      peTchCourse.Fk_Exam_Score_Input_Usera_Id=peExamScoreInputa.Id and");
	//	sql.append("      --课程表和登分人B                                                ");
		sql.append("      peTchCourse.Fk_Exam_Score_Input_Userb_Id=peExamScoreInputb.Id    ");
		return sql;
	}
	
	@Override
	public Page list() {
		StringBuffer sql = getSQL();		      
		Page page = null;
		this.setSqlCondition(sql);
		try {
			page = getGeneralService().getByPageSQL(sql.toString(), Integer.parseInt(this.getLimit()), Integer.parseInt(this.getStart()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
		return page;
	}
}
