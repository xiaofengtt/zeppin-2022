package cn.product.payment.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class CrosInterceptor extends HandlerInterceptorAdapter {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
		response.setHeader("Access-Control-Allow-Origin",request.getHeader("origin"));
		response.setHeader("Access-Control-Allow-Credentials","true");
		
		return true;
	}
}