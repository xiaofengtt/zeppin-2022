package cn.zeppin.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import cn.zeppin.action.sso.UserSession;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

public class adminInterceptor extends MethodFilterInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected String doIntercept(ActionInvocation ai) throws Exception {
		// TODO Auto-generated method stub
		ActionContext actionContext = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) actionContext.get(ServletActionContext.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) actionContext.get(ServletActionContext.HTTP_RESPONSE);
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";

		// 获取session，检查用户是否已经登录，注意此处的return null;很主要，防止页面多次访问

		UserSession user = (UserSession) actionContext.getSession().get("usersession");
		if (user == null) {
			// return "login" 如果没有用户登录则转向此页面，basePath用来防止路径出错
			// 直接用 uuid 登陆测评
			UserSession us = (UserSession) actionContext.getSession().get("papersession");
			if (us == null) {

				String servletPath = request.getServletPath();
				// 当前的目录
				if (servletPath.startsWith("/teacher")) {
					response.sendRedirect(basePath + "teacher/login.html");
					return null;
				} else if (servletPath.startsWith("/paper")) {
					return ai.invoke();
				} else {
					response.sendRedirect(basePath + "index.jsp");
					return null;
				}

			} else {
				return ai.invoke();
			}

		}

		return ai.invoke();
	}

}
