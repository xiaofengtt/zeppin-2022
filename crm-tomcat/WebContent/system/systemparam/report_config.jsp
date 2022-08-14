<%@ page contentType="text/html; charset=GBK" import="enfo.crm.system.*,enfo.crm.dao.*,enfo.crm.tools.*,enfo.crm.vo.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>

<%
Integer type_id = Utility.parseInt(request.getParameter("type_id"),new Integer(0));
String type_value = Utility.trimNull(request.getParameter("type_value"));

DictparamLocal dictparam = EJBFactory.getDictparam();
DictparamVO vo = new DictparamVO();

vo.setType_id(type_id);
vo.setType_value(type_value);

String[] totalColumn = new String[0];
IPageList pageList = dictparam.listReportAll(vo,totalColumn,Utility.parseInt(sPage,1),Utility.parseInt(sPagesize,8));
sUrl = sUrl + "&type_id=" + type_id + "&type_value=" + type_value; 
%>

<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("menu.reportConfig",clientLocale)%> </TITLE>
<!--报表参数-->
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<script language=javascript>
function confirmRemove2(type_id,type_value)
{
	if(!sl_confirm("<%=LocalUtilis.language("message.recordDel",clientLocale)%> ")) return false;
	location = 'report_config_remove.jsp?flag=del&type_id='+type_id+'&type_value='+ type_value;
}
function newInfo()
{
	if(showModalDialog('report_config_new.jsp?id=1', '', 'dialogWidth:320px;dialogHeight:260px;status:0;help:0') != null)
	{
		sl_update_ok();
		location.reload();
	}
}

function refreshPage()
{
	disableAllBtn(true);
	waitting.style.visibility='visible';
    location = 'report_config.jsp?page=<%=sPage%>&pagesize=' + document.theform.pagesize.value +'&type_id=' + document.theform.type_id.value +'&type_value=' + document.theform.type_value.value;
}

function StartQuery()
{
	refreshPage();
}
window.onload = function(){
initQueryCondition()};
</script>
</HEAD>
<BODY class="BODY">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="get" action="report_config_remove.jsp">
<!--查询浮动框-->
<div id="queryCondition" class="qcMain" style="display:none;width:300px;">
	<table  id="qcTitle" class="qcTitle" align="center" width="99%" cellspacing="0" cellpadding="2">
	  <tr>
	   <td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ：</td><!--查询条件-->
	   <td align="right"><button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose"
	       onclick="javascript:cancelQuery();"></button></td>
	  </tr>
	</table>
	<table border="0" width="100%" cellspacing="2" cellpadding="2">
		<tr>						
	         <td align="right"><%=LocalUtilis.language("class.typeID",clientLocale)%> :</td><!--参数类型-->
			 <td align="left"><input type="text" name="type_id" onkeydown="javascript:nextKeyPress(this)" size="20"
	         	<%if(type_id.intValue()!=0){%> value="<%=type_id%>"<%}else{%> value=""<%}%>></td>      
		</tr>
		<tr>						
	         <td align="right"><%=LocalUtilis.language("message.paraValue",clientLocale)%> :</td><!--参数值-->
			 <td align="left"><input type="text" name="type_value" onkeydown="javascript:nextKeyPress(this)" size="20" value="<%=type_value%>"></td>      
		</tr>
		
		<tr>
			<td align="center" colspan="2">
				<button type="button"  class="xpbutton3" accessKey=s name="btnQuery" onclick="javascript:StartQuery();"><%=LocalUtilis.language("message.confirm",clientLocale)%> (<u>S</u>)
				</button>&nbsp;&nbsp;&nbsp;&nbsp;<!--确定-->								
			</td>
		</tr>						
	</table>
</div>

<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TR>	
	<TD vAlign=top align=left>
	<TABLE cellSpacing=0 cellPadding=4 width="100%" align=center border=0>
		<TR>
		<TD>
			<table border="0" width="100%" cellspacing="0" cellpadding="0">
				<tr>
					<td colspan="2"><IMG src="<%=request.getContextPath()%>/images/member.gif" align=absBottom border=0 width="32" height="28"><b><%=menu_info%></b></td>
				</tr>
				<tr>
					<td valign="bottom" align="right">		
						<button type="button"  class="xpbutton3" accessKey=f id="queryButton" name="queryButton"
						<%if(!input_operator.hasFunc(menu_id, 108)){%> style="display:none"<%}%>><%=LocalUtilis.language("message.query",clientLocale)%> <!--查询-->(<u>F</u>)</button>&nbsp;&nbsp;&nbsp;&nbsp;
						<%if(input_operator.hasFunc(menu_id,100)){%>
						<button type="button"  class="xpbutton3" accessKey=n id="btnNew" name="btnNew" title="<%=LocalUtilis.language("message.newRecord",clientLocale)%> " onclick="javascript:newInfo();">
						    <%=LocalUtilis.language("message.new",clientLocale)%> (<u>N</u>)
						</button><!--新建记录--><!--新建-->
						&nbsp;&nbsp;&nbsp; 
						<%}if(input_operator.hasFunc(menu_id,101)){%>
						<button type="button"  class="xpbutton3" accessKey=d id="btnDelete" name="btnDelete" title="<%=LocalUtilis.language("message.delRecordsSelect",clientLocale)%> " 
						    onclick="javascript:if(confirmRemove(document.theform.serial_no)) document.theform.submit();"><%=LocalUtilis.language("message.delete",clientLocale)%> (<u>D</u>)
						</button><!--删除所选记录--><!--删除-->
						&nbsp;&nbsp;&nbsp;
						<%}%>					
					</td>
				</tr>
				<tr>
					<td colspan="2"><hr noshade color="#808080" size="1"></td>
				</tr>
			</table>
			<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%">
				<tr class="trtagsort">
					<td width="15%"><input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAll(document.theform.serial_no);">
					    <%=LocalUtilis.language("class.typeID",clientLocale)%>  &nbsp;&nbsp;
					</td><!--参数类型-->
					<td width="19%"><%=LocalUtilis.language("message.paraValue",clientLocale)%> </td><!--参数值-->
					<td width="60%"><%=LocalUtilis.language("class.typeContent",clientLocale)%> </td><!--参数说明-->
					<td width="6%"><%=LocalUtilis.language("message.delete",clientLocale)%> </td><!--删除-->
				</tr>
				<!--从结果集中显示记录-->
<%
int iCount = 0;
int iCurrent = 0;

List rsList = pageList.getRsList();
Map rowMap = null;

for(int i=0;i<rsList.size();i++){
	rowMap = (Map)rsList.get(i);
	String type_id2 = Utility.trimNull(rowMap.get("TYPE_ID"));
	String type_value2 = Utility.trimNull(rowMap.get("TYPE_VALUE"));
	String type_content = Utility.trimNull(rowMap.get("TYPE_CONTENT"));
%>
				<tr class="tr<%=(iCurrent % 2)%>">
					<td class="tdh">
					<table border="0" width="100%" cellspacing="0" cellpadding="0">
						<tr>
							<td width="10%"><input type="checkbox" name="serial_no" value="<%=type_id2%>&<%=type_value2%>" class="flatcheckbox"></td>
							<td width="90%"  align="left"><%=type_id2%></td>
						</tr>
					</table>
					</td>
					<td align="left" ><%=type_value2%></td>
					<td align="left" ><%=type_content%></td>
					<%if (input_operator.hasFunc(menu_id, 101)){%>
					<td  align="center"> 
					<a href="javascript:#" onclick="javascript:confirmRemove2('<%=type_id2%>','<%=type_value2%>');return false; ">
						<img src="<%=request.getContextPath()%>/images/recycle.gif" width="16" height="16" title="<%=LocalUtilis.language("message.delete",clientLocale)%> " /><!--删除-->
					</a>
					<%}%>
					</td>
				</tr>
				<!--补全表格-->
<%
iCurrent++;
iCount++;
}

for (int i=0;i < pageList.getBlankSize(); i++){
%>

				<tr class="tr<%=(iCurrent % 2)%>">
					<td class="tdh"></td>
					<td align="center" ></td>
					<td align="center" ></td>
					<td align="center" ></td>
				</tr>
<%}%>
				<tr class="trbottom">	
					<td class="tdh" ><b><%=LocalUtilis.language("message.total",clientLocale)%><!--合计--> <%=iCount%> <%=LocalUtilis.language("message.entries",clientLocale)%>  <!--项--></b></td>
					<td align="center" >-</td>
					<td align="center" >-</td>
					<td align="center" >-</td>
				</tr>
			</table>

			<br>
			<table border="0" width="100%">
				<tr valign="top">
					<td><%=pageList.getPageLink(sUrl)%></td>
					<td align="right">
						<input type="hidden" class="xpbutton3" accessKey=r id="btnRefresh" name="btnRefresh" 
						    title="<%=LocalUtilis.language("message.currentPageRefrash",clientLocale)%> " onclick="javascript:refreshPage();"/><!--刷新当前页面--><!--刷新(<u>R</u>)</button>
						&nbsp;&nbsp;&nbsp;-->
						<input type="hidden"class="xpbutton3" accessKey=b id="btnReturn" name="btnReturn" 
						    title="<%=LocalUtilis.language("message.backLastPage",clientLocale)%> " onclick="javascript:history.back();"/><!--返回上一页--><!--返回(<u>B</u>)</button>
						&nbsp;&nbsp;&nbsp;-->
					</td>
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
