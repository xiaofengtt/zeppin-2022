<%
String requestUri = request.getRequestURI();
int uriIndex = requestUri.lastIndexOf("/");
requestUri = requestUri.substring(uriIndex + 1, requestUri.length());
String sPage = enfo.crm.tools.Utility.trimNull(request.getParameter("page"));

String sPagesize = enfo.crm.tools.Utility.trimNull(request.getParameter("pagesize"));
String defaultPagesize = enfo.crm.tools.Utility.trimNull(request.getSession().getAttribute("defaultPagesize"));
if("".equals(sPagesize)){
	sPagesize = defaultPagesize ;
}
String sKeyword = request.getParameter("keyword");
String sUrl = requestUri + "?pagesize=" + sPagesize;
String sUrlEx = requestUri + "?page=" + sPage;
if((sKeyword != null) && (!sKeyword.equals("")))
{
	sUrl = sUrl + "&keyword=" + enfo.crm.tools.Utility.trimNull(sKeyword);
	sUrlEx = sUrlEx + "&keyword=" + enfo.crm.tools.Utility.trimNull(sKeyword);
}
String sKeyword1 = enfo.crm.tools.Utility.trimNull(request.getParameter("keyword1"));
String sKeyword2 = enfo.crm.tools.Utility.trimNull(request.getParameter("keyword2"));
String sKeyword3 = enfo.crm.tools.Utility.trimNull(request.getParameter("keyword3"));

%>