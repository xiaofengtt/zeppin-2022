package com.cmos.chinamobile.media.control.impl;

import java.util.Map;
import java.util.UUID;

import javax.security.auth.Subject;

import com.cmos.chinamobile.media.control.IControlService;
import com.cmos.core.bean.InputObject;
import com.cmos.core.bean.OutputObject;
import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;
import com.cmos.core.util.ControlConstants;
import com.cmos.core.util.ControlConstants.RETURN_CODE;
import com.cmos.core.util.StringUtil;

/**
 * 
 * 
 */
public class ControlServiceImpl implements IControlService {
	private static final Logger logger = LoggerFactory.getServiceLog(ControlServiceImpl.class);
	private com.cmos.core.service.IControlService coreControlService;

	public OutputObject execute(InputObject inputObject) {
		initLoggerParam(inputObject);
		OutputObject outputObject = null;
		try {
			
			logger.info("service=" + inputObject.getService() + ", method=" + inputObject.getMethod());
			
			// 转换成统一出参
			outputObject = coreControlService.execute(inputObject);
			
		} catch (Exception e) {
			outputObject = new OutputObject();
			outputObject.setReturnCode(RETURN_CODE.SYSTEM_ERROR);
			outputObject.setReturnMessage(e.getMessage() == null ? e.getCause().getMessage() : e.getMessage());
		}
		return outputObject;
	}
	
	private void initLoggerParam(InputObject inputObject) {
		Map<String, String> logMap = inputObject.getLogParams();
		if (StringUtil.isEmpty(logMap.get(ControlConstants.OP_REQST_NO))) { //请求唯一标识
			logMap.put(ControlConstants.OP_REQST_NO , UUID.randomUUID().toString());
		}
		
		if (StringUtil.isEmpty(logMap.get("OP_USER_NO"))){// 如果用户名为空
			try {
				//TODO 获取用户名
//				logMap.put("OP_USER_NO", user.getLoginNm());
			} catch (Exception e) {
			}
		}
	}

	public com.cmos.core.service.IControlService getCoreControlService() {
		return coreControlService;
	}

	public void setCoreControlService(
			com.cmos.core.service.IControlService coreControlService) {
		this.coreControlService = coreControlService;
	}
}
