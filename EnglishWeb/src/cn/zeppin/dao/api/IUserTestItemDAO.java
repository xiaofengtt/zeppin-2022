/** 
 * Project Name:CETV_TEST 
 * File Name:IUserTestItemDAO.java 
 * Package Name:cn.zeppin.dao.api 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */  
package cn.zeppin.dao.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.UserTestItem;

/** 
 * ClassName: IUserTestItemDAO <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * date: 2014年10月13日 下午5:27:11 <br/> 
 * 
 * @author Administrator 
 * @version  
 * @since JDK 1.7 
 */
public interface IUserTestItemDAO extends IBaseDAO<UserTestItem, Long> {
	/**
	 * 添加用户做题记录
	 * 
	 * @author Administrator
	 * @date: 2014年10月21日 下午7:16:01 <br/>
	 * @param uti
	 */
	public void addUserTestItem(UserTestItem uti);
	
	/**
	 * 获取个数
	 * @author Administrator
	 * @date: 2014年10月28日 上午11:49:25 <br/> 
	 * @param map
	 * @return
	 */
	public int getUserTestItemCount(Map<String,Object> map);
	
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
	public List<UserTestItem> getUserTestItem(Map<String,Object> map,String sorts,int offset,int length);
	
}
