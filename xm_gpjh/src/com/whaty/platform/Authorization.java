/*
 * Authoration.java
 *
 * Created on 2004年11月26日, 下午4:52
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
	
	/**对字符串pwd进行加密处理，加密方法由cryptType设置
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
