<%@ page language="java" pageEncoding="GBK" import="java.util.*,java.math.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
//获得参数
String cust_no = Utility.trimNull(request.getParameter("cust_no"));
String cust_name = Utility.trimNull(request.getParameter("cust_name"));

//获得对象
CustClassLogListLocal custclassloglist = EJBFactory.getCustClassLogList();
CustClassLogListVO vo = new CustClassLogListVO();
vo.setCust_no(cust_no);
vo.setCust_name(cust_name);

//页面变量
Map map = null;
String[] totalColumn = new String[0];
IPageList pageList = 
custclassloglist.list_fenye(vo,totalColumn,Utility.parseInt(sPage,1),Utility.parseInt(sPagesize,8));
List list = pageList.getRsList();
Iterator it = list.iterator();

sUrl = sUrl + "&cust_no=" + cust_no + "&cust_name" + cust_name ;


%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("menu.custclassloglist",clientLocale)%> </TITLE><!--客户级别变动查询-->
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>

<script language=javascript>

window.onload = function(){
	initQueryCondition();
}

function $$(_name){
	return document.getElementsByName(_name)[0];
}


function StartQuery()
{
	refreshPage();
}

function refreshPage(){
	disableAllBtn(true);
	location  = 'client_class_log_list.jsp?page=<%=sPage%>&pagesize=' + document.theform.pagesize.value 
				+'&cust_name='+ $$("cust_name").getAttribute("value")
				+ '&cust_no=' + $$("cust_no").getAttribute("value");
					
}


</script>

</HEAD>
  
  <body class= "BODY body-nox">
  	<%@ include file="/includes/waiting.inc"%>
  	<input type="hidden" id="tempArray" value=""/>
	<input type="hidden" id="tempCustId" value=""/>
	<input type="hidden" id="inputMan" value="<%=input_operatorCode%>"/>
    <form id="theform" name="theform" method="post" >
    <div id="queryCondition" class="qcMain" style="display: none; width: 430px">
    	<table id="qcTitle" class="qcTitle" align="center" width="99%" cellspacing="0" cellpadding="2">
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
			<td valign="bottom" align="right"><%=LocalUtilis.language("class.customerID",clientLocale)%> :</td><!--客户编号-->
			<td valign="bottom" align="left">
				<input name="cust_no" value='<%=cust_no%>' onKeyDown="javascript:nextKeyPress(this)" size="18">
			</td>
			<td valign="bottom" align="right"><%=LocalUtilis.language("class.customerName",clientLocale)%> :</td><!--客户名称-->
			<td valign="bottom" align="left">
				<input name="cust_name" value='<%=cust_name%>' onKeyDown="javascript:nextKeyPress(this)" size="18" maxlength="100">
			</td>
		  </tr>
		  <tr>
			<td align="center" colspan=4>
				<button type="button"  class="xpbutton3" name="btnQuery" accessKey=o
					onclick="javascript:StartQuery();"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>O</u>)</button><!--确定-->
			</td>
		  </tr>
		</table>
    </div>
    <div>
		<div align="left" class="page-title">
			<font color="#215dc6"><b><%=menu_info%></b></font>
		</div>
		<div align="right"  class="btn-wrapper">
			<%if (input_operator.hasFunc(menu_id, 108)) {%>
			<button type="button"  class="xpbutton3" accessKey=q id="queryButton" title='<%=LocalUtilis.language("message.query",clientLocale)%> ' name="queryButton"><%=LocalUtilis.language("message.query",clientLocale)%> (<u>Q</u>)</button><%}%>
		</div>
	</div>
	<br/>
	<div>
		<table border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%">
			<tr class="trh">
				<td align="center"><%=LocalUtilis.language("class.customerID",clientLocale)%> </td><!--客户编号-->
				<td align="center"><%=LocalUtilis.language("class.customerName",clientLocale)%> </td><!--客户名称-->
				<td align="center"><%=LocalUtilis.language("class.previousdetail",clientLocale)%> </td><!--变动前级别-->
				<td align="center"><%=LocalUtilis.language("class.classdetail",clientLocale)%> </td><!--变动后级别-->
				<td align="center"><%=LocalUtilis.language("class.operator",clientLocale)%> </td><!--操作人-->
				<td align="center"><%=LocalUtilis.language("class.insertTime2",clientLocale)%> </td><!--操作时间-->
				<td align="center"><%=LocalUtilis.language("class.reviewer",clientLocale)%> </td><!--审核人-->
				<td align="center"><%=LocalUtilis.language("class.checktime",clientLocale)%> </td><!--审核时间-->
			</tr>
			<%
				int iCount = 0;
				int iCurrent = 0;
				Integer serial_no = new Integer(0);
					
				while (it.hasNext()) {
				map = (Map) it.next();
				serial_no = Utility.parseInt(Utility.trimNull(map.get("SERIAL_NO")),new Integer(0));
			%>
			<tr class="tr<%=(iCurrent % 2)%>">
				<td class="tdh" align="center">
				<table border="0" width="100%" cellspacing="0" cellpadding="0">
					<tr>
						<td width="90%" align="center"><%=Utility.trimNull(map.get("CUST_NO"))%></td>
					</tr>
				</table>
				</td>
				<td align="left"><%=Utility.trimNull(map.get("CUST_NAME"))%></td>
				<td align="center"><%=Utility.trimNull(map.get("PREVIOUSDETAIL_NAME"))%></td>
				<td align="center"><%=Utility.trimNull(map.get("CLASSDETAIL_NAME"))%></td>
				<td align="left"><%=Utility.trimNull(map.get("OP_NAME"))%></td>
				<td align="left"><%=Utility.trimNull(map.get("INSERTTIME"))%></td>
				<td align="left"><%=Utility.trimNull(map.get("CHECKMAN"))%></td>
				<td align="left"><%=Utility.trimNull(map.get("CHECKTIME"))%></td>
			</tr>
			<tr id="tr<%=serial_no%>" style="display: none">
				<td align="center" bgcolor="#FFFFFF" colspan="8" >
					<div id="div<%=serial_no%>" style="overflow-y:auto;" align=center>
						<table id="tableCustClass<%=serial_no%>" border="0" width="100%" bgcolor="#000000" cellspacing="1">
						</table>
					</div>
				</td>
			</tr>
			<%iCurrent++;
				iCount++;
			}%>

			<%for (; iCurrent < pageList.getPageSize(); iCurrent++){%>
			<tr class="tr<%=(iCurrent % 2)%>">
				<td class="tdh" align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
			</tr>
			<%}
			%>
			<tr class="trbottom">
				<!--合计--><!--项-->
            	<td class="tdh" align="left" colspan="8"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%>&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> </b></td>
			</tr>
		</table>		
	</div>
	<br>
	<div>
		<%=pageList.getPageLink(sUrl,clientLocale)%>
	</div>
    </form>
  <%@ include file="/includes/foot.inc"%>
  </body>
</html>
<%custclassloglist.remove();%>