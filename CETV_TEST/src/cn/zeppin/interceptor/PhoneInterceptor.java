/** 
 * Project Name:CETV_TEST 
 * File Name:PhoneInterceptor.java 
 * Package Name:cn.zeppin.interceptor 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */  
package cn.zeppin.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

/** 
 * ClassName: PhoneInterceptor <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * date: 2014年10月20日 下午4:07:43 <br/> 
 * 
 * @author Administrator 
 * @version  
 * @since JDK 1.7 
 */
public class PhoneInterceptor extends MethodFilterInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8139734739596650793L;

	@Override
	protected String doIntercept(ActionInvocation ai) throws Exception {
		return ai.invoke();
	}

}
