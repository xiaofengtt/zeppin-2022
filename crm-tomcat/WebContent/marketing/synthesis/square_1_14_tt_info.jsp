<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.web.*,java.io.*"%>
<%@ page import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.marketing.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%
//ProductLocal product = EJBFactory.getProduct();
Utility.debug(request.getParameter("print_param"));
String print_param = Utility.trimNull(request.getParameter("print_param")," $ $ $ $ $ $ $ $ ");

String[] param = Utility.splitString(print_param,"$");
String print_date = String.valueOf(Utility.parseInt(param[0],new Integer(Utility.getCurrentDate())));
String hk_cust_name = Utility.removeSpaces(param[1]);
String sk_cust_name = Utility.removeSpaces(param[2]);
String hk_bank_acct = Utility.removeSpaces(param[3]);
String sk_bank_acct = Utility.removeSpaces(param[4]);
String hk_bank_name = Utility.removeSpaces(param[5]);
String sk_bank_name = Utility.removeSpaces(param[6]);
BigDecimal money = Utility.parseDecimal(param[7],new BigDecimal(0));
StringBuffer print_money= new StringBuffer("￥"+Format.formatMoneyPrint(money.doubleValue(),2));
char[] s_money = print_money.reverse().toString().toCharArray();
//String remark = Utility.removeSpaces(param[9]);


%>

<HTML>
<HEAD>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="/includes/default.css" type=text/css rel=stylesheet>

<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<base target="_self">
<title>信托业务电汇凭证打印编辑</title>
<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/calendar.js"></SCRIPT>
<script language=javascript>
function printInfo()
{
	syncDatePicker(document.theform.print_date_picker,document.theform.print_date);
	if(!sl_checkDate(document.theform.print_date_picker,"打印日期"))	return false;
	var print_date = document.theform.print_date.value;
	var hk_cust_name = document.theform.hk_cust_name.value;
	var sk_cust_name = document.theform.sk_cust_name.value;
	var hk_bank_acct = document.theform.hk_bank_acct.value;
	var sk_bank_acct = document.theform.sk_bank_acct.value;
	var hk_bank_name = document.theform.hk_bank_name.value;
	var sk_bank_name = document.theform.sk_bank_name.value;
	if(!sl_checkDecimal(document.theform.money, "付款金额", 13, 3, 1))return false;
	var money = document.theform.money.value;
	window.returnValue = print_date+"$"+hk_cust_name+" $"+sk_cust_name+" $"+hk_bank_acct+" $"+sk_bank_acct
								   +" $"+hk_bank_name+"$"+sk_bank_name+" $"+money+"$";
	window.close();
}
</script>
</HEAD>

<BODY leftMargin=0 topMargin=0 rightmargin="0" bottommargin="0">
<form name="theform" method="post" action="bussiness_notice_receive_info.jsp">

<input type="hidden" name="product_id" value="<%=overall_product_id%>">

<TABLE cellSpacing=0 cellPadding=0 border=0 width="100%">
	<TBODY>
		<TR>
			<TD vAlign=top align=center>
			<TABLE cellSpacing=0 cellPadding=4 align=center border=0 width="100%">
				<TBODY>
					<TR>
						<TD align=center >
						<table border="0" width="96%"   cellspacing="0" cellpadding="0" height="74" >
							<tr>
								<td><img border="0" src="/images/member.gif" align="absbottom" ><b><font color="#215dc6">信托业务电汇凭证</font></b></td>
							</tr>
							<tr>
								<td width="100%">
								<hr noshade color="#808080" size="1">
								</td>
							</tr>
						</table>
						<table border="0" width="100%" cellspacing="3" cellpadding="4">
							<tr>	
								<td align="right">打印日期:</td> 
								<td colspan="3">
									<input type="text" name="print_date_picker"   class=selecttext value="<%=Format.formatDateLine(Utility.getCurrentDate())%>">
									<input type="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.print_date_picker,theform.print_date_picker.value,this);">
									<input type="hidden" name="print_date" value="">
								</td>
							</tr>
							<tr>
								<td align="right">汇款人全称:</td> 
								<td><input name="hk_cust_name"  value="<%=hk_cust_name%>"  size="50" ></td>
								<td align="right">收款人全称:</td> 
								<td><input name="sk_cust_name"  value="<%=sk_cust_name%>" size="30" ></td>
							</tr>
							<tr>
								<td align="right">汇款人帐号:</td> 
								<td><input name="hk_bank_acct" value="<%=hk_bank_acct%>" size="30"></td>
								<td align="right">收款人帐号:</td> 
								<td><input name="sk_bank_acct" value="<%=sk_bank_acct%>" size="30" ></td>
							</tr>
							<tr>

							<tr>
								<td align="right">汇入行名称:</td> 
								<td><input name="hk_bank_name" value="<%=hk_bank_name%>" size="30"></td>
								<td align="right">汇出行名称:</td> 
								<td><input name="sk_bank_name" value="<%=sk_bank_name %>" size="30"></td>
							</tr>
							<tr>
								<td align="right">付款金额:</td>
								<td colspan="3"><input onkeydown="javascript:nextKeyPress(this)" size="40" name="money" value="<%=Format.formatMoney(money)%>" onkeyup="javascript:showCnMoney(this.value,money_cn)">
								<span id="money_cn" class="span"></span></td>
							</tr>
						</table>
						<table border="0" width="100%" align="center">
							<tr>
								<td align="right">
								<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:printInfo();">保存打印</button>
								&nbsp;&nbsp;
								<button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.returnValue=null;window.close();">取消(<u>C</u>)</button>
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
<%//product.remove();%>