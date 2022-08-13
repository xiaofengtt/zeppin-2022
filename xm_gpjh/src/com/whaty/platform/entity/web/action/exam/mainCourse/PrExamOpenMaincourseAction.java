package com.whaty.platform.entity.web.action.exam.mainCourse;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.whaty.platform.entity.bean.PrExamOpenMaincourse;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.web.action.MyBaseAction;
/**
 * 用于修改主干课程所属的考试场次
 * @author zqf
 *
 */
public class PrExamOpenMaincourseAction extends MyBaseAction {

	@Override
	public void initGrid() {
		//删除添加功能 （20090424 龚妮娜）
		this.getGridConfig().setCapability(false,true,true);
		this.getGridConfig().setTitle(this.getText("考试课程调整"));
		this.getGridConfig().addColumn(this.getText("id"),"id",false);
		this.getGridConfig().addColumn(this.getText("考试学期"),"peExamMaincourseNo.peSemester.name",false,false,true,"");
		ColumnConfig column = new ColumnConfig(this.getText("考试场次"),"peExamMaincourseNo.name");
		column.setComboSQL("select t.id,t.name from pe_exam_maincourse_no t ,pe_semester s where t.fk_semester_id = s.id and s.flag_active = '1'");
		this.getGridConfig().addColumn(column);
		this.getGridConfig().addColumn(this.getText("考试课程"),"peTchCourse.name",true,false,true,"");
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PrExamOpenMaincourse.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/exam/prExamOpenMaincourse";
	}
	public void setBean(PrExamOpenMaincourse instance){
		super.superSetBean(instance);
	}
	
	public PrExamOpenMaincourse getBean(){
		return (PrExamOpenMaincourse)super.superGetBean();
	}
	
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PrExamOpenMaincourse.class);
		dc.createAlias("peTchCourse", "peTchCourse");
		dc.createCriteria("peExamMaincourseNo", "peExamMaincourseNo")
			.createAlias("peSemester", "peSemester");
		return dc;
	}
	
	public void checkBeforeUpdate() throws EntityException{		
		if(!isCanOperate()){
			throw new EntityException("只能调整当前活动学期下的课程信息");
		}
	}
	
	/**
	 * 用于判断更新操作时，所更新的记录是否是当前活动学期内的，
	 * 只有当前活动学期的记录才可以修改
	 * @return
	 */
	private boolean isCanOperate(){
		boolean result = false;
		List list = new ArrayList();
		String sql = "";
		try {
			sql = " select t.id                                                              " +
			"   from pr_exam_open_maincourse t, pe_exam_maincourse_no n, pe_semester s " +
			"  where t.fk_exam_maincourse_no_id = n.id                                 " +
			"    and n.fk_semester_id = s.id                                           " +
			"    and s.flag_active = '1'                                               " +
			"    and t.id = '"+this.getBean().getId()+"'                               " ;
			list = this.getGeneralService().getBySQL(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(list.size() > 0){
			result = true;
		}
		return result;
	}
	
}
