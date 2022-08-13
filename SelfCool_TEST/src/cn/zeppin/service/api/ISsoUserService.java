package cn.zeppin.service.api;

import cn.zeppin.entity.SsoUser;

public interface ISsoUserService {
	
	/**
	 * 获取
	 * @param id
	 * @return
	 */
	public SsoUser getById(int id);
	
	/**
	 * 添加用户
	 * @param ssoUser
	 */
	public void addSsoUser(SsoUser ssoUser);
	
	/**
	 * 修改信息
	 * @param ssoUser
	 */
	public void updateSsoUser(SsoUser ssoUser);
}
