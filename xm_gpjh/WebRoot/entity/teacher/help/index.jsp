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
<TITLE>::�����������ƽ̨::>>���߰���</TITLE>
</HEAD>
<Body Bgcolor="#BFBFBF" Text="#000000" style="font-size:10pt;font-family:Arial">
<%
String helpfile=request.getParameter("helpfile");
String title=request.getParameter("title");
String mode=request.getParameter("mode");
String s_userid=(String) session.getValue("s_userid");
String Brief="�ް���";
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
else Brief="�ް���";

work.close(rstemp);
}
%>
<Div style="position:absolute;left:10;top:10;background-color:#808080;text-align:center;width:58;height:79%">
<IMG SRC="<%=com.whaty.parameter.g_root%>/work/images/totd.gif" WIDTH="23" HEIGHT="31" BORDER="0" style="position:relative;top:18"><BR>
<Span Id="cntr" style="position:relative;top:140px;font-family:Arial;font-size:10pt;font-weight:bold;color:#FFFFF0">
</Span>
</Div>
<Div style="position:absolute;left:68;top:10;background-color:#FFFFFF;width:346;height:79%;border:solid 1px #808080">
<BR><center><FONT FACE="����" SIZE="+1" color=red><B>�����������ƽ̨</b> ���߰���</FONT></center>
<HR size="1" style="position:relative;top:0">
<Span Id="mtxt" style="position:relative;top:-10px;padding:15px 10px 10px 10px;font-family:Arial;font-size:10pt;width:100%;overflow:auto;height:148px">
<%
if (mode==null) mode="show";

//<--Debug ��ʱ���,���Ź���ԱȨ�޷���������߰���
//mode="edit" ;
//s_userid="superadmin";
//Degug��ʱ���,���Ź���ԱȨ�޷���������߰���-->
if (mode.equals("edit")==false) //�Ǳ༭״̬,������ʾ
{
%>
<font color=blue><b><%=title%></b></font>
<br><br><%=Brief%>
</Span>
</Div>
<Div style="position:absolute;left:45;top:222;width:100%">
<Input type="button" Value="�ر�" style="width:70;position:relative;left:123;background-color:#BFBFBF;color:#000000;font-family:Arial;font-size:9pt;border:2px outset #FFFFFF" onclick="window.close()">
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
	if(Brief=="�ް���")
		{
%>

</Span>
</Div>
<Div style="position:absolute;left:45;top:222;width:100%">
<Input type="button" Value="��Ӱ�����Ϣ" style="width:90;position:relative;left:60;background-color:#BFBFBF;color:#000000;font-family:Arial;font-size:9pt;border:2px outset #FFFFFF" onclick="HelpEditor('add_help.jsp?helpfile=<%=helpfile%>&title=<%=title%>&mode=add&s_userid=<%=s_userid%>')"><Input type="button" Value="�ر�" style="width:70;position:relative;left:123;background-color:#BFBFBF;color:#000000;font-family:Arial;font-size:9pt;border:2px outset #FFFFFF" onclick="window.close()">
</Div>

<%
		}
	else {
%>
</Span>
</Div>
<Div style="position:absolute;left:45;top:222;width:100%">
<Input type="button" Value="�޸İ�����Ϣ" style="width:90;position:relative;left:60;background-color:#BFBFBF;color:#000000;font-family:Arial;font-size:9pt;border:2px outset #FFFFFF" onclick="HelpEditor('add_help.jsp?help_id=<%=Help_ID%>&mode=edit&s_userid=<%=s_userid%>')"><Input type="button" Value="�ر�" style="width:70;position:relative;left:123;background-color:#BFBFBF;color:#000000;font-family:Arial;font-size:9pt;border:2px outset #FFFFFF" onclick="window.close()">
</Div>

<%
     		}
}

%>
</BODY>
</HTML>
