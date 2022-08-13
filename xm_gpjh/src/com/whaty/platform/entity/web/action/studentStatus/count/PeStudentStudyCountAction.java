package com.whaty.platform.entity.web.action.studentStatus.count;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.web.action.studentStatus.stuInfo.PeStudentInfoAction;
import com.whaty.platform.util.Const;

public class PeStudentStudyCountAction extends PeStudentInfoAction {


	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false,true);
		this.getGridConfig().setTitle(this.getText("学生综合信息列表"));

		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("学号"), "regNo");
		this.getGridConfig().addColumn(this.getText("姓名"), "trueName");
		this.getGridConfig().addColumn(this.getText("学习中心"), "peSite.name");
		this.getGridConfig().addColumn(this.getText("年级"), "peGrade.name");
		this.getGridConfig().addColumn(this.getText("专业"), "peMajor.name");
		this.getGridConfig().addColumn(this.getText("层次"), "peEdutype.name");
		ColumnConfig column = new ColumnConfig(this.getText("状态"),"enumConstByFlagStudentStatus.name");
		column.setComboSQL("select id,name from enum_const where namespace='FlagStudentStatus' and code>='4'");
		this.getGridConfig().addColumn(column);
		this.getGridConfig().addColumn("登陆次数", "ssoUser.loginNum");
		this.getGridConfig().addColumn("最后登录时间", "ssoUser.lastLoginDate",false);
		this.getGridConfig().addColumn("最后登录IP", "ssoUser.lastLoginIp",false);
		this.getGridConfig().addRenderFunction("在校综合信息", "<a href='/entity/studentStatus/peStudentStudyCount_showinfoofstu.action?bean.id=${value}' target='_blank'><font color='blue'>查看学生详情</font></a>", "id");

	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/studentStatus/peStudentStudyCount";
	}

	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeStudent.class);
		dc.createAlias("prStudentInfo", "prStudentInfo")
			.createAlias("ssoUser", "ssoUser")
			.createAlias("peSite", "peSite")
			.createAlias("peMajor", "peMajor")
			.createAlias("peEdutype","peEdutype")
			.createAlias("peGrade", "peGrade")
			.createAlias("enumConstByFlagStudentStatus", "enumConstByFlagStudentStatus")
			.add(Restrictions.ge("enumConstByFlagStudentStatus.code", "4"));
		return dc;
	}
	
	public String showinfoofstu(){
		try {
			this.setBean(this.getGeneralService().getById(this.getBean().getId()));
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "studetinfo";
	}
	
	/**
	 * 获取此学生的教学计划
	 * @return
	 */
	public List getTchProgram(){
		String sql = "select ptp.name,ptp.graduate_min_credit         "+
			" from (select fk_major_id, fk_grade_id, fk_edutype_id ,flag_major_type   "+
			"         from pe_student                                 "+
			"        where id = '"+this.getBean().getId()+"') ps,     "+
			"      pe_tch_program ptp                                 "+
			"where ps.fk_major_id = ptp.fk_major_id                   "+
			"  and ps.fk_grade_id = ptp.fk_grade_id                   "+
			"  and ps.flag_major_type = ptp.flag_major_type                   "+
			"  and ps.fk_edutype_id = ptp.fk_edutype_id";
		List list = null;
		try {
			list = this.getGeneralService().getBySQL(sql);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List getCoursegroup(){
		String sql = "select id from pe_tch_coursegroup order by id ";
		List list = null;
		try {
			list = this.getGeneralService().getBySQL(sql);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 获取学生课程学习成绩信息
	 * @param id
	 * 	只能是必修课和专业选修课
	 * @return
	 */
	public List getCoursesInfos(String id){
		java.util.List list = new ArrayList();
		String sql0 = "select tcg.name, tpg.min_credit              "+
			 "           from (select *                             "+
			 "                   from pe_tch_program_group          "+
			 "                  where fk_program_id =               "+
			 "                    (select ptp.id                    "+
			 "                       from (select fk_major_id, fk_grade_id, fk_edutype_id ,flag_major_type"+
			 "                               from pe_student                              "+
			 "                              where id = '"+this.getBean().getId()+"') ps,  "+
			 "                            pe_tch_program ptp                              "+
             "                      where ps.fk_major_id = ptp.fk_major_id                "+
			 "                        and ps.fk_grade_id = ptp.fk_grade_id                "+
			 "                        and ps.flag_major_type = ptp.flag_major_type                "+
			 "                        and ps.fk_edutype_id = ptp.fk_edutype_id)) tpg,     "+
			 "                (select * from pe_tch_coursegroup where id = '"+id+"') tcg  "+
			 "          where tpg.fk_coursegroup_id = tcg.id";
		String sql1 = "";
		if(id==Const.publicalternation_id){
			sql1="select ptc.code, "+
				"       ptc.name, "+
				"       e.code status_code,"+
				"       to_char(tse.elective_date,'yyyy-mm-dd'), "+
				"       tse.score_total, "+
				"       tse.score_usual, "+
				"       tse.score_homework, "+
				"       tse.score_exam, "+
				"       tse.score_system, "+
				"       tpc.credit, "+
				"       tse.online_time, "+
				"       e.name status "+
				"  from pr_tch_program_course tpc, "+
				"       (select * "+
				"          from pr_tch_stu_elective "+
				"         where fk_stu_id = '"+this.getBean().getId()+"') tse, "+
				"       pe_tch_course ptc, "+
				"       (select * from enum_const where namespace = 'FlagScoreStatus') e "+
				" where ptc.id = tpc.fk_course_id "+
				"   and tse.fk_tch_program_course = tpc.id "+
				"   and e.id = tse.flag_score_status "+
				"minus "+
				"select ptc.code, "+
				"       ptc.name, "+
				"       e.code status_code,"+
				"       to_char(tse.elective_date,'yyyy-mm-dd'), "+
				"       tse.score_total, "+
				"       tse.score_usual, "+
				"       tse.score_homework, "+
				"       tse.score_exam, "+
				"       tse.score_system, "+
				"       tpc.credit, "+
				"       tse.online_time, "+
				"       e.name status "+
				"  from pr_tch_program_course tpc, "+
				"       (select * "+
				"          from pr_tch_stu_elective "+
				"         where fk_stu_id = '"+this.getBean().getId()+"') tse, "+
				"       (select * "+
				"          from pe_tch_program_group "+
				"         where fk_program_id = "+
				"               (select ptp.id "+
				"                  from (select fk_major_id, fk_grade_id, fk_edutype_id ,flag_major_type"+
				"                          from pe_student "+
				"                         where id = '"+this.getBean().getId()+"') ps, "+
				"                       pe_tch_program ptp "+
				"                 where ps.fk_major_id = ptp.fk_major_id "+
				"                   and ps.fk_grade_id = ptp.fk_grade_id "+
				"                   and ps.flag_major_type = ptp.flag_major_type "+
				"                   and ps.fk_edutype_id = ptp.fk_edutype_id)) tpg, "+
				"       (select * from pe_tch_coursegroup where id != '"+id+"') tcg, "+
				"       pe_tch_course ptc, "+
				"       (select * from enum_const where namespace = 'FlagScoreStatus') e "+
				" where ptc.id = tpc.fk_course_id "+
				"   and tse.fk_tch_program_course(+) = tpc.id "+
				"   and tpc.fk_programgroup_id = tpg.id "+
				"   and tpg.fk_coursegroup_id = tcg.id "+
				"   and e.id = tse.flag_score_status";
		}else{
			sql1 = "select ptc.code, "+
				"       ptc.name, "+
				"       e.code status_code,"+
				"       to_char(tse.elective_date,'yyyy-mm-dd'), "+
				"       tse.score_total, "+
				"       tse.score_usual, "+
				"       tse.score_homework, "+
				"       tse.score_exam, "+
				"       tse.score_system, "+
				"       tpc.credit, "+
				"       tse.online_time, "+
				"       e.name status "+
				"  from pr_tch_program_course tpc, "+
				"       (select * "+
				"          from pr_tch_stu_elective "+
				"         where fk_stu_id = '"+this.getBean().getId()+"') tse, "+
				"       (select * "+
				"          from pe_tch_program_group "+
				"         where fk_program_id = "+
				"               (select ptp.id "+
				"                  from (select fk_major_id, fk_grade_id, fk_edutype_id ,flag_major_type"+
				"                          from pe_student "+
				"                         where id = '"+this.getBean().getId()+"') ps, "+
				"                       pe_tch_program ptp "+
				"                 where ps.fk_major_id = ptp.fk_major_id "+
				"                   and ps.fk_grade_id = ptp.fk_grade_id "+
				"                   and ps.flag_major_type = ptp.flag_major_type "+
				"                   and ps.fk_edutype_id = ptp.fk_edutype_id)) tpg, "+
				"       (select * from pe_tch_coursegroup where id = '"+id+"') tcg, "+
				"       pe_tch_course ptc, "+
				"       (select * from enum_const where namespace = 'FlagScoreStatus') e "+
				" where ptc.id = tpc.fk_course_id "+
				"   and tse.fk_tch_program_course(+) = tpc.id "+
				"   and tpc.fk_programgroup_id = tpg.id "+
				"   and tpg.fk_coursegroup_id = tcg.id "+
				"   and e.id(+) = tse.flag_score_status";
		}
		try {
			list.add(this.getGeneralService().getBySQL(sql0));
			list.add(this.getGeneralService().getBySQL(sql1));
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return list;
	}	
}
