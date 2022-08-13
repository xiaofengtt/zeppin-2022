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

	/**�÷���������֤ͳһ��¼��Ϣ
	 * @param loginId
	 * @param inputPassword
	 * @return
	 * @throws PlatformException
	 */
	public abstract boolean login(String loginId,String inputPassword) throws PlatformException;
	
	/**�÷��������޸��û�����
	 * @param loginId
	 * @param oldPassword
	 * @param newPassword
	 * @throws PlatformException
	 */
	public abstract void updatePwd(String loginId,String oldPassword,String newPassword) throws PlatformException;

}
