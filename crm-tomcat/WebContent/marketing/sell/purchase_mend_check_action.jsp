<%@ page contentType="text/html; charset=GBK" import="enfo.crm.customer.*,enfo.crm.service.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<% 
//������ʱ����
input_bookCode = new Integer(1);

Integer serial_no = Utility.parseInt(request.getParameter("serial_no"), null);
int checkflag = Utility.parseInt(request.getParameter("checkflag"),1); 
String check_summary = Utility.trimNull(request.getParameter("check_summary"));
String service = Utility.trimNull(request.getParameter("service"));
String sQuery = Utility.trimNull(request.getParameter("sQuery"));

//��������
int SUB_COOPER = Argument.getSyscontrolValue_intrust("SUB_COOPER");
String fee_type_name = "�Ϲ���";
String purchase_mode_checkedStyle = "readonly class='edline'";
String purchase_mode_checkedStyle2 = "disabled";
String marValue = "";
String product_msg_flag = "";//��Ʒ��Ϣ��־������ʽOR���ʽ���ƽ�ص���ϱ�־��
List attachmentList = new ArrayList();
boolean bSuccess = false;

TMoneyDataLocal moneyData = EJBFactory.getTMoneyData();
ContractLocal contract = EJBFactory.getContract();//��ͬ
CustomerLocal customer = EJBFactory.getCustomer();//�ͻ�
ProductLocal product = EJBFactory.getProduct();//��Ʒ

ContractVO vo = new ContractVO();
CustomerVO cust_vo = new CustomerVO();
ProductVO p_vo = new ProductVO();

//�ͻ���Ϣ����
String cust_no = "";
String cust_name = "";
String card_type_name = "";
String card_id = "";
String age = "";
String sex_name = "";
String mobile = "";
String bp = "";
String fax = "";
String e_mail = "";
String o_tel = "";
String h_tel = "";
String cust_type_name = "";
String cust_level_name = "";
String legal_man = "";
String legal_address = "";
String touch_type_name = "";
String post_address = "";
String post_code = "";
String post_address2 = "";
String post_code2 = "";
Integer cust_type = new Integer(0);
Integer last_rg_date = new Integer(0);
Integer cust_is_link = new Integer(0);
Integer service_man = new Integer(0);
BigDecimal current_money = new BigDecimal(0);

//��ͬ��Ϣ�ֶζ���
String[] sTouch_types = null;
String product_code = "",pre_code = "",product_name = "",sub_product_name = "";
String contract_bh = "",contract_sub_bh = "",bank_id = "";
String bank_sub_name = "",bank_name;
String bank_acct_type = "";
String bank_acct = "";
String gain_acct = "";
String jk_type = "",jk_type_name="";
String rg_money_cn = "";//�Ϲ�������Ĵ���
String channel_cooperation = "";
String channel_type = "";
String channel_memo = "";
String prov_level = "",prov_level_name="",summary = "";
String ht_bank_id = "",ht_bank_sub_name = "";
String bank_city = "",bank_province = "";
String city_name = "";
int selfbenflag = 0;
int check_flag = 0;
int fee_jk_type = 0;
int with_bank_flag = 0;
int with_security_flag = 0;
int with_private_flag = 0;
Integer product_id = new Integer(0);
Integer sub_product_id = new Integer(0);
Integer cust_id = new Integer(0);
Integer qs_date = new Integer(0);
Integer jk_date = new Integer(0);
Integer bonus_flag = new Integer(0);
Integer channel_id = new Integer(0);
Integer link_man = new Integer(0);
Integer prov_flag = new Integer(0);
Integer recommend_man = new Integer(0);
Integer citySerialno = new Integer(0);
Integer valid_period = new Integer(0);
BigDecimal bonus_rate = new BigDecimal(0.00);
BigDecimal rg_money = new BigDecimal(0.00);
BigDecimal market_trench_money = new BigDecimal(0.00);
BigDecimal pre_money = new BigDecimal(0.00);

//��Ʒ����
String product_status = "110202";
String period_unit = "��";
int open_flag = 2;
int asfund_flag = 0;
int intrust_flag1 = 1;
int is_local = 0;
Integer pre_start_date = new Integer(0);//�ƽ���ʼ��
Integer pre_end_date = new Integer(0);//�ƽ������

if (request.getMethod().equals("POST")){
	TMoneyDataVO tmoney_vo = new TMoneyDataVO();
	tmoney_vo.setSerial_no(serial_no);
	tmoney_vo.setCheck_flag(new Integer(checkflag));
	tmoney_vo.setCheck_summary(check_summary);
	tmoney_vo.setInput_man(input_operatorCode);
	moneyData.checkPurchanseContractMend(tmoney_vo);
	bSuccess = true;
}

//ȡ��ͬ����
if(serial_no.intValue()>0){	
	vo.setSerial_no(serial_no);
	vo.setInput_man(input_operatorCode);
	List contractList = contract.load(vo);	
	if (contractList.size()==1){
		Map contractMap = (Map)contractList.get(0);
	
		cust_id =  Utility.parseInt(Utility.trimNull(contractMap.get("CUST_ID")), new Integer(0));	
		product_id = Utility.parseInt(Utility.trimNull(contractMap.get("PRODUCT_ID")), new Integer(0));	
		sub_product_id = Utility.parseInt(Utility.trimNull(contractMap.get("SUB_PRODUCT_ID")), new Integer(0));	 
		citySerialno = Utility.parseInt(Utility.trimNull(contractMap.get("CITY_SERIAL_NO")), new Integer(0));

		fee_jk_type = Utility.parseInt(Utility.trimNull(contractMap.get("FEE_JK_TYPE")), 0);
		valid_period = Utility.parseInt(Utility.trimNull(contractMap.get("VALID_PERIOD")), new Integer(0));	
		link_man =  Utility.parseInt(Utility.trimNull(contractMap.get("LINK_MAN")), new Integer(0));	
		recommend_man = Utility.parseInt(Utility.trimNull(contractMap.get("RECOMMEND_MAN")),new Integer(0));

		qs_date =  Utility.parseInt(Utility.trimNull(contractMap.get("QS_DATE")), new Integer(0));	
		jk_date =  Utility.parseInt(Utility.trimNull(contractMap.get("JK_DATE")), new Integer(0));	

		bonus_flag = Utility.parseInt(Utility.trimNull(contractMap.get("BONUS_FLAG")), new Integer(0));	
		selfbenflag = Utility.parseInt(Utility.trimNull(contractMap.get("ZY_FLAG")),1);	
		prov_flag =   Utility.parseInt(Utility.trimNull(contractMap.get("PROV_FLAG")), new Integer(0));	
		with_bank_flag = Utility.parseInt(Utility.trimNull(contractMap.get("WITH_BANK_FLAG")), 0);	
		with_security_flag = Utility.parseInt(Utility.trimNull(contractMap.get("WITH_SECURITY_FLAG")), 0);	
		with_private_flag = Utility.parseInt(Utility.trimNull(contractMap.get("WITH_PRIVATE_FLAG")), 0);	

		bank_id = Utility.trimNull(contractMap.get("BANK_ID"));
		bank_name = Utility.trimNull(contractMap.get("BANK_NAME"));
		bank_sub_name = Utility.trimNull(contractMap.get("BANK_SUB_NAME"));
		bank_acct_type = Utility.trimNull(contractMap.get("BANK_ACCT_TYPE"));
		bank_acct = Utility.trimNull(contractMap.get("BANK_ACCT"));
		bank_province = Utility.trimNull(contractMap.get("BANK_PROVINCE"));
		bank_city = Utility.trimNull(contractMap.get("BANK_CITY"));
		gain_acct = Utility.trimNull(contractMap.get("GAIN_ACCT"));	
		jk_type_name = Utility.trimNull(contractMap.get("JK_TYPE_NAME"));
		prov_level =Utility.trimNull(contractMap.get("PROV_LEVEL"));
		
		contract_bh = Utility.trimNull(contractMap.get("CONTRACT_BH"));
		contract_sub_bh =  Utility.trimNull(contractMap.get("CONTRACT_SUB_BH"));		
		pre_code = Utility.trimNull(contractMap.get("PRE_CODE"));		
		sub_product_name = Utility.trimNull(contractMap.get("LIST_NAME"));		
		city_name = Utility.trimNull(contractMap.get("CITY_NAME"));		
		summary = Utility.trimNull(contractMap.get("SUMMARY"));

		channel_type = Utility.trimNull(contractMap.get("CHANNEL_TYPE"));
		channel_memo = Utility.trimNull(contractMap.get("CHANNEL_MEMO"));
		channel_cooperation = Utility.trimNull(contractMap.get("CHANNEL_COOPERTYPE"));
		
		ht_bank_id = Utility.trimNull(contractMap.get("HT_BANK_ID"));
		ht_bank_sub_name = Utility.trimNull(contractMap.get("HT_BANK_SUB_NAME"));	

		sTouch_types = Utility.splitString(Utility.trimNull(contractMap.get("TOUCH_TYPE")),";") ;

		rg_money =  Utility.parseDecimal(Utility.trimNull(contractMap.get("RG_MONEY")),new BigDecimal(0));
		bonus_rate=   Utility.parseDecimal(Utility.trimNull(contractMap.get("BONUS_RATE")),new BigDecimal(0));
		market_trench_money = Utility.parseDecimal(Utility.trimNull(contractMap.get("MARKET_MONEY")),new BigDecimal(0));

		if(!pre_code.equals("")){
			ContractVO cont_vo = new ContractVO();	
			cont_vo.setBook_code(input_bookCode);
			cont_vo.setProduct_id(product_id);
			cont_vo.setPre_code(pre_code);
		
			pre_money = contract.getPrecontract_premoney(cont_vo);
		}

		product_msg_flag = Argument.getProductFlag(product_id);
		product_name = Argument.getProductFlag(product_id,"product_name");
		product_code = Argument.getProductFlag(product_id,"product_code");
		product_status = Argument.getProductFlag(product_id,"product_status");
		period_unit =  Argument.getProductFlag(product_id,"period_unit");
		period_unit = Argument.getProductUnitName(Integer.valueOf(period_unit));

		check_summary = Utility.trimNull(contractMap.get("CHECK_SUMMARY"));
	}

	//����Ϲ�����
	AttachmentLocal attachmentLocal = EJBFactory.getAttachment();
	AttachmentVO attachment_vo = new AttachmentVO();
	attachment_vo.setDf_talbe_id(new Integer(5));
	attachment_vo.setDf_serial_no(serial_no);
	
	attachmentList = attachmentLocal.load(attachment_vo);
	attachmentLocal.remove();
}

//�ͻ���Ϣ��ʾ
if(cust_id.intValue()>0){
	//�ͻ�����ֵ		
	cust_vo.setCust_id(cust_id);
	cust_vo.setInput_man(input_operatorCode);
	List customerList = customer.listByControl(cust_vo);
	
	if(customerList.size()>0){
		Map map_cust = (Map)customerList.get(0);
	
		cust_no =  Utility.trimNull(map_cust.get("CUST_NO"));
		cust_name = Utility.trimNull(map_cust.get("CUST_NAME"));
		card_type_name =  Utility.trimNull(map_cust.get("CARD_TYPE_NAME"));
		card_id = Utility.trimNull(map_cust.get("CARD_ID"));
		age =  Utility.trimNull(map_cust.get("AGE"));
		sex_name = Utility.trimNull(map_cust.get("SEX_NAME"));
		mobile = Utility.trimNull(map_cust.get("MOBILE"));
		fax = Utility.trimNull(map_cust.get("FAX"));
		e_mail =  Utility.trimNull(map_cust.get("E_MAIL"));
		bp = Utility.trimNull(map_cust.get("BP"));
		o_tel = Utility.trimNull(map_cust.get("O_TEL"));
		h_tel = Utility.trimNull(map_cust.get("H_TEL"));
		cust_type_name = Utility.trimNull(map_cust.get("CUST_TYPE_NAME"));
		legal_man = Utility.trimNull(map_cust.get("LEGAL_MAN")); 
		legal_address = Utility.trimNull(map_cust.get("LEGAL_ADDRESS")); 
		touch_type_name = Utility.trimNull(map_cust.get("TOUCH_TYPE_NAME"));    
		post_code = Utility.trimNull(map_cust.get("POST_CODE"));
		post_address = Utility.trimNull(map_cust.get("POST_ADDRESS"));
		post_address2 =  Utility.trimNull(map_cust.get("POST_ADDRESS2"));
		post_code = Utility.trimNull(map_cust.get("POST_CODE2"));	
 
		service_man = Utility.parseInt(Utility.trimNull(map_cust.get("SERVICE_MAN")),new Integer(0));
		cust_type = Utility.parseInt(Utility.trimNull(map_cust.get("CUST_TYPE")),new Integer(0));				
		last_rg_date = Utility.parseInt(Utility.trimNull(map_cust.get("LAST_RG_DATE")),new Integer(0));			
		cust_is_link  = Utility.parseInt(Utility.trimNull(map_cust.get("IS_LINK")),new Integer(0));	

		current_money =  Utility.parseDecimal(Utility.trimNull(map_cust.get("CURRENT_MONEY")),new BigDecimal(0)); 
	}		
}
if(product_msg_flag!=null && !"".equals(product_msg_flag)){
	String[] strArray = Utility.splitString(product_msg_flag,"$");
	intrust_flag1  = Utility.parseInt(strArray[1],0);
	asfund_flag    = Utility.parseInt(strArray[2],0);
	open_flag      = Utility.parseInt(strArray[3],0);
	is_local       = Utility.parseInt(strArray[4],0);
	pre_start_date = Utility.parseInt(strArray[5],new Integer(0));
	pre_end_date   = Utility.parseInt(strArray[6],new Integer(0));
	if("110203".equals(strArray[7]))
		fee_type_name = LocalUtilis.language("class.sgFeeAmount",clientLocale);//�깺��
}
//�����������ʣ�ѡ���������Ƶ�
p_vo.setProduct_id(product_id);
p_vo.setSub_product_id(sub_product_id);
p_vo.setChannel_type(channel_type);
p_vo.setR_channel_id(channel_id);
//��Ʒ����������Ϣ
List markList = product.queryMarketTrench(p_vo);
if(markList.size()>0){
	Map marMap = (Map)markList.get(0);
	marValue =  Utility.trimNull(marMap.get("CHANNEL_TYPE"))+"@"+
				Utility.trimNull(marMap.get("CHANNEL_ID"))+"@"+
				Utility.trimNull(Utility.parseDecimal(Utility.trimNull(
					marMap.get("CHANNEL_FARE_RATE")), new BigDecimal(0),4,"1"));
}

String from = Utility.trimNull(request.getParameter("from"));
boolean from_signed_spot = from.equals("signed_spot");
String qs = Utility.trimNull(request.getParameter("qs"));

moneyData.remove();
%>
<HTML>
<HEAD>
<TITLE>��ͬ��Ϣ��ȫ���</TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/utilityService.js'></script>
<script language=javascript>
window.onload = function(){
	<%if (bSuccess){%>
		sl_alert('��˳ɹ�!');	
		op_return();
	<%}%>
	};

function confirmRemoveAttach(obj,serial_no){
	if (confirm('<%=LocalUtilis.language("message.tAreYouSure",clientLocale)%>��')){		
		var updatehref = 'attach_remove.jsp?serial_no='+serial_no;
    	if (showModalDialog(updatehref, '', 'dialogWidth:0px;dialogHeight:0px;dialogLeft:0px;dialogTop:0px;status:0;help:0') != null){
		}
		if (obj) obj.style.display = "none";
	}
}

/*
 *��Ӹ���	
 */
function addline(){
	n++;
	newline=document.all.test.insertRow()
	newline.id="fileRow"+n;
	newline.insertCell().innerHTML="<input type='file' name=file_info"+n+" size='60'>"+"<button type="button"  onclick='javascript:removeline(this)'><%=LocalUtilis.language("messgae.remove",clientLocale)%> </button>";//�Ƴ�
}

function op_check(){
	if (sl_check_pass()){
		disableAllBtn(true);
		document.theform.checkflag.value = "2";
		document.theform.submit();
	}
}

function op_checkDeny(){
	document.getElementById("check_summary_fs").style.display = "";
	document.getElementsByName("check_summary")[0].focus();
	document.getElementsByName("check_summary")[0].select();
	document.getElementById("deny_btn").onclick = function() { deny(); };
}

function deny(){
	if (sl_confirm("��˲�ͨ��")){
		disableAllBtn(true);
		document.theform.checkflag.value = "3";
		document.theform.submit();
	}
}

function op_return(){
<%if (from_signed_spot) {%>
	location.href = "../signed_spot.jsp?<%=qs%>";
<%}else{%>
	var tempArray = '<%=sQuery%>'.split('$');
	location = 'purchase_mode_check.jsp?page='+tempArray[7]+'&pagesize='+tempArray[6] 
					+ '&productid=' + tempArray[0] + '&product_id=' + tempArray[1] 
					+ '&cust_name=' + tempArray[2] + '&query_contract_bh=' + tempArray[3] 
					+ '&card_id=' + tempArray[4] + '&check_flag=' + tempArray[5];
<%}%>
}

function showHtyh(){
	document.getElementById("htyhmc_v").style.display = document.theform.with_bank_flag.checked? "": "none";
}

//�鿴�ͻ���Ϣ
function getCustomer(cust_id){
	var url = '<%=request.getContextPath()%>/marketing/customerInfo2.jsp?cust_id='+cust_id ;
	var v = showModalDialog(url,'','dialogWidth:720px;dialogHeight:720px;status:0;help:0;');
	if (v) {
		document.theform.customer_gain_acct.value = v[0];
		document.theform.customer_cust_name.value = v[0];
	}
}
</script>
</HEAD>

<BODY class="BODY body-nox">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="post" action="purchase_mend_check_action.jsp" onsubmit="javascript:return validateForm(this);">
<input type="hidden" name="serial_no" value="<%=serial_no%>"/>
<input type="hidden" name="checkflag" value="<%=checkflag%>"/>
<input type="hidden" name="sQuery" value="<%=sQuery%>"/>

<input type="hidden" name="from" value="<%=from%>"/>
<input type="hidden" name="qs" value="<%=qs%>"/>

<div align="left" class="page-title">
	<font color="#215dc6"><b>��ͬ��Ϣ��ȫ���</b></font>
	<br/>
</div>
<!--��Ʒѡ��-->
<fieldset style="border-color: background;border-style: solid;border-width: 1px;;margin-left: 4px;margin-right: 4px;">
	<legend style="border-color: lime;"><b><%=LocalUtilis.language("menu.prodInfo",clientLocale)%></b></legend>
	<table  border="0" width="100%" cellspacing="4" cellpadding="4">
		<tr>
			<td align="right" width="11%"><b><font color="red"><b>*</b></font><%=LocalUtilis.language("class.productName",clientLocale)%>:</b></td><!--��Ʒ����-->
			<td align=left colspan=3 width="89%">
				<input class="edline" readonly name="product_name" value="<%=product_code+"_"+product_name %>" size="77"/>
			</td>
		</tr>
		<tr <%if(sub_product_id.intValue()==0) out.print("style='display:none;'");%>>
			<td align="right" width="11%"><b><font color="red"><b>*</b></font><%=LocalUtilis.language("class.subProductID",clientLocale)%>:</b></td><!--�Ӳ�Ʒѡ��-->
			<td align=left colspan=3>
				<input class="edline" readonly name="sub_product_name" value="<%=sub_product_name%>" size="77"/>
			</td>
		</tr>
	</table>
</fieldset>
<br>
<!--�ͻ���Ϣ-->
<fieldset style="border-color: background;border-style: solid;border-width: 1px;margin-left: 4px;margin-right: 4px;">
	<legend>
		<b><%=LocalUtilis.language("message.customerInfomation",clientLocale)%> </b><!--�ͻ���Ϣ-->
		<button type="button"  id ="cust_button" class="xpbutton3" accessKey=e name="btnEdit" 
			title='<%=LocalUtilis.language("message.customerInfomation",clientLocale)%>' 
			onclick="javascript:getCustomer(<%=cust_id%>);"><%=LocalUtilis.language("message.view",clientLocale)%> 
		</button><!--�ͻ���Ϣ--><!--�鿴-->
	</legend>
	<table  border="0" width="100%" cellspacing="4" cellpadding="4">
		<!--�ͻ�ѡ��-->
		<tr>
			<td colspan="4">
				<span id="cust_message" style="display:none;color:red;">
					<%=LocalUtilis.language("message.noCustomerInfo",clientLocale)%> 
				</span><!--�ͻ���Ϣ������-->
			</td>
		<tr>
		<tr>
			<td align="right" width="10%">�ͻ����:</td>
			<td><INPUT readonly class="edline" name="cust_no" size="20" value="<%=cust_no%>"></td>
			<td align="right" width="10%">�ͻ�����:</td>
			<td><INPUT readonly class="edline" name="cust_name" size="30" value="<%=cust_name%>"></td>
		</tr>
		<tr>
			<td align="right" width="10%">֤������:</td>
			<td><input readonly name="card_type_name" size="20" value="<%=card_type_name%>" class="edline"></td>
			<td align="right" width="10%">֤������:</td>
			<td><input readonly name="card_id" size="30" class="edline" value="<%=card_id%>"></td>
		</tr>
	<%if (cust_type.intValue()==1) {%>
		<tr>
			<td align="right" width="10%">����:</td>
			<td><input readonly name="age" size="20" class="edline" value="<%=Utility.trimNull(age)%>"></td>
			<td align="right" width="10%">�Ա�:</td>
			<td><input readonly name="sex" size="20" value="<%=sex_name%>" class="edline"></td>
		</tr>
	<%}%>
		<tr>
			<td align="right" width="10%">�ֻ�:</td>
			<td><input readonly name="mobile" size="20" value="<%=mobile%>" class="edline"></td>
			<td align="right" width="10%">�ֻ�2:</td>
			<td><input readonly name="bp" size="20" class="edline" value="<%=bp%>"></td>
		</tr>
		<tr>
			<td align="right" width="10%">����:</td>
			<td><input readonly name="fax" size="20" value="<%=fax%>" class="edline"></td>
			<td align="right" width="10%">Email:</td>
			<td><input readonly name="e_mail" size="20" value="<%=e_mail%>" class="edline"></td>
		</tr>
		<tr>
			<td align="right" width="10%">��λ�绰:</td>
			<td><input readonly name="o_tel" size="20" class="edline" value="<%=o_tel%>"></td>
			<td align="right" width="10%">��ͥ�绰:</td>
			<td><input readonly name="h_tel" size="20" class="edline" value="<%=h_tel%>"></td>
		</tr>			
		<tr>
			<td align="right" width="10%">�������:</td>
			<td><input readonly name="current_money" size="20" value="<%=current_money%>" class="edline"></td>
			<td align="right" width="10%">���һ�ι���ʱ��:</td>
			<td><input readonly name="last_rg_date" size="20" value="<%=Format.formatDateCn(last_rg_date)%>" class="edline"></td>
		</tr>
		<tr>
			<td align="right" width="10%">�ͻ����:</td>
			<td><input readonly name="cust_type_name" size="20" value="<%=Utility.trimNull(cust_type_name)%>" class="edline"></td>
			<td align="right" width="10%">�ͻ�����:</td>
			<td><input readonly name="cust_level_name" size="20" value="<%=Utility.trimNull(cust_level_name)%>" class="edline"></td>		
		</tr>
	<%if (cust_type.intValue()==2) {%>			
		<tr>
			<td align="right" width="10%">��������:</td>
			<td><input readonly name="legal_man" size="20" value="<%=legal_man%>" class="edline"></td>
			<td align="right" width="10%">��ϵ��ַ:</td>
			<td><input readonly name="legal_address" size="50" value="<%=legal_address%>" class="edline"></td>
		</tr>
	<%}%>			
		<tr style="display:none">
			<td align="right" width="10%">��������־:</td>
			<td><input type="checkbox" disabled name="cust_is_link" value="<%=Utility.trimNull(cust_is_link)%>" 
				<%if(cust_is_link!=null){if(cust_is_link .intValue()==1) out.print("checked");}%> class="flatcheckbox"></td>
			<td align="right" width="10%">��ϵ��ʽ:</td>
			<td><input readonly name="touch_type_name" size="20" value="<%=touch_type_name%>" class="edline"></td>
		</tr>
		<tr>
			<td align="right" width="10%">�ʼĵ�ַ:</td>
			<td><input readonly name="post_address" size="40" value="<%=post_address%>" class="edline"></td>
			<td align="right" width="10%">��������:</td>
			<td><input readonly name="post_code" onkeydown="javascript:nextKeyPress(this)" size="20" maxlength="6" value="<%=post_code%>" class="edline"></td>
		</tr>
		<tr>
			<td align="right" width="10%">���õ�ַ:</td>
			<td><input readonly name="post_address2" size="40" value="<%=post_address2%>" class="edline"></td>
			<td align="right" width="10%">��������:</td>
			<td><input readonly name="post_code2" onkeydown="javascript:nextKeyPress(this)" size="20" maxlength="6" value="<%=post_code2%>" class="edline"></td>
		</tr>	
	</table>
</fieldset>
<br>
<!-- ��ͬ��Ϣ -->
<fieldset style="border-color: background;border-style: solid;border-width: 1px;margin-left: 4px;margin-right: 4px;">
	<legend>
		<b><%=LocalUtilis.language("message.contractInfo",clientLocale)%> </b><!--��ͬ��Ϣ-->
	</legend>
	<table border="0" width="100%" cellspacing="4" cellpadding="4">
		<tr>
			<!--��ͬ���-->
	        <td align="right" width="10%"><b><%=LocalUtilis.language("class.constractBH",clientLocale)%></b></td>
			<td><input <%= purchase_mode_checkedStyle%> name="contract_bh" size="45" maxlength=20 
					onkeydown="javascript:nextKeyPress(this)" value="<%=contract_bh%>">
			</td>
			<td align="right" width="10%"><%=LocalUtilis.language("class.contractID",clientLocale)%>:</td><!--��ͬ���-->
			<!--��֤���-->
	        <td><input <%= purchase_mode_checkedStyle%> name="contract_sub_bh" size="35" maxlength=40 onkeydown="javascript:nextKeyPress(this)" 
					value="<%=contract_sub_bh%>">&nbsp;
			</td>
		</tr>
		<tr>
			<td align="right" width="10%"><%=LocalUtilis.language("class.bankName5",clientLocale)%>:</td><!--��������������-->
			<td>
				<select size="1" name="bank_id" <%=purchase_mode_checkedStyle2%> onchange="javascript:selectBank(this.value);" style="WIDTH:150px">
					<%=Argument.getBankOptions(bank_id)%>
				</select>
			<%if(bank_sub_name.length()>0){%>
				<input type="text" name="bank_sub_name" <%=purchase_mode_checkedStyle%> size="20" onkeydown="javascript:nextKeyPress(this)" value="<%=bank_sub_name%>">
			<%}%>
			</td>
			<td align="right" width="10%"><%=LocalUtilis.language("class.bandAcct4",clientLocale)%>:</td><!--�����������ʺ�-->
			<td>
				<input type="text" name="bank_acct" <%= purchase_mode_checkedStyle%> value="<%=bank_acct%>" size="34"/>
			</td>
		</tr>
		<tr>
			<td align="right" width="10%">���������ڵ�:</td>
			<td>
				<select size="1" <%=purchase_mode_checkedStyle2%>  name="bank_province" style="width: 130px" onkeydown="javascript:nextKeyPress(this)">
					<%=Argument.getCustodianNameLis(new Integer(9999), "", new Integer(0),bank_province)%>
				</select>
				<!-- ʡ������������˳������������ -->
				<span id="gov_regional_span">
					<select style="WIDTH: 150px" name="bank_city" <%=purchase_mode_checkedStyle2%>>
						<%=Argument.getCustodianNameLis(new Integer(9999), bank_province, new Integer(0),bank_city)%>
					</select>
				</span>
			</td>
			<td align="right" width="10%">�Ƽ���:</td>
			<td>
				<select name="recommend_man" style="width:230px;" onkeydown="javascript:nextKeyPress(this)" <%=purchase_mode_checkedStyle2%>>
					<%=Argument.getIntrustOptions(recommend_man,new Integer(1))%>
				</select>
			</td>
		</tr>
		<tr>
			<td align="right" width="10%"><%=LocalUtilis.language("class.bankAccountType",clientLocale)%> :</td><!--�����˺�����-->
			<td >
				<select size="1" name="bank_acct_type" <%= purchase_mode_checkedStyle2%> onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 278px">
					<%=Argument.getBankAcctType(bank_acct_type)%>
				</select>
			</td>
			<td align="right" width="10%"><%=LocalUtilis.language("class.customerGainAcct",clientLocale)%> :</td><!--�����ʺŻ���-->
			<td>
				<input <%=purchase_mode_checkedStyle%> name="customer_gain_acct" size="48" onkeydown="javascript:nextKeyPress(this)" value="<%=gain_acct%>"/>
			</td>
		</tr>
		<tr>
			<td align="right" width="10%"><%=LocalUtilis.language("class.feeType",clientLocale)%>:</td><!--�ɿʽ-->
			<td>
				<input name="jk_type_name" <%=purchase_mode_checkedStyle%> size="48" value="<%=jk_type_name%>" onkeydown="javascript:nextKeyPress(this)">
			</td>
			<td align="right" width="10%"><%=LocalUtilis.language("class.rg_money",clientLocale)%>:</td><!--�Ϲ����-->
			<td>
				<input name="rg_money" <%=purchase_mode_checkedStyle%> size="48" value="<%=rg_money%>" onkeydown="javascript:nextKeyPress(this)">	
				<div id="rg_money_cn" class="span"><%=rg_money_cn%></div>
			</td>
		</tr>
		<tr>
			<td align="right" width="10%"><%=LocalUtilis.language("class.qsDate",clientLocale)%> :</td><!--ǩ������-->
			<td>
				<INPUT TYPE="text" NAME="qs_date_picker" size="46" class=selecttext class=selecttext <%=purchase_mode_checkedStyle2%> value="<%=Format.formatDateLine(qs_date)%>">
				<INPUT TYPE="button" value="��" <%=purchase_mode_checkedStyle2%> class=selectbtn tabindex="13"
					onclick="javascript:CalendarWebControl.show(theform.qs_date_picker,theform.qs_date_picker.value,this);">
				<INPUT TYPE="hidden" NAME="qs_date" value="<%=qs_date%>">
			</td>
			<td align="right" width="10%"><%=LocalUtilis.language("class.dzDate",clientLocale)%> :</td><!--�ɿ�����-->
			<td>
				<INPUT TYPE="text" NAME="jk_date_picker" size="43.5" class=selecttext <%=purchase_mode_checkedStyle2%> value="<%=Format.formatDateLine(jk_date)%>">
				<INPUT TYPE="button" value="��" <%=purchase_mode_checkedStyle2%> class=selectbtn onclick="javascript:CalendarWebControl.show(theform.jk_date_picker,theform.jk_date_picker.value,this);" tabindex="14">
				<INPUT TYPE="hidden" NAME="jk_date" value="<%=jk_date%>">
			</td>
		</tr>
	<%if (open_flag==1) {%>
		<tr>
			<td align="right" width="10%"><%=fee_type_name%><%=LocalUtilis.language("class.feeTypeName",clientLocale)%>:</td><!--�۽ɷ�ʽ-->
			<td>
				<select size="1" <%=purchase_mode_checkedStyle2%> name="fee_jk_type" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 280px">
					<%=Argument.getFeeJkTypeOptions(fee_jk_type)%>
				</select> 
			</td>
			<td align="right" width="10%" ><%=LocalUtilis.language("class.bonusFlag",clientLocale)%>:</td><!--������䷽ʽ-->
			<td>
				<select size="1" <%=purchase_mode_checkedStyle2%> name="bonus_flag" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 265px">
					<%=Argument.getBonus_flag(bonus_flag)%>
				</select>
				<span id="bonus_rate_div" <%if(bonus_flag.intValue()!=3) out.print("style='display:none'");%>>ת�ݶ����:
					<input type="text" name="bonus_rate" size="10" onkeydown="javascript:nextKeyPress(this)"
						value="<%=(Utility.parseDecimal(Utility.trimNull(bonus_rate),new BigDecimal(0))).multiply(new BigDecimal(100)).setScale(2)%>">%
				</span>
			</td>
		</tr>
	<%}%>
		<tr>
			<td align="right" width="10%"><font color="red"><b>*</b></font>��������:</td>
			<td>
				<select id="market_trench" <%=purchase_mode_checkedStyle2%> name="market_trench" style="width: 280px;" onchange="javascript:setMartetTrench(this);">
					<%=Argument.queryMarketTrench(product_id,sub_product_id,marValue) %>
				</select>
			</td>
			<td align="right" width="10%">��������:</td>
			<td><input name="market_trench_money" size="48" value="<%=market_trench_money %>" <%=purchase_mode_checkedStyle2%>
				onkeydown="javascript:nextKeyPress(this)" onkeyup="javascript:showCnMoney(this.value,market_trench_money_cn)">
			</td>
		</tr>
		<tr>
			<td colspan="3"></td>
			<td><span id="market_trench_money_cn" class="span"></span></td>
		</tr>
		<tr >
			<td align="right" width="10%"><%=LocalUtilis.language("class.partnAttachmentInfo",clientLocale)%>:</td><!--����������Ϣ-->
			<td>
				<input name="channel_memo" size="51" <%=purchase_mode_checkedStyle%> onkeydown="javascript:nextKeyPress(this)" value="<%=channel_memo%>">
			</td>
			<td align="right" width="10%"><font color="red"><b>*</b></font><%=LocalUtilis.language("class.channelCooperation",clientLocale)%>:</td><!-- ����������ʽ -->
			<td>
				<SELECT <%=purchase_mode_checkedStyle2%> name="channel_cooperation" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 265px">
					<%=Argument.getChannelCooperationOptions(channel_cooperation)%>
				</SELECT>
			</td>
		</tr>
	<%if(intrust_flag1!=1){%>
		<tr>
			<td align="right" width="10%" ><%=LocalUtilis.language("class.provLevel",clientLocale)%>:</td><!-- �������ȼ� -->
			<td>
				<select <%= purchase_mode_checkedStyle2%> name="prov_flag" style="WIDTH: 278px" <%if(asfund_flag==3){%>onchange="javascript:getprovlevel()"<%}%> onkeydown="javascript:nextKeyPress(this)">
					<%=Argument.getProductProvFlag(product_id,sub_product_id,prov_flag)%>
				</select>
			</td>
		<%if(asfund_flag==3){%>
			<td align="right" width="10%" ><font color="red">*</font><%=LocalUtilis.language("class.incomeLevel",clientLocale)%>:</td><!-- ���漶�� -->
			<td>
				<div id="div_prov_level">
					<select <%=purchase_mode_checkedStyle2%> name="prov_level" style="width:265px">
						<%=Argument.getProductProvLevel(product_id,sub_product_id,prov_flag,prov_level)%>
					</select>
				</div>
			</td>
		<%}%>
		</tr>
	<%}%>
		<tr>
			<td align="right" width="10%"><%=LocalUtilis.language("class.linkMan",clientLocale)%> :</td><!--��ͬ������Ա-->
			<td>
				<select <%= purchase_mode_checkedStyle2%> name="link_man" onkeydown="javascript:nextKeyPress(this)" style="WIDTH:280px" onchange="javascript:queryTeamquota(this.value);">
					<%=Argument.getOperatorOptionsByRoleId(new Integer(2),link_man)%>
				</select>
			</td>
		<%if(is_local==2){ %>
			<td align="right" width="10%"><%=LocalUtilis.language("class.citySerialNO",clientLocale)%>:</td><!--��ͬ�ƽ��-->
			<td >
				<select <%= purchase_mode_checkedStyle2%> size="1" name="city_serialno" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 265px">
					<%=Argument.getCitynameOptions(product_id,citySerialno)%>
				</select>
			</td>
		<%}%>
		</tr>
		<tr style="display:<%=SUB_COOPER==2? "none": ""%>">
			<td align="right" width="10%"><%=LocalUtilis.language("message.cooperationWay",clientLocale)%>:</td><!--������ʽ-->
			<td>
				<input <%=purchase_mode_checkedStyle2%> <%if(with_bank_flag==1)out.print("checked");%>
					onkeydown="javascript:nextKeyPress(this)" type="checkbox" class="flatcheckbox" value="<%=with_bank_flag %>"
					name="with_bank_flag" onclick="javascript:showHtyh();">
					<%=LocalUtilis.language("intrsut.home.silvertrustcooper",clientLocale)%>&nbsp;&nbsp;&nbsp;&nbsp;<!--���ź���-->
				<input <%= purchase_mode_checkedStyle2%> <%if(with_security_flag==1)out.print("checked");%>
					onkeydown="javascript:nextKeyPress(this)" type="checkbox"
					class="flatcheckbox" value="<%=with_security_flag %>" name="with_security_flag">
					<%=LocalUtilis.language("message.cooperation2",clientLocale)%> &nbsp;&nbsp;<!--֤�ź���-->
				<input <%= purchase_mode_checkedStyle2%> <%if(with_private_flag==1)out.print("checked");%>
					onkeydown="javascript:nextKeyPress(this)" type="checkbox"
					class="flatcheckbox" value="<%=with_private_flag %>" name="with_private_flag">
				<%=LocalUtilis.language("class.cooperation4",clientLocale)%> &nbsp;&nbsp;<!--˽ļ�������-->
			</td>
			<td align="right"><%=LocalUtilis.language("class.selfBenFlag",clientLocale)%> :</td><!--�����־-->
			<td><input name="self_ben_flag" class="flatcheckbox" <%=purchase_mode_checkedStyle2%> type="checkbox" value="" <%if(selfbenflag==1) out.print("checked");%> onkeydown="javascript:nextKeyPress(this)"></td>
		</tr>
		<tr id="htyhmc_v" <%if(with_bank_flag!=1) out.print("style='display:none'");%>>
			<td align="right" width="10%"><%=LocalUtilis.language("class.withBankId",clientLocale)%>:<!-- �������� --></td>
			<td colspan="3">
				<select <%=purchase_mode_checkedStyle2%> size="1" name="ht_bank_id" style="WIDTH: 220px">
					<%=Argument.getBankOptions(Utility.trimNull(ht_bank_id))%>
				</select>
				<input <%=purchase_mode_checkedStyle%> type="text" name="ht_bank_sub_name" size="20" onkeydown="javascript:nextKeyPress(this)" value="<%=Utility.trimNull(ht_bank_sub_name)%>">
			</td>
		</tr>
		<tr>
			<td align="right" width="10%"><%=LocalUtilis.language("class.description",clientLocale)%> :</td><!--��ע-->
			<td colspan="3">
			<textarea rows="3" readonly style=edline_textarea name="summary2" onkeydown="javascript:nextKeyPress(this)" cols="83"><%=summary%></textarea>
			</td>
		</tr>
	</table>
</fieldset>
<br>
<fieldset style="border-color: background;border-style: solid;border-width: 1px;;margin-left: 4px;margin-right: 4px;">
	<legend style="border-color: lime;"><b>&nbsp;��������</b></legend>
	<table  border="0" width="100%" cellspacing="4" cellpadding="4">
		<tr id="reader2" style="display:">
	      	<td class="paramTitle"align="right" width="10%">�鿴����:</td>
	        <td colspan="3">
	        	<table id="test" width="100%">
	        		<tr>
					<%
					Iterator attachment_it = attachmentList.iterator();
		           while (attachment_it.hasNext()) {
		            	Map attachment_map = (Map)attachment_it.next(); %>
		            	<div id="divattach<%=Utility.trimNull(attachment_map.get("ATTACHMENTID"))%>" style="display:">
		            		<a title="�鿴����" href="<%=request.getContextPath()%>/system/basedata/downloadattach.jsp?file_name=<%=Utility.replaceAll(Utility.trimNull(attachment_map.get("SAVE_NAME")),"\\","/")%>&name=<%=Utility.trimNull(attachment_map.get("ORIGIN_NAME"))%>"><%=Utility.trimNull(attachment_map.get("ORIGIN_NAME"))%></a>
		            		&nbsp;&nbsp;&nbsp;&nbsp;
		            		<button type="button"  class="xpbutton2" accessKey=d id="btnSave" name="btnSave"
		            			onclick="javascript:confirmRemoveAttach(divattach<%=Utility.trimNull(attachment_map.get("ATTACHMENTID"))%>,'<%=Utility.trimNull(attachment_map.get("ATTACHMENTID"))%>$<%=Utility.replaceAll(Utility.trimNull(attachment_map.get("SAVE_NAME")),"\\","/")%>');">ɾ��</button>
		            	</div><br>
					<%}%>
		            </tr>
		        </table>
	        </td>
	    </tr>	
	</table>
</fieldset>
<br>

<fieldset id="check_summary_fs" style="border-color: background;border-style: solid;border-width: 1px;margin-left: 4px;margin-right: 4px;display:<%=check_summary.equals("")?"none":""%>">
	<legend><b>&nbsp;��˲�ͨ�����</b></legend>
	<table border="0" width="100%" cellspacing="4" cellpadding="4">
		<tr>
			<td align="right" width="10%">��� :</td>
			<td colspan="3"><textarea rows="3" name="check_summary" onkeydown="javascript:nextKeyPress(this)" cols="83"><%=check_summary%></textarea></td>
		</tr>		
	</table>
</fieldset>
<br/>

<div align="right">	
	<br>
	<%if(input_operator.hasFunc(menu_id, 103) || from_signed_spot){ %>
		<button type="button"  class="xpbutton4" title="���ͨ��" onclick="javascript:op_check();">���ͨ��</button>
		&nbsp;&nbsp;&nbsp; 
		<button type="button"  class="xpbutton4" id="deny_btn" title="��˲�ͨ��" onclick="javascript:op_checkDeny();">��˲�ͨ��</button>&nbsp;&nbsp;&nbsp; 
	<%}%>
	<button type="button"  class="xpbutton3" accessKey="b" onclick="javascript:op_return();">����(<u>B</u>)</button>
	&nbsp;&nbsp;&nbsp;		
</div>
</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>