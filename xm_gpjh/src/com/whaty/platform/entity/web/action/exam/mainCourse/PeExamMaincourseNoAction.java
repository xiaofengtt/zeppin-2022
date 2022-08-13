package com.whaty.platform.entity.web.action.exam.mainCourse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeExamMaincourseNo;
import com.whaty.platform.entity.bean.PeSemester;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.Container;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.util.JsonUtil;
/**
 * 主干课程考试场次管理表
 * @author zqf
 *
 */
public class PeExamMaincourseNoAction extends MyBaseAction {

	@Override
	public void initGrid() {
		this.getGridConfig().setTitle(this.getText("主干课程考试场次管理表"));
		this.getGridConfig().addColumn(this.getText("id"),"id",false);
		this.getGridConfig().addColumn(this.getText("考试学期"),"peSemester.name",true,false,true,"");
		this.getGridConfig().addColumn(this.getText("场次名称"),"name");
		this.getGridConfig().addColumn(this.getText("考试开始时间"),"startDatetime");
		this.getGridConfig().addColumn(this.getText("考试结束时间"),"endDatetime");
		this.getGridConfig().addRenderFunction(this.getText("设置考试课程"), "<a href=/entity/exam/examMaincourseSet.action?bean.peExamMaincourseNo.id=${value} target=_blank><font color='#0000ff'>设置考试课程</font></a>", "id");
	}
	
	
	public void checkBeforeDelete(List idList) throws EntityException{
		StringBuffer ExamNoNames = new StringBuffer();
		if(!isCanDelete(idList,ExamNoNames)){
			throw new EntityException("删除失败！<br/>“" + ExamNoNames.toString() + "”不在当前活动考试学期之内，不能删除");
		}
	}
	
	public void checkBeforeAdd() throws EntityException{
		
		if(this.getBean() != null && 
				this.getBean().getStartDatetime() != null &&
				this.getBean().getEndDatetime() != null &&
				this.getBean().getStartDatetime().after(this.getBean().getEndDatetime())){
			throw new EntityException("考试开始时间不能晚于考试结束时间");
		}
		
		DetachedCriteria dcPeSemester = DetachedCriteria.forClass(PeSemester.class);
		dcPeSemester.add(Restrictions.eq("flagActive", "1"));
		List<PeSemester> list = new ArrayList();
		try{
			list = this.getGeneralService().getList(dcPeSemester);
		}catch(Exception e){
			e.printStackTrace();
		}
		if(list.size() == 0){
			throw new EntityException("请先设置一个活动的考试学期后再添加考试场次");
		}
		this.getBean().setPeSemester(list.get(0));
	}
	
	public void checkBeforeUpdate() throws EntityException{
		if(this.getBean() != null && 
				this.getBean().getStartDatetime() != null &&
				this.getBean().getEndDatetime() != null &&
				this.getBean().getStartDatetime().after(this.getBean().getEndDatetime())){
			throw new EntityException("考试开始时间不能晚于考试结束时间");
		}
	}
	@Override
	public void setEntityClass() {
		this.entityClass = PeExamMaincourseNo.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/exam/peExamMaincourseNo";
	}
	
	public void setBean(PeExamMaincourseNo instance){
		super.superSetBean(instance);
	}
	
	public PeExamMaincourseNo getBean(){
		return (PeExamMaincourseNo)super.superGetBean();
	}
	
	public DetachedCriteria initDetachedCriteria() {
		JsonUtil.setDateformat("yyyy-MM-dd HH:mm:ss");
		DetachedCriteria dc = DetachedCriteria.forClass(PeExamMaincourseNo.class);
		dc.createAlias("peSemester", "peSemester");
		return dc;
	}
	
	/**
	 * 判断所删除的是否都是当前活动考试学期内的考试场次
	 * @param ids
	 * @param ExamNoNames
	 * @return
	 */
	private boolean isCanDelete(List ids,StringBuffer ExamNoNames){
		boolean result = false;
		
		String sql = " select examno.name from pe_exam_maincourse_no examno,pe_semester semester where examno.fk_semester_id = semester.id and semester.flag_active != '1' and examno.id in(' '";
		
		for(int i = 0; i < ids.size(); i++){
			sql += ",'"+ids.get(i).toString()+"'";
		}
		sql += ")";
		
		List list = new ArrayList();
		try {
			list = this.getGeneralService().getBySQL(sql);
			if(list.size() < 1){
				result = true;
			}else{
				ExamNoNames.append(list.get(0).toString());
				for(int j = 1; j < list.size(); j++){
					ExamNoNames.append("、" + list.get(j).toString());
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

}
