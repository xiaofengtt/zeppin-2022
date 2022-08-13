package com.whaty.platform.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.whaty.platform.config.PlatformConfig;

public class PlatformAdminFilter implements Filter {

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		String strURL = new String(((HttpServletRequest) request)
				.getRequestURL());
		if (strURL.endsWith("login_info.jsp")) {
			chain.doFilter(request, response);
		} else {

			HttpSession session = request.getSession();
			ServletContext application = session.getServletContext();
			PlatformConfig config = (PlatformConfig) application
					.getAttribute("platformConfig");

			if (session.getAttribute("admin") == null
					|| !((String) session.getAttribute("admin")).equals("1")) {
				// response.sendError(HttpServletResponse.SC_FORBIDDEN);
				response.sendRedirect(config.getPlatformWebAppUriPath()
						+ "error/priv_error.jsp");
				return;
			} else {
				chain.doFilter(request, response);
			}
		}

	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
