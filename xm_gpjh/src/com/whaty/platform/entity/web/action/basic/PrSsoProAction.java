package com.whaty.platform.entity.web.action.basic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeManager;
import com.whaty.platform.entity.bean.PrManProno;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.basic.PeProApplyPriService;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.util.Const;

@SuppressWarnings("unchecked")
public class PrSsoProAction extends MyBaseAction {
	private PeProApplyPriService peProApplyPriService;
	private static final long serialVersionUID = -692739828962977118L;
	private String applyIds;
	
	private String method;
	
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false);
		if(this.getMethod().equals("set")){
			this.getGridConfig().setTitle("指定项目管理员");
			this.getGridConfig().addMenuFunction(this.getText("指定为项目管理员"),"distribute","");
		}else if(this.getMethod().equals("get")){
			this.getGridConfig().setTitle("取消项目管理员");
			this.getGridConfig().addMenuFunction(this.getText("取消项目管理员"),"cancel","");
		}
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("姓名"), "name", true, true, true, "", false, 20);
		this.getGridConfig().addColumn(this.getText("工作单位"), "department", true, true, true, "", false, 40);
		this.getGridConfig().addColumn(this.getText("性别"), "enumConstByFkGender.name", true, true, true, "TextField", false, 50);
		this.getGridConfig().addColumn(this.getText("民族"), "folk", true, true, true, "", true, 200);
		this.getGridConfig().addColumn(this.getText("手机"), "telephone",false, true, true,"",true,200,Const.mobile_for_extjs);
		this.getGridConfig().addColumn(this.getText("出生年月"), "birthday", true, true, true, "", true, 200);
		this.getGridConfig().addColumn(this.getText("邮箱"), "email", true, true, true, "", true, 200,Const.email_for_extjs);
		this.getGridConfig().addColumn(this.getText("办公电话"), "officePhone",false, true, true,"",true,200,Const.phone_number_for_extjs);
		this.getGridConfig().addColumn(this.getText("学历"), "education",true,true,false,"",true,200);
		this.getGridConfig().addColumn(this.getText("政治面貌"), "politics",true,true,true,"",true,200);
		this.getGridConfig().addColumn(this.getText("职务"), "zhiwuzhicheng",true,true,true,"",true,200);
		this.getGridConfig().addColumn(this.getText("通讯地址"), "address",false,true,false,"",true,200);
		this.getGridConfig().addColumn(this.getText("邮编"), "zip",false,true,false,"",true,200,Const.zip_for_extjs);
		this.getGridConfig().addColumn(this.getText("传真"), "fax",false,true,true,"",true,20,Const.phone_number_for_extjs);
		this.getGridConfig().addMenuScript(this.getText("test.back"),"{window.history.back();}");
	}

	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeManager.class);
		dc.createCriteria("enumConstByFkGender", "enumConstByFkGender",DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByFlagIsvalid", "enumConstByFlagIsvalid",DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("ssoUser", "ssoUser")
			.createAlias("pePriRole", "pePriRole")
			.add(Restrictions.eq("pePriRole.id", "ff80808130925d820130b533907301ac"));
		DetachedCriteria expcetdc = DetachedCriteria.forClass(PrManProno.class);
		expcetdc.setProjection(Projections.distinct(Property.forName("peManager.id")));
		expcetdc.createCriteria("peProApplyno","peProApplyno");
		String[] applyIds = getApplyIds().split(",");
		expcetdc.add(Restrictions.in("peProApplyno.id", applyIds));
		if (this.getMethod().equals("get")) {
			dc.add(Subqueries.propertyIn("id", expcetdc));
		}
		return dc;
	}
	
	public Map updateColumn() {
		Map map = new HashMap();
		int count = 0;
		if (this.getIds() != null && this.getIds().length() > 0){
			String[] ids = getIds().split(",");
			List idList = new ArrayList();			
			for (int i = 0; i < ids.length; i++) {
				idList.add(ids[i]);
			}
			String[] applyIds = getApplyIds().split(",");
			List applyIdList = new ArrayList();			
			for (int i = 0; i < applyIds.length; i++) {
				applyIdList.add(applyIds[i]);
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
				if(this.getColumn().equals("distribute")){
					count = this.getPeProApplyPriService().distributePrSsoPro(idList, applyIdList);
					map.put("success", "true");
					map.put("info", this.getText(String.valueOf(count)+"个项目成功指定项目管理员!"));
				}else if(this.getColumn().equals("cancel")){
					count = this.getPeProApplyPriService().cancelPrSsoPro(idList,applyIdList);
					map.put("success", "true");
					map.put("info", this.getText(String.valueOf(count)+"个项目成功取消被指定的项目管理员!"));
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
	
	public void setEntityClass() {
	}

	public void setServletPath() {
		this.servletPath = "/entity/basic/prSsoProAction";
	}
	
	public String getApplyIds() {
		return applyIds != null ? applyIds : (String)ActionContext.getContext().getSession().get("applyIds");
	}

	public void setApplyIds(String applyIds) {
		ActionContext.getContext().getSession().put("applyIds", applyIds);
		this.applyIds = applyIds;
	}
	
	public String getMethod() {
		return  method != null ? method : (String)ActionContext.getContext().getSession().get("method");
	}

	public void setMethod(String method) {
		ActionContext.getContext().getSession().put("method", method);
		this.method = method;
	}

	public PeProApplyPriService getPeProApplyPriService() {
		return peProApplyPriService;
	}

	public void setPeProApplyPriService(PeProApplyPriService peProApplyPriService) {
		this.peProApplyPriService = peProApplyPriService;
	}
}
