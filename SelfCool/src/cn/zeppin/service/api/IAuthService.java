package cn.zeppin.service.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.Auth;

public interface IAuthService {

	/**
	 * 添加用户
	 * @param auth
	 */
	public void addAuth(Auth auth);
	
	/**
	 * 获取认证数据
	 * @param map
	 * @param sort
	 * @param offset
	 * @param length
	 * @return
	 */
	public List<Auth> getAuthsByMap(Map<String, Object> map, String sort, int offset, int length);

}
