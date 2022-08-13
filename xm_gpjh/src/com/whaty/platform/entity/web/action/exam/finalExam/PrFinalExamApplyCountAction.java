package com.whaty.platform.entity.web.action.exam.finalExam;

import java.util.Map;

import com.whaty.platform.entity.bean.PrExamBooking;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class PrFinalExamApplyCountAction extends MyBaseAction {

	private String checkBox;
	
	@Override
	public Map updateColumn() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false,false,false);
		this.getGridConfig().setTitle(this.getText("期末考试预约统计列表"));
//		this.getGridConfig().addColumn(this.getText("row_num"),"row_num",false,false,false,"");
		this.getGridConfig().addColumn(this.getText("id"), "id", false,false,false,"");
		this.getGridConfig().addColumn(this.getText("考试学期"), "combobox_peSemester.semester_name");
		if(this.getCheckBox() != null && !"".equals(this.getCheckBox())){
			if(this.getCheckBox().contains("site")){
				this.getGridConfig().addColumn(this.getText("学习中心"), "combobox_PeSite.site_name");
			}
			if(this.getCheckBox().contains("edutype")){
				this.getGridConfig().addColumn(this.getText("层次"), "combobox_PeEdutype.edutype_name");
			}
			if(this.getCheckBox().contains("major")){
				this.getGridConfig().addColumn(this.getText("专业"), "combobox_peMajor.major_name");
			}
		}
		this.getGridConfig().addColumn(this.getText("课程名称"), "combobox_peTchCourse.course_name");
		this.getGridConfig().addColumn(this.getText("预约学生人数"), "stu_num",false);
		this.getGridConfig().addMenuScript(this.getText("返回"), "{window.history.back()}");
	}

	public Page list(){
		
		String displayItems = "";
		String tables = "";
		String conditions = "";
		String temp_groupBy = "";
		
		if(this.getCheckBox() != null && !"".equals(this.getCheckBox())){
			if(this.getCheckBox().contains("site")){
				displayItems += " ,peSite.name as site_name ";
				tables += " pe_site   peSite, ";
				conditions += " and peStudent.fk_site_id = peSite.id ";
				temp_groupBy += " ,peSite.name ";
			}
			if(this.getCheckBox().contains("edutype")){
				displayItems += " ,peEdutype.name as edutype_name ";
				tables += " pe_edutype   peEdutype, ";
				conditions += " and peStudent.fk_edutype_id = peEdutype.id ";
				temp_groupBy += " ,peEdutype.name ";
			}
			if(this.getCheckBox().contains("major")){
				displayItems += " ,peMajor.name as major_name ";
				tables += " pe_major   peMajor, ";
				conditions += " and peStudent.fk_major_id = peMajor.id ";
				temp_groupBy += " ,peMajor.name ";
			}
		}
		
		String sql = "select rownum as id, one.*	from( select  peSemester.Name as semester_name                      " + displayItems +
		"        ,peTchCourse.Name as course_name,                       " +
		"        count(peStudent.Id) as stu_num                         " +
		"   from pr_exam_booking       booking,                         " + tables +
		"        pr_tch_stu_elective   elective,                        " +
		"        pe_student            peStudent,                       " +
		"        pr_tch_program_course programcourse,                   " +
		"        pe_tch_course         peTchCourse,                     " +
		"        pe_semester           peSemester                       " +
		"  where booking.fk_tch_stu_elective_id = elective.id           " +
		"    and booking.fk_semester_id = peSemester.Id                 " +
		"    and elective.fk_stu_id = peStudent.Id                      " +
		"    and elective.fk_tch_program_course = programcourse.id      " +
		"    and programcourse.fk_course_id = peTchCourse.Id            " + conditions 
		+"    group by  peSemester.Name,peTchCourse.Name      " + temp_groupBy +" ) one";
		
		StringBuffer sql_temp = new StringBuffer(sql);
		this.setSqlCondition(sql_temp);
		Page page = null;
		try {
			page = getGeneralService().getByPageSQL(sql_temp.toString(), Integer.parseInt(this.getLimit()), Integer.parseInt(this.getStart()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}
	
	@Override
	public void setEntityClass() {
		this.entityClass = PrExamBooking.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/exam/prFinalExamApplyCount";
	}

	public String getCheckBox() {
		return checkBox;
	}

	public void setCheckBox(String checkBox) {
		this.checkBox = checkBox;
	}

}
