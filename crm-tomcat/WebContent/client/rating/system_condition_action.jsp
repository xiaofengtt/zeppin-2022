<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.customer.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//���ҳ�洫�ݱ���
Integer operand_v_id = Utility.parseInt(Utility.trimNull(request.getParameter("operand_v_id")),new Integer(0));
String source_table = Utility.trimNull(request.getParameter("source_table"));
String source_field = Utility.trimNull(request.getParameter("source_field"));
Integer include_top = Utility.parseInt(request.getParameter("include_top"),new Integer(0));
Integer include_end = Utility.parseInt(request.getParameter("include_end"),new Integer(0));
String top_threshold = Utility.trimNull(request.getParameter("top_threshold"));
String end_threshold = Utility.trimNull(request.getParameter("end_threshold"));
Integer true_false_value = Utility.parseInt(Utility.trimNull(request.getParameter("true_false_value")),new Integer(0));
String summary = Utility.trimNull(request.getParameter("summary"));
Integer input_man = Utility.parseInt(Utility.trimNull(input_operatorCode),new Integer(0));
String refresh = Utility.trimNull(request.getParameter("refresh"));

//ϵͳ���ȡֵ��Ϣ����
String operand_name = "";
String operand_xh = "";
String source_table_v = "";
String source_field_v = "";
String include_top_v ="";
String include_end_v = "";
String top_threshold_v = "";
String end_threshold_v = "";
String true_value = "";
String false_value = "";
String add_sub_items = "";
BigDecimal weight = new BigDecimal(1);
BigDecimal multiple = new BigDecimal(1);
Integer top_v = new Integer(0);
Integer end_v = new Integer(0);
Integer items = new Integer(0);

Map map = null;
List list = null;
Map map_c = null;
List list_c = null;

//��ö��󼰽����
RatingVO vo = new RatingVO();
SystemValueLocal system_value = EJBFactory.getSystemValue();
SystemConditionLocal system_condition = EJBFactory.getSystemCondition();

boolean bSuccess = false;

//����ϵͳ���������Ϣ
if(request.getMethod().equals("POST")){

	vo.setOperand_v_id(operand_v_id);
	vo.setSource_table(source_table);
	vo.setSource_field(source_field);
	vo.setInclude_top(include_top);
	vo.setTop_threshold(Utility.parseDecimal(top_threshold,new BigDecimal(0)));	
	vo.setInclude_end(include_end);
	vo.setEnd_threshold(Utility.parseDecimal(end_threshold,new BigDecimal(0)));
	vo.setTrue_false_value(true_false_value);
	vo.setSummary(summary);
	vo.setInput_man(input_man);	

	system_condition.modi_tsystemcondition(vo);
	bSuccess = true;
}
else {
	
	//��Ϣ��ʾ
	if(operand_v_id.intValue()!= 0){
		vo.setOperand_v_id(operand_v_id);
		vo.setInput_man(input_man);
		list = system_value.list_tsystemvalue(vo);
		map = (Map)list.get(0); 

		list_c = system_condition.list_tsystemcondition(vo);
		map_c = (Map)list_c.get(0); 
	}
	
	//�鿴ϵͳ���ȡֵ��Ϣ
	if(map != null){
		operand_v_id = Utility.parseInt(Utility.trimNull(map.get("OPERAND_V_ID")),new Integer(0));
		operand_name = Utility.trimNull(map.get("OPERAND_NAME"));
		operand_xh = Utility.trimNull(map.get("OPERAND_XH"));
		source_table_v = Utility.trimNull(map.get("SOURCE_TABLE"));
		source_field_v = Utility.trimNull(map.get("SOURCE_FIELD"));
		top_v = Utility.parseInt(Utility.trimNull(map.get("INCLUDE_TOP")),new Integer(0));
		end_v = Utility.parseInt(Utility.trimNull(map.get("INCLUDE_END")),new Integer(0));
		top_threshold_v = Utility.trimNull(map.get("TOP_THRESHOLD"));
		end_threshold_v = Utility.trimNull(map.get("END_THRESHOLD"));
		true_value = Utility.trimNull(map.get("TRUE_VALUE"));
		false_value = Utility.trimNull(map.get("FALSE_VALUE"));
		items = Utility.parseInt(Utility.trimNull(map.get("ADD_SUB_ITEMS")),new Integer(0));
		weight = Utility.parseDecimal(Utility.trimNull(map.get("WEIGHT")),new BigDecimal(0));
		multiple = Utility.parseDecimal(Utility.trimNull(map.get("MULTIPLE")),new BigDecimal(0));
		if(top_v.intValue()==1)
			include_top_v = "��";
		else if(top_v.intValue()==2)
			include_top_v = "��";
		if(end_v.intValue()==1)
			include_end_v = "��";
		else if(end_v.intValue()==2)
			include_end_v = "��";
		if(items.intValue()==1)
			add_sub_items = "��";
		else if(items.intValue()==-1)
			add_sub_items = "��";
			
	}

	//�鿴ϵͳ���������Ϣ
	if(refresh.equals("")){
		if(map_c != null){
			source_table = Utility.trimNull(map_c.get("TABLE_NAME"));
			source_field = Utility.trimNull(map_c.get("FIELD_NAME"));
			include_top = Utility.parseInt(Utility.trimNull(map_c.get("INCLUDE_TOP")),new Integer(0));
			include_end = Utility.parseInt(Utility.trimNull(map_c.get("INCLUDE_END")),new Integer(0));
			top_threshold = Utility.trimNull(map_c.get("TOP_THRESHOLD"));
			end_threshold = Utility.trimNull(map_c.get("END_THRESHOLD"));
			true_false_value = Utility.parseInt(Utility.trimNull(map_c.get("TRUE_FALSE_VALUE")),new Integer(0));
			summary = Utility.trimNull(map_c.get("SUMMARY"));
		}
	}
}
%>

<HTML>
<HEAD>
<TITLE></TITLE><!--�������ֿ�Ŀ-->
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
/*����*/
function SaveAction(){		
	if(document.theform.onsubmit()){
		disableAllBtn(true);
		document.theform.submit();
	}
}

function validateForm(form){

	if(form.source_table.value == 0){
		sl_alert("<%=LocalUtilis.language("message.select2",clientLocale)%><%=LocalUtilis.language("class.dataSourceTable",clientLocale)%>��");//��ѡ��������Դ��
		return false;
	}
	if(form.source_field.value == 0){
		sl_alert("<%=LocalUtilis.language("message.select2",clientLocale)%><%=LocalUtilis.language("class.dataSourceField",clientLocale)%>��");//��ѡ��������Դ�ֶ�
		return false;
	}

	return sl_check_update();
}

/*��ʼ��*/
window.onload = function(){
	var v_bSuccess = document.getElementById("bSuccess").value;
	
	if(v_bSuccess=="true"){	
		sl_update_ok();	
		window.returnValue=1;	
		window.location = "system_value.jsp";
	}
}

/*����*/
function CancelAction(){
	window.location = "system_value.jsp";
}

/*ѡ������Դ��*/
function sourceTable(value){
		
	if (value!=""){
		var url = 'system_condition_action.jsp?source_table=' + value+'&refresh=true'+ getLastOptions();
		window.location = url;
	}

}

/*ƴ��׺����*/
function getLastOptions(){	

	var temp="";	
	var temp = temp + "&operand_v_id="+ document.theform.operand_v_id.value;		
	var temp = temp + "&top_threshold="+ document.theform.top_threshold.value;	
	var temp = temp + "&end_threshold=" + document.theform.end_threshold.value;
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
</script>
</HEAD>

<BODY class="BODY">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="post" action="system_condition_action.jsp" onsubmit="javascript:return validateForm(this);">
<!--�����ɹ���־-->
<input type="hidden" id="bSuccess" value="<%=bSuccess%>"/>
<input type="hidden" name="operand_v_id" value="<%=operand_v_id%>">
<input type="hidden" name="refresh" value="<%=refresh%>">
<div align="left">
	<img border="0" src="<%=request.getContextPath()%>/images/ico_area.gif" width="32" height="28">
	<font color="#215dc6"><b><%=menu_info%></b></font>
	<hr noshade color="#808080" size="1">
</div>
<div align ="center">
<table  border="0" width="100%" cellspacing="0" cellpadding="3" style="border: 1px; border-style: dashed; border-color: blue; margin-top:5px;">
	<tr>
		<td align="left" colspan="4"><b>ϵͳ���ȡֵ��Ϣ</b></td><!--ϵͳ���������Ϣ-->
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.serialNumber",clientLocale)%> : </td><!--���-->
		<td align="left" >
			<input type="text" name="operand_xh" size="30" value="<%=operand_xh %>"  readonly class='edline'>
		</td>
		<td align="right"><%=LocalUtilis.language("class.scoringID",clientLocale)%>:</td><!--�����-->
		<td align="left">
			<input type="text" name="operand_name" size="30" value="<%=operand_name %>" readonly class='edline'>
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.dataSourceTable",clientLocale)%> :</td><!--������Դ��-->
		<td align="left">
			<input type="text" name="source_table" size="30" value="<%=source_table_v %>"  readonly class='edline'>
		</td>
		<td align="right"><%=LocalUtilis.language("class.dataSourceField",clientLocale)%>:</td><!--������Դ�ֶ�-->
		<td align="left">
			<input type="text" name="source_field" size="30" value="<%=source_field_v %>"  readonly class='edline'>
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.topCriticalValue",clientLocale)%> : </td><!--ͷ�ٽ�ֵ-->
		<td align="left" >
		<input type="text" readonly class='edline' name="top_threshold_v" size="30" value="<%=Format.formatMoney(Utility.parseDecimal(top_threshold_v,new BigDecimal(0))) %>">
		</td>
		<td align="right"><%=LocalUtilis.language("class.endCriticalValue",clientLocale)%>:</td><!--β�ٽ�ֵ-->
		<td align="left" >
			<input type="text" readonly class='edline' name="end_threshold_v" size="30" value="<%=Format.formatMoney(Utility.parseDecimal(end_threshold_v,new BigDecimal(0))) %>">
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.includeTopCriticalValue",clientLocale)%> :</td><!--�Ƿ���ͷ-->
		<td align="left">
			<input type="text" readonly class='edline' name="include_top_v" size="30" value="<%=include_top_v %>">

		</td>
		<td align="right"><%=LocalUtilis.language("class.includeEndCriticalValue",clientLocale)%>: </td><!--�Ƿ���β-->
		<td align="left" >
			<input type="text" readonly class='edline' name="include_end_v" size="30" value="<%=include_end_v %>">
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.trueValue",clientLocale)%> : </td><!--Ϊ��ȡֵ-->
		<td align="left" >
			<input type="text" name="true_value" size="30" value="<%=true_value %>" readonly class='edline'>
		</td>
		<td align="right"><%=LocalUtilis.language("class.falseValue",clientLocale)%>:</td><!--Ϊ��ȡֵ-->
		<td align="left" >
			<input type="text" name="false_value" size="30" value="<%=false_value %>" readonly class='edline'>
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.addSub",clientLocale)%> :</td><!--�Ӽ���-->
		<td align="left" colspan="3">
			<input type="text" readonly class='edline' name="add_sub_items" size="30" value="<%=add_sub_items %>">
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.weight1",clientLocale)%> :</td><!--Ȩ��-->
		<td align="left" >
		<input type="text" name="weight" size="30" value="<%=weight %>"  readonly class='edline'>
		</td>
		<td align="right"><%=LocalUtilis.language("class.multiple",clientLocale)%>:</td><!--����-->
		<td align="left" >
			<input type="text" name="multiple" size="30" value="<%=multiple %>" readonly class='edline'>
		</td>
	</tr>
</table>
</div>
<div align ="center">
<table  border="0" width="100%" cellspacing="0" cellpadding="3" style="border: 1px; border-style: dashed; border-color: blue; margin-top:5px;">
	<tr>
		<td align="left" colspan="4"><b>ϵͳ���������Ϣ</b></td><!--ϵͳ�����������-->
	</tr>
	<tr>
		<td align="right">*<%=LocalUtilis.language("class.dataSourceTable",clientLocale)%> :</td><!--������Դ��-->
		<td align="left">
			<select name="source_table" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 160px" onchange="javascript:sourceTable(this.value);">
			<%=Argument.getSourceTable(source_table)%>
			</select>
		</td>
		<td align="right">*<%=LocalUtilis.language("class.dataSourceField",clientLocale)%>:</td><!--������Դ�ֶ�-->
		<td align="left">
			<select name="source_field" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 160px">
			<%=Argument.getSourceField(source_table,source_field)%>
			</select>
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.topCriticalValue",clientLocale)%> : </td><!--ͷ�ٽ�ֵ-->
		<td align="left" >
		<input type="text" name="top_threshold" size="30" value="<%=Format.formatMoney(Utility.parseDecimal(top_threshold,new BigDecimal(0))) %>" onkeydown="javascript:nextKeyPress(this)">
		</td>
		<td align="right"><%=LocalUtilis.language("class.endCriticalValue",clientLocale)%>:</td><!--β�ٽ�ֵ-->
		<td align="left" >
			<input type="text" name="end_threshold" size="30" value="<%=Format.formatMoney(Utility.parseDecimal(end_threshold,new BigDecimal(0))) %>" onkeydown="javascript:nextKeyPress(this)">
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.includeTopCriticalValue",clientLocale)%> :</td><!--�Ƿ���ͷ-->
		<td align="left">
			<input type = "radio" value = "1"  name = "include_top" 
			<%if(include_top.intValue() == 1) {out.print("checked");}%> /><%=LocalUtilis.language("message.yes",clientLocale)%><input type = "radio" value = "2"  name = "include_top" 
			<%if(include_top.intValue() == 2) {out.print("checked");}%> /><%=LocalUtilis.language("message.no3",clientLocale)%>
		</td>
		<td align="right"><%=LocalUtilis.language("class.includeEndCriticalValue",clientLocale)%>: </td><!--�Ƿ���β-->
		<td align="left" >
			<input type = "radio" value = "1"  name = "include_end" 
			<%if(include_end.intValue() == 1) {out.print("checked");}%> /><%=LocalUtilis.language("message.yes",clientLocale)%><input type = "radio" value = "2"  name = "include_end" 
			<%if(include_end.intValue() == 2) {out.print("checked");}%> /><%=LocalUtilis.language("message.no3",clientLocale)%>
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.estabSitu",clientLocale)%> :</td><!--�����������-->
		<td align="left" colspan="3">
			<input type = "radio" value = "1"  name = "true_false_value" 
			<%if(true_false_value.intValue() == 1) {out.print("checked");}%> /><%=LocalUtilis.language("class.estab",clientLocale)%><input type = "radio" value = "-1"  name = "true_false_value" 
			<%if(true_false_value.intValue() == 2) {out.print("checked");}%> /><%=LocalUtilis.language("class.estabNOT",clientLocale)%>
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.memberDescription",clientLocale)%> :</td><!--����-->
		<td align="left" colspan="3"><textarea  rows="4" name="summary" cols="150"><%=summary %></textarea></td>
	</tr>
</table>
</div>
<div align="right">
	<br>
	<!--����-->
    <button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:SaveAction();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;
	<!--����-->
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
system_condition.remove();
%>
