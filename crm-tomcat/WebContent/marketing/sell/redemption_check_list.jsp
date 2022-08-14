<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
Integer product_id = Utility.parseInt(request.getParameter("product_id"),overall_product_id);
String contract_bh = Utility.trimNull(request.getParameter("contract_bh"));
Integer sq_date = Utility.parseInt(request.getParameter("sq_date"),null);
String[] totalColumn = {"REDEEM_AMOUNT","REDEEM_AMOUNT0"};

RedeemLocal redeem = EJBFactory.getRedeem();
RedeemVO vo = new RedeemVO();
vo.setProduct_id(product_id);
vo.setContract_bh(contract_bh);
vo.setSq_date(sq_date);
vo.setInput_man(input_operatorCode);
vo.setCheck_flag(new Integer(1));

if (user_id.intValue()==2){//北京信托要求：此页面默认显示为ALL
	//再取一次pagesize，以覆盖/includes/parameter.inc中的取值
	sPagesize = enfo.crm.tools.Utility.trimNull(request.getParameter("pagesize"));
	if ("".equals(sPagesize))
		sPagesize = "-1";
}
IPageList pageList = redeem.listAll(vo,totalColumn,Utility.parseInt(sPage,1),Utility.parseInt(sPagesize,-1));

sUrl = "redemption_check_list.jsp?pagesize=" + sPagesize +
								 "&product_id="+product_id+
								 "&sq_date="+sq_date;


String options = Argument.getProductListOptions(new Integer(1), product_id, "",input_operatorCode,28);
options = options.replaceAll("\"", "'");
%>

<HTML>
<HEAD>
<TITLE></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>

<script language=javascript>
function checkinfo(serial_no,ben_serial_no)
{
		location = 'redemption_check_info.jsp?flag=1&serial_no=' + serial_no + '&ben_serial_no='+ben_serial_no;
}


function refreshPage()
{
	
	syncDatePicker(document.theform.change_date_picker, document.theform.sq_date);
	disableAllBtn(true);
	location = 'redemption_check_list.jsp?page=<%=sPage%>&pagesize=' + document.theform.pagesize.value +
												'&product_id=' + document.theform.product_id.value +
												"&contract_bh="+ document.theform.contract_bh.value +
												"&sq_date=" + document.theform.sq_date.value;
}

window.onload = function(){
initQueryCondition()};

function StartQuery()
{
	refreshPage();
}

function searchProductName(value){
	var list = [];
	var list1 = [];
	document.getElementById("select_id").innerHTML = "<SELECT style='width:410' name='product_id' class='productname' onkeydown='javascript:nextKeyPress(this)'>"+"<%=options%>"+"</SELECT>";
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
</script>
</HEAD>
<BODY class="BODY" >
<form name="theform" method="get">

<div id="queryCondition" class="qcMain" style="display:none;width:550px;">
	<table  id="qcTitle" class="qcTitle" align="center" width="100%" cellspacing="0" cellpadding="2">
	  <tr>
	   <td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ：</td><!--查询条件-->
	   <td align="right"><button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose"
	       onclick="javascript:cancelQuery();"></button></td>
	  </tr>
	</table>
	<table border="0" width="100%" cellspacing="2" cellpadding="2">	
	<tr>
		<td  align="right">产品名称 :</td>
		<td  align="left" >
			<input type="text" name="product_name" value="" onkeydown="javascript:nextKeyPress(this);" size="15">&nbsp;
			<button type="button"  class="searchbutton" onclick="javascript:searchProductName(product_name.value);" />		
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.productNumber",clientLocale)%> :</td ><!--产品选择-->
		<td align="left" colspan="3" id="select_id">
			<SELECT style="width:410" name="product_id" class="productname" onkeydown="javascript:nextKeyPress(this)">
						<%=Argument.getProductListOptions(new Integer(1), product_id, "",input_operatorCode,28)%>
			</SELECT>
		</td>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.contractID",clientLocale)%> :  <!--合同编号-->
        </td><td align="left"><input type="text" name="contract_bh"
								onkeydown="javascript:nextKeyPress(this)" size="20"
								value=<%=contract_bh%>></td>	
		<td align="right"><%=LocalUtilis.language("class.sqDate",clientLocale)%> : </td><!--赎回日期-->
		<td>
			<INPUT TYPE="text" NAME="change_date_picker"value="<%=Format.formatDateLine(sq_date)%>">
			<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.change_date_picker,theform.change_date_picker.value,this);" tabindex="13">
			<INPUT TYPE="hidden" NAME="sq_date"   value="">							
		</td>						
	</tr>
	<tr>						
        <!--确认-->
		<td align="center" colspan="4"><button type="button"  class="xpbutton3" accessKey=s name="btnQuery"
									onclick="javascript:StartQuery();"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>S</u>)</button>&nbsp;&nbsp;&nbsp;&nbsp;								
			</td>
		</tr>						
	</table>
</div>


<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>


	<TR>
		<TD vAlign=top align=left>
		<TABLE cellSpacing=0 cellPadding=4 width="100%" align=center border=0>
			<TR>
				<TD>
				<table border="0" width="100%" cellspacing="0" cellpadding="0">
					<tr>
						<td class="page-title">
							<font color="#215dc6"><b><%=menu_info%></b></font></td>
						<td>
					</tr>
					<tr>
					<td align="right">
					<div class="btn-wrapper">
						<%if (input_operator.hasFunc(menu_id, 108)) {%>
						<!--查询-->
                        <button type="button"  class="xpbutton3" accessKey=q id="queryButton" name="queryButton"><%=LocalUtilis.language("message.query",clientLocale)%> (<u>Q</u>)</button>
						<%}%>
						</div>
						<br/>
					</td>
					</tr>
					
				</table>
				<table id="table3" border="0" cellspacing="1" cellpadding="2" 
					class="tablelinecolor" width="100%" >
					<tr class="trh">
						<td width="11%"><%=LocalUtilis.language("class.contractID",clientLocale)%> </td><!--合同编号-->
						<td><%=LocalUtilis.language("class.productName",clientLocale)%></td><!--产品名称-->
						<td><%=LocalUtilis.language("class.customerName",clientLocale)%> </td><!--客户名称-->
						<td align="center" >银行名称</td><!-- 银行名称 -->
						<td align="center" ><%=LocalUtilis.language("class.bankAcct3",clientLocale)%> </td><!--银行帐号-->
						<td align="center" >受益级别</td><!--受益级别-->
						<td align="center" >收益级别</td><!--收益级别-->
						<td sort="num">申请赎回份额</td>
						<td sort="num">实际赎回份额</td>
						<td>是否强制赎回</td>
						<td>是否全额赎回</td>
						<td><%=LocalUtilis.language("class.sqDate",clientLocale)%> </td><!--赎回日期-->
						<td><%=LocalUtilis.language("message.review",clientLocale)%> </td><!--复核-->
					</tr>
					<%int iCount = 0;
int iCurrent = 0;
java.math.BigDecimal redeem_amount = null;
BigDecimal redeem_amount0 = null;
String coerce_flag_name = "";
String fullamount_flag_name = "";
java.math.BigDecimal redeem_amount_coerce = null;
List list = pageList.getRsList();
for(int i=0;i<list.size();i++) {
	Map map = (Map)list.get(i);
	Integer serial_no = Utility.parseInt(Utility.trimNull(map.get("SERIAL_NO")),null);
	String product_name = Utility.trimNull(map.get("PRODUCT_NAME"));
	String contract_sub_bh = Utility.trimNull(map.get("CONTRACT_SUB_BH"));
	String cust_name = Utility.trimNull(map.get("CUST_NAME"));
	sq_date = Utility.parseInt(Utility.trimNull(map.get("SQ_DATE")),null);
	redeem_amount = Utility.parseDecimal(Utility.trimNull(map.get("REDEEM_AMOUNT")),null);
	if(redeem_amount!=null){
		redeem_amount = redeem_amount.setScale(2);
	}
	Integer ben_serail_no = Utility.parseInt(Utility.trimNull(map.get("BEN_SERIAL_NO")),null);
	
	String prov_level_name = Utility.trimNull(map.get("PROV_LEVEL_NAME"));
	String prov_flag_name = Utility.trimNull(map.get("PROV_FLAG_NAME"));
	String bank_name = Utility.trimNull(map.get("BANK_NAME"));
	String bank_acct = Utility.trimNull(map.get("BANK_ACCT"));
	String sub_product_name = Utility.trimNull(map.get("SUB_PRODUCT_NAME"));
	if(!"".equals(sub_product_name) && sub_product_name!=null)
		sub_product_name = "("+sub_product_name+")";
	redeem_amount0 = Utility.parseDecimal(Utility.trimNull(map.get("REDEEM_AMOUNT0")),null);
	redeem_amount_coerce = Utility.parseDecimal(Utility.trimNull(map.get("REDEEM_AMOUNT_COERCE")),null);

	if(Utility.parseInt(Utility.trimNull(map.get("COERCE_FLAG")),new Integer(2)).intValue() ==1)
		coerce_flag_name = "<font color='red'>是:" + Format.formatMoney(redeem_amount_coerce) + "</font>";
	else
		coerce_flag_name = "否";

	if(Utility.parseInt(Utility.trimNull(map.get("FULLAMOUNT_FLAG")),new Integer(2)).intValue() ==1)
		fullamount_flag_name = "<font color='red'>是</font>";
	else
		fullamount_flag_name = "否";
%>

					<tr class="tr<%=(iCurrent % 2)%>">
						<td class="tdh">
							<%=Utility.trimNull(contract_sub_bh)%>
						</td>
						<td align="left" ><%=Utility.trimNull(product_name)%><%=sub_product_name%></td>
						<td align="left" ><%=Utility.trimNull(cust_name)%></td>
						<td align="left" ><%=bank_name%></td>
						<td align="left" ><%=bank_acct%></td>
						<td align="left" ><%=prov_flag_name %></td>
						<td align="left" ><%=prov_level_name %></td>
						<td align="right" ><%=Format.formatMoney(redeem_amount)%></td>
						<td align="right" ><%=Format.formatMoney(redeem_amount0)%></td>
						<td align="center"><%=Utility.trimNull(coerce_flag_name)%></td>
						<td align="center"><%=Utility.trimNull(fullamount_flag_name)%></td>
						<td align="center" ><%=Utility.trimNull(Format.formatDateCn(sq_date))%></td>
						<td align="center" >
						<%if (input_operator.hasFunc(menu_id, 100)){%>
							<a href="javascript:checkinfo(<%=Utility.trimNull(serial_no)%>,<%=Utility.trimNull(ben_serail_no)%>);">
	           			<img border="0" src="<%=request.getContextPath()%>/images/check.gif" width="16" height="16">
	           		</a>
						<%}%>
						</td>
						
					</tr>
<%iCurrent++;
iCount++;
}
for (; iCurrent < pageList.getPageSize(); iCurrent++) {
%>
					<tr class="tr<%=(iCurrent % 2)%>">
						<td class="tdh"  width="3%" align="center"></td>
						<td align="center" ></td>
						<td align="center" ></td>
						<td align="center" ></td>
						<td align="center" ></td>
						<td align="center" ></td>
						<td align="center" ></td>
						<td align="center" ></td>
						<td align="center" ></td>
						<td align="center" ></td>
						<td align="center" ></td>
						<td align="center" ></td>
						<td align="center" ></td>
					</tr>
					<%}
%>
					<tr class="trbottom">
						<!--合计--><!--项-->
                        <td class="tdh">&nbsp;<b><%=LocalUtilis.language("message.total",clientLocale)%> <%=pageList.getTotalSize()%> <%=LocalUtilis.language("message.entries",clientLocale)%> </b></td>
						<td align="center"></td>
			            <td align="center"></td>
			            <td align="center"></td>
			            <td align="center"></td>
			            <td align="center"></td>       
			            <td align="center"></td> 
						<td align="right"><%=Format.formatMoney(Utility.parseBigDecimal(pageList.getTotalValue("REDEEM_AMOUNT"),new BigDecimal(0.00)))%></td>
						<td align="right"><%=Format.formatMoney(Utility.parseBigDecimal(pageList.getTotalValue("REDEEM_AMOUNT0"),new BigDecimal(0.00)))%></td>
						<td align="center"></td>
			            <td align="center"></td>       
						<td align="center"></td>
			            <td align="center"></td> 
					</tr>
				</table>

				<br>

				<table border="0" width="100%" class="page-link">
					<tr valign="top">
						<td><%=pageList.getPageLink(sUrl)%></td>
						<td align="right">
						<!--刷新当前页面--><!--刷新-->
						<input type="hidden" class="xpbutton3" accessKey=r id="btnRefresh"
							name="btnRefresh" title='<%=LocalUtilis.language("message.currentPageRefrash",clientLocale)%> '
							onclick="javascript:refreshPage();"><!--<%=LocalUtilis.language("message.refresh",clientLocale)%> (<u>R</u>)</button>
						&nbsp;&nbsp;&nbsp;-->
                        <!-- 返回上一页--><!-- 返回-->
						<input type="hidden" class="xpbutton3" accessKey=b id="btnReturn"
							name="btnReturn" title='<%=LocalUtilis.language("message.backLastPage",clientLocale)%> '
							onclick="javascript:history.back();"><!--<%=LocalUtilis.language("message.back",clientLocale)%> (<u>B</u>)</button>
						&nbsp;&nbsp;&nbsp;--></td>
					</tr>
				</table>

				</TD>
			</TR>
		</TABLE>
		</TD>
	</TR>
</TABLE>
</form>
<%@ include file="/includes/foot.inc"%>	
</BODY>
</HTML>
<%redeem.remove();%>


