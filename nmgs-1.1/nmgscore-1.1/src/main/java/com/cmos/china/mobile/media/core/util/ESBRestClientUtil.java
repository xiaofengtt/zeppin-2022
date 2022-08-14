package com.cmos.china.mobile.media.core.util;
import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;
import com.cmos.core.util.JsonUtil;
import com.cmos.esbclient.bean.EsbOutObject;
import com.cmos.esbclient.bean.MessageInfo;
import com.cmos.esbclient.remote.RestClientUtil;

public final class ESBRestClientUtil {

	private static final Logger logger = LoggerFactory
			.getApplicationLog(ESBRestClientUtil.class);

	/**
	 *  调用ESB
	 */
	public static EsbOutObject getResult(MessageInfo message) {

		logger.info("调用ESB[getResult]param", JSONUtils.obj2json(message));
		EsbOutObject esbOutObject = null;

		String resultString=null;
		try {
			resultString = RestClientUtil.invoke(message);
			logger.info("调用ESB[getResult]返回值：", resultString);
			esbOutObject = JsonUtil.convertJson2Object(resultString, EsbOutObject.class);
		} catch (Exception e) {
			logger.error("getResult", "e=" + e);
		}
		logger.info("调用ESB[getResult]result", JSONUtils.obj2json(resultString));
		return esbOutObject;

	}
	
	
}
