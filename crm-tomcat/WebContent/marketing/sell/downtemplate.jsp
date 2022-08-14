<%enfo.crm.web.DocumentFile file = new enfo.crm.web.DocumentFile(pageContext);
 
String file_name = pageContext.getRequest().getParameter("file_name");;
String name = pageContext.getRequest().getParameter("name");
if(!(request.getParameter("file_name").equals("")))
	file.downloadProblemFile2(file_name,name);
%>