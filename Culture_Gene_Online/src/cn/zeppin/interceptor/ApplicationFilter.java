package cn.zeppin.interceptor;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.zeppin.entity.User;
import cn.zeppin.utility.Dictionary;

public class ApplicationFilter implements Filter {

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpSession session = request.getSession();
		HttpServletResponse response = (HttpServletResponse) arg1;

		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";

		// 获取session，检查用户是否已经登录，注意此处的return null;很主要，防止页面多次访问

		User user = (User) session.getAttribute("user");
		if (user == null) {
			// return "login" 如果没有用户登录则转向此页面，basePath用来防止路径出错

			String servletPath = request.getServletPath();
			if (servletPath.equals("/index.jsp")) {
				// 放问的是首页
				arg2.doFilter(arg0, arg1);
			} else {
				response.sendRedirect(basePath + "index.jsp");
			}
		} else {
			String servletPath = request.getServletPath();
			if (servletPath.equals("/index.jsp")) {
				// 放问的是首页
				if (user.getRole().getId() == Dictionary.USER_ROLE_ADMIN || user.getRole().getId() == Dictionary.USER_ROLE_SPECIALIST) {
					// 访问的是超级管理员或专家
					response.sendRedirect(basePath + "admin/main.jsp");
				}

			} else {
				arg2.doFilter(arg0, arg1);
			}
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}
