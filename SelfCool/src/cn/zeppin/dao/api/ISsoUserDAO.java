package cn.zeppin.dao.api;

import java.util.Map;
import java.util.List;

import cn.zeppin.entity.SsoUser;

public interface ISsoUserDAO extends IBaseDAO<SsoUser, Integer> {
	/**
	 * 通过Map查询用户
	 * @param searchMap
	 */
	public List<SsoUser> getSsoUserByMap(Map<String,Object> searchMap);
	
	/**
	 * 通过Map查询用户量
	 * @param searchMap
	 */
	public Integer getSsoUserCountByMap(Map<String,Object> searchMap);
}
