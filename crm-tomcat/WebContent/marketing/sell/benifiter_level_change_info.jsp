<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.customer.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
boolean bSuccess = false;
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"), null);
String contract_bh = request.getParameter("contract_bh");

BenifitorLocal benifitor = EJBFactory.getBenifitor();
ProductLocal product=EJBFactory.getProduct();
CustomerLocal customer = EJBFactory.getCustomer();

BenifitorVO benVO = new BenifitorVO();
ProductVO proVO = new ProductVO();
CustomerVO custVO = new CustomerVO();

List benList = null;
List proList = null;
List custList = null;
Map benMap = null;
Map proMap = null;
Map custMap = null;

if(serial_no!=null&&serial_no.intValue()!=0){
	benVO.setSerial_no(serial_no);
	benList = benifitor.load(benVO);
	if(benList!=null&&benList.size()==1){
		benMap = (Map)benList.get(0);
	}
	
	if(benMap!=null){
		proVO.setProduct_id((Integer)benMap.get("PRODUCT_ID"));
		proList = product.load(proVO);
		if(proList!=null&&proList.size()==1)
			proMap = (Map)proList.get(0);
		custVO.setCust_id((Integer)benMap.get("CUST_ID"));
		custVO.setInput_man(input_operatorCode);
		custList = customer.listProcAll(custVO);
		if(custList!=null&&custList.size()==1)
			custMap = (Map)custList.get(0);
	}
}

if (request.getMethod().equals("POST"))
{
	benVO.setSerial_no(serial_no);
	benVO.setProv_level(request.getParameter("prov_level"));
	benVO.setInput_man(input_operatorCode);
	
	benifitor.modBenifitorLevel(benVO);
	bSuccess = true;
}
%>

<HTML>
<HEAD>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<BASE TARGET="_self">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<title><%=LocalUtilis.language("message.custInfo",clientLocale)%> </title>
<!--受益人信息-->

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/investment.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<script language=javascript>
<%if (bSuccess)
{
%>
	window.returnValue = 1;
	window.close();
<%}%>
function validateForm(form)
{
	if(form.serial_no.value=="")
	{
		if(form.customer_cust_id.value=="")	
		{
			sl_alert("<%=LocalUtilis.language("message.chooseBenefiter",clientLocale)%> ！");//请选择受益人
			return false;
		}	
	}
	
	if(theform.prov_level.value==""){
	 alert("<%=LocalUtilis.language("message.chooseProvLevel",clientLocale)%> ！");//请选择受益级别
	 return false;
	}
	return sl_check_update();
}

function showAcctNum(value)
{	
	if (trim(value) == "")
		bank_acct_num.innerText = "";
	else
		bank_acct_num.innerText = "(" + showLength(value) + " 位 )";
}

</script>
<BODY class="BODY body-nox" onkeydown="javascript:chachEsc(window.event.keyCode)">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="post" action="benifiter_level_change_info.jsp" onsubmit="javascript:return validateForm(this);">
<input type="hidden" name="serial_no" value="<%=serial_no%>">
<TABLE cellSpacing=0 cellPadding=0 border=0 width="100%"  >
	
		<TR>
			<TD vAlign=top align=left>
			<TABLE cellSpacing=0 cellPadding=1 border=0 width="100%">
				
					<TR>
						<TD align=center valign=top >
						<table border="0" width="100%" cellspacing="0" cellpadding="0">
							<tr>
								<td height="9" class="page-title">
								    <b><%=LocalUtilis.language("message.custInfo",clientLocale)%> </b><!--受益人信息-->
								</td>
							</tr>
						</table>
						
						<table border="0" width="100%" cellspacing="3" class="product-list">
							<tr>
								<td align="right" height="27"><%=LocalUtilis.language("class.contractID",clientLocale)%> :</td><!--合同编号-->
								<td height="27"><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" size="20" name="contract_bh" value="<%=contract_bh%>"></td>
							</tr>
							<!--<tr>
								<td align="right"><b>受益人信息</b></td>
								<td><button type="button"  class="xpbutton2" accessKey=e name="btnEdit" title="客户信息" onclick="javascript:getTransactionCustomer('customer','1');"><%//=strButton%></button></td>
							<tr>-->
							<tr>
								<td align="right"><%=LocalUtilis.language("class.custName2",clientLocale)%> :</td><!--受益人名称-->
								<td><input maxlength="100" readonly class='edline'  name="customer_cust_name" size="50" onkeydown="javascript:nextKeyPress(this);" value="<%=Utility.trimNull(custMap.get("CUST_NAME"))%>">&nbsp;&nbsp;&nbsp;
								</td>
							</tr>	
							<tr>
								<td align="right"><%=LocalUtilis.language("class.custTypeName",clientLocale)%> :</td><!--受益人类别-->
								<td ><INPUT readonly class='edline' name="customer_cust_type_name" size="20" value="<%=Utility.trimNull(custMap.get("CUST_TYPE_NAME"))%>" onkeydown="javascript:nextKeyPress(this);"></td>
							</tr>	
							<tr>
								<td align="right"><%=LocalUtilis.language("class.customerCardID",clientLocale)%> :</td><!--证件号码-->
								<td><input readonly class='edline' name="customer_card_id" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%=Utility.trimNull(custMap.get("CARD_ID"))%>" size="20"></td>
							</tr>
							<tr>
								<td align="right" height="22">*<%=LocalUtilis.language("class.benAmount",clientLocale)%> :</td><!--受益金额-->
								<td height="22"><input type="text"  readonly class='edline' onkeydown="javascript:nextKeyPress(this)" onkeyup="javascript:showCnMoney(this.value,ben_amount_cn)" name="ben_amount" size="20" value="<%=Utility.trimNull(Format.formatMoney(benMap.get("BEN_AMOUNT")==null?0:((BigDecimal)benMap.get("BEN_AMOUNT")).doubleValue(),2))%>"></td>
							</tr>
							<tr>
								<td align="right" width="40%" height="10"></td>
								<td width="60%" height="10"><span id="ben_amount_cn" class="span"></span></td>
							</tr>
							<tr>
								<td align="right" height="24"><font color=red>*</font><%=LocalUtilis.language("class.provLevel",clientLocale)%> :</td><!--受益优先级别-->
								<td height="24"><SELECT size="1" name="prov_level" onkeydown="javascript:nextKeyPress(this)" style="width: 120px">
									<%=Argument.getProvlevelOptions(Utility.trimNull(benMap.get("PROV_LEVEL")))%>
								</SELECT></td>
							</tr>
							<tr>
								<td align="right" height="29"><font color=red>*<%=LocalUtilis.language("class.moneyType",clientLocale)%> :</td><!--受益付款方式-->
								<td height="29"><select disabled size="1" name="jk_type" onkeydown="javascript:nextKeyPress(this)" style="width: 120px">
									<%=Argument.getJkTypeOptions(Utility.trimNull(benMap.get("JK_TYPE")))%>
								</select></td>
							</tr>
							<tr>
								<td align="right" height="26"><font color=red>*</font><%=LocalUtilis.language("class.bankSubName",clientLocale)%> :</td><!--受益付款银行-->
								<td height="26"><select disabled size="1" name="bank_id" onkeydown="javascript:nextKeyPress(this)" style="width: 120px">
									<%=Argument.getBankOptions(Utility.trimNull(benMap.get("BANK_ID")))%>
								</select></td>
							</tr>
							<tr>
								<td height="18">
								</td>
								<td height="18">
								<input type="text" readonly class='edline' onkeydown="javascript:nextKeyPress(this)" name="bank_sub_name" size="20" value="<%=Utility.trimNull(benMap.get("BANK_SUB_NAME"))%>">
								</td>
							</tr>
							<tr>
								<td align="right" height="21"><font color=red>*</font><%=LocalUtilis.language("class.bankAcct",clientLocale)%> :</td><!--银行账号-->
								<td height="21"><input type="text" readonly class='edline' onkeydown="javascript:nextKeyPress(this)" onkeyup="javascript:showAcctNum(this.value)" name="bank_acct" size="20" value="<%=Utility.trimNull(benMap.get("BANK_ACCT"))%>"><span id="bank_acct_num" class="span"></span></td>
							</tr>
							<tr>
								<td align="right"><%=LocalUtilis.language("class.bankAccountType",clientLocale)%> :</td><!--银行账号类型-->
								<td>
								
									<select size="1" name="bank_acct_type"  disabled onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 250px">
										<%=Argument.getBankAcctType(Utility.trimNull(benMap.get("BANK_ACCT_TYPE")))%>
									</select>
								</td>
							</tr>
							<tr>
								<td align="right"><%=LocalUtilis.language("class.benEndDate",clientLocale)%> </td><!--受益终止日期-->
								<td><INPUT  NAME="ben_end_date_picker" readonly class="edline" size="10"   value="<%=Utility.trimNull(Format.formatDateLine((Integer)benMap.get("BEN_END_DATE")))%>">
								<INPUT  TYPE="hidden" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.ben_end_date_picker,theform.ben_end_date_picker.value,this);" tabindex="13">
								<INPUT TYPE="hidden" NAME="ben_end_date"   value="<%=Utility.trimNull((Integer)benMap.get("BEN_END_DATE"))%>">
								</td>
							</tr>
							<tr>
								<td align="right"><font color=red>*</font><%=LocalUtilis.language("class.benDate2",clientLocale)%> :</td><!--受益起始日期-->
								<td>
								<INPUT type="text" NAME="ben_date_picker" readonly class='edline' value="<%=Utility.trimNull(Format.formatDateLine((Integer)benMap.get("BEN_DATE")))%>" onkeydown="javascript:nextKeyPress(this)">
								
								</td>
							 </tr>
							 <tr>  
							   <td align="right" height="24"><%=LocalUtilis.language("class.tempAcctName",clientLocale)%> :</td><!--受益人银行帐户名称-->
							   <td height="24"><input readonly class='edline'  name="cust_acct_name" type="text" onkeydown="javascript:nextKeyPress(this)" size=25  value=<%=Utility.trimNull(benMap.get("CUST_ACCT_NAME"))%>>  </td>
							</tr>				

							<tr>
								<td align="right" height="22"><font color=red>*</font><%=LocalUtilis.language("class.benPeriod2",clientLocale)%> :</td><!--受益期限-->
								<td height="22"><INPUT  NAME="valid_period"  readonly class='edline' size="10"   value="<%=Utility.trimNull(benMap.get("VALID_PERIOD"))%>"><%//=Utility.trimNull(Argument.getProductUnitName((Integer)proMap.get("PERIOD_UNIT")));)%></td>
							</tr>
						</table>
						<table border="0" width="100%" >
							<tr valign =top>
								<td align="right" height="15">
								<button type="button"  class="xpbutton3"  accessKey=s onclick="javascript:if(document.theform.onsubmit()){ disableAllBtn(true);document.theform.submit();}"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
								&nbsp;&nbsp;<!--保存-->
								<button type="button"  class="xpbutton3" accessKey=c onclick="javascript:window.returnValue=null;window.close();"><%=LocalUtilis.language("message.cancel",clientLocale)%> (<u>C</u>)</button>
								&nbsp;&nbsp;<!--取消-->
								</td>
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
<%benifitor.remove();
customer.remove();
%>