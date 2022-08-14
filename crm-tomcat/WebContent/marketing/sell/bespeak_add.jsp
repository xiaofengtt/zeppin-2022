<%@ page contentType="text/html; charset=GBK" import="enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
try{
//����ҳ�����
input_bookCode = new Integer(1);//������ʱ����
boolean bSuccess = false;
//��ȡҳ�洫�ݱ���
Integer product_id = new Integer(0);
Integer preproduct_id = new Integer(0);
String sProduct_id = "";
String sPreproduct_id = "";
if (request.getParameter("product_id")!=null) {
	String[] t = request.getParameter("product_id").split("-");
	sPreproduct_id = t[0];
	preproduct_id = Utility.parseInt(sPreproduct_id, new Integer(0));

	if (t.length>1) {
		sProduct_id = t[1];
		product_id = Utility.parseInt(sProduct_id, new Integer(0));
	}
}

Integer sub_product_id = Utility.parseInt(Utility.trimNull(request.getParameter("sub_product_id")),new Integer(0));
Integer customer_cust_id = Utility.parseInt(Utility.trimNull(request.getParameter("customer_cust_id")),new Integer(0));
Integer cc_cust_id = Utility.parseInt(Utility.trimNull(request.getParameter("cc_cust_id")),new Integer(0));
Integer cust_id = null;
if(cc_cust_id.intValue()>0){
	cust_id = cc_cust_id;
} else{
	cust_id = customer_cust_id;
}
//��������
//--1.ԤԼ��Ϣ
BigDecimal pre_money = Utility.parseDecimal(request.getParameter("pre_money"),new BigDecimal(0));
Integer	link_man = Utility.parseInt(Utility.trimNull(request.getParameter("link_man")),input_operatorCode);
Integer	valid_days = Utility.parseInt(Utility.trimNull(request.getParameter("valid_days")),new Integer(0));
String	pre_type = Utility.trimNull(request.getParameter("pre_type"));
if (pre_type.equals("")) {
	pre_type = "111202"; // Ĭ��Ϊ �绰ԤԼ
}
Integer	pre_num = Utility.parseInt(Utility.trimNull(request.getParameter("pre_num")),new Integer(1));
Integer preDate = Utility.parseInt(Utility.trimNull(request.getParameter("pre_date")),new Integer(0));
Integer expRegDate = Utility.parseInt(Utility.trimNull(request.getParameter("exp_reg_date")),new Integer(0));
String	summary = Utility.trimNull(request.getParameter("summary"));
String	fact_pre_num = Utility.trimNull(request.getParameter("fact_pre_num"));
String	fact_pre_money_view = Utility.trimNull(request.getParameter("fact_pre_money_view"));
String	fact_pre_money = Utility.trimNull(request.getParameter("fact_pre_money"));
String customer_cust_source = Utility.trimNull(request.getParameter("customer_cust_source"));//�ͻ���Դ
if (customer_cust_source.equals("")) {
	customer_cust_source = "111004"; // Ĭ��Ϊ ������Ա�ͻ�
}
String per_code = Utility.trimNull(request.getParameter("per_code"));

//--2.�ͻ���Ϣ
String cust_no = "";
String cust_name = "";
Integer cust_type = new Integer(0);
String cust_type_name = "";
Integer service_man= new Integer(0);
Integer card_type = new Integer(0);
String post_code = "";
String post_address = "";
Integer is_link = new Integer(0);
String legal_man= null;
String contact_man= null;
//------------------------------------
String card_id = "";
String cust_tel = "";
String o_tel = "";
String h_tel = "";
String mobile= "";
String e_mail = "";
String bp = "";
//------------------------------------
String h_card_id = "";
String h_cust_tel = "";
String h_o_tel = "";
String h_h_tel = "";
String h_mobile= "";
String h_e_mail = "";
String h_bp = "";
//��ȡ����
PreContractLocal preContract = EJBFactory.getPreContract();
Customer_INSTLocal  customer_inst = EJBFactory.getCustomer_INST();
CustomerLocal customer = EJBFactory.getCustomer();//�ͻ�
PreContractVO pre_vo = new PreContractVO();
CustomerVO c_vo = new CustomerVO();
CustomerVO cust_vo = new CustomerVO();



//�ͻ���Ϣ��ϸ
if(cust_id.intValue()>0){
	List rsList_cust = null;
	Map map_cust = null;	
	//�ͻ�����ֵ		
	c_vo.setCust_id(cust_id);
	c_vo.setInput_man(input_operatorCode);
	rsList_cust = customer.listByControl(c_vo);
			
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
		
		h_card_id = Utility.trimNull(map_cust.get("H_CARD_ID"));
		h_o_tel = Utility.trimNull(map_cust.get("H_O_TEL"));
		h_h_tel = Utility.trimNull(map_cust.get("H_H_TEL"));
		h_mobile= Utility.trimNull(map_cust.get("H_MOBILE"));
		h_e_mail = Utility.trimNull(map_cust.get("H_E_MAIL"));
		h_bp = Utility.trimNull(map_cust.get("H_BP"));
	}		
}

if(request.getMethod().equals("POST")){
	c_vo = new CustomerVO();
	//�ͻ���Ϣ���ݴ��
	c_vo.setCust_id(cust_id);
	c_vo.setCust_no(cust_no);
	c_vo.setCust_name(cust_name);
	c_vo.setCust_type(cust_type);
	c_vo.setCard_id(h_card_id);
	c_vo.setCard_type(card_type.toString());
	c_vo.setLegal_man(legal_man);
	c_vo.setContact_man(contact_man);
	c_vo.setPost_address(post_address);
	c_vo.setPost_code(post_code);
	c_vo.setMobile(h_mobile);
	c_vo.setE_mail(h_e_mail);
	c_vo.setService_man(service_man);
	c_vo.setInput_man(input_operatorCode);
	//ͬ���ͻ���Ϣ
	if (user_id.intValue()!=15) customer_inst.cope_customers(c_vo); //����CRM�Ŀͻ���Ϣ��ͬ����INTRUST
	//ԤԼ��Ϣ���
	pre_vo.setProduct_id(product_id);
	pre_vo.setSub_product_id(sub_product_id);
	pre_vo.setCust_id(cust_id);
	pre_vo.setPre_money(pre_money);
	pre_vo.setLink_man(link_man);
	pre_vo.setValid_days(valid_days);
	pre_vo.setPre_type(pre_type);
	pre_vo.setPre_num(pre_num);
	pre_vo.setSummary(summary);
	pre_vo.setInput_man(input_operatorCode);
	pre_vo.setPre_date(preDate);
	pre_vo.setExp_reg_date(expRegDate);
	pre_vo.setCust_source(customer_cust_source);

	pre_vo.setTest_code(Utility.parseInt(per_code,null)); 
	pre_vo.setPre_product_id(preproduct_id); //

	//����ԤԼ��Ϣ	 
	preContract.append(pre_vo);
	bSuccess = true;
}
%>

<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("menu.bespeakAdd",clientLocale)%> </TITLE>
<!--ԤԼ����-->
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<base target="_self">
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/investment.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/utilityService.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/contract.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>
<script language=javascript>
var bSuccess = <%=bSuccess%>; //�����ɹ���־

/*ҳ���ʼ��*/
window.onload = function(){
	if (bSuccess) {		
		sl_alert("<%=LocalUtilis.language("message.bespeakInfoSaveOK",clientLocale)%> ��");//ԤԼ��Ϣ����ɹ�
		location.href = "bespeak_list.jsp";
	}
	
	var productId = <%=product_id%>;
	var preproductId = <%=preproduct_id%>;

	if (productId > 0) {
		showPreinfo();
		getSellInfo();
		getSubproducts(preproductId+"-"+productId);
	} else {
		document.getElementById("sub_product_id").style.display = "none";
	}
};

/*���湦��*/
function SaveAction(){		
	if(document.theform.onsubmit()){
		disableAllBtn(true);
		document.theform.action="bespeak_add.jsp";
		document.theform.method="post";
		document.theform.submit();
	}
}
/*����*/
function CancelAction(){
	location = 'bespeak_list.jsp' ;
}
/*�����������ͻ�ʱ�õ��ĺ���*/
function validateForm(form){
	if(form.customer_cust_id.value=="")	{
			sl_alert("<%=LocalUtilis.language("message.chooseCustomer",clientLocale)%> ��");//��ѡ��ͻ�
			return false;
	}
	if(!sl_checkChoice(form.product_id, "<%=LocalUtilis.language("class.product",clientLocale)%> "))return false;//��Ʒ
	if(form.per_code.value!="")
	{
		if(!sl_checkNum(form.per_code, "<%=LocalUtilis.language("class.preCode2",clientLocale)%> ", 10, 1))return false;//ԤԼ��ű���������
	}
	if(!sl_checkDecimal(form.pre_money, "<%=LocalUtilis.language("class.factPreNum2",clientLocale)%> ", 13, 3, 1))return false;//ԤԼ���
	if(!sl_checkChoice(form.pre_num, "<%=LocalUtilis.language("class.preNum2",clientLocale)%> "))	return false;//ԤԼ����
	if(!sl_checkChoice(form.pre_type, "<%=LocalUtilis.language("class.preType",clientLocale)%> "))	return false;//ԤԼ��ʽ
	if(!sl_checkChoice(form.customer_cust_source, "<%=LocalUtilis.language("class.customerSource",clientLocale)%> "))	return false;//�ͻ���Դ
	if(!sl_checkNum(form.valid_days, "<%=LocalUtilis.language("lass.validDays",clientLocale)%> ", 10, 1))return false;//��Ч����
	if(!sl_checkChoice(form.link_man, "<%=LocalUtilis.language("class.contact",clientLocale)%> "))	return false;//��ϵ��
	if(!sl_checkDate(document.theform.pre_date_picker,"<%=LocalUtilis.language("class.preDate",clientLocale)%> ")){//ԤԼ����
		return false;
	}
	syncDatePicker(form.pre_date_picker, form.pre_date);
	
	if(document.theform.exp_reg_date_picker.value!=""){
		if(!sl_checkDate(document.theform.exp_reg_date_picker,"Ԥ���Ϲ�����")){//Ԥ���Ϲ�����
			return false;
		}
		syncDatePicker(form.exp_reg_date_picker, form.exp_reg_date);
	}
	
	//alert(form.pre_date.value + "-" +  form.pre_end_date.value );
	if (form.pre_date.value > form.pre_end_date.value 
		&& ! confirm("<%=LocalUtilis.language("message.bespeakAddTip",clientLocale)%> ") )
		return false;//ԤԼ���ڴ����ƽ鵽�����ڣ�Ҫ������	

	var org_bal = form.org_bal.value/10000;
	if(form.cust_type.value==1&&form.pre_money.value<=org_bal&&form.current_num.value>=form.contact_num.value){
		if(!confirm(org_bal+"��Ԫ���¸ò�Ʒ��ͬ�����Ѵ�"+form.current_num.value+"��������")){
			return false;
		}
	}
	return sl_check_update();
}
/*ˢ��ҳ��*/
function refreshPage(){
	disableAllBtn(true);
	syncDatePicker(document.theform.pre_date_picker, document.theform.pre_date);
	document.theform.action="bespeak_add.jsp";
	document.theform.method="get";
	document.theform.submit();
}
/*�ͻ���Ϣ*/
function getMarketingCustomer(prefix,readonly){
	cust_id = getElement(prefix, "cust_id").value;	
	var url = '<%=request.getContextPath()%>/marketing/customerInfo.jsp?select_flag=2&prefix='+ prefix+ '&cust_id=' + cust_id+'&readonly='+readonly ;
	
	v = showModalDialog(url,'','dialogWidth:720px;dialogHeight:720px;status:0;help:0;');		
	if (v != null){
		//showMarketingCustomer(prefix, v);
		document.theform.customer_cust_id.value =  v[7];
		refreshPage();
	}	
	
	return (v != null);
}

/*�������۹�����
function showMarketingCustomer(prefix, v){	
	getElement(prefix, "cust_name").value = v[0];
	getElement(prefix, "cust_type_name").value = v[1];
	getElement(prefix, "card_id").value = v[2];
	
	if(getElement(prefix, "h_tel")!=null)
		getElement(prefix, "h_tel").value = v[3];	
	if(getElement(prefix, "mobile")!=null)
		getElement(prefix, "mobile").value = v[4];		
	if(getElement(prefix, "post_address")!=null)
		getElement(prefix, "post_address").value = v[5];		
	if(getElement(prefix, "post_code")!=null)
		getElement(prefix, "post_code").value = v[6];	
	getElement(prefix, "cust_id").value = v[7];
		
	if(getElement(prefix, "service_man")!=null)
		getElement(prefix,"service_man").value=v[8];
	if(getElement(prefix, "service_man_value")!=null)
		getElement(prefix,"service_man_value").value=v[9];
	if(getElement(prefix, "o_tel")!=null)
		getElement(prefix, "o_tel").value = v[10];
	if(getElement(prefix, "bp")!=null)
		getElement(prefix, "bp").value = v[11];
		
	if(getElement(prefix, "is_link")!=null){
		getElement(prefix, "is_link").value = v[12];
		
		if(v[12]==1)
			getElement(prefix, "is_link").checked = true;
		else
			getElement(prefix, "is_link").checked = false;	
	}	
	
	getElement(prefix, "cust_type").value = v[13];		
	getElement(prefix, "cust_on").value = v[14];		
	getElement(prefix, "card_type").value = v[15];			
	getElement(prefix, "legal_man").value = v[16];	
	getElement(prefix, "contact_man").value = v[17];
	if(getElement(prefix, "e_mail")!=null)
		getElement(prefix, "e_mail").value = v[18];	
		
		
	if(getElement(prefix, "gain_acct")!=null)
		getElement(prefix, "gain_acct").value = v[0];  
		 
	prodid=0;		
	for(i=0;i<document.theform.link_man.options.length;i++){
	
		if(document.theform.link_man.options[i].value==v[9]){
		
			document.theform.link_man.options[i].selected=true;
			prodid = document.theform.link_man.options[i].value;
			break;
	    }	
	}
	
	if (prodid==0){
		document.theform.link_man.options[0].selected=true;	
	}
}
*/
/*��ʾ����*/
function showCnMoney(value){
	temp = value;
	if (trim(value) == "")
		pre_money_cn.innerText = " (<%=LocalUtilis.language("message.10000",clientLocale)%> )";//��
	else
		pre_money_cn.innerText = "(" + numToChinese(temp) + ")";
}
/*�޸���ԤԼ��Ŀ ��ԤԼ���*/
function showPreinfo(){
	var preproduct_id = document.theform.product_id.value.split("-")[0];
	var product_id = document.theform.product_id.value.split("-")[1];
	// ȡԤԼ��
	utilityService.getPreInfoById(preproduct_id, product_id,{callback: function(data){
		document.theform.fact_pre_num.value = data[0];
		document.theform.fact_pre_money_view.value = formatNum(data[1]);
		//alert(data[2]);
		document.theform.pre_end_date.value = data[2];
		document.theform.per_code.value = data[3];
	}});
}

/*�����������*/
function getSellInfo(){
	var product_id = document.theform.product_id.value.split("-")[1];
	if (product_id>0){
		contract.getSaleInfo('<%=input_bookCode%>',product_id,{callback: function(data){
			document.theform.org_bal.value = data[0];
			document.theform.contact_num.value = data[1];
			document.theform.current_num.value = data[2]; // �˲�Ʒ���Ϲ����С��3000000��<300�򣩵Ŀͻ�����Ȼ�˵ĺ�ͬ����Ŀ
		}});
	}
}

/*ͨ����Ʒ��֤����Ӳ�Ʒ��־*/
function getSubproducts(product_id){
	product_id = product_id.split("-")[1];
	if (product_id>0) {
		utilityService.getSubProductFlag(product_id, getSubFlag);
		getSellInfo(product_id);
	} else {
		document.getElementById("sub_product_id").style.display = "none";
	}
}

/*��ȡ�Ӳ�Ʒ��Ϣ�����漶�����漶��������Ϣ*/
function getSubFlag(data){
	var arrayL = data.split("$");
	var is_product_sub_flag = arrayL[0];

	var product_id = DWRUtil.getValue("product_id").split("-")[1];	

	if (is_product_sub_flag==1) { //���Ӳ�Ʒ
		document.getElementById("sub_product_id").style.display = "";
		utilityService.getSubProductOptionS(product_id, 0, getSubProductOptions);//����Ӳ�Ʒ�����
	} else { //���Ӳ�Ʒ��
		document.getElementById("sub_product_id").style.display = "none";
	}
}

//����Ӳ�Ʒ����
function getSubProductOptions(data) {
	document.getElementById("tdSubProductId").innerHTML = 
			'<select size="1" name="sub_product_id" onkeydown="javascript:nextKeyPress(this)" class="productname">'
		   + data + '</select>';
}

</script>
</HEAD>
<BODY class="BODY">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" onsubmit="javascript: return validateForm(this);">
<input type="hidden" name="customer_cust_id" value="<%=cust_id%>"/>
<input type="hidden" name="cust_type" value="<%=cust_type%>"/>
<input type="hidden" name="pre_end_date" value="">
<input type="hidden" name="org_bal" value=""><!-- 300W ��� -->
<input type="hidden" name="contact_num" value=""><!-- ��ͬ���� -->
<input type="hidden" name="current_num" value=""><!-- ���к�ͬ -->
<div align="left">
	<img border="0" src="<%=request.getContextPath()%>/images/ico_area.gif" align="absbottom" width="32" height="28">
	<font color="#215dc6"><b><%=LocalUtilis.language("message.salesManagerment",clientLocale)%>>><%=LocalUtilis.language("menu.bespeak",clientLocale)%> </b></font><!--���۹���>>ԤԼ-->
	<hr noshade color="#808080" size="1">
</div>
<!--�ͻ�ѡ��-->
<div vAlign=top align=left>
<table cellSpacing=0 cellPadding=4 width="100%" border=0>
			<tr>
				<td align="left"><b><%=LocalUtilis.language("menu.customerInformation",clientLocale)%> </b></td>	<!--�ͻ���Ϣ-->			
				<!--button class="xpbutton3" accessKey=e name="btnEdit" title="�ͻ���Ϣ" onclick="javascript:getTransactionCustomer2('customer','<!%=readonly%>','<!%=Argument.getSyscontrolValue("CUSTINFO")%>');"><!%=strButton%></button-->
				<td align="left">
					<button type="button"  class="xpbutton3" accessKey=e name="btnEdit" title="<%=LocalUtilis.language("enu.customerInformation",clientLocale)%> " onclick="javascript:getMarketingCustomer('customer','0');"><%=LocalUtilis.language("message.select2",clientLocale)%> 
					</button><!--�ͻ���Ϣ--><!--��ѡ��-->
				</td>
			<tr>
			<tr>
				<td align="right"><%=LocalUtilis.language("class.customerName",clientLocale)%> :</td><!--�ͻ�����-->
				<td ><input maxlength="100" readonly class='edline'  name="customer_cust_name" size="50" onkeydown="javascript:nextKeyPress(this);" value="<%=cust_name%>">&nbsp;&nbsp;&nbsp;
				</td>
				<td align="right"><%=LocalUtilis.language("class.accountManager",clientLocale)%> :</td><!--�ͻ�����-->
				<td ><input maxlength="100" readonly class='edline'  name="customer_service_man" size="10" onkeydown="javascript:nextKeyPress(this);" value="<%=Utility.trimNull(Argument.getManager_Name(service_man))%>">&nbsp;&nbsp;&nbsp;
				</td>
			</tr>	
			<tr>
				<td align="right"><%=LocalUtilis.language("class.customerType",clientLocale)%> :</td><!--�ͻ����-->
				<td ><INPUT readonly class='edline' name="customer_cust_type_name" size="20" value="<%=cust_type_name%>" onkeydown="javascript:nextKeyPress(this);"></td>
				<td align="right"><%=LocalUtilis.language("class.customerCardID",clientLocale)%> :</td><!--֤������-->
				<td><input readonly class='edline' name="customer_card_id" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%=card_id%>" size="20"></td>
			</tr>
			<tr>
				<td align="right"><%=LocalUtilis.language("class.customerHTel",clientLocale)%> :</td><!--��ͥ�绰-->
				<td><input readonly class='edline' name="customer_h_tel" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%=h_tel%>" size="20"></td>
				<td align="right"><%=LocalUtilis.language("class.companyPhone",clientLocale)%> :</td><!--��˾�绰-->
				<td><input readonly class='edline' name="customer_o_tel" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%=o_tel%>" size="20"></td>
				</tr>
			<tr>
				<td align="right"><%=LocalUtilis.language("class.customerMobile",clientLocale)%> 1:</td><!--�ֻ�1-->
				<td><input readonly class='edline' name="customer_mobile" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%=mobile%>" size="20"></td>
				<td align="right"><%=LocalUtilis.language("class.customerMobile",clientLocale)%> 2:</td><!--�ֻ�2-->
				<td><input readonly class='edline' name="customer_bp" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%=bp%>" size="20"></td>
			
			</tr>
			<tr>
				<td align="right"><%=LocalUtilis.language("class.postAddress",clientLocale)%> :</td><!--��ϵ��ַ-->
				<td ><INPUT readonly class='edline' name="customer_post_address" size="50" value="<%=post_address%>" onkeydown="javascript:nextKeyPress(this);"></td>
				<td align="right"><%=LocalUtilis.language("class.postcode",clientLocale)%> :</td><!--��������-->
				<td ><INPUT readonly class='edline' name="customer_post_code" size="20" value="<%=post_code%>" onkeydown="javascript:nextKeyPress(this);"></td>
			</tr>
			
			<tr>
				<td colspan="4"><hr size="1"></td>
			</tr> 
		<tr>
			<td align="right"><%=LocalUtilis.language("class.productName",clientLocale)%> :</td><!--��Ʒ����-->
			<td>
				<select size="1" name="product_id" onkeydown="javascript:nextKeyPress(this)" class="productname" onChange="javascript:showPreinfo();getSellInfo();getSubproducts(this.value)">
					<option><%=LocalUtilis.language("message.pleaseSelect",clientLocale)%> </option><!--��ѡ��-->
					<%--=Argument.getProductListOptions(input_bookCode,product_id,"",input_operatorCode,25)--%>
					<%=Argument.getProductListOptions2(new Integer(2), "", input_operatorCode, preproduct_id+"-"+product_id)%>
				</select>		
			</td>
			<td align="right"><%=LocalUtilis.language("class.qPreCode",clientLocale)%> :</td><!--ԤԼ��-->
			<td><input name="per_code" onkeydown="javascript:nextKeyPress(this)" class="edline" readonly size="27" value="<%=Utility.trimNull(per_code)%>" maxlength="8"></td>
		</tr>	
		<tr id="sub_product_id">
			<td align="right">�Ӳ�Ʒ :</td>
			<td id="tdSubProductId" colspan="3">
				<select size="1" name="sub_product_id" onkeydown="javascript:nextKeyPress(this)" class="productname">
					<%=Argument.getSubProductOptions(product_id, new Integer(0), sub_product_id)%>
				</select>
			</td>			
		</tr>	
		<tr>
			<td align="right"><%=LocalUtilis.language("class.factPreNum3",clientLocale)%> :</td><!--��ԤԼ����-->
			<td><input readonly class="edline" name="fact_pre_num" onkeydown="javascript:nextKeyPress(this)" size="27" value="<%=fact_pre_num%>"></td>
			<td align="right"><%=LocalUtilis.language("class.factPreMoney",clientLocale)%> :</td><!--��ԤԼ�ܽ��-->
			<td>
				<input readonly class="edline" name="fact_pre_money_view" onkeydown="javascript:nextKeyPress(this)" size="27" value="<%=fact_pre_money_view%>"/>
				<input type="hidden" name="fact_pre_money" onkeydown="javascript:nextKeyPress(this)" size="27" value="<%=fact_pre_money%>"/>
			</td>
		</tr>	
		<tr>
			<td align="right">*<%=LocalUtilis.language("class.factPreNum2",clientLocale)%> :</td><!--ԤԼ���-->
			<td><input name="pre_money" size="27" value="<%=pre_money%>" onkeydown="javascript:nextKeyPress(this)" onkeyup="javascript:showCnMoney(this.value)"><span id="pre_money_cn" class="span">&nbsp;</span></td>
<!--��-->
			<td align="right">*<%=LocalUtilis.language("class.preNum2",clientLocale)%> :</td><!--ԤԼ����-->
			<td>
				<select name="pre_num" >
					<%=Argument.getPreNumOptions(pre_num)%>
				</select>
			</td>
		</tr>
		<tr>
			<td align="right">*<%=LocalUtilis.language("class.preType",clientLocale)%> :</td><!--ԤԼ��ʽ-->
			<td>
				<select size="1" name="pre_type" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 160px">
					<%=Argument.getPreTypeOptions(pre_type)%>
				</select>
			</td>
			<td align="right">*<%=LocalUtilis.language("class.validDays",clientLocale)%> :</td><!--��Ч����-->
			<td><input name="valid_days" size="27" value="<%=valid_days%>" onkeydown="javascript:nextKeyPress(this)"/>&nbsp;&nbsp;<%=LocalUtilis.language("message.days",clientLocale)%>&nbsp;��0��ʾһֱ��Ч��<!--��--></td>
		</tr>
		<tr>
			<td align="right">Ԥ���Ϲ�����:</td><!--Ԥ���Ϲ�����-->
			<td>
				<INPUT TYPE="text" NAME="exp_reg_date_picker" class=selecttext 
				<%if(expRegDate==null||expRegDate.intValue()==0){%> value="<%=Format.formatDateLine(Utility.getCurrentDate())%>"
				<%}else{%>value="<%=Format.formatDateLine(expRegDate)%>"<%}%> >
				<INPUT TYPE="button" value="��" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.exp_reg_date_picker,theform.exp_reg_date_picker.value,this);" tabindex="13">
				<INPUT TYPE="hidden" NAME="exp_reg_date" value="">
			</td>
			<td align="right">*<%=LocalUtilis.language("class.customerSource",clientLocale)%> :</td><!--�ͻ���Դ-->
			<td>
				<select onkeydown="javascript:nextKeyPress(this)" size="1" name="customer_cust_source" style="width: 110px">
					<%=Argument.getCustomerSourceOptions(customer_cust_source)%>
				</select>
			</td>
		</tr>
		<tr>
			<td align="right"><%=LocalUtilis.language("class.preDate",clientLocale)%> :</td><!--ԤԼ����-->
			<td><INPUT TYPE="text" NAME="pre_date_picker" class=selecttext 
			<%if(preDate==null||preDate.intValue()==0){%> value="<%=Format.formatDateLine(Utility.getCurrentDate())%>"
			<%}else{%>value="<%=Format.formatDateLine(preDate)%>"<%}%> >
			<INPUT TYPE="button" value="��" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.pre_date_picker,theform.pre_date_picker.value,this);" tabindex="13">
			<INPUT TYPE="hidden" NAME="pre_date"   value=""></td>
			<td align="right">*<%=LocalUtilis.language("class.contact",clientLocale)%> :</td><!--��ϵ��-->
			<td ><select size="1" name="link_man" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 160px">
				<%=Argument.getManager_Value(link_man)%>
			</select></td>
		</tr>
		<tr>
			<td align="right"><%=LocalUtilis.language("class.customerSummary",clientLocale)%> :</td><!--��ע-->
			<td colspan="3"><textarea rows="3" name="summary" onkeydown="javascript:nextKeyPress(this)" cols="80"><%=summary%></textarea></td>
		</tr>
</table>
</div>
<br>
<div align="right" style="margin-right:5px">
	<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:SaveAction();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;<!--����-->
	<button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.CancelAction();"><%=LocalUtilis.language("message.back",clientLocale)%> (<u>C</u>)</button>
	<!--����-->
</div>
<br>
</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>
<%
preContract.remove();
customer_inst.remove();
customer.remove();
}
catch(Exception e){
	throw e;//ϵͳ���ݿ�δ��ʼ��!
}
%>