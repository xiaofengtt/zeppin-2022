<%@ page contentType="text/html; charset=GBK" import="enfo.crm.system.*,enfo.crm.dao.*,enfo.crm.tools.*,enfo.crm.vo.*,java.util.*, enfo.crm.intrust.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>

<%
boolean bSuccess = false;
Integer serial_no  = new Integer(Utility.parseInt(request.getParameter("serial_no"), 0));

Integer product_id  = new Integer(Utility.parseInt(request.getParameter("product_id"), 0));
Integer trade_date = Utility.parseInt(request.getParameter("trade_date"),new Integer(0));
String summary = Utility.trimNull(request.getParameter("summary"));

int editflag = Utility.parseInt(request.getParameter("editflag"), 0); 

ProductLocal product = EJBFactory.getProduct();
ProductVO vo = new ProductVO();



if(request.getMethod().equals("POST"))
{
	vo.setProduct_id(product_id);
	vo.setTask_type(new Integer(103));
	vo.setTask_name("信息披露");
	vo.setTrade_date(trade_date);
	vo.setStatus(new Integer(1));
	vo.setInput_man(input_operatorCode);
	vo.setSummary(summary);
	
	if (editflag == 1){
	    vo.setSerial_no(serial_no);
		product.modiTask(vo);
	}		
	else{
		product.appendTask(vo);
	}
		
	bSuccess = true;
}else{
	if (serial_no.intValue() > 0){	
		editflag = 1;
		vo.setSerial_no(serial_no);
		Map rowMap=(Map)product.listTask(vo).get(0);
		trade_date = Utility.parseInt(Utility.trimNull(rowMap.get("TASK_DATE")),new Integer(0));
		summary = Utility.trimNull(rowMap.get("SUMMARY"));
	}
}

%>

<HTML>
<HEAD>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<base target="_self">
<title><%=LocalUtilis.language("menu.backupNew",clientLocale)%> </title>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<script language=javascript>
<%
if (bSuccess){
%>
	window.returnValue = true;
	window.close();
<%}%>

function validateForm(form)
{
	syncDatePicker(document.theform.trade_date_picker, document.theform.trade_date);
	if(!sl_check(form.summary, "<%=LocalUtilis.language("class.content2",clientLocale)%> ", 500, 1))	return false;
	return sl_check_update();
}
</script>
</HEAD>

<BODY class="BODY" leftMargin=0 topMargin=0 rightmargin="0" bottommargin="0" onkeydown="javascript:chachEsc(window.event.keyCode)">

<form name="theform" method="post" action="product_task_info.jsp" onsubmit="javascript:return validateForm(this);">
<input type=hidden name="serial_no" value="<%=serial_no%>">
<input type=hidden name="product_id" value="<%=product_id%>">
<input type=hidden name="editflag" value="<%=editflag%>">

<TABLE height="100%" cellSpacing=0 cellPadding=0 border=0 width="100%">
	<TBODY>
		<TR>
		<TD vAlign=top align=center>
			<TABLE cellSpacing=0 cellPadding=4 align=center border=0 width="100%">
				<TBODY>
					<TR>
						<TD>
						<table border="0" width="100%" cellspacing="0" cellpadding="0">
							<tr>
								<td class="page-title">
								<font color="#215dc6"><b><%=LocalUtilis.language("message.formatDateLineInfo",clientLocale)%> </b></font></td> 
							</tr>
						</table>
						<br/>
						<table border="0" width="100%" cellspacing="3">
							
							<tr>
							 	<td align="right" valign="top"><b><%=LocalUtilis.language("message.date",clientLocale)%>: </b></td><!--披露日期-->
								<td>
									<input TYPE="text" style="width:120" NAME="trade_date_picker" class=selecttext value="<%=Format.formatDateLine(trade_date)%>" onKeyDown="javascript:nextKeyPress(this)"> 
							        <input TYPE="button" value="▼" class=selectbtn onClick="javascript:CalendarWebControl.show(theform.trade_date_picker,theform.trade_date_picker.value,this);" onKeyDown="javascript:nextKeyPress(this)"> 
							        <input TYPE="hidden" NAME="trade_date" value="">
								</td>
							</tr>
							<tr>
								<td align="right" valign="top"><b><%=LocalUtilis.language("class.content2",clientLocale)%>：</b></td><!--披露信息内容-->
								<td><textarea  rows="25" name="summary" cols="130"><%=Utility.trimNull(summary) %></textarea></td>
							</tr>
						</table>
						<table border="0" width="100%">
							<tr>
								<td align="right">
								<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" 
										onclick="javascript: if(document.theform.onsubmit()){ disableAllBtn(true);document.theform.submit(); } ">
									<%=LocalUtilis.language("message.save",clientLocale)%> <!--保存-->(<u>S</u>)</button>
								&nbsp;&nbsp;
								<button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.returnValue=null;window.close();">
								    <%=LocalUtilis.language("message.cancel",clientLocale)%> <!--取消-->(<u>C</u>)
								</button>
								&nbsp;&nbsp;
								</td>
							</tr>
						</table>
						</TD>
					</TR>
				</TBODY>
			</TABLE>
		</TD>
		</TR>
	</TBODY>
</TABLE>
</form>
</BODY>

</HTML>
<%product.remove();%>
