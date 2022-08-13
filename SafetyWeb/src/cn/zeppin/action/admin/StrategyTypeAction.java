/**
 * Copyright (c) 2014, Zeppin All Rights Reserved.
 * cn.zeppin.action.admin
 * StrategyTypeAction
 */
package cn.zeppin.action.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zeppin.action.base.ActionParam;
import cn.zeppin.action.base.ActionParam.ValueType;
import cn.zeppin.action.base.ActionResult;
import cn.zeppin.action.base.BaseAction;
import cn.zeppin.authority.AuthorityParas;
import cn.zeppin.entity.StrategyType;
import cn.zeppin.entity.base.SerializeEntity;
import cn.zeppin.service.api.IStrategyService;
import cn.zeppin.service.api.IStrategyTypeService;
import cn.zeppin.utility.Utlity;

/**
 * @author Clark
 * 下午2:45:20
 */
public class StrategyTypeAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 555315102504584497L;

	private IStrategyTypeService strategyTypeService;
	private IStrategyService strategyService;
	
	/**
	 * 
	 */
	public IStrategyTypeService getStrategyTypeService() {
		return strategyTypeService;
	}
	public void setStrategyTypeService(IStrategyTypeService strategyTypeService) {
		this.strategyTypeService = strategyTypeService;
	}
	
	public IStrategyService getStrategyService() {
		return strategyService;
	}
	public void setStrategyService(IStrategyService strategyService) {
		this.strategyService = strategyService;
	}
	/**
	 * 查询学习策略类型
	 * @author Clark
	 * @date 2014年8月1日下午5:31:32
	 */
	@ActionParam(key = "id", type = ValueType.NUMBER)
	@ActionParam(key = "name", type = ValueType.STRING)
	public void Search(){
		
		ActionResult result = new ActionResult();
		Map<String, Object> searchKey = new HashMap<>();
		searchKey.put("id", request.getParameter("id"));
		searchKey.put("name", request.getParameter("name"));
		
		List<StrategyType> strategyTypes = this.getStrategyTypeService().searchStrategyType(searchKey);
		List<Map<String, Object>> dataList = new ArrayList<>();
		for (StrategyType strategyType : strategyTypes){
			Map<String, Object> data = SerializeEntity.strategyType2Map(strategyType);
			dataList.add(data);
		}
		result.init(SUCCESS_STATUS, "读取学习策略类型成功！", dataList);
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);
	}

	/**
	 * 添加学习策略类型
	 * @author Clark
	 * @date 2014年8月1日下午5:31:22
	 */
	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT")
	@ActionParam(key = "name", type = ValueType.STRING, nullable=false, emptyable=false)
	public void Add(){
		ActionResult result = new ActionResult();
		String name = request.getParameter("name");
		StrategyType strategyType = this.getStrategyTypeService().getByName(name);
		if (strategyType == null) {
			strategyType = new StrategyType();
			strategyType.setName(name);
			strategyType = this.getStrategyTypeService().addStrategyType(strategyType);
			Map<String, Object> data = SerializeEntity.strategyType2Map(strategyType);
			result.init(SUCCESS_STATUS, "添加学习策略类型成功！", data);
		}
		else {
			result.init(FAIL_STATUS, "已经存在同名的学习策略类型！", null);
		}
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);
	}
	
	/**
	 * 修改学习策略类型
	 * @author Clark
	 * @date 2014年8月1日下午6:06:01
	 */
	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable=false, emptyable=false)
	@ActionParam(key = "name", type = ValueType.STRING, nullable=false, emptyable=false)
	public void Update() {
		ActionResult result = new ActionResult();
		Integer strategyTypeID = Integer.valueOf(request.getParameter("id"));
		String name = request.getParameter("name");
		StrategyType strategyType = this.getStrategyTypeService().getById(strategyTypeID);
		StrategyType duplicateStrategyType = this.getStrategyTypeService().getByName(name);
		if (strategyType == null){
			result.init(FAIL_STATUS, "学习策略类型ID信息不正确！", null);
		}
		else if (duplicateStrategyType != null && 
				duplicateStrategyType.getId() != strategyTypeID &&
				duplicateStrategyType.getName().equals(name)) {
			result.init(FAIL_STATUS, "已经存在同名的学习策略类型！", null);
		}
		else {
			strategyType.setName(name);
			strategyType = this.getStrategyTypeService().updateStrategyType(strategyType);
			Map<String, Object> data = SerializeEntity.strategyType2Map(strategyType);
			result.init(SUCCESS_STATUS, "更新学习策略类型成功！", data);
		}
		
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);
	}
	
	/**
	 * 删除学习策略类型
	 * @author Clark
	 * @date 2014年8月1日下午6:18:06
	 */
	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable=false, emptyable=false)
	public void Delete(){
		ActionResult result = new ActionResult();
		Integer strategyTypeID = Integer.valueOf(request.getParameter("id"));
		StrategyType strategyType = this.getStrategyTypeService().getById(strategyTypeID);
		if (strategyType == null){
			result.init(FAIL_STATUS, "学习策略类型ID信息不正确！", null);
		}
		else {
			Map<String, Object> searchMap = new HashMap<>();
			searchMap.put("strategyType.id", strategyTypeID);
			Integer strategyCount = this.getStrategyService().searchStrategyCount(searchMap);
			if (strategyCount > 0){
				result.init(FAIL_STATUS, "已经存在该类型的学习策略，无法删除！", null);
			}
			else {
				strategyType = this.getStrategyTypeService().deleteStrategyType(strategyType);
				Map<String, Object> data = SerializeEntity.strategyType2Map(strategyType);
				result.init(SUCCESS_STATUS, "删除学习策略类型成功！", data);
			}
		}
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);
	}
	
}
