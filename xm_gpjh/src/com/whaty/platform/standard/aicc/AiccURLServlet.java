package com.whaty.platform.standard.aicc;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.whaty.platform.standard.aicc.Exception.AiccException;
import com.whaty.platform.standard.aicc.operation.AiccDataManage;
import com.whaty.platform.standard.aicc.operation.AiccFactory;
import com.whaty.platform.standard.aicc.operation.AiccURLParam;
import com.whaty.platform.standard.aicc.operation.AiccUserDataManagePriv;
import com.whaty.platform.standard.aicc.util.AiccLog;


public class AiccURLServlet extends HttpServlet {

	/**
	 * ����Ϊ����HACP��ʽ����Aicc��ݴ���ʱ��Ӧ��aicc_url
	 */
	public AiccURLServlet() {
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
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AiccLog.setDebug("GET aicc data!!!!!!!!!!!!!!!!");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out
				.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the GET method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AiccLog.setDebug("________________________________________________________________________");
		AiccLog.setDebug("begin one AiccURL post__________________________________________________");
		AiccLog.setDebug("________________________________________________________________________");
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		AiccUserDataManagePriv aiccUserPriv=(AiccUserDataManagePriv)session.getAttribute("aiccstudent_priv");
		if(aiccUserPriv!=null && aiccUserPriv.learnCourse==1)
		{
		
			try
			{
				AiccLog.setDebug("parse aicc url!!!!!!!!!!!!!!!!");
				AiccLog.setDebug("aicc_data="+request.getParameter("AICC_Data"));
				AiccURLParam aiccURL=new AiccURLParam(request.getParameter("command"),request.getParameter("session_id"),request.getParameter("version"),request.getParameter("AU_Password"),request.getParameter("AICC_Data"));
				AiccLog.setDebug("url="+aiccURL.toString());
				AiccFactory aiccFactory=AiccFactory.getInstance();
				AiccDataManage aiccDataManage=aiccFactory.creatAiccDataManage();
				aiccDataManage.setAiccURLParam(aiccURL);
				AiccLog.setDebug("begin responseData!!!!");
				ResponseData responseData=aiccDataManage.respToReq();
				AiccLog.setDebug("responseData="+responseData.toString());
				out.print(responseData.toString());
			}
			catch(AiccException e)
			{
				AiccLog.setDebug("AiccURLServlet error!"+e.toString());
			}
			
		}
		else
		{
			AiccLog.setDebug("no right to learn this course!");
			out.print("no right to learn this course!");
		}
		out.flush();
		out.close();
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occure
	 */
	public void init() throws ServletException {
		// Put your code here
	}
	
	public void process(HttpServletRequest request,HttpServletResponse response)
	{
		
	}

}
