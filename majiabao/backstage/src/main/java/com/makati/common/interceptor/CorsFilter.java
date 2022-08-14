package com.makati.common.interceptor;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author eden
 * @create 2018-01-18 12:37
 **/
@Component
public class CorsFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        //The value of the 'Access-Control-Allow-Origin' header in the response must not be the wildcard '*'
        // when the request's credentials mode is 'include'.
        // The credentials mode of requests initiated by the XMLHttpRequest is controlled by the withCredentials attribute.
        String origin = request.getHeader("origin");// 获取源站
        //该字段是必须的。它的值要么是请求时Origin字段的值，要么是一个*，表示接受任意域名的请求。由于请求的凭据模式为“include" 时 要动态获取源站域名
        response.setHeader("Access-Control-Allow-Origin", origin);
        //该字段必需，它的值是逗号分隔的一个字符串，表明服务器支持的所有跨域请求的方法。
        // 注意，返回的是所有支持的方法，而不单是浏览器请求的那个方法。这是为了避免多次"预检"请求
        response.setHeader("Access-Control-Allow-Methods", "POST, GET");
        //该字段可选，用来指定本次预检请求的有效期，单位为秒。
        // 在此期间，不用发出另一条预检请求。
        response.setHeader("Access-Control-Max-Age", "3600");
        //该字段可选。CORS请求时，XMLHttpRequest对象的getResponseHeader()方法只能拿到6个基本字段：
        // Cache-Control、Content-Language、Content-Type、Expires、Last-Modified、Pragma。
        // 如果想拿到其他字段，就必须在Access-Control-Expose-Headers里面指定。
        // ，getResponseHeader('x-requested-with,Authorization')可以返回x-requested-with,Authorization字段的值。
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
        //该字段可选。它的值是一个布尔值，表示是否允许发送Cookie。
        // 默认情况下，Cookie不包括在CORS请求之中。设为true，即表示服务器明确许可，Cookie可以包含在请求中，一起发给服务器。
        // 这个值也只能设为true，如果服务器不要浏览器发送Cookie，删除该字段即可。
        response.setHeader("Access-Control-Allow-Credentials", "true");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
