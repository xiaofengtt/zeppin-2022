<%@ page contentType="text/html; charset=GBK" import="enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
Integer product_id = Utility.parseInt(request.getParameter("product_id"),new Integer(0));
Integer sub_product_id = Utility.parseInt(request.getParameter("sub_product_id"),new Integer(0));
String contract_sub_bh = Utility.trimNull(request.getParameter("contract_sub_bh"));

TMoneyDataLocal local = EJBFactory.getTMoneyData(); 
TMoneyDataVO vo = new TMoneyDataVO(); 
 
vo.setBook_code(input_bookCode); 
vo.setProduct_id(product_id);
vo.setSub_product_id(sub_product_id);
vo.setContract_sub_bh(contract_sub_bh);
vo.setInput_man(input_operatorCode);

sUrl = sUrl+ "&product_id=" + product_id
				+ "&sub_product_id=" + sub_product_id
				+ "&contract_sub_bh=" + contract_sub_bh;

IPageList pageList = local.queryContractRestore(vo,Utility.parseInt(sPage,1),Utility.parseInt(sPagesize,8));

%>
<HTML>
<HEAD>
<TITLE>资金到账复核</TITLE>
</HEAD>


<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<base target="_self">
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx1.js"></SCRIPT>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/investment.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/utilityService.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/contract.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>

<script language=javascript>
function refreshPage(){
  	startQuery();
}

function startQuery(){
	disableAllBtn(true);
    location = "sub_capital_entering_recover.jsp?pagesize="+ document.theform.pagesize.value
					+ "&product_id=" + document.theform.product_id.value
					+ "&sub_product_id=" + document.theform.sub_product_id.value
					+ "&contract_sub_bh=" + document.theform.contract_sub_bh.value;
}

function checkInfo(flag){
	obj = document.theform.serial_no;
	if(checkedCount(obj) == 0){
		sl_alert("请选择要审核的资金流水！");
		return false;
	}
	if(!sl_confirm("审核所选资金流水")){
		return false;
	}
	disableAllBtn(true);
	document.theform.action="sub_capital_entering_recover_action.jsp";
	document.theform.submit();
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
			document.theform.product_id.value="";
			document.theform.product_id.options[0].selected=true;
		}
		document.theform.product_id.focus();
		productChange(prodid);
	}	
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
			document.theform.product_id.value="";
			document.theform.product_id.options[0].selected=true;	
		}			
		productChange(prodid);
	}
	nextKeyPress(this);
}

function productChange(product_id){
	var _prodcut_id = product_id;
	var tr_sub_product_id;
	var sub_product_id;
	
	sub_product_id = document.theform.sub_product_id;
	tr_sub_product_id = document.getElementById("tr_sub_product_id");

	DWRUtil.removeAllOptions(sub_product_id);
	
	utilityService.getProductFlag(product_id,'sub_flag',function(data){
		if(data==1){
				tr_sub_product_id.style.display="";
			}else{
				tr_sub_product_id.style.display="none";
			}
	});
	utilityService.getSubProductJson(<%=input_bookCode%>,<%=input_operatorCode%>,product_id,3,function(data){
		var json = eval(data);
		DWRUtil.addOptions(sub_product_id,{"0":"请选择"});
		for(i=0;i<json.length;i++){
			DWRUtil.addOptions(sub_product_id,json[i]);
	  }
	  DWRUtil.setValue('sub_product_id',<%=sub_product_id%>);
  });
}

window.onload = function(){
	initQueryCondition();
	productChange(<%=product_id%>);
}

</script>

<BODY class="BODY" leftMargin=0 topMargin=0 rightmargin="0" bottommargin="0" MARGINWIDTH="0" MARGINHEIGHT="0" >
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="post" action="sub_capital_entering_recover.jsp">

<div id="queryCondition" class="qcMain" style="display:none;width:400px;">
<table  id="qcTitle" class="qcTitle" align="center" width="99%" cellspacing="0" cellpadding="2">
  <tr>
   <td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ：</td><!--查询条件-->
   <td align="right"><button class="qcClose" accessKey=c id="qcClose" name="qcClose"onclick="javascript:cancelQuery();"></button></td>
  </tr>
</table>
<!-- 查询内容 -->
<table border="0" width="100%" cellspacing="2" cellpadding="2">
	<tr>
		<td align="right">产品编号:</td>
		<td>
			<input type="text" name="product_code" value="" onkeydown="javascript:setProduct(this.value);" size="15">&nbsp;
			<button class="searchbutton" onclick="javascript:searchProduct(document.theform.product_code.value);" /></button>
		</td>
	</tr>
	<tr>
		<td align="right">产品选择:</td>
		<td align="left" colspan=3>
			<select size="1" name="product_id" onchange="javascript:productChange(this.value)" class="productname" onkeydown="javascript:nextKeyPress(this)">
				<%=Argument.getCRMProductListOptions(new Integer(0), product_id,"",new Integer(0),input_operatorCode)%>
			</select>
		</td>
	</tr>
	<tr id="tr_sub_product_id" style="display:none">
		<td align="right">子产品选择:</td>
		<td align=left colspan=3>
			<div id="sub_product_span">
				<select size="1" name="sub_product_id" onkeydown="javascript:nextKeyPress(this)" class="productname">
					<%=Argument.getCRMSubProductOptions(product_id, new Integer(0),sub_product_id)%>
				</select>&nbsp;&nbsp;&nbsp;
			</div>
		</td>
	</tr>
	<tr>
		<td  align="right">合同编号:</td><td>
			<input name="contract_sub_bh" value="<%=Utility.trimNull(contract_sub_bh)%>" onkeydown="javascript:nextKeyPress(this);" size="23">
		</td>
	</tr>
	<tr>
		<td align="center" colspan="6"><button class="xpbutton3" accessKey=o name="btnQuery"
			onclick="javascript:startQuery();">确定(<u>O</u>)</button>&nbsp;&nbsp;&nbsp;&nbsp;								
		</td>
	</tr>
</table>
</div>

<table cellSpacing=0 cellPadding=0 width="100%" height="100%" border=0>
	<tr><td vAlign=top align=left>
	<table cellSpacing=0 cellPadding=0 width="100%" align=center border=0>
		<tr><td>
		<table border="0" width="100%" cellspacing="2" cellpadding="0">
			<%//int  showTabInfo =  showTabInfoTop.intValue();
			//if(showTabInfo != 1){ %>
			<tr>
				<td colspan="2"><IMG src="/images/member.gif" align=absBottom border=0 width="32" height="28"><b><%=menu_info%></b></td>
			</tr>
			<%//} %>
			<tr>
				<td valign="bottom" align="right">
					<button <%=input_operator.getFuncLabel(menu_id,108)%> class="xpbutton3" accessKey=f id="queryButton" name="queryButton"><%=LocalUtilis.language("message.query",clientLocale)%> <!--查询-->(<u>F</u>)</button>&nbsp;&nbsp;&nbsp;
					<button <%=input_operator.getFuncLabel(menu_id,100)%> class="xpbutton4" accessKey=c name="btnCheck" onclick="javascript:checkInfo(1);">审核恢复(<u>C</u>)</button>&nbsp;&nbsp;&nbsp;
				</td>
			</tr>
			<tr><td colspan="2">
				<hr noshade color="#808080" size="1">
			</td></tr>
		</table>

		<table id="table3" border="0" sort="multi" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%">
			<tr class="trh">
				<td align="center">
					<table border="0" width="100%" cellspacing="0" cellpadding="0">
						<tr class="trh">
							<td width="10%"><input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAllBox(document.theform.serial_no,this);"></td>
							<td width="90%" align="center">合同编号</td>
						</tr>
					</table>
				</td>
				<td align="center" >产品名称</td>
				<td align="center" >客户编号</td>
				<td align="center" >客户名称</td>
				<td align="center"  sort="num">合同金额</td>
				<td align="center" >签署日期</td>
				<td align="center" >合同类型</td>
			</tr>
			<%
			List list = pageList.getRsList();
			int iCount = 0;
			Map map = null;
			for(int i=list.size()-1;i>=0;i--){
				map = (Map)list.get(i);
				iCount++; 
				String sub_product_name = Utility.trimNull(Argument.getSubProductName(product_id,Utility.parseInt(Utility.trimNull(map.get("SUB_PRODUCT_ID")),new Integer(0))));
			%>
			<tr class="tr<%=iCount%2%>">
				<td align="center" >
					<table border="0" width="100%" cellspacing="0" cellpadding="0">
						<tr>
							<td width="10%">
								<input type="checkbox" name="serial_no" value="<%=Utility.trimNull(map.get("SERIAL_NO"))+"$"+Utility.trimNull(map.get("HT_TYPE"))%>" class="flatcheckbox">
							</td>
							<td width="90%" align="center"><%=Utility.trimNull(map.get("CONTRACT_SUB_BH"))%></td>
						</tr>
					</table>
				</td>
				<td align="left" ><%=Utility.trimNull(map.get("PRODUCT_CODE"))+"-"+Utility.trimNull(map.get("PRODUCT_NAME"))%><%=sub_product_name.equals("")?"":"("+sub_product_name+")"%></td>
				<td align="center" ><%=Utility.trimNull(map.get("CUST_NO"))%></td>
				<td align="left" ><%=Utility.trimNull(map.get("CUST_NAME"))%></td>
				<td align="right" ><%=Format.formatMoney(Utility.parseDecimal(Utility.trimNull(map.get("TO_MONEY")), new BigDecimal(0)))%></td>
				<td align="center" ><%=Format.formatDateCn(Utility.parseInt(Utility.trimNull(map.get("QS_DATE")), new Integer(0)))%></td>
				<td align="center" ><%=Utility.trimNull(map.get("HT_TYPE_NAME"))%></td>
			</tr>
			<%
			}
			 %>
			<%
			for(int i =0;i<pageList.getBlankSize();i++){
				iCount++;
			%>
			<tr class="tr<%=iCount%2%>">
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<%
			}
			 %>
	
		</table>
		<br>
		<table border="0" width="100%">
			<tr valign="top">
				<%=pageList.getPageLink(sUrl)%>
			</tr>
		</table>
	</table>
	</td></tr>
</table>

</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>
<%
local.remove();
%>
