package com.whaty.platform.entity.web.action.exam.inputScore;

import java.io.File;
import java.io.IOException;
import java.util.List;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.apache.struts2.ServletActionContext;

import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class ExamScoreExportInputDataAction extends MyBaseAction {

	@Override
	public void initGrid() {
		if(this.getGridConfig().checkBeforeAddMenu("/entity/exam/examscoreexportinputdata_exportExcel.action")){
		this.getGridConfig().addMenuScript(this.getText("导出所有的登分数据信息"), 
				"{ document.getElementById('user-defined-content').style.display='none'; "+
					"	document.getElementById('user-defined-content').innerHTML=\"<form target='_blank' action='/entity/exam/examscoreexportinputdata_exportExcel.action' method='post' name='formx1' style='display:none'>"+
					"   </form>\"; "+
					"	document.formx1.submit();"+
					"	document.getElementById('user-defined-content').innerHTML=\"\";"+
					"}        ");
		}
		this.getGridConfig().setCapability(false, false, false,true);
		this.getGridConfig().setTitle(this.getText("导出登分数据信息"));
		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("学号"), "peStudentRegNo");
		this.getGridConfig().addColumn(this.getText("姓名"), "peStudentName");
		this.getGridConfig().addColumn(this.getText("学习中心编号"), "peSiteCode");
		this.getGridConfig().addColumn(this.getText("学习中心"), "combobox_peSite.peSiteName");
		this.getGridConfig().addColumn(this.getText("层次"), "combobox_peEdutype.peEdutypeName");
		this.getGridConfig().addColumn(this.getText("专业"), "combobox_peMajor.peMajorName");
		this.getGridConfig().addColumn(this.getText("身份证号"), "prStudentInfoCardNo");
		this.getGridConfig().addColumn(this.getText("课程编号"), "peTchCourseCode");
		this.getGridConfig().addColumn(this.getText("课程"), "combobox_peTchCourse.peTchCourseName");
		this.getGridConfig().addColumn(this.getText("考场编号"), "peExamRoomCode");
		this.getGridConfig().addColumn(this.getText("教室"), "Classroom");
		this.getGridConfig().addColumn(this.getText("座位号"), "prExamBookingSeatNo");
	}

	@Override
	public void setEntityClass() {
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/exam/examscoreexportinputdata";
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
	
	private StringBuffer getSQL(){
		StringBuffer sql = new StringBuffer();
		sql.append("select prExamBooking.Id,        ");
		sql.append("       peStudent.Reg_No peStudentRegNo,          ");
		sql.append("       peStudent.True_Name peStudentName,        ");
		sql.append("       peSite.Code peSiteCode,                   ");
		sql.append("       peSite.Name peSiteName,                   ");
		sql.append("       peEdutype.Name peEdutypeName,             ");
		sql.append("       peMajor.Name peMajorName,                 ");
		sql.append("       prStudentInfo.Card_No prStudentInfoCardNo,");
		sql.append("       peTchCourse.Code peTchCourseCode,         ");
		sql.append("       peTchCourse.Name peTchCourseName,         ");
		sql.append("       peExamRoom.Code peExamRoomCode,           ");
		sql.append("       peExamRoom.Classroom Classroom,           ");
		sql.append("       prExamBooking.Seat_No prExamBookingSeatNo ");
		sql.append("from  pe_site peSite,                            ");
		sql.append("      pe_edutype peEdutype,                      ");
		sql.append("      pe_major peMajor,                          ");
		sql.append("      pe_student peStudent,                      ");
		sql.append("      pr_student_info prStudentInfo,             ");
		sql.append("      pe_tch_course peTchCourse,                 ");
		sql.append("      pe_exam_no peExamNo,                       ");
		sql.append("      pe_exam_room peExamRoom,                   ");
		sql.append("      pr_exam_booking prExamBooking,             ");
		sql.append("      pe_semester peSemester,                    ");
		sql.append("      pr_tch_stu_elective prTchStuElective,      ");
		sql.append("      pr_tch_opencourse prTchOpencourse          ");
	//	sql.append(" --当前学期                                 ");
		sql.append("where peSemester.Flag_Active='1' and             ");
	//	sql.append("      --考试场次表与学期表                       ");
		sql.append("      peExamNo.Fk_Semester_Id=peSemester.Id and  ");
	//	sql.append("      --考试预约表与学生选课表                                       ");
		sql.append("      prExamBooking.Fk_Tch_Stu_Elective_Id=prTchStuElective.Id and   ");
	//	sql.append("        --学生选课表和学生表                                         ");
		sql.append("        prTchStuElective.Fk_Stu_Id=peStudent.Id and                  ");
	//	sql.append("          --学生表和专业                                             ");
		sql.append("          peStudent.Fk_Major_Id=peMajor.Id and                       ");
	//	sql.append("          --学生表盒层次                                             ");
		sql.append("          peStudent.Fk_Edutype_Id=peEdutype.Id and                   ");
	//	sql.append("          --学生表和学生信息表                                       ");
		sql.append("          peStudent.Fk_Student_Info_Id = prStudentInfo.Id and        ");
	//	sql.append("        --学生选课表和开课表                                         ");
		sql.append("        prTchStuElective.Fk_Tch_Opencourse_Id=prTchOpencourse.Id and ");
	//	sql.append("          --开课表和课程表                                           ");
		sql.append("          prTchOpencourse.Fk_Course_Id=peTchCourse.Id and            ");
	//	sql.append("      --考试预约表盒考场表                                           ");
		sql.append("      prExamBooking.Fk_Exam_Room_Id=peExamRoom.Id and                ");
	//	sql.append("        --考场表盒学习中心表                                         ");
		sql.append("        peExamRoom.Fk_Site_Id=peSite.Id and                          ");
	//	sql.append("        --考场表盒考试场次表                                         ");
		sql.append("        peExamRoom.Fk_Exam_No=peExamNo.Id                            ");
		sql.append("order by peTchCourse.Code,peSite.Code,peExamNo.Id,                   ");
		sql.append("         peExamRoom.Code,prExamBooking.Seat_No                       ");
		return sql;
	}
	
	public String exportExcel(){
		StringBuffer sql = getSQL();
		try {
			WritableWorkbook book = Workbook.createWorkbook(new File(ServletActionContext.getServletContext().getRealPath("/test/export.xls")));
			WritableSheet sheet = book.createSheet("登分数据信息", 0);
			sheet.setColumnView(0,30);
			sheet.setColumnView(1,15);
			sheet.setColumnView(2,15);
			sheet.setColumnView(3,40);
			sheet.setColumnView(4,25);
			sheet.setColumnView(5,25);
			sheet.setColumnView(6,20);
			sheet.setColumnView(7,10);
			sheet.setColumnView(8,35);
			sheet.setColumnView(9,10);
			sheet.setColumnView(10,50);
			sheet.setColumnView(11,10);
			Label label;
			WritableCellFormat wcf;
			WritableFont wf = new WritableFont(WritableFont.TIMES, 12);
			wcf = new WritableCellFormat(wf);
			
			wcf.setAlignment(jxl.format.Alignment.CENTRE);
			
			label = new Label(0, 0, "学号");
			label.setCellFormat(wcf);
			sheet.addCell(label);
			label = new Label(1, 0, "姓名");
			label.setCellFormat(wcf);
			sheet.addCell(label);	
			label = new Label(2, 0, "学习中心编号");
			label.setCellFormat(wcf);
			sheet.addCell(label);
			label = new Label(3, 0, "学习中心");
			label.setCellFormat(wcf);
			sheet.addCell(label);
			label = new Label(4, 0, "层次");
			label.setCellFormat(wcf);
			sheet.addCell(label);
			label = new Label(5, 0, "专业");
			label.setCellFormat(wcf);
			sheet.addCell(label);
			label = new Label(6, 0, "身份证号");
			label.setCellFormat(wcf);
			sheet.addCell(label);
			label = new Label(7, 0, "课程编号");
			label.setCellFormat(wcf);
			sheet.addCell(label);
			label = new Label(8, 0, "课程");
			label.setCellFormat(wcf);
			sheet.addCell(label);
			label = new Label(9, 0, "考场编号");
			label.setCellFormat(wcf);
			sheet.addCell(label);
			label = new Label(10, 0, "考场教室");
			label.setCellFormat(wcf);
			sheet.addCell(label);
			label = new Label(11, 0, "座位号");
			label.setCellFormat(wcf);
			sheet.addCell(label);
			List list = this.getGeneralService().getBySQL(sql.toString());
			if(list==null||list.size()<=0){
				this.setMsg("没有需要导出的数据");
				this.setTogo("javascript:window.close();");
				book.write();
				book.close();
				return "msg";
			}
			for(int i = 0;i<list.size();i++){
				Object[] os = (Object[]) list.get(i);
				for(int j = 1;j<os.length;j++){
					if(os[j]==null)
						label = new Label(j-1,i+1,"");
					else
						label = new Label(j-1,i+1, os[j].toString());
					label.setCellFormat(wcf);
					sheet.addCell(label);
				}
			}
			book.write();
			book.close();
		} catch (IOException e) {
			e.printStackTrace();
		}catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "excel";
	}
	
}
