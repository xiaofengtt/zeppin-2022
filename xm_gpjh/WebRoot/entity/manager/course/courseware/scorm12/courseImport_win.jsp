<%@ page language="java" pageEncoding="gb2312"%>
<%@ page contentType="text/html; charset=gb2312"%>
<%@page
	import="java.util.*,com.whaty.util.string.*,com.whaty.util.file.*,com.whaty.platform.standard.scorm.util.*,com.whaty.platform.standard.scorm.*,com.whaty.platform.standard.scorm.operation.*,java.io.*,org.adl.parsers.dom.*,javazoom.upload.*,java.util.zip.*,org.xml.sax.InputSource"%>
<%@ page import="java.io.File"%>
<%@ include file="importUtil.jsp"%>
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
  	
	//ʹ��struts2 ��ķ�ʽ
    MultiPartRequestWrapper mrequest = (MultiPartRequestWrapper)request;
    File[] files = mrequest.getFiles("coursezipfile"); 
    	//�ļ����ڻ�����ʱĿ¼�� 
    String[] fileNames = mrequest.getFileNames("coursezipfile"); 
  	
  	
	//uploadDir = "d:\\SampleRTEFiles\\tempUploads\\" + sessionID;
	uploadDir =tempDir + File.separator + "tempUploads" + File.separator + sessionID;
	
	java.io.File theRTEUploadDir = new java.io.File( uploadDir );
   	if ( !theRTEUploadDir.isDirectory() )
   	{
      	theRTEUploadDir.mkdirs();
   	}
   	upBean.setFolderstore(uploadDir);
   	
//  �õ��洢�ļ�
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
   //	manifestFile = manifestFile.replace("\\","/");
   	String newZip = zipFile.substring( zipFile.lastIndexOf("\\")+1);
   	zipFile = uploadDir + File.separator + newZip;
   
   	// Create a manifest handler instance
   	myManifestHandler = scormFactory.creatLMSManifestHandler();
   
   	InputSource fileToParse = setUpInputSource( manifestFile );
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
	        // filename = filename.replace("\\","/");
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
   	 //sequencingFileName = sequencingFileName.replace("\\","/");
   	java.io.File sequencingFile = new java.io.File(sequencingFileName);
   	File f = new File(theWebPath + "CourseImports\\" + courseID);
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
   
   response.sendRedirect( "import.jsp?error="+error);
}

%>

Error!:
<%=error%>

