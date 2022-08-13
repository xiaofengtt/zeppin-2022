package com.whaty.util;

import java.io.IOException;
import javax.servlet.*;

public class ChangeCharsetFilter implements Filter {
	protected String encoding = null;// ///要制定的编码，在web.xml中配置

	protected FilterConfig filterConfig = null;

	public void destroy() {
		this.encoding = null;
		this.filterConfig = null;
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		if(getEncoding() != null && (request.getCharacterEncoding() == null))
        {
            request.setCharacterEncoding(getEncoding());
            response.setCharacterEncoding(getEncoding());
        }
		chain.doFilter(request, response);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		this.encoding = filterConfig.getInitParameter("encoding");// /得到在web.xml中配置的编码
	}

	protected String getEncoding() {
		return (this.encoding);///得到指定的编码
	}
}

/*
<filter>
<filter-name>SetCharacterEncoding</filter-name>
<filter-class>myFilter.ChangeCharsetFilter </filter-class>
<init-param>
<param-name>encoding</param-name>
<param-value>GB2312</param-value>//////指定编码为UTF-8
</init-param>
</filter>
<filter-mapping>
<filter-name>SetCharacterEncoding</filter-name>
<url-pattern>/*</url-pattern>////////对于所有的request改变其编码
</filter-mapping>
*/