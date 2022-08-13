package com.whaty.platform.entity.web.action.information;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeEnterprise;
import com.whaty.platform.entity.bean.PeEnterpriseManager;
import com.whaty.platform.entity.bean.PeManager;
import com.whaty.platform.entity.bean.PeSitemanager;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.GeneralService;
import com.whaty.platform.entity.service.imp.information.PersonalInfoService;
import com.whaty.platform.entity.web.action.EntityBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

public class PersonalInfoAction extends EntityBaseAction{
	
	private PeManager peManager;
	
	private PeEnterpriseManager peEnterpriseManager;
	private String phone;
	private String mobilePhone;
	private String email;
	private String id;
	
	private String message;
	
	private String oldPwd;
	
	private String pwd;
	
	private GeneralService generalService;
	
	private PersonalInfoService personalInfoService;
	
	
	public PersonalInfoService getPersonalInfoService() {
		return personalInfoService;
	}

	public void setPersonalInfoService(PersonalInfoService personalInfoService) {
		this.personalInfoService = personalInfoService;
	}

	public String getOldPwd() {
		return oldPwd;
	}

	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public PeManager getPeManager() {
		return peManager;
	}

	public void setPeManager(PeManager peManager) {
		this.peManager = peManager;
	}

	public PeEnterpriseManager getPeEnterpriseManager() {
		return peEnterpriseManager;
	}

	public void setPeEnterpriseManager(PeEnterpriseManager peEnterpriseManager) {
		this.peEnterpriseManager = peEnterpriseManager;
	}

	public GeneralService getGeneralService() {
		return generalService;
	}

	public void setGeneralService(GeneralService generalService) {
		this.generalService = generalService;
	}
	
	public String viewInfo() {
		setInfo();
		return "info_detail";
	}
	
	public String turnToEditInfo() {
		setInfo();
		if(peEnterpriseManager != null)
			return "edit_sitemanager_Info";
		else
			return "edit_manager_Info";
	}
	
	public String editInfo() {
		UserSession userSession = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		String loginType = userSession.getUserLoginType();
		DetachedCriteria tempdc = null;
		try {
			if(loginType.equals("3")){
				tempdc= DetachedCriteria.forClass(PeManager.class);
			}else{
				tempdc= DetachedCriteria.forClass(PeEnterpriseManager.class);
			}
			tempdc.add(Restrictions.eq("id", id));
			if(loginType.equals("3")){
				PeManager peManager =(PeManager)this.generalService.getList(tempdc).get(0);
				peManager.setEmail(email);
				peManager.setMobilePhone(mobilePhone);
				peManager.setPhone(phone);
				this.getGeneralService().updatePeManager(peManager);
			}else{
				PeEnterpriseManager enterpriseManager =(PeEnterpriseManager)this.generalService.getList(tempdc).get(0);
				enterpriseManager.setEmail(email);
				enterpriseManager.setMobilePhone(mobilePhone);
				enterpriseManager.setPhone(phone);
				try {
					this.personalInfoService.updatePersonalInfo(enterpriseManager);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		this.message="editOK";
		return "info_view";
	}
	
	private void setInfo() {
		UserSession userSession = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		String loginType = userSession.getUserLoginType();
		DetachedCriteria infodc = null;
		try {
			if(loginType.equals("3")){
				infodc = DetachedCriteria.forClass(PeManager.class);
			}else{
				infodc = DetachedCriteria.forClass(PeEnterpriseManager.class);
			}
			infodc.add(Restrictions.eq("ssoUser", userSession.getSsoUser()));
			if(loginType.equals("3")){
				this.setPeManager((PeManager)this.generalService.getList(infodc).get(0));
			}else{
				this.setPeEnterpriseManager((PeEnterpriseManager)this.generalService.getList(infodc).get(0));
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
	}
	
	public String editPwd() {
		if(this.getOldPwd() == null){
			return "pwd";
		}
		SsoUser ssoUser = ((UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY)).getSsoUser();
		if(!this.getOldPwd().equals(ssoUser.getPassword())){
			this.message = "您输入的原始密码错误";
		}else{
			ssoUser.setPassword(this.getPwd());
			try {
				this.getGeneralService().save(ssoUser);
				this.message = "密码设置成功";
			} catch (EntityException e) {
				e.printStackTrace();
				this.message = "密码设置失败";
			}
		}
		return "pwd";
	}

	public String getPhone() {
		return phone;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
}
