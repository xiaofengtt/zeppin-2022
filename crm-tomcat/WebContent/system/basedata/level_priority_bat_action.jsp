<%@ page contentType="text/html; charset=GBK"  import="java.math.*,java.util.*,enfo.crm.system.*,enfo.crm.dao.*,enfo.crm.customer.*,enfo.crm.tools.*, enfo.crm.vo.*, enfo.crm.intrust.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
boolean bSuccess = false;
Integer rProductId = Utility.parseInt(request.getParameter("rProductId"),new Integer(0));
Integer op_code = Utility.parseInt(request.getParameter("op_code"),new Integer(0));
String[] s_product_id = request.getParameterValues("product_id");
String retUrl = "level_priority_bat.jsp?product_id="+rProductId;
Integer product_id;
Integer level_value_id ;
CustLevelAuthVO vo = new CustLevelAuthVO();
OperatorLocal operator = EJBFactory.getOperator();

for(int i = 0; i < s_product_id.length; i++)
{
	product_id = Utility.parseInt(Utility.splitString(s_product_id[i],"$")[0],new Integer(0));
	level_value_id = Utility.parseInt(Utility.splitString(s_product_id[i],"$")[1],new Integer(0));
	vo.setProduct_id(product_id);
	vo.setLevel_value_id(level_value_id);
	vo.setOp_code(op_code);
	vo.setInput_man(input_operatorCode);
	operator.addCustLevelAuth(vo);
}
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
	alert("<%=LocalUtilis.language("message.setOK",clientLocale)%> ");//…Ë÷√≥…π¶
	location = "<%=retUrl%>";
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