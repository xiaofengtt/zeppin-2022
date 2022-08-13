package cn.zeppin.service.imp;

import java.util.HashMap;

import cn.zeppin.dao.api.IUserLoveResourceDAO;
import cn.zeppin.entity.UserLoveResource;
import cn.zeppin.service.api.IUserLoveResourceService;

public class UserLoveResourceService implements IUserLoveResourceService {

	private IUserLoveResourceDAO userLoveResourceDAO;
	
	public IUserLoveResourceDAO getUserLoveResourceDAO() {
		return userLoveResourceDAO;
	}

	public void setUserLoveResourceDAO(IUserLoveResourceDAO userLoveResourceDAO) {
		this.userLoveResourceDAO = userLoveResourceDAO;
	}
	
	/**
	 * 添加
	 */
	public UserLoveResource addUserLoveResource(UserLoveResource userLoveResource)
	{
		return this.getUserLoveResourceDAO().save(userLoveResource);
	}
	
	/**
	 * 通过参数取count
	 */
	public Integer getCountByParams(HashMap<String,String> searchMap){
		return this.getUserLoveResourceDAO().getCountByParams(searchMap);
	}
}
