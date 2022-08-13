/** 
 * Project Name:Self_Cool 
 * File Name:IItemTypeService.java 
 * Package Name:cn.zeppin.service.api 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */  
package cn.zeppin.service.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.ItemType;

/** 
 * ClassName: IItemTypeService <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * date: 2014年7月14日 下午12:48:10 <br/> 
 * 
 * @author Administrator 
 * @version  
 * @since JDK 1.7 
 */
public interface IItemTypeService {
	
	/**
	 * 添加题型信息
	 * @author Administrator
	 * @date: 2014年7月14日 下午12:56:25 <br/> 
	 * @param itemType
	 */
	public void addItemType(ItemType itemType);

	/**
	 * 通过名称获取题型（主要用于对重名做判断）
	 * @author Clark
	 * @date 2014年7月29日上午9:16:01
	 * @param name
	 * @return itemType
	 */
	public ItemType getItemTypeByName(String name);

	/**
	 * 通过ID获取题型
	 * @author Clark
	 * @date 2014年7月29日上午9:16:11
	 * @param typeID
	 * @return
	 */
	public ItemType getItemTypeById(Integer typeID);

	/**
	 * 修改题型信息
	 * @author Clark
	 * @date 2014年7月29日上午9:16:21
	 * @param itemType
	 * @return
	 */
	public void updateItemType(ItemType itemType);

	/**
	 * 删除题型信息
	 * @author Clark
	 * @date 2014年7月29日上午10:00:46
	 * @param itemType
	 * @return
	 */
	public void deleteItemType(ItemType itemType);

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
