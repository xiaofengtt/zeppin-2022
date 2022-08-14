<%@ page contentType="text/html; charset=GBK"  import="java.math.*,java.util.*,enfo.crm.system.*,enfo.crm.dao.*,enfo.crm.customer.*,enfo.crm.tools.*, enfo.crm.vo.*, enfo.crm.intrust.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
Integer product_id = Utility.parseInt(request.getParameter("product_id"),null);
%>
<HTML>
<HEAD>
<TITLE></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<base target="_self">

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<script language=javascript>
function saveAction(){
	var v={};
	if(!sl_checkChoice(document.theform.level_id,"<%=LocalUtilis.language("class.levelID",clientLocale)%> ")) return false;//类别编号
	if(!sl_check(document.theform.level_value_name, "<%=LocalUtilis.language("class.levelValueName",clientLocale)%> ", 30, 1))		return false;//分类值名称
	if(!sl_checkDecimal(document.theform.min_value,"<%=LocalUtilis.language("class.minLevel",clientLocale)%> ",16,3,1)) return false;//分类最小值
	if(!sl_checkDecimal(document.theform.max_value,"<%=LocalUtilis.language("class.maxValue",clientLocale)%> ",16,3,1)) return false;//分类最大值
	//document.theform.submit();
	v[0] = document.theform.level_id.value;
	v[1] = document.theform.level_value_name.value;
	v[2] = document.theform.min_value.value;
	v[3] = document.theform.max_value.value;
	window.returnValue = v;
	window.close();
}
</script>

</HEAD>

<BODY class="BODY">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" action="contract_level_add_action.jsp">
<input type="hidden" name="product_id" value="<%=product_id%>">

<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TR>
		<TD vAlign=top align=left>
			<table width="99%" cellspacing="0" cellpadding="2" border="0">
				<tr>
					<td colspan="4">
						<img border="0" src="<%=request.getContextPath()%>/images/member.gif" align="absbottom" width="32" height="28"><font color="#215dc6"><b><%=LocalUtilis.language("menu.levelAppend",clientLocale)%> </b></font></td>
				</tr><!--新增分类-->
						<tr>
					<td colspan="4">
					    <hr noshade color="#808080" size="1">
					</td>
				</tr>
				<tr>
					<td align="right"><%=LocalUtilis.language("class.levelID",clientLocale)%> :</td><!--类别编号-->
					<td>
						<select name="level_id" style="width:125px">
							<%=Argument.getLevelID(new Integer(0))%>
						</select>
					</td>
					<td align="right"><%=LocalUtilis.language("class.levelValueName",clientLocale)%> :</td><!--分类值名称-->
					<td>
						<input name="level_value_name" type="text" value="">
					</td>
				</tr>
				<tr>
					<td align="right"><%=LocalUtilis.language("class.minLevel",clientLocale)%> :</td><!--分类最小值-->
					<td>
						<input name="min_value" type="text" value="">
					</td>
					<td align="right"><%=LocalUtilis.language("class.maxValue",clientLocale)%> :</td><!--分类最大值-->
					<td>
						<input name="max_value" type="text" value="">
					</td>
				</tr>
				<tr>
					<td colspan=4 align="right">
						<br/>
						<button type="button"  class="xpbutton3" name="btnNew" accessKey=o onClick="javascript:saveAction();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>O</u>)</button>&nbsp;&nbsp;&nbsp;
						<!--保存-->
						<button type="button"  class="xpbutton3" name="btnCancel" accessKey=c onClick="javascript:window.close();"><%=LocalUtilis.language("message.close",clientLocale)%> (<u>C</u>)</button>
						<!--关闭-->
					</td>
				</tr>
			</table>
		</TD>
	</TR>
</TABLE>
</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>
<%
%>
