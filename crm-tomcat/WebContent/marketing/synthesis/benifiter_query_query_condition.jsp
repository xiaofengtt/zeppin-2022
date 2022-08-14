<%@ page language="java" pageEncoding="GBK" import="java.util.*,java.math.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ include file="/includes/operator.inc" %>
<%
String contract_sub_bh = Utility.trimNull(request.getParameter("contract_sub_bh"));
String cust_name = Utility.trimNull(request.getParameter("cust_name"));
String card_id = Utility.trimNull(request.getParameter("card_id"));
String sy_cust_no = Utility.trimNull(request.getParameter("sy_cust_no"));
String prov_level = Utility.trimNull(request.getParameter("prov_level"));
String ben_status = Utility.trimNull(request.getParameter("ben_status"));
Integer cust_type=Utility.parseInt(request.getParameter("cust_type"),new Integer(0));
Integer product_id=Utility.parseInt(request.getParameter("product_id"),new Integer(0));
Integer team_id = Utility.parseInt(Utility.trimNull(request.getParameter("team_id")),new Integer(0));

String options = Argument.getProductListOptions(input_bookCode, product_id, "",input_operatorCode,15);
options = options.replaceAll("\"", "'");

%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<meta http-equiv="Pragma" content="no-cache">
<LINK href="/includes/default.css" type=text/css rel=stylesheet>
<title>受益人信息查询条件设置</title>
<link href="/includes/default.css" type=text/css rel=stylesheet>
<base target="_self">
<script src="/includes/default.js" language="javascript"></script>
<SCRIPT LANGUAGE="javascript" SRC="/includes/calendar.js"></SCRIPT>

<script language="javascript">
function query(){
	var ret = 'contract_sub_bh='+document.theform.contract_sub_bh.value
	          +'&product_id='+document.theform.product_id.value
	          +'&cust_name='+document.theform.cust_name.value
	          +'&card_id='+document.theform.card_id.value
	          +'&prov_level='+document.theform.prov_level.value
	          +'&sy_cust_no='+document.theform.sy_cust_no.value
	          +'&ben_status='+document.theform.ben_status.value
	          +'&cust_type='+document.theform.cust_type.value
	          +'&team_id='+document.theform.team_id.value;
	window.returnValue = ret;
	window.close();
}

function setProduct(value)
{
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
			sl_alert('输入的产品编号不存在！');
			document.theform.product_code.value="";
			document.theform.product_id.options[0].selected=true;	
		}			
	}
	nextKeyPress(this);
}

function searchProduct(value)
{
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
			sl_alert('输入的产品编号不存在！');
			document.theform.product_code.value="";
			document.theform.product_id.options[0].selected=true;	
		}
		document.theform.product_id.focus();					
	}	
}

function searchProductName(value){
	var list = [];
	var list1 = [];
	document.getElementById("select_id").innerHTML = "<SELECT name='product_id' onkeydown='javascript:nextKeyPress(this)'>"+"<%=options%>"+"</SELECT>";
	if(value != ""){
		for(i=0;i<document.theform.product_id.options.length;i++){
			var j = document.theform.product_id.options[i].text.indexOf(value);
			if(j>0){
				list.push(document.theform.product_id.options[i].text);
				list1.push(document.theform.product_id.options[i].value);
			}
		}	
		if(list.length==0){
			sl_alert('输入的产品名称不存在 ！');//输入的产品名称不存在
			document.theform.product_code.value="";
			document.theform.product_id.options[0].selected=true;
		}else{
			document.theform.product_id.options.length=0;
			for(var i=0;i<list.length;i++){
				document.theform.product_id.options.add(new Option(list[i],list1[i]));
			}
		}
		document.theform.product_id.focus();
	}else{
		document.theform.product_id.options[0].selected=true;
	}
}
</script>
</head>
<body class="BODY body-nox" onkeydown="chachEsc(window.event.keyCode)">
<form name="theform">
<table border="0" width="100%" cellspacing="0" cellpadding="10">
	<tr>
		<td>
		<table border="0" width="100%" cellspacing="0" cellpadding="4" style="border-collapse: collapse" bordercolor="#111111" class="table-select">
			<tr>
				<td colspan="4" height="11"><font color="#000000"><b>查询条件：</b></font></td>
			</tr>
			<tr>
				<td align="right" noWrap>受益人编号:</td>
				<td align="left"><input type="text" name="sy_cust_no" value="<%=sy_cust_no%>" onkeydown="javascript:nextKeyPress(this);" size="20"></td>
				<td align="right" noWrap>受益人名称:</td>
				<td align="left"><input type="text" name="cust_name" value="<%=cust_name%>" onkeydown="javascript:nextKeyPress(this);" size="20"></td>
			</tr>
			<tr>
				<td align="right">证件号码:</td>
				<td align="left"><input type="text" name="card_id" value="<%=card_id%>" onkeydown="javascript:nextKeyPress(this);"  size="30"></td>
			   <td align="right">客户类别:</td>
				<td align="left"><SELECT  size="1" name="cust_type" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px">
							<%=Argument.getCustTypeOptions2(cust_type)%>
					</SELECT>
				</td>
			</tr>
			<tr>	
				<td align="right">受益状态:</td>
				<td ><SELECT  size="1" name="ben_status" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px">
							<%=Argument.getBenStatusOptions(ben_status)%>
					 </SELECT>
				</td>						
				<td align="right">受益级别:</td>
				<td align="left"><SELECT  size="1" name="prov_level" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px">
							<%=Argument.getProvlevelOptions(prov_level)%>
					</SELECT>
				</td>
			</tr>
			<tr>	
				<td align="right">合同编号:</td>
				<td align="left"><INPUT type="text" onkeydown="javascript:nextKeyPress(this)" name="contract_sub_bh" size="20" value="<%=contract_sub_bh%>"></td>						
			
		        <td align="right">营销部门:</td>
		        <td align="left">
		        <select name="team_id" size="1" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px">
					<%=Argument.getTeam_mark()%>
				</select>
		        </td>
			</tr>
			<tr>
				<td align="right">产品编号:</td>
				<td align="left"><input type="text" maxlength="16" name="product_code" value="" onkeydown="javascript:setProduct(this.value);" maxlength=8 size="10">
				&nbsp;<button type="button"  class="searchbutton" onclick="javascript:searchProduct(document.theform.product_code.value);" /></button></td>
				<td align="right">产品名称 :</td>
				<td align="left">
					<input name="product_name" value='' onkeydown="javascript:nextKeyPress(this)" size="18">&nbsp;
					<button type="button"  class="searchbutton" onclick="javascript:searchProductName(product_name.value);" /></button>
				</td>
			</tr>
			<tr>	
				<td align="right">产品选择:</td >
				<td align="left" colspan="3" id="select_id">
				<SELECT name="product_id" onkeydown="javascript:nextKeyPress(this)"><%=Argument.getProductListOptions(input_bookCode, product_id, "",input_operatorCode,15)%></SELECT><!-- 查询正常期 -->
				</td>				
			</tr>			
			<tr>
				<td colspan="4" width="100%" height="1">
				<table border="0" width="100%">
					<tr>
						<td align="right">
						<button type="button"  class="xpbutton3" accessKey=o id="btnQuery" name="btnQuery" onclick="javascript:query();">确定(<u>O</u>)</button>
						&nbsp;&nbsp;
						<button type="button"  class="xpbutton3" accessKey=c id="btnSave" name="btnSave" onclick="javascript:window.returnValue=null;window.close();">取消(<u>C</u>)</button>
						</td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</form>
</body>
</html>