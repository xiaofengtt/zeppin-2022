/** 
 * Project Name:CETV_TEST 
 * File Name:SubjectService.java 
 * Package Name:cn.zeppin.service.imp 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */
package cn.zeppin.service.imp;

import java.util.HashMap;
import java.util.List;

import cn.zeppin.dao.api.IUserRankDAO;
import cn.zeppin.entity.UserRank;
import cn.zeppin.service.api.IUserRankService;

/**
 * ClassName: IUserRankService <br/>
 * date: 2014年7月20日 下午5:04:08 <br/>
 * 
 * @author Administrator
 * @version
 * @since JDK 1.7
 */
public class UserRankService implements IUserRankService {

	private IUserRankDAO userRankDAO;

	public IUserRankDAO getUserRankDAO() {
		return userRankDAO;
	}

	public void setUserRankDAO(IUserRankDAO userRankDAO) {
		this.userRankDAO = userRankDAO;
	}
	
	/**
	 * 根据id来获取UserRank
	 * 
	 * @author Administrator
	 * @date: 2014年7月20日 下午5:04:58 <br/>
	 * @param id
	 * @return
	 */
	@Override
	public UserRank getUserRankById(int id) {
		return this.getUserRankDAO().get(id);
	}

	/**
	 * 根据Name来获取UserRank
	 * 
	 * @author Administrator
	 * @date: 2014年7月20日 下午5:05:22 <br/>
	 * @param name
	 * @return
	 */
	@Override
	public UserRank getUserRankByName(String name) {
		return this.getUserRankDAO().getUserRankByName(name);
	}

	/**
	 * 添加用户等级
	 * 
	 * @author Administrator
	 * @date: 2014年7月20日 下午5:11:15 <br/>
	 * @param userRank
	 */
	@Override
	public void addUserRank(UserRank userRank) {
		this.getUserRankDAO().save(userRank);
	}

	/**
	 * 更新用户等级
	 * 
	 * @author Administrator
	 * @date: 2014年7月20日 下午5:09:46 <br/>
	 * @param userRank
	 */
	@Override
	public void updateUserRank(UserRank userRank) {
		this.getUserRankDAO().update(userRank);
	}

	/**
	 * 获取用户等级Count
	 * 
	 * @author Administrator
	 * @date: 2014年7月20日 下午7:16:13 <br/>
	 * @param params
	 * @return
	 */
	@Override
	public int getUserRankCount() {
		return this.getUserRankDAO().getUserRankCount();
	}

	/**
	 * 获取用户等级列表
	 * 
	 * @author Administrator
	 * @date: 2014年7月20日 下午7:16:24 <br/>
	 * @return
	 */
	@Override
	public List<UserRank> getUserRank(HashMap<String, Object> map,int offset, int length) {
		return this.getUserRankDAO().getUserRank(map,offset, length);
	}
	
	@Override
	public void delete(UserRank userRank){
		this.getUserRankDAO().delete(userRank);
	}
}
