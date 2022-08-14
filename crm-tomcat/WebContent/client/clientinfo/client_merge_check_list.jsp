<%@ page contentType="text/html; charset=GBK" import="java.util.*,java.math.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
Integer q_check_flag = Utility.parseInt(request.getParameter("q_check_flag"), new Integer(0));
String q_cust_name = Utility.trimNull(request.getParameter("q_cust_name"));

CustomerLocal cust_local = EJBFactory.getCustomer();

CustomerVO cust_vo = new CustomerVO();
cust_vo.setCheck_flag(q_check_flag);
cust_vo.setCust_name(q_cust_name);
cust_vo.setInput_man(input_operatorCode);

IPageList pagelist = cust_local.listMerge(cust_vo, Utility.parseInt(sPage,1),Utility.parseInt(sPagesize,8));
List list = pagelist.getRsList();
Iterator it = list.iterator();

String queryString = Utility.getQueryString(request, new String[]{"q_check_flag", "q_cust_name"});
sUrl += "&"+queryString;
cust_local.remove();
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
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<script language=javascript>
window.onload = function() {
		initQueryCondition();
	};

//查看当前客户信息
function showInfo(custid) {
	var url = '<%=request.getContextPath()%>/client/clientinfo/client_query_info.jsp?cust_id='+custid+'&page=<%=sPage%>&pagesize=' + document.theform.pagesize.value;
 	if(showModalDialog(url,'','dialogWidth:840px;dialogHeight:550px;status:0;help:0')!=null) {
	}
}

function StartQuery() {
	refreshPage();
}

function refreshPage() {
	disableAllBtn(true);
	location.search = '?page=<%=sPage%>&pagesize=' + document.theform.pagesize.value 
		+ '&q_check_flag=' + document.theform.q_check_flag.value+ '&q_cust_name=' + document.theform.q_cust_name.value;
}

function check(serial_no, check_flag, from_cust_id, to_cust_id) {
	location.href = "client_unite_info.jsp?<%=queryString%>&serial_no="+serial_no+"&check_flag="+check_flag
						+"&cust1_id="+from_cust_id+"&cust2_id="+to_cust_id;
}

function recover(serial_no, check_flag, from_cust_id, to_cust_id) {
	location.href = "client_unite_info.jsp?<%=queryString%>&serial_no="+serial_no+"&check_flag="+check_flag
						+"&cust1_id="+from_cust_id+"&cust2_id="+to_cust_id;
}
</script>
</HEAD>
<BODY class="BODY body-nox">
<%@ include file="/includes/waiting.inc"%> 
<form name="theform">

<div id="queryCondition" class="qcMain" style="display:none;width:420px;">
<table id="qcTitle" class="qcTitle" align="center" width="99%" cellspacing="0" cellpadding="2">
  <tr>
   <td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ：</td><!--查询条件-->
   <td align="right"><button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose"
       onclick="javascript:cancelQuery();"></button></td>
  </tr>
</table>

<table width="99%" cellspacing="0" cellpadding="2">
	<tr>
		<td align="right">审核状况:</td>
		<td>
			<select size="1" name="q_check_flag"><%=Argument.getMergeCheckStatusList(q_check_flag)%></select>
		</td>
		<td align="right">涉及客户的名字:</td>
		<td align=left>
			<input type="text" name="q_cust_name" onkeydown="javascript:nextKeyPress(this)" value="<%=q_cust_name%>" size="20">
		</td>					
	</tr>
	<tr>
		<td align="center" colspan=4>	  
			<button type="button"  class="xpbutton3" accessKey=o name="btnQuery" onclick="javascript:StartQuery();"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>O</u>)</button>
		</td><!--确定-->
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
						<td colspan=2 class="page-title"><font color="#215dc6"><b><%=menu_info%></b></font></td>						
					</tr>
					
                   <tr>
					 <td align="right">
					 <div class="btn-wrapper">
					 <button type="button"  class="xpbutton3" accessKey=q id="queryButton" name="queryButton"><%=LocalUtilis.language("message.query",clientLocale)%> (<u>Q</u>)</button><!--查询-->	
			<%--if (input_operator.hasFunc(menu_id, 100)) {%>
						<!--合并--> 
						<button type="button"  class="xpbutton3"  name="btnCancel" title="<%=LocalUtilis.language("message.merger",clientLocale)%> "
						    onclick="javascript:if(document.theform.onsubmit()){disableAllBtn(true);hbInfo();}"><%=LocalUtilis.language("message.merger",clientLocale)%> <!--合并--></button>
						&nbsp;&nbsp;&nbsp; 
			<%}--%>
					</div>
					 </td>
					</tr>
				</table>
				<br/>
				<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%">
					<tr class="trh">
						<td align="center">被合并客户的名字</td>
						<td align="center">被合并客户的证件号码</td>
<%if(user_id.intValue() == 21){/*金汇CRM要求显示客户等级*/ %>
						<td align="center">被合并客户的客户等级</td><!--客户等级-->
<%}else{ %>
						<td align="center">被合并客户的客户类型</td>
<%} %>
						<td align="center">合并到客户的名字</td>
						<td align="center">合并到客户的证件号码</td>
<%if(user_id.intValue() == 21){/*金汇CRM要求显示客户等级*/ %>
						<td align="center">合并到客户的客户等级</td><!--客户等级-->
<%}else{ %>
						<td align="center">合并到客户的客户类型</td>
<%} %>
						<td align="center">操作</td>
					</tr>
				<%
				int iCurrent = 0;
				while (it.hasNext()) {
					Map map = (Map)it.next();
					int check_flag = ((Integer)map.get("CHECK_FLAG")).intValue();	%>
					<tr class="tr<%=(iCurrent % 2)%>">
						<td align="center"><%=Utility.trimNull(map.get("FROM_CUST_NAME"))%></td>
						<td align="left"><%=Utility.trimNull(map.get("FROM_CARD_ID"))%>(<%=Utility.trimNull(map.get("FROM_CARD_TYPE_NAME"))%>)</td>
<%if(user_id.intValue() == 21){/*金汇CRM要求显示客户等级*/ %>
						<td align="center"><%=Utility.trimNull(map.get("FROM_CUST_LEVEL_NAME"))%></td><!--客户等级-->
<%}else{ %>
						<td align=center><%=Utility.trimNull(map.get("FROM_IS_DEAL_NAME"))%></td>
<%} %>
						<td align="center"><%=Utility.trimNull(map.get("TO_CUST_NAME"))%></td>
						<td align="left" ><%=Utility.trimNull(map.get("TO_CARD_ID"))%>(<%=Utility.trimNull(map.get("TO_CARD_TYPE_NAME"))%>)</td>
<%if(user_id.intValue() == 21){/*金汇CRM要求显示客户等级*/ %>
						<td align="center"><%=Utility.trimNull(map.get("TO_CUST_LEVEL_NAME"))%></td><!--客户等级-->
<%}else{ %>
						<td align=center><%=Utility.trimNull(map.get("TO_IS_DEAL_NAME"))%></td>
<%} %>
						<td align="center">
							<button type="button"  class="xpbutton2" 
								onclick="javascript:<%=check_flag==1?"check":"recover"%>(<%=map.get("SERIAL_NO")+","+map.get("CHECK_FLAG")+","+map.get("FROM_CUST_ID")+","+map.get("TO_CUST_ID")%>)"><%=check_flag==1?"审核":"恢复"%></button>
						</td>
					</tr>
				<%
					iCurrent++;
				}
				for (; iCurrent < pagelist.getPageSize(); iCurrent++) { %>
					<tr class="tr<%=(iCurrent % 2)%>">
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
					</tr>
				<%}%>
					<tr class="trbottom">
						<td class="tdh" align="left" colspan="7" >
						    <b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%>&nbsp;<!--合计--><%=pagelist.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> <!--项--></b>
						</td>
					</tr>
				</table>

				<table border="0" width="100%" class="page-link">
					<tr valign="top">
						<td><%=pagelist.getPageLink(sUrl,clientLocale)%></td>
						<td align="right">
						<!--<button type="button"  class="xpbutton3" accessKey=b id="btnReturn" name="btnReturn" title="返回上一页" onclick="javascript:history.back();">返回(<u>B</u>)</button>
						&nbsp;&nbsp;&nbsp;--></td>
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