package com.whaty.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.whaty.platform.config.ForumConfig;
import com.whaty.platform.config.PlatformConfig;
import com.whaty.servlet.*;

public class InitForumServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public InitForumServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occure
	 */
	public void init() throws ServletException {
		String path = null;
		String filepath = null;
		String prefix =  getServletContext().getRealPath("/");
		ServletContext application = getServletContext();
		try{
			path = getInitParameter("forumconfigdir");
			filepath = prefix+path;
			ForumConfig forumConfig = new ForumConfig(filepath);
			forumConfig.getConfig();
			application.setAttribute("forumConfig",forumConfig);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

}
