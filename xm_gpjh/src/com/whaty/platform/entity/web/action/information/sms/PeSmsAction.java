package com.whaty.platform.entity.web.action.information.sms;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONString;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeExamPatrol;
import com.whaty.platform.entity.bean.PeSite;
import com.whaty.platform.entity.bean.PeSmsInfo;
import com.whaty.platform.entity.bean.PeSmsSystempoint;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.sms.PeSmsInfoService;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;
import com.whaty.platform.util.JSONStringObject;
import com.whaty.platform.util.JsonUtil;

public class PeSmsAction extends MyBaseAction<PeSmsInfo>{
	
	String mobilePhone;
	String type;
	String msgContent;
	Date setTime;
	PeSmsInfoService peSmsInfoService;
	
	//跳转信息
	String msg ;
	String togo ;
	
	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, true, true);
		this.getGridConfig().setTitle(this.getText("短信列表"));

		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("短信内容"), "note", true, true, true, "TextArea", false, 200);
		this.getGridConfig().addColumn(this.getText("短信类型"), "enumConstByFlagSmsType.name", true, false, true, null);
		this.getGridConfig().addColumn(this.getText("发送时间"), "bookingDate",true, false, true, null);
//		this.getGridConfig().addColumn(this.getText("发送站点"), "peSite.name",true, false, true, null);
		this.getGridConfig().addColumn(this.getText("发送站点"), "peSite.name",
				true, false, false, "TextField", false, 0);
		this.getGridConfig().addRenderScript(this.getText("发送站点"),
				"{if (${value}=='') return '总站'; else return ${value};}",
				"peSite.name");
		this.getGridConfig().addColumn(this.getText("发送人"), "senderLoginInId",true, false, true, null);
		this.getGridConfig().addColumn(this.getText("审核状态"), "enumConstByFlagSmsStatus.name",true, false, true, null);
		this.getGridConfig().addColumn(this.getText("驳回原因"), "returnReason",true, false, true, null);
		this.getGridConfig().addRenderFunction("发送对象", "<a href=/entity/information/prSmsSendStatus.action?sms_id=${value} target=_self>查看详细信息</a>", "id");
		
		this.getGridConfig().addMenuFunction("重发", "/entity/information/peSms_resend.action", false , true);
		
	}
	
	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeSmsInfo.class);
		dc.createAlias("peSite", "peSite",DetachedCriteria.LEFT_JOIN);
		dc.createAlias("enumConstByFlagSmsType", "enumConstByFlagSmsType");
		dc.createAlias("enumConstByFlagSmsStatus", "enumConstByFlagSmsStatus");
		//dc.add(Restrictions.eq("enumConstByFlagSmsStatus.code","1"));
		return dc;
	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
		this.entityClass = PeSmsInfo.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath="/entity/information/peSms";
	}
	
	public void setBean(PeSmsInfo instance){
		this.superSetBean(instance);
	}
	
	public PeSmsInfo getBean(){
		return (PeSmsInfo)this.superGetBean();
	}
	/**
	 * 保存短信和手机号
	 * @return
	 */
	public String save(){
		//获得登陆人信息
		UserSession user_session = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		SsoUser ssouser = user_session.getSsoUser() ;

		//创建短息对象
		PeSmsInfo peSmsInfo = new PeSmsInfo();
		peSmsInfo.setCreatDate(new Date());
		peSmsInfo.setNote(this.getMsgContent());
		//将定时短信的秒设为0
		if(this.getSetTime()!=null){
			this.getSetTime().setSeconds(0);
		}
		peSmsInfo.setBookingDate(this.getSetTime());
		peSmsInfo.setSenderLoginInId(user_session.getLoginId()) ;
//		peSmsInfo.setPeSite()	如何获得站点信息
		peSmsInfo.setSenderName(user_session.getUserName()); //发信人
		
		String[] mobilePhones = mobilePhone.split(",");
		
		try {
			this.getPeSmsInfoService().save(peSmsInfo, mobilePhones, type,ssouser);
			this.setMsg("短信创建成功，转到审核列表！");
			this.setTogo("/entity/information/peSmsCheck.action");
		} catch (EntityException e) {
			e.printStackTrace();
			this.setMsg("短信创建失败！");
			this.setTogo("back");
		}
		return "msg";
		
	}
	/**
	 * 重发短信
	 * @return
	 */
	public String resend(){
		Map map = new HashMap();
		try{
			this.getPeSmsInfoService().update_resend(this.getIds());
			map.put("success", true);
			map.put("info", "短信状态修改成功，请重新审核发送！");
		}catch(Exception e){
			map.put("success", false);
			map.put("info", "短信状态修改失败！");
			e.printStackTrace();
		}
		this.setJsonString(JsonUtil.toJSONString(map));
		return json();
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getMsgContent() {
		return msgContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}

	public Date getSetTime() {
		return setTime;
	}

	public void setSetTime(Date setTime) {
		this.setTime = setTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public PeSmsInfoService getPeSmsInfoService() {
		return peSmsInfoService;
	}

	public void setPeSmsInfoService(PeSmsInfoService peSmsInfoService) {
		this.peSmsInfoService = peSmsInfoService;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getTogo() {
		return togo;
	}

	public void setTogo(String togo) {
		this.togo = togo;
	}
	
	
}
