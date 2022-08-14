<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*,enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.dao.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>

<%
//��ò�ѯ�����������������
String role_id = Utility.trimNull(request.getParameter("role_id"));
String role_name = Utility.trimNull(request.getParameter("role_name"));
String[] totalCorlm = new String[0];
int iCount = 0;
int iCurrent = 0;
Map map = null;
String remark;

//����URL
sUrl = "role_priority.jsp?role_id=" + role_id +"&role_name="+role_name;

//��ȡ����
RoleVO vo = new RoleVO();
RoleLocal role = EJBFactory.getRole();

//��ȡ�����
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
	/*Ȩ�ޱ༭*/
	function showInfo(role_id){			
		location = "role_priority_info.jsp?role_id=" + role_id;
	}
	
	/*ˢ��*/
	function refreshPage(){
		document.theform.btnQuery.disabled = true;
			
		location = 'role_priority.jsp?page=<%=sPage%>&pagesize=' + document.theform.pagesize.value + '&role_id=' + document.theform.role_id.value+'&role_name='+document.theform.role_name.value;
	}
	
	/*��������*/
	window.onload = function(){
		initQueryCondition()
	};
	
	/*��ѯ*/
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
<!-- ��ѯ������ -->
<div id="queryCondition" class="qcMain" style="display:none;width:220px;">
	<table  id="qcTitle" class="qcTitle" align="center" width="100%" cellspacing="0" cellpadding="2">
  		<tr>
			<td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ��</td><!--��ѯ����-->
   			<td align="right">
   				<button type="button"   class="qcClose" accessKey=c id="qcClose" name="qcClose" onclick="javascript:cancelQuery();"></button>
   			</td>
		</tr>
	</table> 	
	<!-- Ҫ����Ĳ�ѯ���� -->
	<table border="0" width="100%" cellspacing="2" cellpadding="2">
		<tr>
			<td align="right"><%=LocalUtilis.language("class.qRoleID",clientLocale)%> : </td><!--��ɫ���-->
			<td>
				<input type="text" name="role_id" size="15" value="<%=role_id%>" onkeydown=javascript:nextKeyPress(this) >&nbsp;&nbsp;
			</td>
		</tr>
		<tr>
			<td align="right"><%=LocalUtilis.language("class.roleName",clientLocale)%> :</td><!--��ɫ����-->
			<td>
				<input type="text" name="role_name" size="15" value="<%=role_name%>" onkeydown=javascript:nextKeyPress(this) />&nbsp;&nbsp;
			</td>
		</tr>
		<tr>
			<td align="center" colspan="2">
				<button type="button"   class="xpbutton3" accessKey=O name="btnQuery" onclick="javascript:StartQuery();"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>O</u>)</button>	 				
			</td><!--ȷ��-->
		</tr>			
	</table>
</div>

<div>
	<div align="left" class="page-title">
		<font color="#215dc6"><b><%=menu_info%></b></font>
	</div>
	<div align="right" class="btn-wrapper">
		<button type="button"  class="xpbutton3" accessKey=q id="queryButton" name="queryButton" ><%=LocalUtilis.language("message.query",clientLocale)%> (<u>Q</u>)</button><!--��ѯ-->
	</div>
	<br/>
</div>

<div>
	<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%">
		<tr class="trtagsort">
			<td width="30px"><%=LocalUtilis.language("class.ID",clientLocale)%> </td><!--���-->
			<td><%=LocalUtilis.language("class.name",clientLocale)%> </td><!--����-->
			<td><%=LocalUtilis.language("class.customerSummary",clientLocale)%> </td><!--��ע-->
			<td width="80px"><%=LocalUtilis.language("menu.accessEdit",clientLocale)%> </td><!--Ȩ�ޱ༭-->
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
				</a><!--Ȩ�ޱ༭-->
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
			<td class="tdh" align="left" colspan="4"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%> <!--�ϼ�-->&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> <!--��--></b></td>
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

