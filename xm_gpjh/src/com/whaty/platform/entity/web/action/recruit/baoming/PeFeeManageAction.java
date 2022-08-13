package com.whaty.platform.entity.web.action.recruit.baoming;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeTchCourseware;
import com.whaty.platform.entity.bean.PeTrainee;
import com.whaty.platform.entity.bean.PeTrainingPlan;
import com.whaty.platform.entity.bean.PrRecPlanMajorEdutype;
import com.whaty.platform.entity.bean.PrRecPlanMajorSite;
import com.whaty.platform.entity.bean.PrTraineeCourseware;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.util.JsonUtil;
import com.whaty.platform.entity.bean.PrTraingCourse;
import com.whaty.platform.entity.bean.PeFeeDetail;
import com.whaty.platform.entity.bean.EnumConst;

public class PeFeeManageAction extends MyBaseAction {


	@Override
	public DetachedCriteria initDetachedCriteria() {
		this.getGeneralService().getGeneralDao().setEntityClass(PeFeeDetail.class);
		DetachedCriteria dc = DetachedCriteria.forClass(PeFeeDetail.class);
		dc.createCriteria("peTrainee","peTrainee")
		.createAlias("enumConstByTrainingType","enumConstByTrainingType",DetachedCriteria.LEFT_JOIN)
		.createAlias("enumConstByStatus","enumConstByStatus",DetachedCriteria.LEFT_JOIN)
		.createAlias("enumConstByGender","enumConstByGender",DetachedCriteria.LEFT_JOIN)
		.createAlias("peTrainingPlan", "peTrainingPlan",DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByFlagInvoiceStatus","enumConstByFlagInvoiceStatus",DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("peManager","peManager",DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByFlagFeeCheck","enumConstByFlagFeeCheck",DetachedCriteria.LEFT_JOIN);
		
		return dc;
	}
	
	public void setBean(PeFeeDetail instance) {
		super.superSetBean(instance);
		
	}
	
	public PeFeeDetail getBean(){
		return  (PeFeeDetail)super.superGetBean();
	}

	@Override
	public void initGrid() {
		this.getGridConfig().setTitle(this.getText("费用管理"));
        this.getGridConfig().setCapability(false, true, true,true,false);
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);

		if(this.getBean()!=null&&this.getBean().getPeTrainee()!=null&&this.getBean().getPeTrainee().getId()!=null){
			this.getGridConfig().addColumn(this.getText("学员名字"),"peTrainee.name",true,false,true,"TextField",true,25);
		}
		this.getGridConfig().addColumn(this.getText("学员ID"),"peTrainee.loginId");//
		this.getGridConfig().addColumn(this.getText("真实姓名"),"peTrainee.trueName");
		this.getGridConfig().addColumn(this.getText("培训级别"),"peTrainee.enumConstByTrainingType.name");
		this.getGridConfig().addColumn(this.getText("学员状态"),"peTrainee.enumConstByStatus.name");
		this.getGridConfig().addColumn(this.getText("性别"),"peTrainee.enumConstByGender.name");
		this.getGridConfig().addColumn(this.getText("证件号码"),"peTrainee.cardNo");
		this.getGridConfig().addColumn(this.getText("培训计划"),"peTrainee.peTrainingPlan.name");
		this.getGridConfig().addColumn(this.getText("通信地址"),"peTrainee.address");
		
		
		this.getGridConfig().addColumn(this.getText("费用状态"),"enumConstByFlagFeeCheck.name",true,false,true,"TextField",false,25);
		this.getGridConfig().addColumn(this.getText("费用额"),"feeAmount",true,true,true,"regex:new RegExp(/^\\d+$/),regexText:'输入格式：数字',");
		this.getGridConfig().addColumn(this.getText("费用导入平台日期"),"inputDate",true,false,true,"Date",false,25);
		this.getGridConfig().addColumn(this.getText("备注"),"note",true,false,true,"");
		this.getGridConfig().addColumn(this.getText("审核人"),"peManager.name",true,false,true,"");
		
//		
//		this.getGridConfig().addColumn(this.getText("培训类型"),"enumConstByTrainingType.name");
//		this.getGridConfig().addColumn(this.getText("学时要求"),"creditRequire");
//		this.getGridConfig().addColumn(this.getText("培训最长时间（天）"),"daysLimit",true,true,true,"TextField",true,25);
//		this.getGridConfig().addColumn(this.getText("版本号"),"enumConstByVersion.name");
//		this.getGridConfig().addColumn(this.getText("生效时间"),"activeDate");
//		this.getGridConfig().addColumn(this.getText("备注"),"note",true,true,true,"TextField",true,25);
//		this.getGridConfig().addRenderFunction(this.getText("查看已有课程"), "<a href=# onclick=window.open('/entity/recruit/prTrainingPlanCourseIn.action?bean.peTrainingPlan.id=${value}','','left=500,top=300,resizable=yes,scrollbars=no,height=270,width=500,location=no')>"+this.getText("查看")+"</a>", "id");
//		this.getGridConfig().addRenderFunction(this.getText("添加其它课程"), "<a href=# onclick=window.open('/entity/recruit/prTrainingPlanCourseOut.action?peTrainingPlanId=${value}','','left=500,top=300,resizable=yes,scrollbars=no,height=270,width=500,location=no')>"+this.getText("查看")+"</a>", "id");
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
		script.append("    	 	Ext.MessageBox.alert('错误','只能选择一个申请进行驳回'); return;");
		script.append("    	 }");
		script.append("        var planname = new Ext.form.TextField({fieldLabel: '驳回理由*',name: 'bean.note',allowBlank:false,maxLength:500,anchor: '90%',regex:new RegExp(/^([^\\s\\'\\\"\\&]|[^\\s\\'\\\"\\&][^\\'\\\"\\&]*[^\\s\\\'\\\"\\&])$/),regexText:'输入格式：不能以空格开头和结尾、不能包含单引号、双引号及&符号'});\n ");
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
		script.append("        title: '驳回申请',");
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
		script.append(" 		 		        	url:'/entity/recruit/peFeeManageAction_refuse.action?' ,");
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
		this.getGridConfig().addMenuFunction(this.getText("费用审核"), "/entity/recruit/peFeeManageAction_pass.action", false, true);
		this.getGridConfig().addMenuFunction(this.getText("取消通过"), "/entity/recruit/peFeeManageAction_back.action", false, true);
		this.getGridConfig().addMenuScript(this.getText("驳回申请"), script.toString());
		this.getGridConfig().setPrepared(false);
		
//		this.getGridConfig().addColumn(this.getText("站点名称"), "peSite.name");
//		this.getGridConfig().addColumn(this.getText("专业"),"prRecPlanMajorEdutype.peMajor.name",false,false,true,"");
//		this.getGridConfig().addColumn(this.getText("层次"),"prRecPlanMajorEdutype.peEdutype.name",false,false,true,"");
	}

	@Override
	public void checkBeforeAdd() throws EntityException {
		//设置默认的费用状态为0 初始导入费用
		EnumConst feeCheck=new EnumConst();
		this.getGeneralService().getGeneralDao().setEntityClass(EnumConst.class);
		DetachedCriteria dc=DetachedCriteria.forClass(EnumConst.class);
		dc.add(Restrictions.eq("namespace", "FlagFeeCheck"));
		dc.add(Restrictions.eq("code", "0"));
		List enumList=new LinkedList();
		enumList=this.getGeneralService().getList(dc);
		if(enumList!=null&&enumList.size()>0){
			feeCheck=(EnumConst) enumList.get(0);
		}		
		this.getBean().setEnumConstByFlagFeeCheck(feeCheck);
		
		//添加默认添加时间为系统当前时间
		this.getBean().setInputDate(new Date());
		
		super.checkBeforeAdd();
	}
	//通过申请
	public String pass(){
		Map map=new HashMap();
		map.put("success", "false");
		map.put("info", "操作失败");
		boolean flag=true;
		String[] ids=this.getIds().split(",");
		for(int i=0;i<ids.length;i++){
			
			//————————改变费用表中状态————————
			this.getGeneralService().getGeneralDao().setEntityClass(PeFeeDetail.class);
			PeFeeDetail fee=null;
			try {
				fee=(PeFeeDetail) this.getGeneralService().getById(ids[i]);
			} catch (EntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				flag=false;
			}
			EnumConst enu=new EnumConst();
			this.getGeneralService().getGeneralDao().setEntityClass(EnumConst.class);
			DetachedCriteria dc=DetachedCriteria.forClass(EnumConst.class);
			dc.add(Restrictions.eq("namespace", "FlagFeeCheck"));
			dc.add(Restrictions.eq("code", "1"));
			List enuList=null;
			try {
				enuList=this.getGeneralService().getList(dc);
			} catch (EntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				flag=false;
			}
			if(enuList!=null&&enuList.size()>0){
				enu=(EnumConst) enuList.get(0);
			}
			
			fee.setEnumConstByFlagFeeCheck(enu);
			this.getGeneralService().getGeneralDao().setEntityClass(PeFeeDetail.class);
			try {
				fee=(PeFeeDetail) this.getGeneralService().save(fee);
			} catch (EntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				flag=false;
			}
			//————————改变费用表中状态————————
			//————————给用户设置培训计划————————
			this.getGeneralService().getGeneralDao().setEntityClass(PeTrainee.class);
			PeTrainee trainee=null;
			try {
				trainee = (PeTrainee) this.getGeneralService().getById(fee.getPeTrainee().getId());
			} catch (EntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				flag=false;
			}
			EnumConst enum_trainType=trainee.getEnumConstByTrainingType();
//System.out.println("trainType code is "+enum_trainType.getCode());
			this.getGeneralService().getGeneralDao().setEntityClass(PeTrainingPlan.class);
			DetachedCriteria dc1=DetachedCriteria.forClass(PeTrainingPlan.class);
			dc1.createCriteria("enumConstByVersion","enumConstByVersion");
			dc1.createCriteria("enumConstByTrainingType","enumConstByTrainingType");
			dc1.add(Restrictions.eq("enumConstByTrainingType.code", enum_trainType.getCode()));
			dc1.addOrder(Order.desc("enumConstByVersion.code"));
			List planList=null;
			try {
				planList=this.getGeneralService().getList(dc1);
			} catch (EntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(planList!=null&&planList.size()>0){
				PeTrainingPlan plan=(PeTrainingPlan) planList.get(0);
				trainee.setPeTrainingPlan(plan);
			}else{
				map.put("success", "false");
				map.put("info", "该学员的培训级别暂时没有培训计划");
				this.setJsonString(JsonUtil.toJSONString(map));
				return this.json();
			}
			

			EnumConst enum_trainStatus=trainee.getEnumConstByStatus();
			if(enum_trainStatus.getCode().equals("0")) {
				EnumConst ec = (EnumConst)this.getMyListService().getEnumConstByNamespaceCode("Status", "1");
				trainee.setEnumConstByStatus(ec);
			} else if (enum_trainStatus.getCode().equals("7")) {
				EnumConst ec = (EnumConst)this.getMyListService().getEnumConstByNamespaceCode("Status", "2");
				trainee.setEnumConstByStatus(ec);
			} else if (enum_trainStatus.getCode().equals("8")) {
				EnumConst ec = (EnumConst)this.getMyListService().getEnumConstByNamespaceCode("Status", "3");
				trainee.setEnumConstByStatus(ec);
			}
			
			this.getGeneralService().getGeneralDao().setEntityClass(PeTrainee.class);
			try {
				trainee=(PeTrainee) this.getGeneralService().save(trainee);
			} catch (EntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				flag=false;
			}
			//————————给用户设置培训计划————————
			//————————给用户设置课件————————
			String sql="select ware.id from pe_training_plan plan,pr_traing_course planCourse,pe_tch_course cour,pe_tch_courseware ware" 
			+" where plan.id=planCourse.Fk_Traing_Plan_Id"
			+"      and planCourse.Fk_Course_Id=cour.id"
			+"      and cour.id=ware.fk_course_id"
			+"      and plan.id='"+trainee.getPeTrainingPlan().getId()+"'";
			List wareList=null;
			try {
				wareList=this.getGeneralService().getBySQL(sql);
			} catch (EntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				flag=false;
			}
			if(wareList!=null&&wareList.size()>0){
				for(int s=0;s<wareList.size();s++){
					PrTraineeCourseware ptc=new PrTraineeCourseware();
					ptc.setPeTrainee(trainee);
					this.getGeneralService().getGeneralDao().setEntityClass(PeTchCourseware.class);
					PeTchCourseware ware=null;
					try {
						ware=(PeTchCourseware) this.getGeneralService().getById((String)wareList.get(s));
					} catch (EntityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						flag=false;
					}
					if(ware!=null){
						ptc.setPeTchCourseware(ware);
					}
					this.getGeneralService().getGeneralDao().setEntityClass(PrTraineeCourseware.class);
					try {
						ptc=(PrTraineeCourseware) this.getGeneralService().save(ptc);
					} catch (EntityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						flag=false;
					}
				}
			}
			//————————给用户设置课件————————

			//发送短信通知
			String mobile=trainee.getMobile();
			if(mobile!=null){
				try {
					this.getGeneralService().sendSystemSms("01","");
				} catch (EntityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					flag=false;
					map.put("info", "短信发送失败");
				}
			}
			//发送短信通知
			
			//改变学员状态
			
			
		}
		
		if(flag){
			map.put("success", "true");
			map.put("info", "操作成功");
		}
		this.setJsonString(JsonUtil.toJSONString(map));
		return this.json();
	}
	
	//驳回费用申请
	public String refuse(){
		Map map=new HashMap();
		map.put("success", "false");
		map.put("info", "驳回失败");
		boolean flag=true;
		this.getGeneralService().getGeneralDao().setEntityClass(PeFeeDetail.class);
		PeFeeDetail fee=null;
		try {
			fee=(PeFeeDetail) this.getGeneralService().getById(this.getBean().getId());
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			flag=false;
		}
		EnumConst enu=new EnumConst();
		this.getGeneralService().getGeneralDao().setEntityClass(EnumConst.class);
		DetachedCriteria dc=DetachedCriteria.forClass(EnumConst.class);
		dc.add(Restrictions.eq("namespace", "FlagFeeCheck"));
		dc.add(Restrictions.eq("code", "2"));
		List enuList=null;
		try {
			enuList=this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			flag=false;
		}
		if(enuList!=null&&enuList.size()>0){
			enu=(EnumConst) enuList.get(0);
		}
		
		fee.setEnumConstByFlagFeeCheck(enu);
		fee.setNote(this.getBean().getNote());
		this.getGeneralService().getGeneralDao().setEntityClass(PeFeeDetail.class);
		try {
			this.getGeneralService().save(fee);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			flag=false;
		}
		if(flag){
			map.put("success", "true");
			map.put("info", "驳回成功");
		}
		this.setJsonString(JsonUtil.toJSONString(map));
		return this.json();
	}

	//取消通过申请
	public String back(){
		Map map=new HashMap();
		map.put("success", "false");
		map.put("info", "操作失败");
		boolean flag=true;
		String[] ids=this.getIds().split(",");
			//————————还原费用状态————————
			for(int i=0;i<ids.length;i++){
			this.getGeneralService().getGeneralDao().setEntityClass(PeFeeDetail.class);
			PeFeeDetail fee=null;
			try {
				fee=(PeFeeDetail) this.getGeneralService().getById(ids[i]);
			} catch (EntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				flag=false;
			}
			EnumConst enu=new EnumConst();
			this.getGeneralService().getGeneralDao().setEntityClass(EnumConst.class);
			DetachedCriteria dc=DetachedCriteria.forClass(EnumConst.class);
			dc.add(Restrictions.eq("namespace", "FlagFeeCheck"));
			dc.add(Restrictions.eq("code", "0"));
			List enuList=null;
			try {
				enuList=this.getGeneralService().getList(dc);
			} catch (EntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				flag=false;
			}
			if(enuList!=null&&enuList.size()>0){
				enu=(EnumConst) enuList.get(0);
			}
			
			fee.setEnumConstByFlagFeeCheck(enu);
			fee.setNote(null);
			fee.getPeTrainee().setPeTrainingPlan(null);//清除学员的培训计划
			this.getGeneralService().getGeneralDao().setEntityClass(PeFeeDetail.class);
			try {
				this.getGeneralService().save(fee);
			} catch (EntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				flag=false;
			}
			//————————还原费用状态————————
			//————————清除学员的课件————————
			this.getGeneralService().getGeneralDao().setEntityClass(PrTraineeCourseware.class);
			String sql="delete from pr_trainee_courseware ptc where ptc.fk_trainee_id='"+fee.getPeTrainee().getId()+"'";
			try {
				this.getGeneralService().executeBySQL(sql);
			} catch (EntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				flag=false;
			}
			//————————清除学员的课件————————
		}
		if(flag){
			map.put("success", "true");
			map.put("info", "操作成功");
		}
		this.setJsonString(JsonUtil.toJSONString(map));
		return this.json();
	}
	
	@Override
	public void checkBeforeUpdate() throws EntityException {
		PeFeeDetail fee=null;
		this.getGeneralService().getGeneralDao().setEntityClass(PeFeeDetail.class);
		fee=(PeFeeDetail) this.getGeneralService().getById(this.getBean().getId());
		if(fee.getEnumConstByFlagFeeCheck()!=null){
			if(!fee.getEnumConstByFlagFeeCheck().getCode().equals("0")){
				throw new EntityException("只有未审核的费用记录才能修改");
			}
		}
	}


	public void checkBeforeDelete(List idList) throws EntityException {
		PeFeeDetail fee=null;
		this.getGeneralService().getGeneralDao().setEntityClass(PeFeeDetail.class);
		String[] ids=this.getIds().split(",");
		for(int i=0;i<ids.length;i++){
			fee=(PeFeeDetail) this.getGeneralService().getById(ids[i]);
			if(fee.getEnumConstByFlagFeeCheck()!=null){
				if(!fee.getEnumConstByFlagFeeCheck().getCode().equals("0")){
					throw new EntityException("发生错误，只有未审核的费用记录才能删除");
				}
			}
		}
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PeFeeDetail.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/recruit/peFeeManageAction";
	}
	


}
