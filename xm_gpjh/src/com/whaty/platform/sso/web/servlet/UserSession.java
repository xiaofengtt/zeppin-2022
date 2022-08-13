package com.whaty.platform.sso.web.servlet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.whaty.platform.entity.bean.PeProApplyno;
import com.whaty.platform.entity.bean.SsoUser;

public class UserSession implements Serializable {

	private static final long serialVersionUID = 4538822591930338135L;

	public UserSession() {
	}

	private SsoUser ssoUser;
	private String id = "";
	private String loginId = "";
	private String userName = "";
	private String userLoginType = "";
	private String roleId = "";
	private String roleType = "";
	private Map userPriority = new HashMap();
	private List<PeProApplyno> priPros = new ArrayList<PeProApplyno>();

	public SsoUser getSsoUser() {
		return ssoUser;
	}

	public void setSsoUser(SsoUser ssoUser) {
		this.ssoUser = ssoUser;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getUserLoginType() {
		return userLoginType;
	}

	public void setUserLoginType(String userLoginType) {
		this.userLoginType = userLoginType;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Map getUserPriority() {
		return userPriority;
	}

	public void setUserPriority(Map userPriority) {
		this.userPriority = userPriority;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public List<PeProApplyno> getPriPros() {
		return priPros;
	}

	public void setPriPros(List<PeProApplyno> priPros) {
		this.priPros = priPros;
	}

}
