<%@ page language="java" pageEncoding="GBK" import="enfo.crm.system.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.web.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>

<%
boolean bSuccess = false;
DocumentFile file = new DocumentFile(pageContext);
String fieldDatas = Utility.trimNull(request.getParameter("fieldDatas"));
String fieldMethod = Utility.trimNull(request.getParameter("fieldMethod"));
String fieldDataType = Utility.trimNull(request.getParameter("fieldDataType"));
file.importCustInfo(fieldDatas,fieldMethod,fieldDataType,input_operatorCode);
bSuccess = true;
%>
<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("message.custImport",clientLocale)%> </TITLE><!--客户导入-->
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<script language=javascript>
<%if (bSuccess){%>
    alert("<%=LocalUtilis.language("message.importCustInfoSuccess",clientLocale)%> ！");//客户信息导入成功
    location = "client_info_list.jsp";
<%}%>

</script>
</HEAD>
<BODY class="BODY">
<form name="theform">   
<%@ include file="/includes/waiting.inc"%>
<%@ include file="/includes/foot.inc"%>
</form>       
</BODY>
</HTML>