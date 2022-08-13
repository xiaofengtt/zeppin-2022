package com.whaty.platform.entity.web.action.programApply;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeProApply;
import com.whaty.platform.entity.bean.PeProApplyno;
import com.whaty.platform.entity.bean.PrProExpert;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;
import com.whaty.platform.util.JsonUtil;

public class PeProJudgmentReportAction extends MyBaseAction {

	private static final long serialVersionUID = 1L;
	
	private String score;//专家评分
	
	private String opinion;//专家意见
	@Override
	public void initGrid() {
		UserSession us = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().setTitle(this.getText("项目初审"));
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("承办单位"), "peProApply.peUnit.name");
		this.getGridConfig().addColumn(this.getText("培训项目"), "peProApply.peProApplyno.name");
		this.getGridConfig().addColumn(this.getText("申报学科"), "peProApply.peSubject.name");
		this.getGridConfig().addColumn(this.getText("申报时间"), "peProApply.declareDate",false,false,true,"Date");
//		this.getGridConfig().addColumn(this.getText("省厅师范处审核结果"), "enumConstByFkCheckResultProvince.name");
//		this.getGridConfig().addColumn(this.getText("项目执行办审核结果"), "enumConstByFkCheckNational.name");
		//this.getGridConfig().addColumn(this.getText("sdf"), "peProApply.declaration",false,false,false,"");
		//this.getGridConfig().addRenderFunction(this.getText("项目申报书"), "<a href='${value}' target='_blank'>下载</a>", "peProApply.declaration");
		//this.getGridConfig().addColumn(this.getText("ads"), "peProApply.scheme",false,false,false,"");
		//this.getGridConfig().addRenderFunction(this.getText("实施方案"), "<a href='${value}' target='_blank'>下载</a>", "peProApply.scheme");
		//this.getGridConfig().addColumn(this.getText("打分"), "peProApply.peProApplyno.id");
		this.getGridConfig().addColumn(this.getText("专家名称"), "peValuaExpert.name");
		this.getGridConfig().addColumn(this.getText("初审评分"), "resultFirst");
		this.getGridConfig().addColumn(this.getText("初审意见"), "noteFirst");
		
	}

	@Override
	public void setEntityClass() {
		
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/programApply/projectJudgmentReport";
	}
	@Override
	public DetachedCriteria initDetachedCriteria() {
		//UserSession userSession = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		//SsoUser ssoUser = userSession.getSsoUser();
		DetachedCriteria dc = DetachedCriteria.forClass(PrProExpert.class);
		dc.createCriteria("peValuaExpert", "peValuaExpert",DetachedCriteria.LEFT_JOIN);
			//.createAlias("ssoUser", "ssoUser");
			//.add(Restrictions.eq("ssoUser", ssoUser));
		dc.createCriteria("peProApply", "peProApply",DetachedCriteria.LEFT_JOIN)
			
			.createAlias("peUnit", "peUnit",DetachedCriteria.LEFT_JOIN)
			.createAlias("peProApplyno", "peProApplyno",DetachedCriteria.LEFT_JOIN)
			//.add(Restrictions.eq("peProApplyno.year", "2010"))
			.add(Restrictions.eq("peProApplyno.year", Const.getYear()))

			.createAlias("peSubject", "peSubject",DetachedCriteria.LEFT_JOIN)
			.createAlias("enumConstByFkCheckFirst", "enumConstByFkCheckFirst",DetachedCriteria.LEFT_JOIN);
		  //.add(Restrictions.not(Restrictions.eq("enumConstByFkCheckFirst.code", "1011")));
		
		return dc;
	}
	
	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

}