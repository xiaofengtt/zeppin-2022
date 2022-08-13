package com.whaty.platform.sso.web.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.ApplicationContext;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.config.ForumConfig;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeEnterpriseManager;
import com.whaty.platform.entity.bean.PeManager;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.servlet.UserSession;


public class ForumAction extends MyBaseAction {
	
	public String manPath;
	private PeManager peManager;
	private PeEnterpriseManager peEnterperisemanager;
	
	public String getManPath() {
		return manPath;
	}

	public void setManPath(String manPath) {
		this.manPath = manPath;
	}
	
	private void initManager() {
		UserSession userSession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		SsoUser ssoUser = userSession.getSsoUser();
		DetachedCriteria peManagerDC = DetachedCriteria.forClass(PeManager.class);
		peManagerDC.add(Restrictions.eq("ssoUser", ssoUser));
		List peManagerList = new ArrayList();
		try {
			peManagerList = this.getGeneralService().getList(peManagerDC);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		this.setPeManager((PeManager)peManagerList.get(0));
	}
	
	private void initEnterpriseManager() {
		UserSession userSession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		SsoUser ssoUser = userSession.getSsoUser();
		DetachedCriteria peEntManagerDC = DetachedCriteria.forClass(PeEnterpriseManager.class);
		peEntManagerDC.add(Restrictions.eq("ssoUser", ssoUser));
		List peEntManagerList = new ArrayList();
		try {
			peEntManagerList = this.getGeneralService().getList(peEntManagerDC);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		this.setPeEnterperisemanager((PeEnterpriseManager)peEntManagerList.get(0));
	}

	//跳转向论坛登陆页面
	public String forumManage(){
		
		this.initManager();
		
		ActionContext ctx = ActionContext.getContext(); 
		Map map = ctx.getApplication();
		ForumConfig config = (ForumConfig)map.get("forumConfig");
		String path = config.getForum_Url()+SsoConstant.INTERACTION_FORUM_MANAGE_PATH;
		this.setManPath(path);
		return "forumpath";
	}
	//	跳转向公共论坛页面
	public String toPublicforum(){
		
		this.initEnterpriseManager();
		
		ActionContext ctx = ActionContext.getContext(); 
		Map map = ctx.getApplication();
		ForumConfig config = (ForumConfig)map.get("forumConfig");
		String path = config.getForum_Url()+SsoConstant.INTERACTION_FORUM_MANAGE_PATH;
		this.setManPath(path);
		return "publicforum";
	}
	
	public String toDayiRoom(){
		
		this.initEnterpriseManager();
	
		return "dayi";
	}

	@Override
	public void initGrid() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		
	}

	public PeManager getPeManager() {
		return peManager;
	}

	public void setPeManager(PeManager peManager) {
		this.peManager = peManager;
	}

	public PeEnterpriseManager getPeEnterperisemanager() {
		return peEnterperisemanager;
	}

	public void setPeEnterperisemanager(PeEnterpriseManager peEnterperisemanager) {
		this.peEnterperisemanager = peEnterperisemanager;
	}

}
