<%@ page contentType="text/html; charset=GBK" import="enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
//获取页面传递变量
Integer cust_id = Utility.parseInt(Utility.trimNull(request.getParameter("cust_id")), new Integer(0)); 
Integer select_flag = Utility.parseInt(Utility.trimNull(request.getParameter("select_flag")), new Integer(1)); 
int readonly = Utility.parseInt(Utility.trimNull(request.getParameter("readonly")), 0); //认购审核查询客户信息用， 1为只读
//辅助变量
String sReadonly = "readonly class='edline'";
String sDisabled= "disabled='disabled'";
String sPrint_deploy_bill = "";
String sPrint_post_info = "";

//获取对象
CustomerLocal customer = EJBFactory.getCustomer();
CustomerVO vo = new CustomerVO();

//声明单一客户信息变量
String cust_type_name ="";
Integer cust_type = new Integer(0);
String cust_name="";
String cust_no="";
String cust_source="";
String cust_source_name="";
String vip_card_id="";
String hgtzr_bh="";
Integer vip_date= new Integer(0);
Integer service_man= new Integer(0);
String card_type_name="";
Integer card_type = new Integer(0);
String card_id="";
String legal_man="";
String contact_man="";
Integer birthday= new Integer(0);
Integer age= new Integer(0);
Integer sex= new Integer(0);
String o_tel="";
String h_tel="";
String mobile="";
String bp="";
String fax="";
String e_mail="";
String post_code="";
String post_address="";
String post_code2="";
String post_address2="";
String touch_type="";
String touch_type_name="";
Integer is_link = new Integer(0);
Integer true_flag = new Integer(1);
String summary="";
String voc_type="";
String voc_type_name="";
String sex_name="";
Integer print_deploy_bill = new Integer(0);
Integer print_post_info = new Integer(0);
Integer is_deal = new Integer(0);
String is_deal_name = "";
String country = "";
String money_source = "";
String country_name = "";
String money_source_name = "";
String card_valid_date ="";
String recommended ="";
String jg_type_name = "";
String jg_wtrlx2 = "";
Integer total_count = new Integer(0);
String managing_scope = "";
String business_code = "";
String tax_reg_code = "";
String organ_code = "";
String legal_name = "";
String legal_tel = "";
String legal_card_type = "";
String legal_card_id = "";
Integer legal_card_valid_date = new Integer(0);
String agent_name = "";
String agent_tel = "";
String agent_card_type = "";
String agent_card_id = "";
Integer agent_card_valid_date = new Integer(0);
String partner_card_id = "";

//选择单一客户信息。查询详细资料 SQL2005		
if (cust_id.intValue()>0){
	vo = new CustomerVO();
	vo.setCust_id(cust_id);
	vo.setInput_man(input_operatorCode);
	
	List rsList_single = customer.listByControl(vo);	
	if (rsList_single.size()>0){
		Map map_single = (Map)rsList_single.get(0);
			
		cust_type_name = Utility.trimNull(map_single.get("CUST_TYPE_NAME"));	
		cust_type = Utility.parseInt(Utility.trimNull(map_single.get("CUST_TYPE")),new Integer(1));
		cust_name = Utility.trimNull(map_single.get("CUST_NAME"));
		cust_no = Utility.trimNull(map_single.get("CUST_NO"));
		cust_source = Utility.trimNull(map_single.get("CUST_SOURCE"));
		cust_source_name = Utility.trimNull(map_single.get("CUST_SOURCE_NAME"));
		vip_card_id = Utility.trimNull(map_single.get("VIP_CARD_ID"));
		hgtzr_bh = Utility.trimNull(map_single.get("HGTZR_BH"));
		vip_date = Utility.parseInt(Utility.trimNull(map_single.get("VIP_DATE")),new Integer(0));
		service_man	= Utility.parseInt(Utility.trimNull(map_single.get("SERVICE_MAN")),new Integer(0));
		card_type_name = Utility.trimNull(map_single.get("CARD_TYPE_NAME"));
		card_type  = Utility.parseInt(Utility.trimNull(map_single.get("CARD_TYPE")),new Integer(0));
		card_id = Utility.trimNull(map_single.get("CARD_ID"));
		legal_man = Utility.trimNull(map_single.get("LEGAL_MAN"));
		contact_man = Utility.trimNull(map_single.get("CONTACT_MAN"));
		birthday = Utility.parseInt(Utility.trimNull(map_single.get("BIRTHDAY")),new Integer(0));
		age = Utility.parseInt(Utility.trimNull(map_single.get("AGE")),new Integer(0));
		sex = Utility.parseInt(Utility.trimNull(map_single.get("SEX")),new Integer(0));
		o_tel =  Utility.trimNull(map_single.get("O_TEL"));
		h_tel =  Utility.trimNull(map_single.get("H_TEL"));
		mobile  =  Utility.trimNull(map_single.get("MOBILE"));
		bp  =  Utility.trimNull(map_single.get("BP"));
		fax =  Utility.trimNull(map_single.get("FAX"));
		e_mail =  Utility.trimNull(map_single.get("E_MAIL"));
		post_code =  Utility.trimNull(map_single.get("POST_CODE"));
		post_address =  Utility.trimNull(map_single.get("POST_ADDRESS"));
		post_code2 =  Utility.trimNull(map_single.get("POST_CODE2"));
		post_address2 =  Utility.trimNull(map_single.get("POST_ADDRESS2"));
		touch_type = Utility.trimNull(map_single.get("TOUCH_TYPE"));
		touch_type_name = Utility.trimNull(map_single.get("TOUCH_TYPE_NAME"));
		is_link = Utility.parseInt(Utility.trimNull(map_single.get("IS_LINK")),new Integer(0));
		true_flag = Utility.parseInt((Integer)map_single.get("TRUE_FLAG"),new Integer(1));
		summary = Utility.trimNull(map_single.get("SUMMARY"));
		voc_type =  Utility.trimNull(map_single.get("VOC_TYPE"));
		voc_type_name =  Utility.trimNull(map_single.get("VOC_TYPE_NAME"));
		sex_name =  Utility.trimNull(map_single.get("SEX_NAME"));	
		print_deploy_bill = Utility.parseInt(Utility.trimNull(map_single.get("PRINT_DEPLOY_BILL")),new Integer(0));
		print_post_info = Utility.parseInt(Utility.trimNull(map_single.get("PRINT_POST_INFO")),new Integer(0));
		is_deal = Utility.parseInt(Utility.trimNull(map_single.get("IS_DEAL")),new Integer(0));
		country = Utility.trimNull(map_single.get("COUNTRY"));
		money_source = Utility.trimNull(map_single.get("MONEY_SOURCE"));
		country_name = Argument.getDictContent(country);
		money_source_name = Utility.trimNull(map_single.get("MONEY_SOURCE_NAME"));
		card_valid_date = Utility.trimNull(map_single.get("CARD_VALID_DATE"));
		recommended = Utility.trimNull(map_single.get("RECOMMENDED"));
		jg_type_name = Utility.trimNull(map_single.get("EAST_JG_TYPE_NAME"));
		jg_wtrlx2 = Utility.trimNull(map_single.get("JG_WTRLX2"));
		total_count = Utility.parseInt((Integer)map_single.get("TOTAL_COUNT"), new Integer(0));
	}

	rsList_single = customer.loadEntCustInfo(vo);
	if (rsList_single.size()>0) {
		Map map = (Map) rsList_single.get(0);	
		managing_scope = Utility.trimNull(map.get("Managing_Scope"));
		business_code = Utility.trimNull(map.get("BUSINESS_CODE"));
		tax_reg_code = Utility.trimNull(map.get("TAX_REG_CODE"));
		organ_code = Utility.trimNull(map.get("ORGAN_CODE"));
		legal_name = Utility.trimNull(map.get("LEGAL_NAME"));
		legal_tel  = Utility.trimNull(map.get("LEGAL_TEL"));
		legal_card_type = Utility.trimNull(map.get("LEGAL_CARD_TYPE"));
		legal_card_id = Utility.trimNull(map.get("LEGAL_CARD_ID"));
		legal_card_valid_date = Utility.parseInt((Integer)map.get("LEGAL_CARD_VALID_DATE"), new Integer(0));
		agent_name = Utility.trimNull(map.get("AGENT_NAME"));
		agent_tel  = Utility.trimNull(map.get("AGENT_TEL"));
		agent_card_type = Utility.trimNull(map.get("AGENT_CARD_TYPE"));
		agent_card_id = Utility.trimNull(map.get("AGENT_CARD_ID"));
		agent_card_valid_date = Utility.parseInt((Integer)map.get("AGENT_CARD_VALID_DATE"), new Integer(0));
		partner_card_id = Utility.trimNull(map.get("PARTNER_CARD_ID"));
	}

	if (print_deploy_bill.intValue()==1) sPrint_deploy_bill = "checked";	
	if (print_post_info.intValue() == 1) sPrint_post_info = "checked";
	
	if (is_deal.intValue()==1)
		is_deal_name = enfo.crm.tools.LocalUtilis.language("class.factCust",clientLocale);//事实客户		
	else
		is_deal_name = enfo.crm.tools.LocalUtilis.language("class.potentialCust",clientLocale);//潜在客户
}
%>
<HTML>
	<HEAD>
		<TITLE><%=LocalUtilis.language("menu.customerInfoDetailsView",clientLocale)%></TITLE><!--客户信息查询显示-->
		<BASE TARGET="_self">
		<meta http-equiv="X-UA-Compatible" content="IE=7" >
		<META HTTP-EQUIV=Content-Type content="text/html; charset=gbk">
		<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
		<META HTTP-EQUIV="Expires" CONTENT="0">
		<meta http-equiv="X-UA-Compatible" content="IE=7" >
		<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
		<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>

		<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
		<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
		<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/investment.js"></SCRIPT>
		<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>

		<script language="javascript">
			/*取得客户信息数组*/
			function getCustomerDetailsInfo(){
				var v = new Array();
				
				v[0] = document.theform.customer_cust_name.value;
				v[1] = document.theform.customer_cust_type_name.value;
				v[2] = document.theform.customer_card_id.value;
				v[3] = document.theform.customer_h_tel.value;
				v[4] = document.theform.customer_mobile.value;
				v[5] = document.theform.customer_post_address.value;
				v[6] = document.theform.customer_post_code.value;
				v[7] = document.theform.cust_id.value;	
				v[8] = document.getElementsByName("customer_service_man_value")[0].value;	
				v[9] = document.getElementsByName("customer_service_man")[0].value;
				v[10]=document.theform.customer_o_tel.value;
				v[11]=document.theform.customer_bp.value;			
				v[12] = document.theform.customer_is_link.checked? 1: 0;
				v[13] = document.getElementsByName("customer_cust_type")[0].value;
				v[14] = document.getElementsByName("customer_cust_on")[0].value;
				v[15] = document.getElementsByName("customer_card_type")[0].value;	
				v[16] = document.getElementById("customer_legal_man").value;	
				v[17] = document.getElementById("customer_contact_man").value;
				v[18] = document.getElementsByName("customer_e_mail")[0].value;
				v[19] = document.getElementById("customer_is_deal").value;
				v[20] = document.getElementById("total_count").value;
				return v;
			}

			/*验证数据*/
			function validateForm(form){
				var custId = document.theform.cust_id.value;
				var select_flag = document.getElementById("select_flag").value;
				
				if (custId == null || custId == "" || custId== "0"){
					sl_alert("<%=LocalUtilis.language("message.selectAccountManager",clientLocale)%> ！");//请选择客户经理
					return false;
				}	
				
				if(select_flag==1){
					var is_deal = document.getElementById("customer_is_deal").value;
					
					//if(is_deal!=1){ --国民需求：不需要验证是否喂事实客户
					//	sl_alert("<%=LocalUtilis.language("message.custTip2",clientLocale)%> ！");//该客户不是事实客户
					//	return false;
					//}
				}
					
				return true;		
			}

			/*保存数据*/
			function saveAction(form){
				if(validateForm(form)){
					var customerInfoArray = getCustomerDetailsInfo();
				
					window.parent.document.all("resultValue").value=customerInfoArray; 
					window.parent.theform.saveButton.click() ;
				}
			}
/**************************************************************************************************************************************/
function showAcctNum(value){		
	if (trim(value) == "")
		bank_acct_num.innerText = "";
	else
		bank_acct_num.innerText = "(" + showLength(value) + " 位 )";
}

function calculateAge(){
	if (event.keyCode == 13 || event.keyCode == 9){
		if (document.theform.customer_card_type.value != '110801')	return;
		var r18  = /^[1-9]\d{5}(\d{8})\d{3}[A-Za-z0-9]$/;
		var r15  = /^[1-9]\d{5}(\d{6})\d{3}$/;
		cardID = cTrim(document.theform.customer_card_id.value,0);
		var current_date = new Date();
		if (r18.test(cardID))
		{
			var t = cardID.match(r18)[1];
			var card_date = getDateByMask(t, "yyyyMMdd");
			if (card_date != null )
			{
				if(card_date.getYear() >= 2000)
					document.theform.customer_age.value =( current_date.getYear() - card_date.getYear());
				else
					document.theform.customer_age.value =( current_date.getYear() - (1900+card_date.getYear()));
			}
			else
				document.theform.customer_age.value = "";
			var flag = cardID.charAt(16);
			if(parseInt(flag) % 2 == 0)
				document.theform.customer_sex.value = '2';
			else
				document.theform.customer_sex.value = '1';
			document.theform.customer_birthday_picker.value = cardID.substring(6,10)+"-"+cardID.substring(10,12)+"-"+cardID.substring(12,14);	
		}
		else if (r15.test(cardID))
		{
			
			var t = cardID.match(r15)[1];
			var card_date = getDateByMask(t,"yyMMdd");
			if (card_date != null )
			{
				if(card_date.getYear()>= 2000)
				{
					document.theform.customer_age.value =( current_date.getYear() - (2000+card_date.getYear()));				
					document.theform.customer_birthday_picker.value = (2000+card_date.getYear())+"-"+cardID.substring(8,10)+"-"+cardID.substring(10,12);				
				}	
				else
				{
					document.theform.customer_age.value =( current_date.getYear() - (1900+card_date.getYear()));
					document.theform.customer_birthday_picker.value = (1900+card_date.getYear())+"-"+cardID.substring(8,10)+"-"+cardID.substring(10,12);				
				}	
			}
			else
				document.theform.customer_age.value = "";
			var flag = cardID.charAt(14);
			if(parseInt(flag) % 2 == 0)
				document.theform.customer_sex.value = '2';
			else
				document.theform.customer_sex.value = '1';		
		}
		else
		{
			sl_alert("<%=LocalUtilis.language("message.customerCardIDError",clientLocale)%> ！");//身份证号码格式不合法
			event.keyCode = 0;
			document.theform.customer_card_id.focus();
		}
	}
}

function calculateAge2(){
	if (document.theform.customer_card_type.value != '110801')	return;
	var r18  = /^[1-9]\d{5}(\d{8})\d{3}[A-Za-z0-9]$/;
	var r15  = /^[1-9]\d{5}(\d{6})\d{3}$/;
	cardID = cTrim(document.theform.customer_card_id.value,0);
	var current_date = new Date();
	if (r18.test(cardID))
	{
		var t = cardID.match(r18)[1];
		var card_date = getDateByMask(t, "yyyyMMdd");
		if (card_date != null )
		{
			if(card_date.getYear() >= 2000)
				document.theform.customer_age.value =( current_date.getYear() - card_date.getYear());
			else
				document.theform.customer_age.value =( current_date.getYear() - (1900+card_date.getYear()));
		}
		else
			document.theform.customer_age.value = "";
		var flag = cardID.charAt(16);
		if(parseInt(flag) % 2 == 0)
			document.theform.customer_sex.value = '2';
		else
			document.theform.customer_sex.value = '1';
		document.theform.customer_birthday_picker.value = cardID.substring(6,10)+"-"+cardID.substring(10,12)+"-"+cardID.substring(12,14);	
	}
	else if (r15.test(cardID))
	{
		
		var t = cardID.match(r15)[1];
		var card_date = getDateByMask(t,"yyMMdd");
		if (card_date != null )
		{
			if(card_date.getYear()>= 2000)
			{
				document.theform.customer_age.value =( current_date.getYear() - (2000+card_date.getYear()));				
				document.theform.customer_birthday_picker.value = (2000+card_date.getYear())+"-"+cardID.substring(8,10)+"-"+cardID.substring(10,12);				
			}	
			else
			{
				document.theform.customer_age.value =( current_date.getYear() - (1900+card_date.getYear()));
				document.theform.customer_birthday_picker.value = (1900+card_date.getYear())+"-"+cardID.substring(8,10)+"-"+cardID.substring(10,12);				
			}	
		}
		else
			document.theform.customer_age.value = "";
		var flag = cardID.charAt(14);
		if(parseInt(flag) % 2 == 0)
			document.theform.customer_sex.value = '2';
		else
			document.theform.customer_sex.value = '1';		
	}
}

function calculateAge(){
	if (event.keyCode == 13 || event.keyCode == 9){
		if (document.theform.customer_card_type.value != '110801')return;
		
		var r18  = /^[1-9]\d{5}(\d{8})\d{3}[A-Za-z0-9]$/;
		var r15  = /^[1-9]\d{5}(\d{6})\d{3}$/;
		cardID = cTrim(document.theform.customer_card_id.value,0);
		var current_date = new Date();
		
		if (r18.test(cardID)){
			var t = cardID.match(r18)[1];
			var card_date = getDateByMask(t, "yyyyMMdd");
			
			if (card_date != null ){
				if(card_date.getYear() >= 2000)
					document.theform.customer_age.value =( current_date.getYear() - card_date.getYear());
				else
					document.theform.customer_age.value =( current_date.getYear() - (1900+card_date.getYear()));
			}
			else
				document.theform.customer_age.value = "";
				
			var flag = cardID.charAt(16);
			if(parseInt(flag) % 2 == 0)
				document.theform.customer_sex.value = '2';
			else
				document.theform.customer_sex.value = '1';
				
			document.theform.customer_birthday_picker.value = cardID.substring(6,10)+"-"+cardID.substring(10,12)+"-"+cardID.substring(12,14);	
		}
		else if (r15.test(cardID)){			
			var t = cardID.match(r15)[1];
			var card_date = getDateByMask(t,"yyMMdd");
			
			if (card_date != null ){
				if(card_date.getYear()>= 2000){
					document.theform.customer_age.value =( current_date.getYear() - (2000+card_date.getYear()));				
					document.theform.customer_birthday_picker.value = (2000+card_date.getYear())+"-"+cardID.substring(8,10)+"-"+cardID.substring(10,12);				
				}	
				else{
					document.theform.customer_age.value =( current_date.getYear() - (1900+card_date.getYear()));
					document.theform.customer_birthday_picker.value = (1900+card_date.getYear())+"-"+cardID.substring(8,10)+"-"+cardID.substring(10,12);				
				}	
			}
			else
				document.theform.customer_age.value = "";
				
			var flag = cardID.charAt(14);
			
			if(parseInt(flag) % 2 == 0)
				document.theform.customer_sex.value = '2';
			else
				document.theform.customer_sex.value = '1';		
		}
		else{
			sl_alert("<%=LocalUtilis.language("message.customerCardIDError",clientLocale)%> ！");//身份证号码格式不合法
			event.keyCode = 0;
			document.theform.customer_card_id.focus();
		}
	}
}

function calculateAge2()
{
	if (document.theform.customer_card_type.value != '110801')	return;
	var r18  = /^[1-9]\d{5}(\d{8})\d{3}[A-Za-z0-9]$/;
	var r15  = /^[1-9]\d{5}(\d{6})\d{3}$/;
	cardID = cTrim(document.theform.customer_card_id.value,0);
	var current_date = new Date();
	if (r18.test(cardID))
	{
		var t = cardID.match(r18)[1];
		var card_date = getDateByMask(t, "yyyyMMdd");
		if (card_date != null )
		{
			if(card_date.getYear() >= 2000)
				document.theform.customer_age.value =( current_date.getYear() - card_date.getYear());
			else
				document.theform.customer_age.value =( current_date.getYear() - (1900+card_date.getYear()));
		}
		else
			document.theform.customer_age.value = "";
		var flag = cardID.charAt(16);
		if(parseInt(flag) % 2 == 0)
			document.theform.customer_sex.value = '2';
		else
			document.theform.customer_sex.value = '1';
		document.theform.customer_birthday_picker.value = cardID.substring(6,10)+"-"+cardID.substring(10,12)+"-"+cardID.substring(12,14);	
	}
	else if (r15.test(cardID))
	{
		
		var t = cardID.match(r15)[1];
		var card_date = getDateByMask(t,"yyMMdd");
		if (card_date != null )
		{
			if(card_date.getYear()>= 2000)
			{
				document.theform.customer_age.value =( current_date.getYear() - (2000+card_date.getYear()));				
				document.theform.customer_birthday_picker.value = (2000+card_date.getYear())+"-"+cardID.substring(8,10)+"-"+cardID.substring(10,12);				
			}	
			else
			{
				document.theform.customer_age.value =( current_date.getYear() - (1900+card_date.getYear()));
				document.theform.customer_birthday_picker.value = (1900+card_date.getYear())+"-"+cardID.substring(8,10)+"-"+cardID.substring(10,12);				
			}	
		}
		else
			document.theform.customer_age.value = "";
		var flag = cardID.charAt(14);
		if(parseInt(flag) % 2 == 0)
			document.theform.customer_sex.value = '2';
		else
			document.theform.customer_sex.value = '1';		
	}
}

function CalAge(){
	if (event.keyCode == 13||event.keyCode == 9){
		if(document.theform.customer_cust_type.value==1)
		{
			if(document.theform.customer_birthday_picker.value!="")
			{
				if(!sl_checkDate(document.theform.customer_birthday_picker,"<%=LocalUtilis.language("class.birthday",clientLocale)%> "))	return false;//出生日期
				var current_date = new Date();					
				document.theform.customer_age.value = current_date.getYear() - document.theform.customer_birthday_picker.value.substring(0,4);			
			}
		}
	}	
}

function CalAge2(){
	if(document.theform.customer_cust_type.value==1){
		if(document.theform.customer_birthday_picker.value!=""){
			if(!sl_checkDate(document.theform.customer_birthday_picker,"<%=LocalUtilis.language("class.birthday",clientLocale)%> "))	return false;//出生日期
			var current_date = new Date();					
			document.theform.customer_age.value = current_date.getYear() - document.theform.customer_birthday_picker.value.substring(0,4);			
		}
	}	
}
//评级
function newInfo(cust_id){
	if(showModalDialog('cust_grade_new.jsp?cust_id='+cust_id, '', 'dialogWidth:700px;dialogHeight:600px;status:0;help:0')){
		sl_alert("<%=LocalUtilis.language("message.rating",clientLocale)%><%=LocalUtilis.language("message.success",clientLocale)%> ！");
	}
}
function showSurvey(cust_id){
	var ret = showModalDialog('/client/cust_property_survey.jsp?cust_id='+cust_id, '', 'dialogWidth:590px;dialogHeight:730px;status:0;help:0');
}

function ajax(options) {
        options = options || {};
        options.type = (options.type || "GET").toUpperCase();
        options.dataType = options.dataType || "json";
        var params = formatParams(options.data);

        //创建 - 非IE6 - 第一步
        if (window.XMLHttpRequest) {
            var xhr = new XMLHttpRequest();
        } else { //IE6及其以下版本浏览器
            var xhr = new ActiveXObject('Microsoft.XMLHTTP');
        }

        //接收 - 第三步
        xhr.onreadystatechange = function () {
            if (xhr.readyState == 4) {
                var status = xhr.status;
                if (status >= 200 && status < 300) {
                    options.success && options.success(xhr.responseText, xhr.responseXML);
                } else {
                    options.fail && options.fail(status);
                }
            }
        }

        //连接 和 发送 - 第二步
        if (options.type == "GET") {
            xhr.open("GET", options.url + "?" + params, true);
            xhr.send(null);
        } else if (options.type == "POST") {
            xhr.open("POST", options.url, true);
            //设置表单提交时的内容类型
            xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            xhr.send(params);
        }
    }
    
//格式化参数
function formatParams(data) {
        var arr = [];
        for (var name in data) {
            arr.push(encodeURIComponent(name) + "=" + encodeURIComponent(data[name]));
        }
        arr.push(("v=" + Math.random()).replace(".",""));
        return arr.join("&");
 }
function saveCustomerInfo(){
	
var customer_cust_type_name = document.getElementsByName("customer_cust_type_name")[0].value;//客户类别
var customer_service_man = document.getElementsByName("customer_service_man")[0].value;//客户经理
var customer_birthday_picker = "";
var customer_sex_name = "";
	//customer_service_man = customer_service_man +"杀";	
	if(document.getElementsByName("customer_birthday_picker")[0]!=null){	
	customer_birthday_picker = document.getElementsByName("customer_birthday_picker")[0].value;//出生日期
	}


	if(document.getElementsByName("customer_sex_name")[0]!=null){
		customer_sex_name = document.getElementsByName("customer_sex_name")[0].value;//性别
		customer_sex_name = customer_sex_name + "的";
	}
var customer_post_address = document.getElementsByName("customer_post_address")[0].value;//地址
	customer_post_address = customer_post_address + "猫";
//alert(customer_service_man);
ajax({
        url: "<%=request.getContextPath()%>/marketing/customerInfo_save.jsp",              //请求地址
        type: "GET",                       //请求方式
        data: { customer_cust_type_name:customer_cust_type_name,customer_service_man:customer_service_man,customer_birthday_picker:customer_birthday_picker,customer_sex_name:customer_sex_name,customer_post_address:customer_post_address},        //请求参数
        dataType: "json",
        success: function (response, xml) {
			//alert(response);
            // 此处放成功后执行的代码
        },
        fail: function (status) {
            // 此处放失败后执行的代码
			//alert(status);
        }
    });
}
setTimeout("saveCustomerInfo()",100);
</script>
</HEAD>

<BODY class="body body-nox">
<form name="theform" method="post">
<input type="hidden" name="cust_id" value="<%=cust_id%>">
<input type='hidden' name="customer_service_man_value"  value='<%=service_man%>'>
<input type='hidden' name="customer_cust_type"  value='<%=Utility.trimNull(cust_type)%>'/>
<input type='hidden' name="customer_cust_on"  value='<%=Utility.trimNull(cust_no)%>'/>
<input type='hidden' id="customer_legal_man"   value="<%=legal_man%>">
<input type='hidden' id="customer_contact_man"  value="<%=contact_man%>">
<input type='hidden' id="customer_card_type"  value='<%=Utility.trimNull(card_type)%>'/>
<input type='hidden' id="customer_is_deal"  value='<%=Utility.trimNull(is_deal)%>'/>
<input type='hidden' id="total_count"  value='<%=Utility.trimNull(total_count)%>'/>
<input type='hidden' id="select_flag" name="select_flag"  value='<%=Utility.trimNull(select_flag)%>'/>
<!--客户信息-->
<div align="left">
<TABLE cellSpacing=0 cellPadding=2 width="100%" border=0 <%if(readonly != 1){ %> style="display:none"<%}%>>
	<tr>
		<td class="page-title"><font color="#215dc6"><b><%=menu_info%></b></font></td>
	</tr>
</TABLE>
<br/>
<table border=0 cellSpacing=0 cellPadding=0 width="100%" <%if(readonly != 1){ %> style="display:none"<%}%>>
</table>
<table border="0" width="100%" cellspacing="0" cellpadding="1" class="product-list">
	<tr>
		<td align="right"><span style="color:red">*</span><%=LocalUtilis.language("class.customerType",clientLocale)%> :&nbsp;&nbsp;</td><!--客户类别-->
		<td>
			<input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this)" 
			name="customer_cust_type_name" size="25" maxlength="20" value="<%=cust_type_name%>"/>
		</td>
		<td align="right"><span style="color:red">*</span><%=LocalUtilis.language("class.custType",clientLocale)%> :&nbsp;&nbsp;</td><!--客户类型-->
		<td ><input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this)" name="customer_is_deal_name" size="25" maxlength="60" value="<%=is_deal_name%>"></td>	
	</tr>
	
	<tr>
		<td align="right"><span style="color:red">*</span><%=LocalUtilis.language("class.customerName",clientLocale)%> :&nbsp;&nbsp;</td><!--客户名称-->
		<td ><input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this)" name="customer_cust_name" size="25" maxlength="60" value="<%=cust_name%>"></td>
		<td align="right">	
			<span style="color:red">*</span><%=cust_type.intValue()==1?LocalUtilis.language("class.profession",clientLocale):LocalUtilis.language("class.industry",clientLocale)%> :&nbsp;&nbsp;<!--职业/行业-->				
		</td>
		<td>
			<input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this)" name="customer_voc_type_name" size="25" maxlength="20" value="<%=voc_type_name%>">			
		</td>
	</tr>
	
	<%if(cust_type.intValue()==2){%>
	<tr>
		<td align="right"><span style="color:red">*</span>机构类型 :&nbsp;&nbsp;</td><!--机构类型-->	
		<td><input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this)" name="jg_type" size="25" maxlength="60" value="<%=jg_type_name%>"></td>
		<td align="right"><span style="color:red">*</span>委托人类型 :&nbsp;&nbsp;</td><!--委托人类型-->	
		<td><input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this)" name="jg_wtrlx2" size="25" maxlength="60" 
				value="<%if(jg_wtrlx2.equals("1")){out.print("金融性公司");} 
						else if(jg_wtrlx2.equals("2")){out.print("政府");}
					   else if(jg_wtrlx2.equals("3")){out.print("非金融性公司");}
						else if(jg_wtrlx2.equals("4")){out.print("境外金融性公司");}%>">		
		</td>
	</tr>
	<%} %>
	<tr>	
		<td align="right"><%=LocalUtilis.language("class.customerNumber",clientLocale)%> :&nbsp;&nbsp;</td><!--客户代码-->
		<td><input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this)" name="customer_cust_no" size="25" maxlength="25" value="<%=cust_no%>"></td>
		<td align="right"><span style="color:red">*</span><%=LocalUtilis.language("class.customerSource",clientLocale)%> :&nbsp;&nbsp;</td><!--客户来源-->
		<td>
			<input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this)" name="customer_cust_source_name" size="25" maxlength="25" value="<%=cust_source_name%>">
		</td>	
	</tr>
	
	<tr>	
		<td align="right"><%=LocalUtilis.language("class.customerVipCardId",clientLocale)%> :&nbsp;&nbsp;</td><!--VIP卡编号-->
		<td><input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this)" name="customer_vip_card_id" size="25" maxlength="25" value="<%=vip_card_id%>"></td>
		<td align="right"><%=LocalUtilis.language("class.customerHgtzrBh",clientLocale)%> :&nbsp;&nbsp;</td><!--合格投资人编号-->
		<td><input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this)" name="customer_hgtzr_bh" size="25" maxlength="25" value="<%=hgtzr_bh%>"></td>	
	</tr>
	
	<tr>
		<td align="right"><%=LocalUtilis.language("class.customerVipDatePicker",clientLocale)%> :&nbsp;&nbsp;</td><!--VIP发卡日期-->
		<td>
			<INPUT TYPE="text" <%=sReadonly%> NAME="customer_vip_date_picker" class=selecttext size="25"
			<%if(vip_date==null){%> value="" <%}else{%>value="<%=Format.formatDateLine(vip_date)%>"<%}%> 
			onkeydown="javascript:nextKeyPress(this)" />
			<%if(sReadonly.equals("")){%><INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.customer_vip_date_picker,theform.customer_vip_date_picker.value,this);" tabindex="13">
			<%}%><INPUT TYPE="hidden" NAME="customer_vip_date"   value="">
		</td>
		<td align="right"><%=LocalUtilis.language("class.accountManager",clientLocale)%> :&nbsp;&nbsp;</td> <!--客户经理-->
		<td>
			<input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this);" name="customer_service_man" size="25" maxlength="20" value="<%=Utility.trimNull(Argument.getManager_Name(service_man))%>"><%session.setAttribute("customer_service_man",Utility.trimNull(Argument.getManager_Name(service_man)));%>
		</td>
	</tr>		
	
	<tr>
		<td align="right"><span style="color:red">*</span><%=LocalUtilis.language("class.customerCardType",clientLocale)%> :&nbsp;&nbsp;</td><!--证件类型-->
		<td>
			<input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this)" name="customer_card_type_name" size="25" maxlength="25" value="<%=card_type_name%>">
		</td>	
				
		<td align="right"><span style="color:red">*</span><%=LocalUtilis.language("class.customerCardID",clientLocale)%> :&nbsp;&nbsp;</td><!--证件号码-->
		<td>
			<input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this)"
				onkeyup="javascript:showAcctNum(this.value)" name="customer_card_id" size="25" maxlength="30" value="<%=card_id%>">
			<span id="bank_acct_num" class="span"></span>
		</td>	
	</tr>

	<tr>		
		<%if(cust_id.intValue()==0&&cust_type.intValue()==1){	%>
		<td align="right"><%=LocalUtilis.language("class.birthday",clientLocale)%> :&nbsp;&nbsp;</td><!--出生日期-->
		<td>
			<INPUT TYPE="text" <%=sReadonly%> NAME="customer_birthday_picker" class=selecttext onblur="javascript:CalAge2();"
			<%if(birthday==null){%> value=""
			<%}else{%>value="<%=Format.formatDateLine(birthday)%>"<%}%> onkeydown="javascript:CalAge();nextKeyPress(this)">
			<%if(sReadonly.equals("")){%><INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.customer_birthday_picker,theform.customer_birthday_picker.value,this);" tabindex="13">
			<%}%><INPUT TYPE="hidden" NAME="customer_birthday"   value="">
		</td>	
		<%}else if(cust_id.intValue()!=0&&cust_type.intValue()==1){%>
		<td align="right"><%=LocalUtilis.language("class.birthday",clientLocale)%> :&nbsp;&nbsp;</td><!--出生日期-->
		<td>
			<INPUT TYPE="text" <%=sReadonly%> NAME="customer_birthday_picker" class=selecttext onblur="javascript:CalAge2();"
			<%if(birthday==null){%> value=""
			<%}else{%>value="<%=Format.formatDateLine(birthday)%>"<%}%> onkeydown="javascript:CalAge();nextKeyPress(this)">
			<%if(sReadonly.equals("")){%>
			<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.customer_birthday_picker,theform.customer_birthday_picker.value,this);" tabindex="13">
			<%}%><INPUT TYPE="hidden" NAME="customer_birthday"   value="">
		</td>

		<%}%>
		<td align="right"><%=LocalUtilis.language("class.tradeDate",clientLocale)%> :&nbsp;&nbsp;</td>
		<td align="left"><input type="text" name="customer_card_valid_date" <%=sReadonly%>  size="10" maxlength="8" value="<%=Utility.trimNull(card_valid_date)%>"></td>
	<%if (cust_type.intValue()==2) { %>
		<td align="right"><%=LocalUtilis.language("class.contact",clientLocale)%> :&nbsp;&nbsp;</td><!--联系人-->
		<td><input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this);" name="customer_contact_man"  size="25" maxlength="20" value="<%=contact_man%>"></td>
	<%} %>
	</tr>
				
	<%if(cust_type.intValue()==2){//机构%>
	<tr>
		<td align="right">经营范围 :&nbsp;&nbsp;</td><!--法人姓名-->
		<td><input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this)" name="managing_scope"  size="25" maxlength="30" value="<%=managing_scope%>"></td>
		<td align="right">企业营业执照编号 :&nbsp;&nbsp;</td>
		<td><input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this);" name="business_code"  size="25" maxlength="20" value="<%=business_code%>"></td>
	</tr>
	<tr>
		<td align="right">税务登记证 :&nbsp;&nbsp;</td>
		<td><input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this)" name="tax_reg_code"  size="25" maxlength="30" value="<%=tax_reg_code%>"></td>
		<td align="right">组织机构代码 :&nbsp;&nbsp;</td>
		<td><input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this);" name="organ_code"  size="25" maxlength="20" value="<%=organ_code%>"></td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.corporate",clientLocale)%> :&nbsp;&nbsp;</td><!--法人姓名-->
		<td><input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this)" name="customer_legal_man" size="25" maxlength="30" value="<%=legal_man%>" style="display:none">
			<input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this)" name="legal_name"  size="25" maxlength="30" value="<%=legal_name%>"/>
		</td>
		<td align="right">法人电话 :&nbsp;&nbsp;</td>
		<td><input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this);" name="legal_tel"  size="25" maxlength="20" value="<%=legal_tel%>"></td>
	</tr>
	<tr>
		<td align="right">法人证件类型 :&nbsp;&nbsp;</td>
		<td><input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this)" name="legal_card_type"  size="25" maxlength="30" value="<%=Argument.getCardTypeName(legal_card_type)%>"></td>
		<td align="right">法人证件号码 :&nbsp;&nbsp;</td>
		<td><input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this);" name="legal_card_id"  size="25" maxlength="20" value="<%=legal_card_id%>"></td>
	</tr>
	<tr>
		<td align="right">法人证件有效期限 :&nbsp;&nbsp;</td>
		<td><input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this)" name="legal_card_valid_date"  size="25" maxlength="30" 
				value="<%=Format.formatDateLine(legal_card_valid_date)%>"></td>
		<td align="right">代理人姓名 :&nbsp;&nbsp;</td>
		<td><input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this)" name="agent_name"  size="25" maxlength="30" value="<%=agent_name%>"></td>
	</tr>	
	<tr>
		<td align="right">代理人电话 :&nbsp;&nbsp;</td>
		<td><input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this);" name="agent_tel"  size="25" maxlength="20" value="<%=agent_tel%>"></td>
		<td align="right">代理人证件类型 :&nbsp;&nbsp;</td>
		<td><input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this)" name="agent_card_type"  size="25" maxlength="30" value="<%=Argument.getCardTypeName(agent_card_type)%>"></td>
	</tr>
	<tr>		
		<td align="right">代理人证件号码 :&nbsp;&nbsp;</td>
		<td><input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this);" name="agent_card_id"  size="25" maxlength="20" value="<%=agent_card_id%>"></td>
		<td align="right">代理人证件有效期限 :&nbsp;&nbsp;</td>
		<td><input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this)" name="agent_card_valid_date"  size="25" maxlength="30" 
				value="<%=Format.formatDateLine(agent_card_valid_date)%>"></td>
	</tr>
	<tr>		
		<td align="right">合伙人身份证明文件编码 :&nbsp;&nbsp;</td>
		<td><input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this)" name="partner_card_id" size="25" maxlength="30" value="<%=partner_card_id%>"></td>
	</tr>	
	<%}%>
	<%if(cust_type.intValue()==1){//个人%>	
	<tr>
		<td align="right"><%=LocalUtilis.language("class.age",clientLocale)%> :&nbsp;&nbsp;</td><!--年龄-->
		<td><INPUT <%=sReadonly%> name="customer_age" onkeydown="javascript:nextKeyPress(this)" size="20" maxlength="4" value="<%=age%>"></td>
		<td align="right"><%=LocalUtilis.language("class.sex",clientLocale)%> :&nbsp;&nbsp;</td><!--性别-->
		<td><input <%=sReadonly%> name="customer_sex_name" onkeydown="javascript:nextKeyPress(this);"  size="25" maxlength="20" value="<%=sex_name%>"></td>
	</tr>
	<%}%>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.countryName",clientLocale)%> :&nbsp;&nbsp;<%session.setAttribute("nationalityText",country_name);session.setAttribute("nationality",country);%></td><!--国别-->
		<td><INPUT <%=sReadonly%> name="customer_country" onkeydown="javascript:nextKeyPress(this)" size="20" maxlength="4" value="<%=country_name%>"></td>
		<td align="right"><%=LocalUtilis.language("class.referee",clientLocale)%> :&nbsp;&nbsp</td> <!--推荐人-->   
		<td>
			<input type="text" name="recommended"<%=sReadonly%> size="25" value="<%=recommended%>">
		</td>
	</tr>
		
	<tr>	
		<td align="right"><%=LocalUtilis.language("class.customerHTel",clientLocale)%> :&nbsp;&nbsp;</td><!--家庭电话-->
		<td><input <%=sReadonly%> name="customer_h_tel" onkeydown="javascript:nextKeyPress(this)" maxlength="20" size="25" value="<%=h_tel%>"></td>
		<td align="right"><%=LocalUtilis.language("class.companyPhone",clientLocale)%> :&nbsp;&nbsp;</td><!--公司电话-->
		<td><INPUT <%=sReadonly%> name="customer_o_tel" onkeydown="javascript:nextKeyPress(this)" maxlength="20" size="25" value="<%=o_tel%>"></td>
	</tr>
	
	<tr>
		<td align="right"><%=LocalUtilis.language("class.customerMobile",clientLocale)%> :&nbsp;&nbsp;</td><!--手机-->
		<td><input <%=sReadonly%> name="customer_mobile" onkeydown="javascript:nextKeyPress(this)" maxlength="30" size="25" value="<%=mobile%>"></td>
		<td align="right"><%=LocalUtilis.language("class.customerMobile",clientLocale)%> 2 :&nbsp;&nbsp;</td><!--手机2-->
		<td><INPUT <%=sReadonly%> name="customer_bp" onkeydown="javascript:nextKeyPress(this)" size="25" maxlength="30" value="<%=bp%>"></td>
	</tr>
	
	<tr>
		<td align="right"><%=LocalUtilis.language("class.fax",clientLocale)%> :&nbsp;&nbsp;</td><!--传真-->
		<td><input <%=sReadonly%> name="customer_fax" onkeydown="javascript:nextKeyPress(this)" size="25" maxlength="30" value="<%=fax%>"></td>
		<td align="right">Email :&nbsp;&nbsp;</td>
		<td><INPUT <%=sReadonly%> name="customer_e_mail" onkeydown="javascript:nextKeyPress(this)" maxlength="30" size="25" value="<%=e_mail%>"></td>
	</tr>
	
	<tr>
		<td align="right"><%=LocalUtilis.language("class.postcode",clientLocale)%> :&nbsp;&nbsp;</td><!--邮政编码-->
		<td ><input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this)" name="customer_post_code" size="25" maxlength="6" value="<%=post_code%>"></td>	
		<td align="right"><%=LocalUtilis.language("class.postAddress2",clientLocale)%> :&nbsp;&nbsp;</td><!--邮寄地址-->
		<td ><input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this)" name="customer_post_address" size="25" maxlength="60" value="<%=post_address%>"><%session.setAttribute("customer_post_address",post_address);%></td>
	</tr>
	
	<tr>
		<td align="right"><%=LocalUtilis.language("class.postcode",clientLocale)%> 2 :&nbsp;&nbsp;</td><!--邮政编码2-->
		<td ><input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this)" name="customer_post_code2" size="25" maxlength="6" value="<%=post_code2%>"></td>
		
		<td align="right"><%=LocalUtilis.language("class.postAddress2",clientLocale)%> 2 :&nbsp;&nbsp;</td><!--邮寄地址2-->
		<td ><input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this)" name="customer_post_address2" size="25" maxlength="60" value="<%=post_address2%>"></td>
	</tr>
	
	<tr>
		<td align="right"><span style="color:red">*</span><%=LocalUtilis.language("class.serviceWay",clientLocale)%> :&nbsp;&nbsp;</td><!--联系方式-->
		<td><input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this);" name="customer_touch_type_name" size="25" maxlength="25" value="<%=Utility.trimNull(touch_type_name)%>"></td>
		<td align="right"><%=LocalUtilis.language("class.moneySource",clientLocale)%> :&nbsp;&nbsp;</td><!--资金来源-->
		<td><input <%=sReadonly%> name="customer_money_source" onkeydown="javascript:nextKeyPress(this);"  size="25" maxlength="20" value="<%=money_source_name%>"></td>
		
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.customerIsLink",clientLocale)%> :&nbsp;&nbsp;</td><!--关联方-->
		<td ><input <%=sDisabled%> type="checkbox" name="customer_is_link" value="<%=is_link%>" <%if(is_link !=null){if(is_link.intValue()==1) out.print("checked");}%> class="flatcheckbox"></td>
		<td align="right">真实性 :&nbsp;&nbsp;</td>
		<td><select <%=sDisabled%> name="true_flag"><%=Argument.getCustInfoTrueFlagList(true_flag)%></td>
	</tr>
	
	<tr>	
		<td align="right"><%=LocalUtilis.language("message.printCustomerStatement",clientLocale)%> :&nbsp;&nbsp;</td><!--打印客户对帐单-->
		<td><input <%=sDisabled%> class="flatcheckbox" type="checkbox" name="customer_print_deploy_bill" <%=sPrint_deploy_bill%> value="1"></td>
		<td align="right"><%=LocalUtilis.language("message.customerPrintPostInfo",clientLocale)%> :&nbsp;&nbsp;</td><!--打印披露信息-->
		<td><input <%=sDisabled%> class="flatcheckbox" type="checkbox" name="customer_print_post_info" <%=sPrint_post_info%> value="1"></td>
	</tr>
	<tr>	
		<td  align="right"><%=LocalUtilis.language("class.customerSummary",clientLocale)%> :&nbsp;&nbsp;</td><!--备注-->
		<td colspan="3">
			<textarea <%if (!sReadonly.equals("")) out.print("readonly class='edline_textarea'");%> rows="3" name="customer_summary" cols="86"><%=summary%></textarea>
		</td>
	</tr>
	<%if(cust_id.intValue()!=0){%>
	<tr>
		<td colspan="4">
			<input type="button" class="xpbutton6" onclick="javascript:showSurvey(<%=cust_id%>);" value="客户信托财产调查表"/>
		</td>
	</tr>
	<%}%>
</table>

<table border="0" cellpadding="4" cellspacing="2" class="tablelinecolor" width="100%" 
	<%if(user_id.intValue() != 2 && user_id.intValue() != 22){ %> style="display:none"<%} %>>
<tr bgColor=#ffffff>
	<td align="left" colspan="1" height="15"><font color="#CC99CC" size="-1">证件信息</font>
	</td>
</tr>
<tr class="tr1">
	<td>
		<%if(cust_type.intValue() == 2){ 
			//获得证件信息附件
			AttachmentLocal attachmentLocal = EJBFactory.getAttachment();
			AttachmentVO attachment_vo = new AttachmentVO();
			attachment_vo.setDf_talbe_id(new Integer(4));
			attachment_vo.setDf_serial_no(cust_id);
		
			List attachmentList = attachmentLocal.load(attachment_vo);
			Iterator attachment_it = attachmentList.iterator();
			HashMap attachment_map = new HashMap();
	       while(attachment_it.hasNext()){
			   	attachment_map = (HashMap)attachment_it.next();
%>
			<div id="divattach<%=Utility.trimNull(attachment_map.get("ATTACHMENTID"))%>" style="display:">
			附件：<a title="查看附件" href="<%=request.getContextPath()%>/system/basedata/downloadattach.jsp?file_name=<%=Utility.replaceAll(Utility.trimNull(attachment_map.get("SAVE_NAME")),"\\","/")%>&name=<%=Utility.trimNull(attachment_map.get("ORIGIN_NAME"))%>" ><%=Utility.trimNull(attachment_map.get("ORIGIN_NAME"))%></a>
	            	&nbsp;&nbsp;&nbsp;&nbsp;
				<br>
			</div>	
		<%}}else{ %>	
		<iframe src ="/client/clientinfo/show_client_image.jsp?cust_id=<%=cust_id%>" name="sonIframe" 	frameborder="no" border="0" height="260" width="100%"></iframe>
		<%} %>
	</td>	
</tr>
</table>

</div>

<div align="right" style="margin-top:5px;margin-right:10px">

	<button type="button"   class="xpbutton3" accessKey=s id="btnSave" name="btnSave" 	<%if(readonly == 1){ %> style="display:none"<%} %> onclick="javascript:saveAction(document.theform);"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>S</u>)</button>
	&nbsp;&nbsp;<!--确认-->

	<button type="button"   class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.returnValue=null;window.close();"><%=LocalUtilis.language("message.close",clientLocale)%> (<u>C</u>)</button>
	&nbsp;&nbsp;<!--关闭-->
</div>

</form>
<%customer.remove();%>
</BODY>
</HTML>
