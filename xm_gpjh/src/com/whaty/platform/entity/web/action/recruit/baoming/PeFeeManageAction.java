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
		this.getGridConfig().setTitle(this.getText("????????????"));
        this.getGridConfig().setCapability(false, true, true,true,false);
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);

		if(this.getBean()!=null&&this.getBean().getPeTrainee()!=null&&this.getBean().getPeTrainee().getId()!=null){
			this.getGridConfig().addColumn(this.getText("????????????"),"peTrainee.name",true,false,true,"TextField",true,25);
		}
		this.getGridConfig().addColumn(this.getText("??????ID"),"peTrainee.loginId");//
		this.getGridConfig().addColumn(this.getText("????????????"),"peTrainee.trueName");
		this.getGridConfig().addColumn(this.getText("????????????"),"peTrainee.enumConstByTrainingType.name");
		this.getGridConfig().addColumn(this.getText("????????????"),"peTrainee.enumConstByStatus.name");
		this.getGridConfig().addColumn(this.getText("??????"),"peTrainee.enumConstByGender.name");
		this.getGridConfig().addColumn(this.getText("????????????"),"peTrainee.cardNo");
		this.getGridConfig().addColumn(this.getText("????????????"),"peTrainee.peTrainingPlan.name");
		this.getGridConfig().addColumn(this.getText("????????????"),"peTrainee.address");
		
		
		this.getGridConfig().addColumn(this.getText("????????????"),"enumConstByFlagFeeCheck.name",true,false,true,"TextField",false,25);
		this.getGridConfig().addColumn(this.getText("?????????"),"feeAmount",true,true,true,"regex:new RegExp(/^\\d+$/),regexText:'?????????????????????',");
		this.getGridConfig().addColumn(this.getText("????????????????????????"),"inputDate",true,false,true,"Date",false,25);
		this.getGridConfig().addColumn(this.getText("??????"),"note",true,false,true,"");
		this.getGridConfig().addColumn(this.getText("?????????"),"peManager.name",true,false,true,"");
		
//		
//		this.getGridConfig().addColumn(this.getText("????????????"),"enumConstByTrainingType.name");
//		this.getGridConfig().addColumn(this.getText("????????????"),"creditRequire");
//		this.getGridConfig().addColumn(this.getText("???????????????????????????"),"daysLimit",true,true,true,"TextField",true,25);
//		this.getGridConfig().addColumn(this.getText("?????????"),"enumConstByVersion.name");
//		this.getGridConfig().addColumn(this.getText("????????????"),"activeDate");
//		this.getGridConfig().addColumn(this.getText("??????"),"note",true,true,true,"TextField",true,25);
//		this.getGridConfig().addRenderFunction(this.getText("??????????????????"), "<a href=# onclick=window.open('/entity/recruit/prTrainingPlanCourseIn.action?bean.peTrainingPlan.id=${value}','','left=500,top=300,resizable=yes,scrollbars=no,height=270,width=500,location=no')>"+this.getText("??????")+"</a>", "id");
//		this.getGridConfig().addRenderFunction(this.getText("??????????????????"), "<a href=# onclick=window.open('/entity/recruit/prTrainingPlanCourseOut.action?peTrainingPlanId=${value}','','left=500,top=300,resizable=yes,scrollbars=no,height=270,width=500,location=no')>"+this.getText("??????")+"</a>", "id");
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
		script.append("    	 	Ext.MessageBox.alert('??????','????????????????????????????????????'); return;");
		script.append("    	 }");
		script.append("        var planname = new Ext.form.TextField({fieldLabel: '????????????*',name: 'bean.note',allowBlank:false,maxLength:500,anchor: '90%',regex:new RegExp(/^([^\\s\\'\\\"\\&]|[^\\s\\'\\\"\\&][^\\'\\\"\\&]*[^\\s\\\'\\\"\\&])$/),regexText:'????????????????????????????????????????????????????????????????????????????????????&??????'});\n ");
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
		script.append("        title: '????????????',");
		script.append("        width: 450,");
		script.append("        height: 225,");
		script.append("        minWidth: 300,");
		script.append("        minHeight: 250,");
		script.append("        layout: 'fit',");
		script.append("        plain:true,");
		script.append("        bodyStyle:'padding:5px;',");
		script.append("        buttonAlign:'center',");
		script.append("        items: roomformPanel,buttons: [{");
		script.append(" 	            text: '??????',");
		script.append(" 	            handler: function() {");
		script.append(" 	                if (roomformPanel.form.isValid()) {");
		script.append(" 		 		        roomformPanel.form.submit({");
		script.append(" 		 		        	url:'/entity/recruit/peFeeManageAction_refuse.action?' ,");
		script.append(" 				            waitMsg:'?????????????????????...',");
		script.append(" 							success: function(form, action) {");
		script.append(" 							    var responseArray = action.result;");
		script.append(" 							    if(responseArray.success=='true'){");
		script.append(" 							    	Ext.MessageBox.alert('??????', responseArray.info);");
		script.append(" 							    	store.load({params:{start:g_start,limit:g_limit"+this.getGridConfig().getFieldsFilter()+"}});                      ");//+this.getGridConfig().getFieldsFilter()+"
		script.append(" 								    saveroomModelWin.close();");
		script.append(" 							    } else {");
		script.append(" 							    	Ext.MessageBox.alert('??????', responseArray.info );");
		script.append(" 							    }");
		script.append(" 							}");
		script.append(" 				        });");
		script.append(" 	                } else{");
		script.append(" 						Ext.MessageBox.alert('??????', '??????????????????????????????????????????');");
		script.append(" 					}");
		script.append(" 		        }");
		script.append(" 	        },{");
		script.append(" 	            text: '??????',");
		script.append(" 	            handler: function(){saveroomModelWin.close();}");
		script.append(" 	        }]");
		script.append("        });");
		script.append("        saveroomModelWin.show();");
		script.append("  }");
		this.getGridConfig().addMenuFunction(this.getText("????????????"), "/entity/recruit/peFeeManageAction_pass.action", false, true);
		this.getGridConfig().addMenuFunction(this.getText("????????????"), "/entity/recruit/peFeeManageAction_back.action", false, true);
		this.getGridConfig().addMenuScript(this.getText("????????????"), script.toString());
		this.getGridConfig().setPrepared(false);
		
//		this.getGridConfig().addColumn(this.getText("????????????"), "peSite.name");
//		this.getGridConfig().addColumn(this.getText("??????"),"prRecPlanMajorEdutype.peMajor.name",false,false,true,"");
//		this.getGridConfig().addColumn(this.getText("??????"),"prRecPlanMajorEdutype.peEdutype.name",false,false,true,"");
	}

	@Override
	public void checkBeforeAdd() throws EntityException {
		//??????????????????????????????0 ??????????????????
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
		
		//?????????????????????????????????????????????
		this.getBean().setInputDate(new Date());
		
		super.checkBeforeAdd();
	}
	//????????????
	public String pass(){
		Map map=new HashMap();
		map.put("success", "false");
		map.put("info", "????????????");
		boolean flag=true;
		String[] ids=this.getIds().split(",");
		for(int i=0;i<ids.length;i++){
			
			//????????????????????????????????????????????????????????????????????????
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
			//????????????????????????????????????????????????????????????????????????
			//???????????????????????????????????????????????????????????????????????????
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
				map.put("info", "????????????????????????????????????????????????");
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
			//???????????????????????????????????????????????????????????????????????????
			//?????????????????????????????????????????????????????????????????????
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
			//?????????????????????????????????????????????????????????????????????

			//??????????????????
			String mobile=trainee.getMobile();
			if(mobile!=null){
				try {
					this.getGeneralService().sendSystemSms("01","");
				} catch (EntityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					flag=false;
					map.put("info", "??????????????????");
				}
			}
			//??????????????????
			
			//??????????????????
			
			
		}
		
		if(flag){
			map.put("success", "true");
			map.put("info", "????????????");
		}
		this.setJsonString(JsonUtil.toJSONString(map));
		return this.json();
	}
	
	//??????????????????
	public String refuse(){
		Map map=new HashMap();
		map.put("success", "false");
		map.put("info", "????????????");
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
			map.put("info", "????????????");
		}
		this.setJsonString(JsonUtil.toJSONString(map));
		return this.json();
	}

	//??????????????????
	public String back(){
		Map map=new HashMap();
		map.put("success", "false");
		map.put("info", "????????????");
		boolean flag=true;
		String[] ids=this.getIds().split(",");
			//??????????????????????????????????????????????????????????????????
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
			fee.getPeTrainee().setPeTrainingPlan(null);//???????????????????????????
			this.getGeneralService().getGeneralDao().setEntityClass(PeFeeDetail.class);
			try {
				this.getGeneralService().save(fee);
			} catch (EntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				flag=false;
			}
			//??????????????????????????????????????????????????????????????????
			//?????????????????????????????????????????????????????????????????????
			this.getGeneralService().getGeneralDao().setEntityClass(PrTraineeCourseware.class);
			String sql="delete from pr_trainee_courseware ptc where ptc.fk_trainee_id='"+fee.getPeTrainee().getId()+"'";
			try {
				this.getGeneralService().executeBySQL(sql);
			} catch (EntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				flag=false;
			}
			//?????????????????????????????????????????????????????????????????????
		}
		if(flag){
			map.put("success", "true");
			map.put("info", "????????????");
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
				throw new EntityException("??????????????????????????????????????????");
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
					throw new EntityException("?????????????????????????????????????????????????????????");
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
