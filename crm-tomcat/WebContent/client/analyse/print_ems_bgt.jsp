<%@ page language="java" pageEncoding="GBK" import="java.util.*,java.math.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
String cust_ids = request.getParameter("cust_id");	
String jjr_name =  Utility.trimNull(request.getParameter("jjr_name"),"客服部");
String nj_name =  request.getParameter("nj_name");	
String number =  request.getParameter("number");		
%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<style media="print"> 
.noPrint{
	display:none;
}
table,td{
	border-style:none;
}
</style> 
<LINK HREF="/includes/default.css" TYPE="text/css" REL="stylesheet">
<LINK href="/includes/print.css" type=text/css rel=stylesheet>
<base target="_self">
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<SCRIPT language="JavaScript">

function printStart(form)
{
	if(sl_confirm('您确认要打印'))
	{
		window.print();
	}
}
function showInfo()
{
	var v = showModalDialog('print_ems_bgt_info.jsp','','dialogWidth:720px;dialogHeight:400px;status:0;help:0;');

	if (v != null){
		var stringArray = v.split("$");
		var jjr_name = stringArray[0];
		var nj_name = stringArray[1];
		var number = stringArray[2];
		location = "print_ems_bgt.jsp?cust_id=<%=cust_ids%>&jjr_name="+jjr_name+"&nj_name="+nj_name+"&number="+number;
	}
}

</SCRIPT>

</head>


<body topmargin="20" leftmargin="0" rightmargin="8" onkeydown="javascript:chachEsc(window.event.keyCode)" class="BODY">
<form name="theform"  method="post">
	
<table  border="0" >
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
Integer print_post_info = new Integer(0);
Map cust_map = new HashMap();
//String[] s = request.getParameterValues("cust_id");	

String[] paras = Utility.splitString(cust_ids, ",");
for(int n = 0;n < paras.length;n++)
{
	strID = new Integer(paras[n].trim());	
	String tel = "";	
	if(!strID.equals(""))
	{
		cust_vo.setBook_code(new Integer(1));
		cust_vo.setCust_id(strID);
		List cust_list = cust_local.listProcAll(cust_vo);
		if(cust_list != null && cust_list.size() > 0){
			cust_map = (Map)cust_list.get(0);
		}
		if(Utility.trimNull(cust_map.get("MOBILE")).equals(""))
			if(Utility.trimNull(cust_map.get("CUST_TEL")).equals(""))
				tel = Utility.trimNull(cust_map.get("O_TEL"));
			else
				tel = Utility.trimNull(cust_map.get("CUST_TEL"));
		else
			tel = Utility.trimNull(cust_map.get("MOBILE"));
		print_post_info = Utility.parseInt(Utility.trimNull(cust_map.get("PRINT_POST_INFO")),new Integer(0));//从客户信息中获取时候时候打印ems,披露信息所用
	}
	if(print_post_info.intValue() ==1)	{

%>
<tr>
	<td <%if(n!=0){%>style="<% out.print("page-break-after:always");%><%}%>"></td>
	<td>&nbsp;</td>
</tr>
<%if(n!=0){%>
<tr><td colspan="2">&nbsp;</td></tr>
<%}%>
<tr>
	<td align="center" style="width:1mm;" >&nbsp;</td>
	<td align="center" >
		<table cellspacing="0" cellpadding="4" align="center" border="0" >
			<tr>
				<td style="height:20mm;"align="center">&nbsp;</td>
			</tr>
			<tr>
				<td align="center">
					<table border="1" width="100%" cellspacing="0" cellpadding="4" align="center" style="width:180mm;" >
						<tr>
							<td style="height:10mm;width:60mm;padding-left:10mm;"><font size=1 ><!--寄件人姓名:-->&nbsp;<font size=3><%=jjr_name%></font></td>
							<td style="width:30mm;padding-left:1mm;"><!--电话:--><font size=3><%=Utility.trimNull(user_map.get("TELEPHONE"))%></font></td>
							<td style="width:50mm;padding-left:15mm;">&nbsp;</td>
							<td style="width:40mm;padding-left:5mm;">&nbsp;</td>	
						</tr>
						<tr>
							<td colspan="2" style="padding-left:20mm;"><!--单位名称:-->&nbsp;<font size=3><%=Utility.trimNull(user_map.get("USER_NAME"))%></font></td>
							<td colspan="2" style="padding-left:10mm;">&nbsp;</td>
						</tr>
						<tr>
							<td colspan="2" style="height:17mm;padding-left:5mm;"><!--地址:-->&nbsp;<font size=3><%=Utility.trimNull(user_map.get("ADDRESS"))%></font></td>
							<td colspan="2" style="height:17mm;padding-left:10mm;">&nbsp;</td>				
						</tr>
						<tr>
							<td colspan="2" style="padding-left:80mm;"><!--邮编:-->&nbsp;<font size=2 style="letter-spacing:8px;"><%=Utility.trimNull(user_map.get("POST_CODE"))%></font></td>
							<td colspan="2" style="padding-left:66mm;">&nbsp;</td>
						</tr>

						<tr>
							<td style="height:20mm;width:60mm;padding-left:10mm;"><!--收件人姓名:--><font size=3><%=Utility.trimNull(cust_map.get("CUST_NAME"))%></td>
							<td style="width:30mm;padding-left:1mm;"><!--电话:--><font size=3><%=Utility.trimNull(tel)%></font></td>
							<td style="width:50mm;padding-left:15mm;">&nbsp;</font></td>
							<td style="width:40mm;padding-left:5mm;">&nbsp;</td>	
						</tr>
						<tr>
							<td colspan="2" style="padding-left:20mm;"><!--单位名称:-->&nbsp;<font size=3></font></td>
							<td colspan="2" style="padding-left:10mm;">&nbsp;</td>
						</tr>
						<tr>
							<td colspan="2" style="height:17mm;padding-left:5mm;"><font size=1><!--地址:-->&nbsp;<font size=3><%=Utility.trimNull(cust_map.get("POST_ADDRESS"))%></font></td>
							<td colspan="2" style="height:17mm;padding-left:10mm;";>&nbsp;</td>				
						</tr>
						<tr>
							<td colspan="2" style="padding-left:80mm;"><!--邮编:-->&nbsp;<font size=2 style="letter-spacing:8px;"><%=Utility.trimNull(cust_map.get("POST_CODE"))%></font></td>
							<td colspan="2">&nbsp;</td>
						</tr>
						<tr>
							<td style="padding-left:10mm;">&nbsp;<%=Utility.trimNull(nj_name) %></td>
							<td style="padding-left:10mm;">&nbsp;<%=Utility.trimNull(number) %></td>
							<td colspan="4">&nbsp;</td>
						</tr>
					</table>
					<br><br><br>
				</td>
			</tr>
		</table>
	</td>
</tr>		
	

<%     
		
    }
 }
%>
<tr>
	<td colspan="2">		
		<table border="0" width="100%" id="btnprint" height="7%" align="center" class="noprint">
			<tr>
				<td width="100%" align="center">
				<OBJECT  id=WebBrowser  classid=CLSID:8856F961-340A-11D0-A96B-00C04FD705A2  height=0  width=0 VIEWASTEXT></OBJECT>
				
				<button type="button"  class="xpbutton3" accessKey=p name="btnCancel" title="打印" onclick="javascript:printStart(document.theform);">打印(<u>P</u>)</button>
				&nbsp;&nbsp;&nbsp;
				<button type="button"  class="xpbutton4" accessKey=a id="btnPrintset" name="btnPrintset" onclick="javascript:document.all.WebBrowser.ExecWB(8,1);">页面设置(<u>A</u>)</button>
				&nbsp;&nbsp;&nbsp;
				<button type="button"  class="xpbutton4" accessKey=c id="btnPrintset" name="btnPrintset" onclick="javascript:document.all.WebBrowser.ExecWB(6,1);">打印机设置(<u>C</u>)</button>
				&nbsp;&nbsp;&nbsp; 
				<button type="button"  class="xpbutton4" onclick="javascript:showInfo();">其他信息设置</button>
				&nbsp;&nbsp;&nbsp; 
				<button type="button"  class="xpbutton3" accessKey=b name="btnReturn" onclick="javascript:window.close();">返回(<u>B</u>)</button>
				&nbsp;&nbsp;&nbsp;</td>
			</tr>
		</table>
	</td>
</tr>	
</table>
</form>
</body>
</html>
<%
cust_local.remove();
user_local.remove();
%>
