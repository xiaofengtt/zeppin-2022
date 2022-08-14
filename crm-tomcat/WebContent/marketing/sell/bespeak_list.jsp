<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*,enfo.crm.web.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
//获得页面传递变量
Integer q_productId = overall_product_id;
Integer preproductId = new Integer(0);
String sProductId = Utility.trimNull(request.getParameter("q_productId"));
String[] temp = sProductId.split("-");
if (temp.length>0) 
	preproductId = Utility.parseInt(temp[0], new Integer(0));

if (temp.length>1) 
	q_productId = Utility.parseInt(temp[1], overall_product_id);

String q_productCode = Utility.trimNull(request.getParameter("q_productCode"));
String q_cust_name = Utility.trimNull(request.getParameter("q_cust_name"));
String q_pre_code = Utility.trimNull(request.getParameter("q_pre_code"));
Integer q_cust_type = Utility.parseInt(Utility.trimNull(request.getParameter("q_cust_type")),new Integer(0));
BigDecimal min_reg_money = Utility.parseDecimal(request.getParameter("min_reg_money"), new BigDecimal(0),2,"1");//最低登记额度
BigDecimal max_reg_money = Utility.parseDecimal(request.getParameter("max_reg_money"), new BigDecimal(0),2,"1");//最高登记额度
String q_customer_cust_source =  Utility.trimNull(request.getParameter("q_customer_cust_source"));//客户来源
Integer q_group_id = Utility.parseInt(Utility.trimNull(request.getParameter("q_group_id")),new Integer(0));
Integer q_class_detail_id = Utility.parseInt(Utility.trimNull(request.getParameter("q_class_detail_id")),new Integer(0));
Integer q_pre_money_class = Utility.parseInt(request.getParameter("q_pre_money_class"), new Integer(0));
int import_flag = Utility.parseInt(request.getParameter("import_flag"),-1);
Integer import_date = Utility.parseInt(request.getParameter("import_date"),new Integer(Utility.getCurrentDate()));

//页面辅助参数
boolean print_flag = false;
boolean bSuccess = false;
String tempUrl = "";
String[] totalColumn = new String[0];
int t_sPage = Utility.parseInt(sPage,1);
int t_sPagesize = Utility.parseInt(sPagesize,8);
int iCount = 0;
int iCurrent = 0;

//帐套暂时设置
input_bookCode = new Integer(1);

//url设置
tempUrl = tempUrl+"&q_productId="+preproductId+"-"+q_productId;
tempUrl = tempUrl+"&q_productCode="+q_productCode;
tempUrl = tempUrl+"&q_cust_name="+q_cust_name;
tempUrl = tempUrl+"&q_pre_code="+q_pre_code;
tempUrl = tempUrl+"&q_cust_type="+q_cust_type;
tempUrl = tempUrl+"&min_reg_money="+min_reg_money;
tempUrl = tempUrl+"&max_reg_money="+max_reg_money;
tempUrl = tempUrl+"&q_customer_cust_source="+q_customer_cust_source;
tempUrl = tempUrl+"&q_class_detail_id="+q_class_detail_id;
tempUrl = tempUrl+"&q_group_id="+q_group_id;
tempUrl = tempUrl+"&q_pre_money_class="+q_pre_money_class;
sUrl = sUrl + tempUrl;

//获得对象及结果集
PreContractLocal preContract = EJBFactory.getPreContract();
PreContractVO vo = new PreContractVO();

vo.setProduct_id(q_productId);
vo.setCust_name(q_cust_name);
vo.setPre_code(q_pre_code);
vo.setBook_code(input_bookCode);
vo.setInput_man(input_operatorCode);
vo.setCust_type(q_cust_type);
vo.setMax_reg_money(max_reg_money);
vo.setMin_reg_money(min_reg_money);
vo.setCust_source(q_customer_cust_source);
vo.setClassdetail_id(q_class_detail_id);
vo.setCust_group_id(q_group_id);
vo.setPre_product_id(preproductId);
IPageList pageList = preContract.preContract_page_query_crm(vo,totalColumn,t_sPage,t_sPagesize);
List list = pageList.getRsList();

if (list.size()>0) print_flag = true;

//导入预约数据
if (import_flag==1){
	//从字典表里面获得路径:
	DocumentFile2  fiel = new DocumentFile2();
	String file_name = Argument.getDictParamName(8006,"800601");
	fiel.readExcelPreCustContract(file_name,import_date,input_operatorCode);
	bSuccess = true;
}

preContract.remove();
%>
<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("menu.bespeakList",clientLocale)%></TITLE><!--预约列表-->
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
<script language=javascript>
<%if(bSuccess){%>
	sl_alert("预约数据导入成功!");
<%}%>

window.onload = function(){
		initQueryCondition();
		var q_cust_type = document.getElementById("q_cust_type").value;
		var tdId = "td"+q_cust_type;
		document.getElementById(tdId).bgColor ="#99FFFF" ;
	};

//新建到账处理
function newPreMoneyInfo(serial_no, cust_name, product_name, pre_money, rg_money) {
	if(showModalDialog('/marketing/sell/pre_money_detail_add.jsp?serial_no='+serial_no+'&cust_name='+cust_name
			+'&product_name='+product_name+'&pre_money='+pre_money+'&rg_money='+rg_money, 
			'', 'dialogWidth:480px;dialogHeight:360px;status:0;help:0') != null) {
		sl_update_ok();
		location.reload();
	}
}

/*查询功能*/
function StartQuery(){
	refreshPage();
}

/*新增*/
function newInfo(){	
	disableAllBtn(true);
	location="bespeak_add.jsp";
}

/*修改*/
function showInfo(serial_no){
	disableAllBtn(true);
	location="bespeak_edit.jsp?serial_no="+ serial_no;
}

//删除
function delInfo(){
	if(confirmRemove(document.theform.serial_no)){
		disableAllBtn(true);
		document.theform.action = "bespeak_del.jsp";
		document.theform.submit();
	}
}

/*刷新*/
function refreshPage(){
	disableAllBtn(true);
		
	var url = "?page=1&pagesize="+ document.theform.pagesize.value;	
	url += '&q_productId=' + document.theform.q_productId.value;
	url += '&q_cust_name=' + document.theform.q_cust_name.value;
	url += '&q_pre_code=' + document.theform.q_pre_code.value;
	url += '&q_productCode=' + document.theform.q_productCode.value;
	url += '&q_cust_type=' +document.theform.q_cust_type.value;
	var preMoneyClass = document.theform.q_pre_money_class.value;
	var minPreMoney = document.theform.min_reg_money.value;
	var maxPreMoney = document.theform.max_reg_money.value;
	if (preMoneyClass==1) { // 预约金额>=300万
		minPreMoney = 3000000;
		maxPreMoney = 0;
	} else if (preMoneyClass==2) { // 预约金额<300万
		maxPreMoney = 2999999.99;
		minPreMoney = 0;
	}
	url += '&min_reg_money=' + minPreMoney;
	url += '&max_reg_money=' + maxPreMoney;
	url += '&q_customer_cust_source=' + document.theform.q_customer_cust_source.value;
	url += '&q_group_id=' +document.theform.q_group_id.value;
	url += '&q_class_detail_id=' +document.theform.q_class_detail_id.value;
	url += '&q_pre_money_class=' +preMoneyClass;
	location.search = url;
}

/*查询产品*/
function searchProduct(value){
	var prodid=0;
	if(value!=""){
        var j = value.length;
        
		for(i=0;i<document.theform.q_productId.options.length;i++){
			if(document.theform.q_productId.options[i].text.substring(0,j)==value){
				document.theform.q_productId.options[i].selected=true;
				prodid = document.theform.q_productId.options[i].value;
				break;
			}	
		}
		
		if (prodid==0){
			sl_alert("<%=LocalUtilis.language("message.idIsNotExists",clientLocale)%> ！");//输入的产品编号不存在
			document.theform.q_productCode.value="";
			document.theform.q_productId.options[0].selected=true;
		}
		
		document.theform.q_productCode.focus();					
	}	
}

/*设置产品*/
function setProduct(value){
	var prodid=0;
	
	if(event.keyCode == 13 && value != ""){
        var j = value.length;
        
		for(i=0;i<document.theform.q_productId.options.length;i++){
			if(document.theform.q_productId.options[i].text.substring(0,j)==value){
				document.theform.q_productId.options[i].selected=true;
				prodid = document.theform.q_productId.options[i].value;
				break;
			}	
		}
		
		if (prodid==0){
			sl_alert("<%=LocalUtilis.language("message.idIsNotExists",clientLocale)%> ！");//输入的产品编号不存在
			document.theform.q_productCode.value="";
			document.theform.q_productId.options[0].selected=true;
		}
	}
	
	nextKeyPress(this);
}

function writetxtfile(){
	if(document.theform.q_productId.value==0){
		alert("<%=LocalUtilis.language("message.choossProduct",clientLocale)%> !"); //请选择产品
		return false;
	}
	
	if (confirm("<%=LocalUtilis.language("message.exportDataTip",clientLocale)%> ？")) { //确认要导出数据吗
		var url = "bespeak_outExcel.jsp?book_code=<%=input_bookCode%>&input_man=<%=input_operatorCode%>&page=1&pagesize="+ document.theform.pagesize.value;	
		var url = url + '&q_productId=' + document.theform.q_productId.value;
		var url = url + '&q_cust_name=' + document.theform.q_cust_name.value;
		var url = url + '&q_pre_code=' + document.theform.q_pre_code.value;
		var url = url + '&q_productCode=' + document.theform.q_productCode.value;
		var url = url + '&q_cust_type=' +document.theform.q_cust_type.value;
		var url = url + '&min_reg_money=' + document.theform.min_reg_money.value;
		var url = url + '&max_reg_money=' + document.theform.max_reg_money.value;
		var url = url + '&q_customer_cust_source=' + document.theform.q_customer_cust_source.value;
		var url = url + '&q_group_id=' +document.theform.q_group_id.value;
		var url = url + '&q_class_detail_id=' +document.theform.q_class_detail_id.value;
	
		location = url;
	}
	
	showWaitting(0);	
}

function importExcel(){
	if(!sl_checkDate(document.theform.import_date_picker,'预约信息导入时间 ')) return false;//预约信息导入时间
	syncDatePicker(document.theform.import_date_picker, document.theform.import_date);
	if (confirm("确认要导入预约数据吗？")) //确认要导出数据吗
		location = 'bespeak_list.jsp?import_flag=1&import_date='+document.theform.import_date.value;
}

/*转换客户类别*/
function changeCustType(flag){
	document.getElementById("q_cust_type").value=flag;
	refreshPage();
}

/*查看明细*/
function setiteminfor(serial_no){
	var v_tr =  "detailsTr"+serial_no;
	var v_table = "detailsTable"+serial_no;
	var v_flag = "detailsFlag_display"+serial_no;
	var v_div = "detailsDiv"+serial_no;
	var v_image = "detailsImage"+serial_no;
	var flag = document.getElementById(v_flag).value;
	
	if (flag==0) {		
		document.getElementById(v_tr).style.display="";
		document.getElementById(v_table).style.display="";
		
		if(document.getElementById(v_div).offsetHeight>200){
			document.getElementById(v_div).style.height=200;
		}
		
		document.getElementById(v_flag).value = 1;
		document.getElementById(v_image).src="<%=request.getContextPath()%>/images/up_enabled.gif";
		
	} else if (flag==1) {
		document.getElementById(v_tr).style.display="none";
		document.getElementById(v_table).style.display="none";
		document.getElementById(v_flag).value = 0;	
		document.getElementById(v_image).src="<%=request.getContextPath()%>/images/down_enabled.gif";
	}
}

function showStatistics() { // 预约统计分析
	var productId = document.theform.q_productId.value;
	if (productId=="0-0") {
		sl_alert("请选择要统计的产品！");
		document.theform.q_productId.focus();
		return;
	}
	showModalDialog("<%=request.getContextPath()%>/marketing/sell/bespeak_statistics.jsp?productId="+productId, '','dialogWidth:800px;dialogHeight:600px;status:0;help:0');
}

function sortTable__2() {
	var start = new Date;
	event.cancelBubble = true;
	var the_obj = event.srcElement;
	if (the_obj.tagName != "TD") return;

	var tab = this.parentElement.parentElement;
	if (tab.tagName=='TBODY') tab = tab.parentElement;
	if (tab.tagName != "TABLE") return;

	window.status = "正在对数据进行排序，请您稍候...";

	if(the_obj.mode == undefined) the_obj.mode = false;

	var len = tab.rows.length;

	if (len > 0 && tab.rows[len-1].cells[0].innerText.indexOf("合") >= 0) len--;
	
	if(the_obj.sort == "num")
		table_sort_num__2(tab, the_obj.cellIndex, the_obj.mode, len);
	else
		table_sort_string(tab, the_obj.cellIndex, the_obj.mode, len);
	the_obj.mode = !the_obj.mode;

	window.status = "";
}

function table_sort_num__2(obj, n, mode, len) {
	var i, j;
	var tab = obj;
	multi = (tab.sort == "multi" || tab.sort == "MULTI");
	if (!multi)
		return table_sort_num_fast__2(obj, n, mode, len);

	for(i = 1; i < len - 1; i++) {
		if (tab.rows[i].cells[0].innerText == "") continue;
		if (tab.rows[i].className == "") continue;

		for(j = i + 1;j < len; j++) {
			if (tab.rows[j].cells[0].innerText == "") continue;
			if (tab.rows[j].className == "") continue;
			if (mode) {
				if (sl_parseFloat(tab.rows[i].cells[n].innerText) < sl_parseFloat(tab.rows[j].cells[n].innerText)) {
					tab.rows[i].swapNode(tab.rows[j]);
					if (multi) swapHistoryRow(tab, i, j, len);
				}
			} else {
				if (sl_parseFloat(tab.rows[i].cells[n].innerText) > sl_parseFloat(tab.rows[j].cells[n].innerText)) {
					tab.rows[i].swapNode(tab.rows[j]);
					if (multi) swapHistoryRow(tab, i, j, len);
				}
			}
		}
	}
}

function table_sort_num_fast__2(the_tab, col, mode, len) {
	var tab_arr = new Array();
	for (var i=1; i<len; i+=2) {
		if (the_tab.rows[i].cells[col]==null || the_tab.rows[i].cells[col].innerText == "")
			continue;
		tab_arr.push(new Array(the_tab.rows[i].cells[col].innerText, the_tab.rows[i], the_tab.rows[i+1]));
	}
	function SortArr(mode) {
		return function (arr1, arr2) {
			var flag;
			var a,b;
			a = sl_parseFloat(arr1[0]);
			b = sl_parseFloat(arr2[0]);
			//flag=mode?b.localeCompare(a):a.localeCompare(b);//flag=mode?(a<b?1:(a>b?-1:0)):(a>b?1:(a<b?-1:0));
			if (mode)
				return b-a;
			else
				return a-b;
			//return flag;
		};
	}
	tab_arr.sort(SortArr(mode));
		
	for(i=0;i<tab_arr.length;i++) {
		the_tab.firstChild.appendChild(tab_arr[i][1]);
		the_tab.firstChild.appendChild(tab_arr[i][2]);
	}
	for(i = 1; i < the_tab.rows.length - tab_arr.length*2; i++)
		the_tab.firstChild.appendChild(the_tab.rows[1]);
}
<%--alert(<%=user_id%>);--%>
</script>
</HEAD>

<BODY class="BODY">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="get">
<input type="hidden" id="q_cust_type" name="q_cust_type" value="<%=q_cust_type%>" />
<!--查询功能模块-->
<div id="queryCondition" class="qcMain" style="display:none;width:450px;">
<table  id="qcTitle" class="qcTitle" align="center" width="99%" cellspacing="0" cellpadding="2">
  <tr>
   <td align="left"><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ：</td><!--查询条件-->
   <td align="right">
   <button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose" onclick="javascript:cancelQuery();"></button></td>
  </tr>
</table>
<table>
	<tr>
	 	 <td align="right"><%=LocalUtilis.language("class.customerSource",clientLocale)%> :</td><!--客户来源-->
	 	 <td>
	 	 	<select onkeydown="javascript:nextKeyPress(this)" size="1" name="q_customer_cust_source" style="width: 110px">
				<%=Argument.getCustomerSourceOptions(q_customer_cust_source)%>
			</select>
	 	 </td>
        <td align="right">&nbsp;&nbsp;&nbsp;<%=LocalUtilis.language("class.qPreCode",clientLocale)%> <!--预约号-->:</td>
		<td align="left"><input type="text" name="q_pre_code" value="<%=q_pre_code%>"onkeydown="javascript:nextKeyPress(this)" size="15"/>
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.customerName",clientLocale)%> <!--客户名称-->:</td>
		<td align="left" colspan="3"><input type="text" name="q_cust_name" value="<%=q_cust_name%>"onkeydown="javascript:setProduct(this.value);" size="52"></td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.regMoney",clientLocale)%> :</td><!--登记额度-->
		<!--从-->
        <td colspan="3">
        		<%=LocalUtilis.language("message.start",clientLocale)%> 
        		<input type="text" onkeydown="javascript:nextKeyPress(this)" maxlength="10" name="min_reg_money" size="20"
				value="<%=Utility.trimNull(min_reg_money)%>" /> 
                <%=LocalUtilis.language("message.end",clientLocale)%> <!-- 到 -->
				<input type="text" onkeydown="javascript:nextKeyPress(this)" maxlength="10" name="max_reg_money" size="20" 
				value="<%=Utility.trimNull(max_reg_money)%>" />
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
	<div align="left">
		<img border="0" src="<%=request.getContextPath()%>/images/member.gif" align="absmiddle" width="32" height="28">
		<font color="#215dc6"><b><%=menu_info%></b></font>
	</div>
<%--if(user_id.intValue()==8 || user_id.intValue()==17){ %>
预约信息导入时间:
<INPUT TYPE="text" NAME="import_date_picker" class=selecttext value="<%=Format.formatDateLine(Utility.getCurrentDate())%>">
<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.import_date_picker,theform.import_date_picker.value,this);" tabindex="13">
<INPUT TYPE="hidden" NAME="import_date"   value="">
<%} --%>
	<div align="right">
		<%if (user_id.intValue()==25 || user_id.intValue()==999) { //25重庆信托 %>
		<button type="button"  class="xpbutton5" accessKey=s id="statButton" name="statButton" onclick="javascript:showStatistics();">预约统计分析(<u>S</u>)</button>&nbsp;&nbsp;&nbsp; 
		<%} %>
		<button type="button"  class="xpbutton3" accessKey=q id="queryButton" name="queryButton"><%=LocalUtilis.language("message.query",clientLocale)%> <!--查询-->(<u>Q</u>)</button>&nbsp;&nbsp;&nbsp; 
		<%if (input_operator.hasFunc(menu_id, 100)) {%>
			<button type="button"  class="xpbutton3" accessKey=n name="btnNew" title="<%=LocalUtilis.language("message.new",clientLocale)%> " onclick="javascript:newInfo();"><%=LocalUtilis.language("message.new",clientLocale)%> (<u>N</u>)
			</button>&nbsp;&nbsp;&nbsp;<!--新建-->
		<%}%>		
		<%if (input_operator.hasFunc(menu_id, 101)) {%>
			<button type="button"  class="xpbutton3" accessKey=d name="btnCancel" title="<%=LocalUtilis.language("message.delete",clientLocale)%> "	onclick="javascript:delInfo();"><%=LocalUtilis.language("message.delete",clientLocale)%> (<u>D</u>)
			</button>&nbsp;&nbsp;&nbsp; <!--删除-->
		<%}%>	
		<%--if(user_id.intValue()==8 || user_id.intValue()==17){ %>
		<button type="button"   class="xpbutton5" name="btnOk" title="导入数据" onclick="javascript:importExcel();">导入数据</button><!--导入数据-->&nbsp;&nbsp;&nbsp;
		<%} --%>
		<%-- <button type="button"   class="xpbutton5" name="btnOk" <%if (!print_flag)out.print("disabled");%> title="<%=LocalUtilis.language("menu.generateXSL&Export",clientLocale)%> " onclick="javascript:writetxtfile();"><%=LocalUtilis.language("message.exportData",clientLocale)%> 
		</button><!--生成XSL文件并导出--><!--导出数据--> --%>
	</div>
	<hr noshade color="#808080" size="1" width="100%">
</div>

<div id="headDiv" style="margin-top:5px">
	<table cellSpacing="1" cellPadding="2"  bgcolor="#CCCCCC">
			<tr style="background:F7F7F7;">
				<td width="50px" align="center" id="td0" <%if (q_cust_type.intValue()==0) out.print(" class='active'"); %>><font size="2" face="微软雅黑"><a href="#" onclick="javascript:changeCustType(0);" class="a2"><%=LocalUtilis.language("message.all",clientLocale)%> </a></font></td>
				<!--全部-->
				<td width="50px" align="center" id="td1" <%if (q_cust_type.intValue()==1) out.print(" class='active'"); %>><font size="2" face="微软雅黑"><a href="#" onclick="javascript:changeCustType(1);" class="a2">个人</a></font></td>
				<!--个人-->
				<td width="50px" align="center" id="td2" <%if (q_cust_type.intValue()==2) out.print(" class='active'"); %>><font size="2" face="微软雅黑"><a href="#" onclick="javascript:changeCustType(2);" class="a2">机构</a></font></td>
				<!--机构-->
				<td>
					<select size="1" colspan="3"name="q_productId"	onkeydown="javascript:nextKeyPress(this)" class="productname" onchange="javascript:StartQuery();">					
						<%=Argument.getProductListOptions2(new Integer(2), "0", input_operatorCode, preproductId+"-"+q_productId)
						/*Argument.getProductListOptions(input_bookCode,q_productId,"",input_operatorCode,0)*/%>
					</select>
				</td>
				<td>
					<input type="text" name="q_productCode" value="<%=q_productCode%>" onkeydown="javascript:setProduct(this.value);" size="15">&nbsp;
					<button type="button"  class="searchbutton" onclick="javascript:searchProduct(document.theform.q_productCode.value);" />
				</td>
				<td align="center" id="td4">
					<select name="q_group_id" id="q_group_id" onkeydown="javascript:nextKeyPress(this)" style="width: 120px;" onchange="javascript:StartQuery();">
						<%=Argument.getCustGroupOption2(new Integer(0),q_group_id)%>
					</select>
				</td>
				<td align="center" id="td4">
					<select name="q_class_detail_id" id="q_class_detail_id" onkeydown="javascript:nextKeyPress(this)" style="width: 120px;" onchange="javascript:StartQuery();">
						<option>请选择关注度</option>
						<%=Argument.getCustClassOption(new Integer(30),q_class_detail_id)%>
					</select>
				</td>
				<td align="center" id="td5">
					<select name="q_pre_money_class" id="q_pre_money_class" onkeydown="javascript:nextKeyPress(this)" style="width: 120px;" onchange="javascript:StartQuery();">
						<%=Argument.getPreMoneyClassOptions(q_pre_money_class)%>
					</select>
				</td>
			</tr> 
	</table>
</div>

<div style="margin-top:5px">
<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%">
	<tr class="trh">
		<td align="center" width="120px">
			<input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAllBox(document.theform.serial_no,this);">
			&nbsp;&nbsp;<%=LocalUtilis.language("class.custName4",clientLocale)%> 
		</td><!--客户姓名-->
		<td align="center" width="*">客户类别</td><!--客户类别-->
		<td align="center" ><%=LocalUtilis.language("class.qPreCode",clientLocale)%> </td><!--预约号-->
		<td align="center" ><%=LocalUtilis.language("class.factNum",clientLocale)%> </td><!--份数-->
		<td align="center" style="ie2:expression(onclick=sortTable__2)" sort="num"><%=LocalUtilis.language("class.money",clientLocale)%> </td><!--金额--><!--金额(万元)-->
		<td align="center">到账金额 </td>
		<td align="center" width="*">预期收益率</td>
		<td align="center" ><%=LocalUtilis.language("class.contact",clientLocale)%> </td><!--联系人-->
		<td align="center" ><%=LocalUtilis.language("login.telephone",clientLocale)%> </td><!--联系电话-->
		<td align="center" width="*"><%=LocalUtilis.language("class.customerSource",clientLocale)%> </td><!-- 客户来源 -->
		<%--td align="center" ><%=LocalUtilis.language("class.custGroups",clientLocale)%></td> --%>
		<%if(q_class_detail_id.intValue()>0){ %>
		<td align="center" >关注度</td>
		<%}%>
		<td align="center" ><%=LocalUtilis.language("class.validDays2",clientLocale)%> </td><!--有效期-->
		<td align="center" ><%=LocalUtilis.language("class.serviceStatusName2",clientLocale)%> </td><!--状态-->
		<td align="center"><%=LocalUtilis.language("class.details",clientLocale)%></td><!--详细-->	
		<%if (user_id.intValue()!=15 && user_id.intValue()!=24 /*建信,厦门*/) {/*厦门信托不在此做到账处理*/%>
		<td align="center">到账处理</td>
		<%}%>
		<td align="center" ><%=LocalUtilis.language("message.edit",clientLocale)%> </td><!--编辑-->
	</tr>
<%
	//声明参数
	Integer serial_no;
	Integer cust_id; 
	String cust_name = null;
	String cust_type_name ="";
	String pre_code= null;
	Integer rg_num = new Integer(0);
	Integer pre_num = new Integer(0);
	BigDecimal pre_money = new BigDecimal(0);
	BigDecimal pay_money = new BigDecimal(0);
	Integer rg_num_total = new Integer(0);
	Integer pre_num_total = new Integer(0); 
	BigDecimal pre_money_total = new BigDecimal(0);
	BigDecimal pay_money_total = new BigDecimal(0);
	BigDecimal exp_rate1;
	BigDecimal exp_rate2;
	Integer link_man;
	String h_tel = null;
	String o_tel = null;
	String mobile = null;
	String bp = null;
	Integer preDate;
	Integer expRegDate;
	Integer valid_days;
	String pre_status_name;
	String summary;
	String inputTime;
	String custSourceName;
		
	Iterator iterator = list.iterator();
	while(iterator.hasNext()){
		iCount++;
		Map map = (Map)iterator.next();
		
		//读数据
		serial_no = Utility.parseInt(Utility.trimNull(map.get("SERIAL_NO")),new Integer(0));
		cust_id = Utility.parseInt(Utility.trimNull(map.get("CUST_ID")),new Integer(0));
		cust_name = Utility.trimNull(map.get("CUST_NAME"));
		cust_type_name =  Utility.trimNull(map.get("CUST_TYPE_NAME"));
		pre_code = Utility.trimNull(map.get("PRE_CODE"));
		rg_num = Utility.parseInt(Utility.trimNull(map.get("RG_NUM")),new Integer(0));
		rg_num_total =new Integer(rg_num_total.intValue() + rg_num.intValue());
		pre_num = Utility.parseInt(Utility.trimNull(map.get("PRE_NUM")),new Integer(0));
		pre_num_total = new Integer(pre_num_total.intValue()+pre_num.intValue());
		pre_money = Utility.parseDecimal(Utility.trimNull(map.get("PRE_MONEY")), new BigDecimal(0.00),2,"1");
		pay_money = Utility.parseDecimal(Utility.trimNull(map.get("DZ_MONEY")), new BigDecimal(0.00),2,"1");
		pre_money_total = pre_money_total.add(pre_money);
		pay_money_total = pay_money_total.add(pay_money);

		exp_rate1 = Utility.parseDecimal(Utility.trimNull(map.get("EXP_RATE1")), new BigDecimal(0.0)).multiply(new BigDecimal(100.0));
		exp_rate1 = exp_rate1.setScale(2);
		exp_rate2 = Utility.parseDecimal(Utility.trimNull(map.get("EXP_RATE2")), new BigDecimal(0.0)).multiply(new BigDecimal(100.0));
		exp_rate2 = exp_rate2.setScale(2);
		//exp_rate2 = Utility.parseDecimal(Utility.trimNull(map.get("EXP_RATE1")), new BigDecimal(0.00),2,"100"); // 2:小数点后2位
		//exp_rate2 = Utility.parseDecimal(Utility.trimNull(map.get("EXP_RATE2")), new BigDecimal(0.00),2,"100");
		
		link_man = Utility.parseInt(Utility.trimNull(map.get("LINK_MAN")),new Integer(0));
		h_tel = Utility.trimNull(map.get("H_TEL"));
		o_tel = Utility.trimNull(map.get("O_TEL"));
		mobile = Utility.trimNull(map.get("MOBILE"));
		bp = Utility.trimNull(map.get("BP"));
		preDate = Utility.parseInt(Utility.trimNull(map.get("PRE_DATE")),new Integer(0));
		expRegDate = Utility.parseInt(Utility.trimNull(map.get("EXP_REG_DATE")),new Integer(0));
		valid_days = Utility.parseInt(Utility.trimNull(map.get("VALID_DAYS")),new Integer(0));
		pre_status_name = Utility.trimNull(map.get("PRE_STATUS_NAME"));
		summary = Utility.trimNull(map.get("SUMMARY"));		
		inputTime =  Utility.trimNull(map.get("INPUT_TIME")).substring(0,16);
		custSourceName = Utility.trimNull(map.get("CUST_SOURCE_NAME"));
		String pre_status = (String)map.get("PRE_STATUS");

		String product_name = (String)map.get("PRODUCT_NAME");
		BigDecimal rg_money = Utility.parseDecimal(Utility.trimNull(map.get("RG_MONEY")), new BigDecimal(0.00),2,"1");
%>
	<tr class="tr<%=(iCount%2)%>">
		<td class="tdh" align="center">
			<table border="0" width="100%" cellspacing="0" cellpadding="0">
				<tr>
					<td width="10%"><input type="checkbox" name="serial_no"	value="<%=serial_no%>" class="selectAllBox"></td>
					<td width="90%" align="left"><input type="text" class="ednone" value="<%=cust_name%>" readonly></td>
				</tr>
			</table>
		</td>
		<td align="center" ><%=cust_type_name%></td>
		<td align="center" ><%=pre_code%></td>
		<td align="center" ><%=rg_num%>/<%=pre_num%></td>
		<td align="right"><%=Format.formatMoney(pre_money)%></td>
		<td align="right"><%=Format.formatMoney(pay_money)%></td>
		<td align="center" width="100px"><%=exp_rate1%>%<%=exp_rate1.equals(exp_rate2)?"":"-"+exp_rate2+"%"%></td>
		<td align="center" ><%=Argument.getOperator_Name(link_man)%></td>
		<td align="center" ><input type="text" class="ednone" value="<%=Utility.trimNull(Argument.getALLTEL(h_tel,o_tel,mobile,bp))%>" readonly></td>		
		<td align="center" ><%=custSourceName%></td>
		<%--td align="center" ><%=Utility.trimNull(Argument.getCustGroups(cust_id))%></td>--%>
		<%if(q_class_detail_id.intValue()>0){ %>
		<td align="center" ><%=Utility.trimNull(map.get("CLASSDETAIL_NAME"))%></td>
		<%}%>
		<td align="center" ><%=valid_days%><%=LocalUtilis.language("message.days",clientLocale)%> </td><!--天-->
		<td align="center" ><%=pre_status_name%></td>
         <td align="center">
         	<button type="button"  class="xpbutton2" name="" onclick="javascript:setiteminfor(<%=serial_no%>);">
     			<IMG id="detailsImage<%=serial_no%>" src="<%=request.getContextPath()%>/images/down_enabled.gif"  width="7" height="9">         		
         	</button>
         	<input type="hidden" id="detailsFlag_display<%=serial_no%>" name="detailsFlag_display<%=serial_no%>" value="0">             
         </td>		
		<%if (user_id.intValue()!=15 && user_id.intValue()!=24 /*建信,厦门*/) {%>
		<td align="center" >
			<%if (!"111304".equals(pre_status)){%>
				<a href="#" onclick="javascript:newPreMoneyInfo('<%=serial_no%>','<%=cust_name%>','<%=product_name%>','<%=pre_money%>','<%=rg_money%>');">
					<img src="<%=request.getContextPath()%>/images/FUNC20076.gif" width="16" height="16" title="到账处理"/>
				</a>
			<%} %>
		</td>
		<%} %>		
		<td align="center" >		
			<%if (input_operator.hasFunc(menu_id, 102)) {%>
		       	<!--<button type="button"  class="xpbutton2" name="btnEdit" onclick="javascript:showInfo(<%=serial_no%>);">&gt;&gt;</button>-->
		       	<a href="javascript:showInfo(<%=serial_no%>);">
               		<img border="0" src="<%=request.getContextPath()%>/images/edit2.gif" width="16" height="16">
               	 </a>
			<%}%>
		</td> 
	</tr>

	<tr id="detailsTr<%=serial_no%>" style="display: none">
		<td align="center" bgcolor="#FFFFFF" <%if(q_class_detail_id.intValue()>0){ %>colspan="16" <%}else{%>colspan="15"<%} %>>
			<div id="detailsDiv<%=serial_no%>" style="overflow-y:auto;" align="center">
				<table id="detailsTable<%=serial_no%>" border="0" width="100%" bgcolor="#000000" cellspacing="1">
					<tr style="background:F7F7F7;" >
						<td align="right" width="157px" ><%=LocalUtilis.language("class.preDate",clientLocale)%> ：</td><!--预约日期-->
						<td  width="*"><%=Utility.trimNull(Format.formatDateCn(preDate))%></td>
					</tr>
					<tr style="background:F7F7F7;" >
						<td align="right">预计认购日期 ： </td>
						<td  width="*"><%=Utility.trimNull(Format.formatDateCn(expRegDate))%></td>
					</tr>
					<tr style="background:F7F7F7;" >
						<td align="right"><%=LocalUtilis.language("class.customerSummary",clientLocale)%>  ： </td><!-- 备注 -->
						<td  width="*"><input type="text" class="ednone" value="<%=summary%>" readonly></td>
					</tr>
					<tr style="background:F7F7F7;" >
						<td align="right">录入时间 ： </td>
						<td  width="*"><%=inputTime%></td>
					</tr>
				</table>
			</div>
		</td>
	</tr>  
<%}
	
for(int i=0;i<(t_sPagesize-iCount);i++){%>  
       <tr class="tr<%=i%2%>">
         <td align="center"></td>
         <td align="center"></td>
         <td align="center"></td>
         <td align="center"></td>
         <td align="center"></td>
         <td align="center"></td>
         <td align="center"></td>
		 <td align="center"></td>
         <td align="center"></td>
         <td align="center"></td>
         <td align="center"></td>
         <%if(q_class_detail_id.intValue()>0){ %>
         <td align="center"></td>
         <%}%>
         <td align="center"></td>
         <td align="center"></td>
		<%if (user_id.intValue()!=15 && user_id.intValue()!=24 /*建信,厦门*/) {%>
         <td align="center"></td>
		<%}%>
         <td align="center"></td>       
       </tr>           
<%}%>
	
	<tr class="trbottom">
		<td class="tdh" align="left"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%><!--合计-->&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> <!--项--></b></td>
		 <td align="center"></td>
         <td align="center"></td>
         <td align="center" ><%=rg_num_total%>/<%=pre_num_total%></td>
		 <td align="right" ><%=Format.formatMoney(pre_money_total)%></td>
		 <td align="right" ><%=Format.formatMoney(pay_money_total)%></td>
         <td align="center"></td>
         <td align="center"></td>
         <td align="center"></td>
         <td align="center"></td>
         <td align="center"></td>
     <%if(q_class_detail_id.intValue()>0){ %>
         <td align="center"></td>
     <%}%>
         <td align="center"></td>
         <td align="center"></td>
      <%if (user_id.intValue()!=15 && user_id.intValue()!=24 /*建信,厦门*/) {%>
		<td align="center"></td>
	  <%} %>
         <td align="center"></td>
	</tr>				
</table>
</div>
<br>
<div>
	<%=pageList.getPageLink(sUrl,clientLocale)%>
</div>
</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>