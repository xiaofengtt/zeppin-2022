<%@ page language="java" pageEncoding="GBK" import="java.util.*,java.math.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>

<%
//获得参数
Integer tree_id = Utility.parseInt(request.getParameter("tree_id"), new Integer(0));
String level_name = Utility.trimNull(request.getParameter("level_name"));
String manager_id_items = Utility.trimNull(request.getParameter("MANAGERID"));//获得群组客户ID
String no = Utility.trimNull(request.getParameter("no"));
String name = Utility.trimNull(request.getParameter("name"));


//获得对象
//enfo.crm.affair.CustManagerTreeLocal custManagerTreeLocal = EJBFactory.getCustManagerTreeLocal();
//CustManagerTreeMembersVO vo1 = new CustManagerTreeMembersVO();
enfo.crm.affair.TcustmanagersLocal custManagersLocal=EJBFactory.getTcustmanagers();
TcustmanagersVO vo=new TcustmanagersVO();
//vo.setTree_id(new Integer(0));//这个页面是执行添加操作的，是把所有的客户经理查出来，所以tree_id设为0

String[] columns=new String[0];
//页面变量
Map map = null;
IPageList pageList =
	custManagersLocal.pagelist_query_forTree(
		vo,
		columns,
		Utility.parseInt(sPage, 1),
		Utility.parseInt(sPagesize, 8));
List list = pageList.getRsList();
Iterator it = list.iterator();

//url设置
sUrl =
	sUrl
		+ "&name="
		+ name
		+ "&no="
		+ no + "&tree_id="+tree_id + "&level_name=" + level_name + "&MANAGERID=" + manager_id_items;
%>

<HTML>
<HEAD>
<TITLE></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css
	rel=stylesheet>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<script language=javascript>
window.onload = function(){
initQueryCondition()};

function StartQuery()
{
	refreshPage();
}

function refreshPage()
{
	disableAllBtn(true);
	var url = 'customer_manager_level_member_new.jsp?page=1&pagesize=' + document.theform.pagesize.value 
				+'&name=' + document.theform.name.value 
				+'&no=' + document.theform.no.value
				+'&tree_id=' + document.theform.tree_id.value
				+'&level_name=' + document.theform.level_name.value
				+'&MANAGERID=' + document.theform.MANAGERID.value;
	location = url;				
}
//验证该群组中是否存在该客户
function changeAction(MANAGERID, MANAGERNAME, form)
{
	tempArray = '<%=manager_id_items%>'.split('$');
	for(var j=0;j<tempArray.length;j++)
	{
		if(MANAGERID == tempArray[j])
		{
			//系统提示    //中已存在
            alert("<%=LocalUtilis.language("message.note",clientLocale)%>：\n\n<%=level_name%><%=LocalUtilis.language("message.alreadyExists",clientLocale)%> "+MANAGERNAME+"！");
			form.checked = false;
		}
	}
	
}

//返回
function returnAction()
{
	location = "customer_manager_level_member_list.jsp?tree_id="+<%=tree_id%> + "&level_name=" + '<%=level_name%>';
}
</script>
</HEAD>
<BODY class="BODY">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="get" action="customer_manager_level_member_new_do.jsp">
<input type="hidden" value="<%=tree_id%>" name="tree_id">
<input type="hidden" value="<%=level_name%>" name="level_name">
<input type="hidden" value="<%=manager_id_items%>" name="MANAGERID">

<div id="queryCondition" class="qcMain"
	style="display: none; width: 460px">
<table id="qcTitle" class="qcTitle" align="center" width="99%" cellspacing="0" cellpadding="2">
	<tr>
		<td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ：</td><!--查询条件-->
		<td align="right">
		<button type="button" class="qcClose" accessKey=c id="qcClose" name="qcClose"
			onclick="javascript:cancelQuery();"></button>
		</td>
	</tr>
</table>
<table>
	<tr>
		<td valign="bottom" align="right"><%=LocalUtilis.language("class.customerID",clientLocale)%> :</td><!--客户编号-->
		<td valign="bottom" align="left">
			<input type="text" name="no"
			onkeydown="javascript:nextKeyPress(this)"
			value="<%=no%>" size="25">&nbsp;&nbsp;
		</td>
		<td valign="bottom" align="right"><%=LocalUtilis.language("class.customerName",clientLocale)%> :</td><!--客户名称-->
		<td valign="bottom" align="left">
			<input type="text" name="name"
			onkeydown="javascript:nextKeyPress(this)"
			value="<%=name%>" size="25">&nbsp;&nbsp;
			
		</td>
	</tr>
	<tr>
		<td align="center" colspan=4>
		<button type="button" class="xpbutton3" name="btnQuery" accessKey=o
			onclick="javascript:StartQuery();"><%=LocalUtilis.language("message.ok2",clientLocale)%> (<u>O</u>)</button><!--确定-->
		</td>
	</tr>
</table>
</div>

<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TR>
		<TD vAlign=top align=left>
		<TABLE cellSpacing=0 cellPadding=4 width="100%" align=center border=0>
			<TR>
				<TD>
				<table border="0" width="100%" cellspacing="0" cellpadding="0">
					<tr>
						<td colspan=6 class="page-title"><b><%=menu_info%></b></td>
					</tr>
					<tr>
						<td align=right>
						<div class="btn-wrapper">
						<button type="button" class="xpbutton3" accessKey=f id="queryButton"
							name="queryButton" style="display: none;"><%=LocalUtilis.language("message.query",clientLocale)%> (<u>F</u>)</button><!--查询-->
							&nbsp;&nbsp;&nbsp;
						<!--保存-->
						<button type="button" class="xpbutton3" accessKey=f id="saveButton"
                            name="saveButton" onclick="javascript:if(confirmRefer(document.theform.selectbox)) document.theform.submit();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
							&nbsp;&nbsp;
						<!--<button type="button" class="xpbutton3" accessKey=f id="cancelButton"
							name="cancelButton" onclick="javascript:window.returnValue=null;window.close();">取消(<u>C</u>)</button>-->
							<button type="button" class="xpbutton3" accessKey=f id="cancelButton"
							name="cancelButton" onclick="javascript:returnAction();"><%=LocalUtilis.language("message.back",clientLocale)%> (<u>R</u>)</button><!--返回-->
							</div>
							</td>
					</tr>
				</table>
				<br/>
				<table id="table3" border="0" cellspacing="1" cellpadding="2"
					class="tablelinecolor" width="100%">
					<tr class="trh">
						<td align="center" width="15%"><input type="checkbox" name="btnCheckbox"
							class="selectAllBox"
							onclick="javascript:selectAllBox(document.theform.selectbox,this);"><%=LocalUtilis.language("class.managerID",clientLocale)%></td> <!--客户经理编号-->
						<td align="left"><%=LocalUtilis.language("class.managerName",clientLocale)%></td><!--名称-->
					</tr>
					<%
						int iCount = 0;
						int iCurrent = 0;
						//Integer MANAGERID = new Integer(0);
						while (it.hasNext()) {
							map = (Map) it.next();
					%>
					<tr class="tr<%=(iCurrent % 2)%>">
						<td class="tdh" align="center" width="15%">
						<table border="0" width="100%" cellspacing="0" cellpadding="0">
							<tr>
								<td width="10%"><input type="checkbox" name="selectbox"
									value="<%=tree_id%>$<%=map.get("ManagerID")%>$<%=map.get("ManagerName")%>" class="flatcheckbox" onclick="javascript:changeAction(<%=map.get("ManagerID")%>,'<%=map.get("ManagerName")%>', this);"></td>
								<td width="90%" align="center"><%=Utility.trimNull(map.get("ManagerID"))%></td>
							</tr>
						</table>
						</td>
						<td align="left"><%=Utility.trimNull(map.get("ManagerName"))%></td>
					</tr>
					<%iCurrent++;
					iCount++;
					}%>

					<%for (; iCurrent < pageList.getPageSize(); iCurrent++) {
					%>
					<tr class="tr<%=(iCurrent % 2)%>">
						<td class="tdh" align="center"></td>
						<td align="center"></td>
					</tr>
					<%}
					%>
					<tr class="trbottom">
						<!--合计--><!--项-->
                        <td class="tdh" align="center"><b><%=LocalUtilis.language("message.total",clientLocale)%> <%=pageList.getTotalSize()%><%=LocalUtilis.language("message.entries",clientLocale)%> </b></td>
						<td align="center"></td>
					</tr>
				</table>

				<br>

				<table border="0" width="100%"  class="page-link">
					<tr valign="top">
						<td><%=pageList.getPageLink(sUrl)%></td>
					</tr>
				</table>

				</TD>
			</TR>
		</TABLE>
		</TD>
</TABLE>
</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>
<%custManagersLocal.remove();
%>
