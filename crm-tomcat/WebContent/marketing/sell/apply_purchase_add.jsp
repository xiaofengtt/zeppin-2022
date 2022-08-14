<%@ page contentType="text/html; charset=GBK" import="enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@page import="enfo.crm.web.DocumentFile2"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
String money_origin = "";
String sub_money_origin = "";
Integer contract_type = new Integer(0);


int is_product_sub_flag = Argument.getSyscontrolValue_1("SUBCP01");//子产品开关
int display_flag = Utility.parseInt(request.getParameter("display_flag"),0);
int inputflag = Utility.parseInt(request.getParameter("inputflag"), 2);//银行帐号输入 或者 下拉选择 标志
int pos_flag = Utility.parseInt(request.getParameter("pos_flag"),0); //证件扫描标志
String session_cust_name = Utility.trimNull(session.getAttribute("name"));

String display_msg = "style='display:none'";
String display_msg1 = "";
if(display_flag == 1){ //有过认购记录时候
	display_msg = "";
	display_msg1 = "style='display:none'";
}
String [] sTouch_types = {"110902"};//联系方式
int bzjFlag = Argument.getSyscontrolValue_intrust("BZJCLFS");//保证金开关 1有,2没有

Integer local_serial_no = Utility.parseInt(request.getParameter("local_serial_no"),null);

Integer valid_period = null;
Integer period_unit = new Integer(2); // 月

CustomerLocal customer = EJBFactory.getCustomer();
Customer_INSTLocal customer_inst = EJBFactory.getCustomer_INST();
ApplyreachVO s_appl_vo = new ApplyreachVO();
ApplyreachLocal apply_reach = EJBFactory.getApplyreach();
CustomerVO c_vo = new CustomerVO();
Map cust_map = null;
DocumentFile2 file = null;
boolean bSuccess = false;
if("POST".equals(request.getMethod())){
	file = new DocumentFile2(pageContext);
	file.parseRequest();
	display_flag = Utility.parseInt(file.getParameter("display_flag"),0);
	local_serial_no = Utility.parseInt(file.getParameter("local_serial_no"),null);

	//获得保存数据
	Integer s_cust_id = Utility.parseInt(file.getParameter("customer_cust_id"), null);
	int bonus_flag = Utility.parseInt(file.getParameter("bonus_flag"), 1);
	inputflag = Utility.parseInt(file.getParameter("inputflag"), 2);//银行帐号输入 或者 下拉选择 标志
	Integer recommend_man = Utility.parseInt(file.getParameter("recommend_man"),new Integer(0));
	String bank_province = Utility.trimNull(file.getParameter("bank_province"));
	String bank_city = Utility.trimNull(file.getParameter("bank_city"));
	Integer contact_id=Utility.parseInt(file.getParameter("cust_message"), null);

	String cust_name = "";
	//同步客户信息
	if(s_cust_id.intValue()>0){
		//客户单个值
		c_vo.setCust_id(s_cust_id);
		c_vo.setInput_man(input_operatorCode);
		List rsList_cust = customer.listByControl(c_vo);

		Map map_cust = (Map)rsList_cust.get(0);
		if(map_cust!=null){
			c_vo.setCust_no( Utility.trimNull(map_cust.get("CUST_NO")));
			cust_name = Utility.trimNull(map_cust.get("CUST_NAME"));
			c_vo.setCust_name(Utility.trimNull(map_cust.get("CUST_NAME")));
			c_vo.setCust_type(Utility.parseInt(Utility.trimNull(map_cust.get("CUST_TYPE")),new Integer(0)));
			c_vo.setService_man(Utility.parseInt(Utility.trimNull(map_cust.get("SERVICE_MAN")),new Integer(0)));
			c_vo.setLegal_man(Utility.trimNull(map_cust.get("LEGAL_MAN")));
			c_vo.setContact_man(Utility.trimNull(map_cust.get("CONTACT_MAN")));
			c_vo.setPost_code(Utility.trimNull(map_cust.get("POST_CODE")));
			c_vo.setPost_address(Utility.trimNull(map_cust.get("POST_ADDRESS")));
			c_vo.setCard_type(Utility.trimNull(map_cust.get("CARD_TYPE")));
			c_vo.setCard_id(Utility.trimNull(map_cust.get("H_CARD_ID")));
			c_vo.setMobile(Utility.trimNull(map_cust.get("H_MOBILE")));
			c_vo.setE_mail(Utility.trimNull(map_cust.get("H_E_MAIL")));
			c_vo.setInput_man(input_operatorCode);		
			customer_inst.cope_customers(c_vo);
		}
	}
	

	//保存数据
	s_appl_vo.setBook_code(input_bookCode);
	s_appl_vo.setCust_id(Utility.parseInt(file.getParameter("customer_cust_id"), null));	
	s_appl_vo.setProduct_id(Utility.parseInt(file.getParameter("product_id"), null));
	s_appl_vo.setLink_man(Utility.parseInt(file.getParameter("link_man"), input_operatorCode));
	//s_appl_vo.setExpect_ror_lower(Utility.parseDecimal(file.getParameter("expect_ror_lower"), new BigDecimal(0)).multiply(new BigDecimal(0.01)));
	//s_appl_vo.setExpect_ror_upper(Utility.parseDecimal(file.getParameter("expect_ror_upper"), new BigDecimal(0)).multiply(new BigDecimal(0.01)));
	BigDecimal temp_dec = Utility.parseDecimal(file.getParameter("expect_ror_lower"), new BigDecimal("0.0")).multiply(new BigDecimal(0.01));
	temp_dec = temp_dec.setScale(6,BigDecimal.ROUND_HALF_UP); //统一设置小数精度为6
	s_appl_vo.setExpect_ror_lower(temp_dec);
	temp_dec = Utility.parseDecimal(file.getParameter("expect_ror_upper"), new BigDecimal(0)).multiply(new BigDecimal(0.01));
	temp_dec = temp_dec.setScale(6,BigDecimal.ROUND_HALF_UP); //统一设置小数精度为6
	s_appl_vo.setExpect_ror_upper(temp_dec);
	s_appl_vo.setSg_money(Utility.parseDecimal(file.getParameter("rg_money"),null));
	s_appl_vo.setBonus_flag(Utility.parseInt(file.getParameter("bonus_flag"), new Integer(1)));
	s_appl_vo.setJk_type(file.getParameter("jk_type"));
	s_appl_vo.setBank_id(file.getParameter("bank_id"));
	s_appl_vo.setBank_sub_name(Utility.trimNull(file.getParameter("bank_sub_name")));
	s_appl_vo.setGain_acct(file.getParameter("customer_gain_acct"));
	s_appl_vo.setStart_date(Utility.parseInt(file.getParameter("start_date"),new Integer(0)));//改参数已作废，所以随便传入当前时间
	
	if (inputflag==1)
		s_appl_vo.setBank_acct(file.getParameter("bank_acct2"));
	else if (inputflag==2)
		s_appl_vo.setBank_acct(file.getParameter("bank_acct"));

	s_appl_vo.setService_man(Utility.parseInt(file.getParameter("link_man"), input_operatorCode));
	s_appl_vo.setSummary(file.getParameter("summary2"));
	s_appl_vo.setContract_bh(file.getParameter("cs_appl_vo.bh"));
	s_appl_vo.setValid_period(Utility.parseInt(file.getParameter("valid_period"), null));
	s_appl_vo.setPeriod_unit(Utility.parseInt(file.getParameter("period_unit"), null));
	s_appl_vo.setInput_man(input_operatorCode);
	s_appl_vo.setSq_date(Utility.parseInt(file.getParameter("qs_date"), new Integer(Utility.getCurrentDate())));
	s_appl_vo.setJk_date(Utility.parseInt(file.getParameter("jk_date"), new Integer(Utility.getCurrentDate())));   //改参数已作废，所以随便传入当前时间
	s_appl_vo.setCheck_man(Utility.parseInt(file.getParameter("check_man"), new Integer(0)));
	s_appl_vo.setInput_man(input_operatorCode);
	if(user_id.intValue()==5) {
		s_appl_vo.setTouch_type(Utility.trimNull(file.getParameter("touchtype")));
		s_appl_vo.setTocuh_type_name(Utility.trimNull(file.getParameter("touch_type_name")));
	}
	s_appl_vo.setCity_serial_no(Utility.parseInt(file.getParameter("city_serialno"), new Integer(0)));
	s_appl_vo.setContract_sub_bh(file.getParameter("contract_sub_bh"));
	s_appl_vo.setFee_jk_type(Utility.parseInt(file.getParameter("fee_jk_type"),new Integer(0)));
	s_appl_vo.setBank_acct_type(Utility.trimNull(file.getParameter("bank_acct_type")));
	s_appl_vo.setSub_product_id(Utility.parseInt(file.getParameter("sub_product_id"), new Integer(0)));
	s_appl_vo.setWith_bank_flag(Utility.parseInt(file.getParameter("with_bank_flag"), new Integer(0)));
	s_appl_vo.setHt_bank_id(Utility.trimNull(file.getParameter("ht_bank_id")));
	s_appl_vo.setHt_bank_sub_name(Utility.trimNull(file.getParameter("ht_bank_sub_name")));
	s_appl_vo.setWith_security_flag(Utility.parseInt(file.getParameter("with_security_flag"), new Integer(0)));
	s_appl_vo.setWith_private_flag(Utility.parseInt(file.getParameter("with_private_flag"), new Integer(0)));
	s_appl_vo.setProv_level(Utility.trimNull(file.getParameter("prov_level")));
	s_appl_vo.setChannel_type(Utility.trimNull(file.getParameter("channel_type")));
	s_appl_vo.setChannel_memo(Utility.trimNull(file.getParameter("channel_memo")));
	s_appl_vo.setChannel_id(Utility.parseInt(file.getParameter("channel_id"), new Integer(0)));
	s_appl_vo.setBzj_flag(Utility.parseInt(file.getParameter("bzj_flag"), new Integer(2)));
	s_appl_vo.setChannel_cooperation(file.getParameter("channel_cooperation"));
	s_appl_vo.setMarket_trench_money(Utility.parseDecimal(file.getParameter("market_trench_money"),new BigDecimal(0)));
	s_appl_vo.setRecommend_man(Utility.parseInt(file.getParameter("recommend_man"),new Integer(0)));
	s_appl_vo.setBank_province(Utility.trimNull(file.getParameter("bank_province")));
	s_appl_vo.setBank_city(Utility.trimNull(file.getParameter("bank_city")));
	s_appl_vo.setContact_id(contact_id);

	s_appl_vo.setHt_cust_name(Utility.trimNull(file.getParameter("ht_cust_name")));
	s_appl_vo.setHt_cust_tel(Utility.trimNull(file.getParameter("ht_cust_tel")));
	s_appl_vo.setHt_cust_address(Utility.trimNull(file.getParameter("ht_cust_address")));
	s_appl_vo.setSpot_deal(Utility.parseInt(file.getParameter("spot_deal"), new Integer(2)));

	s_appl_vo.setMoney_origin(Utility.trimNull(file.getParameter("money_origin")));
	s_appl_vo.setSub_money_origin(Utility.trimNull(file.getParameter("sub_money_origin")));

	s_appl_vo.setProperty_souce(Utility.trimNull(file.getParameter("property_souce")));
	s_appl_vo.setOther_explain(Utility.trimNull(file.getParameter("other_explain")));
	s_appl_vo.setContract_type(Utility.parseInt(file.getParameter("contract_type"), new Integer(0)));


	if(bonus_flag == 1){//现金 
		s_appl_vo.setBonus_rate(new BigDecimal(0));
	}else if(bonus_flag == 2){//转份额 
		s_appl_vo.setBonus_rate(new BigDecimal(1));
	}else{//部分转份额
		s_appl_vo.setBonus_rate(Utility.parseDecimal(file.getParameter("bonus_rate"),new BigDecimal(0)).multiply(new BigDecimal("0.01")));
	}
	s_appl_vo.setProv_flag(Utility.parseInt(file.getParameter("prov_flag"), new Integer(0)));
	if(display_flag == 1 ){
		s_appl_vo.setCntr_serial_no(local_serial_no);
		Integer serial = apply_reach.append2(s_appl_vo);
		file.uploadAttchment(new Integer(7),Utility.parseInt(serial,new Integer(0)),"",null,null,input_operatorCode);
	}else{
		s_appl_vo.setProv_flag(Utility.parseInt(file.getParameter("prov_flag"), new Integer(1)));
		s_appl_vo.setProv_level(Utility.trimNull(file.getParameter("prov_level")));
		Object[] params = apply_reach.append(s_appl_vo);
		file.uploadAttchment(new Integer(7),Utility.parseInt(Utility.trimNull(params[0]),new Integer(0)),"",null,null,input_operatorCode);
	}
	bSuccess = true;

	String	product_name = Argument.getProductName(Utility.parseInt(file.getParameter("product_id"), null));
	Argument.addSysMessage(s_cust_id, "录入新申购合同"
		, "录入新申购合同，客户名称："+cust_name+"，产品名称："+product_name, input_operatorCode);


}else if (!"".equals(session_cust_name)){//通过证件扫描信息查询客户信息
	//通过客户姓名、证件号码来验证客户是否存在，不存在则新建
	c_vo.setCust_name((String)session.getAttribute("name"));
	c_vo.setCard_id((String)session.getAttribute("card_id"));
	List cust_list = customer.queryByCustNo(c_vo);
	if (cust_list.size()>0){
		cust_map = (Map)cust_list.get(0);
	}
}

apply_reach.remove();
customer.remove();

Integer product_id = Utility.parseInt(request.getParameter("product_id"),new Integer(0));
%>

<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("menu.applyPurchaseAdd",clientLocale)%></TITLE><!--申购信息新建-->
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<link href="<%=request.getContextPath()%>/widgets/ext/resources/css/ext-all.css" type="text/css"  rel="stylesheet" />
<link href="<%=request.getContextPath()%>/includes/jQuery/LeeDialog/dialog.css" type="text/css" rel="stylesheet" />
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/investment.js"></SCRIPT>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>

<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/contract.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/utilityService.js'></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/widgets/ext/resources/css/ext-base.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/widgets/ext/resources/css/ext-all.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/widgets/ext/resources/css/ComboBoxTree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/includes/jQuery/js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/includes/jQuery/js/jquery-ui-1.7.2.custom.min.js"></script>
<script language=javascript>
	var j$ = jQuery.noConflict();
</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/includes/jQuery/LeeDialog/dialog.js"></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/includes/jQuery/tree/script/jquery-1.4.2.min.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/includes/jQuery/FormValidate/js/FormValidate.js'></script>
<script language="javascript">
<%if(bSuccess){%>  
	sl_alert("保存成功");
	location = "apply_purchase_list.jsp?";	
<%}%>

/*手持设备读取*/
function card_wireless(){
	var url = "/marketing/card/wireless.jsp?pos_flag=4";
	dialog("","iframe:"+url,"950px","450px","show","<%=request.getContextPath()%>"); 
	showWaitting(0);
}

/*证件扫描*/
function card_discern(){
	var url = "/marketing/card/card_discern_trance.jsp?post_flag=4";
	dialog("","iframe:"+url,"950px","480px","show","<%=request.getContextPath()%>"); 
	showWaitting(0);
}
/*身份证阅读*/
function card_read(){
	var url = "/marketing/card/card_discern3_trance.jsp?pos_flag=4";
	dialog("","iframe:"+url,"950px","450px","show","<%=request.getContextPath()%>"); 
	showWaitting(0);
}

/*设置产品*/
function setProductCode(value){
	var prodid=0;

	if (event.keyCode == 13 && value != ""){
        j = value.length;
		var flag = 0;
		for(i=0;i<document.theform.product_id.options.length;i++){
			if(document.theform.product_id.options[i].text.substring(0,j)==value){
				document.theform.product_id.options[i].selected=true;
				prodid = document.theform.product_id.options[i].value;
				flag = 1;
				break;
			}
		}
		if(flag == 1)
			selectProduct();
		if (prodid==0){
			sl_alert("<%=LocalUtilis.language("message.inputProdIDError",clientLocale)%> ！");//输入的产品编号不存在或者该产品不在推介期
			document.theform.product_id.options[0].selected=true;
		}
	}

	nextKeyPress(this);
}

/*设置产品 获得 sub_flag标志*/
function selectProduct(){
	product_id = document.theform.product_id.value;
	if(product_id>0){
		utilityService.getSubProductFlag(product_id,getSubFlag);
		//产品销售渠道信息
		utilityService.queryMarketTrench(product_id,0,matchCustCallBack);
<%if (user_id.intValue()==2/*2北国投*/ || user_id.intValue()==999) {%>
		utilityService.getProductValidPeriod(product_id, 0, gotProductValidPeriod);
<%}%>
	}
	utilityService.getColumnOfTproductLimit(product_id,"CONTRACT_SINGLE_FLAG",function(data){
		document.theform.contract_single_flag.value = data;
		if(document.theform.customer_cust_id.value!="")
		utilityService.getContractInfo('<%=input_bookCode%>',product_id,0,document.theform.customer_cust_id.value,dwrCallBackMeth);
	})
}

function matchCustCallBack(data){
	var obj_op = document.getElementById("market_trench");  
    DWRUtil.removeAllOptions(obj_op);   
    DWRUtil.addOptions(obj_op,{'':'<%=LocalUtilis.language("message.select2",clientLocale)%> '});   //请选择
    DWRUtil.addOptions(obj_op,data);
}

function getSubFlag(data) {
	var is_product_sub_flag;
	var arrayL = data.split("$");

	is_product_sub_flag = arrayL[0];
	document.theform.intrust_flag1.value = arrayL[1];
	document.theform.asfund_flag.value = arrayL[2];
	document.theform.open_flag.value = arrayL[3];
	document.theform.is_local.value = arrayL[4];
	product_id = document.theform.product_id.value;
	document.theform.sub_flag.value = is_product_sub_flag;

	if (arrayL[8].charAt(arrayL[8].length-1)=="号")
		arrayL[8] = arrayL[8].substring(0, arrayL[8].length-1);
	$("contract_sub_bh_prefix").innerText = arrayL[8];

	if(DWRUtil.getValue("asfund_flag")==3)//收益级别按合同来定义
	{	
		document.getElementById("td_pro_level").style.display = "";
		document.getElementById("div_prov_level").style.display = "";
	}else{
		document.getElementById("td_pro_level").style.display = "none";
		document.getElementById("div_prov_level").style.display = "none";
	}
	
	if(DWRUtil.getValue("is_local")==2)//推荐地
		document.getElementById("tr_city_serialno").style.display = "";
	else
		document.getElementById("tr_city_serialno").style.display = "none";
		
	//申购交款方式
	if(DWRUtil.getValue("open_flag")==1){
		document.getElementById("sg_jk_name").style.display = "";
		document.getElementById("sg_jk_value").style.display = "";
	}else{
		document.getElementById("sg_jk_name").style.display = "none";
		document.getElementById("sg_jk_value").style.display = "none";
	}

	if(is_product_sub_flag==1){
		document.getElementById("sub_product_flag").style.display = "";
		utilityService.getSubProductOptionS(product_id,0,getSubProductOption);//获得子产品结果集
	}else{ //无子产品的 获得 受益优先级、收益级别
		document.getElementById("sub_product_flag").style.display = "none";
		utilityService.getSubProductProvFlag(document.theform.product_id.value,0,0,showProvFlag);
	}

	document.getElementById("td_city_serialno").style.display = "";
	document.getElementById("select_product_city").style.display = "";
	setProductCity(product_id);
}

//添加子产品下拉 ，获得子产品的 受益优先级、收益级别
function getSubProductOption(data) {
	$("select_id_2").innerHTML = "<select size='1' id='sub_product_id' name='sub_product_id' onkeydown='javascript:nextKeyPress(this)' class='productname' onchange='javascript:selectSubProduct(this.value);'>"+data+"</SELECT>";
}

function selectSubProduct(sub_product_id) {
	var product_id_v = DWRUtil.getValue("product_id");
	if (sub_product_id=="") sub_product_id = 0;
	utilityService.getSubPSylb(sub_product_id,showSylbCallBack);//获取子产品的受益类别,合同最低金额,合同最高金额
	//受益优先级，收益类别
	utilityService.getSubProductProvFlag(product_id_v,sub_product_id,0,showProvFlag);
	//获取子产品的销售渠道
	setSubProduct(document.theform.product_id.value, document.theform.sub_product_id.value);
<%if (user_id.intValue()==2/*2北国投*/ || user_id.intValue()==999) {%>
	utilityService.getProductValidPeriod(product_id_v, sub_product_id, gotProductValidPeriod);
<%}%>
}

function gotProductValidPeriod(data) {
	document.theform.valid_period.value = "";
	if (data[0]!=null) {
		document.theform.valid_period.value = data[0];
	}
	
	if (data[1]==null) {
		document.getElementById("period_unit").selectedIndex = 2; // 月
	} else {
		document.getElementById("period_unit").selectedIndex = data[1];
	} 
}

//选择子产品
function setSubProduct(product_id, sub_product_id){
	//1.销售渠道
	var obj_op = document.getElementById("market_trench");  
	var cust_id = document.getElementById("customer_cust_id").value; 
	if(product_id != "" && sub_product_id != ""){
		if(cust_id != "")utilityService.getContractInfo('<%=input_bookCode%>',product_id,sub_product_id,cust_id,dwrCallBackMeth);
		utilityService.queryMarketTrench(product_id,sub_product_id,{callback:function (data){			
		    DWRUtil.removeAllOptions(obj_op);   
		    DWRUtil.addOptions(obj_op,{'':'<%=LocalUtilis.language("message.select2",clientLocale)%> '});   //请选择
		    DWRUtil.addOptions(obj_op,data);
		}});
	}else{
		utilityService.queryMarketTrench(product_id,0,{callback:function (data){			
		    DWRUtil.removeAllOptions(obj_op);   
		    DWRUtil.addOptions(obj_op,{'':'<%=LocalUtilis.language("message.select2",clientLocale)%> '});   //请选择
		    DWRUtil.addOptions(obj_op,data);
		}});
	} 
}

//获取子产品的受益类别（去掉）,合同最低金额,合同最高金额
function showSylbCallBack(data){
	//document.getElementById('sylb').style.display = "";
	//document.getElementById('sylb_v').value = data[0];
	theform.min_buy_limit.value = data[1];//子产品的合同最低金额
	theform.max_buy_limit.value = data[2];//子产品的合同最高金额
}

//收益类别
function showProvFlag(data){
	var obj = document.theform.prov_flag;	
	var sub_flag = DWRUtil.getValue("sub_flag");
	
	var product_id_v = DWRUtil.getValue("product_id");
	var sub_product_id = DWRUtil.getValue("sub_product_id");
	if (document.getElementById("sub_product_flag").style.display == "none" ||
			sub_product_id=='请选择') 
		sub_product_id = 0;

	var intrust_flag1 = DWRUtil.getValue("intrust_flag1");
	var asfund_flag = DWRUtil.getValue("asfund_flag");
	DWRUtil.removeAllOptions(obj);  
	var json = eval(data);
	for(i=0;i<json.length;i++){
		DWRUtil.addOptions(obj,json[i]);
	}
	var prov_flag = obj.value;
	if( intrust_flag1!=1 && asfund_flag!=null && asfund_flag==3){
		utilityService.getProvLevel(product_id_v,sub_product_id,prov_flag,0,setprovlevel);
	}
}

function getprovlevel(){
	var product_id_v = DWRUtil.getValue("product_id");
	var sub_product_id = DWRUtil.getValue("sub_product_id");
	var prov_flag = DWRUtil.getValue("prov_flag");

	utilityService.getProvLevel(product_id_v,sub_product_id,prov_flag,0,setprovlevel);
}

function setprovlevel(data){
	var ret = "<select name='prov_level' style='width:230px' onchange='javascript:getGainRate()'>"+data+"</select>";
	document.getElementById("div_prov_level").innerHTML=ret;

	var rg_money = DWRUtil.getValue("rg_money");
	if (rg_money == "") {
		document.theform.expect_ror_lower.value = "";
		document.theform.expect_ror_upper.value = document.theform.expect_ror_lower.value;
	} else {
		getGainRate();
	}
}

//客户选择
function getTransactionCustomer2_1(prefix,readonly) {
    cust_id = getElement(prefix, "cust_id").value;
	var url = '<%=request.getContextPath()%>/marketing/customerInfo.jsp?prefix='+ prefix+ '&cust_id=' + cust_id+'&readonly='+readonly ;
	var v = showModalDialog(url,'','dialogWidth:720px;dialogHeight:720px;status:0;help:0;');
	if (v != null) {
		//showTransactionCustomer2(prefix, v);
		document.theform.customer_cust_name.value = v[0];

		document.theform.ht_cust_name.value = v[0];

		if (v[4]!=null && trim(v[4])!="")
			document.theform.ht_cust_tel.value = trim(v[4]); // mobile
		else if (v[10]!=null && trim(v[10])!="")
			 document.theform.ht_cust_tel.value = trim(v[10]); // o_tel
		else if (v[3]!=null && trim(v[3])!="") 
			 document.theform.ht_cust_tel.value = trim(v[3]); // h_tel
		else 
			document.theform.ht_cust_tel.value = "";

		document.theform.ht_cust_address.value = v[5];
		document.theform.customer_gain_acct.value = v[0];
		
		var product_id_v = DWRUtil.getValue("product_id");
		var sub_product_id = DWRUtil.getValue("sub_product_id");
		
		if (document.getElementById("sub_product_flag").style.display == "none" ||
				sub_product_id=='请选择') 
			sub_product_id = 0;
		getElement(prefix, "cust_id").value = v[7];	
		cust_id_v = getElement(prefix, "cust_id").value;
		//showCustOptions(cust_id_v);
		utilityService.getContractInfo('<%=input_bookCode%>',product_id_v,sub_product_id,cust_id_v,dwrCallBackMeth);
		selectBank("");
	}
}

//客户选择: 证件扫描自动识别时调用
function getTransactionCustomer2_Auto(prefix,readonly){
    cust_id = getElement(prefix, "cust_id").value;
	var url = '<%=request.getContextPath()%>/marketing/customerInfo.jsp?prefix='+ prefix+ '&cust_id=' + cust_id+'&readonly='+readonly +'&pos_flag=2';
	var v = showModalDialog(url,'','dialogWidth:720px;dialogHeight:720px;status:0;help:0;');

	if (v != null){
		showTransactionCustomer2(prefix, v);
	}	
	var product_id_v = DWRUtil.getValue("product_id");
	var sub_product_id = DWRUtil.getValue("sub_product_id");
	
	cust_id_v = getElement(prefix, "cust_id").value;
	showCustOptions(cust_id_v);
	utilityService.getContractInfo('<%=input_bookCode%>',product_id_v,sub_product_id,cust_id_v,dwrCallBackMeth);
}

//申购合同联系人
function showCustOptions(cust_id){
	DWRUtil.removeAllOptions("cust_message");
	DWRUtil.addOptions("cust_message",{"":"请选择"});
	contract.getCustOptions(cust_id,function(date){
			var json=date;
			for(i=0;i<json.length;i++){
	    		DWRUtil.addOptions("cust_message",json[i]);    
	    	}
			DWRUtil.addOptions("cust_message",{"0":"新建"});	
		}				
	);
	
}

//新增合同联系人
function newlyIncreased(value){
	var obj=value;	
	var cust_id = getElement("customer", "cust_id").value;
	if(cust_id==''){
		alert("请先选择客户");
		return false ;
	}
	var url = "<%=request.getContextPath()%>/client/clientinfo/client_contact_new.jsp?cust_id="+cust_id;
	if(obj=="0"){		
		if(showModalDialog(url,'','dialogWidth:900px;dialogHeight:420px;status:0;help:0;scroll:0')){
			showWaitting(0);
			sl_update_ok();
			showCustOptions(cust_id);
		}
	}
}

function dwrCallBackMeth(data){
	var json = eval(data);
	if(json.length==0){
		document.getElementById("contract1").style.display = "";
		var obj = document.theform.applyreach_flag;	
		DWRUtil.removeAllOptions(obj);
		document.theform.contract_msg.value= "";		
		document.theform.display_flag.value = 0;
		document.theform.contract_bh.value = "";
		document.theform.ar_flag.value = 0;
		document.getElementById("trarFlag").style.display = "none";
		
	}else{
		document.getElementById("contract1").style.display = "none";
		var obj = document.theform.applyreach_flag;	
		DWRUtil.removeAllOptions(obj);
		DWRUtil.addOptions(obj,{"":"请选择"});
		if(document.theform.contract_single_flag.value != 1)
			DWRUtil.addOptions(obj,{"new":"新建合同"});
		for(i=0;i<json.length;i++){
			var soption = "[{'"+json[i].serial_no+"$"+json[i].contract_bh+"':"+"'追加到合同编号"+json[i].contract_sub_bh+"受益份额为"+json[i].ben_amount+"的合同'}]";
			var addOption = eval(soption);
			DWRUtil.addOptions(obj,addOption[0]);
		}
		document.theform.ar_flag.value = 1;
		document.getElementById("trarFlag").style.display = "";	
	}
}

//客户有认购记录的可以进行最加 或者新建
function getContractSubBh(){
	var product_id_v = DWRUtil.getValue("product_id");
	var sub_product_id = DWRUtil.getValue("sub_product_id");
	var contract_bh_2 = DWRUtil.getValue("contract_bh_2");
	if(contract_bh_2=="")
		return false;
	
	utilityService.getBankMessage('<%=input_bookCode%>','<%=input_operatorCode%>',product_id_v,sub_product_id,contract_bh_2,dwrCallBackMessage)
}

function arFlagChange(){
	var arFlag = document.theform.applyreach_flag.value;
	var array = arFlag.split("$");
	var product_id_v = DWRUtil.getValue("product_id");
	var sub_product_id = DWRUtil.getValue("sub_product_id");
	if(arFlag == "new"){
		document.theform.display_flag.value = 0;
		document.theform.local_serial_no.value = "";
		document.theform.contract_bh.value = "";
		document.getElementById("contract1").style.display = "";
		document.getElementById("tr_prov_flag").style.display = "";
	}else{
		document.theform.display_flag.value = 1;
		document.theform.local_serial_no.value = array[0];
		document.theform.contract_bh.value = array[1];
		document.getElementById("contract1").style.display = "none";
		utilityService.getBankMessage('<%=input_bookCode%>','<%=input_operatorCode%>',product_id_v,sub_product_id,array[1],dwrCallBackMessage)
	}
	
}

//获得银行帐号 名称
function dwrCallBackMessage(data){
	var rows = document.theform.bank_id.options.length;
	for(i=0;i<rows;i++)	{
		if(document.theform.bank_id.options[i].value==data[0]) {	
			document.theform.bank_id.options[i].selected=true;
			document.theform.bank_acct.value = data[1];
			break;
	    }	
	}
	if(data[2] == 1)
		document.theform.spot_deal.checked = true;
	else
		document.theform.spot_deal.checked = false;
}

//银行帐号 文本 或者 下拉 选择
function changeInput(obj){
	if(document.theform.inputflag.value==1)	{
		obj.innerText="选择";
		document.theform.bank_acct.style.display="";
		document.theform.bank_acct2.style.display="none";
		document.theform.inputflag.value=2;
	} else {
		obj.innerText="输入";
		document.theform.bank_acct.style.display="none";
		document.theform.bank_acct2.style.display="";
		document.theform.inputflag.value=1;
	}
}

//收益分配方式 等于  转份额
function showBonus_rate(){
	var bonus_flag = document.getElementById("bonus_flag").value;
	if(bonus_flag=='3'){
		document.getElementById("bonus_rate_div").style.display = "";
	}else{
		document.getElementById("bonus_rate_div").style.display = "none";
	}
}

//推荐地
function setProductCity(product_id){
	utilityService.getSelectProductCity(product_id,null,{callback:function(data){
		$("select_product_city").innerHTML = "<select size='1' style='width:280px;' name='city_serialno' onkeydown='javascript:nextKeyPress(this)'>"+data+"</SELECT>"
			+"&nbsp;<button type='button'  class='xpbutton4' accessKey=r id='btnSetcity' name='btnSetcity' onclick='javascript:newCity(document.theform.product_id.value);' ><%=LocalUtilis.language("class.promotionSet",clientLocale)%> </button>";
	}});
}

//认购时可以维护产品的推介地，进而可以选取合适的认购地
function newCity(product_id){	
	var ret = showModalDialog('/marketing/sell/product_city_update.jsp?product_id=' + product_id, '', 'dialogWidth:500px;dialogHeight:150px;status:0;help:0');
	if( ret){
		seloption = new Option(ret[1],ret[0]);
		document.all.city_serialno.options[document.all.city_serialno.length] = seloption;
		document.all.city_serialno.options[document.all.city_serialno.length-1].selected = true;
	}
}

//银行合作方式
function showHtyh(){
	if(document.theform.with_bank_flag.checked){
		document.getElementById("htyhmc_v").style.display = "";
	}else{
		//document.getElementById("htyhmc").style.display = "none";
		document.getElementById("htyhmc_v").style.display = "none";
	}
}

//银行帐号输入 显示
function showAcctNum(value){		
	if (trim(value) == "")
		bank_acct_num.innerText = "";
	else
		bank_acct_num.innerText = "(" + showLength(value) + " 位 )";
}

//合同验证
function check1(){	
	var book_code = DWRUtil.getValue("book_code");
	var contract_type = new Number(2);
	var product_id = DWRUtil.getValue("product_id");
	var contract_sub_prefix = document.getElementById("contract_sub_bh_prefix").innerText;
	var contract_sub = DWRUtil.getValue("contract_sub_bh");
	if(document.theform.contract_sub_bh.value!=""){
		contract.findIfExistSameBH(book_code,contract_type,product_id,contract_sub_prefix+contract_sub,callContractSubBHCallBack);
		contract.getSameBHContractInfo(contract_type,product_id,contract_sub_prefix+contract_sub,callSameBHContractInfoBack);
	}else{
		$("contractDIV").innerHTML = "";
	}
}

function callContractSubBHCallBack(result){
	if(result != "")$("contractDIV").innerHTML = result;
}

function callSameBHContractInfoBack(jaso){
	var flag = jaso;
	if(flag!=""&&flag!="0"){
		$("same_contractBH").value=1;
	}else{
		$("same_contractBH").value=0;
	}
}

/************************************************************************************************************/
var comboBoxTree;
var invest_type_name = '';
var intrustType_Flag;
var str;
Ext.BLANK_IMAGE_URL = '<%=request.getContextPath()%>/widgets/ext/resources/images/default/tree/s.gif';

var comboBoxParentTree;
Ext.BLANK_IMAGE_URL = '<%=request.getContextPath()%>/widgets/ext/resources/images/default/tree/s.gif';

Ext.onReady(function(){
	comboBoxTree = new Ext.ux.ComboBoxTree({
		renderTo : 'comboBoxTree',
		width:280,
		tree : {
			xtype:'treepanel',
			width:280,
			loader: new Ext.tree.TreeLoader({dataUrl:'<%=request.getContextPath()%>/client/channel/treeData.jsp'}),
       	 	root : new Ext.tree.AsyncTreeNode({id:'-1',text:'<%=LocalUtilis.language("class.partnType",clientLocale)%> '}),//渠道信息
       	 	listeners:{
       	 		beforeload:function(node){//选择树结点设值之前的事件   
       	 			if(node.id!='-1')
       	 				this.loader.dataUrl = '<%=request.getContextPath()%>/client/channel/treeData.jsp?value='+node.id;
       	 		},   
       	 		click:function(node)
       	 		{
           	 		channelTree(node.id);
           	 	}
       	 	}
    	},
    	
		//all:所有结点都可选中
		//exceptRoot：除根结点，其它结点都可选(默认)
		//folder:只有目录（非叶子和非根结点）可选
		//leaf：只有叶子结点可选
		selectNodeModel:'leaf'
	});
}); 

Ext.onReady(function(){
	comboBoxParentTree = new Ext.ux.ComboBoxTree({
		renderTo : 'comboBoxParentTree',
		width:230,
		tree : {
			xtype:'treepanel',
			width:230,
			loader: new Ext.tree.TreeLoader({dataUrl:'<%=request.getContextPath()%>/client/channel/parentTree.jsp?channel_type='+comboBoxTree.getValue()}),
       	 	root : new Ext.tree.AsyncTreeNode({id:'-1',text:'<%=LocalUtilis.language("class.partnName",clientLocale)%> '}),//渠道信息
       	 	listeners:{
       	 		beforeload:function(node){
       	 			if(node.id!='-1')
       	 				this.loader.dataUrl = '<%=request.getContextPath()%>/client/channel/parentTree.jsp?value='+node.id+'&channel_type='+comboBoxTree.getValue();
       	       	 }
       	 	}
    	},
    	
		//all:所有结点都可选中
		//exceptRoot：除根结点，其它结点都可选(默认)
		//folder:只有目录（非叶子和非根结点）可选
		//leaf：只有叶子结点可选
		selectNodeModel:'exceptRoot'
	});
}); 

function channelTree(channel_type){
	document.getElementById('comboBoxParentTree').innerHTML = '';
	comboBoxParentTree = new Ext.ux.ComboBoxTree({
		renderTo : 'comboBoxParentTree',
		width:230,
		tree : {
			xtype:'treepanel',
			width:230,
			loader: new Ext.tree.TreeLoader({dataUrl:'<%=request.getContextPath()%>/client/channel/parentTree.jsp?channel_type='+channel_type}),
       	 	root : new Ext.tree.AsyncTreeNode({id:'-1',text:'<%=LocalUtilis.language("class.partnName",clientLocale)%> '}),//渠道信息
       	 	listeners:{
       	 		beforeload:function(node){
       	 			if(node.id!='-1')
       	 				this.loader.dataUrl = '<%=request.getContextPath()%>/client/channel/parentTree.jsp?value='+node.id+'&channel_type='+comboBoxTree.getValue();
       	       	 }
       	 	}
    	},
    	
		//all:所有结点都可选中
		//exceptRoot：除根结点，其它结点都可选(默认)
		//folder:只有目录（非叶子和非根结点）可选
		//leaf：只有叶子结点可选
		selectNodeModel:'exceptRoot'
	});
}

/************************************************************************************************************/
var acct_arr = [];

function selectBank(value) { //选择银行	
	if(!sl_checkChoice(theform.product_id, "产品名称"))		return false;
	var cust_id = DWRUtil.getValue("customer_cust_id");
	var obj = document.theform.bank_acct2;
	utilityService.getBankAcctOption(cust_id,value,'','',{callback:function(data){
		DWRUtil.removeAllOptions(obj);  
		var json = eval(data);
		acct_arr = json;
		if(json.length>0){
			document.theform.inputflag.value = 2;
		}else{
			document.theform.inputflag.value = 1;		
		}
		changeInput(document.theform.btnSelect);
		obj.options.add(new Option("请选择", ""));
		DWRUtil.addOptions(obj, json, "BANK_ACCT");	
	}});
}

//保存
var j$ = jQuery.noConflict();
j$(function(){    
    j$("form[name='theform']").FormValidate({        
        btnSubmit:"#btnSave",
        twiceFlag:1,
        checkFunc:{
        },
        executeFunc:{
        },
        init:{
            <%if(Argument.getSyscontrolValue("FV2WARRANT")==2){%>
            normal:eval('<%=Argument.getRequiredItemJson(menu_id,"10012",new Integer(1))%>'),    
            twice:eval('<%=Argument.getRequiredItemJson(menu_id,"10012",new Integer(3))%>')
            <%}%>
        },
        dataInit:function(){
            subAll(2);     
        }               
    });
});   


function subAll(flag){
	var is_sub_flag = <%=is_product_sub_flag%>;//子产品开关；	

	if(!sl_checkChoice(theform.product_id, "产品名称"))		return false;

	if(theform.customer_cust_id.value=="") {
		sl_alert("请选择客户！");
		return false;
	}	
	if(theform.ar_flag.value!=0)
		if(!sl_checkChoice(theform.applyreach_flag,"申购方式")) return false;
	
	if (! sl_checkChoice(theform.contract_type, "合同分类")) return false;

	if(DWRUtil.getValue("same_contractBH")==1&&DWRUtil.getValue("contract_sub_bh")!=""){
		sl_alert("合同编号已被使用!");
		return false;
	}
	
	//if(!sl_checkChoice(theform.bank_id, "银行名称"))		return false;
	if(theform.bank_id.value != ""){
		if(document.theform.inputflag.value==1){
			if(!sl_checkChoice(theform.bank_acct2, "<%=LocalUtilis.language("class.bankAcct3",clientLocale)%> "))return false;//银行帐号
		}
		else{
			if(!sl_check(theform.bank_acct, "<%=LocalUtilis.language("class.bankAcct3",clientLocale)%> ", 80, 1)) return false;//银行帐号
			theform.bank_acct.value = trimString(theform.bank_acct.value); 
		}		
	}

	if(!sl_checkDecimal(theform.rg_money, "申购金额", 13, 3, 1))	return false;
	if(!sl_checkChoice(theform.jk_type, "缴款方式"))		return false;
	
	//if(!theform.valid_period.disabled){
	//	if(!sl_checkNum(theform.valid_period, "合同期限", 10, 1))	return false;
	//}
	
	if(<%=user_id.intValue()%>==22){
		if(!sl_checkChoice(theform.city_serialno, '<%=LocalUtilis.language("class.citySerialNO",clientLocale)%>')){return false;}//合同推介地
	}
	if(!sl_checkDate(theform.qs_date_picker,"签署日期"))	return false;
	if(!sl_checkDate(theform.jk_date_picker,"缴款日期"))	return false;
	//if(!sl_checkDate(theform.start_date_picker,"起始日期"))	return false;
	if(!sl_check(theform.summary2, "合同备注", 100, 0))	return false;

	if(DWRUtil.getValue("sub_flag") == 1 && is_sub_flag == 1){
	    if(!sl_checkChoice(theform.sub_product_id, "子产品名称"))		return false;
		if(!(theform.min_buy_limit.value == 0 && theform.max_buy_limit.value == 0) && !(sl_parseFloat(theform.min_buy_limit.value)!=0 && sl_parseFloat(theform.max_buy_limit.value)==0)){
			if(parseFloat(theform.rg_money.value) < parseFloat(theform.min_buy_limit.value) || parseFloat(theform.rg_money.value) > parseFloat(theform.max_buy_limit.value)){
				sl_alert("认购金额应该在"+theform.min_buy_limit.value+"到"+ theform.max_buy_limit.value +"范围内");
				return false;
			}
		}
		if(sl_parseFloat(theform.min_buy_limit.value)!=0 && sl_parseFloat(theform.max_buy_limit.value)==0){
			if(parseFloat(theform.rg_money.value) < parseFloat(theform.min_buy_limit.value)){
				sl_alert("认购金额应该大于"+theform.min_buy_limit.value);
				return false;
			}
		}
	}

	syncDatePicker(theform.qs_date_picker, theform.qs_date);
	syncDatePicker(theform.jk_date_picker, theform.jk_date);
	//syncDatePicker(theform.start_date_picker, theform.start_date);

	//document.theform.channel_type.value = comboBoxTree.getValue();
	//document.theform.channel_id.value = comboBoxParentTree.getValue();

	var userid='<%=user_id.intValue()%>';
	if(userid=="5") {
		stouchtypes='';
		for(i=0;i<theform.touch_type.length;i++) {
			if(theform.touch_type[i].checked) {
				stouchtypes=stouchtypes+theform.touch_type[i].value+';';
			}
		}
		theform.touchtype.value=stouchtypes;
	}

	if(theform.with_bank_flag.checked){
		theform.with_bank_flag.value = 1;
	}
	if(theform.with_security_flag.checked){
		theform.with_security_flag.value = 1;
	}
	if(theform.with_private_flag.checked){
		theform.with_private_flag.value = 1;
	}

	var bonus_flag = document.getElementById("bonus_flag").value;
	if(bonus_flag=='3'){
		if(!sl_checkDecimal(theform.bonus_rate, "转份额比例", 3, 2, 0))	return false;
		if(parseFloat(theform.bonus_rate.value) <= parseFloat('0') || parseFloat(theform.bonus_rate.value) >= parseFloat('100')){
			sl_alert("转份额比例取值必须为0到100之间(不包含0和100)");
			return false;
		}
	}

	if(confirm("你确认要保存修改吗?")){
		disableAllBtn(true);
		document.theform.method = "POST";
		document.theform.submit();
	}
}

var n=1;
/*
 *添加附件	
 */
function addline(){
	n++;
	newline=document.all.test.insertRow()
	newline.id="fileRow"+n;
	newline.insertCell().innerHTML="<input type='file' name=file_info"+n+" size='70'>&nbsp;"+"<button type='button' class='xpbutton3' onclick='javascript:removeline(this)'><%=LocalUtilis.language("messgae.remove",clientLocale)%> </button>";//移除
}

/*
 *删除附件	
 */
function removeline(obj){
	var objSourceRow=obj.parentNode.parentNode;
	var objTable=obj.parentNode.parentNode.parentNode.parentNode;
	objTable.lastChild.removeChild(objSourceRow);
}

function openQuery(product_id,item_id){	
	showModalDialog('../base/product_list_detail.jsp?product_id='+product_id+'&item_id='+item_id, '','dialogWidth:950px;dialogHeight:580px;status:0;help:0');
}

function openSubProduct(sub_product_id){	
	var product_id = document.getElementsByName("product_id")[0].value;
	var sub_product_id = document.getElementsByName("sub_product_id")[0].value;
	
	if(product_id!=null&&product_id!=0&&sub_product_id!=null&&sub_product_id!=0){
		var url = "<%=request.getContextPath()%>/marketing/base/sub_product_view.jsp?product_id="+product_id+"&sub_product_id="+sub_product_id;
		showModalDialog(url, '', 'dialogWidth:600px;dialogHeight:420px;status:0;help:0');
	}else{
		sl_alert("请选择产品！");
	}  
}

function setMartetTrench(obj){
	if(obj.value != ""){
		var items = obj.value.split("@");
		document.theform.channel_type.value = items[0];
		document.theform.channel_id.value = items[1];
		document.theform.channel_rate.value = items[2];
		var rg_money = document.theform.rg_money.value;
		if(rg_money != "")
			document.theform.market_trench_money.value = (Number(rg_money) * Number(items[2])).toFixed(2);
		else
			document.theform.market_trench_money.value = "";
	}else{
		document.theform.market_trench_money.value = "";
	}
}

function autoSetGainLevel(data) {
	var rg_money = DWRUtil.getValue("rg_money");
	if (rg_money == "") {
		document.theform.expect_ror_lower.value = "0.0";
		document.theform.expect_ror_upper.value = document.theform.expect_ror_lower.value;
		return;
	}

	var json = eval(data);	
	var i;
	var matched = [];
	for (i=0; i<json.length; i++) {
		if (rg_money>=json[i].LOWER_LIMIT && rg_money<=json[i].UPPER_LIMIT) {			
			matched.push(i);
		}
	}
	if (matched.length==0) { // 找不到匹配的
		document.theform.expect_ror_lower.value = "0.0";
		document.theform.expect_ror_upper.value = document.theform.expect_ror_lower.value;

	} else if (matched.length==1) {
		i = matched[0];

		var prov_flag = document.theform.prov_flag;
		for (var j=0; j<prov_flag.options.length; j++) {
			if (prov_flag.options[j].value == json[i].PROV_FLAG) {
				prov_flag.selectedIndex = j;
				break;
			}			
		}

		var prov_level = document.theform.prov_level;
		for (var j=0; j<prov_level.options.length; j++) {
			if (prov_level.options[j].value == json[i].PROV_LEVEL) {
				prov_level.selectedIndex = j;
				break;
			}
		}

		document.theform.expect_ror_lower.value = Math.round(json[i].GAIN_RATE*10000)/100;
		document.theform.expect_ror_upper.value = document.theform.expect_ror_lower.value;

	} else { // matched.length>1
		document.theform.expect_ror_lower.value = "";
		document.theform.expect_ror_upper.value = document.theform.expect_ror_lower.value;
	}
}

function checkSellInfo(){
	var channel_rate = document.theform.channel_rate.value
	var rg_money = document.theform.rg_money.value;
	if(rg_money != "" && channel_rate != "")
		document.theform.market_trench_money.value = (Number(rg_money) * Number(channel_rate)).toFixed(2);
	else
		document.theform.market_trench_money.value = "";

	var product_id = DWRUtil.getValue("product_id");
	if (document.getElementById("sub_product_flag").style.display=="none") {
		utilityService.getSubProductProvFlag2(product_id, 0, autoSetGainLevel);//受益优先级、收益级别
	} else {
		var sub_product_id = document.theform.sub_product_id.value;
		utilityService.getSubProductProvFlag2(product_id, sub_product_id, autoSetGainLevel);//受益优先级、收益级别
	}
}

/*通过省市过滤相关的行政区域*/
function setGovRegional(value,flag){
	if(value != "" && value.length > 0){
		utilityService.getGovRegional(9999, value, flag,backGovRegional);
	}
}

/*通过省市过滤相关的行政区域 回调函数*/
function backGovRegional(data){
	document.getElementById("gov_regional_span").innerHTML = "<select size='1'  name='bank_city' style='width: 150px' onkeydown='javascript:nextKeyPress(this)'>"+data+"</select>";
}
/*搜索产品*/
function searchProduct(value){
	if(event.keyCode == 13){
		searchProduct2(value);
	}
}
/*搜索产品2*/
function searchProduct2(value){
	var prodid=0;
	if( value != ""){
        var j = value.length;
		for(i=0;i<document.theform.product_id.options.length;i++){
			if(document.theform.product_id.options[i].text.indexOf(value) >= 0) {
				document.theform.product_id.options[i].selected=true;
				prodid = document.theform.product_id.options[i].value;
				if(prodid!=0)
					selectProduct();
				break;
			}
		}
		if (prodid==0){
			sl_alert('<%=LocalUtilis.language("message.idIsNotExists",clientLocale)%> ！');//输入的产品编号不存在
			document.theform.product_id.options[0].selected=true;
		}
	}
	
}

//新建产品渠道
function newChannel(){
	if(DWRUtil.getValue("product_id")==0){
		alert("请先选择产品");return false;
	}else if(DWRUtil.getValue("sub_flag")==1&&DWRUtil.getValue("sub_product_id")==0){
		alert("请先选择子产品");return false;
	}else{
		if(showModalDialog('/marketing/base/product_market_trench_add.jsp?productId='+ DWRUtil.getValue("product_id") +'&sub_flag='+DWRUtil.getValue("sub_flag") +'&productName='+$("product_id").options[$("product_id").selectedIndex].text+'&sub_product_id='+ DWRUtil.getValue("sub_product_id"), '', 'dialogWidth:400px;dialogHeight:400px;status:0;help:0') != null){
		sl_update_ok();
		//产品销售渠道信息
		utilityService.queryMarketTrench(DWRUtil.getValue("product_id"),DWRUtil.getValue("sub_product_id"),matchCustCallBack);
		}
	}
}

function bankAcctView(bank_acct){
	var acct_view  = '';
	bank_acct = trimString(bank_acct);//去除所有空格
	if(bank_acct!=null && bank_acct.length>4){
		for(i=0;i<bank_acct.length;i++){			
			if(i%4==0 && i!=0){
				acct_view = acct_view+' '+bank_acct.charAt(i);
			}else{
				acct_view =  acct_view+bank_acct.charAt(i);
			}
		}
		document.theform.bank_acct.value = acct_view;
	}
}

function getGainRate() {
	var product_id = DWRUtil.getValue("product_id");
	if (document.getElementById("sub_product_flag").style.display=="none") {
		utilityService.getSubProductProvFlag2(product_id, 0, autoSetGainLevel2);//受益优先级、收益级别
	} else {
		var sub_product_id = document.theform.sub_product_id.value;
		utilityService.getSubProductProvFlag2(product_id, sub_product_id, autoSetGainLevel2);//受益优先级、收益级别
	}
}

function autoSetGainLevel2(data) {
	var prov_flag = document.theform.prov_flag.value;
	var prov_level = document.theform.prov_level.value;

	var json = eval(data);	
	var i;
	for (i=0; i<json.length; i++) {
		if (prov_flag==json[i].PROV_FLAG && prov_level==json[i].PROV_LEVEL) {			
			break;
		}
	}
	if (i<json.length) {
		var gain_rate = json[i].GAIN_RATE*100;
		document.theform.expect_ror_lower.value = Math.round(gain_rate * 100) / 100;
		document.theform.expect_ror_upper.value = document.theform.expect_ror_lower.value;
	}
}

function autoSetAcctInfo(sel) {
	if (sel.value=="") {
		document.theform.bank_sub_name.value = "";
		document.theform.bank_province.options.selectedIndex = 0;
		document.theform.bank_city.options.selectedIndex = 0;		
	} else {
		var i;
		for (i=0; i<acct_arr.length; i++)
			if (acct_arr[i].BANK_ACCT==sel.value) {
				document.theform.bank_sub_name.value = acct_arr[i].SUB_BANK_NAME;

				var j;
				for (j=0; j<document.theform.bank_id.options.length; j++) 
					if (document.theform.bank_id.options[j].value == acct_arr[i].BANK_ID) {
						document.theform.bank_id.options.selectedIndex = j;
						break;
					}
				if (j==document.theform.bank_id.options.length) 
					document.theform.bank_id.options.selectedIndex = 0;

				for (j=0; j<document.theform.bank_province.options.length; j++) 
					if (document.theform.bank_province.options[j].text == acct_arr[i].BANK_PROVINCE) {
						document.theform.bank_province.options.selectedIndex = j;						
						setGovRegional(document.theform.bank_province.value, "");
						break;
					}
				if (j==document.theform.bank_province.options.length) 
					document.theform.bank_province.options.selectedIndex = 0;
				
				for (j=0; j<document.theform.bank_city.options.length; j++) 
					if (document.theform.bank_city.options[j].value == acct_arr[i].BANK_CITY) {
						document.theform.bank_city.options.selectedIndex = j;
						break;
					}
				if (j==document.theform.bank_city.options.length) 
					document.theform.bank_city.options.selectedIndex = 0;

				break;
			}		
		if (i==acct_arr.length) {
			document.theform.bank_sub_name.value = "";
			document.theform.bank_province.options.selectedIndex = 0;
			document.theform.bank_city.options.selectedIndex = 0;	
		}		
	}
}
function readCard(btn) {
	var rg_money = parseFloat(document.theform.rg_money.value);
	var pos_product_id=document.theform.product_id.value;
	var pos_cust_id=document.theform.customer_cust_id.value;
	if (pos_product_id=="" || pos_product_id=="0"){
		alert("请先选择产品");
		document.theform.product_id.focus();
		return;
	}
	if (pos_cust_id=="" || pos_cust_id=="0"){
		alert("请先选择客户");
		return;
	}
	//alert(rg_money+" "+isNaN(rg_money)); // NaN
	if (!isNaN(rg_money) && rg_money>0) {
		var obj = showModalDialog('read_pos_card.jsp?rg_money='+rg_money+'&product_id='+pos_product_id+'&cust_id='+pos_cust_id, ''
					,'dialogWidth:470px;dialogHeight:240px;status:0;help:0');
		if (obj.card_no!="") {
			if (document.theform.bank_acct.style.display=="none") 
				changeInput(document.theform.btnSelect);
								
			document.theform.bank_acct.value = obj.card_no;
		}
	} else {
		sl_alert("请先填写有效的认购金额！");
		document.theform.rg_money.focus();
		document.theform.rg_money.select();
	}
}



//选择资金来源后，显示下级选项
function moneyOriginChange(money_origin){
	var sub_money_origin = document.theform.sub_money_origin;
	
	DWRUtil.removeAllOptions(sub_money_origin);
	utilityService.getSubMoneyOriginJson(1158, money_origin, function(data){
		var json = eval(data);
		if (json.length>0){
			sub_money_origin.style.display="";
		}else{
			sub_money_origin.style.display="none";
		}
		DWRUtil.addOptions(sub_money_origin,{"0":"请选择"});
		for(i=0;i<json.length;i++){
			DWRUtil.addOptions(sub_money_origin,json[i]);
	  }
  });
}

function show_property_souce(){
	var urls="property_souce_list.jsp?serial=0&property_souce_str="+document.theform.property_souce.value+"^"+document.theform.other_explain.value;
	var ret = showModalDialog(urls, '', 'dialogWidth:500px;dialogHeight:480px;status:0;help:0');
	if (typeof(ret)!="undefined"){
		var s1=ret.split("^");
		//alert(s1[1]);
		document.theform.property_souce.value =s1[0];
		if (typeof(s1[1])!="undefined"){
			document.theform.other_explain.value =s1[1];
		}
	}
}
//Description:  银行卡号Luhm校验

    //Luhm校验规则：16位银行卡号（19位通用）:
    
    // 1.将未带校验位的 15（或18）位卡号从右依次编号 1 到 15（18），位于奇数位号上的数字乘以 2。
    // 2.将奇位乘积的个十位全部相加，再加上所有偶数位上的数字。
    // 3.将加法和加上校验位能被 10 整除。
function luhmCheck(bankno){
	bankno = trimString(bankno);
	if (bankno.length < 16 || bankno.length > 19 || bankno.length<0) {
		alert("银行卡号长度必须在16到19之间");
		document.getElementById("bank_acct").style.color ="red";
		return false;
	}
	var num = /^\d*$/;  //全数字
	if (!num.exec(bankno)) {
		alert("银行卡号必须全为数字");
		document.getElementById("bank_acct").style.color ="red";
		return false;
	}
	//alert("------1------");
	//开头6位
	var strBin="10,18,30,35,37,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,58,60,62,65,68,69,84,87,88,94,95,98,99";    
	if (strBin.indexOf(bankno.substring(0, 2))== -1) {
		alert("银行卡号开头6位不符合规范");
		document.getElementById("bank_acct").style.color ="red";
		return false;
	}
    var lastNum=bankno.substr(bankno.length-1,1);//取出最后一位（与luhm进行比较）

    var first15Num=bankno.substr(0,bankno.length-1);//前15或18位
    var newArr=new Array();
    for(var i=first15Num.length-1;i>-1;i--){    //前15或18位倒序存进数组
        newArr.push(first15Num.substr(i,1));
    }
    var arrJiShu=new Array();  //奇数位*2的积 <9
    var arrJiShu2=new Array(); //奇数位*2的积 >9
    
    var arrOuShu=new Array();  //偶数位数组
    for(var j=0;j<newArr.length;j++){
        if((j+1)%2==1){//奇数位
            if(parseInt(newArr[j])*2<9)
            arrJiShu.push(parseInt(newArr[j])*2);
            else
            arrJiShu2.push(parseInt(newArr[j])*2);
        }
        else //偶数位
        arrOuShu.push(newArr[j]);
    }
    //alert("------2------");
    var jishu_child1=new Array();//奇数位*2 >9 的分割之后的数组个位数
    var jishu_child2=new Array();//奇数位*2 >9 的分割之后的数组十位数
    for(var h=0;h<arrJiShu2.length;h++){
        jishu_child1.push(parseInt(arrJiShu2[h])%10);
        jishu_child2.push(parseInt(arrJiShu2[h])/10);
    }        
    
    var sumJiShu=0; //奇数位*2 < 9 的数组之和
    var sumOuShu=0; //偶数位数组之和
    var sumJiShuChild1=0; //奇数位*2 >9 的分割之后的数组个位数之和
    var sumJiShuChild2=0; //奇数位*2 >9 的分割之后的数组十位数之和
    var sumTotal=0;
    for(var m=0;m<arrJiShu.length;m++){
        sumJiShu=sumJiShu+parseInt(arrJiShu[m]);
    }
    
    for(var n=0;n<arrOuShu.length;n++){
        sumOuShu=sumOuShu+parseInt(arrOuShu[n]);
    }
    
    for(var p=0;p<jishu_child1.length;p++){
        sumJiShuChild1=sumJiShuChild1+parseInt(jishu_child1[p]);
        sumJiShuChild2=sumJiShuChild2+parseInt(jishu_child2[p]);
    }      
    //计算总和
    sumTotal=parseInt(sumJiShu)+parseInt(sumOuShu)+parseInt(sumJiShuChild1)+parseInt(sumJiShuChild2);
    //alert("------3------");
    //计算Luhm值
    var k= parseInt(sumTotal)%10==0?10:parseInt(sumTotal)%10;        
    var luhm= 10-k;
    //alert(lastNum+"----"+luhm);
    if(lastNum==luhm){
    //alert("卡号验证通过");
	document.getElementById("bank_acct").style.color ="";
    return true;
    }
    else{
    alert("银行卡号校验不通过");
	document.getElementById("bank_acct").style.color ="red";
    return false;
    }        
}
</script>
</HEAD>
<BODY class="BODY body-nox">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="post" action="apply_purchase_add.jsp" ENCTYPE="multipart/form-data">

<!-- 客户信息-->
<input type="hidden" name="customer_cust_id" id="customer_cust_id" value="">

<input type="hidden" name="asfund_flag" value="">
<input type="hidden" name="intrust_flag1" value="">

<!-- 子产品的合同最低金额,最高金额-->
<input type="hidden" name="open_flag" value="0">
<input type="hidden" name="sub_flag" value="">
<input type="hidden" name="is_local" value="1">
<input type="hidden" name="min_buy_limit" value="">
<input type="hidden" name="max_buy_limit" value="">
<input type="hidden" name="contract_msg" value="<%//=contract_msg%>">
<input type="hidden" name="ar_flag" value="0">
<input type="hidden" name="display_flag" value="<%=display_flag%>">
<input type="hidden" name="local_serial_no" value="<%=local_serial_no%>">
<input type="hidden" name="inputflag" value="<%=inputflag%>">
<input type="hidden" name="same_contractBH" id="same_contractBH" value="0">
<input type="hidden" id="channel_type" name="channel_type" value=""/>
<input type="hidden" id="channel_id" name="channel_id" value=""/>
<input type="hidden" id="channel_rate" name="channel_rate" value=""/>
<input type="hidden" id="contract_single_flag" name="contract_single_flag"/>
<input type="hidden" name="property_souce" value=""/>
<input type="hidden" name="other_explain" value=""/>
<div align="left" class="page-title">
	<font color="#215dc6"><b><%=menu_info%></b></font>
</div>
<br/>
<!--产品选择-->
<div>
<table border="0" width="100%" cellspacing="5" cellpadding="4"  class="product-list">
	<tr>
		<td align="right">产品搜索:</td>
		<td>
			<input type="text" name="productcode" value="" onkeydown="javascript:searchProduct(this.value);" size="15">&nbsp;
			<button type="button"   class="searchbutton" onclick="javascript:searchProduct2(document.theform.productcode.value);" />
		</td>
	</tr>
	<tr>
		<td align="right" width="15%"><b><font color="red">*</font><%=LocalUtilis.language("class.productNumber",clientLocale)%>:</b></td><!--产品选择-->
		<td align=left colspan=3>
			<select size="1" id="product_id" name="product_id" onkeydown="javascript:nextKeyPress(this)" class="productname" onchange="javascript:selectProduct();">
					<%=Argument.getCRMProductListOptions(new Integer(2),product_id,"110203",new Integer(101),input_operatorCode)%><!--正常期产品，非单一的-->
			</select>
			<!-- 
			<button type="button"  name="show_info" title="查看产品信息" onclick="javascript:openQuery('<%//=product_id%>','<%//=item_id%>');">查看</button>
			 -->
		</td>
	</tr>
	<tr id="sub_product_flag" style="display: none;">
		<td align="right"><b><font color="red">*</font><%=LocalUtilis.language("class.subProductID",clientLocale)%> </b></td><!--子产品选择-->
		<td align=left colspan=3 id="select_id_2">
		<!-- 	
		<button type="button"  name="show_sub_info" title="查看子产品信息" onclick="javascript:openSubProduct(document.theform.sub_product_id);"> 查看</button>
		 -->
		</td>
	</tr>
	<tr id="sylb" style="display:none">
		<td></td><td>受益类别:<input type="text" class=edline name="sylb_v" readonly value=""></td>
	</tr>
</table>
<br>
<table border="0" width="100%" cellspacing="5" cellpadding="4"  class="product-list">	
	<tr>
		<td colspan="2"><b>客户信息</b>			
       </td>
	</tr>
	<tr>
		<td align="right" width="15%"><font color="red">*</font>委托人:</td> 
		<td width="*"><input readonly class='edline'  name="customer_cust_name" size="25" onkeydown="javascript:nextKeyPress(this);" value=""/>
			&nbsp;&nbsp;&nbsp;<button type="button"  class="xpbutton3" accessKey=e name="btnEdit" title="客户信息" onclick="javascript:getTransactionCustomer2_1('customer',0);">选择/查看</button>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%">合同联系人:</td>
		<td width="*">
			<input type="text" name="ht_cust_name" size="25" onkeydown="javascript:nextKeyPress(this)" value=""/>
			&nbsp;&nbsp;&nbsp;&nbsp;电话:<input type="text" name="ht_cust_tel" size="20" onkeydown="javascript:nextKeyPress(this)" value=""/>		
			&nbsp;&nbsp;&nbsp;&nbsp;地址:<input type="text" name="ht_cust_address" size="50" onkeydown="javascript:nextKeyPress(this)" value=""/>			
		</td>
	</tr>
	<tr id="trarFlag" style="display:none">
		<td align="right" width="15%"><b>申购方式</b></td>
		<td width="*">
			<select name="applyreach_flag" onchange="javascript:arFlagChange();" style="width:300px">
			</select>	
		</td>
	</tr>
</table>
<br>
<table border="0" width="100%" cellspacing="5" cellpadding="4"  class="product-list">	
	<tr>
		<td colspan="3"><b>合同信息</b>
	</tr>
	<tr>
		<td align=right><font color="red"><b>*</b></font>合同分类 : </td>
		<td colspan="3">
			<select size="1" name="contract_type" onkeydown="javascript:nextKeyPress(this)" style="width:150px;">
				<option value="0" <%if(contract_type.intValue() == 0){%>selected<%}%> >请选择</option>
				<option value="1" <%if(contract_type.intValue() == 1){%>selected<%}%> >前台销售人员合同</option>
				<option value="2" <%if(contract_type.intValue() == 2){%>selected<%}%> >产品部门合同</option>
				<option value="3" <%if(contract_type.intValue() == 3){%>selected<%}%> >代销合同</option>
			</select>
		</td>
	</tr>

	<tr id="contract1" >
		<td align="right" width="11%" <%if(Argument.getSyscontrolValue_intrust("C30902")==1){out.print("style='display:none'");}%>><b>合同序号:</b></td> 
		<td width="35%" <%if(Argument.getSyscontrolValue_intrust("C30902")==1){out.print("style='display:none'");}%>>
			<input name="contract_bh" size="20" maxlength=20 onkeydown="javascript:nextKeyPress(this)" value="">
		</td>
		<td align="right" width="11%">合同编号:</td>
		<td width="35%">
<%
String contractSubBhPrefix = Argument.getContractSubBhPrefix(product_id);
if (contractSubBhPrefix.endsWith("号"))
	contractSubBhPrefix = contractSubBhPrefix.substring(0, contractSubBhPrefix.length()-1);
 %>
			<span id="contract_sub_bh_prefix"><%=contractSubBhPrefix%></span>
			<input name="contract_sub_bh" size="36" maxlength=40 onkeydown="javascript:nextKeyPress(this)" value="" onblur="javascript:check1();">
			<INPUT type="button" class="xpbutton3" onclick="javascript:check1();" value="验证编号"><div id="contractDIV"></div>					
		</td>		
	</tr>
<%if (user_id.intValue()==2 /*2北国投*/|| user_id.intValue()==999) { %>
	<tr>
		<td align="right">合同期限:</td>
		<td colspan="3">
			<input type="text" name="valid_period" size="5"/>
			<select name="period_unit" id="period_unit"><%=Argument.getPeriodValidUnitOptions(period_unit)%></select>
		</td>
	</tr>
<%}%>
	<tr>
		<td align="right" width="15%">银行帐号:</td>
		<td width="39%">
			<input <%if(inputflag==1) out.print("style=display:none");%> type="text" name="bank_acct" onkeydown="javascript:nextKeyPress(this)" 
				onkeyup="javascript:showAcctNum(this.value)" onblur="javascript:bankAcctView(this.value);luhmCheck(this.value);" size="28">
			<select <%if(inputflag==2) out.print("style=display:none");%> size="1" name="bank_acct2"  onkeydown="javascript:nextKeyPress(this)"  
				onchange="javascript:autoSetAcctInfo(this);" style="WIDTH: 170px">
			</select>&nbsp;&nbsp;										
			<button type="button"   class="xpbutton2" accessKey=x name="btnSelect" title="输入" onclick="javascript:changeInput(this);">输入(<u>X</u>)</button> 
			<span id="bank_acct_num" class="span"></span> 
		</td>
		<td align="right" width="11%">银行名称:</td>
		<td width="35%">
			<select size="1" name="bank_id"  onchange="javascript:selectBank(this.value);"  style="WIDTH: 150px">
				<%=Argument.getBankOptions("")%>
			</select>	
			<input type="text" name="bank_sub_name" size="20" onkeydown="javascript:nextKeyPress(this)" value="">
		</td>				
	</tr>			
	<tr>
		<td align="right">开户行所在地:</td>
		<td>
			<select size="1"  name="bank_province" style="width: 130px" onkeydown="javascript:nextKeyPress(this)" onchange="javascript:setGovRegional(this.value,'');">
				<%=Argument.getCustodianNameLis(new Integer(9999), "", new Integer(0),"")%>
			</select>
			<span id="gov_regional_span">
				<select style="WIDTH: 150px" name="bank_city">
					<%=Argument.getCustodianNameLis(new Integer(9999), "", new Integer(0),"")%>
				</select>
			</span><!-- 省级行政区域过滤出来的相关区域 -->
		</td>
		<td align="right">推荐人:</td>
		<td>
			<select name="recommend_man" style="width:230px;" onkeydown="javascript:nextKeyPress(this)" >
				<%=Argument.getIntrustOptions(null,new Integer(1)) %>				
			</select>
		</td>
	</tr>
	<!--反洗钱 添加-->
	<tr>
		<td align="right" width="11%">银行账号类型:</td>
		<td width="35%"><select size="1" name="bank_acct_type" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 280px">
				<%=Argument.getBankAcctType("")%>
			</select>						
		</td>
		<td align="right" width="15%">银行帐号户名:</td>
		<td width="39%"><input name="customer_gain_acct" <%if(user_id.intValue()==8){%>class="edline" readonly<%}%> size="1"style="WIDTH: 230px" onkeydown="javascript:nextKeyPress(this)" 
			value="" >
		</td>
	</tr>

	<tr>
	<td align="right"><font color="red">*</font>缴款方式:</td>
		<td><select size="1" name="jk_type" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 280px">
				<%=Argument.getJkTypeOptions("111401")%>
			</select>
		</td>
		<td align="right">收益分配方式:</td>
		<td>
			<select size="1" name="bonus_flag" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 230px" onchange="javascript:showBonus_rate();">
				<%=Argument.getBonus_flag(new Integer(0))%>
			</select>
			<span id="bonus_rate_div" style='display:none'>转份额比例:
				<input type="text" name="bonus_rate" size="10" onkeydown="javascript:nextKeyPress(this)" >%
			</span>		
		</td>
	</tr>
	
	<tr>
		<td align="right"><font color="red">*</font>申购金额:</td>
		<td>
			<input name="rg_money" size="51" value="" onkeydown="javascript:nextKeyPress(this)" 
					onkeyup="javascript:showCnMoney(this.value,rg_money_cn)" onblur="javascript:checkSellInfo();">
			<%if (user_id.intValue()==24/*厦门24*/) { %>
			&nbsp;&nbsp;<button type="button"  class="xpbutton2" onclick="javascript:readCard(this);">刷卡</button>
		<%} %>
		</td>
		<td align="right" id="sg_jk_name">申购费扣缴方式:</td>
		<td id="sg_jk_value">
			<select size="1" name="fee_jk_type" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 230px">
				<%=Argument.getFeeJkTypeOptions(0)%>
			</select>
		</td>
	</tr>
	<tr>
		<td align="right"></td>
		<td colspan="3"><span id="rg_money_cn" class="span"></span></td>
	</tr>
	<tr id="tr_prov_flag" style="<%=display_flag==1?"display:none":""%>">
		<td align="right">受益优先级:</td>
		<td>					
			<select id="prov_flag" name="prov_flag" style="WIDTH: 280px"  onkeydown="javascript:nextKeyPress(this)">
			</select>
		</td>
		<td align="right" id="td_pro_level" style="display:">收益级别:</td>
		<td>
			<div id="div_prov_level" style="display:">
				<select name="prov_level" style="display:;width:234px" onchange="javascript:getGainRate()">
					<option value="0">请选择</option>
				</select>
			</div>
		</td>
	</tr>
	<tr>
		<td align="right">预期收益率:</td>
		<td>
			<input name="expect_ror_lower" size="12" value="" onkeydown="javascript:nextKeyPress(this)"><font color="red">%</font> 到 
			<input name="expect_ror_upper" size="12" value="" onkeydown="javascript:nextKeyPress(this)"><font color="red">%</font>
		</td>

		<td align="right"><font color='red'>*</font>资金/资产来源:</td>
		<td colspan="3">
				<select size="1" name="money_origin" onkeydown="javascript:nextKeyPress(this)" style="width:120px" onchange="javascript:moneyOriginChange(this.value);">
					<%=Argument.getDictOptionsWithLevel(new Integer(1158),"1158",new Integer(0))%>
				</select>
				<select size="1" name="sub_money_origin" <%if("115801".equals(money_origin)||"115807".equals(money_origin)||"".equals(money_origin)||money_origin==null) out.println("style='display:none'");%> onkeydown="javascript:nextKeyPress(this)" style="width:120px">
					<%=Argument.getDictOptionsWithLevel(new Integer(1158),sub_money_origin ,new Integer(0))%>
				</select>
	     </td>
	</tr>

	<tr>			
		<td align="right"><font color="red">*</font>签署日期:</td>
		<td>
			<INPUT TYPE="text" NAME="qs_date_picker" class=selecttext value="<%=Format.formatDateLine(Utility.getCurrentDate())%>" size="46">
			<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.qs_date_picker,theform.qs_date_picker.value,this);" tabindex="13">
			<INPUT TYPE="hidden" NAME="qs_date" value="">
		</td>

		<td align="right"><font color="red">*</font>缴款日期:</td>
		<td>
			<INPUT TYPE="text" NAME="jk_date_picker" class=selecttext value="<%=Format.formatDateLine(Utility.getCurrentDate())%>" size="37">
			<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.jk_date_picker,theform.jk_date_picker.value,this);" tabindex="14">
			<INPUT TYPE="hidden" NAME="jk_date" value="">
		</td>				
	</tr>
	<%if(bzjFlag == 1) {%>
	<tr>	
		<td align="right">是否为保证金:</td>
		<td colspan="3">
			是:<input type="radio" name="bzj_flag" value="1" class="flatcheckbox">&nbsp;&nbsp;
			否:<input type="radio" name="bzj_flag" value="2" checked class="flatcheckbox">
		</td>
	</tr>
	<%}%>
	<%if(user_id.intValue()==5){%>
	<tr>
		<td align="right">联系方式:</td>
		<td colspan=3><%=Argument.getTouchCheckBoxOptions(1109,"touch_type",sTouch_types)%></td>
	</tr>
	<%}%>
	<tr>
		<td align="right">合同销售人员:</td>
		<td>
			<select size="1" name="link_man" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 280px">
				<%=Argument.getManager_Value(new Integer(0))%>//=Argument.getRoledOperatorOptions(input_bookCode, 2,input_operatorCode)%>
			</select>
		</td>
		<td align="right" id="td_city_serialno" style="display: none;"><%if(user_id.intValue()==22){%><font color="red"><b>*</b></font><%}%><%=LocalUtilis.language("class.citySerialNO",clientLocale)%>:</td><!--合同推介地-->
		<td id="select_product_city" style="display: none;"></td>
	</tr>
	<tr id="tr_city_serialno" style="display: none;">
		<td align="right"><%if(user_id.intValue()==22){%><font color="red"><b>*</b></font><%}%>合同推介地:</td>
		<td id="select_product_city"></td>
	</tr>
	<tr>
		<td align="right">合作方式:</td><!--合作方式-->
		<td colspan="3">
			<input onkeydown="javascript:nextKeyPress(this)" type="checkbox" class="flatcheckbox" value="0" name="with_bank_flag" 
				onclick="javascript:showHtyh();">银信合作&nbsp;&nbsp;&nbsp;&nbsp;<!--银信合作-->
			<span id="htyhmc_v" style='display:none'>
				合作银行:				
				<select size="1" name="ht_bank_id" style="WIDTH:170px">
					<%=Argument.getBankOptions("")%>
				</select>
				<input type="text" name="ht_bank_sub_name" size="24" onkeydown="javascript:nextKeyPress(this)" value="">
			</span>
			<input onkeydown="javascript:nextKeyPress(this)" type="checkbox"  
				class="flatcheckbox" value="0" name="with_security_flag" >证信合作&nbsp;&nbsp;<!--证信合作-->
			<input onkeydown="javascript:nextKeyPress(this)" type="checkbox" 
				class="flatcheckbox" value="0" name="with_private_flag" >私募基金合作&nbsp;&nbsp;<!--私募基金合作-->
		</td>
	</tr>	
	<tr>
		<td align="right">渠道名称:</td>
		<td>
			<select id="market_trench" name="market_trench" style="width: 280px;" onclick="javascript:setMartetTrench(this);"></select>&nbsp;&nbsp;
			<button type="button"   class="xpbutton2" id="newChannel1" name="newChannel1" onclick="javascript:newChannel();">新建</button>
		</td>
		<td align="right">渠道费用:</td>
		<td><input name="market_trench_money" size="20" value="" onkeydown="javascript:nextKeyPress(this)" onkeyup="javascript:showCnMoney(this.value,market_trench_money_cn)"><span id="market_trench_money_cn" class="span"></span></td>
	</tr>
	<tr style="display: none;">
		<td align="right"><%=LocalUtilis.language("class.partnType",clientLocale)%>:</td> <!--渠道类型-->
		<td><div id="comboBoxTree"></div></td>
		<td align="right"><%=LocalUtilis.language("class.partnName",clientLocale)%>:</td><!--渠道名称-->
		<td><div id="comboBoxParentTree"></div></td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.channelCooperation",clientLocale)%>:</td><!-- 渠道合作方式 -->
		<td>
			<SELECT size="1" name="channel_cooperation" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 280px">
					<%=Argument.getChannelCooperationOptions("")%>
			</SELECT>
		</td>
		<td align="right">渠道附加信息:</td>
		<td>
			<input type="text" name="channel_memo" size="41" value="">	
		</td>
	</tr>
	<tr>
		<td align="right">非现场交易:</td>
		<td>   
			<input type="checkbox" class="flatcheckbox" name="spot_deal" value="1">
		</td>
		<td colspan="2" align="center"><button type="button" class="xpbutton6"  onclick="show_property_souce();">信托财产来源</button></td>
	</tr>
	<tr>
		<td align="right">备注:</td>
		<td colspan="3"><input type="text" name="summary2" size="70" onkeydown="javascript:nextKeyPress(this)" value=""></td>
	</tr>
			
<!--*************************************附件添加 2010-03-08*************************************************-->
	<tr id="reader2" style="display:">
          	<td class="paramTitle"align="right"><%=LocalUtilis.language("menu.filesAdd",clientLocale)%>:</td><!-- 添加附件 -->
            <td colspan="3">          	
            	<table id="test" width="100%">
            		<tr >
            			<td>
		            	<input type="file" name="file_info" size="70">&nbsp;
		            	<img title="<%=LocalUtilis.language("message.tSelectAdditionalFiles",clientLocale)%> " src="<%=request.getContextPath()%>/images/minihelp.gif"><!--选择附加文件-->
		            	</td>
		            </tr>
		        </table>
		        <button type="button"  class="xpbutton6" onclick="addline()"><%=LocalUtilis.language("class.moreMccessories",clientLocale)%> <!--单击此处添加更多附件--></button>
            </td>
		</tr>	
<!--*********************************************************************************************************-->	
</table>
</div>
<div align="right">
	<br>
	<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave"><%=LocalUtilis.language("message.save",clientLocale)%>(<u>S</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;<!--保存-->
	<button type="button"   class="xpbutton3" accessKey=b id="btnCancel" name="btnCancel" onclick="javascript:history.back();"><%=LocalUtilis.language("message.back",clientLocale)%>(<u>B</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<!--返回-->
</div>
<p>
</form>
<%@ include file="/includes/foot.inc"%>
<script language="javascript" >
<%if (!"".equals(session_cust_name) && cust_map==null){%>
	//扫描证件，并在数据库中验证后不存在，新建客户
	getTransactionCustomer2_Auto('customer','0');
<%}else if (!"".equals(session_cust_name) && cust_map!=null){%>
	//给form赋值
	document.theform.customer_cust_name.value='<%=Utility.trimNull(session.getAttribute("name"))%>';
	document.theform.customer_service_man.value='<%=Utility.trimNull(cust_map.get("SERVICE_MAN_NAME"))%>';
	document.theform.customer_cust_type_name.value='<%=Utility.trimNull(cust_map.get("CUST_TYPE_NAME"))%>';
	document.theform.customer_card_id.value='<%=Utility.trimNull(session.getAttribute("card_id"))%>';
	document.theform.customer_mobile.value='<%=Utility.trimNull(cust_map.get("MOBILE"))%>';
	document.theform.customer_h_tel.value='<%=Utility.trimNull(cust_map.get("H_TEL"))%>';
	document.theform.customer_post_address.value='<%=Utility.trimNull(cust_map.get("POST_ADDRESS"))%>';
	document.theform.customer_post_code.value='<%=Utility.trimNull(cust_map.get("POST_CODE"))%>';
	document.theform.customer_cust_id.value='<%=Utility.trimNull(cust_map.get("CUST_ID"))%>';
	cust_id_v = getElement("customer", "cust_id").value;
	showCustOptions(cust_id_v);
	
<%}%>
</script>
</BODY>
</HTML>