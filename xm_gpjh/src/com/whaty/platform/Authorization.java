/*
 * Authoration.java
 *
 * Created on 2004��11��26��, ����4:52
 */

package com.whaty.platform;

import com.whaty.platform.Exception.PlatformException;

/**
 *
 * @author chenjian
 */
public abstract class Authorization {
    
    private String loginId;
    
    private String rightPwd; 
    
    public String getRightPwd() {
		return rightPwd;
	}

	public void setRightPwd(String rightPwd) {
		this.rightPwd = rightPwd;
	}

	public abstract boolean login(String inputPwd) throws PlatformException;
	
	/**���ַ���pwd���м��ܴ������ܷ�����cryptType����
	 * @param pwd
	 * @param cryptType
	 * @return
	 * @throws PlatformException
	 */
	protected abstract String enCrypt(String pwd,String cryptType) throws PlatformException;

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
    
    
}
