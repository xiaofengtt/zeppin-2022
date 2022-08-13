package cn.zeppin.service.imp;

import cn.zeppin.dao.api.ISsoUserDAO;
import cn.zeppin.entity.SsoUser;
import cn.zeppin.service.api.ISsoUserService;

public class SsoUserService implements ISsoUserService {
	
	private ISsoUserDAO ssoUserDAO;

	public ISsoUserDAO getSsoUserDAO() {
		return ssoUserDAO;
	}

	public void setSsoUserDAO(ISsoUserDAO ssoUserDAO) {
		this.ssoUserDAO = ssoUserDAO;
	}
	
	/**
	 * 添加用户
	 * @param ssoUser
	 */
	public void addSsoUser(SsoUser ssoUser){
		this.getSsoUserDAO().save(ssoUser);
	}
	
	/**
	 * 修改信息
	 * @param ssoUser
	 */
	public void updateSsoUser(SsoUser ssoUser){
		this.getSsoUserDAO().update(ssoUser);
	}

	@Override
	public SsoUser getById(int id) {
		return this.getSsoUserDAO().get(id);
	}
	
	
}
