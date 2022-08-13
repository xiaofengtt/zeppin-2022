package cn.zeppin.service.imp;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.IAuthDAO;
import cn.zeppin.entity.Auth;
import cn.zeppin.service.api.IAuthService;

public class AuthService implements IAuthService {
	
	private IAuthDAO authDAO;

	public IAuthDAO getAuthDAO() {
		return authDAO;
	}

	public void setAuthDAO(IAuthDAO authDAO) {
		this.authDAO = authDAO;
	}
	
	/**
	 * 添加用户
	 * @param auth
	 */
	public void addAuth(Auth auth){
		this.getAuthDAO().save(auth);
	}
	
	/**
	 * 获取认证数据
	 * @param map
	 * @param sort
	 * @param offset
	 * @param length
	 * @return
	 */
	public List<Auth> getAuthsByMap(Map<String, Object> map, String sort, int offset, int length){
		
		return this.getAuthDAO().getAuthsByMap(map, sort, offset, length);
		
	}
	
}
