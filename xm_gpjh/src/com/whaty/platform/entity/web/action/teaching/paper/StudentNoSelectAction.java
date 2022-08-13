package com.whaty.platform.entity.web.action.teaching.paper;

import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class StudentNoSelectAction extends MyBaseAction {

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().setTitle(this.getText("查看选课未选题学生"));

		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("学期"),"combobox_peSemester.semester_name");
		this.getGridConfig().addColumn(this.getText("学号"),"reg_no");
		this.getGridConfig().addColumn(this.getText("姓名"), "stu_name");
		this.getGridConfig().addColumn(this.getText("学习中心"),"combobox_peSite.site_name");
		this.getGridConfig().addColumn(this.getText("层次"),"combobox_peEdutype.edutype_name");
		this.getGridConfig().addColumn(this.getText("专业"),"combobox_peMajor.major_name");
		this.getGridConfig().addColumn(this.getText("年级"),"combobox_peGrade.grade_name");
	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/teaching/studentNoSelect";
	}

	@Override
	public Page list() {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("select stu.id id, semester.name semester_name,stu.reg_no reg_no, ");
		sql.append("       stu.true_name stu_name, site.name site_name,          ");
		sql.append("       edutype.name edutype_name, major.name major_name,     ");
		sql.append("       grade.name   grade_name                               ");
		sql.append("  from pr_tch_stu_elective elv,                              ");
		sql.append("       pe_student          stu,                              ");
		sql.append("       pe_semester         semester,                         ");
		sql.append("       pr_tch_opencourse   opcourse,                         ");
		sql.append("       pe_tch_course       course,                           ");
		sql.append("       pe_major            major,                            ");
		sql.append("       pe_site             site,                             ");
		sql.append("       pe_edutype          edutype,                          ");
		sql.append("       pe_grade            grade                             ");
		sql.append(" where elv.fk_stu_id = stu.id                                ");
		sql.append("   and elv.fk_tch_opencourse_id = opcourse.id                ");
		sql.append("   and opcourse.fk_semester_id = semester.id                 ");
		sql.append("   and opcourse.fk_course_id = course.id                     ");
		sql.append("   and major.id = stu.fk_major_id                            ");
		sql.append("   and site.id = stu.fk_site_id                              ");
		sql.append("   and edutype.id = stu.fk_edutype_id                        ");
		sql.append("   and grade.id = stu.fk_grade_id                            ");
		sql.append("   and course.name = '毕业论文'                                ");
		sql.append("   and stu.id not in (select fk_stu_id from pr_tch_stu_paper)");
		this.setSqlCondition(sql);
		try {
			Page page = this.getGeneralService().getByPageSQL(sql.toString(), Integer.parseInt(this.getLimit()), Integer.parseInt(this.getStart()));
			return page;
		} catch (EntityException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
