/**
 * 
 */
package cn.zeppin.project.chinamobile.media.web.dao.impl;


import java.util.List;

import org.springframework.stereotype.Repository;

import cn.zeppin.project.chinamobile.media.core.entity.User;
import cn.zeppin.project.chinamobile.media.core.entity.base.Entity;
import cn.zeppin.project.chinamobile.media.web.dao.api.IUserDAO;
import cn.zeppin.project.chinamobile.media.web.dao.base.BaseDAO;

/**
 * @author Clark.R 2016年3月29日
 *
 */
@Repository
public class UserDAO extends BaseDAO<User, String> implements IUserDAO {

	/**
	 * 根据用户名返回用户对象
	 * 
	 * @param name
	 * @return user
	 */
	@Override
	public User getByName(String name) {
		String hql = "from User where name=?0 or phone=?1 or email=?2";
		Object[] paras = {name, name, name};
		List<User> users = this.getByHQL(hql, paras);
		if (users != null && users.size() > 0) {
			return users.get(0);
		}
		return null;
	}

	@Override
	public List<Entity> getRoleCount(User user, String sorts, int offset, int pagesize, @SuppressWarnings("rawtypes") Class resultClass) {
		String sql = " select role, count(*) as count from sys_user group by role ";
		List<Entity> results = this.getBySQL(sql, offset, pagesize, null, resultClass);
		System.out.println("success: " + results);
		return results;
	}

	
}
