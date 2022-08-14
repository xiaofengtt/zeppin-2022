<%@ page contentType="text/html; charset=GBK" import="enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.marketing.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ page import="enfo.crm.web.*"%>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
ProductLocal  product  = EJBFactory.getProduct();
CustomerLocal customer = EJBFactory.getCustomer();
TMoneyDataLocal moneyData = EJBFactory.getTMoneyData();

input_bookCode = new Integer(1);//帐套暂时设置
String     sQuery         = request.getParameter("sQuery");
int     is_sub_flag      = Argument.getSyscontrolValue_intrust("SUBCP01");
int     action_type      = Utility.parseInt(request.getParameter("action_type"),2);
int inputflag = Utility.parseInt(request.getParameter("inputflag"), 1);

Integer  productId        = Utility.parseInt(request.getParameter("productId"),Integer.valueOf("0"));
Integer sub_product_id   = Utility.parseInt(request.getParameter("sub_product_id"),new Integer(0));
Integer sub_product_id2   = Utility.parseInt(request.getParameter("sub_product_id2"),new Integer(0));
Integer  cust_id      	    = Utility.parseInt(request.getParameter("cust_id"),new Integer(0));
Integer ht_serial_no     = Utility.parseInt(request.getParameter("ht_serial_no"),Integer.valueOf("0"));
String  sub_product_name = Utility.trimNull(request.getParameter("sub_product_name"),"");
String  serial_no_list   = Utility.trimNull(request.getParameter("serial_no_list"),"0");

String     bank_id            = Utility.trimNull(request.getParameter("bank_id"),null);
String     bank_sub_name      = Utility.trimNull(request.getParameter("bank_sub_name"));
String     bank_acct          = Utility.trimNull(request.getParameter("bank_acct_final"));
String     customer_gain_acct = Utility.trimNull(request.getParameter("customer_gain_acct"));
String     bank_acct_type     = Utility.trimNull(request.getParameter("bank_acct_type"));
Integer    bonus_flag         = Utility.parseInt(request.getParameter("bonus_flag"),Integer.valueOf("1"));
BigDecimal bonus_rate         = Utility.parseDecimal(request.getParameter("bonus_rate"),new BigDecimal("0.0"));
bonus_rate = bonus_rate.intValue()==0?bonus_rate:bonus_rate.multiply(new BigDecimal("0.01"));
Integer    with_bank_flag     = Utility.parseInt(request.getParameter("with_bank_flag"),Integer.valueOf("0"));
String     ht_bank_id         = Utility.trimNull(request.getParameter("ht_bank_id"),"0");

String     contract_sub_bh    =  Utility.trimNull(request.getParameter("contract_sub_bh"));
Integer    qs_date            = Utility.parseInt(request.getParameter("qs_date"),new Integer(0));
String   ht_bank_sub_name   = Utility.trimNull(request.getParameter("ht_bank_sub_name"),"0");
Integer  with_security_flag = Utility.parseInt(request.getParameter("with_security_flag"),Integer.valueOf("0"));
Integer  with_private_flag  = Utility.parseInt(request.getParameter("with_private_flag"),Integer.valueOf("0"));
Integer  city_serialno      = Utility.parseInt(request.getParameter("city_serialno"),Integer.valueOf("0"));
Integer prov_flag        = Utility.parseInt(request.getParameter("prov_flag"),new Integer(1));
String  prov_level       = Utility.trimNull(request.getParameter("prov_level"));
String applyreach_flag = Utility.trimNull(request.getParameter("applyreach_flag"));

String ht_cust_name = Utility.trimNull(request.getParameter("ht_cust_name"));
String ht_cust_tel = Utility.trimNull(request.getParameter("ht_cust_tel"));
String ht_cust_address = Utility.trimNull(request.getParameter("ht_cust_address"));

String bank_province = Utility.trimNull(request.getParameter("bank_province"));
String bank_city = Utility.trimNull(request.getParameter("bank_city"));
Integer recommend_man = Utility.parseInt(request.getParameter("recommend_man"),new Integer(0));

BigDecimal market_trench_money = Utility.parseDecimal(request.getParameter("market_trench_money"),new BigDecimal(0));
Integer channel_id = Utility.parseInt(request.getParameter("channel_id"),new Integer(0));
String channel_type = Utility.trimNull(request.getParameter("channel_type"));
String channel_memo = Utility.trimNull(request.getParameter("channel_memo"));
String channel_cooperation = Utility.trimNull(request.getParameter("channel_cooperation"));

Integer link_man = Utility.parseInt(request.getParameter("link_man"), input_operatorCode);
BigDecimal expect_ror_lower = Utility.parseDecimal(request.getParameter("expect_ror_lower"),new BigDecimal(0.00),4,"0.01");
BigDecimal expect_ror_upper = Utility.parseDecimal(request.getParameter("expect_ror_upper"),new BigDecimal(0.00),4,"0.01");

String is_ykgl = Utility.trimNull(request.getParameter("is_ykgl"));
String xthtyjsyl = Utility.trimNull(request.getParameter("xthtyjsyl"));
String summary = Utility.trimNull(request.getParameter("summary"));

String marValue = "";

boolean bSuccess = false;
int iCount = 0;	
Iterator serial_no_list_iterator = null;
BigDecimal total_to_money = new BigDecimal(0);
BigDecimal total_to_amount = new BigDecimal(0);
String preCodeOptions = null;
//产品数据
Integer sub_flag      = new Integer(0);
Integer	open_flag     = Integer.valueOf("2");
int     intrust_flag1 = 2;
Integer asfund_flag   = Integer.valueOf("0");
Integer share_flag    = new Integer(0);
Integer item_id = new Integer(0);
String product_code  = "";
String product_name   = "";
String cityNameOptions = "";
String product_status = "";
Integer preEndDate = new Integer(0);
Integer preStartDate = new Integer(0);
Integer product_form = new Integer(0);
//客户数据
String cust_name = "";
Integer cust_type = new Integer(0);
String cust_type_name = "";

String from = Utility.trimNull(request.getParameter("from"));
boolean from_signed_spot = "signed_spot".equals(from);
String qs = Utility.trimNull(request.getParameter("qs"));

if (request.getMethod().equals("POST") && action_type==2){
	DocumentFile2 file = new DocumentFile2(pageContext);
	file.parseRequest();

	from = Utility.trimNull(file.getParameter("from"));
    from_signed_spot = "signed_spot".equals(from);
	qs = Utility.trimNull(file.getParameter("qs"));

	sQuery         = file.getParameter("sQuery");
	action_type      = Utility.parseInt(file.getParameter("action_type"),2);
	inputflag = Utility.parseInt(file.getParameter("inputflag"), 1);
	
	productId        = Utility.parseInt(file.getParameter("productId"),Integer.valueOf("0"));
	sub_product_id   = Utility.parseInt(file.getParameter("sub_product_id"),new Integer(0));
	sub_product_id2   = Utility.parseInt(file.getParameter("sub_product_id2"),new Integer(0));
	cust_id      	    = Utility.parseInt(file.getParameter("cust_id"),new Integer(0));
	ht_serial_no     = Utility.parseInt(file.getParameter("ht_serial_no"),Integer.valueOf("0"));
	sub_product_name = Utility.trimNull(file.getParameter("sub_product_name"),"");
	serial_no_list   = Utility.trimNull(file.getParameter("serial_no_list"),"0");
	
	bank_id            = Utility.trimNull(file.getParameter("bank_id"),null);
	bank_sub_name      = Utility.trimNull(file.getParameter("bank_sub_name"));
	bank_acct          = Utility.trimNull(file.getParameter("bank_acct_final"));
	customer_gain_acct = Utility.trimNull(file.getParameter("customer_gain_acct"));
	bank_acct_type     = Utility.trimNull(file.getParameter("bank_acct_type"));
	bonus_flag         = Utility.parseInt(file.getParameter("bonus_flag"),Integer.valueOf("1"));
	bonus_rate         = Utility.parseDecimal(file.getParameter("bonus_rate"),new BigDecimal("0.0"));
	bonus_rate = bonus_rate.intValue()==0?bonus_rate:bonus_rate.multiply(new BigDecimal("0.01"));
	with_bank_flag     = Utility.parseInt(file.getParameter("with_bank_flag"),Integer.valueOf("0"));
	ht_bank_id         = Utility.trimNull(file.getParameter("ht_bank_id"),"0");
	
	contract_sub_bh    =  Utility.trimNull(file.getParameter("contract_sub_bh"));
	qs_date            = Utility.parseInt(file.getParameter("qs_date"),new Integer(0));
	ht_bank_sub_name   = Utility.trimNull(file.getParameter("ht_bank_sub_name"),"0");
	with_security_flag = Utility.parseInt(file.getParameter("with_security_flag"),Integer.valueOf("0"));
	with_private_flag  = Utility.parseInt(file.getParameter("with_private_flag"),Integer.valueOf("0"));
	city_serialno      = Utility.parseInt(file.getParameter("city_serialno"),Integer.valueOf("0"));
	prov_flag        = Utility.parseInt(file.getParameter("prov_flag"),new Integer(1));
	prov_level       = Utility.trimNull(file.getParameter("prov_level"));
	applyreach_flag = Utility.trimNull(file.getParameter("applyreach_flag"));
	
	ht_cust_name = Utility.trimNull(file.getParameter("ht_cust_name"));
	ht_cust_tel = Utility.trimNull(file.getParameter("ht_cust_tel"));
	ht_cust_address = Utility.trimNull(file.getParameter("ht_cust_address"));
	
	bank_province = Utility.trimNull(file.getParameter("bank_province"));
	bank_city = Utility.trimNull(file.getParameter("bank_city"));
	recommend_man = Utility.parseInt(file.getParameter("recommend_man"),new Integer(0));
	
	market_trench_money = Utility.parseDecimal(file.getParameter("market_trench_money"),new BigDecimal(0));
	channel_id = Utility.parseInt(file.getParameter("channel_id"),new Integer(0));
	channel_type = Utility.trimNull(file.getParameter("channel_type"));
	channel_memo = Utility.trimNull(file.getParameter("channel_memo"));
	channel_cooperation = Utility.trimNull(file.getParameter("channel_cooperation"));
	
	link_man = Utility.parseInt(file.getParameter("link_man"), input_operatorCode);
	expect_ror_lower = Utility.parseDecimal(file.getParameter("expect_ror_lower"),new BigDecimal(0.00),4,"0.01");
	expect_ror_upper = Utility.parseDecimal(file.getParameter("expect_ror_upper"),new BigDecimal(0.00),4,"0.01");
	
	is_ykgl = Utility.trimNull(file.getParameter("is_ykgl"));
	xthtyjsyl = Utility.trimNull(file.getParameter("xthtyjsyl"));
	summary = Utility.trimNull(file.getParameter("summary"));

	TMoneyDataVO vo = new TMoneyDataVO();
	
	vo.setProduct_id(productId);
	vo.setSub_product_id(sub_product_id2);
	vo.setSerial_no_list(serial_no_list);
	vo.setContract_sub_bh(contract_sub_bh);
	vo.setQs_date(qs_date);
	vo.setProv_flag(prov_flag);

	vo.setProv_level(prov_level);
	vo.setBank_id(bank_id);
	vo.setBank_sub_name(bank_sub_name);
	vo.setBank_acct(bank_acct);
	vo.setGain_acct(customer_gain_acct);
	vo.setInput_man(input_operatorCode);
	vo.setBank_acct_type(bank_acct_type);
	vo.setBonus_flag(bonus_flag);

	vo.setBonus_rate(bonus_rate);
	vo.setWith_bank_flag(with_bank_flag);
	vo.setHt_bank_id(ht_bank_id);
	vo.setHt_bank_sub_name(ht_bank_sub_name);
	vo.setWith_security_flag(with_security_flag);
	vo.setWith_private_flag(with_private_flag);
	vo.setCity_serialno(city_serialno);
	vo.setApplyreach_flag(applyreach_flag);
	moneyData.combineContract(vo);	

	ContractLocal contract = EJBFactory.getContract();//合同
	ContractVO s_cont_vo = new ContractVO();
	s_cont_vo.setBook_code(input_bookCode);
	s_cont_vo.setProduct_id(productId);
	s_cont_vo.setContract_sub_bh(contract_sub_bh); // like
	s_cont_vo.setInput_man(input_operatorCode);
	List rsList_contract = contract.queryPurchanseContract(s_cont_vo); //contract.queryPurchanseContract_crm(s_cont_vo);

	Integer serial_no = new Integer(0);
	BigDecimal rg_money = new BigDecimal(0.0);
	String jk_type = "";
	Integer service_man = new Integer(0);
	String contract_bh = "";
	Integer jk_date = new Integer(0);
	Integer check_man = new Integer(0);
	Integer fee_jk_type = new Integer(0);
	Integer selfbenflag = new Integer(0);
	Integer valid_period = new Integer(0);
	Integer period_unit = new Integer(0);
	for (int i=0; i<rsList_contract.size(); i++) {
		Map map_contract = (Map)rsList_contract.get(i);

		if (contract_sub_bh.equals((String)map_contract.get("CONTRACT_SUB_BH"))) {
			serial_no = Utility.parseInt(Utility.trimNull(map_contract.get("SERIAL_NO")), new Integer(0));
			rg_money = (BigDecimal)map_contract.get("RG_MONEY");
			jk_type = (String)map_contract.get("JK_TYPE");
			service_man = (Integer)map_contract.get("SERVICE_MAN");
			contract_bh = (String)map_contract.get("CONTRACT_BH");
			jk_date = (Integer)map_contract.get("JK_DATE");
			check_man = (Integer)map_contract.get("CHECK_MAN");
			fee_jk_type = (Integer)map_contract.get("FEE_JK_TYPE");
			selfbenflag = (Integer)map_contract.get("ZY_FLAG");
			valid_period = (Integer)map_contract.get("VALID_PERIOD");
			period_unit = (Integer)map_contract.get("PERIOD_UNIT");
			break;
		}
	}

	//同步团队信息
	Integer r_team_id = Utility.parseInt(file.getParameter("team_id"),new Integer(-1));
	Integer r_serialNo = Utility.parseInt(file.getParameter("serialNo"),new Integer(0));
	BigDecimal r_already_sale = Utility.parseDecimal(file.getParameter("already_sale"),new BigDecimal(0));
	Integer r_already_qualified_num = Utility.parseInt(file.getParameter("already_qualified_num"),new Integer(-1));
	r_already_sale = r_already_sale.add(rg_money);

	SaleParameterLocal sale_parameter = EJBFactory.getSaleParameter();
	SaleParameterVO salevo = new SaleParameterVO();	
	salevo.setSerial_NO(r_serialNo);
	salevo.setProductID(productId);
	salevo.setTeamID(r_team_id);
	salevo.setSub_product_id(sub_product_id2);
	salevo.setSerial_no_subscribe(serial_no);
	salevo.setAlreadysale(r_already_sale);
	salevo.setAlready_qualified_num(r_already_qualified_num);
	sale_parameter.modiAlreadysale(salevo, input_operatorCode);

	s_cont_vo = new ContractVO();
	s_cont_vo.setSerial_no(serial_no);
	s_cont_vo.setBook_code(input_bookCode);
	s_cont_vo.setCust_id(cust_id);
	s_cont_vo.setProduct_id(productId);
	s_cont_vo.setLink_man(link_man);
	s_cont_vo.setRg_money(rg_money);
	s_cont_vo.setBonus_flag(bonus_flag);
	s_cont_vo.setJk_type(jk_type);
	s_cont_vo.setBank_id(bank_id);
	s_cont_vo.setBank_sub_name(bank_sub_name);
	s_cont_vo.setGain_acct(customer_gain_acct);
	s_cont_vo.setBank_acct(bank_acct);
	s_cont_vo.setService_man(service_man);
	s_cont_vo.setSummary(summary);
	s_cont_vo.setContract_bh(contract_bh);
	s_cont_vo.setQs_date(qs_date);
	s_cont_vo.setJk_date(jk_date);
	s_cont_vo.setCheck_man(check_man);
	s_cont_vo.setBank_acct_type(bank_acct_type);
	s_cont_vo.setContract_sub_bh(contract_sub_bh);
	s_cont_vo.setFee_jk_type(fee_jk_type.intValue());
	s_cont_vo.setSelf_ben_flag(selfbenflag.intValue());
	s_cont_vo.setInput_man(input_operatorCode);
	s_cont_vo.setChannel_id(channel_id);
	s_cont_vo.setMarket_trench_money(market_trench_money);
	s_cont_vo.setChannel_type(channel_type);
	s_cont_vo.setChannel_memo(channel_memo);
	s_cont_vo.setChannel_cooperation(channel_cooperation);
	s_cont_vo.setWith_bank_flag(with_bank_flag);
	s_cont_vo.setWith_private_flag(with_private_flag);
	s_cont_vo.setWith_secuity_flag(with_security_flag);
	s_cont_vo.setBonus_rate(bonus_rate);
	s_cont_vo.setProv_flag(prov_flag);
	s_cont_vo.setProv_level(prov_level);
	s_cont_vo.setHt_bank_id(ht_bank_id);
	s_cont_vo.setHt_bank_sub_name(ht_bank_sub_name);
	
	s_cont_vo.setRecommend_man(recommend_man);
	s_cont_vo.setBank_province(bank_province);
	s_cont_vo.setBank_city(bank_city);
	s_cont_vo.setCity_serialno(city_serialno);
	s_cont_vo.setExpect_ror_lower(expect_ror_lower);
	s_cont_vo.setExpect_ror_upper(expect_ror_upper);
	s_cont_vo.setIs_ykgl(is_ykgl);
	s_cont_vo.setXthtyjsyl(xthtyjsyl);
	//s_cont_vo.setContact_id(contact_id);
	s_cont_vo.setHt_cust_name(ht_cust_name);
	s_cont_vo.setHt_cust_address(ht_cust_address);
	s_cont_vo.setHt_cust_tel(ht_cust_tel);
	s_cont_vo.setValid_period(valid_period);
	s_cont_vo.setPeriod_unit(period_unit);
	s_cont_vo.setSub_product_id(sub_product_id2); //

	/*中投要求只“生成（合并）合同”，不同时做“合同补全”*/
	if (user_id.intValue() != 22) contract.save(s_cont_vo);

	if(file.uploadAttchment(new Integer(5),serial_no,"",null,null,input_operatorCode))
		bSuccess = true;

	bSuccess = true;

	sale_parameter.remove();
	contract.remove();
}

if(productId.intValue() > 0){
	ProductVO p_vo = new ProductVO();	
	p_vo.setProduct_id(productId);
	List product_list = product.load(p_vo);

	if(product_list.size()>0){
		Map product_map = (Map)product_list.get(0);

		sub_flag  =  Utility.parseInt(Utility.trimNull(product_map.get("SUB_FLAG")),new Integer(0));
		open_flag =  Utility.parseInt(Utility.trimNull(product_map.get("OPEN_FLAG")),new Integer(0));
		intrust_flag1 = Utility.parseInt(Utility.trimNull(product_map.get("INTRUST_FLAG1")),new Integer(0)).intValue();
		asfund_flag =  Utility.parseInt(Utility.trimNull(product_map.get("ASFUND_FLAG")),new Integer(0));
		share_flag  =  Utility.parseInt(Utility.trimNull(product_map.get("SHARE_FLAG")),new Integer(0));
		product_code = Utility.trimNull(product_map.get("PRODUCT_CODE"));
		product_name = Utility.trimNull(product_map.get("PRODUCT_NAME"));
		item_id = Utility.parseInt(Utility.trimNull(product_map.get("ITEM_ID")),new Integer(0));
		product_status = Utility.trimNull(product_map.get("PRODUCT_STATUS"));
		preEndDate = Utility.parseInt(Utility.trimNull(product_map.get("PRE_END_DATE")),new Integer(0));
		preStartDate = Utility.parseInt(Utility.trimNull(product_map.get("PRE_START_DATE")),new Integer(0));
		product_form = Utility.parseInt(Utility.trimNull(product_map.get("IS_LOCAL")),new Integer(0));
	}
}

ProductVO _p_vo = new ProductVO();
//渠道渠道费率，选中渠道名称的
_p_vo.setProduct_id(productId);
_p_vo.setSub_product_id(sub_product_id2);
_p_vo.setChannel_type(channel_type);
_p_vo.setR_channel_id(channel_id);
//产品销售渠道信息
List markList = product.queryMarketTrench(_p_vo);
if(markList.size()>0){
	Map marMap = (Map)markList.get(0);
	marValue =  Utility.trimNull(marMap.get("CHANNEL_TYPE"))+"@"+
				Utility.trimNull(marMap.get("CHANNEL_ID"))+"@"+
				Utility.trimNull(Utility.parseDecimal(Utility.trimNull(
					marMap.get("CHANNEL_FARE_RATE")), new BigDecimal(0),4,"1"));
}

cityNameOptions = Argument.getCitynameOptions(productId,Integer.valueOf("0"));

if(cust_id.intValue()>0){
	cust_name = Argument.getCustomerFlag(cust_id,input_operatorCode,"cust_name");
	cust_type = Utility.parseInt(Argument.getCustomerFlag(cust_id,input_operatorCode,"cust_type"),new Integer(0));
	cust_type_name = Argument.getCustomerFlag(cust_id,input_operatorCode,"cust_type_name");
}

if(!serial_no_list.equals("0")){
	List rsList = moneyData.getTmoneydata(serial_no_list);
	serial_no_list_iterator = rsList.iterator();
}

if (cust_id != null && bank_id != null)
	preCodeOptions = Argument.getCustBankAcctOptions(cust_id,bank_id,null,bank_acct_type);

if (preCodeOptions == null)
	preCodeOptions = Argument.newStringBuffer().toString();

if(preCodeOptions.equals("<option value=\"\">请选择</option>"))
	inputflag = 2;

product.remove();
customer.remove();
moneyData.remove();
%>
<HTML>
<HEAD>
<TITLE>认/申购资金合同录入</TITLE>
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
//展示产品信息
function openQuery(product_id,item_id){	
	if(product_id!=0)
		showModalDialog('<%=request.getContextPath()%>/marketing/base/product_list_detail.jsp?product_id='+product_id+'&item_id='+item_id, '','dialogWidth:750px;dialogHeight:580px;status:0;help:0');
}

//申购方式选择
function arFlagChange() {
	var arFlag = document.theform.applyreach_flag.value;
	var array = arFlag.split("$");

	if (arFlag=="") return false;

	if(arFlag == "new"){
		document.getElementById("td_contract_bh").style.display="";
		document.getElementById("td_contract_bh_input").style.display="";

		document.theform.bank_id.value = "";
		selectBank("");
		document.theform.bank_acct_type.value = "";
		document.theform.customer_gain_acct.value = "";
	<%if(open_flag!=null && open_flag.intValue()==1){%>
		document.theform.bonus_flag.value = "";
		document.theform.bonus_rate.value = "";
	<%}%>
	<%if(intrust_flag1!=1){%>	
		document.theform.prov_flag.value = "";
		getprovlevel();
		document.theform.prov_level.value = "";
	<%}%>	
	}else{
		document.theform.ht_serial_no.value = array[2];
		document.getElementById("td_contract_bh").style.display="none";
		document.getElementById("td_contract_bh_input").style.display="none";

		contract.getSgContractInfo
			(array[2],
			 function(data){
				if(data[0]!=""){
				    document.theform.bank_id.value = data[0];
					selectBank(data[0]);
				}
				document.theform.bank_sub_name.value = data[1];
				if(data[2]!=""){
					setTimeout("document.theform.bank_acct2.value ='"+data[2]+"';",500);
				}
				document.theform.bank_acct_type.value = data[3];
				document.theform.customer_gain_acct.value = data[4];
			<%if(open_flag!=null && open_flag.intValue()==1){%>
				document.theform.bonus_flag.value = data[5];
				document.theform.bonus_rate.value = data[6];
			<%}%>
			<%if(intrust_flag1!=1){%>	
				document.theform.prov_flag.value = data[7];
				getprovlevel();					
				document.theform.prov_level.value = data[8];
			<%}%>				
		});
	}
}
//验证编号
function check(){
	$("contractDIV").innerHTML = "";
	if(!sl_check(document.theform.contract_sub_bh, "合同编号"))	return false; 
	var book_code=DWRUtil.getValue("book_code");
	var contract_type=new Number(1);
	var product_id=DWRUtil.getValue("productId");
	var contract_sub =DWRUtil.getValue("contract_sub_bh");	
	contract.findIfExistSameBH
			(book_code,contract_type,product_id,contract_sub,
				callContractSubBHCallBack);
}

function callContractSubBHCallBack(result){	
	if (result!="")
		document.getElementById("contractDIV").innerHTML = result;
}

//查看客户信息
function showCustInfo(cust_id,cust_type){
	var url = '<%=request.getContextPath()%>/marketing/customer_info_simple.jsp?cust_id='+cust_id+'&onlyUpdate=1&task=update';
	showModalDialog(url, '', 'dialogWidth:700px;dialogHeight:450px;status:0;help:0')
}

//选择银行
function selectBank(value){
	var bank_id = document.theform.bank_id.value;
	utilityService.getCustBankAcctOptions(<%=cust_id%>,bank_id,null,null,callback)
}

function callback(data){
	if(data =="<option value=\"\">请选择</option>"){
		document.theform.btnSelect.style.display="none";
		document.theform.bank_acct.style.display="";
		document.theform.bank_acct2.style.display="none";
		document.theform.inputflag.value=2;
	}else{
		var content = document.getElementById("bank_acct_content");
		content.innerHTML = "<select size='1' name='bank_acct2' id='bank_acct2' onkeydown='javascript:nextKeyPress(this)'  style='WIDTH: 200px'>"+data+"</select>";
		document.theform.btnSelect.style.display="";
		document.theform.btnSelect.innerText="输入";
		document.theform.bank_acct.style.display="none";
		document.theform.bank_acct2.style.display="";
		document.getElementById("bank_acct_num").style.display="none";
		document.theform.inputflag.value=1;
	}
}

//显示位数
function showAcctNum(value){		
	if (trim(value) == "")
		bank_acct_num.innerText = "";
	else
		bank_acct_num.innerText = "(" + showLength(value) + " 位 )";
}

//按钮变化
function changeInput(obj){
	if(document.theform.inputflag.value==1){
		obj.innerText="选择";
		document.theform.bank_acct.style.display="";
		document.theform.bank_acct2.style.display="none";
		document.getElementById("bank_acct_num").style.display="";
		document.theform.inputflag.value=2;
	} else {
		obj.innerText="输入";
		document.theform.bank_acct.style.display="none";
		document.theform.bank_acct2.style.display="";
		document.getElementById("bank_acct_num").style.display="none";
		document.theform.inputflag.value=1;
	}
}

//转份额比例输入框控制
function showBonus_rate(){
	var bonus_flag = document.getElementById("bonus_flag").value;
	document.getElementById("bonus_rate_div").style.display = bonus_flag=='3'? "": "none";
}

//受益优先级
function getprovlevel(){
<%if(sub_flag.intValue() == 1){%>
	utilityService.getProvLevel(<%=productId%>,document.theform.sub_product_id.value,document.theform.prov_flag.value,0,setprovlevel);
<%}else{%>
	utilityService.getProvLevel(<%=productId%>,0,document.theform.prov_flag.value,0,setprovlevel);
<%}%>
}

function setprovlevel(data){
	var ret = "<select name='prov_level' style='width:100px'>"+data+"</select>";
	document.getElementById("div_prov_level").innerHTML=ret;
}

//认购时可以维护产品的推介地，进而可以选取合适的认购地
function newCity(productId,city_serial_no){	
	var ret = showModalDialog('<%=request.getContextPath()%>/marketing/base/product_city_update.jsp?product_id=' + productId+'&city_serial_no='+city_serial_no, '', 'dialogWidth:500px;dialogHeight:150px;status:0;help:0');
	if (ret) {
		seloption = new Option(ret[1],ret[0]);
		document.all.city_serialno.options[document.all.city_serialno.length] = seloption;
		document.all.city_serialno.options[document.all.city_serialno.length-1].selected = true;
	}
}

//合作方式选择变化
function showHtyh(){
	document.getElementById("htyhmc_v").style.display = document.theform.with_bank_flag.checked? "": "none";
}

//校验
function validateForm(){
	if(!sl_checkChoice(document.theform.contract_sub_bh, "合同编号"))	return false;
	if(!sl_checkDate(document.theform.qs_date_picker,"签署日期"))	    return false;
	//if(!sl_checkChoice(document.theform.bank_id, "银行名称"))	        return false;
	//if(!sl_checkChoice(document.theform.jk_type, "缴款方式"))		    return false;
	syncDatePicker(document.theform.qs_date_picker, document.theform.qs_date);
	
	var bank_acct = document.theform.bank_acct;
	var bank_acct2 = document.theform.bank_acct2;
	if(bank_acct.style.display==""){
		document.theform.bank_acct_final.value = bank_acct.value;
	}else{
		document.theform.bank_acct_final.value = bank_acct2.value;
	}
	
	if(<%=intrust_flag1%>==2){
		if(document.theform.qs_date.value>document.theform.pre_end_date.value){
			if(!confirm("认购日期大于推介到期日期！要继续吗？"))
				return;
		}
		if(document.theform.qs_date.value<document.theform.pre_start_date.value){	
			//选择是否继续
			if(!confirm("认购日期小于推介起始日期！要继续吗？"))
				return;
		}
	}
	if(document.theform.product_from.value == '2' && !document.theform.city_serialno.value){
		sl_alert("产品异地,合同推介地必须选择！");
		return;
	}
	if(document.theform.with_bank_flag.checked){
		document.theform.with_bank_flag.value = 1;
	}
	if(document.theform.with_security_flag.checked){
		document.theform.with_security_flag.value = 1;
	}
	if(document.theform.with_private_flag.checked){
		document.theform.with_private_flag.value = 1;
	}

	<%if(open_flag.intValue()==1){%>
		var bonus_flag = document.getElementById("bonus_flag").value;
		if(bonus_flag=='3'){
			if(!sl_checkDecimal(document.theform.bonus_rate, "转份额比例", 3, 2, 0))	return false;
			if(parseFloat(document.theform.bonus_rate.value) <= parseFloat('0') || parseFloat(document.theform.bonus_rate.value) >= parseFloat('100')){
				sl_alert("转份额比例取值必须为0到100之间(不包含0和100)");
				return false;
			}
		}
	<%}%>

	if( sl_check_update()){
	    disableAllBtn(true);
	    document.theform.submit();    
	};
}

//返回
function op_return(){
<%if (from_signed_spot) {%>
	location = "<%=request.getContextPath()%>/marketing/signed_spot.jsp?<%=qs%>";
<%}else{%>
	location = "<%=request.getContextPath()%>/marketing/sell/sub_capital_make_contract.jsp?back_flag=1&sQuery=<%=sQuery%>";
<%}%>
}
<%if (bSuccess){%>  
	sl_alert("生成合同成功");
	op_return();
<%}%>

//通过合同销售人员，产品ID，子产品ID 查询销售人员所在团队的可销售配额，可销售人数。
function queryTeamquota(link_man) {	
	var product_id = DWRUtil.getValue("productId");
	var sub_product_id = DWRUtil.getValue("sub_product_id2");
	utilityService.queryTeamquota(product_id,sub_product_id,
		link_man,<%=input_operatorCode%>,{callback:function(data){ 
			var arrayList = data.split('&');
			document.theform.serialNo.value=arrayList[0];
			document.theform.quota_money.value=arrayList[1];
			document.theform.already_sale.value=arrayList[2];
			document.theform.quota_qualified_num.value=arrayList[3];
			document.theform.already_qualified_num.value=arrayList[4];
			document.theform.team_id.value=arrayList[5];
	}});
}

// 添加附件
var n = 1;
function addline() {
	n++;
	var newline=document.all.test.insertRow()
	newline.id="fileRow"+n;
	newline.insertCell().innerHTML="<input type='file' name=file_info"+n+" size='75'>"
		+"<button type="button"  onclick='javascript:removeline(this)'><%=LocalUtilis.language("messgae.remove",clientLocale)%> </button>";//移除
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

//渠道费用 计算
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
</script>
</HEAD>
<BODY class="BODY">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="post" action="sub_capital_make_contract_action.jsp" ENCTYPE="multipart/form-data">
<input type="hidden" name="sQuery"  value="<%=sQuery%>">
<input type="hidden" name="productId" value="<%=productId%>">
<input type="hidden" name="sub_product_id2" value="<%=sub_product_id2%>">
<input type="hidden" name="product_from"   value="<%=product_form%>">
<input type="hidden" name="serial_no_list" value="<%=serial_no_list%>">
<input type="hidden" name="pre_end_date"   value="<%=preEndDate%>">
<input type="hidden" name="pre_start_date" value="<%=preStartDate%>">
<input type="hidden" name="inputflag" value="<%=inputflag%>">
<input type="hidden" name="ht_serial_no" value="<%=ht_serial_no%>">
<input type="hidden" name="bank_acct_final">
<!-- 销售团队配额是否超过的验证 -->
<input type="hidden" name="quota_money" value="">
<input type="hidden" name="already_sale" value="">
<input type="hidden" name="quota_qualified_num" value="">
<input type="hidden" name="already_qualified_num" value="0">
<input type="hidden" name="team_id" value="">
<input type="hidden" name="serialNo" value="">

<input type="hidden" id="channel_type" name="channel_type"/>
<input type="hidden" id="channel_id" name="channel_id"/>
<input type="hidden" id="channel_rate" name="channel_rate" value=""/>

<input type="hidden" name="from" value="<%=from%>"/>
<input type="hidden" name="qs" value="<%=qs%>"/>
<div>
	<div align="left">
		<img border="0" src="/images/ico_area.gif" width="32" height="32"><b>认购信息</b>
	</div>
	<hr size="1">

	<TABLE cellSpacing=0 cellPadding=3 width="100%" border=0>
		<tr>
			<td  align="right">产品编号:</td>
			<td  align="LEFT">
				<input type="text" readonly class='edline' maxlength="16" name="product_code" value="<%=Utility.trimNull(product_code)%>" size="20">
			</td>
			<td  align="right"></td>
			<td  align="right"></td>
		</tr>

		<tr>	
			<td align="right"><b>*产品选择</b></td>
			<td align=left colspan=3>						
				<input type="text" name="product_name" class="edline" value="<%=Utility.trimNull(product_code+"-"+product_name)%>" style="width:430px;" >&nbsp;&nbsp;&nbsp;
				<button type="button"  name="show_info" class="xpbutton2" title="查看产品信息" onclick="javascript:openQuery('<%=productId%>','<%=item_id%>');">查看</button>
			</td>
		</tr>

	<%if(sub_flag.intValue() == 1 && is_sub_flag == 1){%>
		<tr>
			<td align="right"><b>*子产品选择</b></td>
			<td align=left colspan=3>
				 <select size="1" disabled name="sub_product_id" class="productname" style="width:430px;">
					<%=Argument.getSubProductOptions2(productId, new Integer(0),sub_product_id2,3,"113801")%>
				</select> &nbsp;&nbsp;&nbsp;
				<!-- button name="show_sub_info" title="查看子产品信息" onclick="javascript:openSubProduct(document.theform.sub_product_id);">查看</button>-->
			</td>
		</tr>
		<tr id="sylb" style="display:none">
			<td></td><td>受益类别:<input type="text" class=edline name="sylb_v" readonly ></td>
		</tr>
	<%}%>

		<tr>
		    <td colspan='4' align="center">
		        <table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="83%">
					<tr class="trh">
						<td align="center" >客户名称</td>						
						<td align="center" >到账日期</td>
						<td align="center" >缴款方式</td>
						<td align="center" >到帐金额</td>
						<td align="center" >到帐份额</td>
						<td align="center" >资金来源地</td>
					</tr>
<%				
				while(serial_no_list_iterator.hasNext()){
					iCount++;
					Map serial_no_list_map = (Map)serial_no_list_iterator.next();
						
					total_to_money =  total_to_money.add(Utility.parseDecimal(Utility.trimNull(serial_no_list_map.get("TO_MONEY")),new BigDecimal(0)));
					total_to_amount = total_to_amount.add(Utility.parseDecimal(Utility.trimNull(serial_no_list_map.get("TO_AMOUNT")),new BigDecimal(0))); %>
					<tr class="tr<%=(iCount% 2)%>">
						<td align="center" ><%=Utility.trimNull(serial_no_list_map.get("CUST_NAME"))%></td>
						<td align="center" ><%=Utility.trimNull(Format.formatDateLine(Utility.parseInt(Utility.trimNull(serial_no_list_map.get("DZ_DATE")),new Integer(0))))%></td>
						<td align="center" ><%=Utility.trimNull(serial_no_list_map.get("JK_TYPE_NAME"))%></td>
						<td align="right" ><font color='red'><%=Format.formatMoney(Utility.parseDecimal(Utility.trimNull(serial_no_list_map.get("TO_MONEY")),new BigDecimal(0)).setScale(2,2))%></font></td>
						<td align="right" ><font color='red'><%=Format.formatMoney(Utility.parseDecimal(Utility.trimNull(serial_no_list_map.get("TO_AMOUNT")),new BigDecimal(0)).setScale(2,2))%></font></td>
						<td align="center" ><%=Utility.trimNull(serial_no_list_map.get("CITY_NAME"))%></td>
					</tr>    
				<%}%>
					<tr class="tr<%=iCount%2%>">
						<td align="center" >合计<%=iCount%>项</td>
						<td align="center" >-</td>
						<td align="center" >-</td>
						<td align="right" ><font color='red'><%=Format.formatMoney(total_to_money)%></font><input name="rg_money" type="hidden" value="<%=total_to_money%>"/></td>
						<td align="right" ><font color='red'><%=Format.formatMoney(total_to_amount)%></font></td>
						<td align="center" >-</td>
					</tr>  
			    </table>
		    </td>
		</tr>

		<tr>
			<td><b>合同信息</b></td>  
		</tr>
		<tr id="trarFlag" style="<%if(product_status.equals("110203")){out.println("display:''");}else{out.println("display:none");}%>">
			<td align="right"><b>申购方式</b></td>
			<td colspan="3">
				<select name="applyreach_flag" onchange="javascript:arFlagChange();" style="width:300px">
					<%=Argument.getContractList(input_bookCode,productId,sub_product_id2,cust_id,"")%>
				</select>	
			</td>
			<td>&nbsp;</td>
		</tr>

		<tr>
			<td align="right"><span style="color:red;font-weight:bold">*</span>签署日期:</td>
			<td>
				<INPUT TYPE="text" NAME="qs_date_picker" class=selecttext >
				<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.qs_date_picker,theform.qs_date_picker.value,this);" tabindex="13">
				<INPUT TYPE="hidden" NAME="qs_date">
			</td>
			<td id="td_contract_bh" style="<%if(product_status.equals("110203")) out.println("display:none");else{out.println("display:''");}%>" align="right"><span style="color:red;font-weight:bold">*</span>合同编号:</td>
			<td id="td_contract_bh_input" style="<%if(product_status.equals("110203")) out.println("display:none");else{out.println("display:''");}%>">
				<input name="contract_sub_bh" size="40" maxlength=40 onkeydown="javascript:nextKeyPress(this)" >
				&nbsp;&nbsp;&nbsp;<INPUT type="button" class="xpbutton3" onclick="javascript:check();" value="验证编号">
				<div id="contractDIV"></div>
			</td>
		</tr>

		<tr>
			<td ><b>客户信息</b></td>
		</tr>
		<tr>
			<td align="right">客户名称:</td>
			<td><input maxlength="100" readonly class='edline'  name="customer_cust_name" size="42" onkeydown="javascript:nextKeyPress(this);" value="<%=Utility.trimNull(cust_name)%>">
				&nbsp;&nbsp;&nbsp;<button type="button"  class="xpbutton2" accessKey=e name="btnEdit" title="客户信息" onclick="javascript:showCustInfo(<%=cust_id%>,<%=cust_type%>);">查看</button></td>
			<td align="right">客户类别:</td>
			<td ><INPUT readonly class='edline' name="customer_cust_type_name" size="20" value="<%=cust_type_name%>" onkeydown="javascript:nextKeyPress(this);"></td>
		</tr>
		<tr>
			<td align="right">合同联系人:</td>
			<td colspan="3">
				<input type="text" name="ht_cust_name" size="25" onkeydown="javascript:nextKeyPress(this)" value="<%=ht_cust_name%>"/>
				&nbsp;&nbsp;电话: <input type="text" name="ht_cust_tel" size="20" onkeydown="javascript:nextKeyPress(this)" value="<%=ht_cust_tel%>"/>
				&nbsp;&nbsp;地址: <input type="text" name="ht_cust_address" size="50" onkeydown="javascript:nextKeyPress(this)" value="<%=ht_cust_address%>"/>	
			</td>
		</tr>
		<tr>
			<td><b>受益信息</b></td>  
		</tr>
		<tr>
			<td align="right" >受益人银行名称:</td>
			<td ><select size="1" name="bank_id" onchange="javascript:selectBank(this.value);" style="WIDTH: 180px">
				<%=Argument.getBankOptions("")%></select>
				<input type="text" name="bank_sub_name" size="20" onkeydown="javascript:nextKeyPress(this)" >
			</td>
			<td align="right">受益人银行帐号:</td>
			<td><input <%if(inputflag==1) out.print("style=display:none");%>  type="text" name="bank_acct" onkeydown="javascript:nextKeyPress(this)" onkeyup="javascript:showAcctNum(this.value)" size="30">
				<span id="bank_acct_content"><select <%if(inputflag==2) out.print("style=display:none");%> size="1" name="bank_acct2" id="bank_acct2" onkeydown="javascript:nextKeyPress(this)"  style="WIDTH: 200px">
					<%=preCodeOptions%></select></span>&nbsp;&nbsp;										
				<button type="button"  class="xpbutton2" <%if(inputflag==1){out.print("style='display: '");}else{out.print("style='display: none'");}%>style="display: none" accessKey=x name="btnSelect" title="输入" onclick="javascript:changeInput(this);">输入(<u>X</u>)</button>
				<span id="bank_acct_num" class="span"></span> 
			</td>
		</tr>

		<!--反洗钱 添加属性-->
		<tr>
			<td align="right">银行账户类型:</td>
			<td >
				<select size="1" name="bank_acct_type" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 250px">
					<%=Argument.getBankAcctType("")%>
				</select>
			</td>
			<td align="right">银行帐号户名:</td>
			<td><input name="customer_gain_acct" size="1" style="WIDTH: 250px" onkeydown="javascript:nextKeyPress(this)"></td>						
		</tr>

		<tr>
			<td align="right" width="10%" >开户行所在地:</td>
			<td>
				<select size="1" name="bank_province" style="width: 130px" onkeydown="javascript:nextKeyPress(this)" onchange="javascript:setGovRegional(this.value,'');">
					<%=Argument.getCustodianNameLis(new Integer(9999), "", new Integer(0),bank_province)%>
				</select>
				<span id="gov_regional_span">
					<select style="WIDTH: 150px" name="bank_city">
						<%=Argument.getCustodianNameLis(new Integer(9999), bank_province, new Integer(0),bank_city)%>
					</select>
				</span><!-- 省级行政区域过滤出来的相关区域 -->
			</td>
			<td align="right" width="10%" >推荐人:</td>
			<td>
				<select name="recommend_man" style="width:230px;" onkeydown="javascript:nextKeyPress(this)">
					<%=Argument.getIntrustOptions(recommend_man,new Integer(1)) %>
				</select>
			</td>
		</tr>

		<tr>
			<td align="right"><%if(open_flag.intValue()==1){%>收益分配方式:<%}%></td>
			<td colspan="3">
				<%if(open_flag!=null && open_flag.intValue()==1){%>
					<select size="1" name="bonus_flag" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px" onchange="javascript:showBonus_rate();">
						<%=Argument.getBonus_flag(Integer.valueOf("0"))%>
					</select>&nbsp;&nbsp;&nbsp;
					<span id="bonus_rate_div" style="display:none">转份额比例:
						<input type="text" name="bonus_rate" size="10" onkeydown="javascript:nextKeyPress(this)" >
					</span>
				<%}%>
			</td>
		</tr>
	<%if(intrust_flag1!=1){%>
		<tr>
			<td align="right">受益优先级:</td>
			<td>					
				<select name="prov_flag" style="WIDTH: 100px" <%if(asfund_flag.intValue()==3){%> onchange="javascript:getprovlevel()"<%}%> onkeydown="javascript:nextKeyPress(this)">
					<%=Argument.getProductProvFlag(productId,sub_product_id2,prov_flag)%>
				</select>
			</td>
		<%if(asfund_flag.intValue()==3){%>
			<td align="right">收益级别:</td>
			<td>
				<div id="div_prov_level">
				<select name="prov_level" style="width:100px">
					<%=Argument.getProductProvLevel(productId,sub_product_id2,prov_flag,prov_level)%>
				</select>	
				</div>
			</td>
		<%}%>
		</tr>
	<%}%>
		
		<tr>
			<td colspan="4"><b>其他信息</b></td>  
		</tr>
		<tr>
			<td align="right" width="10%">渠道名称:</td>
			<td>
				<select id="market_trench" name="market_trench" style="width: 280px;" onchange="javascript:setMartetTrench(this);">
					<%=Argument.queryMarketTrench(productId,sub_product_id2,marValue) %>
				</select>
			</td>
			<td align="right" width="10%" >渠道费用:</td>
			<td><input name="market_trench_money" size="48" value="<%=market_trench_money %>" 
					onkeydown="javascript:nextKeyPress(this)" onkeyup="javascript:showCnMoney(this.value,market_trench_money_cn)">
			</td>
		</tr>
		<tr>
			<td colspan="3"></td>
			<td><span id="market_trench_money_cn" class="span"></span></td>
		</tr>
		<tr >
			<td align="right" width="10%" ><%=LocalUtilis.language("class.partnAttachmentInfo",clientLocale)%>:</td><!--渠道附加信息-->
			<td>
				<input name="channel_memo" size="51" onkeydown="javascript:nextKeyPress(this)"  value="<%=channel_memo%>">
			</td>
			<td align="right" width="10%" ><%=LocalUtilis.language("class.channelCooperation",clientLocale)%>:</td><!-- 渠道合作方式 -->
			<td>
				<SELECT name="channel_cooperation" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 265px">
					<%=Argument.getChannelCooperationOptions(channel_cooperation)%>
				</SELECT>
			</td>
		</tr>
		<tr>
			<td align="right" width="10%"><%=LocalUtilis.language("class.linkMan",clientLocale)%>:</td><!--合同销售人员-->
			<td>
				<select name="link_man" onkeydown="javascript:nextKeyPress(this)" style="WIDTH:280px" onchange="javascript:queryTeamquota(this.value);">
					<%=Argument.getOperatorOptionsByRoleId(new Integer(2),link_man)%>
				</select>
			</td>
	
			<td align="right">预期收益率:</td>
			<td>
				<input name="expect_ror_lower" size="12" value="<%=Utility.trimZero(expect_ror_lower)%>" onkeydown="javascript:nextKeyPress(this)"><font color="red">%</font> 到 
				<input name="expect_ror_upper" size="12" value="<%=Utility.trimZero(expect_ror_upper)%>" onkeydown="javascript:nextKeyPress(this)"><font color="red">%</font>
			</td>
		</tr>
		<tr>
			<td align="right">合同推介地:</td>
			<td>
				<select size="1" name="city_serialno" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px">
				<%=cityNameOptions%></select>&nbsp;&nbsp;
				<button type="button"  class="xpbutton5" accessKey=r id="btnSetcity" name="btnSetcity" onclick="javascript:newCity(<%=productId%>,'');" >推介地设置</button>
			</td>
		</tr>
		<tr>
			<td align="right">合作方式:</td>
			<td colspan="3">
				<input onkeydown="javascript:nextKeyPress(this)" type="checkbox" class="flatcheckbox" value="0" name="with_security_flag">
				证信合作 &nbsp;&nbsp;
				<input onkeydown="javascript:nextKeyPress(this)" type="checkbox" class="flatcheckbox" value="0" name="with_private_flag">
				私募基金合作&nbsp;&nbsp;
				<input onkeydown="javascript:nextKeyPress(this)" type="checkbox" class="flatcheckbox" value="0" name="with_bank_flag" onclick="javascript:showHtyh();">
				银信合作&nbsp;&nbsp;&nbsp;&nbsp;
				<span id="htyhmc_v" style="display:none">
					合作银行:				
					<select size="1" name="ht_bank_id" style="WIDTH: 220px">
						<%=Argument.getBankOptions("")%>
					</select>
					<input type="text" name="ht_bank_sub_name" size="20" onkeydown="javascript:nextKeyPress(this)">
				</span>		
			</td>
		</tr>
		<tr>
			<td width="120px" align="right">用款方关联:</td>
			<td colspan="3">   
				<INPUT type="radio" class="flatcheckbox" id="is_ykgl" name="is_ykgl" value="1" <%if(is_ykgl.equals("1")) out.print("checked");%>>是
				<INPUT type="radio" class="flatcheckbox" id="is_ykgl" name="is_ykgl" value="0" <%if(!is_ykgl.equals("1")) out.print("checked");%>>否
			</td>
		</tr>
		<tr>
			<td align="right" vAlign="top" width="10%">合同预计收益率:</td>
			<td colspan="3"><textarea rows="3" id="xthtyjsyl" name="xthtyjsyl" onkeydown="javascript:nextKeyPress(this)" cols="83"><%=xthtyjsyl %></textarea></td>
		</tr>
		<tr>
			<td align="right" width="10%"><%=LocalUtilis.language("class.description",clientLocale)%>:</td><!--备注-->
			<td colspan="3">
				<textarea rows="3" name="summary2" onkeydown="javascript:nextKeyPress(this)" cols="83"><%=summary%></textarea>
			</td>
		</tr>

		<tr>
	      	<td class="paramTitle"align="right" width="10%"><%=LocalUtilis.language("menu.filesAdd",clientLocale)%>:</td><!-- 添加附件 -->
	        <td colspan="3">
	        	<table id="test" width="100%"></table>
		        <button type="button"   onclick="addline()"><%=LocalUtilis.language("class.moreMccessories",clientLocale)%> <!--单击此处添加更多附件--></button>
	        </td>
		</tr>
	</TABLE>

	<div align="right">
		<button type="button"  class="xpbutton3"  name="btnCheck" title='保存' onclick="javascript:validateForm();">保存(<u>S</u>) </button>&nbsp;&nbsp;&nbsp; 
        <button type="button"  class="xpbutton3" accessKey=b id="btnBack" name="btnBack" onclick="javascript:disableAllBtn(true);op_return();">返回(<u>B</u>)</button>&nbsp;&nbsp;&nbsp;
	</div>
</div>
</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>