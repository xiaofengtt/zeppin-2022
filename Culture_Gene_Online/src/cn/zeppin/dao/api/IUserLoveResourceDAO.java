package cn.zeppin.dao.api;

import java.util.HashMap;

import cn.zeppin.entity.UserLoveResource;

public interface IUserLoveResourceDAO extends IBaseDAO<UserLoveResource, Integer> {

	/**
	 * 通过参数取count
	 */
	public Integer getCountByParams(HashMap<String,String> searchMap);
	
}
