package cn.zeppin.service.imp;

import java.util.HashMap;

import cn.zeppin.dao.api.IUserDownloadResourceDAO;
import cn.zeppin.entity.UserDownloadResource;
import cn.zeppin.service.api.IUserDownloadResourceService;

public class UserDownloadResourceService implements IUserDownloadResourceService {

	private IUserDownloadResourceDAO userDownloadResourceDAO;
	
	public IUserDownloadResourceDAO getUserDownloadResourceDAO() {
		return userDownloadResourceDAO;
	}

	public void setUserDownloadResourceDAO(IUserDownloadResourceDAO userDownloadResourceDAO) {
		this.userDownloadResourceDAO = userDownloadResourceDAO;
	}
	
	/**
	 * 添加
	 */
	public UserDownloadResource addUserDownloadResource(UserDownloadResource userDownloadResource)
	{
		return this.getUserDownloadResourceDAO().save(userDownloadResource);
	}
	
	/**
	 * 通过参数取count
	 */
	public Integer getCountByParams(HashMap<String,String> searchMap){
		return this.getUserDownloadResourceDAO().getCountByParams(searchMap);
	}
}
