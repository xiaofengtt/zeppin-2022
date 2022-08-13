/** 
 * Project Name:CETV_TEST 
 * File Name:IUserTextbookcapterDegreeDAO.java 
 * Package Name:cn.zeppin.dao.api 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */  
package cn.zeppin.dao.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.UserTextbookcapterDegree;

/** 
 * ClassName: IUserTextbookcapterDegreeDAO <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * date: 2014年10月13日 下午5:29:18 <br/> 
 * 
 * @author Administrator 
 * @version  
 * @since JDK 1.7 
 */
public interface IUserTextbookcapterDegreeDAO extends IBaseDAO<UserTextbookcapterDegree, Long> {
	
	/**
	 * 添加
	 * @author Administrator
	 * @date: 2014年10月23日 下午3:01:21 <br/> 
	 * @param utbDegree
	 */
	public void addUserTextbookcapterDegree(UserTextbookcapterDegree utbDegree);
	
	/**
	 * 计算数量
	 * @author Administrator
	 * @date: 2014年10月23日 下午3:01:32 <br/> 
	 * @param map
	 * @return
	 */
	public int getUserTextbookcapterDegreeCount(Map<String, Object> map);

	/**
	 * 获取列表
	 * @author Administrator
	 * @date: 2014年10月23日 下午3:01:46 <br/> 
	 * @param map
	 * @param sort
	 * @param offset
	 * @param length
	 * @return
	 */
	public List<UserTextbookcapterDegree> getUserTextbookcapterDegreeByMap(Map<String, Object> map, String sort, int offset, int length);
	
	/**
	 * by id 
	 * @author Administrator
	 * @date: 2014年10月23日 下午3:01:57 <br/> 
	 * @param id
	 * @return
	 */
	public UserTextbookcapterDegree getUserTextbookcapterDegreeById(Long id);
	
}
