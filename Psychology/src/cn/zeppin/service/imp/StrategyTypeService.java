/**
 * Copyright (c) 2014, Zeppin All Rights Reserved.
 * cn.zeppin.service.imp
 * StrategyTypeService
 */
package cn.zeppin.service.imp;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.IStrategyTypeDAO;
import cn.zeppin.entity.StrategyType;
import cn.zeppin.service.api.IStrategyTypeService;

/**
 * @author Clark
 * 下午2:36:46
 */
public class StrategyTypeService implements IStrategyTypeService {

	private IStrategyTypeDAO strategyTypeDAO;
	
	public IStrategyTypeDAO getStrategyTypeDAO() {
		return strategyTypeDAO;
	}

	public void setStrategyTypeDAO(IStrategyTypeDAO strategyTypeDAO) {
		this.strategyTypeDAO = strategyTypeDAO;
	}

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
		return this.getStrategyTypeDAO().searchStrategyType(searchKey);
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
		return this.getStrategyTypeDAO().getByName(name);
	}

	/**
	 * 添加学习策略类型
	 * @author Clark
	 * @date 2014年8月1日下午6:03:33
	 * @param strategyType
	 * @return
	 */
	@Override
	public StrategyType addStrategyType(StrategyType strategyType) {
		// TODO Auto-generated method stub
		return this.getStrategyTypeDAO().save(strategyType);
	}

	/**
	 * 通过ID查询学习策略类型
	 * @author Clark
	 * @date 2014年8月1日下午6:08:45
	 * @param strategyTypeID
	 * @return
	 */
	@Override
	public StrategyType getById(Integer strategyTypeID) {
		// TODO Auto-generated method stub
		return this.getStrategyTypeDAO().get(strategyTypeID);
	}

	/**
	 * 更新学习策略类型
	 * @author Clark
	 * @date 2014年8月1日下午6:16:54
	 * @param strategyType
	 * @return
	 */
	@Override
	public StrategyType updateStrategyType(StrategyType strategyType) {
		// TODO Auto-generated method stub
		return this.getStrategyTypeDAO().update(strategyType);
	}

	/**
	 * 删除学习策略类型
	 * @author Clark
	 * @date 2014年8月1日下午6:16:54
	 * @param strategyType
	 * @return
	 */
	@Override
	public StrategyType deleteStrategyType(StrategyType strategyType) {
		// TODO Auto-generated method stub
		return this.getStrategyTypeDAO().delete(strategyType);
	}


}
