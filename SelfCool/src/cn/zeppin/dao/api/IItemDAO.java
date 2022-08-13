/** 
 * Project Name:Self_Cool  
 * File Name:IItemDAO.java 
 * Package Name:cn.zeppin.dao.api 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */  
package cn.zeppin.dao.api;


import java.util.List;
import java.util.Map;

import cn.zeppin.entity.Item;
import cn.zeppin.entity.ItemType;
import cn.zeppin.entity.SsoUser;
import cn.zeppin.entity.Subject;

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
	 * 按照条件查询试卷下的题目
	 * @param paperId
	 * @return
	 */
	public List<Item> searchItemForPaper(int paperId);
	
	
	public Map<String,Object> selectUserSubjectItemCount(Map<String,Object> searchMap);
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
	 * 获取下级所有试题
	 * @author jiangfei
	 * @date: 2014年9月2日 下午12:03:55 <br/> 
	 * @param item
	 * @return
	 */
	public List<Item> getItemsByItem(int item);

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
	public List<Map<String, Object>> selectItems(Map<String, Object> searchMap,int defaultItemNumber);


	/**
	 * 通过学科、题型、用户获取试题相关信息
	 * 
	 * @param currentUser
	 * @param subject
	 * @param itemType
	 * @return
	 */
	public List<Map<String, Object>> searchItems(SsoUser currentUser, Subject subject, ItemType itemType);


	/**
	 * 按条件查询试题数量并按题型分组
	 * 
	 * @author Clark
	 * @date 2014年7月29日上午10:35:25
	 * @param searchMap
	 * @return List<count>
	 */
	public List<Map<String, Object>> searchItemCountGroupByItemType(Map<String, Object> searchMap);
	
	/**
	 * 修改一道试题状态
	 * @author Administrator
	 * @date: 2014年8月20日 上午11:47:05 <br/> 
	 * @param item
	 * @param answers
	 */
	public void updateItemStatus(Item item);
	
}
