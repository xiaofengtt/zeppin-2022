package com.whaty.platform.sso.web.action.admin;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.logmanage.LogManage;
import com.whaty.platform.sso.bean.SsoUser;
import com.whaty.platform.sso.exception.SsoException;
import com.whaty.platform.sso.service.SsoUserService;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

public class SsoUserManageAction extends MyBaseAction {
	
	//显示弹出信息
	private String msg;
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

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
	
	private String pwd;
	private String oldPwd;
	private SsoUserService ssoUserService;
	
	public String getOldPwd() {
		return oldPwd;
	}

	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}

	public SsoUserService getSsoUserService() {
		return ssoUserService;
	}

	public void setSsoUserService(SsoUserService ssoUserService) {
		this.ssoUserService = ssoUserService;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	/**
	 * 修改密码
	 */
	public String changePwd()throws SsoException{
		com.whaty.platform.logmanage.LogFactory factory = com.whaty.platform.logmanage.LogFactory.getInstance();
		LogManage logManage = factory.creatLogManage();
		SsoUser u = null;
		
			HttpServletRequest request = ServletActionContext.getRequest();
			UserSession us =(UserSession) request.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
			u = us.getSsoUser();
			if(!u.getPassword().equals(getOldPwd())){
				throw new SsoException(this.getText("error.sso.errorinputoldpassword"));
			}
		try{
			u.setPassword(getPwd());
			u = getSsoUserService().save(u);
		}catch(SsoException e){
			this.addActionError(this.getText("error.sso.errorchangepasswrod"));
			if(SsoConstant.SSO_MANAGER.equals(us.getUserLoginType())){
				try {
					logManage.insertLog("修改密码失败");
				} catch (PlatformException e1) {
					
				}
				return "change_pwd";
			}
			else{
				try {
					logManage.insertLog("修改密码失败");
				} catch (PlatformException e1) {
					
				}
				return "change_sub_pwd";
			}
				
		//	throw new SsoException(this.getText("error.sso.errorchangepasswrod"));
		}
		
		this.addActionMessage(this.getText("test.update")+this.getText("test.success"));
		if(SsoConstant.SSO_MANAGER.equals(us.getUserLoginType())){
			try {
				logManage.insertLog("修改密码成功");
			} catch (PlatformException e1) {
				
			}
			return "change_pwd";
		}
		else{
			try {
				logManage.insertLog("修改密码成功");
			} catch (PlatformException e1) {
				
			}
			return "change_sub_pwd";
		}
			
	}
	
	/**
	 * 显示修改密码
	 */
	public String showPage(){
		return "change_pwd";
	}
	
}
