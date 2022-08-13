package com.whaty.platform.entity.web.action.teaching.elective;

import java.util.Map;

import com.whaty.platform.entity.bean.PrTchStuElective;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;

/**
 * 已开课统计
 * 
 * @author zy
 * 
 */
public class ConfirmedCourseStatAction extends MyBaseAction<PrTchStuElective> {

	@Override
	public void initGrid() {

		this.getGridConfig().setTitle(this.getText("已开课程统计"));
		this.getGridConfig().setCapability(false, false, false);
//		this.getGridConfig().addColumn(this.getText("s"), "row_num", false,
//				false, false, "");

		this.getGridConfig().addColumn(this.getText("id"), "id", false, false,
				false, "");
		this.getGridConfig().addColumn(this.getText("课程"),
				"combobox_peTchCourse.q2");
		this.getGridConfig().addColumn(this.getText("总人数"),"total",false);
		this.getGridConfig().addColumn(this.getText("已开课程人数"), "confirmNum",
				false);
		this.getGridConfig().addColumn(this.getText("未开课程人数"),"uncomfirnNum",false);

	}

	@Override
	public void setEntityClass() {
		this.entityClass = PrTchStuElective.class;

	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/teaching/confirmedCourseStat";

	}

	@Override
	public Page list() {
		String sql =" select rownum as id, one.*  from (select  peTchCourse.name q2, nvl(j.totalNum,0) total, nvl(i.electiveNum,0) confirmNum, (nvl(j.totalNum,0) - nvl(i.electiveNum,0)) uncomfirnNum"+
		"           from pr_tch_opencourse f                                                                                     "+
		"          inner join pe_tch_course peTchCourse on f.fk_course_id = peTchCourse.id                                       "+
		"          inner join pe_semester h on f.fk_semester_id = h.id                                                           "+
		"           left join (select b.id, count(1) electiveNum                                                                 "+
		"                        from pr_tch_stu_elective a                                                                      "+
		"                       inner join pr_tch_opencourse b on a.fk_tch_opencourse_id = b.id                                  "+
		"                       inner join pe_tch_course c on b.fk_course_id = c.id                                              "+
		"                       inner join pe_semester d on b.fk_semester_id = d.id                                              "+
		"                       inner join enum_const e on a.flag_elective_admission = e.id                                      "+
		"                       where d.flag_active = '1'                                                                   "+
		"                         and e.code = '1'                                                                               "+
		"                       group by b.id) i on f.id = i.id                                                                  "+
		"           left join(select b.id, count(1) totalNum                                                                     "+
		"                        from pr_tch_stu_elective a                                                                      "+
		"                       inner join pr_tch_opencourse b on a.fk_tch_opencourse_id = b.id                                  "+
		"                       inner join pe_tch_course c on b.fk_course_id = c.id                                              "+
		"                       inner join pe_semester d on b.fk_semester_id = d.id                                              "+
		"                       inner join enum_const e on a.flag_elective_admission = e.id                                      "+
		"                       where d.flag_active = '1'                                                                   "+
		"                       group by b.id) j on f.id = j.id                                                                  "+
		"          where h.flag_active = '1'  group by peTchCourse.name ,j.totalNum ,i.electiveNum)one   ";

		Page page = null;
		StringBuffer sb = new StringBuffer(sql);
		  this.setSqlCondition(sb);
		try {
			page = this.getGeneralService().getByPageSQL(sb.toString(),
					Integer.parseInt(this.getLimit()),
					Integer.parseInt(this.getStart()));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}

}
