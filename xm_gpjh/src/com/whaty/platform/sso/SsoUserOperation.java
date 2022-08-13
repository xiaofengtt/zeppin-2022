/**
 * 
 */
package com.whaty.platform.sso;

import com.whaty.platform.Exception.PlatformException;

/**
 * @author chenjian
 *
 */
public abstract class SsoUserOperation {

	/**该方法用来验证统一登录信息
	 * @param loginId
	 * @param inputPassword
	 * @return
	 * @throws PlatformException
	 */
	public abstract boolean login(String loginId,String inputPassword) throws PlatformException;
	
	/**该方法用来修改用户密码
	 * @param loginId
	 * @param oldPassword
	 * @param newPassword
	 * @throws PlatformException
	 */
	public abstract void updatePwd(String loginId,String oldPassword,String newPassword) throws PlatformException;

}
