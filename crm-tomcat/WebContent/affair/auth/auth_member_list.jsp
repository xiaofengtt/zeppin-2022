<%@ page language="java" pageEncoding="GBK" import="java.util.*,java.math.*,enfo.crm.system.*,enfo.crm.affair.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>

<%
Integer ca_id  = Utility.parseInt(request.getParameter("ca_id"),new Integer(0));
String ca_name = Utility.trimNull(request.getParameter("ca_name"));

AuthorizationCustsVO authorizationCustsVO = new AuthorizationCustsVO();
AuthorizationLocal authorizationLocal = EJBFactory.getAuthorizationLocal();

authorizationCustsVO.setCa_id(ca_id);
String[] totalColumn=new String[0];
IPageList pageList = authorizationLocal.pagelist_query(authorizationCustsVO, totalColumn,Utility.parseInt(sPage,1), Utility.parseInt(sPagesize,8));
List list = pageList.getRsList();
Iterator it = list.iterator();
Map map = new HashMap();
Map map1 = new HashMap();
//组合客户ID
String items = "";
int k = 0;
for(int j =0;j<list.size(); j++)
{
	k++;
	map1 = (Map)list.get(j);
	items += map1.get("CUST_ID").toString();
	if(k<list.size())
		items += "$";
}
sUrl = sUrl + "&ca_id=" + ca_id
			+ "&ca_name=" + ca_name;
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
//窗体加载
window.onload = function(){
initQueryCondition()};
//查询
function StartQuery()
{
	refreshPage();
}
//分页
function refreshPage()
{
	disableAllBtn(true);
	var url = 'auth_member_list.jsp?page=<%=sPage%>&pagesize=' + document.theform.pagesize.value 
				+'&ca_id=' + document.theform.ca_id.value
				+'&ca_name=' + document.theform.ca_name.value;
	location = url;				
}
/*新增群组成员*/
function newInfo()
{	
	location = "auth_member_new.jsp?ca_id="+<%=ca_id%> + "&ca_name=" + '<%=ca_name%>'+ "&cust_id_items=" + '<%=items%>';
}

/*保存选择条件的文本*/
function changeAction()
{
	var item = document.theform.group_id;
	if(item.options[item.selectedIndex].text != "<%=LocalUtilis.language("message.pleaseSelect",clientLocale)%> ")//请选择
		document.theform.ca_name.value = item.options[item.selectedIndex].text;
}
</script>
</HEAD>
<BODY class="BODY body-nox">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="post" action="auth_member_remove.jsp">
<!--保存群组ID、群组名称(并作为地址栏参数)-->
<input type="hidden" value="<%=ca_id%>" name="ca_id">
<input type="hidden" name="ca_name" value="<%=ca_name%>">

<div id="queryCondition" class="qcMain"
	style="display: none; width: 200px">
<table id="qcTitle" class="qcTitle" align="center" width="99%"
	cellspacing="0" cellpadding="2">
	<tr>
		<td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ：</td><!--查询条件-->
		<td align="right">
		<button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose"
			onclick="javascript:cancelQuery();"></button>
		</td>
	</tr>
</table>
<table>
	<tr>
		<td valign="bottom" align="right" width="80"><%=LocalUtilis.language("class.groupName2",clientLocale)%> :</td><!--群组名称-->
		<td valign="bottom" align="left" width="130">
		<select name="group_id" onkeydown="javascript:nextKeyPress(this)" style="width: 100px;" onclick="javascript:changeAction();">
			<%=Argument.getCustGroupOption(ca_id, ca_id)%>
		</select>
		&nbsp;&nbsp; 
		</td>
	</tr>
	<tr>
		<td align="center" colspan=4>
		<button type="button"  class="xpbutton3" name="btnQuery" accessKey=o
			onclick="javascript:StartQuery();"><%=LocalUtilis.language("message.confirm",clientLocale)%> (<u>O</u>)</button><!--确定-->
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
						<!--授权集客户-->
                        <td colspan=6 class="page-title"><b><%=menu_info%></b></td>
					</tr>
					<tr>
						<td align=right>
						<div class="btn-wrapper">
						<button type="button"  class="xpbutton3" accessKey=f id="queryButton"
							name="queryButton" ><%=LocalUtilis.language("message.query",clientLocale)%> (<u>F</u>)</button><!--查询-->
							&nbsp;&nbsp;&nbsp;
						<button type="button"  class="xpbutton3" accessKey=f id="addButton"
							name="queryButton" onclick="javascript:newInfo();"><%=LocalUtilis.language("message.append",clientLocale)%> (<u>N</u>)</button><!--新增-->
                        &nbsp;&nbsp;&nbsp;
                        <!--删除所选记录--><!--删除-->
						<button type="button"  class="xpbutton3" accessKey=d id="btnDelete" name="btnDelete" title='<%=LocalUtilis.language("message.delRecordsSelect",clientLocale)%>' onclick="javascript:if(confirmRemove(document.theform.selectbox)) document.theform.submit();"><%=LocalUtilis.language("message.delete",clientLocale)%> (<u>D</u>)</button>
							&nbsp;&nbsp;&nbsp;
						<!--返回-->
                        <button type="button"  class="xpbutton3" accessKey=f id="returnBut" name="returnBut" onclick="javascript:disableAllBtn(true);location='auth_define.jsp';"><%=LocalUtilis.language("message.back",clientLocale)%> (<u>R</u>)</button>
							&nbsp;&nbsp;&nbsp;
							</div>
						</td>
					</tr>
				</table>
				<br/>
				<table id="table3" border="0" cellspacing="1" cellpadding="2"
					class="tablelinecolor" width="100%">
					<tr class="trh">
						<td align="center" width="60%"><input type="checkbox" name="btnCheckbox"
							class="selectAllBox"
							onclick="javascript:selectAllBox(document.theform.selectbox,this);"><%=LocalUtilis.language("class.customerName",clientLocale)%></td><!--客户名称-->
						<td align="center"><%=LocalUtilis.language("class.CA_Name",clientLocale)%></td><!--授权集名称-->
						<td align="center" width="8%">授权方式</td>
						
					</tr>
					<%
						int iCount = 0;
						int iCurrent = 0;
						Integer temp_cust_id=new Integer(0);
						int auth_flag = 0;
						String auth_flag_name = "";
						while(it.hasNext())
						{
							map = (Map)it.next();
							temp_cust_id=Utility.parseInt(Utility.trimNull(map.get("CUST_ID")),new Integer(0));
							auth_flag = Utility.parseInt(Utility.trimNull(map.get("AUTH_FLAG")),0);
							if(auth_flag==1)
								auth_flag_name = "可查询";
							else
								auth_flag_name = "仅查询";
					%>
					<tr class="tr<%=(iCurrent % 2)%>">
						<td class="tdh" align="center" width="60%">
						<table border="0" width="100%" cellspacing="0" cellpadding="0">
							<tr>
								<td width="10%"><input type="checkbox" name="selectbox"
									value="<%=map.get("SERIAL_NO")%>$<%=map.get("CUST_ID")%>" class="flatcheckbox"></td>
								<td width="90%" align="left"><%=Argument.getCustomerName(temp_cust_id,input_operatorCode)%></td>
							</tr>
						</table>
						</td>
						<td align="center" ><%=ca_name%></td>
						<td align="center"><%=Utility.trimNull(auth_flag_name)%></td>
					</tr>
					<%	
						iCount++;
						iCurrent++;
						}
					%>
					<%for (; iCurrent < pageList.getPageSize(); iCurrent++) {
					%>
					<tr class="tr<%=(iCurrent % 2)%>">
						<td class="tdh" align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
					</tr>
					<%}
					%>
					<tr class="trbottom">
						<!--合计--><!--项-->
                        <td class="tdh" align="center"><b><%=LocalUtilis.language("message.total",clientLocale)%> <%=pageList.getTotalSize()%><%=LocalUtilis.language("message.entries",clientLocale)%> </b></td>
						<td align="center"></td>
						<td align="center"></td>
					</tr>
				</table>
				<br>
				<table border="0" width="100%" class="page-link">
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
<%authorizationLocal.remove();
%>
