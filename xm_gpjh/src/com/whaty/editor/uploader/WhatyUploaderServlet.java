/*
 * FCKeditor - The text editor for internet
 * Copyright (C) 2003-2005 Frederico Caldeira Knabben
 * 
 * Licensed under the terms of the GNU Lesser General Public License:
 * 		http://www.opensource.org/licenses/lgpl-license.php
 * 
 * For further information visit:
 * 		http://www.fckeditor.net/
 * 
 * File Name: WhatyUploaderServlet.java
 * 	Java File Uploader class.
 * 
 * Version:  2.3
 * Modified: 2005-08-11 16:29:00
 * 
 * File Authors:
 * 		Simone Chiaretta (simo@users.sourceforge.net)
 */ 
 
package com.whaty.editor.uploader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javazoom.upload.MultipartFormDataRequest;
import javazoom.upload.UploadBean;

import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;




/**
 * Servlet to upload files.<br>
 *
 * This servlet accepts just file uploads, eventually with a parameter specifying file type
 *
 * @author Simone Chiaretta (simo@users.sourceforge.net)
 */

public class WhatyUploaderServlet extends HttpServlet {
	
	private static String baseDir;
	private static boolean debug=false;
	private static boolean enabled=false;
	private static boolean overwrite=false;
	private static Hashtable allowedExtensions;
	private static Hashtable deniedExtensions;
	
	/**
	 * Initialize the servlet.<br>
	 * Retrieve from the servlet configuration the "baseDir" which is the root of the file repository:<br>
	 * If not specified the value of "/UserFiles/" will be used.<br>
	 * Also it retrieve all allowed and denied extensions to be handled.
	 *
	 */
	 public void init() throws ServletException {
	 	
	 	debug=(new Boolean(getInitParameter("debug"))).booleanValue();
	 	
	 	overwrite=(new Boolean(getInitParameter("overwrite"))).booleanValue();
	 	
	 	if(debug) System.out.println("\r\n---- WhatyUploaderServlet initialization started ----");
	 	
		baseDir=getInitParameter("baseDir");
		enabled=(new Boolean(getInitParameter("enabled"))).booleanValue();
		if(baseDir==null)
			baseDir="/UserFiles/";
		String realBaseDir=getServletContext().getRealPath(baseDir);
		File baseFile=new File(realBaseDir);
		if(!baseFile.exists()){
			baseFile.mkdir();
		}
		
		allowedExtensions = new Hashtable(3);
		deniedExtensions = new Hashtable(3);
				
		allowedExtensions.put("File",stringToArrayList(getInitParameter("AllowedExtensionsFile")));
		deniedExtensions.put("File",stringToArrayList(getInitParameter("DeniedExtensionsFile")));

		allowedExtensions.put("Image",stringToArrayList(getInitParameter("AllowedExtensionsImage")));
		deniedExtensions.put("Image",stringToArrayList(getInitParameter("DeniedExtensionsImage")));
		
		allowedExtensions.put("Flash",stringToArrayList(getInitParameter("AllowedExtensionsFlash")));
		deniedExtensions.put("Flash",stringToArrayList(getInitParameter("DeniedExtensionsFlash")));
		
		if(debug) System.out.println("---- WhatyUploaderServlet initialization completed ----\r\n");
		
	}
	

	/**
	 * Manage the Upload requests.<br>
	 *
	 * The servlet accepts commands sent in the following format:<br>
	 * simpleUploader?Type=ResourceType<br><br>
	 * It store the file (renaming it in case a file with the same name exists) and then return an HTML file
	 * with a javascript command in it.
	 *
	 */	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		if (debug) System.out.println("--- BEGIN DOPOST ---");

		response.setContentType("text/html; charset=UTF-8");
		response.setHeader("Cache-Control","no-cache");
		PrintWriter out = response.getWriter();
		

		String typeStr=request.getParameter("Type");
		
		if(typeStr==null &&typeStr.equals(""))
		{
			typeStr="File";
		}
		
		String currentPath=baseDir+typeStr;
		String currentDirPath=getServletContext().getRealPath(currentPath);
		currentPath=request.getContextPath()+currentPath;
		
		if (debug) System.out.println(currentDirPath);
		
		String retVal="0";
		String newName="";
		String fileUrl="";
		String errorMessage="";
		
		if(enabled) {		
			try {
				
				// 实例化一个UploadBean
				UploadBean upBean=new UploadBean();	
				//设定使用的parser,cos为.COSPARSER,structs为STRUTSPARSER,Commons-FileUpload parsers 为 CFUPARSER
				upBean.setParser(MultipartFormDataRequest.COSPARSER);

				//如果是structs方式需要设置
				//upBean.setParsertmpdir("e:\\temp");
				  
				//设置文件名的filter,在blacklist中的文件名不会被上传
				//upBean.setBlacklist("*.jsp,*.java,*.class");
				  
				//设置文件名的filter,只有在whitelist中的文件名才会被上传
				//upBean.setWhitelist("*.txt");
								
				//设置文件的覆盖方式,true表示覆盖相同名称的文件,false表示不覆盖,生成新的文件名,缺省为false
				  upBean.setOverwrite(overwrite);
				  
				  //设置最大的文件上载大小setFilesizelimit(int sizelimitinbytes)
				  //upBean.setFilesizelimit(1024*1024);
				  
				  //判断是否为MultipartFormData
				  if (!MultipartFormDataRequest.isMultipartFormData(request))
				  {
				 	return;
				  }
//				  MultipartFormDataRequest mrequest = new MultipartFormDataRequest(request);  
				  
				  
				  //使用struts2 后的方式
				  MultiPartRequestWrapper mpRequest = (MultiPartRequestWrapper)request;
				  File[] files = mpRequest.getFiles("NewFile"); 
				  	//文件现在还在临时目录中 
				  	String[] fileNames = mpRequest.getFileNames("NewFile");//然后就可以处理你的业务了 
				  
				  
				  //设置存储在服务器的目录
				  upBean.setFolderstore(currentDirPath);
				  
				  //得到存储文件
//				  Hashtable files = mrequest.getFiles();
				  if ( (files != null) && (files.length !=0) )
				  {
				    //得到上传文件的属性
//				    UploadFile file = (UploadFile) files.get("NewFile");
					  
				    
				    //判断是否允许上传
				    String fileNameLong=fileNames[0];
					fileNameLong=fileNameLong.replace('\\','/');
					String[] pathParts=fileNameLong.split("/");
					String fileName=pathParts[pathParts.length-1];
					String ext=getExtension(fileName);
					
					//将文件名修改为日期  gaoyuan 2009.12.28
					String s_filename="";
					Calendar CD = Calendar.getInstance();
					String YY = CD.get(Calendar.YEAR) +"";
					String MM = CD.get(Calendar.MONTH) +"";
					String DD = CD.get(Calendar.DATE)+ "";
					String HH = CD.get(Calendar.HOUR)+"";
					String NN = CD.get(Calendar.MINUTE)+"";
					String SS = CD.get(Calendar.SECOND)+"";
					s_filename=YY+MM+DD+HH+NN+SS+"."+ext;
					fileNameLong=s_filename;
					
					if(extIsAllowed(typeStr,ext))
					{
						
						FileInputStream fis = new FileInputStream(files[0]); 
						FileOutputStream fos = new FileOutputStream(currentDirPath+File.separator+s_filename); 
						byte[] buf = new byte[1024]; 
						int j = 0; 
						while ((j = fis.read(buf)) != -1) { 
						fos.write(buf, 0, j); 
						}
						fis.close(); 
						fos.close();
						 // 存入服务器上
//					    upBean.store(mrequest, "NewFile");
					    
					    //得到文件在服务器上存储的名称
//					    newName=upBean.getFilenameOnServer();
					    
					    fileUrl=currentPath+"/"+fileNameLong;
					}
					else {
						retVal="202";
						errorMessage="";
						if (debug) System.out.println("Invalid file type: " + ext);	
					}
				  }
			}catch (Exception ex) {
				if (debug) ex.printStackTrace();
				retVal="203";
			}
		}
		else {
			retVal="1";
			errorMessage="This file uploader is disabled. Please check the WEB-INF/web.xml file";
		}
		
		
		out.println("<script type=\"text/javascript\">");
		out.println("window.parent.OnUploadCompleted("+retVal+",'"+fileUrl+"','"+newName+"','"+errorMessage+"');");
		out.println("</script>");
		out.flush();
		out.close();
	
		if (debug) System.out.println("--- END DOPOST ---");	
		
	}


	/*
	 * This method was fixed after Kris Barnhoorn (kurioskronic) submitted SF bug #991489
	 */
  	private static String getNameWithoutExtension(String fileName) {
    		return fileName.substring(0, fileName.lastIndexOf("."));
    	}
    	
	/*
	 * This method was fixed after Kris Barnhoorn (kurioskronic) submitted SF bug #991489
	 */
	private String getExtension(String fileName) {
		return fileName.substring(fileName.lastIndexOf(".")+1);
	}



	/**
	 * Helper function to convert the configuration string to an ArrayList.
	 */
	 
	 private ArrayList stringToArrayList(String str) {
	 
	 if(debug) System.out.println(str);
	 String[] strArr=str.split("\\|");
	 	 
	 ArrayList tmp=new ArrayList();
	 if(str.length()>0) {
		 for(int i=0;i<strArr.length;++i) {
		 		if(debug) System.out.println(i +" - "+strArr[i]);
		 		tmp.add(strArr[i].toLowerCase());
			}
		}
		return tmp;
	 }


	/**
	 * Helper function to verify if a file extension is allowed or not allowed.
	 */
	 
	 private boolean extIsAllowed(String fileType, String ext) {
	 		
	 		ext=ext.toLowerCase();
	 		
	 		ArrayList allowList=(ArrayList)allowedExtensions.get(fileType);
	 		ArrayList denyList=(ArrayList)deniedExtensions.get(fileType);
	 		
	 		if(allowList.size()==0)
	 			if(denyList.contains(ext))
	 				return false;
	 			else
	 				return true;

	 		if(denyList.size()==0)
	 			if(allowList.contains(ext))
	 				return true;
	 			else
	 				return false;
	 
	 		return false;
	 }

}

