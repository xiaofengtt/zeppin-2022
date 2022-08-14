<%@ page contentType="text/html; charset=GBK" import="enfo.crm.customer.*,enfo.crm.service.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<jsp:directive.page import="com.enfo.webservice.client.SAPClient"/>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//获取页面传递变量
Integer cust_id = Utility.parseInt(Utility.trimNull(request.getParameter("cust_id")), new Integer(0)); 
Integer cust_type = Utility.parseInt(Utility.trimNull(request.getParameter("customer_cust_type")), new Integer(1));
int action_flag = Utility.parseInt(Utility.trimNull(request.getParameter("action_flag")),0);//操作标记：0.无操作 1.新增操作 2.修改操作
int pos_flag = Utility.parseInt(Utility.trimNull(request.getParameter("pos_flag")), 0); //2为证件自动读卡状态

//辅助变量
input_bookCode = new Integer(1);//帐套暂时设置
boolean bSuccess = false;
String sReadonly = "";
String sDisabled= "";
String sPrint_deploy_bill = "";
String sPrint_post_info = "";
int deploy_bill = 0;
int post_info = 0;
Map map_single = null;
List rsList_single = null;//返回结果集
Integer all_flag = new Integer(0);
//获取对象
Customer_INSTLocal customer_inst = EJBFactory.getCustomer_INST();//同步用客户对象
CustomerLocal customer = EJBFactory.getCustomer();
CustomerVO vo = new CustomerVO();

//声明单一客户信息变量
String cust_type_name ="";
String cust_name="";
String cust_no="";
String cust_source="111005";
String cust_source_name="";
String vip_card_id="";
String hgtzr_bh="";
Integer vip_date= new Integer(0);
Integer service_man= input_operatorCode;//new Integer(0);客户经理默认改成当前操作员
String card_type_name="";
Integer card_type = new Integer(110801);
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
String touch_type="110905";
String touch_type_name="";
Integer is_link = new Integer(0);
Integer true_flag = new Integer(1);
String summary="";
String voc_type="";
String voc_type_name="";
String sex_name="";
Integer print_deploy_bill = new Integer(0);
Integer print_post_info = new Integer(0);
Integer is_deal = new Integer(1); //该客户能否进行交易，1.可交易 事实客户；2 不可交易，潜在客户；由用户设定
String country = "9997CHN";//国别 默认中国
String money_source = "";//资金来源
String money_source_name = "";
Integer card_valid_date = new Integer(0);
String recommended = "";
String jg_type = "";
String jg_wtrlx2 = "";

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
Integer cust_flag = new Integer(0);

if(user_id.intValue() ==2){
	sPrint_deploy_bill = sPrint_deploy_bill = "checked";//新建默认选择打印对账单
	sPrint_post_info = sPrint_post_info = "checked";//新建默认选择打印披露信息
	deploy_bill =1;
	post_info = 1;

}
//如果有操作
if(action_flag>0&&request.getMethod().equals("POST")){
	CustomerVO s_vo = new CustomerVO();

	Integer r_cust_type = Utility.parseInt(Utility.trimNull(request.getParameter("customer_cust_type")), new Integer(0));
	String r_cust_name = Utility.trimNull(request.getParameter("customer_cust_name"));
	Integer r_cust_voc_type = Utility.parseInt(Utility.trimNull(request.getParameter("customer_zyhy_type")), new Integer(0));
	String r_cust_no = Utility.trimNull(request.getParameter("customer_cust_no"));
	Integer r_cust_source = Utility.parseInt(Utility.trimNull(request.getParameter("customer_cust_source")), new Integer(0));
	String r_vip_card_id = Utility.trimNull(request.getParameter("customer_vip_card_id"));
	String r_hgtzr_bh = Utility.trimNull(request.getParameter("customer_hgtzr_bh"));
	Integer r_vip_date = Utility.parseInt(Utility.trimNull(request.getParameter("customer_vip_date")), null);
	Integer r_service_man = Utility.parseInt(Utility.trimNull(request.getParameter("customer_service_man")), null);
	Integer r_card_type = Utility.parseInt(Utility.trimNull(request.getParameter("customer_card_type")), null);
	String r_card_id = Utility.trimNull(request.getParameter("customer_card_id"));
	Integer r_card_valid_date = Utility.parseInt(Utility.trimNull(request.getParameter("customer_card_valid_date")), null);
	String r_legal_man = Utility.trimNull(request.getParameter("customer_legal_man"));
	String r_contact_man = Utility.trimNull(request.getParameter("customer_contact_man"));
	Integer r_birthday = Utility.parseInt(Utility.trimNull(request.getParameter("customer_birthday")), null);
	Integer r_age= Utility.parseInt(Utility.trimNull(request.getParameter("customer_age")), null);
	Integer r_sex= Utility.parseInt(Utility.trimNull(request.getParameter("customer_sex")), null);
	String r_h_tel = Utility.trimNull(request.getParameter("customer_h_tel"));
	String r_o_tel = Utility.trimNull(request.getParameter("customer_o_tel"));
	String r_mobile = Utility.trimNull(request.getParameter("customer_mobile"));
	String r_bp = Utility.trimNull(request.getParameter("customer_bp"));
	String r_fax = Utility.trimNull(request.getParameter("customer_fax"));
	String r_e_mail = Utility.trimNull(request.getParameter("customer_e_mail"));
	String r_post_code= Utility.trimNull(request.getParameter("customer_post_code"));
	String r_post_address= Utility.trimNull(request.getParameter("customer_post_address"));
	String r_post_code2= Utility.trimNull(request.getParameter("customer_post_code2"));
	String r_post_address2= Utility.trimNull(request.getParameter("customer_post_address2"));
	Integer r_touch_type = Utility.parseInt(Utility.trimNull(request.getParameter("customer_touch_type")),  new Integer(0));
	Integer r_is_link = Utility.parseInt(Utility.trimNull(request.getParameter("customer_is_link")),  new Integer(0));
	String r_summary = Utility.trimNull(request.getParameter("customer_summary"));
	Integer r_print_deploy_bill = Utility.parseInt(Utility.trimNull(request.getParameter("customer_print_deploy_bill")),  new Integer(0));
	Integer r_print_post_info = Utility.parseInt(Utility.trimNull(request.getParameter("customer_print_post_info")),  new Integer(0));
	Integer r_is_deal = Utility.parseInt(Utility.trimNull(request.getParameter("is_deal")),new Integer(0)); //该客户能否进行交易，1.可交易 事实客户；2 不可交易，潜在客户；由用户设定
	String r_country = Utility.trimNull(request.getParameter("country"));
	String r_money_source = Utility.trimNull(request.getParameter("money_source"));
	String r_money_source_name = Utility.trimNull(request.getParameter("money_source_name"));
	String r_recommended = Utility.trimNull(request.getParameter("recommended"));
	jg_type = Utility.trimNull(request.getParameter("jg_type"));
	jg_wtrlx2 = Utility.trimNull(request.getParameter("jg_wtrlx2"));
	true_flag = Utility.parseInt(request.getParameter("true_flag"),  new Integer(1)); //

	cust_flag = Utility.parseInt(Utility.trimNull(request.getParameter("cust_flag")),new Integer(2));
	String temp = Utility.trimNull(true_flag) + Utility.trimNull(cust_flag);
	cust_flag = Utility.parseInt(temp,new Integer(11));

	if(!r_h_tel.equals(""))
		s_vo.setCust_tel(r_h_tel);
	else if(!r_o_tel.equals(""))
		s_vo.setCust_tel(r_o_tel);

	
	s_vo.setCust_type(r_cust_type);
	s_vo.setCust_name(r_cust_name);
	s_vo.setVoc_type(Utility.trimNull(r_cust_voc_type));
	s_vo.setCust_no(r_cust_no);
	s_vo.setCust_source(Utility.trimNull(r_cust_source));
	s_vo.setVip_card_id(r_vip_card_id);
	s_vo.setHgtzr_bh(r_hgtzr_bh);
	s_vo.setVip_date(r_vip_date);
	s_vo.setService_man(r_service_man);
	s_vo.setCard_type(Utility.trimNull(r_card_type));
	s_vo.setCard_id(r_card_id);
	s_vo.setCard_valid_date(r_card_valid_date);
	s_vo.setLegal_man(r_legal_man);
	s_vo.setContact_man(r_contact_man);
	s_vo.setBirthday(r_birthday);
	s_vo.setAge(r_age);
	s_vo.setSex(r_sex);
	s_vo.setH_tel(r_h_tel);
	s_vo.setO_tel(r_o_tel);
	s_vo.setMobile(r_mobile);
	s_vo.setBp(r_bp);
	s_vo.setFax(r_fax);
	s_vo.setE_mail(r_e_mail);
	s_vo.setPost_code(r_post_code);
	s_vo.setPost_address(r_post_address);
	s_vo.setPost_code2(r_post_code2);
	s_vo.setPost_address2(r_post_address2);
	s_vo.setTouch_type(Utility.trimNull(r_touch_type));
	s_vo.setIs_link(r_is_link);
	s_vo.setSummary(r_summary);
	s_vo.setPrint_deploy_bill(r_print_deploy_bill);
	s_vo.setPrint_post_info(r_print_post_info);
	s_vo.setInput_man(input_operatorCode);
	s_vo.setCountry(r_country);
	s_vo.setMoney_source(r_money_source);
	s_vo.setMoney_source_name(r_money_source_name);
	s_vo.setRecommended(r_recommended);
	s_vo.setEast_jg_type(jg_type);
	s_vo.setJg_wtrlx2(jg_wtrlx2);
	s_vo.setTrue_flag(true_flag);
	//action_flag:1-新增；2编辑；
	try {
		if(action_flag==1){
			if(r_is_deal.intValue()==1){
				cust_id = customer.append(s_vo);			
			}
			else if(r_is_deal.intValue()==2){
				cust_id = customer.append(s_vo);	//全部同步到业务系统。不然两边账号对应不上。
				
			}	
		}
		else if(action_flag==2){
			s_vo.setCust_id(cust_id);
			
			if(r_is_deal.intValue()==1){
				if (user_id.intValue()!=15) customer_inst.cope_customers(s_vo); //建信CRM的客户信息不同步给INTRUST
				customer.modify(s_vo);
			}
			else if(r_is_deal.intValue()==2){
				customer.modify2(s_vo);			
			}		
		}
	} catch (Exception e) {
		String message = enfo.crm.tools.LocalUtilis.language("message.saveFail", clientLocale); //保存失败
		out.println("<script type=\"text/javascript\">alert('"+message+","+e.getMessage()+"');</script>");
		out.println("<script type=\"text/javascript\">window.parent.theform.changeButton.click();</script>");
		bSuccess = false;
		return;
	}

	s_vo.setCust_id(cust_id); //
	s_vo.setBusiness_code(Utility.trimNull(request.getParameter("business_code")));
	s_vo.setOrgan_code(Utility.trimNull(request.getParameter("organ_code")));
	s_vo.setTax_reg_code(Utility.trimNull(request.getParameter("tax_reg_code")));
	s_vo.setLegal_name(Utility.trimNull(request.getParameter("legal_name")));
	s_vo.setLegal_card_id(Utility.trimNull(request.getParameter("legal_card_id")));
	s_vo.setAgent_name(Utility.trimNull(request.getParameter("agent_name")));
	s_vo.setAgent_card_id(Utility.trimNull(request.getParameter("agent_card_id")));
	s_vo.setPartner_card_id(Utility.trimNull(request.getParameter("partner_card_id")));
	s_vo.setManaging_scope(Utility.trimNull(request.getParameter("managing_scope")));
	s_vo.setLegal_card_type(Utility.trimNull(request.getParameter("legal_card_type")));
	s_vo.setLegal_card_valid_date(Utility.parseInt(request.getParameter("legal_card_valid_date"), new Integer(0)));
	s_vo.setLegal_tel(Utility.trimNull(request.getParameter("legal_tel")));
	s_vo.setAgent_card_type(Utility.trimNull(request.getParameter("agent_card_type")));
	s_vo.setAgent_card_valid_date(Utility.parseInt(request.getParameter("agent_card_valid_date"), new Integer(0)));
	s_vo.setAgent_tel(Utility.trimNull(request.getParameter("agent_tel")));
	try{
		customer.updateEntCustInfo(s_vo);
		action_flag = 0;
		bSuccess = true;
	} catch (Exception e) {
		String message = enfo.crm.tools.LocalUtilis.language("message.saveFail", clientLocale); //保存失败
		out.println("<script type=\"text/javascript\">alert('"+message+","+e.getMessage()+"');</script>");
	}

	
}

//选择单一客户信息 查询详细资料 SQL2005		
if(cust_id.intValue()>0){
	vo = new CustomerVO();
	vo.setCust_id(cust_id);
	vo.setInput_man(input_operatorCode);
	
	rsList_single = customer.listByControl(vo);
	
	if(rsList_single.size()>0){
		map_single = (Map)rsList_single.get(0);	
		cust_type_name = Utility.trimNull(map_single.get("CUST_TYPE_NAME"));	
		cust_type = Utility.parseInt(Utility.trimNull(map_single.get("CUST_TYPE")),cust_type);
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
		is_deal = Utility.parseInt(Utility.trimNull(map_single.get("IS_DEAL")),new Integer(0)); //该客户能否进行交易，1.可交易 事实客户；2 不可交易，潜在客户；由用户设定
		country = Utility.trimNull(map_single.get("COUNTRY"));
		money_source = Utility.trimNull(map_single.get("MONEY_SOURCE"));
		money_source_name = Utility.trimNull(map_single.get("MONEY_SOURCE_NAME"));
		card_valid_date = Utility.parseInt(Utility.trimNull(map_single.get("CARD_VALID_DATE")),new Integer(0));
		recommended = Utility.trimNull(map_single.get("RECOMMENDED"));
		jg_type = Utility.trimNull(map_single.get("EAST_JG_TYPE"));
		jg_wtrlx2 = Utility.trimNull(map_single.get("JG_WTRLX2"));
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

	if(print_deploy_bill.intValue()==1){
		sPrint_deploy_bill = "checked";
		deploy_bill =1;
	}else{
		sPrint_deploy_bill = "";
		deploy_bill =0;	
	}
	if(print_post_info.intValue() == 1){
		sPrint_post_info = "checked";
		post_info = 1;
	}else{
		sPrint_post_info = "";
		post_info = 0;
	}
				
}

if (request.getMethod().equals("POST")){
	//华澳客户同步
	if(is_deal.intValue()==1&&Argument.getSyscontrolValue("HUAAO1")==1){
		CustomerService cust_service = new CustomerService();
		cust_service.sendCustToSAP(cust_id,input_operatorCode);
	}
}
%>

<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("menu.customerInformation",clientLocale)%> </TITLE>
<!--客户信息-->
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<BASE TARGET="_self">

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/investment.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>

<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/CustomerService.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>

<script language="javascript">
/*验证数据*/
function validateForm(){		
	form = document.theform;
	var is_deal = document.getElementById("is_deal").value;
	
	if(!sl_checkChoice(form.customer_cust_source, "<%=LocalUtilis.language("class.customerSource",clientLocale)%> "))return false;//客户来源
	
	if(is_deal==1&&!sl_checkChoice(form.customer_card_type, "<%=LocalUtilis.language("class.customerCardType",clientLocale)%> "))return false;//证件类型	
	if(is_deal==1&&!sl_check(form.customer_card_id, "<%=LocalUtilis.language("class.customerCardID",clientLocale)%> ",40,1))return false;//证件号码
	
	if(form.customer_card_type.value=="110801"){
		if(!sl_check(form.customer_card_id,"<%=LocalUtilis.language("class.customerCardID",clientLocale)%> ",18,15))return false;//证件号码
		if(!(form.customer_card_id.value.length==15||form.customer_card_id.value.length==18)){
			sl_alert("<%=LocalUtilis.language("message.customerCardIDSizeIs15or18",clientLocale)%> ");//身份证号码必须为15或18位
			form.customer_card_id.focus();
			return false;
		}
	}
	<%if (user_id.intValue()==17){%>//方正要求证件有效期必填:customer_card_valid_date
		if(form.customer_card_valid_date_picker.value==""){
			alert("请填写证件有效期");
			form.customer_card_valid_date_picker.focus();
			return false;
		}
	<%}%>
	
	if(!sl_checkChoice(form.customer_touch_type, "<%=LocalUtilis.language("class.serviceWay",clientLocale)%> "))return false;//联系方式
	
	<% if(isVPhone == false) {%>
	
	if(form.customer_e_mail.value.length!=0){
		
		if(!isEmail(form.customer_e_mail,"Email"))
		{alert("请填写正确Email");
			return false;
		}
	}
	//手机号必填校验
	if(!sl_checkNum(form.customer_mobile, "<%=LocalUtilis.language("class.mobile",clientLocale)%> ", 11, 1))	return false;//手机号码
	if(!(form.customer_mobile.value.length==11)){
		sl_alert("手机号码必须为11位");//手机号码必须为11位
		form.customer_mobile.focus();
		return false;
	}
	if(form.customer_touch_type.value=="110901"){		
		<%if(user_id.intValue()==11){%>	
			if(!sl_checkNum(form.customer_o_tel, "<%=LocalUtilis.language("class.companyPhone",clientLocale)%> ", 11, 1))	return false;//公司电话
			if(!sl_checkNum(form.customer_mobile, "<%=LocalUtilis.language("class.mobile",clientLocale)%> ", 11, 1))	return false;//手机号码
		<%}
		else{%>
			if(form.customer_h_tel.value.length==0&&form.customer_o_tel.value.length==0&&form.customer_mobile.value.length==0&&form.customer_bp.value.length==0){
				sl_alert("<%=LocalUtilis.language("message.telMoreThanOne",clientLocale)%> ");//请至少填一个联系电话
				return false;
			}
			
			if(!sl_checkNum(form.customer_mobile, "<%=LocalUtilis.language("class.mobile",clientLocale)%> ", 11, 0))	return false;//手机号码
			if(!sl_checkNum(form.customer_bp, "<%=LocalUtilis.language("class.mobile",clientLocale)%> ", 11, 0))	return false;	//手机号码
			if(!(form.customer_mobile.value.length==11)){
				sl_alert("手机号码必须为11位");//手机号码必须为11位
				form.customer_mobile.focus();
				return false;
			}
		<%}%>						
	}

	if(form.customer_touch_type.value=="110903"){
		if(!sl_check(form.customer_e_mail, "Email", 30, 1))	return false;		
	}
	
	if(form.customer_touch_type.value=="110904"){
		if(!sl_check(form.customer_fax, "<%=LocalUtilis.language("class.fax",clientLocale)%> ", 60, 1))return false;//传真
	}
	
	if(form.customer_touch_type.value=="110905" || form.customer_touch_type.value=="110906"){
		if(!sl_checkNum(form.customer_mobile, "<%=LocalUtilis.language("class.mobile",clientLocale)%> ", 11, 1)) return false;//手机号码
		if(!(form.customer_mobile.value.length==11)){
			sl_alert("手机号码必须为11位");//手机号码必须为11位
			form.customer_mobile.focus();
		return false;
		}
	}
	<%}%>
	
	if(form.customer_touch_type.value=="110902"){
		if(!sl_check(form.customer_post_address, "<%=LocalUtilis.language("class.postAddress2",clientLocale)%> ", 60, 1))return false;//邮寄地址	  
	}
	

	if(!sl_checkNum(form.customer_post_code, "<%=LocalUtilis.language("class.postcode",clientLocale)%> ", 6, 0))return false;//邮政编码
	
	if(form.customer_cust_type.value==1){
		if(document.theform.customer_birthday_picker.value!=""){
			if(!sl_checkDate(document.theform.customer_birthday_picker,"<%=LocalUtilis.language("class.birthday",clientLocale)%> "))	return false;//出生日期
			syncDatePicker(form.customer_birthday_picker, form.customer_birthday);
		}
		if(form.customer_age.value != null && form.customer_age.value < 0){
			sl_alert("<%=LocalUtilis.language("message.ageError",clientLocale)%> ！");//年龄非法
			form.customer_age.focus();
			return false;
		}
	}
	
	if(document.theform.customer_is_link.checked){
		document.theform.customer_is_link.value="1";
	}
	else{
		document.theform.customer_is_link.value="2";
	}

	if(document.theform.customer_print_deploy_bill.checked){
		document.theform.customer_print_deploy_bill.value="1";
	}
	else{
		document.theform.customer_print_deploy_bill.value="2";
	}

	if(document.theform.customer_print_post_info.checked){
		document.theform.customer_print_post_info.value="1";
	}
	else{
		document.theform.customer_print_post_info.value="2";
	}

	if(document.theform.customer_vip_date_picker.value!=""){
		if(!sl_checkDate(document.theform.customer_vip_date_picker,"<%=LocalUtilis.language("class.customerVipDatePicker",clientLocale)%> "))return false;//VIP发卡日期	
		syncDatePicker(form.customer_vip_date_picker, form.customer_vip_date);
	}
	if(form.customer_card_valid_date_picker !="")
		syncDatePicker(form.customer_card_valid_date_picker, form.customer_card_valid_date);
	if (form.legal_card_valid_date_picker && form.legal_card_valid_date)
		syncDatePicker(form.legal_card_valid_date_picker, form.legal_card_valid_date);
	if (form.agent_card_valid_date_picker && form.agent_card_valid_date)
		syncDatePicker(form.agent_card_valid_date_picker, form.agent_card_valid_date);
	return true;
}

/*保存数据*/
function saveAction(form){
	var is_deal = document.getElementById("is_deal").value;
	
	if(!sl_check(form.customer_cust_name, "<%=LocalUtilis.language("class.customerName",clientLocale)%> ", 100, 1))return false;//客户名称
<%
if (Argument.getValueOfTSysControlByFlagType("CUST_PROF","1")==1) { // 维护事实客户信息时要求输入职业 1是 0否 %>
	if(is_deal==1&&!sl_checkChoice(form.customer_zyhy_type, "<%=LocalUtilis.language("class.industry",clientLocale)%>/<%=LocalUtilis.language("class.profession",clientLocale)%>"))return false;
	//职业/行业	
<%
}%>
	if(validateForm()){
		var v_cust_id = document.getElementById("cust_id").value;
		if(v_cust_id>0){
			//编辑操作
			document.getElementById("action_flag").value=2;
			
			if(sl_confirm("<%=LocalUtilis.language("message.inputInfoSave",clientLocale)%> ")){//保存输入信息
				form.action="customerInfo_details_action.jsp";
				form.submit();
			}
		}
		/*else if (!form.cust_flag.checked){
			if(sl_confirm("<%=LocalUtilis.language("message.inputInfoSave",clientLocale)%> ")){//保存输入信息
				document.theform.action="customerInfo_details_action.jsp";
				document.theform.submit();
			}
		}*/
		else{
			//新建操作
			document.getElementById("action_flag").value=1;
			CustomerService.findSameCustInfo(document.theform.customer_cust_name.value,0,document.theform.cust_id.value,document.theform.customer_card_id.value,dwrCallbackInfo );
		}	
	}	
}

//DWR 返回函数
function dwrCallbackInfo(data){
	if(data!=""){
		if(data.indexOf("无法保存")>=0) {alert(data); return false;}
		if(!confirm(data)){
			return;
		}
	}
	if(sl_confirm("<%=LocalUtilis.language("message.inputInfoSave",clientLocale)%> ")){//保存输入信息
		document.theform.action="customerInfo_details_action.jsp";
		document.theform.submit();
	}
}

//改变客户类型
function changeCustType(value){	
	var v_cust_id = document.getElementById("cust_id").value;
	var action_flag = document.getElementById("action_flag").value;
	var cust_type = document.getElementsByName("customer_cust_type")[0].value;
	
	var url = "customerInfo_details_action.jsp?action_flag="+action_flag;
	var url = url + "&cust_id=" + v_cust_id;
	var url = url + "&customer_cust_type=" + cust_type;
	
	window.location.href=url;	
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

//校验身份证
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

function getDateByMask(s, m){
	if (s.length!=m.length){
		return false;
	}
	
	var t = m.replace(/d/ig,"\\d").replace(/m/ig,"\\d").replace(/y/ig,"\\d")
	var r = new RegExp("^"+t+"$");
	if (!r.test(s)) return null;
	
	try{
		m=m.replace(/Y/g,"y").replace(/D/g,"d");
		if (m.indexOf("yyyy")>-1)
			return new Date(s.substr(m.indexOf("yyyy"),4),s.substr(m.indexOf("MM"),2)-1,s.substr(m.indexOf("dd"),2));
		else
			return new Date(s.substr(m.indexOf("yy"),2),s.substr(m.indexOf("MM"),2)-1,s.substr(m.indexOf("dd"),2));
	}
	catch (e){
		return null;
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
/*改变客户类型 1-事实客户；2潜在客户；*/
function changeIsDeal(){
	var temp = document.getElementById("is_deal_picker").value;
	document.getElementById("is_deal").value = temp;

	var isDealSpanStyle = "";	
	if(temp==2){
		isDealSpanStyle = "none";
	}

	showSpan(isDealSpanStyle);
}

function showSpan(isDealSpanStyle){
	document.getElementById("isDealSpan_voc_type").style.display = isDealSpanStyle;
	document.getElementById("isDealSpan_customerSource").style.display = isDealSpanStyle;
	document.getElementById("isDealSpan_customerCardType").style.display = isDealSpanStyle;
	document.getElementById("isDealSpan_customerCardID").style.display = isDealSpanStyle;
}
/*多选资金来源*/
function chooseAction(){
	var money_source = document.getElementById("money_source").value;
	var url = "<%=request.getContextPath()%>/client/money_source_check.jsp?money_source="+money_source;
	var ret = showModalDialog(url,'','dialogWidth:450px;dialogHeight:350px;status:0;help:0');
	
	if(ret!=null){
		document.getElementById("money_source").value = ret[0];
		document.getElementById("money_source_name").value = ret[1];
	}
}

function searchServman() {
	var servman = document.theform.customer_service_man;
	var i;
	for (i=1; i<servman.options.length; i++) {
		if (servman.options[i].text.indexOf(document.theform.servman_name.value) >= 0) {
			servman.selectedIndex = i;
			break;
		}
	}
	if (i==servman.options.length) {
		sl_alert("找不到名字带有\""+document.theform.servman_name.value+"\"字的客户经理！");
	}
}

window.onload = function(){
	changeIsDeal();
};
</script>
</HEAD>

<BODY>
<form name="theform" method="post">
<!--新增成功标志-->
<input type="hidden" id="bSuccess" value="<%=bSuccess%>"/>
<input type="hidden" id="action_flag" name="action_flag" value="<%=action_flag%>"/>
<input type="hidden" id="cust_id" name="cust_id" value="<%=cust_id%>">

<!--客户信息-->
<div>
<table border="0" width="100%" cellspacing="0" cellpadding="1">
	<tr>
		<td align="right"><span style="color:red">*</span><%=LocalUtilis.language("class.customerType",clientLocale)%> :&nbsp;&nbsp;</td><!--客户类别-->
		<td >
			<SELECT size="1" <%if (!sReadonly.equals("")){out.print("style='display:none'");}%> 
					onchange="javascript:changeCustType(this.value);"  name="customer_cust_type" 
					onkeydown="javascript:nextKeyPress(this)"  style="WIDTH: 120px">
				<%=Argument.getCustTypeOptions2(cust_type)%>
			</SELECT>	
		</td>
		<td align="right"><span style="color:red">*</span><%=LocalUtilis.language("class.custType",clientLocale)%> :&nbsp;&nbsp;</td><!--客户类型-->	
		<td>
			<select name="is_deal_picker" id="is_deal_picker" onkeydown="javascript:nextKeyPress(this)" style="width:120px" onchange="javascript:changeIsDeal();">	
				<%=Argument.getWTCustOptions(is_deal)%>
			</select>
			<input name="is_deal" id="is_deal"  type="hidden" value="<%=Utility.trimNull(is_deal)%>"  />		
		</td>
	</tr>
	
	<tr>
		<td align="right"><span style="color:red">*</span><%=LocalUtilis.language("class.customerName",clientLocale)%> :&nbsp;&nbsp;</td><!--客户名称-->
		<td ><input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this)" name="customer_cust_name" size="40" maxlength="60" value="<%=cust_name%>"></td>
		<td align="right">		 
			<%if(cust_type.intValue()==1){%><span id="isDealSpan_voc_type">*</span><%=LocalUtilis.language("class.profession",clientLocale)%> <!--职业-->:<%}
				else{%><span id="isDealSpan_voc_type">*</span><%=LocalUtilis.language("class.industry",clientLocale)%> <!--行业-->:<%}%>&nbsp;&nbsp;				
		</td>
		<td colspan=3>
			<select size="1" onkeydown="javascript:nextKeyPress(this)" name="customer_zyhy_type" style="WIDTH: 238px">
			<%if(cust_type.intValue()==1){%>
			   <%=Argument.getGrzyOptions2(voc_type)%>
			<%} 
			else{%>	  
			   <%=Argument.getJghyOptions2(voc_type)%>
		    <%}%>
			</select>								
		</td>
	</tr>
		<%if(cust_type.intValue()==2){%>
	<tr>
		<td align="right"><span style="color:red">*</span>机构类型 :&nbsp;&nbsp;</td><!--机构类型-->	
		<td>
			<select name="jg_type" id="jg_type" onkeydown="javascript:nextKeyPress(this)" style="width:120px">	
				<%=Argument.getDictParamOptions(2109,Utility.trimNull(jg_type))%>
			</select>		
		</td>
		<td align="right"><span style="color:red">*</span>委托人类型 :&nbsp;&nbsp;</td><!--委托人类型-->	
		<td>
			<select name="jg_wtrlx2" id="jg_wtrlx2" onkeydown="javascript:nextKeyPress(this)" style="width:120px">	
				<%=Argument.getInstitutionsOptions(Utility.parseInt(Utility.trimNull(jg_wtrlx2),new Integer(0)))%>
			</select>		
		</td>
	</tr>
	<%}%>
	<tr>	
		<td align="right"><%=LocalUtilis.language("class.customerNumber",clientLocale)%> :&nbsp;&nbsp;</td><!--客户代码-->
		<td><input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this)" name="customer_cust_no" size="25" maxlength="25" value="<%=cust_no%>"></td>
		<td align="right"><span id="isDealSpan_customerSource">*</span><%=LocalUtilis.language("class.customerSource",clientLocale)%> :&nbsp;&nbsp;</td><!--客户来源-->
		<td>
			<select onkeydown="javascript:nextKeyPress(this)" size="1" name="customer_cust_source" style="width: 110px">
				<%=Argument.getCustomerSourceOptions(cust_source)%>
			</select>
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
			<INPUT TYPE="text" <%=sReadonly%> NAME="customer_vip_date_picker" class=selecttext 
			<%if(vip_date==null){%> value=""
			<%}else{%>value="<%=Format.formatDateLine(vip_date)%>"<%}%> onkeydown="javascript:nextKeyPress(this)">
			<%if(sReadonly.equals("")){%><INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.customer_vip_date_picker,theform.customer_vip_date_picker.value,this);" tabindex="13">
			<%}%><INPUT TYPE="hidden" NAME="customer_vip_date"   value="">
		</td>
		
		<td align="right"><%=LocalUtilis.language("class.accountManager",clientLocale)%> :&nbsp;&nbsp;</td><!--客户经理--> 
		<td>
			<%if (cust_id!=null&&!"".equals(cust_id)&&cust_id.intValue()!=0){%>
				<%if(user_id.intValue()==2){%>
				<select size="1" name="customer_service_man" disabled="disabled" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px">
					<%=Argument.getManagerListAuth(input_operatorCode,service_man,new Integer(0))%>
				</select>
				<%}else{ %>
				<select size="1" name="customer_service_man" disabled="disabled" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px">
					<%=Argument.getManager_Value(service_man)%>
				</select>
				<%} %>
			<%}else{%>
				<%if(user_id.intValue()==2){%>
				<select size="1" name="customer_service_man" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px">
					<%=Argument.getManagerListAuth(input_operatorCode,service_man,new Integer(0))%>
				</select>
				<%}else{ %>
				<select size="1" name="customer_service_man" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px">
					<%=Argument.getManager_Value(service_man)%>
				</select>
				<%} %>
				&nbsp;&nbsp;<input type="text" name="servman_name" size="10"/>
				&nbsp;&nbsp;<button type="button"  class="searchbutton" onclick="javascript:searchServman();" /></button>
			<%}%>
		</td>
	</tr>		
	
	<tr>
		<td align="right"><span id="isDealSpan_customerCardType">*</span><%=LocalUtilis.language("class.customerCardType",clientLocale)%> :&nbsp;&nbsp;</td><!--证件类型-->
		<td>		   
			<input type="hidden" onkeydown="javascript:nextKeyPress(this)" name="customer_card_type_name" size="25" maxlength="25" value="<%=card_type_name%>">
		   <%
			if(cust_type==null){
				cust_type=new Integer(0);			
			}			

			if(cust_type.intValue()==1){
			%>				
				<select id="customer_card_type" size="1" onkeydown="javascript:nextKeyPress(this)" <%if (!sReadonly.equals("")) out.print("style='display:none'");%>  onchange="javascript:document.theform.customer_card_type_name.value=document.theform.customer_card_type.options[document.theform.customer_card_type.selectedIndex].text;" name="customer_card_type" style="WIDTH: 120px">
					<%=Argument.getCardTypeOptions2(card_type.toString())%>
				</select>
			<%}
			else{
			%>				
				<select size="1" onkeydown="javascript:nextKeyPress(this)" <%if (!sReadonly.equals("")) out.print("style='display:none'");%> onchange="javascript:document.theform.customer_card_type_name.value=document.theform.customer_card_type.options[document.theform.customer_card_type.selectedIndex].text;" name="customer_card_type" style="WIDTH: 120px">
					<%=Argument.getCardTypeJgOptions2(card_type)%>
				</select>
			<%}%>
		</td>	
		
		<td align="right"><span id="isDealSpan_customerCardID">*</span><%=LocalUtilis.language("class.customerCardID",clientLocale)%> :&nbsp;&nbsp;</td><!--证件号码-->
		<td>
			<input <%=sReadonly%> onkeydown="javascript:calculateAge();nextKeyPress(this)"onblur="javascript:calculateAge2();" 
			onkeyup="javascript:showAcctNum(this.value)" name="customer_card_id" size="25" maxlength="30" value="<%=card_id%>">
			<span id="bank_acct_num" class="span"></span>
		</td>	
	</tr>
	<!--  <tr>
		<td align="right">唯一性校验:&nbsp;&nbsp;</td>
		<td><input type="checkbox" id="cust_flag" name="cust_flag" value="1" checked=checked></td>
	</tr>-->
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
			<%}%><INPUT TYPE="hidden" NAME="customer_birthday" >
		</td>
	
	<%}%>
		<td align="right"><%if (user_id.intValue()==17){/*方正要求证件有效期必填*/%><span style="color:red">*</span><%} %><%=LocalUtilis.language("class.tradeDate",clientLocale)%> :&nbsp;&nbsp;</td><!--证件有效期限-->
		<td align="left">
			<input type="text" name="customer_card_valid_date_picker" size="10" maxlength="8" 
			<%if(card_valid_date==null) {%> value=""
			<%}else{ %> value="<%=Format.formatDateLine(card_valid_date)%>" <%}%> onkeydown="javascript:CalAge();nextKeyPress(this)">
			<%if(sReadonly.equals("")){%>
			<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.customer_card_valid_date_picker,theform.customer_card_valid_date_picker.value,this);"
					   onkeydown="javascript:CalAge();nextKeyPress(this)"> 
			<%}%><INPUT type="hidden" name="customer_card_valid_date"  /> &nbsp;&nbsp;
			<button type="button"  class="xpbutton3" onclick="javascript:document.theform.customer_card_valid_date_picker.value='2500-01-01';">长期有效</button>
		</td>
	<%if(cust_type.intValue()==2){%>
		<td align="right"><%=LocalUtilis.language("class.contact",clientLocale)%> :&nbsp;&nbsp;</td><!--联系人-->
		<td><input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this);" name="customer_contact_man"  size="25" maxlength="20" value="<%=contact_man%>"></td>
	<%}%>
	</tr>
				
	<%if(cust_type.intValue()==2){%>
	<tr>
		<td align="right">经营范围 :&nbsp;&nbsp;</td>
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
		<td>
			<select name="legal_card_type" id="legal_card_type" onkeydown="javascript:nextKeyPress(this)" style="width:120px">	
				<%=Argument.getCardTypeOptions2(legal_card_type)%>
			</select>
		</td>
		<td align="right">法人证件号码 :&nbsp;&nbsp;</td>
		<td>
			<input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this)" name="legal_card_id"  size="25" maxlength="20" value="<%=legal_card_id%>">
		</td>
	</tr>
	<tr>
		<td align="right">法人证件有效期限 :&nbsp;&nbsp;</td>
		<td>
			<INPUT TYPE="text" <%=sReadonly%> NAME="legal_card_valid_date_picker" class=selecttext 
			<%if(legal_card_valid_date==null){%> value=""
			<%}else{%>value="<%=Format.formatDateLine(legal_card_valid_date)%>"<%}%> onkeydown="javascript:nextKeyPress(this)">
			<%if(sReadonly.equals("")){%><INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.legal_card_valid_date_picker,theform.legal_card_valid_date_picker.value,this);" tabindex="13">
			<%}%><INPUT TYPE="hidden" NAME="legal_card_valid_date"/>
		</td>
		<td align="right">代理人姓名 :&nbsp;&nbsp;</td>
		<td><input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this)" name="agent_name"  size="25" maxlength="30" value="<%=agent_name%>"></td>
	</tr>	
	<tr>
		<td align="right">代理人电话 :&nbsp;&nbsp;</td>
		<td><input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this);" name="agent_tel"  size="25" maxlength="20" value="<%=agent_tel%>"></td>
		<td align="right">代理人证件类型 :&nbsp;&nbsp;</td>
		<td>
			<select name="agent_card_type" id="agent_card_type" onkeydown="javascript:nextKeyPress(this)" style="width:120px">	
				<%=Argument.getCardTypeOptions2(agent_card_type)%>
			</select>
		</td>
	</tr>
	<tr>		
		<td align="right">代理人证件号码 :&nbsp;&nbsp;</td>
		<td><input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this);" name="agent_card_id"  size="25" maxlength="20" value="<%=agent_card_id%>"></td>
		<td align="right">代理人证件有效期限 :&nbsp;&nbsp;</td>
		<td>
			<INPUT TYPE="text" <%=sReadonly%> NAME="agent_card_valid_date_picker" class=selecttext 
			<%if(agent_card_valid_date==null){%> value=""
			<%}else{%>value="<%=Format.formatDateLine(agent_card_valid_date)%>"<%}%> onkeydown="javascript:nextKeyPress(this)">
			<%if(sReadonly.equals("")){%><INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.agent_card_valid_date_picker,theform.agent_card_valid_date_picker.value,this);" tabindex="13">
			<%}%><INPUT TYPE="hidden" NAME="agent_card_valid_date"/>
		</td>
	</tr>
	<tr>		
		<td align="right">合伙人身份证明文件编码 :&nbsp;&nbsp;</td>
		<td><input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this)" name="partner_card_id" size="25" maxlength="30" value="<%=partner_card_id%>"></td>
	</tr>		
	<%}%>
	<%if(cust_type.intValue()==1){%>	
	<tr>
		<td align="right"><%=LocalUtilis.language("class.age",clientLocale)%> :&nbsp;&nbsp;</td><!--年龄-->
		<td><INPUT <%=sReadonly%> name="customer_age" onkeydown="javascript:nextKeyPress(this)" size="20" maxlength="4" value="<%=age%>"></td>
		<td align="right"><%=LocalUtilis.language("class.sex",clientLocale)%> :&nbsp;&nbsp;</td><!--性别-->
		<td>				
			<SELECT size="1" onkeydown="javascript:nextKeyPress(this)" name="customer_sex" id="customer_sex" style="WIDTH: 120px">
				<%=Argument.getSexOptions(sex)%>
			</SELECT>
		</td>
	</tr>
	<%}%>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.countryName",clientLocale)%> :&nbsp;&nbsp;</td><!--国别-->
		<td>
			<select size="1"  name="country"  id="country" style="width: 125px" onkeydown="javascript:nextKeyPress(this)">
				<%=Argument.getDictParamOptions(9997,country)%>
			</select>
		</td>
		<td align="right"><%=LocalUtilis.language("class.referee",clientLocale)%> :&nbsp;&nbsp;</td> <!--推荐人-->   
		<td>
			<input type="text" name="recommended" size="25" value="<%=recommended%>">
		</td>
	</tr>	
	<% if(isVPhone == false) {%>	
	<tr>	
		<td align="right"><%=LocalUtilis.language("class.customerHTel",clientLocale)%> :&nbsp;&nbsp;</td><!--家庭电话-->
		<td><input <%=sReadonly%> name="customer_h_tel" onkeydown="javascript:nextKeyPress(this)" maxlength="20" size="25" value="<%=h_tel%>"></td>
		<td align="right"><%=LocalUtilis.language("class.companyPhone",clientLocale)%> :&nbsp;&nbsp;</td><!--公司电话-->
		<td><INPUT <%=sReadonly%> name="customer_o_tel" onkeydown="javascript:nextKeyPress(this)" maxlength="20" size="25" value="<%=o_tel%>"></td>
	</tr>
	
	<tr>
		<td align="right"><span style="color:red">*</span><%=LocalUtilis.language("class.customerMobile",clientLocale)%> :&nbsp;&nbsp;</td><!--手机-->
		<td><input <%=sReadonly%> name="customer_mobile" onkeydown="javascript:nextKeyPress(this)" maxlength="30" size="25" value="<%=mobile%>"></td>
		<td align="right"><%=LocalUtilis.language("class.customerMobile",clientLocale)%> 2 :&nbsp;&nbsp;</td><!--手机2-->
		<td><INPUT <%=sReadonly%> name="customer_bp" onkeydown="javascript:nextKeyPress(this)" size="25" maxlength="30" value="<%=bp%>"></td>
	</tr>
	
	<tr>
		<td align="right"><%=LocalUtilis.language("class.fax",clientLocale)%> :&nbsp;&nbsp;</td><!--传真-->
		<td><input <%=sReadonly%> name="customer_fax" onkeydown="javascript:nextKeyPress(this)" size="25" maxlength="30" value="<%=fax%>"></td>
		<td align="right">Email :&nbsp;&nbsp;</td>
		<td><INPUT <%=sReadonly%> name="customer_e_mail" onkeydown="javascript:nextKeyPress(this)" maxlength="30" size="40" value="<%=e_mail%>"></td>
	</tr>
	<% } %>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.postcode",clientLocale)%> :&nbsp;&nbsp;</td><!--邮政编码-->
		<td ><input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this)" name="customer_post_code" size="25" maxlength="6" value="<%=post_code%>"></td>	
		<td align="right"><%=LocalUtilis.language("class.postAddress2",clientLocale)%> :&nbsp;&nbsp;</td><!--邮寄地址-->
		<td ><input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this)" name="customer_post_address" size="40" maxlength="60" value="<%=post_address%>"></td>
	</tr>
	
	<tr>
		<td align="right"><%=LocalUtilis.language("class.postcode",clientLocale)%> 2 :&nbsp;&nbsp;</td><!--邮政编码2-->
		<td ><input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this)" name="customer_post_code2" size="25" maxlength="6" value="<%=post_code2%>"></td>
		
		<td align="right"><%=LocalUtilis.language("class.postAddress2",clientLocale)%> 2 :&nbsp;&nbsp;</td><!--邮寄地址2-->
		<td ><input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this)" name="customer_post_address2" size="40" maxlength="60" value="<%=post_address2%>"></td>
	</tr>
	
	<tr>
		<td align="right"><span style="color:red">*</span><%=LocalUtilis.language("class.serviceWay",clientLocale)%> :&nbsp;&nbsp;</td><!--联系方式-->
		<td>
			<select size="1" name="customer_touch_type" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px">
				<%=Argument.getTouchTypeOptions(touch_type)%>
			</select>
		</td>
		<td align="right">资金来源 :&nbsp;&nbsp;</td>
		<td>
				<input type="text" name="money_source_name" id="money_source_name" size="27"  value="<%=money_source_name%>" onkeydown="javascript:nextKeyPress(this)" readonly/>
				<input type="hidden" name="money_source" id="money_source" value="<%=money_source%>" /> 
				<!-- 选择 -->&nbsp;&nbsp;
                <button type="button"  class="xpbutton2" id="btnChoMoneySource" name="btnChoMoneySource" onclick="javascript:chooseAction();"><%=LocalUtilis.language("message.choose",clientLocale)%> </button>
		</td>
		
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.customerIsLink",clientLocale)%> :&nbsp;&nbsp;</td><!--关联方-->
		<td ><input <%=sDisabled%> type="checkbox" name="customer_is_link" value="<%=is_link%>" <%if(is_link !=null){if(is_link.intValue()==1) out.print("checked");}%> class="flatcheckbox"></td>
		<td align="right">真实性 :&nbsp;&nbsp;</td>
		<td><select name="true_flag"><%=Argument.getCustInfoTrueFlagList(true_flag)%></td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("message.printCustomerStatement",clientLocale)%> :&nbsp;&nbsp;</td><!--打印客户对帐单-->
		<td><input <%=sDisabled%> class="flatcheckbox" type="checkbox" name="customer_print_deploy_bill" <%=sPrint_deploy_bill%> value="<%=deploy_bill %>"></td>
		<td align="right"><%=LocalUtilis.language("message.customerPrintPostInfo",clientLocale)%> :&nbsp;&nbsp;</td><!--打印披露信息-->
		<td><input <%=sDisabled%> class="flatcheckbox" type="checkbox" name="customer_print_post_info" <%=sPrint_post_info%> value="<%=post_info %>"></td>
	</tr>
	
	<tr>	
		<td  align="right"><%=LocalUtilis.language("class.customerSummary",clientLocale)%> :&nbsp;&nbsp;</td><!--备注-->
		<td colspan=3><textarea <%if (!sReadonly.equals("")) out.print("readonly class='edline_textarea'");%> rows="3" name="customer_summary" cols="86"><%=summary%></textarea></td>
	</tr>
</table>
</div>
<br>
<div align="right">
	<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:saveAction(document.theform);"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;<!--保存-->
	<button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.returnValue=null;window.close();"><%=LocalUtilis.language("message.close",clientLocale)%> (<u>C</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<!--关闭-->
</div>

</form>

<%customer.remove();
customer_inst.remove();%>
<script language=javascript>
<% if (pos_flag==2){
%>
	//从session中给表单赋值
	document.theform.customer_cust_name.value='<%=Utility.trimNull(session.getAttribute("name"))%>';
	document.theform.customer_card_id.value='<%=Utility.trimNull(session.getAttribute("card_id"))%>';
	document.theform.customer_post_address.value='<%=Utility.trimNull(session.getAttribute("address"))%>';
	document.theform.customer_birthday_picker.value='<%=Utility.trimNull(session.getAttribute("birth"))%>';
	document.theform.customer_card_valid_date.value='<%=Utility.trimNull(session.getAttribute("period"))%>';
	setValue1();//设置客户类型
	document.getElementById("customer_card_type").options[1].selected=true;//设置 证件类型 为身份证
	//设置 客户性别
	<%String c_sex = Utility.trimNull((String)session.getAttribute("sex")); 
	  if ("男".equals(c_sex)){%>
		document.getElementById("customer_sex").options[1].selected=true;
	<%}else if  ("女".equals(c_sex)){%>
		document.getElementById("customer_sex").options[2].selected=true;
	<%}%>
	
<%}%>
window.onload = function(){
	var v_bSuccess = document.getElementById("bSuccess").value;
	var v_cust_id = document.getElementById("cust_id").value;
	var isDeal = document.getElementById("is_deal").value;
	
	if(v_cust_id==1){
		document.getElementById("is_deal_picker").disabled=true;
	}
	else{
		showSpan("none");
	}
	
	//控制父窗口 转换子窗口	
	if(v_bSuccess=="true"){		
		sl_alert("<%=LocalUtilis.language("message.saveOk",clientLocale)%> ！");//保存成功
		window.parent.document.all("v_cust_id").value=v_cust_id; 
		window.parent.theform.changeButton.click() ;
	}
}
//设置客户类型
function setValue1(){
var o = document.getElementById("is_deal_picker").options;
	for(var i=0; i<o.length; i++){
		if(1==o[i].value){ //只能选：事实客户
			o[i].selected=true;
			changeIsDeal();
			return;
		}
	}
}
</script>
</BODY>
</HTML>
