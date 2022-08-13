<%@ page contentType="text/html;charset=GBK" %>
<%@page import="java.sql.*,java.io.*,java.net.*" %>
<jsp:useBean id="work" scope="application" class="com.whaty.platform.database.oracle.dbpool"/>
<HTML>
<HEAD>
<script language=javascript>
function HelpEditor(url){
  	window.open(url,"edit_help","height=350 width=380");
  	window.close();
}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<TITLE>::网梯网络教育平台::>>在线帮助</TITLE>
</HEAD>
<Body Bgcolor="#BFBFBF" Text="#000000" style="font-size:10pt;font-family:Arial">
<%
String helpfile=request.getParameter("helpfile");
String title=request.getParameter("title");
String mode=request.getParameter("mode");
String s_userid=(String) session.getValue("s_userid");
String Brief="无帮助";
String Help_ID="1";
String TSql;

URL url = new URL(helpfile); 
//helpfile=url.getFile();
helpfile=url.getPath();
TSql="select * from TONLINEHELP where title like '%"+title+"%' and SCRIPTNAME like '%"+helpfile+"%'";
//out.print(TSql);
com.dbConnection.MyResultSet rstemp=work.executeQuery(TSql);
if (rstemp!=null)
{
if (rstemp.next())
{
Brief=""+rstemp.getString("BRIEF");
Help_ID=rstemp.getString("HELP_ID");
}
else Brief="无帮助";

work.close(rstemp);
}
%>
<Div style="position:absolute;left:10;top:10;background-color:#808080;text-align:center;width:58;height:79%">
<IMG SRC="<%=com.whaty.parameter.g_root%>/work/images/totd.gif" WIDTH="23" HEIGHT="31" BORDER="0" style="position:relative;top:18"><BR>
<Span Id="cntr" style="position:relative;top:140px;font-family:Arial;font-size:10pt;font-weight:bold;color:#FFFFF0">
</Span>
</Div>
<Div style="position:absolute;left:68;top:10;background-color:#FFFFFF;width:346;height:79%;border:solid 1px #808080">
<BR><center><FONT FACE="宋体" SIZE="+1" color=red><B>网梯网络教育平台</b> 在线帮助</FONT></center>
<HR size="1" style="position:relative;top:0">
<Span Id="mtxt" style="position:relative;top:-10px;padding:15px 10px 10px 10px;font-family:Arial;font-size:10pt;width:100%;overflow:auto;height:148px">
<%
if (mode==null) mode="show";

//<--Debug 临时添加,开放管理员权限方便添加在线帮助
//mode="edit" ;
//s_userid="superadmin";
//Degug临时添加,开放管理员权限方便添加在线帮助-->
if (mode.equals("edit")==false) //非编辑状态,进入显示
{
%>
<font color=blue><b><%=title%></b></font>
<br><br><%=Brief%>
</Span>
</Div>
<Div style="position:absolute;left:45;top:222;width:100%">
<Input type="button" Value="关闭" style="width:70;position:relative;left:123;background-color:#BFBFBF;color:#000000;font-family:Arial;font-size:9pt;border:2px outset #FFFFFF" onclick="window.close()">
</Div>
<%
}
else 
{

%>
<font color=blue><b><%=title%></b></font>(<font color=grey><%=helpfile%></font>):
<p>
</p>
<%=Brief%>
<%
	if(Brief=="无帮助")
		{
%>

</Span>
</Div>
<Div style="position:absolute;left:45;top:222;width:100%">
<Input type="button" Value="添加帮助信息" style="width:90;position:relative;left:60;background-color:#BFBFBF;color:#000000;font-family:Arial;font-size:9pt;border:2px outset #FFFFFF" onclick="HelpEditor('add_help.jsp?helpfile=<%=helpfile%>&title=<%=title%>&mode=add&s_userid=<%=s_userid%>')"><Input type="button" Value="关闭" style="width:70;position:relative;left:123;background-color:#BFBFBF;color:#000000;font-family:Arial;font-size:9pt;border:2px outset #FFFFFF" onclick="window.close()">
</Div>

<%
		}
	else {
%>
</Span>
</Div>
<Div style="position:absolute;left:45;top:222;width:100%">
<Input type="button" Value="修改帮助信息" style="width:90;position:relative;left:60;background-color:#BFBFBF;color:#000000;font-family:Arial;font-size:9pt;border:2px outset #FFFFFF" onclick="HelpEditor('add_help.jsp?help_id=<%=Help_ID%>&mode=edit&s_userid=<%=s_userid%>')"><Input type="button" Value="关闭" style="width:70;position:relative;left:123;background-color:#BFBFBF;color:#000000;font-family:Arial;font-size:9pt;border:2px outset #FFFFFF" onclick="window.close()">
</Div>

<%
     		}
}

%>
</BODY>
</HTML>
