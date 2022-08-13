package com.cmos.chinamobile.media.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class CSRFInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = -6047388094890251679L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String referer = request.getHeader("Referer");
//		if(!referer.startsWith("http://localhost:8090/")){
//			return null;
//		}
		return invocation.invoke();
	}
}
