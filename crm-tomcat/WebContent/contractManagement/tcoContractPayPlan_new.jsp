<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.system.*,enfo.crm.dao.*,java.util.*,enfo.crm.cont.*,java.math.BigDecimal" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>

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
	if(!sl_checkDate(document.theform.exp_date_picker,"Ԥ�Ƹ�������"))return false;//Ԥ�Ƹ�������
	syncDatePicker(document.theform.exp_date_picker, document.theform.exp_date);	
	if(!sl_checkDecimal(theform.pay_money, "", 13, 3, 1)){return false;}//��ͬ�ܽ��
	var v = new Array();
	v[0] = document.theform.pay_num.value;
	v[1] = document.theform.pay_num_name.value;
	v[2] = document.theform.exp_date.value;
	v[3] = document.theform.pay_rate.value;
	v[4] = document.theform.pay_money.value;
	v[5] = document.theform.pay_summary.value;
	//if confirm("ȷ��Ҫ������?"){
		window.returnValue = v;
		//sl_alert("����ɹ�!");
		window.close();
	//}
}
/*��ʾ����Ǯ��*/
function showCnMoney(value){
	temp = value;
	if (trim(value) == "")
		pay_money_cn.innerText = "(Ԫ)";
	else
		pay_money_cn.innerText = "(" + numToChinese(temp) + ")";
}
</script>
</head>
<BODY class="BODY" onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform" method="post"  onsubmit="javascript:return validateForm(this);">
<table border="0" width="100%" cellspacing="0" cellpadding="0">
	<tr>
		<td><img border="0" src="<%=request.getContextPath()%>/images/ico_area.gif" align="absbottom" width="32" height="28"><font color="#215dc6"><b>����ƻ�</b></font></td>
	</tr>
	<tr> 
		<td>
		<hr noshade color="#808080" size="1">
		</td>
	</tr>
</table>
<table border="0" width="100%" cellspacing="3">
	<tr>
		<td  align="right">����: </td>
		<td  align="left"><input type="text" name="pay_num" size="25" value="" onkeyup="javascript:sl_checkDecimal(form.pay_num,'����',13,3,0);"></td>
	</tr>
	<tr>
		<td align="right">����˵��:</td>
		<td>
			<INPUT TYPE="text" NAME="pay_num_name" size="25" class=selecttext value="" >
		</td>
	</tr>
	<tr>
		<td align="right">Ԥ�Ƹ�������: </td>
		<td>
			<INPUT TYPE="text" NAME="exp_date_picker" class=selecttext value="" >
			<INPUT TYPE="button" value="��" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.exp_date_picker,theform.exp_date_picker.value,this);" tabindex="15">
			<INPUT TYPE="hidden" NAME="exp_date"   value="">
		</td>
	</tr>
	<tr>
		<td align="right">�������:</td>
		<td>
			<INPUT TYPE="text" NAME="pay_rate" size="25"  value="" >&nbsp;&nbsp;%
		</td>
	</tr>
	<tr>
		<td align="right">������:</td>
		<td>
			<INPUT TYPE="text" NAME="pay_money" size="25"  value="" onkeyup="javascript:sl_checkDecimal(form.pay_money,'��ͬ���',13,3,0);showCnMoney(this.value)">
			<span id="pay_money_cn" class="span">&nbsp;(Ԫ)</span>
		</td>
	</tr>
	<tr>
		<td align="right">��������˵��: </td>
		<td align="left" ><textarea cols="25" rows="5" name="pay_summary"></textarea></td>
	</tr>
</table>
<table border="0" width="100%">
	<tr>
		<td align="right">
		<button type="button" class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:if(document.theform.onsubmit()) document.theform.submit();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
		&nbsp;&nbsp;<!--����-->
		<button type="button" class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.returnValue=null;window.close();"><%=LocalUtilis.language("message.cancel",clientLocale)%> (<u>C</u>)</button>
		&nbsp;&nbsp;<!--ȡ��-->
		</td>
	</tr>
</table>
</form>
</body>
</html>
