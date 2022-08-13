/**
 * 
 */
package cn.zeppin.project.chinamobile.media.web.dao.api;

import java.util.List;

import cn.zeppin.project.chinamobile.media.core.entity.User;
import cn.zeppin.project.chinamobile.media.core.entity.base.Entity;
import cn.zeppin.project.chinamobile.media.web.dao.base.IBaseDAO;

/**
 * @author Clark.R 2016年3月29日
 *
 */
public interface IUserDAO extends IBaseDAO<User, String> {

	/**
	 * 根据用户名返回用户对象
	 * 
	 * @param name
	 * @return user
	 */
	User getByName(String name);

	/**
	 * 测试
	 * @param user
	 * @param sorts
	 * @param offset
	 * @param pagesize
	 * @param resultClass
	 * @return
	 */
	List<Entity> getRoleCount(User user, String sorts, int offset, int pagesize, @SuppressWarnings("rawtypes") Class resultClass);

}
