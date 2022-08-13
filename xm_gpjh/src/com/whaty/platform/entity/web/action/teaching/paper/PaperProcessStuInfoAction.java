package com.whaty.platform.entity.web.action.teaching.paper;

import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class PaperProcessStuInfoAction extends MyBaseAction {
	private String peSemester;
	private String peTeacher;
	private String section;
	private String type;
	private String user;

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false);
		String theTitle = "论文学生详情:";
		if(this.getSection()!=null&&this.getSection().equals("kaiti")){
			theTitle +="开题报告";
		}
		if (this.getSection()!=null&&this.getSection().equals("chugao")){
			theTitle +="论文初稿";
		}
		if (this.getSection()!=null&&this.getSection().equals("zhonggao")){
			theTitle +="论文终稿";
		}
		if(this.getType()!=null&&this.getType().equals("tijiao")){
			theTitle +="未提交";
		}
		if(this.getType()!=null&&this.getType().equals("liuyan")){
			theTitle +="未留言";
		}
		this.getGridConfig().setTitle(this.getText(theTitle));
//		this.getGridConfig().addColumn(this.getText("row_num"),"row_num",false,false,false,"");
		this.getGridConfig().addColumn(this.getText("id"),"id",false,false,false,"");
		this.getGridConfig().addColumn(this.getText("学期"),"combobox_peSemester.peSemester_Name");
		this.getGridConfig().addColumn(this.getText("学号"),"peStudent_Reg_No");
		this.getGridConfig().addColumn(this.getText("学生姓名"),"peStudent_True_Name");
		this.getGridConfig().addColumn(this.getText("学习中心"),"combobox_peSite.peSite_Name");
		this.getGridConfig().addColumn(this.getText("层次"),"combobox_peEdutype.peEdutype_Name");
		this.getGridConfig().addColumn(this.getText("专业"),"combobox_peMajor.peMajor_Name");
		this.getGridConfig().addColumn(this.getText("年级"),"combobox_peGrade.peGrade_Name");
		this.getGridConfig().addColumn(this.getText("指导教师"),"combobox_peTeacher.peTeacher_True_Name");
		this.getGridConfig().addColumn(this.getText("论文题目"),"prTchPaperTitle_Title");
	}
	public Page list() {
		StringBuffer sql_temp = new StringBuffer();
		sql_temp.append("  select rownum as id, one.*																													");
		sql_temp.append("    from (select peSemester.Name       as peSemester_Name,                           ");
		sql_temp.append("                 peStudent.Reg_No      as peStudent_Reg_No,                          ");
		sql_temp.append("                 peStudent.True_Name   as peStudent_True_Name,                       ");
		sql_temp.append("                 peSite.Name           as peSite_Name,                               ");
		sql_temp.append("                 peEdutype.Name        as peEdutype_Name,                            ");
		sql_temp.append("                 peMajor.Name          as peMajor_Name,                              ");
		sql_temp.append("                 peGrade.Name          as peGrade_Name,                              ");
		sql_temp.append("                 peTeacher.True_Name   as peTeacher_True_Name,                       ");
		sql_temp.append("                 prTchPaperTitle.Title as prTchPaperTitle_Title                      ");
		sql_temp.append("            from PR_TCH_STU_PAPER   prTchStuPaper,                                   ");
		sql_temp.append("                 PR_TCH_PAPER_TITLE prTchPaperTitle,                                 ");
		sql_temp.append("                 PE_TEACHER         peTeacher,                                       ");
		sql_temp.append("                 pe_student         peStudent,                                       ");
		sql_temp.append("                 pe_semester        peSemester,                                      ");
		sql_temp.append("                 pe_site            peSite,                                          ");
		sql_temp.append("                 pe_edutype         peEdutype,                                       ");
		sql_temp.append("                 pe_major           peMajor,                                         ");
		sql_temp.append("                 pe_grade           peGrade                                          ");
		sql_temp.append("           where prTchPaperTitle.Fk_Semester = peSemester.Id                         ");
		sql_temp.append("             and prTchStuPaper.Fk_Paper_Title_Id = prTchPaperTitle.Id                ");
		sql_temp.append("             and prTchPaperTitle.Fk_Teacher = peTeacher.Id                           ");
		sql_temp.append("             and peTeacher.Id = '"+this.getPeTeacher()+"'                           ");
		sql_temp.append("             and peSemester.Id = '"+this.getPeSemester()+"'                           ");
		sql_temp.append("             and prTchStuPaper.Fk_Stu_Id = peStudent.Id                              ");
		sql_temp.append("             and peStudent.Fk_Site_Id = peSite.Id                                    ");
		sql_temp.append("             and peStudent.Fk_Major_Id = peMajor.Id                                  ");
		sql_temp.append("             and peStudent.Fk_Grade_Id = peGrade.Id                                  ");
		sql_temp.append("             and peStudent.Fk_Edutype_Id = peEdutype.Id                              ");
		sql_temp.append("             and prTchStuPaper.Id not in                                             ");
		sql_temp.append("                 (select prTchPaperContent.Fk_Tch_Stu_Paper                          ");
		sql_temp.append("                    from pr_tch_paper_content prTchPaperContent,                     ");
		sql_temp.append("                         PR_TCH_STU_PAPER     prTchStuPaper,                         ");
		sql_temp.append("                         PR_TCH_PAPER_TITLE   prTchPaperTitle,                       ");
		sql_temp.append("                         PE_TEACHER           peTeacher,                             ");
		sql_temp.append("                         pe_student           peStudent,                             ");
		sql_temp.append("                         pe_semester          peSemester,                            ");
		sql_temp.append("                         enum_const           enumConstUser,                         ");
		sql_temp.append("                         enum_const           enumConstSection                       ");
		sql_temp.append("                   where prTchPaperContent.Fk_Tch_Stu_Paper = prTchStuPaper.Id       ");
		sql_temp.append("                     and prTchPaperTitle.Fk_Semester = peSemester.Id                 ");
		sql_temp.append("             and peTeacher.Id = '"+this.getPeTeacher()+"'                           ");
		sql_temp.append("             and peSemester.Id = '"+this.getPeSemester()+"'                           ");
		sql_temp.append("                     and prTchStuPaper.Fk_Paper_Title_Id = prTchPaperTitle.Id        ");
		sql_temp.append("                     and prTchPaperTitle.Fk_Teacher = peTeacher.Id                   ");
		sql_temp.append("                     and prTchStuPaper.Fk_Stu_Id = peStudent.Id                      ");
		sql_temp.append("                     and prTchPaperContent.Flag_Action_User = enumConstUser.Id       ");
		sql_temp.append("                     and enumConstUser.Namespace = 'FlagActionUser'                  ");
		if(this.getUser()!=null&&this.getUser().equals("teacher")){
			sql_temp.append("                     and enumConstUser.Code = '1'                                    ");
		} else {
			sql_temp.append("                     and enumConstUser.Code = '0'                                    ");
		}
		sql_temp.append("                     and prTchPaperContent.Flag_Paper_Section =                      ");
		sql_temp.append("                         enumConstSection.Id                                         ");
		sql_temp.append("                     and enumConstSection.Namespace = 'FlagPaperSection'             ");
		if(this.getSection()!=null&&this.getSection().equals("kaiti")){
			sql_temp.append("                     and enumConstSection.Code = '0'                                 ");
		}
		if(this.getSection()!=null&&this.getSection().equals("chugao")){
			sql_temp.append("                     and enumConstSection.Code = '1'                                 ");
		}
		if(this.getSection()!=null&&this.getSection().equals("zhonggao")){
			sql_temp.append("                     and enumConstSection.Code = '2'                                 ");
		}
		if(this.getType()!=null&&this.getType().equals("tijiao")){
			sql_temp.append("                     and prTchPaperContent.Url is not null                           ");
		}
		if(this.getType()!=null&&this.getType().equals("liuyan")){
			sql_temp.append("                     and prTchPaperContent.note is not null                           ");
		}
		sql_temp.append("                   group by prTchPaperContent.Fk_Tch_Stu_Paper)) one				");		
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
	public void setEntityClass() {
		this.servletPath="/entity/teaching/paperProcessStuInfo";

	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub

	}
	public String getPeSemester() {
		return peSemester;
	}
	public void setPeSemester(String peSemester) {
		this.peSemester = peSemester;
	}
	public String getPeTeacher() {
		return peTeacher;
	}
	public void setPeTeacher(String peTeacher) {
		this.peTeacher = peTeacher;
	}
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
}
