package com.whaty.platform.sso.web.interceptor;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class AdminInterceptor extends AbstractInterceptor{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final Log logger = LogFactory.getLog(UserLoginInterceptor.class);
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		// 在action运行前先看看有没有需要等待的进程，如果有则返回提示信息
		ActionContext ac = invocation.getInvocationContext();
		ServletContext servletContext = (ServletContext) ac.get(ServletActionContext.SERVLET_CONTEXT);
		WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		if (wc == null) {
			logger.error("ApplicationContext could not be found.");
			return "intercepthtml";
		} else { 
			String us =  (String) ac.getSession().get("admin");

			if (us != null) {// 通行证已登录
				return invocation.invoke();
			}else{
				//判断管理员登陆的action
				final String action = invocation.getProxy().getActionName();
				if("admin_login".equals(action))
					return invocation.invoke();
				return "input";
			}
		}
	}
}
