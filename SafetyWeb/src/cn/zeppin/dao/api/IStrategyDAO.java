/**
 * Copyright (c) 2014, Zeppin All Rights Reserved.
 * cn.zeppin.dao.api
 * IStrategyDAO
 */
package cn.zeppin.dao.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.Strategy;

/**
 * @author Clark
 * 下午2:31:32
 */
public interface IStrategyDAO extends IBaseDAO<Strategy, Integer> {

	/**
	 * 符合条件的学科策略数量
	 * @author Clark
	 * @date 2014年8月3日上午10:44:53
	 * @param searchMap
	 * @return count
	 */
	Integer searchStrategyCount(Map<String, Object> searchMap);

	/**
	 * 按条件搜索学科策略
	 * @author Clark
	 * @date 2014年8月3日上午10:44:53
	 * @param searchMap
	 * @return count
	 */
	List<Strategy> searchStrategy(Map<String, Object> searchMap);

	
}
