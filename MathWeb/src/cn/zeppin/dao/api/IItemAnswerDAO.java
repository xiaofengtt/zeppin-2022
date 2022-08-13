/** 
 * Project Name:CETV_TEST 
 * File Name:IItemAnswerDAO.java 
 * Package Name:cn.zeppin.dao.api 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */
package cn.zeppin.dao.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.ItemAnswer;

/**
 * ClassName: IItemAnswerDAO <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年6月10日 下午9:23:55 <br/>
 * 
 * @author Clark
 * @version
 * @since JDK 1.7
 */
public interface IItemAnswerDAO extends IBaseDAO<ItemAnswer, Integer> {

	/**
	 * 删除ItemAnswer
	 * @author Administrator
	 * @date: 2014年8月20日 上午11:52:54 <br/> 
	 * @param itemID
	 */
	public void deleteItemAnswerByItemId(int itemID);
	
	/**
	 * 按条件查询试题答案
	 * 
	 * @author Clark
	 * @date 2014年7月29日上午10:35:25
	 * @param searchMap
	 * @return ItemAnswerList
	 */
	public List<ItemAnswer> getItemAnswerByParam(Map<String, Object> searchMap);

}
