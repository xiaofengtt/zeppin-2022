package com.whaty.platform.listener.session;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

/**
 * @param
 * @version 创建时间：2009-8-27 下午03:51:02
 * @return
 * @throws PlatformException
 *             类说明
 */
public class LoginSessionListener implements HttpSessionListener,
		HttpSessionAttributeListener {

	public void sessionCreated(HttpSessionEvent event) {
		ServletContext ctx = event.getSession().getServletContext();
		Integer numSessions = (Integer) ctx.getAttribute("numSessions");
		if (numSessions == null) {
			numSessions = new Integer(1);
		} else {
			int count = numSessions.intValue();
			numSessions = new Integer(count + 1);
		}
		ctx.setAttribute("numSessions", numSessions);
	}

	public void sessionDestroyed(HttpSessionEvent event) {
		ServletContext ctx = event.getSession().getServletContext();
		Integer numSessions = (Integer) ctx.getAttribute("numSessions");
		if (numSessions == null) {
			numSessions = new Integer(0);
		} else {
			int count = numSessions.intValue();
			numSessions = new Integer(count - 1);
		}
		ctx.setAttribute("numSessions", numSessions);
	}

	public void attributeAdded(HttpSessionBindingEvent arg0) {
		ServletContext ctx = arg0.getSession().getServletContext();
		String name = arg0.getName();
		Object value = arg0.getValue();
		if (SsoConstant.SSO_USER_SESSION_KEY.equals(name)
				&& value instanceof UserSession) {
			Integer numUsers = (Integer) ctx.getAttribute("numUsers");
			if (numUsers == null) {
				numUsers = new Integer(1);
			} else {
				int count = numUsers.intValue();
				numUsers = new Integer(count + 1);
			}
			ctx.setAttribute("numUsers", numUsers);
		}
	}

	public void attributeRemoved(HttpSessionBindingEvent arg0) {
		ServletContext ctx = arg0.getSession().getServletContext();
		String name = arg0.getName();
		Object value = arg0.getValue();
		if (SsoConstant.SSO_USER_SESSION_KEY.equals(name)
				&& value instanceof UserSession) {
			Integer numUsers = (Integer) ctx.getAttribute("numUsers");
			if (numUsers == null) {
				numUsers = new Integer(0);
			} else {
				int count = numUsers.intValue();
				numUsers = new Integer(count - 1);
			}
			ctx.setAttribute("numUsers", numUsers);
		}
	}

	public void attributeReplaced(HttpSessionBindingEvent arg0) {

	}

}
