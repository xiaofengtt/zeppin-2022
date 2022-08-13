package com.whaty.platform.entity.web.action.programApply;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeProApply;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.programJudge.ProgramJudgmentService;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

/**
 * 管理员评审
 * 
 *
 */
@SuppressWarnings("unchecked")
public class ManagerJudgmentAction extends MyBaseAction {
	private static final long serialVersionUID = 8359793802771930842L;
	
	private ProgramJudgmentService programJudgmentService;
	private String note;
	
	public void initGrid() {
		UserSession us = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().setTitle(this.getText("管理员评审"));
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("承办单位"), "peUnit.name",true,false,true,"");
		this.getGridConfig().addColumn(this.getText("培训项目"), "peProApplyno.name",true,false,true,"");
		this.getGridConfig().addColumn(this.getText("申报学科"), "peSubject.name",true,false,true,"");
		this.getGridConfig().addColumn(this.getText("班级标识码"), "classIdentifier",false,false,us.getRoleType().equals("4"),"");
		this.getGridConfig().addColumn(this.getText("申报时间"), "declareDate",false,false,true,"");
		this.getGridConfig().addColumn(this.getText("初审状态"), "enumConstByFkCheckFirst.name");
		this.getGridConfig().addColumn(this.getText("终审状态"), "enumConstByFkCheckFinal.name");
//		this.getGridConfig().addColumn(this.getText("平均分"), "avgScore",false,false,false,"");
//		this.getGridConfig().addRenderScript("平均分", 
//				"{if((record.data['avgScore'])=='')" +
//				"{return '未评分';}else if((record.data['combobox_EnumConstByFkCheckFinal.checkStatus'])=='未审核'){ return '未审核';}" +
//				"else { return record.data['avgScore'];}}", ""); 
		this.getGridConfig().addColumn(this.getText("sdf"), "declaration",false,false,false,"");
		this.getGridConfig().addRenderFunction(this.getText("项目申报书"), "<a href='${value}' target='_blank'>下载</a>", "declaration");
		this.getGridConfig().addColumn(this.getText("ads"), "scheme",false,false,false,"");
		this.getGridConfig().addRenderFunction(this.getText("实施方案"), "<a href='${value}' target='_blank'>下载</a>", "scheme");
	
		this.getGridConfig().addRenderFunction(this.getText("查看审核意见"), "<a href='/entity/programApply/viewFinalJudgmentOpinion_viewDetail.action?ids=${value}' target='_blank'>查看</a>", "id");
		
		this.getGridConfig().addMenuFunction("项目审核通过", "programcheck","1");
		this.getGridConfig().addMenuFunction("项目审核不通过", "programcheck","2");
	}

	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeProApply.class);
		dc.createAlias("peUnit", "peUnit");
		dc.createCriteria("peProApplyno", "peProApplyno");
		dc.createAlias("peSubject", "peSubject");
		dc.createAlias("enumConstByFkCheckFinal", "enumConstByFkCheckFinal",DetachedCriteria.LEFT_JOIN);
		dc.createAlias("enumConstByFkCheckFirst", "enumConstByFkCheckFirst",DetachedCriteria.LEFT_JOIN);
		dc.createAlias("enumConstByFkCheckResultProvince", "enumConstByFkCheckResultProvince",1);
		dc.createAlias("enumConstByFkCheckNational", "enumConstByFkCheckNational",1);
		dc.add(Restrictions.eq("enumConstByFkCheckNational.code", "1"));
//		dc.add(Restrictions.eq("peProApplyno.year", Const.getYear()));
		return dc;
	}
	
	public Map updateColumn() {
		Map map = new HashMap();
		if (this.getColumn().equals("programcheck")) {
			try {
				String msg = this.getProgramJudgmentService().saveMangerForceCheck(this.getIds(), this.getValue());
				map.put("success", "true");
				map.put("info", msg);
			} catch (EntityException e) {
				map.put("success", "true");
				map.put("info", e.getMessage());
			}
		}
		return map;
	}
	
	public void setEntityClass() {
		this.entityClass = PeProApply.class;
	}

	public void setServletPath() {
		this.servletPath = "/entity/programApply/managerJudgmentAction";
	}


	
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}

	public ProgramJudgmentService getProgramJudgmentService() {
		return programJudgmentService;
	}

	public void setProgramJudgmentService(
			ProgramJudgmentService programJudgmentService) {
		this.programJudgmentService = programJudgmentService;
	}
}