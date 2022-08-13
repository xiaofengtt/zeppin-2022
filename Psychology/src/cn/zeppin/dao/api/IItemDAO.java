/** 
 * Project Name:CETV_TEST 
 * File Name:IItemDAO.java 
 * Package Name:cn.zeppin.dao.api 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */  
package cn.zeppin.dao.api;


import java.util.List;
import java.util.Map;

import cn.zeppin.entity.Item;

/** 
 * ClassName: IItemDAO <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * date: 2014年6月10日 下午9:27:18 <br/> 
 * 
 * @author Clark 
 * @version  
 * @since JDK 1.7 
 */
public interface IItemDAO extends IBaseDAO<Item, Integer> {

	/**
	 * 按条件查询试题数量
	 * @author Clark
	 * @date 2014年7月29日上午10:35:25
	 * @param searchMap
	 * @return count
	 */
	public int searchItemCount(Map<String, Object> searchMap);

	/**
	 * 按条件查询试题列表
	 * @author Clark
	 * @date: 2014年7月23日 下午4:09:44 <br/> 
	 * @param searchMap
	 * @param sorts
	 * @param offset
	 * @param pagesize
	 * @return List<Item>
	 */
	public List<Item> searchItem(Map<String, Object> searchMap, String sorts,
			int offset, int pagesize);

	/**
	 * 按条件查询试题列表
	 * 
	 * @author Clark
	 * @date: 2014年7月23日 下午4:09:44 <br/>
	 * @param searchMap
	 * @return List<Item>
	 */
	public List<Item> getItemsForPhone(Map<String, Object> searchMap);
	
	/**
	 * 获取下级所有试题
	 * @author jiangfei
	 * @date: 2014年9月2日 下午12:03:55 <br/> 
	 * @param item
	 * @return
	 */
	public List<Item> getItemsByItem(int item);
	
}
