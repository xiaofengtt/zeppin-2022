<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.system.*,enfo.crm.dao.*,java.util.*,enfo.crm.cont.*,java.math.BigDecimal,enfo.crm.contractManage.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
boolean bSuccess = false;

Integer coContract_id=Utility.parseInt(request.getParameter("coContract_id"),new Integer(0));
if(request.getMethod().equals("POST")){
	TcoContractPayPlanLocal tcoContractPayPlanLocal = EJBFactory.getTcoContractPayPlan();
	TcoContractPayPlanVO tcoContractPayPlanVO = new TcoContractPayPlanVO();
	Integer pay_num=Utility.parseInt(request.getParameter("pay_num"),new Integer(0));
	String pay_num_name=request.getParameter("pay_num_name");
	Integer exp_date=Utility.parseInt(request.getParameter("exp_date"),new Integer(0));
	BigDecimal pay_money=Utility.parseDecimal(Utility.trimNull(request.getParameter("pay_money")),new BigDecimal(0.00));
	BigDecimal pay_rate=Utility.parseDecimal(Utility.trimNull(request.getParameter("pay_rate")),new BigDecimal(0.00)).divide(new BigDecimal(100), 4, BigDecimal.ROUND_UP);
	String pay_summary=request.getParameter("pay_summary");
	tcoContractPayPlanVO.setCocontract_id(coContract_id);
	tcoContractPayPlanVO.setPay_num(pay_num);
	tcoContractPayPlanVO.setPay_num_name(pay_num_name);
	tcoContractPayPlanVO.setExp_date(exp_date);
	tcoContractPayPlanVO.setPay_rate(pay_rate);
	tcoContractPayPlanVO.setPay_money(pay_money);
	tcoContractPayPlanVO.setPay_summary(pay_summary);
	tcoContractPayPlanVO.setInput_man(input_operatorCode);
	tcoContractPayPlanLocal.append(tcoContractPayPlanVO);
	tcoContractPayPlanLocal.remove();
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
	<% if(bSuccess){ %>
		window.returnValue = true;
		window.close();
	<%}%>
function validateForm(form)
{
	if(!sl_checkDate(document.theform.exp_date_picker,"预计付款日期"))return false;//预计付款日期
	syncDatePicker(document.theform.exp_date_picker, document.theform.exp_date);	
	if(!sl_checkDecimal(theform.pay_money, "", 13, 3, 1)){return false;}//合同总金额
	var v = new Array();
	v[0] = document.theform.pay_num.value;
	v[1] = document.theform.pay_num_name.value;
	v[2] = document.theform.exp_date.value;
	v[3] = document.theform.pay_rate.value;
	v[4] = document.theform.pay_money.value;
	v[5] = document.theform.pay_summary.value;
	//if confirm("确定要保存吗?"){
		window.returnValue = v;
		//sl_alert("保存成功!");
		window.close();
	//}
}
/*显示中文钱数*/
function showCnMoney(value){
	temp = value;
	if (trim(value) == "")
		pay_money_cn.innerText = "(元)";
	else
		pay_money_cn.innerText = "(" + numToChinese(temp) + ")";
}
function saveAction(){
	document.theform.action="tco_updatePayPlan_new.jsp";
	document.theform.submit();
}
</script>
</head>
<BODY class="BODY" onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform" method="post"  onsubmit="javascript:return validateForm(this);">
<input type="hidden" name="coContract_id" value="<%=coContract_id%>"/>
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
		<td  align="right">期数: </td>
		<td  align="left"><input type="text" name="pay_num" size="25" value="" onkeyup="javascript:sl_checkDecimal(form.pay_num,'期数',13,3,0);"></td>
	</tr>
	<tr>
		<td align="right">期数说明:</td>
		<td>
			<INPUT TYPE="text" NAME="pay_num_name" size="25" class=selecttext value="" >
		</td>
	</tr>
	<tr>
		<td align="right">预计付款日期: </td>
		<td>
			<INPUT TYPE="text" NAME="exp_date_picker" class=selecttext value="" >
			<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.exp_date_picker,theform.exp_date_picker.value,this);" tabindex="15">
			<INPUT TYPE="hidden" NAME="exp_date"   value="">
		</td>
	</tr>
	<tr>
		<td align="right">付款比例:</td>
		<td>
			<INPUT TYPE="text" NAME="pay_rate" size="25"  value="" >&nbsp;&nbsp;%
		</td>
	</tr>
	<tr>
		<td align="right">付款金额:</td>
		<td>
			<INPUT TYPE="text" NAME="pay_money" size="25"  value="" onkeyup="javascript:sl_checkDecimal(form.pay_money,'合同金额',13,3,0);showCnMoney(this.value)">
			<span id="pay_money_cn" class="span">&nbsp;(元)</span>
		</td>
	</tr>
	<tr>
		<td align="right">付款条件说明: </td>
		<td align="left" ><textarea cols="25" rows="5" name="pay_summary"></textarea></td>
	</tr>
</table>
<table border="0" width="100%">
	<tr>
		<td align="right">
		<button type="button" class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:saveAction();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
		&nbsp;&nbsp;<!--保存-->
		<button type="button" class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.returnValue=null;window.close();"><%=LocalUtilis.language("message.cancel",clientLocale)%> (<u>C</u>)</button>
		&nbsp;&nbsp;<!--取消-->
		</td>
	</tr>
</table>
</form>
</body>
</html>
