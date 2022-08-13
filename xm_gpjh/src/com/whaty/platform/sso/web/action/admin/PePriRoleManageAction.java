package com.whaty.platform.sso.web.action.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.bean.PePriRole;
import com.whaty.platform.sso.exception.SsoException;
import com.whaty.platform.sso.service.PePriRoleService;

public class PePriRoleManageAction extends MyBaseAction {
	
	private PePriRole bean;
	
	private PePriRoleService pePriRoleService;
	
	public PePriRole getBean() {
		return bean;
	}

	public void setBean(PePriRole bean) {
		this.bean = bean;
	}

	public PePriRoleService getPePriRoleService() {
		return pePriRoleService;
	}

	public void setPePriRoleService(PePriRoleService pePriRoleService) {
		this.pePriRoleService = pePriRoleService;
	}

	@Override
	public Map add() {
		this.setBean((PePriRole)super.setSubIds(this.getBean()));
		
		PePriRole instance = null;
		Map map = new HashMap();
		try {
			instance = this.getPePriRoleService().save(this.getBean());
			map.put("success", "true");
			map.put("info", this.getText("test.add")+this.getText("test.success"));
			
		} catch (Exception e) {
			map.put("success", "false");
			map.put("info", this.getText("test.add")+this.getText("test.failed"));
		}
		
		return map;
	}

	@Override
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
				
				int count = 0;
				try{
					count =this.getPePriRoleService().deleteByIds(idList);
					if (count >0) {
						map.put("success", "true");
						map.put("info", this.getText("test.delete") + this.getText("test.success"));
					}
				}catch(SsoException e){
					map.put("success", "false");
					map.put("info", e.getMessage());
					return map;
				}
			}
		} else {
			map.put("success", "false");
			map.put("info", "delete error");
		}

		return map;
	}

	@Override
	public void init() {
		this.setServletPath("/sso/admin/role");
		
		this.getGridConfig().setTitle(this.getText("role.gridtitle"));

		this.getGridConfig().addColumn(this.getText("role.id"), "id", false);
		this.getGridConfig().addColumn(this.getText("role.name"), "name");
		
		this.getGridConfig().addRenderFunction(this.getText("role.haveManager"), "<a href=\"/sso/admin/rolesso.action?roleId=${value}&type=3\">查看</a>", "id");
		this.getGridConfig().addRenderFunction(this.getText("role.changePriority"), "<a href=\"/sso/admin/changepriority_showPage.action?roleId=${value}&roleName=\">更改权限</a>", "id");
//		this.getGridConfig().addRenderFunction(this.getText("role.delete"), "<a href=\"#\">删除</a>","id");
		
		this.getGridConfig().addMenuScript(this.getText("test.back"), "{window.location='/admin/right_main.jsp';}");
		
	}

	@Override
	public Page list() {
		DetachedCriteria dc = DetachedCriteria.forClass(PePriRole.class);
		
		dc = super.setDetachedCriteria(dc, this.getBean());

		return getPePriRoleService().getByPage(dc, Integer.parseInt(this.getLimit()), Integer.parseInt(this.getStart()));
	}

	@Override
	public Map update() {
		
		PePriRole dbInstance = this.getPePriRoleService().getById(this.getBean().getId());
		
		this.setBean((PePriRole)super.setSubIds(dbInstance, this.getBean()));
		
		PePriRole instance = null;
		Map map = new HashMap();
		try {
			instance = this.getPePriRoleService().save(this.getBean());
			map.put("success", "true");
			map.put("info", this.getText("test.save")+this.getText("test.success"));
			
		} catch (Exception e) {
			map.put("success", "false");
			map.put("info", this.getText("test.save")+this.getText("test.failed"));
		}
		
		return map;
	}

	@Override
	public Map updateColumn() {
		// TODO Auto-generated method stub
		return null;
	}

}
