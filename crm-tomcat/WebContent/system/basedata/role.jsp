<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.system.*,enfo.crm.dao.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
Integer role_id = Utility.parseInt(request.getParameter("q_role_id"),null);
String role_name = Utility.trimNull(request.getParameter("role_name"));

RoleVO vo = new RoleVO();
RoleLocal role = EJBFactory.getRole();
vo.setRole_name(role_name);
vo.setRole_id(Utility.parseInt(role_id,new Integer(0)));
String[] totalColumn = new String[0];
IPageList pageList = role.listByMul(vo,totalColumn,Utility.parseInt(sPage,1),Utility.parseInt(sPagesize,8));

sUrl = sUrl+ "&role_id=" + role_id+"&role_name="+role_name ;
%>

<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("menu.roleSet",clientLocale)%> </TITLE>
<!--角色设置-->
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
</HEAD>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>

<script language=javascript>
function showMember(role_id){
	location="operator.jsp?d_role_id="+role_id+"&flag=1";
}

function showInfo(role_id)
{
	if(showModalDialog('role_update.jsp?role_id=' + role_id, '', 'dialogWidth:380px;dialogHeight:240px;status:0;help:0') != null)
	{
		sl_update_ok();
		location.reload();
	}
}

function newInfo()
{	
	if(showModalDialog('role_new.jsp', '', 'dialogWidth:380px;dialogHeight:240px;status:0;help:0') != null)
	{
		sl_update_ok();
		location.reload();
	}
}

function $$(_name){
	return document.getElementsByName(_name)[0];
}


function refreshPage()
{
	if(document.theform.q_role_id.value != ""){
		if(!sl_checkNum(document.theform.q_role_id, "<%=LocalUtilis.language("class.qRoleID",clientLocale)%> ", 3, 1)){//角色编号
			return false;
		}
	}	

	
	disableAllBtn(true);
	
	location = 'role.jsp?page=<%=sPage%>&pagesize=' + document.theform.pagesize.value + '&q_role_id=' + document.theform.q_role_id.value+'&role_name='+document.theform.role_name.value;
}

function removeInfo(role_id)
{
	if(!sl_confirm("<%=LocalUtilis.language("message.delRoleConfirm",clientLocale)%> "))//该操作将删除角色，要继续
	     return;
	location = 'role_remove.jsp?role_id=' + role_id;
}


window.onload = function(){
initQueryCondition()};

function StartQuery()
{
	refreshPage();
}
</script>
<BODY class="BODY body-nox" >
<form name="theform" method="POST" action="role_remove.jsp">
<%@ include file="/includes/waiting.inc"%>
		<div id="queryCondition" class="qcMain" style="display:none;width:200px;">
			<table  id="qcTitle" class="qcTitle" align="center" width="100%" cellspacing="0" cellpadding="2">  				
  				<tr>
					<td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ：</td><!--查询条件-->
   					<td align="right">
   						<button type="button"   class="qcClose" accessKey=c id="qcClose" name="qcClose" onclick="javascript:cancelQuery();"></button>
   					</td>
				</tr>
			</table> 
			<!-- 要加入的查询内容 -->
			<table border="0" width="100%" cellspacing="2" cellpadding="2">
				<tr>
					<td align="right"><%=LocalUtilis.language("class.qRoleID",clientLocale)%> : </td><!--角色编号-->
					<td>
						<input onkeydown="javascript:nextKeyPress(this)" type="text" name="q_role_id" size="15" value="<%=Utility.trimNull(role_id)%>">&nbsp;&nbsp;
					</td>
				</tr>
				<tr>
					<td align="right"><%=LocalUtilis.language("class.roleName",clientLocale)%> :</td><!--角色名称-->
					<td><input type="text" name="role_name" size="15" value="<%=role_name%>" onkeydown="javascript:nextKeyPress(this)"></td>
				</tr>
				<tr>
					<td align="center" colspan="2">
						<button type="button"   class="xpbutton3" accessKey=O name="btnQuery" onclick="javascript:StartQuery();"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>O</u>)</button>&nbsp;&nbsp;&nbsp;&nbsp;	 				
					</td><!--确认-->
				</tr>			
			</table>
		</div>


<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>


	<TR>
		<%//@ include file="menu.inc"%>
		<TD vAlign=top align=left>
		<TABLE cellSpacing=0 cellPadding=4 width="100%" align=center border=0>
			<TR>
				<TD>
				<table border="0" width="100%" cellspacing="0" cellpadding="0">
					<tr>
						<td class="page-title"> 			
							<font color="#215dc6"><b><%=menu_info%></b></font>
						</td>
					</tr>
					<tr>
						<td align="right" >
						<div class="btn-wrapper">
							<button type="button"   class="xpbutton3" accessKey=q id="queryButton" name="queryButton"<%if(!input_operator.hasFunc(menu_id, 108)){%> style="display:none"<%}%>><%=LocalUtilis.language("message.query",clientLocale)%> (<u>Q</u>)</button>
							&nbsp;&nbsp;&nbsp;<!--查询-->
						<%if(input_operator.hasFunc(menu_id, 100)){%> 		
							<button type="button"   class="xpbutton3" accessKey=n id="btnNew" name="btnNew" title="<%=LocalUtilis.language("message.newRecord",clientLocale)%> " onclick="javascript:newInfo();"><%=LocalUtilis.language("message.new",clientLocale)%> (<u>N</u>)</button> <!--新建记录-->
							&nbsp;&nbsp;&nbsp;<!--新建-->
						<%}if(input_operator.hasFunc(menu_id, 101)){%> 
							<button type="button"   class="xpbutton3" accessKey=d id="btnDelete" name="btnDelete" title="<%=LocalUtilis.language("message.delRecordsSelect",clientLocale)%> " onclick="javascript:if(confirmRemove(document.theform.role_id)) document.theform.submit();"><%=LocalUtilis.language("message.delete",clientLocale)%> (<u>D</u>)</button>
							<!--删除所选记录--><!--删除-->
						<%}%>
						</div>
						<br/>
						</td>
					</tr>
				</table>
				<table id="table3" border="0" cellspacing="1" cellpadding="2"
					class="tablelinecolor" width="100%" >
					<tr class=trtagsort>
						<td width="10%">
							<input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAll(document.theform.role_id);">
							<%=LocalUtilis.language("class.ID",clientLocale)%> 
						</td>
						<!--编号-->
						<td><%=LocalUtilis.language("class.name",clientLocale)%> </td><!--名称-->
						<td><%=LocalUtilis.language("class.customerSummary",clientLocale)%> </td><!--备注-->
						<td width="50px">成员</td>
						<td width="30px"><%=LocalUtilis.language("message.edit",clientLocale)%> </td><!--编辑-->
						<td width="30px"><%=LocalUtilis.language("message.delete",clientLocale)%> </td><!--删除-->
					</tr>
<%
int iCount = 0;
int iCurrent = 0;
List list = pageList.getRsList();
Map map = null;
for(int i=0;i<list.size();i++)
{
    map = (Map)list.get(i);
	role_id = Utility.parseInt(Utility.trimNull(map.get("ROLE_ID")),null);
	role_name = Utility.trimNull(map.get("ROLE_NAME"));
	String summary = Utility.trimNull(map.get("SUMMARY"));
%>

					<tr class="tr<%=(iCurrent % 2)%>">
						<td width="10%">
						  <input type="checkbox" name="role_id" value="<%=role_id%>" class="flatcheckbox">
						  &nbsp;&nbsp;<%=role_id %>
						</td>
						<td align="left">&nbsp;&nbsp;<%=role_name%></td>
						<td align="left">&nbsp;&nbsp;<%=summary%></td>
						<td align="center" width="50px">
							<button type="button"   class="xpbutton2" name="btnEdit" onclick="javascript:showMember(<%=role_id%>);">&gt;&gt;</button>
						</td>
						<td align="center"> 
						<%if(input_operator.hasFunc(menu_id, 102)){%>
							<a href="#" onclick="javascript:showInfo(<%=role_id%>)">
								<img src="<%=request.getContextPath()%>/images/edit2.gif" width="16" height="16" title="<%=LocalUtilis.language("message.edit",clientLocale)%> " /><!--编辑-->
							</a>
						<%}%>	
						</td>
						<td align="center">
							<%if(input_operator.hasFunc(menu_id, 101)){%>
							<a href="#" onclick="javascript:removeInfo(<%=role_id%>)">
								<img src="<%=request.getContextPath()%>/images/recycle.gif" width="16" height="16" title="<%=LocalUtilis.language("message.delete",clientLocale)%> " /><!--删除-->
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
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
<%}
%>
					<tr class="trbottom">
						<td class="tdh" align="left" colspan="6"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%><!--合计-->&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> <!--项--></b></td>
					</tr>
				</table>
				<table border="0" width="100%" class="page-link">
					<tr valign="top">
						<td><%=pageList.getPageLink(sUrl,clientLocale)%></td>
					</tr>
				</table>
			</td>
		</tr>		
	</table>
	</td>
	</tr>	
</table>				
</form>
<%@ include file="/includes/foot.inc"%>	
</BODY>
</HTML>
<%role.remove();%>

