package cn.zeppin.service.api;

import java.util.List;
import java.util.Map;

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
	
	/**
	 * 通过Map查询用户
	 * @param searchMap
	 */
	public List<SsoUser> getSsoUserByMap(Map<String, Object> searchMap);
	
	/**
	 * 通过Map查询用户数
	 * @param searchMap
	 */
	public Integer getSsoUserCountByMap(Map<String, Object> searchMap);
}
