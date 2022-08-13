package com.whaty.platform.entity.web.action.studentStatus.count;

import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class PrStudentDegreeAction extends MyBaseAction {
	private String checkBox;
	private String type;


	@Override
	public void initGrid() {
		// 拼接查看详细操作的url
		String temp = "";
		if(this.getType().equals("degree")){
			temp += "<a href='/entity/studentStatus/peStudentDegree.action?1=1";
		} else {
			temp += "<a href='/entity/studentStatus/peGraduatedStudent.action?1=1";
		}
		
		this.getGridConfig().setCapability(false, false, false,true);
		this.getGridConfig().setTitle(this.getText("毕业学位统计"));
//		this.getGridConfig().addColumn(this.getText("row_num"),"row_num",false,false,false,"");
		this.getGridConfig().addColumn(this.getText("id"),"id",false,false,false,"");
		if (this.getCheckBox() != null && !"".equals(this.getCheckBox())) {
			if (this.getCheckBox().contains("site")) {
				this.getGridConfig().addColumn(this.getText("学习中心"),"combobox_peSite.peSite_Name");
				this.getGridConfig().addColumn(this.getText("peSite_id"), "peSite_id", false,false,false,"");
				if(this.getType().equals("degree")){
					temp += "&bean.peStudent.peSite.id=\"+record.data['peSite_id']+\"";
				} else {
					temp += "&bean.peSite.id=\"+record.data['peSite_id']+\"";
				}
				
			}
			if (this.getCheckBox().contains("grade")) {
				this.getGridConfig().addColumn(this.getText("年级"),"combobox_peGrade.peGrade_Name");
				this.getGridConfig().addColumn(this.getText("peGrade_id"), "peGrade_id", false,false,false,"");
				if(this.getType().equals("degree")){
					temp += "&bean.peStudent.peGrade.id=\"+record.data['peGrade_id']+\"";
				}else {
					temp += "&bean.peGrade.id=\"+record.data['peGrade_id']+\"";
				}
			}
			if (this.getCheckBox().contains("edutype")) {
				this.getGridConfig().addColumn(this.getText("层次"),"combobox_peEdutype.peEdutype_Name");
				this.getGridConfig().addColumn(this.getText("peEdutype_id"), "peEdutype_id", false,false,false,"");
				if(this.getType().equals("degree")){
					temp += "&bean.peStudent.peEdutype.id=\"+record.data['peEdutype_id']+\"";
				}else {
					temp += "&bean.peEdutype.id=\"+record.data['peEdutype_id']+\"";
				}
			}
			if (this.getCheckBox().contains("major")) {
				this.getGridConfig().addColumn(this.getText("专业"),"combobox_peMajor.peMajor_Name");
				this.getGridConfig().addColumn(this.getText("peMajor_id"), "peMajor_id", false,false,false,"");
				if(this.getType().equals("degree")){
					temp += "&bean.peStudent.peMajor.id=\"+record.data['peMajor_id']+\"";
				}else {
					temp += "&bean.peMajor.id=\"+record.data['peMajor_id']+\"";
				}
			}
		}
		this.getGridConfig().addColumn("人数", "total",false);
		temp += "' target='_blank'>查看明细</a>";
		this.getGridConfig().addRenderFunction(this.getText("操作"), temp);
		this.getGridConfig().addMenuScript(this.getText("返回"),"{window.history.back()}");
	}

	public Page list() {
		
		// 根据所选择的统计条件动态拼接查询语句
		String displayItems = "";// 显示的列
		String temp_from = ""; //from 条件
		String temp_where = "";// where条件
		String temp_groupBy = "";// group by 条件
		if (this.getCheckBox() != null && !"".equals(this.getCheckBox())) {
			if (this.getCheckBox().contains("site")) {
				displayItems += "  peSite.Name as peSite_Name, peSite.id as peSite_id, ";
				temp_from +="       pe_site    peSite,              ";
				temp_where += "      and peStudent.Fk_Site_Id = peSite.Id             ";
				temp_groupBy += "  peSite.Name , peSite.id ,";
			}
			if (this.getCheckBox().contains("grade")) {
				displayItems += "   peGrade.Name as peGrade_Name, peGrade.id as peGrade_id,    ";
				temp_from +="        pe_grade   peGrade,                ";
				temp_where += "      and peStudent.Fk_Grade_Id = peGrade.Id            ";
				temp_groupBy += "   peGrade.Name  , peGrade.id ,";
			}
			if (this.getCheckBox().contains("edutype")) {
				displayItems += "    peEdutype.Name as peEdutype_Name,   peEdutype.id as peEdutype_id,       ";
				temp_from +="         pe_edutype peEdutype,             ";
				temp_where += "       and peStudent.Fk_Edutype_Id = peEdutype.Id              ";
				temp_groupBy += "  peEdutype.Name ,peEdutype.id ,";
			}
			if (this.getCheckBox().contains("major")) {
				displayItems += " peMajor.name as peMajor_name,    peMajor.id as peMajor_id,        ";
				temp_from += " pe_major           peMajor,   ";
				temp_where += "   and peStudent.Fk_Major_Id = peMajor.Id    ";
				temp_groupBy += " peMajor.name ,  peMajor.id ,";
			}

		}
		if(temp_groupBy.length()>0){
			temp_groupBy = " group by " + temp_groupBy.substring(0, temp_groupBy.length()-1);
		}
		
		StringBuffer sql_temp = new StringBuffer();
		sql_temp.append(" select rownum as id, one.* from (   ");				
		sql_temp.append(" select    " +displayItems);		
//		sql_temp.append("        peGrade.Name as peGrade_Name,                                ");
//		sql_temp.append("        peEdutype.Name as peEdutype_Name,                            ");
//		sql_temp.append("        peMajor.Name as peMajor_Name,                                ");
		sql_temp.append("        count(*) as total                                            ");
		sql_temp.append("   from ENUM_CONST enumConst,                                           ");
//		sql_temp.append("        pe_grade   peGrade,                                          ");
//		sql_temp.append("        pe_edutype peEdutype,                                        ");
//		sql_temp.append("        pe_major   peMajor,                                          ");
//		sql_temp.append("        pe_site    peSite,                                         ");
		sql_temp.append(temp_from);
		sql_temp.append("        pe_student peStudent                                         ");
//		sql_temp.append("  where peStudent.Fk_Site_Id = peSite.Id                             ");
//		sql_temp.append("    and peStudent.Fk_Major_Id = peMajor.Id                           ");
//		sql_temp.append("    and peStudent.Fk_Grade_Id = peGrade.Id                           ");
//		sql_temp.append("    and peStudent.Fk_Edutype_Id = peEdutype.Id                       ");
		sql_temp.append("    where enumConst.Namespace = 'FlagStudentStatus'                    ");
		sql_temp.append(temp_where);
		sql_temp.append("    and enumConst.Code = '5'                                         ");
		sql_temp.append("    and peStudent.Flag_Student_Status = enumConst.Id                 ");
		if(this.getType().equals("degree")){
			sql_temp.append("    and peStudent.Degree_Date is not null                             ");
		}
//		sql_temp.append("  group by peSite.Name, peGrade.Name, peEdutype.Name, peMajor.Name	) one	");	
		sql_temp.append(temp_groupBy + "	) one	");
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
		this.entityClass = PeStudent.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/studentStatus/prStudentDegree";
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
