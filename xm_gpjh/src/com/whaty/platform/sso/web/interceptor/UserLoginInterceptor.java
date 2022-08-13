package com.whaty.platform.sso.web.interceptor;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

public class UserLoginInterceptor extends AbstractInterceptor {

	private static final Log logger = LogFactory.getLog(UserLoginInterceptor.class);

	/**
	 *
	 */
	private static final long serialVersionUID = 6748629623401983281L;

	@SuppressWarnings("unchecked")
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		
		ActionContext ac = invocation.getInvocationContext();
		ServletContext servletContext = (ServletContext) ac.get(ServletActionContext.SERVLET_CONTEXT);
		WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(servletContext);

		if (wc == null) {
			logger.error("ApplicationContext could not be found.");
			return "intercepthtml";
		} else {
			
			UserSession us = (UserSession) ac.getSession().get(SsoConstant.SSO_USER_SESSION_KEY);

			if (us != null) {// 通行证已登陆
				return invocation.invoke();
				
			}else{
				return "input";
			}
		}
	}
}
