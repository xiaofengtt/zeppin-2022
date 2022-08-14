<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.system.*,enfo.crm.vo.*,enfo.crm.service.*,enfo.crm.dao.*,enfo.crm.tools.*,java.util.*"%>
<%@ include file="/includes/operator.inc" %>
<html> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title></title>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="/includes/default.css" type=text/css rel=stylesheet>
<base target="content">
<script src="includes/JSpublic/treejs/ua.js"></script>

<!-- Infrastructure code for the tree -->
<script src="includes/JSpublic/treejs/ftiens4.js"></script>
<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>

</head>
<body topmargin="0" leftmargin="0" rightmargin="0" >
<script language="javascript">
<%
MenuInfoLocal menu = EJBFactory.getMenuInfo();
LogListLocal logList = EJBFactory.getLogList();

String menuId = Utility.trimNull(request.getParameter("menu_id"));
Utility.debugln("menuID is " + menuId);

if(menuId.equals(""))
{
	throw new Exception("无法取得菜单id！");
}
List menuList = null;
Map menuMap = null;

menuList = menu.listMenuInfo(menuId,languageFlag);
menuMap = (Map)menuList.get(0);

String url = Utility.trimNull(menuMap.get("URL"));

session.setAttribute("menu_id",Utility.trimNull(menuMap.get("MENU_ID")));
session.setAttribute("menu_info",Utility.trimNull(menuMap.get("MENU_INFO")));
session.setAttribute("menu_url",Utility.trimNull(menuMap.get("MENU_URL")));

Integer LOG0001=(Integer)session.getAttribute("LOG0001");
if(LOG0001.intValue()==1)
{
	logList.addLog(new Integer(91001),"点击菜单",input_operatorCode,Utility.trimNull(menuMap.get("MENU_NAME"))); 
}

if(!(url.equals(""))){
	if((Utility.trimNull(menuMap.get("MENU_ID")).substring(0,1)).equals("9") && !(Utility.trimNull(menuMap.get("MENU_ID")).substring(0,3)).equals("901") ){
%>
location.href="/webreport/init.jsp?filename=http://<%=request.getServerName()%>:<%=request.getServerPort()%>/webreport/Cells<%=Utility.trimNull(menuMap.get("URL"))%>";
<%}else{%>
location.href="<%=url%>?firstFlag=1&menuIn=0&UNQUERY=1"
<%}}%>
</script>

</body>
</html>
<%menu.remove();logList.remove();%>