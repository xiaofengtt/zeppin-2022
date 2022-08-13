/**
 * Copyright (c) 2014, Zeppin All Rights Reserved.
 * cn.zeppin.service.api
 * IStrategyService
 */
package cn.zeppin.service.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.Strategy;

/**
 * @author Clark
 * 下午2:33:07
 */
public interface IStrategyService {

	
	/**
	 * 按照条件查询学习策略
	 * @author Clark
	 * @date 2014年8月3日下午4:28:37
	 * @param searchMap
	 * @return
	 */
	List<Strategy> searchStrategy(Map<String, Object> searchMap);
	
	/**
	 * 按照条件查询学习策略数量
	 * @author Clark
	 * @date 2014年8月1日下午6:22:14
	 * @param searchMap
	 * @return count
	 */
	Integer searchStrategyCount(Map<String, Object> searchMap);
	
	/**
	 * 通过策略类型和知识点找到唯一的学习策略
	 * @author Clark
	 * @date 2014年8月3日下午3:10:13
	 * @param strategyTypeID
	 * @param knowledgeID
	 * @return Strategy
	 */
	Strategy getByUniqueKey1(Integer strategyTypeID, Integer knowledgeID);

	/**
	 * 通过策略类型和教材章节找到唯一的学习策略
	 * @author Clark
	 * @date 2014年8月3日下午3:10:13
	 * @param strategyTypeID
	 * @param textbookCapterID
	 * @return Strategy
	 */
	Strategy getByUniqueKey2(Integer strategyTypeID, Integer textbookCapterID);
	
	/**
	 * 通过ID获取学习策略
	 * @author Clark
	 * @date 2014年8月3日下午3:15:13
	 * @param strategyID
	 * @return
	 */
	Strategy getById(Integer strategyID);
	
	/**
	 * 添加学习策略
	 * @author Clark
	 * @date 2014年8月3日下午2:47:19
	 * @param strategy
	 * @return Strategy
	 */
	Strategy addStrategy(Strategy strategy);


	/**
	 * 更新学习策略
	 * @author Clark
	 * @date 2014年8月3日下午3:50:18
	 * @param strategy
	 * @return Strategy
	 */
	Strategy updateStrategy(Strategy strategy);

	/**
	 * 删除学习策略
	 * @author Clark
	 * @date 2014年8月3日下午4:23:45
	 * @param strategy
	 * @return Strategy
	 */ 
	Strategy deleteStrategy(Strategy strategy);


}
