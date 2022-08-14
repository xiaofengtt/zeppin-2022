<%@ page contentType="text/html; charset=GBK"  import="java.math.*,java.util.*,enfo.crm.system.*,enfo.crm.dao.*,enfo.crm.customer.*,enfo.crm.tools.*, enfo.crm.vo.*, enfo.crm.intrust.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
boolean bSuccess = false;
Integer product_id = Utility.parseInt(request.getParameter("product_id"),null);
String[] s = request.getParameterValues("serial_no");
CustomerLocal customer = EJBFactory.getCustomer();
CustLevelVO levelvo = new CustLevelVO();

for(int i = 0; i < s.length; i++)
{
	int serial_no = Utility.parseInt(s[i], 0);
	if(serial_no != 0)
	{
		levelvo.setSerial_no(new Integer(serial_no));
		levelvo.setInput_man(input_operatorCode);
		customer.delCustLevel(levelvo);
	}
}
customer.remove();
bSuccess = true;
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
	alert("<%=LocalUtilis.language("message.deleteOk",clientLocale)%> ");//ɾ���ɹ�
	location="contract_level_setting.jsp?product_id=<%=product_id%>"
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