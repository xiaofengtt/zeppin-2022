package com.whaty.platform.entity.web.action.programApply;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeManager;
import com.whaty.platform.entity.bean.PeProApply;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.programApply.ProgramApplyService;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;

/**
 * 申报项目审核
 * 
 * @author 侯学龙
 *
 */
public class PeProApplyAction extends MyBaseAction {

	private static final long serialVersionUID = 1L;
	
	private ProgramApplyService programApplyService;
	
	private boolean flag; //用于判断是不是项目执行办

	@Override
	public void initGrid() {
		UserSession us = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().setTitle(this.getText("项目审核"));
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
//		this.getGridConfig().addColumn(this.getText("承办单位"), "peUnit.name");
		this.getGridConfig().addColumn(this.getText("培训项目"), "peProApplyno.name");
		this.getGridConfig().addColumn(this.getText("申报学科"), "peSubject.name");
		this.getGridConfig().addColumn(this.getText("申报时间"), "declareDate");
		this.getGridConfig().addColumn(this.getText("省厅师范处审核结果"), "enumConstByFkCheckResultProvince.name");
		this.getGridConfig().addColumn(this.getText("项目执行办审核结果"), "enumConstByFkCheckNational.name");
		this.getGridConfig().addColumn(this.getText("sdf"), "declaration",false,false,false,"");
		this.getGridConfig().addRenderFunction(this.getText("项目申报书"), "<a href='${value}' target='_blank'>下载</a>", "declaration");
		this.getGridConfig().addColumn(this.getText("ads"), "scheme",false,false,false,"");
		this.getGridConfig().addRenderFunction(this.getText("实施方案"), "<a href='${value}' target='_blank'>下载</a>", "scheme");
		this.getGridConfig().addMenuFunction(this.getText("省厅师范处审核通过"),"provincePass","");
		this.getGridConfig().addMenuFunction(this.getText("省厅师范处审核不通过"),"provinceNoPass","");
		
		//flagPEO用来判断是否是项目执行办 通过admin用户设置权限
		if(this.getGridConfig().checkBeforeAddMenu("/entity/programApply/flagPEO_flagPEO.action")){
			this.getGridConfig().addMenuFunction(this.getText("项目执行办审核通过"),"nationalPass","");
			this.getGridConfig().addMenuFunction(this.getText("项目执行办审核不通过"),"nationalNoPass","");
			this.setFlag(true);
		}
		
	}
	
	@Override
	public void setEntityClass() {
		this.entityClass = PeProApply.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/programApply/peProApply";
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
		if(!isFlag()){
			dcpeUnit.createAlias("peProvince", "peProvince", DetachedCriteria.LEFT_JOIN);
			try {
				List peManagerList = this.getGeneralService().getByHQL("from PeManager p where p.ssoUser = '"+userSession.getSsoUser().getId()+"'");
				PeManager manager = (PeManager)peManagerList.get(0);
				dcpeUnit.add(Restrictions.eq("peProvince", manager.getPeUnit().getPeProvince()));
			} catch (EntityException e) {
				e.printStackTrace();
			}
			
			dcPeProApplyno.createAlias("enumConstByFkProvinceCheck", "enumConstByFkProvinceCheck");
			dc.add(Restrictions.eq("enumConstByFkProvinceCheck.code", "1"));
		}
		return dc;
	}
	@Override
	public Map updateColumn() {
		Map map = new HashMap();
		int count = 0;
		if (this.getIds() != null && this.getIds().length() > 0){
			String[] ids = getIds().split(",");
			List idList = new ArrayList();			
			for (int i = 0; i < ids.length; i++) {
				idList.add(ids[i]);
			}
			try {
				checkBeforeUpdateColumn(idList);
			} catch (EntityException e) {
				map.clear();
				map.put("success", "false");
				map.put("info", e.getMessage());
				return map;
			}
			try {
				if(this.getColumn().equals("provincePass")){
					count = this.getProgramApplyService().updateForProvincePass(idList);
					map.put("success", "true");
					map.put("info", this.getText(String.valueOf(count)+"个项目审核通过..."));
				}else if(this.getColumn().equals("provinceNoPass")){
					count = this.getProgramApplyService().updateForProvinceNoPass(idList);
					map.put("success", "true");
					map.put("info", this.getText(String.valueOf(count)+"个项目审核不通过..."));
				}else if(this.getColumn().equals("nationalPass")){
					count = this.getProgramApplyService().updateForNationalPass(idList);
					map.put("success", "true");
					map.put("info", this.getText(String.valueOf(count)+"个项目审核通过..."));
				}else if(this.getColumn().equals("nationalNoPass")){
					count = this.getProgramApplyService().updateForNationalNoPass(idList);
					map.put("success", "true");
					map.put("info", this.getText(String.valueOf(count)+"个项目审核不通过..."));
				}
			} catch (EntityException e) {
				map.put("success", "false");
				map.put("info", e.getMessage());
				return map;
			}

		} else {
			map.put("success", "false");
			map.put("info", "parameter value error");
		}
		return map;
	}
	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public ProgramApplyService getProgramApplyService() {
		return programApplyService;
	}

	public void setProgramApplyService(ProgramApplyService programApplyService) {
		this.programApplyService = programApplyService;
	}

}
