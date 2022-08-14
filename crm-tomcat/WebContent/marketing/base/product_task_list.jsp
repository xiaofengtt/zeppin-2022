<%@ page contentType="text/html; charset=GBK"  import="java.math.*,java.util.*,enfo.crm.system.*,enfo.crm.dao.*,enfo.crm.tools.*, enfo.crm.vo.*, enfo.crm.intrust.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%

Integer product_id = Utility.parseInt(request.getParameter("product_id"),new Integer(0));
Integer start_date = Utility.parseInt(request.getParameter("start_date"),null);
Integer end_date = Utility.parseInt(request.getParameter("end_date"),null);

String[] totalColumn = new String[0];
ProductLocal product = EJBFactory.getProduct();
ProductVO vo = new ProductVO();

vo.setProduct_id(product_id);
vo.setStart_date(start_date);
vo.setEnd_date(end_date);
vo.setTask_type(new Integer(103));
IPageList pageList = product.listTask(vo,totalColumn,Utility.parseInt(sPage,1),Utility.parseInt(sPagesize,8));

sUrl = sUrl+"&product_id="+ product_id+"&start_date="+start_date+"&end_date"+end_date;
%>
<!DOCTYPE html>
<HTML>
<HEAD>
<TITLE></TITLE>
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<base target="_self">

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/financing.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<script language=javascript>

function newInfo(product_id)
{
	if(showModalDialog('product_task_info.jsp?product_id='+ product_id, '', 'dialogWidth:800px;dialogHeight:600px;status:0;help:0') != null)
	{
		sl_update_ok();
		location.reload();
	}
}
function showInfo(serial_no)
{
	if(showModalDialog('product_task_info.jsp?serial_no='+ serial_no, '', 'dialogWidth:800px;dialogHeight:600px;status:0;help:0') != null)
	{
		sl_update_ok();
		location.reload();
	}
}
/*删除功能*/
function removeInfo()
{
	if(checkedCount(document.getElementsByName("serial_no")) == 0){
		sl_alert("<%=LocalUtilis.language("message.selectRecordsToDelete",clientLocale)%> ！");//请选定要删除的记录
		return false;
	}
	if(sl_check_remove()){			
		var url = 'product_task_remove.jsp?';
		document.getElementsByName("theform")[0].setAttribute("action",url);
		document.getElementsByName("theform")[0].submit();
		return true;		
	}
	return false;
}
function refreshPage()
{
	syncDatePicker(document.theform.start_date_picker, document.theform.start_date);
	syncDatePicker(document.theform.end_date_picker, document.theform.end_date);
	var url ="product_task_list.jsp?page=1&pagesize=" + document.theform.pagesize.value
					+"&product_id="+document.theform.product_id.value
					+"&start_date="+document.theform.start_date.value
					+"&end_date="+document.theform.end_date.value;
					
	location = url
}

function StartQuery()
{
	refreshPage();
}

window.onload = function(){
initQueryCondition()};
</script>

</HEAD>

<BODY class="BODY body-nox">
<%@ include file="/includes/waiting.inc"%>
<form name="theform">
<input type=hidden name="product_id" value="<%=product_id%>">
<div id="queryCondition" class="qcMain" style="display:none;width:480px;">
	<table  id="qcTitle" class="qcTitle" align="center" width="100%" cellspacing="0" cellpadding="2">
		<tr>
			<td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ：</td><!--查询条件-->
			<td align="right"><button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose"
	       		onclick="javascript:cancelQuery();"></button></td>
		</tr>
	</table>
	<table width="100%" cellspacing="0" cellpadding="2" border="0">
		<tr>
			<td align="right"><%=LocalUtilis.language("class.startDate",clientLocale)%> :</td><!--开始日期-->
			<td>
				<input TYPE="text" style="width:120" NAME="start_date_picker" class=selecttext value="<%=Format.formatDateLine(start_date)%>" onKeyDown="javascript:nextKeyPress(this)"> 
		        <input TYPE="button" value="▼" class=selectbtn onClick="javascript:CalendarWebControl.show(theform.start_date_picker,theform.start_date_picker.value,this);" onKeyDown="javascript:nextKeyPress(this)"> 
		        <input TYPE="hidden" NAME="start_date" value="">	</td>
			<td align="right"><%=LocalUtilis.language("class.endDate",clientLocale)%> :</td><!--结束日期-->
			<td>
				<input TYPE="text" style="width:120" NAME="end_date_picker" class=selecttext value="<%=Format.formatDateLine(end_date)%>" onKeyDown="javascript:nextKeyPress(this)"> 
	       		<input TYPE="button" value="▼" class=selectbtn onClick="javascript:CalendarWebControl.show(theform.end_date_picker,theform.end_date_picker.value,this);" onKeyDown="javascript:nextKeyPress(this)"> 
		        <input TYPE="hidden" NAME="end_date" value="">	</td>
		</tr>
		<tr>
			<td align="center" colspan=6><button type="button"  class="xpbutton3" name="btnQuery" accessKey=o onClick="javascript:StartQuery();"><%=LocalUtilis.language("message.ok",clientLocale)%> <!--确认-->(<u>O</u>)</button></td>
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
						<td colspan=4 class="page-title">
							<!--基本管理-->
							<font color="#215dc6"><b><%=LocalUtilis.language("class.formatDateLine",clientLocale)%></b></font>
						</td><!--基本管理>>产品管理-->						
					</tr>
					<tr>
						<td align=right class="btn-wrapper">	
							<font size=3>&nbsp;&nbsp;&nbsp;<b><%=LocalUtilis.language("class.productName4",clientLocale)%>：</font><%=Utility.trimNull(Argument.getProductName(product_id))%></b>		
							<button type="button"  class="xpbutton3" accessKey=f id="queryButton" name="queryButton"><%=LocalUtilis.language("message.query",clientLocale)%> <!--查询-->(<u>F</u>)</button>
							&nbsp;&nbsp;&nbsp;
							<button type="button"  class="xpbutton3" accessKey=n id="newButton" name="newButton" onclick="javascript:newInfo('<%=product_id%>');"><%=LocalUtilis.language("message.new",clientLocale)%> <!--新建-->(<u>N</u>)</button>
							&nbsp;&nbsp;&nbsp;
							<button type="button"  class="xpbutton3" accessKey=d id="btnDelete" name="btnDelete" title="<%=LocalUtilis.language("message.delRecordsSelect",clientLocale)%> " onclick="javascript:removeInfo();"><%=LocalUtilis.language("message.delete",clientLocale)%> (<u>D</u>)</button>
							<!--删除所选记录--><!--删除-->
							&nbsp;&nbsp;&nbsp;
						 	<button type="button"  class="xpbutton3" accessKey=b id="backButton" name="backButton" onclick="javascript:window.location.href = '/base/product_list.jsp?menu_id=3010101&menu_info=产品查询&_=1527679028497';">返回 (<u>B</u>)</button>
						</td>				
					</tr>
				</table>
				<br/>
				<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%" >
					<tr class="trh">
						<td style="width:30mm;">
							<input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAll(document.theform.serial_no,this);">
							<%=LocalUtilis.language("class.formatDateLine",clientLocale)%></td>
						<td align="center" height="25"> <%=LocalUtilis.language("class.content2",clientLocale)%></td>
						<td width="30px"><%=LocalUtilis.language("message.edit",clientLocale)%></td>
					</tr> 						
					</tr>
<%
int iCurrent = 0;
int iCount=0;
List rsList = pageList.getRsList();
Map rowMap = null;
for(int i=0;i<rsList.size();i++)
{ 
	rowMap = (Map)rsList.get(i);
	Integer serial_no2 = Utility.parseInt(Utility.trimNull(rowMap.get("SERIAL_NO")),new Integer(0));

%>
            <tr class="tr<%=(iCurrent % 2)%>">
				<td align="center">	
					<table border="0" width="100%" cellspacing="0" cellpadding="0">
						<tr>
							<td width="10%"><input type="checkbox" name="serial_no" value="<%=serial_no2%>" class="flatcheckbox"></td>
							<td width="90%" align="left">&nbsp;&nbsp;<%=Utility.trimNull(rowMap.get("TASK_DATE"))%></td>
						</tr>
					</table>
				</td>
				<td align="center"><%=Utility.trimNull(rowMap.get("SUMMARY"))%></td>
				<td align="center">
					<a href="#" onclick="javascript:showInfo('<%=serial_no2%>')">
						<img src="<%=request.getContextPath()%>/images/edit2.gif" width="16" height="16" title="<%=LocalUtilis.language("message.edit",clientLocale)%> " /><!--编辑-->
					</a>
				</td>
			</tr>
<%
			iCurrent++;
			iCount++;
	
}

for (; iCurrent < pageList.getBlankSize(); iCurrent++){
%>
			<tr class="tr<%=(iCurrent % 2)%>">
				<td class="tdh" align="center" ></td>
				<td align="center" ></td>
				<td align="center" ></td>
			</tr>
<%
}
%>
			<tr class="trbottom">
				<td class="tdh" align="left" colspan="4">
					<b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%><!--合计-->&nbsp;
					<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> <!--项--></b>
				</td>
			</tr>
		</table>

		<br>

		<table border="0" width="100%" class="page-link">
			<tr valign="top">
				<td><%=pageList.getPageLink(sUrl,clientLocale)%></td>
					<td align="right"></td>
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
<%product.remove();
%>
