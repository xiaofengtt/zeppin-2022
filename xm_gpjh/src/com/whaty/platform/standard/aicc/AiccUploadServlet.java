package com.whaty.platform.standard.aicc;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javazoom.upload.MultipartFormDataRequest;
import javazoom.upload.UploadBean;
import javazoom.upload.UploadException;
import javazoom.upload.UploadFile;

import com.whaty.platform.standard.aicc.Exception.AiccException;
import com.whaty.platform.standard.aicc.operation.AiccFactory;
import com.whaty.platform.standard.aicc.operation.AiccFileManage;

public class  AiccUploadServlet extends HttpServlet {

	/**
	 * ����Ϊ�������ص�Aicc�����ļ����еĲ���
	 */
	public AiccUploadServlet() {
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
		
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		
		try
		{
			   //ʵ��һ��UploadBean
			   UploadBean upBean=new UploadBean();
			   
			   //�趨ʹ�õ�parser,cosΪ.COSPARSER,structsΪSTRUTSPARSER,Commons-FileUpload parsers Ϊ CFUPARSER
			  upBean.setParser(MultipartFormDataRequest.COSPARSER);
			  
			  //�����ļ����filter,ֻ����whitelist�е��ļ���Żᱻ�ϴ�
			  upBean.setWhitelist("*.zip");
			  
			  //�����ļ��ĸ��Ƿ�ʽ,true��ʾ������ͬ��Ƶ��ļ�,false��ʾ������,����µ��ļ���,ȱʡΪfalse
			  upBean.setOverwrite(true);
			  
			  //���������ļ����ش�СsetFilesizelimit(int sizelimitinbytes)
			  upBean.setFilesizelimit(1024*1024);  //1M
			  
			  //�ж��Ƿ�ΪMultipartFormData
			  if (!MultipartFormDataRequest.isMultipartFormData(request))
			  {
				  return;
			  }
			  MultipartFormDataRequest mrequest = new MultipartFormDataRequest(request);   
			  String course_id=mrequest.getParameter("course_id");
			 
			  
			  upBean.setFolderstore("d:\\temp");
			  //�õ��洢�ļ�
			  Hashtable files = mrequest.getFiles();
			  if ( (files != null) && (!files.isEmpty()) )
			  {
				    UploadFile file = (UploadFile) files.get("src");
				    upBean.store(mrequest, "src");
				    String fileName=upBean.getFilenameOnServer();
				    File packageFile=new File("d:\\temp\\"+fileName);
				    //��Aicc����
				    try
				    {
				    	
				    	AiccFactory aiccFactory=AiccFactory.getInstance();
					    AiccFileManage aiccFileManage=aiccFactory.creatAiccFileManage();
					    aiccFileManage.setCourse_id(course_id);
					    if(aiccFileManage.courseIsExist())
					    {
					    	response.sendRedirect("/whatymanager/training/manager/course/courseware/aicc/importError.jsp?course_id="+course_id);
						}
					    else
				    	{
					    	aiccFileManage.setUploadFile(packageFile);
					    	aiccFileManage.parseAiccFile();
					    	response.sendRedirect("/whatymanager/training/manager/course/courseware/aicc/importSuccess.jsp?course_id="+course_id);
				    	}
					}
				    catch(AiccException e)
				    {
				    	response.sendRedirect("/whatymanager/training/manager/course/courseware/aicc/importError.jsp?course_id="+course_id);
				    }
				   
			   }
			   else
			   {
				   	
					out.println("no file!");
			   }
		}
		catch(UploadException e)
		{
			out.println(e.toString());
		}
		out.println("  </BODY>");
		out.println("</HTML>");
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

}
