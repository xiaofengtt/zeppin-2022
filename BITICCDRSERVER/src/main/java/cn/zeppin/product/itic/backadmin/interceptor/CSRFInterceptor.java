package cn.zeppin.product.itic.backadmin.interceptor;


import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.zeppin.product.itic.backadmin.security.CSRFTokenManager;

/**
 * 预防CSRF攻击拦截器
 * 
 * @author Clark.R 2016年9月14日
 */

public class CSRFInterceptor extends HandlerInterceptorAdapter {

	/**
	 * POST请求的Session令牌验证
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		/**
		 * request.getHeader("Referer")要走HTTP协议才有值，也就是说要通过<a href="url" />标记，才能获取到值。当然通过表单提交的也可以。
		 * 而通过location或是<a href="javascript:window.location=''" />是得不到值的
		 */
//		String referer = request.getHeader("Referer");
//		if(!referer.startsWith("")){
//			return false;
//		}
		
		if ("POST".equalsIgnoreCase(request.getMethod()) && request.getParameterMap().size() != 0) {
			String CSRFToken = CSRFTokenManager.getTokenFromRequest(request);
            if (CSRFToken == null || !CSRFToken.equals(request.getSession().getAttribute(CSRFTokenManager.CSRF_TOKEN_FOR_SESSION_ATTR_NAME))) {
                String reLoginUrl = "../views/login.jsp?backurl=" + URLEncoder.encode(getCurrentUrl(request), "utf-8");
                response.sendRedirect(reLoginUrl);
                return false;
            }
		 }
		return true;
	}
	
	private String getCurrentUrl(HttpServletRequest request) {
		String currentUrl = request.getRequestURL().toString();
		if (!StringUtils.isEmpty(request.getQueryString())) {
			currentUrl += "?" + request.getQueryString();
		}

		return currentUrl;
	}
}
