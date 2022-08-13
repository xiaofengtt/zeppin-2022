/**
 * Copyright (c) 2014, Zeppin All Rights Reserved.
 * cn.zeppin.service.api
 * IStrategyTypeService
 */
package cn.zeppin.service.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.StrategyType;

/**
 * @author Clark
 * 下午2:36:17
 */
public interface IStrategyTypeService {

	/**
	 * 查询学习策略类型
	 * @author Clark
	 * @date 2014年8月1日下午3:22:00
	 * @param searchKey
	 * @return List<StrategyType>
	 */
	List<StrategyType> searchStrategyType(Map<String, Object> searchKey);

	/**
	 * 通过ID查询学习策略类型
	 * @author Clark
	 * @date 2014年8月1日下午6:08:45
	 * @param strategyTypeID
	 * @return
	 */
	StrategyType getById(Integer strategyTypeID);
	
	/**
	 * 通过名称查询学习策略类型（主要用于判断重名）
	 * @author Clark
	 * @date 2014年8月1日下午5:39:47
	 * @param name
	 * @return
	 */
	StrategyType getByName(String name);

	/**
	 * 添加学习策略类型
	 * @author Clark
	 * @date 2014年8月1日下午6:03:33
	 * @param strategyType
	 * @return
	 */
	StrategyType addStrategyType(StrategyType strategyType);

	/**
	 * 更新学习策略类型
	 * @author Clark
	 * @date 2014年8月1日下午6:16:54
	 * @param strategyType
	 * @return
	 */
	StrategyType updateStrategyType(StrategyType strategyType);

	/**
	 * 删除学习策略类型
	 * @author Clark
	 * @date 2014年8月1日下午6:16:54
	 * @param strategyType
	 * @return
	 */
	StrategyType deleteStrategyType(StrategyType strategyType);



	
}
