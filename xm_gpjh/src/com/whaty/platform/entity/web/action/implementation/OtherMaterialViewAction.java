package com.whaty.platform.entity.web.action.implementation;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeManager;
import com.whaty.platform.entity.bean.PeProImplemt;
import com.whaty.platform.entity.bean.PeUnit;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;

/**
 * 查看其他材料
 * @author 侯学龙
 *
 */
public class OtherMaterialViewAction extends MyBaseAction {

	private PeProImplemt peProImplemt;
	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().setTitle(this.getText("材料一（按学科提交的材料）"));
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("培训项目"), "peProApply.peProApplyno.name");
//		this.getGridConfig().addColumn(this.getText("承办单位"), "peProApply.peUnit.name");
		UserSession us = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		if(us.getRoleType().equals("4")){
			ColumnConfig cc=new ColumnConfig(this.getText("培训单位"), "peProApply.peUnit.name", true, true, true, "TextField", true, 50,null);
			cc.setComboSQL("select u.id,u.name from pe_unit u join enum_const e on u.fk_unit_type=e.id and e.code ='1526' order by u.name");
			this.getGridConfig().addColumn(cc);
		}else if(us.getRoleType().equals("3")){
			ColumnConfig cc=new ColumnConfig(this.getText("培训单位"), "peProApply.peUnit.name", true, true, true, "TextField", true, 50,null);
			cc.setComboSQL("select u.id,u.name from pe_unit u join enum_const e on u.fk_unit_type=e.id  where  e.code ='1526'  and u.fk_province=(select unit.fk_province from pe_manager manager join pe_unit unit on manager.fk_unit=unit.id where manager.fk_sso_user_id='"+us.getSsoUser().getId()+"') order by u.name");
			this.getGridConfig().addColumn(cc);
		}else{
			this.getGridConfig().addColumn(this.getText("培训单位"), "peProApply.peUnit.name",false,false,true,"");
		}
		this.getGridConfig().addColumn(this.getText("申报学科"), "peProApply.peSubject.name");
		this.getGridConfig().addColumn(this.getText("申报时间"), "peProApply.declareDate",false,false,true,"Date");
		this.getGridConfig().addColumn(this.getText("开班时间"), "startcourseDate",false,false,true,"Date");
		this.getGridConfig().addColumn(this.getText("简报最近上传时间"), "lastUploadDate",false,false,true,"Date");
		this.getGridConfig().addColumn(this.getText("日程最近上传时间"), "courseModifyDate",false,false,true,"Date");
		this.getGridConfig().addColumn(this.getText("sdf"), "noticeName",false,false,false,"");
		this.getGridConfig().addRenderScript(this.getText("开班通知"), "{if (${value}==null||${value}=='') return '未提交'; else return '<a href=\\'/entity/implementation/otherMaterialView_viewDetail.action?ids='+record.data['id']+'\\' target=\\'_blank\\'>'+${value}+'</a>';}", "noticeName");
//		this.getGridConfig().addColumn(this.getText("培训日程"), "peProApply.peSubject.name");
		this.getGridConfig().addRenderScript(this.getText("培训日程"), "{ return '<a href=\\'/entity/implementation/coursePlanView.action?id_='+record.data['id']+'\\' target=\\'_blank\\'>查看</a>';}", "id");
//		this.getGridConfig().addColumn(this.getText("简报名称"), "briefReportName");
		this.getGridConfig().addColumn(this.getText("werw"), "briefReportFile",false,false,false,"");
		this.getGridConfig().addRenderScript(this.getText("工作简报"), "{ return '<a href=\\'/entity/implementation/briefReportView.action?id_='+record.data['id']+'\\' target=\\'_blank\\'>查看</a>';}", "id");
//		this.getGridConfig().addColumn(this.getText("ads"), "summaryFile",false,false,false,"");
//		this.getGridConfig().addRenderScript(this.getText("查看开班总结"), "{if (${value}==null||${value}=='') return '未提交'; else return '<a href='+${value}+' target=\\'_blank\\'>查看</a>';}", "summaryFile");
	}

	@Override
	public void setEntityClass() {

	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/implementation/otherMaterialView";
	}
	public String viewDetail(){
		PeProImplemt peProImplemt = null;
		try {
//			 list = this.getGeneralService().getBySQL("select to_char(t.NOTICE_CONTENT) from PE_PRO_IMPLEMT t where t.id = '"+this.getId_()+"'");
			peProImplemt = (PeProImplemt)this.getGeneralService().getById(PeProImplemt.class, this.getIds());
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
		if(peProImplemt!=null){
			this.setPeProImplemt(peProImplemt);
		}
//		return "msg";
		return "note_detail";
	}
	
	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeProImplemt.class);
		DetachedCriteria dcPeProApply = dc.createCriteria("peProApply", "peProApply", DetachedCriteria.LEFT_JOIN);
		dcPeProApply.createAlias("peProApplyno", "peProApplyno", DetachedCriteria.LEFT_JOIN);
		dcPeProApply.createAlias("peSubject", "peSubject", DetachedCriteria.LEFT_JOIN);
		dcPeProApply.createAlias("peUnit", "peUnit", DetachedCriteria.LEFT_JOIN);
		if(this.getPeUnit().getEnumConstByFkUnitType().getCode().equals("1526")){
			dcPeProApply.add(Restrictions.eq("peUnit", getPeUnit()));
		}
//		dcPeProApply.add(Restrictions.like("peProApplyno.year", Const.getYear() , MatchMode.ANYWHERE));
		return dc;
	}

	private PeUnit getPeUnit(){
		UserSession userSession = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		try {
			List peManagerList = this.getGeneralService().getByHQL("from PeManager p where p.ssoUser = '"+userSession.getSsoUser().getId()+"'");
			PeManager manager = (PeManager)peManagerList.get(0);
			return manager.getPeUnit();
		} catch (EntityException e) {
			e.printStackTrace();
			return null;
		}
	}

	public PeProImplemt getPeProImplemt() {
		return peProImplemt;
	}

	public void setPeProImplemt(PeProImplemt peProImplemt) {
		this.peProImplemt = peProImplemt;
	}

}
