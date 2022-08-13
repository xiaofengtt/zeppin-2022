/** 
 * Project Name:CETV_TEST 
 * File Name:IUserKnowledgeDegreeDAO.java 
 * Package Name:cn.zeppin.dao.api 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */
package cn.zeppin.dao.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.UserKnowledgeDegree;

/**
 * ClassName: IUserKnowledgeDegreeDAO <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年10月13日 下午4:58:17 <br/>
 * 
 * @author Administrator
 * @version
 * @since JDK 1.7
 */
public interface IUserKnowledgeDegreeDAO extends IBaseDAO<UserKnowledgeDegree, Long> {
	
	/**
	 * 添加
	 * @author Administrator
	 * @date: 2014年10月26日 下午5:18:20 <br/> 
	 * @param ukld
	 */
	public void addUserKnowledgeDegree(UserKnowledgeDegree ukld);
	
	/**
	 * 计算count
	 * @author Administrator
	 * @date: 2014年10月23日 下午6:08:06 <br/> 
	 * @param map
	 * @return
	 */
	public int getUserKnowledgeDegreeCount(Map<String,Object> map);
	
	
	/**
	 * 获取
	 * @author Administrator
	 * @date: 2014年10月23日 下午6:08:44 <br/> 
	 * @param map
	 * @param sort
	 * @param offset
	 * @param length
	 * @return
	 */
	public List<UserKnowledgeDegree> getUserKnowledgeDegree(Map<String,Object> map,String sort,int offset,int length);

}
