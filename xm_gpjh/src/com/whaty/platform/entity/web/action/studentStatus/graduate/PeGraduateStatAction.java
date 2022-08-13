package com.whaty.platform.entity.web.action.studentStatus.graduate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
/**
 * 毕业申请统计
 * @author 李冰
 *
 */
public class PeGraduateStatAction extends MyBaseAction {

	private String startDate;
	private String endDate;

	/**
	 * 转向jsp页面
	 * @return
	 */
	public String toGraduateStat(){
		return "graduateStat";
	}
	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false );
		this.getGridConfig().setTitle(this.getText("毕业申请统计"));
//		this.getGridConfig().addColumn(this.getText("row_num"),"row_num",false,false,false,"");
		this.getGridConfig().addColumn(this.getText("id"),"id",false,false,false,"");
		this.getGridConfig().addColumn(this.getText("学习中心"),"combobox_peSite.sitename");
		this.getGridConfig().addColumn(this.getText("毕业申请人数"), "total",false);
		this.getGridConfig().addColumn(this.getText("待审核人数"), "wait",false);
		this.getGridConfig().addColumn(this.getText("审核通过人数"), "pass",false);
		this.getGridConfig().addColumn(this.getText("审核不通过人数"), "nopass",false);
		this.getGridConfig().addMenuScript(this.getText("返回"),"{window.history.back()}");
	}
	
	public Page list() {
		String start = "";
		if (this.getStartDate()!=null&&this.getStartDate().length()>0){
			start = this.getStartDate();
		} else {
			start = "1900-01-01";
		}
		String end = "";
		if (this.getEndDate()!=null&&this.getEndDate().length()>0){
			end = this.formatEndDate(this.getEndDate());
		} else {
			end = this.formatEndDate("9999-01-01");;
		}
		StringBuffer sql_temp = new StringBuffer();
		sql_temp.append("  select peSite.id  as id,          														");				
		sql_temp.append("         peSite.name as sitename,                                                               ");
		sql_temp.append("         total.num as total,                                                               ");
		sql_temp.append("         nvl(waits.num, 0) as wait,                                                        ");
		sql_temp.append("         nvl(pass.num, 0) as pass,                                                         ");
		sql_temp.append("         nvl(nopass.num, 0) as nopass                                                      ");
		sql_temp.append("    from pe_site peSite, (select site.id as id, site.name as name, count(*) num                            ");
		sql_temp.append("            from system_apply apply,                                                       ");
		sql_temp.append("                 enum_const   const,                                                       ");
		sql_temp.append("                 pe_student   student,                                                     ");
		sql_temp.append("                 pe_site      site                                                         ");
		sql_temp.append("           where apply.apply_type = const.id                                               ");
		sql_temp.append("             and const.code = '11'                                                         ");
		sql_temp.append("             and apply.fk_student_id = student.id                                          ");
		sql_temp.append("             and student.fk_site_id = site.id                                              ");
		sql_temp.append("             and (apply.apply_date between to_date('" + start +"', 'yyyy-MM-DD') and         ");
		sql_temp.append("                 to_date('" +end +"', 'yyyy-mm-dd hh24:mi:ss'))                   ");
		sql_temp.append("           group by site.id, site.name) total                                              ");
		sql_temp.append("    left join (select site.id as id, site.name as name, count(*) num                       ");
		sql_temp.append("                 from system_apply apply,                                                  ");
		sql_temp.append("                      enum_const   const,                                                  ");
		sql_temp.append("                      enum_const   const2,                                                 ");
		sql_temp.append("                      pe_student   student,                                                ");
		sql_temp.append("                      pe_site      site                                                    ");
		sql_temp.append("                where apply.apply_type = const.id                                          ");
		sql_temp.append("                  and const.code = '11'                                                    ");
		sql_temp.append("                  and apply.fk_student_id = student.id                                     ");
		sql_temp.append("                  and student.fk_site_id = site.id                                         ");
		sql_temp.append("                  and apply.flag_apply_status = const2.id                                  ");
		sql_temp.append("                  and const2.code = '0'                                                    ");
		sql_temp.append("                  and (apply.apply_date between                                            ");
		sql_temp.append("                      to_date('" + start +"', 'yyyy-MM-DD') and                              ");
		sql_temp.append("                      to_date('" +end +"', 'yyyy-mm-dd hh24:mi:ss'))              ");
		sql_temp.append("                group by site.id, site.name) waits on total.id = waits.id                  ");
		sql_temp.append("    left join (select site.id as id, site.name as name, count(*) num                       ");
		sql_temp.append("                 from system_apply apply,                                                  ");
		sql_temp.append("                      enum_const   const,                                                  ");
		sql_temp.append("                      enum_const   const2,                                                 ");
		sql_temp.append("                      pe_student   student,                                                ");
		sql_temp.append("                      pe_site      site                                                    ");
		sql_temp.append("                where apply.apply_type = const.id                                          ");
		sql_temp.append("                  and const.code = '11'                                                    ");
		sql_temp.append("                  and apply.fk_student_id = student.id                                     ");
		sql_temp.append("                  and student.fk_site_id = site.id                                         ");
		sql_temp.append("                  and apply.flag_apply_status = const2.id                                  ");
		sql_temp.append("                  and const2.code = '1'                                                    ");
		sql_temp.append("                  and (apply.apply_date between                                            ");
		sql_temp.append("                      to_date('" + start +"', 'yyyy-MM-DD') and                              ");
		sql_temp.append("                      to_date('" +end +"', 'yyyy-mm-dd hh24:mi:ss'))              ");
		sql_temp.append("                group by site.id, site.name) pass on total.id = pass.id                    ");
		sql_temp.append("    left join (select site.id as id, site.name as name, count(*) num                       ");
		sql_temp.append("                 from system_apply apply,                                                  ");
		sql_temp.append("                      enum_const   const,                                                  ");
		sql_temp.append("                      enum_const   const2,                                                 ");
		sql_temp.append("                      pe_student   student,                                                ");
		sql_temp.append("                      pe_site      site                                                    ");
		sql_temp.append("                where apply.apply_type = const.id                                          ");
		sql_temp.append("                  and const.code = '11'                                                    ");
		sql_temp.append("                  and apply.fk_student_id = student.id                                     ");
		sql_temp.append("                  and student.fk_site_id = site.id                                         ");
		sql_temp.append("                  and apply.flag_apply_status = const2.id                                  ");
		sql_temp.append("                  and const2.code = '2'                                                    ");
		sql_temp.append("                  and (apply.apply_date between                                            ");
		sql_temp.append("                      to_date('" + start +"', 'yyyy-MM-DD') and                              ");
		sql_temp.append("                      to_date('" +end +"', 'yyyy-mm-dd hh24:mi:ss'))              ");
		sql_temp.append("                group by site.id, site.name) nopass on total.id = nopass.id  							");			
		sql_temp.append(" 			where peSite.id=total.id ");
		sql_temp.append("  				group by peSite.id , peSite.name , total.num,waits.num,pass.num,nopass.num");
		
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
	/**
	 * 对结束时间的处理
	 * @param endDate
	 * @return
	 */
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
		// TODO Auto-generated method stub

	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/studentStatus/peGraduateStat";
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
}
