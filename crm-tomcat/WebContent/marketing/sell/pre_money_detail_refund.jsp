<%@ page contentType="text/html; charset=GBK" import="enfo.crm.marketing.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
PreMoneyDetailLocal local = EJBFactory.getPreMoneyDetail();
PreMoneyDetailVO vo = new PreMoneyDetailVO();

Integer serial_no = Utility.parseInt(request.getParameter("serial_no"),new Integer(0));
String cust_name = Utility.trimNull(request.getParameter("cust_name"));
String product_name = Utility.trimNull(request.getParameter("product_name"));
BigDecimal pre_money = Utility.parseDecimal(Utility.trimNull(request.getParameter("pre_money")),new BigDecimal(0));
BigDecimal rg_money = Utility.parseDecimal(Utility.trimNull(request.getParameter("rg_money")),new BigDecimal(0));
Integer refund_date = Utility.parseInt(request.getParameter("refund_date"),new Integer(0));
BigDecimal refund_money = Utility.parseDecimal(Utility.trimNull(request.getParameter("refund_money")),new BigDecimal(0));
String jk_type = Utility.trimNull(request.getParameter("jk_type"));
boolean bSuccess = false;
List list = new ArrayList();
Map map = new HashMap();

vo.setSerial_no(serial_no);
if(request.getMethod().equals("POST")){
	vo.setRefund_date(refund_date);
	vo.setRefund_money(refund_money);
	vo.setInput_man(input_operatorCode);
	local.refundPreMoneyDetail(vo);
	bSuccess = true;
}else{
	if(serial_no.intValue() != 0){
		list = local.queryPreMoneyDetail(vo);
			if(list != null && list.size() != 0)
				map = (Map)list.get(0);
	}
}
%>
<html>
<head>
<title>到账处理</title>
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
<script language=javascript>
<%
if (bSuccess){
%>
	window.returnValue = 1;
	window.close();
<%}%>

function validateForm(form)
{
	if(document.theform.refund_date_picker.value!=""){
		syncDatePicker(form.refund_date_picker, form.refund_date);
		if(!sl_checkDate(document.theform.refund_date_picker,"退款日期")){
			return false;
		}
	}
	if(!sl_checkDecimal(document.theform.refund_money, "退款金额", 13, 3, 1))	return false;
	if(document.theform.refund_money.value <= 0){
		sl_alert("退款金额不能小于等于零！");
		return false;
	}
	if(parseFloat(form.refund_money.value) > parseFloat(form.dz_money.value)){
		sl_alert("本次退款金额不能大于到账金额？");
		return false;
	}
	return sl_check_update();
}
</script>
</head>
<BODY class="BODY body-nox" onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform" method="post" action="#" onsubmit="javascript:return validateForm(this);">
<input type=hidden name="serial_no" value="<%=serial_no%>">
<input type=hidden name="cust_name" value="<%=cust_name%>">
<input type=hidden name="product_name" value="<%=product_name%>">
<input type=hidden name="pre_money" value="<%=pre_money%>">
<input type=hidden name="rg_money" value="<%=rg_money%>">
<input type=hidden name="dz_money" value="<%=Utility.parseDecimal(Utility.trimNull(map.get("DZ_MONEY")), new BigDecimal(0.00),2,"1")%>">
<table border="0" width="100%" cellspacing="0" cellpadding="0">
	<tr>
		<td class="page-title"><font color="#215dc6"><b>到账处理</b></font></td>
	</tr>
</table>
<br/>
<table border="0" width="100%" cellspacing="3" class="product-list">
	<tr>
		<td align="right">产品名称:</td>
		<td align="left">
			<input type="text" readonly="readonly" class="edline" size="30" value="<%=Utility.trimNull(product_name) %>">
		</td>
	</tr>
	<tr>
		<td align="right">客户名称:</td>
		<td align="left">
			<input type="text" readonly="readonly" class="edline" size="30" value="<%=Utility.trimNull(cust_name) %>">
		</td>
	</tr>
	<tr>
		<td align="right">预约金额:</td>
		<td align="left">
			<input type="text" readonly="readonly" class="edline" size="30" value="<%=Utility.trimNull(Format.formatMoney(pre_money)) %>">
		</td>
	</tr>
	<tr>
		<td align="right">已到账金额:</td>
		<td align="left">
			<input type="text" readonly="readonly" class="edline" size="30" value="<%=Utility.trimNull(Format.formatMoney(rg_money)) %>">
		</td>
	</tr>
	<tr>
		<td align="right">到账日期:</td>
		<td align="left">
			<input type="text" readonly="readonly" class="edline" size="30" value="<%=Format.formatDateLine(Utility.parseInt(Utility.trimNull(map.get("DZ_DATE")),new Integer(0))) %>">
		</td>
	</tr>
	<tr>
		<td align="right">到账金额:</td>
		<td align="left">
			<input type="text" readonly="readonly" class="edline" size="30" value="<%=Format.formatMoney(Utility.parseDecimal(Utility.trimNull(map.get("DZ_MONEY")), new BigDecimal(0.00),2,"1")) %>">
		</td>
	</tr>
	<tr>
		<td align="right">交款方式:</td>
		<td align="left">
			<input type="text" readonly="readonly" class="edline" size="30" value="<%=Utility.trimNull(map.get("JK_TYPE_NAME")) %>">
		</td>
	</tr>
	<tr>
		<td align="right"><font color="red">*</font>退款日期:</td>
		<td align="left">
			<INPUT TYPE="text" NAME="refund_date_picker"value="<%=Format.formatDateLine(Utility.parseInt(Utility.trimNull(map.get("REFUND_DATE")),new Integer(0)).intValue() == 0 ? Utility.getCurrentDate() : Utility.parseInt(Utility.trimNull(map.get("REFUND_DATE")),new Integer(0)).intValue())%>" size="25" onkeydown="javascript:nextKeyPress(this)">
			<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.refund_date_picker,theform.refund_date_picker.value,this);" tabindex="13">
			<INPUT TYPE="hidden" NAME="refund_date" value="">
		</td>
	</tr>
	<tr>
		<td align="right"><font color="red">*</font>退款金额:</td>
		<td align="left">
			<input type="text" name="refund_money" size="30" onkeydown="javascript:nextKeyPress(this)" onkeyup="javascript:showCnMoney(this.value,refund_money_cn)" value="<%=Format.formatMoney(Utility.parseDecimal(Utility.trimNull(map.get("REFUND_MONEY")), new BigDecimal(0.00),2,"1")) %>">
			<br><span id="refund_money_cn" class="span"></span>
		</td>
	</tr>
</table>
<table border="0" width="100%">
	<tr>
		<td align="center">
		<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:if(document.theform.onsubmit()) document.theform.submit();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
		&nbsp;&nbsp;<!--保存-->
		<button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.returnValue=null;window.close();"><%=LocalUtilis.language("message.cancel",clientLocale)%> (<u>C</u>)</button>
		&nbsp;&nbsp;<!--取消-->
		</td>
	</tr>
</table>
</form>
</body>
</html>
<%local.remove();%>