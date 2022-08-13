/** 
 * Project Name:CETV_TEST 
 * File Name:ItemAnswerDAO.java 
 * Package Name:cn.zeppin.dao.imp 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */
package cn.zeppin.dao.imp;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.IItemAnswerDAO;
import cn.zeppin.dao.base.HibernateTemplateDAO;
import cn.zeppin.entity.ItemAnswer;

/**
 * ClassName: ItemAnswerDAO <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年6月10日 下午9:23:27 <br/>
 * 
 * @author Clark
 * @version
 * @since JDK 1.7
 */
public class ItemAnswerDAO extends HibernateTemplateDAO<ItemAnswer, Integer> implements IItemAnswerDAO {

	/**
	 * 删除ItemAnswer
	 * 
	 * @author Administrator
	 * @date: 2014年8月20日 上午11:52:54 <br/>
	 * @param itemID
	 */
	@Override
	public void deleteItemAnswerByItemId(int itemID) {

		StringBuilder sb = new StringBuilder();
		sb.append("delete from ItemAnswer ia ");
		sb.append(" where ia.item=").append(itemID);

		this.executeHQL(sb.toString());

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
		StringBuilder sb = new StringBuilder();
		sb.append(" from ItemAnswer ia where 1=1 ");
		if (searchMap.get("item.id") != null && !searchMap.get("item.id").equals("")) {
			sb.append(" and ia.item=").append(searchMap.get("item.id"));
		}
		sb.append(" group by ia.inx");
		return this.getByHQL(sb.toString());
	}
}
