<%@ page contentType="text/html;charset=utf-8" %>
<%@ page language="java" import="javazoom.upload.*,java.util.*,java.io.*" %>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.activity.*,com.whaty.platform.entity.activity.score.*"%>
<%@ page import="com.whaty.platform.entity.*,com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.entity.recruit.*,com.whaty.platform.entity.setup.*"%>
<%@ page import="com.whaty.platform.entity.user.*,com.whaty.platform.entity.test.*"%>
<jsp:directive.page import="org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper"/>
<%@ include file="../pub/priv.jsp"%>
 
<%  

  	String loreId = "";
	String type = "";
	String loreDirId = "";
	String xlsFile ="";
	//out.print("enter exe!<br>");
	int totalSuc = 0;
	  
	try{
   	//实例化一个UploadBean
   	UploadBean upBean=new UploadBean();
   
  	
  	//upBean.setFolderstore("d:/uploads");
  
  	//设定使用的parser,cos�?COSPARSER,structs为STRUTSPARSER,Commons-FileUpload parsers �?CFUPARSER
  	upBean.setParser(MultipartFormDataRequest.COSPARSER);
  
  	//
  	upBean.setParsertmpdir("d:/temp");
  
  	//设置文件名的filter,在blacklist中的文件名不会被上传
  	//upBean.setBlacklist("*.jsp,*.java,*.class");
  
  	//设置文件名的filter,只有在whitelist中的文件名才会被上传
  	upBean.setWhitelist("*.xls");
  
  	
 	 upBean.setOverwrite(false);
  
  	//设置最大的文件上载大小setFilesizelimit(int sizelimitinbytes)
  	//upBean.setFilesizelimit(1024*1024);
  
  	//判断是否为MultipartFormData
 	if (!MultipartFormDataRequest.isMultipartFormData(request)) {
 		return;
  	}
  	
  	//struts2 的request
  	 
  	//MultipartFormDataRequest mrequest = new MultipartFormDataRequest(request);   
  	
  	
  	MultiPartRequestWrapper mpRequest = (MultiPartRequestWrapper)request;
  	 File[] files = mpRequest.getFiles("src"); 
  	
  	String[] fileNames = mpRequest.getFileNames("src");//然后就可以处理你的业务了 
	loreId = request.getParameter("loreId");
	type = request.getParameter("type");
    loreDirId = request.getParameter("loreDirId");
    
   
   	String scr = request.getRealPath("/") + "entity" + File.separator +"incoming" + File.separator + "store_questions" + File.separator + type +File.separator;
  	
  	upBean.setFolderstore(scr);
  	
  	
  	
  
  	//得到存储文件
  //	Hashtable files = mrequest.getFiles();
//  	if ( (files != null) && (!files.isEmpty()) ) {
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
				 
	  
///	    UploadFile file = (UploadFile) files.get("src");
	//    UploadFile file = (UploadFile) files[0];
	    // 存入服务器上
//	    upBean.store(mrequest, "src");
	    
	    //得到文件在服务器上存储的名称
	 //   String filename=upBean.getFilenameOnServer();
	    String filename=fileNames[0];
	 	
	    
	    xlsFile= scr + filename;
	    
	    xlsFile = xlsFile.replace('\\','/');
		session.putValue("xlsFile", filename); 
		
 }
 else
 {
   out.println("<li>No uploaded files");
 }
 }catch(Exception e)
 {
 
 %>
 <script>
  alert("上传文件失败！请验证是否标准的excel文件?");
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
<script language="javascript">
	window.location.href = "store_questions_importexe.jsp?loreId=<%=loreId%>&xlsFile=<%=xlsFile%>&loreDirId=<%=loreDirId%>&type=<%=type%>";
</script>
</body>    
</html>  