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
		//���ر�����ȡ�����¼�û���(�������ϵ�3�������¼ϵͳ)
		final HttpSession session = request.getSession(false);
		String login_user = Utility.trimNull(request.getParameter("uid"));
		String sessn_user = (String)session.getAttribute(fd.sso.client.SSOClientFilter.SSO_LOGON_USER);
		if(sessn_user==null||sessn_user.trim().length()<1){
			return "";
			//throw new ServletException("��ʧϵͳ��֤���ϣ������µ�½��");
		}else if (!login_user.equals(sessn_user)) {
			return "";
			//throw new ServletException("�Ƿ����ʣ���������������������������������������");
		} else {
			return login_user;
		}
	}
}
