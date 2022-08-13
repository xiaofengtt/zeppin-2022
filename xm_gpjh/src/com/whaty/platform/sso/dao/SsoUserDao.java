package com.whaty.platform.sso.dao;


import com.whaty.platform.entity.dao.AbstractEntityDao;
import com.whaty.platform.sso.bean.SsoUser;
/**
 * @author lwx 2008-7-28
 * @email  <p>liweixin@whaty.com</p>
 *
 */
public interface SsoUserDao extends AbstractEntityDao<SsoUser,String> {
	
	/**
	 * 根据登陆id查找用户
	 * @param loginId
	 * @return
	 */
	public SsoUser getByLoginId(String loginId);
}
