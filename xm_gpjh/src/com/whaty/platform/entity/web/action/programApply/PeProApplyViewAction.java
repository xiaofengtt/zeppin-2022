package com.whaty.platform.entity.web.action.programApply;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeManager;
import com.whaty.platform.entity.bean.PeProApply;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;

public class PeProApplyViewAction extends MyBaseAction {

	@Override
	public void initGrid() {
		UserSession us = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().setTitle(this.getText("查看申报结果"));
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		if(us.getRoleType().equals("4")){
			ColumnConfig cc=new ColumnConfig(this.getText("培训单位"), "peUnit.name", true, true, true, "TextField", true, 50,null);
			cc.setComboSQL("select u.id,u.name from pe_unit u join enum_const e on u.fk_unit_type=e.id and e.code ='1526' order by u.name");
			this.getGridConfig().addColumn(cc);
		}else if(us.getRoleType().equals("3")){
			ColumnConfig cc=new ColumnConfig(this.getText("培训单位"), "peUnit.name", true, true, true, "TextField", true, 50,null);
			cc.setComboSQL("select u.id,u.name from pe_unit u join enum_const e on u.fk_unit_type=e.id  where  e.code ='1526'  and u.fk_province=(select unit.fk_province from pe_manager manager join pe_unit unit on manager.fk_unit=unit.id where manager.fk_sso_user_id='"+us.getSsoUser().getId()+"') order by u.name");
			this.getGridConfig().addColumn(cc);
		}else{
			this.getGridConfig().addColumn(this.getText("培训单位"), "peUnit.name",false,false,true,"");
		}
		this.getGridConfig().addColumn(this.getText("培训项目"), "peProApplyno.name");
		this.getGridConfig().addColumn(this.getText("申报学科"), "peSubject.name");
		this.getGridConfig().addColumn(this.getText("申报时间"), "declareDate",false,false,true,"");
		this.getGridConfig().addColumn(this.getText("省厅师范处审核结果"), "enumConstByFkCheckResultProvince.name");
		this.getGridConfig().addColumn(this.getText("项目执行办审核结果"), "enumConstByFkCheckNational.name");
		this.getGridConfig().addColumn(this.getText("sdf"), "declaration",false,false,false,"");
		this.getGridConfig().addRenderFunction(this.getText("项目申报书"), "<a href='${value}' target='_blank'>下载</a>", "declaration");
		this.getGridConfig().addColumn(this.getText("ads"), "scheme",false,false,false,"");
		this.getGridConfig().addRenderFunction(this.getText("实施方案"), "<a href='${value}' target='_blank'>下载</a>", "scheme");
		
	}

	@Override
	public void setEntityClass() {

	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/programApply/peProApplyView";
	}
	@Override
	public DetachedCriteria initDetachedCriteria() {
		UserSession userSession = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		DetachedCriteria dc = DetachedCriteria.forClass(PeProApply.class);
		DetachedCriteria dcpeUnit = dc.createCriteria("peUnit", "peUnit");
		DetachedCriteria dcPeProApplyno = dc.createCriteria("peProApplyno", "peProApplyno");
		dc.createAlias("peSubject", "peSubject");
		dc.createAlias("enumConstByFkCheckResultProvince", "enumConstByFkCheckResultProvince",1);
		dc.createAlias("enumConstByFkCheckNational", "enumConstByFkCheckNational",1);
//		dcPeProApplyno.add(Restrictions.eq("year", Const.getYear()));
		try {
			List peManagerList = this.getGeneralService().getByHQL("from PeManager p where p.ssoUser = '"+userSession.getSsoUser().getId()+"'");
			PeManager manager = (PeManager)peManagerList.get(0);
			if(manager.getPeUnit().getEnumConstByFkUnitType().getCode().equals("1526")){
				dcpeUnit.add(Restrictions.eq("id", manager.getPeUnit().getId()));
			}else if(manager.getPeUnit().getEnumConstByFkUnitType().getCode().equals("1525")){
				dcpeUnit.createAlias("peProvince", "peProvince");
				dcpeUnit.add(Restrictions.eq("peProvince", manager.getPeUnit().getPeProvince()));
			}
			
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return dc;
	}
}
