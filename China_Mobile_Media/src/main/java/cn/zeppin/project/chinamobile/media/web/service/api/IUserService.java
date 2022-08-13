/**
 * 
 */
package cn.zeppin.project.chinamobile.media.web.service.api;

import java.util.List;

import cn.zeppin.project.chinamobile.media.core.entity.User;
import cn.zeppin.project.chinamobile.media.core.entity.base.Entity;
import cn.zeppin.project.chinamobile.media.web.service.base.IBaseService;

/**
 * @author Clark.R 2016年3月29日
 *
 */
public interface IUserService extends IBaseService<User, String> {

	public User insert(User user);
	
	public User delete(User user);
	
	public User update(User user);
	
	public User get(String id);
	
	public List<User> getAll();
	
	/**
	 * 根据用户名返回用户对象
	 * 
	 * @param name
	 * @return user
	 */
	public User getByName(String name);
	

	/**
	 * 测试VO
	 * @param user
	 * @param sorts
	 * @param offset
	 * @param pagesize
	 * @param class1
	 * @return
	 */
	List<Entity> getRoleCount(User user, String sorts, int offset, int pagesize, @SuppressWarnings("rawtypes") Class resultClass);

}
