package com.whaty.platform.entity.web.action.recruit.setting;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeTrainingPlan;
import com.whaty.platform.entity.bean.PrRecPlanMajorEdutype;
import com.whaty.platform.entity.bean.PrRecPlanMajorSite;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.util.Const;
import com.whaty.platform.util.JsonUtil;
import com.whaty.platform.entity.bean.PrTraingCourse;

public class PeTrainingPlanManage extends MyBaseAction {

	private String trainingPlanId;
	@Override
	public DetachedCriteria initDetachedCriteria() {
		this.getGeneralService().getGeneralDao().setEntityClass(PeTrainingPlan.class);
		DetachedCriteria dc = DetachedCriteria.forClass(PeTrainingPlan.class);
		dc.createCriteria("enumConstByTrainingType","enumConstByTrainingType");
		dc.createCriteria("enumConstByVersion","enumConstByVersion");
		return dc;
	}
	
	public void setBean(PeTrainingPlan instance) {
		super.superSetBean(instance);
		
	}
	
	public PeTrainingPlan getBean(){
		return  (PeTrainingPlan)super.superGetBean();
	}

	@Override
	public void initGrid() {
		this.getGridConfig().setTitle(this.getText("培训计划管理"));
        this.getGridConfig().setCapability(true, true, true,true);
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("名称"),"name");
		this.getGridConfig().addColumn(this.getText("培训类型"),"enumConstByTrainingType.name");
		this.getGridConfig().addColumn(this.getText("学时要求"),"creditRequire",true, true, true, Const.scoreLine_for_extjs);
//		this.getGridConfig().addColumn(this.getText("培训最长时间（天）"),"daysLimit",true,true,true,"TextField",true,3);
		this.getGridConfig().addColumn(this.getText("培训最长时间（天）"), "daysLimit", true, true, true, Const.scoreLine_for_extjs);
		//this.getGridConfig().addColumn(this.getText("版本号"),"enumConstByVersion.name");
		ColumnConfig cc=new ColumnConfig(this.getText("版本号"),"enumConstByVersion.name");
		cc.setComboSQL("select id,name from enum_const where namespace='Version' order by code");
		cc.setSearch(true);
		cc.setList(true);
		this.getGridConfig().addColumn(cc);
		this.getGridConfig().addColumn(this.getText("生效时间"),"activeDate");
		this.getGridConfig().addColumn(this.getText("备注"),"note",true,true,true,"TextField",true,225);
		this.getGridConfig().addRenderFunction(this.getText("查看已有课程"), "<a href=# onclick=window.open('/entity/recruit/prTrainingPlanCourseIn.action?bean.peTrainingPlan.id=${value}','','left=200,top=100,resizable=yes,scrollbars=no,height=540,width=1000,location=no')>"+this.getText("查看")+"</a>", "id");
		this.getGridConfig().addRenderFunction(this.getText("添加其它课程"), "<a href=# onclick=window.open('/entity/recruit/prTrainingPlanCourseOut.action?peTrainingPlanId=${value}','','left=200,top=100,resizable=yes,scrollbars=no,height=540,width=1000,location=no')>"+this.getText("查看")+"</a>", "id");
		StringBuilder script = new StringBuilder();
		script.append(" {");
		script.append(" 	 var m = grid.getSelections();");
		script.append("    	 var ids = '';");
		script.append("    	 if(m.length == 1){");
		script.append("    	 		for(var i=0,len =m.length;i<len;i++ ){");
		script.append("    	 			var ss = m[i].get('id');");
		script.append("    	 			if(i==0)ids = ids+ ss ;");
		script.append("    	 			else ids = ids +','+ss;");
		script.append("    	 		}");
		script.append("    	 }else{");
		script.append("    	 	Ext.MessageBox.alert('错误','只能选择一个培训计划进行复制'); return;");
		script.append("    	 }");
		script.append("        var planname = new Ext.form.TextField({fieldLabel: '新培训计划名称*',name: 'bean.name',maxLength:200,allowBlank:false,anchor: '90%',regex:new RegExp(/^([^\\s\\'\\\"\\&]|[^\\s\\'\\\"\\&][^\\'\\\"\\&]*[^\\s\\\'\\\"\\&])$/),regexText:'输入格式：不能以空格开头和结尾、不能包含单引号、双引号及&符号'});\n ");
		script.append("        var fids = new Ext.form.Hidden({name:'bean.id',value:ids});");
		script.append("    	 var roomformPanel = new Ext.form.FormPanel({");
		script.append(" 	    frame:true,");
		script.append("         labelWidth: 100,");
		script.append("        	defaultType: 'textfield',");
		script.append(" 				autoScroll:true,reader: new Ext.data.JsonReader({root: 'models'},['bean.id','bean.name']),");
		script.append("         items: [ planname ,fids]");
		script.append("       });  ");
//		script.append("        roomformPanel.form.load({url:'"+this.getDetailAction()+"?bean.id='+ids,waitMsg:'Loading'});  ");
		script.append("        var saveroomModelWin = new Ext.Window({");
		script.append("        title: '复制培训计划',");
		script.append("        width: 450,");
		script.append("        height: 225,");
		script.append("        minWidth: 300,");
		script.append("        minHeight: 250,");
		script.append("        layout: 'fit',");
		script.append("        plain:true,");
		script.append("        bodyStyle:'padding:5px;',");
		script.append("        buttonAlign:'center',");
		script.append("        items: roomformPanel,buttons: [{");
		script.append(" 	            text: '保存',");
		script.append(" 	            handler: function() {");
		script.append(" 	                if (roomformPanel.form.isValid()) {");
		script.append(" 		 		        roomformPanel.form.submit({");
		script.append(" 		 		        	url:'/entity/recruit/peTrainingPlanManage_copyPlan.action?' ,");
		script.append(" 				            waitMsg:'处理中，请稍候...',");
		script.append(" 							success: function(form, action) {");
		script.append(" 							    var responseArray = action.result;");
		script.append(" 							    if(responseArray.success=='true'){");
		script.append(" 							    	Ext.MessageBox.alert('提示', responseArray.info);");
		script.append(" 							    	store.load({params:{start:g_start,limit:g_limit"+this.getGridConfig().getFieldsFilter()+"}});                      ");//+this.getGridConfig().getFieldsFilter()+"
		script.append(" 								    saveroomModelWin.close();");
		script.append(" 							    } else {");
		script.append(" 							    	Ext.MessageBox.alert('错误', responseArray.info );");
		script.append(" 							    }");
		script.append(" 							}");
		script.append(" 				        });");
		script.append(" 	                } else{");
		script.append(" 						Ext.MessageBox.alert('错误', '输入错误，请先修正提示的错误');");
		script.append(" 					}");
		script.append(" 		        }");
		script.append(" 	        },{");
		script.append(" 	            text: '取消',");
		script.append(" 	            handler: function(){saveroomModelWin.close();}");
		script.append(" 	        }]");
		script.append("        });");
		script.append("        saveroomModelWin.show();");
		script.append("  }");

		this.getGridConfig().addMenuScript(this.getText("复制培训计划"), script.toString());
		this.getGridConfig().setPrepared(false);
		
//		this.getGridConfig().addColumn(this.getText("站点名称"), "peSite.name");
//		this.getGridConfig().addColumn(this.getText("专业"),"prRecPlanMajorEdutype.peMajor.name",false,false,true,"");
//		this.getGridConfig().addColumn(this.getText("层次"),"prRecPlanMajorEdutype.peEdutype.name",false,false,true,"");
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PeTrainingPlan.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/recruit/peTrainingPlanManage";
	}
	//返回所有该培训计划下的所有课程名称和ID
	public String getCoursesWithin(){
		List list=null;
		
		try {
			list=this.getGeneralService().getBySQL(" select c.id,c.name from pr_traing_course s,PE_TCH_COURSE c where s.fk_course_id=c.id(+) and s.fk_course_id='"+this.getTrainingPlanId()+"'");
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "";
	}
	//复制培训计划
	public String copyPlan(){
		Map map=new HashMap();
		map.put("success", "false");
		map.put("info", "复制失败");
		//复制培训计划信息
		PeTrainingPlan plan0=null;
		PeTrainingPlan plan=new PeTrainingPlan();
		try {
			this.getGeneralService().getGeneralDao().setEntityClass(PeTrainingPlan.class);
			plan0 = (PeTrainingPlan)this.getGeneralService().getById(this.getBean().getId());
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		PeTrainingPlan plan=new PeTrainingPlan();
		
		plan.setEnumConstByTrainingType(plan0.getEnumConstByTrainingType());
		plan.setEnumConstByVersion(plan0.getEnumConstByVersion());
		plan.setName(this.getBean().getName());
		plan.setDaysLimit(plan0.getDaysLimit());
		plan.setCreditRequire(plan0.getCreditRequire());
		plan.setActiveDate(plan0.getActiveDate());
		plan.setNote(plan0.getNote());
		
		boolean flag=false;
		try {
			plan=(PeTrainingPlan) this.getGeneralService().save(plan);
			flag=true;
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			List list=null;
			String sql_name="select id from pe_training_plan where name ='"+plan.getName()+"'";
			try {
				list=this.getGeneralService().getBySQL(sql_name);
			} catch (EntityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(list!=null&&list.size()>0){
				map.put("success", "false");
				map.put("info", "此名字已存在");
			}
			e.printStackTrace();
		}
		
		//复制培训计划的课程
		if(flag){
			List oldList=new LinkedList();
			DetachedCriteria dc=DetachedCriteria.forClass(PrTraingCourse.class);
			dc.createCriteria("peTrainingPlan","peTrainingPlan");
			dc.createCriteria("peTeacher","peTeacher",DetachedCriteria.LEFT_JOIN);
			dc.createCriteria("peTchCourse","peTchCourse");
			dc.add(Restrictions.eq("peTrainingPlan.id", plan0.getId()));
			try {
				this.getGeneralService().getGeneralDao().setEntityClass(PrTraingCourse.class);
				oldList=this.getGeneralService().getList(dc);
			} catch (EntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for(int i=0;i<oldList.size();i++){
				PrTraingCourse oldTrainCours=(PrTraingCourse) oldList.get(i);
				PrTraingCourse trCour=this.cloneCourse(oldTrainCours, plan.getId());
				this.getGeneralService().getGeneralDao().setEntityClass(PeTrainingPlan.class);
				try {
					this.getGeneralService().save(trCour);
				} catch (EntityException e) {
					// TODO Auto-generated catch block
//					e.printStackTrace();
					System.out.println("保存新培训计划的课程时发生错误，旧培训计划关系表中ID为"+oldTrainCours.getId());
					flag=false;
				}
				
			}
		}
		if(flag){
			map.put("success", "true");
			map.put("info", "复制成功");
		}
		this.setJsonString(JsonUtil.toJSONString(map));
		return this.json();
	}
	//复制该培训计划下的课程将培训计划ID改为新培训计划ID
	private PrTraingCourse cloneCourse(PrTraingCourse traiCour,String planId){
		if(traiCour==null){
			return null;
		}
		PrTraingCourse trainCourNew=new PrTraingCourse();
		trainCourNew.setPeTchCourse(traiCour.getPeTchCourse());
		trainCourNew.setPeTeacher(traiCour.getPeTeacher());
		this.getGeneralService().getGeneralDao().setEntityClass(PeTrainingPlan.class);
		DetachedCriteria dc=DetachedCriteria.forClass(PeTrainingPlan.class);
		dc.add(Restrictions.eq("id", planId));
		List planList=new LinkedList();
		try {
			planList=this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			System.out.println("复制培训计划的课程中 查询培训计划 出错了");
		}
		PeTrainingPlan plan=new PeTrainingPlan();
		if(planList!=null&&planList.size()>0){
			plan=(PeTrainingPlan) planList.get(0);
		}
		trainCourNew.setPeTrainingPlan(plan);
		return trainCourNew;
	}
	

	@Override
	public void checkBeforeDelete(List idList) throws EntityException {
		// TODO Auto-generated method stub
		for(int i=0;i<idList.size();i++){
			this.getGeneralService().getGeneralDao().setEntityClass(PrTraingCourse.class);
			DetachedCriteria dc=DetachedCriteria.forClass(PrTraingCourse.class);
			dc.createCriteria("peTrainingPlan","peTrainingPlan");
			dc.add(Restrictions.eq("peTrainingPlan.id", (String)idList.get(i)));
			List testCount=null;
			testCount=this.getGeneralService().getList(dc);
			if(testCount!=null&&testCount.size()>0){
				PeTrainingPlan plan=new PeTrainingPlan();
				dc=DetachedCriteria.forClass(PeTrainingPlan.class);
				this.getGeneralService().getGeneralDao().setEntityClass(PeTrainingPlan.class);
				plan=(PeTrainingPlan) this.getGeneralService().getById((String)idList.get(i));
				throw new EntityException(plan.getName()+"已经分配了课程，请先删掉所有课程");
			}
			
			
		}
		
		this.getGeneralService().getGeneralDao().setEntityClass(PeTrainingPlan.class);
	}

	public String getTrainingPlanId() {
		return trainingPlanId;
	}

	public void setTrainingPlanId(String trainingPlanId) {
		this.trainingPlanId = trainingPlanId;
	}
}
