<%@ page contentType="text/html; charset=GBK"  import="java.util.*,java.math.*,enfo.crm.affair.*,enfo.crm.intrust.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.marketing.*,enfo.crm.tools.*,enfo.crm.dao.*"%>
<%@ include file="/includes/operator.inc" %>
<%
//�����к��ڲ�ͬ�Ĳ˵�������ͬ��ҳ���ѽ���˵����⣬����Ӵ˲���
String menu_id_v = Utility.trimNull(request.getParameter("menu_id_v"));
if("".equals(menu_id_v))	
	menu_id_v = menu_id;
boolean bSuccess = false;
//��ò���
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
