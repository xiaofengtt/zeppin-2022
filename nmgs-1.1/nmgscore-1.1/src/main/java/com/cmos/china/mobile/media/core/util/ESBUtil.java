package com.cmos.china.mobile.media.core.util;

import java.util.Map;

import com.cmos.esbclient.bean.EsbInObject;
import com.cmos.esbclient.bean.EsbOutObject;
import com.cmos.esbclient.bean.MessageInfo;
import com.cmos.esbclient.bean.RestMethodType;

public class ESBUtil {

	/**
	 * @Description: 调用esb接口
	 * @throws CommonException
	 * @ReturnType void
	 * @author: 
	 * @Created 
	 */

	public static EsbOutObject ssologin(Map<String, String> params , String id ,String url) throws ExceptionUtil {
		MessageInfo msg = new MessageInfo();
		msg.setRestMethodType(RestMethodType.GET);//调用方式，get，post。delete等
		msg.setClientId(id);//调用端clientID
		msg.setUri(url);//接口地址
		
		EsbInObject esbInput = new EsbInObject();
		esbInput.setParams(params);//参数
		
		String param = JSONUtils.obj2json(esbInput);
		msg.setInput(param);
		EsbOutObject esbOutObject = ESBRestClientUtil.getResult(msg);
		return esbOutObject;
	}
	
	public static EsbOutObject smslogin(Map<String, String> params , String id ,String url) throws ExceptionUtil {
		MessageInfo msg = new MessageInfo();
		msg.setRestMethodType(RestMethodType.POST);//调用方式，get，post。delete等
		msg.setClientId(id);//调用端clientID
		msg.setUri(url);//接口地址
		
		EsbInObject esbInput = new EsbInObject();
		esbInput.setParams(params);//参数
		
		String param = JSONUtils.obj2json(esbInput);
		msg.setInput(param);
		EsbOutObject esbOutObject = ESBRestClientUtil.getResult(msg);
		return esbOutObject;
	}
}
