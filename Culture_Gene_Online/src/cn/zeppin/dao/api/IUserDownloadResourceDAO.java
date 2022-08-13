package cn.zeppin.dao.api;

import java.util.HashMap;

import cn.zeppin.entity.UserDownloadResource;

public interface IUserDownloadResourceDAO extends IBaseDAO<UserDownloadResource, Integer> {
	
	/**
	 * 通过参数取count
	 */
	public Integer getCountByParams(HashMap<String,String> searchMap);
	
}
