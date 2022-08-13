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
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.util.JsonUtil;

public class PrTrainingPlanCourseOutManage extends MyBaseAction {

	private String peTrainingPlanId;
	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeTchCourse.class);
//		dc.createCriteria("peTrainingPlan","peTrainingPlan");
//		dc.createCriteria("peTchCourse","peTchCourse");
//		dc.add(Restrictions.ne("peTrainingPlan.id", peTrainingPlanId));
//		dc.createCriteria("peTeacher","peTeacher",DetachedCriteria.LEFT_JOIN);
//		dc.add(Restrictions.eq("peTrainingPlan.id", this.getPeTrainingPlanId()));
		return dc;
	}
	


	@Override
	public Page list() {
		Page page=null;
		StringBuffer strb=new StringBuffer("select c.id,c.name,c.code,c.credit from pe_tch_course c,enum_const ec where c.id not in(select distinct fk_course_id from pr_traing_course where fk_traing_plan_id='"+this.getPeTrainingPlanId()+"') and c.flag_isvalid = ec.id and ec.namespace = 'FlagIsvalid' and ec.code = '1'");
		this.setSqlCondition(strb);
		try {
			page=this.getGeneralService().getByPageSQL(strb.toString(), Integer.parseInt(this.getLimit()), Integer.parseInt(this.getStart()));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			System.out.println("查询不属于培训计划的课程数字转化失败");
		} catch (EntityException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			System.out.println("查询不属于培训计划的课程查询错误");
		}
		// TODO Auto-generated method stub
		return page;
	}



	public void setBean(PrTraingCourse instance) {
		super.superSetBean(instance);
		
	}
	
	public PrTraingCourse getBean(){
		return  (PrTraingCourse) super.superGetBean();
	}

	@Override
	public void initGrid() {
		String planName=this.getPlanName();
		
		this.getGridConfig().setTitle(this.getText("为"+planName+"培训计划添加课程"));
        this.getGridConfig().setCapability(false,false,false,true);
        this.getGridConfig().addColumn("","id",false);
        this.getGridConfig().addColumn(this.getText("课程名称"), "name",true,false,true,"");
        this.getGridConfig().addColumn(this.getText("课程编号"), "code");
        this.getGridConfig().addColumn(this.getText("学分"), "credit");
        this.getGridConfig().addMenuFunction(this.getText("添加选中课程"), "/entity/recruit/prTrainingPlanCourseOut_addCourse.action?peTrainingPlanId="+this.getPeTrainingPlanId(), false, true);
        

        
        
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
		if(this.getPeTrainingPlanId()!=null){
			String sql="select name from pe_training_plan p where p.id='"+this.getPeTrainingPlanId()+"'";
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
		this.servletPath = "/entity/recruit/prTrainingPlanCourseOut";
	}
	
	public String addCourse(){
		Map map=new HashMap();
		map.put("success", "false");
		map.put("info", "添加失败");
		String[] courses=this.getIds().split(",");
		int succCount=0;
		int failCount=0;
		PeTrainingPlan ptp=null;
		try {
			this.getGeneralService().getGeneralDao().setEntityClass(PeTrainingPlan.class);
			ptp = (PeTrainingPlan) this.getGeneralService().getById(this.getPeTrainingPlanId());
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i=0;i<courses.length;i++){
			
			PrTraingCourse ptc=new PrTraingCourse();
			PeTchCourse ptco=new PeTchCourse();
			try {
				this.getGeneralService().getGeneralDao().setEntityClass(PeTchCourse.class);
				ptco=(PeTchCourse) this.getGeneralService().getById(courses[i]);
			} catch (EntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			ptc.setPeTchCourse(ptco);
			ptc.setPeTrainingPlan(ptp);
			
			try {
				this.getGeneralService().getGeneralDao().setEntityClass(PrTraingCourse.class);
				this.getGeneralService().save(ptc);
				succCount++;
			} catch (EntityException e1) {
				// TODO Auto-generated catch block
//				e1.printStackTrace();
				System.out.println("为培训计划添加课程出错，课程号为"+courses[i]);
				failCount++;
			}
			
		}
		if(failCount>0){
			map.put("success", "false");
			map.put("info", succCount+"个课程添加成功，"+failCount+"个添加失败");
		}else{
			map.put("success", "true");
			map.put("info", succCount+"个课程添加成功");
		}
		this.setJsonString(JsonUtil.toJSONString(map));
		return this.json();
	}


	public String getPeTrainingPlanId() {
		return peTrainingPlanId;
	}

	public void setPeTrainingPlanId(String peTrainingPlanId) {
		this.peTrainingPlanId = peTrainingPlanId;
	}

}
