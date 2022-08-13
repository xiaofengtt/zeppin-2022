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
import cn.zeppin.entity.ItemType;
import cn.zeppin.entity.SsoUser;
import cn.zeppin.entity.Subject;
import cn.zeppin.service.api.IItemService;

/**
 * @author Clark 上午10:12:30
 */
public class ItemService implements IItemService {

	private IItemDAO itemDAO;
	private IItemAnswerDAO itemAnswerDAO;

	public Map<String,Object> selectUserSubjectItemCount(Map<String,Object> searchMap)
	{
		return this.itemDAO.selectUserSubjectItemCount(searchMap);
	}
	
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
	 * 按条件查询试题数量并按题型分组
	 * 
	 * @author Clark
	 * @date 2014年7月29日上午10:35:25
	 * @param searchMap
	 @return List<count>
	 */
	@Override
	public  List<Map<String, Object>> searchItemCountGroupByItemType(Map<String, Object> searchMap) {
		// TODO Auto-generated method stub
		return this.getItemDAO().searchItemCountGroupByItemType(searchMap);
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
		}
		//else{
		//	item.setDefaultScore(0);
		//	this.getItemDAO().update(item);
		//}

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
		// 2.更新Item
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

		this.getItemDAO().merge(item);

		if (answers != null) {
			int score = 0;
			for (ItemAnswer answer : answers) {
				score += answer.getScore();
				answer.setItem(item);
				this.getItemAnswerDAO().save(answer);
			}
			item.setDefaultScore(score);
			this.getItemDAO().merge(item);
		}
	}

	/**
	 * 修改一道试题状态
	 * @author Administrator
	 * @date: 2014年8月20日 上午11:47:05 <br/> 
	 * @param item
	 * @param answers
	 */
	public void updateItemStatus(Item item){
		this.getItemDAO().updateItemStatus(item);
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

	
	/**
	 * 为某个用户按学科知识点智能进行出题（选择哪些题让用户做和他们的排序，是一个非常最复杂的算法逻辑，需要考虑的因素比较多，需要理解后进行修改） 
	 * 设计思路：
	 * 首先应该计算用户该知识点下做过的题的次数以及正确的次数，根据选择最少正确次数的题的原则，在集合中进行随机出题
	 * ***暂时不考虑找出的试题不够数量的情况，暂时不考虑标准化组合题的情况（如阅读理解） 
	 * Ver1.0只考虑单选题，因为都是单选题，所以也不需要太复杂的算法逻辑
	 * @param searchMap
	 * @param defaultItemNumber
	 * @return  List<Item>
	 */
	@Override
	public List<Map<String, Object>> selectItems(Map<String, Object> searchMap, int defaultItemNumber) {
		// TODO Auto-generated method stub
		return this.getItemDAO().selectItems(searchMap, defaultItemNumber);
	}

	@Override
	public List<Item> getItemByPaper(int paperId) {
		// TODO Auto-generated method stub
		return this.getItemDAO().searchItemForPaper(paperId);
	}

	
	/**
	 * 通过学科、题型、用户获取试题相关信息
	 * 
	 * @param currentUser
	 * @param subject
	 * @param itemType
	 * @return
	 */
	@Override
	public List<Map<String, Object>> searchItems(SsoUser currentUser, Subject subject, ItemType itemType) {
		// TODO Auto-generated method stub
		return this.getItemDAO().searchItems(currentUser, subject, itemType);
	}

}
