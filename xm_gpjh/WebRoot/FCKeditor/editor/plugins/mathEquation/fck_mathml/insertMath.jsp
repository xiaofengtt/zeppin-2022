<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.io.*"%>
<%@ page import="java.util.*"%>

<%!
 
  /////////////////////////// 
   //以时间为种子数产生新文件名 
   public String getNewFileName() { 
       int pos=0;                         //.的位置 
   long seed=0;                       //随机种子数 
   String newFileName;  //存入文件扩展名 
   //System.out.println("upload file name："+fileName); 
   //pos=fileName.lastIndexOf("."); 
   //ext=fileName.substring(pos);       //得到扩展名 
   seed=new Date().getTime(); 
   Random rand=new Random(seed);//以时间为种子产生随机数作为文件名 
   newFileName="MathML"+Long.toString(Math.abs(rand.nextInt()));   //生成文件名 
  
   return newFileName; 
      
   } 

%>
<%

//WhatyEditorConfig editorConfig=(WhatyEditorConfig)session.getAttribute("whatyEditorConfig");
String filename,imageDir,appDir,imgtype,ext,Out_ImageHTML;
String MathML=request.getParameter("MathML");
String imageString=request.getParameter("image");


int EqHeight,EqWidth,EqFontSize;
 EqHeight=java.lang.Integer.parseInt(request.getParameter("height"));
 EqWidth=java.lang.Integer.parseInt(request.getParameter("width"));
 EqFontSize=java.lang.Integer.parseInt(request.getParameter("size"));
 imgtype=request.getParameter("imgtype");
 
 if("jpeg".equals(imgtype))
 ext=".jpg";
 else if ("gif".equals(imgtype)) ext=".gif";
 else ext=".png"; 

  String uripath=application.getInitParameter("mathEquationUriPath");
  //String uripath = editorConfig.getUploadURI();
  //String baseurl=request.getParameter("baseurl")+request.getParameter("subfolder"); //这里得到的是相对目录
  //String root_dir=editorConfig.getUploadAbsPath();  
  String root_dir=application.getInitParameter("mathEquationPath");
filename=getNewFileName();

            
try { 

File myDir=new File(root_dir);
if (!myDir.isDirectory()) myDir.mkdirs();
FileOutputStream outStream0 = new FileOutputStream(root_dir+ File.separator + filename+".gif");		
outStream0.write(WhatyMath.util.Base64.decode(imageString));
outStream0.close();  

} 
catch (java.io.IOException ex) { 
System.err.println("Problems invoking class : "+ex); 
}


//Out_ImageHTML="<IMG src=\""+uripath+filename+ext+"\" width=\""+EqWidth+"\"  height=\""+EqHeight+"\">"; 
Out_ImageHTML=uripath+filename+ext; 
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body bgcolor="white">
<%=Out_ImageHTML%>
<script>
var ImageHtml='<%=Out_ImageHTML%>';
var ImageWidth='<%=EqWidth%>';
var ImageHeight='<%=EqHeight%>';

//alert(ImageHtml);
parent.doInsert(ImageHtml,ImageWidth,ImageHeight);
//parent.close();
</script>
</body>
</html>