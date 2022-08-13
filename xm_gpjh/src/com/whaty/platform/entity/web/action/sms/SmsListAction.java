package com.whaty.platform.entity.web.action.sms;

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
import com.whaty.platform.entity.bean.PeTrainee;
import com.whaty.platform.entity.bean.PeTrainingPlan;
import com.whaty.platform.entity.bean.PrRecPlanMajorEdutype;
import com.whaty.platform.entity.bean.PrRecPlanMajorSite;
import com.whaty.platform.entity.bean.SystemApply;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.JsonUtil;
import com.whaty.platform.entity.bean.PrTraingCourse;
import com.whaty.platform.entity.bean.PeFeeDetail;
import com.whaty.platform.entity.bean.EnumConst;

public class SmsListAction extends MyBaseAction {

	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(SystemApply.class);
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
		this.getGridConfig().setTitle(this.getText("申请结业"));
        this.getGridConfig().setCapability(false, false, false,true,false);
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);


			this.getGridConfig().addColumn(this.getText("姓名"),"peTrainee.name");
	}


	@Override
	public void setEntityClass() {
		this.entityClass = PeFeeDetail.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/study/peEndCourseApplyAction";
	}
	


}
