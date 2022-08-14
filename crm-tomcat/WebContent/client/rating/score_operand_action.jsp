<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.customer.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//获得页面传递变量
Integer operand_id = Utility.parseInt(Utility.trimNull(request.getParameter("operand_id")),new Integer(0));
Integer subject_id = Utility.parseInt(Utility.trimNull(request.getParameter("subject_id")),new Integer(0));
String operand_no = Utility.trimNull(request.getParameter("operand_no"));
String operand_name = Utility.trimNull(request.getParameter("operand_name"));
Integer scoring = Utility.parseInt(request.getParameter("scoring"),new Integer(0));
Integer source = Utility.parseInt(request.getParameter("source"),new Integer(0));
String summary = Utility.trimNull(request.getParameter("summary"));
Integer input_man = Utility.parseInt(Utility.trimNull(input_operatorCode),new Integer(0));

Map map = null;
List rsList = null;

//获得对象及结果集
RatingVO vo = new RatingVO();
ScoreOperandLocal score_operand = EJBFactory.getScoreOperand();

boolean bSuccess = false;

//保存信息
if(request.getMethod().equals("POST")){

	vo.setSubject_id(subject_id);
	vo.setOperand_no(operand_no);
	vo.setOperand_name(operand_name);
	vo.setScoring(scoring);
	vo.setSource(source);
	vo.setSummary(summary);
	vo.setInput_man(input_man);	

	if(operand_id.intValue()!= 0){
		vo.setOperand_id(operand_id);
		score_operand.modi_tscoreoperand(vo);
	}else{
		score_operand.append_tscoreoperand(vo);
	}
	bSuccess = true;
}
else {
	//信息显示
	if(operand_id.intValue()!= 0){
		vo.setOperand_id(operand_id);
		vo.setInput_man(input_man);
		List list = score_operand.list_tscoreoperand(vo);
		map = (Map)list.get(0); 
	}
	
	if(map != null){
		operand_id = Utility.parseInt(Utility.trimNull(map.get("OPERAND_ID")),new Integer(0));
		operand_no = Utility.trimNull(map.get("OPERAND_NO"));
		operand_name = Utility.trimNull(map.get("OPERAND_NAME"));
		subject_id = Utility.parseInt(Utility.trimNull(map.get("SUBJECT_ID")),new Integer(0));
		scoring = Utility.parseInt(Utility.trimNull(map.get("SCORING")),new Integer(0));
		source = Utility.parseInt(Utility.trimNull(map.get("SOURCE")),new Integer(0));
		summary = Utility.trimNull(map.get("SUMMARY"));
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
	if(form.subject_id.value == 0){
			sl_alert("<%=LocalUtilis.language("message.select2",clientLocale)%><%=LocalUtilis.language("class.ratingSubjectName",clientLocale)%>！");//请选择评级科目
			return false;
	}
	if(form.operand_no.value == ""){
			sl_alert("<%=LocalUtilis.language("class.pleaseFillIn",clientLocale)%><%=LocalUtilis.language("class.operandNumber",clientLocale)%>！");//请填写操作数编号
			return false;
	}
	if(form.operand_name.value == ""){
			sl_alert("<%=LocalUtilis.language("class.pleaseFillIn",clientLocale)%><%=LocalUtilis.language("class.operandName",clientLocale)%>！");//请填写操作数名称
			return false;
	}	

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

/*系统打分时人工打分来源不可用*/
function showSource(){

	var radio1=document.getElementsByName("scoring");
	var radio2=document.getElementsByName("source");
	for(var i=0;i<radio1.length;i++)
	{
		if(radio1[i].checked==true)
		{
			if(radio1[i].value == "1")
				document.getElementById("source_v").style.display = "";
			else
				document.getElementById("source_v").style.display = "none";
		}
	}
	if(document.getElementById("source_v").style.display == "none"){
		for(var i=0;i<radio2.length;i++)
		{
			radio2[i].value = "";
		}
	}
}
</script>
</HEAD>

<BODY class="BODY" style="overflow-x: hidden">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="post" action="score_operand_action.jsp" onsubmit="javascript:return validateForm(this);">
<!--新增成功标志-->
<input type="hidden" id="bSuccess" value="<%=bSuccess%>"/>
<input type="hidden" name="operand_id" value="<%=operand_id%>">
<div align="left">
	<img border="0" src="<%=request.getContextPath()%>/images/ico_area.gif" width="32" height="28">
	<font color="#215dc6"><b><%=menu_info%></b></font>
	<hr noshade color="#808080" size="1">
</div>
<div align="left"  style="margin-left:20px">
<table  border="0" width="100%" cellspacing="2" cellpadding="3" style="margin-top:5px;">
	<tr>
		<td align="right">*<%=LocalUtilis.language("class.subjectsName",clientLocale)%>:</td><!--科目名称-->
		<td align="left" colspan="3">
			<select name="subject_id" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 160px">
			<%=Argument.getScoreSubjectOptions(subject_id)%>
			</select>
		</td>
	</tr>
	<tr>
		<td align="right">*<%=LocalUtilis.language("class.operandNumber",clientLocale)%> : </td><!--操作数编号-->
		<td align="left">
			<input type="text" name="operand_no" value="<%=operand_no %>" onkeydown="javascript:nextKeyPress(this)" size="30"> 
		</td>
		<td align="right">*<%=LocalUtilis.language("class.operandName",clientLocale)%> : </td><!--操作数名称-->
		<td align="left" >
			<input type="text" name="operand_name" value='<%=operand_name%>' onkeydown="javascript:nextKeyPress(this)" size="30">
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.scoringPeople",clientLocale)%> : </td><!--打分人-->
		<td align="left">
			<input type = "radio" value = "1"  name = "scoring" onclick="javascript:showSource();"
			<%if(scoring.intValue() == 1) {out.print("checked");}%> /><%=LocalUtilis.language("class.aritificial",clientLocale)%><input type = "radio" value = "2"  name = "scoring" onclick="javascript:showSource();"
			<%if(scoring.intValue() == 2) {out.print("checked");}%> /><%=LocalUtilis.language("class.system",clientLocale)%>
		</td>
		<td align="center" colspan="2"><span id="source_v" <%if(scoring.intValue()!=1) out.print("style='display:none'");%>>
			<%=LocalUtilis.language("class.atrificialOpposesSource",clientLocale)%>: <!--人工打分来源-->
			<!-- 手工打分 --><input type = "radio" value = "1"  name = "source" 
			<%if(source.intValue() == 1) {out.print("checked");}%> /><%=LocalUtilis.language("class.manualScoring",clientLocale)%><input type = "radio" value = "2"  name = "source" 
			<%if(source.intValue() == 2) {out.print("checked");}%> /><%=LocalUtilis.language("class.calculationScoring",clientLocale)%><!-- 通过计算打分 -->
			</span>
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.memberDescription",clientLocale)%> : </td><!--描述-->
		<td align="left" colspan="3"><textarea  rows="20" name="summary" cols="90"><%=summary %></textarea></td>
	</tr>
</table>
</div>
<div align="right">
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
score_operand.remove();
%>
