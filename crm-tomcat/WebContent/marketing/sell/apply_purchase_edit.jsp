<%@ page contentType="text/html; charset=GBK" import="enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@page import="enfo.crm.web.DocumentFile2"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
//申购信息编辑 获得页面传递变量
String money_origin = "";
String sub_money_origin = "";
String sQuery = request.getParameter("sQuery");
String q_productId = Utility.trimNull(request.getParameter("product_id"));  //产品信息ID
String q_cust_id = Utility.trimNull(request.getParameter("customer_cust_id")); //客户ID
String bank_id = Utility.trimNull(request.getParameter("bank_id")); //银行账户ID
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"),null);//申购合同信息流水号
Integer cntr_serial_no = Utility.parseInt(request.getParameter("cntr_serial_no"),null);//认购合同信息流水号

String channel_type = Utility.trimNull(request.getParameter("channel_type"));
String channel_memo = Utility.trimNull(request.getParameter("channel_memo"));
Integer r_channel_id = Utility.parseInt(Utility.trimNull(request.getParameter("channel_id")),new Integer(0));

int inputflag= Utility.parseInt(request.getParameter("inputflag"), 1);//银行账号输入标志
int sub_flag = Utility.parseInt(request.getParameter("sub_flag"), 0);//提交标志
Integer bonus_flag = Utility.parseInt(request.getParameter("bonus_flag"), new Integer(0));//收益分配方式

Integer fee_jk_type = Utility.parseInt(request.getParameter("fee_jk_type"), new Integer(0));
String bank_acct_type = Utility.trimNull(request.getParameter("bank_acct_type")); 
String jk_type = Utility.trimNull(request.getParameter("jk_type")); 

String bank_sub_name =  Utility.trimNull(request.getParameter("bank_sub_name")); 
String contract_bh = Utility.trimNull(request.getParameter("contract_bh"));//合同序号

BigDecimal expect_ror_lower = Utility.parseDecimal(request.getParameter("expect_ror_lower"), new BigDecimal(0)); //预期收益率(起)
BigDecimal expect_ror_upper = Utility.parseDecimal(request.getParameter("expect_ror_upper"), new BigDecimal(0)); //预期收益率(止)
Integer qs_date = Utility.parseInt(Utility.trimNull(request.getParameter("qs_date")),null);//签署日期
Integer jk_date = Utility.parseInt(Utility.trimNull(request.getParameter("jk_date")),null);
Integer start_date = Utility.parseInt(Utility.trimNull(request.getParameter("start_date")),null);//起始日期
BigDecimal market_trench_money = Utility.parseDecimal(Utility.trimNull(request.getParameter("market_trench_money")),new BigDecimal(0));
String prov_level =  Utility.trimNull(request.getParameter("prov_level"));
String ht_bank_id =  Utility.trimNull(request.getParameter("ht_bank_id"));
String ht_bank_sub_name =  Utility.trimNull(request.getParameter("ht_bank_sub_name"));
Integer with_bank_flag = Utility.parseInt(request.getParameter("with_bank_flag"), new Integer(0));
Integer with_security_flag = Utility.parseInt(request.getParameter("with_security_flag"), new Integer(0));
Integer with_private_flag = Utility.parseInt(request.getParameter("with_private_flag"), new Integer(0));
Integer prov_flag = Utility.parseInt(request.getParameter("prov_flag"), new Integer(0));
BigDecimal bonus_rate = Utility.parseDecimal(Utility.trimNull(request.getParameter("bonus_rate")),new BigDecimal(0));
Integer spot_deal = null;

Integer asfund_flag = new Integer(0);
Integer open_flag = new Integer(0);
Integer intrust_flag1 = new Integer(0);
String property_souce="";
String other_explain="";

DocumentFile2 file = null;
if(request.getMethod().equals("POST")){
	file = new DocumentFile2(pageContext);
	file.parseRequest();
	/*********因为增加了一个附件上传，所以原先request方法取值的改为 file*******************/
	sQuery = file.getParameter("sQuery");
	q_productId = Utility.trimNull(file.getParameter("product_id"));  //产品信息ID
	q_cust_id = Utility.trimNull(file.getParameter("customer_cust_id")); //客户ID
	bank_id = Utility.trimNull(file.getParameter("bank_id")); //银行账户ID
	serial_no = Utility.parseInt(file.getParameter("serial_no"),null);//申购合同信息流水号
	cntr_serial_no = Utility.parseInt(file.getParameter("cntr_serial_no"),null);//认购合同信息流水号
	
	inputflag= Utility.parseInt(file.getParameter("inputflag"), 1);//银行账号输入标志
	sub_flag = Utility.parseInt(file.getParameter("sub_flag"), 0);//提交标志
	bonus_flag = Utility.parseInt(file.getParameter("bonus_flag"), new Integer(0));//收益分配方式
	
	fee_jk_type = Utility.parseInt(file.getParameter("fee_jk_type"), new Integer(0));
	bank_acct_type = Utility.trimNull(file.getParameter("bank_acct_type")); 
	jk_type = Utility.trimNull(file.getParameter("jk_type")); 
	
	bank_sub_name =  Utility.trimNull(file.getParameter("bank_sub_name")); 
	contract_bh = Utility.trimNull(file.getParameter("contract_bh"));//合同序号
	//expect_ror_lower = Utility.parseDecimal(file.getParameter("expect_ror_lower"), new BigDecimal(0));//预期收益率(起)
	//expect_ror_upper = Utility.parseDecimal(file.getParameter("expect_ror_upper"), new BigDecimal(0));//预期收益率(止)
	expect_ror_lower = Utility.parseDecimal(file.getParameter("expect_ror_lower"),new BigDecimal(0.00),4,"0.01");//预期收益率(起)
	expect_ror_upper = Utility.parseDecimal(file.getParameter("expect_ror_upper"),new BigDecimal(0.00),4,"0.01");//预期收益率(止)

	qs_date = Utility.parseInt(Utility.trimNull(file.getParameter("qs_date")),null);//签署日期
	jk_date = Utility.parseInt(Utility.trimNull(file.getParameter("jk_date")),null);
	start_date = Utility.parseInt(Utility.trimNull(file.getParameter("start_date")),null);//起始日期
	
	prov_level =  Utility.trimNull(file.getParameter("prov_level"));
	ht_bank_id =  Utility.trimNull(file.getParameter("ht_bank_id"));
	ht_bank_sub_name =  Utility.trimNull(file.getParameter("ht_bank_sub_name"));
	with_bank_flag = Utility.parseInt(file.getParameter("with_bank_flag"), new Integer(0));
	with_security_flag = Utility.parseInt(file.getParameter("with_security_flag"), new Integer(0));
	with_private_flag = Utility.parseInt(file.getParameter("with_private_flag"), new Integer(0));
	prov_flag = Utility.parseInt(file.getParameter("prov_flag"), new Integer(0));
	bonus_rate = Utility.parseDecimal(Utility.trimNull(file.getParameter("bonus_rate")),new BigDecimal(0)); 
	
	if(bonus_flag.intValue() == 1){//现金
		bonus_rate = new BigDecimal(0);
	}else if(bonus_flag.intValue() == 2){//转份额
		bonus_rate = new BigDecimal(1);
	}else{//部分转份额
		bonus_rate = Utility.parseDecimal(Utility.trimNull(file.getParameter("bonus_rate")),new BigDecimal(0)).multiply(new BigDecimal("0.01"));
	}
}	

//页面参数变量声明 及帐套暂时设置
input_bookCode = new Integer(1);
Integer userid = new Integer(2);
Integer citySerialno = new Integer(0);
int is_local = 1;//本地标志
int is_product_sub_flag = Argument.getSyscontrolValue_1("SUBCP01");//子产品开关
String fee_type_name = enfo.crm.tools.LocalUtilis.language("class.rgFee",clientLocale);//认购费
boolean bSuccess = false;
String preCodeOptions = "";//受益人银行帐号
String cityNameOptions = "";
String summary = "";
String checkedStyle = ""; //如果审核 一些信息要变成只读
String checkedStyle2 = "";

//声明参数
//ID 声明
Integer cust_id = Utility.parseInt(q_cust_id,new Integer(0));
Integer product_id = Utility.parseInt(q_productId,new Integer(0));

//申购合同信息
String bank_acct = "";
String gain_acct = "";
BigDecimal rg_money = new BigDecimal(0);//申购金额
Integer sub_product_id = new Integer(0);
Integer link_man = new Integer(0);
Integer check_flag = new Integer(0);
Integer contact_id = null;
Integer contract_type = new Integer(0);

//产品信息
Integer item_id = new Integer(0);
String period_unit_name = "";
Integer period_unit = new Integer(0);
Integer product_sub_flag = new Integer(0);//子产品标志
String product_code = "";
String product_status ="";
Integer valid_period = null;
Integer pre_start_date = new Integer(0);
Integer pre_end_date = new Integer(0);

//客户信息
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
String channel_coopertype = "";
List markList = null;
Map markMap = null;
String market_selected = "";
Integer recommend_man = new Integer(0);
String bank_city = "",bank_province = "";

String ht_cust_name = "";
String ht_cust_address = "";
String ht_cust_tel = "";

//认购合同信息
String contract_sub_bh = "";//合同编号
Integer target_cust_id = new Integer(0);

//------------------------------------
//获得对象
ContractLocal contract = EJBFactory.getContract();//合同
ApplyreachLocal apply_reach = EJBFactory.getApplyreach();
ProductLocal product = EJBFactory.getProduct();//产品
CustomerLocal customer = EJBFactory.getCustomer();//客户
Customer_INSTLocal  customer_inst = EJBFactory.getCustomer_INST();//同步用客户对象

ContractVO cont_vo = new ContractVO();
ApplyreachVO appl_vo = new ApplyreachVO();
CustomerVO cust_vo = new CustomerVO();
CustomerVO c_vo = new CustomerVO();//同步用
ProductVO p_vo = new ProductVO();

//说明：产品信息控制认购信息 认购信息控制客户信息 
//申购合同信息显示
List attachmentList = null;
if(serial_no!=null){
	List rsList_appl = null;
	Map map_appl = null;
	
	//取单个产品值	 		
	appl_vo.setBook_code(input_bookCode);
	appl_vo.setSerial_no(serial_no);	
	appl_vo.setInput_man(input_operatorCode);
	rsList_appl = apply_reach.listBySql(appl_vo);
	
	if(rsList_appl.size()>0){
		map_appl = (Map)rsList_appl.get(0);
	}
	
	//赋值
	if(map_appl!=null){
		cust_id = Utility.parseInt(Utility.trimNull(map_appl.get("CUST_ID")), new Integer(0));	
		contact_id = Utility.parseInt(Utility.trimNull(map_appl.get("CONTACT_ID")),null);
		product_id =  Utility.parseInt(Utility.trimNull(map_appl.get("PRODUCT_ID")), new Integer(0));
		bank_id = Utility.trimNull(map_appl.get("BANK_ID"));
		bank_sub_name =  Utility.trimNull(map_appl.get("BANK_SUB_NAME"));
		bank_acct = Utility.trimNull(map_appl.get("BANK_ACCT"));
		bank_acct_type = Utility.trimNull(map_appl.get("BANK_ACCT_TYPE"));
		contract_bh = Utility.trimNull(map_appl.get("CONTRACT_BH"));
		contract_sub_bh = Utility.trimNull(map_appl.get("CONTRACT_SUB_BH"));
		gain_acct = Utility.trimNull(map_appl.get("GAIN_ACCT"));
		jk_type = Utility.trimNull(map_appl.get("JK_TYPE"));
		bonus_flag = Utility.parseInt(Utility.trimNull(map_appl.get("BONUS_FLAG")), new Integer(0));
		rg_money = 	Utility.parseDecimal(Utility.trimNull(map_appl.get("SG_MONEY")), new BigDecimal(0)).setScale(2,2);
		fee_jk_type = Utility.parseInt(Utility.trimNull(map_appl.get("FEE_JK_TYPE")), new Integer(0));	
		qs_date = Utility.parseInt(Utility.trimNull(map_appl.get("QS_DATE")),null);//签署日期
		jk_date = Utility.parseInt(Utility.trimNull(map_appl.get("JK_DATE")),null);
		start_date = Utility.parseInt(Utility.trimNull(map_appl.get("START_DATE")),null);//起始日期
		link_man = Utility.parseInt(Utility.trimNull(map_appl.get("LINK_MAN")), new Integer(0));	
		citySerialno = Utility.parseInt(Utility.trimNull(map_appl.get("CITY_SERIAL_NO")), new Integer(0));	
		summary = Utility.trimNull(map_appl.get("SUMMARY"));
		check_flag = Utility.parseInt(Utility.trimNull(map_appl.get("CHECK_FLAG")),new Integer(0));
		sub_product_id =  Utility.parseInt(Utility.trimNull(map_appl.get("SUB_PRODUCT_ID")),new Integer(0));
		channel_type = Utility.trimNull(map_appl.get("CHANNEL_TYPE"));
		channel_memo = Utility.trimNull(map_appl.get("CHANNEL_MEMO"));
		r_channel_id = Utility.parseInt(Utility.trimNull(map_appl.get("CHANNEL_ID")),new Integer(0));
		market_trench_money = Utility.parseDecimal(Utility.trimNull(map_appl.get("MARKET_MONEY")),new BigDecimal(0)).setScale(2,2);
		with_bank_flag = Utility.parseInt(Utility.trimNull(map_appl.get("WITH_BANK_FLAG")), new Integer(0));
		with_security_flag = Utility.parseInt(Utility.trimNull(map_appl.get("WITH_SECURITY_FLAG")), new Integer(0));
		with_private_flag = Utility.parseInt(Utility.trimNull(map_appl.get("WITH_PRIVATE_FLAG")), new Integer(0));
		prov_flag = Utility.parseInt(Utility.trimNull(map_appl.get("PROV_FLAG")), new Integer(0));
	
		prov_level = Utility.trimNull(map_appl.get("PROV_LEVEL"));
		ht_bank_id =  Utility.trimNull(map_appl.get("HT_BANK_ID"));
		ht_bank_sub_name =  Utility.trimNull(map_appl.get("HT_BANK_SUB_NAME"));
		bonus_rate = Utility.parseDecimal(Utility.trimNull(map_appl.get("BONUS_RATE")),new BigDecimal(0));
		channel_coopertype = Utility.trimNull(map_appl.get("CHANNEL_COOPERTYPE"));
		recommend_man = Utility.parseInt(Utility.trimNull(
						map_appl.get("RECOMMEND_MAN")),new Integer(0));
		
		bank_province = Utility.trimNull(map_appl.get("BANK_PROVINCE"));
		bank_city = Utility.trimNull(map_appl.get("BANK_CITY"));
		expect_ror_lower = Utility.parseDecimal(Utility.trimNull(map_appl.get("EXPECT_ROR_LOWER")), new BigDecimal(0)).multiply(new BigDecimal(100));//预期收益率(起)
		expect_ror_upper = Utility.parseDecimal(Utility.trimNull(map_appl.get("EXPECT_ROR_UPPER")), new BigDecimal(0)).multiply(new BigDecimal(100));//预期收益率(止)
		valid_period = (Integer)map_appl.get("VALID_PERIOD"); //
		period_unit = (Integer)map_appl.get("PERIOD_UNIT"); //

		money_origin=Utility.trimNull(map_appl.get("MONEY_ORIGIN"));
		sub_money_origin=Utility.trimNull(map_appl.get("SUB_MONEY_ORIGIN"));

		ht_cust_name = Utility.trimNull(map_appl.get("HT_CUST_NAME"));
		ht_cust_address = Utility.trimNull(map_appl.get("HT_CUST_ADDRESS"));
		ht_cust_tel = Utility.trimNull(map_appl.get("HT_CUST_TEL"));

		spot_deal = Utility.parseInt(Utility.trimNull(map_appl.get("SPOT_DEAL")), new Integer(2));
		property_souce=Utility.trimNull(map_appl.get("PROPERTY_SOURCE"));
		other_explain=Utility.trimNull(map_appl.get("OTHER_EXPLAIN"));
		contract_type = Utility.parseInt(Utility.trimNull(map_appl.get("CONTRACT_TYPE")), new Integer(0));

		if(check_flag.intValue()>=2){
			checkedStyle = "readonly class='edline'";
			checkedStyle2 = "disabled";
		}
	}
	
	//获得认购附件
	
	AttachmentLocal attachmentLocal = EJBFactory.getAttachment();
	AttachmentVO attachment_vo = new AttachmentVO();
	attachment_vo.setDf_talbe_id(new Integer(7));
	attachment_vo.setDf_serial_no(serial_no);
	
	attachmentList = attachmentLocal.load(attachment_vo);
}

//产品信息显示
if(product_id.intValue()>0){	
	List rsList_product = null;
	Map map_product = null;
	
	//取单个产品值	 		
	p_vo.setProduct_id(product_id);	
	rsList_product = product.load(p_vo);
	
	if(rsList_product.size()>0){
		map_product = (Map)rsList_product.get(0);
	}

	//产品销售渠道信息
	//markList = product.queryMarketTrench(p_vo);	
	
	if(map_product!=null){		
		item_id = Utility.parseInt(Utility.trimNull(map_product.get("ITEM_ID")),new Integer(0));
		open_flag = Utility.parseInt(Utility.trimNull(map_product.get("OPEN_FLAG")),new Integer(0));
		intrust_flag1 = Utility.parseInt(Utility.trimNull(map_product.get("INTRUST_FLAG1")),new Integer(0));
		product_code = Utility.trimNull(map_product.get("PRODUCT_CODE"));	
		product_status = Utility.trimNull(map_product.get("PRODUCT_STATUS"));
		//period_unit = (Integer)map_product.get("PERIOD_UNIT");
		//valid_period = (Integer)map_product.get("VALID_PERIOD");
		pre_start_date =  Utility.parseInt(Utility.trimNull(map_product.get("PRE_STRAT_DATE")),new Integer(0));
		pre_end_date =  Utility.parseInt(Utility.trimNull(map_product.get("PRE_END_DATE")),new Integer(0));
		is_local = Utility.parseInt(Utility.trimNull(map_product.get("IS_LOCAL")),0);
		Boolean temp_flag = (Boolean)map_product.get("SUB_FLAG");		
		Integer input_man = Utility.parseInt(Utility.trimNull(input_operatorCode),new Integer(0));
		String asfund_flag_s = Argument.getProductAsfundFlag(product_id,input_man);
		asfund_flag = Utility.parseInt(Utility.trimNull(asfund_flag_s),new Integer(0));
		
		if(temp_flag.booleanValue()){
			product_sub_flag = new Integer(1);
		}
		else{
			product_sub_flag = new Integer(2);
		}
		
		/*if(period_unit!=null && period_unit.intValue()>0){
			period_unit_name = Argument.getProductUnitName(period_unit);
		}*/
		
		if(product_status.equals("110203")){
			fee_type_name = enfo.crm.tools.LocalUtilis.language("class.sgFeeAmount",clientLocale);//申购费
		}						
	}
	//取产品结束

	//搜索认购合同信息 取合同编号及客户ID 及contract_serial_no
	if(!contract_bh.equals("")){
		List rsList_contract = null;
		Map map_contract = null;
		
		cont_vo.setBook_code(input_bookCode);
		cont_vo.setInput_man(input_operatorCode);
		cont_vo.setContract_bh(contract_bh);
		cont_vo.setProduct_id(product_id);
		
		rsList_contract = contract.queryContract(cont_vo);
		
		if(rsList_contract.size()>0){
			map_contract = (Map)rsList_contract.get(0);
		}
		
		if(map_contract!=null){
			contract_sub_bh = Utility.trimNull(map_contract.get("CONTRACT_SUB_BH"));
			target_cust_id = Utility.parseInt(Utility.trimNull(map_contract.get("CUST_ID")), new Integer(0));			
			cntr_serial_no =  Utility.parseInt(Utility.trimNull(map_contract.get("SERIAL_NO")), new Integer(0)); 	
		}			
		//搜索完成	定义客户ID	
		if(target_cust_id.intValue()>0){
			cust_id = target_cust_id;
		}	
	}	
}

//客户信息显示
if(cust_id.intValue()>0){
	List rsList_cust = null;
	Map map_cust = null;	
	
	//客户单个值		
	cust_vo.setCust_id(cust_id);
	cust_vo.setInput_man(input_operatorCode);
	rsList_cust = customer.listByControl(cust_vo);
			
	if(rsList_cust.size()>0){
		map_cust = (Map)rsList_cust.get(0);
	}

	if(map_cust!=null){		
		cust_name = Utility.trimNull(map_cust.get("CUST_NAME"));
		e_mail  = Utility.trimNull(map_cust.get("E_MAIL"));
		cust_no = Utility.trimNull(map_cust.get("CUST_NO"));
		service_man = Utility.parseInt(Utility.trimNull(map_cust.get("SERVICE_MAN")),new Integer(0));		
		card_id = Utility.trimNull(map_cust.get("CARD_ID"));
		o_tel = Utility.trimNull(map_cust.get("O_TEL"));
		h_tel = Utility.trimNull(map_cust.get("H_TEL"));
		mobile = Utility.trimNull(map_cust.get("MOBILE"));
		bp = Utility.trimNull(map_cust.get("BP"));
		post_code = Utility.trimNull(map_cust.get("POST_CODE"));
		post_address = Utility.trimNull(map_cust.get("POST_ADDRESS"));
		card_type = Utility.parseInt(Utility.trimNull(map_cust.get("CARD_TYPE")),new Integer(0));
		cust_type = Utility.parseInt(Utility.trimNull(map_cust.get("CUST_TYPE")),new Integer(0));
		cust_type_name = Utility.trimNull(map_cust.get("CUST_TYPE_NAME"));
		is_link = Utility.parseInt(Utility.trimNull(map_cust.get("IS_LINK")),new Integer(0));
		legal_man = Utility.trimNull(map_cust.get("LEGAL_MAN"));
		contact_man  = Utility.trimNull(map_cust.get("CONTACT_MAN"));	
			
	}		
	
	String inputBank_acct ="";//空银行账号		
	preCodeOptions = Argument.getCustBankAcctOptions(cust_id,bank_id,card_id,bank_acct);
}

//执行提交操作
if(request.getMethod().equals("POST")&&sub_flag==1){
	//获得保存数据
	Integer s_cust_id = Utility.parseInt(file.getParameter("customer_cust_id"), null);
	Integer s_product_id = Utility.parseInt(file.getParameter("productId"), null); 
	Integer s_link_man = Utility.parseInt(file.getParameter("link_man"), input_operatorCode);
	BigDecimal s_rg_money = Utility.parseDecimal(file.getParameter("rg_money"),new BigDecimal(0));
	Integer s_bonus_flag = Utility.parseInt(file.getParameter("bonus_flag"),new Integer(1));
	String s_jk_type = file.getParameter("jk_type");
	String s_bank_id = file.getParameter("bank_id");
	String s_bank_sub_name = Utility.trimNull(file.getParameter("bank_sub_name"));
	String s_gain_acct = file.getParameter("customer_gain_acct");	
	Integer s_start_date = Utility.parseInt(file.getParameter("start_date"),new Integer(0));
	String s_bank_acct = "";
	
	if(inputflag==1){
		s_bank_acct = file.getParameter("bank_acct2");
	}
	else{
		s_bank_acct = file.getParameter("bank_acct");
	}
	
	Integer s_service_man = Utility.parseInt(file.getParameter("service_man"), input_operatorCode);
	String s_summary = Utility.trimNull(file.getParameter("summary2"));
	String s_contract_bh = file.getParameter("contract_bh");
	valid_period = Utility.parseInt(file.getParameter("valid_period"), null);
	period_unit = Utility.parseInt(file.getParameter("period_unit"), null);
	Integer s_qs_date = Utility.parseInt(file.getParameter("qs_date"), new Integer(Utility.getCurrentDate()));
	Integer s_jk_date = Utility.parseInt(file.getParameter("jk_date"), new Integer(Utility.getCurrentDate()));//改参数已作废，所以随便传入当前时间
	Integer s_check_man = Utility.parseInt(file.getParameter("check_man"), new Integer(0));
	
	String s_bank_acct_type = Utility.trimNull(file.getParameter("bank_acct_type"));
	Integer s_city_serialno = Utility.parseInt(file.getParameter("city_serialno"), new Integer(0));
	String s_contract_sub_bh = file.getParameter("contract_sub_bh");
	int s_fee_jk_type = Utility.parseInt(file.getParameter("fee_jk_type"),0);
	channel_type = Utility.trimNull(file.getParameter("channel_type"));
	channel_memo = Utility.trimNull(file.getParameter("channel_memo"));
	r_channel_id = Utility.parseInt(Utility.trimNull(file.getParameter("channel_id")),new Integer(0));
	market_trench_money = Utility.parseDecimal(Utility.trimNull(file.getParameter("market_trench_money")),new BigDecimal(0));
	Integer r_with_bank_flag = Utility.parseInt(file.getParameter("with_bank_flag"), new Integer(0));
	Integer r_with_security_flag = Utility.parseInt(file.getParameter("with_security_flag"), new Integer(0));
	Integer r_with_private_flag = Utility.parseInt(file.getParameter("with_private_flag"), new Integer(0));
	Integer r_prov_flag = Utility.parseInt(file.getParameter("prov_flag"), new Integer(0));
	String r_prov_level = Utility.trimNull(file.getParameter("prov_level"));
	String r_ht_bank_id =  Utility.trimNull(file.getParameter("ht_bank_id"));
	String r_ht_bank_sub_name =  Utility.trimNull(file.getParameter("ht_bank_sub_name"));
	channel_coopertype = Utility.trimNull(file.getParameter("channel_coopertype"));
	recommend_man = Utility.parseInt(file.getParameter("recommend_man"),new Integer(0));
	bank_province = Utility.trimNull(file.getParameter("bank_province"));
	bank_city = Utility.trimNull(file.getParameter("bank_city"));
	BigDecimal r_bonus_rate = new BigDecimal(0);
	//expect_ror_lower = Utility.parseDecimal(file.getParameter("expect_ror_lower"), new BigDecimal(0)); //预期收益率(起)
	//expect_ror_upper = Utility.parseDecimal(file.getParameter("expect_ror_upper"), new BigDecimal(0)); //预期收益率(止)
    expect_ror_lower = Utility.parseDecimal(file.getParameter("expect_ror_lower"),new BigDecimal(0.00),4,"0.01");//预期收益率(起)
	expect_ror_upper = Utility.parseDecimal(file.getParameter("expect_ror_upper"),new BigDecimal(0.00),4,"0.01");//预期收益率(止)

	contact_id = Utility.parseInt(file.getParameter("cust_message"),null);

	money_origin=Utility.trimNull(file.getParameter("money_origin"));
	sub_money_origin=Utility.trimNull(file.getParameter("sub_money_origin"));

	ht_cust_name = Utility.trimNull(file.getParameter("ht_cust_name"));
	ht_cust_address = Utility.trimNull(file.getParameter("ht_cust_address"));
	ht_cust_tel = Utility.trimNull(file.getParameter("ht_cust_tel"));
	
	spot_deal = Utility.parseInt(file.getParameter("spot_deal"), new Integer(2));
	property_souce=Utility.trimNull(file.getParameter("property_souce"));
	other_explain=Utility.trimNull(file.getParameter("other_explain"));
	contract_type = Utility.parseInt(file.getParameter("contract_type"),new Integer(0));

	if(s_bonus_flag.intValue() == 1){//现金
		r_bonus_rate = new BigDecimal(0);
	}else if(s_bonus_flag.intValue() == 2){//转份额
		r_bonus_rate = new BigDecimal(1);
	}else{//部分转份额
		r_bonus_rate = Utility.parseDecimal(Utility.trimNull(file.getParameter("bonus_rate")),new BigDecimal(0)).multiply(new BigDecimal("0.01"));
	}
	
	//同步客户信息
	c_vo.setCust_id(cust_id);
	c_vo.setCust_no(cust_no);
	c_vo.setCust_name(cust_name);
	c_vo.setCust_type(cust_type);
	c_vo.setCard_id(card_id);
	c_vo.setCard_type(card_type.toString());
	c_vo.setLegal_man(legal_man);
	c_vo.setContact_man(contact_man);
	c_vo.setPost_address(post_address);
	c_vo.setPost_code(post_code);
	c_vo.setMobile(mobile);
	c_vo.setE_mail(e_mail);
	c_vo.setService_man(service_man);
	c_vo.setInput_man(input_operatorCode);
	
	customer_inst.cope_customers(c_vo);	
	
	//保存数据	
	ApplyreachVO s_appl_vo = new ApplyreachVO();	
	
	s_appl_vo.setSerial_no(serial_no);
	s_appl_vo.setBook_code(input_bookCode);
	s_appl_vo.setCust_id(s_cust_id);
	s_appl_vo.setProduct_id(s_product_id);
	s_appl_vo.setLink_man(s_link_man);
	s_appl_vo.setSg_money(s_rg_money);	
	s_appl_vo.setBonus_flag(s_bonus_flag);
	s_appl_vo.setJk_type(s_jk_type);
	s_appl_vo.setBank_id(s_bank_id);
	s_appl_vo.setBank_sub_name(s_bank_sub_name);
	s_appl_vo.setGain_acct(s_gain_acct);
	s_appl_vo.setBank_acct(s_bank_acct);
	s_appl_vo.setService_man(s_service_man);
	s_appl_vo.setSummary(s_summary);
	s_appl_vo.setContract_bh(s_contract_bh);
	s_appl_vo.setValid_period(valid_period);
	s_appl_vo.setPeriod_unit(period_unit);
	s_appl_vo.setSq_date(s_qs_date);
	s_appl_vo.setJk_date(s_jk_date);
	s_appl_vo.setStart_date(s_start_date);
	s_appl_vo.setCheck_man(s_check_man);
	s_appl_vo.setBank_acct_type(s_bank_acct_type);
	s_appl_vo.setCity_serial_no(s_city_serialno);
	s_appl_vo.setContract_sub_bh(s_contract_sub_bh);
	s_appl_vo.setFee_jk_type(new Integer(s_fee_jk_type));	
	s_appl_vo.setInput_man(input_operatorCode);
	s_appl_vo.setCntr_serial_no(cntr_serial_no);
	s_appl_vo.setChannel_id(r_channel_id);
	s_appl_vo.setChannel_type(channel_type);
	s_appl_vo.setChannel_memo(channel_memo);
	s_appl_vo.setWith_bank_flag(r_with_bank_flag);
	s_appl_vo.setWith_private_flag(r_with_private_flag);
	s_appl_vo.setWith_security_flag(r_with_security_flag);
	s_appl_vo.setBonus_rate(r_bonus_rate);
	s_appl_vo.setProv_flag(r_prov_flag);
	s_appl_vo.setProv_level(r_prov_level);
	s_appl_vo.setHt_bank_id(r_ht_bank_id);
	s_appl_vo.setHt_bank_sub_name(r_ht_bank_sub_name);
	s_appl_vo.setChannel_cooperation(channel_coopertype);
	s_appl_vo.setMarket_trench_money(market_trench_money);
	s_appl_vo.setContact_id(contact_id);

	s_appl_vo.setMoney_origin(money_origin);
	s_appl_vo.setSub_money_origin(sub_money_origin);

	s_appl_vo.setHt_cust_name(ht_cust_name);
	s_appl_vo.setHt_cust_tel(ht_cust_tel);
	s_appl_vo.setHt_cust_address(ht_cust_address);
	s_appl_vo.setSpot_deal(spot_deal);
	s_appl_vo.setProperty_souce(property_souce);
	s_appl_vo.setOther_explain(other_explain);
	s_appl_vo.setContract_type(contract_type);

	s_appl_vo.setRecommend_man(Utility.parseInt(file.getParameter("recommend_man"),new Integer(0)));
	s_appl_vo.setBank_province(Utility.trimNull(file.getParameter("bank_province")));
	s_appl_vo.setBank_city(Utility.trimNull(file.getParameter("bank_city")));
	s_appl_vo.setExpect_ror_lower(expect_ror_lower);
	s_appl_vo.setExpect_ror_upper(expect_ror_upper);

	expect_ror_lower = expect_ror_lower.multiply(new BigDecimal(100));//显示用
	expect_ror_upper = expect_ror_upper.multiply(new BigDecimal(100));//显示用

	apply_reach.modi(s_appl_vo);	
	file.uploadAttchment(new Integer(7),serial_no,"",null,null,input_operatorCode);
	bSuccess = true;
}
else{
}

//设置城市推介地
cityNameOptions = Argument.getCitynameOptions(product_id,citySerialno);

//认购金额中文处理
String rg_money_cn = "";
if (rg_money.intValue()!= 0){
	rg_money_cn = "(" + Utility.numToChinese(rg_money.toString()) + ")";
}

//渠道渠道费率，选中渠道名称的
p_vo.setProduct_id(product_id);
p_vo.setSub_product_id(sub_product_id);
p_vo.setChannel_type(channel_type);
p_vo.setR_channel_id(r_channel_id);
//产品销售渠道信息
String marValue = "";
markList = product.queryMarketTrench(p_vo);
if(markList.size()>0){
	Map marMap = (Map)markList.get(0);
	marValue =  Utility.trimNull(marMap.get("CHANNEL_TYPE"))+"@"+
				Utility.trimNull(marMap.get("CHANNEL_ID"))+"@"+
				Utility.trimNull(Utility.parseDecimal(Utility.trimNull(
					marMap.get("CHANNEL_FARE_RATE")), new BigDecimal(0),4,"1"));
}

%>

<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("menu.applyPurchaseEdit",clientLocale)%> </TITLE>
<!--申购信息编辑-->
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<link href="<%=request.getContextPath()%>/widgets/ext/resources/css/ext-all.css" type="text/css"  rel="stylesheet" /> 
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
<script language="javascript">
var n=1;
/*设置产品*/
function setProduct(value){
	var prodid=0;
	
	if (event.keyCode == 13 && value != ""){
       j = value.length;
       
	   for(i=0;i<document.theform.product_id.options.length;i++){
			if(document.theform.product_id.options[i].text.substring(0,j)==value){
				document.theform.product_id.options[i].selected=true;
				prodid = document.theform.product_id.options[i].value;
				location.replace('apply_purchase_add.jsp?page=<%=sPage%>' + getLastOptions());				
			}	
		}
		
		if (prodid==0){
			sl_alert("<%=LocalUtilis.language("message.inputProdIDError",clientLocale)%> ！");//输入的产品编号不存在或者该产品不在推介期
			document.theform.productid.value="";
			document.theform.product_id.options[0].selected=true;
		}
	}
	
	nextKeyPress(this);
}

/*设置产品及客户信息*/
function selectProductAndCustomer(){		
	var url = "apply_purchase_add.jsp?page=1"+ getLastOptions();	
	window.location.href = url;
}

/*选择认购合同*/
function getContractSubBh(){	
	document.getElementsByName("serarch_flag")[0].value=1;
	
	if(!sl_checkChoice(theform.contract_bh_2, "<%=LocalUtilis.language("message.subscribeConstract",clientLocale)%> ")) return false;//认购合同
	var url = "apply_purchase_add.jsp?page=1"+ getLastOptions();
	window.location.href = url;
}

/*选择银行*/
function selectBank(value){
	var userId = <%=new enfo.crm.web.DocumentFile().getUserId()%>;
	if (userId==17) { <%-- 方正 --%>
		return;
	}

	if (value!=""){	
		location = 'apply_purchase_add.jsp?page=<%=sPage%>&bank_id=' + value+ getLastOptions();
	}
}

/*打开产品查询页面*/
function openQuery(product_id,item_id){	
	if(product_id!=0){
		alert("<%=LocalUtilis.language("message.prodViewError",clientLocale)%> ！")//产品查询页面未移
	}
}

/*客户信息*/
function getMarketingCustomer(prefix,readonly){
	
	var cust_id = getElement(prefix, "cust_id").value;	
	var url = '<%=request.getContextPath()%>/marketing/customer_info2.jsp?prefix='+ prefix+ '&cust_id=' + cust_id+'&readonly='+readonly ;
	
	v = showModalDialog(url,'','dialogWidth:700px;dialogHeight:688px;status:0;help:0;');		
	if (v != null){
		/*showMarketingCustomer(prefix, v)*/
		getElement(prefix, "cust_id").value = v[7];
		document.theform.customer_gain_acct.value = v[0];
		selectProductAndCustomer();
	}	
	
	return (v != null);
}

//新增申购合同联系人
function newlyIncreased(value){
	var obj=value;	
	var cust_id = getElement("customer", "cust_id").value;;
	var url = "<%=request.getContextPath()%>/client/clientinfo/client_contact_new.jsp?cust_id="+cust_id;
	if(obj=="0"){		
		if(showModalDialog(url,'','dialogWidth:900px;dialogHeight:420px;status:0;help:0;scroll:0')){
			sl_update_ok();
			location.reload();
		}
	}
}

/*显示账号位数*/
function showAcctNum(value){		
	if (trim(value) == "")
		bank_acct_num.innerText = "";
	else
		bank_acct_num.innerText = "(" + showLength(value) + " 位 )";
}

/*改变输入*/
function changeInput(obj){
	if(document.theform.inputflag.value==1){
		obj.innerText="<%=LocalUtilis.language("message.choose",clientLocale)%> ";//选择
		document.theform.bank_acct.style.display="";
		document.theform.bank_acct2.style.display="none";
		document.theform.inputflag.value=2;
	}
	else{
		obj.innerText="<%=LocalUtilis.language("message.input",clientLocale)%> ";//输入
		document.theform.bank_acct.style.display="none";
		document.theform.bank_acct2.style.display="";
		document.theform.inputflag.value=1;
	}
}

/**************************************************************************************************************************/
/*验证合同编号*/
function check(){
	$("contractDIV").innerHTML = "";
	if(!sl_check(document.theform.contract_sub_bh, "<%=LocalUtilis.language("class.contractID",clientLocale)%> "))return false; //合同编号
	
	var book_code=DWRUtil.getValue("book_code");
	var contract_type=new Number(1);
	var product_id=DWRUtil.getValue("product_id");
	var contract_sub =DWRUtil.getValue("contract_sub_bh");	
	
	if(product_id==0){
		sl_alert("<%=LocalUtilis.language("message.chooseProdTip",clientLocale)%> !");//请先选择产品
		return false;
	}
	contract.findIfExistSameBH(book_code,contract_type,product_id,contract_sub,callContractSubBHCallBack);
}

/*结果返回*/
function callContractSubBHCallBack(result){	
	if(result!=""){
		$("contractDIV").innerHTML = result;
	}	
}
/**************************************************************************************************************************/


/*保存*/
function SaveAction(){		
	if(document.theform.onsubmit()){
		disableAllBtn(true);
		document.getElementById("sub_flag").value = 1;		
		document.theform.submit();
	}
}
	
/*返回*/
function CancelAction(){
	var tempArray = '<%=sQuery%>'.split('$');
	
	var url = "apply_purchase_list.jsp?page=1&pagesize="+ tempArray[5] ;	
	var url = url + '&q_productCode=' + tempArray[1];	
	var url = url + '&q_productId=' + tempArray[2];
	var url = url + '&q_check_flag=' + tempArray[6];
	var url = url + '&q_cust_name=' + tempArray[3];	
	var url = url + '&query_contract_bh=' + tempArray[4];
	
	location = url;
}

/*下面是编辑申购信息时用到的函数*/
function validateForm(form){	
	//if(!sl_checkChoice(form.product_id, "<%=LocalUtilis.language("class.productName",clientLocale)%> ")){return false;}//产品名称
	
	if (! sl_checkChoice(form.contract_type, "合同分类")) return false;
	
	<%if(Argument.getSyscontrolValue_intrust("C30902")!=1){%>
    var contract_bh=form.contract_bh;
	if(!sl_check(contract_bh, "<%=LocalUtilis.language("class.constractBH",clientLocale)%> ", 20,0))	return false; //合同序号
	
	if(contract_bh.value!=null && contract_bh.value!=""){
	  var s=contract_bh.value;
	  var len=contract_bh.value.length;       
	       
	  if(len<3){
	    sl_alert("<%=LocalUtilis.language("message.inputContractBHTip",clientLocale)%> ");//输入的合同序号至少为3位
	    return false;
	  }else{		     
	     if((s.charCodeAt(len-1)<48 || s.charCodeAt(len-1)>57) || (s.charCodeAt(len-2)<48 || s.charCodeAt(len-2)>57)|| (s.charCodeAt(len-3)<48 || s.charCodeAt(len-3)>57)){
	        sl_alert("<%=LocalUtilis.language("messgae.contractSerialError",clientLocale)%> ");//合同序号最后三位必须是数字，请您重新填写！
	        return false;
	     }   
	  }
	}
	<%}%>
	
	if(form.customer_cust_id.value==""||theform.customer_cust_id.value==0){
		sl_alert("<%=LocalUtilis.language("message.chooseCustomer",clientLocale)%> ！");//请选择客户
		return false;
	}	
	
 //  if(!sl_checkChoice(form.bank_id, "<%=LocalUtilis.language("class.bankName3",clientLocale)%> ")){return false;}//银行名称
	if(form.bank_id.value != ""){
		if(document.theform.inputflag.value==1){
			//if(!sl_checkChoice(form.bank_acct2, "<%=LocalUtilis.language("class.bankAcct3",clientLocale)%> "))return false;//银行帐号
		}
		else{
			if(!sl_check(form.bank_acct, "<%=LocalUtilis.language("class.bankAcct3",clientLocale)%> ", 80, 1)) return false;//银行帐号
		}		
	}
	//if(intrustType_Flag == '1'){
		//document.theform.channel_type.value = comboBoxTree.getValue();
		//document.theform.channel_id.value = comboBoxParentTree.getValue();
	//}
	if(!sl_checkDecimal(theform.rg_money, "<%=LocalUtilis.language("class.rgMoney2",clientLocale)%> ", 13, 3, 1)){return false;}//申购金额
	//if(!sl_checkChoice(theform.jk_type, "<%=LocalUtilis.language("class.feeType",clientLocale)%> ")){return false;}//缴款方式	
	if(!sl_checkDate(theform.qs_date_picker,"<%=LocalUtilis.language("class.qsDate",clientLocale)%> ")){return false;}//签署日期
	if(!sl_checkDate(theform.jk_date_picker,"<%=LocalUtilis.language("class.dzDate",clientLocale)%> ")){return false;}//缴款日期
	if(!sl_checkDate(theform.start_date_picker,"<%=LocalUtilis.language("class.beginDate",clientLocale)%> ")){return false;}//起始日期
	if(!sl_check(theform.summary2, "<%=LocalUtilis.language("class.contractSummary",clientLocale)%> ", 100, 0)){return false;}//合同备注
	//if(!sl_checkChoice(form.link_man, "<%=LocalUtilis.language("class.linkMan",clientLocale)%> ")){return false;}//合同销售人员
	
	syncDatePicker(theform.qs_date_picker, theform.qs_date);
	syncDatePicker(theform.jk_date_picker, theform.jk_date);
	syncDatePicker(theform.start_date_picker, theform.start_date);
	
	if(form.with_bank_flag.checked){
		form.with_bank_flag.value = 1;
	}
	if(form.with_security_flag.checked){
		form.with_security_flag.value = 1;
	}
	if(form.with_private_flag.checked){
		form.with_private_flag.value = 1;
	}

	<%if(open_flag.intValue()==1){%>
		var bonus_flag = document.getElementById("bonus_flag").value;
		if(bonus_flag=='3'){
			if(!sl_checkDecimal(form.bonus_rate, "转份额比例", 3, 2, 0))	return false;
			if(parseFloat(form.bonus_rate.value) <= parseFloat('0') || parseFloat(form.bonus_rate.value) >= parseFloat('100')){
				sl_alert("转份额比例取值必须为0到100之间(不包含0和100)");
				return false;
			}
			//if(!isBetween(form.bonus_rate,0,100,2,"转份额比例取值必须为0到100之间(不包含0和100)")) return false;
		}
	<%}%>
		
	var userid='<%=userid.intValue()%>';	
	if(<%=user_id.intValue()%>==22){
		if(!sl_checkChoice(form.city_serialno, '<%=LocalUtilis.language("class.citySerialNO",clientLocale)%>')){return false;}//合同推介地
	}
	return sl_check_update();
	
}

/*拼后缀参数*/
function getLastOptions(){
	if(!sl_checkDate(document.theform.qs_date_picker,"<%=LocalUtilis.language("class.qsDate",clientLocale)%> "))return false;//签署日期
	syncDatePicker(document.theform.qs_date_picker, document.theform.qs_date);	
	
	var temp="";	
	var contract_bh = "";
	var contract_sub_bh = "";
	var contract_bh_3 = "";
		
	//if(apply_flag==1){	
		contract_bh = document.theform.contract_bh.value;
		contract_sub_bh = document.theform.contract_sub_bh.value;		
	//}
	
	var temp = temp + "&product_id="+ document.theform.product_id.value;
	var temp = temp + "&customer_cust_id="+ document.theform.customer_cust_id.value;
	var temp = temp + "&jk_type=" + document.theform.jk_type.value;
	if (document.theform.sub_product_id)
		var temp = temp + "&sub_product_id=" + document.theform.sub_product_id.value;
	var temp = temp + "&qs_date=" + document.theform.qs_date.value;
	var temp = temp + "&contract_bh=" + contract_bh;
	var temp = temp + "&contract_sub_bh=" + contract_sub_bh;
	var temp = temp +"&customer_gain_acct="+ document.theform.customer_gain_acct.value;
	var temp = temp +"&inputflag="+ document.theform.inputflag.value;	
	var temp = temp +"&cntr_serial_no="+ document.getElementById("cntr_serial_no").value;
	<%if(open_flag.intValue()==1){%>
		var temp = temp +"&bonus_flag="+document.theform.bonus_flag.value;
	<%}%>
	<%if(product_id!=null&&product_id.intValue()!=0&&open_flag.intValue()==1){%>
	var temp = temp + "&bonus_flag="+document.theform.bonus_flag.value
	<%}%>		
	<%if(intrust_flag1.intValue()!=1){%>
		var temp = temp +"&prov_flag="+document.theform.prov_flag.value
	<%}%>
	<%if(intrust_flag1.intValue()!=1&&asfund_flag!=null&&asfund_flag.intValue()==3){%>
		var temp = temp +"&prov_level="+document.theform.prov_level.value
	<%}%>
	var temp = temp + "&sQuery=<%=sQuery%>";
	
	return temp;
}

//查看客户信息
function getCustomer(cust_id){
	var url = '<%=request.getContextPath()%>/marketing/customerInfo2.jsp?cust_id='+ cust_id ;	
	var v = showModalDialog(url,'','dialogWidth:720px;dialogHeight:720px;status:0;help:0;');
	if(v!=null){
		document.theform.customer_gain_acct.value = v[0];
		document.theform.customer_cust_name.value = v[0];
	}
}
/*
 *添加附件	
 */
function addline()
{
	n++;
	newline=document.all.test.insertRow()
	newline.id="fileRow"+n;
	newline.insertCell().innerHTML="<input type='file' name=file_info"+n+" size='60'>"+"<button type='button' class='xpbutton3' onclick='javascript:removeline(this)'><%=LocalUtilis.language("messgae.remove",clientLocale)%> </button>";//移除
}


/*
 *删除附件	
 */
function removeline(obj)
{
	var objSourceRow=obj.parentNode.parentNode;
	var objTable=obj.parentNode.parentNode.parentNode.parentNode;
	objTable.lastChild.removeChild(objSourceRow);
}

function confirmRemoveAttach(obj,serial_no)
{
	if(confirm('<%=LocalUtilis.language("message.tAreYouSure",clientLocale)%>？'))
	{
		
		var updatehref = 'attach_remove.jsp?serial_no='+serial_no;
    	if(showModalDialog(updatehref, '', 'dialogWidth:0px;dialogHeight:0px;dialogLeft:0px;dialogTop:0px;status:0;help:0') != null)
		{
		}
		if(obj!=null)
			obj.style.display="none";
	}
}

var comboBoxTree;
var invest_type_name = '<%=Argument.getDictContent(channel_type)%>';
var intrustType_Flag;
var str;
Ext.BLANK_IMAGE_URL = '<%=request.getContextPath()%>/widgets/ext/resources/images/default/tree/s.gif';

var comboBoxParentTree;
Ext.BLANK_IMAGE_URL = '<%=request.getContextPath()%>/widgets/ext/resources/images/default/tree/s.gif';

Ext.onReady(function(){
	comboBoxTree = new Ext.ux.ComboBoxTree({
		renderTo : 'comboBoxTree',
		width:220,
		tree : {
			xtype:'treepanel',
			width:220,
			loader: new Ext.tree.TreeLoader({dataUrl:'<%=request.getContextPath()%>/client/channel/treeData.jsp'}),
       	 	root : new Ext.tree.AsyncTreeNode({id:'-1',text:'<%=LocalUtilis.language("class.partnType",clientLocale)%> '}),//渠道信息
       	 	listeners:{
       	 		beforeload:function(node){//选择树结点设值之前的事件   
       	 			if(node.id!='-1')
       	 				this.loader.dataUrl = '<%=request.getContextPath()%>/client/channel/treeData.jsp?value='+node.id;
       	 		},   
       	 		click:function(node)
       	 		{
       	 			invest_type_name = '';
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
	if(<%=r_channel_id%> !=0){
		invest_type_name = '<%=Argument.getChannel(r_channel_id)%>';}
		else{
			invest_type_name = ' ';
			}
	comboBoxParentTree = new Ext.ux.ComboBoxTree({
		renderTo : 'comboBoxParentTree',
		width:220,
		tree : {
			xtype:'treepanel',
			width:220,
			loader: new Ext.tree.TreeLoader({dataUrl:'<%=request.getContextPath()%>/client/channel/parentTree.jsp?channel_type=<%=channel_type%>'}),
       	 	root : new Ext.tree.AsyncTreeNode({id:'-1',text:'<%=LocalUtilis.language("class.partnName",clientLocale)%> '}),//渠道信息
       	 	listeners:{
       	 		beforeload:function(node){
       	 			if(node.id!='-1')
       	 				this.loader.dataUrl = '<%=request.getContextPath()%>/client/channel/parentTree.jsp?value='+node.id+'&channel_type=<%=channel_type%>';
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
		width:220,
		tree : {
			xtype:'treepanel',
			width:220,
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
function showHtyh(){
	if(document.theform.with_bank_flag.checked){
		document.getElementById("htyhmc_v").style.display = "";
	}else{
		document.getElementById("htyhmc_v").style.display = "none";
	}
}

function showProvFlag(data){
	var obj = document.theform.prov_flag;	
	DWRUtil.removeAllOptions(obj);  
	var json = eval(data);
	for(i=0;i<json.length;i++){
		DWRUtil.addOptions(obj,json[i]);
	}
		<%if(product_sub_flag!=null && product_sub_flag.intValue() == 1 &&intrust_flag1.intValue()!=1&&asfund_flag!=null&&asfund_flag.intValue()==3){%>
			utilityService.getProvLevel(<%=product_id%>,document.theform.sub_product_id.value,document.theform.prov_flag.value,0,setprovlevel);
		<%}else if(intrust_flag1.intValue()!=1&&asfund_flag!=null&&asfund_flag.intValue()==3){%>
			utilityService.getProvLevel(<%=product_id%>,0,document.theform.prov_flag.value,0,setprovlevel);
		<%}%>
}
function getprovlevel(){
	<%if(product_sub_flag!=null && product_sub_flag.intValue() == 1){%>
		utilityService.getProvLevel(<%=product_id%>,document.theform.sub_product_id.value,document.theform.prov_flag.value,0,setprovlevel);
	<%}else{%>
		utilityService.getProvLevel(<%=product_id%>,0,document.theform.prov_flag.value,0,setprovlevel);
	<%}%>
}

function showSylb(sub_product_id){
	if(sub_product_id==""){sub_product_id = 0;}
	utilityService.getSubPSylb(sub_product_id,showSylbCallBack);
	<%if(intrust_flag1.intValue()!=1){%>
	utilityService.getSubProductProvFlag(document.theform.product_id.value,sub_product_id,document.theform.prov_flag.value,showProvFlag);
<%}%>
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


function checkSellInfo(){
	var channel_rate = document.theform.channel_rate.value
	var rg_money = document.theform.rg_money.value;
	if(rg_money != "" && channel_rate != "")
		document.theform.market_trench_money.value = (Number(rg_money) * Number(channel_rate)).toFixed(2);
	else
		document.theform.market_trench_money.value = "";
	return true;
}

//选择子产品
function setSubProduct(product_id, sub_product_id){
	//1.销售渠道
	var obj_op = document.getElementById("market_trench");  
	if(product_id != "" && sub_product_id != ""){
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
	} else {
		document.theform.expect_ror_lower.value = "";
		document.theform.expect_ror_upper.value = document.theform.expect_ror_lower.value;
	}
}

function checkSellInfo() {
	var product_id = DWRUtil.getValue("product_id");
	if (document.theform.sub_product_id == null) {
		utilityService.getSubProductProvFlag2(product_id, 0, autoSetGainLevel);//受益优先级、收益级别
	} else {
		var sub_product_id = document.theform.sub_product_id.value;
		utilityService.getSubProductProvFlag2(product_id, sub_product_id, autoSetGainLevel);//受益优先级、收益级别
	}
}

function getGainRate() {
	var product_id = DWRUtil.getValue("product_id");
	if (document.theform.sub_product_id == null) {
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

function setprovlevel(data){
	var ret = "<select name='prov_level' style='width:100px' onchange='javascript:getGainRate()'>"+data+"</select>";
	document.getElementById("div_prov_level").innerHTML=ret;

	var rg_money = DWRUtil.getValue("rg_money");
	if (rg_money == "") {
		document.theform.expect_ror_lower.value = "";
		document.theform.expect_ror_upper.value = document.theform.expect_ror_lower.value;
	} else {
		getGainRate();
	}
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

function show_property_souce(serial){
	var urls="property_souce_list.jsp?serial="+serial+"&property_souce_str="+document.theform.property_souce.value+"^"+document.theform.other_explain.value;
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

<form name="theform" method="post"  onsubmit="javascript:return validateForm(this);" ENCTYPE="multipart/form-data">

<input type="hidden" name="book_code" value="<%=input_bookCode%>"/>
<input type="hidden" name="serial_no" value="<%=serial_no%>"/>
<input type="hidden" name="cntr_serial_no" value="<%=cntr_serial_no%>"/>
<input type="hidden" name="inputflag" value="<%=inputflag%>"/>
<!--客户信息-->
<input type="hidden" name="cust_no" value="<%=cust_no%>"/>
<input type="hidden" name="cust_name" value="<%=cust_name%>"/>
<input type="hidden" name="customer_cust_id" value="<%=cust_id%>"/>
<!--产品信息-->
<%if (user_id.intValue()!=2/*2北国投*/ && user_id.intValue()!=999) { %>
<input type="hidden" name="period_unit" value="<%=Utility.trimNull(period_unit)%>"/>
<%} %>
<input type="hidden" name="intrust_flag1" value="<%=intrust_flag1%>"/>
<input type="hidden" name="pre_start_date" value="<%=pre_start_date%>"/>
<input type="hidden" name="pre_end_date" value="<%=pre_end_date%>"/>
<%if (user_id.intValue()!=2/*2北国投*/ && user_id.intValue()!=999) { %>
	<input type="hidden" name="valid_period" value="<%=Utility.trimNull(valid_period)%>"/>
<%} %>
<!--新增成功标志 提交标志-->
<input type="hidden" id="bSuccess" value="<%=bSuccess%>"/>
<input type="hidden" name="sQuery" value="<%=sQuery%>"/>
<input type="hidden" name="sub_flag"id="sub_flag" value="<%=sub_flag%>"/>

<input type="hidden" id="channel_type" name="channel_type" value="<%=channel_type%>"/>
<input type="hidden" id="channel_id" name="channel_id" value="<%=r_channel_id%>"/>
<input type="hidden" id="channel_rate" name="channel_rate" value=""/>
<input type="hidden" name="property_souce" value="<%=property_souce%>"/>
<input type="hidden" name="other_explain" value="<%=other_explain%>"/>

<div align="left" class="page-title">
	<font color="#215dc6"><b><%=menu_info%></b></font>
</div>
<br/>
<!--产品选择-->
<div>
<table cellSpacing=0 cellPadding=4 width="100%" border=0 class="product-list">
	<tr>   
		<td ><b><%=LocalUtilis.language("menu.prodInfo",clientLocale)%> </b></td>	<!--产品信息-->	
	<tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.productID",clientLocale)%> :</td><!--产品编号-->
		<td align="LEFT">
			<input type="text" maxlength="16" <%=checkedStyle%> name="productcode" value="<%=product_code%>" onkeydown="javascript:setProduct(this.value);" size="20" disabled>
		</td>
		<td>&nbsp;&nbsp;</td>
		<td>&nbsp;&nbsp;</td>
	</tr>
	<tr>	
		<td align="right"><b>*<%=LocalUtilis.language("class.productNumber",clientLocale)%> :</b></td><!--产品选择-->
		<td align=left colspan=3>
			<select size="1"  <%= checkedStyle2%> name="product_id" onkeydown="javascript:nextKeyPress(this)" class="productname" onchange="javascript:selectProductAndCustomer();" disabled>
					<option><%=LocalUtilis.language("message.pleaseSelect",clientLocale)%> </option><!--请选择-->
					<%=Argument.getCRMProductListOptions(new Integer(2),product_id,"110203",new Integer(1),input_operatorCode)%><!--正常期产品，非单一的-->
			</select>
			<input type="hidden" name="productId" value=<%= product_id%>/>			
			<!--<button type="button"  name="show_info" title="查看产品信息" onclick="javascript:openQuery('<!%=product_id%>','<!%=item_id%>');"<!%if(product_id.intValue()!=0){out.print("style=display:'';");}else{out.print("style=display:'none';");}%>>查看</button>-->
		</td>
	</tr>
	<%if(product_sub_flag.intValue() == 1 && is_product_sub_flag == 1){%>
			<tr>	
				<td align="right"><b>*<%=LocalUtilis.language("class.subProductID",clientLocale)%> :</b></td><!--子产品选择-->
				<td align=left colspan=3>
					<select size="1" <%if(serial_no!= null&&serial_no.intValue()!=0){out.print("disabled");}%> name="sub_product_id" onkeydown="javascript:nextKeyPress(this)" class="productname" onclick="javascript:setSubProduct(document.theform.product_id.value, this.value);">
						<%=Argument.getSubProductOptions(product_id, new Integer(0),sub_product_id)%> 
					</select>
					<!--button name="show_sub_info" title="查看子产品信息" onclick="javascript:openSubProduct(document.theform.sub_product_id);"> 查看</button-->
				</td>
			</tr>
	<%}%>
	<tr>
		<td align="right"><b><%=LocalUtilis.language("class.constractBH",clientLocale)%> :</b></td><!--合同序号--> 
		<td ><input  readonly class='edline' name="contract_bh" size="20" maxlength=20 onkeydown="javascript:nextKeyPress(this)" value="<%=contract_bh%>"></td>
		<td align="right"><%=LocalUtilis.language("class.contractID",clientLocale)%> :</td><!--合同编号-->
		<td ><input <%= checkedStyle%> name="contract_sub_bh" size="40" maxlength=40 onkeydown="javascript:nextKeyPress(this)" value="<%=contract_sub_bh%>"></td>
	</tr>

	<!--客户选择-->
	<tr>   
		<td><b><%=LocalUtilis.language("message.customerInfomation",clientLocale)%> </b></td><!--客户信息-->
		<td>
			<button type="button"  id="cust_button" class="xpbutton3" accessKey=e name="btnEdit" title="<%=LocalUtilis.language("message.customerInfomation",clientLocale)%> " 
			    onclick="javascript:getCustomer(<%=cust_id%>);"><%=LocalUtilis.language("message.view",clientLocale)%> 
			</button>&nbsp;&nbsp;<!--客户信息--><!--查看-->
			<span id="cust_message" style="display:none;color:red;"><%=LocalUtilis.language("message.noCustomerInfo",clientLocale)%> </span><!--客户信息不存在-->
		</td>
	<tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.customerName",clientLocale)%> :</td> <!--客户名称-->
		<td ><input maxlength="100" readonly class='edline'  name="customer_cust_name" size="25" onkeydown="javascript:nextKeyPress(this);" value="<%=cust_name%>">&nbsp;&nbsp;&nbsp;
		</td>
		<td align="right"><%=LocalUtilis.language("class.accountManager",clientLocale)%> :</td><!--客户经理-->
		<td ><input maxlength="100" readonly class='edline'  name="customer_service_man" size="15" onkeydown="javascript:nextKeyPress(this);" value="<%=Utility.trimNull(Argument.getManager_Name(service_man))%>">&nbsp;&nbsp;&nbsp;
		</td>
	</tr>   	
	<tr>
		<td align="right"><%=LocalUtilis.language("class.customerType",clientLocale)%> :</td><!--客户类别-->
		<td ><INPUT readonly class='edline' name="customer_cust_type_name" size="20" value="<%=cust_type_name%>" onkeydown="javascript:nextKeyPress(this);"></td>
		<td align="right"><%=LocalUtilis.language("class.customerCardID",clientLocale)%> :</td><!--证件号码-->
		<td><input readonly class='edline' name="customer_card_id" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%=card_id%>" size="20"></td>
	</tr>
	<tr>
	<tr>
		<td align="right">合同联系人 :</td>
		<td colspan="3">
			<input type="text" name="ht_cust_name" size="25" onkeydown="javascript:nextKeyPress(this)" value="<%=ht_cust_name%>"/>
			&nbsp;&nbsp;电话 :<input type="text" name="ht_cust_tel" size="20" onkeydown="javascript:nextKeyPress(this)" value="<%=ht_cust_tel%>"/>
			&nbsp;&nbsp;地址 :<input type="text" name="ht_cust_address" size="50" onkeydown="javascript:nextKeyPress(this)" value="<%=ht_cust_address%>"/>	
		</td>
	</tr>
	
	<tr>
		<td><b><%=LocalUtilis.language("message.contractInfo",clientLocale)%> </b></td><!--合同信息-->  
	<tr>
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
	
<%if (user_id.intValue()==2 /*2北国投*/|| user_id.intValue()==999) { %>
		<tr>
			<td align="right">合同期限 :</td>
			<td colspan="3">
				<input type="text" name="valid_period" size="5" onkeydown="javascript:nextKeyPress(this)" value="<%=Utility.trimNull(valid_period)%>"/> 
				<select name="period_unit" id="period_unit"><%=Argument.getPeriodValidUnitOptions(period_unit)%></select>
			</td>
		</tr>
<%} %>
	<tr>
		<td align="right" ><%=LocalUtilis.language("class.bankName3",clientLocale)%> :</td><!--银行名称-->
		<td >
			<select size="1" name="bank_id"  onchange="javascript:selectBank(this.value);"  style="WIDTH: 180px">
				<%=Argument.getBankOptions(bank_id)%>
			</select>
			<input type="text" name="bank_sub_name" size="20" onkeydown="javascript:nextKeyPress(this)" value="<%=bank_sub_name %>">
		</td>
		<td align="right"><%=LocalUtilis.language("class.bankAcct3",clientLocale)%> :</td><!--银行帐号-->
		<td>
			<input <%if(inputflag==1) out.print("style=display:none");%>  type="text" name="bank_acct" onblur="luhmCheck(this.value);" onkeydown="javascript:nextKeyPress(this)" onkeyup="javascript:showAcctNum(this.value)" size="30">
			<select <%if(inputflag==2) out.print("style=display:none");%> size="1" name="bank_acct2"  onkeydown="javascript:nextKeyPress(this)"  style="WIDTH: 200px">
				<%=preCodeOptions%>  
			</select>&nbsp;&nbsp;										
			<button type="button"  class="xpbutton2" accessKey=x name="btnSelect" title="<%=LocalUtilis.language("message.input",clientLocale)%> " onclick="javascript:changeInput(this);">
			  <%if(inputflag==1){%><%=LocalUtilis.language("message.input",clientLocale)%><%} else{%><%=LocalUtilis.language("message.choose",clientLocale)%> <%}%> (<u>X</u>)
			</button><!--输入--><!--选择-->
			<span id="bank_acct_num" class="span"></span> 
		</td>
	</tr>
	<tr>
		<td align="right">开户行所在地 :</td>
		<td>
			<select size="1"  name="bank_province" style="width: 130px" onkeydown="javascript:nextKeyPress(this)" onchange="javascript:setGovRegional(this.value,'');">
				<%=Argument.getCustodianNameLis(new Integer(9999), "", new Integer(0),bank_province)%>
			</select>
			<span id="gov_regional_span">
				<select style="WIDTH: 150px" name="bank_city">
					<%=Argument.getCustodianNameLis(new Integer(9999), bank_province, new Integer(0),bank_city)%>
				</select>
			</span><!-- 省级行政区域过滤出来的相关区域 -->
		</td>
		<td align="right">推荐人 :</td>
		<td>
			<select name="recommend_man" style="width:230px;" onkeydown="javascript:nextKeyPress(this)" >
				<%=Argument.getIntrustOptions(recommend_man,new Integer(1)) %>	
			</select>
		</td>
	</tr>
	<!--反洗钱 添加-->
	<tr>
		<td align="right"><%=LocalUtilis.language("class.bankAccountType",clientLocale)%> :</td><!--银行账号类型-->
		<td><select size="1" <%= checkedStyle2%> name="bank_acct_type" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 250px">
				<%=Argument.getBankAcctType(bank_acct_type)%>
			</select>						
		</td>
		<td align="right"><%=LocalUtilis.language("class.customerGainAcct",clientLocale)%> :</td><!--银行帐号户名-->
		<td ><input name="customer_gain_acct"  <%if(user_id.intValue()==8){%>class="edline" readonly<%}%> size="1"style="WIDTH: 250px"  onkeydown="javascript:nextKeyPress(this)" value="<%=gain_acct%>"></td>
	</tr>
	<tr>
	<td align="right">*<%=LocalUtilis.language("class.feeType",clientLocale)%> :</td><!--缴款方式-->
		<td><select size="1" <%= checkedStyle2%> name="jk_type" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px">
				<%=Argument.getJkTypeOptions(jk_type)%>
			</select>
		</td>
		<td align="right"><%=LocalUtilis.language("class.bonusFlag",clientLocale)%> :</td><!--收益分配方式-->
		<td>
			<select size="1" name="bonus_flag" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px">
				<%=Argument.getBonus_flag(bonus_flag)%>
			</select>	
			<span id="bonus_rate_div" <%if(Utility.parseInt(Utility.trimNull(bonus_flag),0)!=3) out.print("style='display:none'");%>>转份额比例:
				<input type="text" name="bonus_rate" size="10" onkeydown="javascript:nextKeyPress(this)" 
					value="<%=(Utility.parseDecimal(Utility.trimNull(bonus_rate),new BigDecimal(0))).multiply(new BigDecimal(100)).setScale(2)%>">%
			</span>	
		</td>
	</tr>
	
	<tr>
		<td align="right">*<%=LocalUtilis.language("class.rgMoney2",clientLocale)%> :</td><!--申购金额-->
		<td>
			<input name="rg_money" <%= checkedStyle%> size="20" value="<%=rg_money%>" onkeydown="javascript:nextKeyPress(this)" onkeyup="javascript:showCnMoney(this.value,rg_money_cn)"
				onchange="javascript:checkSellInfo()">
			<span id="rg_money_cn" class="span"><%=rg_money_cn%></span>
		</td>
		<td align="right"><%if(open_flag.intValue()==1){%><%=fee_type_name%><%=LocalUtilis.language("class.feeTypeName",clientLocale)%> :<%}%></td><!--扣缴方式-->
		<td ><%if(open_flag.intValue()==1){%>
			<select size="1" <%= checkedStyle2%> name="fee_jk_type" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px">
				<%=Argument.getFeeJkTypeOptions(fee_jk_type.intValue())%>
			</select>
			<%}%>
		</td>
	</tr>
	<tr>
		<td align="right">预期收益率 :</td>
		<td>
			<input name="expect_ror_lower" size="12" value="<%=Utility.trimZero(expect_ror_lower) %>" onkeydown="javascript:nextKeyPress(this)" ><font color="red">%</font> 到 
			<input name="expect_ror_upper" size="12" value="<%=Utility.trimZero(expect_ror_upper) %>" onkeydown="javascript:nextKeyPress(this)" ><font color="red">%</font>
		</td>
		<td align="right">资金/资产来源:</td>
				<td colspan="3">
					<select size="1" name="money_origin" onkeydown="javascript:nextKeyPress(this)" style="width:120px" onchange="javascript:moneyOriginChange(this.value);">
						<%=Argument.getDictOptionsWithLevel(new Integer(1158),"1158",new Integer(Utility.parseInt(money_origin,0)))%>
					</select>
					<select size="1" name="sub_money_origin" <%if("115801".equals(money_origin)||"115807".equals(money_origin)||"".equals(money_origin)||money_origin==null) out.println("style='display:none'");%> onkeydown="javascript:nextKeyPress(this)" style="width:120px">
						<%=Argument.getDictOptionsWithLevel(new Integer(1158),money_origin ,new Integer(Utility.parseInt(sub_money_origin,0)))%>
					</select>
		     </td>
	</tr>
	<tr>					
		<td align="right">*<%=LocalUtilis.language("class.qsDate",clientLocale)%> :</td><!--签署日期-->
		<td>
			<INPUT TYPE="text" NAME="qs_date_picker" class=selecttext <%= checkedStyle%>
			<%if(qs_date==null){%> value="<%=Format.formatDateLine(Utility.getCurrentDate())%>"
			<%}else{%>value="<%=Format.formatDateLine(qs_date)%>"<%}%> >
			<INPUT TYPE="button" <%= checkedStyle2%> value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.qs_date_picker,theform.qs_date_picker.value,this);" tabindex="13">
			<INPUT TYPE="hidden" NAME="qs_date" value="<%=qs_date%>">
		</td>	
		<td align="right">*<%=LocalUtilis.language("class.dzDate",clientLocale)%> :</td><!--缴款日期-->
		<td>
			<INPUT TYPE="text" NAME="jk_date_picker" class=selecttext <%= checkedStyle%>
			<%if(jk_date==null){%> value="<%=Format.formatDateLine(Utility.getCurrentDate())%>"
			<%}else{%>value="<%=Format.formatDateLine(jk_date)%>"<%}%> >
			<INPUT TYPE="button" <%= checkedStyle2%> value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.jk_date_picker,theform.jk_date_picker.value,this);" tabindex="14">
			<INPUT TYPE="hidden" NAME="jk_date" value="<%=jk_date%>">
		</td>
	</tr>
	<tr style="display: none">
		<td align="right">*<%=LocalUtilis.language("class.beginDate",clientLocale)%> :</td><!--起始日期-->
		<td>
			<INPUT TYPE="text" NAME="start_date_picker" class=selecttext <%= checkedStyle%>
			<%if(start_date==null){%> value="<%=Format.formatDateLine(Utility.getCurrentDate())%>"
			<%}else{%>value="<%=Format.formatDateLine(start_date)%>"<%}%> >
			<INPUT TYPE="button" <%= checkedStyle2%> value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.start_date_picker,theform.start_date_picker.value,this);" tabindex="14">
			<INPUT TYPE="hidden" NAME="start_date" value="<%=start_date%>">
		</td>				
	</tr>
	<%if(check_flag.intValue()<2){%>
	<tr>
		<td align="right">渠道名称 :</td>
		<td>
			<select id="market_trench" name="market_trench" style="width: 280px;" onclick="javascript:setMartetTrench(this);">
				<%=Argument.queryMarketTrench(product_id,sub_product_id,marValue) %>
			</select>
		</td>
		<td align="right">渠道费用 :</td>
		<td><input name="market_trench_money" size="20" value="<%=market_trench_money %>" onkeydown="javascript:nextKeyPress(this)" onkeyup="javascript:showCnMoney(this.value,market_trench_money_cn)"><span id="market_trench_money_cn" class="span"></span></td>
	</tr>
	<%}else{%>
	<tr>
		<td  width="100px" align="right">渠道名称:&nbsp;&nbsp;</td> 
		<td><input readonly class="edline" value="<%=Argument.getDictContent(channel_type)%><%=r_channel_id.intValue() == 0 ? "" : "-"%><%=Argument.getChannel(r_channel_id)%>"/></td>
		<td align="right">渠道费用 :</td><!--渠道来源-->
		<td><input readonly class="edline" value="<%=Format.formatMoney(market_trench_money)%>"/></td>
	</tr>
	<%}%>
	<tr style="display: none;">
		<td  width="100px" align="right"><%=LocalUtilis.language("class.partnType",clientLocale)%>:&nbsp;&nbsp;</td> 
		<td><div id="comboBoxTree"></div></td>
		<td align="right"><%=LocalUtilis.language("class.partnName",clientLocale)%> :</td><!--渠道来源-->
		<td><div id="comboBoxParentTree"></div></td>
	</tr>
	<tr >
		<td align="right"><%=LocalUtilis.language("class.partnAttachmentInfo",clientLocale)%> :</td><!--渠道附加信息-->
		<td>
			<input name="channel_memo" size="1" style="WIDTH: 250px" onkeydown="javascript:nextKeyPress(this)"  value="<%=channel_memo%>">
		</td>
		<td align="right">渠道合作方式 :</td><!--合同销售人员-->
		<td>
			<select size="1" name="channel_coopertype" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px">				
				<%=Argument.getDictParamOptions_intrust(5502,channel_coopertype)%>
			</select>
		</td>	
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.linkMan",clientLocale)%> :</td><!--合同销售人员-->
		<td colspan="3">
			<select size="1" name="link_man" <%= checkedStyle2%> onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px">				
				<%=Argument.getOperatorOptionsByRoleId(new Integer(2),link_man)%>
			</select>
		</td>
	</tr>
	<%//if(is_local == 2){%>
	<tr>
		<td align="right"><%if(user_id.intValue()==22){%><font color="red"><b>*</b></font><%}%><%=LocalUtilis.language("class.citySerialNO",clientLocale)%> :</td><!--合同推介地-->
			<td>
				<select size="1" name="city_serialno" <%= checkedStyle2%> onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px">
					<%=cityNameOptions%>
				</select>
				&nbsp;<button type="button"  class='xpbutton4' accessKey=r id='btnSetcity' name='btnSetcity' onclick='javascript:newCity(document.theform.product_id.value);' ><%=LocalUtilis.language("class.promotionSet",clientLocale)%> </button>
			</td>
		</tr>
	<%//}%>		
	<tr>
		<%if(intrust_flag1.intValue()!=1){%>
		<td align="right"><%=LocalUtilis.language("class.provLevel",clientLocale)%> :</td><!-- 受益优先级 -->
		<td>					
			<select name="prov_flag" style="WIDTH: 100px" <%if(asfund_flag!=null&&asfund_flag.intValue()==3){%>onchange="javascript:getprovlevel()"<%}%> onkeydown="javascript:nextKeyPress(this)">
				<%
				if(prov_flag ==null){
					out.println(Argument.getProductProvFlag(product_id,sub_product_id,prov_flag));
				}else{							
					out.println(Argument.getProductProvFlag(product_id,sub_product_id,prov_flag));
				}
				%>
			</select>
		</td>
		<%if(asfund_flag!=null&&asfund_flag.intValue()==3){%>
		<td align="right">*<%=LocalUtilis.language("class.incomeLevel",clientLocale)%> :</td><!-- 收益级别 -->
		<td>
			<div id="div_prov_level">
			<select name="prov_level" style="width:100px" onchange="javascript:getGainRate()">
				<%
				if(prov_level==null){
					out.println(Argument.getProvlevelOptions(prov_level));
				}else{							
					out.println(Argument.getProductProvLevel(product_id,sub_product_id,prov_flag,prov_level));
				}
				%>
			</select>	
			</div>
		</td>
		<%}
		}%>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("message.cooperationWay",clientLocale)%> :</td><!--合作方式-->
		<td>
			<input <%if (with_bank_flag != null && with_bank_flag.intValue()==1)out.print("checked");%>
					onkeydown="javascript:nextKeyPress(this)" type="checkbox" class="flatcheckbox" value="<%=with_bank_flag %>" name="with_bank_flag" onclick="javascript:showHtyh();">
					<%=LocalUtilis.language("intrsut.home.silvertrustcooper",clientLocale)%>&nbsp;&nbsp;&nbsp;&nbsp;<!--银信合作-->
			<span id="htyhmc_v" <%if(with_bank_flag.intValue()!=1) out.print("style='display:none'");%>>
				<%=LocalUtilis.language("class.withBankId",clientLocale)%>:	<!-- 合作银行 -->	
				<select size="1" name="ht_bank_id" style="WIDTH: 220px">
					<%=Argument.getBankOptions(Utility.trimNull(ht_bank_id))%>
				</select>
				<input type="text" name="ht_bank_sub_name" size="20" onkeydown="javascript:nextKeyPress(this)" value="<%=Utility.trimNull(ht_bank_sub_name)%>">
			</span>
			<input <%if (with_security_flag != null && with_security_flag.intValue()==1)out.print("checked");%>
				onkeydown="javascript:nextKeyPress(this)" type="checkbox"  
				class="flatcheckbox" value="<%=with_security_flag %>" name="with_security_flag"
				><%=LocalUtilis.language("message.cooperation2",clientLocale)%> &nbsp;&nbsp;<!--证信合作-->
			<input <%if (with_private_flag != null && with_private_flag.intValue()==1)out.print("checked");%>
				onkeydown="javascript:nextKeyPress(this)" type="checkbox" 
				class="flatcheckbox" value="<%=with_private_flag %>" name="with_private_flag"
				><%=LocalUtilis.language("class.cooperation4",clientLocale)%> &nbsp;&nbsp;<!--私募基金合作-->
		</td>
	</tr>
	<tr>
		<td align="right">非现场交易:</td>
		<td>   
			<input type="checkbox" class="flatcheckbox" name="spot_deal" value="1" <%=spot_deal.intValue() == 1 ? "checked" : ""%> <%=checkedStyle2%>>
		</td>
		<td colspan="2" align="center"><button type="button" class="xpbutton6"  onclick="show_property_souce(<%=serial_no%>);">信托财产来源</button></td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.customerSummary",clientLocale)%> :</td><!--备注-->
		<td colspan="3"><textarea rows="3" <%if(check_flag.intValue()>=2){out.print("style=edline_textarea");}%> name="summary2" onkeydown="javascript:nextKeyPress(this)" cols="83"><%=summary%></textarea></td>
	</tr>
	<tr id="reader1">
          	<td align="right"><%if(attachmentList!=null&&attachmentList.size()> 0){%>附件:<%}%></td>
            <td colspan="3">
			<%
			Iterator attachment_it = attachmentList.iterator();
			HashMap attachment_map = new HashMap();
            while(attachment_it.hasNext()){
            	attachment_map = (HashMap)attachment_it.next();
            %>
            	<div id="divattach<%=Utility.trimNull(attachment_map.get("ATTACHMENTID"))%>" style="display:">
            	<a title="查看附件" href="<%=request.getContextPath()%>/system/basedata/downloadattach.jsp?file_name=<%=Utility.replaceAll(Utility.trimNull(attachment_map.get("SAVE_NAME")),"\\","/")%>&name=<%=Utility.trimNull(attachment_map.get("ORIGIN_NAME"))%>" ><%=Utility.trimNull(attachment_map.get("ORIGIN_NAME"))%></a>
            	&nbsp;&nbsp;&nbsp;&nbsp;
            	<button type="button"  class="xpbutton2" accessKey=d id="btnSave" name="btnSave" 
            		onclick="javascript:confirmRemoveAttach(divattach<%=Utility.trimNull(attachment_map.get("ATTACHMENTID"))%>,'<%=Utility.trimNull(attachment_map.get("ATTACHMENTID"))%>$<%=Utility.replaceAll(Utility.trimNull(attachment_map.get("SAVE_NAME")),"\\","/")%>');">删除</button>
            	</div><br>
			<%}	%>
            </td>	
         </tr> 		
         
		<tr id="reader2" style="display:">
          	<td class="paramTitle"align="right">添加附件 :</td>
            <td colspan="3">          	
            	<table id="test" width="100%">
            		<tr >
            			<td>
		            	<input type="file" name="file_info" size="60">&nbsp;
		            	<img title="<%=LocalUtilis.language("message.tSelectAdditionalFiles",clientLocale)%> " src="<%=request.getContextPath()%>/images/minihelp.gif"><!--选择附加文件-->
		            	</td>
		            </tr>
		        </table>
		        <button type="button" class="xpbutton6"  onclick="addline();"><%=LocalUtilis.language("class.moreMccessories",clientLocale)%> <!--单击此处添加更多附件--></button> 
            </td>
        </tr>
</table>
</div>

<div align="right">
	<br>
	<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" <%if(check_flag.intValue()>=2){out.print("style=display:none");}%> 
	    onclick="javascript:SaveAction();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)<!--保存-->
	</button>
	&nbsp;&nbsp;&nbsp;&nbsp;
	<button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:CancelAction();"><%=LocalUtilis.language("message.back",clientLocale)%> <!--返回-->(<u>C</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</div>
</form>
<p>
<%
contract.remove();
apply_reach.remove();
customer.remove();
product.remove();
customer_inst.remove();
%>
<script language=javascript>
window.onload = function(){
	var v_bSuccess = document.getElementById("bSuccess").value;
	var cust_no = document.getElementsByName("cust_no")[0].value;
	
	if(cust_no==""){
		document.getElementById("cust_message").style.display="";
		document.getElementById("cust_button").style.display="none";
	}else{
		document.getElementById("cust_button").style.display="";
	}
	
	if(v_bSuccess=="true"){		
		sl_update_ok();
		CancelAction();
	}
	<%if(product_sub_flag.intValue() == 1 && is_product_sub_flag == 1 && check_flag.intValue()<2){%>
	//	setSubProduct(<%=product_id%>,<%=sub_product_id%>);
	<%}%>
}
</script>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>

