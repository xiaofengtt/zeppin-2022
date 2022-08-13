package cn.zeppin.dao.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.Auth;

public interface IAuthDAO extends IBaseDAO<Auth, Integer> {
	
	
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
