package com.cmos.chinamobile.media.control.impl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cmos.core.bean.xml.Parameter;
import com.cmos.core.bean.xml.ValidateResult;
import com.cmos.core.filter.IControlRequest;
import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;
import com.cmos.core.util.ControlConstants.RETURN_CODE;
import com.cmos.core.util.ControlRequestUtil;

public class ControlRequestImpl extends IControlRequest {
	private static Logger logger = LoggerFactory.getServiceLog(ControlRequestImpl.class);

	/**
	 * 参数验证
	 */
	public ValidateResultImpl validate(String path, List<Parameter> sources,
			Map<String, String[]> params) {
		if (sources == null) {
			return new ValidateResultImpl(RETURN_CODE.IS_OK, null);
		}

		// 验证请求的参数个数�?顺序、和正则�?
		ValidateResult result = ControlRequestUtil.checkParams(sources, params);
		ValidateResultImpl resultImpl = new ValidateResultImpl(result.getReturnCode(), result.getReturnMessage());
		if (!RETURN_CODE.IS_OK.equals(result.getReturnCode())) {
			System.err.println("参数验证有问�?" + path);
			logger.error("参数验证有问�?", "path=" + path);
		}
		return resultImpl;
	}
	
	public boolean loginValidate(HttpServletRequest arg0) {
		return false;
	}
}
