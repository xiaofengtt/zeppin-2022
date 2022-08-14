<%@ page contentType="text/html; charset=GBK"  import="java.math.*,java.util.*,enfo.crm.system.*,enfo.crm.dao.*,enfo.crm.customer.*,enfo.crm.tools.*, enfo.crm.vo.*, enfo.crm.intrust.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
boolean bSuccess = false;
Integer product_id = Utility.parseInt(request.getParameter("product_id"),null);
Integer level_id = Utility.parseInt(request.getParameter("level_id"),null);
String level_value_name = Utility.trimNull(request.getParameter("level_value_name"));
BigDecimal min_value = Utility.parseDecimal(request.getParameter("min_value"),null);
BigDecimal max_value = Utility.parseDecimal(request.getParameter("max_value"),null);
CustomerLocal customer = EJBFactory.getCustomer();
CustLevelVO levelvo = new CustLevelVO();
levelvo.setProduct_id(product_id);
levelvo.setLevel_id(level_id);
levelvo.setLevel_value_name(level_value_name);
levelvo.setMin_value(min_value);
levelvo.setMax_value(max_value);
levelvo.setInput_man(input_operatorCode);
customer.addCustLevel(levelvo);
bSuccess=true;
%>
<HTML>
<HEAD>
<TITLE></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<script language=javascript>
<%if(bSuccess){%>
	alert("<%=LocalUtilis.language("message.AddOK",clientLocale)%> ！");//新建成功
	window.returnValue=true;
	window.close();
<%}%>
</script>
</HEAD>
<BODY class="BODY">
<%@ include file="/includes/waiting.inc"%> 
<form name="theform" method="post">
</form>
<%@ include file="/includes/foot.inc"%> 
</BODY>
</HTML>