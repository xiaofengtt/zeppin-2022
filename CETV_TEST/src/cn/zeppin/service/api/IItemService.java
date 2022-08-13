/**
 * Copyright (c) 2014, Zeppin All Rights Reserved.
 * cn.zeppin.service.api
 * IItemService
 */
package cn.zeppin.service.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.Item;
import cn.zeppin.entity.ItemAnswer;

/**
 * @author Clark 上午10:11:25
 */
public interface IItemService {

	public Item getItemById(int id);

	/**
	 * 按条件查询试题列表
	 * 
	 * @author Clark
	 * @date: 2014年7月23日 下午4:09:44 <br/>
	 * @param searchMap
	 * @param sorts
	 * @param offset
	 * @param pagesize
	 * @return List<Item>
	 */
	public List<Item> searchItem(Map<String, Object> searchMap, String sorts, int offset, int pagesize);

	
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
	 * 按条件查询试题数量
	 * 
	 * @author Clark
	 * @date 2014年7月29日上午10:35:25
	 * @param searchMap
	 * @return count
	 */
	public int searchItemCount(Map<String, Object> searchMap);

	/**
	 * 按条件查询试题答案
	 * 
	 * @author Clark
	 * @date 2014年7月29日上午10:35:25
	 * @param searchMap
	 * @return ItemAnswerList
	 */
	public List<ItemAnswer> getItemAnswerByParam(Map<String, Object> searchMap);

	/**
	 * 添加一道试题
	 * @author Administrator
	 * @date: 2014年8月19日 下午12:02:01 <br/> 
	 * @param item
	 * @param answers
	 */
	public void addItem(Item item, List<ItemAnswer> answers);
	
	/**
	 * 修改一道试题
	 * @author Administrator
	 * @date: 2014年8月20日 上午11:47:05 <br/> 
	 * @param item
	 * @param answers
	 */
	public void updateItem(Item item,List<ItemAnswer> answers);
	
	/**
	 * 修改一道试题
	 * @author Administrator
	 * @date: 2014年8月20日 上午11:47:05 <br/> 
	 * @param item
	 * @param answers
	 */
	public void updateItem(Item item);
	
	
	/**
	 * 获取下级所有试题
	 * @author jiangfei
	 * @date: 2014年9月2日 下午12:03:55 <br/> 
	 * @param item
	 * @return
	 */
	public List<Item> getItemsByItem(int item);
}
