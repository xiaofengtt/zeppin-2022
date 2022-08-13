package com.whaty.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.whaty.platform.config.DbKeySetUp;
import com.whaty.platform.config.PlatformConfig;
import com.whaty.platform.sso.web.action.SsoConstant;

public class InitDBKeyServlet extends HttpServlet{

	public InitDBKeyServlet() {
		super();
	}
	
	public void destroy() {
		super.destroy(); 
	}
	
	public void init() throws ServletException {
		String path = null;
		String filepath = null;
		String prefix =  getServletContext().getRealPath("/");
		ServletContext application = getServletContext();
		try{
			path = getInitParameter("dbKeyInfodir");
			filepath = prefix+path;
			DbKeySetUp dbKeySetUp = new DbKeySetUp(filepath);
			dbKeySetUp.setUp();
			application.setAttribute(SsoConstant.DB_FOREIGN_KEY,dbKeySetUp.getForeignKeys());
			application.setAttribute(SsoConstant.DB_ALTERNATE_KEY, dbKeySetUp.getAlternateKeys());
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

}
