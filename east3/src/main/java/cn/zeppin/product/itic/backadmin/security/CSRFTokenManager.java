package cn.zeppin.product.itic.backadmin.security;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class CSRFTokenManager {
	
	// 隐藏域参数名称
	public static final String CSRF_PARAM_NAME = "CSRFToken";
	
	// Session中CSRFToken参数名称
	public static final String CSRF_TOKEN_FOR_SESSION_ATTR_NAME = CSRFTokenManager.class.getName() + ".tokenval";
	
	private CSRFTokenManager() {
		
	}
	
	// 在Session中创建CSRFToken
	public static String createTokenForSession(HttpSession session) {
		String token = null;
		synchronized (session) {
			token = (String) session.getAttribute(CSRF_TOKEN_FOR_SESSION_ATTR_NAME);
			if (token == null) {
				token = UUID.randomUUID().toString();
				session.setAttribute(CSRF_TOKEN_FOR_SESSION_ATTR_NAME, token);
			}
		}
		return token;
	}
	

	public static String getTokenFromRequest(HttpServletRequest request) {
		return request.getParameter(CSRF_PARAM_NAME);
	}
}
