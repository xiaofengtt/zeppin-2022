package com.whaty.platform.entity.web.action.sms;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.GlobalProperties;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.standard.sms.basic.OracleSmsMessage;
import com.whaty.platform.entity.bean.PeManager;
import com.whaty.platform.entity.bean.PeTrainee;
import com.whaty.platform.entity.bean.PeTrainingPlan;
import com.whaty.platform.entity.bean.PrRecPlanMajorEdutype;
import com.whaty.platform.entity.bean.PrRecPlanMajorSite;
import com.whaty.platform.entity.bean.SmsInfo;
import com.whaty.platform.entity.bean.SystemApply;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sms.SmsSendThread;
import com.whaty.platform.sms.basic.SmsMessage;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.JsonUtil;
import com.whaty.platform.entity.bean.PrTraingCourse;
import com.whaty.platform.entity.bean.PeFeeDetail;
import com.whaty.platform.entity.bean.EnumConst;

public class PeSmsInfoManageAction extends MyBaseAction {

	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(SmsInfo.class);
		dc.createCriteria("enumConstByScope","enumConstByScope",DetachedCriteria.LEFT_JOIN);//发送范围
		dc.createCriteria("enumConstBySmsStatus", "enumConstBySmsStatus",DetachedCriteria.LEFT_JOIN);//状态，是否审核过 1 已审核 -1 已驳回
		dc.createCriteria("peManagerBySender", "peManagerBySender",DetachedCriteria.LEFT_JOIN);//发送人姓名
		dc.createCriteria("enumConstBySendstatus", "enumConstBySendstatus",DetachedCriteria.LEFT_JOIN);//是否已发送 0 未发送 1已发送
		dc.createCriteria("peManagerByChecker", "peManagerByChecker",DetachedCriteria.LEFT_JOIN);//审核人
		dc.createCriteria("enumConstByType", "enumConstByType",DetachedCriteria.LEFT_JOIN);//短信类型，0普通短信、1定时短信、2系统短信
		
		dc.add(Restrictions.ne("enumConstBySmsStatus.code", "1"));//没有通过审核
		
		return dc;
	}
	
	public void setBean(SmsInfo instance) {
		super.superSetBean(instance);
		
	}
	
	public SmsInfo getBean(){
		return  (SmsInfo)super.superGetBean();
	}

	@Override
	public void initGrid() {
		this.getGridConfig().setTitle(this.getText("审核短信"));
        this.getGridConfig().setCapability(false, true, false,true,false);
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);

		this.getGridConfig().addColumn(this.getText("发送者"),"peManagerBySender.trueName");
		this.getGridConfig().addColumn(this.getText("发送对象"),"targets");
		this.getGridConfig().addColumn(this.getText("短信内容"),"content");
		this.getGridConfig().addColumn(this.getText("短信类型"),"enumConstByType.name");
		this.getGridConfig().addColumn(this.getText("审核状态"),"enumConstBySmsStatus.name");
		this.getGridConfig().addColumn(this.getText("发送时间"),"sendDate");
		this.getGridConfig().addColumn(this.getText("状态"),"enumConstBySendstatus.name");
		this.getGridConfig().addColumn(this.getText("审核人"),"peManagerByChecker.trueName");
		this.getGridConfig().addColumn(this.getText("备注"),"note");
		
//		this.getGridConfig().addMenuFunction(this.getText("审核"), "enumConstBySmsStatus.id",this.getMyListService().getEnumConstByNamespaceCode("SmsStatus", "1").getId());
		this.getGridConfig().addMenuFunction(this.getText("审核"), "/entity/information/peSmsInfoManageAction_pass.action", false, true);
//		this.getGridConfig().addMenuFunction(this.getText("驳回"), "enumConstBySmsStatus.id",this.getMyListService().getEnumConstByNamespaceCode("SmsStatus", "-1").getId());
		
		StringBuilder script = new StringBuilder();
		script.append(" {");
		script.append(" 	 var m = grid.getSelections();");
		script.append("    	 var ids = '';");
		script.append("    	 		for(var i=0,len =m.length;i<len;i++ ){");
		script.append("    	 			var ss = m[i].get('id');");
		script.append("    	 			if(i==0)ids = ids+ ss ;");
		script.append("    	 			else ids = ids +','+ss;");
		script.append("    	 		}");
		script.append("        var planname = new Ext.form.TextField({fieldLabel: '驳回理由',name: 'bean.note',allowBlank:true,anchor: '90%',regex:new RegExp(/^([^\\s\\'\\\"\\&]|[^\\s\\'\\\"\\&][^\\'\\\"\\&]*[^\\s\\\'\\\"\\&])$/),regexText:'输入格式：不能以空格开头和结尾、不能包含单引号、双引号及&符号'});\n ");
		script.append("        var fids = new Ext.form.Hidden({name:'ids',value:ids});");
		script.append("    	 var roomformPanel = new Ext.form.FormPanel({");
		script.append(" 	    frame:true,");
		script.append("         labelWidth: 100,");
		script.append("        	defaultType: 'textfield',");
		script.append(" 				autoScroll:true,reader: new Ext.data.JsonReader({root: 'models'},['bean.id','bean.name']),");
		script.append("         items: [ planname ,fids]");
		script.append("       });  ");
//		script.append("        roomformPanel.form.load({url:'"+this.getDetailAction()+"?bean.id='+ids,waitMsg:'Loading'});  ");
		script.append("        var saveroomModelWin = new Ext.Window({");
		script.append("        title: '驳回短信',");
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
		script.append(" 		 		        	url:'/entity/information/peSmsInfoManageAction_refuse.action?' ,");
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

		this.getGridConfig().addMenuScript(this.getText("驳回"), script.toString());
		this.getGridConfig().setPrepared(false);
		

	}
	//审核通过
	public String pass(){
		Map map=new HashMap();
		map.put("success", "false");
		map.put("info", "审核失败");
		
		boolean flag_status=true;//成功标识
		
		UserSession us=(UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		String login_id=us.getSsoUser().getLoginId();
		
		//查找审核者
		PeManager mgr=null;
		DetachedCriteria dc_m=DetachedCriteria.forClass(PeManager.class);
		dc_m.add(Restrictions.eq("loginId", login_id));
		List list_m=new LinkedList();
		try {
			list_m=this.getGeneralService().getList(dc_m);
		} catch (EntityException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		if(list_m!=null&&list_m.size()>0){
			mgr=(PeManager) list_m.get(0);
		}else{
			flag_status=false;
			map.put("info", "查找审核者失败");
		}
		//查找已审核状态
		EnumConst enu_status=null;
		DetachedCriteria dc_status=DetachedCriteria.forClass(EnumConst.class);
		enu_status=this.getMyListService().getEnumConstByNamespaceCode("SmsStatus", "1");
		
		//查找已发送状态
		EnumConst enu_send=null;
		enu_send=this.getMyListService().getEnumConstByNamespaceCode("Sendstatus", "1");
		
		String[] ids=this.getIds().split(",");
		this.getGeneralService().getGeneralDao().setEntityClass(SmsInfo.class);
		for(int i=0;i<ids.length;i++){
			SmsInfo sms=null;
			try {
				sms=(SmsInfo) this.getGeneralService().getById(ids[i]);
			} catch (EntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				flag_status=false;
				map.put("info", "查找短信失败");
			}
			sms.setEnumConstBySmsStatus(enu_status);//已审核
			sms.setPeManagerByChecker(mgr);//审核者

			if (sms.getEnumConstByType().getCode().equals("0")){//普通短信
				sms.setEnumConstBySendstatus(enu_send);//已发送
			}
//			sms.setSendDate(new Date());//发送时间
			try {
				sms=(SmsInfo) this.getGeneralService().save(sms);
			} catch (EntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//发送短信 begin
			if (sms.getEnumConstByType().getCode().equals("0")){//普通短信
				String mobile = sms.getTargets();
				String content = sms.getContent();
				SmsSendThread sendThread = new SmsSendThread(mobile,
						content);
				sendThread.start();
			}
			//发送短信 end
		}
		if(flag_status){
			map.put("success", "true");
			map.put("info", "短信发送成功");
		}
		this.setJsonString(JsonUtil.toJSONString(map));
		
		return this.json();
	}
	

	//发送短信
	public String sendMessage(String msgId ,String phone, String content)throws PlatformException {
		GlobalProperties props = new GlobalProperties();
		HashMap map = props.getSmsGWSendPrameter();
		String smsUrl = (String) map.get("url");
		String smsAccount = (String) map.get("account");
		String smsPassword = (String) map.get("password");
		try {
			content = new String(content.getBytes("UTF-8"), "ISO-8859-1");
		} catch (UnsupportedEncodingException e1) {
			
		}
		String yid = String.valueOf(new Date().getTime());

		HttpClient client = new HttpClient();
		PostMethod post = new PostMethod(smsUrl);
		NameValuePair accPara = new NameValuePair("account", smsAccount);
		NameValuePair pswPara = new NameValuePair("password", smsPassword);
		NameValuePair phonePara = new NameValuePair("phone", phone);
		NameValuePair yidPara = new NameValuePair("yid", yid);
		NameValuePair pcodePara = new NameValuePair("pcode", "");
		NameValuePair msgPara = new NameValuePair("message", content);

		post.setRequestBody(new NameValuePair[] { accPara, pswPara, phonePara,
				yidPara, pcodePara, msgPara });
		
		
		try {
			int code = client.executeMethod(post);
			
			int statusCode = post.getStatusCode();
			//判断短信是否发送成功
			OracleSmsMessage smsMessage = new OracleSmsMessage();
			smsMessage.setMsgId(msgId);
			if(statusCode==200){
				smsMessage.setSendStatus("0");   //发送成功状态
			}else{
				smsMessage.setSendStatus("1");   //发送失败状态
			}
			smsMessage.updateMsgStatus();
			
			return new Integer(statusCode).toString();
		} catch (HttpException e) {
			throw new PlatformException("Error in connect with Service Provider:" + e.toString());
		} catch (IOException e) {
			throw new PlatformException("Error in I/O:" + e.toString());
		}
	}
	
	//驳回短信发送申请
	public String refuse(){
		Map map=new HashMap();
		map.put("success", "false");
		map.put("info", "驳回失败");
		String[] ids=this.getIds().split(",");
		boolean flag_status=true;//成功标识

		UserSession us=(UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		String login_id=us.getSsoUser().getLoginId();
		//查找审核者
		PeManager mgr=null;
		DetachedCriteria dc_m=DetachedCriteria.forClass(PeManager.class);
		dc_m.add(Restrictions.eq("loginId", login_id));
		List list_m=new LinkedList();
		try {
			list_m=this.getGeneralService().getList(dc_m);
		} catch (EntityException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		if(list_m!=null&&list_m.size()>0){
			mgr=(PeManager) list_m.get(0);
		}else{
			flag_status=false;
			map.put("info", "查找审核者失败");
		}
		//查找未审核状态
		EnumConst enu_status=null;
		DetachedCriteria dc_status=DetachedCriteria.forClass(EnumConst.class);
		dc_status.add(Restrictions.eq("namespace","SmsStatus"));
		dc_status.add(Restrictions.eq("code","-1"));
		List list_status=new LinkedList();
		try {
			list_status=this.getGeneralService().getList(dc_status);
		} catch (EntityException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		if(list_status!=null&&list_status.size()>0){
			enu_status=(EnumConst) list_status.get(0);
		}else{
			flag_status=false;
			map.put("info", "查找未审核状态失败");
		}

		
		//
		this.getGeneralService().getGeneralDao().setEntityClass(SmsInfo.class);
		for(int i=0;i<ids.length;i++){
			SmsInfo sms=null;
			try {
				sms=(SmsInfo) this.getGeneralService().getById(ids[i]);
			} catch (EntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				flag_status=false;
				map.put("info", "查找短信内容失败");
			}
			sms.setEnumConstBySmsStatus(enu_status);//更改短信状态为已审核
			sms.setNote(this.getBean().getNote());//设置短信备注为驳回原因
			sms.setPeManagerByChecker(mgr);//设置发送者
			try {
				sms=(SmsInfo) this.getGeneralService().save(sms);
			} catch (EntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				flag_status=false;
				map.put("info", "保存短信被驳回状态失败");
			}
			
		}
		if(flag_status){
			map.put("success", "true");
			map.put("info", "短信驳回成功");
		}
		this.setJsonString(JsonUtil.toJSONString(map));
		return this.json();
	}
	@Override
	public void setEntityClass() {
		this.entityClass = SmsInfo.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/information/peSmsInfoManageAction";
	}
	
	//对短信的发送对象进行处理 target=phone 
	public String processPhone(String phone) {
		String[] phones = null;
		if (phone != null)
			phones = phone.split(",");
		else
			return "";
		String tmpPhone = "";
		for (int i = 0; i < phones.length; i++) {
			if (phones[i] != null
					&& !phones[i].equals("")
					&& !phones[i].equals("null")
					&& (tmpPhone.length() == 0 || (tmpPhone.length() > 0 && tmpPhone
							.indexOf("," + phones[i]) < 0)))
				tmpPhone += "," + phones[i];
		}
		if (tmpPhone.length() > 0)
			tmpPhone = tmpPhone.substring(1);
		return tmpPhone;
	}
	


}
