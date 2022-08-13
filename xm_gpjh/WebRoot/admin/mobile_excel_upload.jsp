<%@ page contentType="text/html;charset=utf-8" %>
<%@ page language="java" import="javazoom.upload.*,java.util.*,java.io.*" %>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.activity.*,com.whaty.platform.entity.activity.score.*"%>
<%@ page import="com.whaty.platform.entity.*,com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.entity.recruit.*,com.whaty.platform.entity.setup.*"%>
<%@ page import="com.whaty.platform.entity.user.*,com.whaty.platform.entity.test.*"%>
<%@ include file="./pub/priv.jsp"%>

<%  
  	
	String Out_FileHtml="";
	
   //实例化一个UploadBean
   UploadBean upBean=new UploadBean();
   
  //设置存储在服务器的目录
  //upBean.setFolderstore("d:/uploads");
  
  //设定使用的parser,cos为.COSPARSER,structs为STRUTSPARSER,Commons-FileUpload parsers 为 CFUPARSER
  upBean.setParser(MultipartFormDataRequest.COSPARSER);
  
  //
  upBean.setParsertmpdir("/temp");
  
  //设置文件名的filter,在blacklist中的文件名不会被上传
  //upBean.setBlacklist("*.jsp,*.java,*.class");
  
  //设置文件名的filter,只有在whitelist中的文件名才会被上传
  upBean.setWhitelist("*.xls");
  
  //设置文件的覆盖方式,true表示覆盖相同名称的文件,false表示不覆盖,生成新的文件名,缺省为false
  upBean.setOverwrite(false);
  
  //设置最大的文件上载大小setFilesizelimit(int sizelimitinbytes)
  //upBean.setFilesizelimit(1024*1024);
  
  //判断是否为MultipartFormData
  if (!MultipartFormDataRequest.isMultipartFormData(request))
  {
 	return;
  }
  MultipartFormDataRequest mrequest = new MultipartFormDataRequest(request);   

  //得到存储路径

//   String scr=com.whaty.parameter.g_basedir +  "/work/manager/achievement/xls/";
  String scr = request.getRealPath("/") + "incoming" + File.separator +"entity" + File.separator;
  //设置存储在服务器的目录
  upBean.setFolderstore(scr);
  
 
  
  //得到存储文件
  Hashtable files = mrequest.getFiles();
  if ( (files != null) && (!files.isEmpty()) )
  {
    //得到上传文件的属性
    UploadFile file = (UploadFile) files.get("src");
    //if (file != null) out.println("<li>Form fields : uploadfile"+"<BR> Uploaded file : "+file.getFileName()+" ("+file.getFileSize()+" bytes)"+"<BR> Content Type : "+file.getContentType());
    
    // 存入服务器上
    upBean.store(mrequest, "src");
    
    //得到文件在服务器上存储的名称
    String filename=upBean.getFilenameOnServer();
    //Out_FileHtml="<a href=\""+com.whaty.parameter.g_incomingURI+baseurl+"/"+filename+"\" alt=\""+S_alt+"\" >"+S_alt+"("+file.getFileSize()+"Bytes)</a>";
      		session.putValue("scr", scr); 
		session.putValue("T_filename", filename); 

 }
 else
 {
  out.println("<li>No uploaded files");
 }
%>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link rel="stylesheet" type="text/css" href="./css/FORUM.CSS">
<title>插入附件</title>
</head>

<body topmargin="10" leftmargin="10" rightmargin="0">
表格上载成功！
<%
	response.sendRedirect("mobile_excel_addexe.jsp");
%>
</body>    
</html>  