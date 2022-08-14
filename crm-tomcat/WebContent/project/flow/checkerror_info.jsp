<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.vo.* ,enfo.crm.project.*,enfo.crm.system.*,enfo.crm.dao.*,java.util.*"%>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
	//查询参数传递
	String object_id = Utility.trimNull(request.getParameter("object_id"));
	String object_type = Utility.trimNull(request.getParameter("object_type"));
	String check_flag = Utility.trimNull(request.getParameter("check_flag"));
	String returnValue="";
	boolean  success=false;
	//显示页面信息
	returnValue=CRMBusinessCheck.checkBusinessInfo(object_id,object_type,check_flag);
	if(returnValue==null || returnValue.equals("")){//不存在错误信息
		success=true;
	}
	String[] megInfo=returnValue.split("@@");
%>
<SCRIPT LANGUAGE="javascript">
	<%if(success) {%>
	   window.returnValue = 1;
	   window.close();
	<%}%>
</SCRIPT>
<HTML>
<HEAD>
<TITLE>业务完整性校验信息</TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="/includes/default.css" type=text/css rel=stylesheet>
<BASE TARGET="_self">
</HEAD>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/calendar2.js"></SCRIPT>

<BODY class="BODY">

<form name="theform" method="post" action="">
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0 style="margin:0 0 0 0;">
	<TR>
		<TD vAlign=top align=left>
			<TABLE cellSpacing=0 cellPadding=3 width="100%" align=center border=0>
				<tr>
					<td >
						<table width="100%" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor">
						<tr>
						<!-- <td width="20%"> &nbsp;&nbsp;&nbsp;客户名称：&nbsp;<%="111"%></td> -->
						</tr>
						<tr class="trh">
								<td width="20%">校验编号</td>
								<td width="80%">校验信息</td>
							</tr>
						</table>
						<div style="height:250px; width:100%; overflow-y:auto; border:1px solid black;">
						<!-- 展现开始-->
						<table width="100%" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor">
							<% 										
							Map map1 = null;
							for(int k=0;k<megInfo.length;k++)
							{	
							%>
							<tr style="display:"  class="tr<%=k%2%>">
								<td align="center" height="5" width="20%">&nbsp;<%=k+1 %></td>
								<td align="left" height="25" width="80%">&nbsp;<font color="red"><%=megInfo[k]%></font></td>
							</tr>
							<%} %>
						</table>
						<!--  展现结束-->
						</div>
					</TD>
				</tr>
				<tr valign="top">
					<td align="right">
						<div style="border:0px;">
							<div style="float:left"></div>
							<div style="float:right">
								<button class="xpbutton3" accessKey=b id="btnCancel" name="btnCancel" onclick="javascript:window.close();">关闭(<u>B</u>)</button>
							<div>
						</div>
					</td>
				</tr>
			</TABLE>
		</TD>
	</tr>
</TABLE>
</form>
</BODY>
</HTML>