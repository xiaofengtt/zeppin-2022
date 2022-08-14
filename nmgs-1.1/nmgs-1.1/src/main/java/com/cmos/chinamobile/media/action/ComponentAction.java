package com.cmos.chinamobile.media.action;

import com.cmos.chinamobile.media.action.base.ActionResult;
import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;

public class ComponentAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getActionLog(ComponentAction.class);
	
	/**
	 * 分类相关操作Action
	 */
	public String execute() {
		logger.info("execute", "Start");
		ActionResult<Object> result = super.getActionResult();
		super.sendJson(super.convertOutputObject2Json(result));
		logger.info("execute", "End");
		return null;
	}
}
