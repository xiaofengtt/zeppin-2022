/*
 * Date  : 2011-6-21
 * Author: dongyg
 */
package com.enfo.authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;
import enfo.crm.tools.*;

/**
 * @author Dong
 *
 */
public class CrmLoginFilterForBocomm extends CrmLoginFilter {

	public String captureSSOUser(final HttpServletRequest request) {
		//重载本方法取单点登录用户名(用于整合第3方单点登录系统)
		final HttpSession session = request.getSession(false);
		String login_user = Utility.trimNull(request.getParameter("uid"));
		String sessn_user = (String)session.getAttribute(fd.sso.client.SSOClientFilter.SSO_LOGON_USER);
		if(sessn_user==null||sessn_user.trim().length()<1){
			return "";
			//throw new ServletException("丢失系统认证资料！请重新登陆！");
		}else if (!login_user.equals(sessn_user)) {
			return "";
			//throw new ServletException("非法访问！！！！！！！！！！！！！！！别再试验了");
		} else {
			return login_user;
		}
	}
}
