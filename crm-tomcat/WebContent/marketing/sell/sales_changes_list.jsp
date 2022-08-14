<%@ page contentType="text/html; charset=GBK" import="enfo.crm.marketing.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<% 
String q_productCode= Utility.trimNull(request.getParameter("q_productCode"));
int main_flag  = Utility.parseInt(Utility.trimNull(request.getParameter("main_flag")),0);
//���ҳ�洫�ݱ���
Integer q_pre_product_id = Utility.parseInt(Utility.trimNull(request.getParameter("q_pre_product_id")),new Integer(0));
String q_product_name = Utility.trimNull(request.getParameter("q_product_name"));
String q_cust_name = Utility.trimNull(request.getParameter("q_cust_name"));
Integer start_date = Utility.parseInt(Utility.trimNull(request.getParameter("start_date")),new Integer(0));
Integer end_date = Utility.parseInt(Utility.trimNull(request.getParameter("end_date")),new Integer(0));
Integer team_id = Utility.parseInt(Utility.trimNull(request.getParameter("team_id")),new Integer(0));
Integer begin_date = Utility.parseInt(Utility.trimNull(request.getParameter("begin_date")),new Integer(0));
String pre_status = Utility.trimNull(request.getParameter("pre_status"),"110202");
String q_pre_level = Utility.trimNull(request.getParameter("q_pre_level"));
Integer from_service_man = Utility.parseInt(Utility.trimNull(request.getParameter("from_service_man")),new Integer(0));
Integer to_service_man = Utility.parseInt(Utility.trimNull(request.getParameter("to_service_man")),new Integer(0));

Integer sa_status = Utility.parseInt(Utility.trimNull(request.getParameter("sa_status")),new Integer(0));
//ҳ�渨������
boolean print_flag = false;
String tempUrl = "";
int iCount = 0;
int iCurrent = 0;

Map map = null;

// PreMoneyDetailLocal local = EJBFactory.getPreMoneyDetail();
// SalesResultForStatisticLocal local = EJBFactory.getSalesResultForStatistic();
// enfo.crm.vo.SalesResultForStatisticVO preVo = new enfo.crm.vo.SalesResultForStatisticVO();

SalesChangesLocal sclocal = EJBFactory.getSalesChanges();
SalesChangesVO scVo = new SalesChangesVO();
scVo.setPre_product_id(q_pre_product_id);
scVo.setCust_name(q_cust_name);
scVo.setInput_man(input_operatorCode);
scVo.setFrom_service_man(from_service_man);
scVo.setTo_service_man(to_service_man);
scVo.setStatus(sa_status);

// preVo.setPre_product_id(q_pre_product_id);
// preVo.setStart_date(start_date);
// preVo.setEnd_date(end_date);
// preVo.setPre_status(pre_status);
// preVo.setCust_name(q_cust_name);
// preVo.setInput_man(input_operatorCode);
// preVo.setPre_level(q_pre_level);
// preVo.setTeam_id(team_id);
List list = null;
if(UNQUERY.intValue()==0)
    list = sclocal.querySalesChanges(scVo);
else
    list = new ArrayList();
    
//url����
tempUrl = tempUrl+"&q_pre_product_id="+q_pre_product_id
				+"&q_cust_name="+q_cust_name;
tempUrl = tempUrl+"&start_date="+start_date;
tempUrl = tempUrl+"&end_date="+end_date;
tempUrl = tempUrl+"&pre_status="+pre_status;
tempUrl = tempUrl+"&q_pre_level="+q_pre_level;
tempUrl = tempUrl+"&from_service_man="+from_service_man;
tempUrl = tempUrl+"&to_service_man="+to_service_man;
tempUrl = tempUrl+"&sa_status="+sa_status;
sUrl = sUrl + tempUrl;

String options = Argument.getPreProductListOptions(q_pre_product_id,"","110299",input_operatorCode);
options = options.replaceAll("\"", "'");
%>

<HTML>
<HEAD>
<TITLE>ת���� </TITLE>
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
<!--TABLE ��ͬjs-->
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/jQuery/js/jquery-1.3.2.min.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/tablespan.js"></SCRIPT>
<script language=javascript>
/*��������*/
window.onload = function(){
	initQueryCondition();
}

/*��ѯ����*/
function StartQuery(){
	refreshPage();
}

/*ˢ��*/
function refreshPage(){
	disableAllBtn(true);
	var url = "sales_changes_list.jsp?page=1&pagesize="+ document.theform.pagesize.value;	
	var url = url + '&q_pre_product_id=' + document.theform.q_pre_product_id.value;
	var url = url + '&q_cust_name=' + document.theform.q_cust_name.value;
	var url = url + "&from_service_man="+document.theform.from_service_man.value;
	var url = url + "&to_service_man="+document.theform.to_service_man.value;
	var url = url + "&sa_status="+document.theform.sa_status.value;
	location = url;	
}

/*���ò�Ʒ*/
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
			sl_alert('<%=LocalUtilis.language("message.idIsNotExists",clientLocale)%> ');//����Ĳ�Ʒ��Ų�����
			document.theform.q_productCode.value="";
			document.theform.q_pre_product_id.options[0].selected=true;
		}
	}
	
	nextKeyPress(this);
}

/*��ѯ��Ʒ*/
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
			sl_alert('<%=LocalUtilis.language("message.idIsNotExists",clientLocale)%> ��');//����Ĳ�Ʒ��Ų�����
			document.theform.q_productCode.value="";
			document.theform.q_pre_product_id.options[0].selected=true;
		}
		
		document.theform.q_productCode.focus();					
	}	
}

/*
 *���ú�ͬtable�ķ���
 */
$(document).ready(function(){  
	//_w_table_lefttitle_rowspan("#table3",1);
});


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
			sl_alert('����Ĳ�Ʒ���Ʋ����� ��');//����Ĳ�Ʒ���Ʋ�����
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

// 1. �����ж���ĺϼ���
// 2. sort="num"��ʾ����ʹ����������
// 3. sort="multi"��ʾ�ı��ע�������У�����ʹ��className���ж�
// 4. Ҫ���ִ��Ч��
function sortTable__2() {
	var start = new Date;
	event.cancelBubble = true;
	var the_obj = event.srcElement;
	if (the_obj.tagName != "TD")
		return;

	var tab = this.parentElement.parentElement;
	if (tab.tagName=='TBODY') tab = tab.parentElement;
	if(tab.tagName != "TABLE") return;

	window.status = "���ڶ����ݽ������������Ժ�...";

	if(the_obj.mode == undefined)
		the_obj.mode = false;

	var len = tab.rows.length;

	if (len>0 && tab.rows[len-1].cells[0].innerText.indexOf("��")>=0)
		len--;

	if(the_obj.sort == "num")
		table_sort_num__2(tab, the_obj.cellIndex, the_obj.mode, len);
	else
		table_sort_string__2(tab, the_obj.cellIndex, the_obj.mode, len);

	the_obj.mode = !the_obj.mode; // ���������

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

// ���Ч�ʸ��ߣ�����������multi�����
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
/*�½�*/
function newInfo(){
	if(showModalDialog('sales_changes_list_new.jsp?UNQUERY=1', '', 'dialogWidth:900px;dialogHeight:700px;status:0;help:0')) {
		sl_update_ok();
		location.reload();
	}
}
//�޸�ת����������Ϣ
function modiInfo(serial_no){

	if (showModalDialog('sales_changes_list_new_update.jsp?serial_no='+serial_no+'&cust_name=',
		 	'', 'dialogWidth:540px;dialogHeight:480px;status:0;help:0')) {
		sl_update_ok();
		location.reload();
	}
}

//����ת����������Ϣ
function cancelInfo(serial_no){

	if (showModalDialog('sales_changes_list_new_cancel.jsp?serial_no='+serial_no+'&cust_name=',
		 	'', 'dialogWidth:540px;dialogHeight:480px;status:0;help:0')) {
		sl_update_ok();
		location.reload();
	}
}

//ȷ��ת����������Ϣ
function checkInfo(serial_no){

	if (showModalDialog('sales_changes_list_new_check.jsp?serial_no='+serial_no+'&cust_name=',
		 	'', 'dialogWidth:540px;dialogHeight:480px;status:0;help:0')) {
		sl_update_ok();
		location.reload();
	}
}

//���ת����������Ϣ
function recheckInfo(serial_no){

	if (showModalDialog('sales_changes_list_new_recheck.jsp?serial_no='+serial_no+'&cust_name=',
		 	'', 'dialogWidth:540px;dialogHeight:480px;status:0;help:0')) {
		sl_update_ok();
		location.reload();
	}
}

//�鿴ת����������Ϣ
function loadInfo(serial_no){

	if (showModalDialog('sales_changes_list_new_query.jsp?serial_no='+serial_no+'&cust_name=',
		 	'', 'dialogWidth:540px;dialogHeight:480px;status:0;help:0')) {
		sl_update_ok();
		location.reload();
	}
}
</script>
</HEAD>

<BODY class="BODY body-nox">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="post">
<input type="hidden" name="pagesize">
<!--��ѯ����ģ��-->
<div id="table99">
<div id="queryCondition" class="qcMain" style="display:none;width:540px;">
<table  id="qcTitle" class="qcTitle" align="center" width="99%" cellspacing="0" cellpadding="2">
  <tr>
   <td align="left"><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ��</td><!--��ѯ����-->
   <td align="right">
   <button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose" onclick="javascript:cancelQuery();"></button></td>
  </tr>
</table>
<table>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.productID",clientLocale)%>:</td><!--��Ʒ���-->
		<td >
			<input type="text" name="q_productCode" value="<%= q_productCode%>" onkeydown="javascript:setProduct(this.value);" size="16">&nbsp;
			<button type="button"  class="searchbutton" onclick="javascript:searchProduct(document.theform.q_productCode.value);" />
		</td>
		<td  align="right">��Ʒ���� :</td>
		<td  align="left" >
			<input type="text" name="q_product_name" value="" onkeydown="javascript:nextKeyPress(this);" size="15">&nbsp;
			<button type="button"  class="searchbutton" onclick="javascript:searchProductName(document.theform.q_product_name.value);" />		
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.productName",clientLocale)%>:</td><!--��Ʒ����-->
		<td colspan="3" id="select_id">
			<select size="1" name="q_pre_product_id" onkeydown="javascript:nextKeyPress(this)" class="productname" style="width: 340px;">
				<%=Argument.getPreProductListOptions(q_pre_product_id,"","110299",input_operatorCode)%>
			</select>		
		</td>
	</tr>
	<tr>
		<td align="right" width="20%"><%=LocalUtilis.language("class.customerName",clientLocale)%>:</td><!--�ͻ�����-->
		<td align="left" width="30%">
			<input type="text" name="q_cust_name" value="<%=q_cust_name%>" size="20">
		</td>
		<td align="center" width="20%">ԭ�����ͻ�����:</td>
		<td align="left" width="30%">
			<select size="1" name="from_service_man" onkeydown="javascript:nextKeyPress(this)" style="width: 120px;">
				<%=Argument.getManager_Value(null)%>
			</select>
		</td>
	</tr>
	<tr>
		<td align="center" width="20%">ת������ͻ�����:</td>
		<td align="left" width="30%">
			<select size="1" name="to_service_man" onkeydown="javascript:nextKeyPress(this)" style="width: 120px;">
				<%=Argument.getManager_Value(null)%>
			</select>
		</td>
		<td align="center" width="20%">ת��������״̬:</td>
		<td align="left" width="30%">
			<select size="1" name="sa_status" onkeydown="javascript:nextKeyPress(this)" style="width: 120px;">
				<%=Argument.getSalecChangesStatusOptions(Utility.trimNull(sa_status))%>
			</select>
		</td>
	</tr>
	
	<tr>
		<td align="center" colspan="4">		
		<%if (input_operator.hasFunc(menu_id, 102)) {%>
				<button type="button"  class="xpbutton3" accessKey=o name="btnQuery" onclick="javascript:StartQuery();"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>O</u>)</button>
				<!--ȷ��-->
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
		<!--�½�-->
		<button type="button"  class="xpbutton3" accessKey=f id="queryButton" name="queryButton"><%=LocalUtilis.language("message.query",clientLocale)%> <!--��ѯ-->(<u>F</u>)</button>&nbsp;&nbsp;&nbsp;
		<button type="button"   class="xpbutton3" accessKey=n name="btnNew" title="<%=LocalUtilis.language("message.new",clientLocale)%> " onclick="javascript:newInfo();"><%=LocalUtilis.language("message.new",clientLocale)%> (<u>N</u>)
			</button>&nbsp;&nbsp;&nbsp;<!--�½�-->
	</div>
</div>
</div>
	<br/>
<div style="margin-top:5px">
<table id="table3"  border="1px" bordercolor="#000000" cellspacing="0px" style="border-collapse:collapse"  width="100%" class="tablelinecolor">
	<tr class="trh">
		<td sort="string" style="ie2:expression(onclick=sortTable__2)">��Ʒ����</td>
		<td sort="string" style="ie2:expression(onclick=sortTable__2)">��Ʒ��������</td>
		<td sort="string" style="ie2:expression(onclick=sortTable__2)">�ͻ�����</td>
		<td align="center" width="*" sort="string" style="ie2:expression(onclick=sortTable__2)">��������</td>
		<td align="center" sort="num" style="ie2:expression(onclick=sortTable__2)">���˽��</td>
		<td sort="string" style="ie2:expression(onclick=sortTable__2)">ԭ�����ͻ�����</td>
		<td sort="string" style="ie2:expression(onclick=sortTable__2)">ת������ͻ�����</td>
		<td align="center" sort="num" style="ie2:expression(onclick=sortTable__2)">ת�ý��</td>
		<td align="left">ת��������</td>
		<td sort="string" style="ie2:expression(onclick=sortTable__2)">¼����Ա</td>
		<td align="center" width="*" sort="string" style="ie2:expression(onclick=sortTable__2)">¼��ʱ��</td>
		<td sort="string" style="ie2:expression(onclick=sortTable__2)">��¼״̬</td>
		<td sort="string" style="ie2:expression(onclick=sortTable__2)">ȷ����Ա</td>
		<td align="center" width="*" sort="string" style="ie2:expression(onclick=sortTable__2)">ȷ��ʱ��</td>
<!-- 		<td align="left">ȷ�����</td> -->
		<td sort="string" style="ie2:expression(onclick=sortTable__2)">�����Ա</td>
		<td align="center" width="*" sort="string" style="ie2:expression(onclick=sortTable__2)">���ʱ��</td>
<!-- 		<td align="left">������</td> -->
		<td align="center">�༭</td>
		<td align="center">�鿴</td>
		<td align="center">����</td>
		<td align="center">ȷ��</td>
		<td align="center">���</td>
	</tr>
<%
	//��������
	BigDecimal dz_money = new BigDecimal(0);
	BigDecimal refund_money = new BigDecimal(0);
	for(int j=0; j<list.size(); j++){
		iCount++;
		map = (Map)list.get(j);
		dz_money = dz_money.add(Utility.parseDecimal(Utility.trimNull(map.get("DZ_MONEY")), new BigDecimal(0.00),2,"1"));
		refund_money = refund_money.add(Utility.parseDecimal(Utility.trimNull(map.get("REFUND_MONEY")), new BigDecimal(0.00),2,"1"));
		
		boolean isModi = false;
		boolean isCancel = false;
		boolean isCheck = false;
		boolean isRecheck = false;
		Integer statusDri = Integer.parseInt(map.get("STATUS") == null ? "1" : map.get("STATUS").toString());
		if(statusDri == 1){
			isModi = true;
			isCheck = true;
			String input_man = map.get("TC_INPUT_MAN") == null ? "" : map.get("TC_INPUT_MAN").toString();
			if(input_man != "" && input_man.equals(input_operatorCode+"")){
				//input_operatorCode+""
				isCancel = true;
			}
		}  
		if (statusDri == 3) {
			isRecheck = true;
		}
		
		String statusName = map.get("STATUS_NAME") == null ? "��" : map.get("STATUS_NAME").toString();
		String input_time = map.get("TC_INPUT_TIME") == null ? "" : map.get("TC_INPUT_TIME").toString();
		String input_man = map.get("TC_INPUT_MAN") == null ? "" : map.get("TC_INPUT_MAN").toString();
		String input_man_name = "";
		if(!"".equals(input_man)){
			input_man_name = Argument.getOperator_Name(Utility.parseInt(Utility.trimNull(input_man),new Integer(0)));
		}
		input_time = input_time.substring(0,input_time.lastIndexOf("."));
		String check_time = map.get("CHECK_TIME") == null ? "" : map.get("CHECK_TIME").toString();
		if(!"".equals(check_time)){
			check_time = check_time.substring(0,check_time.lastIndexOf("."));
		}
		String checker = map.get("CHECKER") == null ? "" : map.get("CHECKER").toString();
		String checker_name = "";
		if(!"".equals(checker)){
			checker_name = Argument.getOperator_Name(Utility.parseInt(Utility.trimNull(checker),new Integer(0)));
		}
		String check_reason = Utility.trimNull(map.get("CHECK_REASON"));
		String re_check_time = map.get("RE_CHECK_TIME") == null ? "" : map.get("RE_CHECK_TIME").toString();
		if(!"".equals(re_check_time)){
			re_check_time = re_check_time.substring(0,re_check_time.lastIndexOf("."));
		}
		String re_checker = map.get("RE_CHECKER") == null ? "" : map.get("RE_CHECKER").toString();
		String re_checker_name = "";
		if(!"".equals(re_checker)){
			re_checker_name = Argument.getOperator_Name(Utility.parseInt(Utility.trimNull(re_checker),new Integer(0)));
		}
		String re_check_reason = Utility.trimNull(map.get("RE_CHECK_REASON"));
%>
	<tr>
		<td class="tdh" height="28px;" align="center"><%=Utility.trimNull(map.get("PREPRODUCT_NAME")) %></td>
		<td class="tdh" height="28px;" align="center"><%=Utility.trimNull(map.get("PRE_PRODUCT_TYPE_NAME")) %></td>
		<td align="center" height="28px;"> <%=Utility.trimNull(map.get("CUST_NAME")) %></td>
		<td align="center" height="28px;"> <%=Format.formatDateLine(Utility.parseInt(Utility.trimNull(map.get("TC_DZ_DATE")),new Integer(0))) %></td>
		<td class="center" height="28px;" align="center"> <%=Format.formatMoney(Utility.parseDecimal(Utility.trimNull(map.get("TC_DZ_MONEY")), new BigDecimal(0.00),2,"1")) %></td>
		<td align="center" height="28px;"><%=Utility.trimNull(map.get("FROM_SERVICE_MAN_NAME")) %></td>
		<td align="center" height="28px;"><%=Utility.trimNull(map.get("TO_SERVICE_MAN_NAME")) %></td>
		<td align="center"  height="28px;">&nbsp;<%=Format.formatMoney(Utility.parseDecimal(Utility.trimNull(map.get("CHANGE_MONEY")), new BigDecimal(0.00),2,"1")) %></td>
		<td align="center" height="28px;"><%=Utility.trimNull(map.get("CHANGE_REASON")) %></td>
		<td align="center" height="28px;"><%=input_man_name%></td>
		<td align="right" height="28px;"><%=input_time %></td>
		<td align="center" height="28px;"><%=statusName%></td>
		<td align="center" height="28px;"><%=checker_name%></td>
		<td align="center" height="28px;"><%=check_time%></td>
<%-- 		<td align="center" height="28px;"><%=check_reason%></td> --%>
		<td align="center" height="28px;"><%=re_checker_name%></td>
		<td align="center" height="28px;"><%=re_check_time%></td>
<%-- 		<td align="center" height="28px;"><%=re_check_reason%></td> --%>
		<td>
		<%if (isModi) { //�ɱ༭%>
			<a href="#" onclick="javascript:modiInfo('<%=Utility.trimNull(map.get("TC_SERIAL_NO"))%>');">						 	
			<img src="<%=request.getContextPath()%>/images/edit.gif" width="16" height="16" title="�༭ת������Ϣ"/></a>
		<%} else { %>
		    				 
		<%} %>
		</td>
		<td bgcolor="#FFFFFF" align="center">
			<a href="#" onclick="javascript:loadInfo('<%=Utility.trimNull(map.get("TC_SERIAL_NO"))%>');">						 	
			<img src="<%=request.getContextPath()%>/images/search.gif" width="16" height="16" title="�鿴ת������Ϣ"/></a>
		</td>	
		<td bgcolor="#FFFFFF" align="center">
		<%if (input_operator.hasFunc(menu_id, 103)){ %>	
		<%if (isCancel) { //�ɳ���%>
				<a href="#" onclick="javascript:cancelInfo('<%=Utility.trimNull(map.get("TC_SERIAL_NO"))%>');">					 	
				<img src="<%=request.getContextPath()%>/images/close.gif" width="16" height="16" title="����ת������Ϣ"/></a>
		<%} else { %>
		    				
		<%}} %>
		</td>
		<td bgcolor="#FFFFFF" align="center">
		<%if (input_operator.hasFunc(menu_id, 100)){ %>	
		<%if (isCheck) { //��ȷ��%>
				<a href="#" onclick="javascript:checkInfo('<%=Utility.trimNull(map.get("TC_SERIAL_NO"))%>');">					 	
				<img src="<%=request.getContextPath()%>/images/check.gif" width="16" height="16" title="ȷ��ת������Ϣ"/></a>
		<%} else { %>
		    				
		<%}} %>
		</td>
		<td bgcolor="#FFFFFF" align="center">
		<%if (input_operator.hasFunc(menu_id, 101)){ %>	
		<%if (isRecheck) { //�����%>
				<a href="#" onclick="javascript:recheckInfo('<%=Utility.trimNull(map.get("TC_SERIAL_NO"))%>');">					 	
				<img src="<%=request.getContextPath()%>/images/check.gif" width="16" height="16" title="���ת������Ϣ"/></a>
		<%} else { %>
		    				
		<%}} %>
		</td>
	</tr>
	
<%}%>
	<tr > 
		<td class="tdh" colspan="21" align="center"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%><!--�ϼ�-->&nbsp;<%=list.size()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> <!--��--></b></td>
	</tr>				
</table>
</div>
<%sclocal.remove();%>
</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>