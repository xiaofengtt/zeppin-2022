/**
 * 
 */
package com.whaty.platform.sso;

import com.whaty.platform.Exception.PlatformException;

/**
 * @author chenjian
 *
 */
public abstract class SsoManage {

	
	public abstract SsoUser getSsoUser(String id) throws PlatformException;
	
	public abstract SsoUser getSsoUserLogin(String loginId) throws PlatformException;
	
	public abstract SsoUser getSsoUserByLogin(String loginId) throws PlatformException;
	
	public abstract void addSsoUser(String loginId,String password,String name,String nickName,String email) throws PlatformException;
	
	public abstract void updateSsoUser(String id,String loginId,String password,String name,String nickName,String email) throws PlatformException;
	
	public abstract void deleteSsoUser(String id) throws PlatformException;
	
	public abstract void updateUserPwd(String id,String oldPwd,String newPwd) throws PlatformException;
	
}
