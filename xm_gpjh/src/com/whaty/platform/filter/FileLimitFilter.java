/*
 * 本filter是为了采用过滤器禁止某个目录下某类文件的访问
 * 在web.xml中的配置如下（以禁止访问jsp,xtp文件为例）
 * 
 	<filter>
		<filter-name>FileLimitFilter</filter-name>
		<filter-class>
			com.whaty.platform.filter.FileLimitFilter
		</filter-class>
	</filter>
	
	<filter-mapping>
		<filter-name>FileLimitFilter</filter-name>
		<url-pattern>/incoming/*.jsp</url-pattern>
	</filter-mapping>
	
	<filter-mapping>
		<filter-name>FileLimitFilter</filter-name>
		<url-pattern>/incoming/*.xtp</url-pattern>
	</filter-mapping>
 * 
 * */

package com.whaty.platform.filter;


import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;


public class FileLimitFilter implements Filter {

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest req, ServletResponse res,
		 FilterChain chain) throws IOException, ServletException {
		 HttpServletResponse response = (HttpServletResponse) res;
		 response.sendError(403);
	     return;
	     
	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
