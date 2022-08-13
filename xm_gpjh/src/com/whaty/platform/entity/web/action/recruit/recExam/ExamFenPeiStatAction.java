package com.whaty.platform.entity.web.action.recruit.recExam;

import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeRecStudent;
import com.whaty.platform.entity.bean.PrRecPatrolSetting;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class ExamFenPeiStatAction extends MyBaseAction {

	private String siteName;
	private String majorName;
	private String edutypeName;
	
	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getMajorName() {
		return majorName;
	}

	public void setMajorName(String majorName) {
		this.majorName = majorName;
	}

	public String getEdutypeName() {
		return edutypeName;
	}

	public void setEdutypeName(String edutypeName) {
		this.edutypeName = edutypeName;
	}

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
		this.getGridConfig().setTitle(this.getText("统计结果"));
		this.getGridConfig().setCapability(false, false, false);
		
		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("已分配考场人数"), "allotCount",false);
		this.getGridConfig().addColumn(this.getText("未分配考场人数"), "unallotCount",false);
		this.getGridConfig().addMenuScript(this.getText("返回"),"{window.history.back()}");
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PeRecStudent.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/recruit/examfenpeistat";
	}
	public void setBean(PeRecStudent instance) {
		super.superSetBean(instance);
		
	}
	
	public PeRecStudent getBean(){
		return (PeRecStudent) super.superGetBean();
	}
	
	
	public Page list(){
		
		String sql_temp = "";
		
		if(this.getSiteName() != null && !"".equals(this.getSiteName())){
			sql_temp += " and site.name = '"+this.getSiteName()+"' ";
		}
		if(this.getMajorName() != null && !"".equals(this.getMajorName())){
			sql_temp += " and major.name = '"+this.getMajorName()+"' ";
		}
		if(this.getEdutypeName() != null && !"".equals(this.getEdutypeName())){
			sql_temp += " and edutype.name = '"+this.getEdutypeName()+"' ";
		}
		String  sql= " select rownum as id, one.* from ( select total_num.total - unassign_num.unassign, unassign_num.unassign         "
		 + "     from (select count(student.id) as total                                     "
		 + "             from pe_rec_student            student,                             "
		 + "                  pr_rec_plan_major_site    majorsite,                           "
		 + "                  pe_site                   site,                                "
		 + "                  pr_rec_plan_major_edutype majoredutype,                        "
		 + "                  pe_major                  major,                               "
		 + "                  pe_edutype                edutype,                             "
		 + "                  pe_recruitplan            recplan,                             "
		 + "                  enum_const                const1,                              "
		 + "                  enum_const                const2                               "
		 + "            where student.fk_rec_major_site_id = majorsite.id                    "
		 + "              and student.flag_noexam = const1.id                                "
		 + "              and const1.code = '0'                                              "
		 + "              and student.flag_rec_status = const2.id                            "
		 + "              and const2.code = '3'                                              "
		 + "              and majorsite.fk_site_id = site.id                                 "
		 + "              and majorsite.fk_rec_plan_major_edutype_id = majoredutype.id       "
		 + "              and majoredutype.fk_major_id = major.id                            "
		 + "              and majoredutype.fk_edutype_id = edutype.id                        "
		 + sql_temp +"              and majoredutype.fk_recruitplan_id = recplan.id                    "
		 + "              and recplan.flag_active = '1') total_num,                          "
		 + "          (select count(student.id) as unassign                                  "
		 + "             from pe_rec_student            student,                             "
		 + "                  pr_rec_plan_major_site    majorsite,                           "
		 + "                  pe_site                   site,                                "
		 + "                  pr_rec_plan_major_edutype majoredutype,                        "
		 + "                  pe_major                  major,                               "
		 + "                  pe_edutype                edutype,                             "
		 + "                  pe_recruitplan            recplan,                             "
		 + "                  enum_const                const1,                              "
		 + "                  enum_const                const2                               "
		 + "            where student.fk_rec_major_site_id = majorsite.id                    "
		 + "              and student.flag_noexam = const1.id                                "
		 + "              and const1.code = '0'                                              "
		 + "              and student.flag_rec_status = const2.id                            "
		 + "              and const2.code = '3'                                              "
		 + "              and majorsite.fk_site_id = site.id                                 "
		 + "              and majorsite.fk_rec_plan_major_edutype_id = majoredutype.id       "
		 + "              and majoredutype.fk_major_id = major.id                            "
		 + "              and majoredutype.fk_edutype_id = edutype.id                        "
		 + sql_temp + "              and majoredutype.fk_recruitplan_id = recplan.id                    "
		 + "              and recplan.flag_active = '1'                                      "
		 + "              and student.fk_rec_room is null) unassign_num		)one									"	;
		StringBuffer sqltemp = new StringBuffer(sql);
		this.setSqlCondition(sqltemp);
		Page page = null;
		try{
			page = this.getGeneralService().getByPageSQL(sqltemp.toString(), Integer.parseInt(this.getLimit()), Integer.parseInt(this.getStart()));
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		return page;
	}
}
