<%@ page language="java" pageEncoding="gb2312"%>
<%@ page contentType="text/html; charset=gb2312"%>
<%@page
	import="java.util.*,com.whaty.util.string.*,com.whaty.util.file.*,com.whaty.platform.standard.scorm.util.*,com.whaty.platform.standard.scorm.*,com.whaty.platform.standard.scorm.operation.*,java.io.*,org.adl.parsers.dom.*,javazoom.upload.*,java.util.zip.*,org.xml.sax.InputSource"%>
<%@ page import="java.io.File"%>
<%@ include file="importUtil.jsp"%>
<%@ page import="com.whaty.platform.util.*"%>
<jsp:directive.page
	import="org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper" />
<%
 ScormFactory scormFactory=ScormFactory.getInstance();
%>
<%

String sessionID = new String();
String uploadDir = new String();
String userDir = new String();
String error = new String();
LMSManifestHandler myManifestHandler;
LMSPackageHandler myPackageHandler;

String osname=SystemInfoUtil.getOsName();
//out.println("isname:"+osname);

try
{
	sessionID = session.getId();
   	String theWebPath = getServletConfig().getServletContext().getRealPath( "/" );
   	String tempDir = System.getProperty("java.io.tmpdir"); 
	UploadBean upBean=new UploadBean();
	upBean.setParser(MultipartFormDataRequest.COSPARSER);
	upBean.setParsertmpdir(tempDir);
	upBean.setWhitelist("*.zip");
	upBean.setOverwrite(true);
	ScormLog.setDebug("encoding="+MultipartFormDataRequest.DEFAULTENCODING);
	if (!MultipartFormDataRequest.isMultipartFormData(request))
	{
		return;
	}
  //	MultipartFormDataRequest mrequest = new MultipartFormDataRequest(request);
  	
	//使用struts2 后的方式
    MultiPartRequestWrapper mrequest = (MultiPartRequestWrapper)request;
    File[] files = mrequest.getFiles("coursezipfile"); 
    	//文件现在还在临时目录中 
    String[] fileNames = mrequest.getFileNames("coursezipfile"); 
  	
  	
	//uploadDir = "d:\\SampleRTEFiles\\tempUploads\\" + sessionID;
	uploadDir =tempDir + File.separator + "tempUploads" + File.separator + sessionID;
	
	java.io.File theRTEUploadDir = new java.io.File( uploadDir );
   	if ( !theRTEUploadDir.isDirectory() )
   	{
      	theRTEUploadDir.mkdirs();
   	}
   	upBean.setFolderstore(uploadDir);
   	
//  得到存储文件
    // Hashtable files = mrequest.getFiles();
//    if ( (files != null) && (!files.isEmpty()) )
   // {
	String filename="";
  	if ( (files != null) && (files.length!=0) ) {
    	
    			  	FileInputStream fis = new FileInputStream(files[0]); 
  				FileOutputStream fos = new FileOutputStream(uploadDir+File.separator+fileNames[0]); 
  				byte[] buf = new byte[1024]; 
  				int j = 0; 
  				while ((j = fis.read(buf)) != -1) { 
  				fos.write(buf, 0, j); 
  				}
  				fis.close();
  				fos.close();
  	  filename=fileNames[0];	
  	}
  	
  	
    String courseID=mrequest.getParameter("course_id");
    //StrManage strManage=StrManageFactory.creat(mrequest.getParameter("course_title"));
   //	String courseTitle=strManage.charsetEncode("GBK","UTF-8") ;
   String courseTitle = mrequest.getParameter("course_title");
   	//String courseTitle=mrequest.getParameter("course_title");
   	//String zipFile = mrequest.getParameter( "theZipFile" );
   	String zipFile =uploadDir+File.separator+filename;
   	String controlType = mrequest.getParameter( "controltype" );
   	String navigate = mrequest.getParameter( "navigate" );
    //out.print(courseTitle);
   	// Extract the manfest from the package
   	myPackageHandler = new LMSPackageHandler();
   	myPackageHandler.extract(zipFile, "imsmanifest.xml", uploadDir); 

   	String manifestFile = uploadDir + File.separator + "imsmanifest.xml";
   	String newZip="";
   	if(osname.equals("Linux"))
   	{
   		manifestFile = manifestFile.replace("\\","/");
   		newZip = zipFile.substring( zipFile.lastIndexOf("/")+1);
   	}
   	else
   	{
   		newZip = zipFile.substring( zipFile.lastIndexOf("\\")+1);
   	}
   	
   	zipFile = uploadDir + File.separator + newZip;
   
   	// Create a manifest handler instance
   	myManifestHandler = scormFactory.creatLMSManifestHandler();
   
   	InputSource fileToParse = setUpInputSource( manifestFile );
   	//fileToParse.setEncoding("gb2312");
   	myManifestHandler.setCourseID(courseID);
   	myManifestHandler.setCourseTitle(courseTitle);
   	myManifestHandler.setFileToParse( fileToParse );
   	myManifestHandler.setControl( controlType );
   	myManifestHandler.setNavigate(navigate);
   
   	// Parse the manifest and fill up the object structure
   	boolean result = myManifestHandler.processManifest();
    
    FileManage fm =  FileManageFactory.creat();
    fm.unZip(zipFile,theWebPath + "CourseImports" +File.separator+ courseID);
    
    /*
    //out.print(result);
   	// Get the course ID
   	ZipFile archive = new ZipFile( zipFile );
   
   	// do our own buffering; reuse the same buffer.
   	byte[] buffer = new byte[16384];
   
   	// Loop through each Zip file entry
   	for ( Enumeration e=archive.entries(); e.hasMoreElements(); )
   	{
      	// get the next entry in the archive
      	ZipEntry entry = (ZipEntry) e.nextElement();
        
      	if ( !entry.isDirectory() )
      	{
	         // Set up the name and location of where the file will be 
	         // extracted to
	         String filename = entry.getName();
	         filename = filename.replace('/',java.io.File.separatorChar);
	         filename = theWebPath + "CourseImports\\" + courseID + "\\" + filename;
	         if(osname.equals("Linux"))
	        	 filename = filename.replace("\\","/");
	         java.io.File destFile = new java.io.File(filename);
	         
	         String parent = destFile.getParent();
	         if ( parent != null )
	         {
	            java.io.File parentFile = new java.io.File(parent);
	            if ( !parentFile.exists() )
	            {
	               // create the chain of subdirs to the file
	               parentFile.mkdirs();
	            }
	         }
         
	         // get a stream of the archive entry's bytes
	         InputStream in = archive.getInputStream(entry);
	         
	         // open a stream to the destination file
	         OutputStream outStream = new FileOutputStream(filename);
	         
	         // repeat reading into buffer and writing buffer to file,
	         // until done.  count will always be # bytes read, until
	         // EOF when it is -1.
	         int count;
	         while ( (count=in.read(buffer)) != -1 )
	            outStream.write(buffer, 0, count);
	
	         in.close();
	         outStream.close();
      	}
   	}
      */
   	//  Write the Sequencing Object to a file
   	String sequencingFileName = theWebPath + "CourseImports\\" + courseID + "\\sequence.obj";
   	if(osname.equals("Linux"))
   	 	sequencingFileName = sequencingFileName.replace("\\","/");
   	java.io.File sequencingFile = new java.io.File(sequencingFileName);
   	String t_filename=theWebPath + "CourseImports\\" + courseID;
   	if(osname.equals("Linux"))
   		t_filename=theWebPath + "CourseImports/" + courseID;
   	File f = new File(t_filename);
   	if(!f.exists()){
   		f.mkdirs();
   	}
   	FileOutputStream ostream = new FileOutputStream(sequencingFile);
   	ObjectOutputStream oos = new ObjectOutputStream(ostream); 
   	oos.writeObject(myManifestHandler.getOrgsCopy());
   	oos.flush();
   	oos.close();

   	// Delete uploaded files
   	boolean wasdeleted = false;
   	java.io.File uploadFiles[] = theRTEUploadDir.listFiles();
   	for( int i = 0; i < uploadFiles.length; i++ )
   	{
      	uploadFiles[i].deleteOnExit();
   	}   
   	theRTEUploadDir.deleteOnExit();  

   	// set content-type header before accessing Writer
   	response.setContentType("text/html");
   	response.sendRedirect( "confirmImport.jsp?course_id="+courseID);

}
catch (Exception e)
{
   error = e.getLocalizedMessage();
   session.setAttribute("error",error);
   //out.println("e:"+e.toString());
   response.sendRedirect( "import.jsp?error="+error);
}
%>

Error!:
<%=error%>

