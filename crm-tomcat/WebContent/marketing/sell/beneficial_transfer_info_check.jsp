<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"),new Integer(0));
String summary = Utility.trimNull(request.getParameter("summary"));
Integer product_id=Utility.parseInt(request.getParameter("product_id"),new Integer(0));
String contract_bh=Utility.trimNull(request.getParameter("contract_bh"));
String first = Utility.trimNull(request.getParameter("first"));
Integer check_flag = Utility.parseInt(request.getParameter("check_flag"),new Integer(0));
Integer customer_cust_id = Utility.parseInt(request.getParameter("customer_cust_id"),new Integer(0));//���÷��ͻ�ID
String check_summary = Utility.trimNull(request.getParameter("check_summary"));

BenChangeLocal change_local = EJBFactory.getBenChanage();
CustomerLocal cust_local = EJBFactory.getCustomer();

Map change_map = new HashMap();
Map cust_map = new HashMap();
int	readonly=1;
String contractsum_cn="";
boolean is_succes_flag = false;

//��������Ȩת����Ϣ
if (request.getMethod().equals("POST")) {
	BenChangeVO change_vo = new BenChangeVO();
	change_vo.setInput_man(input_operatorCode);
	change_vo.setSerial_no(serial_no);
	change_vo.setSummary(summary);
	change_local.modiSummary(change_vo);

	if (check_flag.intValue()!=0) {
		change_vo.setCheck_flag(check_flag);
		change_vo.setCheck_summary(check_summary);
		change_local.check(change_vo);
	}

	is_succes_flag = true;

} else {
	//��Ų�Ϊ0ʱ���������Ȩת����Ϣ
	if (serial_no!=null && serial_no.intValue()!=0) {
		BenChangeVO change_vo = new BenChangeVO();
		change_vo.setSerial_no(serial_no);
		List change_list = change_local.listByChange(change_vo);
		if (change_list.size() > 0) {
			change_map = (Map)change_list.get(0);
			customer_cust_id = Utility.parseInt(Utility.trimNull(change_map.get("TO_CUST_ID")), new Integer(0));
			contractsum_cn=Utility.numToChinese1(Utility.trimNull(change_map.get("TO_AMOUNT")));
			//������÷��ͻ�ID��Ϊ0���������÷��ͻ���Ϣ
			if (customer_cust_id!=null && customer_cust_id.intValue()!=0) {
				CustomerVO cust_vo = new CustomerVO();
				cust_vo.setCust_id(customer_cust_id);
				cust_vo.setInput_man(input_operatorCode);
				List cust_list = cust_local.listProcAll(cust_vo);
				if (! cust_list.isEmpty()) cust_map = (Map)cust_list.get(0);
			}
		}
	}
}

cust_local.remove();
change_local.remove();
%>
<HTML>
<HEAD>
<title>����Ȩת�����</title>
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
    location.href = "beneficial_transfer_check_list.jsp";     
<%}%>

//ѡ�����÷��ͻ���Ϣ
function getTransactionCustomer(prefix,readonly) {
	var cust_id = getElement(prefix, "cust_id").value;//���Ĭ�Ͽͻ�ID(customer_cust_id)
	v = showModalDialog('<%=request.getContextPath()%>/marketing/customerInfo2.jsp?prefix=' + prefix + '&cust_id=' + cust_id + '&readonly='+readonly,'','dialogWidth:700px;dialogHeight:638px;status:0;help:0;');
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
function exchage() {
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
	return sl_check_update();
}

function pass() {
	if (document.theform.onsubmit()) {
		document.theform.check_flag.value = "2"; // ���ͨ��
		document.theform.submit();
	}
}

function deny() { // ��˲�ͨ��
	document.getElementById("check_summary_tr1").style.display = 
		document.getElementById("check_summary_tr2").style.display = "";

	document.getElementsByName("check_summary")[0].focus();
	document.getElementsByName("check_summary")[0].select();

	document.getElementById("deny_btn").onclick = function() {
			if (document.theform.onsubmit()) {
				document.theform.check_flag.value = "4";
				document.theform.submit();
			}
		};
}
</script>
</HEAD>
<BODY class="BODY">
<form name="theform" action="beneficial_transfer_info_check.jsp" method="post" onsubmit="javascript:return validateForm(this);">
<input type="hidden" name="check_flag" value="<%=check_flag%>"/>

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
				<td><img border="0" src="<%=request.getContextPath()%>/images/ico_area.gif" width="32" height="28"/><font color="#215dc6"><b><%=menu_info%></b></font></td>
			</tr>
			<tr>
				<td><hr size="1"></td>
			</tr>
		</table>
		<!--end ��ͷ-->
		<!--start ����-->
		<table border="0" width="100%" cellspacing="1" cellpadding="4">
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
					<select size="1" disabled="disabled"  onkeydown="javascript:nextKeyPress(this)" name="bank_id" style="width: 120">
						<%=Argument.getBankOptions(Utility.trimNull(change_map.get("BANK_ID")))%>
					</select>
					<input type="text" readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bank_sub_name" size="20" value="<%=Utility.trimNull(change_map.get("BANK_SUB_NAME"))%>">
				</td>						
				<td align="right"><%=LocalUtilis.language("class.bankAcct3",clientLocale)%> :</td><!--�����ʺ�-->
					<td><input type="text" readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bank_acct" size="25" value="<%=Utility.trimNull(change_map.get("BANK_ACCT"))%>" onkeyup="javascript:showAcctNum(this.value)">
					<span id="bank_acct_num" class="span"></span>
				</td>
			</tr>
			<tr>
				<td align="right">�����˻����� :</td>
				<td>
					<input type="text" name="bank_acct_name" value="<%=Utility.trimNull(change_map.get("BANK_ACCT_NAME"))%>" size="25" readonly class="edline"/>
				</td>
				<td colspan="2"></td>
			</tr>
			<tr>
				<td align="right" width="15%"><%=LocalUtilis.language("class.exchangeType",clientLocale)%> :</td><!--ת�÷�ʽ-->
				<td width="35%">
					<select size="1" disabled onkeydown="javascript:nextKeyPress(this)" name="exchangetype" style="width: 150">
						<%=Argument.getDictParamOptions_intrust(1115, Utility.trimNull(change_map.get("TRANS_FLAG")))%>
					</select>
				</td>
				<td align="right" width="15%"><%=LocalUtilis.language("class.contractSum",clientLocale)%> :</td><!--ת�÷ݶ�-->
				<td width="35%"><input readonly class="edline"  onkeydown="javascript:nextKeyPress(this)" onkeyup="javascript:showCnMoney(this.value,contractsum_cn)" name="contractsum" size="20" value="<%=Utility.trimNull(change_map.get("TO_AMOUNT"))%>"><span id="contractsum_cn" class="span"><%=contractsum_cn%>&nbsp;(Ԫ)</span></td>
			</tr>
		<%if(user_id.intValue()==5){%>
			<tr>
				<td align="right" onkeydown="javascript:onKeyDown(this)"><%=LocalUtilis.language("class.sxFee",clientLocale)%> :</td><!--ԭ�����˱����-->
				<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="sxfee" size="25" value="<%=Utility.trimNull(Format.formatMoney(Utility.stringToDouble(Utility.trimNull(change_map.get("SX_FEE")))))%>" tabindex="10"></td>
				<td align="right" onkeydown="javascript:onKeyDown(this)"><%=LocalUtilis.language("class.sxFee1",clientLocale)%> :</td><!--�������˱����-->
				<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="sxfee1" size="25" value="<%=Utility.trimNull(Format.formatMoney(Utility.stringToDouble(Utility.trimNull(change_map.get("SX_FEE1")))))%>" tabindex="10"></td>
			</tr>
				<tr>
				<td  align="right" onkeydown="javascript:onKeyDown(this)"><%=LocalUtilis.language("class.sxFee2",clientLocale)%> :</td><!--ԭ����������Ȩ��ת�÷�-->
				<td ><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="sxfee2" size="25" value="<%=Utility.trimNull(Format.formatMoney(Utility.stringToDouble(Utility.trimNull(change_map.get("SX_FEE2")))))%>" tabindex="10"></td>
				<td align="right" onkeydown="javascript:onKeyDown(this)"><%=LocalUtilis.language("class.sxFee3",clientLocale)%> :</td><!--������������Ȩ��ת�÷�-->
				<td ><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="sxfee3" size="25" value="<%=Utility.trimNull(Format.formatMoney(Utility.stringToDouble(Utility.trimNull(change_map.get("SX_FEE3")))))%>" tabindex="10"></td>
			</tr>
			<tr>
				<td align="right"><%=LocalUtilis.language("class.changeDate4",clientLocale)%> :</td><!--ת����Ч����-->
				<td><INPUT TYPE="text" readonly class="edline"  NAME="change_date_picker"  class=selecttext  value="<%=Format.formatDateLine(Utility.getCurrentDate())%>"></td>
				<td align="right"></td><td></td>
			</tr>
		<%}else{%>
			<tr>
				<td align="right"><%=LocalUtilis.language("class.transFee",clientLocale)%> :</td><!--ת��������-->
				<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="peoplenum" size="20" value="<%=Utility.trimNull(Format.formatMoney(Utility.stringToDouble(Utility.trimNull(change_map.get("SX_FEE"))).doubleValue(),2))%>"></td>
				<td align="right"><%=LocalUtilis.language("class.changeDate4",clientLocale)%> :</td><!--ת����Ч����-->
				<td><INPUT TYPE="text" readonly class="edline" NAME="change_date_picker" class=selecttext value="<%=Format.formatDateLine(Utility.parseInt(Utility.trimNull(change_map.get("CHANGE_DATE")),new Integer(0)))%>"></td>

			</tr>
		<%}%>
			<tr>
				<td align="right" width="15%"><%=LocalUtilis.language("class.transType",clientLocale)%> :</td><!--ת�����-->
				<td width="35%">
					<select size="1" disabled  onkeydown="javascript:nextKeyPress(this)" name="trans_type" style="width: 150">
						<%=Argument.getTransTypeOptions(Utility.trimNull(change_map.get("TRANS_TYPE")))%>
					</select>
				</td>
				<td align="right"><%=LocalUtilis.language("class.changeQSDate",clientLocale)%> :</td><!--Э��ǩ������-->
				<td><INPUT readonly class="edline" TYPE="text" NAME="change_qs_date_picker" class=selecttext value="<%=Format.formatDateLine(Utility.parseInt(Utility.trimNull(change_map.get("CHANGE_QS_DATE")),new Integer(0)))%>"></td>
			</tr>
			<tr>
				<td align="right" width="15%"><%=LocalUtilis.language("class.fxChangeFlag",clientLocale)%> :</td><!--ת�÷�������Ϣ-->
				<td width="35%"><input class="flatcheckbox" type="checkbox" name="fx_change_flag"  value="1" <%if(Utility.parseInt(Utility.trimNull(change_map.get("FX_CHANGE_FLAG")),0)==1) out.print("checked");%>></td>
				<td align="right" width="15%"><%=LocalUtilis.language("message.syChangeFlag",clientLocale)%> :</td><!--ת������-->
				<td width="35%">
					<select size="1" disabled  onkeydown="javascript:nextKeyPress(this)" name="sy_change_flag" style="width: 150">
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
					<input onkeydown="javascript:nextKeyPress(this)" name="summary" size="80" value="<%=Utility.trimNull(change_map.get("SUMMARY"))%>">
				</td>
			</tr>	
			<!--end ���÷���Ϣ-->	

			<!--start ��˲�ͨ����� -->
			<tr id="check_summary_tr1" style="display:none">
				<td><b>��˲�ͨ����� </b></td>
				<td></td>
				<td align="right"></td>
				<td></td>
			<tr>
			<tr id="check_summary_tr2" style="display:none">
				<td align="right">��� :</td>
				<td colspan=3>
					<textarea rows="3" name="check_summary" onkeydown="javascript:nextKeyPress(this)" cols="83"><%=check_summary%></textarea>
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
							<%if (Utility.parseInt((Integer)change_map.get("CHECK_FLAG"),new Integer(0)).intValue() == 1){%>	
								<button type="button"  class="xpbutton4" onkeydown="javascript:nextKeyPress(this)" accessKey=c onclick="javascript:pass();">���ͨ�� (<u>C</u>)</button>
								&nbsp;&nbsp;
								<button type="button"  class="xpbutton5" id='deny_btn' onkeydown="javascript:nextKeyPress(this)" accessKey=n onclick="javascript:deny();">��˲�ͨ�� (<u>N</u>)</button>						
								&nbsp;&nbsp;
							<%}else{%>
								<button type="button"  class="xpbutton4" onkeydown="javascript:nextKeyPress(this)" accessKey=s onclick="javascript:if(document.theform.onsubmit()){document.theform.submit();}">�޸ı�ע (<u>S</u>)</button>		
								&nbsp;&nbsp;
							<%}%>
								<button type="button"  class="xpbutton3" accessKey=b onclick="javascript:history.back();"><%=LocalUtilis.language("message.back",clientLocale)%> (<u>B</u>)</button>
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