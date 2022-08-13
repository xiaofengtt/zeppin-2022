package com.whaty.platform.entity.web.action.exam.mainCourse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PrExamOpenMaincourse;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.exam.mainCourse.PrExamOpenMaincourseService;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.util.JsonUtil;
/**
 * 为所选中的学生预约主干课程考试
 * @author zqf
 *
 */
public class MaincourseElectivecourseAction extends MainCourseBaseAction {

	private PrExamOpenMaincourseService prExamOpenMaincourseService;
	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().setTitle(this.getText(this.getSemesterName() + "主干课程考试-预约考试课程"));
		this.getGridConfig().addColumn(this.getText("id"),"id",false);
		this.getGridConfig().addColumn(this.getText("主干课程名称"),"peTchCourse.name");
		this.getGridConfig().addMenuFunction(this.getText("选择课程以预约考试"), "/entity/exam/maincourseElectivecourse_saveBooking.action", true, true);
		this.getGridConfig().addMenuScript(this.getText("返回"), "{window.history.go(-2)}");
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PrExamOpenMaincourse.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/exam/maincourseElectivecourse";
	}
	
	public String execute() {
		ActionContext axt = ActionContext.getContext();
		axt.getSession().put("maincourseElectivecourse_ids", this.getIds());
		return super.execute();
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
		dc.add(Restrictions.eq("peSemester.flagActive", "1"));
		return dc;
	}
	
	/**
	 * 主干课程预约考试操作
	 * @return
	 */
	public String saveBooking(){
		Map map = new HashMap();
		String stu_ids = ActionContext.getContext().getSession().get("maincourseElectivecourse_ids").toString();
		
		try{
			this.getPrExamOpenMaincourseService().saveBooking(stu_ids, this.getCourseId());
			map.put("success", true);
			map.put("info", "主干课程预约考试成功");
		}catch(Exception e){
			e.printStackTrace();
			map.clear();
			map.put("success", false);
			map.put("info", e.getMessage());
		}
		
		this.setJsonString(JsonUtil.toJSONString(map));
		return json();
	}
	
	private String getCourseId(){
		String id = "";
		String sql = " select t.fk_course_id from pr_exam_open_maincourse t where t.id = '"+this.getIds().substring(0,this.getIds().length() - 1)+"' ";
		
		List list = new ArrayList();
		
		try{
			list = this.getGeneralService().getBySQL(sql);
			if(list.size() > 0){
				id = list.get(0).toString();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return id;
	}
	
	public PrExamOpenMaincourseService getPrExamOpenMaincourseService() {
		return prExamOpenMaincourseService;
	}

	public void setPrExamOpenMaincourseService(
			PrExamOpenMaincourseService prExamOpenMaincourseService) {
		this.prExamOpenMaincourseService = prExamOpenMaincourseService;
	}

}
