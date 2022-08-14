<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*"%>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %> 
<%
Integer qs_date = Utility.parseInt(request.getParameter("qs_date"),new Integer(0));
Integer end_date = Utility.parseInt(request.getParameter("end_date"),new Integer(0));
java.math.BigDecimal min_rg_money=Utility.parseDecimal(request.getParameter("min_rg_money"),null);
java.math.BigDecimal max_rg_money=Utility.parseDecimal(request.getParameter("max_rg_money"),null);
String contract_sub_bh = Utility.trimNull(request.getParameter("contract_sub_bh"));
String product_name = "";

int ismonyfh = Argument.getSyscontrolValue_intrust("ISMONYFH");

if(overall_product_id.intValue()!=0){
	product_name = Utility.trimNull(Argument.getProductFlag(overall_product_id,"product_name"));
}

ToMoneyAccountLocal local = EJBFactory.getToMoneyAccount();
ToMoneyAccountVO vo = new ToMoneyAccountVO();
String[] totalColumn = {"TO_MONEY","TO_AMOUNT"};
IPageList pageList = null;
List list = null;
Map map = null;

boolean bsuccess = false;
if(request.getMethod().equals("POST")){
	String[] s = request.getParameterValues("s_id");
	int serial_no;
	if(s != null){
		for(int i = 0;i < s.length; i++){
			serial_no = Utility.parseInt(s[i], 0);
			if(serial_no != 0){
				vo.setSerial_no(new Integer(serial_no));
				vo.setInput_man(input_operatorCode);
				local.checkByManage(vo);
			}
		}
        bsuccess = true;
	}
}else{
	vo.setBook_code(input_bookCode);
	vo.setContract_sub_bh(contract_sub_bh);
	vo.setProduct_id(overall_product_id);
	if(ismonyfh == 1)
		vo.setCheck_flag(new Integer(1));
	else
		vo.setCheck_flag(new Integer(99));
	vo.setInput_man(input_operatorCode);
	vo.setDz_date(qs_date);
	vo.setEnd_date(end_date);
	vo.setMin_to_money(min_rg_money);
	vo.setMax_to_money(max_rg_money);;
	pageList = local.query_page(vo,totalColumn, Utility.parseInt(sPage,1),Utility.parseInt(sPagesize,8));
}

status=16;
String showstring = "";
if(user_id.toString().equals("22")){
	showstring ="style='display:none'";
}

sUrl = sUrl + "&contract_sub_bh=" + contract_sub_bh + "&product_id=" + overall_product_id + "&qs_date=" + qs_date + "&end_date=" + end_date + "&min_rg_money=" + min_rg_money + "&max_rg_money=" + max_rg_money;

String options = Argument.getProductListOptions(input_bookCode, overall_product_id, "",input_operatorCode,0);
options = options.replaceAll("\"", "'");
%>
<HTML>
<HEAD>
<TITLE>资金到帐经理审核</TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
</HEAD>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<script language=javascript>
<%if(bsuccess){%>
	alert("审核成功!");
	location = "<%=sUrl%>";
<%}%>

function StartQuery(){
	syncDatePicker(document.theform.qs_date_picker,document.theform.qs_date);
	syncDatePicker(document.theform.end_date_picker,document.theform.end_date);

	location="square_manage_check.jsp?pagesize="+ document.theform.pagesize.value
									+ "&qs_date=" + document.theform.qs_date.value
									+ "&end_date=" + document.theform.end_date.value
									+ "&min_rg_money=" + document.theform.min_rg_money.value
									+ "&max_rg_money=" + document.theform.max_rg_money.value
									+ "&contract_sub_bh=" + document.theform.contract_sub_bh.value
									+ "&product_id=" + document.theform.product_id.value;		
}

function confirmRemove(){
	if(checkedCount(document.theform.s_id) == 0){
		sl_alert("请选定要审核的记录！");
		return false;
	}
	if(confirm("您确定对选定的记录进行审核吗？")){	
		 document.theform.submit();
	}
}

function refreshPage(){
	StartQuery();
}

function setProduct(value){
	prodid=0;
	if (event.keyCode == 13 && value != ""){
        j = value.length;
		for(i=0;i<document.theform.product_id.options.length;i++){
			if(document.theform.product_id.options[i].text.substring(0,j)==value){
				document.theform.product_id.options[i].selected=true;
				prodid = document.theform.product_id.options[i].value;
				break;
			}	
		}
		if (prodid==0){
			sl_alert('<%=LocalUtilis.language("message.idIsNotExists",clientLocale)%> ！');//输入的产品编号不存在
			document.theform.productid.value="";
			document.theform.product_id.options[0].selected=true;	
		}			
	}
	nextKeyPress(this);
}
function searchProduct(value){
	prodid=0;
	if (value != ""){
        j = value.length;
		for(i=0;i<document.theform.product_id.options.length;i++){
			if(document.theform.product_id.options[i].text.substring(0,j)==value){
				document.theform.product_id.options[i].selected=true;
				prodid = document.theform.product_id.options[i].value;
				break;
			}	
		}
		if (prodid==0){
			sl_alert('<%=LocalUtilis.language("message.idIsNotExists",clientLocale)%> ！');//输入的产品编号不存在
			document.theform.productid.value="";
			document.theform.product_id.options[0].selected=true;	
		}	
		document.theform.product_id.focus();		
	}
}

function searchProductName(value){
	var list = [];
	var list1 = [];
	document.getElementById("select_id").innerHTML = "<SELECT name='product_id' class='productname' onkeydown='javascript:nextKeyPress(this)'>"+"<%=options%>"+"</SELECT>";
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

window.onload = function(){
	initQueryCondition();
}
</script>
<BODY  class="BODY">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" action="square_manage_check.jsp" method="post">
<div id="queryCondition" class="qcMain" style="display:none;width:450px;">	
	<table id="qcTitle" class="qcTitle" align="center" width="99%" cellspacing="0" cellpadding="2">
		<tr> 
			<td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> :</td><!--查询条件-->
			<td align="right">
				<button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose" onclick="javascript:cancelQuery();"></button>
			</td>
		</tr>
	</table>	
	<table border="0" width="100%" cellspacing="2" cellpadding="2">		
		<tr>
			<td align="right">缴款日期:</td><!--缴款日期-->
			<td align="left" colspan="3">
				从
				<INPUT TYPE="text" NAME="qs_date_picker" class=selecttext size=14 onkeydown="javascript:nextKeyPress(this)" value="<%=Format.formatDateLine(qs_date)%>">
				<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.qs_date_picker,theform.qs_date_picker.value,this);" tabindex="13">
				<INPUT TYPE="hidden" NAME="qs_date"   value="">
				到
                <INPUT TYPE="text" NAME="end_date_picker" class=selecttext size=14 onkeydown="javascript:nextKeyPress(this)" value="<%=Format.formatDateLine(end_date)%>">
				<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.end_date_picker,theform.end_date_picker.value,this);" tabindex="13">
				<INPUT TYPE="hidden" NAME="end_date"  value="">
			</td>
		</tr>
		<tr>
			<td align="right">合同金额:</td><!--合同金额-->
            <td align="left" colspan="3">
				从
				<input type="text" maxlength="16" name="min_rg_money" value="<%=Utility.trimNull(min_rg_money)%>" onkeydown="javascript:nextKeyPress(this);" size="10">
            	到
            	<input type="text" maxlength="16" name="max_rg_money" value="<%=Utility.trimNull(max_rg_money)%>" onkeydown="javascript:nextKeyPress(this);" size="10">
			</td>
		</tr>
			<tr>	
				<td align="right">合同编号:</td><!--合同编号-->
				<td ><input type="text" name="contract_sub_bh" size="15" onkeydown="javascript:nextKeyPress(this)" value="<%=contract_sub_bh%>"></td>										
			</tr>
			<tr>
				<td align="right">产品编号:</td><!--产品编号-->
				<td>
					<input type="text" maxlength="16" name="productid" value="" onkeydown="javascript:setProduct(this.value);" maxlength=8 size="10">&nbsp;
					<button type="button"  class="searchbutton" onclick="javascript:searchProduct(document.theform.productid.value);" /></button>
				</td>
				<td align="right">产品名称 :</td>
				<td>
					<input type="text" name="product_name" value="" onkeydown="javascript:nextKeyPress(this);" size="15">&nbsp;
					<button type="button"  class="searchbutton" onclick="javascript:searchProductName(product_name.value);"></button>		
				</td>
			</tr>
			<tr>	
				<td align="right"><%=LocalUtilis.language("class.productNumber",clientLocale)%> :</td ><!--产品选择-->
				<td align="left" colspan="3" id="select_id">
					<SELECT name="product_id" class="productname"><%=Argument.getProductListOptions(input_bookCode, overall_product_id, "",input_operatorCode,status)%></SELECT>
				</td>				
			</tr>		
			<tr>
				<td align="center" colspan="4">
					<!--确认-->
                    <button type="button"  class="xpbutton3" accessKey=o name="btnQuery" onclick="javascript:StartQuery();"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>O</u>)</button>&nbsp;&nbsp;&nbsp;&nbsp;								
				</td>
			</tr>	
	</table>
</div>
<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TR>
		<%//@ include file="menu.inc" %>
		<TD vAlign=top align=left>
		<TABLE cellSpacing=0 cellPadding=4 width="100%" align=center border=0>
			<TR>
				<TD>
				<table border="0" width="100%" cellspacing="0" cellpadding="0">
					<tr>
						<td ><IMG height=28 src="<%=request.getContextPath()%>/images/member.gif" width=32 border=0><b><%=menu_info%></b>
						<td align="right" colspan=4></td>
					</tr>
					<tr>
						<td align="left">
							<%
							String date_around = "";
							String money_around = "";
							if(qs_date.intValue() > 0 || end_date.intValue() > 0){
								date_around = "缴款日期：";
								if(qs_date.intValue() > 0)
									date_around = date_around + "从" + Format.formatDateLine(qs_date);
								if(end_date.intValue() > 0)
									date_around = date_around + "到" + Format.formatDateLine(end_date);
							}

							if(min_rg_money != null || max_rg_money != null){
								money_around = "合同金额：";
								if(min_rg_money != null)
									money_around = money_around + "从" + min_rg_money;
								if(max_rg_money != null)
									money_around = money_around + "到" + max_rg_money;
							}
							%>
							
							<b><%=date_around%>&nbsp;&nbsp;<%=money_around%></b>
						</td>
						<%if(!product_name.equals("")){%>
				        <td align=left style="color:red;"><b>产品名称：</b><%=product_name%></td>				        	
						<%}%>				
						<td align="right">
							<%if(input_operator.hasFunc(menu_id, 108)){%>
							<button type="button"  class="xpbutton3" name="queryButton" id="queryButton" accessKey=f">查询(<u>F</u>)</button>&nbsp;&nbsp;&nbsp;
							<%}if(input_operator.hasFunc(menu_id, 103)){%>
							<button type="button"  class="xpbutton2" name="btnNew" title="审核" onclick="javascript:confirmRemove();">审核</button>&nbsp;&nbsp;&nbsp;
							<%}%>
						</td>
					</tr>					
					<tr>
						<td colspan=4>
						<hr noshade color="#808080" size="1">
						</td>
					</tr>
				</table>
				<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%">
					<tr class="trh" >
						<td align="center" rowspan="2"><input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAllBox (document.theform.s_id,this);">&nbsp;&nbsp;产品名称</td>
						<td align="center" rowspan="2">合同编号</td>
						<td align="center" rowspan="2">客户名称</td>
                        <td align="center" rowspan="2" <%=showstring%>>证件号码</td>						
						<td align="center" rowspan="2">缴款日期</td>                       
						<td colspan="3" <%=showstring%>>受益银行信息</td>								
						<td align="center" rowspan="2">到账金额</td>
						<td align="center" rowspan="2" <%=showstring%>>到帐份额</td>
						<td align="center" rowspan="2" <%=showstring%>>收益级别</td>
						<td align="center" rowspan="2" <%=showstring%>>缴款方式</td>			
						<td align="center" rowspan="2" <%=showstring%>>手续费</td>
                        <td align="center" rowspan="2" <%=showstring%>>成立日期</td>	
						<td align="center" rowspan="2" <%=showstring%>>备注</td>		
					</tr>
					<tr  class="trh">
					    <td align="center" <%=showstring%>>受益银行</td>
						<td align="center" <%=showstring%>>受益账号</td>
						<td align="center" <%=showstring%>>受益户名</td>
					</tr>
<%
if(pageList != null)
	list = pageList.getRsList();
int iCurrent = 0;
if(list != null){
	for(; iCurrent < list.size(); iCurrent++){
		map = (Map)list.get(iCurrent);
		String fee_type_name = "";
		String show_product_name = "";
	
		if(!Utility.trimNull(map.get("FEE_TYPE")).equals("")){
			if(Utility.trimNull(map.get("FEE_TYPE")).equals("1"))
				fee_type_name = "认购费";
			if(Utility.trimNull(map.get("FEE_TYPE")).equals("2"))
				fee_type_name = "申购费";
			if(Utility.trimNull(map.get("FEE_TYPE")).equals("4"))
				fee_type_name = "份额转增费";	
		}
	
		show_product_name = Utility.trimNull(map.get("SUB_PRODUCT_NAME")).equals("") ? Utility.trimNull(map.get("PRODUCT_NAME")) : Utility.trimNull(map.get("PRODUCT_NAME")) + "(" + Utility.trimNull(map.get("SUB_PRODUCT_NAME")) + ")";
%>
					<tr class="tr<%=(iCurrent % 2)%>">
						<td class="tdh" align="left" >
						<table border="0" width="100%" cellspacing="0" cellpadding="0">
							<tr>
								<td width="10%">
									<input class="flatcheckbox" type="checkbox" name="s_id" value="<%=Utility.trimNull(map.get("SERIAL_NO"))%>"></td>
								<td width="90%" align="left"><%=show_product_name%></td>
							</tr>
						</table>
						</td>
						<td align="left"><%=Utility.trimNull(map.get("CONTRACT_SUB_BH"))%></td>
						<td align="left"><%=Utility.trimNull(map.get("CUST_NAME"))%></td>
                        <td align="left" <%=showstring%>><%=Utility.trimNull(map.get("CARD_ID"))%></td>
						<td align="center"><%=Format.formatDateCn(Utility.parseInt(Utility.trimNull(map.get("DZ_DATE")),new Integer(0)))%></td>
						<td align="left" <%=showstring%>><%=Utility.trimNull(map.get("BANK_NAME"))%></td>
						<td align="left" <%=showstring%>><%=Format.formatBankNo(Utility.trimNull(map.get("BANK_ACCT")))%></td>
						<td align="left" <%=showstring%>><%=Utility.trimNull(map.get("GAIN_ACCT"))%></td>					
						<td align="right"><%=Format.formatMoney(Utility.parseDecimal(Utility.trimNull(map.get("TO_MONEY")),new BigDecimal(0)))%></td>
						<td align="right" <%=showstring%>><%=Format.formatMoney(Utility.parseDecimal(Utility.trimNull(map.get("TO_AMOUNT")),new BigDecimal(0)))%></td>
						<td align="center"<%=showstring%>><%=Utility.trimNull(map.get("PROV_LEVEL_NAME"))%></td>
						<td align="center" <%=showstring%>><%=Utility.trimNull(map.get("JK_TYPE_NAME"))%></td>		
						<td align="right" <%=showstring%>><%=Utility.trimNull(fee_type_name)%><%=Format.formatMoney(Utility.parseDecimal(Utility.trimNull(map.get("FEE_MONEY")),new BigDecimal(0)))%></td>	
                        <td align="center" <%=showstring%>><%=Format.formatDateCn(Utility.parseInt(Utility.trimNull(map.get("START_DATE")),new Integer(0)))%></td>				
						<td align="right" <%=showstring%>><%=Utility.trimNull(map.get("SUMMARY"))%></td>
					</tr>
<%
	}
for (; iCurrent < pageList.getBlankSize(); iCurrent++){
%>
					<tr class="tr<%=(iCurrent % 2)%>">
						<td class="tdh" align="left"></td>
						<td align="center"></td>
						<td align="center"></td>
                        <td align="center" <%=showstring%>></td>
                        <td align="center"></td>
						<td align="center" <%=showstring%>></td>
						<td align="center" <%=showstring%>></td>					
						<td align="center" <%=showstring%>></td>		
						<td align="center"></td>
						<td align="center" <%=showstring%>></td>
						<td align="center" <%=showstring%>></td>						
						<td align="center" <%=showstring%>></td>		
						<td align="center" <%=showstring%>></td>
						<td align="center" <%=showstring%>></td>	
						<td align="center" <%=showstring%>></td>						
					</tr>
<%
}
%>
					<tr class="tr1">
						<td class="tdh" align="left" ><b>合计 <%=pageList.getTotalSize()%> 项</b></td>
						<td align="center"></td>					
						<td align="center"></td>
                        <td align="center" <%=showstring%>></td>
                        <td align="center"></td>								
						<td align="center" <%=showstring%>></td>
						<td align="center" <%=showstring%>></td>
						<td align="center" <%=showstring%>></td>						
						<td align="right"><%=pageList.getTotalValue("TO_MONEY")%></td>
						<td align="right" <%=showstring%>><%=pageList.getTotalValue("TO_AMOUNT")%></td>
						<td align="center" <%=showstring%>></td>	
						<td align="center" <%=showstring%>></td>	
						<td align="center" <%=showstring%>></td>
                        <td align="center" <%=showstring%>></td>
						<td align="center" <%=showstring%>></td>							
					</tr>
<%
}
%>
				</table>
				<br>
				<table border="0" width="100%">
					<tr valign="top">
						<td><%=pageList == null ? "" : pageList.getPageLink(sUrl,clientLocale)%></td>
					</tr>
				</table>

				</TD>
			</TR>
		</TABLE>
		</TD>
	</TR>
</TABLE>
</form><%@ include file="/includes/foot.inc"%> 
</BODY>
</HTML>
<%local.remove();%>
