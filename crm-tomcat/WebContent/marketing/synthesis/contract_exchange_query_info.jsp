<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.web.*,java.io.*"%>
<%@ page import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.marketing.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%
String Query = request.getParameter("Query");
String serial_no = request.getParameter("serial_no");
Integer pro_id=Utility.parseInt(request.getParameter("product_id"),null);
String sPage=request.getParameter("page");
String sPagesize=request.getParameter("pagesize");
BigDecimal rg_money = Utility.parseDecimal(request.getParameter("rg_money"),null); 

BenChangeLocal change = EJBFactory.getBenChange();
BenChangeVO vo = new BenChangeVO();
vo.setSerial_no(new Integer(Utility.parseInt(serial_no, 0)));
Map map = (Map)change.listByChange(vo).get(0);

ContractLocal con = EJBFactory.getContract();
ContractVO conVo = new ContractVO();
conVo.setBook_code(input_bookCode);
conVo.setInput_man(input_operatorCode);
conVo.setProduct_id(pro_id);
conVo.setContract_bh((String)map.get("CONTRACT_BH"));
Map conMap = (Map)con.queryContract(conVo).get(0);
rg_money = (BigDecimal)conMap.get("RG_MONEY");

CustomerInfoLocal to_customer=EJBFactory.getCustomerInfo();
Map cusMap = null;
if (map.get("TO_CUST_ID")!=null) {
	CustomerInfoVO cusVo = new CustomerInfoVO();
	cusVo.setCust_id((Integer)map.get("TO_CUST_ID"));
	cusVo.setInput_man(input_operatorCode);
	cusMap = (Map)to_customer.load(cusVo).get(0);
}

boolean bsuccess=false;
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

<base target="_self">
</HEAD>

<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>

<script language=javascript>
<%if (request.getMethod().equals("POST")) { // 不会执行，因为负责post的js函数check()未被使用
	vo.setInput_man(input_operatorCode);
	vo.setSerial_no(new Integer(Utility.parseInt(serial_no, 0)));
	change.check(vo);
	bsuccess=true;
}
if(bsuccess){%>
	alert('审核通过！');
	//location='contract_exchange_check.jsp?page=<%=sPage%>&pagesize=<%=sPagesize%>';
<%}%>

function check() {
	if(sl_check_pass()) 
		document.theform.submit();
}
</script>

<BODY class="BODY">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="post" action="contract_exchange_info_check.jsp">
<input type="hidden" name="serial_no" value="<%=serial_no%>"> 
<input type="hidden" name="product_id" value="<%=pro_id%>">
<input type="hidden" name="page" value="<%=sPage%>">
<input type="hidden" name="pagesize" value="<%=sPagesize%>">

<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TR>
		<%//@ include file="menu.inc" %>
		<TD vAlign=top align=left>
		<table border="0" width="100%" cellspacing="0" cellpadding="10">
			<tr>
				<td>

				<table border="0" width="100%" cellspacing="1" cellpadding="4">
					<tr>
						<td><img border="0" src="/images/project.gif" width="32" height="32"><b>受益权转让查询</b></td>
					</tr>
					<tr>
						<td><hr size="1"></td>
					</tr>
				</table>

				<table border="0" width="100%" cellspacing="1" cellpadding="4">
					
					<tr>
						<td align="right">产品名称:</td>
						<td><input readonly class="edline" name="moneytype1" size="40" value="<%=Utility.trimNull(Argument.getProductName((Integer)map.get("PRODUCT_ID")))%>"></td>
						<td align="right">合同编号:</td>
						<td><input readonly class="edline" name="moneytype3" size="20" value="<%=Utility.trimNull(map.get("CONTRACT_SUB_BH"))%>"></td>
					</tr>
					<tr>
						<td colspan="4"><b>原受益人信息</b></td>
					</tr>
					<tr>
						<td align="right">编号:</td>
						<td><input readonly class="edline" name="moneytype2" size="20" value="<%=Utility.trimNull(map.get("FROM_CUST_NO"))%>"></td>
						<td align="right">名称:</td>
						<td><input readonly class="edline" name="moneytype2" size="20" value="<%=Utility.trimNull(map.get("FROM_CUST_NAME"))%>"></td>
					</tr>
					
					<tr>
					    <td align="right">原受益份额:</td>
						<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="rg_money" size="25" value="<%=Utility.trimNull(Format.formatMoney(Argument.getAmount(
	input_bookCode, (Integer)map.get("PRODUCT_ID"),
	(String)map.get("CONTRACT_BH"),
	(Integer)map.get("FROM_CUST_ID"),
	(String)map.get("JK_TYPE"))))%>"></td>
					</tr>
					<tr>
						<td colspan="4"><b>受让方信息</b></td>
					</tr>
					<tr style="display:<%=user_id.intValue() != 24 ? "none" : ""%>">
						<td align="right">转让合同编号:</td>
						<td><input type="text" readonly class="edline" name="transfer_contract_sub_bh" size="30" value="<%=Utility.trimNull(map.get("TRANSFER_CONTRACT_SUB_BH"))%>"></td>
					</tr>
					<tr>
						<td align="right">编号:</td>
						<td><input readonly class="edline" name="moneytype5" size="20" value="<%=Utility.trimNull(map.get("TO_CUST_NO"))%>"></td>
						<td align="right">名称:</td>
						<td><input readonly class="edline" name="connum1" size="20" value="<%=Utility.trimNull(map.get("TO_CUST_NAME"))%>"></td>
					</tr>
					<tr>
						<td align="right">证件类别:</td>
						<td><input readonly class="edline" name="connum1" size="20" value="<%=cusMap!=null?Utility.trimNull(cusMap.get("CARD_TYPE_NAME")):""%>"></td>
						<td align="right">证件号:</td>
						<td><input readonly class="edline" name="connum1" size="20" value="<%=cusMap!=null?Utility.trimNull(cusMap.get("CARD_ID")):""%>"></td>
					</tr>
					<tr>
						<td align="right">转让方式:</td>
						<td><input readonly class="edline" name="moneytype6" size="20" value="<%=Utility.trimNull(map.get("TRANS_FLAG_NAME"))%>"></td>
						<td align="right">转让份额:</td>
						<td><input readonly class="edline" name="contractsum" size="20" value="<%=Utility.trimNull(Format.formatMoney(map.get("TO_AMOUNT")==null?0:((BigDecimal)map.get("TO_AMOUNT")).doubleValue(),2))%>"></td>
					</tr>
					<%if(user_id.intValue()==5 || user_id.intValue()==24){%>
					<tr>
						<td align="right"  onkeydown="javascript:onKeyDown(this)">原受益人变更费:</td>
						<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="sxfee" size="25" 
								value="<%=Utility.trimNull(Format.formatMoney((BigDecimal)map.get("SX_FEE")))%>" tabindex="10"></td>
						<td align="right" onkeydown="javascript:onKeyDown(this)">现受益人变更费:</td>
						<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="sxfee1" size="25" 
								value="<%=Utility.trimNull(Format.formatMoney((BigDecimal)map.get("SX_FEE1")))%>" tabindex="10"></td>
					</tr>
						<%if(user_id.intValue()==5){%>
					<tr>
						<td  align="right" onkeydown="javascript:onKeyDown(this)">原受益人受益权代转让费:</td>
						<td ><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="sxfee2" size="25" 
								value="<%=Utility.trimNull(Format.formatMoney((BigDecimal)map.get("SX_FEE2")))%>" tabindex="10"></td>
						<td align="right" onkeydown="javascript:onKeyDown(this)">现受益人受益权代转让费:</td>
						<td ><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="sxfee3" size="25" 
								value="<%=Utility.trimNull(Format.formatMoney((BigDecimal)map.get("SX_FEE3")))%>" tabindex="10"></td>
					</tr>
						<%}%>
					<%}else{%>
					<tr>
						<td align="right">转让手续费:</td>
						<td ><input readonly class="edline" name="peoplenum" size="20" 
								value="<%=Utility.trimNull(Format.formatMoney(map.get("SX_FEE")==null?0:((BigDecimal)map.get("SX_FEE")).doubleValue(),2))%>"></td>
						<td align="right"></td>
						<td></td>
					</tr><%}%>
					<tr>
						<td align="right">银行名称:</td>
						<td ><input readonly class="edline" name="bank_name" size="20" value="<%=Utility.trimNull((String)map.get("BANK_NAME")+(String)map.get("BANK_SUB_NAME"))%>"></td>
						<td align="right">银行帐号:</td>
						<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bank_acct" size="25" value="<%=Utility.trimNull(map.get("BANK_ACCT"))%>"></td>
					</tr>
					<tr>
						<td align="right" width="15%">发行期利息转让:</td>
						<td width="35%"><input disabled class="flatcheckbox" type="checkbox" name="fx_change_flag"  value="1" 
											<%if(map.get("FX_CHANGE_FLAG")!=null && ((Integer)map.get("FX_CHANGE_FLAG")).intValue()==1) out.print("checked");%>></td>
						<td align="right" width="15%">收益转让:</td>
						<td width="35%"><select size="1" disabled  onkeydown="javascript:nextKeyPress(this)" name="sy_change_flag" style="width: 150">
						    <option <%if(map.get("SY_CHANGE_FLAG")!=null && ((Integer)map.get("SY_CHANGE_FLAG")).intValue()==1) out.print("selected");%>  value="1">转让未分配收益</option>
							<option <%if(map.get("SY_CHANGE_FLAG")!=null && ((Integer)map.get("SY_CHANGE_FLAG")).intValue()==2) out.print("selected");%> value="2">不转让未分配收益</option>
							<option <%if(map.get("SY_CHANGE_FLAG")!=null && ((Integer)map.get("SY_CHANGE_FLAG")).intValue()==3) out.print("selected");%> value="3">收益按时间分拆转让</option>
						</select></td>
					</tr>
					
					<tr>
					<td align="right">转让生效日期:</td>
						<td><INPUT TYPE="text" readonly class="edline" NAME="change_date_picker" class=selecttext 
				<%if(map.get("CHANGE_DATE")==null){%> value="<%=Format.formatDateLine(Utility.getCurrentDate())%>"
				<%}else{%>value="<%=Format.formatDateLine((Integer)map.get("CHANGE_DATE"))%>"<%}%> ></td>
					<td align="right">协议签署日期:</td>
						<td><INPUT readonly class="edline" TYPE="text" NAME="change_qs_date_picker" class=selecttext 
				<%if(map.get("CHANGE_QS_DATE")==null){%> value="<%=Format.formatDateLine(Utility.getCurrentDate())%>"
				<%}else{%>value="<%=Format.formatDateLine((Integer)map.get("CHANGE_QS_DATE"))%>"<%}%> >
				</td>
					</tr>
					<tr>
						<td align="right">备注:</td>
						<td colspan="3"><input readonly class="edline" name="data6" size="80" value="<%=Utility.trimNull(map.get("SUMMARY"))%>"></td>
					</tr></table>

				<table border="0" width="100%" cellspacing="1" cellpadding="4">
					<tr>
						<td>
						<table border="0" width="100%">
							<tr>
								<td align="right">
								<button type="button"  class="xpbutton3" accessKey=b id="btnCancel" name="btnCancel" onclick="javascript:disableAllBtn(true);history.back();">返回(<u>B</u>)</button>
								&nbsp;&nbsp;
								</td>
							</tr>
						</table>
						</td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		</TD></TABLE>
</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>
<%change.remove();
to_customer.remove();
%>