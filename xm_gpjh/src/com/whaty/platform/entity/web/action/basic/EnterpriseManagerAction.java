package com.whaty.platform.entity.web.action.basic;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeBulletin;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeEnterpriseManager;
import com.whaty.platform.entity.bean.PeManager;
import com.whaty.platform.entity.bean.PeSitemanager;
import com.whaty.platform.entity.bean.PeEnterprise;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;
import com.whaty.platform.entity.service.GeneralService;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.util.RandomString;

/**
 * @param
 * @version 创建时间：2009-7-3 下午04:35:51
 * @return
 * @throws PlatformException
 *             类说明
 */
public class EnterpriseManagerAction extends MyBaseAction<PeEnterpriseManager> {

	private GeneralService peEnterprisemanagerService;

	@Override
	public void initGrid() {
		// TODO Auto-generated method stub

		this.getGridConfig().setTitle("企业管理员");
		
		UserSession us = (UserSession) ServletActionContext.getRequest()
		.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("姓名"), "name");
		this.getGridConfig().addColumn(this.getText("登录帐号"), "loginId", true,
				true, true, "", false, 25);
		//if(us.getRoleId().equals("3"))
		this.getGridConfig().addColumn(this.getText("密码"), "ssoUser.password");

		ColumnConfig c_name = new ColumnConfig(this.getText("权限组"),
				"pePriRole.name");
		// c_name.setAdd(true); c_name.setSearch(false); c_name.setList(false);
		if (!us.getRoleId().equals("3"))
			c_name
					.setComboSQL("select t.id,t.name  from pe_pri_role t, enum_const e where t.flag_role_type=e.id and e.code='2' and e.namespace='FlagRoleType' and t.id!='2' order by t.name");
		else if (us.getRoleId().equals("3"))
			c_name
					.setComboSQL("select t.id,t.name  from pe_pri_role t, enum_const e where t.flag_role_type=e.id and e.code='2' and e.namespace='FlagRoleType' order by t.name");
		this.getGridConfig().addColumn(c_name);

		if (us.getRoleId().equals("2")
				|| us.getRoleId().equals("402880a92137be1c012137db62100006")) {
			ColumnConfig c_name1 = new ColumnConfig(this.getText("所在企业"),
					"peEnterprise.name");
			c_name1
					.setComboSQL("select t.id,t.name as p_name from pe_enterprise t where t.id='"
							+ us.getPriEnterprises().get(0).getId()
							+ "' or t.fk_parent_id='"
							+ us.getPriEnterprises().get(0).getId() + "'");
			this.getGridConfig().addColumn(c_name1);
		} else {
			this.getGridConfig().addColumn(this.getText("所在企业"),
					"peEnterprise.name", true, true, true, "");
		}
		
		if (us.getRoleId().equals("3")) {
			
			this.getGridConfig().addMenuFunction(
					this.getText("重置密码"),"ssoUser.password");
		}
		
		if(us.getRoleId().equals("3")){
			this.getGridConfig().addColumn(this.getText("审核人"), "confirmManagerId", true,
					false, true, "");
			this.getGridConfig().addColumn(this.getText("审核时间"), "confirmDate",
				false, false, true, "");
			this.getGridConfig().addMenuFunction(
					this.getText("通过审核"),
					"FlagIsvalid.true");
			this.getGridConfig().addMenuFunction(this.getText("不通过审核"),
					"FlagIsvalid.false");
		}

		this.getGridConfig().addColumn(this.getText("是否有效"),
				"enumConstByFlagIsvalid.name", false, false, true, null);
		//this.getGridConfig().addColumn(this.getText("性别"),
		//		"enumConstByGender.name");
		this.getGridConfig().addColumn(this.getText("职务"), "position");
		
		this.getGridConfig().addColumn(this.getText("办公电话"),"phone",false,true,false,"TextField",true,50);
		this.getGridConfig().addColumn(this.getText("移动电话"),"mobilePhone",false,true,false,"TextField",true,20);
		this.getGridConfig().addColumn(this.getText("电子邮件"),"email",false,true,false,"TextField",true,50);

		//this.getGridConfig().addColumn(this.getText("办公电话"), "phone",false,true,true,Const.phone_number_for_extjs);
		//this.getGridConfig().addColumn(this.getText("移动电话"), "mobilePhone",false,true,true,Const.mobile_for_extjs);

		//this.getGridConfig().addColumn(this.getText("电子邮件"), "email", false,true, true, Const.email_for_extjs);
	}

	public Map updateColumn() {
		Map map = new HashMap();
		UserSession us = (UserSession) ServletActionContext.getRequest()
				.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		DetachedCriteria dc = DetachedCriteria.forClass(PeManager.class);
		dc.add(Restrictions.eq("ssoUser", us.getSsoUser()));
		List<PeManager> peManagerList = new ArrayList<PeManager>();
		try {
			peManagerList = this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		String manager = peManagerList.get(0).getName();
		int count = 0;
		if (this.getIds() != null && this.getIds().length() > 0) {

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
				String acrion = this.getColumn();
				if (acrion.equals("ssoUser.password")) {
					DetachedCriteria sdc = DetachedCriteria
							.forClass(PeEnterpriseManager.class);
					sdc.setProjection(Projections.property("ssoUser.id"));
					sdc.add(Restrictions.in("id", ids));

					DetachedCriteria tampdc = DetachedCriteria
							.forClass(SsoUser.class);
					tampdc.add(Subqueries.propertyIn("id", sdc));
					List<SsoUser> slist = this.peEnterprisemanagerService
							.getList(tampdc);
					Iterator<SsoUser> iterator = slist.iterator();
					while (iterator.hasNext()) {
						SsoUser ssoUser = iterator.next();
						String pwsd = RandomString.getString(8);
						//ssoUser.setPassword("1111");
						ssoUser.setPassword(pwsd);
						this.peEnterprisemanagerService.updateSsoUser(ssoUser);
						count++;
					}
				}
				if (acrion.equals("FlagIsvalid.true")) {

					DetachedCriteria tdc = DetachedCriteria
							.forClass(EnumConst.class);
					tdc.add(Restrictions.eq("namespace", "FlagIsvalid"));
					tdc.add(Restrictions.eq("code", "1"));
					EnumConst enumConst = (EnumConst) this.peEnterprisemanagerService
							.getList(tdc).get(0);

					DetachedCriteria sdc = DetachedCriteria
							.forClass(PeEnterpriseManager.class);
					sdc.add(Restrictions.in("id", ids));
					List<PeEnterpriseManager> Pelist = this.peEnterprisemanagerService
							.getList(sdc);
					Iterator<PeEnterpriseManager> iterator = Pelist.iterator();
					while (iterator.hasNext()) {
						PeEnterpriseManager enterpriseManager = iterator.next();
						enterpriseManager.setConfirmDate(new Date());
						enterpriseManager.setConfirmManagerId(manager);
						enterpriseManager.setEnumConstByFlagIsvalid(enumConst);
						this.peEnterprisemanagerService
								.updatePeEnterpriseManager(enterpriseManager);
						count++;
					}
				}
				if (acrion.equals("FlagIsvalid.false")) {

					DetachedCriteria tdc = DetachedCriteria
							.forClass(EnumConst.class);
					tdc.add(Restrictions.eq("namespace", "FlagIsvalid"));
					tdc.add(Restrictions.eq("code", "0"));
					EnumConst enumConst = (EnumConst) this.peEnterprisemanagerService
							.getList(tdc).get(0);

					DetachedCriteria sdc = DetachedCriteria
							.forClass(PeEnterpriseManager.class);
					sdc.add(Restrictions.in("id", ids));
					List<PeEnterpriseManager> Pelist = this.peEnterprisemanagerService
							.getList(sdc);
					Iterator<PeEnterpriseManager> iterator = Pelist.iterator();
					while (iterator.hasNext()) {
						PeEnterpriseManager enterpriseManager = iterator.next();
						enterpriseManager.setConfirmDate(new Date());
						enterpriseManager.setConfirmManagerId(manager);
						enterpriseManager.setEnumConstByFlagIsvalid(enumConst);
						this.peEnterprisemanagerService
								.updatePeEnterpriseManager(enterpriseManager);
						count++;
					}
				}

			} catch (EntityException e) {
				e.printStackTrace();
				map.clear();
				map.put("success", "false");
				map.put("info", "操作失败");
				return map;
			}
			map.clear();
			map.put("success", "true");
			map.put("info", this.getText(String.valueOf(count) + "条记录操作成功"));

		} else {
			map.put("success", "false");
			map.put("info", "parameter value error");
		}
		return map;
	}

	public void setEntityClass() {
		this.entityClass = PeEnterpriseManager.class;
	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		this.servletPath = "/entity/basic/enterpriseManager";
	}

	public GeneralService getGeneralService() {
		return peEnterprisemanagerService;
	}

	public void setPeEnterprisemanagerService(
			GeneralService peEnterprisemanagerService) {
		this.peEnterprisemanagerService = peEnterprisemanagerService;
	}

	public Map delete() {
		Map map = new HashMap();
		if (this.getIds() != null && this.getIds().length() > 0) {
			String str = this.getIds();
			if (str != null && str.length() > 0) {
				String[] ids = str.split(",");
				List idList = new ArrayList();
				for (int i = 0; i < ids.length; i++) {
					idList.add(ids[i]);
				}
				DetachedCriteria criteria = DetachedCriteria.forClass(PeBulletin.class);
				criteria.createCriteria("enterpriseManager", "enterpriseManager");
				criteria.add(Restrictions.in("enterpriseManager.id", ids));
				try {
					List<PeBulletin> plist = this.getGeneralService().getList(criteria);
					if(plist.size()>0){
						map.clear();
						map.put("success", "false");
						map.put("info", "该企业管理员下存在相关联的公告信息,无法删除!");
						return map;
					}
				} catch (EntityException e) {
					e.printStackTrace();
				}
				try{
					this.getGeneralService().deleteByIds(idList);
					map.put("success", "true");
					map.put("info", "删除成功");
				}catch(RuntimeException e){
					return this.checkForeignKey(e);
				}catch(Exception e1){
					map.clear();
					map.put("success", "false");
					map.put("info", "删除失败");
				}

			} else {
				map.put("success", "false");
				map.put("info", "请选择操作项");
			}
		}
		return map;
	}
	
	
	@Override
	public DetachedCriteria initDetachedCriteria() {
		// TODO Auto-generated method stub
		UserSession us = (UserSession) ServletActionContext.getRequest()
				.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		
		//获取一级企业下属二级单位
		DetachedCriteria criteria = DetachedCriteria
				.forClass(PeEnterprise.class);
		criteria.setProjection(Projections.property("id"));
		criteria.createCriteria("peEnterprise", "peEnterprise");
		if (!us.getRoleId().equals("3"))
			criteria.add(Expression.eq("peEnterprise.id", us
					.getPriEnterprises().get(0).getId()));
		criteria.add(Expression.neProperty("id", "peEnterprise.id"));
		List idList = new ArrayList();
		try {
			idList = this.getGeneralService().getList(criteria);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
		DetachedCriteria dc = DetachedCriteria
				.forClass(PeEnterpriseManager.class);
		dc.createCriteria("enumConstByGender", "enumConstByGender",
				DetachedCriteria.LEFT_JOIN);
		if (us.getRoleId().equals("2")
				|| us.getRoleId().equals("402880a92137be1c012137db62100006")) {
			//添加一级企业id
			idList.add(us.getPriEnterprises().get(0).getId());
			dc.createAlias("peEnterprise", "peEnterprise",
					DetachedCriteria.LEFT_JOIN).add(
					Restrictions.in("peEnterprise.id", idList));
//			dc.createAlias("peEnterprise", "peEnterprise",
//					DetachedCriteria.LEFT_JOIN).add(
//					Restrictions.or(Restrictions.in("peEnterprise.id", idList),
//							Restrictions.eq("peEnterprise.id", us
//									.getPriEnterprises().get(0).getId())));
			// dc.createAlias("peEnterprise", "peEnterprise",
			// DetachedCriteria.LEFT_JOIN).add(Subqueries.propertyIn("peEnterprise.id",
			// criteria));
		} else if (us.getRoleId().equals("3")) {
			dc.createCriteria("peEnterprise", "peEnterprise");
		} else {
			dc.createCriteria("peEnterprise", "peEnterprise").add(
					Restrictions.eq("peEnterprise.id", us.getPriEnterprises()
							.get(0).getId()));
		}
		dc.createCriteria("enumConstByFlagIsvalid", "enumConstByFlagIsvalid",
				DetachedCriteria.LEFT_JOIN);
		if (!us.getRoleId().equals("3"))
			dc.createCriteria("ssoUser", "ssoUser").createAlias("pePriRole",
					"pePriRole").add(Restrictions.ne("pePriRole.id", "2"));
		else
			dc.createCriteria("ssoUser", "ssoUser").createAlias("pePriRole",
					"pePriRole");
		return dc;
	}

	public PeEnterpriseManager getBean() {
		return (PeEnterpriseManager) super.superGetBean();
	}

	public void setBean(PeEnterpriseManager bean) {
		super.superSetBean(bean);
	}
	
	public void checkBeforeAdd() throws EntityException {
		DetachedCriteria criteria = DetachedCriteria
		.forClass(SsoUser.class);
		criteria.add(Restrictions.eq("loginId", this.getBean().getLoginId()));
		List pList=null;
		pList=this.getGeneralService().getList(criteria);
		if (pList!=null && pList.size()>0) {
			throw new EntityException("该登录已经帐号存在，请重新输入新帐号");
		}
	}
	
	public void checkBeforeUpdate() throws EntityException {
		String sql="select id from pe_enterprise_manager where login_id='"+this.getBean().getLoginId()+"'";
		List pList=null;
		pList=this.getGeneralService().getBySQL(sql);
		String id="";
		if(pList!=null && pList.size()>0)
			id = pList.get(0).toString();
		if (pList!=null && id!=null && pList.size()>0 && !id.equals(this.getBean().getId())) {
			throw new EntityException("该登录已经帐号存在，请重新输入新帐号");
		}
	}
}
