package com.cmos.chinamobile.media.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class CSRFInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = -6047388094890251679L;
	
	/**
	 * 拦截器防止跨站攻击
	 */
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String referer = request.getHeader("Referer");
//		if(!referer.startsWith("http://192.168.100.4:19150/")){
//			return null;
//		}
		return invocation.invoke();
	}
}
