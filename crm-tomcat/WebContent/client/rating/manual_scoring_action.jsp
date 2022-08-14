<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.customer.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//获得页面传递变量
Integer manual_id = Utility.parseInt(Utility.trimNull(request.getParameter("manual_id")),new Integer(0));
Integer operand_id = Utility.parseInt(Utility.trimNull(request.getParameter("operand_id")),new Integer(0));
String operation_value = Utility.trimNull(request.getParameter("operation_value"));
Integer score = Utility.parseInt(Utility.trimNull(request.getParameter("score")),new Integer(0));
Integer input_man = Utility.parseInt(Utility.trimNull(input_operatorCode),new Integer(0));

Map map = null;
List rsList = null;

//获得对象及结果集
RatingVO vo = new RatingVO();
ManualScoringLocal manual_scoring = EJBFactory.getManualScoring();

boolean bSuccess = false;

//保存信息
if(request.getMethod().equals("POST")){

	vo.setOperand_id(operand_id);
	vo.setOperation_value(operation_value);
	vo.setScore(score);
	vo.setInput_man(input_man);	

	if(manual_id.intValue()!= 0){
		vo.setManual_id(manual_id);
		manual_scoring.modi_tmanualscoring(vo);
	}else{
		manual_scoring.append_tmanualscoring(vo);
	}
	bSuccess = true;
}
else {
	//信息显示
	if(manual_id.intValue()!= 0){
		vo.setManual_id(manual_id);
		vo.setInput_man(input_man);
		List list = manual_scoring.list_tmanualscoring(vo);
		map = (Map)list.get(0); 
	}
	
	if(map != null){
		manual_id = Utility.parseInt(Utility.trimNull(map.get("MANUAL_ID")),new Integer(0));
		operand_id = Utility.parseInt(Utility.trimNull(map.get("OPERAND_ID")),new Integer(0));
		operation_value = Utility.trimNull(map.get("OPERATION_VALUE"));
		score = Utility.parseInt(Utility.trimNull(map.get("SCORE")),new Integer(0));
	}
}
%>

<HTML>
<HEAD>
<TITLE></TITLE><!--新增评分科目-->
<base target="_self">
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<script language="javascript" src="<%=request.getContextPath()%>/includes/jQuery/js/jquery-1.3.2.min.js"></SCRIPT>
<script language="javascript">	
/*保存*/
function SaveAction(){		
	if(document.theform.onsubmit()){
		disableAllBtn(true);
		document.theform.submit();
	}
}

function validateForm(form){
	if(form.operand_id.value == 0){
			sl_alert("<%=LocalUtilis.language("message.select2",clientLocale)%><%=LocalUtilis.language("class.scoringID",clientLocale)%>！");//请选择打分项
			return false;
	}
	if(form.operation_value.value == ""){
			sl_alert("<%=LocalUtilis.language("class.pleaseOptionValue",clientLocale)%>！");//请添写选项值
			return false;
	}
	if((form.score.value!="")&&(!sl_checkNum(form.score, '<%=LocalUtilis.language("class.score",clientLocale)%>', 20, 1)))	return false;	//分值

	return sl_check_update();
}

/*初始化*/
window.onload = function(){
	var v_bSuccess = document.getElementById("bSuccess").value;
	
	if(v_bSuccess=="true"){	
		sl_update_ok();	
		window.returnValue=1;	
		window.close();
	}
}

/*取消*/
function CancelAction(){
	window.close();
}

</script>
</HEAD>

<BODY class="BODY" style="overflow-x: hidden">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="post" action="manual_scoring_action.jsp" onsubmit="javascript:return validateForm(this);">
<!--新增成功标志-->
<input type="hidden" id="bSuccess" value="<%=bSuccess%>"/>
<input type="hidden" name="manual_id" value="<%=manual_id%>">
<div align="left">
	<img border="0" src="<%=request.getContextPath()%>/images/ico_area.gif" width="32" height="28">
	<font color="#215dc6"><b><%=menu_info%></b></font>
	<hr noshade color="#808080" size="1">
</div>
<div align="left"  style="margin-left:20px">
<table  border="0" width="100%" cellspacing="0" cellpadding="3" style="margin-top:5px;">
	<tr>
		<td align="right">*<%=LocalUtilis.language("class.operandName",clientLocale)%>:</td><!--操作数-->
		<td align="left">
			<select name="operand_id" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 170px">
			<%=Argument.getOperandOptions(operand_id,new Integer(1),new Integer(1))%>
			</select>
		</td>
	</tr>
	<tr>
		<td align="right">*<%=LocalUtilis.language("class.topicValue2",clientLocale)%>:</td><!--选项值-->
		<td align="left">
			<input type="text" name="operation_value" value="<%=operation_value %>" onkeydown="javascript:nextKeyPress(this)" size="30"> 
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.score",clientLocale)%> : </td><!--分值-->
		<td align="left" >
			<input type="text" name="score" value='<%=score%>' onkeydown="javascript:nextKeyPress(this)" size="30">
		</td>
	</tr>
</table>
</div>
<div align="center">
	<br>
	<!--保存-->
    <button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:SaveAction();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;
	<!--返回-->
    <button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:CancelAction();"><%=LocalUtilis.language("message.close",clientLocale)%>(<u>C</u>)</button>
</div>
</form>
<p>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>
<%
manual_scoring.remove();
%>
