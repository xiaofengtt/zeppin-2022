package com.whaty.platform.entity.web.action;

import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ActionSupport;

public class EntityBaseAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3173329912317245025L;

	protected static final Log logger = LogFactory.getLog("EntityLogger");

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
