package com.whaty.platform.entity.web.action.teaching.basicInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeTchCourse;
import com.whaty.platform.entity.bean.PeTeacher;
import com.whaty.platform.entity.bean.PrTchCourseTeacher;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.util.JsonUtil;

public class PrTchCourseTeacherOutAction extends MyBaseAction {

	private String courseId;
	@Override
	public void initGrid() {
		String courseName=this.getCourseName();
		this.getGridConfig().setTitle("为"+courseName+"课程添加培训教师");
		this.getGridConfig().setCapability(false, false, false, true);
		this.getGridConfig().addColumn("","id",false);
		this.getGridConfig().addColumn(this.getText("姓名"),"trueName");
		this.getGridConfig().addColumn(this.getText("性别"),"enumConstByGender.name");
		this.getGridConfig().addColumn(this.getText("是否有效"),"enumConstByFlagIsvalid.name", false);
		this.getGridConfig().addColumn(this.getText("出生日期"),"birthDate", false);
		this.getGridConfig().addColumn(this.getText("工作单位"),"workplace", false);
		this.getGridConfig().addMenuFunction(this.getText("分配教师"), "/entity/teaching/prTchCourseTeacherOutAction_addCourse.action?courseId="+this.getCourseId(), false, true);
		
	}

	private String getCourseName() {
		String name=null;
		PeTchCourse course=null;
		try {
			this.getGeneralService().getGeneralDao().setEntityClass(PeTchCourse.class);
			course = (PeTchCourse) this.getGeneralService().getById(this.getCourseId());
		} catch (EntityException e) {
			e.printStackTrace();
		}
		name=course.getName();
		return name;
	}
	public String addCourse(){
		Map map=new HashMap();
		map.put("success", "false");
		map.put("info", "添加失败");
		boolean flag=true;
		PeTchCourse course=null;
		try {
			this.getGeneralService().getGeneralDao().setEntityClass(PeTchCourse.class);
			course=(PeTchCourse) this.getGeneralService().getById(this.getCourseId());
		} catch (EntityException e) {
			e.printStackTrace();
			if(course==null){
				flag=false;
			}
			
		}
		String[] ids=this.getIds().split(",");
		for(int i=0;i<ids.length;i++){
			PeTeacher teacher=null;
			try {
				this.getGeneralService().getGeneralDao().setEntityClass(PeTeacher.class);
				teacher=(PeTeacher) this.getGeneralService().getById(ids[i]);
			} catch (EntityException e) {
				e.printStackTrace();
				if(teacher==null){
					flag=false;
				}
			}
			PrTchCourseTeacher ct=new PrTchCourseTeacher();
			ct.setPeTchCourse(course);
			ct.setPeTeacher(teacher);
			try {
				this.getGeneralService().getGeneralDao().setEntityClass(PrTchCourseTeacher.class);
				ct=(PrTchCourseTeacher) this.getGeneralService().save(ct);
			} catch (EntityException e) {
				e.printStackTrace();
				if(ct==null){
					flag=false;
				}
			}
		}
		if(flag){
			map.put("success", "true");
			map.put("info", "添加成功");
		}
		this.setJsonString(JsonUtil.toJSONString(map));
		return this.json();
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PeTeacher.class;

	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/teaching/prTchCourseTeacherOutAction";

	}
	
	public void setBean(PeTeacher instance) {
		super.superSetBean(instance);
		
	}
	
	public PeTeacher getBean(){
		return (PeTeacher) super.superGetBean();
	}

	@Override
	public DetachedCriteria initDetachedCriteria() {
		this.getGeneralService().getGeneralDao().setEntityClass(PeTeacher.class);
		DetachedCriteria dc = DetachedCriteria.forClass(PeTeacher.class);
		dc.createCriteria("enumConstByGender", "enumConstByGender");
		dc.createCriteria("enumConstByFlagIsvalid", "enumConstByFlagIsvalid").add(Restrictions.eq("name", "是"));
		
		List<String> list = this.getTchIds();
		if(list != null && list.size() > 0) {
			dc.add(Restrictions.not(Restrictions.in("id", this.getTchIds())));
		}
		
		return dc;
		
	}
	
	/**
	 * 获取已经分配给本课程的所有教师id
	 * 
	 * @return
	 */
	private List<String> getTchIds() {
		
		List<String> ids = new ArrayList<String>();
		List<PrTchCourseTeacher> ctl = null;
		
		DetachedCriteria dc = DetachedCriteria.forClass(PrTchCourseTeacher.class);
		dc.createCriteria("peTchCourse", "peTchCourse").add(Restrictions.eq("id", this.getCourseId()));
		dc.createCriteria("peTeacher", "peTeacher");
		this.getGeneralService().getGeneralDao().setEntityClass(PrTchCourseTeacher.class);
		try {
			ctl = this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
		for(PrTchCourseTeacher ptc : ctl) {
			ids.add(ptc.getPeTeacher().getId());
		}
		
		this.getGeneralService().getGeneralDao().setEntityClass(PeTeacher.class);
		return ids;
		
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	
	

}
