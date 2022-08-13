/** 
 * Project Name:CETV_TEST 
 * File Name:IUserRankService.java 
 * Package Name:cn.zeppin.service.api 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */
package cn.zeppin.service.api;

import java.util.HashMap;
import java.util.List;

import cn.zeppin.entity.UserRank;

/**
 * ClassName: IUserRankService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年7月20日 下午5:04:08 <br/>
 * 
 * @author Administrator
 * @version
 * @since JDK 1.7
 */
public interface IUserRankService {

	/**
	 * 根据id来获取UserRank
	 * 
	 * @author Administrator
	 * @date: 2014年7月20日 下午5:04:58 <br/>
	 * @param id
	 * @return
	 */
	UserRank getUserRankById(int id);
	
	/**
	 * 根据Name来获取UserRank
	 * 
	 * @author Administrator
	 * @date: 2014年7月20日 下午5:05:22 <br/>
	 * @param name
	 * @return
	 */
	UserRank getUserRankByName(String name);

	/**
	 * 添加用户等级
	 * 
	 * @author Administrator
	 * @date: 2014年7月20日 下午5:11:15 <br/>
	 * @param userRank
	 */
	void addUserRank(UserRank userRank);

	/**
	 * 更新用户等级
	 * 
	 * @author Administrator
	 * @date: 2014年7月20日 下午5:09:46 <br/>
	 * @param userRank
	 */
	void updateUserRank(UserRank userRank);

	/**
	 * 获取用户等级Count
	 * 
	 * @author Administrator
	 * @date: 2014年7月20日 下午7:16:13 <br/>
	 * @return
	 */
	int getUserRankCount();

	/**
	 * 获取用户等级列表
	 * 
	 * @author Administrator
	 * @date: 2014年7月20日 下午7:16:24 <br/>
	 * @param params
	 * @return
	 */
	List<UserRank> getUserRank(HashMap<String, Object> map,int offset, int length);

	
	void delete(UserRank userRank);
}
