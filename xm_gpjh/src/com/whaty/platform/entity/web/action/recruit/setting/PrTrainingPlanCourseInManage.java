package com.whaty.platform.entity.web.action.recruit.setting;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeTchCourse;
import com.whaty.platform.entity.bean.PeTrainingPlan;
import com.whaty.platform.entity.bean.PrRecPlanMajorEdutype;
import com.whaty.platform.entity.bean.PrRecPlanMajorSite;
import com.whaty.platform.entity.bean.PrTraingCourse;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.util.JsonUtil;

public class PrTrainingPlanCourseInManage extends MyBaseAction<PrTraingCourse> {

//	private String peTrainingPlanId;
	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PrTraingCourse.class);
//		dc.createCriteria("peTrainingPlan","peTrainingPlan");
		dc.createCriteria("peTchCourse","peTchCourse");
		dc.createCriteria("peTeacher","peTeacher",DetachedCriteria.LEFT_JOIN);
//		dc.add(Restrictions.eq("peTrainingPlan.id", this.getPeTrainingPlanId()));
		return dc;
	}
	
	public void setBean(PrTraingCourse instance) {
		super.superSetBean(instance);
		
	}
	
	public PrTraingCourse getBean(){
		return  super.superGetBean();
	}

	@Override
	public void initGrid() {
		String planName=this.getPlanName();
		
		this.getGridConfig().setTitle(this.getText(planName+"培训计划课程管理"));
        this.getGridConfig().setCapability(false,false,true,true);
        this.getGridConfig().addColumn("","id",false);
        this.getGridConfig().addColumn(this.getText("课程名称"), "peTchCourse.name",true,false,true,"");
        this.getGridConfig().addColumn(this.getText("课程编号"), "peTchCourse.code");
        this.getGridConfig().addColumn(this.getText("学分"), "peTchCourse.credit");
        this.getGridConfig().addColumn(this.getText("答疑教师"),"peTeacher.name");
        this.getGridConfig().addMenuFunction(this.getText("删除选中课程"), "/entity/recruit/prTrainingPlanCourseIn_delCourse.action", false, true);
        

        
        
//		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
//		this.getGridConfig().addColumn(this.getText("名称"),"name");
//		this.getGridConfig().addColumn(this.getText("培训类型"),"enumConstByTrainingType.name");
//		this.getGridConfig().addColumn(this.getText("学时要求"),"creditRequire");
//		this.getGridConfig().addColumn(this.getText("培训最长时间（天）"),"daysLimit",true,true,true,"TextField",true,25);
//		this.getGridConfig().addColumn(this.getText("版本号"),"enumConstByVersion.name");
//		this.getGridConfig().addColumn(this.getText("生效时间"),"activeDate");
//		this.getGridConfig().addColumn(this.getText("备注"),"note",true,true,true,"TextField",true,25);
//		this.getGridConfig().addRenderFunction(this.getText("查看已有课程"), "<a href=# onclick=window.open('/peUser/peUserInfoPut_getTrainer.action?userId=${value}','','left=500,top=300,resizable=yes,scrollbars=no,height=270,width=500,location=no')>"+this.getText("查看")+"</a>", "id");
//		this.getGridConfig().addRenderFunction(this.getText("添加其它课程"), "<a href=# onclick=window.open('/peUser/peUserInfoPut_getTrainer.action?userId=${value}','','left=500,top=300,resizable=yes,scrollbars=no,height=270,width=500,location=no')>"+this.getText("查看")+"</a>", "id");
		
		
//		this.getGridConfig().addColumn(this.getText("站点名称"), "peSite.name");
//		this.getGridConfig().addColumn(this.getText("专业"),"prRecPlanMajorEdutype.peMajor.name",false,false,true,"");
//		this.getGridConfig().addColumn(this.getText("层次"),"prRecPlanMajorEdutype.peEdutype.name",false,false,true,"");
	}

	private String getPlanName() {
		String planName="";
		if(this.getBean()!=null&&this.getBean().getPeTrainingPlan()!=null&&this.getBean().getPeTrainingPlan().getId()!=null){
			String sql="select name from pe_training_plan p where p.id='"+this.getBean().getPeTrainingPlan().getId()+"'";
			List list=null;
			try {
				list=this.getGeneralService().getBySQL(sql);
			} catch (EntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(list!=null&&list.size()>0){
				planName=(String)list.get(0);
			}
		}
		
		return planName;
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PrTraingCourse.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/recruit/prTrainingPlanCourseIn";
	}
	
	public String delCourse(){
		Map map=new HashMap();
		map.put("success", "false");
		map.put("info", "删除失败");
		String[] courses=this.getIds().split(",");
		int succCount=0;
		int failCount=0;
		for(int i=0;i<courses.length;i++){
			String sql="delete from pr_traing_course tc where tc.id='"+courses[i]+"'";
			int flag=0;
			try {
				flag = this.getGeneralService().executeBySQL(sql);
			} catch (EntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("删除培训计划已有课程失败,此课程id为"+courses[i]);
			}
			if(flag>0){
				succCount++;
			}else{
				failCount++;
			}
		}
		if(failCount>0){
			map.put("success", "false");
			map.put("info", succCount+"个课程删除成功，"+failCount+"个删除失败");
		}else{
			map.put("success", "false");
			map.put("info", succCount+"个课程删除成功");
		}
		this.setJsonString(JsonUtil.toJSONString(map));
		return this.json();
	}


//	public String getPeTrainingPlanId() {
//		return peTrainingPlanId;
//	}
//
//	public void setPeTrainingPlanId(String peTrainingPlanId) {
//		this.peTrainingPlanId = peTrainingPlanId;
//	}

}
