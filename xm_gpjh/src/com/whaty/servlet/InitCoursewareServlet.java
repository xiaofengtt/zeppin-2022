package com.whaty.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.whaty.platform.courseware.config.CoursewareConfig;

public class InitCoursewareServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public InitCoursewareServlet() {
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
			path = getInitParameter("coursewareConfigFileDir");
			filepath = prefix+path;
			CoursewareConfig coursewareConfig = new CoursewareConfig(filepath);
			coursewareConfig.getConfig();
			coursewareConfig.setCoursewareConfigAbsPath(application.getServletContextName());
			application.setAttribute("coursewareConfig",coursewareConfig);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
