<%@ page contentType="text/html; charset=GBK" import="enfo.crm.marketing.*,enfo.crm.web.*,enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<% // 有预约的认购合同 新建
//获得页面传递变量
int flash_flag=Utility.parseInt(request.getParameter("flash_flag"), 0);
String sQuery = request.getParameter("sQuery");
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"), null);//认购主键

String preSerialNo =  Utility.trimNull(request.getParameter("pre_serial_no1"), null);//预约主键
Integer pre_serial_no =  new Integer(0);
if(preSerialNo!=null){
	pre_serial_no = Utility.parseInt(Utility.splitString(preSerialNo,"$")[0],new Integer(0));
}
String q_productId = Utility.trimNull(request.getParameter("product_id"));  //产品信息ID
String q_cust_id = Utility.trimNull(request.getParameter("customer_cust_id")); //客户ID
String bank_id = request.getParameter("bank_id");//银行ID
String channel_type = Utility.trimNull(request.getParameter("channel_type"));
String channel_memo = Utility.trimNull(request.getParameter("channel_memo"));
String channel_cooperation = Utility.trimNull(request.getParameter("channel_cooperation"));
Integer r_channel_id = Utility.parseInt(Utility.trimNull(request.getParameter("channel_id")),new Integer(0));
BigDecimal market_trench_money = Utility.parseDecimal(Utility.trimNull(request.getParameter("market_trench_money")),new BigDecimal(0));
Integer check_flag=Utility.parseInt(request.getParameter("check_flag"),new Integer(0));
int inputflag=Utility.parseInt(request.getParameter("inputflag"), 1);
int inputflag2=Utility.parseInt(request.getParameter("inputflag2"), 1);
int selfbenflag = Utility.parseInt(request.getParameter("selfbenflag"), 1);//自益标志
Integer bonus_flag = Utility.parseInt(request.getParameter("bonus_flag"),new Integer(1));

Integer fee_jk_type = Utility.parseInt(request.getParameter("fee_jk_type"), new Integer(0));
String jk_type = Utility.trimNull(request.getParameter("jk_type"),"111404"); //缴款方式
Integer qs_date = Utility.parseInt(Utility.trimNull(request.getParameter("qs_date")),new Integer(0));//签约日期
Integer jk_date = Utility.parseInt(Utility.trimNull(request.getParameter("jk_date")),new Integer(0));//缴款日期
String is_ykgl=Utility.trimNull(request.getParameter("is_ykgl"),"0");//用款方关联标志
String xthtyjsyl=Utility.trimNull(request.getParameter("xthtyjsyl"));//信托合同预计收益率
String bank_acct_type = Utility.trimNull(request.getParameter("bank_acct_type"));
String q_cust_name =  Utility.trimNull(request.getParameter("cust_name"));

String prov_level =  Utility.trimNull(request.getParameter("prov_level"));
String ht_bank_id =  Utility.trimNull(request.getParameter("ht_bank_id"));
String ht_bank_sub_name =  Utility.trimNull(request.getParameter("ht_bank_sub_name"));
Integer with_bank_flag = Utility.parseInt(request.getParameter("with_bank_flag"), new Integer(0));
Integer with_security_flag = Utility.parseInt(request.getParameter("with_security_flag"), new Integer(0));
Integer with_private_flag = Utility.parseInt(request.getParameter("with_private_flag"), new Integer(0));
Integer prov_flag = Utility.parseInt(request.getParameter("prov_flag"), new Integer(0));
BigDecimal bonus_rate = Utility.parseDecimal(Utility.trimNull(request.getParameter("bonus_rate")),new BigDecimal(0));

Integer input_man = Utility.parseInt(Argument.getContractLinkMan(pre_serial_no),input_operatorCode);
Integer link_man = Utility.parseInt(request.getParameter("link_man"), input_man);
String gain_acct = Utility.trimNull(request.getParameter("gain_acct"));
Integer pre_date = Utility.parseInt(request.getParameter("pre_date"), new Integer(0));
String contract_sub_bh = Utility.trimNull(request.getParameter("contract_sub_bh"));
Integer contract_type = Utility.parseInt(request.getParameter("contract_type"),new Integer(0));

Integer valid_period = null;
Integer period_unit = new Integer(2);
Integer spot_deal = null;
String money_origin = "";
String sub_money_origin = "";

//团队信息
Integer team_id = new Integer(-1);
BigDecimal quota_money = new BigDecimal(0);
BigDecimal already_sale = new BigDecimal(0);
Integer quota_qualified_num = new Integer(0);//合格投资人数量配额
Integer already_qualified_num = new Integer(0);//累计已销售的合同投资人数量
Integer serialNo = new Integer(0);
Integer asfund_flag = new Integer(0);
Integer open_flag = new Integer(0);
Integer intrust_flag1 = new Integer(0);

//执行添加操作 添加附件
DocumentFile2 file = null;

if(request.getMethod().equals("POST")){
	file = new DocumentFile2(pageContext);
	file.parseRequest();
	/*********因为增加了一个附件上传，所以原先request方法取值的改为 file*******************/
	flash_flag=Utility.parseInt(file.getParameter("flash_flag"), 0);
	contract_sub_bh = file.getParameter("contract_sub_bh");
	preSerialNo =  Utility.trimNull(file.getParameter("pre_serial_no1"), null);//预约主键
	pre_serial_no =  new Integer(0);
	if(preSerialNo!=null&&!"".equals(preSerialNo)){
		pre_serial_no = Utility.parseInt(Utility.splitString(preSerialNo,"$")[0],new Integer(0));
	}
	sQuery = file.getParameter("sQuery");
	serial_no = Utility.parseInt(file.getParameter("serial_no"), null);//认购主键
	pre_serial_no = Utility.parseInt(file.getParameter("pre_serial_no1"), null);//预约主键
	q_productId = Utility.trimNull(file.getParameter("product_id"));  //产品信息ID
	q_cust_id = Utility.trimNull(file.getParameter("customer_cust_id")); //客户ID
	bank_id = file.getParameter("bank_id");//银行ID

	check_flag=Utility.parseInt(file.getParameter("check_flag"),new Integer(0));
	inputflag=Utility.parseInt(file.getParameter("inputflag"), 1);
	inputflag2=Utility.parseInt(file.getParameter("inputflag2"), 1);
	selfbenflag = Utility.parseInt(file.getParameter("selfbenflag"), 1);//自益标志
	bonus_flag = Utility.parseInt(file.getParameter("bonus_flag"),new Integer(1));

	fee_jk_type = Utility.parseInt(file.getParameter("fee_jk_type"), new Integer(0));
	jk_type = Utility.trimNull(file.getParameter("jk_type"),"111404"); //缴款方式
	qs_date = Utility.parseInt(Utility.trimNull(file.getParameter("qs_date")),new Integer(0));//签约日期
	jk_date = Utility.parseInt(Utility.trimNull(file.getParameter("jk_date")),new Integer(0));//缴款日期
	is_ykgl=Utility.trimNull(file.getParameter("is_ykgl"),"0");//用款方关联标志
	xthtyjsyl=Utility.trimNull(file.getParameter("xthtyjsyl"));//信托合同预计收益率
	Integer contact_id=Utility.parseInt(file.getParameter("cust_message"),null);//合同联系人

	bank_acct_type = Utility.trimNull(file.getParameter("bank_acct_type"));
	q_cust_name =  Utility.trimNull(file.getParameter("cust_name"));

	prov_level =  Utility.trimNull(file.getParameter("prov_level"));
	ht_bank_id =  Utility.trimNull(file.getParameter("ht_bank_id"));
	ht_bank_sub_name =  Utility.trimNull(file.getParameter("ht_bank_sub_name"));
	with_bank_flag = Utility.parseInt(file.getParameter("with_bank_flag"), new Integer(0));
	with_security_flag = Utility.parseInt(file.getParameter("with_security_flag"), new Integer(0));
	with_private_flag = Utility.parseInt(file.getParameter("with_private_flag"), new Integer(0));
	prov_flag = Utility.parseInt(file.getParameter("prov_flag"), new Integer(0));
	bonus_rate = Utility.parseDecimal(Utility.trimNull(file.getParameter("bonus_rate")),new BigDecimal(0));
	pre_date = Utility.parseInt(file.getParameter("pre_date"),new Integer(0));

	valid_period = Utility.parseInt(file.getParameter("valid_period"), null);
	period_unit = Utility.parseInt(file.getParameter("period_unit"), null);

	spot_deal = Utility.parseInt(file.getParameter("spot_deal"), new Integer(2));
	money_origin=Utility.trimNull(file.getParameter("money_origin"));//资金/资产来源
	sub_money_origin=Utility.trimNull(file.getParameter("sub_money_origin"));//资金/资产来源(二级)
	contract_type = Utility.parseInt(Utility.trimNull(file.getParameter("contract_type")),new Integer(0));

	if(bonus_flag.intValue() == 1){//现金
		bonus_rate = new BigDecimal(0);
	}else if(bonus_flag.intValue() == 2){//转份额
		bonus_rate = new BigDecimal(1);
	}else{//部分转份额
		bonus_rate = Utility.parseDecimal(Utility.trimNull(file.getParameter("bonus_rate")),new BigDecimal(0)).multiply(new BigDecimal("0.01"));
	}

	/****************************/
}

//页面参数变量声明 及帐套暂时设置
boolean bSuccess = false;
input_bookCode = new Integer(1);
Integer userid = new Integer(2);//用户公司代号
String fee_type_name = enfo.crm.tools.LocalUtilis.language("class.rgFee",clientLocale);//认购费

String preCodeOptions = "";//本地预约号列表
String preCodeOptions2 = "";//总行预约号列表
String bankOptions = "";//银行号列表
String cityNameOptions= "";//合同推介地

//声明参数
//ID
Integer cust_id = Utility.parseInt(q_cust_id,new Integer(0));
Integer product_id = Utility.parseInt(q_productId,new Integer(0));
Integer cust_type = new Integer(0);

//产品信息
String period_unit_name = "";
Integer sub_product_id = Utility.parseInt(request.getParameter("sub_product_id"), new Integer(0));
Integer product_sub_flag = new Integer(0);//子产品标志
String product_code = "";
String product_status ="";
Integer pre_start_date = new Integer(0);
Integer pre_end_date = new Integer(0);

//预约相关信息
Integer pre_cust_id = new Integer(0);
String pre_cust_name = "";
String pre_cust_no = "";
String pre_code ="";
BigDecimal pre_money = Utility.parseDecimal(request.getParameter("pre_money"),new BigDecimal(0.00));
BigDecimal r_rg_money = Utility.parseDecimal(request.getParameter("rg_money"),new BigDecimal(0));
//------------------------------------
String cust_name = Utility.trimNull(q_cust_name);
String contract_bh = "";//合同序号
String bank_acct = Utility.trimNull(request.getParameter("bank_acct"));

List markList = null;
Map markMap = null;

//获得对象
CustomerLocal customer = EJBFactory.getCustomer();//客户
ContractLocal contract = EJBFactory.getContract();//合同
ProductLocal product = EJBFactory.getProduct();//产品
PreContractLocal preContract = EJBFactory.getPreContract();//预约

ContractVO cont_vo = new ContractVO();
CustomerVO cust_vo = new CustomerVO();
ProductVO p_vo = new ProductVO();
PreContractVO pre_vo = new PreContractVO();
SaleParameterVO salevo = new SaleParameterVO();
SaleParameterLocal sale_parameter = EJBFactory.getSaleParameter();

if(request.getMethod().equals("POST")&&flash_flag==0){
	//获得保存数据
	Integer r_product_id = Utility.parseInt(file.getParameter("product_id"), null);
	String r_product_name = Argument.getProductName(Utility.parseInt(file.getParameter("product_id"), null));
	Integer r_cust_id = Utility.parseInt(file.getParameter("customer_cust_id"), null);
	String r_pre_code = file.getParameter("pre_code");
	Integer r_qs_date = Utility.parseInt(file.getParameter("qs_date"), new Integer(Utility.getCurrentDate()));
	Integer r_jk_date = Utility.parseInt(file.getParameter("jk_date"), new Integer(Utility.getCurrentDate()));//改参数已作废，所以随便传入当前时间
	r_rg_money = Utility.parseDecimal(file.getParameter("rg_money"),new BigDecimal(0));
	String r_jk_type = file.getParameter("jk_type");
	String r_bank_id = file.getParameter("bank_id");
	String r_bank_sub_name = Utility.trimNull(file.getParameter("bank_sub_name"));
	String r_gain_acct = file.getParameter("gain_acct");
	int r_fee_jk_type = Utility.parseInt(file.getParameter("fee_jk_type"),0);
	String r_bank_acct_type = Utility.trimNull(file.getParameter("bank_acct_type"));
	Integer r_bonus_flag = Utility.parseInt(file.getParameter("bonus_flag"),new Integer(1));
	String r_bank_acct = "";
	Integer r_with_bank_flag = Utility.parseInt(file.getParameter("with_bank_flag"), new Integer(0));
	Integer r_with_security_flag = Utility.parseInt(file.getParameter("with_security_flag"), new Integer(0));
	Integer r_with_private_flag = Utility.parseInt(file.getParameter("with_private_flag"), new Integer(0));
	Integer r_prov_flag = Utility.parseInt(file.getParameter("prov_flag"), new Integer(0));
	String r_prov_level = Utility.trimNull(file.getParameter("prov_level"));
	String r_ht_bank_id =  Utility.trimNull(file.getParameter("ht_bank_id"));
	String r_ht_bank_sub_name =  Utility.trimNull(file.getParameter("ht_bank_sub_name"));
	is_ykgl=Utility.trimNull(file.getParameter("is_ykgl"),"0");//用款方关联标志
	xthtyjsyl=Utility.trimNull(file.getParameter("xthtyjsyl"));//信托合同预计收益率
	//Integer contact_id=Utility.parseInt(file.getParameter("cust_message"),null);//合同联系人
	String ht_cust_name = Utility.trimNull(file.getParameter("ht_cust_name"));
	String ht_cust_address = Utility.trimNull(file.getParameter("ht_cust_address"));
	String ht_cust_tel = Utility.trimNull(file.getParameter("ht_cust_tel"));

	BigDecimal expect_ror_lower = Utility.parseDecimal(file.getParameter("expect_ror_lower"),new BigDecimal(0.00),4,"0.01");
	BigDecimal expect_ror_upper = Utility.parseDecimal(file.getParameter("expect_ror_upper"),new BigDecimal(0.00),4,"0.01");
	BigDecimal r_bonus_rate = new BigDecimal(0);

	if(r_bonus_flag.intValue() == 1){//现金
		r_bonus_rate = new BigDecimal(0);
	}else if(r_bonus_flag.intValue() == 2){//转份额
		r_bonus_rate = new BigDecimal(1);
	}else{//部分转份额
		r_bonus_rate = Utility.parseDecimal(Utility.trimNull(file.getParameter("bonus_rate")),new BigDecimal(0)).multiply(new BigDecimal("0.01"));
	}

	if(inputflag2==1){
		r_bank_acct = file.getParameter("bank_acct2");
	}
	else{
		r_bank_acct = file.getParameter("bank_acct");
	}

	Integer r_service_man = Utility.parseInt(file.getParameter("service_man"), input_operatorCode);
	String r_summary = Utility.trimNull(file.getParameter("summary2"));
	Integer r_check_man = Utility.parseInt(file.getParameter("check_man"), new Integer(0));
	Integer r_city_serialno = Utility.parseInt(file.getParameter("city_serialno"), new Integer(0));
	String r_contract_sub_bh = file.getParameter("contract_sub_bh");
	String r_contract_bh = file.getParameter("contract_bh");
	int r_selfbenflag = Utility.parseInt(file.getParameter("selfbenflag"),1);
	Integer r_sub_product_id = Utility.parseInt(Utility.trimNull(file.getParameter("sub_product_id")),new Integer(0));
	channel_type = Utility.trimNull(file.getParameter("channel_type"));
	channel_memo = Utility.trimNull(file.getParameter("channel_memo"));
	channel_cooperation = Utility.trimNull(file.getParameter("channel_cooperation"));
	r_channel_id = Utility.parseInt(Utility.trimNull(file.getParameter("channel_id")),new Integer(0));
	market_trench_money = Utility.parseDecimal(Utility.trimNull(file.getParameter("market_trench_money")),new BigDecimal(0));
	//修改团队累计已销售额
	Integer r_team_id = Utility.parseInt(Utility.trimNull(file.getParameter("team_id")),new Integer(-1));
	Integer r_serialNo = Utility.parseInt(Utility.trimNull(file.getParameter("serialNo")),new Integer(0));
	BigDecimal r_already_sale = Utility.parseDecimal(file.getParameter("already_sale"),new BigDecimal(0));
	Integer r_already_qualified_num = Utility.parseInt(Utility.trimNull(file.getParameter("already_qualified_num")),new Integer(0));

	pre_money = Utility.parseDecimal(file.getParameter("pre_money"),new BigDecimal(0.00));

	if(r_already_sale.equals(new BigDecimal(0)))
	{
		r_already_sale = r_rg_money;
	}
	else{
		r_already_sale = r_already_sale.add(r_rg_money);
	}

	//交银：如果是总行导入的数据需要先进入预约表
	if(user_id.intValue()==8){
		String[] pre_flag = Utility.splitString(file.getParameter("pre_serial_no"),"$");

		if(Utility.parseInt(pre_flag[1],0)==1){
			PreContractVO preVO = new PreContractVO();
			PreContractLocal preLocal = EJBFactory.getPreContract();
			preVO.setProduct_id(r_product_id);
//			preVO.setSub_product_id(r_sub_product_id); 
			preVO.setCust_id(r_cust_id);
			preVO.setPre_money(pre_money);
			preVO.setLink_man(input_operatorCode);
			preVO.setPre_type("111201");
			preVO.setSummary("");
			preVO.setPre_num(new Integer(1));
			preVO.setInput_man(input_operatorCode);
			preVO.setPre_date(pre_date);
			preVO.setExp_reg_date(pre_date);
			preVO.setCust_source("111005");
			
			Object[] test = preLocal.append(preVO);
		    r_pre_code = Utility.trimNull(test[0]);

			PreContractVO preVO2 = new PreContractVO();
			preVO2.setProduct_id(r_product_id);
			preVO2.setCust_id(Utility.parseInt(pre_flag[0],new Integer(0)));
			preVO2.setCustomer_id(Utility.trimNull(r_cust_id));
			preVO2.setPre_code(r_pre_code);
			preVO2.setInput_man(input_operatorCode);
			preLocal.updateImportPreContract(preVO2);
		} 
	}


	//保存数据
	ContractVO s_cont_vo = new ContractVO();

	s_cont_vo.setBook_code(input_bookCode);
	s_cont_vo.setProduct_id(r_product_id);
	s_cont_vo.setProduct_name(r_product_name);
	s_cont_vo.setCust_id(r_cust_id);
	s_cont_vo.setPre_code(r_pre_code);
	s_cont_vo.setQs_date(r_qs_date);
	s_cont_vo.setJk_date(r_jk_date);
	s_cont_vo.setRg_money(r_rg_money);
	s_cont_vo.setJk_type(r_jk_type);
	s_cont_vo.setBank_id(r_bank_id);
	s_cont_vo.setBank_sub_name(r_bank_sub_name);
	s_cont_vo.setGain_acct(r_gain_acct);
	s_cont_vo.setFee_jk_type(r_fee_jk_type);
	s_cont_vo.setBank_acct_type(r_bank_acct_type);
	s_cont_vo.setBonus_flag(r_bonus_flag);
	s_cont_vo.setBank_acct(r_bank_acct);
	s_cont_vo.setService_man(r_service_man);
	s_cont_vo.setCheck_man(r_check_man);
	s_cont_vo.setSummary(r_summary);
	s_cont_vo.setInput_man(input_operatorCode);
	s_cont_vo.setCity_serialno(r_city_serialno);
	s_cont_vo.setContract_sub_bh(r_contract_sub_bh);
	s_cont_vo.setContract_bh(r_contract_bh);
	s_cont_vo.setSelf_ben_flag(r_selfbenflag);
	s_cont_vo.setSub_product_id(r_sub_product_id);
	s_cont_vo.setChannel_id(r_channel_id);
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
	s_cont_vo.setIs_ykgl(is_ykgl);
	s_cont_vo.setXthtyjsyl(xthtyjsyl);
	//s_cont_vo.setContact_id(contact_id);
	s_cont_vo.setHt_cust_name(ht_cust_name);
	s_cont_vo.setHt_cust_address(ht_cust_address);
	s_cont_vo.setHt_cust_tel(ht_cust_tel);

	s_cont_vo.setExpect_ror_lower(expect_ror_lower);
	s_cont_vo.setExpect_ror_upper(expect_ror_upper);
	s_cont_vo.setValid_period(valid_period);
	s_cont_vo.setPeriod_unit(period_unit);
	s_cont_vo.setSpot_deal(spot_deal);
	s_cont_vo.setMoney_origin(money_origin);
	s_cont_vo.setSub_money_origin(sub_money_origin);
	s_cont_vo.setContract_type(contract_type);

	Object[] resultSet =contract.append(s_cont_vo);

	serial_no = (Integer)resultSet[0];
	contract_bh = (String)resultSet[1];

	if(file.uploadAttchment(new Integer(5),serial_no,"",null,null,input_operatorCode))
			bSuccess = true;

	//同步团队信息
	salevo.setSerial_NO(r_serialNo);
	salevo.setTeamID(r_team_id);
	salevo.setProductID(r_product_id);
	salevo.setSerial_no_subscribe(new Integer(0));
	salevo.setAlreadysale(r_already_sale);
	salevo.setAlready_qualified_num(r_already_qualified_num);
	sale_parameter.modiAlreadysale(salevo,input_operatorCode);

	bSuccess = true;

	String product_name = Argument.getProductName(r_product_id);
	Argument.addSysMessage(r_cust_id, "录入新认购合同"
		, "录入新认购合同，客户名称："+cust_name+"，产品名称："+product_name, input_operatorCode);

} else {
	//产品信息显示 查询相应预约号列表
	if(product_id.intValue()>0){
		Map map_product = null;

		//取单个产品值
		p_vo.setProduct_id(product_id);
		List rsList_product = product.load(p_vo);

		if(rsList_product.size()>0){
			map_product = (Map)rsList_product.get(0);
		}
		//产品销售渠道信息
		markList = product.queryMarketTrench(p_vo);

		if(map_product!=null){
			open_flag = Utility.parseInt(Utility.trimNull(map_product.get("OPEN_FLAG")),new Integer(0));
			intrust_flag1 = Utility.parseInt(Utility.trimNull(map_product.get("INTRUST_FLAG1")),new Integer(0));
			product_code = Utility.trimNull(map_product.get("PRODUCT_CODE"));
			product_status = Utility.trimNull(map_product.get("PRODUCT_STATUS"));
			period_unit = Utility.parseInt(Utility.trimNull(map_product.get("PERIOD_UNIT")),new Integer(0));//合同期限
			pre_start_date =  Utility.parseInt(Utility.trimNull(map_product.get("PRE_STRAT_DATE")),new Integer(0));
			pre_end_date =  Utility.parseInt(Utility.trimNull(map_product.get("PRE_END_DATE")),new Integer(0));
			Boolean temp_flag = (Boolean)map_product.get("SUB_FLAG");
			String asfund_flag_s = Argument.getProductAsfundFlag(product_id,input_man);
			asfund_flag = Utility.parseInt(Utility.trimNull(asfund_flag_s),new Integer(0));
			valid_period = Argument.getProductValidPeriod(product_id, sub_product_id)[0];//(Integer)map_product.get("VALID_PERIOD");

			if(temp_flag.booleanValue()){
				product_sub_flag = new Integer(1);
			}
			else{
				product_sub_flag = new Integer(2);
			}

			if(period_unit.intValue()>0){
				period_unit_name = Argument.getProductUnitName(period_unit);
			}

			if(product_status.equals("110203")){
				fee_type_name = enfo.crm.tools.LocalUtilis.language("class.sgFeeAmount",clientLocale);//申购费
			}
		}

		//本地 预约列表
		if (preCodeOptions == null || preCodeOptions.equals("")){
			preCodeOptions = Argument.getPreContractOptions(input_bookCode, product_id, sub_product_id, preSerialNo, input_operatorCode,q_cust_name);		
		}
		//总行导入预约列表
		if (preCodeOptions2 == null || preCodeOptions2.equals("")){
			preCodeOptions2 = Argument.getPreContractOptions2(input_bookCode, product_id, preSerialNo, input_operatorCode,q_cust_name);		
		}
	} 
}


Integer citySerialno = new Integer(0);//因新建 设为0
cityNameOptions = Argument.getCitynameOptions(product_id,citySerialno);

String inputBank_acct ="";//空银行账号
if(cust_id!=null && bank_id!=null){
	bankOptions = Argument.getCustBankAcctOptions(cust_id,bank_id,null,inputBank_acct)	;
}

if(bankOptions.equals("<option value=\"\">"+enfo.crm.tools.LocalUtilis.language("message.pleaseSelect",clientLocale)+"</option>")||bankOptions.equals("")){
	inputflag2=2;
}

//客户信息显示
if(cust_id.intValue()>0){
	Map map_cust = null;

	//客户单个值
	cust_vo.setCust_id(cust_id);
	cust_vo.setInput_man(input_operatorCode);
	List rsList_cust = customer.listByControl(cust_vo);

	if(rsList_cust.size()>0){
		map_cust = (Map)rsList_cust.get(0);
		cust_type = Utility.parseInt(Utility.trimNull(map_cust.get("CUST_TYPE")), new Integer(0));
	}
}

//查看用户所在团队信息
team_id = Utility.parseInt(Utility.trimNull(Argument.getTeam(link_man)),new Integer(-1));
salevo.setTeamID(team_id);
salevo.setProductID(product_id);
List saleList = sale_parameter.queryQuota(salevo,input_operatorCode);
Map map_sale = null;

if(saleList.size()>0){
	map_sale = (Map)saleList.get(0);
	serialNo = Utility.parseInt(Utility.trimNull(map_sale.get("SERIAL_NO")), new Integer(0));
	quota_money = Utility.parseDecimal(Utility.trimNull(map_sale.get("QUOTAMONEY")),new BigDecimal(0));
	already_sale = Utility.parseDecimal(Utility.trimNull(map_sale.get("ALREADYSALE")),new BigDecimal(0));
	quota_qualified_num = Utility.parseInt(Utility.trimNull(map_sale.get("QUOTA_QUALIFIED_NUM")),new Integer(0));
	already_qualified_num = Utility.parseInt(Utility.trimNull(map_sale.get("ALREADY_QUALIFIED_NUM")),new Integer(0));
}
%>
<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("menu.addSubscriptionInfo",clientLocale)%>（<%=LocalUtilis.language("message.booked",clientLocale)%> ）</TITLE><!--认购信息新增--><!--已预约-->
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
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
/*保存*/
function SaveAction(){
	if(document.theform.onsubmit()){
		disableAllBtn(true);
		document.theform.action="subscribe_add1.jsp" ;
		document.theform.submit();
	}
}

/*返回*/
function CancelAction(){
	tempArray = '<%=sQuery%>'.split('$');
	location = 'subscribe_list.jsp?page=1&q_productId='+tempArray[0]+'&q_productCode='+tempArray[1]+'&q_cust_name='+tempArray[2]+'&query_contract_bh='+tempArray[3]+'&q_pre_flag='+tempArray[4]+'&pagesize='+tempArray[5]+'&q_check_flag='+tempArray[6];
}

/*下面是新增认购信息时用到的函数*/
function validateForm(form){
	var userid= <%=user_id%>;
	var v = document.theform.pre_serial_no.value.split('$');

	if (! sl_checkChoice(form.contract_type, "合同分类")) return false;

	if(document.theform.contract_sub_bh.value==""){
		alert("合同编号不能为空");
		return false;
	}
	if(DWRUtil.getValue("same_contractBH")==0){
		sl_alert("合同编号已被使用!");
		return false;
	}
	if (document.theform.inputflag.value==2 
			&& !sl_checkNum(form.pre_code, "<%=LocalUtilis.language("class.preCode2",clientLocale)%> ", 16, 1))
		return false; //预约编号

	if(document.theform.inputflag.value==1){
		if (! sl_checkChoice(form.product_id, "<%=LocalUtilis.language("class.productName",clientLocale)%> ")) return false; //产品名称
		if (! sl_checkChoice(form.pre_serial_no, "<%=LocalUtilis.language("class.preCode2",clientLocale)%> ")) return false; //预约编号

		form.pre_date.vlaue = <%=pre_date%>;

		if (v[1]==1 && form.customer_cust_id.value==0) {
			sl_alert("这是总行导入过来的数据,客户需要验证是否存在? \n\n 如果不存在需要新建客户,如果存在要以此系统中客户为准.");
			return false;
		}

		if (form.serial_no.value>0) {
			form.pre_code.value=v[0];
		} else {
			stext=form.pre_serial_no.options[form.pre_serial_no.selectedIndex].text;
			index=stext.indexOf("-");
			svalue=stext.substring(0,index);
			form.pre_code.value=svalue;
		}
	}

<%if(Argument.getSyscontrolValue_intrust("C30902")!=1){%>
	var contract_bh=form.contract_bh;

	if(!sl_check(contract_bh, "<%=LocalUtilis.language("class.constractBH",clientLocale)%> ",20,0))	return false;//合同序号
	if(contract_bh.value!= "" && contract_bh.value != null){
	  var s=contract_bh.value;
	  var len=contract_bh.value.length;
      var a=new Array();

	  if(len<3){
	    sl_alert('<%=LocalUtilis.language("message.inputContractBHTip",clientLocale)%> ');//输入的合同序号至少为3位
	    return false;
	  }else{
	     if((s.charCodeAt(len-1)<48 || s.charCodeAt(len-1)>57) || (s.charCodeAt(len-2)<48 || s.charCodeAt(len-2)>57)|| (s.charCodeAt(len-3)<48 || s.charCodeAt(len-3)>57)){
	        sl_alert("<%=LocalUtilis.language("messgae.contractSerialError",clientLocale)%> ！");//合同序号最后三位必须是数字，请您重新填写！
	        return false;
	     }
	  }
	}
<%}%>
	if(!sl_checkChoice(form.bank_id, '<%=LocalUtilis.language("class.bankName3",clientLocale)%> ')) return false;//银行名称

	if(form.bank_id.value != ""){
		if(document.theform.inputflag2.value==1){
			if(!sl_checkChoice(form.bank_acct2, '<%=LocalUtilis.language("class.bankAcct3",clientLocale)%> '))return false;//银行帐号
		}
		else{
			if(!sl_check(form.bank_acct, '<%=LocalUtilis.language("class.bankAcct3",clientLocale)%> ', 80, 1)) return false;//银行帐号
		}
	}
	if(!sl_checkDecimal(form.rg_money, '<%=LocalUtilis.language("class.rg_money",clientLocale)%> ', 13, 3, 1))return false;//认购金额
	if(!sl_checkChoice(form.jk_type, '<%=LocalUtilis.language("class.feeType",clientLocale)%> '))return false;//缴款方式

	//trim_money(form.pre_money);
	form.pre_money.value = form.pre_money.value.replace(",","");
	strTemp="";

	if(parseFloat(form.pre_money.value)<parseFloat(form.rg_money.value)){
		strTemp="<%=LocalUtilis.language("message.subscriptionAmountError",clientLocale)%> ";//认购金额已大于预约金额
		sl_alert(strTemp);
		return false;
	}

	if(!sl_checkDate(form.qs_date_picker,'<%=LocalUtilis.language("class.qsDate",clientLocale)%> '))return false;//签署日期
	if(!sl_checkDate(form.jk_date_picker,"<%=LocalUtilis.language("class.dzDate",clientLocale)%> "))return false;//缴款日期

	syncDatePicker(form.qs_date_picker, form.qs_date);
	syncDatePicker(form.jk_date_picker, form.jk_date);
	if (userid!=7 && !sl_checkChoice(form.market_trench, '渠道名称 ')) return false; //渠道名称
    if (! sl_checkChoice(form.channel_cooperation, '渠道合作方式 ')) return false;  //渠道合作方式

	form.selfbenflag.value = form.self_ben_flag.checked?'1':'0';

	//if(intrustType_Flag == '1'){
		//document.theform.channel_type.value = comboBoxTree.getValue();
		//document.theform.channel_id.value = comboBoxParentTree.getValue();
	//}

	if (! checkSellInfo()) return false;//检查销售上限

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

	if(form.quota_money.value != 0){
		var sum = parseInt(form.rg_money.value) + <%=already_sale%>;
		if(sum > <%=quota_money%>){
			sl_alert('<%=LocalUtilis.language("message.teamQuota",clientLocale)%>');//用户所在团队在该产品上累计已销售额超过配额值
			return false;
		}
	}
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
	if (userid==22/*中投22*/ && !sl_checkChoice(form.city_serialno, '<%=LocalUtilis.language("class.citySerialNO",clientLocale)%>')) return false;//合同推介地
	if (! sl_checkDecimal(form.expect_ror_lower, '预期收益率下限', 5, 4, 0)) return false; //预期收益率下限
	if (! sl_checkDecimal(form.expect_ror_upper, '预期收益率上限', 5, 4, 0)) return false; //预期收益率上限
	return sl_check_update();
}

/**************************************************************************************************************************/
/*预约号变更*/
function changeInput(obj){
	if(document.theform.inputflag.value==1){
		obj.innerText="<%=LocalUtilis.language("message.choose",clientLocale)%> ";//选择
		document.theform.pre_code.style.display="";
		document.theform.pre_serial_no.style.display="none";
		document.theform.inputflag.value=2;
	}
	else{
		obj.innerText="<%=LocalUtilis.language("message.input",clientLocale)%> ";//输入
		document.theform.pre_code.style.display="none";
		document.theform.pre_serial_no.style.display="";
		document.theform.inputflag.value=1;
	}
}

/*银行账号变更*/
function changeInput2(obj){
	if(document.theform.inputflag2.value==1){
		obj.innerText="<%=LocalUtilis.language("message.choose",clientLocale)%> ";//选择
		document.theform.bank_acct.style.display="";
		document.theform.bank_acct2.style.display="none";
		document.theform.inputflag2.value=2;
	}
	else {
		obj.innerText="<%=LocalUtilis.language("message.input",clientLocale)%> ";//输入
		document.theform.bank_acct.style.display="none";
		document.theform.bank_acct2.style.display="";
		document.theform.inputflag2.value=1;
	}
}

/*显示账号位数*/
function showAcctNum(value){
	if (trim(value) == ""){
		bank_acct_num.innerText = "";
	} else {
		bank_acct_num.innerText = "(" + showLength(value) + " 位 )";
	}
}

/*设置产品编号*/
function setProduct(value){
	var prodid=0;

	if (event.keyCode == 13 && value != ""){
        var j = value.length;

		for(i=0;i<document.theform.product_id.options.length;i++){
			if(document.theform.product_id.options[i].text.substring(0,j)==value){
				document.theform.product_id.options[i].selected=true;
				prodid = document.theform.product_id.options[i].value;
				break;
			}
		}

		if (prodid!=0) 
			selectPrecodeItem();
		else {
            //输入的产品编号不存在或者该产品不在推介期
			sl_alert('<%=LocalUtilis.language("message.inputProdIDError",clientLocale)%> ！');
			document.theform.productid.value="";
			document.theform.product_id.options[0].selected=true;
		}
	}

	nextKeyPress(this);
}

/*设定预约号*/
function selectPrecodeItem(preSerialNo){
	if(typeof(preSerialNo)=='undefined') return;
	var v = preSerialNo.split('$');

	if(v[0]>0){ <%-- intrust..tprecontract.serial_no --%>
		contract.getPreContractInfo(preSerialNo,
			{ callback: 
				function(data){
					if (document.theform.sub_product_id) {
					    var opts = document.theform.sub_product_id.options;
						for (var i=0; i<opts.length; i++)
							if (opts[i].value==data[5])
								document.theform.sub_product_id.selectedIndex = i;
					}
					document.theform.cust_no.value = data[2];
					document.theform.cust_name.value = data[1];
					document.theform.pre_money.value = data[4];
					document.theform.rg_money.value = data[4];
					document.theform.gain_acct.value = data[1];
					document.theform.customer_cust_id.value = data[0];		
		
					document.theform.pre_date.value = data[3];	
					showCustOptions(data[0]);		
		
					checkSellInfo(); //
				}
			});
	}

}

/*根据客户名称查找*/
function selectCustName(value){
	var preSerialNo=0;

	if(value!=""&&event.keyCode == 13){
		 var j = value.length;
		 var len = document.theform.pre_serial_no.options.length;

		 for(i=0;i<len;i++){
		 	var text = document.theform.pre_serial_no.options[i].innerText;
		 	var tempArray = text.split('-');

			if(trim(tempArray[1])==value){
				document.theform.pre_serial_no.options[i].selected=true;
				preSerialNo = document.theform.pre_serial_no.options[i].value;
				document.theform.cust_name.value = text.substring[4,10];
				selectPrecodeItem(preSerialNo);
				break;
			}
		 }

		if (preSerialNo==0){
			sl_alert('<%=LocalUtilis.language("message.custNameTip",clientLocale)%> ！');//输入的客户名称不存在
			document.theform.cust_name.value="";
			document.theform.pre_serial_no.options[0].selected=true;
		}
	}
}

/*手工输入预约号*/
function findPreNo(obj){
	var i=0;
	var values=obj.value;
	var count=0;
	var len = document.theform.pre_serial_no.options.length;

	if (! sl_checkNum(obj,"<%=LocalUtilis.language("class.preCode2",clientLocale)%> ",16,0)) return false; //预约编号

	if (event.keyCode == 13){
		for(;i<len;i++){
			var stext=document.theform.pre_serial_no.options[i].innerText;
			var tempArray = stext.split('-');

			if(trim(tempArray[0])==trim(values)){
				count=1;
				document.theform.pre_serial_no.options[i].selected=true;
				var preSerialNo = document.theform.pre_serial_no.options[i].value;
				selectPrecodeItem(preSerialNo);
				break;
			}
		}

		if(count==0){
			sl_alert("<%=LocalUtilis.language("message.bookingNumTip",clientLocale)%> ！");//预约编号不存在
			obj.focus();
			return false;
		}
	}
	else{
		nextKeyPress(obj);
	}
}

/*设置产品及客户信息*/
function selectProductAndCustomer(){
	document.theform.flash_flag.value="2";
	document.theform.submit();
	//location.href = "subscribe_add1.jsp?page=<%=sPage%>" + getLastOptions();
}

/**************************************************************************************************************************/
/*验证合同编号*/
function check(){
	$("contractDIV").innerHTML = "";
	if(!sl_check(document.theform.contract_sub_bh, "<%=LocalUtilis.language("class.contractID",clientLocale)%> "))return false; //合同编号

	var book_code=DWRUtil.getValue("book_code");
	var contract_type=new Number(1);
	var product_id=DWRUtil.getValue("product_id");
	var contract_sub_prefix = $("contract_sub_bh_prefix").innerHTML;
	var contract_sub =DWRUtil.getValue("contract_sub_bh");
	if(product_id==0){
		sl_alert("<%=LocalUtilis.language("message.chooseProdTip",clientLocale)%> !");//请先选择产品
		return false;
	}
	contract.findIfExistSameBH(book_code,contract_type,product_id,contract_sub_prefix+contract_sub,callContractSubBHCallBack);
	contract.getSameBHContractInfo(contract_type,product_id,contract_sub_prefix+contract_sub,callSameBHContractInfoBack);
}

/*结果返回*/
function callContractSubBHCallBack(result){
	if (result!="") $("contractDIV").innerHTML = result;
}

function callSameBHContractInfoBack(jaso){
	var flag = jaso;
	if(flag!=""){
		alert(flag);
		$("same_contractBH").value=0;
	}else{
		$("same_contractBH").value=1;
	}
}

/**************************************************************************************************************************/

/*拼后缀参数*/
function getLastOptions(){
	if(!sl_checkDate(document.theform.qs_date_picker,"<%=LocalUtilis.language("class.qsDate",clientLocale)%> ")){return false};//签署日期
	syncDatePicker(document.theform.qs_date_picker, document.theform.qs_date);

	var temp="";
	temp += "&product_id="+ document.theform.product_id.value;
	temp += "&jk_type=" + document.theform.jk_type.value;
	temp += "&qs_date=" + document.theform.qs_date.value ;
	temp += "&contract_bh=" + document.theform.contract_bh.value ;
	temp += "&gain_acct="+ document.theform.gain_acct.value ;
	temp += "&sQuery=" + document.theform.sQuery.value ;
	temp += "&contract_sub_bh=" + document.theform.contract_sub_bh.value ;
	temp += "&cust_name=" + document.theform.cust_name.value ;

<%if (serial_no!= null) {%>
	temp += "&link_man=" + document.theform.link_man.value;
<%}%>

<%if (product_sub_flag.intValue() == 1) {%>
	temp += "&sub_product_id=" + document.theform.sub_product_id.value;
<%}%>

<%if(product_id!=null && product_id.intValue()!=0 && open_flag.intValue()==1){ // 开放式%>
	temp += "&bonus_flag="+document.theform.bonus_flag.value
<%}%>

<%if(intrust_flag1.intValue()!=1){%>
	temp += "&prov_flag="+document.theform.prov_flag.value
<%}%>
<%if(intrust_flag1.intValue()!=1&&asfund_flag!=null&&asfund_flag.intValue()==3){%>
	temp += "&prov_level="+document.theform.prov_level.value
<%}%>
<%if(open_flag!=null && open_flag.intValue()==1){%>
	temp += "&bonus_flag="+document.theform.bonus_flag.value
<%}%>
	temp += "&pre_date=" + document.theform.pre_date.value ;
	temp += "&customer_cust_id=" + document.theform.customer_cust_id.value ;
	return temp;
}

/**************************************************************************************************************************/
/*获得销售限制*/
function getSellInfo(){
	if(document.theform.product_id.value>0){
		contract.getSaleInfo('<%=input_bookCode%>',document.theform.product_id.value,{callback: function(data){
			document.theform.org_bal.value = data[0];
			document.theform.contact_num.value = data[1];
			document.theform.current_num.value = data[2];
		}});
	}
}

/*销售限制检查*/
function checkSellInfo(){
	var rg_money = document.theform.rg_money.value;
	var org_bal = document.theform.org_bal.value;
	var current_num = document.theform.current_num.value;
	var contact_num = document.theform.contact_num.value;
	var org_bal_show = org_bal/10000;
	var cust_no = document.theform.cust_no.value;

	if(cust_no.indexOf("J")!=-1&&document.theform.product_id.value>0&&rg_money<=org_bal){
		if(current_num>=contact_num){
			sl_alert(org_bal_show+"万元以下该产品合同份数已达"+current_num+"!");
			return false;
		}
	}
	var channel_rate = document.theform.channel_rate.value
	var rg_money = document.theform.rg_money.value;
	if(rg_money != "" && channel_rate != "")
		document.theform.market_trench_money.value = (Number(rg_money) * Number(channel_rate)).toFixed(2);
	else
		document.theform.market_trench_money.value = "";

	var product_id = DWRUtil.getValue("product_id");
	var sub_product_id;
	if (document.theform.sub_product_id == null)
		sub_product_id = 0;
	else
		sub_product_id = document.theform.sub_product_id.value;
	//alert("\""+sub_product_id+"\"");
	//alert(product_id + " " + sub_product_id);
	if (sub_product_id=="")	sub_product_id = 0;

	utilityService.getSubProductProvFlag2(product_id, sub_product_id, autoSetGainLevel);

	return true;
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
		var gain_rate = json[i].GAIN_RATE*100;
		document.theform.expect_ror_lower.value = Math.round(gain_rate * 100) / 100;
		document.theform.expect_ror_upper.value = document.theform.expect_ror_lower.value;
	} else {
		document.theform.expect_ror_lower.value = "";
		document.theform.expect_ror_upper.value = document.theform.expect_ror_lower.value;
	}
}

/**************************************************************************************************************************/
/*初始化*/
window.onload = function(){
		var v_bSuccess = document.getElementById("bSuccess").value;
		var v_selfbenflag = document.getElementById("selfbenflag").value;
		var v_serial_no = document.getElementById("serial_no").value;
		var v_contract_bh = document.getElementById("contract_bh").value;
		<%if (flash_flag==2){%>selectPrecodeItem("<%=preSerialNo%>");<%}%>
	
		if(v_bSuccess=="true"){
			if(v_selfbenflag==0){
	           //认购成功 //合同序号 //为//合同为他益信托，需要进行该合同受益人维护吗
			  if (confirm("<%=LocalUtilis.language("message.subscriptionSucc",clientLocale)%>！<%=LocalUtilis.language("class.constractBH",clientLocale)%><%=LocalUtilis.language("message.is",clientLocale)%>:"+v_contract_bh+"，<%=LocalUtilis.language("message.contractMaintenanceTip",clientLocale)%> ？")){
			  	location = 'benifiter_list.jsp?from_flag_benifitor=1&contract_id='+v_serial_no+'&page=1&sQuery=<%=sQuery%>';
			  }
			}
			sl_alert("<%=LocalUtilis.language("message.subscriptionSucc",clientLocale)%> ");//认购成功
			CancelAction();//返回认购列表
		} else {
			getSellInfo();
		
			if (document.theform.rg_money.value!="") {
				checkSellInfo();
			}
		}
	};

function openSubProduct(){
	var product_id = document.getElementsByName("product_id")[0].value;
	var sub_product_id = document.getElementsByName("sub_product_id")[0].value;

	if(product_id!=null&&product_id!=0&&sub_product_id!=null&&sub_product_id!=0){
		var url = "<%=request.getContextPath()%>/marketing/base/sub_product_view.jsp?product_id="+product_id+"&sub_product_id="+sub_product_id;
		showModalDialog(url, '', 'dialogWidth:600px;dialogHeight:420px;status:0;help:0');
	}
	else{
		sl_alert("请选择产品！");
	}
}

function selectBank(value) {
<%if(serial_no !=null){%>
	var v = document.theform.pre_serial_no.value.split('$');
	document.theform.pre_serial_no1.value = document.theform.pre_serial_no.value;
	document.theform.flash_flag.value="2";
	document.theform.submit();
<%}else{%>
	document.theform.pre_serial_no1.value = document.theform.pre_serial_no.value;
	if (document.theform.pre_serial_no.value!="") {
		document.theform.flash_flag.value="2";
		document.theform.submit();
	}	
<%}%>
}

var n=1;
/*
 *添加附件
 */
function addline() {
	n++;
	newline=document.all.test.insertRow()
	newline.id="fileRow"+n;
	newline.insertCell().innerHTML="<input type='file' name=file_info"+n+" size='60'>"+"<button type='button' class='xpbutton3'  onclick='javascript:removeline(this)'><%=LocalUtilis.language("messgae.remove",clientLocale)%> </button>";//移除
}


/*
 *删除附件
 */
function removeline(obj) {
	var objSourceRow=obj.parentNode.parentNode;
	var objTable=obj.parentNode.parentNode.parentNode.parentNode;
	objTable.lastChild.removeChild(objSourceRow);
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

function showSylb(sub_product_id){
	if(sub_product_id==""){sub_product_id = 0;}
	utilityService.getSubPSylb(sub_product_id,showSylbCallBack);
	<%if(intrust_flag1.intValue()!=1){%>
	utilityService.getSubProductProvFlag(document.theform.product_id.value,sub_product_id,document.theform.prov_flag.value,showProvFlag);
<%}%>
}

/*查看团队信息*/
function selectTeam(value){
	if (value!=""){
		location = 'subscribe_add1.jsp?page=<%=sPage%>&link_man=' + value+ getLastOptions();
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

//认购合同联系人
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
	var cust_id = document.theform.customer_cust_id.value;
	var url = "<%=request.getContextPath()%>/client/clientinfo/client_contact_new.jsp?cust_id="+cust_id;
	if(obj=="0"){		
		if(showModalDialog(url,'','dialogWidth:900px;dialogHeight:420px;status:0;help:0;scroll:0')){
			sl_update_ok();
			showCustOptions(cust_id);
		}
	}
}

//新建产品渠道
function newChannel(){
	if(DWRUtil.getValue("product_id")==0){
		alert("请先选择产品");return false;
	}else if(DWRUtil.getValue("product_sub_flag")==1&&DWRUtil.getValue("sub_product_id")==0){
		alert("请先选择子产品");return false;
	}else{
		if(showModalDialog('/marketing/base/product_market_trench_add.jsp?productId='+ DWRUtil.getValue("product_id") +'&sub_flag='+DWRUtil.getValue("product_sub_flag") +'&productName='+$("product_id").options[$("product_id").selectedIndex].text+'&sub_product_id='+ DWRUtil.getValue("sub_product_id"), '', 'dialogWidth:400px;dialogHeight:400px;status:0;help:0') != null){
		sl_update_ok();
		//产品销售渠道信息
		utilityService.queryMarketTrench(DWRUtil.getValue("product_id"),DWRUtil.getValue("sub_product_id"),matchCustCallBack);
		}
	}
}

//渠道名称
function matchCustCallBack(data){
	var obj_op = document.getElementById("market_trench");
    DWRUtil.removeAllOptions(obj_op);
    DWRUtil.addOptions(obj_op,{'':'<%=LocalUtilis.language("message.select2",clientLocale)%> '});   //请选择
    DWRUtil.addOptions(obj_op,data);
}

function getGainRate() {
	var product_id = DWRUtil.getValue("product_id");
	var sub_product_id;
	if (document.theform.sub_product_id == null)
		sub_product_id = 0;
	else
		sub_product_id = document.theform.sub_product_id.value;
	//alert("\""+sub_product_id+"\"");
	//alert(product_id + " " + sub_product_id);
	if (sub_product_id=="")	sub_product_id = 0;

	utilityService.getSubProductProvFlag2(product_id, sub_product_id, autoSetGainLevel2);
}

function autoSetGainLevel2(data) {
	var prov_flag = document.theform.prov_flag.value;
	var prov_level = document.theform.prov_level.value;

	var json = eval(data);	
	var i;
	for (i=0; i<json.length; i++) {
		if (prov_flag==json[i].PROV_FLAG && prov_level==json[i].PROV_LEVEL) {	
			var gain_rate = json[i].GAIN_RATE*100;
			document.theform.expect_ror_lower.value = Math.round(gain_rate * 100) / 100;
			document.theform.expect_ror_upper.value = document.theform.expect_ror_lower.value;		
			break;
		}
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

function readCard(btn) {
	/*var to_pay_money = parseFloat(document.theform.rg_money.value);
	//alert(to_pay_money+" "+isNaN(to_pay_money)); // NaN
	if (!isNaN(to_pay_money) && to_pay_money!=0) {
		btn.disabled = true;*/
		/*alert($.ajax);
		$.ajax({
		  type: 'POST',
		  url: "pos_read_card.jsp",
		  data: {
				to_pay_money: to_pay_money
		  },
		  success: function() {
			
		  }
		});*/
		/*utilityService.readPosCard(<%=input_operatorCode%>, to_pay_money
			, function(bank_acct) {
				//alert(bank_acct);
				if (bank_acct!="" && bank_acct!="E*") {
					if (document.theform.bank_acct.style.display=="none") 
						changeInput2(document.theform.btnSelect);
						
					document.theform.bank_acct.value = bank_acct;
				}
			});
		sl_alert("服务器端已就绪，请刷卡！");	

	} else {
		sl_alert("请先填写有效的认购金额！");
		document.theform.rg_money.focus();
		document.theform.rg_money.select();
	}*/

	var rg_money = parseFloat(document.theform.rg_money.value);
	//alert(rg_money+" "+isNaN(rg_money)); // NaN
	if (!isNaN(rg_money) && rg_money>0) {
		var obj = showModalDialog('read_pos_card.jsp?rg_money='+rg_money, ''
					,'dialogWidth:470px;dialogHeight:150px;status:0;help:0');
		if (obj.card_no!="") {
			if (document.theform.bank_acct.style.display=="none") 
				changeInput2(document.theform.btnSelect);
							
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
</script>
</HEAD>
<BODY class="BODY body-nox">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="post" onsubmit="javascript:return validateForm(this);" ENCTYPE="multipart/form-data">

<input type="hidden" id="flash_flag" name="flash_flag" value=""/><!--是否刷新标识 -->
<input type="hidden" id="bSuccess" value="<%=bSuccess%>"/><!--新增成功标志-->
<input type="hidden" name="sQuery" value="<%=sQuery%>"/>

<input type="hidden" name="book_code" value="<%=input_bookCode%>"/>
<input type="hidden" name="customer_cust_id" value="<%=cust_id%>"/><!--客户ID标志-->
<input type="hidden" name="cust_type" value="<%=cust_type%>"/>
<input type="hidden" name="serial_no" id="serial_no" value="<%=serial_no%>">
<input type="hidden" name="pre_serial_no1" value=""><!--预约号临时保存-->
<input type="hidden" id="t_contract_bh" name="t_contract_bh" value="<%=contract_bh%>"/>
<input type="hidden" id="product_sub_flag" name="product_sub_flag" value="<%=product_sub_flag%>"/>

<input type="hidden" name="inputflag" value="<%=inputflag%>"/><!--预约号标志-->
<input type="hidden" name="inputflag2" value="<%=inputflag2%>"/><!--银行账号标志-->
<input type="hidden" name="check_flag" value="<%=check_flag%>"/>
<input type="hidden" name="intrust_flag1" value="<%=intrust_flag1%>"/>
<input type="hidden" id="selfbenflag" name="selfbenflag" value="<%=selfbenflag%>"/>
<input type="hidden" name="same_contractBH" value="0">

<%if (user_id.intValue()!=2/*2北国投*/ && user_id.intValue()!=999) { %>
<input type="hidden" name="period_unit" value="<%=period_unit%>"/>
<%} %>
<input type="hidden" name="pre_start_date" value="<%=pre_start_date%>"/>
<input type="hidden" name="pre_end_date" value="<%=pre_end_date%>"/>
<!-- 团队配额信息 -->
<input type="hidden" name="quota_money" value="<%=quota_money%>">
<input type="hidden" name="already_sale" value="<%=already_sale%>">
<input type="hidden" name="team_id" value="<%=team_id%>">
<input type="hidden" name="serialNo" value="<%=serialNo%>">
<input type="hidden" name="quota_qualified_num" value="<%=quota_qualified_num%>">
<input type="hidden" name="already_qualified_num" value="<%=already_qualified_num%>">
<input type="hidden" name="pre_date" value="<%=pre_date%>"><!-- 导入预约信息的 预约时间 -->
<input type="hidden" name="org_bal" value=""><!-- 300W 金额 -->
<input type="hidden" name="contact_num" value=""><!-- 合同上限 -->
<input type="hidden" name="current_num" value=""><!-- 已有合同 -->

<input type="hidden" id="channel_type" name="channel_type" value="<%=channel_type%>"/>
<input type="hidden" id="channel_id" name="channel_id" value="<%=r_channel_id%>"/>
<input type="hidden" id="channel_rate" name="channel_rate" value=""/>
<div align="left" class="page-title">
	<font color="#215dc6"><b><%=menu_info%></b></font>
</div>

<div>
	<table  border="0" width="100%" cellspacing="0" cellpadding="2" class="product-list">
		<tr>
			<td  align="right" width="120px">&nbsp;&nbsp;&nbsp;&nbsp;<%=LocalUtilis.language("class.productID",clientLocale)%> :&nbsp;&nbsp;</td><!--产品编号-->
			<td  align="left">
				<input type="text" name="productcode" value="<%=product_code%>" onkeydown="javascript:setProduct(this.value);" size="20">
			</td>
		</tr>
		<tr>
			<td align="right" width="120px"><font color="red"><b>*</b></font><%=LocalUtilis.language("class.productNumber",clientLocale)%> :&nbsp;&nbsp;</td><!--产品选择-->
			<td>
				<select size="1" id="product_id" name="product_id" onkeydown="javascript:nextKeyPress(this)" onchange="javascript:selectProductAndCustomer();" class="productname">
					<option><%=LocalUtilis.language("message.pleaseSelect",clientLocale)%> </option><!--请选择-->
					<%=Argument.getCRMProductListOptions(new Integer(2),product_id,"110202",new Integer(0),input_operatorCode)%>
				</select>
				<!--<button type="button"  name="show_info" title="查看产品信息" onclick="javascript:openQuery('<!%=product_id%>','<!%=item_id%>');"<!%if(product_id.intValue()!=0){out.print("style=display:'';");}else{out.print("style=display:'none';");}%>>查看</button>-->
			</td>
		</tr>
	<%if(product_sub_flag.intValue() == 1){%>
		<tr>
			<td align="right" width="120px"><b><font color="red"><b>*</b></font><%=LocalUtilis.language("class.subProductID",clientLocale)%> </b>:&nbsp;&nbsp;</td><!--子产品选择-->
			<td align=left colspan=3>
				<select size="1" id="sub_product_id" name="sub_product_id" onkeydown="javascript:nextKeyPress(this)" class="productname" onchange="javascript:selectProductAndCustomer()">
					<%=Argument.getSubProductOptions(product_id,new Integer(0),sub_product_id)%>
				</select>&nbsp;&nbsp;
				<button type="button"  class="xpbutton2" name="show_sub_info" title="查看子产品信息" onclick="javascript:openSubProduct();"><%=LocalUtilis.language("message.view",clientLocale)%></button><!-- 查看 -->
			</td>
		</tr>
	<%}%>
	</table>
	<br/>
	<table  border="0" width="100%" cellspacing="0" cellpadding="3" class="product-list">
		<TR>
			<td align="right" width="120px"><%=LocalUtilis.language("class.customerID",clientLocale)%> :&nbsp;&nbsp;</td><!--客户编号-->
			<td><input readonly class="edline" name="cust_no" size="20" onkeydown="javascript:nextKeyPress(this)" value=""/></td>
		</TR>
		<TR>
			<td align="right" width="120px"><%=LocalUtilis.language("class.customerName",clientLocale)%> :&nbsp;&nbsp;</td><!--客户名称-->
			<!--  onkeydown="javascript:selectCustName(this.value);" -->
			<td colspan=3>
				<input name="cust_name" size="50" value="<%=cust_name%>"/>&nbsp;&nbsp;
				<button type="button"  class="xpbutton5" accessKey=E name="btnEdit" title="<%=LocalUtilis.language("menu.customerInformation",clientLocale)%> " onclick="javascript:selectProductAndCustomer();">查询预约编号 </button>
			</td>
		</TR>

		<tr>
			<td align="right" width="120px"><%=LocalUtilis.language("class.preCode2",clientLocale)%> :&nbsp;&nbsp;</td><!--预约编号-->
			<td>
				<input type="text" name="pre_code" onkeydown="javascript:findPreNo(this)"
					style="display:<%=inputflag==1?"none":""%>"/>
				<select name="pre_serial_no" size="1" onchange="javascript:selectPrecodeItem(this.value)" 
					style="WIDTH:200px;display:<%=inputflag==2?"none":""%>"	onkeydown="javascript:nextKeyPress(this)">
					<%=preCodeOptions%>
					<%=preCodeOptions2%>
				</select>&nbsp;&nbsp;
				<button type="button"  class="xpbutton2" accessKey=x name="btnPrint" 
					title='<%=LocalUtilis.language("message.input",clientLocale)%> ' onclick="javascript:changeInput(this);">
				<!-- 输入 --><!-- 选择 -->
				<%if(inputflag==1) out.print(enfo.crm.tools.LocalUtilis.language("message.input",clientLocale));else out.print(enfo.crm.tools.LocalUtilis.language("message.choose",clientLocale));%>(<u>X</u>)</button>
			</td>
		</tr>
		<tr>
			<td align="right" width="120px"><%=LocalUtilis.language("class.factPreNum2",clientLocale)%> :&nbsp;&nbsp;</td><!--预约金额-->
			<td>
				<input readonly class="edline" name="pre_money" size="20" onkeydown="javascript:nextKeyPress(this)" value="<%=Utility.trimNull(pre_money)%>">
			</td>
		</tr>
	</table>
	<br/>
	<table border="0" width="100%" cellspacing="0" cellpadding="2" class="product-list">
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
            <td align="right" width="120px" <%if(Argument.getSyscontrolValue_intrust("C30902")==1){out.print("style='display:none'");}%>>
            	<%=LocalUtilis.language("class.constractBH",clientLocale)%> :&nbsp;&nbsp;
            </td>
			<td <%if(Argument.getSyscontrolValue_intrust("C30902")==1){out.print("style='display:none'");}%>>
				<input name="contract_bh" id="contract_bh" size="20" maxlength=20 value="<%=contract_bh%>" onkeydown="javascript:nextKeyPress(this)" <%if(Argument.getSyscontrolValue_intrust("C30902")==1){out.print("tabindex='-1'");}%>>
			</td>
<%
String contractSubBhPrefix = Argument.getContractSubBhPrefix(product_id);
if (contractSubBhPrefix.endsWith("号"))
	contractSubBhPrefix = contractSubBhPrefix.substring(0, contractSubBhPrefix.length()-1);
 %>
			<TD align="right" width="120px"><font color="red"><b>*</b></font><%=LocalUtilis.language("class.contractID",clientLocale)%> :&nbsp;&nbsp;</TD><!--合同编号-->
			<TD><span id="contract_sub_bh_prefix"><%=contractSubBhPrefix%></span>
				<INPUT name="contract_sub_bh" onkeydown=javascript:nextKeyPress(this) maxLength="40" size="40"  onblur="javascript:check();"
					value="<%=Utility.trimNull(contract_sub_bh) %>">&nbsp;&nbsp;
				<INPUT type="button" class="xpbutton3" onclick="javascript:check();" 
					value="<%=LocalUtilis.language("messgae.CheckBH",clientLocale)%> "><!--验证编号-->
				<div id="contractDIV"></div>
			</TD>
		</tr>
<%if (user_id.intValue()==2 /*2北国投*/|| user_id.intValue()==999) { %>
		<tr>
			<td align="right">合同期限 :&nbsp;&nbsp;</td>
			<td>
				<input type="text" name="valid_period" size="5" value="<%=Utility.trimNull(valid_period)%>"> 
				<select name="period_unit" id="period_unit"><%=Argument.getPeriodValidUnitOptions(period_unit)%></select>
			</td>
		</tr>
<%} %>
		<tr>
			<td align="right" width="120px"><font color="red"><b>*</b></font><%=LocalUtilis.language("class.bankName3",clientLocale)%> :&nbsp;&nbsp;</td><!--银行名称-->
			<td >
				<select size="1" name="bank_id" onchange="javascript:selectBank(this.value);" style="WIDTH: 120px">
					<%=Argument.getBankOptions(bank_id)%>
				</select>
				<input name="bank_sub_name" size="20" value="" onkeydown="javascript:nextKeyPress(this)">
			</td>
			<td align="right" width="120px"><font color="red"><b>*</b></font><%=LocalUtilis.language("class.bankAcct3",clientLocale)%> :&nbsp;&nbsp;</td><!--银行帐号-->
			<td>
				<input type="text" name="bank_acct" style="display:<%=inputflag2==1?"none":""%>" value="<%=Utility.trimNull(bank_acct)%>" 
					onkeydown="javascript:nextKeyPress(this)" onkeyup="javascript:showAcctNum(this.value)" size="30"/>
				<select name="bank_acct2" size="1" style="display:<%=inputflag2==2?"none":""%>"
						onkeydown="javascript:nextKeyPress(this)" style="WIDTH:200px">
					<%=bankOptions%>
				</select>&nbsp;&nbsp;
			<%if(inputflag2==1){%>
				<!-- 输入 -->
                <button type="button"  class="xpbutton2" accessKey=x name="btnSelect" title='<%=LocalUtilis.language("message.input",clientLocale)%>' 
					onclick="javascript:changeInput2(this);"><%=LocalUtilis.language("message.input",clientLocale)%> (<u>X</u>)</button>
			<%}%>
				<span id="bank_acct_num" class="span"></span>
			</td>
		</tr>

		<!-- 反洗钱 添加-->
		<tr>
			<td align="right" width="120px"><%=LocalUtilis.language("class.bankAccountType",clientLocale)%> :&nbsp;&nbsp;</td><!--银行账号类型-->
			<td>
				<select size="1" name="bank_acct_type" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 250px">
						<%=Argument.getBankAcctType(bank_acct_type)%>
				</select>
			</td>
			<td align="right" width="120px"><%=LocalUtilis.language("class.customerGainAcct",clientLocale)%> :&nbsp;&nbsp;</td><!--银行帐号户名-->
			<td ><input name="gain_acct" size="20"   onkeydown="javascript:nextKeyPress(this)" value="<%=Utility.trimNull(gain_acct)%>"></td>
		</tr>
		<tr>
			<td align="right" width="120px"><font color="red"><b>*</b></font><%=LocalUtilis.language("class.feeType",clientLocale)%> :&nbsp;&nbsp;</td><!--缴款方式-->
			<td><select size="1" name="jk_type" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px">
				<%=Argument.getJkTypeOptions("111401")%>
			</select></td>
			<td align="right" width="120px"><%if(open_flag.intValue()==1){%><%=LocalUtilis.language("class.bonusFlag",clientLocale)%> :<%}%></td><!--收益分配方式-->
			<td><%if(open_flag!=null && open_flag.intValue()==1){%>
				<select size="1" name="bonus_flag" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px">
					<%=Argument.getBonus_flag(bonus_flag)%>
				</select>
				<span id="bonus_rate_div" <%if(Utility.parseInt(Utility.trimNull(bonus_flag),0)!=3) out.print("style='display:none'");%>>转份额比例:
					<input type="text" name="bonus_rate" size="10" onkeydown="javascript:nextKeyPress(this)"
						value="<%=(Utility.parseDecimal(Utility.trimNull(bonus_rate),new BigDecimal(0))).multiply(new BigDecimal(100)).setScale(2)%>">%
				</span>
				<%}%>
			</td>
		</tr>

		<tr>
			<td align="right" width="120px"><font color="red"><b>*</b></font><%=LocalUtilis.language("class.rg_money",clientLocale)%> :&nbsp;&nbsp;</td><!--认购金额-->
			<td>
				<input name="rg_money" size="20" value="<%=Utility.trimNull(r_rg_money)%>" onkeydown="javascript:nextKeyPress(this)" 
					onkeyup="javascript:showCnMoney(this.value,rg_money_cn)" onblur="javascript:checkSellInfo();">
			<%if (user_id.intValue()==24/*厦门24*/) { %>
				&nbsp;&nbsp;<button type="button"  class="xpbutton2" onclick="javascript:readCard(this);">刷卡</button>
			<%} %>
				<span id="rg_money_cn" class="span"></span>
			</td>
			<td align="right" width="120px"><%if(open_flag.intValue()==1){%><%=fee_type_name%><%=LocalUtilis.language("class.feeTypeName",clientLocale)%> :<%}%></td><!--扣缴方式-->
			<td >
			<%if(open_flag.intValue()==1){%>
				<select size="1" name="fee_jk_type" onkeydown="javascript:nextKeyPress(this)" 
					style="WIDTH:120px" onblur="javascript:checkSellInfo();">
					<%=Argument.getFeeJkTypeOptions(fee_jk_type.intValue())%>
				</select>
			<%}%>
			</td>
		</tr>
		<tr>
		<td align="right">渠道名称 :&nbsp;&nbsp;</td>
		<td>
			<select id="market_trench" name="market_trench" style="width:280px" onchange="javascript:setMartetTrench(this);">
				<option value="">请选择</option>
				<%if(markList != null && markList.size() != 0){
				for(int u=0;u<markList.size();u++){
					markMap = (Map)markList.get(u);
				%>
				<option value="<%=Utility.trimNull(markMap.get("CHANNEL_TYPE")) %>@<%=Utility.trimNull(markMap.get("CHANNEL_ID")) %>@<%=Utility.trimNull(Utility.parseDecimal(Utility.trimNull(markMap.get("CHANNEL_FARE_RATE")), new BigDecimal(0),4,"1")) %>"><%=Utility.trimNull(markMap.get("CHANNEL_TYPE_NAME")) %>-<%=Utility.trimNull(markMap.get("CHANNEL_NAME")) %>[费率：<%=Utility.trimNull(Utility.parseDecimal(Utility.trimNull(markMap.get("CHANNEL_FARE_RATE")), new BigDecimal(0),4,"100")) %>]</option>
				<%}} %>
			</select>&nbsp;&nbsp;
			<button type="button"  class="xpbutton2" id="newChannel1" name="newChannel1" onclick="javascript:newChannel();">新建</button>
		</td>
		<td align="right">渠道费用 :&nbsp;&nbsp;</td>
		<td><input name="market_trench_money" size="20" value="" onkeydown="javascript:nextKeyPress(this)" onkeyup="javascript:showCnMoney(this.value,market_trench_money_cn)"><span id="market_trench_money_cn" class="span"></span></td>
	</tr>
		<tr>
			<td align="right" width="120px"><font color="red"><b>*</b></font><%=LocalUtilis.language("class.qsDate",clientLocale)%> :&nbsp;&nbsp;</td><!--签署日期-->
			<td>
				<INPUT TYPE="text" NAME="qs_date_picker" class=selecttext
				<%if(qs_date.intValue()==0){%> value="<%=Format.formatDateLine(Utility.getCurrentDate())%>"
				<%}else{%>value="<%=Format.formatDateLine(qs_date)%>"<%}%> >
				<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.qs_date_picker,theform.qs_date_picker.value,this);" tabindex="13">
				<INPUT TYPE="hidden" NAME="qs_date"   value="">
			</td>
		</tr>

		<tr>
			<td align="right" width="120px"><font color="red"><b>*</b></font><%=LocalUtilis.language("class.dzDate",clientLocale)%> :&nbsp;&nbsp;</td><!--缴款日期-->
			<td>
				<INPUT TYPE="text" NAME="jk_date_picker" class=selecttext
				<%if(jk_date.intValue()==0){%> value="<%=Format.formatDateLine(Utility.getCurrentDate())%>"
				<%}else{%>value="<%=Format.formatDateLine(jk_date)%>"<%}%> >
				<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.jk_date_picker,theform.jk_date_picker.value,this);" tabindex="14">
				<INPUT TYPE="hidden" NAME="jk_date" value="<%=jk_date%>">
			</td>
			<td align="right" width="120px"><%if (serial_no == null){%> <%=LocalUtilis.language("class.selfBenFlag",clientLocale)%> :&nbsp;&nbsp;<%}%></td><!--自益标志-->
			<td><%if (serial_no == null){%><input name="self_ben_flag" class="flatcheckbox" type="checkbox" value="<%=selfbenflag%>" <%if(selfbenflag==1) out.print("checked");%> onkeydown="javascript:nextKeyPress(this)"><%}%></td>
		</tr>
	<tr style="display: none;">
		<td  width="100px" align="right"><%=LocalUtilis.language("class.partnType",clientLocale)%>:&nbsp;&nbsp;</td>
		<td><div id="comboBoxTree"></div></td>
		<td align="right"><%=LocalUtilis.language("class.partnName",clientLocale)%> :</td><!--渠道来源-->
		<td><div id="comboBoxParentTree"></div></td>
	</tr>
	<tr >
		<td align="right"><%=LocalUtilis.language("class.partnAttachmentInfo",clientLocale)%> :&nbsp;&nbsp;</td><!--渠道附加信息-->
		<td>
			<input name="channel_memo" size="1" style="WIDTH: 250px" onkeydown="javascript:nextKeyPress(this)"  value="<%=channel_memo%>">
		</td>
		<td align="right"><font color="red"><b>*</b></font><%=LocalUtilis.language("class.channelCooperation",clientLocale)%> :&nbsp;&nbsp;</td><!-- 渠道合作方式 -->
		<td>
			<SELECT size="1" name="channel_cooperation" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px">
					<%=Argument.getChannelCooperationOptions(channel_cooperation)%>
			</SELECT>
		</td>
	</tr>
		<tr>
			<%if (serial_no!= null){%>
				<td align="right" width="120px"><font color="red"><b>*</b></font><%=LocalUtilis.language("class.linkMan",clientLocale)%>:&nbsp;&nbsp;</td><%=LocalUtilis.language("class.linkMan",clientLocale)%> <!--合同销售人员-->
				<td>
					<select size="1" name="link_man" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px" onchange="javascript:selectTeam(this.value);">
						<%=Argument.getOperatorOptionsByRoleId(new Integer(2),link_man)%>
					</select>
				</td>
			<%}%>
		</tr>
		<tr>
			<%if(intrust_flag1.intValue()!=1){%>
			<td align="right"><%=LocalUtilis.language("class.provLevel",clientLocale)%> :&nbsp;&nbsp;</td><!-- 受益优先级 -->
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
			<td align="right"><%=LocalUtilis.language("class.incomeLevel",clientLocale)%> :&nbsp;&nbsp;</td><!-- 收益级别 -->
			<td>
				<div id="div_prov_level">
				<select name="prov_level" style="width:100px" onchange="javascript:getGainRate()">
					<%
					if(prov_level==null){
						out.println(Argument.getProvlevelOptions(prov_level));
					}else{
						out.println(Argument.getProductProvLevel(product_id,sub_product_id,prov_flag.intValue()==0?new Integer(1):prov_flag,prov_level));
					}
					%>
				</select>
				</div>
			</td>
			<%}
			}%>
			</tr>
			<tr id="money_origin_id">
				<td align="right"><font color='red'>*</font>资金/资产来源:</td>
				<td colspan="3">
					<select size="1" name="money_origin" onkeydown="javascript:nextKeyPress(this)" style="width:120px" onchange="javascript:moneyOriginChange(this.value);">
						<%=Argument.getDictOptionsWithLevel(new Integer(1158),"1158",new Integer(0))%>
					</select>
					<select size="1" name="sub_money_origin" <%if("115801".equals(money_origin)||"115807".equals(money_origin)||"".equals(money_origin)||money_origin==null) out.println("style='display:none'");%> onkeydown="javascript:nextKeyPress(this)" style="width:120px">
						<%=Argument.getDictOptionsWithLevel(new Integer(1158),sub_money_origin, new Integer(0))%>
					</select>
				</td>
			</tr>
			<tr>
				<td align="right"><%=LocalUtilis.language("message.cooperationWay",clientLocale)%> :&nbsp;&nbsp;</td><!--合作方式-->
				<td>
					<input <%if (with_bank_flag != null && with_bank_flag.intValue()==1)out.print("checked");%>
							onkeydown="javascript:nextKeyPress(this)" type="checkbox" class="flatcheckbox" value="0" name="with_bank_flag" onclick="javascript:showHtyh();">
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
						class="flatcheckbox" value="0" name="with_security_flag"
						><%=LocalUtilis.language("message.cooperation2",clientLocale)%> &nbsp;&nbsp;<!--证信合作-->
					<input <%if (with_private_flag != null && with_private_flag.intValue()==1)out.print("checked");%>
						onkeydown="javascript:nextKeyPress(this)" type="checkbox"
						class="flatcheckbox" value="0" name="with_private_flag"
						><%=LocalUtilis.language("class.cooperation4",clientLocale)%> &nbsp;&nbsp;<!--私募基金合作-->
				</td>
				<td align="right">预期收益率 :&nbsp;&nbsp;</td>
				<td>
					<input name="expect_ror_lower" size="12" value="" onkeydown="javascript:nextKeyPress(this)"><font color="red">%</font> 到 
					<input name="expect_ror_upper" size="12" value="" onkeydown="javascript:nextKeyPress(this)"><font color="red">%</font>
				</td>
			</tr>
			<tr>
				<td align="right" width="120px"><%=LocalUtilis.language("class.citySerialNO",clientLocale)%> :&nbsp;&nbsp;</td><!--合同推介地-->
				<td>
					<select size="1" name="city_serialno" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px">
						<%=cityNameOptions%>
					</select>
					&nbsp;<button type="button"  class='xpbutton4' accessKey=r id='btnSetcity' name='btnSetcity' onclick='javascript:newCity(document.theform.product_id.value);' ><%=LocalUtilis.language("class.promotionSet",clientLocale)%> </button>
				</td>
			</tr>
			<tr>
				<td width="120px" align="right">用款方关联 :&nbsp;&nbsp;</td><!--用款方关联-->
				<td>   
					<INPUT type="radio" class="flatcheckbox" id="is_ykgl" name="is_ykgl" value="1" <%if(is_ykgl.equals("1")) out.print("checked");%>>是
					<INPUT type="radio" class="flatcheckbox" id="is_ykgl" name="is_ykgl" value="0" <%if(!is_ykgl.equals("1")) out.print("checked");%>>否
				</td>
				<td width="120px" align="right">非现场交易:</td>
				<td>   
					<input type="checkbox" class="flatcheckbox" name="spot_deal" value="1">
				</td>
			</tr>
			<tr>
				<td align="right">合同联系人 :&nbsp;&nbsp;</td>
				<td>
					<%--select size="1" name="cust_message" onkeydown="javascript:nextKeyPress(this)" onchange="newlyIncreased(this.value);">
					</select--%>
					&nbsp;&nbsp;姓名:<input type="text" name="ht_cust_name" size="15" onkeydown="javascript:nextKeyPress(this)" value=""/>
					&nbsp;&nbsp;电话:<input type="text" name="ht_cust_tel" size="15" onkeydown="javascript:nextKeyPress(this)" value=""/>
					&nbsp;&nbsp;地址:<input type="text" name="ht_cust_address" size="35" onkeydown="javascript:nextKeyPress(this)" value=""/>	
				</td>
			</tr>
			<tr>
				<td align="right" vAlign="top" width="120px">合同预计收益率 :&nbsp;&nbsp;</td><!--信托合同预计收益率-->
				<td  colspan=3><textarea rows="3" id="xthtyjsyl" name="xthtyjsyl" onkeydown="javascript:nextKeyPress(this)" cols="83"></textarea></td>
			</tr>

			<tr>
				<td align="right" vAlign="top" width="120px"><%=LocalUtilis.language("class.description",clientLocale)%> :&nbsp;&nbsp;</td><!--备注-->
				<td  colspan=3><textarea rows="3" name="summary2" onkeydown="javascript:nextKeyPress(this)" cols="83"></textarea></td>
			</tr>
			<tr id="reader2" style="display:">
	          	<td class="paramTitle"align="right"><%=LocalUtilis.language("menu.filesAdd",clientLocale)%> :&nbsp;&nbsp;</td><!-- 添加附件 -->
	            <td colspan="3">
	            	<table id="test" width="100%">
	            		<tr >
	            			<td>
			            	<input type="file" name="file_info" size="60">&nbsp;
			            	<img title="<%=LocalUtilis.language("message.tSelectAdditionalFiles",clientLocale)%> " src="<%=request.getContextPath()%>/images/minihelp.gif"><!--选择附加文件-->
			            	</td>
			            </tr>
			        </table>
			        <button type="button"  class="xpbutton6" onclick="addline()"><%=LocalUtilis.language("class.moreMccessories",clientLocale)%> <!--单击此处添加更多附件--></button>
	            </td>
	        </tr>
	</TABLE>
</div>
<br>
<div align="right">
	<!--保存-->
    <button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:SaveAction();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
	&nbsp;&nbsp;
	<!--返回-->
    <button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:CancelAction();"><%=LocalUtilis.language("message.back",clientLocale)%> (<u>C</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</div>
</form>
<p>
<%
contract.remove();
product.remove();
preContract.remove();
sale_parameter.remove();
customer.remove();
%>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>
