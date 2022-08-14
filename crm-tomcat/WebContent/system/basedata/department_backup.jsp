<%@ page contentType="text/html; charset=GBK" import="enfo.crm.tools.*,enfo.crm.system.*,enfo.crm.dao.*"  %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>

<HTML>
<HEAD>
<TITLE></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/mstree/deeptree.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
var menus = new Array();
menus[0] = "<%=LocalUtilis.language("message.SueDepartmentAdd",clientLocale)%> ";//添加下级部门
menus[1] = "<%=LocalUtilis.language("message.update",clientLocale)%> ";//修改
menus[2] = "<%=LocalUtilis.language("message.delete",clientLocale)%> ";//删除
menus[3] = "-";
menus[4] = "<%=LocalUtilis.language("message.refresh",clientLocale)%> ";//刷新
menus[5] = "<%=LocalUtilis.language("message.back",clientLocale)%> ";//返回
var links = new Array();
links[0] = "javascript: newInfo();";
links[1] = "javascript: showInfo();";
links[2] = "javascript: removeInfo();";
links[3] = "";
links[4] = "javascript: location.reload();";
links[5] = "javascript: history.back();";

var vis = new Array();
vis[0] = <%=input_operator.hasFunc(menu_id, 100)%>;
vis[1] = <%=input_operator.hasFunc(menu_id, 102)%>;
vis[2] = <%=input_operator.hasFunc(menu_id, 101)%>;
vis[3] = true;
vis[4] = true;
vis[5] = true;

var depart_id = "";
var depart_name = "";
var paremt_id = "";

function getPopInfo()
{
	if(window.event.srcElement == null)         
		return false;
	
	var obj = window.event.srcElement;

	var hrefs = obj.href;
	if (hrefs == null || hrefs == "")
		return false;

	var index = hrefs.indexOf("#") + 1;
	depart_id = hrefs.substring(index, hrefs.length);
	depart_name = obj.innerText;
	
	
	return true;
}

function showInfo()
{	
    if(depart_id==''||depart_id=='0')//depart_id=0或者为空，右键修改实效
    	return;
    if(showModalDialog('department_update.jsp?depart_id=' + depart_id,'','dialogWidth:420px;dialogHeight:310px;status:0;help:0')!= null){
		sl_update_ok();
		location.reload();
	}
}

function newInfo()
{
	if(showModalDialog('department_new.jsp?parent_id=' + depart_id,'', 'dialogWidth:420px;dialogHeight:310px;status:0;help:0')!=null){
		sl_update_ok();
		location.reload();
	}
}

function removeInfo()
{
 	if(depart_id == "")	return;
	if(!sl_confirm("<%=LocalUtilis.language("message.deleteManager",clientLocale)%>(" + depart_name + ")<%=LocalUtilis.language("class.department",clientLocale)%>，<%=LocalUtilis.language("message.toContinue",clientLocale)%> "))
	//"该操作将删除(" + depart_name + ")部门，要继续"
	     return;
	location = 'department_delete.jsp?depart_id=' + depart_id;
}
</SCRIPT>
</HEAD>
<BODY class="BODY body-nox">
<form name="theform">
<TABLE  height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TBODY>
		<TR>
	 <TD>
            <TABLE  class=flyoutMenu >
      </table>
    </TD>
			<TD vAlign=top align=left width="100%">
			<TABLE  cellSpacing=0 cellPadding=0 width="100%" align=center border=0>
				<TBODY>
					<TR>
						<TD>
						<table border="0" width="100%">
							<tr>
								<td class="page-title">		
									<font color="#215dc6"><b><%=menu_info%></b></font>
								</td>
							</tr>
							<tr>
								<td align="right" valign="bottom">
									<div class="btn-wrapper">
								<button type="button"  class="xpbutton3" accessKey=r name="btnRefresh" title="<%=LocalUtilis.language("message.refresh",clientLocale)%> " onclick="javascript:location.reload();"><%=LocalUtilis.language("message.refresh",clientLocale)%> (<u>R</u>)</button></div><br/></td>
								<!--刷新-->
							</tr>
						</table>
						</TD>
					</TR>
					<tr>
						<td>
						<div id="deeptree" class="deeptree" align="left" CfgXMLSrc="department_tree.xml" style="width: 100%"></div>
						</td>
					</tr>
				</TBODY>
			</TABLE>
			</TD>
		</TR>
	</TBODY>
</TABLE>
</form>
</BODY>
</HTML>
