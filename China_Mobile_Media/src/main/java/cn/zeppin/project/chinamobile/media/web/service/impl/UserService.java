/**
 * 
 */
package cn.zeppin.project.chinamobile.media.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.project.chinamobile.media.core.entity.User;
import cn.zeppin.project.chinamobile.media.core.entity.base.Entity;
import cn.zeppin.project.chinamobile.media.web.dao.api.IUserDAO;
import cn.zeppin.project.chinamobile.media.web.service.api.IUserService;
import cn.zeppin.project.chinamobile.media.web.service.base.BaseService;

/**
 * @author Clark.R 2016年3月29日
 *
 */
@Service
public class UserService extends BaseService implements IUserService {
	
	@Autowired
	private IUserDAO userDAO;
	
	public User insert(User user) {
		return userDAO.insert(user);
	}
 
	public User delete(User user) {
		return userDAO.delete(user);
	}

	public User update(User user) {
		return userDAO.update(user);
	}
	
	public User get(String id) {
		return userDAO.get(id);
	}

	public List<User> getAll() {
		return userDAO.getAll();
	}

	/**
	 * 根据用户名返回用户对象
	 * 
	 * @param name
	 * @return
	 */
	public User getByName(String name) {
		return userDAO.getByName(name);
	}

	@Override
	public List<Entity> getRoleCount(User user, String sorts, int offset, int pagesize, @SuppressWarnings("rawtypes") Class resultClass) {
		List<Entity> list = userDAO.getRoleCount(user, sorts, offset, pagesize, resultClass);
		return list;
	}

}
