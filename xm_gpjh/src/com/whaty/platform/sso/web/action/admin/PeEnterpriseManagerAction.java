package com.whaty.platform.sso.web.action.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.entity.bean.PeBulletin;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeEnterprise;
import com.whaty.platform.entity.bean.PeEnterpriseManager;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.util.Const;
import com.whaty.platform.entity.service.GeneralService;
import com.whaty.platform.entity.util.ColumnConfig;

/**
 * @param
 * @version 创建时间：2009-6-29 上午11:21:06
 * @return
 * @throws PlatformException
 * 类说明
 */
public class PeEnterpriseManagerAction extends MyBaseAction <PeEnterpriseManager> {
	
	private GeneralService peEnterprisemanagerService;

	@Override
	public void initGrid() {
		// TODO Auto-generated method stub
		this.getGridConfig().setCapability(true, true, true);
		this.getGridConfig().setTitle("企业管理员");
		this.getGridConfig().addColumn(this.getText("ID"), "id",false);
		this.getGridConfig().addColumn(this.getText("姓名"), "name");
		this.getGridConfig().addColumn(this.getText("用户名"), "loginId", true, true, true, "", false, 25);
		
		ColumnConfig c_name = new ColumnConfig(this.getText("权限组"), "pePriRole.name");
//		c_name.setAdd(true); c_name.setSearch(false); c_name.setList(false);
		c_name.setComboSQL("select t.id,t.name  from pe_pri_role t, enum_const e where t.flag_role_type=e.id and e.code='2' and e.namespace='FlagRoleType' order by t.name");
		this.getGridConfig().addColumn(c_name);
		
		this.getGridConfig().addColumn(this.getText("所在企业"),"peEnterprise.name");
		
		this.getGridConfig().addColumn(this.getText("是否有效"), "enumConstByFlagIsvalid.name");
		
		this.getGridConfig().addColumn(this.getText("性别"), "enumConstByGender.name");
		this.getGridConfig().addColumn(this.getText("职务"), "position");
		
		this.getGridConfig().addColumn(this.getText("办公电话"),"phone",false,true,true,Const.phone_number_for_extjs);
		this.getGridConfig().addColumn(this.getText("移动电话"),"mobilePhone",false,true,true,Const.mobile_for_extjs);
		this.getGridConfig().addColumn(this.getText("电子邮件"),"email",false,true,true,Const.email_for_extjs);
		
	}

	public void setEntityClass() {
		// TODO Auto-generated method stub
		this.entityClass=PeEnterpriseManager.class;
	}
	
	public void setServletPath() {
		// TODO Auto-generated method stub
		this.servletPath="/sso/admin/peEnterprisemanager";
	}
	
	public DetachedCriteria initDetachedCriteria() {
		// TODO Auto-generated method stub
		DetachedCriteria dc=DetachedCriteria.forClass(PeEnterpriseManager.class);
		dc.createAlias("enumConstByGender", "enumConstByGender", DetachedCriteria.LEFT_JOIN)
		.createAlias("peEnterprise", "peEnterprise", DetachedCriteria.LEFT_JOIN)
		.createAlias("enumConstByFlagIsvalid", "enumConstByFlagIsvalid", DetachedCriteria.LEFT_JOIN)
		.createCriteria("ssoUser", "ssoUser", DetachedCriteria.LEFT_JOIN).createAlias("pePriRole", "pePriRole", DetachedCriteria.LEFT_JOIN);
		return dc;
	}
	
	public PeEnterpriseManager getBean() {
		return super.superGetBean();
	}

	public void setBean(PeEnterpriseManager bean) {
		super.superSetBean(bean);
	}

	public GeneralService getGeneralService() {
		return peEnterprisemanagerService;
	}

	public void setPeEnterprisemanagerService(
			GeneralService peEnterprisemanagerService) {
		this.peEnterprisemanagerService = peEnterprisemanagerService;
	}

}
