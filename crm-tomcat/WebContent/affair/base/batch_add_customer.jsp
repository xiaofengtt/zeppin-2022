<%@ page contentType="text/html; charset=GBK" 
	import="enfo.crm.customer.*,enfo.crm.affair.*,enfo.crm.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.marketing.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
Integer op_code = Utility.parseInt(request.getParameter("op_code"), new Integer(0));
if (op_code.intValue()==0) op_code = input_operatorCode;
String op_name = Argument.getOpName(op_code);
 %>
 
<HTML>
<HEAD>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<title><%=LocalUtilis.language("menu.batchAddCustomers",clientLocale)%> </title><!-- 客户批量新增 -->
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<base target="_self">

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/investment.js"></SCRIPT>

<script language=javascript>
/*客户信息显示控制*/
function showQueryInfo(){
	var s=5;
	var minheight=1;
	var maxheight=200;	
	var flag = document.getElementById("show_image").title;

	if(document.getElementById("query_info").style.pixelHeight==0){
		document.getElementById("query_info").style.pixelHeight=minheight;
	}
	
	if(flag=="next"){
		 document.getElementById("query_info").style.pixelHeight+=s;
		
		 if(document.getElementById("table_view").style.pixelHeight>200){
		 	document.getElementById("table_view").style.pixelHeight-=s;
		 }
		 
		 if(document.getElementById("query_info").style.pixelHeight<maxheight){
		   setTimeout("showQueryInfo()",1);
		 }else{		
		    document.getElementById("show_image").src = "<%=request.getContextPath()%>/images/previous_down.gif";
		    document.getElementById("show_image").title = "previous_down";
		 }		
		
	}else if(flag=="previous_down"){
		document.getElementById("query_info").style.pixelHeight-=s;
		document.getElementById("tableList").style.pixelHeight+=s;
		//document.getElementById("tableList").style.height=document.getElementById("tableList").style.height+s;
		
		if(document.getElementById("query_info").style.pixelHeight>minheight){
		   setTimeout("showQueryInfo()",1);
		}else{		
		    document.getElementById("show_image").src = "<%=request.getContextPath()%>/images/next.gif";
		    document.getElementById("show_image").title = "next";
		}			
	}
}

function checkCust(){
	var retval =[];
	var cust_id = window.frames['ifrPage'].document.getElementsByName('cust_id');
	var cust_no = window.frames['ifrPage'].document.getElementsByName('cust_no');
	var cust_name = window.frames['ifrPage'].document.getElementsByName('cust_name');
	var cust_type_name = window.frames['ifrPage'].document.getElementsByName('cust_type_name');
	for(i=0;i<cust_id.length-1;i++){
		if (cust_id[1+i].checked){
			retval.push(
				{"cust_id":cust_id[1+i].value, 
				 "cust_no":cust_no[i].value, 
				 "cust_name":cust_name[i].value,
				 "cust_type_name":cust_type_name[i].value}
			);
		}
	}
	return retval;
}

function selectAllBox2(){
	var cust_id = window.frames["ifrPage"].document.theform.cust_id;
	
}

/*保存*/
function submitAction(){		
	if (document.all.ifrPage.src=='') {
		sl_alert("请先查询您要添加的客户！");//请选定要添加的客户
		return;
	}

	if(checkedCount(window.frames["ifrPage"].document.theform.cust_id) == 0){
		sl_alert("<%=LocalUtilis.language("message.selectCustomers",clientLocale)%> ！");//请选定要添加的客户
		return;
	}
	window.returnValue = checkCust();;
	window.close();	
}

function setProduct(value){
	prodid=0;
	if (event.keyCode == 13 && value != "")
	{
        j = value.length;
		for(i=0;i<document.theform.product_id.options.length;i++)
		{
			if(document.theform.product_id.options[i].text.substring(0,j)==value)
			{
				document.theform.product_id.options[i].selected=true;
				prodid = document.theform.product_id.options[i].value;
				break;
			}	
		}
		if (prodid==0)
		{
			sl_alert('<%=LocalUtilis.language("message.idIsNotExists",clientLocale)%> !');//输入的产品编号不存在
			document.theform.productid.value="";
			document.theform.product_id.options[0].selected=true;	
		}			
	}
	nextKeyPress(this);
}

function searchProduct(value){
	prodid=0;
	if (value != "")
	{
        j = value.length;
		for(i=0;i<document.theform.product_id.options.length;i++)
		{
			if(document.theform.product_id.options[i].text.substring(0,j)==value)
			{
				document.theform.product_id.options[i].selected=true;
				prodid = document.theform.product_id.options[i].value;
				break;
			}	
		}
		if (prodid==0)
		{
			sl_alert('<%=LocalUtilis.language("message.idIsNotExists",clientLocale)%> !');//输入的产品编号不存在
			document.theform.productid.value="";
			document.theform.product_id.options[0].selected=true;	
		}
		document.theform.product_id.focus();					
	}	
}

//查询
function queryCustomer(action_flag){
	var is_link;
	if(!document.theform.is_link.checked)
		is_link = 0;//是否关联方未选中为0
	else
		is_link = 1;
	var url = "<%=request.getContextPath()%>/affair/service/customer_query_sms.jsp?page=1"
				+"&pagesize=8&name="+document.theform.name.value
				+"&no="+document.theform.no.value+"&card_id="+document.theform.card_id.value
				+"&tel="+document.theform.tel.value+"&accountManager="+document.theform.accountManager.value
				+"&cust_level="+document.theform.cust_level.value+"&is_link="+is_link			
				+"&product_id="+document.theform.product_id.value
				+"&start_rg_times="+document.theform.start_rg_times.value
				+"&end_rg_times="+document.theform.end_rg_times.value
				+"&min_total_money="+document.theform.min_total_money.value
				+"&max_total_money="+document.theform.max_total_money.value
				+"&ben_amount_min="+document.theform.ben_amount_min.value
				+"&ben_amount_man="+document.theform.ben_amount_max.value;
	document.all.ifrPage.src = url;
}

/*取消*/
function CancelAction(){
	window.returnValue = [];
	window.close();
}

</script>
</HEAD>

<BODY class="BODY">
<%//@ include file="/includes/waiting.inc"%>
<form name="theform" method="get">
<div align="left" width="575px">
	<img border="0" src="<%=request.getContextPath()%>/images/ico_area.gif" width="32" height="28">
	<font color="#215dc6"><b><%=LocalUtilis.language("menu.batchAddCustomers",clientLocale)%> </b></font><!-- 客户批量新增 -->
</div>
<div align="right">
<button type="button" class="xpbutton3" name="btnQuery" accessKey=q onclick="javascript:queryCustomer(1);"><%=LocalUtilis.language("message.query",clientLocale)%> (<u>Q</u>)</button><!-- 查询 -->
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</div>
<hr noshade color="#808080" size="1" width="575px">

<fieldset style="width=575px;heigh=350" align="center">
 <!-- 查询信息 -->
<legend>
	<%=LocalUtilis.language("message.queryInformation",clientLocale)%>
</legend>
<div id="query_info" align="center">
<table border="0" cellspacing="0" cellpadding="4">
	<tr>
		<td align="right" width="13%"><%=LocalUtilis.language("class.customerID",clientLocale)%>:</td><!-- 客户编号 -->
		<td align="left">
			<input name="no" value='' onkeydown="javascript:nextKeyPress(this)" size="25">
		</td>
		<td align="right" width="13%"><%=LocalUtilis.language("class.customerName",clientLocale)%>:</td><!-- 客户名称 -->
		<td align="left">
			<input name="name" value='' onkeydown="javascript:nextKeyPress(this)" size="25" maxlength="100">
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.customerCardID",clientLocale)%>:</td><!-- 证件号码 -->
		<td align="left">
			<input name="card_id" value='' onkeydown="javascript:nextKeyPress(this)" size="25">
		</td>
		<td align="right"><%=LocalUtilis.language("login.telephone",clientLocale)%>:</td><!-- 联系电话 -->
		<td align="left">
			<input name="tel" value='' onkeydown="javascript:nextKeyPress(this)" size="25">
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.accountManager",clientLocale)%> </td><!--客户经理-->
		<td align="left">
			<select name="accountManager" style="width: 150px">
				<option value="<%=op_code%>" selected="selected"><%=op_name%></option>
			</select>
		</td>
		<td align="right"><%=LocalUtilis.language("class.customerLevel",clientLocale)%> </td><!-- 客户级别 -->
		<td align="left">
		<select style="width:150px" name="cust_level" onkeydown="javascript:nextKeyPress(this)">
			<%=Argument.getDictParamOptions2(1111, "")%>
		</select>
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.isLink",clientLocale)%>:</td><!-- 是否关联方 -->
		<td align="left">
		 <input type="checkbox" name="is_link" value="1"  class="flatcheckbox">
		 </td>
		<td  align="right"><%=LocalUtilis.language("class.productID",clientLocale)%>:</td><!-- 产品编号 -->
		<td  align="left">
			<input type="text" maxlength="16" name="productid"
			value="" onkeydown="javascript:setProduct(this.value);" maxlength=8
			size="22" onkeydown="javascript:nextKeyPress(this)"> &nbsp;
		<button type="button" class="searchbutton"
			onclick="javascript:searchProduct(document.theform.productid.value);" /></button>
		</td>
	</tr>

	<tr>

		<td align="right"><%=LocalUtilis.language("class.productNumber",clientLocale)%>:</td><!-- 产品选择 -->
		<td align="left" colspan=3><SELECT name="product_id" style="width: 320px;" onkeydown="javascript:nextKeyPress(this)">
				<%=Argument.getProductListOptions(new Integer(1), null, "",input_operatorCode,status)%>
			</SELECT></td>
	</tr>
	<tr>  
		<td align="right"><%=LocalUtilis.language("class.rg_times",clientLocale)%>:</td><!-- 购买次数 -->
        <!-- 从  -->
		<td colspan="3"><%=LocalUtilis.language("message.start",clientLocale)%> 
			<input type="text" onkeydown="javascript:nextKeyPress(this)" maxlength="9" name="start_rg_times" size="25" value="">
            <!-- 到 -->&nbsp;&nbsp;<%=LocalUtilis.language("message.end",clientLocale)%>&nbsp;&nbsp;  
			<input type="text" onkeydown="javascript:nextKeyPress(this)" maxlength="9" name="end_rg_times" size="25" value="">
		</td>
	</tr>
	<tr>
		<!-- 购买金额 -->
        <td align="right"><%=LocalUtilis.language("class.totalMoney",clientLocale)%>:</td>
        <!-- 从  -->
		<td colspan="3"><%=LocalUtilis.language("message.start",clientLocale)%> 
		 	<input type="text" onkeydown="javascript:nextKeyPress(this)" maxlength="10" name="min_total_money" size="25" value=""> 
			<!-- 到 -->&nbsp;&nbsp;<%=LocalUtilis.language("message.end",clientLocale)%>&nbsp;&nbsp;  
			<input type="text" onkeydown="javascript:nextKeyPress(this)" maxlength="10" name="max_total_money" size="25" value="">
		</td>
	</tr>
	<tr>
		<!-- 受益金额 -->
        <td align="right"><%=LocalUtilis.language("class.ben_amount",clientLocale)%>:</td>
        <!-- 从 -->
		<td colspan="3"><%=LocalUtilis.language("message.start",clientLocale)%> 
			<input type="text" onkeydown="javascript:nextKeyPress(this)" maxlength="10" name="ben_amount_min" size="25" value="">
			<!-- 到 -->&nbsp;&nbsp;<%=LocalUtilis.language("message.end",clientLocale)%>&nbsp;&nbsp;   
			<input type="text" onkeydown="javascript:nextKeyPress(this)" maxlength="10" name="ben_amount_max" size="25"value="">
		</td>
	</tr>
</table>
</div>
</fieldset>
<p>
<div id="table_view" align="left">
	<table border="0" width="575px" align="center" cellspacing="1" cellpadding="2"	class="tablelinecolor">
		<tr class="trh">
			<td align="center" width="30%">
				<input type="checkbox" name="btnCheckbox" class="selectAllBox"	
				onclick="javascript:selectAllBox(window.frames['ifrPage'].document.theform.cust_id,this);"><%=LocalUtilis.language("class.customerID",clientLocale)%> <!-- 客户编号 -->
			</td>
			<td align="center" width="40%"><%=LocalUtilis.language("class.customerName",clientLocale)%> </td><!-- 客户名称 -->
			<td align="center" width="15%"><%=LocalUtilis.language("class.customerType",clientLocale)%> </td><!-- 客户类别 -->
			<td align="center" width="15%"><%=LocalUtilis.language("class.accountManager",clientLocale)%> </td><!-- 客户经理 -->
		</tr>
	</table>
	<iframe id="ifrPage" align="center" width="575px" height="240px" frameborder="0" scrolling="no"></iframe>
</div>
<div align="right">	
    <!-- 确认 -->
	<button type="button" class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:submitAction();"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>S</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;
    <!-- 关闭 -->
	<button type="button" class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:CancelAction();"><%=LocalUtilis.language("message.close",clientLocale)%> (<u>C</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</div>
</form>
</BODY>
</HTML>
