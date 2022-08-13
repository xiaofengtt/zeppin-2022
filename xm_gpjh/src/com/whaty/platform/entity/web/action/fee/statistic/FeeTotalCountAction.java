package com.whaty.platform.entity.web.action.fee.statistic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class FeeTotalCountAction extends MyBaseAction {
	private String startDate;
	private String endDate;
	private String feeType;
	private String checkBox;
	/**
	 * 转向条件设置页面
	 * 
	 * @return
	 */
	public String turntoStat() {
		return "stat";
	}

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false );
		this.getGridConfig().setTitle(this.getText("交费人数统计结果"));
//		this.getGridConfig().addColumn(this.getText("row_num"),"row_num",false,false,false,"");
		this.getGridConfig().addColumn(this.getText("id"),"id",false,false,false,"");
		if (this.getCheckBox() != null && !"".equals(this.getCheckBox())) {
			if (this.getCheckBox().contains("site")) {
				this.getGridConfig().addColumn(this.getText("学习中心"),"combobox_peSite.peSite_name");
			}
			if (this.getCheckBox().contains("edutype")) {
				this.getGridConfig().addColumn(this.getText("层次"),"combobox_peEdutype.peEdutype_name");
			}
			if (this.getCheckBox().contains("major")) {
				this.getGridConfig().addColumn(this.getText("专业"),"combobox_peMajor.peMajor_name");
			}
			if (this.getCheckBox().contains("grade")) {
				this.getGridConfig().addColumn(this.getText("年级"),"combobox_peGrade.peGrade_Name");
			}
		}
		this.getGridConfig().addColumn(this.getText("交费统计人数"), "totalCount",false);
		this.getGridConfig().addColumn(this.getText("交费总金额"), "totalFee",false);
		this.getGridConfig().addMenuScript(this.getText("返回"),"{window.history.back()}");
		
	}
	public Page list() {
		// 根据所选择的统计条件动态拼接查询语句
		String displayItems = "";// 显示的列
		String temp_groupBy = "";// group by 条件
		if (this.getCheckBox() != null && !"".equals(this.getCheckBox())) {
			if (this.getCheckBox().contains("site")) {
				displayItems += " fee.peSiteName as peSite_Name,    ";
				temp_groupBy += " fee.peSiteName ,";
			}
			if (this.getCheckBox().contains("edutype")) {
				displayItems += "    fee.peEdutypeName as peEdutype_Name,       ";
				temp_groupBy += "  fee.peEdutypeName ,";
			}
			if (this.getCheckBox().contains("major")) {
				displayItems += "  fee.peMajorName as peMajor_name,        ";
				temp_groupBy += " fee.peMajorName ,";
			}
			if (this.getCheckBox().contains("grade")) {
				displayItems += "   fee.peGradeName as peGrade_Name,      ";
				temp_groupBy += "  fee.peGradeName  ,";
			}
		}
		if(temp_groupBy.length()>0){
			temp_groupBy = " group by " + temp_groupBy.substring(0, temp_groupBy.length()-1);
		}
		String temp_where = "";// where条件
		if(!this.getFeeType().equals("所有交费类型")){
			temp_where += "  and   enumConst.name = '" + this.getFeeType() + "'  ";
		}
		String start = "";
		if (this.getStartDate()!=null&&this.getStartDate().length()>0){
			start = this.getStartDate();
		} else {
			start = "0001-01-01";
		}
		String end = "";
		if (this.getEndDate()!=null&&this.getEndDate().length()>0){
			end = this.formatEndDate(this.getEndDate());
		} else {
			end = this.formatEndDate("9999-01-01");;
		}
		StringBuffer sql_temp = new StringBuffer();
//		sql_temp.append("select rownum as id,one.* from (  select count(fee.stu_id) as totalCount, sum(fee.money)	as totalFee			                                          ");   
//		sql_temp.append("   from (select prFeeDetail.fk_stu_id as stu_id,                                           ");
//		sql_temp.append("                sum(prFeeDetail.fee_amount) as money                                       ");
//		sql_temp.append("           from pr_fee_detail prFeeDetail,                                                 ");
//		sql_temp.append("                pe_student    peStudent,                                                   ");
//		sql_temp.append("                pe_site       peSite,                                                      ");
//		sql_temp.append("                pe_edutype    peEdutype,                                                   ");
//		sql_temp.append("                pe_major      peMaor,                                                      ");
//		sql_temp.append("                pe_grade      peGrade,                                                     ");
//		sql_temp.append("                enum_const    enumConst                                                    ");
//		sql_temp.append("          where prFeeDetail.fee_amount > 0                                                 ");
//		sql_temp.append("            and peStudent.Id = prFeeDetail.Fk_Stu_Id                                       ");
//		sql_temp.append("            and peStudent.Fk_Site_Id = peSite.Id                                           ");
//		sql_temp.append("            and peStudent.Fk_Edutype_Id = peEdutype.Id                                     ");
//		sql_temp.append("            and peStudent.Fk_Major_Id = peMaor.Id                                          ");
//		sql_temp.append("            and peStudent.Fk_Grade_Id = peGrade.Id                                         ");
//		sql_temp.append("            and enumConst.Namespace = 'FlagFeeType'                                        ");
//		sql_temp.append("            and prFeeDetail.Flag_Fee_Type = enumConst.Id                                   ");
//		sql_temp.append("            and enumConst.Code in ('1', '0')                                               ");
//		sql_temp.append(temp_where);
//		sql_temp.append("   and (prFeeDetail.input_date between to_date('"+this.getStartDate()+"', 'yyyy-MM-DD')  and   to_date('"+end+"', 'yyyy-mm-dd hh24:mi:ss'))      ");
//		sql_temp.append("          group by prFeeDetail.fk_stu_id) fee  ) one                                            "); 
		
		sql_temp.append("  select rownum as id, one.*																																								");	
		sql_temp.append("    from (select  " +  displayItems);
//		sql_temp.append("                 fee.peEdutypeName as peEdutype_Name,                                                      ");
//		sql_temp.append("                 fee.peMajorName as peMajor_name,                                                          ");
//		sql_temp.append("                 fee.peGradeName as peGrade_Name,                                                          ");
		sql_temp.append("                 count(fee.stu_id) as totalCount,                                                                        ");
		sql_temp.append("                 nvl(sum(fee.money),0) as  totalFee                                                                     ");
		sql_temp.append("            from (select peSite.name as peSiteName,                                                        ");
		sql_temp.append("                         peEdutype.Name as peEdutypeName,                                                  ");
		sql_temp.append("                         peMajor.Name as peMajorName,                                                      ");
		sql_temp.append("                         peGrade.name as peGradeName,                                                      ");
		sql_temp.append("                         prFeeDetail.fk_stu_id as stu_id,                                                  ");
		sql_temp.append("                         sum(prFeeDetail.fee_amount) as money                                              ");
		sql_temp.append("                    from pr_fee_detail prFeeDetail,                                                        ");
		sql_temp.append("                         pe_student    peStudent,                                                          ");
		sql_temp.append("                         pe_site       peSite,                                                             ");
		sql_temp.append("                         pe_edutype    peEdutype,                                                          ");
		sql_temp.append("                         pe_major      peMajor,                                                            ");
		sql_temp.append("                         pe_grade      peGrade,                                                            ");
		sql_temp.append("                         enum_const    enumConst                                                           ");
		sql_temp.append("                   where prFeeDetail.fee_amount > 0                                                        ");
		sql_temp.append("                     and peStudent.Id = prFeeDetail.Fk_Stu_Id                                              ");
		sql_temp.append("                     and peStudent.Fk_Site_Id = peSite.Id                                                  ");
		sql_temp.append("                     and peStudent.Fk_Edutype_Id = peEdutype.Id                                            ");
		sql_temp.append("                     and peStudent.Fk_Major_Id = peMajor.Id                                                ");
		sql_temp.append("                     and peStudent.Fk_Grade_Id = peGrade.Id                                                ");
		sql_temp.append("                     and enumConst.Namespace = 'FlagFeeType'                                               ");
		sql_temp.append("                     and prFeeDetail.Flag_Fee_Type = enumConst.Id                                          ");
		sql_temp.append("                     and enumConst.Code in ('1', '0')     " + temp_where);
		sql_temp.append("   and (prFeeDetail.input_date between to_date('" + start +"', 'yyyy-MM-DD')  and   to_date('" +end +"', 'yyyy-mm-dd hh24:mi:ss'))      ");
		sql_temp.append("                   group by peSite.name,                                                                   ");
		sql_temp.append("                            peEdutype.Name,                                                                ");
		sql_temp.append("                            peMajor.Name,                                                                  ");
		sql_temp.append("                            peGrade.name,                                                                  ");
		sql_temp.append("                            prFeeDetail.fk_stu_id) fee                                                     ");
//		sql_temp.append("           group by fee.peSiteName,                                                                        ");
//		sql_temp.append("                    fee.peEdutypeName,                                                                     ");
//		sql_temp.append("                    fee.peMajorName,                                                                       ");
		sql_temp.append( temp_groupBy + "          ) one																																		");	
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
	
	private String formatEndDate(String endDate){
	       try {
	    	   SimpleDateFormat bartDateFormat =  
	           new SimpleDateFormat("yyyy-MM-dd");  

			Date date = bartDateFormat.parse(endDate);
			long time = date.getTime()+ 86400000 - 1;
			Date end = new Date(time);

		       SimpleDateFormat bartDateFormat2 =  
		       new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		       return bartDateFormat2.format(end);
	       } catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public void setEntityClass() {
		this.entityClass = PeStudent.class;		
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/fee/feeTotalCount";
	}
	public void setBean(PeStudent instance){
		this.superSetBean(instance);
	}
	
	public PeStudent getBean(){
		return (PeStudent)this.superGetBean();
	}

	public String getFeeType() {
		return feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getCheckBox() {
		return checkBox;
	}

	public void setCheckBox(String checkBox) {
		this.checkBox = checkBox;
	}

}
