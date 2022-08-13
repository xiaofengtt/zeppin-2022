package com.whaty.platform.entity.web.action.recruit.stat;

import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeRecStudent;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;

/**
 * 报名信息查询统计
 * 
 * @author 李冰
 * 
 */
public class BaomingStatAction extends MyBaseAction {
	private String checkBox;

	@Override
	public void initGrid() {
		// 拼接查看详细操作的url
		String temp = "<a href='/entity/recruit/baomingStu.action?bean.prRecPlanMajorSite.prRecPlanMajorEdutype.peRecruitplan.id=\" + record.data['peRecruitplan.id'] + \"";
		this.getGridConfig().setCapability(false, false, false);

		this.getGridConfig().setTitle(this.getText("报名信息查询统计"));
		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("招生批次"),
				"combobox_peRecruitplan.peRecruitplanName");
		this.getGridConfig().addColumn(this.getText("peRecruitplan.id"), "peRecruitplan.id", false);
		// 根据所选择的统计条件判断显示所需要的列
		if (this.getCheckBox() != null && !"".equals(this.getCheckBox())) {
			if (this.getCheckBox().contains("site")) {
				this.getGridConfig().addColumn(this.getText("学习中心"),
						"combobox_PeSite.site_name");
				this.getGridConfig().addColumn(this.getText("peSite.id"), "peSite.id", false);
				temp += "&bean.prRecPlanMajorSite.peSite.id=\"+record.data['peSite.id']+\"";
			}
			if (this.getCheckBox().contains("edutype")) {
				this.getGridConfig().addColumn(this.getText("层次"),
						"combobox_PeEdutype.edutype_name");
				this.getGridConfig().addColumn(this.getText("peEdutype.id"), "peEdutype.id", false);
				temp += "&bean.prRecPlanMajorSite.prRecPlanMajorEdutype.peEdutype.id=\"+record.data['peEdutype.id']+\"";
			}
			if (this.getCheckBox().contains("major")) {
				this.getGridConfig().addColumn(this.getText("专业"),
						"combobox_peMajor.major_name");
				this.getGridConfig().addColumn(this.getText("peMajor.id"), "peMajor.id", false);
				temp += "&bean.prRecPlanMajorSite.prRecPlanMajorEdutype.peMajor.id=\"+record.data['peMajor.id']+\"";
			}
			if (this.getCheckBox().contains("noexam")) {
				this.getGridConfig().addColumn(this.getText("是否是免试生"),
						"combobox_enumConstByFlagNoexam.noexam");
				this.getGridConfig().addColumn(this.getText("id"), "enumConstByFlagNoexam.id", false);
				temp += "&bean.enumConstByFlagNoexam.id=\"+record.data['enumConstByFlagNoexam.id']+\"";
			}
		}
		temp += "' target='_blank'>查看明细</a>";
		this.getGridConfig().addColumn(this.getText("人数"), "num", false);
		this.getGridConfig().addRenderFunction(this.getText("操作"), temp);
		this.getGridConfig().addMenuScript(this.getText("返回"),
				"{window.history.back()}");
	}

	public Page list() {
		// 根据所选择的统计条件动态拼接查询语句
		String displayItems = "";// 显示的列
		String temp_groupBy = "";// group by 条件
		String temp_table = "";// 所查的表
		String temp_where = "";// where条件
		if (this.getCheckBox() != null && !"".equals(this.getCheckBox())) {
			if (this.getCheckBox().contains("site")) {
				displayItems += " ,peSite.name as site_name ,peSite.id as site_id";
				temp_table += " pe_site  peSite,";
				temp_where += " and prRecPlanMajorSite.Fk_Site_Id = peSite.Id ";
				temp_groupBy += " ,peSite.name ,peSite.id";
			}
			if (this.getCheckBox().contains("edutype")) {
				displayItems += " ,peEdutype.name as edutype_name ,peEdutype.id as edutype_id ";
				temp_table += " pe_edutype  peEdutype,";
				temp_where += "   and prRecPlanMajorEdutype.Fk_Edutype_Id = peEdutype.Id  ";
				temp_groupBy += " ,peEdutype.name ,peEdutype.id";
			}
			if (this.getCheckBox().contains("major")) {
				displayItems += " ,peMajor.name as major_name ,peMajor.id as major_id ";
				temp_table += "  pe_major peMajor,  ";
				temp_where += "    and prRecPlanMajorEdutype.Fk_Major_Id = peMajor.Id      ";
				temp_groupBy += " ,peMajor.name ,peMajor.id";
			}
			if (this.getCheckBox().contains("noexam")) {
				displayItems += " ,enumConstByFlagNoexam.Name as noexam ,enumConstByFlagNoexam.Id as enumId";
				temp_table += "  enum_const  enumConstByFlagNoexam    ,";
				temp_where += "  and enumConstByFlagNoexam.Id = peRecStudent.Flag_Noexam   ";
				temp_groupBy += " ,enumConstByFlagNoexam.Name ,enumConstByFlagNoexam.Id ";
			}
		}

		String sql = "  select rownum as id, one.* from (  " 
			    + "     select peRecruitplan.name      as peRecruitplanName             "
				+ "        ,peRecruitplan.id as peRecruitplan_id 						"	
				+ ",enumConstByFlagNoexam.Name as noexam ,enumConstByFlagNoexam.Id as enumId"
				+ "        ,count(peRecStudent.id) as num                                   "
				+ "    from pe_rec_student            peRecStudent,                         "
				+ "         pr_rec_plan_major_site    prRecPlanMajorSite,                   "
				+ "         pr_rec_plan_major_edutype prRecPlanMajorEdutype,                "
				+ "         enum_const  enumConstByFlagNoexam    ,"
				+ "         pe_recruitplan            peRecruitplan                         "
				+ "   where peRecStudent.Fk_Rec_Major_Site_Id = prRecPlanMajorSite.Id       "
				+ "     and prRecPlanMajorSite.Fk_Rec_Plan_Major_Edutype_Id =               "
				+ "         prRecPlanMajorEdutype.Id                                        "
				+ "     and prRecPlanMajorEdutype.Fk_Recruitplan_Id = peRecruitplan.Id      "
				+ "  and enumConstByFlagNoexam.Id = peRecStudent.Flag_Noexam   " 
				+ "  group by peRecruitplan.Name , peRecruitplan.id  ,enumConstByFlagNoexam.Name ,enumConstByFlagNoexam.Id ) one";

		StringBuffer sql_temp = new StringBuffer(sql);
		this.setSqlCondition(sql_temp);
		Page page = null;
		try {
			page = getGeneralService().getByPageSQL(sql_temp.toString(),
					Integer.parseInt(this.getLimit()),
					Integer.parseInt(this.getStart()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printS
		return page;
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PeRecStudent.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/recruit/baomingStat";
	}

	public String getCheckBox() {
		return checkBox;
	}

	public void setCheckBox(String checkBox) {
		this.checkBox = checkBox;
	}

}
