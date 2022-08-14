<%@ page contentType="text/html; charset=GBK" import="enfo.crm.marketing.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<% 
String q_productCode= Utility.trimNull(request.getParameter("q_productCode"));
int main_flag  = Utility.parseInt(Utility.trimNull(request.getParameter("main_flag")),0);
//获得页面传递变量
Integer q_pre_product_id = Utility.parseInt(Utility.trimNull(request.getParameter("q_pre_product_id")),new Integer(0));
String q_product_name = Utility.trimNull(request.getParameter("q_product_name"));
String q_cust_name = Utility.trimNull(request.getParameter("q_cust_name"));
Integer start_date = Utility.parseInt(Utility.trimNull(request.getParameter("start_date")),new Integer(0));
Integer end_date = Utility.parseInt(Utility.trimNull(request.getParameter("end_date")),new Integer(0));
Integer team_id = Utility.parseInt(Utility.trimNull(request.getParameter("team_id")),new Integer(0));
Integer begin_date = Utility.parseInt(Utility.trimNull(request.getParameter("begin_date")),new Integer(0));
BigDecimal pre_money1 = Utility.parseDecimal(Utility.trimNull(request.getParameter("pre_money1")),new BigDecimal(0));
BigDecimal pre_money2 = Utility.parseDecimal(Utility.trimNull(request.getParameter("pre_money2")),new BigDecimal(0));
String pre_status = Utility.trimNull(request.getParameter("pre_status"),"110202");
String q_pre_level = Utility.trimNull(request.getParameter("q_pre_level"));
//页面辅助参数
boolean print_flag = false;
String tempUrl = "";
int iCount = 0;
int iCurrent = 0;

Map map = null;

PreMoneyDetailLocal local = EJBFactory.getPreMoneyDetail();
enfo.crm.vo.PreMoneyDetailVO preVo = new enfo.crm.vo.PreMoneyDetailVO();
preVo.setPre_product_id(q_pre_product_id);
preVo.setStart_date(start_date);
preVo.setEnd_date(end_date);
preVo.setPre_status(pre_status);
preVo.setCust_name(q_cust_name);
preVo.setInput_man(input_operatorCode);
preVo.setPre_level(q_pre_level);
preVo.setTeam_id(team_id);
List list = null;
if(UNQUERY.intValue()==0)
    list = local.queryPreMoneyDetail(preVo);
else
    list = new ArrayList();
    
//url设置
tempUrl = tempUrl+"&q_pre_product_id="+q_pre_product_id
				+"&q_cust_name="+q_cust_name;
tempUrl = tempUrl+"&start_date="+start_date;
tempUrl = tempUrl+"&end_date="+end_date;
tempUrl = tempUrl+"&pre_status="+pre_status;
tempUrl = tempUrl+"&q_pre_level="+q_pre_level;
sUrl = sUrl + tempUrl;

String options = Argument.getPreProductListOptions(q_pre_product_id,"","110299",input_operatorCode);
options = options.replaceAll("\"", "'");
%>

<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("menu.bespeakList",clientLocale)%> </TITLE>
<!--预约列表-->
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<!--TABLE 合同js-->
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/jQuery/js/jquery-1.3.2.min.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/tablespan.js"></SCRIPT>
<script language=javascript>
/*启动加载*/
window.onload = function(){
	initQueryCondition();
}

/*查询功能*/
function StartQuery(){
	refreshPage();
}

/*刷新*/
function refreshPage(){
	disableAllBtn(true);
	syncDatePicker(document.theform.start_date_picker, document.theform.start_date);
	syncDatePicker(document.theform.end_date_picker, document.theform.end_date);
	if(!sl_checkDecimal(document.theform.pre_money1, "", 13, 3, 0))return false;
	if(!sl_checkDecimal(document.theform.pre_money2, "", 13, 3, 0))return false;
	var url = "pre_money_detail_query.jsp?page=1&pagesize="+ document.theform.pagesize.value;	
	var url = url + '&q_pre_product_id=' + document.theform.q_pre_product_id.value;
	var url = url + '&q_cust_name=' + document.theform.q_cust_name.value;
	var url = url + '&start_date=' + document.theform.start_date.value;
	var url = url + '&end_date=' + document.theform.end_date.value;
	var url = url + '&pre_status=' + document.theform.pre_status.value;
	var url = url + '&q_pre_level=' + document.theform.q_pre_level.value;
	var url = url + '&team_id=' + document.theform.team_id.value;
	location = url;	
}

/*设置产品*/
function setProduct(value){
	var prodid=0;
	
	if(event.keyCode == 13 && value != ""){
        var j = value.length;
        
		for(i=0;i<document.theform.q_pre_product_id.options.length;i++){
			if(document.theform.q_pre_product_id.options[i].text.substring(0,j)==value){
				document.theform.q_pre_product_id.options[i].selected=true;
				prodid = document.theform.q_pre_product_id.options[i].value;
				break;
			}	
		}
		
		if (prodid==0){
			sl_alert('<%=LocalUtilis.language("message.idIsNotExists",clientLocale)%> ');//输入的产品编号不存在
			document.theform.q_productCode.value="";
			document.theform.q_pre_product_id.options[0].selected=true;
		}
	}
	
	nextKeyPress(this);
}

/*查询产品*/
function searchProduct(value){
	var prodid=0;
	if(value!=""){
        var j = value.length;
        
		for(i=0;i<document.theform.q_pre_product_id.options.length;i++){
			if(document.theform.q_pre_product_id.options[i].text.substring(0,j)==value){
				document.theform.q_pre_product_id.options[i].selected=true;
				prodid = document.theform.q_pre_product_id.options[i].value;
				break;
			}	
		}
		
		if (prodid==0){
			sl_alert('<%=LocalUtilis.language("message.idIsNotExists",clientLocale)%> ！');//输入的产品编号不存在
			document.theform.q_productCode.value="";
			document.theform.q_pre_product_id.options[0].selected=true;
		}
		
		document.theform.q_productCode.focus();					
	}	
}

/*
 *调用合同table的方法
 */
$(document).ready(function(){  
	//_w_table_lefttitle_rowspan("#table3",1);
});

/*导出数据
 *传入参数说明：查询条件既导出信息与显示信息一致
*/
function exportInfo()
{
	if(sl_confirm("导出数据"))
	{
		syncDatePicker(document.theform.start_date_picker, document.theform.start_date); //
		syncDatePicker(document.theform.end_date_picker, document.theform.end_date);
		var query_term = 'q_pre_product_id=' + document.theform.q_pre_product_id.value
					+ '&q_cust_name=' + document.theform.q_cust_name.value
					+ '&start_date=' + document.theform.start_date.value
					+ '&end_date=' + document.theform.end_date.value
					+ '&pre_status=' + document.theform.pre_status.value
					+ '&q_pre_level=' + document.theform.q_pre_level.value
					+ '&team_id=' + document.theform.team_id.value;
	  	setWaittingFlag(false);
	 	var url = 'pre_money_detail_export.jsp?'+query_term;
	 	location = url;
	}
}

function searchProductName(value){
	var list = [];
	var list1 = [];
	document.getElementById("select_id").innerHTML = "<SELECT size='1' name='q_pre_product_id' class='productname' onkeydown='javascript:nextKeyPress(this)' style='width: 340px;'>"+"<%=options%>"+"</SELECT>";
	if(value != ""){
		for(i=0;i<document.theform.q_pre_product_id.options.length;i++){
			var j = document.theform.q_pre_product_id.options[i].text.indexOf(value);
			if(j>0){
				list.push(document.theform.q_pre_product_id.options[i].text);
				list1.push(document.theform.q_pre_product_id.options[i].value);
			}
		}	
		if(list.length==0){
			sl_alert('输入的产品名称不存在 ！');//输入的产品名称不存在
			document.theform.q_productCode.value="";
			document.theform.q_pre_product_id.options[0].selected=true;
		}else{
			document.theform.q_pre_product_id.options.length=0;
			for(var i=0;i<list.length;i++){
				document.theform.q_pre_product_id.options.add(new Option(list[i],list1[i]));
			}
		}
		document.theform.q_pre_product_id.focus();
	}else{
		document.theform.q_pre_product_id.options[0].selected=true;
	}
}

// 1. 表格会有多余的合计行
// 2. sort="num"标示的列使用数字排序
// 3. sort="multi"标示的表格注意隐藏列，这里使用className来判断
// 4. 要提高执行效率
function sortTable__2() {
	var start = new Date;
	event.cancelBubble = true;
	var the_obj = event.srcElement;
	if (the_obj.tagName != "TD")
		return;

	var tab = this.parentElement.parentElement;
	if (tab.tagName=='TBODY') tab = tab.parentElement;
	if(tab.tagName != "TABLE") return;

	window.status = "正在对数据进行排序，请您稍候...";

	if(the_obj.mode == undefined)
		the_obj.mode = false;

	var len = tab.rows.length;

	if (len>0 && tab.rows[len-1].cells[0].innerText.indexOf("合")>=0)
		len--;

	if(the_obj.sort == "num")
		table_sort_num__2(tab, the_obj.cellIndex, the_obj.mode, len);
	else
		table_sort_string__2(tab, the_obj.cellIndex, the_obj.mode, len);

	the_obj.mode = !the_obj.mode; // 升序或逆序

	window.status = "";
}

function table_sort_num__2(obj, n, mode, len)
{
	var i, j;
	var tab = obj;
	multi = (tab.sort == "multi" || tab.sort == "MULTI");
	if (!multi)
		return table_sort_num_fast__2(obj, n, mode, len);

	for(i = 1; i < len - 1; i++)
	{
		if(tab.rows[i].cells[0].innerText == "")
			continue;
		if(tab.rows[i].className == "")
			continue;
		for(j = i + 1;j < len; j++)
		{
			if(tab.rows[j].cells[0].innerText == "")
				continue;
			if(tab.rows[j].className == "")
				continue;
			if (mode)
			{
				if(sl_parseFloat(tab.rows[i].cells[n].innerText) < sl_parseFloat(tab.rows[j].cells[n].innerText))
				{
					tab.rows[i].swapNode(tab.rows[j]);
					if (multi)	swapHistoryRow(tab, i, j, len);
				}
			}
			else
			{
				if(sl_parseFloat(tab.rows[i].cells[n].innerText) > sl_parseFloat(tab.rows[j].cells[n].innerText))
				{
					tab.rows[i].swapNode(tab.rows[j]);
					if (multi)	swapHistoryRow(tab, i, j, len);
				}
			}
		}
	}
}

function SortArr__2(mode) {
	return function(obj1, obj2) {
		var flag;
		var key1 = sl_parseFloat(obj1.key);
		var key2 = sl_parseFloat(obj2.key);
		//flag=mode?b.localeCompare(a):a.localeCompare(b);//flag=mode?(a<b?1:(a>b?-1:0)):(a>b?1:(a<b?-1:0));
		if (mode)
			return key2-key1;
		else
			return key1-key2;
		//return flag;
	};
}

function table_sort_num_fast__2(the_tab, col, mode, len) {
	var arr_arr = new Array();
	var tab_arr = new Array();
	for (var i=1; i<len; i++) {
		if (trim(the_tab.rows[i].cells[col].innerText) == "")
			continue;

		if (tab_arr.length==0 
				|| the_tab.rows[i].cells[0].innerText == the_tab.rows[i-1].cells[0].innerText) {
			tab_arr.push({"key": the_tab.rows[i].cells[col].innerText, 
						  "tr": the_tab.rows[i]});			
		} else {		
			arr_arr.push(tab_arr);
			tab_arr = new Array();
			tab_arr.push({"key": the_tab.rows[i].cells[col].innerText, 
						  "tr": the_tab.rows[i]});			
		}
	}
	arr_arr.push(tab_arr);

	var total = 0;
	for (var i=0; i<arr_arr.length; i++) {
		tab_arr = arr_arr[i];
		tab_arr.sort(SortArr__2(mode));

		/*for(var j=0; j<tab_arr.length; j++) {
			if (tab_arr[j].tr.cells[0].rowSpan > 1) {
				if (j!=0) {
					//var temp = tab_arr[0].tr.cells[0].innerText;
					tab_arr[0].tr.cells[0].rowSpan = tab_arr.length;
					tab_arr[j].tr.cells[0].rowSpan = 1;

					//tab_arr[0].tr.cells[0].setAttribute("rowSpan", tab_arr.length);
					//tab_arr[0].tr.cells[0].innerText = tab_arr[j].tr.cells[0].innerText;
			
					//tab_arr[j].tr.cells[0].removeAttribute("rowSpan");
					//tab_arr[j].tr.cells[0].innerText = temp;
					//var temp = document.createElement("td"); //class="tdh" height="28px;" align="center"
					//temp.

				}
				break;
			}
		}*/

		for(var j=0; j<tab_arr.length; j++) {
			the_tab.firstChild.appendChild(tab_arr[j].tr);
		}		

		total += tab_arr.length;
	}		

	for (var i=1; i<the_tab.rows.length - total; i++)
		the_tab.firstChild.appendChild(the_tab.rows[1]); // 
}

function table_sort_string__2(obj, n, mode, len)
{
	var i, j, multi;
	var tab = obj;
	multi = (tab.sort == "multi" || tab.sort == "MULTI");
	if (!multi)
		return table_sort_string_fast__2(obj, n, mode, len);

	for(i = 1; i < len - 1; i++)
	{
		if(tab.rows[i].cells[0].innerText == "")
			continue;
		if(tab.rows[i].className == ""||tab.rows[i].className =="trbottom")
			continue;
		for(j = i + 1; j < len; j++)
		{
			if(tab.rows[j].cells[0].innerText == "")
				continue;
			if(tab.rows[j].className == ""||tab.rows[i].className =="trbottom")
				continue;
			if (mode)
			{
				if(tab.rows[j].cells[n].innerText.localeCompare(tab.rows[i].cells[n].innerText))//if(tab.rows[i].cells[n].innerText > tab.rows[j].cells[n].innerText)
				{
					tab.rows[i].swapNode(tab.rows[j]);
					if (multi)	swapHistoryRow(tab, i, j, len);
				}
			}
			else
			{
				if(tab.rows[j].cells[n].innerText.localeCompare(tab.rows[i].cells[n].innerText))//if(tab.rows[i].cells[n].innerText < tab.rows[j].cells[n].innerText)
				{
					tab.rows[i].swapNode(tab.rows[j]);
					if (multi)	swapHistoryRow(tab, i, j, len);
				}
			}
		}
	}
}

// 这个效率更高，但不适用于multi的情况
function table_sort_string_fast__2(the_tab, col, mode, len){
	var tab_arr = new Array();
	var i;
	for(i=1;i<len;i++){
		if(the_tab.rows[i].cells[0].innerText == "")
			continue;
		if(the_tab.rows[i].className =="trbottom")
			continue;
		tab_arr.push(new Array(the_tab.rows[i].cells[col].innerText, the_tab.rows[i]));
	}
	function SortArr(mode) {
		return function (arr1, arr2){
			var flag;
			var a,b;
			a = arr1[0];
			b = arr2[0];
			//flag=mode?(a<b?1:(a>b?-1:0)):(a>b?1:(a<b?-1:0));
			flag=mode?b.localeCompare(a):a.localeCompare(b);
			return flag;
		}
	}
	tab_arr.sort(SortArr(mode));

	for(i=0;i<tab_arr.length;i++)
		the_tab.firstChild.appendChild(tab_arr[i][1]);
	for(i = 1; i < the_tab.rows.length - tab_arr.length; i++)
		the_tab.firstChild.appendChild(the_tab.rows[1]);
}

/*
// 这个效率更高，但不适用于multi的情况
function table_sort_string_fast__2(the_tab, col, mode, len){
	function SortArr(mode) {
		return function (arr1, arr2){
			var flag;
			var a,b;
			a = arr1[0];
			b = arr2[0];
			//flag=mode?(a<b?1:(a>b?-1:0)):(a>b?1:(a<b?-1:0));
			flag=mode?b.localeCompare(a):a.localeCompare(b);
			return flag;
		}
	}
	
	var arr_arr = new Array();
	var tab_arr = new Array();
	for (var i=1; i<len; i++) {
		if(the_tab.rows[i].cells[col].innerText == "")
			continue;
		if(the_tab.rows[i].className =="trbottom")
			continue;

		if (tab_arr.length==0 
			|| the_tab.rows[i].cells[0].innerText == the_tab.rows[i-1].cells[0].innerText) {
			tab_arr.push(new Array(the_tab.rows[i].cells[col].innerText, the_tab.rows[i]));		
		} else {		
			arr_arr.push(tab_arr);
			tab_arr = new Array();
			tab_arr.push(new Array(the_tab.rows[i].cells[col].innerText, the_tab.rows[i]));		
		}
	}
	arr_arr.push(tab_arr);

	var total = 0;
	for (var i=0; i<arr_arr.length; i++) {
		tab_arr = arr_arr[i];
		tab_arr.sort(SortArr(mode));

		for(var j=0; j<tab_arr.length; j++) {
			the_tab.firstChild.appendChild(tab_arr[j][1]);
		}		

		total += tab_arr.length;
	}	

	for(var i=1; i<the_tab.rows.length - total; i++)
		the_tab.firstChild.appendChild(the_tab.rows[1]);
}*/
</script>
</HEAD>

<BODY class="BODY body-nox">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="post">
<input type="hidden" name="pagesize">
<!--查询功能模块-->
<div id="table99">
<div id="queryCondition" class="qcMain" style="display:none;width:540px;">
<table  id="qcTitle" class="qcTitle" align="center" width="99%" cellspacing="0" cellpadding="2">
  <tr>
   <td align="left"><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ：</td><!--查询条件-->
   <td align="right">
   <button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose" onclick="javascript:cancelQuery();"></button></td>
  </tr>
</table>
<table>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.productID",clientLocale)%>:</td><!--产品编号-->
		<td >
			<input type="text" name="q_productCode" value="<%= q_productCode%>" onkeydown="javascript:setProduct(this.value);" size="16">&nbsp;
			<button type="button"  class="searchbutton" onclick="javascript:searchProduct(document.theform.q_productCode.value);" />
		</td>
		<td  align="right">产品名称 :</td>
		<td  align="left" >
			<input type="text" name="q_product_name" value="" onkeydown="javascript:nextKeyPress(this);" size="15">&nbsp;
			<button type="button"  class="searchbutton" onclick="javascript:searchProductName(document.theform.q_product_name.value);" />		
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.productName",clientLocale)%>:</td><!--产品名称-->
		<td colspan="3" id="select_id">
			<select size="1" name="q_pre_product_id" onkeydown="javascript:nextKeyPress(this)" class="productname" style="width: 340px;">
				<%=Argument.getPreProductListOptions(q_pre_product_id,"","110299",input_operatorCode)%>
			</select>		
		</td>
	</tr>
	<tr>
		<td align="right" width="20%">到账开始日期:</td>
		<td width="30%">
			<INPUT TYPE="text" NAME="start_date_picker"value="<%=Format.formatDateLine(start_date)%>" size="16">
			<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.start_date_picker,theform.start_date_picker.value,this);" tabindex="13">
			<INPUT TYPE="hidden" NAME="start_date" value="">	
		</td>
		<td align="right" width="20%">到账结束日期:</td>
		<td align="left" width="30%">
			<INPUT TYPE="text" NAME="end_date_picker"value="<%=Format.formatDateLine(end_date)%>" size="16">
			<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.end_date_picker,theform.end_date_picker.value,this);" tabindex="13">
			<INPUT TYPE="hidden" NAME="end_date" value="">
		</td>
	</tr>
	<tr>
		<td align="right" width="20%"><%=LocalUtilis.language("class.customerName",clientLocale)%>:</td><!--客户名称-->
		<td align="left" width="30%">
			<input type="text" name="q_cust_name" value="<%=q_cust_name%>" size="20">
		</td>
		<td align="center" width="20%">产品状态:</td>
		<td align="left" width="30%">
			<select size="1" name="pre_status" onkeydown="javascript:nextKeyPress(this)" style="width: 120px;">
				<%=Argument.getDictParamOptions(1102, pre_status)%>
			</select>
		</td>
	</tr>
	<tr>
		<td align="right" width="20%">预约级别:</td><!--预约级别-->
		<td align="left" width="30%">
			<select size="1" name="q_pre_level" onkeydown="javascript:nextKeyPress(this)" style="width: 120px;">
				<%=Argument.getDictParamOptions(1182, q_pre_level)%>
			</select>
		</td>
		<td align="right" width="20%">团队名称:</td>
		<td align="left" width="30%">
			<select size="1" name="team_id" onkeydown="javascript:nextKeyPress(this)" style="width: 120px;">
				<%=Argument.getManagerTeams(team_id)%>
			</select>
		</td>
	</tr>
	
	<tr>
		<td align="center" colspan="4">		
		<%if (input_operator.hasFunc(menu_id, 108)) {%>
				<button type="button"  class="xpbutton3" accessKey=o name="btnQuery" onclick="javascript:StartQuery();"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>O</u>)</button>
				<!--确认-->
		<%}%>
		</td>
	</tr>
</table>
</div>

<div>
	<div align="left" class="page-title">
		<font color="#215dc6"><b><%=menu_info%></b></font>
	</div>
	<div align="right" class="btn-wrapper">
		<button type="button"  class="xpbutton3" accessKey=f id="queryButton" name="queryButton"><%=LocalUtilis.language("message.query",clientLocale)%> <!--查询-->(<u>F</u>)</button>&nbsp;&nbsp;&nbsp; 
		<button type="button"  class="xpbutton3" accessKey=p name="btnPrint" title="打印" onclick="javascript:table99.style.display = 'none';window.print();table99.style.display = '';">打印(<u>P</u>)</button>&nbsp;&nbsp;&nbsp;
		<button type="button"  class="xpbutton3" accessKey=e name="btnExort" title="导出Excel" onclick="javascript:exportInfo();">导出Excel(<u>E</u>)</button>
	</div>
</div>
</div>
	<br/>
<div style="margin-top:5px">
<table id="table3"  border="1px" bordercolor="#000000" cellspacing="0px" style="border-collapse:collapse"  width="100%" class="tablelinecolor">
	<tr class="trh">
		<td sort="string" style="ie2:expression(onclick=sortTable__2)">产品名称</td>
		<td sort="string" style="ie2:expression(onclick=sortTable__2)">客户名称</td>
		<td sort="string" style="ie2:expression(onclick=sortTable__2)">客户经理</td>
		<td sort="string" style="ie2:expression(onclick=sortTable__2)">销售团队</td>
		<td sort="string" style="ie2:expression(onclick=sortTable__2)">预约级别</td>
		<td align="center" width="*" sort="string" style="ie2:expression(onclick=sortTable__2)">到账日期</td>
		<td align="center" sort="num" style="ie2:expression(onclick=sortTable__2)">到账金额</td>
		<td align="center" width="*" style="ie2:expression(onclick=sortTable__2)">到账方式</td>
		<td align="center" >退款日期</td>
		<td align="center" sort="num" <%--style="ie2:expression(onclick=sortTable__2)"--%>>退款金额 </td>
	</tr>
<%
	//声明参数
	BigDecimal dz_money = new BigDecimal(0);
	BigDecimal refund_money = new BigDecimal(0);
	for(int j=0; j<list.size(); j++){
		iCount++;
		map = (Map)list.get(j);
		dz_money = dz_money.add(Utility.parseDecimal(Utility.trimNull(map.get("DZ_MONEY")), new BigDecimal(0.00),2,"1"));
		refund_money = refund_money.add(Utility.parseDecimal(Utility.trimNull(map.get("REFUND_MONEY")), new BigDecimal(0.00),2,"1"));
%>
	<tr>
		<td class="tdh" height="28px;" align="center"><%=Utility.trimNull(map.get("PREPRODUCT_NAME")) %></td>
		<td class="tdh" height="28px;" align="center"><%=Utility.trimNull(map.get("CUST_NAME")) %></td>
		<td align="center" height="28px;"> <%=Utility.trimNull(map.get("SERVICE_MAN_NAME")) %></td>
		<td align="center" height="28px;"> <%=Utility.trimNull(map.get("TEAM_NAME")) %></td>
		<td class="center" height="28px;" align="center"> <%=Utility.trimNull(map.get("PRE_LEVEL_NAME")) %></td>
		<td align="center" height="28px;"><%=Format.formatDateLine(Utility.parseInt(Utility.trimNull(map.get("DZ_DATE")),new Integer(0)))%></td>
		<td align="right" height="28px;"><%=Format.formatMoney(Utility.parseDecimal(Utility.trimNull(map.get("DZ_MONEY")), new BigDecimal(0.00),2,"1")) %></td>
		<td align="left"  height="28px;">&nbsp;<%=Utility.trimNull(map.get("JK_TYPE_NAME")) %></td>
		<td align="center" height="28px;"><%=Format.formatDateLine(Utility.parseInt(Utility.trimNull(map.get("REFUND_DATE")),new Integer(0)))%></td>
		<td align="right" height="28px;"><%=Format.formatMoney(Utility.parseDecimal(Utility.trimNull(map.get("REFUND_MONEY")), new BigDecimal(0.00),2,"1")) %></td>
	</tr>
	
<%}%>
	<tr > 
		<td class="tdh" align="center"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%><!--合计-->&nbsp;<%=list.size()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> <!--项--></b></td>
        <td align="center"></td>
		<td align="center"></td>
        <td align="center"></td>
        <td align="center"></td>
		<td align="center"></td>
		<td align="right" ><%=Utility.trimNull(Format.formatMoney(dz_money))%></td>
        <td align="center"></td>
        <td align="left"></td>
		<td align="right" ><%=Utility.trimNull(Format.formatMoney(refund_money))%></td>
	</tr>				
</table>
</div>
<%local.remove();%>
</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>