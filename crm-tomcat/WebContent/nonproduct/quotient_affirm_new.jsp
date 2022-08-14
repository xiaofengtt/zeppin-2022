<%@ page contentType="text/html; charset=GBK" import="enfo.crm.web.*, enfo.crm.marketing.*,enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//���ҳ�洫�ݱ���
String sQuery = request.getParameter("sQuery");
Integer q_subscribeId = Utility.parseInt(request.getParameter("q_subscribeId"),new Integer(-1));
BigDecimal q_changeAmount = Utility.parseDecimal(Utility.trimNull(request.getParameter("q_changeAmount")),new BigDecimal(0),2,"1");
Integer q_changeDate  = Utility.parseInt(request.getParameter("q_changeDate"),new Integer(0));

Integer q_nonproductId = Utility.parseInt(request.getParameter("q_nonproductId"),new Integer(0));
String q_nonproductName = Utility.trimNull(request.getParameter("q_nonproductName"));
String q_custName = Utility.trimNull(request.getParameter("q_custName"));
Integer q_checkFlag = Utility.parseInt(request.getParameter("q_checkFlag"),new Integer(0));
Integer pay_date = Utility.parseInt(request.getParameter("pay_date"),new Integer(0));
String bank_id = Utility.trimNull(request.getParameter("bank_id")); //�����˻�ID
String bank_name = Utility.trimNull(request.getParameter("bank_name"));
String bank_sub_name = Utility.trimNull(request.getParameter("bank_sub_name")); 
String bank_acct = Utility.trimNull(request.getParameter("bank_acct")); 
String acct_name = Utility.trimNull(request.getParameter("acct_name")); 
String subscribe_bh = Utility.trimNull(request.getParameter("contract_sub_bh"));
Integer sign_date = Utility.parseInt(request.getParameter("sign_date"),new Integer(0));
BigDecimal subscribe_money = Utility.parseDecimal(Utility.trimNull(request.getParameter("rg_money")),new BigDecimal(0),2,"1");
Integer check_flag = Utility.parseInt(request.getParameter("check_flag"),new Integer(0));
String check_flag_name;

Integer cust_id = Utility.parseInt(Utility.trimNull(request.getParameter("cust_id")),new Integer(0));
String q_status = Utility.trimNull(request.getParameter("q_status"));
Integer customer_id = Utility.parseInt(Utility.trimNull(request.getParameter("customer_cust_id")),new Integer(0));

//�ͻ���Ϣ
String cust_no = "";
String cust_name = "";
Integer cust_type = new Integer(0);
String cust_type_name = "";
Integer service_man= new Integer(0);
String card_id = "";
Integer card_type = new Integer(0);
//------------------------------------
String cust_tel = "";
String o_tel = "";
String h_tel = "";
String mobile= "";
String e_mail = "";
String bp = "";
String post_code = "";
String post_address = "";
Integer is_link = new Integer(0);
String legal_man= null;
String contact_man= null;

Map map = null;
Map map_cust = null;
List rsList_cust = null;

//��ö��󼰽����
QuotientAffirmLocal quotientaffirm = EJBFactory.getQuotientAffirmLocal();
QuotientAffirmVO quotientvo = new QuotientAffirmVO();
SubscribeVO vo = new SubscribeVO();
CustomerVO cust_vo = new CustomerVO();
CustomerVO c_vo = new CustomerVO();//ͬ����
Customer_INSTLocal  customer_inst = EJBFactory.getCustomer_INST();//ͬ���ÿͻ�����
CustomerLocal customer = EJBFactory.getCustomer();//�ͻ�
SubscribeLocal subscribe = EJBFactory.getSubscribeLocal();

boolean bSuccess = false;

//�����Ϲ���Ϣ 
if(request.getMethod().equals("POST")){
	quotientvo.setSubscribe_id(q_subscribeId);
	quotientvo.setChange_amount(q_changeAmount);
	quotientvo.setChange_date(q_changeDate);
	quotientaffirm.append(quotientvo,input_operatorCode);
	bSuccess = true;
}
else{
	vo.setSubscribe_id(q_subscribeId);
	vo.setNonproduct_id(q_nonproductId);
	vo.setNonproduct_name(q_nonproductName);
	vo.setCust_name(q_custName);
	vo.setCheck_flag(q_checkFlag);
	vo.setStatus(q_status);
	List list = subscribe.load(vo, input_operatorCode);
	if (list!=null && list.size()>0)
	{
		map = (Map)list.get(0);
	}
	if(map != null){
		q_nonproductId = Utility.parseInt(Utility.trimNull(map.get("NONPRODUCT_ID")),null);
		q_nonproductName = Utility.trimNull(map.get("NONPRODUCT_NAME"));
		subscribe_bh = Utility.trimNull(map.get("SUBSCRIBE_BH"));
		sign_date = Utility.parseInt(Utility.trimNull(map.get("SIGN_DATE")),new Integer(0));
		subscribe_money = Utility.parseDecimal(Utility.trimNull(map.get("SUBSCRIBE_MONEY")),new BigDecimal(0),2,"1");
		pay_date = Utility.parseInt(Utility.trimNull(map.get("PAY_DATE")),new Integer(0));
		bank_id = Utility.trimNull(map.get("BANK_ID")); 
		bank_name = Utility.trimNull(map.get("BANK_NAME"));
		bank_sub_name = Utility.trimNull(map.get("BANK_SUB_NAME")); 
		bank_acct = Utility.trimNull(map.get("BANK_ACCT")); 
		acct_name = Utility.trimNull(map.get("ACCT_NAME")); 
		cust_id = Utility.parseInt(Utility.trimNull(map.get("CUST_ID")),new Integer(0));
	}
	
	//�ͻ���Ϣ��ʾ
	if(cust_id.intValue()>0){
		
		//�ͻ�����ֵ		
		cust_vo.setCust_id(cust_id);
		cust_vo.setInput_man(input_operatorCode);
		rsList_cust = customer.listByControl(cust_vo);
				
		if(rsList_cust.size()>0){
			map_cust = (Map)rsList_cust.get(0);
		}

		if(map_cust!=null){		
			cust_no = Utility.trimNull(map_cust.get("CUST_NO"));
			cust_name = Utility.trimNull(map_cust.get("CUST_NAME"));
			cust_type = Utility.parseInt(Utility.trimNull(map_cust.get("CUST_TYPE")),new Integer(0));
			service_man = Utility.parseInt(Utility.trimNull(map_cust.get("SERVICE_MAN")),new Integer(0));		
			legal_man = Utility.trimNull(map_cust.get("LEGAL_MAN"));
			contact_man  = Utility.trimNull(map_cust.get("CONTACT_MAN"));
			post_code = Utility.trimNull(map_cust.get("POST_CODE"));
			post_address = Utility.trimNull(map_cust.get("POST_ADDRESS"));
			card_type = Utility.parseInt(Utility.trimNull(map_cust.get("CARD_TYPE")),new Integer(0));
			cust_type_name = Utility.trimNull(map_cust.get("CUST_TYPE_NAME"));
			is_link = Utility.parseInt(Utility.trimNull(map_cust.get("IS_LINK")),new Integer(0));
			//������Ϣ
			card_id = Utility.trimNull(map_cust.get("CARD_ID"));
			o_tel = Utility.trimNull(map_cust.get("O_TEL"));
			h_tel = Utility.trimNull(map_cust.get("H_TEL"));
			mobile = Utility.trimNull(map_cust.get("MOBILE"));
			bp = Utility.trimNull(map_cust.get("BP"));
			e_mail  = Utility.trimNull(map_cust.get("E_MAIL"));
			
		}		
	}
}
%>

<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("menu.addSubscriptionInfo",clientLocale)%>��<%=LocalUtilis.language("message.withoutBooked",clientLocale)%> ��</TITLE><!--�Ϲ���Ϣ����--><!--δԤԼ-->
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>

<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/investment.js"></SCRIPT>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>

<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/contract.js'></script>

<script language="javascript">

/*ƴ��׺����*/
function getLastOptions(){	
	if(!sl_checkDate(document.theform.q_changeDate_picker,"<%=LocalUtilis.language("class.qsDate",clientLocale)%> "))return false;
	syncDatePicker(document.theform.q_changeDate_picker, document.theform.q_changeDate);
	var temp="";
	var temp = temp + "&q_changeDate="+ document.theform.q_changeDate.value;	
	var temp = temp + "&q_changeAmount="+ document.theform.q_changeAmount.value;	
	var temp = temp + "&q_nonproductId="+ document.theform.q_nonproductName.value;	
	var temp = temp + "&cust_id="+ document.theform.customer_cust_id.value;	
	var temp = temp + "&q_custName="+ document.theform.customer_cust_name.value;	
	var temp = temp + "&bank_id="+ document.theform.bank_id.value;	
	var temp = temp + "&sign_date="+ document.theform.sign_date.value;	
	var temp = temp + "&pay_date="+ document.theform.pay_date.value;
	var temp = temp + "&q_nonproductName="+ document.theform.q_nonproductName.value;	
	var temp = temp + '&bank_sub_name=' + document.theform.bank_sub_name.value;
	var temp = temp + '&rg_money=' + document.theform.rg_money.value;
	var temp = temp + '&bank_acct=' + document.theform.bank_acct.value;
	var temp = temp + '&q_subscribeId=' + document.theform.q_subscribeId.value;
	
	var temp = temp + "&sQuery=<%=sQuery%>";
	return temp;
}

/*���ò�Ʒ���ͻ���Ϣ*/
function selectProductAndCustomer(){	
	var url = "quotient_affirm_new.jsp?page=<%=sPage%>" + getLastOptions();
	window.location.href = url;
}

/**************************************************************************************************************************/

/*��ʾ�˺�λ��*/
function showAcctNum(value){		
	if (trim(value) == "")
		bank_acct_num.innerText = "";
	else
		bank_acct_num.innerText = "(" + showLength(value) + " λ )";
}

/**************************************************************************************************************************/
	
/*�ͻ���Ϣ*/
function getMarketingCustomer(prefix,readonly){
	
	var cust_id = getElement(prefix, "cust_id").value;	
	var url = '<%=request.getContextPath()%>/marketing/customerInfo.jsp?prefix='+ prefix+ '&cust_id=' + cust_id+'&readonly='+readonly ;	
	var v = showModalDialog(url,'','dialogWidth:720px;dialogHeight:720px;status:0;help:0;');		

	if (v != null){
		/*showMarketingCustomer(prefix, v)*/
		getElement(prefix, "cust_id").value = v[7];
		selectProductAndCustomer();
	}	
	
	return (v != null);
}
	

/**************************************************************************************************************************/
/*����*/
function SaveAction(){	
	
	if(document.theform.onsubmit()){
		disableAllBtn(true);
		document.theform.action="quotient_affirm_new.jsp";
		document.theform.submit();
	}
}
	
/*����*/
function CancelAction(){
	location = 'quotient_affirm.jsp';
}

/*�������޸�ʱ�õ��ĺ���*/
function validateForm(form){
	if(form.q_nonproductName.value==0){
		sl_alert("<%=LocalUtilis.language("message.chooseProdName",clientLocale)%> !");//��ѡ���Ʒ����
		return false;
	}
	if(form.q_subscribeId.value==0){
		sl_alert("<%=LocalUtilis.language("message.contractNumNotExist",clientLocale)%> !");//��ѡ���ͬ���
		return false;
	}
	if(!sl_checkDecimal(form.q_changeAmount, '<%=LocalUtilis.language("class.changeAmount",clientLocale)%> ', 13, 3, 1)){return false;}//�ݶ�	
	
	return sl_check_update();
}

/*��ʼ��*/
window.onload = function(){
	var v_bSuccess = document.getElementById("bSuccess").value;
	
	if(v_bSuccess=="true"){		
			sl_alert("<%=LocalUtilis.language("message.addOK",clientLocale)%> ");//��ӳɹ�
		   	CancelAction();//�����Ϲ��б�
	}
}

</script>
</HEAD>

<BODY class="BODY body-nox">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="post" action="quotient_affirm_new.jsp" onsubmit="javascript:return validateForm(this);">
<!--�����ɹ���־-->
<input type="hidden" id="bSuccess" value="<%=bSuccess%>"/>
<input type="hidden" name="book_code" value="<%=input_bookCode%>">
<input type="hidden" name="sQuery" value="<%=sQuery%>">

<!--�ͻ���Ϣ-->
<input type="hidden" name="cust_name" value="<%=cust_name%>">
<input type="hidden" name="cust_type" value="<%=cust_type%>">
<input type="hidden" name="customer_cust_id" value="<%=cust_id%>">
<input type="hidden" name="card_type" value="<%=card_type%>">
<input type="hidden" name="card_type_value" value="<%=card_type%>">

<input type="hidden" name="org_bal" value=""><!-- 300W ��� -->
<input type="hidden" name="contact_num" value=""><!-- ��ͬ���� -->
<input type="hidden" name="current_num" value=""><!-- ���к�ͬ -->

<div align="left" class="page-title">
	<font color="#215dc6"><b><%=menu_info%></b></font>
</div>
<br/>
<!--��Ʒѡ��-->
<div>
<p class="title-table"><b><%=LocalUtilis.language("menu.prodInfo",clientLocale)%> </b></p><!--��Ʒ��Ϣ-->
<table  border="0" width="100%" cellspacing="0" cellpadding="3" class="product-list" >

	<tr>
		<td align="right" width="120px"><%=LocalUtilis.language("class.productID",clientLocale)%> :&nbsp;&nbsp;</td><!--��Ʒ���-->
		<td align="LEFT">
			<input readonly class='edline' maxlength="16" name="q_nonproductId" value="<%=q_nonproductId%>" size="20">
		</td>
		<td>&nbsp;&nbsp;</td>
		<td>&nbsp;&nbsp;</td>
	</tr>

	<tr>
		<td align="right" width="120px"><%=LocalUtilis.language("class.productNumber",clientLocale)%> :&nbsp;&nbsp;</td><!--��Ʒѡ��-->
		<td align=left colspan=3>
			<select style="WIDTH:220px" size="1" name="q_nonproductName" onkeydown="javascript:nextKeyPress(this)" class="q_nonproductName" onchange="javascript:selectProductAndCustomer();">
				<!--��ѡ��-->
                   <option><%=LocalUtilis.language("message.pleaseSelect",clientLocale)%> </option>
				<%=Argument.getNonproductName(q_nonproductId) %>
			</select>
		</td>
			<td align="right"><b>*<%=LocalUtilis.language("message.date",clientLocale)%></b> :&nbsp;&nbsp;</td><!--����-->
			<td>
			<INPUT TYPE="text" NAME="q_changeDate_picker" class=selecttext
			<%if(q_changeDate.intValue()==0){%> value="<%=Format.formatDateLine(Utility.getCurrentDate())%>"
			<%}else{%>value="<%=Format.formatDateLine(q_changeDate)%>"<%}%> >
			<INPUT TYPE="button" value="��" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.q_changeDate_picker,theform.q_changeDate_picker.value,this);" tabindex="14">
			<INPUT TYPE="hidden" NAME="q_changeDate" value="<%=q_changeDate%>">
		</td>
   </tr>
   <tr>
   		<!--��ͬ���-->
		<td align="right" width="120px"><b>*<%=LocalUtilis.language("class.constractBH",clientLocale)%> </b> :&nbsp;&nbsp;</td>
		<td align=left colspan=3>
			<select style="WIDTH:220px" size="1" name="q_subscribeId" onkeydown="javascript:nextKeyPress(this)" class="q_subscribeId" onchange="javascript:selectProductAndCustomer();">
				<!--��ѡ��-->
                   <option><%=LocalUtilis.language("message.pleaseSelect",clientLocale)%> </option>
				<%=Argument.getSubscribeBh(Utility.trimNull(q_subscribeId), q_nonproductId) %>
			</select>
		</td>
   		<td align="right" width="120px"><%=LocalUtilis.language("class.changeAmount",clientLocale)%> :&nbsp;&nbsp;</td><!--�ݶ�-->
		<td align="LEFT">
			<input type="text" maxlength="16" name="q_changeAmount" value="<%=q_changeAmount%>" size="20"  onkeydown="javascript:nextKeyPress(this)" >
		</td>
	
		
   </tr>
</table>
<br>
<p class="title-table"><b><%=LocalUtilis.language("menu.customerInformation",clientLocale)%> </b><!--�ͻ���Ϣ--></p>
<table  border="0" width="100%" cellspacing="0" cellpadding="3" class="product-list">
	<!--�ͻ�ѡ��-->
	
	<tr>
		<td align="right"><%=LocalUtilis.language("class.customerName",clientLocale)%> :</td> <!--�ͻ�����-->
		<td ><input maxlength="100" readonly class='edline'  name="customer_cust_name" size="50" onkeydown="javascript:nextKeyPress(this);" value="<%=cust_name%>">&nbsp;&nbsp;&nbsp;
		</td>
		<td align="right"><%=LocalUtilis.language("class.accountManager",clientLocale)%> :</td><!--�ͻ�����-->
		<td ><input maxlength="100" readonly class='edline'  name="customer_service_man" size="50" onkeydown="javascript:nextKeyPress(this);" value="<%=Utility.trimNull(Argument.getManager_Name(service_man))%>">&nbsp;&nbsp;&nbsp;
		</td>
	</tr>   	
	<tr>
		<td align="right"><%=LocalUtilis.language("class.customerType",clientLocale)%> :</td><!--�ͻ����-->
		<td ><INPUT readonly class='edline' name="customer_cust_type_name" size="20" value="<%=cust_type_name%>" onkeydown="javascript:nextKeyPress(this);"></td>
		<td align="right"><%=LocalUtilis.language("class.customerCardID",clientLocale)%> :</td><!--֤������-->
		<td><input readonly class='edline' name="customer_card_id" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%=card_id%>" size="50"></td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.companyPhone",clientLocale)%> :</td>  <!--��˾�绰-->
		<td><input readonly class='edline' name="customer_o_tel" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%=o_tel%>" size="20"></td>
		<td align="right"><%=LocalUtilis.language("class.customerMobile",clientLocale)%> :</td><!--�ֻ�-->
		<td><input readonly class='edline' name="customer_mobile" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%=mobile%>" size="20"></td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.customerHTel",clientLocale)%> :</td>  <!--��ͥ�绰-->
		<td><input readonly class='edline' name="customer_h_tel" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%=h_tel%>" size="20"></td>
		<td align="right"><%=LocalUtilis.language("class.email",clientLocale)%> :</td> <!--�����ʼ�-->
		<td><input readonly class='edline' name="customer_email" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%=e_mail%>" size="20"></td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.postAddress",clientLocale)%> :</td><!--��ϵ��ַ-->
		<td ><INPUT readonly class='edline' name="customer_post_address" size="50" value="<%=post_address%>" onkeydown="javascript:nextKeyPress(this);"></td>
		<td align="right"><%=LocalUtilis.language("class.postcode",clientLocale)%> :</td><!--��������-->
		<td ><INPUT readonly class='edline' name="customer_post_code" size="20" value="<%=post_code%>" onkeydown="javascript:nextKeyPress(this);"></td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.isLink3",clientLocale)%> :</td><!--��������־-->
		<td><input type="checkbox" disabled name="customer_is_link" value="<%=is_link%>" <%if(is_link!=null){if(is_link.intValue()==1) out.print("checked");}%> class="flatcheckbox"></td>
		<td align="right">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>	
</table>
<br>
<p class="title-table"> <b><%=LocalUtilis.language("message.contractInfo",clientLocale)%> </b>  <!--��ͬ��Ϣ--></p>
<table  border="0" width="100%" cellspacing="0" cellpadding="3" class="product-list">	
	
	<tr>
		<td align="right"><b>*<%=LocalUtilis.language("class.rg_money",clientLocale)%> </b> :&nbsp;&nbsp;</td><!--�Ϲ����-->
		<td><input readonly class='edline' name="rg_money" size="20" value="<%=subscribe_money %>" > 
		</td>
		<td align="right"><b>*<%=LocalUtilis.language("class.qsDate",clientLocale)%> </b> :&nbsp;&nbsp;</td><!--ǩ������-->
		<td>
			<INPUT readonly class='edline' NAME="sign_date" value="<%=sign_date%>">
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.bankName5",clientLocale)%> :</td><!--����-->
		<td >
			<select disabled size="1" name="bank_id"  onchange="javascript:selectBank(this.value);"  style="WIDTH:180px">
				<%=Argument.getBankID(Utility.trimNull(bank_id))%>
			</select>
			<input readonly class='edline' name="bank_sub_name" size="20" onkeydown="javascript:nextKeyPress(this)" value="<%=bank_sub_name%>">
		</td>		
	</tr>			
	<tr>
		<td align="right"><%=LocalUtilis.language("class.bandAcct4",clientLocale)%> :</td><!--�����ʺ�-->
		<td>
			<input readonly class='edline' name="bank_acct" id="bank_acct"  style="WIDTH: 250px" onkeydown="javascript:nextKeyPress(this)" size="1"  value="<%=bank_acct%>">
		</td>	
		<td align="right"><b>*<%=LocalUtilis.language("class.dzDate",clientLocale)%></b>:&nbsp;&nbsp;</td><!--�ɿ�����-->
		<td>
			<INPUT readonly class='edline' NAME="pay_date" value="<%=pay_date%>">
		</td>
	</tr>
</table>
</div>

<div align="right">
	<br>
	<!--����-->
    <button type="button" class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:SaveAction();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;
	<!--����-->
    <button type="button" class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:CancelAction();"><%=LocalUtilis.language("message.back",clientLocale)%> (<u>C</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</div>
</form>
<p>
<%
customer.remove();
customer_inst.remove();
subscribe.remove();
quotientaffirm.remove();
%>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>
