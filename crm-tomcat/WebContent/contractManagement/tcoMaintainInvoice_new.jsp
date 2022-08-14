<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.system.*,enfo.crm.dao.*,java.util.*,enfo.crm.cont.*,java.math.BigDecimal,enfo.crm.contractManage.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
TcoContractMaintainLocal tcoContractMaintainLocal = EJBFactory.getTcoContractMaintain();
TcoContractMaintainVO tcoContractMaintainVO = new TcoContractMaintainVO();
Integer maintain_id=Utility.parseInt(Utility.trimNull(request.getParameter("maintain_id")),new Integer(0));
boolean bSuccess = false;

if(request.getMethod().equals("POST")){
	
	Integer invoice_time=Utility.parseInt(Utility.trimNull(request.getParameter("invoice_time")),new Integer(0));
	BigDecimal invoice_money=Utility.parseDecimal(Utility.trimNull(request.getParameter("invoice_money")),new BigDecimal(0.00));
	String bank_id=Utility.trimNull(request.getParameter("bank_id"));
	String bank_acct=Utility.trimNull(request.getParameter("bank_acct"));
	String acct_name=Utility.trimNull(request.getParameter("acct_name"));
	String invoice_summary=Utility.trimNull(request.getParameter("invoice_summary"));
	tcoContractMaintainVO.setMaintain_id(maintain_id);
	tcoContractMaintainVO.setInvoice_time(invoice_time);
	tcoContractMaintainVO.setInvoice_money(invoice_money);
	tcoContractMaintainVO.setBank_id(bank_id);
	tcoContractMaintainVO.setBank_acct(bank_acct);
	tcoContractMaintainVO.setAcct_name(acct_name);
	tcoContractMaintainVO.setInput_man(input_operatorCode);
	tcoContractMaintainVO.setInvoice_summary(invoice_summary);
	tcoContractMaintainLocal.setInvoice(tcoContractMaintainVO);
	bSuccess = true;

}
%>
<html>
<head>
<title></title>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<base target="_self">


<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/investment.js"></SCRIPT>
<script language=javascript>

function validateForm(form)
{
	if(!sl_checkDate(document.theform.invoice_time_picker,"预计付款日期"))return false;//预计付款日期
	syncDatePicker(document.theform.invoice_time_picker, document.theform.invoice_time);	
	if(!sl_checkDecimal(theform.invoice_money, "", 13, 3, 1)){return false;}//合同总金额
	return true;
	
}
<%if (bSuccess){%>
	window.returnValue = 1;
	window.close();
<%}%>
</script>
</head>
<BODY class="BODY" onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform" method="post" action="tcoMaintainInvoice_new.jsp" onsubmit="javascript:return validateForm(this);">
<input type="hidden" name="maintain_id" value="<%=maintain_id%>"/>
<table border="0" width="100%" cellspacing="0" cellpadding="0">
	<tr>
		<td><img border="0" src="<%=request.getContextPath()%>/images/ico_area.gif" align="absbottom" width="32" height="28"><font color="#215dc6"><b>付款计划</b></font></td>
	</tr>
	<tr> 
		<td>
		<hr noshade color="#808080" size="1">
		</td>
	</tr>
</table>
<table border="0" width="100%" cellspacing="3">
	<tr>
		<td align="right">银行:</td>
		<td>
			<select name="bank_id" style="width: 145px;">
				<%=Argument.getDictParamOptions(1103,"") %>
			</select>
		</td>
	</tr>
	<tr>
		<td align="right">银行账号:</td>
		<td>
			<INPUT TYPE="text" NAME="bank_acct" size="25" class=selecttext value="" >
		</td>
	</tr>
	<tr>
		<td align="right">银行账号名称:</td>
		<td>
			<INPUT TYPE="text" NAME="acct_name" size="25" class=selecttext value="" >
		</td>
	</tr>
	<tr>
		<td  align="right">开票时间: </td>
		<td  align="left">
			<INPUT TYPE="text" NAME="invoice_time_picker" class=selecttext value="" >
			<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.invoice_time_picker,theform.invoice_time_picker.value,this);" tabindex="15">
			<INPUT TYPE="hidden" NAME="invoice_time"   value="">
		</td>
	</tr>
	<tr>
		<td align="right">开票金额:</td>
		<td>
			<INPUT TYPE="text" NAME="invoice_money" size="25" class=selecttext value="" >
		</td>
	</tr>
	<tr>
		<td align="right">备注:</td>
		<td>
			<TEXTAREA rows="5" cols="50" name="invoice_summary"></TEXTAREA>
		</td>
	</tr>
</table>
<table border="0" width="100%">
	<tr>
		<td align="right">
		<button type="button" class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:if(document.theform.onsubmit()) document.theform.submit();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
		&nbsp;&nbsp;<!--保存-->
		<button type="button" class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.returnValue=null;window.close();"><%=LocalUtilis.language("message.cancel",clientLocale)%> (<u>C</u>)</button>
		&nbsp;&nbsp;<!--取消-->
		</td>
	</tr>
</table>
</form>
</body>
</html>
<%tcoContractMaintainLocal.remove();%>