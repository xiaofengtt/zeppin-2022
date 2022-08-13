/** 
 * Project Name:CETV_TEST 
 * File Name:ISubjectDAO.java 
 * Package Name:cn.zeppin.dao.api 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */
package cn.zeppin.dao.api;

import java.util.HashMap;
import java.util.List;

import cn.zeppin.entity.UserRank;

/**
 * ClassName: ISubjectDAO <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年6月10日 下午10:07:10 <br/>
 * 
 * @author Clark
 * @version
 * @since JDK 1.7
 */
public interface IUserRankDAO extends IBaseDAO<UserRank, Integer> {

	
	/**
	 * 根据名称获取UserRank
	 * @author Administrator
	 * @date: 2014年7月20日 下午5:18:29 <br/> 
	 * @param name
	 * @return
	 */
	UserRank getUserRankByName(String name);
	
	/**
	 * 获取学科Count
	 * @author Administrator
	 * @date: 2014年7月20日 下午7:16:13 <br/> 
	 * @return
	 */
	int getUserRankCount();

	/**
	 * 获取学科列表
	 * @author Administrator
	 * @date: 2014年7月20日 下午7:16:24 <br/> 
	 * @return
	 */
	List<UserRank> getUserRank(HashMap<String, Object> map,int offset, int length);
	
}
