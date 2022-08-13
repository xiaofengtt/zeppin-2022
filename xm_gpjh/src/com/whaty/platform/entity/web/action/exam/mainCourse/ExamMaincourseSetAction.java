package com.whaty.platform.entity.web.action.exam.mainCourse;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.whaty.platform.entity.bean.PrExamOpenMaincourse;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;
/**
 * 为主干课程的考试场次设定考试课程
 * @author zqf
 *
 */
public class ExamMaincourseSetAction extends MyBaseAction {

	@Override
	public void initGrid() {
		
		StringBuffer examnoName = new StringBuffer();
		if(!isCanOperate(examnoName)){
			this.getGridConfig().setCapability(false, false, false);
		}else{
			this.getGridConfig().setCapability(true, true, false);
		}
		this.getGridConfig().setTitle(examnoName.toString() + this.getText("-设置考试课程"));
		this.getGridConfig().addColumn(this.getText("id"),"id",false);
		this.getGridConfig().addColumn(this.getText("考试课程"),"peTchCourse.name");
		this.getGridConfig().addColumn(this.getText("考试场次"),"peExamMaincourseNo.name",true,false,true,"");
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PrExamOpenMaincourse.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/exam/examMaincourseSet";
	}

	public void setBean(PrExamOpenMaincourse instance){
		super.superSetBean(instance);
	}
	public PrExamOpenMaincourse getBean(){
		return (PrExamOpenMaincourse)super.superGetBean();
	}
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PrExamOpenMaincourse.class);
		dc.createAlias("peExamMaincourseNo", "peExamMaincourseNo");
		dc.createAlias("peTchCourse", "peTchCourse");
		return dc;
	}
	
	public boolean isCanOperate(StringBuffer examnoName){
		boolean result = false;
		List list = new ArrayList();
		String sql = "";
		try {
			sql = " select semester.flag_active,examno.name                                           " + 
			"   from pe_exam_maincourse_no examno, pe_semester semester    " +
			"  where examno.fk_semester_id = semester.id                   " +
			"    and examno.id = '"+this.getBean().getPeExamMaincourseNo().getId()+"'    " ;
			
			list = this.getGeneralService().getBySQL(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(list.size() > 0){
			Object[] obj = (Object[])list.get(0);
			examnoName.append(obj[1].toString());
			if(obj[0] != null && "1".equals(obj[0].toString())){
				result = true;
			}
		}
		return result;
	}
}
