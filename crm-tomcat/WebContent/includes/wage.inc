<%
String requestUri = request.getRequestURI();
int uriIndex = requestUri.lastIndexOf("/");
requestUri = requestUri.substring(uriIndex + 1, requestUri.length());
String sPage = Utility.trimNull(request.getParameter("page"));
String sPagesize = Utility.trimNull(request.getParameter("pagesize"));

String sUrl = requestUri + "?pagesize=" + sPagesize;
String sUrlEx = requestUri + "?page=" + sPage;

%>