package cn.zeppin.product.ntb.backadmin.interceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.zeppin.product.ntb.backadmin.security.CSRFTokenManager;

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
			if(request.getSession().getAttribute(CSRFTokenManager.CSRF_TOKEN_FOR_SESSION_ATTR_NAME) == null){
				response.setContentType("application/json");
            	response.setCharacterEncoding("UTF-8");
    			response.setHeader("Cache-Control", "no-cache");
            	response.getWriter().print("{\"status\":\"FAIL\",\"message\":\"页面载入超时，请刷新页面！\"}");
                return false;
			}
			if("".equals((String)request.getSession().getAttribute(CSRFTokenManager.CSRF_TOKEN_FOR_SESSION_ATTR_NAME))){
				response.setContentType("application/json");
            	response.setCharacterEncoding("UTF-8");
    			response.setHeader("Cache-Control", "no-cache");
            	response.getWriter().print("{\"status\":\"FAIL\",\"message\":\"页面载入超时，请刷新页面！\"}");
                return false;
			}
            if (CSRFToken == null || !CSRFToken.equals(request.getSession().getAttribute(CSRFTokenManager.CSRF_TOKEN_FOR_SESSION_ATTR_NAME))) {
            	response.setContentType("application/json");
            	response.setCharacterEncoding("UTF-8");
    			response.setHeader("Cache-Control", "no-cache");
            	response.getWriter().print("{\"status\":\"FAIL\",\"message\":\"access barred!\"}");
                return false;
            }
		 }
		return true;
	}
	
}
