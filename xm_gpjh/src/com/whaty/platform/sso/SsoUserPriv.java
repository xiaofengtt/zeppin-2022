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
	 * �Ƿ������¼
	 */
	public int login=1;

	/**
	 * �Ƿ������޸�����
	 */
	public int updatePwd=0;
	
	/**
	 * �Ƿ������޸ĸ�����Ϣ
	 */
	public int updateInfo=0;


}
