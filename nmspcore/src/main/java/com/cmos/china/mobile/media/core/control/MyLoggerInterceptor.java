package com.cmos.china.mobile.media.core.control;

import com.cmos.core.bean.InputObject;
import com.cmos.core.logger.ThreadLoggerParam;
import com.cmos.core.logger.interceptor.AbstractLoggerInterceptor;
import com.cmos.core.util.StringUtil;

public class MyLoggerInterceptor extends AbstractLoggerInterceptor {

	// 获取当前请求唯一标识
	protected String getUniqueNo(Object object) {
		String uniqueNo = ThreadLoggerParam.getThreadInstance().getOpReqstNo();
		if (StringUtil.isEmpty(uniqueNo) && object != null && object instanceof InputObject) {
			InputObject inputObject = (InputObject)object;
			uniqueNo = inputObject.getLogParams().get("OP_REQST_NO");
		}
		return uniqueNo;
	}

	// 获取当前请求用户标识
	protected String getUserNo(Object object) {
		String userNo = ThreadLoggerParam.getThreadInstance().getUserNo();
		if (StringUtil.isEmpty(userNo) && object != null && object instanceof InputObject) {
			InputObject inputObject = (InputObject)object;
			userNo = inputObject.getLogParams().get("OP_USER_NO");
		}
		return userNo;
	}
}
