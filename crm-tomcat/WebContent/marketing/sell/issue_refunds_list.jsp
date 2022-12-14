<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
Integer product_id = Utility.parseInt(request.getParameter("product_id"), new Integer(0));
String contract_bh = Utility.trimNull(request.getParameter("contract_bh"));
Integer check_flag = Utility.parseInt(request.getParameter("check_flag"), new Integer(0));
Integer sq_date = Utility.parseInt(request.getParameter("sq_date"), new Integer(0));
Integer sub_product_id = Utility.parseInt(request.getParameter("sub_product_id"), new Integer(0));
Integer rebate_flag = Utility.parseInt(request.getParameter("rebate_flag"), new Integer(1));
String[] totalColumn = {"SQ_MONEY"};
SqstopContractLocal local = EJBFactory.getSqstopContract();
SqstopContractVO vo = new SqstopContractVO();

vo.setProduct_id(product_id);
vo.setContract_bh(contract_bh);
vo.setSq_date(sq_date);
vo.setCheck_flag(check_flag);
vo.setInput_man(input_operatorCode);
vo.setSub_product_id(sub_product_id);
vo.setRebate_flag(rebate_flag);

IPageList pageList = local.listAll(vo,totalColumn, Utility.parseInt(sPage,1), Utility.parseInt(sPagesize,8));
List list = pageList.getRsList();
Iterator it = list.iterator();
Map map = new HashMap();

String tempUrl = "&product_id=" + product_id + "&contract_bh=" + contract_bh + "&check_flag=" + check_flag
				+"&sq_date=" + sq_date + "&rebate_flag=" + rebate_flag + "&sub_product_id="+sub_product_id;
sUrl = sUrl + tempUrl;

String options = Argument.getProductListOptions(new Integer(1), product_id, "",input_operatorCode,status);
options = options.replaceAll("\"", "'");
%>
<html>
<head>
<title></title>
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
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/utilityService.js'></script>
<script language="javascript">
//????????
window.onload = function(){
	initQueryCondition();
}

//????
function StartQuery(){
	syncDatePicker(document.theform.change_date_picker, document.theform.sq_date);
	refreshPage();
}

//????
function refreshPage(){
	disableAllBtn(true);
	var url = 'issue_refunds_list.jsp?page=<%=sPage%>&pagesize=' + document.theform.pagesize.value
											+'&product_id=' + document.theform.product_id.value
											+'&contract_bh=' + document.theform.contract_bh.value
											+'&check_flag=' + document.theform.check_flag.value
											+'&sq_date=' + document.theform.sq_date.value
											+'&rebate_flag=' + document.theform.rebate_flag.value;
	location = url;
}

//????????
function showInfo(serial_no,rebate_flag){
	var url = 'issue_refunds_add.jsp?flag=edit&serial_no=' + serial_no+'&rebate_flag='+rebate_flag;
	location = url;
}

//????
function newInfo(){
	location = 'issue_refunds_add.jsp?flag=new';
}

//????????????
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
			sl_alert('<%=LocalUtilis.language("message.idIsNotExists",clientLocale)%> ??');//????????????????????
			document.theform.productid.value="";
			document.theform.product_id.options[0].selected=true;	
		}
		document.theform.product_id.focus();					
	}	
}

//????????????
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
			sl_alert('<%=LocalUtilis.language("message.idIsNotExists",clientLocale)%> ??');//????????????????????
			document.theform.productid.value="";
			document.theform.product_id.options[0].selected=true;	
		}
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
	
	utilityService.getSubProductFlag(product_id,'sub_flag',function(data){
		if(data==1){
				tr_sub_product_id.style.display="";
			}else{
				tr_sub_product_id.style.display="none";
			}
	});
	utilityService.getSubProductJson(product_id,3,function(data){
		var json = eval(data);
		DWRUtil.addOptions(sub_product_id,{"0":"??????"});
		for(i=0;i<json.length;i++){
			DWRUtil.addOptions(sub_product_id,json[i]);
	  }
	  DWRUtil.setValue('sub_product_id',<%=sub_product_id%>);
  });
}

function searchProductName(value){
	var list = [];
	var list1 = [];
	document.getElementById("select_id").innerHTML = "<SELECT id='product_id' name='product_id' class='productname' onkeydown='javascript:nextKeyPress(this)'>"+"<%=options%>"+"</SELECT>";
	if(value != ""){
		for(i=0;i<document.theform.product_id.options.length;i++){
			var j = document.theform.product_id.options[i].text.indexOf(value);
			if(j>0){
				list.push(document.theform.product_id.options[i].text);
				list1.push(document.theform.product_id.options[i].value);
			}
		}	
		if(list.length==0){
			sl_alert('???????????????????? ??');//????????????????????
			document.theform.productid.value="";
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
<body class="BODY">
<form name="theform" action="issue_refunds_remove.jsp" method="post">
<!--????????-->
<div id="queryCondition" class="qcMain" style="display: none; width:500px">
<table id="qcTitle" class="qcTitle" align="center" width="100%" cellpadding="2" cellspacing="0">
	<tr>
		<td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> :</td><!--????????-->
		<td align="right">
			<button type="button"   class="qcClose" accesskey="c" id="qcClose" name=qcClose onclick="javascript:cancelQuery();"/>
		</td>
	</tr>
</table>
<table border="0" width="100%" cellpadding="2" cellspacing="2">
	
	<tr>
		<td align="right"><%=LocalUtilis.language("class.contractID",clientLocale)%> :</td><!--????????-->
		<td>
			<input type="text" name="contract_bh" id="contract_number" size="20" onkeydown="javascript:nextKeyPress(this)" value="<%=contract_bh%>">
		</td>
	</tr>

	<tr>
		<td align="right"><%=LocalUtilis.language("class.checkFlag",clientLocale)%> :</td><!--????????-->
		<td>
			<select id="check_flag" name="check_flag" style="width:120px;">
				<option value="0"><%=LocalUtilis.language("message.all",clientLocale)%> </option><!--????-->
				<option value="1" <%if(check_flag.equals(new Integer(1))) out.print("selected");%>><%=LocalUtilis.language("message.unaudited",clientLocale)%> </option><!--??????-->
				<option value="2" <%if(check_flag.equals(new Integer(2))) out.print("selected");%>><%=LocalUtilis.language("message.checked",clientLocale)%> </option><!--??????-->
				<option value="3" <%if(check_flag.equals(new Integer(3))) out.print("selected");%>><%=LocalUtilis.language("message.finanAudit",clientLocale)%> </option><!--??????????-->
			</select>
		</td>
	</tr>

	<tr>
		<td align="right">??????????:</td>
		<td>
		<select onkeydown="javascript:nextKeyPress(this)" name=rebate_flag  >
			<option value="1" <%if(rebate_flag.intValue()==1){out.print("selected");}%>>??????????</option>
			<option value="2" <%if(rebate_flag.intValue()==2){out.print("selected");}%>>??????????</option>
		</select>
		</td>
	</tr>

	<tr>
		<td align="right"><%=LocalUtilis.language("class.changeDate2",clientLocale)%> :</td><!--????????-->
		<td>
			<INPUT TYPE="text" NAME="change_date_picker" value="<%=Format.formatDateLine(sq_date)%>">
			<INPUT TYPE="button" value="??" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.change_date_picker,theform.change_date_picker.value,this);" tabindex="13">
			<INPUT TYPE="hidden" NAME="sq_date"   value="">	
		</td>
	</tr>

	<tr>
		<td align="right"><%=LocalUtilis.language("class.productID",clientLocale)%> :</td><!--????????-->
		<td>
			<input name="productid" size="10" onkeydown="javascript:setProduct(this.value);">&nbsp;
			<button type="button"   class="searchbutton" onclick="javascript:searchProduct(theform.productid.value)"></button>
		</td>
		<td  align="right">???????? :</td>
		<td>
			<input type="text" name="product_name" value="" onkeydown="javascript:nextKeyPress(this);" size="15">&nbsp;
			<button type="button"   class="searchbutton" onclick="javascript:searchProductName(product_name.value);" />		
		</td>
	</tr>

	<tr>
		<td align="right"><%=LocalUtilis.language("class.productNumber",clientLocale)%> :</td><!--????????-->
		<td align="left" colspan="3" id="select_id">
			<select id="product_id" name="product_id" class="productname" onkeydown="javascript:nextKeyPress(this);">  
				<%=Argument.getProductListOptions(new Integer(1), product_id, "",input_operatorCode,status)%>
			</select>
		</td>
	</tr>
	<tr id="tr_sub_product_id" name="tr_sub_product_id" style="display:none">			
		<td align="right">??????:</td>
		<td align="left" id="td_sub_product_id">
			<SELECT name="sub_product_id" onkeydown="javascript:nextKeyPress(this)" class="productname">
				<%=Argument.getSubProductOptions2(product_id, new Integer(0),sub_product_id,0,"")%> 
			</SELECT>
		</td>
	</tr>
	<tr>
		<td align="center" colspan="4">
			<!--????-->
            <button type="button"   class="xpbutton3" id="btnQuery" accesskey="O" onclick="javascript:StartQuery();"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>O</u>)</button>
		</td>
	</tr>
</table>
</div>
<!--????????-->
<table width="100%" border="0">
	<tr>
		<td class="page-title">
		<font color="#215dc6"><b><%=menu_info%></b></font></td>
	</tr>
	<tr>
		<td align="right">
		<div class="btn-wrapper">
			<!--????-->
            <button type="button"   class="xpbutton3" accessKey=q id="queryButton" name="queryButton"><%=LocalUtilis.language("message.query",clientLocale)%> (<u>Q</u>)</button>&nbsp;&nbsp;&nbsp;
			<%if (input_operator.hasFunc(menu_id, 100)) {%>
            <!--????????--><!--????-->
			<button type="button"   class="xpbutton3" accessKey=n id="btnNew" name="btnNew"
				title='<%=LocalUtilis.language("message.newRecord",clientLocale)%>' onclick="javascript:newInfo();"><%=LocalUtilis.language("message.new",clientLocale)%> (<u>N</u>)</button>&nbsp;&nbsp;&nbsp; <%}%> 
			<%if (input_operator.hasFunc(menu_id, 101)) {%>
            <!--????????????--><!--????-->
			<button type="button"   class="xpbutton3" accessKey=d id="btnDelete" name="btnDelete" title='<%=LocalUtilis.language("message.delRecordsSelect",clientLocale)%> '
                onclick="javascript:if(confirmRemove(document.theform.serial_no)) document.theform.submit();"><%=LocalUtilis.language("message.delete",clientLocale)%> (<u>D</u>)</button><%}%>
		</div>
		</td>
	</tr>
</table>
<!--????????-->
<table align="center" cellspacing="0" cellpadding="3" width="100%" class="table-select">
	<tr>
		<td>
			<TABLE width="100%" class="tablelinecolor" cellspacing="1" cellpadding="1">
				<tr class="trh">
					<td align="center">
						<!--????????-->
                        <input type="checkbox" name="btnCheckBox" class="selectAllBox" onclick="javascript:selectAllBox(document.theform.serial_no,this);"><%=LocalUtilis.language("class.contractID",clientLocale)%> 
					</td>
					<td align="center"><%=LocalUtilis.language("class.productName",clientLocale)%> </td><!--????????-->
					<td align="center"><%=LocalUtilis.language("class.customerName",clientLocale)%> </td><!--????????-->
					<td align="center">????????</td>
					<td align="center"><%=LocalUtilis.language("class.sqMoney",clientLocale)%> </td><!--????????-->
					<td align="center"><%=LocalUtilis.language("class.refundFee",clientLocale)%> </td><!--??????????-->
					<td align="center"><%=LocalUtilis.language("class.changeDate2",clientLocale)%> </td><!--????????-->
					<td align="center"><%=LocalUtilis.language("message.edit",clientLocale)%> </td><!--????-->
				</tr>
				<%
				int iCount = 0;
				int iCurrent = 0;
				String rebate_name = "";
				while(it.hasNext())
				{
					map = (Map)it.next();

					rebate_flag = Utility.parseInt(Utility.trimNull(map.get("REBATE_FLAG")),new Integer(0));
					if(rebate_flag.intValue()==1){
						rebate_name = "??????????";
					}
					else if(rebate_flag.intValue()==2){
						rebate_name = "??????????";
					}
				%>
				<tr class="tr<%=(iCurrent % 2)%>">
					<td class="tdh">
						<!--??????????????????????????-->
						<%if(Utility.parseInt(Utility.trimNull(map.get("CHECK_FLAG")), new Integer(0)).intValue() == 1){%>
							<input type="checkbox" name="serial_no"
									value="<%=Utility.trimNull(map.get("SERIAL_NO"))%>" class="flatcheckbox">
						<%}else{%>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<%}%>
						<%=Utility.trimNull(map.get("CONTRACT_BH"))%>
					</td>
					<td align="left"><%=Utility.trimNull(map.get("PRODUCT_NAME"))%></td>
					<td align="left"><%=Utility.trimNull(map.get("CUST_NAME"))%></td>
					<td align="left" ><%=Utility.trimNull(rebate_name)%></td>
					<td align="right"><%=Format.formatMoney(Utility.stringToDouble(Utility.trimNull(map.get("SQ_MONEY"))))%></td>
					<td align="right"><%=Format.formatMoney(Utility.stringToDouble(Utility.trimNull(map.get("FEE"))))%></td>
					<td align="center"><%=Format.formatDateCn(Utility.parseInt(Utility.trimNull(map.get("SQ_DATE")), new Integer(0)))%></td>
					<td align="center" width="6%">
						<% if(Utility.parseInt(Utility.trimNull(map.get("CHECK_FLAG")), new Integer(0)).intValue() == 1)
							{ if (input_operator.hasFunc(menu_id, 102)) {%>
							<a href="javascript:#" onclick="javascript:showInfo(<%=Utility.trimNull(map.get("SERIAL_NO"))%>,<%=rebate_flag%>); return false;">
								<img src="<%=request.getContextPath()%>/images/edit2.gif" width="16" height="16" title='<%=LocalUtilis.language("message.edit",clientLocale)%> ' /><!--????-->
							</a>
						<%}}%>
					</td>
				</tr>
				<%
				iCount ++;
				iCurrent ++;
				}
				for(; iCurrent < pageList.getPageSize(); iCurrent++){
				%>
				<tr class="tr<%=(iCurrent % 2)%>">
					<td class="tdh"></td>
					<td align="center"></td>
					<td align="center"></td>
					<td align="center"></td>
					<td align="center"></td>
					<td align="center"></td>
					<td align="center"></td>
					<td align="center"></td>
				<%}%>
				<tr class="trbottom">
					<!--????--><!--??-->
                    <td class="tdh" align="left" colspan="1"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%>&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> </b></td>
					<td align="center"></td>
					<td align="center"></td>
					<td align="center"></td>
					<td align="right"><%=Format.formatMoney(Utility.parseBigDecimal(pageList.getTotalValue("SQ_MONEY"),new BigDecimal(0.00)))%></td>
					<td align="center"></td>
					<td align="center"></td>
					<td align="center"></td>
				</tr>
			</table>
		</td>
	</tr>
	<tr><td>&nbsp;</td></tr>
	<tr>
		<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="page-link">
				<tr>
					<td><%=pageList.getPageLink(sUrl,clientLocale)%></td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</form>
</body>
</html>
<%local.remove();%>
