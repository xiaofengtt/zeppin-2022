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
public class SsoManagerPriv {
	
	private String id;
	private String loginId;
	private String name;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 是否允许添加用户
	 */
	public int addUser=0;
	
	/**
	 * 是否允许修改用户信息
	 */
	public int updateUser=0;
	
	/**
	 * 是否允许删除用户
	 */
	public int deleteUser=0;
	
	/**
	 * 是否允许得到用户信息
	 */
	public int getUser=1;

	

}
