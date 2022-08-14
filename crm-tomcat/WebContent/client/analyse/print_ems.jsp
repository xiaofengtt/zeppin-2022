<%@ page language="java" pageEncoding="GBK" import="java.util.*,java.math.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK HREF="/includes/default.css" TYPE="text/css" REL="stylesheet">
<LINK href="/includes/print.css" type=text/css rel=stylesheet>
<base target="_self">
<SCRIPT LANGUAGE="javascript" SRC="/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT language="JavaScript">

function printStart(form)
{
	if(sl_confirm('您确认要打印'))
	{
		btnprint.style.display='none'; 
		window.print();
		btnprint.style.display='';
	}
}

</SCRIPT>

</head>


<body topmargin="8" leftmargin="8" rightmargin="8" onkeydown="javascript:chachEsc(window.event.keyCode)" class="BODY">
<form name="theform"  method="post">
<table border="0" width="100%" id="btnprint" height="7%">
	<tr>
		<td width="100%" align="center">
		<OBJECT  id=WebBrowser  classid=CLSID:8856F961-340A-11D0-A96B-00C04FD705A2  height=0  width=0 VIEWASTEXT></OBJECT>
		<button type="button"  class="xpbutton4" accessKey=a id="btnPrintset" name="btnPrintset" onclick="javascript:document.all.WebBrowser.ExecWB(8,1);">页面设置(<u>A</u>)</button>
		&nbsp;&nbsp;&nbsp; 
		<button type="button"  class="xpbutton3" accessKey=p name="btnCancel" title="打印" onclick="javascript:printStart(document.theform);">打印(<u>P</u>)</button>
		&nbsp;&nbsp;&nbsp; 
		<button type="button"  class="xpbutton3" accessKey=b name="btnReturn" onclick="javascript:window.close();">返回(<u>B</u>)</button>
		&nbsp;&nbsp;&nbsp;</td>
	</tr>
</table>

<%
//获得系统客户信息
UserInfoLocal user_local = EJBFactory.getUserInfo();
UserInfoVO user_vo = new UserInfoVO();
user_vo.setUser_id(user_id);
List user_list = user_local.queryUserInfo(user_vo);
Map user_map = new HashMap();
if(user_list != null && user_list.size() > 0){
	user_map = (Map)user_list.get(0);
}
//获得客户信息
CustomerLocal cust_local = EJBFactory.getCustomer();
CustomerVO cust_vo = new CustomerVO();
Integer strID = null;
Map cust_map = new HashMap();
String[] s = request.getParameterValues("cust_id");		
if (s != null)
{
	for(int i = 0;i < s.length; i++)
	{  	
		String[] paras = Utility.splitString(s[i], ",");
		for(int n = 0;n < paras.length;n++)
		{
			strID = new Integer(paras[n].trim());		
			if(!strID.equals(""))
			{
				cust_vo.setBook_code(new Integer(1));
				cust_vo.setCust_id(strID);
				List cust_list = cust_local.listProcAll(cust_vo);
				if(cust_list != null && cust_list.size() > 0){
					cust_map = (Map)cust_list.get(0);
				}
			}	
				%>
	<br><br>
	<table style="<% out.print("page-break-after:always");%>" border="0" width="100%" cellspacing="0" cellpadding="4" >
		<tr>
			<td width="50%">
				<table border="0" width="100%" cellspacing="0" cellpadding="4">
					<tr>
						<td height="25px"><font size=1 ><!--寄件人姓名:-->&nbsp;<font size=2>&nbsp;</font></td>
						<td><font size=1><!--电话:-->&nbsp;<font size=2></td>
					</tr>
					<tr>
						<td colspan="2" style="padding-left:38px"><font size=1><!--单位名称:-->&nbsp;<font size=2><%=Utility.trimNull(user_map.get("USER_NAME"))%></font></td>

					</tr>
					<tr>
						<td colspan="2" height="70px"><font size=1><!--地址:-->&nbsp;<font size=2><%=Utility.trimNull(user_map.get("ADDRESS"))%></font></td>
					</tr>
					<tr>
						<td align="left" style="padding-left:130px">&nbsp;</td>
						<td	><font size=1><!--邮编:-->&nbsp;<font size=2><%=Utility.trimNull(user_map.get("POST_CODE"))%></font></td>
						
					</tr>
				</table>
			</td>
			<td width="50%">
				<table border="0" width="100%" cellspacing="0" cellpadding="4">
					<tr>
						<td height="25px"><font size=1><!--收件人姓名:--><font size=2>&nbsp;</font></td>
						<td><font size=1><!--电话:--><font size=2><%=Utility.trimNull(cust_map.get("MOBILE"))%></td>
					</tr>
					<tr>
						<td colspan="2"><font size=1><!--单位名称:--><font size=2><%=Utility.trimNull(cust_map.get("CUST_NAME"))%></font></td>

					</tr>
					<tr>
						<td colspan="2" height="70px"><font size=1><!--地址:-->&nbsp;<font size=2><%=Utility.trimNull(cust_map.get("POST_ADDRESS"))%></font></td>
						
					</tr>
					<tr>
						<td align="left" style="padding-left:180px">&nbsp;</td>
						<td><font size=1><!--邮编:-->&nbsp;<font size=2><%=Utility.trimNull(cust_map.get("POST_CODE"))%></font></td>
						
					</tr>
				</table>
			</td>
			<td>
				<table border="0" width="100%" cellspacing="0" cellpadding="4">
					<tr>
						<td>
							
						</td>	
					</tr>
				</table>	
			</td>
		</tr>
		</table>
		<br><br><br><br>
<%     }
    }
  }
%>

</form>
</body>
</html>
<%
cust_local.remove();
user_local.remove();
%>
