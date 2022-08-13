package com.whaty.platform.sso.web.interceptor;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.whaty.platform.entity.bean.PeExamScoreInputUser;

public class SessionOutInterceptorforScoreInput extends AbstractInterceptor {
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		ActionContext ac = invocation.getInvocationContext();
		ServletContext servletContext = (ServletContext) ac.get(ServletActionContext.SERVLET_CONTEXT);
		WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(servletContext);

		if (wc == null) {
			return "error";
		} else {
			 String flag = (String)ac.getSession().get("flag");
			 PeExamScoreInputUser user = (PeExamScoreInputUser)ac.getSession().get("user");
			 if(flag!=null&&user!=null){
				 return invocation.invoke();
			 }else{
				 return "login";
			 }
		}
	}
}
