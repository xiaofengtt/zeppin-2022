/** 
 * Project Name:CETV_TEST 
 * File Name:UserService.java 
 * Package Name:cn.zeppin.service.imp 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */  
package cn.zeppin.service.imp;

import java.util.Map;

import cn.zeppin.dao.api.IUserDAO;
import cn.zeppin.entity.User;
import cn.zeppin.service.api.IUserService;

/** 
 * ClassName: UserService <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * date: 2014年10月20日 上午11:03:39 <br/> 
 * 
 * @author Administrator 
 * @version  
 * @since JDK 1.7 
 */
public class UserService implements IUserService {
	
	private IUserDAO userDAO;
	
	public IUserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(IUserDAO userDAO) {
		this.userDAO = userDAO;
	}

	/**
	 * 根据id来获取User
	 * @author Administrator
	 * @date: 2014年10月20日 上午11:11:38 <br/> 
	 * @param id
	 * @return
	 */
	public User getUserById(int id){
		
		return this.getUserDAO().get(id);
		
	}
	
	/**
	 * 根据Map来获取User
	 * @author Administrator
	 * @date: 2014年10月20日 上午11:11:42 <br/> 
	 * @param map
	 * @return
	 */
	public User getUserByMap(Map<String,Object> map){
		return this.getUserDAO().getUserByMap(map);
	}
	
	/**
	 * 更新用户信息
	 * @author Administrator
	 * @date: 2014年10月21日 下午3:21:10 <br/> 
	 * @param user
	 */
	public void updateUser(User user){
		this.getUserDAO().updateUser(user);
	}

	/**
	 * 根据Map来获取Count
	 * @author Administrator
	 * @date: 2014年10月21日 下午3:21:10 <br/> 
	 * @param map
	 */
	public int getUserCountByMap(Map<String,Object> map){
		return this.getUserDAO().getUserCountByMap( map);
	}
}
