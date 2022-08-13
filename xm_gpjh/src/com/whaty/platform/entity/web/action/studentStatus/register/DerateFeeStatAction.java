package com.whaty.platform.entity.web.action.studentStatus.register;

import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class DerateFeeStatAction extends MyBaseAction {
	private String checkBox;


	/**
	 * 转向统计选项选择页面
	 * @return
	 */
	public  String turnToJsp(){
		return "derateFeeStat";
	}
	

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false,true);
		this.getGridConfig().setTitle(this.getText("预交费人数统计"));
//		this.getGridConfig().addColumn(this.getText("row_num"),"row_num",false,false,false,"");
		this.getGridConfig().addColumn(this.getText("id"),"id",false,false,false,"");
		this.getGridConfig().addColumn(this.getText("招生批次"),"combobox_peRecruitplan.recname");
		if (this.getCheckBox() != null && !"".equals(this.getCheckBox())) {
			if (this.getCheckBox().contains("site")) {
				this.getGridConfig().addColumn(this.getText("学习中心"),"combobox_peSite.siteName");
				
			}
			if (this.getCheckBox().contains("grade")) {
				this.getGridConfig().addColumn(this.getText("年级"),"combobox_peGrade.gradeName");
			}
			if (this.getCheckBox().contains("edutype")) {
				this.getGridConfig().addColumn(this.getText("层次"),"combobox_peEdutype.edutypeName");
			}
			if (this.getCheckBox().contains("major")) {
				this.getGridConfig().addColumn(this.getText("专业"),"combobox_peMajor.majorName");
			}
		}
		this.getGridConfig().addColumn("录取人数", "total",false);
		this.getGridConfig().addColumn("未交费人数", "num",false);
		this.getGridConfig().addColumn("已交费人数", "feeNum",false);
		this.getGridConfig().addMenuScript(this.getText("返回"),"{window.history.back()}");

	}
	public Page list() {
		
		// 根据所选择的统计条件动态拼接查询语句
		String columnDisplay = "";//显示的列
		String displayItems = "";// 子表显示的列
		String temp_from = ""; //from 条件
		String temp_where = "";// where条件
		String temp_groupBy = "";// group by 条件
		String temp_on = ""; //连接条件
		if (this.getCheckBox() != null && !"".equals(this.getCheckBox())) {
			if (this.getCheckBox().contains("site")) {
				columnDisplay += "  total.siteName siteName,   ";
				displayItems += "   site.name as siteName, ";
				temp_from +="       pe_site                   site,            ";
				temp_where += "          and peStudent.Fk_Site_Id = site.id               ";
				temp_groupBy += "   site.name,   ";
				temp_on += "   and luqu.siteName = total.siteName   ";
			}
			if (this.getCheckBox().contains("grade")) {
				columnDisplay += "         total.gradeName gradeName,              ";
				displayItems += "      grade.name as gradeName,       ";
				temp_from +="        pe_grade                  grade,                  ";
				temp_where += "           and peStudent.Fk_Grade_Id = grade.id              ";
				temp_groupBy += "     grade.name ,";
				temp_on += "     and luqu.gradeName = total.gradeName      ";
			}
			if (this.getCheckBox().contains("edutype")) {
				columnDisplay += "         total.edutypeName edutypeName,                  ";
				displayItems += "       edutype.name as edutypeName,          ";
				temp_from +="            pe_edutype                edutype,           ";
				temp_where += "              and peStudent.Fk_Edutype_Id = edutype.id                   ";
				temp_groupBy += "     edutype.name, ";
				temp_on += "      and luqu.edutypeName = total.edutypeName   ";
			}
			if (this.getCheckBox().contains("major")) {
				columnDisplay += "          total.majorName majorName,                    ";
				displayItems += "   major.name as majorName,          ";
				temp_from += "     pe_major                  major,      ";
				temp_where += "    and peStudent.Fk_Major_Id = major.id         ";
				temp_groupBy += "  major.name,  ";
				temp_on += "      and luqu.majorName = total.majorName   ";
			}

		}

		
		StringBuffer sql_temp = new StringBuffer();
		sql_temp.append("    select rownum as id, one.*																																");	
		sql_temp.append("      from (select total.recname recname,                                                             ");
		sql_temp.append(columnDisplay);
		sql_temp.append("                   total.num total,                                                                 ");
		sql_temp.append("                   nvl(luqu.num, 0) num,                                                          ");
		sql_temp.append("                   total.num - nvl(luqu.num, 0)  feeNum                                             ");
		sql_temp.append("              from (select rec.name as recname,                                               ");
		sql_temp.append(displayItems);
		sql_temp.append("                           count(*) as num                                                    ");
		sql_temp.append("                      from pe_student                peStudent,                               ");
		sql_temp.append("                           enum_const                statusConst,                             ");
		sql_temp.append(temp_from);
		sql_temp.append("                           pe_rec_student            recStudent,                              ");
		sql_temp.append("                           PR_REC_PLAN_MAJOR_SITE    planMajorSite,                           ");
		sql_temp.append("                           PR_REC_PLAN_MAJOR_EDUTYPE planMajorEdutype,                        ");
		sql_temp.append("                           PE_RECRUITPLAN            rec                                      ");
		sql_temp.append("                     where peStudent.flag_student_status = statusConst.id                     ");
		sql_temp.append("                                                           ");
		sql_temp.append(temp_where);
		sql_temp.append("                       and peStudent.Fk_Rec_Student_Id = recStudent.id                        ");
		sql_temp.append("                       and recStudent.Fk_Rec_Major_Site_Id = planMajorSite.Id                 ");
		sql_temp.append("                       and planMajorSite.Fk_Rec_Plan_Major_Edutype_Id =                       ");
		sql_temp.append("                           planMajorEdutype.Id                                                ");
		sql_temp.append("                       and planMajorEdutype.Fk_Recruitplan_Id = rec.id                        ");
		sql_temp.append("                     group by "+temp_groupBy+" rec.name                                                       ");
		sql_temp.append("                            ) total                                               ");
		sql_temp.append("              left join (select rec.name as recname,                                          ");
		sql_temp.append(displayItems);
		sql_temp.append("                               count(*) as num                                                ");
		sql_temp.append("                          from pe_student                peStudent,                           ");
		sql_temp.append("                               enum_const                statusConst,                         ");
		sql_temp.append(temp_from);
		sql_temp.append("                               pe_rec_student            recStudent,                          ");
		sql_temp.append("                               PR_REC_PLAN_MAJOR_SITE    planMajorSite,                       ");
		sql_temp.append("                               PR_REC_PLAN_MAJOR_EDUTYPE planMajorEdutype,                    ");
		sql_temp.append("                               PE_RECRUITPLAN            rec                                  ");
		sql_temp.append("                         where peStudent.flag_student_status = statusConst.id                 ");
		sql_temp.append("                           and statusConst.code = '0'                                         ");
		sql_temp.append(temp_where);
		sql_temp.append("                           and peStudent.Fk_Rec_Student_Id = recStudent.id                    ");
		sql_temp.append("                           and recStudent.Fk_Rec_Major_Site_Id =                              ");
		sql_temp.append("                               planMajorSite.Id                                               ");
		sql_temp.append("                           and planMajorSite.Fk_Rec_Plan_Major_Edutype_Id =                   ");
		sql_temp.append("                               planMajorEdutype.Id                                            ");
		sql_temp.append("                           and planMajorEdutype.Fk_Recruitplan_Id = rec.id                    ");
		sql_temp.append("                      group by "+temp_groupBy+" rec.name                                    ");
		sql_temp.append("                                  ) luqu on luqu.recname =                          ");
		sql_temp.append("                                                      total.recname                           ");
		sql_temp.append(temp_on);
		sql_temp.append("                                                     ) one										");
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
		this.entityClass=PeStudent.class;

	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/studentStatus/derateFeeStat";

	}
	public String getCheckBox() {
		return checkBox;
	}


	public void setCheckBox(String checkBox) {
		this.checkBox = checkBox;
	}
}
