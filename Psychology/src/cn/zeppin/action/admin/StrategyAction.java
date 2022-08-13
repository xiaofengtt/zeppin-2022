/**
 * Copyright (c) 2014, Zeppin All Rights Reserved.
 * cn.zeppin.action.admin
 * StrategyAction
 */
package cn.zeppin.action.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zeppin.action.base.ActionParam;
import cn.zeppin.action.base.ActionResult;
import cn.zeppin.action.base.BaseAction;
import cn.zeppin.action.base.ActionParam.ValueType;
import cn.zeppin.authority.AuthorityParas;
import cn.zeppin.entity.Knowledge;
import cn.zeppin.entity.Strategy;
import cn.zeppin.entity.base.SerializeEntity;
import cn.zeppin.service.api.IKnowledgeService;
import cn.zeppin.service.api.IStrategyService;
import cn.zeppin.service.api.IStrategyTypeService;
import cn.zeppin.service.api.ITextbookCapterService;
import cn.zeppin.utility.Utlity;

/**
 * @author Clark 上午10:53:23
 */
public class StrategyAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2395609046529927486L;

	private IStrategyService strategyService;
	private IStrategyTypeService strategyTypeService;
	private IKnowledgeService knowledgeService;
	private ITextbookCapterService textbookCapterService;

	public IStrategyService getStrategyService() {
		return strategyService;
	}

	public void setStrategyService(IStrategyService strategyService) {
		this.strategyService = strategyService;
	}

	public IStrategyTypeService getStrategyTypeService() {
		return strategyTypeService;
	}

	public void setStrategyTypeService(IStrategyTypeService strategyTypeService) {
		this.strategyTypeService = strategyTypeService;
	}

	public IKnowledgeService getKnowledgeService() {
		return knowledgeService;
	}

	public void setKnowledgeService(IKnowledgeService knowledgeService) {
		this.knowledgeService = knowledgeService;
	}

	public ITextbookCapterService getTextbookCapterService() {
		return textbookCapterService;
	}

	public void setTextbookCapterService(ITextbookCapterService textbookCapterService) {
		this.textbookCapterService = textbookCapterService;
	}

	/**
	 * 搜索学习策略列表
	 * 
	 * @author Clark
	 * @date 2014年8月3日上午10:55:07
	 */
	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT")
	@ActionParam(key = "id", type = ValueType.NUMBER)
	public void List() {
		ActionResult result = new ActionResult();
		String split = request.getParameter("split");
		split = split == null ? "." : split;

		Integer knowledgeID = Integer.valueOf(request.getParameter("id"));
		Knowledge knowledge = this.getKnowledgeService().getById(knowledgeID);
		List<Map<String, Object>> dataList = new ArrayList<>();
		Map<String, Object> data = SerializeEntity.knowledge2Map(knowledge, split);

		Map<String, Object> searchMap = new HashMap<>();
		searchMap.put("knowledge.id", knowledgeID);
		List<Strategy> strategyList = this.getStrategyService().searchStrategy(searchMap);
		if (strategyList != null && strategyList.size() > 0) {
			for (Strategy strategy : strategyList) {
				if (strategy.getStrategyType().getId() == 1) {
					data.put("target", strategy.getContent());
				} else if (strategy.getStrategyType().getId() == 2) {
					data.put("point", strategy.getContent());
				}
			}
		}
		dataList.add(data);
		result.init(SUCCESS_STATUS, null, dataList);
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);
	}

	/**
	 * 给知识点编辑学习策略
	 * 
	 * @author Clark
	 * @date 2014年8月3日上午10:55:07
	 */
	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT")
	@ActionParam(key = "id", type = ValueType.NUMBER)
	@ActionParam(key = "target", type = ValueType.STRING, nullable = false)
	@ActionParam(key = "point", type = ValueType.STRING, nullable = false)
	public void Update() {
		ActionResult result = new ActionResult();
		Integer knowledgeID = Integer.valueOf(request.getParameter("id"));
		String target = request.getParameter("target");
		String point = request.getParameter("point");
		Map<String, Object> searchMap = new HashMap<>();
		searchMap.put("knowledge.id", knowledgeID);
		List<Strategy> strategyList = this.getStrategyService().searchStrategy(searchMap);
		if (strategyList != null && strategyList.size() > 0) {
			for (Strategy strategy : strategyList) {
				this.getStrategyService().deleteStrategy(strategy);
			}
		}
		if (!target.equals("")) {
			Strategy strategy = new Strategy();
			strategy.setKnowledge(this.getKnowledgeService().getById(knowledgeID));
			strategy.setStrategyType(this.getStrategyTypeService().getById(1));
			strategy.setContent(target);
			this.getStrategyService().addStrategy(strategy);
		}
		if (!point.equals("")) {
			Strategy strategy = new Strategy();
			strategy.setKnowledge(this.getKnowledgeService().getById(knowledgeID));
			strategy.setStrategyType(this.getStrategyTypeService().getById(2));
			strategy.setContent(point);
			this.getStrategyService().addStrategy(strategy);
		}
		result.init(SUCCESS_STATUS, "编辑学习策略成功！", null);
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);
	}
}
