<%@ page contentType="text/html; charset=GBK"  import="java.util.*,java.math.*,enfo.crm.affair.*,enfo.crm.intrust.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.marketing.*,enfo.crm.tools.*,enfo.crm.dao.*"%>
<%@ include file="/includes/operator.inc" %>
<%
//处理中后期不同的菜单调用相同的页面已解决菜单问题，才添加此参数
String menu_id_v = Utility.trimNull(request.getParameter("menu_id_v"));
if("".equals(menu_id_v))	
	menu_id_v = menu_id;
boolean bSuccess = false;
//获得参数
OperatorLocal local = EJBFactory.getOperator();
String viewstr = Utility.trimNull(request.getParameter("viewstr")).toUpperCase();
local.addMenuView(menu_id_v,input_operatorCode,viewstr);
bSuccess = true;
%>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<script src="<%=request.getContextPath()%>/includes/default.vbs" language="vbscript"></script>
<script src="<%=request.getContextPath()%>/includes/default.js" language="javascript"></script>
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
