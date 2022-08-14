<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.vo.* ,enfo.crm.dao.*,enfo.crm.system.*,java.util.*" %>
<%
List elementTdHideList = (List)request.getAttribute("elementTdHideList");
for(int i=0;i<elementTdHideList.size();i++){
Map hideMap = (Map)elementTdHideList.get(i);
String element_codeHide = Utility.trimNull(hideMap.get("ELEMENT_CODE"));
String hideValue = Utility.trimNull(hideMap.get("RESULT_VLAUE"));
%>
<input type="hidden" name="<%=element_codeHide%>" value="<%=hideValue%>">
<%}%>