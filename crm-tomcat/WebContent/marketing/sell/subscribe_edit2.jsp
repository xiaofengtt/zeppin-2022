<%@ page contentType="text/html; charset=GBK" import="enfo.crm.marketing.*,enfo.crm.web.*,enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<% // 没有预约的认购合同 修改
try{
String from = Utility.trimNull(request.getParameter("from"));
boolean from_signed_spot = from.equals("signed_spot");
String qs = Utility.trimNull(request.getParameter("qs"));

String sQuery = request.getParameter("sQuery");
Integer serial_no = Utility.parseInt(Utility.trimNull(request.getParameter("serial_no")),new Integer(0));//认购信息ID
Integer purchase_mode_flag =  Utility.parseInt(request.getParameter("purchase_mode_flag"), new Integer(0));//从合同信息补全进入
String object_id = Utility.trimNull(request.getParameter("object_id"));
if(!"".equals(object_id)){
		serial_no=Utility.parseInt(object_id,new Integer(0));
}
//执行添加操作 添加附件
DocumentFile2 file = null;
boolean bSuccess = false;

//获得对象
ContractLocal contract = EJBFactory.getContract();//合同
CustomerLocal customer = EJBFactory.getCustomer();//客户
ProductLocal product = EJBFactory.getProduct();//产品
Customer_INSTLocal  customer_inst = EJBFactory.getCustomer_INST();//同步用客户对象
ChannelQueryLocal channel = EJBFactory.getChannelQuery();//修改已审核渠道

ContractVO cont_vo = new ContractVO();
CustomerVO cust_vo = new CustomerVO();
CustomerVO c_vo = new CustomerVO();//同步用
ProductVO p_vo = new ProductVO();
SaleParameterVO salevo = new SaleParameterVO();
SaleParameterLocal sale_parameter = EJBFactory.getSaleParameter();

List customerList = new ArrayList();

//如果审核 一些信息要变成只读
String checkedStyle = ""; 
String checkedStyle2 = "";
//事后修改功能 部分字段需要放开修改 这些字段的样式由下面的变量控制
String purchase_mode_checkedStyle = "";
String purchase_mode_checkedStyle2 = "";

String product_msg_flag = "";//产品信息标志（开放式OR封闭式，推介地等组合标志）
int inputflag= 2;//1输入 2选择
int selfbenflag = 1; //1自缢 2他缢
String fee_type_name = enfo.crm.tools.LocalUtilis.language("class.rgFee",clientLocale);//认购费
String marValue = "";
int SUB_COOPER = Argument.getSyscontrolValue_intrust("SUB_COOPER");
//认购合同的合同序号自动获取标志 1: 自动获取 2: 界面输入
int contract_bh_flag = Argument.getSyscontrolValue_intrust("C30902");

//产品标志
int open_flag = 0;
int asfund_flag = 0;
int intrust_flag1 = 0;
int is_local = 0;
Integer pre_start_date = new Integer(0);//推介起始日
Integer pre_end_date = new Integer(0);//推介结束日

//合同信息字段定义
String product_code = "",product_name = "";
String contract_bh = "",contract_sub_bh = "",bank_id = "";
String bank_sub_name = "";
String bank_acct_type = "";
String bank_acct = "";
String gain_acct = "";
String jk_type = "";
String rg_money_cn = "";//认购金额中文处理
String channel_cooperation = "";
String channel_type = "";
String channel_memo = "";
String prov_level = "",summary = "";
String ht_bank_id = "",ht_bank_sub_name = "";
String bank_city = "",bank_province = "";
String is_ykgl="";
String xthtyjsyl="";
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
Integer contact_id = new Integer(0);
BigDecimal bonus_rate = new BigDecimal(0.00);
BigDecimal rg_money = new BigDecimal(0.00);
BigDecimal market_trench_money = new BigDecimal(0.00);
Integer city_serialno = new Integer(0);
BigDecimal expect_ror_lower = new BigDecimal(0.00);
BigDecimal expect_ror_upper = new BigDecimal(0.00);
Integer valid_period = null;
Integer period_unit = null;
String ht_cust_name = "";
String ht_cust_address = "";
String ht_cust_tel = "";
Integer spot_deal = null;
String money_origin = "";
String sub_money_origin = "";
String property_souce="";
String other_explain="";
Integer contract_type = new Integer(0);

//客户信息字段定义
String cust_name = "",cust_no = "",card_type = "";
String card_id = "",legal_man = "",contact_man = "";
String o_tel = "";
String mobile = "";
String h_tel = "";
String post_code = "";
String post_address = "";
String e_mail = "";
String cust_type_name = "";
int is_link = 0;
Integer service_man = new Integer(0);
Integer cust_type = new Integer(0);
Integer rg_times = new Integer(0);
List attachmentList = new ArrayList();//附件
if("POST".equals(request.getMethod())){
	file = new DocumentFile2(pageContext);
	file.parseRequest();
	
	from = Utility.trimNull(file.getParameter("from"));
	from_signed_spot = from.equals("signed_spot");
	qs = Utility.trimNull(file.getParameter("qs"));

	//获得保存数据
	sQuery = file.getParameter("sQuery");
	purchase_mode_flag =  Utility.parseInt(file.getParameter("purchase_mode_flag"), 
											new Integer(0));//从合同信息补全进入
	cust_id = Utility.parseInt(file.getParameter("cust_id"), null);
	cust_no = file.getParameter("cust_no");
	card_id = file.getParameter("customer_card_id");
	cust_name = file.getParameter("customer_cust_name");
	cust_type = Utility.parseInt(file.getParameter("cust_type"), null);
	card_type = file.getParameter("card_type");
	legal_man = file.getParameter("legal_man");
	contact_man = file.getParameter("contact_man");
	post_address = file.getParameter("customer_post_address");
	post_code = file.getParameter("customer_post_code");
	mobile = file.getParameter("customer_mobile");
	e_mail = file.getParameter("customer_email");
	service_man = Utility.parseInt(file.getParameter("service_man"),new Integer(0));
	is_ykgl=file.getParameter("is_ykgl");
	xthtyjsyl=file.getParameter("xthtyjsyl");
	//contact_id = Utility.parseInt(file.getParameter("cust_message"),null);
	ht_cust_name = Utility.trimNull(file.getParameter("ht_cust_name"));
	ht_cust_address = Utility.trimNull(file.getParameter("ht_cust_address"));
	ht_cust_tel = Utility.trimNull(file.getParameter("ht_cust_tel"));

	serial_no = Utility.parseInt(file.getParameter("serial_no"),new Integer(0));//认购信息ID
	Integer r_product_id = Utility.parseInt(file.getParameter("product_id"), null);
	Integer r_link_man = Utility.parseInt(file.getParameter("link_man"), input_operatorCode);
	BigDecimal r_rg_money = Utility.parseDecimal(file.getParameter("rg_money"),new BigDecimal(0));
	Integer r_bonus_flag = Utility.parseInt(file.getParameter("bonus_flag"),new Integer(1));
	String r_jk_type = file.getParameter("jk_type");
	String r_bank_id = file.getParameter("bank_id");
	String r_bank_sub_name = Utility.trimNull(file.getParameter("bank_sub_name"));
	String r_gain_acct = file.getParameter("customer_gain_acct");
	Integer s_product_id = Utility.parseInt(file.getParameter("sub_product_id"), new Integer(0));
	inputflag = Utility.parseInt(file.getParameter("inputflag"), 1);
	String r_bank_acct = "";

	if(inputflag==1){
		r_bank_acct = file.getParameter("bank_acct2");
	}
	else{
		r_bank_acct = file.getParameter("bank_acct");
	}

	Integer r_service_man = Utility.parseInt(file.getParameter("service_man"), input_operatorCode);
	String r_summary = Utility.trimNull(file.getParameter("summary2"));
	String r_contract_bh = file.getParameter("contract_bh");
	Integer r_qs_date = Utility.parseInt(file.getParameter("qs_date"), new Integer(Utility.getCurrentDate()));
	Integer r_jk_date = Utility.parseInt(file.getParameter("jk_date"), new Integer(Utility.getCurrentDate()));//改参数已作废，所以随便传入当前时间
	Integer r_check_man = Utility.parseInt(file.getParameter("check_man"), new Integer(0));
	String r_bank_acct_type = Utility.trimNull(file.getParameter("bank_acct_type"));
	//Integer r_city_serialno = Utility.parseInt(file.getParameter("city_serialno"), new Integer(0));
	String r_contract_sub_bh = file.getParameter("contract_sub_bh");
	int r_fee_jk_type = Utility.parseInt(file.getParameter("fee_jk_type"),0);
	int r_selfbenflag = Utility.parseInt(file.getParameter("selfbenflag"),1);
	channel_id = Utility.parseInt(Utility.trimNull(file.getParameter("channel_id")),new Integer(0));
	market_trench_money = Utility.parseDecimal(Utility.trimNull(file.getParameter("market_trench_money")),new BigDecimal(0));
	channel_type = Utility.trimNull(file.getParameter("channel_type"));
	channel_memo = Utility.trimNull(file.getParameter("channel_memo"));
	channel_cooperation = Utility.trimNull(file.getParameter("channel_cooperation"));

	Integer r_with_bank_flag = Utility.parseInt(file.getParameter("with_bank_flag"), new Integer(0));
	Integer r_with_security_flag = Utility.parseInt(file.getParameter("with_security_flag"), new Integer(0));
	Integer r_with_private_flag = Utility.parseInt(file.getParameter("with_private_flag"), new Integer(0));
	Integer r_prov_flag = Utility.parseInt(file.getParameter("prov_flag"), new Integer(0));
	String r_prov_level = Utility.trimNull(file.getParameter("prov_level"));
	String r_ht_bank_id =  Utility.trimNull(file.getParameter("ht_bank_id"));
	String r_ht_bank_sub_name =  Utility.trimNull(file.getParameter("ht_bank_sub_name"));
	check_flag = Utility.parseInt(file.getParameter("check_flag"), 0);
	recommend_man = Utility.parseInt(file.getParameter("recommend_man"),new Integer(0));
	bank_province = Utility.trimNull(file.getParameter("bank_province"));
	bank_city = Utility.trimNull(file.getParameter("bank_city"));
	city_serialno = Utility.parseInt(file.getParameter("city_serialno"),new Integer(0));
	expect_ror_lower = Utility.parseDecimal(file.getParameter("expect_ror_lower"),new BigDecimal(0.00),4,"0.01");
	expect_ror_upper = Utility.parseDecimal(file.getParameter("expect_ror_upper"),new BigDecimal(0.00),4,"0.01");
	valid_period = Utility.parseInt(file.getParameter("valid_period"), null);
	period_unit = Utility.parseInt(file.getParameter("period_unit"), null);
	spot_deal = Utility.parseInt(file.getParameter("spot_deal"), new Integer(2));
	money_origin=Utility.trimNull(file.getParameter("money_origin"));//资金/资产来源
	sub_money_origin=Utility.trimNull(file.getParameter("sub_money_origin"));//资金/资产来源(二级)
	property_souce=Utility.trimNull(file.getParameter("property_souce")); //信托财产来源
	other_explain=Utility.trimNull(file.getParameter("other_explain"));//信托财产来源之其他说明
	contract_type = Utility.parseInt(file.getParameter("contract_type"), new Integer(0));

	BigDecimal r_bonus_rate = new BigDecimal(0);

	if(r_bonus_flag.intValue() == 1){//现金
		r_bonus_rate = new BigDecimal(0);
	}else if(r_bonus_flag.intValue() == 2){//转份额
		r_bonus_rate = new BigDecimal(1);
	}else{//部分转份额
		r_bonus_rate = Utility.parseDecimal(Utility.trimNull(file.getParameter("bonus_rate")),new BigDecimal(0)).multiply(new BigDecimal("0.01"));
	}

	//同步团队信息
	Integer r_team_id = Utility.parseInt(Utility.trimNull(file.getParameter("team_id")),new Integer(-1));
	Integer r_serialNo = Utility.parseInt(Utility.trimNull(file.getParameter("serialNo")),new Integer(0));
	BigDecimal r_already_sale = Utility.parseDecimal(Utility.trimNull(file.getParameter("already_sale")),new BigDecimal(0));
	Integer r_already_qualified_num = Utility.parseInt(Utility.trimNull(file.getParameter("already_qualified_num")),new Integer(-1));
	r_already_sale = r_already_sale.add(r_rg_money);

	//保存数据
	ContractVO s_cont_vo = new ContractVO();

		//修改未审核的认购合同
		//同步客户信息
		c_vo.setCust_id(cust_id);
		c_vo.setCust_no(cust_no);
		c_vo.setCust_name(cust_name);
		c_vo.setCust_type(cust_type);
		c_vo.setCard_id(card_id);
		c_vo.setCard_type(card_type);
		c_vo.setLegal_man(legal_man);
		c_vo.setContact_man(contact_man);
		c_vo.setPost_address(post_address);
		c_vo.setPost_code(post_code);
		c_vo.setMobile(mobile);
		c_vo.setE_mail(e_mail);
		c_vo.setService_man(service_man);
		c_vo.setInput_man(input_operatorCode);
		customer_inst.cope_customers(c_vo);

		//同步团队信息
		salevo.setSerial_NO(r_serialNo);
		salevo.setProductID(r_product_id);
		salevo.setTeamID(r_team_id);
		salevo.setSub_product_id(s_product_id);
		salevo.setSerial_no_subscribe(serial_no);
		salevo.setAlreadysale(r_already_sale);
		salevo.setAlready_qualified_num(r_already_qualified_num);
		sale_parameter.modiAlreadysale(salevo,input_operatorCode);

		//保存合同信息
		s_cont_vo.setSerial_no(serial_no);
		s_cont_vo.setBook_code(input_bookCode);
		s_cont_vo.setCust_id(cust_id);
		s_cont_vo.setProduct_id(r_product_id);
		s_cont_vo.setLink_man(r_link_man);
		s_cont_vo.setRg_money(r_rg_money);
		s_cont_vo.setBonus_flag(r_bonus_flag);
		s_cont_vo.setJk_type(r_jk_type);
		s_cont_vo.setBank_id(r_bank_id);
		s_cont_vo.setBank_sub_name(r_bank_sub_name);
		s_cont_vo.setGain_acct(r_gain_acct);
		s_cont_vo.setBank_acct(r_bank_acct);
		s_cont_vo.setService_man(r_service_man);
		s_cont_vo.setSummary(r_summary);
		s_cont_vo.setContract_bh(r_contract_bh);
		s_cont_vo.setQs_date(r_qs_date);
		s_cont_vo.setJk_date(r_jk_date);
		s_cont_vo.setCheck_man(r_check_man);
		s_cont_vo.setBank_acct_type(r_bank_acct_type);
		//s_cont_vo.setCity_serialno(r_city_serialno);
		s_cont_vo.setContract_sub_bh(r_contract_sub_bh);
		s_cont_vo.setFee_jk_type(r_fee_jk_type);
		s_cont_vo.setSelf_ben_flag(r_selfbenflag);
		s_cont_vo.setInput_man(input_operatorCode);
		s_cont_vo.setChannel_id(channel_id);
		s_cont_vo.setMarket_trench_money(market_trench_money);
		s_cont_vo.setChannel_type(channel_type);
		s_cont_vo.setChannel_memo(channel_memo);
		s_cont_vo.setChannel_cooperation(channel_cooperation);
		s_cont_vo.setWith_bank_flag(r_with_bank_flag);
		s_cont_vo.setWith_private_flag(r_with_private_flag);
		s_cont_vo.setWith_secuity_flag(r_with_security_flag);
		s_cont_vo.setBonus_rate(r_bonus_rate);
		s_cont_vo.setProv_flag(r_prov_flag);
		s_cont_vo.setProv_level(r_prov_level);
		s_cont_vo.setHt_bank_id(r_ht_bank_id);
		s_cont_vo.setHt_bank_sub_name(r_ht_bank_sub_name);
		
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
		s_cont_vo.setSub_product_id(s_product_id); //
		s_cont_vo.setSpot_deal(spot_deal);
		s_cont_vo.setMoney_origin(money_origin);
		s_cont_vo.setSub_money_origin(sub_money_origin);
		s_cont_vo.setProperty_source(property_souce);
		s_cont_vo.setOther_explain(other_explain);
		s_cont_vo.setContract_type(contract_type);
		contract.save(s_cont_vo);

		if(file.uploadAttchment(new Integer(5),serial_no,"",null,null,input_operatorCode))
			bSuccess = true;
	//}
	bSuccess = true;
}

//获取认购合同信息
if(serial_no.intValue()>0){
	List rsList_contract = null;
	Map map_contract = null;

	cont_vo.setSerial_no(serial_no);
	rsList_contract = contract.load(cont_vo);

	if(rsList_contract.size()>0){
		map_contract = (Map)rsList_contract.get(0);
	}
	
	//编辑查询，读取合同信息
	if(map_contract!=null){
		contract_bh = Utility.trimNull(map_contract.get("CONTRACT_BH"));
		contract_sub_bh = Utility.trimNull(map_contract.get("CONTRACT_SUB_BH"));
		product_code = Utility.trimNull(map_contract.get("PRODUCT_CODE"));
		product_name = Utility.trimNull(map_contract.get("PRODUCT_NAME"));
		product_id =  Utility.parseInt(Utility.trimNull(
						map_contract.get("PRODUCT_ID")),new Integer(0));

		product_msg_flag = Argument.getProductFlag(product_id);

		sub_product_id = Utility.parseInt(Utility.trimNull(
						map_contract.get("SUB_PRODUCT_ID")),new Integer(0));
		cust_id = Utility.parseInt(Utility.trimNull(
						map_contract.get("CUST_ID")),new Integer(0));

		if(cust_id.intValue()>0){
			//客户单个值
			cust_vo.setCust_id(cust_id);
			cust_vo.setInput_man(input_operatorCode);
			customerList = customer.listByControl(cust_vo);
		}

		check_flag = Utility.parseInt(Utility.trimNull(map_contract.get("CHECK_FLAG")),0);	
		if(check_flag==2){ //已审核过的合同改为只读
			checkedStyle = "readonly class='edline'";
			checkedStyle2 = "disabled";
			purchase_mode_checkedStyle = "readonly class='edline'";
			purchase_mode_checkedStyle2 = "disabled";
		}	
		//从合同信息补全进入 部分字段可修改编辑
		if(purchase_mode_flag.intValue()==1){
			purchase_mode_checkedStyle = "";
			purchase_mode_checkedStyle2 = "";
		}
		
		bank_id = Utility.trimNull(map_contract.get("BANK_ID"));
		bank_sub_name = Utility.trimNull(map_contract.get("BANK_SUB_NAME"));
		bank_acct_type = Utility.trimNull(map_contract.get("BANK_ACCT_TYPE"));
		bank_acct = Utility.trimNull(map_contract.get("BANK_ACCT"));

		gain_acct = Utility.trimNull(map_contract.get("GAIN_ACCT"));	
		jk_type = Utility.trimNull(map_contract.get("JK_TYPE"));
		bonus_flag = Utility.parseInt(Utility.trimNull(
						map_contract.get("BONUS_FLAG")), new Integer(0));
		bonus_rate = Utility.parseDecimal(Utility.trimNull(
						map_contract.get("BONUS_RATE")),new BigDecimal(0));
		rg_money = Utility.parseDecimal(Utility.trimNull(
						map_contract.get("RG_MONEY")),new BigDecimal(0));
		if(rg_money.intValue()!= 0)
			rg_money_cn = "(" + Utility.numToChinese(rg_money.toString()) + ")";

		fee_jk_type = Utility.parseInt(Utility.trimNull(map_contract.get("FEE_JK_TYPE")),0);
		qs_date = Utility.parseInt(Utility.trimNull(
						map_contract.get("QS_DATE")), new Integer(0));
		jk_date = Utility.parseInt(Utility.trimNull(
						map_contract.get("JK_DATE")), new Integer(0));
		channel_id = Utility.parseInt(Utility.trimNull(
						map_contract.get("CHANNEL_ID")),new Integer(0));
		market_trench_money = Utility.parseDecimal(Utility.trimNull(
						map_contract.get("MARKET_MONEY")),new BigDecimal(0));
		channel_memo = Utility.trimNull(map_contract.get("CHANNEL_MEMO"));
		channel_cooperation = Utility.trimNull(map_contract.get("CHANNEL_COOPERTYPE"));
		channel_type = Utility.trimNull(map_contract.get("CHANNEL_TYPE"));
		link_man = Utility.parseInt(Utility.trimNull(
						map_contract.get("LINK_MAN")),new Integer(0));
		prov_flag = Utility.parseInt(Utility.trimNull(map_contract.get("PROV_FLAG")), new Integer(0));
		prov_level = Utility.trimNull(map_contract.get("PROV_LEVEL"));
		selfbenflag = Utility.parseInt(Utility.trimNull(map_contract.get("ZY_FLAG")),1);
		with_bank_flag = Utility.parseInt(Utility.trimNull(
							map_contract.get("WITH_BANK_FLAG")), 0);
		with_security_flag = Utility.parseInt(Utility.trimNull(
							map_contract.get("WITH_SECURITY_FLAG")),0);
		with_private_flag = Utility.parseInt(Utility.trimNull(
							map_contract.get("WITH_PRIVATE_FLAG")),0);
		ht_bank_id =  Utility.trimNull(map_contract.get("HT_BANK_ID"));
		ht_bank_sub_name =  Utility.trimNull(map_contract.get("HT_BANK_SUB_NAME"));
		summary = Utility.trimNull(map_contract.get("SUMMARY"));
		citySerialno = Utility.parseInt(Utility.trimNull(
						map_contract.get("CITY_SERIAL_NO")), new Integer(0));
		recommend_man = Utility.parseInt(Utility.trimNull(
						map_contract.get("RECOMMEND_MAN")),new Integer(0));
		
		bank_province = Utility.trimNull(map_contract.get("BANK_PROVINCE"));
		bank_city = Utility.trimNull(map_contract.get("BANK_CITY"));
		city_serialno = Utility.parseInt(Utility.trimNull(map_contract.get("CITY_SERIAL_NO")),new Integer(0));
		expect_ror_lower = Utility.parseDecimal(Utility.trimNull(map_contract.get("EXPECT_ROR_LOWER")),new BigDecimal(0.00)).multiply(new BigDecimal(100));
		expect_ror_upper = Utility.parseDecimal(Utility.trimNull(map_contract.get("EXPECT_ROR_UPPER")),new BigDecimal(0.00)).multiply(new BigDecimal(100));
		is_ykgl=Utility.trimNull(map_contract.get("IS_YKGL"));
		xthtyjsyl=Utility.trimNull(map_contract.get("XTHTYJSYL"));
		//contact_id=Utility.parseInt(Utility.trimNull(map_contract.get("CONTACT_ID")),null);
		ht_cust_name = Utility.trimNull(map_contract.get("HT_CUST_NAME"));
		ht_cust_address = Utility.trimNull(map_contract.get("HT_CUST_ADDRESS"));
		ht_cust_tel = Utility.trimNull(map_contract.get("HT_CUST_TEL"));
		valid_period = (Integer)map_contract.get("VALID_PERIOD");			
		period_unit = (Integer)map_contract.get("PERIOD_UNIT");	
		spot_deal = Utility.parseInt(Utility.trimNull(map_contract.get("SPOT_DEAL")),new Integer(2));
		money_origin = Utility.trimNull(map_contract.get("BEN_MONEY_ORIGIN"));
		sub_money_origin = Utility.trimNull(map_contract.get("BEN_SUB_MONEY_ORIGIN"));
		property_souce = Utility.trimNull(map_contract.get("PROPERTY_SOURCE"));
		other_explain = Utility.trimNull(map_contract.get("OTHER_EXPLAIN"));
		contract_type = Utility.parseInt(Utility.trimNull(map_contract.get("CONTRACT_TYPE")),new Integer(0));
	}

	//获得认购附件
	AttachmentLocal attachmentLocal = EJBFactory.getAttachment();
	AttachmentVO attachment_vo = new AttachmentVO();
	attachment_vo.setDf_talbe_id(new Integer(5));
	attachment_vo.setDf_serial_no(serial_no);

	attachmentList = attachmentLocal.load(attachment_vo);
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
		fee_type_name = LocalUtilis.language("class.sgFeeAmount",clientLocale);//申购费
}

//获得客户信息
if(customerList.size()>0){
	Map map = (Map)customerList.get(0);
	cust_name = Utility.trimNull(map.get("CUST_NAME"));
	service_man = Utility.parseInt(Utility.trimNull(map.get("SERVICE_MAN")),new Integer(0));
	card_id = Utility.trimNull(map.get("CARD_ID"));
	o_tel = Utility.trimNull(map.get("O_TEL"));
	h_tel = Utility.trimNull(map.get("H_TEL"));
	mobile = Utility.trimNull(map.get("MOBILE"));
	post_code = Utility.trimNull(map.get("POST_CODE"));
	post_address = Utility.trimNull(map.get("POST_ADDRESS"));
	cust_type_name = Utility.trimNull(map.get("CUST_TYPE_NAME"));
	is_link = Utility.parseInt(Utility.trimNull(map.get("IS_LINK")),0);
	card_type = Utility.trimNull(map.get("CARD_TYPE"));
	cust_no = Utility.trimNull(map.get("CUST_NO"));
	legal_man = Utility.trimNull(map.get("LEGAL_MAN"));
	contact_man = Utility.trimNull(map.get("CONTACT_MAN"));
	cust_type = Utility.parseInt(Utility.trimNull(map.get("CUST_TYPE")),new Integer(0));
	rg_times = Utility.parseInt(Utility.trimNull(map.get("TOTAL_COUNT")),new Integer(0));
}

//渠道渠道费率，选中渠道名称的
p_vo.setProduct_id(product_id);
p_vo.setSub_product_id(sub_product_id);
p_vo.setChannel_type(channel_type);
p_vo.setR_channel_id(channel_id);
//产品销售渠道信息
List markList = product.queryMarketTrench(p_vo);
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
<TITLE><%=LocalUtilis.language("menu.editSubscriptionInfo",clientLocale)%>（<%=LocalUtilis.language("message.withoutBooked",clientLocale)%> ）</TITLE><!--认购信息编辑--><!--未预约-->
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
var n = 1;
/*有子产品的情况下 获得受益优先级，收益类别，渠道类别*/
function selectSubProduct(sub_product_id){
	var product_id_v = DWRUtil.getValue("product_id");
	var link_man = DWRUtil.getValue("link_man");
	if(sub_product_id==""){sub_product_id = 0;}

	//受益优先级，收益类别
	utilityService.getSubProductProvFlag(product_id_v,sub_product_id,0,showProvFlag);

	//产品销售渠道信息
	utilityService.queryMarketTrench(product_id_v,sub_product_id,matchCustCallBack);
<%if (user_id.intValue()==2 /*2北国投*/|| user_id.intValue()==999) { %>
	utilityService.getProductValidPeriod(product_id_v, sub_product_id, gotProductValidPeriod);
<%}%>
}

function gotProductValidPeriod(data) {
	document.theform.valid_period.value = "";
	if (data[0]) document.theform.valid_period.value = data[0];

	if (data[1]==null) {
		document.getElementById("period_unit").selectedIndex = 2;//"月";
	} else {
		document.getElementById("period_unit").selectedIndex = data[1];
	} 
}

//收益类别
function showProvFlag(data){
	var obj = document.theform.prov_flag;
	var product_id_v = <%=product_id%>;
	var sub_product_id = DWRUtil.getValue("sub_product_id");
	var intrust_flag1 = <%=intrust_flag1%>;
	var asfund_flag = <%=asfund_flag%>;
	DWRUtil.removeAllOptions(obj);
	var json = eval(data);
	for (i=0;i<json.length;i++)	DWRUtil.addOptions(obj,json[i]);
	
	var prov_flag = obj.value;
	if (intrust_flag1!=1 && asfund_flag!=null && asfund_flag==3)
		utilityService.getProvLevel(product_id_v,sub_product_id,prov_flag,0,setprovlevel);
}

//渠道名称
function matchCustCallBack(data){
	var obj_op = document.getElementById("market_trench");
    DWRUtil.removeAllOptions(obj_op);
    DWRUtil.addOptions(obj_op,{'':'<%=LocalUtilis.language("message.select2",clientLocale)%> '});   //请选择
    DWRUtil.addOptions(obj_op,data);
}

//查看客户信息
function getCustomer(cust_id){
	var url = '<%=request.getContextPath()%>/marketing/customerInfo2.jsp?cust_id='+ cust_id ;
	var v = showModalDialog(url,'','dialogWidth:720px;dialogHeight:720px;status:0;help:0;');
	if (v) {
		document.theform.customer_gain_acct.value = v[0];
		document.theform.customer_cust_name.value = v[0];
		document.theform.customer_cust_name.value = v[0];
		document.theform.customer_service_man.value = v[9];
		document.theform.customer_cust_type_name.value = v[1];
		document.theform.customer_card_id.value = v[2];
		document.theform.customer_o_tel.value = v[10];
		document.theform.customer_mobile.value = v[4];
		document.theform.customer_h_tel.value = v[3];
		document.theform.customer_email.value = v[18];
		document.theform.customer_post_address.value = v[5];
		document.theform.customer_post_code.value = v[6];
	}
}

//新增合同联系人
function newlyIncreased(value){
	var obj=value;	
	var cust_id = <%=cust_id%>;
	var url = "<%=request.getContextPath()%>/client/clientinfo/client_contact_new.jsp?cust_id="+cust_id;
	if(obj=="0"){		
		if(showModalDialog(url,'','dialogWidth:900px;dialogHeight:420px;status:0;help:0;scroll:0')){
			sl_update_ok();
			location.reload();
		}
	}
}

var acct_arr = [];
function selectBank(value) { //选择银行
	var cust_id = DWRUtil.getValue("cust_id");

	if(cust_id == 0){
		sl_alert("请先选择受益人!");
		return false;
	}
	var obj = document.theform.bank_acct2;
	utilityService.getBankAcctOption(cust_id,value,'','',
		{ callback:
			function(data){
				DWRUtil.removeAllOptions(obj);
				var json = eval(data);
				acct_arr = json;
				document.theform.inputflag.value = json.length?2:1;
				changeInput(document.theform.btnSelect);	
				obj.options.add(new Option("请选择", ""));
				DWRUtil.addOptions(obj, json, "BANK_ACCT");		
			}
		});
}

/*改变输入*/
function changeInput(obj){
	if(document.theform.inputflag.value==1){
		obj.innerText='<%=LocalUtilis.language("message.choose",clientLocale)%> ';//选择
		document.theform.bank_acct.style.display="";
		document.theform.bank_acct2.style.display="none";
		document.theform.inputflag.value=2;
	}else{
		obj.innerText='<%=LocalUtilis.language("message.input",clientLocale)%> ';//输入
		document.theform.bank_acct.style.display="none";
		document.theform.bank_acct2.style.display="";
		document.theform.inputflag.value=1;
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
	} else {
		document.theform.expect_ror_lower.value = "";
		document.theform.expect_ror_upper.value = document.theform.expect_ror_lower.value;
	}
}

/*销售限制检查*/
function checkSellInfo(){
	var channel_rate = document.theform.channel_rate.value
	var rg_money = document.theform.rg_money.value;
	if(rg_money != "" && channel_rate != "")
		document.theform.market_trench_money.value = (Number(rg_money) * Number(channel_rate)).toFixed(2);
	else
		document.theform.market_trench_money.value = "";

	var product_id = DWRUtil.getValue("product_id");
	var sub_product_id = document.theform.sub_product_id.value;
	if (document.getElementById("sub_product_id").style.display=="none") sub_product_id = 0;
	utilityService.getSubProductProvFlag2(product_id, sub_product_id, autoSetGainLevel);//受益优先级、收益级别

	return true;
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

/*受益优先级 刷选收益级别*/
function getprovlevel(){
	var product_id_v = DWRUtil.getValue("product_id");
	var sub_product_id = document.theform.sub_product_id.value;
	if (document.getElementById("sub_product_id").style.display=="none") sub_product_id = 0;
	var prov_flag = DWRUtil.getValue("prov_flag");
	utilityService.getProvLevel(product_id_v,sub_product_id,prov_flag,0,setprovlevel);
}

function setprovlevel(data){
	var ret = "<select name='prov_level' style='width:265px' onchange='javascript:getGainRate()'>"+data+"</select>";
	document.getElementById("div_prov_level").innerHTML=ret;
	var rg_money = DWRUtil.getValue("rg_money");
	if (rg_money == "") {
		document.theform.expect_ror_lower.value = "";
		document.theform.expect_ror_upper.value = document.theform.expect_ror_lower.value;
	} else {
		getGainRate();
	}
}

function getGainRate() {
	var product_id = DWRUtil.getValue("product_id");
	var sub_product_id = document.theform.sub_product_id.value;
	if (document.getElementById("sub_product_id").style.display=="none") sub_product_id = 0;
	utilityService.getSubProductProvFlag2(product_id, sub_product_id, autoSetGainLevel2);//受益优先级、收益级别
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

//通过合同销售人员，产品ID，子产品ID 查询销售人员所在团队的可销售配额，可销售人数。
function queryTeamquota(link_man){	
	var product_id = DWRUtil.getValue("product_id");
	var sub_product_id = DWRUtil.getValue("sub_product_id");
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

//银行合作
function showHtyh(){
	if(document.theform.with_bank_flag.checked){
		document.getElementById("htyhmc_v").style.display = "";
	}else{
		document.getElementById("htyhmc_v").style.display = "none";
	}
}

/*
 *添加附件
 */
function addline(){
	n++;
	newline=document.all.test.insertRow()
	newline.id="fileRow"+n;
	newline.insertCell().innerHTML="<input type='file' name=file_info"+n+" size='75'>"
		+"<button type='button'  class='xpbutton2' onclick='javascript:removeline(this)'><%=LocalUtilis.language("messgae.remove",clientLocale)%> </button>";//移除
}

/*
 *删除附件
 */
function removeline(obj){
	var objSourceRow=obj.parentNode.parentNode;
	var objTable=obj.parentNode.parentNode.parentNode.parentNode;
	objTable.lastChild.removeChild(objSourceRow);
}

function confirmRemoveAttach(obj,serial_no){
	if (confirm('<%=LocalUtilis.language("message.tAreYouSure",clientLocale)%>？')) {
		var updatehref = 'attach_remove.jsp?serial_no='+serial_no;
    	showModalDialog(updatehref, '', 'dialogWidth:0px;dialogHeight:0px;dialogLeft:0px;dialogTop:0px;status:0;help:0');
		if (obj) obj.style.display="none";
	}
}

function SaveAction(){
	if (document.theform.onsubmit()) {
		disableAllBtn(true);
		document.theform.action="subscribe_edit2.jsp";
		document.theform.submit();
	}
}

//下面是修改认购信息时用到的函数
function validateForm(form){
	if(form.quota_money.value != 0){
		var sum = parseInt(form.rg_money.value) + parseInt(form.already_sale.value);
		if(sum > parseInt(form.quota_money.value)){
			sl_alert('<%=LocalUtilis.language("message.teamQuota",clientLocale)%>');//用户所在团队在该产品上累计已销售额超过配额值
			return false;
		}
	}

	//认购合同编号开关
	<%if(contract_bh_flag!=1){%>
	    var contract_bh = form.contract_bh;
		if(!sl_check(contract_bh, '<%=LocalUtilis.language("class.constractBH",clientLocale)%> ', 20,0)){return false;}//合同序号
		if(contract_bh.value!= ""&&contract_bh.value!=null){
		  var s=contract_bh.value;
		  var len=contract_bh.value.length;
		  if(len<3){
		    sl_alert('<%=LocalUtilis.language("message.inputContractBHTip",clientLocale)%> ');//输入的合同序号至少为3位
		    return false;
		  }
		  else{
		     if((s.charCodeAt(len-1)<48 || s.charCodeAt(len-1)>57) || (s.charCodeAt(len-2)<48 || s.charCodeAt(len-2)>57)|| (s.charCodeAt(len-3)<48 || s.charCodeAt(len-3)>57)){
		        sl_alert('<%=LocalUtilis.language("messgae.contractSerialError",clientLocale)%> ！');
		        return false;
		     }
		  }
		}
	<%}%>

    if(!sl_checkChoice(form.bank_id, '<%=LocalUtilis.language("class.bankName3",clientLocale)%> ')){return false;}//银行名称
	if(form.bank_id.value != ""){
		if(document.theform.inputflag.value==1){
			if(!sl_checkChoice(form.bank_acct2, '<%=LocalUtilis.language("class.bankAcct3",clientLocale)%> '))return false;//银行帐号
		}
		else{
			if(!sl_check(form.bank_acct, '<%=LocalUtilis.language("class.bankAcct3",clientLocale)%> ', 80, 1)) return false;//银行帐号
			form.bank_acct.value = trimString(form.bank_acct.value);
		}
	}
	if(!sl_checkDecimal(form.rg_money, '<%=LocalUtilis.language("class.rg_money",clientLocale)%> ', 13, 3, 1)){return false;}//认购金额
	if(!sl_checkChoice(form.jk_type, '<%=LocalUtilis.language("class.feeType",clientLocale)%> ')){return false;}//缴款方式

	if(!sl_checkDate(form.qs_date_picker,'<%=LocalUtilis.language("class.qsDate",clientLocale)%> ')){return false;}//签署日期
	if(!sl_checkDate(form.jk_date_picker,'<%=LocalUtilis.language("class.dzDate",clientLocale)%> ')){return false;}//缴款日期
	if(!sl_check(form.summary2, '<%=LocalUtilis.language("class.contractSummary",clientLocale)%> ', 100, 0)){return false;}//合同备注

	syncDatePicker(form.qs_date_picker, form.qs_date);
	syncDatePicker(form.jk_date_picker, form.jk_date);

	var userid = <%=user_id.intValue()%>;
	var intrust_flag1 = <%=intrust_flag1%>;

	if(userid==8 && intrust_flag1==2){
	    if(!sl_checkChoice(form.market_trench, '渠道名称 ')){return false;}//渠道名称
	    if(!sl_checkChoice(form.channel_cooperation, '渠道合作方式 ')){return false;}//渠道合作方式
	}

	if(form.jk_date.value < form.qs_date.value)	{
        //缴款日期  //不能小于  //签署日期
		sl_alert('<%=LocalUtilis.language("class.dzDate",clientLocale)%><%=LocalUtilis.language("message.cannotLess",clientLocale)%><%=LocalUtilis.language("class.qsDate",clientLocale)%>');
		return false;
	}

	if(form.self_ben_flag.checked){
		form.selfbenflag.value='1';
	}else{
		form.selfbenflag.value='2';
	}

	<%if(intrust_flag1==2){%>
		if(form.qs_date.value>form.pre_end_date.value){
            //认购日期  //大于  //推介起始日期  //要继续
			if(!confirm('<%=LocalUtilis.language("class.subscriptionDate",clientLocale)%><%=LocalUtilis.language("message.greaterThan",clientLocale)%><%=LocalUtilis.language("class.preStartDate",clientLocale)%>！<%=LocalUtilis.language("message.toContinue",clientLocale)%> ？'))
				return;
		}
		if(form.qs_date.value<form.pre_start_date.value){
		//选择是否继续
            //认购日期  //小于  //推介起始日期  //要继续
			if(!confirm('<%=LocalUtilis.language("class.subscriptionDate",clientLocale)%><%=LocalUtilis.language("message.lessThan",clientLocale)%><%=LocalUtilis.language("class.preStartDate",clientLocale)%>！<%=LocalUtilis.language("message.toContinue",clientLocale)%> ？'))
				return;
		}
	<%}%>


	if(form.with_bank_flag.checked){
		form.with_bank_flag.value = 1;
	}
	if(form.with_security_flag.checked){
		form.with_security_flag.value = 1;
	}
	if(form.with_private_flag.checked){
		form.with_private_flag.value = 1;
	}

	<%if(open_flag==1){%>
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

	<%if(asfund_flag==3){%>
		if(!sl_checkChoice(form.prov_level, '<%=LocalUtilis.language("class.incomeLevel",clientLocale)%>'))return false;//收益级别
	<%}%>

	if(form.quota_qualified_num.value != 0){
		if(form.cust_type.value == 1){
			if(parseInt(form.rg_money.value) < 3000000){
				form.already_qualified_num.value = parseInt(form.already_qualified_num.value) + 1;
				if(form.already_qualified_num.value > form.quota_qualified_num.value){
					sl_alert('客户在该产品上累计已销售的合同投资人数量超过 配额值');
					return false;
				}
			}
		}
	}
	if(<%=user_id.intValue()%>==22){
		if(!sl_checkChoice(form.city_serialno, '<%=LocalUtilis.language("class.citySerialNO",clientLocale)%>')){return false;}//合同推介地
	}
	if(!sl_checkDecimal(form.expect_ror_lower, '预期收益率下限', 5, 4, 0)){return false;}//预期收益率下限
	if(!sl_checkDecimal(form.expect_ror_upper, '预期收益率上限', 5, 4, 0)){return false;}//预期收益率上限
	return sl_check_update();
}

/*显示账号位数*/
function showAcctNum(value){
	if (trim(value) == "")
		bank_acct_num.innerText = "";
	else
		bank_acct_num.innerText = "(" + showLength(value) + " 位 )";
}

/*返回*/
function CancelAction(){
<%if (from_signed_spot) {%>
	location.href = "../signed_spot.jsp?<%=qs%>";
<%}else{%>
	tempArray = '<%=sQuery%>'.split('$');
	//alert(tempArray+"---"+tempArray[8]); 
	var purchase_mode_flag = document.theform.purchase_mode_flag.value;
	if(purchase_mode_flag==1){
		location = 'purchase_mode.jsp?page=1&pagesize=' + tempArray[6] + '&productid=' + tempArray[0] + '&product_id=' + tempArray[1] 
						+ '&cust_name=' + tempArray[2] + '&query_contract_bh=' + tempArray[3] + '&card_id=' + tempArray[4] + '&check_flag=' + tempArray[5];
	}else{
		location = 'subscribe_list.jsp?page='+tempArray[12]+'&q_productId='+tempArray[0]+'&q_productCode='+tempArray[1]
				+'&q_cust_name='+tempArray[2]+'&query_contract_bh='+tempArray[3]+'&q_pre_flag='+tempArray[4]
				+'&pagesize='+tempArray[5]+'&q_check_flag='+tempArray[6]+"&q_product_name="+tempArray[7]
				+"&q_managerID="+tempArray[8]+"&q_team_id="+tempArray[9]+"&q_card_id="+tempArray[10]+"&q_channel_id="+tempArray[11];
	}
<%}%>
}

/*通过省市过滤相关的行政区域*/
function setGovRegional(value,flag){
	if(value != "" && value.length > 0){
		DWREngine.setAsync(false); // sync
		utilityService.getGovRegional(9999, value, flag,backGovRegional);
		DWREngine.setAsync(true); // async
	}
}

/*通过省市过滤相关的行政区域 回调函数*/
function backGovRegional(data){
	document.getElementById("gov_regional_span").innerHTML = "<select size='1'  name='bank_city' style='width: 150px' onkeydown='javascript:nextKeyPress(this)'>"+data+"</select>";
}

window.onload = function(){
		//queryTeamquota(<%=link_man%>);
		bankAcctView('<%=bank_acct%>');
		//setMartetTrench(document.theform.market_trench);
	<%if(bSuccess){%>
		sl_alert("认购合同编辑保存成功!");
		CancelAction();
	<%}%>
	}; 

function bankAcctView(bank_acct){
	var acct_view  = '';
	bank_acct = trimString(bank_acct);//去除所有空格
	if (bank_acct && bank_acct.length>4){
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

//验证编号
function check(){
	$("contractDIV").innerHTML = "";
	if(!sl_check(document.theform.contract_sub_bh, "合同编号"))	return false; 
	var book_code=DWRUtil.getValue("book_code");
	var contract_type=new Number(1);
	var product_id=DWRUtil.getValue("product_id");
	var contract_sub =DWRUtil.getValue("contract_sub_bh");	
	contract.findIfExistSameBH(book_code,contract_type,product_id,contract_sub,	callContractSubBHCallBack);
}

function callContractSubBHCallBack(result){	
	if (result!="")	document.getElementById("contractDIV").innerHTML = result;
}

//认购时可以维护产品的推介地，进而可以选取合适的认购地
function newCity(product_id,city_serial_no){	
	var ret = showModalDialog('/marketing/sell/product_city_update.jsp?product_id=' + product_id+'&city_serial_no='+city_serial_no, '', 'dialogWidth:500px;dialogHeight:150px;status:0;help:0');
	if (ret) {
		seloption = new Option(ret[1],ret[0]);
		document.all.city_serialno.options[document.all.city_serialno.length] = seloption;
		document.all.city_serialno.options[document.all.city_serialno.length-1].selected = true;
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
						setGovRegional(document.theform.bank_province.value, ""); //
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
</script>
</HEAD>
<BODY class="BODY body-nox">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="post" onsubmit="javascript:return validateForm(this);" ENCTYPE="multipart/form-data">
<input type="hidden" name="sQuery" value="<%=sQuery%>">
<input type="hidden" name="serial_no" value="<%=serial_no%>"/>
<input type="hidden" name="product_id" value="<%=product_id%>"/>
<input type="hidden" name="cust_id" value="<%=cust_id%>"/>
<input type="hidden" name="purchase_mode_flag" value="<%=purchase_mode_flag%>">
<!-- 受益人银行帐号输入或者选择 -->
<input type="hidden" name="inputflag" value="<%=inputflag%>"/>
<input type="hidden" name="pre_start_date" value="<%=pre_start_date %>">
<input type="hidden" name="pre_end_date" value="<%=pre_end_date %>">

<input type="hidden" id="channel_type" name="channel_type" value="<%=channel_type%>"/>
<input type="hidden" id="channel_id" name="channel_id" value="<%=channel_id%>"/>
<input type="hidden" id="channel_rate" name="channel_rate" value=""/>
<input type="hidden" name="legal_man" value="<%=legal_man %>"/>
<input type="hidden" name="contact_man" value="<%=contact_man %>"/>
<input type="hidden" name="card_type" value="<%=card_type %>"/>
<input type="hidden" name="cust_type" value="<%=cust_type %>"/>
<input type="hidden" name="cust_no" value="<%=cust_no %>"/>
<input type="hidden" name="service_man" value="<%=service_man %>"/>
<input type="hidden" name="selfbenflag" value="<%=selfbenflag%>">
<!-- 销售团队配额是否超过的验证 -->
<input type="hidden" name="quota_money" value="">
<input type="hidden" name="already_sale" value="">
<input type="hidden" name="quota_qualified_num" value="">
<input type="hidden" name="already_qualified_num" value="0">
<input type="hidden" name="team_id" value="">
<input type="hidden" name="serialNo" value="">

<input type="hidden" name="from" value="<%=from%>"/>
<input type="hidden" name="qs" value="<%=qs%>"/>
<input type="hidden" name="property_souce" value="<%=property_souce%>"/>
<input type="hidden" name="other_explain" value="<%=other_explain%>"/>
<div align="left" class="page-title">
	<font color="#215dc6"><b><%=menu_info%></b></font>
	<br/>
</div>

<!--产品选择-->
<div class="direct-panel">
<fieldset style="border-color: background;border-style: solid;border-width: 1px;;margin-left: 4px;margin-right: 4px;">
<legend style="border-color: lime;"><b><%=LocalUtilis.language("menu.prodInfo",clientLocale)%></b></legend>
<table  border="0" width="100%" cellspacing="4" cellpadding="4">
	<tr>
		<td align="right" width="11%"><b><font color="red"><b>*</b></font><%=LocalUtilis.language("class.productName",clientLocale)%>:</b></td><!--产品名称-->
		<td align=left colspan=3 width="89%">
			<input class="edline" readonly name="product_name" value="<%=product_code+"_"+product_name %>" size="77"/>
		</td>
	</tr>
	<tr id="sub_product_id" <%if(sub_product_id.intValue()==0) out.print("style='display:none;'");%>>
		<td align="right" width="11%"><b><font color="red"><b>*</b></font><%=LocalUtilis.language("class.subProductID",clientLocale)%>:</b></td><!--子产品选择-->
		<td align=left colspan=3>
			<select  name="sub_product_id" onkeydown="javascript:nextKeyPress(this)" style="width: 410px;" onclick="javascript:selectSubProduct(this.value);">
				<%=Argument.getSubProductOptions(product_id, new Integer(0),sub_product_id)%>
			</select>&nbsp;&nbsp;
			<!-- <button type="button"  class="xpbutton2" name="show_sub_info" title="查看子产品信息" onclick="javascript:openSubProduct();">
				<%//=LocalUtilis.language("message.view",clientLocale)%></button>!-- 查看 -->
		</td>
	</tr>
</table>
</fieldset>
<br>
<fieldset style="border-color: background;border-style: solid;border-width: 1px;;margin-left: 4px;margin-right: 4px;">
<legend>
	<b><%=LocalUtilis.language("message.customerInfomation",clientLocale)%> </b><!--客户信息-->
	<button type="button"   id ="cust_button" class="xpbutton3" accessKey=e name="btnEdit" 
		title='<%=LocalUtilis.language("message.customerInfomation",clientLocale)%>' 
		onclick="javascript:getCustomer(<%=cust_id%>);"><%=LocalUtilis.language("message.view",clientLocale)%> 
	</button><!--客户信息--><!--查看-->
</legend>
<table  border="0" width="100%" cellspacing="4" cellpadding="4">
	<!--客户选择-->
	<tr>
		<td colspan="4">
			<span id="cust_message" style="display:none;color:red;">
				<%=LocalUtilis.language("message.noCustomerInfo",clientLocale)%> 
			</span><!--客户信息不存在-->
		</td>
	<tr>
	<tr>
		<td align="right" width="10%"><%=LocalUtilis.language("class.customerName",clientLocale)%>:</td> <!--客户名称-->
		<td ><input maxlength="100" readonly class='edline'  name="customer_cust_name" size="50" onkeydown="javascript:nextKeyPress(this);" value="<%=cust_name%>">&nbsp;&nbsp;&nbsp;
		</td>
		<td align="right" width="10%"><%=LocalUtilis.language("class.accountManager",clientLocale)%>:</td><!--客户经理-->
		<td ><input maxlength="100" readonly class='edline'  name="customer_service_man" size="50" onkeydown="javascript:nextKeyPress(this);" 
			value="<%=Utility.trimNull(Argument.getManager_Name(service_man))%>">
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.customerType",clientLocale)%>:</td><!--客户类别-->
		<td ><INPUT readonly class='edline' name="customer_cust_type_name" size="50" value="<%=cust_type_name%>" onkeydown="javascript:nextKeyPress(this);"></td>
		<td align="right"><%=LocalUtilis.language("class.customerCardID",clientLocale)%>:</td><!--证件号码-->
		<td><input readonly class='edline' name="customer_card_id" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%=card_id%>" size="50"></td>
	</tr>
	<tr>
		<td align="right">已认购次数累计:</td>
		<td colspan="3"><INPUT readonly class='edline' name="rg_times" size="50" value="<%=rg_times%>" onkeydown="javascript:nextKeyPress(this);"></td>
	</tr>
	<tr>
		<td align="right">合同联系人:</td>
		<td colspan="3">
			<input type="text" name="ht_cust_name" size="25" onkeydown="javascript:nextKeyPress(this)" value="<%=ht_cust_name%>"/>
			&nbsp;&nbsp;电话: <input type="text" name="ht_cust_tel" size="20" onkeydown="javascript:nextKeyPress(this)" value=""/>
			&nbsp;&nbsp;地址: <input type="text" name="ht_cust_address" size="50" onkeydown="javascript:nextKeyPress(this)" value="<%=ht_cust_address%>"/>	
		</td>
	</tr>
</table>
</fieldset>
<br>
<fieldset style="border-color: background;border-style: solid;border-width: 1px;margin-left: 4px;margin-right: 4px;">
<legend>
	<b><%=LocalUtilis.language("message.contractInfo",clientLocale)%> </b><!--合同信息-->
</legend>
<table border="0" width="100%" cellspacing="4" cellpadding="4">
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
	<tr>
		<!--合同序号-->
        <td align="right" width="10%" <%if(contract_bh_flag==1){out.print("style='display:none'");}%>><b><%=LocalUtilis.language("class.constractBH",clientLocale)%>:</b></td>
		<td <%if(contract_bh_flag==1){out.print("style='display:none'");}%>>
			<input <%= checkedStyle%> name="contract_bh" size="45" maxlength=20 onkeydown="javascript:nextKeyPress(this)" value="<%=contract_bh%>"></td>
		<td align="right" width="10%" ><%=LocalUtilis.language("class.contractID",clientLocale)%>:</td><!--合同编号-->
		<!--验证编号-->
        <td ><input <%= purchase_mode_checkedStyle%> name="contract_sub_bh" size="35" maxlength=40 onkeydown="javascript:nextKeyPress(this)" value="<%=contract_sub_bh%>">&nbsp;
			<INPUT type="button" class="xpbutton3" <%=purchase_mode_checkedStyle2%> onclick="javascript:check();" value='<%=LocalUtilis.language("messgae.CheckBH",clientLocale)%> '>
			<!-- 如果是合同补全进入 由于编号可变 显示原编号 -->
			&nbsp;&nbsp;<%if(purchase_mode_flag.intValue()==1){%><span id="ex_contract_sub_bh" style="color:green">当前合同编号:<%=contract_sub_bh%></span><%}%>
			<div id="contractDIV"></div>
		</td>
	</tr>
<%if (user_id.intValue()==2 /*2北国投*/|| user_id.intValue()==999) { %>
		<tr>
			<td align="right">合同期限:</td>
			<td colspan="3">
				<input type="text" name="valid_period" size="5" onkeydown="javascript:nextKeyPress(this)" value="<%=Utility.trimNull(valid_period)%>"/> 
				<select name="period_unit" id="period_unit"><%=Argument.getPeriodValidUnitOptions(period_unit)%></select>
			</td>
		</tr>
<%} %>
	<tr>
		<td align="right" width="10%" ><font color="red"><b>*</b></font><%=LocalUtilis.language("class.bandAcct4",clientLocale)%>:</td><!--受益人银行帐号-->
		<td>
			<input <%if(inputflag==1) out.print("style=display:none");%>  type="text" name="bank_acct" 
				value="<%//=bank_acct%>" <%= purchase_mode_checkedStyle%> onkeydown="javascript:nextKeyPress(this)" onblur="javscript:bankAcctView(this.value);"
				onkeyup="javascript:showAcctNum(this.value)" size="34"/>
			<select style="WIDTH:200px;display:<%=inputflag==2?"none":""%>" size="1" name="bank_acct2" onchange="javascript:autoSetAcctInfo(this);"
				<%= purchase_mode_checkedStyle2%> onkeydown="javascript:nextKeyPress(this)">
				<%=Argument.getCustBankAcctOptions(cust_id,bank_id,card_id,"")%>
			</select>&nbsp;
			<!--输入-->
            <button type="button"   class="xpbutton2" <%=purchase_mode_checkedStyle2%> accessKey=x name="btnSelect" title='<%=LocalUtilis.language("message.input",clientLocale)%>' onclick="javascript:changeInput(this);"><%=LocalUtilis.language("message.choose",clientLocale)%> (<u>X</u>)</button>
			<span id="bank_acct_num" class="span"></span>
		</td>
		<td align="right" width="10%" ><font color="red"><b>*</b></font><%=LocalUtilis.language("class.bankName5",clientLocale)%>:</td><!--受益人银行名称-->
		<td >
			<select size="1" name="bank_id" <%=purchase_mode_checkedStyle2%> onchange="javascript:selectBank(this.value);"  style="WIDTH:150px">
				<%=Argument.getBankOptions(bank_id)%>
			</select>
			<input type="text" name="bank_sub_name" <%=purchase_mode_checkedStyle%> size="20" onkeydown="javascript:nextKeyPress(this)" value="<%=bank_sub_name%>">
		</td>		
	</tr>
	<tr>
		<td align="right" width="10%" >开户行所在地:</td>
		<td>
			<select size="1" <%=purchase_mode_checkedStyle2%>  name="bank_province" style="width: 130px" onkeydown="javascript:nextKeyPress(this)" onchange="javascript:setGovRegional(this.value,'');">
				<%=Argument.getCustodianNameLis(new Integer(9999), "", new Integer(0),bank_province)%>
			</select>
			<span id="gov_regional_span">
				<select style="WIDTH: 150px" name="bank_city" <%=purchase_mode_checkedStyle2%>>
					<%=Argument.getCustodianNameLis(new Integer(9999), bank_province, new Integer(0),bank_city)%>
				</select>
			</span><!-- 省级行政区域过滤出来的相关区域 -->
		</td>
		<td align="right" width="10%" >推荐人:</td>
		<td>
			<select name="recommend_man" style="width:230px;" onkeydown="javascript:nextKeyPress(this)" <%=purchase_mode_checkedStyle2%>>
				<%=Argument.getIntrustOptions(recommend_man,new Integer(1)) %>
			</select>
		</td>
	</tr>
	<tr>
		<td align="right" width="10%" ><%=LocalUtilis.language("class.bankAccountType",clientLocale)%>:</td><!--银行账号类型-->
		<td >
			<select size="1" name="bank_acct_type" <%= checkedStyle2%> onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 278px">
				<%=Argument.getBankAcctType(bank_acct_type)%>
			</select>
		</td>
		<td align="right" width="10%" ><%=LocalUtilis.language("class.customerGainAcct",clientLocale)%>:</td><!--银行帐号户名-->
		<td>
			<input <%=purchase_mode_checkedStyle%> name="customer_gain_acct" size="48" onkeydown="javascript:nextKeyPress(this)" value="<%=gain_acct%>" />
		</td>
	</tr>
	<tr>
		<td align="right" width="10%" ><font color="red"><b>*</b></font><%=LocalUtilis.language("class.feeType",clientLocale)%>:</td><!--缴款方式-->
		<td>
			<select size="1" name="jk_type" <%=purchase_mode_checkedStyle2%> onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 278px">
				<%=Argument.getJkTypeOptions(jk_type)%>
			</select>
		</td>
		<td align="right" width="10%" ><font color="red"><b>*</b></font><%=LocalUtilis.language("class.rg_money",clientLocale)%>:</td><!--认购金额-->
		<td>
			<input name="rg_money" <%=checkedStyle%> size="48" value="<%=rg_money%>" 
				onchange="javascript:checkSellInfo();" onkeydown="javascript:nextKeyPress(this)" 
				onkeyup="javascript:showCnMoney(this.value,rg_money_cn)">	
		</td>
	</tr>
	<tr>
		<td colspan="3"></td>
		<td><span id="rg_money_cn" class="span"><%=rg_money_cn%></span></td>
	</tr>
	<tr>
		<td align="right" width="10%" ><font color="red"><b>*</b></font><%=LocalUtilis.language("class.qsDate",clientLocale)%>:</td><!--签署日期-->
		<td>
			<INPUT TYPE="text" NAME="qs_date_picker" size="46" class=selecttext class=selecttext <%=purchase_mode_checkedStyle2%> value="<%=Format.formatDateLine(qs_date)%>" >
			<INPUT TYPE="button" value="▼" <%=purchase_mode_checkedStyle2%> class=selectbtn
			onclick="javascript:CalendarWebControl.show(theform.qs_date_picker,theform.qs_date_picker.value,this);"
			tabindex="13">
			<INPUT TYPE="hidden" NAME="qs_date" value="<%=qs_date%>">
		</td>
		<td align="right" width="10%" ><font color="red"><b>*</b></font><%=LocalUtilis.language("class.dzDate",clientLocale)%>:</td><!--缴款日期-->
		<td>
			<INPUT TYPE="text" NAME="jk_date_picker" size="43.5" class=selecttext <%=purchase_mode_checkedStyle2%> value="<%=Format.formatDateLine(jk_date)%>">
			<INPUT TYPE="button" value="▼" <%=purchase_mode_checkedStyle2%> class=selectbtn onclick="javascript:CalendarWebControl.show(theform.jk_date_picker,theform.jk_date_picker.value,this);" tabindex="14">
			<INPUT TYPE="hidden" NAME="jk_date" value="<%=jk_date%>">
		</td>
	</tr>
<%if(open_flag==1){%>
	<tr>
		<td align="right" width="10%"><%=fee_type_name%><%=LocalUtilis.language("class.feeTypeName",clientLocale)%>:</td><!--扣缴方式-->
		<td >
			<select size="1" <%= checkedStyle2%> name="fee_jk_type" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 280px">
				<%=Argument.getFeeJkTypeOptions(fee_jk_type)%>
			</select> 
		</td>
		<td align="right" width="10%" ><%=LocalUtilis.language("class.bonusFlag",clientLocale)%>:</td><!--收益分配方式-->
		<td>
			<select size="1" <%=purchase_mode_checkedStyle2%> name="bonus_flag" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 265px">
				<%=Argument.getBonus_flag(bonus_flag)%>
			</select>
			<span id="bonus_rate_div" <%if(bonus_flag.intValue()!=3) out.print("style='display:none'");%>>转份额比例:
				<input type="text" name="bonus_rate" size="10" onkeydown="javascript:nextKeyPress(this)"
					value="<%=(Utility.parseDecimal(Utility.trimNull(bonus_rate),new BigDecimal(0))).multiply(new BigDecimal(100)).setScale(2)%>">%
			</span>
		</td>
	</tr>
<%}%>
	<tr>
		<td align="right" width="10%" ><font color="red"><b>*</b></font>渠道名称:</td>
		<td>
			<select id="market_trench" <%=purchase_mode_checkedStyle2%> name="market_trench" style="width: 280px;" onchange="javascript:setMartetTrench(this);">
				<%=Argument.queryMarketTrench(product_id,sub_product_id,marValue) %>
			</select>
		</td>
		<td align="right" width="10%" >渠道费用:</td>
		<td><input name="market_trench_money" size="48" value="<%=market_trench_money %>" <%=purchase_mode_checkedStyle2%>
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
			<input name="channel_memo" size="51" <%=purchase_mode_checkedStyle%>  onkeydown="javascript:nextKeyPress(this)"  value="<%=channel_memo%>">
		</td>
		<td align="right" width="10%" ><font color="red"><b>*</b></font><%=LocalUtilis.language("class.channelCooperation",clientLocale)%>:</td><!-- 渠道合作方式 -->
		<td>
			<SELECT <%=purchase_mode_checkedStyle2%> name="channel_cooperation" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 265px">
				<%=Argument.getChannelCooperationOptions(channel_cooperation)%>
			</SELECT>
		</td>
	</tr>
	<%if(intrust_flag1!=1){%>
	<tr>
		<td align="right" width="10%" ><%=LocalUtilis.language("class.provLevel",clientLocale)%>:</td><!-- 受益优先级 -->
		<td>
			<select <%= purchase_mode_checkedStyle2%> name="prov_flag" style="WIDTH: 278px" <%if(asfund_flag==3){%>onchange="javascript:getprovlevel()"<%}%> onkeydown="javascript:nextKeyPress(this)">
				<%=Argument.getProductProvFlag(product_id,sub_product_id,prov_flag)%>
			</select>
		</td>
		<%if(asfund_flag==3){%>
		<td align="right" width="10%" ><font color="red">*</font><%=LocalUtilis.language("class.incomeLevel",clientLocale)%>:</td><!-- 收益级别 -->
		<td>
			<div id="div_prov_level">
			<select <%=purchase_mode_checkedStyle2%> name="prov_level" style="width:265px" onchange='javascript:getGainRate()'>
				<%=Argument.getProductProvLevel(product_id,sub_product_id,prov_flag,prov_level)%>
			</select>
			</div>
		</td>
		<%}%>
	</tr>
	<%}%>
	<tr id="money_origin_id">
				<td align="right">资金/资产来源:</td>
				<td colspan="3">
					<select size="1" name="money_origin" onkeydown="javascript:nextKeyPress(this)" style="width:120px" onchange="javascript:moneyOriginChange(this.value);">
						<%=Argument.getDictOptionsWithLevel(new Integer(1158),"1158",new Integer(Utility.parseInt(money_origin,0)))%>
					</select>
					<select size="1" name="sub_money_origin" <%if("115801".equals(money_origin)||"115807".equals(money_origin)||"".equals(money_origin)||money_origin==null) out.println("style='display:none'");%> onkeydown="javascript:nextKeyPress(this)" style="width:120px">
						<%=Argument.getDictOptionsWithLevel(new Integer(1158),money_origin, new Integer(Utility.parseInt(sub_money_origin,0)))%>
					</select>
				</td>
	</tr>
	<tr>
		<td align="right" width="10%" ><%=LocalUtilis.language("class.linkMan",clientLocale)%>:</td><!--合同销售人员-->
		<td>
			<select <%= purchase_mode_checkedStyle2%> name="link_man" onkeydown="javascript:nextKeyPress(this)" style="WIDTH:280px" onchange="javascript:queryTeamquota(this.value);">
				<%=Argument.getOperatorOptionsByRoleId(new Integer(2),link_man)%>
			</select>
		</td>

		<td align="right">预期收益率:</td>
		<td>
			<input name="expect_ror_lower" size="12" value="<%=Utility.trimZero(expect_ror_lower)%>" onkeydown="javascript:nextKeyPress(this)"><font color="red">%</font> 到 
			<input name="expect_ror_upper" size="12" value="<%=Utility.trimZero(expect_ror_upper)%>" onkeydown="javascript:nextKeyPress(this)"><font color="red">%</font>
		</td>
	</tr>
	<tr style="display: <%=SUB_COOPER == 2 ? "none" : ""%>;">
		<td align="right" width="10%" ><%=LocalUtilis.language("message.cooperationWay",clientLocale)%>:</td><!--合作方式-->
		<td>
			<input <%=purchase_mode_checkedStyle2%> <%if(with_bank_flag==1)out.print("checked");%>
				onkeydown="javascript:nextKeyPress(this)" type="checkbox" class="flatcheckbox" value="<%=with_bank_flag %>"
				name="with_bank_flag" onclick="javascript:showHtyh();">
				<%=LocalUtilis.language("intrsut.home.silvertrustcooper",clientLocale)%>&nbsp;&nbsp;&nbsp;&nbsp;<!--银信合作-->
			<input <%= purchase_mode_checkedStyle2%> <%if(with_security_flag==1)out.print("checked");%>
				onkeydown="javascript:nextKeyPress(this)" type="checkbox"
				class="flatcheckbox" value="<%=with_security_flag %>" name="with_security_flag">
				<%=LocalUtilis.language("message.cooperation2",clientLocale)%> &nbsp;&nbsp;<!--证信合作-->
			<input <%= purchase_mode_checkedStyle2%> <%if(with_private_flag==1)out.print("checked");%>
				onkeydown="javascript:nextKeyPress(this)" type="checkbox"
				class="flatcheckbox" value="<%=with_private_flag %>" name="with_private_flag">
			<%=LocalUtilis.language("class.cooperation4",clientLocale)%> &nbsp;&nbsp;<!--私募基金合作-->
		</td>
		
		
	</tr>
	<tr id="htyhmc_v" <%if(with_bank_flag!=1) out.print("style='display:none'");%>>
		<td align="right" width="10%"><%=LocalUtilis.language("class.withBankId",clientLocale)%>:<!-- 合作银行 --></td>
		<td colspan="3">
			<select <%=purchase_mode_checkedStyle2%> size="1" name="ht_bank_id" style="WIDTH: 220px">
				<%=Argument.getBankOptions(Utility.trimNull(ht_bank_id))%>
			</select>
			<input <%=purchase_mode_checkedStyle%> type="text" name="ht_bank_sub_name" size="20" onkeydown="javascript:nextKeyPress(this)" value="<%=Utility.trimNull(ht_bank_sub_name)%>">
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.selfBenFlag",clientLocale)%>:</td><!--自益标志-->
		<td><input name="self_ben_flag" class="flatcheckbox" <%=checkedStyle2%> type="checkbox" value="" <%if(selfbenflag==1) out.print("checked");%> onkeydown="javascript:nextKeyPress(this)"></td>
		<td align="right" width="10%" ><%if(user_id.intValue()==22){%><font color="red"><b>*</b></font><%}%><%=LocalUtilis.language("class.citySerialNO",clientLocale)%>:</td><!--合同推介地-->
		<td >
			<select <%= purchase_mode_checkedStyle2%> size="1" name="city_serialno" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 200px">
				<%=Argument.getCitynameOptions(product_id,citySerialno)%>
			</select>&nbsp;&nbsp;
			<button type="button"   class="xpbutton4" accessKey=r id="btnSetcity" name="btnSetcity" onclick="javascript:newCity(<%=product_id%>,'');" ><%=LocalUtilis.language("class.promotionSet",clientLocale)%> </button>
		</td>
	</tr>
	<tr>
		<td width="120px" align="right">用款方关联:</td><!--用款方关联-->
		<td>   
			<INPUT type="radio" class="flatcheckbox" id="is_ykgl" name="is_ykgl" value="1" <%if(is_ykgl.equals("1")) out.print("checked");%>>是
			<INPUT type="radio" class="flatcheckbox" id="is_ykgl" name="is_ykgl" value="0" <%if(!is_ykgl.equals("1")) out.print("checked");%>>否
		</td>
		<td width="120px" align="right">非现场交易:</td>
		<td>   
			<input type="checkbox" class="flatcheckbox" name="spot_deal" value="1" <%=spot_deal.intValue() == 1 ? "checked" : ""%> <%=purchase_mode_checkedStyle2%>>
		</td>
	</tr>
	<tr>
		<td align="right" vAlign="top" width="10%">合同预计收益率:</td><!--信托合同预计收益率-->
		<td  colspan=1><textarea rows="3" id="xthtyjsyl" name="xthtyjsyl" onkeydown="javascript:nextKeyPress(this)" cols="83"><%=xthtyjsyl %></textarea></td>
		<td colspan="2" align="center"><button type="button"  class="xpbutton5"  onclick="show_property_souce(<%=serial_no%>);">信托财产来源</button></td>
	</tr>	
	<tr>
		<td align="right" width="10%" ><%=LocalUtilis.language("class.description",clientLocale)%>:</td><!--备注-->
		<td colspan="3">
		<textarea rows="3" <%if(check_flag==2&&purchase_mode_flag.intValue()==0){out.print("readonly style=edline_textarea");}%> name="summary2" onkeydown="javascript:nextKeyPress(this)" cols="83"><%=summary%></textarea>
		</td>
	</tr>
</table>
</fieldset>
<br>
<fieldset style="border-color: background;border-style: solid;border-width: 1px;;margin-left: 4px;margin-right: 4px;">
<legend style="border-color: lime;"><b>&nbsp;附件资料</b></legend>
<table  border="0" width="100%" cellspacing="4" cellpadding="4">
	<tr id="reader2" style="display:">
      	<td class="paramTitle"align="right" width="10%"><%=LocalUtilis.language("menu.filesAdd",clientLocale)%>:</td><!-- 添加附件 -->
        <td colspan="3">
        	<table id="test" width="100%">
        		<tr >
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
	            </tr>
	        </table>
	        <button type="button"   class="xpbutton6" onclick="addline()" <%=purchase_mode_checkedStyle2%>><%=LocalUtilis.language("class.moreMccessories",clientLocale)%> <!--单击此处添加更多附件--></button>
        </td>
    </tr>	
</table>
</fieldset>
</div>
<div align="right">
	<br>
	<!--未审核保存-->
    <button type="button"   class="xpbutton3" <%=purchase_mode_checkedStyle2%> accessKey=s id="btnSave" name="btnSave"  onclick="javascript:SaveAction();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;
	<!--返回-->
    <button type="button"   class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:CancelAction();"><%=LocalUtilis.language("message.back",clientLocale)%> (<u>C</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</div>
</form>
<p>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>
<%}catch(Exception e){throw e;}%>