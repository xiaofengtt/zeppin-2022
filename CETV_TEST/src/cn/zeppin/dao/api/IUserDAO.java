package cn.zeppin.dao.api;

import java.util.Map;

import cn.zeppin.entity.User;

public interface IUserDAO extends IBaseDAO<User,Integer>  {
	
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
