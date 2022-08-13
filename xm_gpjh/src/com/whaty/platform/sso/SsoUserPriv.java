/**
 * 
 */
package com.whaty.platform.sso;

/**
 * @author chenjian
 *
 */
/**
 * @author chenjian
 *
 */
public abstract class  SsoUserPriv {
	
	private String id;
	private String loginId;
	private String name;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id= id;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 是否允许登录
	 */
	public int login=1;

	/**
	 * 是否允许修改密码
	 */
	public int updatePwd=0;
	
	/**
	 * 是否允许修改个人信息
	 */
	public int updateInfo=0;


}
