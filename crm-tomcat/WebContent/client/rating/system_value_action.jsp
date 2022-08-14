<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.customer.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//获得页面传递变量
Integer operand_v_id = Utility.parseInt(Utility.trimNull(request.getParameter("operand_v_id")),new Integer(0));
Integer operand_id = Utility.parseInt(Utility.trimNull(request.getParameter("operand_id")),new Integer(0));
String operand_xh = Utility.trimNull(request.getParameter("operand_xh"));
String source_table = Utility.trimNull(request.getParameter("source_table"));
String source_field = Utility.trimNull(request.getParameter("source_field"));
Integer include_top = Utility.parseInt(request.getParameter("include_top"),new Integer(1));
Integer include_end = Utility.parseInt(request.getParameter("include_end"),new Integer(2));
String top_threshold = Utility.trimNull(request.getParameter("top_threshold"));
String end_threshold = Utility.trimNull(request.getParameter("end_threshold"));
String true_value = Utility.trimNull(request.getParameter("true_value"));
String false_value = Utility.trimNull(request.getParameter("false_value"));
Integer add_sub_items = Utility.parseInt(Utility.trimNull(request.getParameter("add_sub_items")),new Integer(0));
BigDecimal weight = Utility.parseDecimal(Utility.trimNull(request.getParameter("weight")),new BigDecimal(1));
BigDecimal multiple = Utility.parseDecimal(Utility.trimNull(request.getParameter("multiple")),new BigDecimal(1));
String summary = Utility.trimNull(request.getParameter("summary"));
Integer input_man = Utility.parseInt(Utility.trimNull(input_operatorCode),new Integer(0));
String refresh = Utility.trimNull(request.getParameter("refresh"));
int gm_flag = Utility.parseInt(request.getParameter("gm_flag"),0);
Integer cust_type = Utility.parseInt(request.getParameter("cust_type"),new Integer(1));

Map map = null;
List rsList = null;

//获得对象及结果集
RatingVO vo = new RatingVO();
SystemValueLocal system_value = EJBFactory.getSystemValue();

boolean bSuccess = false;

//保存信息
if(request.getMethod().equals("POST")){

	vo.setOperand_id(operand_id);
	vo.setOperand_xh(operand_xh);
	vo.setSource_table(source_table);
	vo.setSource_field(source_field);
	vo.setInclude_top(include_top);
	vo.setTop_threshold(Utility.parseDecimal(top_threshold,new BigDecimal(0)));	
	vo.setInclude_end(include_end);
	vo.setEnd_threshold(Utility.parseDecimal(end_threshold,new BigDecimal(0)));
	vo.setTrue_value(true_value);
	vo.setFalse_value(false_value);
	vo.setAdd_sub_items(add_sub_items);
	vo.setWeight(weight);
	vo.setMultiple(multiple);
	vo.setSummary(summary);
	vo.setInput_man(input_man);	
	vo.setCust_type(cust_type); 

	if(operand_v_id.intValue()!= 0){
		vo.setOperand_v_id(operand_v_id);
		system_value.modi_tsystemvalue(vo);
	}else{
		system_value.append_tsystemvalue(vo);
	}
	bSuccess = true;
}
else {
	if(refresh.equals("")){
		//信息显示
		if(operand_v_id.intValue()!= 0){
			vo.setOperand_v_id(operand_v_id);
			vo.setInput_man(input_man);
			List list = system_value.list_tsystemvalue(vo);
			map = (Map)list.get(0); 
		}
		
		if(map != null){
			operand_v_id = Utility.parseInt(Utility.trimNull(map.get("OPERAND_V_ID")),new Integer(0));
			operand_id = Utility.parseInt(Utility.trimNull(map.get("OPERAND_ID")),new Integer(0));
			operand_xh = Utility.trimNull(map.get("OPERAND_XH"));
			source_table = Utility.trimNull(map.get("SOURCE_TABLE"));
			source_field = Utility.trimNull(map.get("SOURCE_FIELD"));
			include_top = Utility.parseInt(Utility.trimNull(map.get("INCLUDE_TOP")),new Integer(0));
			include_end = Utility.parseInt(Utility.trimNull(map.get("INCLUDE_END")),new Integer(0));
			top_threshold = Utility.trimNull(map.get("TOP_THRESHOLD"));
			end_threshold = Utility.trimNull(map.get("END_THRESHOLD"));
			true_value = Utility.trimNull(map.get("TRUE_VALUE"));
			false_value = Utility.trimNull(map.get("FALSE_VALUE"));
			add_sub_items = Utility.parseInt(Utility.trimNull(map.get("ADD_SUB_ITEMS")),new Integer(0));
			weight = Utility.parseDecimal(Utility.trimNull(map.get("WEIGHT")),new BigDecimal(0));
			multiple = Utility.parseDecimal(Utility.trimNull(map.get("MULTIPLE")),new BigDecimal(0));
			summary = Utility.trimNull(map.get("SUMMARY"));
			cust_type = Utility.parseInt(Utility.trimNull(map.get("CUST_TYPE")),new Integer(1));
			if("999999999999.000".equals(end_threshold))
				end_threshold = "";
		}
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
	if(form.operand_xh.value == ""){
			sl_alert("<%=LocalUtilis.language("class.pleaseFillIn",clientLocale)%><%=LocalUtilis.language("class.serialNumber",clientLocale)%>！");//请填写编号
			return false;
	}
	if(form.source_table.value == 0){
		sl_alert("<%=LocalUtilis.language("message.select2",clientLocale)%><%=LocalUtilis.language("class.dataSourceTable",clientLocale)%>！");//请选择数据来源表
		return false;
	}
	if(form.source_field.value == 0){
		sl_alert("<%=LocalUtilis.language("message.select2",clientLocale)%><%=LocalUtilis.language("class.dataSourceField",clientLocale)%>！");//请选择数据来源字段
		return false;
	}
	//if(!sl_checkDecimal(form.bonus_rate, "转份额比例", 3, 2, 0))	return false;
	//if(!sl_checkDecimal(form.bonus_rate, "转份额比例", 3, 2, 0))	return false;
	return sl_check_update();
}

/*初始化*/
window.onload = function(){
	var v_bSuccess = document.getElementById("bSuccess").value;
	
	if(v_bSuccess=="true"){	
		sl_update_ok();	
		window.returnValue=1;	
		window.location = "system_value.jsp";
	}
}

/*返回*/
function CancelAction(){
	window.location = "system_value.jsp";
}

/*选择数据源表*/
function sourceTable(value){
		
	if (value!=""){
		var url = 'system_value_action.jsp?source_table=' + value+'&refresh=true'+ getLastOptions();
		window.location = url;
	}

}

/*拼后缀参数*/
function getLastOptions(){	

	var temp="";	
	var temp = temp + "&operand_v_id="+ document.theform.operand_v_id.value;	
	var temp = temp + "&operand_id="+ document.theform.operand_id.value;	
	var temp = temp + "&operand_xh="+ document.theform.operand_xh.value;	
	var temp = temp + "&top_threshold="+ document.theform.top_threshold.value;	
	var temp = temp + "&end_threshold=" + document.theform.end_threshold.value;
	var temp = temp + "&true_value=" + document.theform.true_value.value;
	//var temp = temp +"&false_value="+document.theform.false_value.value;
	//var temp = temp + "&weight=" + document.theform.weight.value;
	//var temp = temp + "&multiple=" + document.theform.multiple.value;
	var temp = temp + "&cust_type=" + document.theform.cust_type.value;
	var temp = temp + "&summary=" + document.theform.summary.value;

	var include_top;
	var include_end;
	var radio1=document.getElementsByName("include_top");
	for(var i=0;i<radio1.length;i++)
	{
		if(radio1[i].checked==true)
		{
			include_top = radio1[i].value;
		}
	}

	var radio2=document.getElementsByName("include_end");
	for(var i=0;i<radio2.length;i++)
	{
		if(radio2[i].checked==true)
		{
			include_end = radio2[i].value;
		}
	}
	var temp = temp + "&include_top=" + include_top;
	var temp = temp + "&include_end=" + include_end;
	
	return temp;
}
function trueValue(form){
	if(document.theform.selfTrue.checked){
		if(document.theform.selfTrue.value==0){
			document.theform.true_value.value ="__SELF";
			document.theform.true_value.disabled = true;
		}
	}else{
		document.theform.true_value.value ="";
		document.theform.true_value.disabled = false;
		document.theform.true_value.readonly = false;
	}
}

function changeUnit(value)
{
	if("BUREND_OF_DEBT"==value)
	{
		document.getElementById("unit_id_1").innerHTML = "<font color=red>%</font>";
		document.getElementById("unit_id_2").innerHTML = "<font color=red>%</font>";
	}else{
		document.getElementById("unit_id_1").innerHTML = "";
		document.getElementById("unit_id_2").innerHTML = "";
	}
}

</script>
</HEAD>

<BODY class="BODY">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="post" action="system_value_action.jsp" onsubmit="javascript:return validateForm(this);">
<!--新增成功标志-->
<input type="hidden" id="bSuccess" value="<%=bSuccess%>"/>
<input type="hidden" name="operand_v_id" value="<%=operand_v_id%>">
<input type="hidden" name="refresh" value="<%=refresh%>">
<div align="left">
	<img border="0" src="<%=request.getContextPath()%>/images/ico_area.gif" width="32" height="28">
	<font color="#215dc6"><b><%=menu_info%></b></font>
	<hr noshade color="#808080" size="1">
</div>
<div align ="center">
<table  border="0" width="100%" cellspacing="0" cellpadding="3" style="border: 0px; border-style: dashed; border-color: blue; margin-top:5px;">
	<tr>
		<td align="right">*<%=LocalUtilis.language("class.operandName",clientLocale)%>:</td><!--操作数名称-->
		<td align="left">
			<select name="operand_id" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 160px">
			<%=Argument.getOperandOptions(operand_id,new Integer(2),new Integer(0))%>
			</select>
		</td>
		<td align="right">*<%=LocalUtilis.language("class.serialNumber",clientLocale)%>:</td><!--编号-->
		<td align="left" >
		<input type="text" name="operand_xh" size="30" value="<%=operand_xh %>" onkeydown="javascript:nextKeyPress(this)">
		</td>
	</tr>
	<tr>
		<td align="right">*<%=LocalUtilis.language("class.dataSourceTable",clientLocale)%>:</td><!--数据来源表-->
		<td align="left">
			<select name="source_table" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 160px" onchange="javascript:sourceTable(this.value);">
			<%=Argument.getSourceTable(source_table)%>
			</select>
		</td>
		<td align="right">*<%=LocalUtilis.language("class.dataSourceField",clientLocale)%>:</td><!--数据来源字段-->
		<td align="left">
			<select name="source_field" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 160px" onchange="javascript:changeUnit(this.value);">
			<%=Argument.getSourceField(source_table,source_field)%>
			</select>
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.topCriticalValue",clientLocale)%>:</td><!--头临界值-->
		<td align="left" >
		<input type="text" name="top_threshold" size="30" value="<%=Utility.parseDecimal(top_threshold,new BigDecimal(0))%>" onkeydown="javascript:nextKeyPress(this)">		
		<span id="unit_id_1">
			<%if("BUREND_OF_DEBT".equals(source_field)) out.print("<font color=red>%</font>");%>
		</span>
		</td>
		<td align="right"><%=LocalUtilis.language("class.endCriticalValue",clientLocale)%>:</td><!--尾临界值-->
		<td align="left" >
			<input type="text" name="end_threshold" size="30" value="<%=Utility.parseDecimal(end_threshold,new BigDecimal(0))%>" onkeydown="javascript:nextKeyPress(this)">
		<span id="unit_id_2">
			<%if("BUREND_OF_DEBT".equals(source_field)) out.print("<font color=red>%</font>");%>
		</span>
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.includeTopCriticalValue",clientLocale)%>:</td><!--是否算头-->
		<td align="left">
			<input type = "radio" value = "1"  name = "include_top" class="flatcheckbox" class="flatcheckbox"
			<%if(include_top.intValue() == 1) {out.print("checked");}%> /><%=LocalUtilis.language("message.yes",clientLocale)%><input type = "radio" class="flatcheckbox" value = "2"  name = "include_top" 
			<%if(include_top.intValue() == 2) {out.print("checked");}%> /><%=LocalUtilis.language("message.no3",clientLocale)%>
		</td>
		<td align="right"><%=LocalUtilis.language("class.includeEndCriticalValue",clientLocale)%>:</td><!--是否算尾-->
		<td align="left" >
			<input type = "radio" value = "1"  name = "include_end" class="flatcheckbox" class="flatcheckbox"
			<%if(include_end.intValue() == 1) {out.print("checked");}%> /><%=LocalUtilis.language("message.yes",clientLocale)%><input type = "radio" class="flatcheckbox" value = "2"  name = "include_end" 
			<%if(include_end.intValue() == 2) {out.print("checked");}%> /><%=LocalUtilis.language("message.no3",clientLocale)%>
		</td>
	</tr>
	<tr>
		<td align="right">分数值:</td><!--为真取值-->
		<td align="left" >
		<input type="text" <%if (true_value.equals("__SELF"))out.print("disabled");%>
			name="true_value" size="30" value="<%=true_value %>" onkeydown="javascript:nextKeyPress(this)">&nbsp;&nbsp;&nbsp;
		</td>
		<td align="right">客户类型:</td>
		<td>
			<select name="cust_type" style="width: 160px;">
				<%=Argument.getCustTypeOptions2(cust_type)%>
			</select>
		</td>	
	<%if(gm_flag !=0){ %>
		<td>
		<input <%//if (true_value.equals("__SELF"))out.print("checked");%> onkeydown="javascript:nextKeyPress(this)" 
			type="checkbox" class="flatcheckbox" value="0" name="selfTrue" id="selfTrue" onclick="javascript:trueValue()">使用数据来源字段值
		</td>
		
		<td align="right"><%=LocalUtilis.language("class.falseValue",clientLocale)%>:</td><!--为假取值-->
		<td align="left" >
			<input type="text" name="false_value" size="30" value="<%=false_value %>" onkeydown="javascript:nextKeyPress(this)">
		</td>
		
	</tr>
 
	<tr>
		<td align="right"><%=LocalUtilis.language("class.addSub",clientLocale)%>:</td><!--加减项-->
		<td align="left" colspan="3">
			<input type = "radio" value = "1"  name = "add_sub_items" class="flatcheckbox"
			<%if(include_top.intValue() == 1) {out.print("checked");}%> /><%=LocalUtilis.language("class.add",clientLocale)%><input type = "radio" value = "-1" class="flatcheckbox"  name = "add_sub_items" 
			<%if(include_top.intValue() == 2) {out.print("checked");}%> /><%=LocalUtilis.language("class.subtract",clientLocale)%>
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.weight1",clientLocale)%>:</td><!--权重-->
		<td align="left" >
		<input type="text" name="weight" size="30" value="<%=weight %>" onkeydown="javascript:nextKeyPress(this)">
		</td>
		<td align="right"><%=LocalUtilis.language("class.multiple",clientLocale)%>:</td><!--倍数-->
		<td align="left" >
			<input type="text" name="multiple" size="30" value="<%=multiple %>" onkeydown="javascript:nextKeyPress(this)">
		</td>
	<%} %>	
	</tr>
	
	<tr>
		<td align="right"><%=LocalUtilis.language("class.memberDescription",clientLocale)%>:</td><!--描述-->
		<td align="left" colspan="3"><textarea  rows="4" name="summary" cols="150"><%=summary %></textarea></td>
	</tr>
</table>
</div>
<div align="right">
	<br>
	<!--保存-->
    <button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:SaveAction();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;
	<!--返回-->
    <button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:CancelAction();"><%=LocalUtilis.language("message.back",clientLocale)%>(<u>C</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</div>
</form>
<p>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>
<%
system_value.remove();
%>
