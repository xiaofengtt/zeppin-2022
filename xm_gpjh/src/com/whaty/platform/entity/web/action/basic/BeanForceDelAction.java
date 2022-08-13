package com.whaty.platform.entity.web.action.basic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PePriRole;
import com.whaty.platform.entity.bean.PeTrainee;
import com.whaty.platform.entity.bean.PeTrainingPlan;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.util.Const;
import com.whaty.platform.util.JsonUtil;
import com.whaty.util.string.AttributeManage;
import com.whaty.util.string.WhatyAttributeManage;

public class BeanForceDelAction extends MyBaseAction {

	/**
	 * 课程
	 * 强制删除外键关联
	 * @return
	 */
	public String forseDelCourse(){
		String ids[]=this.getIds().split(",");
		boolean flag=true;
		String msg="";
		for(int i=0;i<ids.length;i++){
			String id=ids[i];
			String sql_1="delete from pr_tch_course_teacher where fk_course_id=:ids";
//			String sql_2="delete from pr_trainee_course where fk_course_id=:ids";
//			String sql_3="delete from pr_trainee_course_score where fk_course_id=:ids";
			String sql_4="delete from pr_traing_course where fk_course_id=:ids";
			String sql_5="delete from PR_TCH_TRAINEE_COURSE where fk_course_id=:ids";
			String sql_6="delete from pe_questions where fk_course_id=:ids";
			
			Map map1=new HashMap();
			map1.put("ids", id);
			
			int c=0;
			try {
				c=this.getGeneralService().executeBySQL(sql_1, map1);
				if(c>0){
					msg+="从pr_tch_course_teacher中删除掉"+c+"条数据";
				}
				
//				c=this.getGeneralService().executeBySQL(sql_2, map1);
//				if(c>0){
//					msg+="从pr_trainee_course中删除掉"+c+"条数据";
//				}
				
//				c=this.getGeneralService().executeBySQL(sql_3, map1);
//				if(c>0){
//					msg+="从pr_trainee_course_score中删除掉"+c+"条数据";
//				}
				
				c=this.getGeneralService().executeBySQL(sql_4, map1);
				if(c>0){
					msg+="从pr_traing_course中删除掉"+c+"条数据";
				}
				
				c=this.getGeneralService().executeBySQL(sql_5, map1);
				if(c>0){
					msg+="从pr_tch_trainee_course中删除掉"+c+"条数据";
				}
				
				c=this.getGeneralService().executeBySQL(sql_6, map1);
				if(c>0){
					msg+="从pr_tch_trainee_course中删除掉"+c+"条数据";
				}
			} catch (EntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Map map=new HashMap();
		map.put("info", msg);
		if(flag){
			map.put("success", "true");
		}else{
			map.put("success", "false");
		}
		this.setJsonString(JsonUtil.toJSONString(map));
		return this.json();
	}
	

	/**
	 * 课件
	 * 强制删除外键关联
	 * @return
	 */
	public String forseDelCourseware(){
		String ids[]=this.getIds().split(",");
		boolean flag=true;
		String msg="";
		for(int i=0;i<ids.length;i++){
			String id=ids[i];
			String sql_1="delete from pr_trainee_courseware where fk_courseware_id=:ids";
			String sql_2="delete from TRAINING_COURSE_STUDENT where course_id=:ids";
			
			Map map1=new HashMap();
			map1.put("ids", id);
			
			int c=0;
			try {
				c=this.getGeneralService().executeBySQL(sql_1, map1);
				if(c>0){
					msg+="从pr_trainee_courseware中删除掉"+c+"条数据";
				}
				
				c=this.getGeneralService().executeBySQL(sql_2, map1);
				if(c>0){
					msg+="从TRAINING_COURSE_STUDENT中删除掉"+c+"条数据";
				}
				
			} catch (EntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Map map=new HashMap();
		map.put("info", msg);
		if(flag){
			map.put("success", "true");
		}else{
			map.put("success", "false");
		}
		this.setJsonString(JsonUtil.toJSONString(map));
		return this.json();
	}

	/**
	 * 学员
	 * 强制删除外键关联
	 * @return
	 */
	public String forseDelTrainee(){
		String ids[]=this.getIds().split(",");
		boolean flag=true;
		String msg="";
		for(int i=0;i<ids.length;i++){
			String id=ids[i];
			String sql_1="delete from pe_fee_detail where fk_trainee_id=:ids";
			String sql_2="delete from pr_tch_trainee_course where fk_trainee_id=:ids";
			String sql_30="delete from pe_answers where fk_ques_id in(select id from pe_questions where fk_trainee_id=:ids)";
			String sql_3="delete from pe_questions where fk_trainee_id=:ids";
//			String sql_4="delete from pr_trainee_course where fk_trainee_id=:ids";
			String sql_5="delete from system_apply where fk_trainee_id=:ids";
			String sql_6="delete from pr_trainee_courseware where fk_trainee_id=:ids";
			String sql_7="delete from pr_trainee_course_score where fk_trainee_id=:ids";
			String sql_8="delete from pr_trainee_teacher_score where fk_trainee_id=:ids";

			
			Map map1=new HashMap();
			map1.put("ids", id);
			
			int c=0;
			try {
				c=this.getGeneralService().executeBySQL(sql_1, map1);
				if(c>0){
					msg+="从pe_fee_detail中删除"+c+"条数据，";
				}

				c=this.getGeneralService().executeBySQL(sql_2, map1);
				if(c>0){
					msg+="从pr_tch_trainee_course中删除"+c+"条数据，";
				}

				c=this.getGeneralService().executeBySQL(sql_30, map1);
				if(c>0){
					msg+="从pe_answers中删除"+c+"条数据，";
				}

				c=this.getGeneralService().executeBySQL(sql_3, map1);
				if(c>0){
					msg+="从pe_questions中删除"+c+"条数据，";
				}

//				c=this.getGeneralService().executeBySQL(sql_4, map1);
//				if(c>0){
//					msg+="从pr_trainee_course中删除"+c+"条数据，";
//				}

				c=this.getGeneralService().executeBySQL(sql_5, map1);
				if(c>0){
					msg+="从system_apply中删除"+c+"条数据，";
				}

				c=this.getGeneralService().executeBySQL(sql_6, map1);
				if(c>0){
					msg+="从pr_trainee_courseware中删除"+c+"条数据，";
				}
				
				c=this.getGeneralService().executeBySQL(sql_7, map1);
				if(c>0){
					msg+="从pr_trainee_course_score中删除"+c+"条数据";
				}

				c=this.getGeneralService().executeBySQL(sql_8, map1);
				if(c>0){
					msg+="从pr_trainee_teacher_score中删除"+c+"条数据";
				}
				
				//删除sso_user的外键关联
				String sql_sso="select fk_sso_user_id from pe_trainee where id=:ids";
				List list_sso=this.getGeneralService().getBySQL(sql_sso,map1);
				String ssoId="";
				if(list_sso!=null&&list_sso.size()>0){
					ssoId=(String) list_sso.get(0);
				}
				
				String sso1="delete from training_course_student where student_id=:ids";
				

				c=this.getGeneralService().executeBySQL(sso1, map1);
				if(c>0){
					msg+="从中training_course_student删除"+c+"条数据，";
				}
				
			} catch (EntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Map map=new HashMap();
		map.put("info", msg);
		if(flag){
			map.put("success", "true");
		}else{
			map.put("success", "false");
		}
		this.setJsonString(JsonUtil.toJSONString(map));
		return this.json();
	}

	/**
	 * 教师
	 * 强制删除外键关联
	 * @return
	 */
	public String forseDelTeacher(){
		String ids[]=this.getIds().split(",");
		boolean flag=true;
		String msg="";
		for(int i=0;i<ids.length;i++){
			String id=ids[i];
			String sql_1="delete from pr_tch_course_teacher where fk_teacher_id=:ids";
			String sql_2="delete from pr_traing_course where fk_ans_teacher_id=:ids";
			String sql_3="delete from pr_trainee_teacher_score where fk_teacher_id=:ids";
			
			Map map1=new HashMap();
			map1.put("ids", id);
			
			int c=0;
			try {
				c=this.getGeneralService().executeBySQL(sql_1, map1);
				if(c>0){
					msg+="从中pr_tch_course_teachers删除"+c+"条数据，";
				}
				
				c=this.getGeneralService().executeBySQL(sql_2, map1);
				if(c>0){
					msg+="从中pr_traing_course删除"+c+"条数据，";
				}
				
				c=this.getGeneralService().executeBySQL(sql_3, map1);
				if(c>0){
					msg+="从pr_trainee_teacher_score中删除"+c+"条数据，";
				}
				//删除sso_user的外键关联
				String sql_sso="select fk_sso_user_id from pe_teacher where id=:ids";
				List list_sso=this.getGeneralService().getBySQL(sql_sso,map1);
				String ssoId="";
				if(list_sso!=null&&list_sso.size()>0){
					ssoId=(String) list_sso.get(0);
				}
				
				String sso1="delete from pe_answers where fk_sso_user=:ids";
				

				c=this.getGeneralService().executeBySQL(sso1, map1);
				if(c>0){
					msg+="从中pe_answers删除"+c+"条数据，";
				}

				
			} catch (EntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Map map=new HashMap();
		map.put("info", msg);
		if(flag){
			map.put("success", "true");
		}else{
			map.put("success", "false");
		}
		this.setJsonString(JsonUtil.toJSONString(map));
		return this.json();
	}
	/**
	 * 培训计划
	 * 强制删除外键关联
	 * @return
	 */
	public String forseDelPlan(){
		String ids[]=this.getIds().split(",");
		boolean flag=true;
		String msg="";
		for(int i=0;i<ids.length;i++){
			String id=ids[i];
			String sql_1="delete from pr_traing_course where fk_traing_plan_id=:ids";
			
			Map map1=new HashMap();
			map1.put("ids", id);
			
			int c=0;
			try {
				c=this.getGeneralService().executeBySQL(sql_1, map1);
				if(c>0){
					msg+="从中pr_traing_course删除"+c+"条数据，";
				}
				
			} catch (EntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Map map=new HashMap();
		map.put("info", msg);
		if(flag){
			map.put("success", "true");
		}else{
			map.put("success", "false");
		}
		this.setJsonString(JsonUtil.toJSONString(map));
		return this.json();
	}

	
	@Override
	public void initGrid() {
	}
	
	@Override
	public void setEntityClass() {
		this.entityClass = PeTrainee.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/recruit/beanForceDelAction";
	}

	public PeTrainee getBean() {
		return (PeTrainee)super.superGetBean();
	}

	public void setBean(PeTrainee bean) {
		super.superSetBean(bean);
	}


	@Override
	public DetachedCriteria initDetachedCriteria() {
		return null;
	}
	
	

}
