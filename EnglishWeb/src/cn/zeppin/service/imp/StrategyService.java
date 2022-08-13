/**
 * Copyright (c) 2014, Zeppin All Rights Reserved.
 * cn.zeppin.service.imp
 * StrategyService
 */
package cn.zeppin.service.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.IStrategyDAO;
import cn.zeppin.entity.Strategy;
import cn.zeppin.service.api.IStrategyService;

/**
 * @author Clark
 * 下午2:33:38
 */
public class StrategyService implements IStrategyService {
	
	private IStrategyDAO strategyDAO;

	public IStrategyDAO getStrategyDAO() {
		return strategyDAO;
	}

	public void setStrategyDAO(IStrategyDAO strategyDAO) {
		this.strategyDAO = strategyDAO;
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
		return this.getStrategyDAO().searchStrategy(searchMap);
	}
	
	/**
	 * 按照条件查询学习策略数量
	 * @author Clark
	 * @date 2014年8月1日下午6:22:14
	 * @param searchMap
	 * @return count
	 */
	@Override
	public Integer searchStrategyCount(Map<String, Object> searchMap) {
		// TODO Auto-generated method stub
		return this.getStrategyDAO().searchStrategyCount(searchMap);
	}

	/**
	 * 添加学习策略
	 * @author Clark
	 * @date 2014年8月3日下午2:47:19
	 * @param strategy
	 * @return Strategy
	 */
	@Override
	public Strategy addStrategy(Strategy strategy) {
		// TODO Auto-generated method stub
		return this.getStrategyDAO().save(strategy);
	}
	
	/**
	 * 通过策略类型和知识点找到唯一的学习策略
	 * @author Clark
	 * @date 2014年8月3日下午3:10:13
	 * @param strategyTypeID
	 * @param knowledgeID
	 * @return Strategy
	 */
	@Override
	public Strategy getByUniqueKey1(Integer strategyTypeID, Integer knowledgeID) {
		// TODO Auto-generated method stub
		Map<String, Object> searchMap = new HashMap<>();
		searchMap.put("strategyType.id", strategyTypeID);
		searchMap.put("knowledge.id", knowledgeID);
		List<Strategy> strategys = searchStrategy(searchMap);
		if (strategys.size() > 0){
			return strategys.get(0);
		}
		return null;
	}
	
	/**
	 * 通过策略类型和教材章节找到唯一的学习策略
	 * @author Clark
	 * @date 2014年8月3日下午3:10:13
	 * @param strategyTypeID
	 * @param textbookCapterID
	 * @return Strategy
	 */
	@Override
	public Strategy getByUniqueKey2(Integer strategyTypeID,	Integer textbookCapterID) {
		// TODO Auto-generated method stub
		Map<String, Object> searchMap = new HashMap<>();
		searchMap.put("strategyType.id", strategyTypeID);
		searchMap.put("textbookCapter.id", textbookCapterID);
		List<Strategy> strategys = searchStrategy(searchMap);
		if (strategys.size() > 0){
			return strategys.get(0);
		}
		return null;
	}

	/**
	 * 通过ID获取学习策略
	 * @author Clark
	 * @date 2014年8月3日下午3:15:13
	 * @param strategyID
	 * @return
	 */
	@Override
	public Strategy getById(Integer strategyID) {
		// TODO Auto-generated method stub
		return this.getStrategyDAO().get(strategyID);
	}

	/**
	 * 更新学习策略
	 * @author Clark
	 * @date 2014年8月3日下午3:50:18
	 * @param strategy
	 * @return
	 */
	@Override
	public Strategy updateStrategy(Strategy strategy) {
		// TODO Auto-generated method stub
		return this.getStrategyDAO().update(strategy);
	}

	/**
	 * 删除学习策略
	 * @author Clark
	 * @date 2014年8月3日下午4:23:45
	 * @param strategy
	 * @return Strategy
	 */ 
	@Override
	public Strategy deleteStrategy(Strategy strategy) {
		// TODO Auto-generated method stub
		return this.getStrategyDAO().delete(strategy);
	}



}
