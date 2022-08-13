package com.whaty.platform.sso.web.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.entity.user.ManagerPriv;
import com.whaty.platform.entity.user.SiteManagerPriv;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.info.InfoFactory;
import com.whaty.platform.info.user.InfoManagerPriv;

public class InfoAction extends MyBaseAction {

	@Override
	public Map add() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map delete() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public Page list() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map update() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map updateColumn() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 进入新闻管理
	 */
	public String InfoManage()throws PlatformException{
		
		HttpServletRequest request = ServletActionContext.getRequest();
		ManagerPriv includePriv=(ManagerPriv)request.getSession().getAttribute("eduplatform_priv");
		
		if(includePriv != null){
			InfoFactory factory = InfoFactory.getInstance();
			InfoManagerPriv priv =  factory.getInfoManagerPriv(includePriv);
			request.getSession().setAttribute("infomanager_priv", priv);
		}else{
			throw new PlatformException(this.getText("error.noPermission"));
		}
		return "info_news_list";
	}
	
	/**
	 * 进入新闻管理
	 */
	public String InfoSiteManage()throws PlatformException{
		
		HttpServletRequest request = ServletActionContext.getRequest();
		SiteManagerPriv includePriv=(SiteManagerPriv)request.getSession().getAttribute("eduplatform_priv");
		
		if(includePriv != null){
			InfoFactory factory = InfoFactory.getInstance();
			InfoManagerPriv priv =  factory.getInfoManagerPriv(includePriv);
			request.getSession().setAttribute("infomanager_priv", priv);
		}else{
			throw new PlatformException(this.getText("error.noPermission"));
		}
		return "info_news_list";
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

}
