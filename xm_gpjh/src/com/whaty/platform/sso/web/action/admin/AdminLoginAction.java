package com.whaty.platform.sso.web.action.admin;

import java.io.File;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.config.PlatformConfig;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;

public class AdminLoginAction extends MyBaseAction {

	private String admin_pwd; //密码
	
	public String getAdmin_pwd() {
		return admin_pwd;
	}

	public void setAdmin_pwd(String admin_pwd) {
		this.admin_pwd = admin_pwd;
	}

	
	
	/**
	 * 后台管理员入口
	 */
	public String adminLogin(){
		return "login";
	}
	
	public String login(){
		ServletActionContext.getRequest().getSession().removeAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		ActionContext act  = ActionContext.getContext();
		
//		PlatformConfig platformConfig = (PlatformConfig)act.getApplication().get("platformConfig");
		String prefix =  ServletActionContext.getServletContext().getRealPath("/");
		String configdir=prefix+"WEB-INF"+File.separator+"config"+File.separator;
		//out.print(configdir);
		PlatformConfig platformConfig = new PlatformConfig(configdir);
		try {
			platformConfig.getConfig();
		} catch (PlatformException e) {
			e.printStackTrace();
		}
		
		String admin_pwd = this.getAdmin_pwd();
		System.out.println(admin_pwd);
		if(admin_pwd.equals(platformConfig.getManagepwd())) {
			act.getSession().put("admin", "1");
			return "in";
		}else{
			return "login";
		}
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
		this.servletPath = "/sso/admin/admin";
		
	}

}
