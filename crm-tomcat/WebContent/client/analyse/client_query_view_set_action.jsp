<%@ page contentType="text/html; charset=GBK"  import="java.util.*,java.math.*,enfo.crm.affair.*,enfo.crm.intrust.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*"%>
<%@ include file="/includes/operator.inc" %>
<%
boolean bSuccess = false;
//获得参数
OperatorLocal local = EJBFactory.getOperator();
String viewstr = Utility.trimNull(request.getParameter("viewstr")).toUpperCase();
local.addMenuView(menu_id,input_operatorCode,viewstr);
bSuccess = true;
%>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<script src="<%=request.getContextPath()%>/includes/default.vbs" language="vbscript"></script>
<script src="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js" language="javascript"></script>
	<script language="javascript">
	<%if(bSuccess){%>
		window.returnValue=1;
		window.close();
	<%}%>
	</script>
</head>
<body>
</body>
<%local.remove();%>