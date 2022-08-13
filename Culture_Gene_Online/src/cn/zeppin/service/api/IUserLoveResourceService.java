package cn.zeppin.service.api;

import java.util.HashMap;

import cn.zeppin.entity.UserLoveResource;

public interface IUserLoveResourceService {
	/**
	 * 添加
	 */
	public UserLoveResource addUserLoveResource(UserLoveResource userLoveResource);
	
	/**
	 * 通过参数取count
	 */
	public Integer getCountByParams(HashMap<String,String> searchMap);
}
