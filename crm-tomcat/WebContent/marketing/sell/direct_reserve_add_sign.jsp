<%@ page contentType="text/html; charset=GBK" import="enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.marketing.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
String name="";
String cardId="";
String cardType="";
if(session.getAttribute("name")!=null){
    name = session.getAttribute("name").toString().trim();
}
if(session.getAttribute("card_id")!=null){
    cardId = session.getAttribute("card_id").toString().trim();
}
if(session.getAttribute("card_type")!=null){
    cardType = session.getAttribute("card_type").toString().trim();
}
//����ҳ�����
input_bookCode = new Integer(1);//������ʱ����
boolean bSuccess = false;
//��ȡҳ�洫�ݱ���
Integer pre_product_id = Utility.parseInt(Utility.trimNull(request.getParameter("pre_product_id")),new Integer(0));
Integer customer_cust_id = Utility.parseInt(Utility.trimNull(request.getParameter("customer_cust_id")),new Integer(0));
Integer cc_cust_id = Utility.parseInt(Utility.trimNull(request.getParameter("cc_cust_id")),new Integer(0));
Integer cust_id = null;
if(cc_cust_id.intValue()>0){
	cust_id = cc_cust_id;
}
else{
	cust_id = customer_cust_id;
}
//��������
//--1.ԤԼ��Ϣ

BigDecimal pre_money = Utility.parseDecimal(request.getParameter("pre_money"),new BigDecimal(0));
Integer	link_man = Utility.parseInt(Utility.trimNull(request.getParameter("link_man")),input_operatorCode);
Integer	valid_days = Utility.parseInt(Utility.trimNull(request.getParameter("valid_days")),new Integer(0));
String	pre_type = Utility.trimNull(request.getParameter("pre_type"));
Integer	pre_num = Utility.parseInt(Utility.trimNull(request.getParameter("pre_num")),new Integer(1));
Integer preDate = Utility.parseInt(Utility.trimNull(request.getParameter("pre_date")),new Integer(0));
Integer expRegDate = Utility.parseInt(Utility.trimNull(request.getParameter("exp_reg_date")),new Integer(0));
String	summary = Utility.trimNull(request.getParameter("summary"));
String customer_cust_source = Utility.trimNull(request.getParameter("customer_cust_source"));//�ͻ���Դ
String channel_type = Utility.trimNull(request.getParameter("channel_type"));
BigDecimal channel_fare = Utility.parseBigDecimal(Utility.stringToDouble(Utility.trimNull(request.getParameter("channel_fare"))), null);
String per_code = Utility.trimNull(request.getParameter("per_code"));
String product_name = Utility.trimNull(request.getParameter("product_name"));
BigDecimal huge_money = Utility.parseBigDecimal(Utility.stringToDouble(Utility.trimNull(request.getParameter("huge_money"))), new BigDecimal(0));
String card_type = Utility.trimNull(request.getParameter("card_type"));
String card_type1 = Utility.trimNull(request.getParameter("card_type1"));
String pre_level = Utility.trimNull(request.getParameter("pre_level"));
//--2.�ͻ���Ϣ
String cust_no = "";
String cust_name = "";
Integer cust_type = new Integer(0);
String cust_type_name = "";
Integer service_man= new Integer(0);
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
String smsContent = "";
//��ȡ����
PreContractCrmLocal preContract = EJBFactory.getPreContractCrm(); 
Customer_INSTLocal  customer_inst = EJBFactory.getCustomer_INST();
CustomerLocal customer = EJBFactory.getCustomer();//�ͻ�
PreContractCrmVO pre_vo = new PreContractCrmVO();
CustomerVO c_vo = new CustomerVO();
CustomerVO cust_vo = new CustomerVO();

//�ͻ���Ϣ��ϸ
if(cust_id.intValue()>0){
	List rsList_cust = null;
	Map map_cust = null;	
	//�ͻ�����ֵ		
	c_vo.setCust_id(cust_id);
	//c_vo.setInput_man(input_operatorCode);
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
		card_type = Utility.trimNull(map_cust.get("CARD_TYPE"));
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

	if(cust_id.intValue() == 0){
		//�����ͻ���Ϣ
		String customer_cust_name = Utility.trimNull(request.getParameter("customer_cust_name"));
		Integer is_deal_picker = Utility.parseInt(Utility.trimNull(request.getParameter("is_deal_picker")), new Integer(0));
		cust_tel = Utility.trimNull(request.getParameter("cust_tel"));
		card_type = Utility.trimNull(request.getParameter("card_type"));
		if("".equals(card_type) || card_type.equals("0"))
			card_type=  "110801";//Ĭ�����֤
		String card_ids = Utility.trimNull(request.getParameter("card_id"));
		if("".equals(card_ids))
			card_ids = " ";//Ĭ�Ͽ�ֵ
		c_vo.setCust_name(customer_cust_name);
		c_vo.setCust_type(is_deal_picker);
		c_vo.setCust_tel(cust_tel);
		c_vo.setCard_type(is_deal_picker.intValue() == 1 ? card_type : card_type1);
		c_vo.setCard_id(card_ids);
		c_vo.setInput_man(input_operatorCode);
		c_vo.setService_man(link_man);
		c_vo.setComplete_flag(new Integer(2)); //�ͻ���Ϣ������
		c_vo.setPrint_deploy_bill(new Integer(1));//Ĭ�ϴ�ӡ���˵�
		c_vo.setPrint_post_info(new Integer(1));//
		cust_id =  customer.append(c_vo);
	
		if(cust_id.intValue()>0){
			List rsList_cust = null;
			Map map_cust = null;	
			//�ͻ�����ֵ		
			cust_vo.setCust_id(cust_id);
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
				card_type = Utility.trimNull(map_cust.get("CARD_TYPE"));
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
	}

	//�ͻ���Ϣ���ݴ��
	c_vo.setCust_id(cust_id);
	c_vo.setCust_no(cust_no);
	c_vo.setCust_name(cust_name);
	c_vo.setCust_type(cust_type);
	c_vo.setCard_id(h_card_id);
	if(!card_type.toString().equals(""))
		c_vo.setCard_type(card_type.toString());
	else
		c_vo.setCard_type(card_type1);	
	c_vo.setLegal_man(legal_man);
	c_vo.setContact_man(contact_man);
	c_vo.setPost_address(post_address);
	c_vo.setPost_code(post_code);
	c_vo.setMobile(h_mobile);
	c_vo.setE_mail(h_e_mail);
	c_vo.setService_man(service_man);
	c_vo.setInput_man(input_operatorCode);
	//ͬ���ͻ���Ϣ
	customer_inst.cope_customers(c_vo);
	//ԤԼ��Ϣ���
	pre_vo.setPre_product_id(pre_product_id);
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
	pre_vo.setChannel_type(channel_type);
	pre_vo.setChannel_fare(channel_fare);
	pre_vo.setPre_level(pre_level);

	//����ԤԼ��Ϣ	 
	preContract.append(pre_vo);
	//��������-ԤԼ�����ڲ�Ʒ�����ԤԼ���ʱ��������
	//if(pre_money.compareTo(huge_money) >= 1){
		//�ͷ����ܽ�ɫ
	 //	int role_id = Argument.getSyscontrolValue("AROLE98");
		//if(role_id != 0){
			//��ɫ�µ�Ա��ѭ�����Ͷ���
			//Map map = new HashMap();
			//List list = Argument.getOperatorListByRoleId(new Integer(role_id));
			//if(list != null && list.size() != 0){
				//for(int j=0; j<list.size(); j++){
					//map = (Map)list.get(j);
					//smsContent = Utility.trimNull(map.get("OP_NAME")) + "���ã��ͻ���" + cust_name + "�Ѿ�ԤԼ�ɹ�����Ʒ���ƣ�"+product_name+"����Ʒ���ԤԼ��"+Format.formatMoney(huge_money)+"��ԤԼ��" + Format.formatMoney(pre_money) + "Ԫ����ϵ�绰��" +cust_tel+"�������Թ�ע��";
					
					//if(!"".equals(Utility.trimNull(map.get("MOBILE"))) && Utility.trimNull(map.get("MOBILE")).length() == 11){
						//Argument.sendSMSSimple(input_operatorCode+"",Utility.trimNull(map.get("MOBILE")),smsContent,new Integer(1),"����",new Integer(0),input_operatorCode);
					//}
				//}
			//}
		//}
	//}

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
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/ccService.js'></script>	
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>

<script language=javascript>

/*���湦��*/
function SaveAction(){		
	if(document.theform.onsubmit()){
		disableAllBtn(true);
		document.theform.action="direct_reserve_add_sign.jsp";
		document.theform.method="post";
		document.theform.submit();
	}
}
/*����*/
function CancelAction(){
	history.back();
}
/*�����������ͻ�ʱ�õ��ĺ���*/
function validateForm(form){
	if((form.customer_cust_id.value == "" || form.customer_cust_id.value == 0)){
		if(!sl_check(form.customer_cust_name, "<%=LocalUtilis.language("class.customerName",clientLocale)%> ", 100, 1))	return false;//�ͻ�����
		if(!sl_checkChoice(document.theform.is_deal_picker,"<%=LocalUtilis.language("class.custType",clientLocale)%> ")){return false;}//�ͻ�����
		if(!sl_checkNum(form.cust_tel, "��ϵ�绰", 20, 1))	return false;//��ϵ�绰
	}
	if(!sl_checkChoice(form.pre_product_id, "<%=LocalUtilis.language("class.product",clientLocale)%> "))return false;//��Ʒ
	if(!sl_checkDecimal(form.pre_money, "<%=LocalUtilis.language("class.factPreNum2",clientLocale)%> ", 13, 3, 1))return false;//ԤԼ���
	if(!sl_checkDecimal(form.input_info3, "<%=LocalUtilis.language("class.factPreNum2",clientLocale)%> ", 13, 3, 0))return false;
	if(form.pre_money.value - form.input_info3.value > 0){
		sl_alert("ԤԼ���ܴ��ڵ�ǰ��ԤԼ���!");
		return false;
	}
	if(form.pre_money.value < 3000000 && (form.cust_type.value ==1 || form.is_deal_picker.value ==1)){
		if(parseInt(form.pre_num.value) > parseInt(form.input_info4.value)){
			sl_alert("С��ԤԼ�������ܴ��ڵ�ǰ��ԤԼ������");
			return null;
		}
	}
	if(!sl_checkChoice(form.pre_num, "<%=LocalUtilis.language("class.preNum2",clientLocale)%> "))	return false;//ԤԼ����
	if(!sl_checkChoice(form.pre_type, "<%=LocalUtilis.language("class.preType",clientLocale)%> "))	return false;//ԤԼ��ʽ
	//if(!sl_checkChoice(form.customer_cust_source, "<%=LocalUtilis.language("class.customerSource",clientLocale)%> "))	return false;//�ͻ���Դ
	if(!sl_checkDecimal(document.theform.channel_fare, "��������", 13, 3, 0))return false;
	if(document.theform.channel_fare.value != "")
		if(!sl_checkChoice(form.channel_type, "�������"))	return false;
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
	if(form.exp_reg_date.value > form.pre_end_date.value){
		sl_alert("Ԥ���Ϲ����ڲ�������ԤԼ��ֹ����!");
		return false;
	}

	//��������ԤԼ������
	var pre_salemoney = document.getElementById("pre_salemoney").value;
	var quotamoney = document.getElementById("quotamoney").value;
	var pre_qualifiednum = document.getElementById("pre_qualifiednum").value;
	var quota_qualified_num = document.getElementById("quota_qualified_num").value;
	var pre_money = document.getElementById("pre_money").value;
	var current_money = document.getElementById("current_money").value;
	var current_num = document.getElementById("current_num").value;


	//if(form.channel_type.value.substring(0,6) != "550003")
		//document.theform.channel_fare.value = "";
	var choose = document.getElementById("pre_product_id");
	document.getElementById("product_name").value = choose.options[choose.selectedIndex].text;
	return sl_check_update();
}
/*ˢ��ҳ��*/
function refreshPage(){
	disableAllBtn(true);
	syncDatePicker(document.theform.pre_date_picker, document.theform.pre_date);
	document.theform.action="direct_reserve_add.jsp";
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
/*��ʾ����*/
function showCnMoney(value){
	temp = value;
	if (trim(value) == "")
		pre_money_cn.innerText = " (<%=LocalUtilis.language("message.10000",clientLocale)%> )";//��
	else
		pre_money_cn.innerText = "(" + numToChinese(temp) + ")";
}

function showCnMoney1(value,name)
{
	if (trim(value) == "")
		name.innerText = "";
	else
		name.innerText = "(" + numToChinese(value) + ")";
}

/*ҳ���ʼ��*/
window.onload = function(){
   
   queryCustInfo();
	var v_bSuccess = document.getElementById("bSuccess").value;
	if(v_bSuccess=="true"){		
		window.returnValue= 1;
		window.close();
	}else{
		if(document.theform.pre_product_id.value > 0){
			showPreinfo(document.theform.pre_product_id.value);
		}
	}
}
/*��ȡ��ԤԼ��Ŀ ��ԤԼ���*/
function showPreinfo(pre_product_id){
	//��Ʒ��Ϣ
	utilityService.getPreInfoByPreId(pre_product_id,0,{callback: function(data){
		document.theform.product_total_money.value = data[0];	
		document.theform.pre_micro_num.value = data[1];
		document.theform.pre_total_num.value = data[1]+" / "+data[2];
		document.theform.pre_total_money.value = data[2];
		document.theform.pre_end_date.value = data[3];	
		document.theform.pre_total_money.value = data[4];
		document.theform.product_sy_money.value = data[5];
		document.theform.huge_money.value = data[6];
	}});
	//�����Ϣ
	getTempMountByProductIdAndServerMan();
}

/*���ݲ�ƷID��ԤԼ�Ǽ��˻�ȡ���Ŷӵ������Ϣ*/
function getTempMountByProductIdAndServerMan(){
	var pre_product_id = document.theform.pre_product_id.value;
	var link_man = document.theform.link_man.value;
	if(pre_product_id != 0 && link_man != ""){
		utilityService.getTempMountByProductIdAndServerMan(pre_product_id,link_man,{callback: function(data){
			var num1 = data.PRE_SALEMONEY_STR;
			if(num1 == "")
				num1 = "0.00";
			var num2 = data.QUOTAMONEY_STR;
			if(num2 == "")
				num2 = "0.00";
			document.getElementById("input_info1").value = num1+"/"+num2;
			document.getElementById("input_info2").value = data.PRE_QUALIFIEDNUM+"/"+data.QUOTA_QUALIFIED_NUM;
			document.getElementById("input_info3").value = data.CURRENT_MONEY_STR;
			document.getElementById("input_info4").value = data.CURRENT_NUM;
		
			document.getElementById("pre_salemoney").value = data.PRE_SALEMONEY;
			document.getElementById("quotamoney").value = data.QUOTAMONEY;
			document.getElementById("pre_qualifiednum").value = data.PRE_QUALIFIEDNUM;
			document.getElementById("quota_qualified_num").value = data.QUOTA_QUALIFIED_NUM;
			document.getElementById("current_money").value = data.CURRENT_MONEY;
			document.getElementById("current_num").value = data.CURRENT_NUM;
		}});
	}
}

//�����������
function setChannelType(obj){
	var channel_type = obj.value;
	if(channel_type != ""){
		if(channel_type.substring(0,6) == "550003"){
			document.getElementById("channel_td1").style.display = "";
			document.getElementById("channel_td2").style.display = "";
		}else{
			document.getElementById("channel_td1").style.display = "none";
			document.getElementById("channel_td2").style.display = "none";
		}
	}
}

//��ѯ�ͻ���Ϣ
function queryCustInfo(){
	var query_cust_name = document.theform.query_cust_name.value;
	var query_card_id = document.theform.query_card_id.value;
	var query_cust_tel_val = document.theform.query_cust_tel_val.value;
	var input_operatorCode = document.theform.input_operatorCode.value;
	if(query_cust_name == "" && query_card_id == "" && query_cust_tel_val == ""){
		sl_alert("�������ѯ�������ͻ����ơ�֤�����������ϵ�绰��");
		return;
	}
	utilityService.getCustomerBy(query_cust_name,query_card_id,input_operatorCode,query_cust_tel_val,matchCustCallBack);
}

function matchCustCallBack(data){
	var obj_op = document.getElementById("query_result");
    DWRUtil.removeAllOptions(obj_op);   
    DWRUtil.addOptions(obj_op,{'':'<%=LocalUtilis.language("message.select2",clientLocale)%> '});   //��ѡ��
    DWRUtil.addOptions(obj_op,data);
	loadInfo("<%=name%>");
}

//��λ������ͻ�
function loadInfo(cust_name) {
	var objSelect = document.getElementById("query_result");
	if (objSelect.options.length == 2) { //ֻ��1���ͻ�ʱ���Զ���λ
		for (var i = 0; i < objSelect.options.length; i++) {        
	        if (objSelect.options[i].text == cust_name) {        
	            objSelect.options[i].selected = true;
	            break;        
	        }        
	    }
	}
	queryCustomerinfo(objSelect);
}

//�ͻ���ϸ��Ϣ
function queryCustomerinfo(obj){
	if(obj.value != ""){
		ccService.getCustInfoById(obj.value,refreshCustInfoCallBack);
	}else{
		document.theform.customer_cust_name.style.display = "";
		document.theform.is_deal_picker.style.display = "";
		document.theform.cust_tel.style.display = "";
		document.theform.card_type.style.display = "";
		document.theform.card_id.style.display = "";
         //�ֳ�ǩԼ��ӿ�ʼ
        document.theform.customer_cust_name.value = "<%=name%>";
		document.theform.card_type.value = "<%=cardType%>";
		document.theform.card_id.value = "<%=cardId%>";
      //�ֳ�ǩԼ��ӽ���
     
		document.theform.input_value1.style.display = "none";
		document.theform.input_value2.style.display = "none";
		document.theform.input_value3.style.display = "none";
		document.theform.input_value4.style.display = "none";
		document.theform.input_value5.style.display = "none";
       	//document.theform.input_value11.style.display = "none";
        //document.theform.input_value12.style.display = "none";
		//document.getElementById("tel_tr_id").style.display = "none";
		document.getElementById("service_man").style.display = "none";
		document.getElementById("service_man1").style.display = "none";
		document.theform.input_value1.value = "";
		document.theform.input_value2.value = "";
		document.theform.input_value3.value = "";
		document.theform.input_value4.value = "";
		document.theform.input_value5.value = "";
		document.theform.input_value6.value = "";
       	//document.theform.input_value11.value = "";
		//document.theform.input_value12.value = "";
      	//document.theform.input_value13.value = "";
		//document.theform.input_value14.value = "";
		document.theform.customer_cust_id.value = "";
		document.getElementById("span_id1").innerText = "*��ϵ�绰:";
	}
}

function refreshCustInfoCallBack(data){
	var custJsonStr = data;
	custJson = eval(custJsonStr);
	if(custJson.length>0){
		document.theform.customer_cust_name.style.display = "none";
		document.theform.is_deal_picker.style.display = "none";
		document.theform.cust_tel.style.display = "none";
		document.theform.card_type.style.display = "none";
		document.theform.card_id.style.display = "none";
        //document.theform.query_cust_tel.style.display = "none";
       	//document.theform.mobile.style.display = "none";
		document.theform.input_value1.style.display = "";
		document.theform.input_value2.style.display = "";
		document.theform.input_value3.style.display = "";
		document.theform.input_value4.style.display = "";
		document.theform.input_value5.style.display = "";
        //document.theform.input_value11.style.display = "";
        //document.theform.input_value12.style.display = "";
		//document.getElementById("tel_tr_id").style.display = "";
		document.getElementById("service_man").style.display = "";
		document.getElementById("service_man1").style.display = "";
		document.theform.input_value1.value = custJson[0].CUST_NAME;
		document.theform.input_value2.value = custJson[0].CUST_TYPE_NAME;
		document.theform.input_value3.value = custJson[0].CUST_SOURCE_NAME;
		document.theform.input_value4.value = custJson[0].CARD_TYPE_NAME;
		document.theform.input_value5.value = custJson[0].CARD_ID;
		document.theform.input_value6.value = custJson[0].SERVICE_MAN_NAME;
       // document.theform.input_value11.value = custJson[0].H_TEL;
       // document.theform.input_value12.value = custJson[0].MOBILE;
       // document.theform.input_value13.value = custJson[0].O_TEL;
       // document.theform.input_value14.value = custJson[0].BP;
		document.theform.customer_cust_id.value = custJson[0].CUST_ID;
		document.theform.cust_type.value = custJson[0].CUST_TYPE;
		document.getElementById("span_id1").innerText = "*�ͻ���Դ:";
	}else{
		document.theform.customer_cust_name.style.display = "";
		document.theform.is_deal_picker.style.display = "";
		document.theform.cust_tel.style.display = "";
		document.theform.card_type.style.display = "";
		document.theform.card_id.style.display = "";
       // document.theform.query_cust_tel.style.display = "";
        //document.theform.mobile.style.display = "";
		document.theform.input_value1.style.display = "none";
		document.theform.input_value2.style.display = "none";
		document.theform.input_value3.style.display = "none";
		document.theform.input_value4.style.display = "none";
		document.theform.input_value5.style.display = "none";
        //document.theform.input_value11.style.display = "none";
       // document.theform.input_value12.style.display = "none";
		//document.getElementById("tel_tr_id").style.display = "none";
		document.getElementById("service_man").style.display = "none";
		document.getElementById("service_man1").style.display = "none";
		document.theform.input_value1.value = "";
		document.theform.input_value2.value = "";
		document.theform.input_value3.value = "";
		document.theform.input_value4.value = "";
		document.theform.input_value5.value = "";
		document.theform.input_value6.value = "";
        //document.theform.input_value11.value = "";
        //document.theform.input_value12.value = "";
       // document.theform.input_value13.value = "";
        //document.theform.input_value14.value = "";
		document.theform.customer_cust_id.value = "";
		document.getElementById("span_id1").innerText = "*��ϵ�绰:";
	}
}
//��ʾ֤������
function changeType(value){
	if(value == 1){
		document.getElementById("card_type").style.display = "";
		document.getElementById("card_type1").style.display ="none";
	}else{
		document.getElementById("card_type").style.display = "none";
		document.getElementById("card_type1").style.display = "";
	}

}

</script>
</HEAD>
<BODY class="BODY">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" onsubmit="javascript: return validateForm(this);">
<input type="hidden" id="bSuccess" value="<%=bSuccess%>"/><!--�����ɹ���־-->
<input type="hidden" name="customer_cust_id" value="<%=cust_id%>"/>
<input type="hidden" name="cust_type" value="<%=cust_type%>"/>
<input type="hidden" name="pre_micro_num" value="">

<input type="hidden" name="pre_salemoney" id="pre_salemoney" value="">
<input type="hidden" name="quotamoney" id="quotamoney" value="">
<input type="hidden" name="pre_qualifiednum" id="pre_qualifiednum" value="">
<input type="hidden" name="quota_qualified_num" id="quota_qualified_num" value="">
<input type="hidden" name="current_money" id="current_money" value="">
<input type="hidden" name="current_num" id="current_num" value="">
<input type="hidden" name="huge_money" id="huge_money" value="">
<input type="hidden" name="product_name" id="product_name" value="">
<input type="hidden" name="input_operatorCode" id="input_operatorCode" value="<%=input_operatorCode%>">
<div align="left">
	<img border="0" src="<%=request.getContextPath()%>/images/ico_area.gif" align="absbottom" width="32" height="28">
	<font color="#215dc6"><b><%=LocalUtilis.language("message.salesManagerment",clientLocale)%>>><%=LocalUtilis.language("menu.bespeak",clientLocale)%> </b></font><!--���۹���>>ԤԼ-->
	<hr noshade color="#808080" size="1">
</div>
<!--�ͻ�ѡ��-->
<div vAlign=top align=left>
<fieldset>
<legend><b style="color: blue;">�ͻ���Ϣ</b></legend>
<table cellSpacing=0 cellPadding=3 width="100%" border=0>
<tr>
	<td align="center" colspan="4">
		<table  border="0" width="90%" cellspacing="4" cellpadding="0" style="border: 1px; border-style: dashed; border-color: red; margin-top:5px;">
			<tr>
				<td>
					<b>�ͻ���ѯ</b>&nbsp;&nbsp;&nbsp;&nbsp;
				</td>
				<td>
					�ͻ����ƣ�<input maxlength="100" name="query_cust_name" size="20" onkeydown="javascript:nextKeyPress(this);" value="<%=name%>" id="query_cust_name">&nbsp;&nbsp;&nbsp;&nbsp;
					֤�����룺<input maxlength="100" name="query_card_id" size="20" onkeydown="javascript:nextKeyPress(this);">&nbsp;&nbsp;&nbsp;&nbsp;
					��ϵ�绰��<input maxlength="100" name="query_cust_tel_val" size="20" onkeydown="javascript:nextKeyPress(this);">&nbsp;&nbsp;&nbsp;&nbsp;
					<button type="button"  class="xpbutton3" onclick="javascript:queryCustInfo();">��ѯ</button>&nbsp;&nbsp;&nbsp;&nbsp;
				</td>
			</tr>
			<tr>
				<td></td>	
				<td>
					��ѯ�����<select id="query_result" name="query_result"  onclick="javascript:queryCustomerinfo(this);"></select>
				</td>
			</tr>
		</table>
	</td>
</tr>
<tr>
	<td align="right" >*�ͻ�����:</td>
	<td>
		<input maxlength="100" name="customer_cust_name" size="27" onkeydown="javascript:nextKeyPress(this);" value="<%=cust_name %>">
		<input maxlength="100" readonly="readonly" class="edline" name="input_value1" size="27" style="display: none;">
	</td>
	<td align="right" style="display: none;" id="service_man"><%=LocalUtilis.language("class.accountManager",clientLocale)%>:</td><!--�ͻ�����--> 
	<td style="display: none;" id="service_man1">
		<input maxlength="100" readonly="readonly" class="edline" name="input_value6" size="27">
	</td>
</tr>
<tr>
	<td align="right" width="20%">�ͻ�����:</td>
	<td width="25%">
		<select name="is_deal_picker" id="is_deal_picker" onkeydown="javascript:nextKeyPress(this)" style="width:160px" onchange="javascript:changeType(this.value);">	
			<option value="0">��ѡ��</option>
			<option value="1" <%=cust_type.intValue() == 1 ? "selected" : ""%>>����</option>
			<option value="2" <%=cust_type.intValue() == 2 ? "selected" : ""%>>����</option>
		</select>
		<input maxlength="100" readonly="readonly" class="edline" name="input_value2" size="27" style="display: none;">
	</td>
	<td align="right" width="20%"><span id="span_id1">��ϵ�绰:</span></td>
	<td width="25%">
		<input maxlength="100" name="cust_tel" size="27" onkeydown="javascript:nextKeyPress(this);" value="<%=cust_tel %>">
		<input maxlength="100" readonly="readonly" class="edline" name="input_value3" size="27" style="display: none;">
	</td>
</tr>
<tr>
	<td align="right">֤������:</td>
	<td >
		<select onkeydown="javascript:nextKeyPress(this)" id ="card_type" name="card_type" style="WIDTH: 160px">
			<%=Argument.getCardTypeOptions2(cust_type+"")%>
		</select>
		<select onkeydown="javascript:nextKeyPress(this)" id ="card_type1"name="card_type1" style="WIDTH: 160px;display:none" >
			<%=Argument.getCardTypeJgOptions2(card_type)%>
		</select>	
		<input maxlength="100" readonly="readonly" class="edline" name="input_value4" size="27" style="display: none;">
	</td>
	<td align="right">֤������:</td>
	<td >
		<input maxlength="100" name="card_id" size="27" onkeydown="javascript:nextKeyPress(this);" value="<%=card_id %>">
		<input maxlength="100" readonly="readonly" class="edline "name="input_value5" size="27" style="display: none;">
	</td>
</tr>
<!--  
	<tr>
		<td align="right">��ͥ�绰:</td>
		<td >
	        <input maxlength="100" name="query_cust_tel" size="27" onkeydown="javascript:nextKeyPress(this);" value="">
			<input maxlength="100" readonly="readonly" class="edline" name="input_value11" size="27" style="display: none;">
		</td>
		<td align="right">�ֻ�����1:</td>
		<td >
	        <input maxlength="100" name="mobile" size="27" onkeydown="javascript:nextKeyPress(this);" value="">
			<input maxlength="100" readonly="readonly" class="edline" name="input_value12" size="27" style="display: none;">
		</td>
	</tr>
	<tr style="display: none;" id="tel_tr_id">
		<td align="right">��˾�绰:</td>
		<td >
			<input maxlength="100" readonly="readonly" class="edline" name="input_value13" size="27">
		</td>
		<td align="right">�ֻ�����2:</td>
		<td >
			<input maxlength="100" readonly="readonly" class="edline" name="input_value14" size="27">
		</td>
	</tr>
-->
</table>
</fieldset>
<fieldset>
<legend><b style="color: blue;">��Ʒ��Ϣ</b></legend>
<table cellSpacing=0 cellPadding=3 width="100%" border=0>
		<tr>
			<td align="right"><%=LocalUtilis.language("class.productName",clientLocale)%> :</td><!--��Ʒ����-->
			<td colspan="3">
				<select size="1" id="pre_product_id" name="pre_product_id" onkeydown="javascript:nextKeyPress(this)" class="productname" onChange="javascript:showPreinfo(this.value);">
					<%=Argument.getPreProductListOptions(pre_product_id,"","110202",input_operatorCode)%>
				</select>
			</td>
 			
		</tr>
		<tr>
			<td align="right" width="20%">��Ʒ�ܹ�ģ :</td>
			<td width="25%"><input readonly class="edline" name="product_total_money" size="27" value=""></td>
			<td align="right" width="20%">��ֹ���� :</td>
			<td width="25%"><input readonly class="edline" name="pre_end_date"  size="27" value=""></td>
		</tr>
		<tr style="display: none;">
			<td align="right">С�����/�ۼƷ��� :</td>
			<td><input readonly class="edline" name="pre_total_num"  size="27" value=""></td>
			<td align="right">��ֹ���� :</td>
			<td><input readonly class="edline" name="pre_end_date1"  size="27" value=""></td>
		</tr>	
		<tr style="display: none;">
			<td align="right">��ԤԼ��ģ :</td>
			<td><input readonly class="edline" name="pre_total_money" size="27" value=""/></td>
			<td align="right">��ԤԼ��ģ </td>
			<td><input readonly class="edline" name="product_sy_money" size="27" value=""/></td>
		</tr>
		
		<tr>
			<td align="right">��ԤԼ���/�Ŷӽ�����:</td>
			<td><input readonly class="edline" name="input_info1" id="input_info1" size="35" value=""/></td>
			<td align="right">��ԤԼС�����/�ϸ�Ͷ�����������:</td>
			<td><input readonly class="edline" name="input_info2" id="input_info2" size="27" value=""/></td>
		</tr>
		<tr>
			<td align="right">��ǰ��ԤԼ���:</td>
			<td><input readonly class="edline" name="input_info3" id="input_info3" size="27" value=""/></td>
			<td align="right">��ǰ��С��ԤԼ����:</td>
			<td><input readonly class="edline" name="input_info4" id="input_info4" size="27" value=""/></td>
		</tr>
</TABLE>
</fieldset>
<fieldset>
<legend><b style="color: blue;">ԤԼ��Ϣ</b></legend>
<table cellSpacing=0 cellPadding=3 width="100%" border=0>
		<tr>
			<td align="right" width="20%">*<%=LocalUtilis.language("class.factPreNum2",clientLocale)%> :</td><!--ԤԼ���-->
			<td width="25%"><input name="pre_money" id="pre_money" size="27" value="" onkeydown="javascript:nextKeyPress(this)" onkeyup="javascript:showCnMoney(this.value)"><span id="pre_money_cn" class="span">&nbsp;</span></td>
<!--��-->
			<td align="right" width="20%">*<%=LocalUtilis.language("class.preNum2",clientLocale)%> :</td><!--ԤԼ����-->
			<td width="25%"><input readonly class="edline"  name="pre_num" onkeydown="javascript:nextKeyPress(this)" size="27" value="<%=pre_num%>"/></td>
			<!-- <td>
				<select name="pre_num" >
					<%=Argument.getPreNumOptions(pre_num)%>
				</select>
			 -->
			</td>
		</tr>
		<tr>
			<td align="right">*<%=LocalUtilis.language("class.preType",clientLocale)%> :</td><!--ԤԼ��ʽ-->
			<td>
				<select size="1" name="pre_type" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 160px">
					<%=Argument.getPreTypeOptions(pre_type)%>
				</select>
			</td>
			<td align="right">*ԤԼ�Ǽ���:</td><!--ԤԼ�Ǽ���-->
			<td ><select size="1" name="link_man" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 160px" onclick="javascript:getTempMountByProductIdAndServerMan();">
				<%=Argument.getManager_Value(link_man)%>
			</select></td>
		</tr>
		<tr>
			<td align="right"><%=LocalUtilis.language("class.preDate",clientLocale)%> :</td><!--ԤԼ����-->
			<td><INPUT TYPE="text" NAME="pre_date_picker" class=selecttext size="23"
			<%if(preDate==null||preDate.intValue()==0){%> value="<%=Format.formatDateLine(Utility.getCurrentDate())%>"
			<%}else{%>value="<%=Format.formatDateLine(preDate)%>"<%}%> >
			<INPUT TYPE="button" value="��" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.pre_date_picker,theform.pre_date_picker.value,this);" tabindex="13">
			<INPUT TYPE="hidden" NAME="pre_date"   value=""></td>
			<td align="right">Ԥ���Ϲ�����:</td><!--Ԥ���Ϲ�����-->
			<td>
				<INPUT TYPE="text" NAME="exp_reg_date_picker" class=selecttext size="23"
				<%if(expRegDate==null||expRegDate.intValue()==0){%> value="<%=Format.formatDateLine(Utility.getCurrentDate())%>"
				<%}else{%>value="<%=Format.formatDateLine(expRegDate)%>"<%}%> >
				<INPUT TYPE="button" value="��" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.exp_reg_date_picker,theform.exp_reg_date_picker.value,this);" tabindex="13">
				<INPUT TYPE="hidden" NAME="exp_reg_date" value="">
			</td>
			
		</tr>
		<tr>
			<td align="right">�������:</td>
			<td>
				<select size="1" name="channel_type" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 160px">
					<%=Argument.getDictParamOptions(5500, channel_type)%>
				</select>
			</td>
			<td align="right" id="channel_td1">��������:</td>
			<td id="channel_td2">
				<input type="text" name="channel_fare" value="<%=Format.formatMoney(channel_fare)%>" size="20" onkeydown="javascript:nextKeyPress(this)" onkeyup="javascript:showCnMoney1(this.value,channel_fare_cn)"><span id="channel_fare_cn" class="span">&nbsp;</span>
			</td>
		</tr>
		<tr>
			<td align="right">ԤԼ����:</td><!--ԤԼ����-->
			<td>
				<select size="1" name="pre_level" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 160px">
					<%=Argument.getDictParamOptions(1182,pre_level)%>
				</select>
			</td>
			<td align="right">&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td align="right"><%=LocalUtilis.language("class.customerSummary",clientLocale)%> :</td><!--��ע-->
			<td colspan="3"><textarea rows="3" name="summary" onkeydown="javascript:nextKeyPress(this)" cols="80"><%=summary%></textarea></td>
		</tr>
</table>
</fieldset>
</div>
<br>
<div align="right" style="margin-right:5px">
	<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:SaveAction();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;<!--����-->
	<button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.close();"><%=LocalUtilis.language("message.back",clientLocale)%> (<u>C</u>)</button>
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
%>