<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"),new Integer(0));
Integer product_id=Utility.parseInt(request.getParameter("product_id"),new Integer(0));
String contract_bh=Utility.trimNull(request.getParameter("contract_bh"));
String first = Utility.trimNull(request.getParameter("first"));

Integer customer_cust_id = Utility.parseInt(request.getParameter("customer_cust_id"),new Integer(0));//���÷��ͻ�ID

BenChangeLocal change_local = EJBFactory.getBenChanage();//����Ȩת��Bean
BenChangeVO change_vo = new BenChangeVO();
CustomerLocal cust_local = EJBFactory.getCustomer();//�ͻ�Bean

Map change_map = new HashMap();
Map cust_map = new HashMap();
int	readonly=1;
String contractsum_cn="";
boolean is_succes_flag = false;

//��������Ȩת����Ϣ
if(request.getMethod().equals("POST")) {
	java.math.BigDecimal to_amounts = Utility.parseDecimal(request.getParameter("contractsum1"), new java.math.BigDecimal(0));
	String exchangetype = request.getParameter("exchangetype");
	java.math.BigDecimal sx_fee = Utility.parseDecimal(request.getParameter("peoplenum1"),new java.math.BigDecimal(0));
	String check_man = Utility.trimNull(request.getParameter("check_man"));
	String Summary = request.getParameter("Summary");
	String bank_id=Utility.trimNull(request.getParameter("bank_id"));
	String bank_sub_name = Utility.trimNull(request.getParameter("bank_sub_name"));
	String bank_acct=Utility.trimNull(request.getParameter("bank_acct"));
	String bank_acct_name = Utility.trimNull(request.getParameter("bank_acct_name"));
	change_vo.setBook_code(input_bookCode);
	change_vo.setSerial_no(serial_no);
	change_vo.setTo_cust_id(customer_cust_id);
	change_vo.setProduct_id(product_id);
	change_vo.setContract_bh(contract_bh);
	change_vo.setTrans_flag(exchangetype);
	change_vo.setFrom_cust_id(new Integer(Utility.parseInt(request.getParameter("from_cust_id"), 0)));
	change_vo.setJk_type(request.getParameter("jk_type"));
	change_vo.setTo_amount(to_amounts);
	if(user_id.intValue()==5){
		change_vo.setSx_fee(Utility.parseDecimal(Utility.trimNull(request.getParameter("sxfee")),new java.math.BigDecimal(0)));
		change_vo.setSx_fee1(Utility.parseDecimal(Utility.trimNull(request.getParameter("sxfee1")),new java.math.BigDecimal(0)));
		change_vo.setSx_fee2(Utility.parseDecimal(Utility.trimNull(request.getParameter("sxfee2")),new java.math.BigDecimal(0)));
		change_vo.setSx_fee3(Utility.parseDecimal(Utility.trimNull(request.getParameter("sxfee3")),new java.math.BigDecimal(0)));
	}else{
		change_vo.setSx_fee(sx_fee);
	}
	change_vo.setCheck_man(new Integer(Utility.parseInt(check_man, 0)));
	change_vo.setSummary(Summary);
	change_vo.setBank_id(bank_id);
	change_vo.setBank_acct(bank_acct);
	change_vo.setBank_acct_name(bank_acct_name);
	change_vo.setBank_sub_name(bank_sub_name);
	change_vo.setChange_date(new Integer(request.getParameter("change_date")));
	change_vo.setTrans_type(Utility.trimNull(request.getParameter("trans_type")));
	change_vo.setFx_change_flag(Utility.parseInt(request.getParameter("fx_change_flag"),new Integer(2)));
	change_vo.setSy_change_flag(Utility.parseInt(request.getParameter("sy_change_flag"),new Integer(2)));
	change_vo.setChange_qs_date(new Integer(request.getParameter("change_qs_date")));
	change_local.modi(change_vo);
	is_succes_flag = true;

}else{
	//��Ų�Ϊ0ʱ���������Ȩת����Ϣ
	if (serial_no != null || !(serial_no.equals(new Integer(0)))) {
		change_vo.setSerial_no(serial_no);
		List change_list = change_local.listByChange(change_vo);
		if (change_list.size() > 0){
			change_map = (Map)change_list.get(0);
			customer_cust_id = Utility.parseInt(Utility.trimNull(change_map.get("TO_CUST_ID")), new Integer(0));
			contractsum_cn=Utility.numToChinese1(Utility.trimNull(change_map.get("TO_AMOUNT")));
			//������÷��ͻ�ID��Ϊ0���������÷��ͻ���Ϣ
			if (customer_cust_id.intValue() != 0) {
				CustomerVO cust_vo = new CustomerVO();
				cust_vo.setCust_id(customer_cust_id);
				cust_vo.setInput_man(input_operatorCode);
				List cust_list = cust_local.listProcAll(cust_vo);
				if (cust_list.size() > 0) cust_map = (Map)cust_list.get(0);			
			}
		}
	}
}

cust_local.remove();
change_local.remove();
%>
<HTML>
<HEAD>
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
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/investment.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<script language="javascript">
<%if(is_succes_flag){%>
	sl_alert("<%=LocalUtilis.language("message.saveOk",clientLocale)%> !");//����ɹ�
    location = "beneficial_transfer_list.jsp?firstFlag=1&product_id=<%=product_id%>";     
<%}%>

//ѡ�����÷��ͻ���Ϣ
function getTransactionCustomer(prefix,readonly) {
	var cust_id = getElement(prefix, "cust_id").value;//���Ĭ�Ͽͻ�ID(customer_cust_id)
	var v = showModalDialog('<%=request.getContextPath()%>/marketing/customerInfo2.jsp?prefix=' + prefix + '&cust_id=' + cust_id + '&readonly='+readonly,'','dialogWidth:700px;dialogHeight:638px;status:0;help:0;');
	if (v) {
		document.getElementById("aa").value = v[7];
		alert(document.getElementById("aa").value);
		showTransactionCustomer(prefix, v);
	}	
	return v != null;
}

//ѡ����������
function selectBank(value) {
	if (value != "") selectOthers();
}

//�����˺�ѡ�������
function changeInput(obj) {
	if(document.theform.inputflag.value==1)
	{
		obj.innerText="<%=LocalUtilis.language("message.choose",clientLocale)%> ";//ѡ��
		document.theform.bank_acct.style.display="";
		document.theform.bank_acct2.style.display="none";
		document.theform.inputflag.value=2;
	}
	else
	{
		obj.innerText="<%=LocalUtilis.language("message.input",clientLocale)%> ";//����
		document.theform.bank_acct.style.display="none";
		document.theform.bank_acct2.style.display="";
		document.theform.inputflag.value=1;
	}
}

//ѡ��ת�÷�ʽ���ת�ý��
function exchage()
{
	if(document.theform.exchangetype.value == '111501')//ȫ��ת��
	{	
		document.theform.contractsum.disabled = true;	
		//document.theform.contractsum.value =;
	}
	else
	{
		document.theform.contractsum.disabled = false;	
	}
	//showCnMoney(document.theform.contractsum.value,document.theform.contractsum_cn);
}

//���������˺�ʱ��ʾλ��
function showAcctNum(value) {		
	if (trim(value) == "")
		bank_acct_num.innerText = "";
	else
		bank_acct_num.innerText = "(" + showLength(value) + " λ )";
}

//������֤
function validateForm(theform) { 	
	if(theform.customer_cust_id.value=="")	{
		sl_alert("<%=LocalUtilis.language("message.chooseAssigneeTip",clientLocale)%> ��");//��ѡ�����÷�
		return false;
	}
	if(!sl_checkChoice(theform.exchangetype,"<%=LocalUtilis.language("class.exchangeType",clientLocale)%> "))//ת�÷�ʽ
		return false;
	
	if(!sl_checkChoice(theform.trans_type,"<%=LocalUtilis.language("class.transType",clientLocale)%> "))//ת�����
		return false;
		
	if(!sl_checkDecimal(theform.contractsum, "<%=LocalUtilis.language("class.contractSum",clientLocale)%> ", 13,3,1))	//ת�÷ݶ�
		return false;
	if(sl_parseFloat(theform.contractsum.value)>sl_parseFloat(theform.moneytype2.value)){
		sl_alert("<%=LocalUtilis.language("message.TransferableSharesLack",clientLocale)%> !");//��ת�÷ݶ��
		return false;
	}
	theform.contractsum1.value=sl_parseFloat(theform.contractsum.value);
		
	var userid = <%=user_id.intValue()%>;
	if(userid==5){
		if(!sl_checkDecimal(theform.sxfee,"<%=LocalUtilis.language("class.sxFee",clientLocale)%> ", 13,3,0))	//ԭ�����˱����
			return false;
		if(!sl_checkDecimal(theform.sxfee1,"<%=LocalUtilis.language("class.sxFee1",clientLocale)%> ", 13,3,0))//�������˱����	
			return false;
		if(!sl_checkDecimal(theform.sxfee2,"<%=LocalUtilis.language("class.sxFee2",clientLocale)%> ", 13,3,0))//ԭ����������Ȩ��ת�÷�	
			return false;
		if(!sl_checkDecimal(theform.sxfee3,"<%=LocalUtilis.language("class.sxFee3",clientLocale)%> ", 13,3,0))//������������Ȩ��ת�÷�	
			return false;
	}else{
		if(!sl_checkDecimal(theform.peoplenum,"<%=LocalUtilis.language("class.transFee",clientLocale)%> ", 13,3,0))//ת��������
			return false;
		theform.peoplenum1.value=sl_parseFloat(theform.peoplenum.value);
	}
	if(!sl_checkDate(document.theform.change_date_picker,"<%=LocalUtilis.language("class.transDate",clientLocale)%> "))	return false;//ת������ 
	syncDatePicker(document.theform.change_date_picker, document.theform.change_date);	
	if(!sl_checkDate(document.theform.change_qs_date_picker,"<%=LocalUtilis.language("class.changeQSDate",clientLocale)%> "))	return false; //Э��ǩ������  
	syncDatePicker(document.theform.change_qs_date_picker, document.theform.change_qs_date);	
	
	if(!sl_check(theform.Summary,"<%=LocalUtilis.language("class.customerSummary",clientLocale)%> ",200,0))//��ע
		return false; 
	if(document.theform.fx_change_flag.checked)
		document.theform.fx_change_flag.value="1";		
	if(!sl_checkChoice(theform.sy_change_flag,"<%=LocalUtilis.language("message.syChangeFlag",clientLocale)%> "))//ת������
		return false; 
	return sl_check_update();
}
</script>
</HEAD>
<BODY class="BODY body-nox">
<form name="theform" action="beneficial_transfer_info.jsp" method="post"  onsubmit="javascript:return validateForm(this);">
<input type="hidden" name="aa" id="aa" value="">
<input type="hidden" name="customer_cust_id" value="<%=customer_cust_id%>">
<input type="hidden" name="contractsum1" value="0"> 
<input type="hidden" name="peoplenum1" value="0">
<input type="hidden" name="from_cust_id" value="<%=Utility.trimNull(change_map.get("FROM_CUST_ID"))%>">
<input type="hidden" name="jk_type" value="<%=Utility.trimNull(change_map.get("JK_TYPE"))%>">
<input type="hidden" name="serial_no" value="<%=serial_no%>">
<table height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>
<tr valign="top" align="left">
	<td>
		<!--start ��ͷ-->
		<table border="0" width="100%" cellspacing="1" cellpadding="4">
			<tr>
				<td class="page-title"><font color="#215dc6"><b><%=menu_info%></b></font></td>
			</tr>
		</table>
		<!--end ��ͷ-->
		<!--start ����-->
		<table border="0" width="100%" cellspacing="1" cellpadding="4" class="product-list">
			<!--start ԭ��������Ϣ-->
			<tr>
				<td align="right"><%=LocalUtilis.language("class.productName",clientLocale)%> :</td><!--��Ʒ����-->
				<td><input readonly class="edline" name="product_name" size="50" maxlength="6" value="<%=Utility.trimNull(Argument.getProductName(Utility.parseInt(Utility.trimNull(change_map.get("PRODUCT_ID")),new Integer(0))))%>"></td>
				<td align="right"><%=LocalUtilis.language("class.contractID",clientLocale)%> :</td ><!--��ͬ���-->
				<td><input readonly class="edline" name="contract_bh" size="20" maxlength="6" value="<%=Utility.trimNull(change_map.get("CONTRACT_SUB_BH"))%>"></td>
			</tr>
			<tr>
				<td colspan="3"><b><%=LocalUtilis.language("menu.fromCustomerInfo",clientLocale)%> </b></td><!--ԭ��������Ϣ-->
			</tr>
			<tr>
				<td align="right"><%=LocalUtilis.language("class.custName2",clientLocale)%> :</td><!--����������-->
				<td><input readonly class="edline" name="from_cust_id" size="40" maxlength="6" value="<%=Utility.trimNull(change_map.get("FROM_CUST_NAME"))%>"></td>
				<td align="right"><%=LocalUtilis.language("class.moneyType",clientLocale)%> :</td><!--���渶�ʽ-->
				<td><input readonly class="edline" name="moneytype" size="20" maxlength="6" value="<%=Utility.trimNull(Argument.getDictContentIntrust(Utility.trimNull(change_map.get("JK_TYPE"))))%>"></td>
			</tr>
			<tr>
				<td align="right"><%=LocalUtilis.language("class.moneyType2",clientLocale)%> :</td><!--��ת�÷ݶ�-->
				<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="moneytype2" size="25" value="<%=Utility.trimNull(Format.formatMoney(Utility.stringToDouble(Utility.trimNull(change_map.get("TO_AMOUNT")))))%>"></td>
				<td align="right"></td>
				<td></td>
			</tr>
			<!--end ԭ��������Ϣ-->
			<!--start ���÷���Ϣ-->
			<tr>
				<td><b><%=LocalUtilis.language("menu.assigneeInfo",clientLocale)%> </b></td><!--���÷���Ϣ-->
				<td><button type="button"  class="xpbutton3" accessKey=e name="btnEdit" title="<%=LocalUtilis.language("message.customerInfomation",clientLocale)%> " 
				        onclick="javascript:getTransactionCustomer('customer','<%=readonly%>');"><%=LocalUtilis.language("message.view",clientLocale)%> 
				    </button><!--�ͻ���Ϣ--><!--�鿴-->
				</td>
				<td align="right"></td>
				<td></td>
			<tr>
			<tr>
				<td align="right" width="137"><%=LocalUtilis.language("class.customerCustName",clientLocale)%> :</td><!--���÷�����-->
				<td colspan=3><input maxlength="100" readonly class='edline'  name="customer_cust_name" size="50" onkeydown="javascript:nextKeyPress(this);" value="<%=Utility.trimNull(cust_map.get("CUST_NAME"))%>">&nbsp;&nbsp;&nbsp;
				</td>
			</tr>
			<tr>
				<td align="right" width="137"><%=LocalUtilis.language("class.customerCustTypeName",clientLocale)%> :</td><!--���÷����-->
				<td width="433"><INPUT readonly class='edline' name="customer_cust_type_name" size="20" value="<%=Utility.trimNull(cust_map.get("CUST_TYPE_NAME"))%>" onkeydown="javascript:nextKeyPress(this);"></td>
				<td align="right"><%=LocalUtilis.language("class.customerCardID",clientLocale)%> :</td><!--֤������-->
				<td><input readonly class='edline' name="customer_card_id" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%=Utility.trimNull(cust_map.get("CARD_ID"))%>" size="20"></td>
			</tr>
			<tr>
				<td align="right"><%=LocalUtilis.language("class.telephone",clientLocale)%> :</td><!--סլ�绰-->
				<td><input readonly class='edline' name="customer_h_tel" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%=Utility.trimNull(cust_map.get("CUST_TEL"))%>" size="20"></td>
				<td align="right"><%=LocalUtilis.language("class.customerMobile",clientLocale)%> :</td><!--�ֻ�-->
				<td><input readonly class='edline' name="customer_mobile" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%=Utility.trimNull(cust_map.get("MOBILE"))%>" size="20"></td>
			</tr>
			<tr>
				<td align="right"><%=LocalUtilis.language("class.postAddress",clientLocale)%> :</td><!--��ϵ��ַ-->
				<td ><INPUT readonly class='edline' name="customer_post_address" size="50" value="<%=Utility.trimNull(cust_map.get("POST_ADDRESS"))%>" onkeydown="javascript:nextKeyPress(this);"></td>
				<td align="right"><%=LocalUtilis.language("class.postcode",clientLocale)%> :</td><!--��������-->
				<td ><INPUT readonly class='edline' name="customer_post_code" size="20" value="<%=Utility.trimNull(cust_map.get("POST_CODE"))%>" onkeydown="javascript:nextKeyPress(this);"></td>
			</tr>
			<tr>
				<td align="right"><%=LocalUtilis.language("class.bankName3",clientLocale)%> :</td><!--��������-->
				<td>
					<select size="1"  onkeydown="javascript:nextKeyPress(this)" name="bank_id" style="width: 120">
						<%=Argument.getBankOptions(Utility.trimNull(change_map.get("BANK_ID")))%>
					</select>
					<input type="text" onkeydown="javascript:nextKeyPress(this)" name="bank_sub_name" size="20" value="<%=Utility.trimNull(change_map.get("BANK_SUB_NAME"))%>">
				</td>						
				<td align="right"><%=LocalUtilis.language("class.bankAcct3",clientLocale)%> :</td><!--�����ʺ�-->
					<td><input type="text" onkeydown="javascript:nextKeyPress(this)" name="bank_acct" size="25" value="<%=Utility.trimNull(change_map.get("BANK_ACCT"))%>" onkeyup="javascript:showAcctNum(this.value)">
					<span id="bank_acct_num" class="span"></span>
				</td>
			</tr>
			<tr>
				<td align="right">�����˻����� :</td>
				<td>
					<input type="text" name="bank_acct_name" value="<%=Utility.trimNull(change_map.get("BANK_ACCT_NAME"))%>" size="25"/>
				</td>
				<td colspan="2"></td>
			</tr>
			<tr>
				<td align="right" width="15%"><%=LocalUtilis.language("class.exchangeType",clientLocale)%> :</td><!--ת�÷�ʽ-->
				<td width="35%">
					<select size="1" onkeydown="javascript:nextKeyPress(this)" name="exchangetype" style="width: 150">
						<%=Argument.getDictParamOptions_intrust(1115, Utility.trimNull(change_map.get("TRANS_FLAG")))%>
					</select>
				</td>
				<td align="right" width="15%"><%=LocalUtilis.language("class.contractSum",clientLocale)%> :</td><!--ת�÷ݶ�-->
				<td width="35%"><input  onkeydown="javascript:nextKeyPress(this)" onkeyup="javascript:showCnMoney(this.value,contractsum_cn)" name="contractsum" size="20" value="<%=Utility.trimNull(change_map.get("TO_AMOUNT"))%>"><span id="contractsum_cn" class="span"><%=contractsum_cn%>&nbsp;(Ԫ)</span></td>
			</tr>
		<%if(user_id.intValue()==5){%>
			<tr>
				<td align="right" onkeydown="javascript:onKeyDown(this)"><%=LocalUtilis.language("class.sxFee",clientLocale)%> :</td><!--ԭ�����˱����-->
				<td><input onkeydown="javascript:nextKeyPress(this)" name="sxfee" size="25" value="<%=Utility.trimNull(Format.formatMoney(Utility.stringToDouble(Utility.trimNull(change_map.get("SX_FEE")))))%>" tabindex="10"></td>
				<td align="right" onkeydown="javascript:onKeyDown(this)"><%=LocalUtilis.language("class.sxFee1",clientLocale)%> :</td><!--�������˱����-->
				<td><input onkeydown="javascript:nextKeyPress(this)" name="sxfee1" size="25" value="<%=Utility.trimNull(Format.formatMoney(Utility.stringToDouble(Utility.trimNull(change_map.get("SX_FEE1")))))%>" tabindex="10"></td>
			</tr>
				<tr>
				<td  align="right" onkeydown="javascript:onKeyDown(this)"><%=LocalUtilis.language("class.sxFee2",clientLocale)%> :</td><!--ԭ����������Ȩ��ת�÷�-->
				<td ><input onkeydown="javascript:nextKeyPress(this)" name="sxfee2" size="25" value="<%=Utility.trimNull(Format.formatMoney(Utility.stringToDouble(Utility.trimNull(change_map.get("SX_FEE2")))))%>" tabindex="10"></td>
				<td align="right" onkeydown="javascript:onKeyDown(this)"><%=LocalUtilis.language("class.sxFee3",clientLocale)%> :</td><!--������������Ȩ��ת�÷�-->
				<td ><input onkeydown="javascript:nextKeyPress(this)" name="sxfee3" size="25" value="<%=Utility.trimNull(Format.formatMoney(Utility.stringToDouble(Utility.trimNull(change_map.get("SX_FEE3")))))%>" tabindex="10"></td>
			</tr>
			<tr>
				<td align="right"><%=LocalUtilis.language("class.changeDate4",clientLocale)%> :</td><!--ת����Ч����-->
				<td><INPUT TYPE="text" NAME="change_date_picker"  class=selecttext  value="<%=Format.formatDateLine(Utility.getCurrentDate())%>">
				<INPUT TYPE="button" value="��" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.change_date_picker,theform.change_date_picker.value,this);" tabindex="13">
				<INPUT TYPE="hidden" NAME="change_date"   value=""></td>
				<td align="right"></td><td></td>
			</tr>
		<%}else{%>
			<tr>
				<td align="right"><%=LocalUtilis.language("class.transFee",clientLocale)%> :</td><!--ת��������-->
				<td><input onkeydown="javascript:nextKeyPress(this)" name="peoplenum" size="20" value="<%=Utility.trimNull(Format.formatMoney(Utility.stringToDouble(Utility.trimNull(change_map.get("SX_FEE"))).doubleValue(),2))%>"></td>
				<td align="right"><%=LocalUtilis.language("class.changeDate4",clientLocale)%> :</td><!--ת����Ч����-->
				<td><INPUT TYPE="text" NAME="change_date_picker" class=selecttext 
				<%if(change_map.get("CHANGE_DATE")==null){%> value="<%=Format.formatDateLine(Utility.getCurrentDate())%>"
				<%}else{%>value="<%=Format.formatDateLine(Utility.parseInt(Utility.trimNull(change_map.get("CHANGE_DATE")),new Integer(0)))%>"<%}%> >
				<INPUT TYPE="button" value="��" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.change_date_picker,theform.change_date_picker.value,this);" tabindex="13">
				<INPUT TYPE="hidden" NAME="change_date"   value=""></td>
			</tr>
		<%}%>
			<tr>
				<td align="right"  width="15%"><%=LocalUtilis.language("class.transType",clientLocale)%> :</td><!--ת�����-->
				<td width="35%">
					<select size="1"  onkeydown="javascript:nextKeyPress(this)" name="trans_type" style="width: 150">
						<%=Argument.getTransTypeOptions(Utility.trimNull(change_map.get("TRANS_TYPE")))%>
					</select>
				</td>
				<td align="right"><%=LocalUtilis.language("class.changeQSDate",clientLocale)%> :</td><!--Э��ǩ������-->
				<td><INPUT TYPE="text" NAME="change_qs_date_picker" class=selecttext 
				<%if(change_map.get("CHANGE_QS_DATE")==null){%> value="<%=Format.formatDateLine(Utility.getCurrentDate())%>"
				<%}else{%>value="<%=Format.formatDateLine(Utility.parseInt(Utility.trimNull(change_map.get("CHANGE_QS_DATE")),new Integer(0)))%>"<%}%> >
				<INPUT TYPE="button" value="��" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.change_qs_date_picker,theform.change_qs_date_picker.value,this);" tabindex="13">
				<INPUT TYPE="hidden" NAME="change_qs_date"   value=""></td>
			</tr>
			<tr>
				<td align="right" width="15%"><%=LocalUtilis.language("class.fxChangeFlag",clientLocale)%> :</td><!--ת�÷�������Ϣ-->
				<td width="35%"><input class="flatcheckbox" type="checkbox" name="fx_change_flag"  value="1" <%if(Utility.parseInt(Utility.trimNull(change_map.get("FX_CHANGE_FLAG")),0)==1) out.print("checked");%>></td>
				<td align="right" width="15%"><%=LocalUtilis.language("message.syChangeFlag",clientLocale)%> :</td><!--ת������-->
				<td width="35%">
					<select size="1"  onkeydown="javascript:nextKeyPress(this)" name="sy_change_flag" style="width: 150">
						<option <%if(Utility.parseInt(Utility.trimNull(change_map.get("SY_CHANGE_FLAG")),0)==1) out.print("selected");%>  value="1"><%=LocalUtilis.language("message.syChangeFlag1",clientLocale)%> </option>
						<!--ת��δ��������-->
						<option <%if(Utility.parseInt(Utility.trimNull(change_map.get("SY_CHANGE_FLAG")),0)==2) out.print("selected");%> value="2"><%=LocalUtilis.language("message.syChangeFlag3",clientLocale)%> </option>
						<!--���水ʱ��ֲ�ת��-->
					</select>
				</td>
			</tr>
			<tr>
				<td align="right"><%=LocalUtilis.language("class.customerSummary",clientLocale)%> :</td><!--��ע-->
				<td colspan="3">
					<input onkeydown="javascript:nextKeyPress(this)" name="Summary" size="80" value="<%=Utility.trimNull(change_map.get("SUMMARY"))%>">
				</td>
			</tr>	
			<!--end ���÷���Ϣ-->	
		</table>
		<!--end ����-->
		<table border="0" width="100%" cellspacing="1" cellpadding="4">
			<tr>
				<td>
					<table border="0" width="100%">
						<tr>
							<td align="right">
							<button type="button"  class="xpbutton3" onkeydown="javascript:nextKeyPress(this)" accessKey=s id="btnSave" name="btnSave" onclick="javascript:if(document.theform.onsubmit()){window.document.theform.submit();}"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)
							</button><!--����-->
							&nbsp;&nbsp;
							<button type="button"  class="xpbutton3" accessKey=b id="btnCancel" name="btnCancel" onclick="javascript:history.back();"><%=LocalUtilis.language("message.back",clientLocale)%> (<u>B</u>)
							</button><!--����-->
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
</form>
</BODY>
</HTML>