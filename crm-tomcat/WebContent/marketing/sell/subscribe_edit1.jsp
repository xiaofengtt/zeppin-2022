<%@ page contentType="text/html; charset=GBK" import="enfo.crm.marketing.*,enfo.crm.web.*,enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<% // ��ԤԼ���Ϲ���ͬ �޸�
//���ҳ�洫�ݱ���
String sQuery = request.getParameter("sQuery");
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"), null);//�Ϲ�����
//Integer pre_serial_no = Utility.parseInt(request.getParameter("pre_serial_no"), new Integer(0));//ԤԼ����
String q_productId = Utility.trimNull(request.getParameter("product_id"));  //��Ʒ��ϢID
String q_cust_id = Utility.trimNull(request.getParameter("customer_cust_id")); //�ͻ�ID
String bank_id = Utility.trimNull(request.getParameter("bank_id"));

String channel_type = Utility.trimNull(request.getParameter("channel_type"));
String channel_memo = Utility.trimNull(request.getParameter("channel_memo"));
String channel_cooperation = Utility.trimNull(request.getParameter("channel_cooperation"));
Integer r_channel_id = Utility.parseInt(Utility.trimNull(request.getParameter("channel_id")),new Integer(0));
BigDecimal market_trench_money = Utility.parseDecimal(Utility.trimNull(request.getParameter("market_trench_money")),new BigDecimal(0));

int inputflag=Utility.parseInt(request.getParameter("inputflag"), 1);
int inputflag2=Utility.parseInt(request.getParameter("inputflag2"), 1);
int selfbenflag = Utility.parseInt(request.getParameter("selfbenflag"), 1);//�����־
Integer bonus_flag = Utility.parseInt(request.getParameter("bonus_flag"),new Integer(1));
Integer purchase_mode_flag =  Utility.parseInt(request.getParameter("purchase_mode_flag"), new Integer(0));//�Ӻ�ͬ��Ϣ��ȫ����

Integer fee_jk_type = Utility.parseInt(request.getParameter("fee_jk_type"), new Integer(0));
String jk_type = Utility.trimNull(request.getParameter("jk_type"));
Integer qs_date = Utility.parseInt(Utility.trimNull(request.getParameter("qs_date")),new Integer(0));
Integer jk_date = Utility.parseInt(Utility.trimNull(request.getParameter("jk_date")),new Integer(0));

String bank_acct_type = Utility.trimNull(request.getParameter("bank_acct_type"));

String prov_level =  Utility.trimNull(request.getParameter("prov_level"));
String ht_bank_id =  Utility.trimNull(request.getParameter("ht_bank_id"));
String ht_bank_sub_name =  Utility.trimNull(request.getParameter("ht_bank_sub_name"));
Integer with_bank_flag = Utility.parseInt(request.getParameter("with_bank_flag"), new Integer(0));
Integer with_security_flag = Utility.parseInt(request.getParameter("with_security_flag"), new Integer(0));
Integer with_private_flag = Utility.parseInt(request.getParameter("with_private_flag"), new Integer(0));
Integer prov_flag = Utility.parseInt(request.getParameter("prov_flag"), new Integer(0));
BigDecimal bonus_rate = Utility.parseDecimal(Utility.trimNull(request.getParameter("bonus_rate")),new BigDecimal(0));
Integer link_man = Utility.parseInt(request.getParameter("link_man"), new Integer(0));
Integer valid_period = null;
Integer period_unit = null;
String money_origin = "";
String sub_money_origin = "";
Integer contract_type = new Integer(0);

//�Ŷ���Ϣ
Integer team_id = new Integer(-1);
BigDecimal quota_money = new BigDecimal(0);
BigDecimal already_sale = new BigDecimal(0);
Integer serialNo = new Integer(0);
Integer already_qualified_num = new Integer(0);//�ۼ������۵ĺ�ͬͶ��������

Integer asfund_flag = new Integer(0);
Integer open_flag = new Integer(0);
Integer intrust_flag1 = new Integer(0);

//ִ����Ӳ��� ��Ӹ���
DocumentFile2 file = null;
if(request.getMethod().equals("POST")){
	file = new DocumentFile2(pageContext);
	file.parseRequest();
	/*********��Ϊ������һ�������ϴ�������ԭ��request����ȡֵ�ĸ�Ϊ file*******************/
	sQuery = file.getParameter("sQuery");
	serial_no = Utility.parseInt(file.getParameter("serial_no"), null);//�Ϲ�����
	//pre_serial_no = Utility.parseInt(file.getParameter("pre_serial_no"), new Integer(0));//ԤԼ����
	q_productId = Utility.trimNull(file.getParameter("product_id"));  //��Ʒ��ϢID
	q_cust_id = Utility.trimNull(file.getParameter("customer_cust_id")); //�ͻ�ID
	bank_id = Utility.trimNull(file.getParameter("bank_id"));

	inputflag=Utility.parseInt(file.getParameter("inputflag"), 1);
	inputflag2=Utility.parseInt(file.getParameter("inputflag2"), 1);
	selfbenflag = Utility.parseInt(file.getParameter("selfbenflag"), 1);//�����־
	purchase_mode_flag =  Utility.parseInt(file.getParameter("purchase_mode_flag"), new Integer(0));//�Ӻ�ͬ��Ϣ��ȫ����

	fee_jk_type = Utility.parseInt(file.getParameter("fee_jk_type"), new Integer(0));
	jk_type = Utility.trimNull(file.getParameter("jk_type"));
	qs_date = Utility.parseInt(Utility.trimNull(file.getParameter("qs_date")),new Integer(0));
	jk_date = Utility.parseInt(Utility.trimNull(file.getParameter("jk_date")),new Integer(0));

	bank_acct_type = Utility.trimNull(file.getParameter("bank_acct_type"));

	prov_level =  Utility.trimNull(file.getParameter("prov_level"));
	ht_bank_id =  Utility.trimNull(file.getParameter("ht_bank_id"));
	ht_bank_sub_name =  Utility.trimNull(file.getParameter("ht_bank_sub_name"));
	with_bank_flag = Utility.parseInt(file.getParameter("with_bank_flag"), new Integer(0));
	with_security_flag = Utility.parseInt(file.getParameter("with_security_flag"), new Integer(0));
	with_private_flag = Utility.parseInt(file.getParameter("with_private_flag"), new Integer(0));
	prov_flag = Utility.parseInt(file.getParameter("prov_flag"), new Integer(0));
	bonus_rate = Utility.parseDecimal(Utility.trimNull(file.getParameter("bonus_rate")),new BigDecimal(0));
	valid_period = Utility.parseInt(file.getParameter("valid_period"), null);
	period_unit = Utility.parseInt(file.getParameter("period_unit"), null);
	money_origin=Utility.trimNull(file.getParameter("money_origin"));//�ʽ�/�ʲ���Դ
	sub_money_origin=Utility.trimNull(file.getParameter("sub_money_origin"));//�ʽ�/�ʲ���Դ(����)

	if(bonus_flag.intValue() == 1){//�ֽ�
		bonus_rate = new BigDecimal(0);
	}else if(bonus_flag.intValue() == 2){//ת�ݶ�
		bonus_rate = new BigDecimal(1);
	}else{//����ת�ݶ�
		bonus_rate = Utility.parseDecimal(Utility.trimNull(file.getParameter("bonus_rate")),new BigDecimal(0)).multiply(new BigDecimal("0.01"));
	}
	/****************************/
}

//ҳ������������� ��������ʱ����
input_bookCode = new Integer(1);
boolean bSuccess = false;
int is_product_sub_flag = Argument.getSyscontrolValue_1("SUBCP01");//�Ӳ�Ʒ����
Integer userid = new Integer(2);//�û���˾����
String fee_type_name = enfo.crm.tools.LocalUtilis.language("class.rgFee",clientLocale);//�Ϲ���
String preCodeOptions = "";//ԤԼ���б�
String cityNameOptions = ""; //��ͬ�ƽ��
String bankOptions = "";
String checkedStyle = ""; //������ һЩ��ϢҪ���ֻ��
String checkedStyle2 = "";
List attachmentList = new ArrayList();

//��������
//ID
Integer cust_id = Utility.parseInt(q_cust_id,new Integer(0));
Integer product_id = Utility.parseInt(q_productId,new Integer(0));

//��Ʒ��Ϣ
String period_unit_name = "";
String product_code = "";
String product_status ="";
Integer pre_start_date = new Integer(0);
Integer pre_end_date = new Integer(0);
Integer product_sub_flag = new Integer(0);//�Ӳ�Ʒ��־
List markList = null;
Map markMap = null;
String market_selected = "";

//ԤԼ�����Ϣ
Integer pre_cust_id = new Integer(0);
String pre_cust_name = "";
String pre_cust_no = "";
String pre_code ="";
BigDecimal pre_money = new BigDecimal(0);

//�Ϲ���ͬ��Ϣ
String cust_no ="";
String cust_name = "";
String card_id = "";
String contract_bh = "";//��ͬ���
String contract_sub_bh = "";
String summary = "";
String bank_sub_name="";
String bank_acct = "";
String gain_acct = "";
Integer service_man = new Integer(0);
Integer check_flag = new Integer(0);
Integer citySerialno = new Integer(0);
BigDecimal rg_money = new BigDecimal(0);
Integer sub_product_id = new Integer(0);
Integer link_man2 = new Integer(0);
Integer contact_id = new Integer(0);
String bank_province = "";
String bank_city = "";
String ht_cust_name = "";
String ht_cust_address = "";
String ht_cust_tel = "";
//------------------------------------

//��ö���
ContractLocal contract = EJBFactory.getContract();//��ͬ
ProductLocal product = EJBFactory.getProduct();//��Ʒ
PreContractLocal preContract = EJBFactory.getPreContract();//ԤԼ
ChannelQueryLocal channel = EJBFactory.getChannelQuery();//�޸����������

ContractVO cont_vo = new ContractVO();
ProductVO p_vo = new ProductVO();
PreContractVO pre_vo = new PreContractVO();
SaleParameterVO salevo = new SaleParameterVO();
SaleParameterLocal sale_parameter = EJBFactory.getSaleParameter();

if(request.getMethod().equals("POST")){
	//��ñ�������
	Integer r_product_id = Utility.parseInt(file.getParameter("productId"), null);
	String r_product_name = Argument.getProductName(Utility.parseInt(file.getParameter("product_id"), null));
	Integer r_cust_id = Utility.parseInt(file.getParameter("customer_cust_id"), null);
	String r_pre_code = file.getParameter("pre_code");
	Integer r_qs_date = Utility.parseInt(file.getParameter("qs_date"), new Integer(Utility.getCurrentDate()));
	Integer r_jk_date = Utility.parseInt(file.getParameter("jk_date"), new Integer(Utility.getCurrentDate()));//�Ĳ��������ϣ�������㴫�뵱ǰʱ��
	BigDecimal r_rg_money = Utility.parseDecimal(file.getParameter("rg_money"),new BigDecimal(0));
	String r_jk_type = file.getParameter("jk_type");
	String r_bank_id = file.getParameter("bank_id");
	String r_bank_sub_name = Utility.trimNull(file.getParameter("bank_sub_name"));
	String r_gain_acct = file.getParameter("gain_acct");
	int r_fee_jk_type = Utility.parseInt(file.getParameter("fee_jk_type"),0);
	String r_bank_acct_type = Utility.trimNull(file.getParameter("bank_acct_type"));
	Integer r_bonus_flag = Utility.parseInt(file.getParameter("bonus_flag"),new Integer(1));
	Integer s_product_id = Utility.parseInt(file.getParameter("r_product_id"), new Integer(0));
	String r_bank_acct = "";

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
	Integer r_link_man = Utility.parseInt(file.getParameter("link_man"), new Integer(0));
	r_channel_id = Utility.parseInt(Utility.trimNull(file.getParameter("channel_id")),new Integer(0));
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
	check_flag = Utility.parseInt(file.getParameter("check_flag"), new Integer(0));
	
	BigDecimal r_bonus_rate = new BigDecimal(0);
	//contact_id = Utility.parseInt(Utility.trimNull(file.getParameter("cust_message")),new Integer(0));
	ht_cust_name = Utility.trimNull(file.getParameter("ht_cust_name"));
	ht_cust_address = Utility.trimNull(file.getParameter("ht_cust_address"));
	ht_cust_tel = Utility.trimNull(file.getParameter("ht_cust_tel"));

	if(r_bonus_flag.intValue() == 1){//�ֽ�
		r_bonus_rate = new BigDecimal(0);
	}else if(r_bonus_flag.intValue() == 2){//ת�ݶ�
		r_bonus_rate = new BigDecimal(1);
	}else{//����ת�ݶ�
		r_bonus_rate = Utility.parseDecimal(Utility.trimNull(file.getParameter("bonus_rate")),new BigDecimal(0)).multiply(new BigDecimal("0.01"));
	}

	//ͬ���Ŷ���Ϣ
	Integer r_team_id = Utility.parseInt(Utility.trimNull(file.getParameter("team_id")),new Integer(-1));
	Integer r_serialNo = Utility.parseInt(Utility.trimNull(file.getParameter("serialNo")),new Integer(0));
	BigDecimal r_already_sale = Utility.parseDecimal(Utility.trimNull(file.getParameter("already_sale")),new BigDecimal(0));
	Integer r_already_qualified_num = Utility.parseInt(Utility.trimNull(file.getParameter("already_qualified_num")),new Integer(0));
	
	contract_type = Utility.parseInt(Utility.trimNull(file.getParameter("contract_type")),new Integer(0));
	
	r_already_sale = r_already_sale.add(r_rg_money);

	//��������
	ContractVO s_cont_vo = new ContractVO();

	if(check_flag.intValue()>=2){
		//�޸�����˵��Ϲ���ͬ����
		s_cont_vo.setSerial_no(serial_no);
		s_cont_vo.setContract_sub_bh(r_contract_sub_bh);
		s_cont_vo.setBank_id(r_bank_id);
		s_cont_vo.setBank_sub_name(r_bank_sub_name);
		s_cont_vo.setBank_acct(r_bank_acct);
		s_cont_vo.setQs_date(r_qs_date);
		s_cont_vo.setJk_date(r_jk_date);
		s_cont_vo.setJk_type(r_jk_type);
		s_cont_vo.setSummary(r_summary);
		s_cont_vo.setProv_flag(r_prov_flag);
		s_cont_vo.setProv_level(r_prov_level);

		s_cont_vo.setChannel_id(r_channel_id);
		s_cont_vo.setChannel_type(channel_type);
		s_cont_vo.setChannel_memo(channel_memo);

		channel.modiContractChannel(s_cont_vo,input_operatorCode);

	}else{
		//�޸�δ��˵��Ϲ���ͬ
		//ͬ���Ŷ���Ϣ
		salevo.setSerial_NO(r_serialNo);
		salevo.setTeamID(r_team_id);
		salevo.setProductID(s_product_id);
		salevo.setSerial_no_subscribe(serial_no);
		salevo.setAlreadysale(r_already_sale);
		salevo.setAlready_qualified_num(r_already_qualified_num);
		sale_parameter.modiAlreadysale(salevo,input_operatorCode);

		//�����ͬ��Ϣ
		s_cont_vo.setSerial_no(serial_no);
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
		s_cont_vo.setLink_man(r_link_man);
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
		//s_cont_vo.setContact_id(contact_id);
		s_cont_vo.setHt_cust_name(ht_cust_name);
		s_cont_vo.setHt_cust_address(ht_cust_address);
		s_cont_vo.setHt_cust_tel(ht_cust_tel);
		s_cont_vo.setValid_period(valid_period);
		s_cont_vo.setPeriod_unit(period_unit);
		s_cont_vo.setMoney_origin(money_origin);
		s_cont_vo.setSub_money_origin(sub_money_origin);
		s_cont_vo.setContract_type(contract_type);
		
		contract.save(s_cont_vo);
		if(file.uploadAttchment(new Integer(5),serial_no,"",null,null,input_operatorCode))
			bSuccess = true;
	}

	bSuccess = true;
}

//��ȡ�Ϲ���ͬ��Ϣ
if(serial_no.intValue()>0){
	List rsList_contract = null;
	Map map_contract = null;

	cont_vo.setSerial_no(serial_no);
	rsList_contract = contract.load(cont_vo);

	if(rsList_contract.size()>0){
		map_contract = (Map)rsList_contract.get(0);
	}

	if(map_contract!=null){
		cust_id =  Utility.parseInt(Utility.trimNull(map_contract.get("CUST_ID")), new Integer(0));
		product_id =  Utility.parseInt(Utility.trimNull(map_contract.get("PRODUCT_ID")), new Integer(0));
		service_man = Utility.parseInt(Utility.trimNull(map_contract.get("SERVICE_MAN")), new Integer(0));

		cust_name = Utility.trimNull(map_contract.get("CUST_NAME"));
		cust_no =  Utility.trimNull(map_contract.get("CUST_NO"));

		contract_sub_bh = Utility.trimNull(map_contract.get("CONTRACT_SUB_BH"));
		bank_id = Utility.trimNull(map_contract.get("BANK_ID"));
		bank_sub_name = Utility.trimNull(map_contract.get("BANK_SUB_NAME"));
		bank_acct_type = Utility.trimNull(map_contract.get("BANK_ACCT_TYPE"));
		gain_acct =  Utility.trimNull(map_contract.get("GAIN_ACCT"));

		jk_type =  Utility.trimNull(map_contract.get("JK_TYPE"));
		bonus_flag = Utility.parseInt(Utility.trimNull(map_contract.get("BONUS_FLAG")), new Integer(0));
		rg_money = Utility.parseDecimal(Utility.trimNull(map_contract.get("RG_MONEY")),new BigDecimal(0));
		fee_jk_type = Utility.parseInt(Utility.trimNull(map_contract.get("FEE_JK_TYPE")), new Integer(0));

		qs_date = Utility.parseInt(Utility.trimNull(map_contract.get("QS_DATE")), new Integer(0));
		jk_date = Utility.parseInt(Utility.trimNull(map_contract.get("JK_DATE")), new Integer(0));
		link_man2 =  Utility.parseInt(Utility.trimNull(map_contract.get("LINK_MAN")), new Integer(0));
		if(link_man.intValue() == 0){
			link_man =  link_man2;
		}
		citySerialno =  Utility.parseInt(Utility.trimNull(map_contract.get("CITY_SERIAL_NO")), new Integer(0));
		summary  = Utility.trimNull(map_contract.get("SUMMARY"));
		card_id = Utility.trimNull(map_contract.get("CARD_ID"));
		bank_acct = Utility.trimNull(map_contract.get("BANK_ACCT"));
		pre_code =  Utility.trimNull(map_contract.get("PRE_CODE"));
		check_flag = Utility.parseInt(Utility.trimNull(map_contract.get("CHECK_FLAG")),new Integer(0));
		sub_product_id =  Utility.parseInt(Utility.trimNull(map_contract.get("SUB_PRODUCT_ID")),new Integer(0));
		r_channel_id = Utility.parseInt(Utility.trimNull(map_contract.get("CHANNEL_ID")),new Integer(0));
        market_trench_money = Utility.parseDecimal(Utility.trimNull(map_contract.get("MARKET_MONEY")),new BigDecimal(0));
		channel_memo = Utility.trimNull(map_contract.get("CHANNEL_MEMO"));
		channel_cooperation = Utility.trimNull(map_contract.get("CHANNEL_COOPERTYPE"));
		channel_type = Utility.trimNull(map_contract.get("CHANNEL_TYPE"));
		with_bank_flag = Utility.parseInt(Utility.trimNull(map_contract.get("WITH_BANK_FLAG")), new Integer(0));
		with_security_flag = Utility.parseInt(Utility.trimNull(map_contract.get("WITH_SECURITY_FLAG")), new Integer(0));
		with_private_flag = Utility.parseInt(Utility.trimNull(map_contract.get("WITH_PRIVATE_FLAG")), new Integer(0));
		prov_flag = Utility.parseInt(Utility.trimNull(map_contract.get("PROV_FLAG")), new Integer(0));
		prov_level = Utility.trimNull(map_contract.get("PROV_LEVEL"));
		ht_bank_id =  Utility.trimNull(map_contract.get("HT_BANK_ID"));
		ht_bank_sub_name =  Utility.trimNull(map_contract.get("HT_BANK_SUB_NAME"));
		bonus_rate = Utility.parseDecimal(Utility.trimNull(map_contract.get("BONUS_RATE")),new BigDecimal(0));
		//contact_id = Utility.parseInt(Utility.trimNull(map_contract.get("CONTACT_ID")), new Integer(0));
		ht_cust_name = Utility.trimNull(map_contract.get("HT_CUST_NAME"));
		ht_cust_address = Utility.trimNull(map_contract.get("HT_CUST_ADDRESS"));
		ht_cust_tel = Utility.trimNull(map_contract.get("HT_CUST_TEL"));
		bank_province =  Utility.trimNull(map_contract.get("BANK_PROVINCE"));
		bank_city =  Utility.trimNull(map_contract.get("BANK_CITY"));
		valid_period = (Integer)map_contract.get("VALID_PERIOD");
		period_unit = (Integer)map_contract.get("PERIOD_UNIT");
		money_origin = Utility.trimNull(map_contract.get("BEN_MONEY_ORIGIN"));
		sub_money_origin = Utility.trimNull(map_contract.get("BEN_SUB_MONEY_ORIGIN"));

		contract_type = Utility.parseInt(Utility.trimNull(map_contract.get("CONTRACT_TYPE")), new Integer(0));

		if(check_flag.intValue()==2){
			checkedStyle = "readonly class='edline'";
			checkedStyle2 = "disabled";
		}

		if(!pre_code.equals("")){
			cont_vo = new ContractVO();
			cont_vo.setBook_code(input_bookCode);
			cont_vo.setProduct_id(product_id);
			cont_vo.setPre_code(pre_code);

			pre_money = contract.getPrecontract_premoney(cont_vo);
		}
	}

	bankOptions =Argument.getCustBankAcctOptions(cust_id,bank_id,card_id,bank_acct);

	//����Ϲ�����
	AttachmentLocal attachmentLocal = EJBFactory.getAttachment();
	AttachmentVO attachment_vo = new AttachmentVO();
	attachment_vo.setDf_talbe_id(new Integer(5));
	attachment_vo.setDf_serial_no(serial_no);

	attachmentList = attachmentLocal.load(attachment_vo);
}

//��Ʒ��Ϣ��ʾ ��ѯ��ӦԤԼ���б�
if(product_id.intValue()>0){
	List rsList_product = null;
	Map map_product = null;

	//ȡ������Ʒֵ
	p_vo.setProduct_id(product_id);
	rsList_product = product.load(p_vo);

	if(rsList_product.size()>0){
		map_product = (Map)rsList_product.get(0);
	}
	//��Ʒ����������Ϣ
	markList = product.queryMarketTrench(p_vo);

	if(map_product!=null){
		open_flag = Utility.parseInt(Utility.trimNull(map_product.get("OPEN_FLAG")),new Integer(0));
		intrust_flag1 = Utility.parseInt(Utility.trimNull(map_product.get("INTRUST_FLAG1")),new Integer(0));
		product_code = Utility.trimNull(map_product.get("PRODUCT_CODE"));
		product_status = Utility.trimNull(map_product.get("PRODUCT_STATUS"));
		//period_unit = Utility.parseInt(Utility.trimNull(map_product.get("PERIOD_UNIT")),new Integer(0));//��ͬ����
		//valid_period = Utility.parseInt(Utility.trimNull(map_product.get("VALID_PERIOD")),new Integer(0));
		pre_start_date =  Utility.parseInt(Utility.trimNull(map_product.get("PRE_STRAT_DATE")),new Integer(0));
		pre_end_date =  Utility.parseInt(Utility.trimNull(map_product.get("PRE_END_DATE")),new Integer(0));
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

		/*if(period_unit.intValue()>0){
			period_unit_name = Argument.getProductUnitName(period_unit);
		}*/

		if(product_status.equals("110203")){
			fee_type_name = enfo.crm.tools.LocalUtilis.language("class.sgFeeAmount",clientLocale);//�깺��
		}
	}
}

//�Ϲ�������Ĵ���
String rg_money_cn = "";
if (rg_money.intValue()!= 0){
	rg_money_cn = "(" + Utility.numToChinese(rg_money.toString()) + ")";
}

cityNameOptions = Argument.getCitynameOptions(product_id,citySerialno);

if(bankOptions.equals("<option value=\"\">"+enfo.crm.tools.LocalUtilis.language("message.pleaseSelect",clientLocale)+"</option>")||bankOptions.equals("")){
//"<option value=\"\">��ѡ��</option>")||bankOptions.equals("")
	inputflag2=2;
}

//�鿴�û������Ŷ���Ϣ
team_id = Utility.parseInt(Utility.trimNull(Argument.getTeam(link_man)),new Integer(-1));
Integer team_id2 = Utility.parseInt(Utility.trimNull(Argument.getTeam(link_man2)),new Integer(-1));

salevo.setTeamID(team_id);
salevo.setProductID(product_id);
List saleList = sale_parameter.queryQuota(salevo,input_operatorCode);
if(saleList.size()>0){
	Map map_sale = (Map)saleList.get(0);
	serialNo = Utility.parseInt(Utility.trimNull(map_sale.get("SERIAL_NO")), new Integer(0));
	quota_money = Utility.parseDecimal(Utility.trimNull(map_sale.get("QUOTAMONEY")),new BigDecimal(0));
	already_sale = Utility.parseDecimal(Utility.trimNull(map_sale.get("ALREADYSALE")),new BigDecimal(0));
	already_qualified_num = Utility.parseInt(Utility.trimNull(map_sale.get("ALREADY_QUALIFIED_NUM")),new Integer(0));
}

if (team_id.intValue() == team_id2.intValue())	already_sale = already_sale.subtract(rg_money);
%>
<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("menu.editSubscriptionInfo",clientLocale)%>��<%=LocalUtilis.language("message.booked",clientLocale)%> ��</TITLE><!--�Ϲ���Ϣ�༭--><!--��ԤԼ-->
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
/*δ��˱���*/
function SaveAction(){
	if(document.theform.onsubmit()){
		disableAllBtn(true);
		document.theform.action="subscribe_edit1.jsp" ;
		document.theform.submit();
	}
}
/*����˱���*/
function SaveChannelAction(){
	if(sl_check_update()){
	if(intrustType_Flag == '1'){
		document.theform.channel_type.value = comboBoxTree.getValue();
		document.theform.channel_id.value = comboBoxParentTree.getValue();
	}
		disableAllBtn(true);
		document.theform.action="subscribe_edit2.jsp";
		document.theform.submit();
	}
}

/*����*/
function CancelAction(){
	tempArray = '<%=sQuery%>'.split('$');
	var purchase_mode_flag = document.theform.purchase_mode_flag.value;
	if(purchase_mode_flag==1){
		location = 'purchase_mode.jsp';
	}
	else{
		location = 'subscribe_list.jsp?page='+tempArray[12]+'&q_productId='+tempArray[0]+'&q_productCode='+tempArray[1]
				+'&q_cust_name='+tempArray[2]+'&query_contract_bh='+tempArray[3]+'&q_pre_flag='+tempArray[4]+'&pagesize='+tempArray[5]+'&q_check_flag='+tempArray[6]+"&q_product_name="+tempArray[7]+"&q_managerID="+tempArray[8]+"&q_team_id="+tempArray[9]+"&q_card_id="+tempArray[10]+"&q_channel_id="+tempArray[11];
	
	}
}

/*�����������Ϲ���Ϣʱ�õ��ĺ���*/
function validateForm(form){
	<%if(Argument.getSyscontrolValue_intrust("C30902")!=1){%>
		var contract_bh=form.contract_bh;

		if(!sl_check(contract_bh, '<%=LocalUtilis.language("class.constractBH",clientLocale)%> ',20,0))	return false;//��ͬ���
		if(contract_bh.value!= "" && contract_bh.value != null){
		  var s=contract_bh.value;
		  var len=contract_bh.value.length;
          var a=new Array();

		  if(len<3){
		    sl_alert('<%=LocalUtilis.language("message.inputContractBHTip",clientLocale)%> ');//����ĺ�ͬ�������Ϊ3λ
		    return false;
		  }else{
		     if((s.charCodeAt(len-1)<48 || s.charCodeAt(len-1)>57) || (s.charCodeAt(len-2)<48 || s.charCodeAt(len-2)>57)|| (s.charCodeAt(len-3)<48 || s.charCodeAt(len-3)>57)){
		        sl_alert('<%=LocalUtilis.language("messgae.contractSerialError",clientLocale)%> ��');//��ͬ��������λ���������֣�����������д��
		        return false;
		     }
		  }
		}
	<%}%>
	if(!sl_checkChoice(form.bank_id, '<%=LocalUtilis.language("class.bankName3",clientLocale)%> ')) return false;//��������

	if(form.bank_id.value!=""){
		if(document.theform.inputflag2.value==1){
			if(!sl_checkChoice(form.bank_acct2, '<%=LocalUtilis.language("class.bankAcct3",clientLocale)%> '))return false;//�����ʺ�
		}
		else{
			if(!sl_check(form.bank_acct, '<%=LocalUtilis.language("class.bankAcct3",clientLocale)%> ', 40, 1)) return false;//�����ʺ�
		}
	}

	if(!sl_checkDecimal(form.rg_money, '<%=LocalUtilis.language("class.rg_money",clientLocale)%> ', 13, 3, 1))return false;//�Ϲ����
	if(!sl_checkChoice(form.jk_type, '<%=LocalUtilis.language("class.feeType",clientLocale)%> '))return false;//�ɿʽ

	//trim_money(form.pre_money);
	form.pre_money.value = form.pre_money.value.replace(",","");
	strTemp="";

	if(parseFloat(form.pre_money.value)<parseFloat(form.rg_money.value)){
		strTemp='<%=LocalUtilis.language("message.subscriptionAmountError",clientLocale)%> !';//�Ϲ�����Ѵ���ԤԼ���
		sl_alert(strTemp);
	}

	if(!sl_checkDate(form.qs_date_picker,'<%=LocalUtilis.language("class.qsDate",clientLocale)%> '))	return false;//ǩ������
	if(!sl_checkDate(form.jk_date_picker,'<%=LocalUtilis.language("class.dzDate",clientLocale)%> '))	return false;	//�ɿ�����

	syncDatePicker(form.qs_date_picker, form.qs_date);
	syncDatePicker(form.jk_date_picker, form.jk_date);
	//if(!sl_checkChoice(form.market_trench, '�������� ')){return false;}//��������
    if(!sl_checkChoice(form.channel_cooperation, '����������ʽ ')){return false;}//����������ʽ
	var userid='<%=userid.intValue()%>';

	//if(intrustType_Flag == '1'){
		//document.theform.channel_type.value = comboBoxTree.getValue();
		//document.theform.channel_id.value = comboBoxParentTree.getValue();
	//}
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
			if(!sl_checkDecimal(form.bonus_rate, "ת�ݶ����", 3, 2, 0))	return false;
			if(parseFloat(form.bonus_rate.value) <= parseFloat('0') || parseFloat(form.bonus_rate.value) >= parseFloat('100')){
				sl_alert("ת�ݶ����ȡֵ����Ϊ0��100֮��(������0��100)");
				return false;
			}
			//if(!isBetween(form.bonus_rate,0,100,2,"ת�ݶ����ȡֵ����Ϊ0��100֮��(������0��100)")) return false;
		}
	<%}%>
	if(0!= 0){
		var sum = parseInt(form.rg_money.value) + <%=already_sale%>;
		if(sum > <%=quota_money%>){
			sl_alert('<%=LocalUtilis.language("message.teamQuota",clientLocale)%>');//�û������Ŷ��ڸò�Ʒ���ۼ������۶�����ֵ
			return false;
		}
	}

	return sl_check_update();
}

/**************************************************************************************************************************/
/*��֤��ͬ���*/
function check(){
	$("contractDIV").innerHTML = "";
	if(!sl_check(document.theform.contract_sub_bh, '<%=LocalUtilis.language("class.contractID",clientLocale)%> '))return false; //��ͬ���

	var book_code=DWRUtil.getValue("book_code");
	var contract_type=new Number(1);
	var product_id=DWRUtil.getValue("product_id");
	var contract_sub =DWRUtil.getValue("contract_sub_bh");

	if(product_id==0){
		sl_alert('<%=LocalUtilis.language("message.chooseProdTip",clientLocale)%> !');//����ѡ���Ʒ
		return false;
	}
	contract.findIfExistSameBH(book_code,contract_type,product_id,contract_sub,callContractSubBHCallBack);
}

/*�������*/
function callContractSubBHCallBack(result){
	if (result!="")	$("contractDIV").innerHTML = result;
}

/**************************************************************************************************************************/
/*ԤԼ�ű��*/
function changeInput(obj){
	if(document.theform.inputflag.value==1){
		obj.innerText='<%=LocalUtilis.language("message.choose",clientLocale)%> ';//ѡ��
		document.theform.pre_code.style.display="";
		document.theform.pre_serial_no.style.display="none";
		document.theform.inputflag.value=2;
	}else{
		obj.innerText='<%=LocalUtilis.language("message.input",clientLocale)%> ';//����
		document.theform.pre_code.style.display="none";
		document.theform.pre_serial_no.style.display="";
		document.theform.inputflag.value=1;
	}
}

/*�����˺ű��*/
function changeInput2(obj){
	if(document.theform.inputflag2.value==1){
		obj.innerText='<%=LocalUtilis.language("message.choose",clientLocale)%> ';//ѡ��
		document.theform.bank_acct.style.display="";
		document.theform.bank_acct2.style.display="none";
		document.theform.inputflag2.value=2;
	} else {
		obj.innerText='<%=LocalUtilis.language("message.input",clientLocale)%> ';//����
		document.theform.bank_acct.style.display="none";
		document.theform.bank_acct2.style.display="";
		document.theform.inputflag2.value=1;
	}
}

/*��ʾ�˺�λ��*/
function showAcctNum(value){
	if (trim(value) == "")
		bank_acct_num.innerText = "";
	else
		bank_acct_num.innerText = "(" + showLength(value) + " λ )";
}

/*���ò�Ʒ���*/
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

		if (prodid!=0){
			selectPrecodeItem();
		}

		if (prodid==0){
			sl_alert('<%=LocalUtilis.language("message.inputProdIDError",clientLocale)%> ��');//����Ĳ�Ʒ��Ų����ڻ��߸ò�Ʒ�����ƽ���
			document.theform.productid.value="";
			document.theform.product_id.options[0].selected=true;
		}
	}

	nextKeyPress(this);
}

/**************************************************************************************************************************/

/*�趨ԤԼ��*/
function selectPrecodeItem(preSerialNo){
	if(preSerialNo>0){
		contract.getPreContractInfo(preSerialNo,{callback: function(data){
			document.theform.cust_no.value = data[2];
			document.theform.cust_name.value = data[1];
			document.theform.pre_money.value = data[4];
			document.theform.rg_money.value = data[4];
		}});
	}
}

/*���ݿͻ����Ʋ���*/
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
			sl_alert('<%=LocalUtilis.language("message.custNameTip",clientLocale)%> ��');//����Ŀͻ����Ʋ�����
			document.theform.cust_name.value="";
			document.theform.pre_serial_no.options[0].selected=true;
		}
	}
}

/*�ֹ�����ԤԼ��*/
function findPreNo(obj){
	var i=0;
	var values=obj.value;
	var count=0;
	var len = document.theform.pre_serial_no.options.length;

	if(!sl_checkNum(obj,'<%=LocalUtilis.language("class.preCode2",clientLocale)%> ',16,0)){return false;}//ԤԼ���

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
			sl_alert("<%=LocalUtilis.language("message.bookingNumTip",clientLocale)%> ��");//ԤԼ��Ų�����
			obj.focus();
			return false;
		}
	}
	else{
		nextKeyPress(obj);
	}
}

/*���ò�Ʒ���ͻ���Ϣ*/
function selectProductAndCustomer(){
	var url = "subscribe_add1.jsp?page=<%=sPage%>" + getLastOptions();
	window.location.href = url;
}

/**************************************************************************************************************************/

/*ƴ��׺����*/
function getLastOptions(){
	if(!sl_checkDate(document.theform.qs_date_picker,'<%=LocalUtilis.language("class.qsDate",clientLocale)%> ')){return false};//ǩ������
	syncDatePicker(document.theform.qs_date_picker, document.theform.qs_date);

	var temp="";
	var temp = temp + "&product_id="+ document.theform.product_id.value;
	var temp = temp + "&jk_type=" + document.theform.jk_type.value;
	var temp = temp + "&sub_product_id=" + document.theform.sub_product_id.value;
	var temp = temp + "&qs_date=" + document.theform.qs_date.value ;
	var temp = temp + "&contract_bh=" + document.theform.contract_bh.value ;
	var temp = temp + "&gain_acct="+ document.theform.gain_acct.value ;
	var temp = temp + "&sQuery=" + document.theform.sQuery.value ;
	var temp = temp + "&contract_sub_bh=" + document.theform.contract_sub_bh.value ;
	var temp = temp +"&link_man="+document.theform.link_man.value;

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
	<%if(product_id!=null&&product_id.intValue()!=0&&open_flag.intValue()==1){%>
		var temp = temp + "&bonus_flag="+document.theform.bonus_flag.value
	<%}%>
	return temp;
}

//�鿴�ͻ���Ϣ
function getCustomer(cust_id){
	var url = '<%=request.getContextPath()%>/marketing/customerInfo2.jsp?cust_id='+ cust_id ;
	var v = showModalDialog(url,'','dialogWidth:720px;dialogHeight:720px;status:0;help:0;');
}

function openSubProduct(){
	var product_id = document.getElementsByName("product_id")[0].value;
	var sub_product_id = document.getElementsByName("sub_product_id")[0].value;

	if(product_id!=null&&product_id!=0&&sub_product_id!=null&&sub_product_id!=0){
		var url = "<%=request.getContextPath()%>/marketing/base/sub_product_view.jsp?product_id="+product_id+"&sub_product_id="+sub_product_id;
		showModalDialog(url, '', 'dialogWidth:600px;dialogHeight:420px;status:0;help:0');
	}
	else{
		sl_alert("��ѡ���Ʒ��");
	}
}

var n=1;
/*
 *��Ӹ���
 */
function addline(){
	n++;
	newline=document.all.test.insertRow()
	newline.id="fileRow"+n;
	newline.insertCell().innerHTML="<input type='file' name=file_info"+n+" size='60'>"+"<button type="button"  onclick='javascript:removeline(this)'><%=LocalUtilis.language("messgae.remove",clientLocale)%> </button>";//�Ƴ�
}

/*
 *ɾ������
 */
function removeline(obj){
	var objSourceRow=obj.parentNode.parentNode;
	var objTable=obj.parentNode.parentNode.parentNode.parentNode;
	objTable.lastChild.removeChild(objSourceRow);
}

function confirmRemoveAttach(obj,serial_no){
	if (confirm('<%=LocalUtilis.language("message.tAreYouSure",clientLocale)%>��')) {
		var updatehref = 'attach_remove.jsp?serial_no='+serial_no;
    	showModalDialog(updatehref, '', 'dialogWidth:0px;dialogHeight:0px;dialogLeft:0px;dialogTop:0px;status:0;help:0');
		if (obj) obj.style.display="none";
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
       	 	root : new Ext.tree.AsyncTreeNode({id:'-1',text:'<%=LocalUtilis.language("class.partnType",clientLocale)%> '}),//������Ϣ
       	 	listeners:{
       	 		beforeload:function(node){//ѡ���������ֵ֮ǰ���¼�
       	 			if(node.id!='-1')
       	 				this.loader.dataUrl = '<%=request.getContextPath()%>/client/channel/treeData.jsp?value='+node.id;
       	 		},
       	 		click:function(node)
       	 		{
           	 		channelTree(node.id);
           	 	}
       	 	}
    	},

		//all:���н�㶼��ѡ��
		//exceptRoot��������㣬������㶼��ѡ(Ĭ��)
		//folder:ֻ��Ŀ¼����Ҷ�ӺͷǸ���㣩��ѡ
		//leaf��ֻ��Ҷ�ӽ���ѡ
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
       	 	root : new Ext.tree.AsyncTreeNode({id:'-1',text:'<%=LocalUtilis.language("class.partnName",clientLocale)%> '}),//������Ϣ
       	 	listeners:{
       	 		beforeload:function(node){
       	 			if(node.id!='-1')
       	 				this.loader.dataUrl = '<%=request.getContextPath()%>/client/channel/parentTree.jsp?value='+node.id+'&channel_type=<%=channel_type%>';
       	       	 }
       	 	}
    	},

		//all:���н�㶼��ѡ��
		//exceptRoot��������㣬������㶼��ѡ(Ĭ��)
		//folder:ֻ��Ŀ¼����Ҷ�ӺͷǸ���㣩��ѡ
		//leaf��ֻ��Ҷ�ӽ���ѡ
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
       	 	root : new Ext.tree.AsyncTreeNode({id:'-1',text:'<%=LocalUtilis.language("class.partnName",clientLocale)%> '}),//������Ϣ
       	 	listeners:{
       	 		beforeload:function(node){
       	 			if(node.id!='-1')
       	 				this.loader.dataUrl = '<%=request.getContextPath()%>/client/channel/parentTree.jsp?value='+node.id+'&channel_type='+comboBoxTree.getValue();
       	       	 }
       	 	}
    	},

		//all:���н�㶼��ѡ��
		//exceptRoot��������㣬������㶼��ѡ(Ĭ��)
		//folder:ֻ��Ŀ¼����Ҷ�ӺͷǸ���㣩��ѡ
		//leaf��ֻ��Ҷ�ӽ���ѡ
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

/*�鿴�Ŷ���Ϣ*/
function selectTeam(value){
	if (value!=""){
		location = 'subscribe_edit1.jsp?link_man=' + value+ '&serial_no='+document.theform.serial_no.value;
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

function checkSellInfo(){
	var channel_rate = document.theform.channel_rate.value
	var rg_money = document.theform.rg_money.value;
	if(rg_money != "" && channel_rate != "")
		document.theform.market_trench_money.value = (Number(rg_money) * Number(channel_rate)).toFixed(2);
	else
		document.theform.market_trench_money.value = "";
	return true;
}

//�Ϲ���ͬ��ϵ��
function showCustOptions(cust_id,contact_id){
	DWRUtil.removeAllOptions("cust_message");
	DWRUtil.addOptions("cust_message",{"":"��ѡ��"});
	contract.getCustOptions(cust_id,function(date){
			var json=date;
			for (i=0;i<json.length;i++)	DWRUtil.addOptions("cust_message",json[i]);    
	    	
			DWRUtil.addOptions("cust_message",{"0":"�½�"});	
		}				
	);
}

//������ͬ��ϵ��
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
	if (showModalDialog(url,'','dialogWidth:900px;dialogHeight:420px;status:0;help:0;scroll:0')){
		sl_update_ok();
		showCustOptions(cust_id);
	}
}

/*ͨ��ʡ�й�����ص���������*/
function setGovRegional(value,flag){
	if (value != "" && value.length > 0) {
		DWREngine.setAsync(false); // sync
		utilityService.getGovRegional(9999, value, flag,backGovRegional);
		DWREngine.setAsync(true); // async
	}
}

/*ͨ��ʡ�й�����ص��������� �ص�����*/
function backGovRegional(data){
	document.getElementById("gov_regional_span").innerHTML = "<select size='1'  name='bank_city' style='width: 150px' onkeydown='javascript:nextKeyPress(this)'>"+data+"</select>";
}

var acct_arr = [];
function selectBank(value) { //ѡ������
	var cust_id = DWRUtil.getValue("customer_cust_id");

	if (cust_id == 0) {
		sl_alert("����ѡ��������!");
		return false;
	}
	var obj = document.theform.bank_acct2;
	utilityService.getBankAcctOption(cust_id,value,'','',
		{ callback:
			function(data){
				DWRUtil.removeAllOptions(obj);
				var json = eval(data);
				acct_arr = json;
				document.theform.inputflag2.value = json.length?2:1;
				changeInput2(document.theform.btnPrint);	
				obj.options.add(new Option("��ѡ��", ""));
				DWRUtil.addOptions(obj, json, "BANK_ACCT");		
			}
		});
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
//ѡ���ʽ���Դ����ʾ�¼�ѡ��
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
		DWRUtil.addOptions(sub_money_origin,{"0":"��ѡ��"});
		for(i=0;i<json.length;i++){
			DWRUtil.addOptions(sub_money_origin,json[i]);
	  }
  });
}
</script>
</HEAD>
<BODY class="BODY body-nox" onload="javascript:">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="post" onsubmit="javascript:return validateForm(this);" ENCTYPE="multipart/form-data">
<input type="hidden" id="bSuccess" value="<%=bSuccess%>"/><!--�����ɹ���־-->
<input type="hidden" name="book_code" value="<%=input_bookCode%>"/>
<input type="hidden" id="serial_no" name="serial_no" value="<%=serial_no%>"/>
<input type="hidden" name="customer_cust_id" id="customer_cust_id" value="<%=cust_id%>"/><!--�ͻ�ID��־-->
<input type="hidden" name="sQuery" value="<%=sQuery%>"/>

<input type="hidden" name="inputflag" value="<%=inputflag%>"/><!--ԤԼ�ű�־-->
<input type="hidden" name="inputflag2" value="<%=inputflag2%>"/><!--�����˺ű�־-->
<input type="hidden" name="check_flag" value="<%=check_flag%>"/>
<input type="hidden" name="intrust_flag1" value="<%=intrust_flag1%>"/>
<input type="hidden" id="selfbenflag" name="selfbenflag" value="<%=selfbenflag%>"/>
<input type="hidden" name="purchase_mode_flag" value="<%=purchase_mode_flag%>">

<input type="hidden" id="t_contract_bh" name="t_contract_bh" value="<%=contract_bh%>"/>
<%if (user_id.intValue()!=2/*2����Ͷ*/ && user_id.intValue()!=999) { %>
<input type="hidden" name="period_unit" value="<%=period_unit%>"/>
<%}%>
<input type="hidden" name="pre_start_date" value="<%=pre_start_date%>"/>
<input type="hidden" name="pre_end_date" value="<%=pre_end_date%>"/>

<!-- �Ŷ������Ϣ -->
<input type="hidden" name="quota_money" value="<%=quota_money%>">
<input type="hidden" name="already_sale" value="<%=already_sale%>">
<input type="hidden" name="team_id" value="<%=team_id%>">
<input type="hidden" name="serialNo" value="<%=serialNo%>">
<input type="hidden" name="r_product_id" value="<%=product_id%>">
<input type="hidden" name="already_qualified_num" value="<%=already_qualified_num%>">

<input type="hidden" id="channel_type" name="channel_type" value="<%=channel_type%>"/>
<input type="hidden" id="channel_id" name="channel_id" value="<%=r_channel_id%>"/>
<input type="hidden" id="channel_rate" name="channel_rate" value=""/>
<input type="hidden" id="contact_id" name="contact_id" value="<%=contact_id%>"/>
<div align="left" class="page-title">
	<font color="#215dc6"><b><%=menu_info%></b></font>
	<br/>
</div>

<div class="direct-panel">
	<table  border="0" width="100%" cellspacing="0" cellpadding="2" class="table-popup">
		<tr>
			<td align="right" width="120px"><%=LocalUtilis.language("class.productID",clientLocale)%> :</td><!--��Ʒ���-->
			<td align="left">
				<input type="text" <%=checkedStyle%> readonly class="edline" name="productcode" value="<%=product_code%>" onkeydown="javascript:setProduct(this.value);" size="20">
			</td>
		</tr>
		<tr>
			<td align="right" width="120px"><font color="red"><b>*</b></font><%=LocalUtilis.language("class.productNumber",clientLocale)%> :</td><!--��Ʒѡ��-->
			<td>
				<%if(Argument.getSyscontrolValue("PRODUCT_FLAG")==1){%>
					<select size="1"  disabled name="product_id" onkeydown="javascript:nextKeyPress(this)" onchange="javascript:selectProductAndCustomer();" class="productname" disabled>
						<option><%=LocalUtilis.language("message.pleaseSelect",clientLocale)%> </option><!--��ѡ��-->
						<%=Argument.getCRMProductListOptions(new Integer(2),product_id,"110203",new Integer(0),input_operatorCode)%>
					</select>
				<%}else{%>
					<select size="1"  disabled name="product_id" onkeydown="javascript:nextKeyPress(this)" onchange="javascript:selectProductAndCustomer();" class="productname" disabled>
						<option><%=LocalUtilis.language("message.pleaseSelect",clientLocale)%> </option><!--��ѡ��-->
						<%=Argument.getCRMProductListOptions(new Integer(2),product_id,"110202",new Integer(0),input_operatorCode)%>
					</select>
				<%}%>
				<input type="hidden" name="productId" value="<%= product_id%>"/>
			</td>
		</tr>

		<%if(product_sub_flag.intValue() == 1 && is_product_sub_flag == 1){%>
			<tr>
				<td align="right" width="120px"><b><font color="red"><b>*</b></font><%=LocalUtilis.language("class.subProductID",clientLocale)%> </b></td><!--�Ӳ�Ʒѡ��-->
				<td align=left colspan=3>
					<select size="1" <%if(serial_no!= null&&serial_no.intValue()!=0){out.print("disabled");}%> name="sub_product_id" onkeydown="javascript:nextKeyPress(this)" class="productname">
						<%=Argument.getSubProductOptions(product_id, new Integer(0),sub_product_id)%>
					</select>&nbsp;&nbsp;
					<button type="button"  class="xpbutton2" name="show_sub_info" title="�鿴�Ӳ�Ʒ��Ϣ" onclick="javascript:openSubProduct();"><%=LocalUtilis.language("message.view",clientLocale)%></button><!-- �鿴 -->
				</td>
			</tr>
		<%}%>
		</table>
		<br/>
		<table  border="0" width="100%" cellspacing="0" cellpadding="2"  class="table-popup">
			<TR>
				<td align="right" width="120px"><%=LocalUtilis.language("class.customerID",clientLocale)%> :</td><!--�ͻ����-->
				<td><input readonly class="edline" name="cust_no" size="20" onkeydown="javascript:nextKeyPress(this)" value="<%= cust_no%>"/></td>
			</TR>
			<TR>
				<td align="right" width="120px"><%=LocalUtilis.language("class.customerName",clientLocale)%> :</td><!--�ͻ�����-->
				<td colspan=3><input name="cust_name" readonly class="edline" onkeydown="javascript:selectCustName(this)" size="50" value="<%= cust_name%>"/></td>
			</TR>

			<tr>
				<td align="right" width="120px"><%=LocalUtilis.language("class.preCode2",clientLocale)%> :</td><!--ԤԼ���-->
				<td>
					<input readonly class="edline" name="pre_code" size="20" onkeydown="javascript:nextKeyPress(this)" value="<%=pre_code%>">
				</td>
			</tr>
			<tr>
				<td align="right" width="120px"><%=LocalUtilis.language("class.factPreNum2",clientLocale)%> :</td><!--ԤԼ���-->
				<td><input readonly class="edline" name="pre_money" size="20" onkeydown="javascript:nextKeyPress(this)" value="<%=Format.formatMoney(pre_money)%>"></td>
			</tr>
		</table>
		<br/>
		<table  border="0" width="100%" cellspacing="0" cellpadding="2"  class="table-popup">
			<tr>
				<td align=right><font color="red"><b>*</b></font>��ͬ���� : </td>
				<td colspan="3">
					<select size="1" name="contract_type" onkeydown="javascript:nextKeyPress(this)" style="width:150px;">
						<option value="0" <%if(contract_type.intValue() == 0){%>selected<%}%> >��ѡ��</option>
						<option value="1" <%if(contract_type.intValue() == 1){%>selected<%}%> >ǰ̨������Ա��ͬ</option>
						<option value="2" <%if(contract_type.intValue() == 2){%>selected<%}%> >��Ʒ���ź�ͬ</option>
						<option value="3" <%if(contract_type.intValue() == 3){%>selected<%}%> >������ͬ</option>
					</select>
				</td>
			</tr>
			<tr>
				<td align="right"<%if(Argument.getSyscontrolValue_intrust("C30902")==1){out.print("style='display:none'");}%> width="120px"><%=LocalUtilis.language("class.constractBH",clientLocale)%> :</td><!--��ͬ���-->
				<td <%if(Argument.getSyscontrolValue_intrust("C30902")==1){out.print("style='display:none'");}%>>
					<input name="contract_bh" size="20" maxlength=20 onkeydown="javascript:nextKeyPress(this)" <%if(Argument.getSyscontrolValue_intrust("C30902")==1){out.print("tabindex='-1'");}%>>
				</td>
				<TD align="right" width="120px"><%=LocalUtilis.language("class.contractID",clientLocale)%> :</TD><!--��ͬ���-->
				<!--��֤���-->
	            <TD><INPUT <%=checkedStyle%> name="contract_sub_bh" onkeydown=javascript:nextKeyPress(this) maxLength="40" size="40"  value="<%= contract_sub_bh%>">&nbsp;&nbsp;<INPUT type="button" <%if(check_flag.intValue()==2){out.print("style='display:none'");}%>class="xpbutton3" onclick="javascript:check();" value='<%=LocalUtilis.language("messgae.CheckBH",clientLocale)%> '><div id="contractDIV"></div></TD>
			</tr>
<%if (user_id.intValue()==2 /*2����Ͷ*/|| user_id.intValue()==999) { %>
		<tr>
			<td align="right">��ͬ���� :</td>
			<td colspan="3">
				<input type="text" name="valid_period" size="5" onkeydown="javascript:nextKeyPress(this)" value="<%=Utility.trimNull(valid_period)%>"/> 
				<select name="period_unit" id="period_unit"><%=Argument.getPeriodValidUnitOptions(period_unit)%></select>
			</td>
		</tr>
<%} %>
		<tr>
			<td align="right" width="120px"><font color="red"><b>*</b></font><%=LocalUtilis.language("class.bankAcct3",clientLocale)%> :</td><!--�����ʺ�-->
			<td>
				<input <%if(inputflag2==1) out.print("style=display:none");%> type="text" name="bank_acct"
					onkeydown="javascript:nextKeyPress(this)" onkeyup="javascript:showAcctNum(this.value)"  size="30" >
				<select style="WIDTH:200px;display:<%=inputflag2==2?"none":""%>" size="1" name="bank_acct2" 
					onkeydown="javascript:nextKeyPress(this)" onchange="javascript:autoSetAcctInfo(this);">
					<%=bankOptions%>
				</select>&nbsp;&nbsp;
				<%if(inputflag2==1){%>
				<!--����-->
                <button type="button"  class="xpbutton2" accessKey=x name="btnPrint" title='<%=LocalUtilis.language("message.input",clientLocale)%>' onclick="javascript:changeInput2(this);"><%=LocalUtilis.language("message.input",clientLocale)%> (<u>X</u>)</button>
				<%}%>
				<span id="bank_acct_num" class="span"></span>
			</td>
		</tr>
		<tr>
			<td align="right" width="120px"><font color="red"><b>*</b></font><%=LocalUtilis.language("class.bankName3",clientLocale)%> :</td><!--��������-->
			<td >
				<select size="1" <%=checkedStyle2%> name="bank_id" onchange="javascript:selectBank(this.value);" style="WIDTH: 120px">
					<%=Argument.getBankOptions(bank_id)%>
				</select>
				<input name="bank_sub_name" size="20" value="<%=bank_sub_name%>" onkeydown="javascript:nextKeyPress(this)">
			</td>
			<td align="right">���������ڵ�:</td>
			<td>
				<select size="1"  name="bank_province" id ="bank_province" style="width: 130px" onkeydown="javascript:nextKeyPress(this)" onchange="javascript:setGovRegional(this.value,'');">
					<%=Argument.getCustodianNameLis(new Integer(9999), "", new Integer(0),bank_province)%>
				</select>
				<span id="gov_regional_span">
					<select style="WIDTH: 150px" name="bank_city" id="bank_city"><option value="">��ѡ��</option></select>
				</span><!-- ʡ������������˳������������ -->
			</td>
		</tr>
			
		<!-- ��ϴǮ ���-->
		<tr>
			<td align="right" width="120px"><font color="red"><b>*</b></font><%=LocalUtilis.language("class.bankAccountType",clientLocale)%> :</td><!--�����˺�����-->
			<td>
				<select size="1" name="bank_acct_type" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 250px">
					<%=Argument.getBankAcctType(bank_acct_type)%>
				</select>
			</td>
			<td align="right" width="120px"><%=LocalUtilis.language("class.customerGainAcct",clientLocale)%> :</td><!--�����ʺŻ���-->
			<td ><input name="gain_acct" size="20" <%=checkedStyle%> onkeydown="javascript:nextKeyPress(this)" value="<%=gain_acct%>"/></td>
		</tr>
		<tr>
			<td align="right" width="120px"><font color="red"><b>*</b></font><%=LocalUtilis.language("class.feeType",clientLocale)%> :</td><!--�ɿʽ-->
			<td>
				<select size="1" name="jk_type" <%=checkedStyle2%> onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px">
					<%=Argument.getJkTypeOptions(jk_type)%>
				</select>
			</td>
			<td align="right" width="120px"><%if(open_flag.intValue()==1){%><%=LocalUtilis.language("class.bonusFlag",clientLocale)%> :<%}%></td><!--������䷽ʽ-->
			<td><%if(open_flag!=null && open_flag.intValue()==1){%>
				<select size="1" name="bonus_flag" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px">
					<%=Argument.getBonus_flag(bonus_flag)%>
				</select>
				<span id="bonus_rate_div" <%if(Utility.parseInt(Utility.trimNull(bonus_flag),0)!=3) out.print("style='display:none'");%>>ת�ݶ����:
					<input type="text" name="bonus_rate" size="10" onkeydown="javascript:nextKeyPress(this)"
						value="<%=(Utility.parseDecimal(Utility.trimNull(bonus_rate),new BigDecimal(0))).multiply(new BigDecimal(100)).setScale(2)%>">%
				</span>
				<%}%>
			</td>
		</tr>

		<tr>
			<td align="right" width="120px">*<%=LocalUtilis.language("class.rg_money",clientLocale)%> :</td><!--�Ϲ����-->
			<td><input name="rg_money" size="20" <%=checkedStyle%> value="<%=rg_money%>" onblur="javascript:checkSellInfo();" onkeydown="javascript:nextKeyPress(this)" onkeyup="javascript:showCnMoney(this.value,rg_money_cn)"> <span id="rg_money_cn" class="span"><%=rg_money_cn%></span></td>
			<td align="right" width="120px"><%if(open_flag.intValue()==1){%><%=fee_type_name%><%=LocalUtilis.language("class.feeTypeName",clientLocale)%> :<%}%></td><!--�۽ɷ�ʽ-->
			<td ><%if(open_flag.intValue()==1){%>
				<select size="1" name="fee_jk_type" <%=checkedStyle2%> onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px">
					<%=Argument.getFeeJkTypeOptions(fee_jk_type.intValue())%>
				</select>
				<%}%>
			</td>
		</tr>
		<tr>
		<td align="right"><font color="red"><b>*</b></font>��������:</td>
		<td>
			<select id="market_trench" name="market_trench" style="width: 280px;" onchange="javascript:setMartetTrench(this);">
				<option value="">��ѡ��</option>
				<%
				if(markList != null && markList.size() != 0){
				for(int u=0;u<markList.size();u++){
					markMap = (Map)markList.get(u);
					if((r_channel_id.intValue() == Utility.parseInt(Utility.trimNull(markMap.get("CHANNEL_ID")), new Integer(0)).intValue()) && channel_type.equals(Utility.trimNull(markMap.get("CHANNEL_TYPE"))))
						market_selected = "selected";
					else
						market_selected = "";
				%>
				<option <%=market_selected %> value="<%=Utility.trimNull(markMap.get("CHANNEL_TYPE")) %>@<%=Utility.trimNull(Utility.parseInt(Utility.trimNull(markMap.get("CHANNEL_ID")), new Integer(0))) %>@<%=Utility.trimNull(Utility.parseDecimal(Utility.trimNull(markMap.get("CHANNEL_FARE_RATE")), new BigDecimal(0),4,"1")) %>"><%=Utility.trimNull(markMap.get("CHANNEL_TYPE_NAME")) %><%="".equals(Utility.trimNull(markMap.get("CHANNEL_NAME"))) ? "" : "-"%><%=Utility.trimNull(markMap.get("CHANNEL_NAME")) %>[���ʣ�<%=Utility.trimNull(Utility.parseDecimal(Utility.trimNull(markMap.get("CHANNEL_FARE_RATE")), new BigDecimal(0),4,"100")) %>]</option>
				<%}} %>
			</select>
		</td>
		<td align="right">��������:</td>
		<td><input name="market_trench_money" size="20" value="<%=market_trench_money %>" onkeydown="javascript:nextKeyPress(this)" onkeyup="javascript:showCnMoney(this.value,market_trench_money_cn)"><span id="market_trench_money_cn" class="span"></span></td>
	</tr>
		<tr>
			<td align="right" width="120px">*<%=LocalUtilis.language("class.qsDate",clientLocale)%> :</td><!--ǩ������-->
			<td>
				<INPUT TYPE="text" NAME="qs_date_picker" class=selecttext <%=checkedStyle2%>
				<%if(qs_date==null){%> value="<%=Format.formatDateLine(Utility.getCurrentDate())%>"
				<%}else{%>value="<%=Format.formatDateLine(qs_date)%>"<%}%> >
				<INPUT TYPE="button" value="��" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.qs_date_picker,theform.qs_date_picker.value,this);" tabindex="13">
				<INPUT TYPE="hidden" NAME="qs_date"   value="">
			</td>
		</tr>

		<tr>
			<td align="right" width="120px">*<%=LocalUtilis.language("class.dzDate",clientLocale)%> :</td><!--�ɿ�����-->
			<td>
				<INPUT TYPE="text" NAME="jk_date_picker" class=selecttext <%=checkedStyle2%>
				<%if(jk_date==null){%> value="<%=Format.formatDateLine(Utility.getCurrentDate())%>"
				<%}else{%>value="<%=Format.formatDateLine(jk_date)%>"<%}%> >
				<INPUT TYPE="button" value="��" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.jk_date_picker,theform.jk_date_picker.value,this);" tabindex="14">
				<INPUT TYPE="hidden" NAME="jk_date" value="<%=jk_date%>">
			</td>
			<td align="right" width="120px"><%if (serial_no == null){%> <%=LocalUtilis.language("class.selfBenFlag",clientLocale)%> :<%}%></td><!--�����־-->
			<td><%if (serial_no == null){%><input name="self_ben_flag" <%=checkedStyle2%> class="flatcheckbox" type="checkbox" value="<%=selfbenflag%>" <%if(selfbenflag==1) out.print("checked");%> onkeydown="javascript:nextKeyPress(this)"><%}%></td>
		</tr>

		<tr style="display: none;">
			<td  width="100px" align="right"><%=LocalUtilis.language("class.partnType",clientLocale)%>:&nbsp;&nbsp;</td>
			<td><div id="comboBoxTree"></div></td>
			<td align="right"><%=LocalUtilis.language("class.partnName",clientLocale)%> :</td><!--������Դ-->
			<td><div id="comboBoxParentTree"></div></td>
		</tr>
		<tr >
			<td align="right"><%=LocalUtilis.language("class.partnAttachmentInfo",clientLocale)%> :</td><!--����������Ϣ-->
			<td>
				<input name="channel_memo" size="1" style="WIDTH: 250px" onkeydown="javascript:nextKeyPress(this)"  value="<%=channel_memo%>">
			</td>
			<td align="right"><font color="red"><b>*</b></font><%=LocalUtilis.language("class.channelCooperation",clientLocale)%> :</td><!-- ����������ʽ -->
			<td>
				<SELECT size="1" name="channel_cooperation" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px">
					<%=Argument.getChannelCooperationOptions(channel_cooperation)%>
				</SELECT>
			</td>
		</tr>
		<tr>
			<td align="right" width="120px">*<%=LocalUtilis.language("class.linkMan",clientLocale)%> :</td><!--��ͬ������Ա-->
			<td>
				<select <%= checkedStyle2%> size="1" name="link_man" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px" onchange="javascript:selectTeam(this.value);">
					<%=Argument.getOperatorOptionsByRoleId(new Integer(2),link_man)%>
				</select>
			</td>
		</tr>
		<%if(!"".equals(Utility.trimNull(cityNameOptions))){%>
			<tr>
				<td align="right" width="120px"><%=LocalUtilis.language("class.citySerialNO",clientLocale)%> :</td><!--��ͬ�ƽ��-->
				<td>
					<select <%= checkedStyle2%> size="1" name="city_serialno" <%=checkedStyle2%> onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px">
						<%=cityNameOptions%>
					</select>
				</td>
			</tr>
		<%}%>
		<tr>
			<%if(intrust_flag1.intValue()!=1){%>
			<td align="right"><%=LocalUtilis.language("class.provLevel",clientLocale)%>:</td><!-- �������ȼ� -->
			<td>
				<select <%= checkedStyle2%> name="prov_flag" style="WIDTH: 100px" <%if(asfund_flag!=null&&asfund_flag.intValue()==3){%>onchange="javascript:getprovlevel()"<%}%> onkeydown="javascript:nextKeyPress(this)">
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
			<td align="right"><%=LocalUtilis.language("class.incomeLevel",clientLocale)%>:</td><!-- ���漶�� -->
			<td>
				<div id="div_prov_level">
				<select <%= checkedStyle2%> name="prov_level" style="width:100px">
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
			<tr id="money_origin_id">
				<td align="right">�ʽ�/�ʲ���Դ:</td>
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
				<td align="right"><%=LocalUtilis.language("message.cooperationWay",clientLocale)%>:</td><!--������ʽ-->
				<td>
					<input <%= checkedStyle2%> <%if (with_bank_flag != null && with_bank_flag.intValue()==1)out.print("checked");%>
							onkeydown="javascript:nextKeyPress(this)" type="checkbox" class="flatcheckbox" value="<%=with_bank_flag %>" name="with_bank_flag" onclick="javascript:showHtyh();">
							<%=LocalUtilis.language("intrsut.home.silvertrustcooper",clientLocale)%>&nbsp;&nbsp;&nbsp;&nbsp;<!--���ź���-->
					<span id="htyhmc_v" <%if(with_bank_flag.intValue()!=1) out.print("style='display:none'");%>>
						<%=LocalUtilis.language("class.withBankId",clientLocale)%>:	<!-- �������� -->
						<select <%= checkedStyle2%> size="1" name="ht_bank_id" style="WIDTH: 220px">
							<%=Argument.getBankOptions(Utility.trimNull(ht_bank_id))%>
						</select>
						<input <%= checkedStyle2%> type="text" name="ht_bank_sub_name" size="20" onkeydown="javascript:nextKeyPress(this)" value="<%=Utility.trimNull(ht_bank_sub_name)%>">
					</span>
					<input <%= checkedStyle2%> <%if (with_security_flag != null && with_security_flag.intValue()==1)out.print("checked");%>
						onkeydown="javascript:nextKeyPress(this)" type="checkbox"
						class="flatcheckbox" value="<%=with_security_flag %>" name="with_security_flag"
						><%=LocalUtilis.language("message.cooperation2",clientLocale)%> &nbsp;&nbsp;<!--֤�ź���-->
					<input <%= checkedStyle2%> <%if (with_private_flag != null && with_private_flag.intValue()==1)out.print("checked");%>
						onkeydown="javascript:nextKeyPress(this)" type="checkbox"
						class="flatcheckbox" value="<%=with_private_flag %>" name="with_private_flag"
						><%=LocalUtilis.language("class.cooperation4",clientLocale)%> &nbsp;&nbsp;<!--˽ļ�������-->
				</td>
			</tr>
			<tr>
				<td align="right">��ͬ��ϵ��:</td>
				<td colspan="3">
					<%--select size="1" name="cust_message" onkeydown="javascript:nextKeyPress(this)" onchange="newlyIncreased(this.value);">
					</select>&nbsp;
					<button type="button"  class="xpbutton3" id="link_cust" onclick="javascript:showLinkCust();" style="display:none;">��ϸ</button--%>
					&nbsp;&nbsp;����:<input type="text" name="ht_cust_name" size="15" onkeydown="javascript:nextKeyPress(this)" value="<%=ht_cust_name%>"/>
					&nbsp;&nbsp;�绰:<input type="text" name="ht_cust_tel" size="15" onkeydown="javascript:nextKeyPress(this)" value="<%=ht_cust_tel%>"/>
					&nbsp;&nbsp;��ַ:<input type="text" name="ht_cust_address" size="35" onkeydown="javascript:nextKeyPress(this)" value="<%=ht_cust_address%>"/>	
				</td>
			</tr>
			<tr>
				<td align="right" width="120px"><%=LocalUtilis.language("class.description",clientLocale)%> :</td><!--��ע-->
				<td  colspan=3>
					<textarea rows="3" <%if(check_flag.intValue()==2){out.print("style=edline_textarea");}%> name="summary2" onkeydown="javascript:nextKeyPress(this)" cols="100"><%=summary%></textarea>
				</td>
			</tr>
			<tr id="reader1">
	          	<td align="right"><%if(attachmentList!=null&&attachmentList.size()> 0){%>����:<%}%></td>
	            <td colspan="3">
				<%
				Iterator attachment_it = attachmentList.iterator();
				HashMap attachment_map = new HashMap();
	            while(attachment_it.hasNext()){
	            	attachment_map = (HashMap)attachment_it.next();
	            %>
	            	<div id="divattach<%=Utility.trimNull(attachment_map.get("ATTACHMENTID"))%>" style="display:">
	            	<a title="�鿴����" href="<%=request.getContextPath()%>/system/basedata/downloadattach.jsp?file_name=<%=Utility.replaceAll(Utility.trimNull(attachment_map.get("SAVE_NAME")),"\\","/")%>&name=<%=Utility.trimNull(attachment_map.get("ORIGIN_NAME"))%>" ><%=Utility.trimNull(attachment_map.get("ORIGIN_NAME"))%></a>
	            	&nbsp;&nbsp;&nbsp;&nbsp;
	            	<button type="button"  class="xpbutton2" accessKey=d id="btnSave" name="btnSave"
	            		onclick="javascript:confirmRemoveAttach(divattach<%=Utility.trimNull(attachment_map.get("ATTACHMENTID"))%>,'<%=Utility.trimNull(attachment_map.get("ATTACHMENTID"))%>$<%=Utility.replaceAll(Utility.trimNull(attachment_map.get("SAVE_NAME")),"\\","/")%>');">ɾ��</button>
	            	</div><br>
				<%}	%>
	            </td>
	         </tr>

			<tr id="reader2" style="display:">
	          	<td class="paramTitle"align="right"><%=LocalUtilis.language("menu.filesAdd",clientLocale)%>:&nbsp;&nbsp;</td><!-- ��Ӹ��� -->
	            <td colspan="3">
	            	<table id="test" width="100%">
	            		<tr >
	            			<td>
			            	<input type="file" name="file_info" size="60">&nbsp;
			            	<img title="<%=LocalUtilis.language("message.tSelectAdditionalFiles",clientLocale)%> " src="<%=request.getContextPath()%>/images/minihelp.gif"><!--ѡ�񸽼��ļ�-->
			            	</td>
			            </tr>
			        </table>
			        <button type="button"   onclick="addline()"><%=LocalUtilis.language("class.moreMccessories",clientLocale)%> <!--�����˴���Ӹ��฽��--></button>
	            </td>
	        </tr>
	</TABLE>
</div>
<br>
<div align="right">
	<!--δ��˱���-->
    <button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" <%if(check_flag.intValue()>=2){out.print("style=display:none");}%> onclick="javascript:SaveAction();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;
	<!--����-->
    <button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:CancelAction();"><%=LocalUtilis.language("message.back",clientLocale)%> (<u>C</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</div>
</form>
<%@ include file="/includes/foot.inc"%>
<%
contract.remove();
product.remove();
preContract.remove();
sale_parameter.remove();
channel.remove();
%>
<script language=javascript>
window.onload = function(){
	setGovRegional(document.getElementById("bank_province").value,'<%=bank_city%>');
	var v_bSuccess = document.getElementById("bSuccess").value;

	if(v_bSuccess=="true"){
		 sl_update_ok();
		 CancelAction();
	}
}
showCustOptions(document.getElementById("customer_cust_id").value);
//DWRUtil.setValue("cust_message",'<%=contact_id%>');


</script>
</BODY>
</HTML>


