package com.whaty.platform.entity.web.action.study;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeFeeDetail;
import com.whaty.platform.entity.bean.PeTrainee;
import com.whaty.platform.entity.bean.SystemApply;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.JsonUtil;

public class PeCertificateApplyAction extends MyBaseAction {

//	private List applyList;//所有申请列表
//	private boolean forbidApply;//可以申请结业，是的话就不能再申请了
//	private String msg;//提示信息
	@Override
	public DetachedCriteria initDetachedCriteria() {
		UserSession us=(UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		this.getGeneralService().getGeneralDao().setEntityClass(SystemApply.class);
		DetachedCriteria dc = DetachedCriteria.forClass(SystemApply.class);
		dc.createCriteria("enumConstByApplyType","enumConstByApplyType");
		dc.createCriteria("enumConstByFlagApplyStatus","enumConstByFlagApplyStatus");
		dc.createCriteria("peTrainee","peTrainee");
		dc.add(Restrictions.or(Restrictions.or(Restrictions.eq("enumConstByApplyType.code","22"),Restrictions.eq("enumConstByApplyType.code","23")),Restrictions.eq("enumConstByApplyType.code","24")));//证书申请
		return dc;
	}
	
	public void setBean(SystemApply instance) {
		super.superSetBean(instance);
		
	}
	
	public SystemApply getBean(){
		return  (SystemApply)super.superGetBean();
	}

	@Override
	public void initGrid() {
		this.getGridConfig().setTitle(this.getText("申请证书"));
        this.getGridConfig().setCapability(false, true, false,true,false);
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);


			this.getGridConfig().addColumn(this.getText("姓名"),"peTrainee.name");
			this.getGridConfig().addColumn(this.getText("证书申请类型"),"enumConstByApplyType.name");
			this.getGridConfig().addColumn(this.getText("是否通过"),"enumConstByFlagApplyStatus.name");
			this.getGridConfig().addColumn(this.getText("申请备注"),"applyNote",true,true,true,"TextField",false,255);
			this.getGridConfig().addColumn(this.getText("申请原因"),"applyInfo",true,true,true,"TextField",false,255);
			this.getGridConfig().addColumn(this.getText("申请日期"),"applyDate",true,false,true,"");
			this.getGridConfig().addColumn(this.getText("审核备注"),"checkNote",true,false,true,"");
			this.getGridConfig().addColumn(this.getText("审核日期"),"checkDate",true,false,true,"");
//			this.getGridConfig().addColumn(this.getText("流水号"),"moveSn",true,false,true,"");
		
		StringBuilder script = new StringBuilder();//驳回申请
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
		script.append("        var planname = new Ext.form.TextField({fieldLabel: '驳回理由*',maxLength:200,name: 'bean.checkNote',allowBlank:false,anchor: '90%',regex:new RegExp(/^([^\\s\\'\\\"\\&]|[^\\s\\'\\\"\\&][^\\'\\\"\\&]*[^\\s\\\'\\\"\\&])$/),regexText:'输入格式：不能以空格开头和结尾、不能包含单引号、双引号及&符号'});\n ");
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
		script.append(" 		 		        	url:'/entity/study/peCertificateApply_refuse.action?' ,");
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
		

		StringBuilder script1 = new StringBuilder();
		script1.append(" {");
		script1.append(" 	 var m = grid.getSelections();");
		script1.append("    	 var ids = '';");
		script1.append("    	 if(m.length == 1){");
		script1.append("    	 		for(var i=0,len =m.length;i<len;i++ ){");
		script1.append("    	 			var ss = m[i].get('id');");
		script1.append("    	 			if(i==0)ids = ids+ ss ;");
		script1.append("    	 			else ids = ids +','+ss;");
		script1.append("    	 		}");
		script1.append("    	 }else{");
		script1.append("    	 	Ext.MessageBox.alert('错误','只能选择一个申请进行通过'); return;");
		script1.append("    	 }");
		script1.append("        var planname = new Ext.form.TextField({fieldLabel: '通过理由*',maxLength:200,name: 'bean.checkNote',allowBlank:false,anchor: '90%',regex:new RegExp(/^([^\\s\\'\\\"\\&]|[^\\s\\'\\\"\\&][^\\'\\\"\\&]*[^\\s\\\'\\\"\\&])$/),regexText:'输入格式：不能以空格开头和结尾、不能包含单引号、双引号及&符号'});\n ");
		script1.append("        var fids = new Ext.form.Hidden({name:'bean.id',value:ids});");
		script1.append("    	 var roomformPanel = new Ext.form.FormPanel({");
		script1.append(" 	    frame:true,");
		script1.append("         labelWidth: 100,");
		script1.append("        	defaultType: 'textfield',");
		script1.append(" 				autoScroll:true,reader: new Ext.data.JsonReader({root: 'models'},['bean.id','bean.name']),");
		script1.append("         items: [ planname ,fids]");
		script1.append("       });  ");
//		script1.append("        roomformPanel.form.load({url:'"+this.getDetailAction()+"?bean.id='+ids,waitMsg:'Loading'});  ");
		script1.append("        var saveroomModelWin = new Ext.Window({");
		script1.append("        title: '通过申请',");
		script1.append("        width: 450,");
		script1.append("        height: 225,");
		script1.append("        minWidth: 300,");
		script1.append("        minHeight: 250,");
		script1.append("        layout: 'fit',");
		script1.append("        plain:true,");
		script1.append("        bodyStyle:'padding:5px;',");
		script1.append("        buttonAlign:'center',");
		script1.append("        items: roomformPanel,buttons: [{");
		script1.append(" 	            text: '保存',");
		script1.append(" 	            handler: function() {");
		script1.append(" 	                if (roomformPanel.form.isValid()) {");
		script1.append(" 		 		        roomformPanel.form.submit({");
		script1.append(" 		 		        	url:'/entity/study/peCertificateApply_pass.action?' ,");
		script1.append(" 				            waitMsg:'处理中，请稍候...',");
		script1.append(" 							success: function(form, action) {");
		script1.append(" 							    var responseArray = action.result;");
		script1.append(" 							    if(responseArray.success=='true'){");
		script1.append(" 							    	Ext.MessageBox.alert('提示', responseArray.info);");
		script1.append(" 							    	store.load({params:{start:g_start,limit:g_limit"+this.getGridConfig().getFieldsFilter()+"}});                      ");//+this.getGridConfig().getFieldsFilter()+"
		script1.append(" 								    saveroomModelWin.close();");
		script1.append(" 							    } else {");
		script1.append(" 							    	Ext.MessageBox.alert('错误', responseArray.info );");
		script1.append(" 							    }");
		script1.append(" 							}");
		script1.append(" 				        });");
		script1.append(" 	                } else{");
		script1.append(" 						Ext.MessageBox.alert('错误', '输入错误，请先修正提示的错误');");
		script1.append(" 					}");
		script1.append(" 		        }");
		script1.append(" 	        },{");
		script1.append(" 	            text: '取消',");
		script1.append(" 	            handler: function(){saveroomModelWin.close();}");
		script1.append(" 	        }]");
		script1.append("        });");
		script1.append("        saveroomModelWin.show();");
		script1.append("  }");

		this.getGridConfig().addMenuScript(this.getText("审核通过"), script1.toString());
		this.getGridConfig().addMenuScript(this.getText("驳回申请"), script.toString());
		this.getGridConfig().setPrepared(false);
		
//		this.getGridConfig().addColumn(this.getText("站点名称"), "peSite.name");
//		this.getGridConfig().addColumn(this.getText("专业"),"prRecPlanMajorEdutype.peMajor.name",false,false,true,"");
//		this.getGridConfig().addColumn(this.getText("层次"),"prRecPlanMajorEdutype.peEdutype.name",false,false,true,"");
	}

	@Override
	public void checkBeforeAdd() throws EntityException {
		this.getBean().setApplyDate(new Date());
		this.getBean().setCheckNote("");
		this.getBean().setCheckDate(null);
		
		UserSession us=(UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		if(us.getUserLoginType().equals(SsoConstant.SSO_STUDENT)){//如果当前用户是学员，只显示属于他自己的信息即可
			String traineeId=us.getSsoUser().getLoginId();
			this.getGeneralService().getGeneralDao().setEntityClass(PeTrainee.class);
			PeTrainee trainee=(PeTrainee) this.getGeneralService().getById(traineeId);
			if(trainee!=null){
				this.getBean().setPeTrainee(trainee);
			}else{
				System.out.println("查询当前用户出错");
			}
		}
		EnumConst enu=null;
		DetachedCriteria dc=DetachedCriteria.forClass(EnumConst.class);
		dc.add(Restrictions.eq("namespace", "FlagApplyStatus"));
		dc.add(Restrictions.eq("code", "0"));//未通过
		List enuList=null;
		this.getGeneralService().getGeneralDao().setEntityClass(EnumConst.class);
		enuList=this.getGeneralService().getList(dc);
		if(enuList!=null&&enuList.size()>0){
			enu=(EnumConst) enuList.get(0);
		}else{
			System.out.println("查询未通过的状态出错");
		}
		this.getBean().setEnumConstByFlagApplyStatus(enu);
	}
	//通过申请
	public String pass(){
		Map map=new HashMap();
		map.put("success", "false");
		map.put("info", "操作失败");
		boolean flag=true;
			this.getGeneralService().getGeneralDao().setEntityClass(SystemApply.class);
			SystemApply apply=null;
			try {
				apply=(SystemApply) this.getGeneralService().getById(this.getBean().getId());
			} catch (EntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				flag=false;
			}
			if(!apply.getEnumConstByFlagApplyStatus().getCode().equals("0")){//判断该申请是否已经被允许或驳回
				flag=false;
				map.put("success", "false");
				map.put("info", "该申请已经被允许或驳回，不能再操作");
			}else{
				EnumConst enu=new EnumConst();
				this.getGeneralService().getGeneralDao().setEntityClass(EnumConst.class);
				DetachedCriteria dc=DetachedCriteria.forClass(EnumConst.class);
				dc.add(Restrictions.eq("namespace", "FlagApplyStatus"));
				dc.add(Restrictions.eq("code", "1"));//审核通过
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
				}else{
					System.out.println("查询通过的请求状态错误");
				}
				apply.setCheckNote(this.getBean().getCheckNote());
				apply.setEnumConstByFlagApplyStatus(enu);
				apply.setCheckDate(new Date());
				this.getGeneralService().getGeneralDao().setEntityClass(SystemApply.class);
				try {
					this.getGeneralService().save(apply);
				} catch (EntityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					flag=false;
				}
			}
		if(flag){
			map.put("success", "true");
			map.put("info", "操作成功");
		}
		this.setJsonString(JsonUtil.toJSONString(map));
		return this.json();
	}
	
	//驳回申请
	public String refuse(){
		Map map=new HashMap();
		map.put("success", "false");
		map.put("info", "驳回失败");
		boolean flag=true;
		this.getGeneralService().getGeneralDao().setEntityClass(SystemApply.class);
		SystemApply apply=null;
		try {
			apply=(SystemApply) this.getGeneralService().getById(this.getBean().getId());
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			flag=false;
		}
		if(!apply.getEnumConstByFlagApplyStatus().getCode().equals("0")){//判断该申请是否已经被允许或驳回
			flag=false;
			map.put("success", "false");
			map.put("info", "该申请已经被允许或驳回，不能再操作");
		}else{
			EnumConst enu=new EnumConst();
			this.getGeneralService().getGeneralDao().setEntityClass(EnumConst.class);
			DetachedCriteria dc=DetachedCriteria.forClass(EnumConst.class);
			dc.add(Restrictions.eq("namespace", "FlagApplyStatus"));
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
			
			apply.setEnumConstByFlagApplyStatus(enu);
			apply.setCheckNote(this.getBean().getCheckNote());
			apply.setCheckDate(new Date());
	//		fee.setNote(this.getBean().getNote());
			this.getGeneralService().getGeneralDao().setEntityClass(PeFeeDetail.class);
			try {
				this.getGeneralService().save(apply);
			} catch (EntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				flag=false;
			}
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
			this.getGeneralService().getGeneralDao().setEntityClass(PeFeeDetail.class);
			try {
				this.getGeneralService().save(fee);
			} catch (EntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				flag=false;
			}
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

	/**
	 * 获取删除记录的信息
	 * 
	 * @param tableName
	 * @param ids
	 * @return
	 */
	protected String getDataInfo(List idList) {
		String tableName = "";
		Class clazz = this.getEntityClass();
		
		String ids = "";
		for(int i = 0; i < idList.size(); i++) {
			if(i == 0) {
				ids = "'" + (String)idList.get(i) + "'";
			} else {
				ids += ", '" + (String)idList.get(i) + "'";
			}
		}
		
		String dataInfo = "" ;
		String sql = "select apply_note from system_apply where id in (" + ids + ")";
		List list = null;
		try {
			list = this.getGeneralService().getBySQL(sql);
		} catch (EntityException e) {
//			e.printStackTrace();
		}
		
		if(list != null && list.size() > 0) {
			for(int i = 0; i < list.size(); i++) {
				if(i == 0) {
					dataInfo += "\"" + (String)list.get(i) + "\"";
				} else {
					dataInfo += ", \"" + (String)list.get(i) + "\"";
				}
			}
		}
		
		return dataInfo;
	}


	
	@Override
	public void setEntityClass() {
		this.entityClass=SystemApply.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/study/peCertificateApply";
	}
	
//	public String getList(){
//
//		UserSession us=(UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
//		
//		this.getGeneralService().getGeneralDao().setEntityClass(SystemApply.class);
//		DetachedCriteria dc = DetachedCriteria.forClass(SystemApply.class);
//		dc.createCriteria("peTrainee","peTrainee");
//		dc.createCriteria("enumConstByFlagApplyStatus","enumConstByFlagApplyStatus");
//		dc.createCriteria("enumConstByApplyType","enumConstByApplyType");
//		dc.add(Restrictions.eq("enumConstByApplyType.code","21"));//类型为 结业申请
//		dc.add(Restrictions.eq("peTrainee.loginId", us.getSsoUser().getLoginId()));//学员只能看到自己的申请记录
//
////		if(!this.checkTraineeForLevelUp(us.getSsoUser().getLoginId())){//查看当前用户是否已结业
////			this.setForbidApply(true);
////			return "showApplyList";
////		}
//		List applyList=null;
//		try {
//			applyList=this.getGeneralService().getList(dc);
//		} catch (EntityException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		this.setApplyList(applyList);
//		return "showApplyList";
//	}
//	
//	//检查该学员是否结束了培训计划
//	private boolean checkTraineeForLevelUp(String loginId) {
//		this.getGeneralService().getGeneralDao().setEntityClass(PeTrainee.class);
//		DetachedCriteria dc=DetachedCriteria.forClass(PeTrainee.class);
//		dc.add(Restrictions.eq("loginId", loginId));
//		PeTrainee trainee=null;
//		List traineeList=null;
//		try {
//			traineeList=this.getGeneralService().getList(dc);
//		} catch (EntityException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		if(traineeList!=null&&traineeList.size()>0){
//			trainee=(PeTrainee) traineeList.get(0);
//		}else{
//			;
//		}
//		if(true){//判断条件
//			return true;
//		}
//		return false;
//	}
//
//	public String addApply(){
//
//		UserSession us=(UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
//		List<String> resultList=new LinkedList<String>();
//		try {//查看该学员是否可以申请结业
//			 resultList=this.getGeneralService().canApplyHigherTrainingType(us.getSsoUser().getLoginId());
//		} catch (EntityException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		if(resultList!=null&&resultList.size()>0){
//			StringBuffer strb=new StringBuffer();
//			for(int i=0;i<resultList.size();i++){
//				strb.append(resultList.get(i));
//				strb.append("\\n");
//			}
//			strb.deleteCharAt(strb.length()-1);
//			strb.deleteCharAt(strb.length()-1);
//			strb.deleteCharAt(strb.length()-1);
//			this.setForbidApply(true);
//			this.setMsg(strb.toString());
//		}
//		
//		return "newApply";
//	}
//	public String saveNewApply(){
//		//-------查找当前用户----------
//		UserSession us=(UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
//
//		List<String> resultList=new LinkedList<String>();
//		try {
//			resultList =this.getGeneralService().canApplyHigherTrainingType(us.getSsoUser().getLoginId());//查看当前学员是否可以提交结业申请
//		} catch (EntityException e2) {
//			// TODO Auto-generated catch block
//			e2.printStackTrace();
//		}
//		if(resultList!=null&&resultList.size()>0){//该学员不能提交结业申请
//			StringBuffer strb=new StringBuffer();
//			for(int i=0;i<resultList.size();i++){
//				strb.append(resultList.get(i));
//				strb.append("\\n");
//			}
//			strb.deleteCharAt(strb.length()-1);
//			strb.deleteCharAt(strb.length()-1);
//			strb.deleteCharAt(strb.length()-1);
//			this.setMsg(strb.toString());
//			return "addNewSucce";
//		}
//		
//		DetachedCriteria dc=DetachedCriteria.forClass(PeTrainee.class);
//		dc.add(Restrictions.eq("loginId", us.getSsoUser().getLoginId()));
//		List traineeList=null;
//		PeTrainee trainee=null;
//		try {
//			traineeList=this.getGeneralService().getList(dc);
//		} catch (EntityException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		if(traineeList!=null&&traineeList.size()>0){
//			trainee=(PeTrainee) traineeList.get(0);
//			this.getBean().setPeTrainee(trainee);
//		}//-------查找当前用户----------
//		//--------------查找申请培训级别的状态-----------------
//		EnumConst enu=null;
//		DetachedCriteria dc1=DetachedCriteria.forClass(EnumConst.class);
//		dc1.add(Restrictions.eq("namespace", "FlagApplyStatus"));
//		dc1.add(Restrictions.eq("code", "0"));//未通过
//		List enuList=null;
//		this.getGeneralService().getGeneralDao().setEntityClass(EnumConst.class);
//		try {
//			enuList=this.getGeneralService().getList(dc1);
//		} catch (EntityException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		if(enuList!=null&&enuList.size()>0){
//			enu=(EnumConst) enuList.get(0);
//		}else{
//			System.out.println("查询未通过的状态出错");
//		}
//		this.getBean().setEnumConstByFlagApplyStatus(enu);
//		//--------------查找申请培训级别的状态-----------------
//		//--------------查找申请培训级别的类型-----------------
//		EnumConst enuu=null;
//		DetachedCriteria dcc=DetachedCriteria.forClass(EnumConst.class);
//		dcc.add(Restrictions.eq("namespace", "ApplyType"));
//		dcc.add(Restrictions.eq("code", "21"));//结业申请
//		List enuListt=null;
//		this.getGeneralService().getGeneralDao().setEntityClass(EnumConst.class);
//		try {
//			enuListt=this.getGeneralService().getList(dcc);
//		} catch (EntityException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		if(enuListt!=null&&enuListt.size()>0){
//			enuu=(EnumConst) enuListt.get(0);
//		}else{
//			System.out.println("查询结业申请的类别出错");
//		}
//		this.getBean().setEnumConstByApplyType(enuu);
//		//--------------查找申请培训级别的类型-----------------
//		
//		
//		this.getGeneralService().getGeneralDao().setEntityClass(EnumConst.class);
//		
//		
//		
//		
//		this.getBean().setApplyDate(new Date());
//		this.getGeneralService().getGeneralDao().setEntityClass(SystemApply.class);
//		try {
//			this.getGeneralService().save(this.getBean());
//		} catch (EntityException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		this.setMsg("提交成功");
//		return "addNewSucce";
//	}
//	//删除用户自己删除未待审核的申请
//	public String delApply(){
//		String[] ids=this.getIds().split(",");
//		this.getGeneralService().getGeneralDao().setEntityClass(SystemApply.class);
//		int delSucc=0;
//		for(int i=0;i<ids.length;i++){
//			SystemApply apply=null;
//			try {
//				apply=(SystemApply) this.getGeneralService().getById(ids[i]);
//			} catch (EntityException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			if(apply.getEnumConstByFlagApplyStatus().getCode().equals("0")){//只有为审核的请求才能删除
//				try {
//					this.getGeneralService().delete(apply);
//					delSucc++;
//				} catch (EntityException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		}
//		this.setMsg(delSucc+"个删除成功");
//		return this.getList();
//	}
//
//
//	public List getApplyList() {
//		return applyList;
//	}
//
//	public void setApplyList(List applyList) {
//		this.applyList = applyList;
//	}
//
//	public boolean isForbidApply() {
//		return forbidApply;
//	}
//
//	public void setForbidApply(boolean forbidApply) {
//		this.forbidApply = forbidApply;
//	}
//
//	public String getMsg() {
//		return msg;
//	}
//
//	public void setMsg(String msg) {
//		this.msg = msg;
//	}
	
}
