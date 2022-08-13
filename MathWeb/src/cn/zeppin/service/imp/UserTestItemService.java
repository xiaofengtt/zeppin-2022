/** 
 * Project Name:CETV_TEST 
 * File Name:UserTestService.java 
 * Package Name:cn.zeppin.service.imp 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */
package cn.zeppin.service.imp;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.IUserTestItemDAO;
import cn.zeppin.entity.UserTestItem;
import cn.zeppin.service.api.IUserTestItemService;

/**
 * ClassName: UserTestService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年10月21日 下午6:49:16 <br/>
 * 
 * @author Administrator
 * @version
 * @since JDK 1.7
 */
public class UserTestItemService implements IUserTestItemService {

	private IUserTestItemDAO userTestItemDAO;

	public IUserTestItemDAO getUserTestItemDAO() {
		return userTestItemDAO;
	}

	public void setUserTestItemDAO(IUserTestItemDAO userTestItemDAO) {
		this.userTestItemDAO = userTestItemDAO;
	}
	
	/**
	 * 添加用户做题记录
	 * @author Administrator
	 * @date: 2014年10月21日 下午7:16:01 <br/> 
	 * @param ut
	 */
	public void addUserTestItem(UserTestItem uti){
		this.getUserTestItemDAO().save(uti);
	}
	
	/**
	 * 获取个数
	 * @author Administrator
	 * @date: 2014年10月28日 上午11:49:25 <br/> 
	 * @param map
	 * @return
	 */
	public int getUserTestItemCount(Map<String,Object> map){
		return this.getUserTestItemDAO().getUserTestItemCount(map);
	}
	
	/**
	 * 获取列表
	 * @author Administrator
	 * @date: 2014年10月28日 上午11:49:36 <br/> 
	 * @param map
	 * @param sorts
	 * @param offset
	 * @param length
	 * @return
	 */
	public List<UserTestItem> getUserTestItem(Map<String,Object> map,String sorts,int offset,int length){
		
		return this.getUserTestItemDAO().getUserTestItem(map, sorts, offset, length);
		
	}
	
}
