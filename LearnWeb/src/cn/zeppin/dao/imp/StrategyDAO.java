/**
 * Copyright (c) 2014, Zeppin All Rights Reserved.
 * cn.zeppin.dao.imp
 * StrategyDAO
 */
package cn.zeppin.dao.imp;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.IStrategyDAO;
import cn.zeppin.dao.base.HibernateTemplateDAO;
import cn.zeppin.entity.Strategy;

/**
 * @author Clark
 * 下午2:32:18
 */
public class StrategyDAO extends HibernateTemplateDAO<Strategy, Integer> implements IStrategyDAO{

	/**
	 * 按条件搜索学科策略
	 * @author Clark
	 * @date 2014年8月3日上午10:44:53
	 * @param searchMap
	 * @return count
	 */
	@Override
	public Integer searchStrategyCount(Map<String, Object> searchMap) {
		// TODO Auto-generated method stub
		StringBuilder hql = new StringBuilder();
		hql.append(" select count(*) from Strategy where 1=1 ");
		if (searchMap.get("strategyType.id") != null && ! searchMap.get("strategyType.id").equals("")) {
			hql.append(" and strategyType.id=").append(searchMap.get("strategyType.id"));
		}
		if (searchMap.get("knowledge.id") != null && ! searchMap.get("knowledge.id").equals("")) {
			hql.append(" and knowledge.id=").append(searchMap.get("knowledge.id"));
		}
		if (searchMap.get("textbookCapter.id") != null && ! searchMap.get("textbookCapter.id").equals("")) {
			hql.append(" and textbookCapter.id=").append(searchMap.get("textbookCapter.id"));
		}
		if (searchMap.get("content") != null && ! searchMap.get("content").equals("")) {
			hql.append(" and content like '%").append(searchMap.get("content")).append("%' ");
		}
		return ((Long)this.getResultByHQL(hql.toString())).intValue();
	}

	/**
	 * 按照条件查询学习策略
	 * @author Clark
	 * @date 2014年8月3日下午4:28:37
	 * @param searchMap
	 * @return
	 */
	@Override
	public List<Strategy> searchStrategy(Map<String, Object> searchMap) {
		// TODO Auto-generated method stub
		StringBuilder hql = new StringBuilder();
		hql.append(" from Strategy where 1=1 ");
		if (searchMap.get("strategyType.id") != null && ! searchMap.get("strategyType.id").equals("")) {
			hql.append(" and strategyType.id=").append(searchMap.get("strategyType.id"));
		}
		if (searchMap.get("knowledge.id") != null && ! searchMap.get("knowledge.id").equals("")) {
			hql.append(" and knowledge.id=").append(searchMap.get("knowledge.id"));
		}
		if (searchMap.get("textbookCapter.id") != null && ! searchMap.get("textbookCapter.id").equals("")) {
			hql.append(" and textbookCapter.id=").append(searchMap.get("textbookCapter.id"));
		}
		if (searchMap.get("content") != null && ! searchMap.get("content").equals("")) {
			hql.append(" and content like '%").append(searchMap.get("content")).append("%' ");
		}
		hql.append(" order by strategyType.id");
		return this.getByHQL(hql.toString());
	}

}
