package cn.zeppin.service.api;

import java.util.Map;

import cn.zeppin.entity.User;

public interface IUserService {
	
	/**
	 * 根据id来获取User
	 * @author Administrator
	 * @date: 2014年10月20日 上午11:11:38 <br/> 
	 * @param id
	 * @return
	 */
	public User getUserById(int id);
	
	/**
	 * 添加User
	 * @param user
	 */
	public void addUser(User user);
	
	/**
	 * 根据Map来获取User
	 * @author Administrator
	 * @date: 2014年10月20日 上午11:11:42 <br/> 
	 * @param map
	 * @return
	 */
	public User getUserByMap(Map<String,Object> map);
	
	/**
	 * 更新用户信息
	 * @author Administrator
	 * @date: 2014年10月21日 下午3:21:10 <br/> 
	 * @param user
	 */
	public void updateUser(User user);
	
	/**
	 * 根据Map来获取Count
	 * @author Administrator
	 * @date: 2014年10月21日 下午3:21:10 <br/> 
	 * @param map
	 */
	public int getUserCountByMap(Map<String,Object> map);

}
