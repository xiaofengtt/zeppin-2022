package com.whaty.platform.entity.web.action.information.sms;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeExamPatrol;
import com.whaty.platform.entity.bean.PeSmsInfo;
import com.whaty.platform.entity.bean.PeSmsSystempoint;
import com.whaty.platform.entity.service.sms.PeSmsInfoService;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.util.JsonUtil;

public class PeSmsCheckAction extends MyBaseAction<PeSmsInfo>{
	
	PeSmsInfoService peSmsInfoService;
	
	public PeSmsInfoService getPeSmsInfoService() {
		return peSmsInfoService;
	}

	public void setPeSmsInfoService(PeSmsInfoService peSmsInfoService) {
		this.peSmsInfoService = peSmsInfoService;
	}

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().setTitle(this.getText("审核短信"));

		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		
		this.getGridConfig().addColumn(this.getText("短信内容"), "note");
		this.getGridConfig().addColumn(this.getText("短信类型"), "enumConstByFlagSmsType.name");
		this.getGridConfig().addColumn(this.getText("发送时间"), "bookingDate");
//		this.getGridConfig().addColumn(this.getText("发送站点"), "peSite.name");
		this.getGridConfig().addColumn(this.getText("发送站点"), "peSite.name",
				true, false, false, "TextField", false, 0);
		this.getGridConfig().addRenderScript(this.getText("发送站点"),
				"{if (${value}=='') return '总站'; else return ${value};}",
				"peSite.name");
		this.getGridConfig().addColumn(this.getText("发送人"), "senderLoginInId");
//		this.getGridConfig().addColumn(this.getText("审核状态"), "enumConstByFlagSmsStatus.name");
		this.getGridConfig().addColumn(this.getText("审核状态"), "enumConstByFlagSmsStatus.name", false);
//		this.getGridConfig().addColumn(this.getText("驳回原因"), "returnReason");
		this.getGridConfig().addMenuFunction("通过审核", "/entity/information/peSmsCheck_CheckSms.action", false, true);
		this.getGridConfig().addRenderFunction("发送对象", "<a href=/entity/information/prSmsSendStatus.action?sms_id=${value} target=_self>查看详细信息</a>", "id");
		
		if(this.getGridConfig().checkBeforeAddMenu("/entity/information/peSmsCheck_reject.action")){
		this.getGridConfig().addMenuScript(this.getText("驳回"), "{"+ 
				"			 var m = grid.getSelections();                                                                                             "+
				"	   	 var ids = '';                                                                                                             "+
				"	   	 if(m.length > 0){                                                                                                         "+
				"	   	 		for(var i=0,len =m.length;i<len;i++ ){                                                                                 "+
				"	   	 			var ss = m[i].get('id');                                                                                   "+
				"	   	 			if(i==0)ids = ids+ ss ;                                                                                              "+
				"	   	 			else ids = ids +','+ss;                                                                                              "+
				"	   	 		}                                                                                                                      "+
				"	   	 }else{                                                                                                                    "+
				"	   	 	Ext.MessageBox.alert('错误','请至少选择一条信息'); return;                                                                      "+
				"	   	 }                                                                                                                         "+
				"			 var returnReason = new Ext.form.TextArea({fieldLabel: '驳回原因*', name: 'bean.returnReason', allowBlank:false,          "+
				"			 		maxLength:50,regex:new RegExp(/^(\\S|\\S.*\\S)$/),regexText:'输入格式：不能以空格开头和结尾',anchor: '90%'});            "+
				"            var fids = new Ext.form.Hidden({name:'ids',value:ids});        					                                      "+
				"	   	                                                                                                                           "+
				"	   	 var formPanel = new Ext.form.FormPanel({                                                                                  "+
				"		    frame:true,                                                                                                              "+
				"	        labelWidth: 100,                                                                                                       "+
				"	       	defaultType: 'textfield',                                                                                              "+
				"					autoScroll:true,                                                                                                       "+
				"	        items: [ returnReason ,fids]                                                                                                "+
				"	    });                                                                                                                        "+
				"	   	                                                                                                                           "+
				"	   	var addModelWin = new Ext.Window({                                                                                         "+
				"	       title: '驳回短信',                                                                                                      "+
				"	       width: 525,                                                                                                             "+
				"	       height: 150,                                                                                                            "+
				"	       minWidth: 300,                                                                                                          "+
				"	       minHeight: 250,                                                                                                         "+
				"	       layout: 'fit',                                                                                                          "+
				"	       plain:true,                                                                                                             "+
				"	       bodyStyle:'padding:5px;',                                                                                               "+
				"	       buttonAlign:'center',                                                                                                   "+
				"	       items: formPanel,                                                                                                       "+
				"	       buttons: [{                                                                                                             "+
				"		            text: '保存',                                                                                                    "+
				"		            handler: function() {                                                                                            "+
				"		                                                                                                                             "+
				"		                if (formPanel.form.isValid()) {                                                                              "+
				"			 		        formPanel.form.submit({                                                                                        "+
				"			 		        	url:'/entity/information/peSmsCheck_reject.action?' ,                                                        "+
				"					            waitMsg:'处理中，请稍候...',                                                                               "+
				"								success: function(form, action) {                                                                                "+
				"								    var responseArray = action.result;                                                                           "+
				"								    if(responseArray.success=='true'){                                                                           "+
				"								    	Ext.MessageBox.alert('提示', responseArray.info);                              "+
				"								    	store.load({params:{start:g_start,limit:g_limit}}); "+
				"									    addModelWin.close();                                                                                       "+
				"								    } else {                                                                                                     "+
				"								    	Ext.MessageBox.alert('错误', responseArray.info );                 "+
				"								    }                                                                                                            "+
				"								}                                                                                                                "+
				"					        });                                                                                                            "+
				"		                } else{                                                                                                      "+
				"							Ext.MessageBox.alert('错误', '输入错误，请先修正提示的错误');                                                      "+
				"						}                                                                                                                    "+
				"			        }                                                                                                                  "+
				"		        },{                                                                                                                  "+
				"		            text: '取消',                                                                                                    "+
				"		            handler: function(){addModelWin.close();}                                                                        "+
				"		        }]                                                                                                                   "+
				"	                                                                                                                               "+
				"	       });                                                                                                                     "+
				"	       addModelWin.show();                                                                                                     "+				
				" }   ");
		}
		System.out.println(this.getGridConfig().getFieldsFilter());
		
	}
	
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeSmsInfo.class);
		dc.createAlias("peSite", "peSite",DetachedCriteria.LEFT_JOIN);
		dc.createAlias("enumConstByFlagSmsType", "enumConstByFlagSmsType");
		dc.createAlias("enumConstByFlagSmsStatus", "enumConstByFlagSmsStatus");
		dc.add(Restrictions.eq("enumConstByFlagSmsStatus.code", "0"));
		return dc;
	}
	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
		this.entityClass = PeSmsInfo.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/information/peSmsCheck";
	}
	
	public void setBean(PeSmsInfo instance){
		this.superSetBean(instance);
	}
	
	public PeSmsInfo getBean(){
		return (PeSmsInfo)this.superGetBean();
	}
	
	public String CheckSms(){
		
		Map map = new HashMap();
		String peSms_ids = this.getIds();
		
		try{
			this.getPeSmsInfoService().update_checkSmsMessage(peSms_ids);
			
			map.put("success", true);
			map.put("info", "短信审核通过,普通短信将会马上发送！");
		}catch(Exception e){
			e.printStackTrace();
			map.clear();
			map.put("success", false);
			map.put("info", e.getMessage());
		}
		
		this.setJsonString(JsonUtil.toJSONString(map));
		return json();
	}
	/**
	 * 驳回短信
	 * @return
	 */
	public String reject(){
		Map map = new HashMap();
		String peSms_ids = this.getIds();
		String returnReason = this.getBean().getReturnReason();
		try{
			this.getPeSmsInfoService().update_rejectSmsMessage(peSms_ids,returnReason);
			
			map.put("success", true);
			map.put("info", "短信驳回成功！");
		}catch(Exception e){
			e.printStackTrace();
			map.clear();
			map.put("success", false);
			map.put("info", e.getMessage());
		}
		this.setJsonString(JsonUtil.toJSONString(map));
		return json();
	}
}
