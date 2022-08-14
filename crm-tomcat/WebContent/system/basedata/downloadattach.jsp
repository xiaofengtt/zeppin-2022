<%@page import="enfo.crm.dao.BusiException"%>
<%enfo.crm.web.DocumentFile2 file = new enfo.crm.web.DocumentFile2(pageContext);
 
String file_name = pageContext.getRequest().getParameter("file_name");;
String name = pageContext.getRequest().getParameter("name");
try{
	if(!(request.getParameter("file_name").equals("")))
		file.downloadProblemFile2(file_name,name);
}catch(BusiException e){
	out.println("<script type=\"text/javascript\">alert('"+e.getMessage()+"')</script>");
	out.println("<script type=\"text/javascript\">window.close();</script>");
	return;
}


out.clear();
out = pageContext.pushBody();
%>