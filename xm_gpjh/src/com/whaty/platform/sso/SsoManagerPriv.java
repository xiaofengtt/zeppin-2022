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
	 * �Ƿ���������û�
	 */
	public int addUser=0;
	
	/**
	 * �Ƿ������޸��û���Ϣ
	 */
	public int updateUser=0;
	
	/**
	 * �Ƿ�����ɾ���û�
	 */
	public int deleteUser=0;
	
	/**
	 * �Ƿ�����õ��û���Ϣ
	 */
	public int getUser=1;

	

}
