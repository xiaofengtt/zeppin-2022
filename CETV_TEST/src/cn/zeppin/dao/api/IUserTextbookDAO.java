/** 
 * Project Name:CETV_TEST 
 * File Name:IUserTextbookDAO.java 
 * Package Name:cn.zeppin.dao.api 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */  
package cn.zeppin.dao.api;

import java.util.Map;

import cn.zeppin.entity.UserTextbook;

/** 
 * ClassName: IUserTextbookDAO <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * date: 2014年10月13日 下午5:28:32 <br/> 
 * 
 * @author Administrator 
 * @version  
 * @since JDK 1.7 
 */
public interface IUserTextbookDAO extends IBaseDAO<UserTextbook, Integer>{
	
	/**
	 * 搜索UserTextbook
	 * @author Administrator
	 * @date: 2014年10月21日 下午3:44:41 <br/> 
	 * @param map
	 * @return
	 */
	public UserTextbook getUserTextbookByMap(Map<String,Object> map);
	
	/**
	 * 添加UserTextbook
	 * @author Administrator
	 * @date: 2014年10月21日 下午3:44:56 <br/> 
	 * @param book
	 */
	public void addUserTextbook(UserTextbook book);
	
	
	/**
	 * 删除
	 * @author Administrator
	 * @date: 2014年10月21日 下午3:45:10 <br/> 
	 * @param book
	 */
	public void deleteUserTextbook(UserTextbook book);
	
}
