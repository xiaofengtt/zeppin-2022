<%@ page contentType="text/html; charset=GBK" import="enfo.crm.system.*,enfo.crm.dao.*,enfo.crm.tools.*,enfo.crm.vo.*,java.util.*" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%

DictparamLocal dictparam = EJBFactory.getDictparam();
DictparamVO vo = new DictparamVO();


IPageList printTemplateSetPageList = dictparam.getPrintCatalog(vo,-1,-1);
List printTemplateSetList = printTemplateSetPageList.getRsList();
%>
<HTML>
<HEAD>
<TITLE></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="/includes/default.css" type=text/css rel=stylesheet>
<LINK href="/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">

<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<script language=javascript>
function showInfo(catalog_code,catalog_name){
    location = "printtemplateset_list.jsp?catalog_code="+catalog_code+"&catalog_name="+catalog_name;    
}
</script>
<BODY class="BODY" >
<form name="theform" method="POST" action="">
<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%" >
	<tr class="trh">
		<td>分类代码</td>
		<td>分类名称</td>
		<td>备注</td>
		<td>模板设置</td>
		<!--<td>删除</td>-->
	</tr>        					
	<%
	Map map = null;
	for(int i=0;printTemplateSetList!=null&&i<printTemplateSetList.size();i++){
	    map = (Map)printTemplateSetList.get(i);
	%>
	<tr class="tr<%=i%2%>">
	    <td align="center"><%=Utility.trimNull(map.get("CATALOG_CODE"))%></td>
		<td align="center"><%=Utility.trimNull(map.get("CATALOG_NAME"))%></td>
		<td><%=Utility.trimNull(map.get("SUMMARY"))%></td>
		<td align="center">
		    <button type="button"  class="xpbutton2" onclick="javascript:showInfo('<%=map.get("CATALOG_CODE")%>','<%=map.get("CATALOG_NAME")%>')">>></button>
		</td>
	</tr>
	<%}%>
</table>
<br>
<table>
</form>
<%@ include file="/includes/foot.inc"%>	
</BODY>
</HTML>
<%
dictparam.remove();
%>