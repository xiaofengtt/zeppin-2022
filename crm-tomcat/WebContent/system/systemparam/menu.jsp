<%@ page contentType="text/html; charset=GBK" import="enfo.crm.system.*,enfo.crm.dao.*,enfo.crm.tools.*,enfo.crm.vo.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>

<%
String menu_id3 = Utility.trimNull(request.getParameter("menu_id"));
String parent_id = Utility.trimNull(request.getParameter("parent_id"));

MenuInfoVO vo = new MenuInfoVO();
vo.setMenu_id(menu_id3);
vo.setParent_id(parent_id);

String[] totalColumn = new String[0];
MenuInfoLocal menu = EJBFactory.getMenuInfo();
IPageList pageList = menu.listByMul(vo,totalColumn,Utility.parseInt(sPage,1),Utility.parseInt(sPagesize,8));
sUrl = sUrl + "&menu_id=" + menu_id3 + "&parent_id=" + parent_id;
%>
<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("menu.menu",clientLocale)%> </TITLE>
<!--菜单管理-->
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK HREF="<%=request.getContextPath()%>/includes/default.css" TYPE="text/css" REL="stylesheet">
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/financing.js"></SCRIPT>
<script language=javascript>
function showInfo(menu_id)
{
	if(showModalDialog('menu_update.jsp?menu_id=' + menu_id, '', 'dialogWidth:420px;dialogHeight:280px;status:0;help:0') != null)
	{
		sl_update_ok();
		location.reload();
	}
}

function refreshPage()
{
	disableAllBtn(true);
	waitting.style.visibility='visible';
	location = 'menu.jsp?page=<%=sPage%>&pagesize=' + document.theform.pagesize.value +'&menu_id=' + document.theform.menu_id.value +'&parent_id=' + document.theform.parent_id.value;
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
<form name="theform">
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
		<td align="right"><%=LocalUtilis.language("class.menuID",clientLocale)%> :</td><!--菜单编号-->
		<td align="left"><input type="text" name="menu_id" onkeydown="javascript:nextKeyPress(this)" size="20" value=<%=menu_id3%>></td>						
	</tr>
	<tr>	
         <td align="right"><%=LocalUtilis.language("class.parentID2",clientLocale)%> :</td><!--父菜单编号-->
		 <td align="left"><input type="text" name="parent_id" onkeydown="javascript:nextKeyPress(this)" size="20" value=<%=parent_id%>></td>      
	</tr>
	<tr>
		<td align="center" colspan="2"><button type="button"  class="xpbutton3" accessKey=s name="btnQuery"
									onclick="javascript:StartQuery();"><%=LocalUtilis.language("message.confirm",clientLocale)%> (<u>S</u>)</button>&nbsp;&nbsp;&nbsp;&nbsp;								
			</td><!--确定-->
		</tr>						
	</table>
</div>

<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TR>
	<%//@ include file="menu.inc" %>
	<TD vAlign=top align=left>
	<TABLE cellSpacing=0 cellPadding=4 width="100%" align=center border=0>
		<TR>
			<TD>
			<table border="0" width="100%" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<img border="0" src="<%=request.getContextPath()%>/images/member.gif" align="absmiddle" width="32" height="28">
						<font color="#215dc6"><b><%=menu_info%></b></font>
					</td>
				</tr>
				<tr>	
					<td align="right">
					<button type="button"  class="xpbutton3" accessKey=f id="queryButton" name="queryButton"<%if(!input_operator.hasFunc(menu_id, 108)){%> style="display:none"<%}%>>
					      <%=LocalUtilis.language("message.query",clientLocale)%> <!--查询-->(<u>F</u>)
					</button>
					</td>
				</tr>
				<tr>
					<td>
					<hr noshade color="#808080" size="1">
					</td>
				</tr>
			</table>
			<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%">
				<tr class="trtagsort">
					<td width="20%"><%=LocalUtilis.language("class.menuID",clientLocale)%> </td><!--菜单编号-->
					<td width="20%" ><%=LocalUtilis.language("class.menuName",clientLocale)%> </td><!--菜单名称-->
					<td width="22%"><%=LocalUtilis.language("class.menuInfo2",clientLocale)%> </td><!--菜单内容-->
					<td width="32%">URL</td>
					<td width="6%"><%=LocalUtilis.language("message.edit",clientLocale)%> </td><!--编辑-->
				</tr>
<%
int iCount = 0;
int iCurrent = 0;

List rsList = pageList.getRsList();
Map rowMap = null;
for(int i=0;i<rsList.size();i++)
{
	rowMap = (Map)rsList.get(i);
	String menu_id2 = Utility.trimNull(rowMap.get("MENU_ID"));
	String menu_name = Utility.trimNull(rowMap.get("MENU_NAME"));
	String menu_info2 = Utility.trimNull(rowMap.get("MENU_INFO"));
	String url = Utility.trimNull(rowMap.get("URL"));
%>
					<tr class="tr<%=(iCurrent % 2)%>">
						<td align="left"><%=menu_id2%></td>
						<td align="left" ><%=menu_name%></td>
						<td align="left" ><%=menu_info2%></td>
						<td align="left" ><%=url%></td>
						<td align="center">
						<%if (input_operator.hasFunc(menu_id, 102)){%>
						<a href="javascript:#" onclick="javascript:showInfo(<%=menu_id2%>);return false;">
							<img src="<%=request.getContextPath()%>/styles/images/edit2.gif" width="16" height="16" title="<%=LocalUtilis.language("message.edit",clientLocale)%> "/>
							<!--编辑-->
						</a>
						<%}%>
						</td>
					</tr>
<%
iCurrent++;
iCount++;
}
for (int i=0;i < pageList.getBlankSize(); i++)
{
%>
					<tr class="tr<%=(i % 2)%>">
						<td class="tdh" align="center"></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
<%}%>
					<tr class="tr1">
						<td class="tdh" align="center"><b><%=LocalUtilis.language("message.total",clientLocale)%><!--合计--> <%=iCount%> <%=LocalUtilis.language("message.entries",clientLocale)%>  <!--项--></b></td>
						<td align="center" class="tdh">-</td>
						<td align="center" class="tdh">-</td>
						<td align="center" class="tdh">-</td>
						<td align="center" class="tdh">-</td>
					</tr>
				</table>
				<br>
				<table border="0" width="100%">
					<tr valign="top">
						<td><%=pageList.getPageLink(sUrl)%></td>
					</tr>
				</table>
			</td>
		</tr>		
	</table>
	</td></tr>	
</table>				
</form>
<%@ include file="/includes/foot.inc"%>	
</BODY>
</HTML>
<%menu.remove();%>