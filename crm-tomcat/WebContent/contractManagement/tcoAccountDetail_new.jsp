<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.system.*,enfo.crm.dao.*,java.util.*,enfo.crm.cont.*,java.math.BigDecimal,enfo.crm.contractManage.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
	boolean bSuccess=false;
	String payPlan_id=request.getParameter("payPlan_id");
	TcoContractMoneyDetailLocal tcoContractMoneyDetailLocal=EJBFactory.getTcoContractMoneyDetail();
	TcoContractMoneyDetailVO tcoContractMoneyDetailVO=new TcoContractMoneyDetailVO();
	if(request.getMethod().equals("POST")){
		String arrive_time=request.getParameter("arrive_time");
		String arrive_money=request.getParameter("arrive_money");
		String arrive_summary=request.getParameter("arrive_summary");
		String flag=request.getParameter("isAllArriveFlag");
		Integer isAllArriveFlag=Utility.parseInt(flag,new Integer(0));
		tcoContractMoneyDetailVO.setPayPlan_id(Utility.parseInt(payPlan_id,new Integer(0))); 
		tcoContractMoneyDetailVO.setArrive_time(Utility.parseInt(arrive_time,new Integer(0)));
		tcoContractMoneyDetailVO.setArrive_money(Utility.parseDecimal(arrive_money,new BigDecimal(0.00)));
		tcoContractMoneyDetailVO.setArrive_summary(arrive_summary);
		tcoContractMoneyDetailVO.setInput_man(input_operatorCode);
		tcoContractMoneyDetailVO.setIsAllArriveFlag(isAllArriveFlag);
		tcoContractMoneyDetailLocal.append(tcoContractMoneyDetailVO);
		bSuccess=true;
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
/*验证数据*/
function validateForm(form){		
	return sl_check_update();	
}
function saveAction()
{
	if(!sl_checkDate(document.theform.arrive_time_picker,"到账时间"))return false;//到账时间
	syncDatePicker(document.theform.arrive_time_picker, document.theform.arrive_time);	
	if(!sl_checkDecimal(theform.arrive_money, "", 13, 3, 1)){return false;}//到账金额
	document.theform.submit();
}
/*取消*/
function CancelAction(){
	window.returnValue=null;
	window.close();
}
</script>
</head>
<BODY class="BODY">
<form name="theform" method="post"  action="tcoAccountDetail_new.jsp" onsubmit="javascript:return validateForm(this);">
<input type="hidden" name="payPlan_id" value="<%=payPlan_id%>">
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
		<td  align="right">到账时间: </td>
		<td  align="left">
			<INPUT TYPE="text" NAME="arrive_time_picker" class=selecttext value="" >
			<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.arrive_time_picker,theform.arrive_time_picker.value,this);" tabindex="15">
			<INPUT TYPE="hidden" NAME="arrive_time"   value="">
		</td>
	</tr>
	<tr>
		<td align="right">到账金额:</td>
		<td>
			<INPUT TYPE="text" NAME="arrive_money" size="25" class=selecttext value="" >
		</td>
	</tr>
	<tr>
		<td align="right">备注:</td>
		<td>
			<textarea name="arrive_summary" cols="25"></textarea>
		</td>
	</tr>
	<tr>
		<td align="right">
			<input type="checkbox" name="isAllArriveFlag" value="1" class="flatcheckbox">
		</td>
		<td>全部到账</td>
	</tr>
</table>
<table border="0" width="100%">
	<tr>
		<td align="right">
		<button type="button" class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:saveAction();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
		&nbsp;&nbsp;<!--保存-->
		<button type="button" class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:CancelAction();"><%=LocalUtilis.language("message.cancel",clientLocale)%> (<u>C</u>)</button>
		&nbsp;&nbsp;<!--取消-->
		</td>
	</tr>
</table>
</form>
</body>
</html>
