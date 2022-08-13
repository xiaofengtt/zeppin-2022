package com.gpjh.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginFilter extends HttpServlet implements Filter {

	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {

		HttpServletResponse response = (HttpServletResponse) arg1;
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpSession session = request.getSession(true);
		String usercode = (String) session.getAttribute("usercode");//
		String url = request.getRequestURI();
		if (usercode == null || usercode.equals("")) {
			// 判断获取的路径不为空且不是访问登录页面或执行登录操作时跳转
			if (url != null && !url.equals("")
					&& (url.indexOf("Login") < 0 && url.indexOf("login") < 0)) {
				response.sendRedirect("登录路径");
				return;
			}
		}
		// 已通过验证，用户访问继续
		arg2.doFilter(arg0, arg1);
		return;
	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}
}
