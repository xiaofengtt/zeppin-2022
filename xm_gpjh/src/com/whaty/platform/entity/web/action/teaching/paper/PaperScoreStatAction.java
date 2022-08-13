package com.whaty.platform.entity.web.action.teaching.paper;

import java.util.Map;

import com.whaty.platform.entity.bean.PrTchStuPaper;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class PaperScoreStatAction extends MyBaseAction {
	private String checkBox;
	private String type;


	/**
	 * 转向条件选择页面
	 * @return
	 */
	public String toJSP(){
		return "paperScoreStat";
	}
	
	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().setTitle(this.getText("论文成绩统计"));

		this.getGridConfig().addColumn(this.getText("id"),"id",false,false,false,"");
		this.getGridConfig().addColumn(this.getText("学期"),"combobox_peSemester.peSemester_Name");
		if (this.getCheckBox() != null && !"".equals(this.getCheckBox())) {
			if (this.getCheckBox().contains("major")) {
				this.getGridConfig().addColumn(this.getText("专业"),"combobox_peMajor.peMajor_name");
			}
			if (this.getCheckBox().contains("teacher")) {
				this.getGridConfig().addColumn(this.getText("教师"),"combobox_peTeacher.peTeacher_Name");
			}
		}
		this.getGridConfig().addColumn(this.getText("总人数"),"total");
		this.getGridConfig().addColumn(this.getText("及格率(%)"),"pass");
		this.getGridConfig().addColumn(this.getText("良好率(%)"),"good");
		this.getGridConfig().addColumn(this.getText("优秀率(%)"),"excellent");
		this.getGridConfig().addMenuScript(this.getText("返回"),"{window.history.back()}");
//		this.getGridConfig().addColumn(this.getText("row_num"),"row_num",false,false,false,"");
	}

	public Page list() {
		
		// 根据所选择的统计条件动态拼接查询语句
		String displayItems = "";// 显示的列
		String temp_from = ""; //from 条件
		String temp_where = "";// where条件
		String temp_groupBy = "";// group by 条件
		if (this.getCheckBox() != null && !"".equals(this.getCheckBox())) {
			if (this.getCheckBox().contains("major")) {
				displayItems += " peMajor.name as peMajor_name,          ";
				temp_from += " pe_major           peMajor,   ";
				temp_where += "   and peStudent.Fk_Major_Id = peMajor.Id    ";
				temp_groupBy += " peMajor.name  , ";
			}
			if (this.getCheckBox().contains("teacher")) {
				displayItems += "  peTeacher.Name as peTeacher_Name,  ";
				temp_from +="       PE_TEACHER         peTeacher,             ";
				temp_where += "      and prTchPaperTitle.Fk_Teacher = peTeacher.Id           ";
				temp_groupBy += " peTeacher.Name ,";
			}
		}
		StringBuffer sql_temp = new StringBuffer();
		if (this.getType().equals("final")){
		sql_temp.append(" select rownum as id, one.*																							");					
		sql_temp.append("   from (select peSemester.Name as peSemester_Name,                             ");
//		sql_temp.append("                peMajor.name as peMajor_name,                                   ");
//		sql_temp.append("                peTeacher.Name as peTeacher_Name,                               ");
		sql_temp.append(displayItems);
		sql_temp.append("                count(*) as total,                                       ");
		sql_temp.append("                round(SUM(CASE                                           ");
		sql_temp.append("                            WHEN prTchStuPaper.Final_Score >= 60 THEN    ");
		sql_temp.append("                             1                                           ");
		sql_temp.append("                            ELSE                                         ");
		sql_temp.append("                             0                                           ");
		sql_temp.append("                          END) / count(*) * 100,                         ");
		sql_temp.append("                      2) as pass,                                        ");
		sql_temp.append("                round(SUM(CASE                                           ");
		sql_temp.append("                            WHEN prTchStuPaper.Final_Score >= 80 THEN    ");
		sql_temp.append("                             1                                           ");
		sql_temp.append("                            ELSE                                         ");
		sql_temp.append("                             0                                           ");
		sql_temp.append("                          END) / count(*) * 100,                         ");
		sql_temp.append("                      2) as good,                                        ");
		sql_temp.append("                round(SUM(CASE                                           ");
		sql_temp.append("                            WHEN prTchStuPaper.Final_Score >= 90 THEN    ");
		sql_temp.append("                             1                                           ");
		sql_temp.append("                            ELSE                                         ");
		sql_temp.append("                             0                                           ");
		sql_temp.append("                          END) / count(*) * 100,                         ");
		sql_temp.append("                      2) as excellent                                    ");
		sql_temp.append("           from PR_TCH_STU_PAPER   prTchStuPaper,                        ");
		sql_temp.append("                PR_TCH_PAPER_TITLE prTchPaperTitle,                      ");
		sql_temp.append("                pe_student         peStudent,                           ");
		sql_temp.append(temp_from);
		sql_temp.append("                pe_semester        peSemester                           ");
//		sql_temp.append("                PE_TEACHER         peTeacher,                             ");
//		sql_temp.append("                pe_major           peMajor                               ");
		sql_temp.append("          where prTchStuPaper.Fk_Paper_Title_Id = prTchPaperTitle.Id     ");
//		sql_temp.append("            and prTchPaperTitle.Fk_Teacher = peTeacher.Id                ");
		sql_temp.append(temp_where);
		sql_temp.append("            and prTchPaperTitle.Fk_Semester = peSemester.Id              ");
		sql_temp.append("            and prTchStuPaper.Fk_Stu_Id = peStudent.Id                   ");
//		sql_temp.append("            and peStudent.Fk_Major_Id = peMajor.Id                       ");
		sql_temp.append("          group by " +temp_groupBy +" peSemester.Name) one		");	
		} else {
			sql_temp.append(" select rownum as id, one.*																						 ");		
			sql_temp.append("   from (select peSemester.Name as peSemester_Name,                     ");
//			sql_temp.append("                peMajor.name as peMajor_name,                           ");
//			sql_temp.append("                peTeacher.Name as peTeacher_Name,                       ");
			sql_temp.append(displayItems);
			sql_temp.append("                count(*) as total,                                      ");
			sql_temp.append("                round(SUM(CASE                                          ");
			sql_temp.append("                            WHEN prTchStuPaper.Score_Total >= 60 THEN   ");
			sql_temp.append("                             1                                          ");
			sql_temp.append("                            ELSE                                        ");
			sql_temp.append("                             0                                          ");
			sql_temp.append("                          END) / count(*) * 100,                        ");
			sql_temp.append("                      2) as pass,                                       ");
			sql_temp.append("                round(SUM(CASE                                          ");
			sql_temp.append("                            WHEN prTchStuPaper.Score_Total >= 80 THEN   ");
			sql_temp.append("                             1                                          ");
			sql_temp.append("                            ELSE                                        ");
			sql_temp.append("                             0                                          ");
			sql_temp.append("                          END) / count(*) * 100,                        ");
			sql_temp.append("                      2) as good,                                       ");
			sql_temp.append("                round(SUM(CASE                                          ");
			sql_temp.append("                            WHEN prTchStuPaper.Score_Total >= 90 THEN   ");
			sql_temp.append("                             1                                          ");
			sql_temp.append("                            ELSE                                        ");
			sql_temp.append("                             0                                          ");
			sql_temp.append("                          END) / count(*) * 100,                        ");
			sql_temp.append("                      2) as excellent                                   ");
			sql_temp.append("           from PR_TCH_STU_PAPER   prTchStuPaper,                       ");
			sql_temp.append("                PR_TCH_PAPER_TITLE prTchPaperTitle,                     ");
			sql_temp.append("                pe_student         peStudent,                          ");
			sql_temp.append(temp_from);
			sql_temp.append("                pe_semester        peSemester                          ");
//			sql_temp.append("                PE_TEACHER         peTeacher,                           ");
//			sql_temp.append("                pe_major           peMajor                              ");
			sql_temp.append("          where prTchStuPaper.Fk_Paper_Title_Id = prTchPaperTitle.Id    ");
//			sql_temp.append("            and prTchPaperTitle.Fk_Teacher = peTeacher.Id               ");
			sql_temp.append(temp_where);
			sql_temp.append("            and prTchPaperTitle.Fk_Semester = peSemester.Id             ");
			sql_temp.append("            and prTchStuPaper.Fk_Stu_Id = peStudent.Id                  ");
//			sql_temp.append("            and peStudent.Fk_Major_Id = peMajor.Id                      ");
			sql_temp.append("          group by " +temp_groupBy +" peSemester.Name) one		");	
		}
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
		this.entityClass = PrTchStuPaper.class;
		
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/teaching/paperScoreStat";
	}
	
	public String getCheckBox() {
		return checkBox;
	}

	public void setCheckBox(String checkBox) {
		this.checkBox = checkBox;
	}
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
