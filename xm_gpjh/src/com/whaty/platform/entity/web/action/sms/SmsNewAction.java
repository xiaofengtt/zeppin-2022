package com.whaty.platform.entity.web.action.sms;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeManager;
import com.whaty.platform.entity.bean.PeTrainee;
import com.whaty.platform.entity.bean.PeTrainingPlan;
import com.whaty.platform.entity.bean.PrRecPlanMajorEdutype;
import com.whaty.platform.entity.bean.PrRecPlanMajorSite;
import com.whaty.platform.entity.bean.SmsInfo;
import com.whaty.platform.entity.bean.SystemApply;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.JsonUtil;
import com.whaty.platform.entity.bean.PrTraingCourse;
import com.whaty.platform.entity.bean.PeFeeDetail;
import com.whaty.platform.entity.bean.EnumConst;

public class SmsNewAction extends MyBaseAction {
	
	private List trainingType;
	private List smsType;
	private List managers;
	private boolean succ;//是否操作成功
	
	public String index(){
		//查找三个培训类别
		this.getGeneralService().getGeneralDao().setEntityClass(EnumConst.class);
		DetachedCriteria dc=DetachedCriteria.forClass(EnumConst.class);
		dc.add(Restrictions.eq("namespace", "TrainingType"));
		List list_trainingType=new LinkedList();
		try {
			list_trainingType=this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setTrainingType(list_trainingType);
		
		//查找短信类别，不包括系统短信
		DetachedCriteria dc1=DetachedCriteria.forClass(EnumConst.class);
		dc1.add(Restrictions.eq("namespace", "Type"));
		dc1.add(Restrictions.ne("code", "2"));
		dc1.addOrder(Order.asc("code"));//按code升序排列
		List list_smsType=new LinkedList();
		try {
			list_smsType=this.getGeneralService().getList(dc1);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setSmsType(list_smsType);
		return "newSms";
	}
	public String manager_list(){
		DetachedCriteria dc=DetachedCriteria.forClass(PeManager.class);
		dc.createCriteria("enumConstByGender","enumConstByGender");
		dc.createCriteria("enumConstByFlagIsvalid","enumConstByFlagIsvalid");
		dc.add(Restrictions.eq("enumConstByFlagIsvalid.code", "1"));
		List list_m=new LinkedList();
		try {
			list_m=this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setManagers(list_m);
		return "manager_list";
	}

	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc =DetachedCriteria.forClass(PeManager.class);
		dc.createCriteria("enumConstByGender","enumConstByGender");
		dc.createCriteria("enumConstByFlagIsvalid","enumConstByFlagIsvalid");
		dc.add(Restrictions.eq("enumConstByFlagIsvalid.code", "1"));
		return dc;
	}

	@Override
	public void initGrid() {
		this.getGridConfig().setTitle(this.getText("添加管理员"));
        this.getGridConfig().setCapability(false, false, false,true,false);
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("登录ID"),"loginId");
		this.getGridConfig().addColumn(this.getText("性别"),"enumConstByGender.name");
		this.getGridConfig().addColumn(this.getText("真实姓名"),"trueName");
		this.getGridConfig().addColumn(this.getText("手机"),"mobilePhone");
		this.getGridConfig().addColumn(this.getText("电话"),"phone");
		this.getGridConfig().addColumn(this.getText("身份证号"),"idCard");
		this.getGridConfig().addColumn(this.getText("电子邮件"),"email");
		this.getGridConfig().addColumn(this.getText("毕业院校及专业层次"),"graduationInfo");
		this.getGridConfig().addColumn(this.getText("毕业时间"),"graduationDate");
		this.getGridConfig().addColumn(this.getText("地址"),"address");
		this.getGridConfig().addColumn(this.getText("职称"),"zhiCheng");
		this.getGridConfig().addColumn(this.getText("工作时间"),"workTime");


		StringBuilder script = new StringBuilder();
		script.append(" {");
		script.append(" 	 var m = grid.getSelections();");
		script.append("    	 var ids = '';");
		script.append("    	 		for(var i=0,len =m.length;i<len;i++ ){");
		script.append("    	 			var ss = m[i].get('mobilePhone')+'|'+m[i].get('trueName');");
		script.append("    	 			if(i==0)ids = ids+ ss ;");
		script.append("    	 			else ids = ids +','+ss;");
		script.append("    	 		}");
		script.append("  var openerObj = opener.sms.mobilePhone;                           ");
		script.append("  var mvalues = opener.sms.mvalues;                           ");
		script.append("  if(openerObj.value == ''){                           ");
		script.append("      openerObj.value += ids;                       ");
		script.append("      mvalues.value += ids;                       ");
		script.append("  }else{                           ");
		script.append("       openerObj.value += ',' + ids;                      ");
		script.append("       mvalues.value += ',' + ids;                      ");
		script.append("  } ");
		script.append("  Ext.MessageBox.alert('提示', '添加成功' ); ");
		script.append("  handler: function(){window.close(); } ");
		script.append("  } ");
		

		this.getGridConfig().addMenuScript(this.getText("添加选中"), script.toString());
		this.getGridConfig().setPrepared(false);
		
	}
	private String mobilePhone;//发送对象
//	private List<String> trainingType;//已经存在
	private List<String> sm_id;
	private String msgContent;//发送内容
	private String type;//发送类型，普通短信 定时短信
	private String setTime;//定时
	private String isorName;//是否显示姓名 1 是 0 否
	
	//将要发送的内容进行处理并存进数据库等待审核  在短信发送页面被调用
	public String sms_send(){
		boolean flag_status=true;//成功标识
		String mobilePhone =this.getMobilePhone();// request.getParameter("mobilePhone");
		List<String> trainingType = this.getTrainingType();//request.getParameterValues("trainingType");//培训级别，初 中 高
		List<String> sm_id = this.getSm_id();//request.getParameterValues("sm_id");//?
		//String[] major_id = request.getParameterValues("major_id");
		//String[] grade_id = request.getParameterValues("grade_id");
		//String[] level_id =request.getParameterValues("level_id");
		
		
		String msgContent =this.getMsgContent();// request.getParameter("msgContent");//内容
		String type =this.getType();// request.getParameter("type");//发送类型，普通短信 定时短信
		String setTime =this.getSetTime();// request.getParameter("setTime");//定时
		String flag = this.getIsorName();// request.getParameter("isorName");//是否显示姓名 1 是 0 否
		if(flag!=null && flag.equals("0")){//如果不需要显示姓名，则去掉发送对象中的姓名
			String[] vals = mobilePhone.split(",");
			String tmp="";
			for(int i=0;i<vals.length;i++){
			    String[] vs=vals[i].split("\\|");
			    tmp+=vs[0]+",";
			}
			if(null!=tmp && tmp.length()>0){
			    tmp = tmp.substring(0,tmp.length()-1);	  
			}
			mobilePhone = tmp;
		}
		if(setTime!=null&&!setTime.equals("")){
		 	setTime = setTime.substring(0,16); 
		}
		
		String trainingTypeIds = "";//培训计划类型的ID字符串，以','隔开
		if(null != trainingType && trainingType.size() > 0){
			for(int i=0; i<trainingType.size(); i++){
				if(null != trainingType.get(i) && !"".equals(trainingType.get(i))){
					trainingTypeIds += "'"+trainingType.get(i)+"',";
				}
			}
		}
		
		//当培训计划类型不为空时进行查找
		if(trainingTypeIds.length() > 0 ){ 
		    StringBuffer sql = new StringBuffer();
		    sql.append(" select pt.true_name name,pt.mobile mobilephone	");	
			sql.append("     from pe_trainee pt      ");
			sql.append("      where pt.training_type in ("+trainingTypeIds.substring(0,trainingTypeIds.length()-1)+")  ");
			sql.append("      and pt.mobile is not null and length(pt.mobile) >0 ");
		    
			List list_phone=new LinkedList();
			try {
				list_phone=this.getGeneralService().getBySQL(sql.toString());
			} catch (EntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				flag_status=false;
				if(this.getMsg()!=null){
					this.setMsg(this.getMsg()+","+"查找学生的手机号码出现错误");
				}else{
					this.setMsg("查找学生的手机号码出现错误");
				}
			}
		    //rs.getString();
		    if(null != flag && flag.equals("0")){//如果不显示姓名
		    	if(mobilePhone.length() > 0){
				    
				    for(int i=0;i<list_phone.size();i++){
				    	Object[] objs=(Object[]) list_phone.get(i);
				    	String phone=objs[1]==null?"":(String) objs[1];
				    	mobilePhone += "," + phone;
				    }
				    
			    } else {
				    for(int i=0;i<list_phone.size();i++){
				    	Object[] objs=(Object[]) list_phone.get(i);
				    	String phone=objs[1]==null?"":(String) objs[1];
				    	mobilePhone +=  phone + ",";
				    }
				    mobilePhone.substring(0,mobilePhone.length() - 1);
			    }
		    } else {//如果显示姓名
		    	 if(mobilePhone.length() > 0){
				     for(int i=0;i<list_phone.size();i++){
				    	Object[] objs=(Object[]) list_phone.get(i);
				    	String phone=objs[1]==null?"":(String) objs[1];
				    	String name=objs[0]==null?"":(String) objs[0];
				        mobilePhone += "," + phone+"|"+name;
					 } 
				 } else {
				     for(int i=0;i<list_phone.size();i++){
					    	Object[] objs=(Object[]) list_phone.get(i);
					    	String phone=objs[1]==null?"":(String) objs[1];
					    	String name=objs[0]==null?"":(String) objs[0];
					        mobilePhone += phone+"|"+name +",";
					 } 
					  mobilePhone.substring(0,mobilePhone.length() - 1);
				 }
		    }
	    }
	   
		//查找发送者，即当前用户
		PeManager manager=null;
		UserSession us=(UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		String loginId=us.getSsoUser().getLoginId();
		DetachedCriteria dc=DetachedCriteria.forClass(PeManager.class);
		dc.add(Restrictions.eq("loginId",loginId ));
		List list_m=new LinkedList();
		try {
			list_m=this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(list_m!=null&&list_m.size()>0){
			manager=(PeManager) list_m.get(0);
		}else{
			flag_status=false;
			if(this.getMsg()!=null){
				this.setMsg(this.getMsg()+","+"查找发送者出现错误");
			}else{
				this.setMsg("查找发送者出现错误");
			}
		}
		//查找短信类型 普通短信还是定时短信
		EnumConst enu_type=null;
		this.getGeneralService().getGeneralDao().setEntityClass(EnumConst.class);
		try {
			enu_type=(EnumConst) this.getGeneralService().getById(type);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			flag_status=false;
			if(this.getMsg()!=null){
				this.setMsg(this.getMsg()+","+"查找短信类型出现错误");
			}else{
				this.setMsg("查找短信类型出现错误");
			}
		}
		
		//查找发送状态 未发送
		EnumConst enu_send=null;
		enu_send=this.getMyListService().getEnumConstByNamespaceCode("Sendstatus", "0");//未发送
		
		//查找短信状态 未审核
		EnumConst enu_status=null;
		enu_status=this.getMyListService().getEnumConstByNamespaceCode("SmsStatus", "0");//未审核
		

		int count = 0; // 发送对象数 --sendObjNum
		String[] args = { "" };
		int bytes_num = msgContent.getBytes().length / 100;
		int itemnum = msgContent.getBytes().length % 100 == 0 ? bytes_num
				: bytes_num + 1; // 拆分短信条数--splititem

		String targets=mobilePhone;
		if (targets.indexOf(",") >= 0) {
			args = targets.split(",");
		} else if (targets.indexOf("'") >= 0) {
			args = targets.split("'");
		}
		for (int i = 0; i < args.length; i++) {
			if (args[i] != null && !args[i].equals("")) {
				count++;
			} else if (args.length == 1) {
				count = 1;
				break;
			}
		}
		
		SmsInfo sms=new SmsInfo();
		sms.setContent(msgContent);
		sms.setEnumConstByScope(null);
		sms.setEnumConstBySendstatus(enu_send);
		sms.setEnumConstBySmsStatus(enu_status);
		sms.setEnumConstByType(enu_type);
		if(enu_type.getCode().equals("1")){//定时短信
			sms.setSettime(setTime);
		}else{
			sms.setSettime("");
		}
		sms.setNote("");
		sms.setPeManagerByChecker(null);//未审核
		sms.setPeManagerBySender(manager);
		sms.setSendDate(new Date());
		sms.setSendobjnum((long)count);
		sms.setSite(null);
		sms.setSplititem((long)itemnum);
		sms.setTargets(mobilePhone);
		sms.setTeaId(null);
		
		this.getGeneralService().getGeneralDao().setEntityClass(SmsInfo.class);
		try {
			sms=(SmsInfo) this.getGeneralService().save(sms);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			flag_status=false;
			if(this.getMsg()!=null){
				this.setMsg(this.getMsg()+","+"保存短信时出现错误");
			}else{
				this.setMsg("保存短信时出现错误");
			}
		}
		
		this.setSucc(flag_status);
		
		return "sendResult";
	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		this.servletPath = "/entity/information/smsNewAction";
		
	}

	public List getTrainingType() {
		return trainingType;
	}

	public void setTrainingType(List trainingType) {
		this.trainingType = trainingType;
	}

	public List getSmsType() {
		return smsType;
	}

	public void setSmsType(List smsType) {
		this.smsType = smsType;
	}
	public List getManagers() {
		return managers;
	}
	public void setManagers(List managers) {
		this.managers = managers;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public List<String> getSm_id() {
		return sm_id;
	}
	public void setSm_id(List<String> sm_id) {
		this.sm_id = sm_id;
	}
	public String getMsgContent() {
		return msgContent;
	}
	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSetTime() {
		return setTime;
	}
	public void setSetTime(String setTime) {
		this.setTime = setTime;
	}
	public String getIsorName() {
		return isorName;
	}
	public void setIsorName(String isorName) {
		this.isorName = isorName;
	}
	public boolean isSucc() {
		return succ;
	}
	public void setSucc(boolean succ) {
		this.succ = succ;
	}
	




}
