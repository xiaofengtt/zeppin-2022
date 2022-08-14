<%@ page contentType="text/html; charset=GBK" import="enfo.crm.system.*,enfo.crm.dao.*,enfo.crm.tools.*,enfo.crm.vo.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
Integer type_id = Utility.parseInt(request.getParameter("type_id"),new Integer(5600));
String type_name = "外部网站链接";
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"),new Integer(0));
String type_content = Utility.trimNull(request.getParameter("type_content"));
String additive_value = Utility.trimNull(request.getParameter("additive_value"));

DictparamLocal dictparam = EJBFactory.getDictparam();
DictparamVO vo = new DictparamVO();

vo.setType_id(type_id);
vo.setType_name(type_name);

List list = dictparam.listDictparamAll(vo);

%>

<HTML>
<HEAD>
<TITLE></TITLE>
<!--数据字典-->
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<link href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<link href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
</HEAD>
<BODY class="BODY body-nox">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="get" action="weblinks_remove.jsp">
<input type="hidden" name="del_flag" value="0">
<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TR>
	<%//@ include file="menu.inc" %>
	<TD vAlign=top align=left>
	<TABLE cellSpacing=0 cellPadding=4 width="100%" align=center border=0>

		<TR>
			<TD>
			<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%">
				<tr class="trtagsort">
					<td width="*"><%=LocalUtilis.language("class.linkName",clientLocale)%> </td><!--链接名称-->
				</tr>
				<!--从结果集中显示记录-->
<%
int iCount = 0;
int iCurrent = 0;
Map rowMap = null;
String type_value = "";

for(int i=0;i<list.size();i++)
{
	rowMap = (Map)list.get(i);
	serial_no = Utility.parseInt(Utility.trimNull(rowMap.get("SERIAL_NO")), new Integer(0));
	type_id = Utility.parseInt(Utility.trimNull(rowMap.get("TYPE_ID")), new Integer(0));
	type_name = Utility.trimNull(rowMap.get("TYPE_NAME"));
	type_value = Utility.trimNull(rowMap.get("TYPE_VALUE"));
	type_content = Utility.trimNull(rowMap.get("TYPE_CONTENT"));
	additive_value =  Utility.trimNull(rowMap.get("ADDITIVE_VALUE"));
%>
				<tr class="tr<%=(iCurrent % 2)%>">
					<td align="center" ><a href="<%=additive_value%>" target="_blank"><%=type_content%></a></td>
				</tr>
				<!--补全表格-->
<%
iCurrent++;
iCount++;
}

%>
				<tr class="trbottom">	
					<td class="tdh" align="left" colspan="5" ><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%> <!--合计-->&nbsp;<%=iCount%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> <!--项--></b></td>
				</tr>
			</table>

			</TD>
		</TR>
	</TABLE>
	</TD>
	</TR>
</TABLE>
</form>
<%@ include file="/includes/foot.inc"%>	
</BODY>
</HTML>
<%dictparam.remove();%>
