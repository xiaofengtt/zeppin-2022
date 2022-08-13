/**
 * Copyright (c) 2014, Zeppin All Rights Reserved.
 * cn.zeppin.dao.imp
 * StrategyTypeDAO
 */
package cn.zeppin.dao.imp;


import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.IStrategyTypeDAO;
import cn.zeppin.dao.base.HibernateTemplateDAO;
import cn.zeppin.entity.StrategyType;

/**
 * @author Clark
 * 下午2:30:32
 */
public class StrategyTypeDAO extends HibernateTemplateDAO<StrategyType, Integer> implements IStrategyTypeDAO {

	/**
	 * 查询学习策略类型
	 * @author Clark
	 * @date 2014年8月1日下午3:22:00
	 * @param searchKey
	 * @return List<StrategyType>
	 */
	@Override
	public List<StrategyType> searchStrategyType(Map<String, Object> searchKey) {
		// TODO Auto-generated method stub
		StringBuilder hql = new StringBuilder();
		hql.append(" from StrategyType where 1=1 ");
		if (searchKey.get("id") != null && ! searchKey.get("id").equals("")){
			hql.append(" and id=").append(searchKey.get("id"));
		}
		if (searchKey.get("name") != null && ! searchKey.get("name").equals("")){
			hql.append(" and name like '%").append(searchKey.get("name")).append("%' ");
		}
		return this.getByHQL(hql.toString());
	}

	/**
	 * 通过名称查询学习策略类型（主要用于判断重名）
	 * @author Clark
	 * @date 2014年8月1日下午5:39:47
	 * @param name
	 * @return
	 */
	@Override
	public StrategyType getByName(String name) {
		// TODO Auto-generated method stub
		StringBuilder hql = new StringBuilder();
		hql.append(" from StrategyType where name='").append(name).append("'");
		return (StrategyType) this.getResultByHQL(hql.toString());
	}


}
