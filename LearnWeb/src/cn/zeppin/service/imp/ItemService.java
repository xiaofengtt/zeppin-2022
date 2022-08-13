/**
 * Copyright (c) 2014, Zeppin All Rights Reserved.
 * cn.zeppin.service.imp
 * ItemService
 */
package cn.zeppin.service.imp;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.IItemAnswerDAO;
import cn.zeppin.dao.api.IItemDAO;
import cn.zeppin.entity.Item;
import cn.zeppin.entity.ItemAnswer;
import cn.zeppin.service.api.IItemService;

/**
 * @author Clark 上午10:12:30
 */
public class ItemService implements IItemService {

	private IItemDAO itemDAO;
	private IItemAnswerDAO itemAnswerDAO;

	public IItemDAO getItemDAO() {
		return itemDAO;
	}

	public void setItemDAO(IItemDAO itemDAO) {
		this.itemDAO = itemDAO;
	}

	public IItemAnswerDAO getItemAnswerDAO() {
		return itemAnswerDAO;
	}

	public void setItemAnswerDAO(IItemAnswerDAO itemAnswerDAO) {
		this.itemAnswerDAO = itemAnswerDAO;
	}

	@Override
	public Item getItemById(int id) {
		return this.getItemDAO().get(id);
	}

	/**
	 * 按条件查询知识点列表
	 * 
	 * @author Clark
	 * @date: 2014年7月23日 下午4:09:44 <br/>
	 * @param searchMap
	 * @param sorts
	 * @param offset
	 * @param pagesize
	 * @return List<Item>
	 */
	@Override
	public List<Item> searchItem(Map<String, Object> searchMap, String sorts, int offset, int pagesize) {
		// TODO Auto-generated method stub
		return this.getItemDAO().searchItem(searchMap, sorts, offset, pagesize);
	}
	
	/**
	 * 按条件查询试题答案
	 * 
	 * @author Clark
	 * @date 2014年7月29日上午10:35:25
	 * @param searchMap
	 * @return ItemAnswerList
	 */
	@Override
	public List<ItemAnswer> getItemAnswerByParam(Map<String, Object> searchMap){
		return this.getItemAnswerDAO().getItemAnswerByParam(searchMap);
	}
	
	/**
	 * 按条件查询试题列表
	 * 
	 * @author Clark
	 * @date: 2014年7月23日 下午4:09:44 <br/>
	 * @param searchMap
	 * @return List<Item>
	 */
	@Override
	public List<Item> getItemsForPhone(Map<String, Object> searchMap){
		return this.getItemDAO().getItemsForPhone(searchMap);
	}
	
	/**
	 * 按条件查询试题数量
	 * 
	 * @author Clark
	 * @date 2014年7月29日上午10:35:25
	 * @param searchMap
	 * @return count
	 */
	@Override
	public int searchItemCount(Map<String, Object> searchMap) {
		// TODO Auto-generated method stub
		return this.getItemDAO().searchItemCount(searchMap);
	}

	/**
	 * 添加一道试题
	 * 
	 * @author Administrator
	 * @date: 2014年8月19日 下午12:02:01 <br/>
	 * @param item
	 * @param answers
	 */
	@Override
	public void addItem(Item item, List<ItemAnswer> answers) {

		this.getItemDAO().save(item);

		if (answers != null) {
			int score = 0;
			for (ItemAnswer answer : answers) {
				score += answer.getScore();
				answer.setItem(item);
				this.getItemAnswerDAO().save(answer);
			}
			item.setDefaultScore(score);
			this.getItemDAO().update(item);
		}

	}

	/**
	 * 修改一道试题
	 * 
	 * @author Administrator
	 * @date: 2014年8月20日 上午11:47:05 <br/>
	 * @param item
	 * @param answers
	 */
	@Override
	public void updateItem(Item item, List<ItemAnswer> answers) {
		// ====================================
		// 1.先删除ItemAnswer和所有下级试题
		// 2.跟新Item
		// 3.添加ItemAnswer
		// ====================================

		this.getItemAnswerDAO().deleteItemAnswerByItemId(item.getId());

		List<Item> childItems = this.getItemsByItem(item.getId());
		if (childItems != null && childItems.size() > 0) {
			for (Item child : childItems) {
				// 删除Answer
				this.getItemAnswerDAO().deleteItemAnswerByItemId(child.getId());
				this.getItemDAO().delete(child);
			}
		}

		this.getItemDAO().update(item);

		if (answers != null) {
			int score = 0;
			for (ItemAnswer answer : answers) {
				score += answer.getScore();
				answer.setItem(item);
				this.getItemAnswerDAO().save(answer);
			}
			item.setDefaultScore(score);
			this.getItemDAO().update(item);
		}
	}

	/**
	 * 修改一道试题
	 * 
	 * @author Administrator
	 * @date: 2014年8月20日 上午11:47:05 <br/>
	 * @param item
	 * @param answers
	 */
	@Override
	public void updateItem(Item item) {
		this.getItemDAO().update(item);
	}

	/**
	 * 获取下级所有试题
	 * 
	 * @author jiangfei
	 * @date: 2014年9月2日 下午12:03:55 <br/>
	 * @param item
	 * @return
	 */
	public List<Item> getItemsByItem(int item) {
		return this.getItemDAO().getItemsByItem(item);
	}

}
