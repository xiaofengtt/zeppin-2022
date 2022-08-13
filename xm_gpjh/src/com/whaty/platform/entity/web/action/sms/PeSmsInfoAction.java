package com.whaty.platform.entity.web.action.sms;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
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

public class PeSmsInfoAction extends MyBaseAction {

	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(SmsInfo.class);
//		dc.createCriteria("enumConstByScope","enumConstByScope",DetachedCriteria.LEFT_JOIN);//发送范围
		dc.createCriteria("enumConstBySmsStatus", "enumConstBySmsStatus",DetachedCriteria.LEFT_JOIN);//状态，是否审核过 1 已审核 -1 已驳回
		dc.createCriteria("peManagerBySender", "peManagerBySender",DetachedCriteria.LEFT_JOIN);//发送人姓名
		dc.createCriteria("enumConstBySendstatus", "enumConstBySendstatus",DetachedCriteria.LEFT_JOIN);//是否已发送 0 未发送 1已发送
		dc.createCriteria("peManagerByChecker", "peManagerByChecker",DetachedCriteria.LEFT_JOIN);//审核人
		dc.createCriteria("enumConstByType", "enumConstByType",DetachedCriteria.LEFT_JOIN);//短信类型，0普通短信、1定时短信、2系统短信
		
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
		this.getGridConfig().setTitle(this.getText("短信列表"));
        this.getGridConfig().setCapability(false, false, false,true,false);
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);

		this.getGridConfig().addColumn(this.getText("发送者"),"peManagerBySender.trueName");
		this.getGridConfig().addColumn(this.getText("发送对象"),"targets");
		this.getGridConfig().addColumn(this.getText("短信内容"),"content");
		this.getGridConfig().addColumn(this.getText("短信类型"),"enumConstByType.name");
		this.getGridConfig().addColumn(this.getText("审核状态"),"enumConstBySmsStatus.name");
		this.getGridConfig().addColumn(this.getText("发送时间"),"sendDate");
		this.getGridConfig().addColumn(this.getText("状态"),"enumConstBySendstatus.name");
		this.getGridConfig().addColumn(this.getText("审核人"),"peManagerByChecker.trueName");
		
//		this.getGridConfig().addMenuFunction(this.getText("审核"), "/entity/information/.action", false, true);
//		this.getGridConfig().addMenuFunction(this.getText("驳回"), "/entity/information/.action", false, true);
		this.getGridConfig().addMenuFunction(this.getText("删除"), "/entity/information/peSmsInfoAction_del.action",false,true);
		this.getGridConfig().addMenuFunction(this.getText("重新发送"), "/entity/information/peSmsInfoAction_reSend.action",false,true);
		
		
		
		

	}
	
	public String del(){
		Map map=new HashMap();
		map.put("success", "false");
		map.put("info", "删除失败");
		String[] ids=this.getIds().split(",");
		this.getGeneralService().getGeneralDao().setEntityClass(SmsInfo.class);
		List idList=Arrays.asList(ids);
		int succ=0;
		try {
			succ=this.getGeneralService().deleteByIds(idList);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(succ>0){
			map.put("success", "true");
			map.put("info", succ+"条记录删除成功");
		}
		this.setJsonString(JsonUtil.toJSONString(map));
		return this.json();
	}
	public String reSend(){
		Map map=new HashMap();
		map.put("success", "false");
		map.put("info", "重发失败");
		String[] ids=this.getIds().split(",");
		
		UserSession us=(UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		String login_id=us.getLoginId();
		int suc=this.reSendMessage(login_id,ids);
		if(suc>0){
			map.put("success", "true");
			map.put("info", "短信复制成功，请等待审核");
		}
		this.setJsonString(JsonUtil.toJSONString(map));
		return this.json();
	}

	//将选中的短信设置为已审核和已发送，发送时间为当前时间，并添加新短信内容与当前短信相同，未审核未发送
	private int reSendMessage(String login_id, String[] ids) {
		boolean flag_status=true;
		int succ=0;
		//查找审核者和添加者
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
		}
		//查找已审核状态（旧短信设置为已审核）
		EnumConst enu_status=null;
		DetachedCriteria dc_status=DetachedCriteria.forClass(EnumConst.class);
		dc_status.add(Restrictions.eq("namespace","SmsStatus"));
		dc_status.add(Restrictions.eq("code","1"));
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
		}

		//查找未审核状态（新短信设置为未审核）
		EnumConst enu_statusN=null;
		DetachedCriteria dc_statusN=DetachedCriteria.forClass(EnumConst.class);
		dc_statusN.add(Restrictions.eq("namespace","SmsStatus"));
		dc_statusN.add(Restrictions.eq("code","0"));
		List list_statusN=new LinkedList();
		try {
			list_statusN=this.getGeneralService().getList(dc_statusN);
		} catch (EntityException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		if(list_statusN!=null&&list_statusN.size()>0){
			enu_statusN=(EnumConst) list_statusN.get(0);
		}else{
			flag_status=false;
		}
		//查找已发送状态
		EnumConst enu_send=null;
		DetachedCriteria dc_send=DetachedCriteria.forClass(EnumConst.class);
		dc_send.add(Restrictions.eq("namespace", "Sendstatus"));
		dc_send.add(Restrictions.eq("code", "1"));
		List list_send=new LinkedList();
		try {
			list_send=this.getGeneralService().getList(dc_send);
		} catch (EntityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(list_send!=null&&list_send.size()>0){
			enu_send=(EnumConst) list_send.get(0);
		}else{
			flag_status=false;
		}
		//查找未发送状态
		EnumConst enu_sendN=null;
		DetachedCriteria dc_sendN=DetachedCriteria.forClass(EnumConst.class);
		dc_sendN.add(Restrictions.eq("namespace", "Sendstatus"));
		dc_sendN.add(Restrictions.eq("code", "0"));
		List list_sendN=new LinkedList();
		try {
			list_sendN=this.getGeneralService().getList(dc_sendN);
		} catch (EntityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(list_sendN!=null&&list_sendN.size()>0){
			enu_sendN=(EnumConst) list_sendN.get(0);
		}else{
			flag_status=false;
		}
		//
		//
		
		this.getGeneralService().getGeneralDao().setEntityClass(SmsInfo.class);
		for(int i=0;i<ids.length;i++){//将当前短信设为已发送，并添加新短信
			boolean flag=true;
			SmsInfo sms=null;
			try {
				sms=(SmsInfo) this.getGeneralService().getById(ids[i]);
			} catch (EntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			EnumConst enu=null;
			sms.setEnumConstBySmsStatus(enu_status);//已审核
			sms.setPeManagerByChecker(mgr);//审核者
			sms.setSendDate(new Date());//设置当前时间为发送时间
			if(sms.getEnumConstByType()==null||sms.getEnumConstByType().getCode().equals("0")||sms.getEnumConstByType().getCode().equals("2")){//如果不是定时短信
				sms.setEnumConstBySendstatus(enu_send);//已发送
				sms.setSendDate(new Date());
			}
			try {//保存原来短信信息
				sms=(SmsInfo) this.getGeneralService().save(sms);
			} catch (EntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				flag=false;
			}
			//新建短信
			SmsInfo sms_new=new SmsInfo();
			sms_new.setTargets(sms.getTargets());//接受者
			sms_new.setContent(sms.getContent());//短信内容
			sms_new.setPeManagerBySender(sms.getPeManagerBySender());//发送者
			sms_new.setEnumConstByType(sms.getEnumConstByType());//短信类型，普通短信、定时短信、系统短信
			sms_new.setEnumConstBySmsStatus(enu_statusN);//审核状态，0未通过1已通过-1已驳回
			sms_new.setEnumConstBySendstatus(enu_sendN);//发送状态，0未发送，1已发送
			sms_new.setSendDate(new Date());
			try {
				sms_new=(SmsInfo) this.getGeneralService().save(sms_new);
			} catch (EntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				flag=false;
			}
			if(flag){
				succ++;
			}
		}
		return succ;
	}

	@Override
	public void setEntityClass() {
		this.entityClass = SmsInfo.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/information/peSmsInfoAction";
	}
	


}
