package cn.zeppin.service.imp;

import java.util.HashMap;
import java.util.List;

import cn.zeppin.dao.api.IUserDAO;
import cn.zeppin.entity.User;
import cn.zeppin.service.api.IUserService;
import cn.zeppin.utility.Dictionary;

public class UserService implements IUserService {

	private IUserDAO userDAO;

	public IUserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(IUserDAO userDAO) {
		this.userDAO = userDAO;
	}

	/**
	 * 获取
	 */
	public User getUser(Integer id) {
		return this.getUserDAO().get(id);
	}

	/**
	 * 添加
	 */
	public User addUser(User user) {
		return getUserDAO().save(user);
	}

	/**
	 * 停用
	 */
	public User deleteUser(User user) {
		user.setStatus(Dictionary.USER_STATUS_CLOSED);
		return this.getUserDAO().update(user);
	}

	/**
	 * 修改
	 */
	public User updateUser(User user) {
		return this.getUserDAO().update(user);
	}

	/**
	 * 通过参数取count
	 */
	public Integer getCountByParams(HashMap<String,String> searchMap){
		return this.getUserDAO().getCountByParams(searchMap);
	}
	
	/**
	 * 获取页面信息
	 */
	public List<User> getListForPage(HashMap<String,String> searchMap, String sort,  Integer offset, Integer pagesize ){
		return this.getUserDAO().getListByParams(searchMap, sort, offset, pagesize);
	}
	
	/**
	 * 通过参数取列表
	 */
	public List<User> getListByParams(HashMap<String,String> searchMap){
		return this.getUserDAO().getListByParams(searchMap, null, null, null);
	}
}
