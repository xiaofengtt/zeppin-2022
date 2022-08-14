<%@page import="java.io.*,java.net.*,enfo.crm.tools.*"%>
<%
String path_base=Argument.getValueOfTDictParam("800903");
String fname=request.getParameter("fname");
fname=path_base+fname;
//File file = new File("c:/uploadfiles/2017/9/88117-118-27992-1011.mp4");
InputStream fis = new BufferedInputStream(new FileInputStream(fname));
byte[] buffer = new byte[fis.available()];
fis.read(buffer);
fis.close();
response.reset();
//response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
//response.addHeader("Content-Length", "" + file.length());
OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
response.setContentType("application/octet-stream");
toClient.write(buffer);
toClient.flush();
toClient.close();
%>
