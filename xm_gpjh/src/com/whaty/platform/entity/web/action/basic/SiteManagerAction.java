package com.whaty.platform.entity.web.action.basic;

import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeSitemanager;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.GeneralService;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;
import com.whaty.util.string.AttributeManage;
import com.whaty.util.string.WhatyAttributeManage;

public class SiteManagerAction extends MyBaseAction <PeSitemanager>{
	
	private GeneralService peSitemanagerService;

	@Override
	public void initGrid() {
		this.getGridConfig().setTitle(this.getText("分站管理员管理"));

		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("姓名"), "trueName");
		this.getGridConfig().addColumn(this.getText("用户名"), "loginId", true, true, true, "", false, 25);
		ColumnConfig c_name = new ColumnConfig(this.getText("权限组"), "pePriRole.name");
//		c_name.setAdd(true); c_name.setSearch(true); c_name.setList(true);
		c_name.setComboSQL("select t.id,t.name  from pe_pri_role t, enum_const e where t.flag_role_type=e.id and e.code='2' and e.namespace='FlagRoleType' and t.id!='2' order by t.name");
		this.getGridConfig().addColumn(c_name);
//		this.getGridConfig().addColumn(this.getText("权限组"), "ssoUser.pePriRole.name");
		this.getGridConfig().addColumn(this.getText("所属站点"), "peSite.name", false, false, true, "");
		this.getGridConfig().addColumn(this.getText("地区"), "regionName");
		this.getGridConfig().addColumn(this.getText("是否有效"), "enumConstByFlagIsvalid.name");
		this.getGridConfig().addColumn(this.getText("性别"), "enumConstByGender.name");
		this.getGridConfig().addColumn(this.getText("身份证号"), "idCard");
		this.getGridConfig().addColumn(this.getText("电话"), "phone",true,true,true,Const.telephone_for_extjs);
		this.getGridConfig().addColumn(this.getText("手机"), "mobilePhone",true,true,true,Const.mobile_for_extjs);
		this.getGridConfig().addColumn(this.getText("邮箱"), "email",true,true,true,Const.email_for_extjs);
		this.getGridConfig().addColumn(this.getText("通信地址及邮编"), "address", true, true, true, "", false, 150);
		this.getGridConfig().addColumn(this.getText("毕业院校、专业"), "graduationInfo");
		this.getGridConfig().addColumn(this.getText("毕业时间"), "graduationDate");
		this.getGridConfig().addColumn(this.getText("职称"), "zhiCheng");
		this.getGridConfig().addColumn(this.getText("从事成人教育时间"), "workTime");
		
	}
	@Override
	public void checkBeforeAdd() throws EntityException {
		UserSession us = (UserSession)ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		String userLoginType = "";//保存管理员类型
		if (us!=null) {
			userLoginType = us.getUserLoginType();
			if(userLoginType.equals("2")){
				DetachedCriteria dc = DetachedCriteria.forClass(PeSitemanager.class);
				dc.add(Restrictions.eq("ssoUser", us.getSsoUser()));
				List<PeSitemanager> list = null;
				list = this.getGeneralService().getList(dc);
				if(list != null && list.size() > 0){
					this.getBean().setPeSite(list.get(0).getPeSite());
				}
			}
		}
		
		AttributeManage manage=new WhatyAttributeManage();
		try {
			if(!manage.isValidIdcard(this.getBean().getIdCard())){
				throw new EntityException("身份证号码输入错误！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new EntityException("身份证号码输入错误！");
		}
	}
	
	@Override
	public void checkBeforeUpdate() throws EntityException {
		AttributeManage manage=new WhatyAttributeManage();
		try {
			if(!manage.isValidIdcard(this.getBean().getIdCard())){
				throw new EntityException("身份证号码输入错误！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new EntityException("身份证号码输入错误！");
		}
	}
	
	@Override
	public void setEntityClass() {
		this.entityClass = PeSitemanager.class;
		
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/basic/siteManager";
	}

	public GeneralService getGeneralService() {
		return peSitemanagerService;
	}

	public void setPeSitemanagerService(GeneralService peSitemanagerService) {
		this.peSitemanagerService = peSitemanagerService;
	}
	
	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeSitemanager.class);
		dc.createAlias("enumConstByGender", "enumConstByGender", DetachedCriteria.LEFT_JOIN)
			.createAlias("peSite", "peSite", DetachedCriteria.LEFT_JOIN)
			.createAlias("enumConstByFlagIsvalid", "enumConstByFlagIsvalid", DetachedCriteria.LEFT_JOIN)
			.createCriteria("ssoUser", "ssoUser").createAlias("pePriRole", "pePriRole").add(Restrictions.ne("pePriRole.id", "2"));
		return dc;
	}

	public PeSitemanager getBean() {
		return super.superGetBean();
	}

	public void setBean(PeSitemanager bean) {
		super.superSetBean(bean);
	}

}
