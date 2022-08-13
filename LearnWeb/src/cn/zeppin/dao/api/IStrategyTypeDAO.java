/**
 * Copyright (c) 2014, Zeppin All Rights Reserved.
 * cn.zeppin.dao.api
 * IStrategyTypeDAO
 */
package cn.zeppin.dao.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.StrategyType;

/**
 * @author Clark
 * 下午2:29:19
 */
public interface IStrategyTypeDAO extends IBaseDAO<StrategyType, Integer> {

	/**
	 * 查询学习策略类型
	 * @author Clark
	 * @date 2014年8月1日下午3:22:00
	 * @param searchKey
	 * @return List<StrategyType>
	 */
	List<StrategyType> searchStrategyType(Map<String, Object> searchKey);

	/**
	 * 通过名称查询学习策略类型（主要用于判断重名）
	 * @author Clark
	 * @date 2014年8月1日下午5:39:47
	 * @param name
	 * @return
	 */
	StrategyType getByName(String name);

}
