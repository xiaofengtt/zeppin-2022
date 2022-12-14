<%@ page contentType="text/html; charset=GBK" import="enfo.crm.marketing.*,enfo.crm.web.*,enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
//获得页面传递变量
String sQuery = request.getParameter("sQuery");
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"), null);//认购主键
Integer pre_serial_no = Utility.parseInt(request.getParameter("pre_serial_no"), null);//crm预约主键
Integer bind_serial_no = Utility.parseInt(request.getParameter("bind_serial_no"), null);//预约主键
String q_productId = Utility.trimNull(request.getParameter("product_id"));  //产品信息ID
Integer pre_productId = Utility.parseInt(request.getParameter("preproduct_id"),new Integer(0));  //产品信息ID
String q_cust_id = Utility.trimNull(request.getParameter("customer_cust_id")); //客户ID
String bank_id = request.getParameter("bank_id");//银行ID
//---------------------------------------------------------------------------------------------------
String channel_type = Utility.trimNull(request.getParameter("channel_type"));
String channel_memo = Utility.trimNull(request.getParameter("channel_memo"));
String channel_cooperation = Utility.trimNull(request.getParameter("channel_cooperation"),"550203");
Integer r_channel_id = Utility.parseInt(Utility.trimNull(request.getParameter("channel_id")),new Integer(0));
BigDecimal market_trench_money = Utility.parseDecimal(Utility.trimNull(request.getParameter("market_trench_money")),new BigDecimal(0));
//---------------------------------------------------------------------------------------------------
Integer check_flag=Utility.parseInt(request.getParameter("check_flag"),new Integer(0));
int inputflag=Utility.parseInt(request.getParameter("inputflag"), 1);
int inputflag2=Utility.parseInt(request.getParameter("inputflag2"), 1);
int selfbenflag = Utility.parseInt(request.getParameter("selfbenflag"), 1);//自益标志
Integer bonus_flag = Utility.parseInt(request.getParameter("bonus_flag"),new Integer(1));
//---------------------------------------------------------------------------------------------------
Integer fee_jk_type = Utility.parseInt(request.getParameter("fee_jk_type"), new Integer(0));
String jk_type = Utility.trimNull(request.getParameter("jk_type"),"111404"); //缴款方式
Integer qs_date = Utility.parseInt(Utility.trimNull(request.getParameter("dz_date")),new Integer(0));//签约日期
Integer jk_date = Utility.parseInt(Utility.trimNull(request.getParameter("dz_date")),new Integer(0));//缴款日期
String bank_acct_type = Utility.trimNull(request.getParameter("bank_acct_type"),"99200017");
String q_cust_name =  Utility.trimNull(request.getParameter("cust_name"));
String prov_level =  Utility.trimNull(request.getParameter("prov_level"));
String ht_bank_id =  Utility.trimNull(request.getParameter("ht_bank_id"));
String ht_bank_sub_name =  Utility.trimNull(request.getParameter("ht_bank_sub_name"));
Integer with_bank_flag = Utility.parseInt(request.getParameter("with_bank_flag"), new Integer(0));
Integer with_security_flag = Utility.parseInt(request.getParameter("with_security_flag"), new Integer(0));
Integer with_private_flag = Utility.parseInt(request.getParameter("with_private_flag"), new Integer(0));
Integer prov_flag = Utility.parseInt(request.getParameter("prov_flag"), new Integer(0));
BigDecimal bonus_rate = Utility.parseDecimal(Utility.trimNull(request.getParameter("bonus_rate")),new BigDecimal(0));
Integer input_man = Utility.parseInt(Argument.getContractLinkMan(bind_serial_no),input_operatorCode);
Integer link_man = Utility.parseInt(request.getParameter("link_man"), input_man);
String gain_acct = Utility.trimNull(request.getParameter("gain_acct"));
String bank_province = Utility.trimNull(request.getParameter("bank_acct_type"));
String bank_city = Utility.trimNull(request.getParameter("bank_city"));
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
	sQuery = file.getParameter("sQuery");
	serial_no = Utility.parseInt(file.getParameter("serial_no"), null);//认购主键
	bind_serial_no = Utility.parseInt(file.getParameter("bind_serial_no"), null);//预约主键    
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
int is_product_sub_flag = Argument.getSyscontrolValue_1("SUBCP01");//子产品开关
String fee_type_name = enfo.crm.tools.LocalUtilis.language("class.rgFee",clientLocale);//认购费

String preCodeOptions = "";//预约号列表
String bankOptions = "";//银行号列表
String cityNameOptions= "";//合同推介地

//声明参数
//ID
Integer cust_id = Utility.parseInt(q_cust_id,new Integer(0));
Integer product_id = Utility.parseInt(q_productId,new Integer(0));
Integer cust_type = new Integer(0);
String cust_no=null;
//产品信息
String period_unit_name = "";
Integer period_unit = new Integer(0);
Integer sub_product_id = new Integer(0);
Integer product_sub_flag = new Integer(0);//子产品标志
String product_code = "";
String product_name = "";
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
String[] contractNo = null;
//------------------------------------
String cust_name = Utility.trimNull(q_cust_name);
String contract_bh = "";//合同序号
String bank_acct = Utility.trimNull(request.getParameter("bank_acct"));
String contract_sub_bh = Utility.trimNull(request.getParameter("contract_sub_bh"));
List markList = null;
Map markMap = null;
//获得对象
CustomerLocal customer = EJBFactory.getCustomer();//客户
ContractLocal contract = EJBFactory.getContract();//合同
ProductLocal product = EJBFactory.getProduct();//产品
PreContractLocal preContract = EJBFactory.getPreContract();//预约
//获得各种数据VO
ContractVO cont_vo = new ContractVO();
CustomerVO cust_vo = new CustomerVO();
ProductVO p_vo = new ProductVO();
PreContractVO pre_vo = new PreContractVO();
SaleParameterVO salevo = new SaleParameterVO();
SaleParameterLocal sale_parameter = EJBFactory.getSaleParameter();
if(request.getMethod().equals("POST")){
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
	BigDecimal r_bonus_rate = new BigDecimal(0);
	bank_province = Utility.trimNull(file.getParameter("bank_province"));
	bank_city = Utility.trimNull(file.getParameter("bank_city"));
	if(r_bonus_flag.intValue() == 1){//现金
		r_bonus_rate = new BigDecimal(0);
	}else if(r_bonus_flag.intValue() == 2){//转份额
		r_bonus_rate = new BigDecimal(1);
	}else{//部分转份额
		r_bonus_rate = Utility.parseDecimal(Utility.trimNull(file.getParameter("bonus_rate")),new BigDecimal(0)).multiply(new BigDecimal("0.01"));
	}

	if (inputflag2==1)
		r_bank_acct = file.getParameter("bank_acct2");
	else
		r_bank_acct = file.getParameter("bank_acct");

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
	Integer spot_deal = Utility.parseInt(file.getParameter("spot_deal"), new Integer(2));

	if (r_already_sale.equals(new BigDecimal(0)))
		r_already_sale = r_rg_money;
	else
		r_already_sale = r_already_sale.add(r_rg_money);
	
	Integer contact_id = Utility.parseInt(file.getParameter("cust_message"),null);//合同联系人
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
	s_cont_vo.setContact_id(contact_id);
	s_cont_vo.setBank_province(bank_province);
	s_cont_vo.setBank_city(bank_city);
	s_cont_vo.setSpot_deal(spot_deal);
	s_cont_vo.setContract_type(new Integer(1)); //
	Object[] resultSet =contract.append(s_cont_vo);

	serial_no = (Integer)resultSet[0];
	contract_bh = (String)resultSet[1];
	PreMoneyDetailLocal local = EJBFactory.getPreMoneyDetail();
	//关联到账表TCONTRACT_SERIAL_NO	
	String [] items = file.getParameterValues("money_serial_no");
	Utility.debug("items:"+items);
	if(items != null && items.length > 0)
	{
		for(int i=0; i<items.length; i++)
		{
			Integer money_serial_no = Utility.parseInt(Utility.trimNull(items[i]), new Integer(0));
			if(money_serial_no != null && (!money_serial_no.equals(new Integer(0))))
			{
				local.relateContract(money_serial_no,serial_no);
			}
		}
	}

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

}else{
	//根据绑定预约主键查询相关信息
	if(pre_serial_no !=null && pre_serial_no.intValue()>0){
		pre_vo.setSerial_no(pre_serial_no);
		List rsList_preContract = preContract.load(pre_vo);

		if (rsList_preContract.size()>0){
			Map map_preContract = (Map)rsList_preContract.get(0);
				
			cust_id = Utility.parseInt(Utility.trimNull(map_preContract.get("CUST_ID")),new Integer(0));
			pre_code = Utility.trimNull(map_preContract.get("PRE_CODE2"));
            pre_money= Utility.parseBigDecimal(Utility.stringToDouble(Utility.trimNull(map_preContract.get("PRE_MONEY"))),new BigDecimal(0)); 
			contract_sub_bh = Utility.trimNull(map_preContract.get("CONTRACT_NO"));
			contractNo = contract_sub_bh.split("&");
		}
	}

	//产品信息显示 查询相应预约号列表
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
		markList = product.queryMarketTrench(p_vo);

		if(map_product!=null){
			open_flag = Utility.parseInt(Utility.trimNull(map_product.get("OPEN_FLAG")),new Integer(0));
			intrust_flag1 = Utility.parseInt(Utility.trimNull(map_product.get("INTRUST_FLAG1")),new Integer(0));
			product_code = Utility.trimNull(map_product.get("PRODUCT_CODE"));
			product_name = Utility.trimNull(map_product.get("PRODUCT_NAME"));
			product_status = Utility.trimNull(map_product.get("PRODUCT_STATUS"));
			period_unit = Utility.parseInt(Utility.trimNull(map_product.get("PERIOD_UNIT")),new Integer(0));//合同期限
			pre_start_date =  Utility.parseInt(Utility.trimNull(map_product.get("PRE_STRAT_DATE")),new Integer(0));
			pre_end_date =  Utility.parseInt(Utility.trimNull(map_product.get("PRE_END_DATE")),new Integer(0));
			Boolean temp_flag = (Boolean)map_product.get("SUB_FLAG");
			String asfund_flag_s = Argument.getProductAsfundFlag(product_id,input_man);
			asfund_flag = Utility.parseInt(Utility.trimNull(asfund_flag_s),new Integer(0));

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

		//预约列表
		if (preCodeOptions == null || preCodeOptions.equals("")){
                         
			preCodeOptions = Argument.getPreContractOptions(input_bookCode, product_id, bind_serial_no.toString(), input_operatorCode,q_cust_name);
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
		cust_type = Utility.parseInt(Utility.trimNull(map_cust.get("CUST_TYPE")), new Integer(0));
        cust_name = Utility.trimNull(map_cust.get("CUST_NAME"));
        cust_no = Utility.trimNull(map_cust.get("CUST_NO"));
		if("".equals(Utility.trimNull(gain_acct)))
			gain_acct = cust_name;
	}
}

//查看用户所在团队信息
team_id = Utility.parseInt(Utility.trimNull(Argument.getTeam(link_man)),new Integer(-1));
salevo.setTeamID(team_id);
salevo.setProductID(product_id);
salevo.setPre_product_id(pre_productId);
List saleList = sale_parameter.queryQuota(salevo,input_operatorCode);
Map map_sale = null;

if(saleList.size()>0){
	map_sale = (Map)saleList.get(0);
}

if(map_sale != null){
	serialNo = Utility.parseInt(Utility.trimNull(map_sale.get("SERIAL_NO")), new Integer(0));
	quota_money = Utility.parseDecimal(Utility.trimNull(map_sale.get("QUOTAMONEY")),new BigDecimal(0));
	already_sale = Utility.parseDecimal(Utility.trimNull(map_sale.get("ALREADYSALE")),new BigDecimal(0));
	quota_qualified_num = Utility.parseInt(Utility.trimNull(map_sale.get("QUOTA_QUALIFIED_NUM")),new Integer(0));
	already_qualified_num = Utility.parseInt(Utility.trimNull(map_sale.get("ALREADY_QUALIFIED_NUM")),new Integer(0));
}
int is_contract_flow = Argument.getSyscontrolValue("CONTRACT_FLOW");
%>
<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("menu.addSubscriptionInfo",clientLocale)%>（<%=LocalUtilis.language("message.booked",clientLocale)%> ）</TITLE><!--认购信息新增--><!--已预约-->
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<base target="_self">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<link href="<%=request.getContextPath()%>/widgets/ext/resources/css/ext-all.css" type="text/css"  rel="stylesheet" />

<script type="text/javascript" src="<%=request.getContextPath()%>/includes/jQuery/js/jquery-1.6.2.min.js"></script>
<script type="text/javascript">
	var j$ = jQuery.noConflict();
</script>

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
		document.theform.action="signed_spot_aciton.jsp" ;
		document.theform.submit();
	}
}

function changeProv_level(_productid,prov_level){
	j$("#span_gain_rate")[0].innerHTML = "";
	
	j$.ajax({
		url: "<%=request.getContextPath()%>/marketing/sell/benifiter_level_info_t_do.jsp",
		type : "get",
		cache:false,
		data: {
			product_id : _productid,
			prov_flag : j$("#prov_flag").val(),
			prov_level : prov_level
		},
		success: function(data){
			j$("#span_gain_rate")[0].innerHTML = data;
		}
	});
}

/*返回*/
function CancelAction(){
	//tempArray = '<%=sQuery%>'.split('$');
	window.close();
}

/*下面是新增认购信息时用到的函数*/
function validateForm(form){
	var userid='<%=user_id.intValue()%>';
	if(userid == 2 && form.sfpj.value != 1){
		sl_alert("请对客户进行评级处理!");//请选择客户进行评级
		return false;
	}
	if(document.theform.inputflag.value==2){
		if(!sl_checkNum(form.pre_code, "<%=LocalUtilis.language("class.preCode2",clientLocale)%> ", 16, 1)){return false;}//预约编号
	}

	if(document.theform.inputflag.value==1){
		if(!sl_checkChoice(form.product_id, "<%=LocalUtilis.language("class.productName",clientLocale)%> ")){return false;}//产品名称
		if(!sl_checkChoice(form.pre_serial_no, "<%=LocalUtilis.language("class.preCode2",clientLocale)%> ")){return false;}//预约编号
	}
	var str = checkedValue(document.theform.money_serial_no);
    if(str == null || str == "")
    {
        sl_alert("请选择缴款信息!");
        return;
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
	if(!/^[0-9]*$/.test(form.bank_acct.value)){
		alert("银行账号必须填写数字！");
		return false;
	}
	if(form.bank_acct.value == ""){
		alert("银行账号不能为空");
		return false;
	}
	<% if(user_id.intValue() == 2){%>
		if(!sl_checkChoice(form.bank_province, '开户行所在地'))return false;//开户行所在地
		if(!sl_checkChoice(form.bank_city, '开户行所在地'))return false;//开户行所在地
	<%}%>
	if(!sl_checkDecimal(form.rg_money, '<%=LocalUtilis.language("class.rg_money",clientLocale)%> ', 13, 3, 1))return false;//认购金额
	if(!sl_checkChoice(form.jk_type, '<%=LocalUtilis.language("class.feeType",clientLocale)%> '))return false;//缴款方式
	if(!sl_checkChoice(form.prov_flag, '<%=LocalUtilis.language("class.provLevel",clientLocale)%> '))return false;//受益优先级级别
	if(!sl_checkChoice(form.prov_level, '<%=LocalUtilis.language("class.incomeLevel",clientLocale)%> '))return false;//收益级别
	trim_money(form.pre_money);
	strTemp="";

	if(parseFloat(form.pre_money.value)<parseFloat(form.rg_money.value)){
		strTemp="<%=LocalUtilis.language("message.subscriptionAmountError",clientLocale)%> ";//认购金额已大于预约金额
		return false;
	}

	if(!sl_checkDate(form.qs_date_picker,'<%=LocalUtilis.language("class.qsDate",clientLocale)%> '))return false;//签署日期
	if(!sl_checkDate(form.jk_date_picker,"<%=LocalUtilis.language("class.dzDate",clientLocale)%> "))return false;//缴款日期

	syncDatePicker(form.qs_date_picker, form.qs_date);
	syncDatePicker(form.jk_date_picker, form.jk_date);
    //if(!sl_checkChoice(form.market_trench, '渠道名称 ')){return false;}//渠道名称
    if(!sl_checkChoice(form.channel_cooperation, '渠道合作方式 ')){return false;}//渠道合作方式
	var userid='<%=userid.intValue()%>';
	if(form.self_ben_flag.checked){
		form.selfbenflag.value='1';
	}
	else{
		form.selfbenflag.value='0';
	}

	//if(intrustType_Flag == '1'){
		//document.theform.channel_type.value = comboBoxTree.getValue();
		//document.theform.channel_id.value = comboBoxParentTree.getValue();
	//}

	//if(!checkSellInfo())return false;//检查销售上限

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
/*
	if(form.quota_money.value != 0){
		var sum = parseInt(form.rg_money.value) + <%=already_sale%>;
		if(sum > <%=quota_money%>){
			sl_alert('<%=LocalUtilis.language("message.teamQuota",clientLocale)%>');//用户所在团队在该产品上累计已销售额超过配额值
			return false;
		}
	}
	*/
	return sl_check_update();
}

/**************************************************************************************************************************/
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
	}
	else{
		bank_acct_num.innerText = "(" + showLength(value) + " 位 )";
	}
}

/*设定预约号*/
function selectPrecodeItem(preSerialNo){
	if(preSerialNo>0){
		contract.getPreContractInfo(preSerialNo,{callback: function(data){
			document.theform.cust_no.value = data[2];
			document.theform.cust_name.value = data[1];
			document.theform.pre_money.value = data[4];
			document.theform.rg_money.value = data[4];
			document.theform.gain_acct.value = data[1];
			document.theform.customer_cust_id.value = data[0];
		}});
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

/*拼后缀参数*/
function getLastOptions(){
	if(!sl_checkDate(document.theform.qs_date_picker,"<%=LocalUtilis.language("class.qsDate",clientLocale)%> ")){return false};//签署日期
	syncDatePicker(document.theform.qs_date_picker, document.theform.qs_date);

	var temp="";
	var temp = temp + "&product_id="+ document.theform.product_id.value;
	var temp = temp + "&jk_type=" + document.theform.jk_type.value;
	var temp = temp + "&qs_date=" + document.theform.qs_date.value ;
	var temp = temp + "&contract_bh=" + document.theform.contract_bh.value ;
	var temp = temp + "&gain_acct="+ document.theform.gain_acct.value ;
	var temp = temp + "&sQuery=" + document.theform.sQuery.value ;
	var temp = temp + "&contract_sub_bh=" + document.theform.contract_sub_bh.value ;
	var temp = temp + "&cust_name=" + document.theform.cust_name.value ;

	<%if (serial_no!= null){%>
	var temp = temp + "&link_man=" + document.theform.link_man.value;
	<%}%>

	<%if(product_sub_flag.intValue() == 1 && is_product_sub_flag == 1){%>
		var temp = temp + "&sub_product_id=" + document.theform.sub_product_id.value;
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
	<%if(open_flag!=null && open_flag.intValue()==1){%>
		var temp = temp +"&bonus_flag="+document.theform.bonus_flag.value
	<%}%>
	var temp = temp + "&customer_cust_id=" + document.theform.customer_cust_id.value ;
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
	return true;
}
/**************************************************************************************************************************/
/*初始化*/
window.onload = function(){
	var v_bSuccess = document.getElementById("bSuccess").value;
	var v_selfbenflag = document.getElementById("selfbenflag").value;
	var v_serial_no = document.getElementById("serial_no").value;
	var v_contract_bh = document.getElementById("contract_bh").value;
	var v_pre_serial_no = document.getElementById("pre_serial_no").value;
	
	if(v_bSuccess=="true"){
		if(v_selfbenflag==0){
           //认购成功 //合同序号 //为//合同为他益信托，需要进行该合同受益人维护吗
		  if (confirm("<%=LocalUtilis.language("message.subscriptionSucc",clientLocale)%>！<%=LocalUtilis.language("class.constractBH",clientLocale)%><%=LocalUtilis.language("message.is",clientLocale)%>:"+v_contract_bh+"，<%=LocalUtilis.language("message.contractMaintenanceTip",clientLocale)%> ？")){
		  	location = 'benifiter_list.jsp?from_flag_benifitor=1&contract_id='+v_serial_no+'&page=1&sQuery=<%=sQuery%>';
		  }
		}
		window.returnValue = 1;
		window.close();
		
	}
	
	selectPrecodeItem(v_pre_serial_no);
	getSellInfo();
	showCustOptions(document.getElementById("customer_cust_id").value);
	showMoney();
	
	//gain_rate chanage
	changeProv_level('<%=product_id%>',j$("#prov_level").val());
}

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

function selectBank(value)
{
	var url ;
	<%if(serial_no !=null){%>
	document.theform.bind_serial_no.value = document.theform.pre_serial_no.value;
	url = 'signed_spot_aciton.jsp?bank_id=' + value
		+'&pre_money='+document.theform.pre_money.value
		+'&rg_money='+document.theform.rg_money.value
		+'&bank_acct='+document.theform.bank_acct.value
		+'&contract_sub_bh='+document.theform.contract_sub_bh.value
		+'&serial_no=<%=serial_no%>' + getLastOptions();
	<%}else{%>
	document.theform.bind_serial_no.value= document.theform.pre_serial_no.value;
	url = 'signed_spot_aciton.jsp?bank_id=' + value
		+'&pre_money='+document.theform.pre_money.value
		+'&rg_money='+document.theform.rg_money.value
		+'&bank_acct='+document.theform.bank_acct.value
		+'&contract_sub_bh='+document.theform.contract_sub_bh.value
		+ '&bind_serial_no=' + document.theform.bind_serial_no.value;
	<%}%>
	location = url ;
}

var n=1;
/*
 *添加附件
 */
function addline()
{
	n++;
	newline=document.all.test.insertRow()
	newline.id="fileRow"+n;
	newline.insertCell().innerHTML="<input type='file' name=file_info"+n+" size='60'>"+"<button type='button' class='xpbutton3'  onclick='javascript:removeline(this)'><%=LocalUtilis.language("messgae.remove",clientLocale)%> </button>";//移除
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
		width:220,
		tree : {
			xtype:'treepanel',
			width:220,
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
	j$("#span_gain_rate")[0].innerHTML = "";

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

/*查看团队信息*/
function selectTeam(value){
	if (value!=""){
		location = 'signed_spot_action.jsp?page=<%=sPage%>&link_man=' + value+ getLastOptions();
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

//查看客户信息
function getCustomer(cust_id){
	var url = '/marketing/customerInfo2.jsp?cust_id='+ cust_id ;	
	var v = showModalDialog(url,'','dialogWidth:720px;dialogHeight:620px;status:0;help:0;');
}

/*查看客户详细*/
function showInfo(custid) {
 	var url = '/client/clientinfo/client_query_info.jsp?cust_id='+custid;
	showModalDialog(url, '', 'dialogWidth:1024px;dialogHeight:600px;status:0;help:0');
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
	var cust_id = getElement("customer", "cust_id").value;
	var url = "<%=request.getContextPath()%>/client/clientinfo/client_contact_new.jsp?cust_id="+cust_id;
	if(obj=="0"){	
		document.theform.link_cust.style.display="none";		
		if(showModalDialog(url,'','dialogWidth:900px;dialogHeight:420px;status:0;help:0;scroll:0')){
			sl_update_ok();
			showCustOptions(cust_id);
		}
	}else if(obj == ""){
		document.theform.link_cust.style.display="none";
	}else{
		document.theform.link_cust.style.display="";
	}
}

function showLinkCust(){

	var cust_id = getElement("customer", "cust_id").value;
	var contact_id = document.theform.cust_message.value;
	var url = "<%=request.getContextPath()%>/client/clientinfo/client_contact_new.jsp?contact_id="+contact_id+"&cust_id="+cust_id;
	if(showModalDialog(url,'','dialogWidth:900px;dialogHeight:420px;status:0;help:0;scroll:0')){
			sl_update_ok();
			showCustOptions(cust_id);
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

function showMoney(){
	var money_serial_no = document.getElementsByName("money_serial_no");
	document.getElementById("rg_money").value = '';
	for(var i=0;i< money_serial_no.length;i++){
		if(money_serial_no[i].checked){
			var texts = money_serial_no[i].nextSibling.nodeValue;
			if(texts != null && texts != ""){
				text = texts.split("-");
				document.getElementById("jk_type").value = text[0];
				document.getElementById("jk_date_picker").value = text[2];
				document.getElementById("qs_date_picker").value = text[2];
				document.getElementById("rg_money").value = Number(text[3])+Number(document.getElementById("rg_money").value);
			}
		}
	}
	showCnMoney(document.getElementById("rg_money").value,rg_money_cn);
}

/*根据缴款记录获取到账日期、缴款方式、认购金额
function showMoney(){
	var selectIndex = document.getElementById("money_serial_no").selectedIndex;
	var texts = document.getElementById("money_serial_no").options[selectIndex].text;
	if(texts != "请选择" ){
		text = texts.split("-");
		document.getElementById("jk_type").value = text[0];
		document.getElementById("jk_date_picker").value = text[2];
		document.getElementById("qs_date_picker").value = text[2];
		document.getElementById("rg_money").value = text[3];
	}
}
*/

function showCustBank(){
	var selectIndex = document.getElementById("cust_bank").selectedIndex;
	var texts = document.getElementById("cust_bank").options[selectIndex].text;
	//alert(texts);
	if( document.getElementById("cust_bank").value != "" ){
		text = texts.split("-");
		document.getElementById("bank_id").value = document.getElementById("cust_bank").value;
		document.getElementById("bank_acct").value = text[1];
	}
}

//评级
function newInfo(cust_id){
	if(cust_id <= 0)
		sl_alert('请选择客户之后再进行评级');
	else {
		if(showModalDialog('/marketing/cust_grade_new.jsp?cust_id='+cust_id, '', 'dialogWidth:700px;dialogHeight:600px;status:0;help:0')){
			sl_alert("<%=LocalUtilis.language("message.rating",clientLocale)%><%=LocalUtilis.language("message.success",clientLocale)%> ！");
			document.theform.sfpj.value = 1;
		}
	}
}

function selContractNo(contract){
	var text = contract.options[contract.selectedIndex].text;
	document.getElementById("contract_sub_bh").value = text;
}
</script>
</HEAD>

<BODY class="BODY body-nox">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="post" onsubmit="javascript:return validateForm(this);" ENCTYPE="multipart/form-data">
<input type="hidden" id="bSuccess" value="<%=bSuccess%>"/><!--新增成功标志-->
<input type="hidden" name="sQuery" value="<%=sQuery%>"/>

<input type="hidden" name="book_code" value="<%=input_bookCode%>"/>
<input type="hidden" name="customer_cust_id" value="<%=cust_id%>"/><!--客户ID标志-->
<input type="hidden" name="cust_type" value="<%=cust_type%>"/>
<input type="hidden" name="serial_no" id="serial_no" value="<%=serial_no%>">
<input type="hidden" name="contract_bh" id="contract_bh" value="<%=contract_bh%>">
<input type="hidden" name="bind_serial_no" value=""><!--预约号临时保存-->
<input type="hidden" id="t_contract_bh" name="t_contract_bh" value="<%=contract_bh%>"/>

<input type="hidden" name="inputflag" value="<%=inputflag%>"/><!--预约号标志-->
<input type="hidden" name="inputflag2" value="<%=inputflag2%>"/><!--银行账号标志-->
<input type="hidden" name="check_flag" value="<%=check_flag%>"/>
<input type="hidden" name="intrust_flag1" value="<%=intrust_flag1%>"/>
<input type="hidden" id="selfbenflag" name="selfbenflag" value="<%=selfbenflag%>"/>

<input type="hidden" name="period_unit" value="<%=period_unit%>"/>
<input type="hidden" name="pre_start_date" value="<%=pre_start_date%>"/>
<input type="hidden" name="pre_end_date" value="<%=pre_end_date%>"/>
<!-- 团队配额信息 -->
<input type="hidden" name="quota_money" value="<%=quota_money%>">
<input type="hidden" name="already_sale" value="<%=already_sale%>">
<input type="hidden" name="team_id" value="<%=team_id%>">
<input type="hidden" name="serialNo" value="<%=serialNo%>">
<input type="hidden" name="quota_qualified_num" value="<%=quota_qualified_num%>">
<input type="hidden" name="already_qualified_num" value="<%=already_qualified_num%>">

<input type="hidden" name="org_bal" value=""><!-- 300W 金额 -->
<input type="hidden" name="contact_num" value=""><!-- 合同上限 -->
<input type="hidden" name="current_num" value=""><!-- 已有合同 -->

<input type="hidden" id="channel_type" name="channel_type" value="<%=channel_type%>"/>
<input type="hidden" id="channel_id" name="channel_id" value="<%=r_channel_id%>"/>
<input type="hidden" id="channel_rate" name="channel_rate" value=""/>
<input type="hidden" name="sfpj" value="0">
<div align="left" class="page-title">
	<font color="#215dc6"><b>我的工作台>>现场签约</b></font>
	<br/>
</div>
<br/>
<div>
	<table class="table-popup" border="0" width="100%" cellspacing="2" cellpadding="2" >
		<tr >
			<td align="right" width="120px"><%=LocalUtilis.language("class.productNumber",clientLocale)%> :&nbsp;&nbsp;</td><!--产品选择-->
			<td>
				<input type="hidden" id="product_id" name="product_id" value="<%=product_id%>"/>
				<input type="text" readonly class="edline" name="product_name" value="<%=product_code%>-<%=product_name%>" onkeydown="javascript:nextKeyPress(this);" size="50">		
			</td>
		<%if(product_sub_flag.intValue() == 1 && is_product_sub_flag == 1){%>
			<td align="right" width="120px"><b><font color="red"><b>*</b></font><%=LocalUtilis.language("class.subProductID",clientLocale)%> </b>:&nbsp;&nbsp;</td><!--子产品选择-->
			<td align=left colspan=3>
				<select size="1" name="sub_product_id" onkeydown="javascript:nextKeyPress(this)" class="productname" >
					<%=Argument.getSubProductOptions(product_id,new Integer(0),sub_product_id)%>
				</select>&nbsp;&nbsp;
				<button type="button"  class="xpbutton2" name="show_sub_info" title="查看子产品信息" onclick="javascript:openSubProduct();"><%=LocalUtilis.language("message.view",clientLocale)%></button><!-- 查看 -->
			</td>

		<%}%>
		</tr>
	</table>
	<table class="table-popup" border="0" width="100%" cellspacing="2" cellpadding="3" >
		<TR>
			<td align="right" width="120px"><%=LocalUtilis.language("class.customerID",clientLocale)%> :&nbsp;&nbsp;</td><!--客户编号-->
			<td><input readonly class="edline" name="cust_no" size="20" onkeydown="javascript:nextKeyPress(this)" value="<%=cust_no%>"/></td>
			<td align="right" width="120px"><%=LocalUtilis.language("class.customerName",clientLocale)%> :&nbsp;&nbsp;</td><!--客户名称-->
			<td><input name="cust_name" readonly class="edline" size="40" value="<%=cust_name%>"/>
				<a href="#" onclick="javascript:getCustomer(<%=cust_id%>)">更新客户信息</a>
				<!--  <a href="#" onclick="javascript:showInfo(<%=cust_id%>);">查看详细信息</a>-->
			</td>
		</TR>

		<tr>
			<td align="right" width="120px"><%=LocalUtilis.language("class.preCode2",clientLocale)%> :&nbsp;&nbsp;</td><!--预约编号-->
			<td>
				<input type="text" readonly class="edline" name="pre_code" onkeydown="javascript:nextKeyPress(this);" value="<%=pre_code%>">
				<input type="hidden" name="pre_serial_no" id="pre_serial_no" value="<%=bind_serial_no+"$0"%>">
			</td>
			<td align="right" width="120px"><%=LocalUtilis.language("class.factPreNum2",clientLocale)%> :&nbsp;&nbsp;</td><!--预约金额-->
			<td><input readonly class="edline" name="pre_money" size="20" onkeydown="javascript:nextKeyPress(this)" value="<%=Utility.trimNull(pre_money)%>"></td>
		</tr>
		
		<tr>
			<td align="right"><font color="red"><b>*</b></font>缴款记录 :&nbsp;&nbsp;</td>
			<td>
				<table border="0" cellspacing="2" cellpadding="3">
				<%
				if(pre_serial_no != null && pre_serial_no > 0){
				enfo.crm.marketing.PreMoneyDetailLocal local = EJBFactory.getPreMoneyDetail();
				enfo.crm.vo.PreMoneyDetailVO preVo = new enfo.crm.vo.PreMoneyDetailVO();
				preVo.setPre_serial_no(pre_serial_no);
				String check_box_string = null;
				List preList = local.queryPreMoneyDetail(preVo);
				for (int i = 0; i < preList.size(); i++) {
					Map map = (Map) preList.get(i);
					String check_box_value = Utility.trimNull(map.get("SERIAL_NO"));
					String check_box_content =	Utility.trimNull(map.get("JK_TYPE")) +"-"+ Utility.trimNull(map.get("JK_TYPE_NAME"))
							+"-"+Utility.trimNull(map.get("DZ_DATE"))+"-"+Utility.parseDecimal(Utility.trimNull(map.get("DZ_MONEY")),new BigDecimal(0));
				%>
				<tr>
					<td>		
						<input type='checkbox' name='money_serial_no' value ='<%=check_box_value %>' class='selectAllBox' onclick='javascript:showMoney();'><%=check_box_content %><br>
					</td>
				</tr>	
				<%}} %>	
				</table>	
			</td>
			<%if(user_id.intValue() ==2){ %>
			<td align="right"><font color="red"><b>*</b></font><%=LocalUtilis.language("menu.customerRating",clientLocale)%>:&nbsp;&nbsp; </td>
			<td ><button type="button"  class="xpbutton3"  title='<%=LocalUtilis.language("message.newRating",clientLocale)%>' onclick="javascript:newInfo(document.theform.customer_cust_id.value);"><%=LocalUtilis.language("message.rating",clientLocale)%> </button>
			</td>
			<%} %>
		</tr>
	</table>
	
	<table class="table-popup" border="0" width="100%" cellspacing="4" cellpadding="4" >
		<tr>
			<!--合同序号-->
            <td align="right" width="120px" <%if(Argument.getSyscontrolValue_intrust("C30902")==1){out.print("style='display:none'");}%>>
            	<%=LocalUtilis.language("class.constractBH",clientLocale)%> :&nbsp;&nbsp;
            </td>
			<td <%if(Argument.getSyscontrolValue_intrust("C30902")==1){out.print("style='display:none'");}%>>
				<input name="contract_bh" size="20" maxlength=20 onkeydown="javascript:nextKeyPress(this)" <%if(Argument.getSyscontrolValue_intrust("C30902")==1){out.print("tabindex='-1'");}%>>
			</td>
			<TD align="right" width="120px"><%=LocalUtilis.language("class.contractID",clientLocale)%> :&nbsp;&nbsp;</TD><!--合同编号-->
			<TD>
				<INPUT <%if(is_contract_flow == 1){ %>readonly class="edline"<%} %>id="contract_sub_bh" name="contract_sub_bh" onkeydown=javascript:nextKeyPress(this) maxLength="40" size="40"  value="">&nbsp;&nbsp;
				<SELECT id="selContract" name="selContract" onchange="javascript:selContractNo(this);">
					<%
					StringBuffer sb = new StringBuffer();
					for(int i=0;i<contractNo.length;i++){
						Argument.appendOptions(sb,i,contractNo[i],0) ;
					} 
					out.print(sb.toString());%>
				</SELECT>
				<INPUT type="button" class="xpbutton3" onclick="javascript:check();" value="<%=LocalUtilis.language("messgae.CheckBH",clientLocale)%> "><!--验证编号--><div id="contractDIV"></div>
			</TD>
		</tr>
		<tr>
			<td align="right" width="120px">已使用银行账户 :&nbsp;&nbsp;</td><!--银行名称-->
			<td >
				<select size="1" name="cust_bank" onchange="javascript:showCustBank();">
					<%=Argument.getCustBankList(cust_id)%>
				</select>
			</td>
		</tr>
		<tr>
			<td align="right" width="120px"><font color="red"><b>*</b></font><%=LocalUtilis.language("class.bankName3",clientLocale)%> :&nbsp;&nbsp;</td><!--银行名称-->
			<td >
				<select size="1" name="bank_id" id ="bank_id" style="WIDTH: 120px">
					<%=Argument.getBankOptions(bank_id)%>
				</select>
				<input name="bank_sub_name" size="20" value="" onkeydown="javascript:nextKeyPress(this)">
			</td>
			<td align="right" width="120px"><font color="red"><b>*</b></font><%=LocalUtilis.language("class.bankAcct3",clientLocale)%> :&nbsp;&nbsp;</td><!--银行帐号-->
			<td>
				<input <%if(inputflag2==1) out.print("style=display:none");%> value="<%=Utility.trimNull(bank_acct)%>"  type="text" id="bank_acct" name="bank_acct" onkeydown="javascript:nextKeyPress(this)" onkeyup="javascript:showAcctNum(this.value)"  size="30" >
				<select <%if(inputflag2==2) out.print("style=display:none");%> size="1" name="bank_acct2"  onkeydown="javascript:nextKeyPress(this)"  style="WIDTH: 200px">
					<%=bankOptions%>
				</select>&nbsp;&nbsp;
				<%if(inputflag2==1){%>
				<!-- 输入 -->
                <button type="button"  class="xpbutton2" accessKey=x name="btnPrint" title='<%=LocalUtilis.language("message.input",clientLocale)%>' onclick="javascript:changeInput2(this);"><%=LocalUtilis.language("message.input",clientLocale)%> (<u>X</u>)</button>
				<%}%>
				<span id="bank_acct_num" class="span"></span>
			</td>
		</tr>
		<tr>
			<td align="right"><% if(user_id.intValue() == 2){%><font color="red"><b>*</b></font><%} %>开户行所在地:</td>
			<td>
				<select size="1"  name="bank_province" style="width: 130px" onkeydown="javascript:nextKeyPress(this)" onchange="javascript:setGovRegional(this.value,'');">
					<%=Argument.getCustodianNameLis(new Integer(9999), "", new Integer(0),"")%>
				</select>
				<span id="gov_regional_span">
					<select style="WIDTH: 150px" name="bank_city"><option value="">请选择</option></select>
				</span><!-- 省级行政区域过滤出来的相关区域 -->
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
			<td ><input name="gain_acct" size="20"  onkeydown="javascript:nextKeyPress(this)" value="<%=Utility.trimNull(gain_acct)%>"></td>
		</tr>
		<tr>
			<td align="right" width="120px"><font color="red"><b>*</b></font><%=LocalUtilis.language("class.feeType",clientLocale)%> :&nbsp;&nbsp;</td><!--缴款方式-->
			<td><select size="1" name="jk_type" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px">
				<%=Argument.getJkTypeOptions(jk_type)%>
			</select></td>
			<td align="right" width="120px"><%if(open_flag.intValue()==1){%><%=LocalUtilis.language("class.bonusFlag",clientLocale)%> :<%}%></td><!--收益分配方式-->
			<td><%if(open_flag!=null && open_flag.intValue()==1){%>
				<select size="1" id="bonus_flag" name="bonus_flag" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px">
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
			<td><input name="rg_money" size="20" value="<%=Utility.trimNull(r_rg_money)%>" onkeydown="javascript:nextKeyPress(this)" onkeyup="javascript:showCnMoney(this.value,rg_money_cn)" onblur="javascript:checkSellInfo();"> <span id="rg_money_cn" class="span"></span></td>
			<td align="right" width="120px"><%if(open_flag.intValue()==1){%><%=fee_type_name%><%=LocalUtilis.language("class.feeTypeName",clientLocale)%> :<%}%></td><!--扣缴方式-->
			<td ><%if(open_flag.intValue()==1){%>
				<select size="1" name="fee_jk_type" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px" onblur="javascript:checkSellInfo();">
					<%=Argument.getFeeJkTypeOptions(fee_jk_type.intValue())%>
				</select>
				<%}%>
			</td>
		</tr>
		<tr>
			<td align="right">渠道名称:</td>
			<td>
				<select id="market_trench" name="market_trench" style="width: 280px;" onchange="javascript:setMartetTrench(this);">
					<option value="">请选择</option>
					<%if(markList != null && markList.size() != 0){
					for(int u=0;u<markList.size();u++){
						markMap = (Map)markList.get(u);
					%>
					<option value="<%=Utility.trimNull(markMap.get("CHANNEL_TYPE")) %>@<%=Utility.trimNull(markMap.get("CHANNEL_ID")) %>@<%=Utility.trimNull(Utility.parseDecimal(Utility.trimNull(markMap.get("CHANNEL_FARE_RATE")), new BigDecimal(0),4,"1")) %>"><%=Utility.trimNull(markMap.get("CHANNEL_TYPE_NAME")) %>-<%=Utility.trimNull(markMap.get("CHANNEL_NAME")) %>[费率：<%=Utility.trimNull(Utility.parseDecimal(Utility.trimNull(markMap.get("CHANNEL_FARE_RATE")), new BigDecimal(0),4,"100")) %>]</option>
					<%}} %>
				</select>
			</td>
			<td align="right">渠道费用:</td>
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
		<td align="right"><%=LocalUtilis.language("class.partnAttachmentInfo",clientLocale)%> :</td><!--渠道附加信息-->
		<td>
			<input name="channel_memo" size="1" style="WIDTH: 250px" onkeydown="javascript:nextKeyPress(this)"  value="<%=channel_memo%>">
		</td>
		<td align="right"><font color="red"><b>*</b></font><%=LocalUtilis.language("class.channelCooperation",clientLocale)%> :</td><!-- 渠道合作方式 -->
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
			<td align="right"><% if(user_id.intValue() == 2){%><font color="red"><b>*</b></font><%} %><%=LocalUtilis.language("class.provLevel",clientLocale)%>:</td><!-- 受益优先级 -->
			<td>
				<select id="prov_flag" name="prov_flag" style="WIDTH: 100px" <%if(asfund_flag!=null&&asfund_flag.intValue()==3){%>onchange="javascript:getprovlevel()"<%}%> onkeydown="javascript:nextKeyPress(this)"><option value="">请选择</option>
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
			<td align="right"><% if(user_id.intValue() == 2){%><font color="red"><b>*</b></font><%} %><%=LocalUtilis.language("class.incomeLevel",clientLocale)%>:</td><!-- 收益级别 -->
			<td>
				<div id="div_prov_level">
				<select id="prov_level" name="prov_level" style="width:100px" onchange="javascript:changeProv_level('<%=product_id%>',this.value);"><option value="">请选择</option>
					<%
					if(prov_level==null){
						out.println(Argument.getProvlevelOptions(prov_level));
					}else{
						out.println(Argument.getProductProvLevel(product_id,sub_product_id,prov_flag,prov_level));
					}
					%>
				</select>
				<label>收益率：<span id="span_gain_rate"></span> %</label>
				</div>
			</td>
			<%}
			}%>
			</tr>
			<tr>
				<td align="right"><%=LocalUtilis.language("message.cooperationWay",clientLocale)%>:</td><!--合作方式-->
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
			</tr>
		<%if(!"".equals(Utility.trimNull(cityNameOptions))){%>
			<tr>
				<td align="right" width="120px"><%=LocalUtilis.language("class.citySerialNO",clientLocale)%> :&nbsp;&nbsp;</td><!--合同推介地-->
				<td>
					<select size="1" name="city_serialno" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px">
						<%=cityNameOptions%>
					</select>
				</td>
			</tr>
		<%}%>
			<tr>
				<td align="right">合同联系人:</td>
				<td colspan="3">
					<select size="1" name="cust_message" onkeydown="javascript:nextKeyPress(this)" onchange="newlyIncreased(this.value);">
					</select>&nbsp;
					<button type="button"  class="xpbutton3" id="link_cust" onclick="javascript:showLinkCust();" style="display:none;">明细</button>
				</td>
			</tr>
			<tr>
				<td width="120px" align="right">非现场交易:</td>
				<td>   
					<input type="checkbox" class="flatcheckbox" name="spot_deal" value="1">
				</td>
			</tr>
			<tr>
				<td align="right" vAlign="top" width="120px"><%=LocalUtilis.language("class.description",clientLocale)%> :&nbsp;&nbsp;</td><!--备注-->
				<td  colspan=3><textarea rows="3" name="summary2" onkeydown="javascript:nextKeyPress(this)" cols="100"></textarea></td>
			</tr>
			<tr id="reader2" style="display:">
	          	<td class="paramTitle"align="right"><%=LocalUtilis.language("menu.filesAdd",clientLocale)%>:&nbsp;&nbsp;</td><!-- 添加附件 -->
	            <td colspan="3">
	            	<table id="test" width="100%">
	            		<tr >
	            			<td>
			            	<input type="file" name="file_info" size="60">&nbsp;
			            	<img title="<%=LocalUtilis.language("message.tSelectAdditionalFiles",clientLocale)%> " src="<%=request.getContextPath()%>/images/minihelp.gif"><!--选择附加文件-->
			            	</td>
			            </tr>
			        </table>
			        <button type="button" class="xpbutton6"  onclick="addline()"><%=LocalUtilis.language("class.moreMccessories",clientLocale)%> <!--单击此处添加更多附件--></button>
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
<script language="javascript">
	selContractNo(document.getElementById("selContract"));
</script>
</HTML>
