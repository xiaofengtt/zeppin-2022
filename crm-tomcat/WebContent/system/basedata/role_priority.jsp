<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*,enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.dao.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>

<%
//获得查询条件及定义参数变量
String role_id = Utility.trimNull(request.getParameter("role_id"));
String role_name = Utility.trimNull(request.getParameter("role_name"));
String[] totalCorlm = new String[0];
int iCount = 0;
int iCurrent = 0;
Map map = null;
String remark;

//定义URL
sUrl = "role_priority.jsp?role_id=" + role_id +"&role_name="+role_name;

//获取对象
RoleVO vo = new RoleVO();
RoleLocal role = EJBFactory.getRole();

//获取结果集
vo.setRole_name(role_name);
vo.setRole_id(Utility.parseInt(role_id,new Integer(0)));
IPageList pageList = role.listByMul(vo,totalCorlm,Utility.parseInt(sPage,1),Utility.parseInt(sPagesize,8));

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

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>

<script language=javascript>
	/*权限编辑*/
	function showInfo(role_id){			
		location = "role_priority_info.jsp?role_id=" + role_id;
	}
	
	/*刷新*/
	function refreshPage(){
		document.theform.btnQuery.disabled = true;
			
		location = 'role_priority.jsp?page=<%=sPage%>&pagesize=' + document.theform.pagesize.value + '&role_id=' + document.theform.role_id.value+'&role_name='+document.theform.role_name.value;
	}
	
	/*启动加载*/
	window.onload = function(){
		initQueryCondition()
	};
	
	/*查询*/
	function StartQuery(){
		refreshPage();
	}
</script>
<style>
.row div{
	display:inline;
	width:240px;
	border-top:1px solid  #C1BBAB;
	border-left:1px solid #C1BBAB;
}

.lastcell{
	border-right:1px solid #C1BBAB;
	text-align:right;
}

.lastcell2{
	border-right:1px solid #C1BBAB;
	text-align:center;
}

.row{
	font:normal 12px;
}

.lastrow div{
	border-bottom:1px solid #C1BBAB;
}
</style>
</HEAD>

<BODY class="body body-nox">
<form name="theform" method="POST" action="../basedata/role_remove.jsp">
<%@ include file="/includes/waiting.inc"%>
<!-- 查询浮动框 -->
<div id="queryCondition" class="qcMain" style="display:none;width:220px;">
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
				<input type="text" name="role_id" size="15" value="<%=role_id%>" onkeydown=javascript:nextKeyPress(this) >&nbsp;&nbsp;
			</td>
		</tr>
		<tr>
			<td align="right"><%=LocalUtilis.language("class.roleName",clientLocale)%> :</td><!--角色名称-->
			<td>
				<input type="text" name="role_name" size="15" value="<%=role_name%>" onkeydown=javascript:nextKeyPress(this) />&nbsp;&nbsp;
			</td>
		</tr>
		<tr>
			<td align="center" colspan="2">
				<button type="button"   class="xpbutton3" accessKey=O name="btnQuery" onclick="javascript:StartQuery();"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>O</u>)</button>	 				
			</td><!--确认-->
		</tr>			
	</table>
</div>

<div>
	<div align="left" class="page-title">
		<font color="#215dc6"><b><%=menu_info%></b></font>
	</div>
	<div align="right" class="btn-wrapper">
		<button type="button"  class="xpbutton3" accessKey=q id="queryButton" name="queryButton" ><%=LocalUtilis.language("message.query",clientLocale)%> (<u>Q</u>)</button><!--查询-->
	</div>
	<br/>
</div>

<div>
	<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%">
		<tr class="trtagsort">
			<td width="30px"><%=LocalUtilis.language("class.ID",clientLocale)%> </td><!--编号-->
			<td><%=LocalUtilis.language("class.name",clientLocale)%> </td><!--名称-->
			<td><%=LocalUtilis.language("class.customerSummary",clientLocale)%> </td><!--备注-->
			<td width="80px"><%=LocalUtilis.language("menu.accessEdit",clientLocale)%> </td><!--权限编辑-->
		</tr>
		
		<%
			List list = pageList.getRsList();
			
			for(int i=0;i<list.size();i++){
			    map = (Map)list.get(i);
				role_id = Utility.trimNull(map.get("ROLE_ID"));
				role_name = Utility.trimNull(map.get("ROLE_NAME"));
				remark = Utility.trimNull(map.get("REMARK"));
		%>		
		<tr class="tr<%=(iCurrent%2)%>">
			<td class="tdh" align="center">
				<%=role_id %>
			</td>
			<td align="center"><%=role_name%></td>
			<td align="center"><%=remark%></td>
			<td align="center">
			<%if(input_operator.hasFunc(menu_id, 100)){%>
				<a href="javascript:showInfo(<%=role_id%>)">
					<img src="<%=request.getContextPath()%>/images/edit2.gif" width="16" height="16" title="<%=LocalUtilis.language("menu.accessEdit",clientLocale)%> " />
				</a><!--权限编辑-->
			<%}%>
			</td>
		</tr>
		<%
				iCurrent++;
				iCount++;
			}
		%>
	
		<%
			for(int i=0;i<pageList.getBlankSize();i++){
		%>
		<tr class="tr<%=(i%2)%>">
			<td class="tdh" align="center">&nbsp;&nbsp;</td>
			<td>&nbsp;&nbsp;</td>
			<td>&nbsp;&nbsp;</td>
			<td>&nbsp;&nbsp;</td>
		</tr>
		<%}%>
		<tr class="trbottom">
			<td class="tdh" align="left" colspan="4"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%> <!--合计-->&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> <!--项--></b></td>
		</tr>
	</table>
</div>
<div style="margin-top:5px;" class="page-link">
	&nbsp;<%=pageList.getPageLink(sUrl,clientLocale)%>
</div>
</form>
<%@ include file="/includes/foot.inc"%>	
<% role.remove();%>
</BODY>
</HTML>

