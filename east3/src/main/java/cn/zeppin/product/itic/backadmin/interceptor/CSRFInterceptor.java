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
 */

public class CSRFInterceptor extends HandlerInterceptorAdapter {

	/**
	 * POST请求的Session令牌验证
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
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
