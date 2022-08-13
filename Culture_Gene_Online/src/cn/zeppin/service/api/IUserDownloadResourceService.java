package cn.zeppin.service.api;

import java.util.HashMap;

import cn.zeppin.entity.UserDownloadResource;

public interface IUserDownloadResourceService {
	/**
	 * 添加
	 */
	public UserDownloadResource addUserDownloadResource(UserDownloadResource userDownloadResource);
	
	/**
	 * 通过参数取count
	 */
	public Integer getCountByParams(HashMap<String,String> searchMap);
}
