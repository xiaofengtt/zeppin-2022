/** 
 * Project Name:ItemDatabase 
 * File Name:IItemTypeDAO.java 
 * Package Name:cn.zeppin.dao.api 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */  
package cn.zeppin.dao.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.ItemType;

/** 
 * ClassName: IItemTypeDAO <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * date: 2014年6月10日 下午9:29:34 <br/> 
 * 
 * @author Clark 
 * @version  
 * @since JDK 1.7 
 */
public interface IItemTypeDAO extends IBaseDAO<ItemType, Integer> {

	/**
	 * 通过名称获取题型（主要用于对重名做判断）
	 * @author Clark
	 * @date 2014年7月29日上午9:16:01
	 * @param name
	 * @return itemType
	 */
	public ItemType getItemTypeByName(String name);
	
	/**
	 * 通过条件查询题型列表
	 * @author Clark
	 * @date 2014年7月29日上午11:09:14
	 * @param searchMap
	 * @param sorts
	 * @param offset
	 * @param pagesize
	 * @return List<ItemType>
	 */
	public List<ItemType> searchItemType(Map<String, Object> searchMap,
			String sorts, int offset, int pagesize);

	/**
	 * 通过条件查询题型数量
	 * @author Clark
	 * @date 2014年7月29日上午11:09:55
	 * @param searchMap
	 * @return count
	 */
	public int searchItemTypeCount(Map<String, Object> searchMap);
	
}
