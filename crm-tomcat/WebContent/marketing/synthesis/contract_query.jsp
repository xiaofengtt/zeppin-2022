<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.web.*,java.io.*"%>
<%@ page import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.marketing.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
int _page = Utility.parseInt(sPage, 1);
int pagesize = Utility.parseInt(sPagesize, 8);

String contract_sub_bh = Utility.trimNull(request.getParameter("contract_sub_bh"));
Integer start_date = Utility.parseInt(request.getParameter("start_date"), new Integer(0));
Integer end_date = Utility.parseInt(request.getParameter("end_date"), new Integer(0));
Integer sub_product_id = Utility.parseInt(request.getParameter("sub_product_id"), new Integer(0));

ContractLocal contract = EJBFactory.getContract();
ContractVO vo = new ContractVO();
vo.setProduct_id(overall_product_id);
vo.setSub_product_id(sub_product_id);
vo.setContract_sub_bh(contract_sub_bh);
vo.setStart_date(start_date);
vo.setEnd_date(end_date);
vo.setBook_code(input_bookCode);
vo.setInput_man(input_operatorCode);
IPageList pageList = contract.queryEndContract(vo, _page, pagesize);
List list = pageList.getRsList();

sUrl = sUrl + "&product_id=" + overall_product_id
			+ "&contract_sub_bh=" + contract_sub_bh
			+ "&start_date=" + start_date
			+ "&end_date=" + end_date
			+ "&sub_product_id=" + sub_product_id;
%>

<HTML>
<HEAD>
<TITLE></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="/includes/default.css" type=text/css rel=stylesheet>
<LINK href="/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="javascript" SRC="/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/calendar.js"></SCRIPT>
<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>
<script type='text/javascript' src='/dwr/interface/utilityService.js'></script>
<script language=javascript>
window.onload = function(){
initQueryCondition()
productChange(<%=overall_product_id%>);
};

function refreshPage()
{
	//if(!sl_checkDate(document.theform.start_date_picker,"开始日期"))	return false;
	//if(!sl_checkDate(document.theform.end_date_picker,"结束日期"))	return false;
	syncDatePicker(theform.start_date_picker, theform.start_date);
	syncDatePicker(theform.end_date_picker, theform.end_date);
	document.theform.btnQuery.disabled = true;
    var sub_product_id = 0;
    if(document.theform.sub_product_id)
    sub_product_id = document.theform.sub_product_id.value;
	location = 'contract_query.jsp?page=<%=sPage%>'
			+ '&pagesize=' + document.theform.pagesize.value
			+ '&product_id=' + document.theform.product_id.value
			+ '&contract_sub_bh=' + document.theform.contract_sub_bh.value
			+ '&start_date=' + document.theform.start_date.value
			+ '&end_date=' + document.theform.end_date.value
			+ '&sub_product_id=' + sub_product_id;
}

function StartQuery()
{
	refreshPage();
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
	utilityService.getSubProductJson(product_id,3,function(data){
		var json = eval(data);
		DWRUtil.addOptions(sub_product_id,{"0":"请选择"});
		for(i=0;i<json.length;i++){
			DWRUtil.addOptions(sub_product_id,json[i]);
	  }
	  DWRUtil.setValue('sub_product_id',<%=sub_product_id%>);
  });
}
</script>
</HEAD>
<BODY class="BODY"><%@ include file="/includes/waiting.inc"%> 
<form name="theform" method="get" action="contract_query.jsp">

<div id="queryCondition" class="qcMain" style="display:none;width:420px;">
<table  id="qcTitle" class="qcTitle" align="center" width="99%" cellspacing="0" cellpadding="2">
  	<tr>
   		<td>查询条件：</td>
   		<td align="right"><button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose"
       		onclick="javascript:cancelQuery();"></button></td>
  	</tr>
</table>

<table>
	<tr>
		<td>产品名称:</td>
		<td colspan=3>
			<select size="1" onkeydown="javascript:nextKeyPress(this)" name="product_id" onchange="javascript:productChange(this.value)" class=productname>
				<%=Argument.getProductListOptions(input_bookCode,overall_product_id, "", input_operatorCode,0)%>
			</select>
		</td>
	</tr>
	<tr id="tr_sub_product_id" style="display:none">			
		<td align="right">子产品:</td>
		<td align="left" colspan="3" id="td_sub_product_id">
			<SELECT name="sub_product_id" onkeydown="javascript:nextKeyPress(this)" class="productname">
					<%=Argument.getSubProductOptions2(overall_product_id, new Integer(0),sub_product_id,0,"")%> 
			</SELECT>
		</td>
	</tr>
	<tr>
		<td>合同编号:</td>
		<td colspan=3>
			<input type="text" maxlength="16" name="contract_sub_bh" size="12" onkeydown="javascript:nextKeyPress(this)" value="<%=Utility.trimNull(contract_sub_bh)%>"> 
		</td>
	</tr>
	<tr>	
		<td align="left" colspan=4>到期日期范围: 从 
			<INPUT TYPE="text" NAME="start_date_picker" class=selecttext <%if(end_date!=null){%>value="<%=Format.formatDateLine(start_date)%>"<%}%>>
			<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.start_date_picker,theform.start_date_picker.value,this);" tabindex="13">
			<INPUT TYPE="hidden" NAME="start_date" value="">
			<br/>　　　　　　 到 <INPUT TYPE="text" NAME="end_date_picker" class=selecttext <%if(end_date!=null){%>value="<%=Format.formatDateLine(end_date)%>"<%}%>>
 			<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.end_date_picker,theform.end_date_picker.value,this);" tabindex="13">
 			<INPUT TYPE="hidden" NAME="end_date" value=""></td>
	</tr>
	<tr>
		<td align=center colspan=4>
			<button type="button"  class="xpbutton3" accessKey=o name="btnQuery" onclick="javascript:disableAllBtn(true);StartQuery();">确定(<u>O</u>)</button>
		</td>
	</tr>
</table>
</div>

<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TR>
		<%//@ include file="menu.inc" %>
		<TD vAlign=top align=left>
		<TABLE cellSpacing=0 cellPadding=4 width="100%" border=0>
			<TR>
				<TD>
				<table cellspacing="1" cellpadding="2" border="0" width="100%">
					<tr>
						<td align="center">
						<table border="0" cellspacing="1" width="100%">
							<%//int  showTabInfo =  showTabInfoTop.intValue();
							//if(showTabInfo != 1){ %>
							<tr>
								<td colspan="1"><IMG src="/images/member.gif" align=absBottom border=0 width="32" height="28"><b><%=menu_info%></b></td>
							</tr>
							<%//} %>
							<tr>
								<td align=right> 
									<button type="button"  class="xpbutton3" accessKey=f id="queryButton" name="queryButton">查询(<u>F</u>)</button>&nbsp;&nbsp;&nbsp;
									<button type="button"  style="display: none" class="xpbutton5" accessKey=p id="btnDel" name="btnDel" onclick="javascript:printall();">打印全部(<u>P</u>)</button>
									<button type="button"  style="display: none" class="xpbutton3" accessKey=p id="btnDel" name="btnDel" onclick="javascript:printcard();">打印(<u>P</u>)</button>
								</td>
							</tr>							
							<tr>
								<td colspan="1">
									<hr noshade color="#808080" size="1">
								</td>
							</tr>
						</table>
						<table border="0" cellspacing="1" cellpadding="2"  class="tablelinecolor" width="100%">
							<tr class="trh">
								<td width="12%" align="center" >客户编号</td>
								<td width="12%" align="center" >客户名称</td>
								<td width="12%" align="center" >合同编号</td>
								<td width="13%" align="center" >产品名称</td>
								<td width="13%" align="center" sort="num">合同金额</td>
								<td width="13%" align="center" >币种</td>
								<td width="13%" align="center" >成立日期</td>
								<td width="13%" align="center" >到期日期</td>
							</tr>
<%
int iCount = 0;
int iCurrent = 0;
Integer serial_no;
for (int i=0; i<list.size(); i++) {
	Map map = (Map)list.get(i);
	serial_no = (Integer)map.get("SERIAL_NO");
%>

							<tr class="tr<%=(iCurrent % 2)%>">
								<td class="tdh" width="9%" align="center" >
									<table border="0" width="100%" cellspacing="0" cellpadding="0">
										<tr>
											<td width="10%"></td>
											<td width="90%" align="left"><%=map.get("CUST_NO")%></td>
										</tr>
									</table>
								</td>
								<td width="10%" align="left"  ><%=Utility.trimNull(map.get("CUST_NAME"))%></td>
								<td width="10%" align="center"><%=Utility.trimNull(map.get("CONTRACT_SUB_BH"))%></td>
								<td width="10%" align="left"  ><%=Utility.trimNull(map.get("SUB_PRODUCT_NAME")).equals("") ? Utility.trimNull(map.get("PRODUCT_NAME")) : Utility.trimNull(map.get("PRODUCT_NAME")) + "(" + Utility.trimNull(map.get("SUB_PRODUCT_NAME")) + ")"%></td>
								<td width="10%" align="right" ><%=Utility.trimNull(Format.formatMoney((BigDecimal)map.get("RG_MONEY")))%></td>
								<td width="10%" align="center"><%=Utility.trimNull(map.get("CURRENCY_NAME"))%></td>
								<td width="10%" align="center"><%=Format.formatDateCn((Integer)map.get("START_DATE"))%></td>
								<td width="10%" align="center"><%if(map.get("END_DATE")!=null && ((Integer)map.get("END_DATE")).intValue()>=20990101) out.print("无固定期限"); else out.print(Format.formatDateCn((Integer)map.get("END_DATE")));%></td>
							</tr>
<%
	iCurrent++;
	iCount++;
}

for (; iCurrent < pageList.getPageSize(); iCurrent++) {
%>
							<tr class="tr<%=(iCurrent % 2)%>">
								<td class="tdh" width="9%" align="center" ></td>
								<td width="10%" align="center" ></td>
								<td width="10%" align="center" ></td>
								<td width="10%" align="center" ></td>
								<td width="10%" align="center" ></td>
								<td width="10%" align="center" ></td>
								<td width="10%" align="center" ></td>
								<td width="10%" align="center" ></td>
							</tr>
							<%}
%>
							<tr class="trbottom">
								<td class="tdh" width="9%" align="center" ><b>合计 <%=pageList.getTotalSize()%> 项</b></td>
								<td width="10%" align="center" >-</td>
								<td width="10%" align="center" >-</td>
								<td width="10%" align="center" >-</td>
								<td width="10%" align="center" >-</td>
								<td width="10%" align="center" >-</td>
								<td width="10%" align="center" ></td>
								<td width="10%" align="center" ></td>
							</tr>
						</table>

						<br>

						<table border="0" width="100%">
							<tr valign="top">
								<td><%=pageList.getPageLink(sUrl)%></td>
								<td align="right"></td>
							</tr>
						</table>
						</td>
					</tr>
				</table>
				</TD>
			</TR>
		</TABLE>
		</TD>
</TABLE>
</form><%@ include file="/includes/foot.inc"%> 
</BODY>
</HTML>
<%contract.remove();
%>
