/**
 * 
 */
package com.whaty.platform.sso.web.action;

import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author wq
 * 
 */
public class SsoBaseAction extends ActionSupport {

	
	private static final long serialVersionUID = 273882982993358439L;
	
	protected static final Log logger = LogFactory.getLog("SsoLogger");

	private String action = "index";

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	protected String executeMethod(String method) throws Exception {
		Class[] c = null;
		Method m = this.getClass().getMethod(method, c);
		Object[] o = null;
		String result = (String) m.invoke(this, o);
		return result;
	}
}
