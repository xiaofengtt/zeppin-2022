package com.whaty.platform.entity.web.action.programApply;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeManager;
import com.whaty.platform.entity.bean.PeProApplyno;
import com.whaty.platform.entity.bean.PrProgramUnit;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;

/**
 * 承办单位 查看培训项目
 * 
 * @author 侯学龙
 *
 */
public class PeProApplynoViewAction extends MyBaseAction {

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().setTitle(this.getText("查看培训项目"));
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("培训项目名称"), "name");
		this.getGridConfig().addColumn(this.getText("培训项目编号"), "code");
		this.getGridConfig().addColumn(this.getText("所属年度"), "year");
		this.getGridConfig().addColumn(this.getText("项目类型"), "enumConstByFkProgramType.name");
		this.getGridConfig().addColumn(this.getText("项目申报书格式"), "replyBook",false,false,false,"File",true,50);
		this.getGridConfig().addRenderScript("项目申报书格式","{if(${value}==null||${value}=='') return '未上传'; else return '<a href='+${value}+' target=\\'_blank\\'>下载</a>';}", "replyBook");
		this.getGridConfig().addColumn(this.getText("申报时限"), "deadline",false,false,true,"Date",false,20);
		this.getGridConfig().addColumn(this.getText("人均经费标准"), "feeStandard",false,true,true,Const.fee_for_extjs);
		this.getGridConfig().addColumn(this.getText("申报书状态"), "enumConstByFkProgramStatus.name",false,false,true,"",false,50);
		this.getGridConfig().addColumn(this.getText("是否需要省级审核"), "enumConstByFkProvinceCheck.name",false,false,true,"",false,50);
		this.getGridConfig().addColumn(this.getText("申报学科上限"), "limit",false,false,true,"",true,200);
		this.getGridConfig().addColumn(this.getText("备注"), "note",false,false,true,"",true,200);
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PeProApplyno.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/programApply/peProApplynoView";
	}
	
	@Override
	public DetachedCriteria initDetachedCriteria() {
		UserSession userSession = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		
		DetachedCriteria dc = DetachedCriteria.forClass(PeProApplyno.class);
		dc.createAlias("enumConstByFkProgramType", "enumConstByFkProgramType");
		dc.createAlias("enumConstByFkProvinceCheck", "enumConstByFkProvinceCheck");
		dc.createAlias("enumConstByFkProgramStatus", "enumConstByFkProgramStatus");
//		dc.add(Restrictions.eq("year", Const.getYear()));
		List peManagerList = null;
		try {
			peManagerList = this.getGeneralService().getByHQL("from PeManager p where p.ssoUser = '"+userSession.getSsoUser().getId()+"'");
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PeManager manager = (PeManager)peManagerList.get(0);
		if(manager.getPeUnit().getEnumConstByFkUnitType().getCode().equals("1526")){
			DetachedCriteria expcetdc = DetachedCriteria.forClass(PrProgramUnit.class);
			expcetdc.createCriteria("peUnit", "peUnit");
			expcetdc.add(Restrictions.eq("peUnit.id", manager.getPeUnit().getId()));
			expcetdc.createAlias("peProApplyno", "peProApplyno");
			expcetdc.setProjection(Projections.distinct(Property.forName("peProApplyno.id")));
			dc.add(Subqueries.propertyIn("id", expcetdc));
			dc.add(Restrictions.or(Restrictions.and(Restrictions.eq("enumConstByFkProgramStatus.code", "2"),
					Restrictions.sqlRestriction("deadline-sysdate>0")),
					Restrictions.eq("enumConstByFkProgramStatus.code", "1")));
			
		}
		
		
		
//		DetachedCriteria dc = DetachedCriteria.forClass(PrProgramUnit.class);
//		DetachedCriteria dcApplyno = dc.createCriteria("peProApplyno","peProApplyno");
//		dcApplyno.createAlias("enumConstByFkProvinceCheck", "enumConstByFkProvinceCheck");
//		dcApplyno.createAlias("enumConstByFkProgramStatus", "enumConstByFkProgramStatus");
////			.add(Restrictions.eq("enumConstByFkProgramStatus.code", "1"));
//		dcApplyno.createAlias("enumConstByFkProgramType", "enumConstByFkProgramType");
//		dcApplyno.add(Restrictions.eq("year", Const.getYear()));
//		DetachedCriteria dcPeUnit = dc.createCriteria("peUnit", "peUnit");
//		try {
//			List peManagerList = this.getGeneralService().getByHQL("from PeManager p where p.ssoUser = '"+userSession.getSsoUser().getId()+"'");
//			PeManager manager = (PeManager)peManagerList.get(0);
//			if(manager.getPeUnit().getEnumConstByFkUnitType().getCode().equals("1526")){
//				dcPeUnit.add(Restrictions.eq("id", manager.getPeUnit().getId()));
//				dc.createAlias("enumConstByFkProgramStatus", "enumConstByFkProgramStatus");
//				dcApplyno.add(Restrictions.or(Restrictions.and(Restrictions.eq("enumConstByFkProgramStatus.code", "2"),
//						Restrictions.sqlRestriction("deadline-sysdate>0")),
//						Restrictions.eq("enumConstByFkProgramStatus.code", "1")));
//			}
//			
//		} catch (EntityException e) {
//			e.printStackTrace();
//		}
		
		return dc;
	}
}
