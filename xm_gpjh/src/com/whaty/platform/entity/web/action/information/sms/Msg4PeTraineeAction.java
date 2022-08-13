package com.whaty.platform.entity.web.action.information.sms;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.InternetAddress;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeEmailHistory;
import com.whaty.platform.entity.bean.PeNoteHistory;
import com.whaty.platform.entity.bean.PeTrainee;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.EmailSendUtil;
import com.whaty.platform.entity.util.SmsSendUtil;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;
import com.whaty.util.JsonUtil;

/**
 * 学员短信 邮件管理
 * 
 * @author houxuelong 2010-09-20
 *
 */
public class Msg4PeTraineeAction extends MyBaseAction {
	
	private String itemNum = "0";//附件的个数
	private String content;//短信以及邮件内容
	private String theme;//邮件主题
	private String savePath;
	
	private File upload1;
	private File upload2;
	private File upload3;
	private File upload4;
	private File upload5;
	
	private String upload1FileName;
	private String upload2FileName;
	private String upload3FileName;
	private String upload4FileName;
	private String upload5FileName;
	
	@Override
	public void initGrid() {
		this.getGridConfig().setTitle(this.getText("学员短信与邮件管理"));
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("姓名"), "name");
		this.getGridConfig().addColumn(this.getText("性别"), "enumConstByFkGender.name",true,true,true,"TextField",true,10,"");
		this.getGridConfig().addColumn(this.getText("年龄"), "age", true, true, true, "", true, 4);
		this.getGridConfig().addColumn(this.getText("民族"), "folk", true, true, true, "", true, 20);
		this.getGridConfig().addColumn(this.getText("单位"), "workPlace",true, true, true, "TextField", true, 50, null);
		this.getGridConfig().addColumn(this.getText("学历"), "education", true, true, true, "", true, 4);
		this.getGridConfig().addColumn(this.getText("职称"), "zhicheng", true, true, true, "", true, 4);
		this.getGridConfig().addColumn(this.getText("手机"), "telephone", true, true, true, "TextField", true, 20);
		this.getGridConfig().addColumn(this.getText("电子邮箱"), "email", true,true, true, "TextField", true, 50, Const.email_for_extjs);
		this.getGridConfig().addColumn(this.getText("培训项目"), "peProApplyno.name",true,true,true,"TextField",true,50);
//		this.getGridConfig().addColumn(this.getText("办公电话"), "officePhone", true, true, true, "", true, 20);
		this.getGridConfig().addColumn(this.getText("参训状态"), "enumConstByFkStatusTraining.name", true, true, true, "TextField", true, 20);
		this.getGridConfig().addColumn(this.getText("学科"), "peSubject.name", true, true, true, "TextField", true, 255);
		this.getGridConfig().addColumn(this.getText("省份"), "peProvince.name", true, true, true, "TextField", true, 20);
		this.getGridConfig().addColumn(this.getText("是否结业"), "enumConstByFkGraduted.name", true, true, true, "TextField", true, 20);
		this.getGridConfig().addColumn(this.getText("是否通过审核"), "enumConstByFkCheckedTrainee.name", true, true, true, "TextField", true, 20);
		this.getGridConfig().addColumn(this.getText("培训单位"), "peUnitByFkTrainingUnit.name");
		this.getGridConfig().setCapability(false, false, false);
		initMsgGridConfig();
		initMailGridConfig();
	}
	public void initMsgGridConfig(){
		String noteArea = "var noteArea = new Ext.form.TextArea({fieldLabel:'发送内容*'," +
		"name:'content'," +
		"allowBlank:false," +
		"width:300,height:160 });";
		StringBuilder script1 = new StringBuilder();
		script1.append(" {");
		script1.append(" 	 var m = grid.getSelections();");
		script1.append("    	 var ids = '';");
		script1.append("    	 if(m.length > 0){");
		script1.append("    	 		for(var i=0,len =m.length;i<len;i++ ){");
		script1.append("    	 			var ss = m[i].get('id');");
		script1.append("    	 			if(i==0)ids = ids+ ss ;");
		script1.append("    	 			else ids = ids +','+ss;");
		script1.append("    	 		}");
		script1.append("    	 }else{");
		script1.append("    	 	Ext.MessageBox.alert('错误','请至少选择一条记录'); return;");
		script1.append("    	 }");
		script1.append(noteArea);
		script1.append("        var fids = new Ext.form.Hidden({name:'ids',value:ids});");
//		script1.append("        var flag = new Ext.form.Hidden({name:'flagString',value:'content'});");//.append(flagString).append("'});");		//自己加的隐藏域
		script1.append("    	 var formPanel = new Ext.form.FormPanel({");
		script1.append(" 	    frame:true,");
		script1.append("         labelWidth: 70,");
		script1.append("        	defaultType: 'textarea',");
		script1.append(" 				autoScroll:true,");
		script1.append("         items: [ noteArea ,fids]");
		script1.append("       });");
		script1.append("        var addModelWin = new Ext.Window({");
		script1.append("        title: '发送短信',");
		script1.append("        width: 450,");
		script1.append("        height: 280,");
		script1.append("        minWidth: 300,");
		script1.append("        minHeight: 250,");
		script1.append("        layout: 'fit',");
		script1.append("        plain:true,");
		script1.append("        bodyStyle:'padding:5px;',");
		script1.append("        buttonAlign:'center',");
		script1.append("        items: formPanel,");
		script1.append("        buttons: [{");
		script1.append(" 	            text: '发送',");
		script1.append(" 	            handler: function() {");
		script1.append(" 	                if (formPanel.form.isValid()) {");
		script1.append(" 		 		        formPanel.form.submit({");
		script1.append(" 		 		        	url:'"+this.getServletPath()+"_sendMessage.action?' ,");
		script1.append(" 				            waitMsg:'发送中，请稍候...',");
		script1.append(" 							success: function(form, action) {");
		script1.append(" 							    var responseArray = action.result;");
		script1.append(" 							    if(responseArray.success=='true'){");
		script1.append(" 							    	Ext.MessageBox.alert('提示', responseArray.info);");
		script1.append(" 							    	store.load({params:{start:g_start,limit:g_limit"+this.getGridConfig().getFieldsFilter()+"}});                      ");//+this.getGridConfig().getFieldsFilter()+"
		script1.append(" 								    addModelWin.close();");
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
		script1.append(" 	            handler: function(){addModelWin.close();}");
		script1.append(" 	        }]");
		script1.append("        });");
		script1.append("        addModelWin.show();");
		script1.append("  }");
		
		this.getGridConfig().addMenuScript(this.getText("发送短信"), script1.toString());
		this.getGridConfig().setPrepared(false);
	}
	
	public void initMailGridConfig(){
		this.getGridConfig().addMenuScript(this.getText("发送邮件"), 
				"{var m = grid.getSelections();  "+
					"if(m.length > 0){	         "+
					"Ext.MessageBox.confirm(\"请确认\",\"是否真的要给指定的目标发送邮件?\",function(button,text){ " +
					" if(button=='yes'){  "+
					"	var jsonData = '';       "+
					"	for(var i = 0, len = m.length; i < len; i++){"+
					"		var ss =  m[i].get('id');"+
					"		if(i==0)	jsonData = jsonData + ss ;"+
					"		else	jsonData = jsonData + ',' + ss;"+
					"	}                        " +
//					"   window.open('"+this.getServletPath()+"_toSendMailPage.action?ids='+jsonData);"+
					"	document.getElementById('user-defined-content').style.display='none'; "+
					"	document.getElementById('user-defined-content').innerHTML=\"<form target='_self' action='"+this.getServletPath()+"_toSendMailPage.action' method='post' name='formx1' style='display:none'>" +
					"   <input name='ids' type='hidden' value='\"+jsonData+\"' />"+
					"   </form>\";"+
					"	document.formx1.submit();"+
					"	document.getElementById('user-defined-content').innerHTML=\"\";  "+
					" }});"+
					"} else {                    "+ 
					"Ext.MessageBox.alert('" + this.getText("test.error") + "', '" + this.getText("test.pleaseSelectAtLeastOneItem") + "');  "  +                    
		"}}                         ");
	}
	
	@Override
	public void setEntityClass() {

	}
	public String toSendMailPage(){
		String peTraineeIds[] = this.getIds().split(",");
		DetachedCriteria dc = DetachedCriteria.forClass(PeTrainee.class);
		dc.add(Restrictions.in("id", peTraineeIds));
		dc.add(Restrictions.isNotNull("email"));
		dc.setProjection(Projections.projectionList()
				.add(Property.forName("email"))
				.add(Property.forName("id")));
		try {
			List list = this.getGeneralService().getList(dc);
			if(list==null||list.isEmpty()){
				this.setMsg("您所选的学员没有邮箱信息！");
				this.setTogo("back");
				return "msg";
			}
			ActionContext.getContext().getSession().put("targetList", list);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "sendMailPage";
	}
	
	@Override
	public void setServletPath() {
		this.servletPath = "/entity/information/msg4PeTrainee";
	}
	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeTrainee.class);
		dc.createAlias("enumConstByFkGender", "enumConstByFkGender",DetachedCriteria.LEFT_JOIN);
		dc.createAlias("peProApplyno", "peProApplyno",DetachedCriteria.LEFT_JOIN);
		dc.createAlias("enumConstByFkStatusTraining", "enumConstByFkStatusTraining",DetachedCriteria.LEFT_JOIN);
		dc.createAlias("peSubject", "peSubject",DetachedCriteria.LEFT_JOIN);
		dc.createAlias("peProvince", "peProvince",DetachedCriteria.LEFT_JOIN);
		dc.createAlias("enumConstByFkGraduted", "enumConstByFkGraduted",DetachedCriteria.LEFT_JOIN);
		dc.createAlias("enumConstByFkCheckedTrainee", "enumConstByFkCheckedTrainee",DetachedCriteria.LEFT_JOIN);
		dc.createAlias("peUnitByFkTrainingUnit", "peUnitByFkTrainingUnit",DetachedCriteria.LEFT_JOIN);
		dc.add(Restrictions.isNotNull("peUnitByFkTrainingUnit"));	//只显示分配培训单位的学员
		
		return dc;
	}
	// 发送短信
	public String sendMessage() {
		Map<String,String> map=new HashMap<String,String>();
		map.put("success", "false");
		map.put("info", "发送失败");

		String[] sendNoteIds = getIds().split(",");
		List<String> phoneList = null;
		StringBuffer phones = new StringBuffer();
		for(int i=0;i<sendNoteIds.length;i++) {
			if(i!=sendNoteIds.length-1){
				phones.append("'"+sendNoteIds[i]+"',");
			}else{
				phones.append("'"+sendNoteIds[i]+"'");
			}
		}
		phoneList = getTelephoneByIdSQL(phones.toString());
		if(phoneList==null||phoneList.isEmpty()){
			map.put("success", "false");
			map.put("info", "您选择的目标没有手机号！");
			this.setJsonString(JsonUtil.toJSONString(map));
			return json();
		}
		SmsSendUtil smsSend = new SmsSendUtil();
		if(smsSend.canSendCount()<phoneList.size()){
			map.put("info", "您的余额只剩:"+smsSend.canSendCount()+"条，不足以发送:"+phoneList.size()+"个用户!");
			this.setJsonString(JsonUtil.toJSONString(map));
			return json();
		}
		if(smsSend.sendMsg(phoneList, getContent())){//(queryInfoMap.get(nameString), getContent());
			//将短信发送记录存入数据库
			saveNoteHistory(sendNoteIds, getContent());
			map.put("success", "true");
			map.put("info", "发送成功，您的余额还剩："+(smsSend.canSendCount()-phoneList.size())+"条！");
			this.setJsonString(JsonUtil.toJSONString(map));
			return json();
		}else{
			map.put("success", "false");
			map.put("info", "发送失败，请检查短信网关配置是否正确！");
			this.setJsonString(JsonUtil.toJSONString(map));
			return json();
		}
	}
	/**
	 * @description 查询联系人或评审专家的名字和手机号
	 * @param id 联系人或评审专家的id号
	 * @return map 由联系人或评审专家的名字和手机组成的map集合
	 */
	protected List<String> getTelephoneByIdSQL(String ids) {
		String getTelephoneSQL = "select telephone from pe_trainee where id in ("+ids+")";
		List resultList = null;
		try {
			resultList = getGeneralService().getBySQL(getTelephoneSQL);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		resultList.remove(null);
		return resultList;
	}
	
	protected void saveNoteHistory(String[] receiverIds, String content) {
		UserSession session = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		List<String> managerList = null;
		try {
			managerList = this.getGeneralService().getBySQL("SELECT T.ID FROM PE_MANAGER T WHERE T.FK_SSO_USER_ID='"+session.getSsoUser().getId()+"'");
		} catch (EntityException e1) {
			e1.printStackTrace();
		}
		
		getGeneralService().getGeneralDao().setEntityClass(PeNoteHistory.class);
		for(int i=0;i<receiverIds.length;i++) {
			PeNoteHistory noteHistory=new PeNoteHistory();
			noteHistory.setContent(content);
			noteHistory.setSendTime(new Date());
			noteHistory.setSenderId(managerList.isEmpty()?"unknown":managerList.get(0));
			noteHistory.setReceiverId(receiverIds[i]);
			try {
				getGeneralService().save(noteHistory);
			} catch (EntityException e) {
 				e.printStackTrace();
			}
		}
	}
	
	protected void saveEmailHistory(List<String> receiverIds,List<String> emails ,String title,String content,String attachments) {
		UserSession session = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		List<String> managerList = null;
		try {
			managerList = this.getGeneralService().getBySQL("SELECT T.ID FROM PE_MANAGER T WHERE T.FK_SSO_USER_ID='"+session.getSsoUser().getId()+"'");
		} catch (EntityException e1) {
			e1.printStackTrace();
		}
		
		getGeneralService().getGeneralDao().setEntityClass(PeEmailHistory.class);
		for(int i=0;i<receiverIds.size();i++) {
			PeEmailHistory emailHistory=new PeEmailHistory();
			emailHistory.setTitle(title);
			emailHistory.setContent(content);
			emailHistory.setAttachments(attachments);
			emailHistory.setSendTime(new Date());
			emailHistory.setSenderId(managerList.isEmpty()?"unknown":managerList.get(0));
			emailHistory.setReceiverId(receiverIds.get(i));
			emailHistory.setReceiverEmail(emails.get(i));
			try {
				getGeneralService().save(emailHistory);
			} catch (EntityException e) {
 				e.printStackTrace();
			}
		}
	}
	
	public String sendEmail(){
		List<Object[]> targetList = (List<Object[]>)ActionContext.getContext().getSession().get("targetList");
		int count = Integer.parseInt(this.getItemNum());
		String files[] = new String[count];
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String date = sdf.format(new Date());
		String attachments = "";
		String folder = this.getSavePath() +date + "/";
		File emailFloder = new File( ServletActionContext.getServletContext().getRealPath(folder) );
		if (!emailFloder.exists()) {
			emailFloder.mkdirs();
		}
		File photoLink = null;
		//先将附件上传到服务器
		try {
			if(this.getUpload1()!=null){
				photoLink = new File(emailFloder,this.getUpload1FileName());
				attachments += folder+this.getUpload1FileName()+";";
				FileOutputStream fos = new FileOutputStream(photoLink);
				FileInputStream fis = new FileInputStream(this.getUpload1());
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = fis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}
				fis.close();
				fos.close();
				files[0]= photoLink.getPath() ;
			}
			if(this.getUpload2()!=null){
				photoLink = new File(emailFloder,this.getUpload2FileName());
				attachments += folder+this.getUpload2FileName()+";";
				FileOutputStream fos = new FileOutputStream(photoLink);
				FileInputStream fis = new FileInputStream(this.getUpload2());
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = fis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}
				fis.close();
				fos.close();
				files[1]= photoLink.getPath() ;
			}
			if(this.getUpload3()!=null){
				photoLink = new File(emailFloder,this.getUpload3FileName());
				attachments += folder+this.getUpload3FileName()+";";
				FileOutputStream fos = new FileOutputStream(photoLink);
				FileInputStream fis = new FileInputStream(this.getUpload3());
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = fis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}
				fis.close();
				fos.close();
				files[2]= photoLink.getPath() ;
			}
			if(this.getUpload4()!=null){
				photoLink = new File(emailFloder,this.getUpload4FileName());
				attachments += folder+this.getUpload4FileName()+";";
				FileOutputStream fos = new FileOutputStream(photoLink);
				FileInputStream fis = new FileInputStream(this.getUpload4());
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = fis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}
				fis.close();
				fos.close();
				files[3]= photoLink.getPath() ;
			}
			if(this.getUpload5()!=null){
				photoLink = new File(emailFloder,this.getUpload5FileName());
				attachments += folder+this.getUpload5FileName()+";";
				FileOutputStream fos = new FileOutputStream(photoLink);
				FileInputStream fis = new FileInputStream(this.getUpload5());
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = fis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}
				fis.close();
				fos.close();
				files[4]= photoLink.getPath() ;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<InternetAddress> target = new ArrayList<InternetAddress>();
		List<String> emails = new ArrayList<String>();
		List<String> receivers = new ArrayList<String>();
		for(Object[] infos:targetList){
			InternetAddress ia = new InternetAddress();
			ia.setAddress(infos[0].toString());
			target.add(ia);
			emails.add(infos[0].toString());
			receivers.add(infos[1].toString());
		}
		//发送邮件
		if (EmailSendUtil.sendMail(target,this.getTheme(),this.getContent(),files) > 0) {
			saveEmailHistory(receivers,emails,this.getTheme(),this.getContent(),attachments);
		}
		
		this.setMsg("邮件发送成功！");
		this.setTogo("back");
		return "msg";
	}
	
	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}
	
	public String getItemNum() {
		return itemNum;
	}
	public void setItemNum(String itemNum) {
		this.itemNum = itemNum;
	}
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}
	public File getUpload1() {
		return upload1;
	}
	public void setUpload1(File upload1) {
		this.upload1 = upload1;
	}
	public File getUpload2() {
		return upload2;
	}
	public void setUpload2(File upload2) {
		this.upload2 = upload2;
	}
	public File getUpload3() {
		return upload3;
	}
	public void setUpload3(File upload3) {
		this.upload3 = upload3;
	}
	public File getUpload4() {
		return upload4;
	}
	public void setUpload4(File upload4) {
		this.upload4 = upload4;
	}
	public File getUpload5() {
		return upload5;
	}
	public void setUpload5(File upload5) {
		this.upload5 = upload5;
	}
	public String getUpload1FileName() {
		return upload1FileName;
	}
	public void setUpload1FileName(String upload1FileName) {
		this.upload1FileName = upload1FileName;
	}
	public String getUpload2FileName() {
		return upload2FileName;
	}
	public void setUpload2FileName(String upload2FileName) {
		this.upload2FileName = upload2FileName;
	}
	public String getUpload3FileName() {
		return upload3FileName;
	}
	public void setUpload3FileName(String upload3FileName) {
		this.upload3FileName = upload3FileName;
	}
	public String getUpload4FileName() {
		return upload4FileName;
	}
	public void setUpload4FileName(String upload4FileName) {
		this.upload4FileName = upload4FileName;
	}
	public String getUpload5FileName() {
		return upload5FileName;
	}
	public void setUpload5FileName(String upload5FileName) {
		this.upload5FileName = upload5FileName;
	}
	public String getSavePath() {
		return savePath;
	}
	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}
}
