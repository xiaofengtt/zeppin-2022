package com.whaty.platform.entity.web.action.information.sms;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeSmsInfo;
import com.whaty.platform.entity.bean.PrSmsSendStatus;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class PrSmsSendStatusAction extends MyBaseAction<PrSmsSendStatus> {

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, true, false);
		this.getGridConfig().setTitle(this.getText("发送手机号列表"));
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("手机号"), "mobilePhone");
		this.getGridConfig().addColumn(this.getText("发送状态"), "flagSendStatus", false, false, false,"");
		
		this.getGridConfig().addRenderScript("是否发送", "{if (${value}=='0') return '未发送'; if (${value}=='1') return '已发送'; }", "flagSendStatus");
		
		this.getGridConfig().addMenuScript("返回", "{history.back();}");
	}
	
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PrSmsSendStatus.class);
		dc.add(Restrictions.eq("peSmsInfo.id", this.getSms_id()));
		
		//dc.add(Restrictions.eq("enumConstByFlagSmsStatus.code","1"));
		return dc;
	}
	
	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
		this.entityClass = PrSmsSendStatus.class;
		
	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		this.servletPath="/entity/information/prSmsSendStatus";
	}
	
	private String sms_id ;
	
	public String getSms_id() {
		return sms_id;
	}

	public void setSms_id(String sms_id) {
		this.sms_id = sms_id;
	}

	
}
