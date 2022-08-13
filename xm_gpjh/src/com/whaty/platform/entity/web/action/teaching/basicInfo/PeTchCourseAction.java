package com.whaty.platform.entity.web.action.teaching.basicInfo;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeTchCourse;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;

/**
 * 课程管理
 * @author 赵玉晓
 *
 */
public class PeTchCourseAction extends MyBaseAction<PeTchCourse> {

	@Override
	public void initGrid() {
		this.getGridConfig().setTitle(this.getText("课程管理"));
		this.getGridConfig().setCapability(true, true, true, true, false);
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
//		this.getGridConfig().addColumn(this.getText("ID2"), "peTchCourse.id", false);
//		this.getGridConfig().addColumn(this.getText("dataSourse"), "jswDataSource", false);
		this.getGridConfig().addColumn(this.getText("课程名称"), "name");
		this.getGridConfig().addColumn(this.getText("课程编码"),"code");
		this.getGridConfig().addColumn(this.getText("学分"),"credit",false,true,true,"regex:new RegExp(/^\\d+$/),regexText:'输入格式：数字',");
		this.getGridConfig().addColumn(this.getText("是否有效"),"enumConstByFlagIsvalid.name");
		this.getGridConfig().addColumn(this.getText("备注"), "note",false,true,true,"TextField",true,225);
		this.getGridConfig().addRenderFunction(this.getText("已分配教师"), "<a href=# onclick=window.open('/entity/teaching/prTchCourseTeacherInAction.action?bean.peTchCourse.id=${value}','','left=200,top=100,resizable=yes,scrollbars=no,height=540,width=1000,location=no')>"+this.getText("查看")+"</a>", "id");
		this.getGridConfig().addRenderFunction(this.getText("分配教师"), "<a href=# onclick=window.open('/entity/teaching/prTchCourseTeacherOutAction.action?courseId=${value}','','left=200,top=100,resizable=yes,scrollbars=no,height=540,width=1000,location=no')>"+this.getText("分配")+"</a>", "id");
		this.getGridConfig().addRenderFunction(this.getText("课程资源管理"), "<a href='/entity/studyZone/courseResources_manage.action?course_id=${value}'  target='_blank'>管理资源</a>","id");
//		this.getGridConfig().addRenderFunction(this.getText("课程资源管理"), "<a href='/entity/studyZone/courseResources_manage.action?course_id=\"+record.data['peTchCourse.id']+\"&dataSourse=\"+record.data['dataSourse'] + \"'  target='_blank'>管理资源</a>");
		
	}
	

	@Override
	public void checkBeforeAdd() throws EntityException {
		String name =this.getBean().getName();
		String code=this.getBean().getCode();
		String sql1="select id from pe_tch_course where name=:name";
		String sql2="select id from pe_tch_course where code=:code";
		
		Map map1=new HashMap();
		map1.put("name", name);
		List list1=new LinkedList();
		list1=this.getGeneralService().getBySQL(sql1, map1);
		if(list1!=null&&list1.size()>0){
			throw new EntityException("此课程名已经存在，不能重复添加");
		}
		
		Map map2=new HashMap();
		map2.put("code",code);
		List list2=new LinkedList();
		list2=this.getGeneralService().getBySQL(sql2, map2);
		if(list2!=null&&list2.size()>0){
			throw new EntityException("此课程编号已经存在，请更改为其他编号");
		}
	}


	@Override
	public void checkBeforeDelete(List idList) throws EntityException {
		String sql_name="select name from pe_tch_course where id=:ids";
		String courseName="";
		for(int i=0;i<idList.size();i++){
			String id=(String) idList.get(i);
			Map map=new HashMap();
			map.put("ids", id);
			
			String sql1="select id from pe_tch_courseware where fk_course_id=:ids";
			List list1=new LinkedList();
			list1=this.getGeneralService().getBySQL(sql1, map);
			if(list1!=null&&list1.size()>0){
				List list_name=this.getGeneralService().getBySQL(sql_name, map);
				if(list_name!=null&&list_name.size()>0){
					courseName=(String) list_name.get(0);
				}
				throw new EntityException(courseName+" 下存在课件，不能删除");
			}
			
			String sql2="select id from pr_tch_trainee_course  where fk_course_id=:ids";
			List list2=new LinkedList();
			list2=this.getGeneralService().getBySQL(sql2, map);
			if(list1!=null&&list1.size()>0){
				List list_name=this.getGeneralService().getBySQL(sql_name, map);
				if(list_name!=null&&list_name.size()>0){
					courseName=(String) list_name.get(0);
				}
				throw new EntityException(courseName+" 已经有学员学习过，不能删除");
			}

			String sql3="select id,fk_traing_plan_id from pr_traing_course  where fk_course_id=:ids";
			List list3=new LinkedList();
			list3=this.getGeneralService().getBySQL(sql3, map);
			if(list1!=null&&list1.size()>0){
				List list_name=this.getGeneralService().getBySQL(sql_name, map);
				if(list_name!=null&&list_name.size()>0){
					courseName=(String) list_name.get(0);
				}
				
				String sql_plan="select name from pe_training_plan where id=:ids";
				Map map_plan=new HashMap();
				map_plan.put("ids", id);
				List list_plan=new LinkedList();
				list_plan=this.getGeneralService().getBySQL(sql_plan, map_plan);
				String planName="";
				if(list_plan!=null&&list_plan.size()>0){
					planName=(String) list_plan.get(0);
				}
				throw new EntityException(courseName+" 已经分配给培训计划 "+planName+"，不能删除");
			}
		}
	}


	@Override
	public void checkBeforeUpdate() throws EntityException {
		String id=this.getBean().getId();
		String name =this.getBean().getName();
		String code=this.getBean().getCode();
		String sql1="select id from pe_tch_course where id!=:ids and name=:name";
		String sql2="select id from pe_tch_course where id!=:ids and code=:code";
		
		Map map1=new HashMap();
		map1.put("name", name);
		map1.put("ids",id);
		List list1=new LinkedList();
		list1=this.getGeneralService().getBySQL(sql1, map1);
		if(list1!=null&&list1.size()>0){
			throw new EntityException("此课程名已经存在，请更改为其它名称");
		}
		
		Map map2=new HashMap();
		map2.put("code",code);
		map2.put("ids",id);
		List list2=new LinkedList();
		list2=this.getGeneralService().getBySQL(sql2, map2);
		if(list2!=null&&list2.size()>0){
			throw new EntityException("此课程编号已经存在，请更改为其他编号");
		}
	}


	public String withInCourse(){
		return "";
	}
	public String withOutCourse(){
		return "";
	}
	@Override
	public void setEntityClass() {
		this.entityClass = PeTchCourse.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/teaching/peTchCourseAction";
	}
	public void setBean(PeTchCourse instance) {
		super.superSetBean(instance);
	}

	public PeTchCourse getBean() {
		return super.superGetBean();
	}

	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeTchCourse.class);
		dc.createCriteria("enumConstByFlagIsvalid", "enumConstByFlagIsvalid");
//		dc.createCriteria("enumConstByCourseModule","enumConstByCourseModule",DetachedCriteria.LEFT_JOIN);
		dc.add(Restrictions.ne("name", "留言板"));
//		dc.createCriteria("peTchBook", "peTchBook");
		return dc;
	}
}
