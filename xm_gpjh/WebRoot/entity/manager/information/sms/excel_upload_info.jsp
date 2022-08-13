<%@ page contentType="text/html;charset=utf-8" %>
<%@ page language="java" import="javazoom.upload.*,java.util.*,java.io.*" %>
<jsp:directive.page import="org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper"/>

<%  	
	String Out_FileHtml="";
		
	//实例化一个UploadBean
	try { 
		UploadBean upBean=new UploadBean();
		 
		//设置存储在服务器的目录
		//upBean.setFolderstore("d:/uploads");
		
		//设定使用的parser,cos为.COSPARSER,structs为STRUTSPARSER,Commons-FileUpload parsers 为 CFUPARSER
		upBean.setParser(MultipartFormDataRequest.COSPARSER);
		
		//
		upBean.setParsertmpdir("d:/temp");
		
		//设置文件名的filter,在blacklist中的文件名不会被上传
		//upBean.setBlacklist("*.jsp,*.java,*.class");
		
		//设置文件名的filter,只有在whitelist中的文件名才会被上传
		upBean.setWhitelist("*.xls");
		
		//设置文件的覆盖方式,true表示覆盖相同名称的文件,false表示不覆盖,生成新的文件名,缺省为false
		upBean.setOverwrite(false);
		
		//设置最大的文件上载大小setFilesizelimit(int sizelimitinbytes)
		//upBean.setFilesizelimit(1024*1024);
		  
		//判断是否为MultipartFormData
		if (!MultipartFormDataRequest.isMultipartFormData(request)) {
			return;
		}
		MultiPartRequestWrapper mpRequest = (MultiPartRequestWrapper)request;
	  	File[] files = mpRequest.getFiles("src"); 
	  	//文件现在还在临时目录中 
	  	String[] fileNames = mpRequest.getFileNames("src");//然后就可以处理你的业务了 
	  	
	   	String scr = request.getRealPath("/") + "incoming" + File.separator +"sms" + File.separator;
	  	//设置存储在服务器的目录
	  	upBean.setFolderstore(scr);
	  	
	  	if ( (files != null) && (files.length!=0) ) {
	  	
	  			  	FileInputStream fis = new FileInputStream(files[0]); 
					FileOutputStream fos = new FileOutputStream(scr+fileNames[0]); 
					byte[] buf = new byte[1024]; 
					int j = 0; 
					while ((j = fis.read(buf)) != -1) { 
					fos.write(buf, 0, j); 
					}
					fis.close(); 
					fos.close();
					
		    String filename=fileNames[0];
		 	
			session.putValue("scr", scr); 
			session.putValue("T_filename", filename);
		} else {
%>
 <script>
  alert("上传文件失败！请验证是否标准的excel文件！");
  window.history.back(-1);
 </script>
 <%
			return;
		}
 	} catch(Exception e) {
 	e.printStackTrace();
 %>
 <script>
  alert("上传文件失败！请验证是否标准的excel文件！");
  window.history.back(-1);
 </script>
<%
		return;
	}
%>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link rel="stylesheet" type="text/css" href="./css/FORUM.CSS">
<title></title>
</head>

<body topmargin="10" leftmargin="10" rightmargin="0">
表格上载成功！
<%
	response.sendRedirect("mobile_insert_exe.jsp");
%>
</body>    
</html>  