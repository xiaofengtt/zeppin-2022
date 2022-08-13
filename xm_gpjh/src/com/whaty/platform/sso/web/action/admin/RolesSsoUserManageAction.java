package com.whaty.platform.sso.web.action.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeManager;
import com.whaty.platform.entity.service.priority.PeManagerService;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.bean.PePriRole;
import com.whaty.platform.sso.bean.SsoUser;
import com.whaty.platform.sso.exception.SsoException;
import com.whaty.platform.sso.service.PePriRoleService;
import com.whaty.platform.sso.service.SsoUserService;
import com.whaty.platform.sso.web.action.SsoConstant;

public class RolesSsoUserManageAction extends MyBaseAction {
	
	private PeManager bean;
	
	private SsoUser ssoUser;
	
	private String roleId;
	
	private PePriRoleService pePriRoleService;
	
	private PeManagerService peManagerService;
	
	private SsoUserService ssoUserService;
	
	public PePriRoleService getPePriRoleService() {
		return pePriRoleService;
	}

	public void setPePriRoleService(PePriRoleService pePriRoleService) {
		this.pePriRoleService = pePriRoleService;
	}

	public SsoUser getSsoUser() {
		return ssoUser;
	}

	public void setSsoUser(SsoUser ssoUser) {
		this.ssoUser = ssoUser;
	}

	public SsoUserService getSsoUserService() {
		return ssoUserService;
	}

	public void setSsoUserService(SsoUserService ssoUserService) {
		this.ssoUserService = ssoUserService;
	}

	public PeManager getBean() {
		return bean;
	}

	public void setBean(PeManager bean) {
		this.bean = bean;
	}

	public PeManagerService getPeManagerService() {
		return peManagerService;
	}

	public void setPeManagerService(PeManagerService peManagerService) {
		this.peManagerService = peManagerService;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	@Override
	public Map add() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map delete() {
		
		Map map = new HashMap();
		int count = 0 ;
			if (this.getIds() != null && this.getIds().length() > 0) {
				String str = this.getIds();
					String[] ids = str.split(",");
					List idList = new ArrayList();
					for (int i = 0; i < ids.length; i++) {
						idList.add(ids[i]);
					}
			try{
				count = getSsoUserService().updateColumnByIds(idList, "pePriRole.id", "");
				map.put("success", "true");
				map.put("info", this.getIds());
			}catch(SsoException e){
				map.put("success", "false");
				map.put("info", this.getText("sso.update") + this.getText("sso.failed"));
				return map ;
			}
		}
		return map;
	}

	@Override
	public void init() {
		this.setServletPath("/sso/admin/rolesso");

		this.getGridConfig().setTitle(this.getText("role.haveManager"));

		this.getGridConfig().setCapability(false, false, false, true);
		this.getGridConfig().addColumn(this.getText("sso.user.id"), "id",false);
		this.getGridConfig().addColumn(this.getText("ssouser.name"), "name");
		this.getGridConfig().addColumn(this.getText("sso.user.type"), "ssoUser.enumConstByUserType.name");
		
		this.getGridConfig().addMenuScript(this.getText("test.back"), "{window.history.back();}");
	}

	@Override
	public Page list() {
		
		DetachedCriteria dc = DetachedCriteria.forClass(PeManager.class,"PeManager");
		dc.createCriteria("ssoUser", "ssoUser").createCriteria("pePriRole", "pePriRole");
		dc.add(Restrictions.eq("pePriRole.id", getRoleId()));
		
		super.setDetachedCriteria(dc, bean);
		Page page = getPeManagerService().getByPage(dc,Integer.parseInt(getLimit()), Integer.parseInt(getStart()));
		
		return page;
	}

	@Override
	public Map update() {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map updateColumn() {
		Map map = new HashMap();
		
		if (this.getIds() != null && this.getIds().length() > 0) {
			String str = this.getIds();
				String[] ids = str.split(",");
				List idList = new ArrayList();
				for (int i = 0; i < ids.length; i++) {
					idList.add(ids[i]);
				}
			
				HttpServletRequest request = ServletActionContext.getRequest();
				String type = (String)request.getAttribute("type");
				
				
				if(SsoConstant.SSO_PRO_GROUP_ADDUSER.equals(type)){//向权限组中添加管理员
					
					String id =(String)idList.get(0);
					SsoUser ssoUser = getSsoUserService().getById(id);
					
					PePriRole role = getPePriRoleService().getById(getRoleId());
					ssoUser.setPePriRole(role);
					
					try{
						getSsoUserService().save(ssoUser);
						map.put("success", "true");
						map.put("info", ssoUser.getId());
					}catch(SsoException e){
						map.put("success", "false");
						map.put("info", this.getText("test.update") + this.getText("test.failed"));
						return map ;
					}
					
				}else if(SsoConstant.SSO_PRO_GROUP_DELUSER.equals(type)){//删除管理员
					int count = 0 ;
					try{
						count = getSsoUserService().updateColumnByIds(idList, "pePriRole.id", "");
						map.put("success", "true");
						map.put("info", this.getIds());
					}catch(SsoException e){
						map.put("success", "false");
						map.put("info", this.getText("test.update") + this.getText("test.failed"));
						return map ;
					}
				}
				
				
		}
		return map;
		
	}

}
