package com.whaty.platform.sso.service;

import com.whaty.platform.entity.bean.PeSitemanager;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.GeneralService;
import com.whaty.platform.sso.exception.SsoException;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.entity.bean.PeEnterpriseManager;

public interface SsoUserService extends GeneralService<SsoUser>{
	public boolean CheckSsoUserByType(SsoUser ssoUser, String loginType) throws SsoException;
	
	public SsoUser getByLoginId(String loginId) throws EntityException;
	
	public UserSession getUserSession(SsoUser ssoUser, String loginType) throws SsoException;
	
	/**
	 * 首页取回密码功能验证输入是否正确
	 * @param ssoUser
	 * @param loginType
	 * @param trueName 姓名
	 * @param cardId	证件号码
	 * @return
	 */
	public boolean checkTrue(SsoUser ssoUser, String loginType ,String trueName ,String cardId);
	
	/**
	 * 首页取回密码功能设置新密码
	 * @param ssoUser	用户
	 * @param passwd	新密码
	 */
	public void saveNewPassword(SsoUser ssoUser,String passwd);

	public PeSitemanager getPeSitemanager(String loginId);
	
	public PeEnterpriseManager getEnterprisemanager(String loginId);
}
