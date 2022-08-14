package com.cmos.chinamobile.media.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.cmos.chinamobile.media.remote.XSSRequestWrapper;
 
public class XSSFilter implements Filter {
	public void init(FilterConfig filterConfig) throws ServletException {
    }
    public void destroy() {
    }
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
    	ServletRequest xssRequest = new XSSRequestWrapper((HttpServletRequest) request);
        chain.doFilter(xssRequest, response);
    }
}