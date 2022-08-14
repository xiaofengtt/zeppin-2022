<%@ page contentType="text/html; charset=GBK" import="enfo.crm.marketing.*,enfo.crm.web.*,enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
//���ҳ�洫�ݱ���
String sQuery = request.getParameter("sQuery");
Integer serial_no = Utility.parseInt(Utility.trimNull(request.getParameter("serial_no")),new Integer(0));//�Ϲ���ϢID
String q_productId = Utility.trimNull(request.getParameter("product_id"));  //��Ʒ��ϢID
String q_cust_id = Utility.trimNull(request.getParameter("customer_cust_id")); //�ͻ�ID
String bank_id = Utility.trimNull(request.getParameter("bank_id")); //����ID
int s_flag = Utility.parseInt(Utility.trimNull(request.getParameter("s_flag")),0);
int modi_check_flag = Utility.parseInt(request.getParameter("check_flag"), 2);

String channel_type = Utility.trimNull(request.getParameter("channel_type"));
String channel_memo = Utility.trimNull(request.getParameter("channel_memo"));
String channel_cooperation = Utility.trimNull(request.getParameter("channel_cooperation"));
Integer r_channel_id = Utility.parseInt(Utility.trimNull(request.getParameter("channel_id")),new Integer(0));
BigDecimal market_trench_money = Utility.parseDecimal(Utility.trimNull(request.getParameter("market_trench_money")),new BigDecimal(0));
Integer bonus_flag = Utility.parseInt(request.getParameter("bonus_flag"),new Integer(1));

int inputflag= Utility.parseInt(request.getParameter("inputflag"), 2); // �����ʺŵ����뷽ʽ Ĭ���� ����
Integer selfbenflag = Utility.parseInt(Utility.trimNull(request.getParameter("selfbenflag")),new Integer(1));//�����־

int SUB_COOPER = Argument.getSyscontrolValue_intrust("SUB_COOPER");
String bank_sub_name =  Utility.trimNull(request.getParameter("bank_sub_name")); 

Integer fee_jk_type = Utility.parseInt(request.getParameter("fee_jk_type"), new Integer(0));
String bank_acct_type = Utility.trimNull(request.getParameter("bank_acct_type")); 
String jk_type = Utility.trimNull(request.getParameter("jk_type")); 

Integer qs_date = Utility.parseInt(Utility.trimNull(request.getParameter("qs_date")),new Integer(0));
Integer jk_date = Utility.parseInt(Utility.trimNull(request.getParameter("jk_date")),new Integer(0));

String prov_level =  Utility.trimNull(request.getParameter("prov_level"));
String ht_bank_id =  Utility.trimNull(request.getParameter("ht_bank_id"));
String ht_bank_sub_name =  Utility.trimNull(request.getParameter("ht_bank_sub_name"));
Integer with_bank_flag = Utility.parseInt(request.getParameter("with_bank_flag"), new Integer(0));
Integer with_security_flag = Utility.parseInt(request.getParameter("with_security_flag"), new Integer(0));
Integer with_private_flag = Utility.parseInt(request.getParameter("with_private_flag"), new Integer(0));
Integer prov_flag = Utility.parseInt(request.getParameter("prov_flag"), new Integer(0));
BigDecimal bonus_rate = Utility.parseDecimal(Utility.trimNull(request.getParameter("bonus_rate")),new BigDecimal(0));
Integer link_man = Utility.parseInt(request.getParameter("link_man"), new Integer(0));
String is_ykgl=Utility.trimNull(request.getParameter("is_ykgl"),"0");//�ÿ������־
String xthtyjsyl=Utility.trimNull(request.getParameter("xthtyjsyl"));//���к�ͬԤ��������
Integer spot_deal = null;
//�Ŷ���Ϣ
Integer team_id = new Integer(-1);
BigDecimal quota_money = new BigDecimal(0);
BigDecimal already_sale = new BigDecimal(0);
Integer serialNo = new Integer(0);
Integer quota_qualified_num = new Integer(0);//�ϸ�Ͷ�����������
Integer already_qualified_num = new Integer(0);//�ۼ������۵ĺ�ͬͶ��������

Integer asfund_flag = new Integer(0);
Integer open_flag = new Integer(0);
Integer intrust_flag1 = new Integer(0);
String bank_acct = "";

//ִ����Ӳ��� ��Ӹ���
DocumentFile2 file = null;
if(request.getMethod().equals("POST")){
	file = new DocumentFile2(pageContext);
	file.parseRequest();
	/*********��Ϊ������һ�������ϴ�������ԭ��request����ȡֵ�ĸ�Ϊ file*******************/
	sQuery = file.getParameter("sQuery");
	serial_no = Utility.parseInt(Utility.trimNull(file.getParameter("serial_no")),new Integer(0));//�Ϲ���ϢID
	q_productId = Utility.trimNull(file.getParameter("product_id"));  //��Ʒ��ϢID
	q_cust_id = Utility.trimNull(file.getParameter("customer_cust_id")); //�ͻ�ID
	
	inputflag= Utility.parseInt(file.getParameter("inputflag"), 2);
	selfbenflag = Utility.parseInt(Utility.trimNull(file.getParameter("selfbenflag")),new Integer(1));//�����־	
	
	fee_jk_type = Utility.parseInt(file.getParameter("fee_jk_type"), new Integer(0));
	bank_acct_type = Utility.trimNull(file.getParameter("bank_acct_type")); 	
	
	bank_id = Utility.trimNull(file.getParameter("bank_id")); //����ID
	bank_sub_name =  Utility.trimNull(file.getParameter("bank_sub_name")); 
	bank_acct = Utility.trimNull(file.getParameter("bank_acct")); 
	qs_date = Utility.parseInt(Utility.trimNull(file.getParameter("qs_date")),new Integer(0));
	jk_date = Utility.parseInt(Utility.trimNull(file.getParameter("jk_date")),new Integer(0));	
	jk_type = Utility.trimNull(file.getParameter("jk_type")); 
	prov_flag = Utility.parseInt(file.getParameter("prov_flag"), new Integer(0));
	prov_level =  Utility.trimNull(file.getParameter("prov_level"));

	ht_bank_id =  Utility.trimNull(file.getParameter("ht_bank_id"));
	ht_bank_sub_name =  Utility.trimNull(file.getParameter("ht_bank_sub_name"));
	with_bank_flag = Utility.parseInt(file.getParameter("with_bank_flag"), new Integer(0));
	with_security_flag = Utility.parseInt(file.getParameter("with_security_flag"), new Integer(0));
	with_private_flag = Utility.parseInt(file.getParameter("with_private_flag"), new Integer(0));
	
	bonus_rate = Utility.parseDecimal(Utility.trimNull(file.getParameter("bonus_rate")),new BigDecimal(0));
	is_ykgl=Utility.trimNull(file.getParameter("is_ykgl"),"0");//�ÿ������־
	xthtyjsyl=Utility.trimNull(file.getParameter("xthtyjsyl"));//���к�ͬԤ��������
	
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
boolean bSuccess = false;
input_bookCode = new Integer(1);
Integer userid = new Integer(2);
String fee_type_name = enfo.crm.tools.LocalUtilis.language("class.rgFee",clientLocale);//�Ϲ���
String checkedStyle = ""; //������ һЩ��ϢҪ���ֻ��
String checkedStyle2 = "";

String preCodeOptions = "";//�����������ʺ�
String cityNameOptions = "";//��ͬ�ƽ��
String bankOptions = "";//���к��б�
List attachmentList = new ArrayList();

//��������
//ID
Integer cust_id = Utility.parseInt(q_cust_id,new Integer(0));
Integer product_id = Utility.parseInt(q_productId,new Integer(0));

//��Ʒ��Ϣ
String period_unit_name = "";
Integer period_unit = new Integer(0);
String product_code = "";
String product_status ="";
Integer valid_period = new Integer(0);
Integer pre_start_date = new Integer(0);
Integer pre_end_date = new Integer(0);
Integer product_sub_flag = new Integer(0);//�Ӳ�Ʒ��־

//�ͻ���Ϣ
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
List markList = null;
Map markMap = null;
String market_selected = "";

//�Ϲ���ͬ��Ϣ
String contract_bh = "";//��ͬ���
String contract_sub_bh = "";
String gain_acct = "";
String summary = "";
Integer link_man2 = new Integer(0);

Integer check_flag = new Integer(0);
Integer mode_flag = null;//�Ϲ���ͬ��Դ��־
Integer citySerialno = new Integer(0);
Integer sub_product_id = new Integer(0);
Integer contact_id=null;
BigDecimal rg_money = new BigDecimal(0);
//------------------------------------

//��ö���
ContractLocal contract = EJBFactory.getContract();//��ͬ
CustomerLocal customer = EJBFactory.getCustomer();//�ͻ�
ProductLocal product = EJBFactory.getProduct();//��Ʒ
Customer_INSTLocal  customer_inst = EJBFactory.getCustomer_INST();//ͬ���ÿͻ�����
ChannelQueryLocal channel = EJBFactory.getChannelQuery();//�޸����������

ContractVO cont_vo = new ContractVO();
CustomerVO cust_vo = new CustomerVO();
CustomerVO c_vo = new CustomerVO();//ͬ����
ProductVO p_vo = new ProductVO();
SaleParameterVO salevo = new SaleParameterVO();
SaleParameterLocal sale_parameter = EJBFactory.getSaleParameter();

//ִ���޸Ĳ���
if(request.getMethod().equals("POST")){
	//�ͻ���Ϣ��ʾ
	if(cust_id.intValue()>0){
		List rsList_cust = null;
		Map map_cust = null;	
		
		//�ͻ�����ֵ		
		cust_vo.setCust_id(cust_id);
		cust_vo.setInput_man(input_operatorCode);
		rsList_cust = customer.listByControl(cust_vo);
		
		if(rsList_cust.size()>0){
			map_cust = (Map)rsList_cust.get(0);
		}

		if(map_cust!=null){		
			cust_name = Utility.trimNull(map_cust.get("CUST_NAME"));
			cust_no =  Utility.trimNull(map_cust.get("CUST_NO"));
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
		}		
		
		String inputBank_acct ="";//�������˺�		
		preCodeOptions = Argument.getCustBankAcctOptions(cust_id,bank_id,card_id,inputBank_acct);
	}
	//��ñ�������
	Integer r_cust_id = Utility.parseInt(file.getParameter("customer_cust_id"), null);
	Integer r_product_id = Utility.parseInt(file.getParameter("productId"), null); 
	Integer r_link_man = Utility.parseInt(file.getParameter("link_man"), input_operatorCode);
	BigDecimal r_rg_money = Utility.parseDecimal(file.getParameter("rg_money"),new BigDecimal(0));
	Integer r_bonus_flag = Utility.parseInt(file.getParameter("bonus_flag"),new Integer(1));
		
	String r_gain_acct = file.getParameter("customer_gain_acct"); //
	Integer s_product_id = Utility.parseInt(file.getParameter("r_product_id"), new Integer(0)); 
	String r_bank_acct = "";
	

	String r_contract_sub_bh = file.getParameter("contract_sub_bh");
	String r_bank_id = file.getParameter("bank_id");
	String r_bank_sub_name = Utility.trimNull(file.getParameter("bank_sub_name"));
	if (inputflag==1) // ѡ��
		r_bank_acct = file.getParameter("bank_acct2");
	else
		r_bank_acct = file.getParameter("bank_acct");
	
	Integer r_qs_date = Utility.parseInt(file.getParameter("qs_date"), new Integer(Utility.getCurrentDate()));
	Integer r_jk_date = Utility.parseInt(file.getParameter("jk_date"), new Integer(Utility.getCurrentDate()));
	String r_jk_type = file.getParameter("jk_type");	
	Integer r_prov_flag = Utility.parseInt(file.getParameter("prov_flag"), new Integer(0));
	String r_prov_level = Utility.trimNull(file.getParameter("prov_level"));

	r_channel_id = Utility.parseInt(Utility.trimNull(file.getParameter("channel_id")),new Integer(0));
	Integer r_service_man = Utility.parseInt(file.getParameter("service_man"), input_operatorCode);
	String r_summary = Utility.trimNull(file.getParameter("summary2"));

	String r_contract_bh = file.getParameter("contract_bh");	

	Integer r_check_man = Utility.parseInt(file.getParameter("check_man"), new Integer(0));
	String r_bank_acct_type = Utility.trimNull(file.getParameter("bank_acct_type"));
	Integer r_city_serialno = Utility.parseInt(file.getParameter("city_serialno"), new Integer(0));
	
	int r_fee_jk_type = Utility.parseInt(file.getParameter("fee_jk_type"),0);
	int r_selfbenflag = Utility.parseInt(file.getParameter("selfbenflag"),1);	
	
	market_trench_money = Utility.parseDecimal(Utility.trimNull(file.getParameter("market_trench_money")),new BigDecimal(0));
	channel_type = Utility.trimNull(file.getParameter("channel_type"));
	channel_memo = Utility.trimNull(file.getParameter("channel_memo"));
	channel_cooperation = Utility.trimNull(file.getParameter("channel_cooperation"));
	
	Integer r_with_bank_flag = Utility.parseInt(file.getParameter("with_bank_flag"), new Integer(0));
	Integer r_with_security_flag = Utility.parseInt(file.getParameter("with_security_flag"), new Integer(0));
	Integer r_with_private_flag = Utility.parseInt(file.getParameter("with_private_flag"), new Integer(0));
	
	String r_ht_bank_id =  Utility.trimNull(file.getParameter("ht_bank_id"));
	String r_ht_bank_sub_name =  Utility.trimNull(file.getParameter("ht_bank_sub_name"));
	check_flag = Utility.parseInt(file.getParameter("check_flag"), new Integer(0));
	BigDecimal r_bonus_rate = new BigDecimal(0);

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
	r_already_sale = r_already_sale.add(r_rg_money);

	//��������	
	ContractVO s_cont_vo = new ContractVO();	
	/*if(check_flag.intValue()>=2){
		//�޸�����˵��Ϲ���ͬ����
		s_cont_vo.setSerial_no(serial_no);
		s_cont_vo.setContract_sub_bh(r_contract_sub_bh);
		s_cont_vo.setBank_id(r_bank_id);
		s_cont_vo.setBank_sub_name(r_bank_sub_name);
		s_cont_vo.setBank_acct(r_bank_acct);
		s_cont_vo.setGain_acct(r_gain_acct);
		s_cont_vo.setQs_date(r_qs_date);
		s_cont_vo.setJk_date(r_jk_date);
		s_cont_vo.setJk_type(r_jk_type);
		s_cont_vo.setSummary(r_summary);
		s_cont_vo.setProv_flag(r_prov_flag);
		s_cont_vo.setProv_level(r_prov_level);

		s_cont_vo.setChannel_id(r_channel_id);
		s_cont_vo.setChannel_type(channel_type);
		s_cont_vo.setChannel_memo(channel_memo);		
		s_cont_vo.setBonus_flag(r_bonus_flag);
		
		channel.modiContractChannel(s_cont_vo,input_operatorCode); // ʵ���ϲ������Ǹĺ�ͬ��������Ϣ�������˺�ͬ�����������Ϣ
		bSuccess = true;*/

	if (check_flag.intValue()==1) {
		s_cont_vo.setSerial_no(serial_no);
		s_cont_vo.setContract_sub_bh(r_contract_sub_bh);
		s_cont_vo.setQs_date(r_qs_date);
		s_cont_vo.setJk_date(r_jk_date);
		s_cont_vo.setJk_type(r_jk_type);
		s_cont_vo.setBank_id(r_bank_id);
		s_cont_vo.setBank_sub_name(r_bank_sub_name);
		s_cont_vo.setBank_acct(r_bank_acct);
		s_cont_vo.setGain_acct(r_gain_acct);

		s_cont_vo.setSummary(r_summary);
		s_cont_vo.setProv_flag(r_prov_flag);
		s_cont_vo.setProv_level(r_prov_level);

		s_cont_vo.setChannel_id(r_channel_id);
		s_cont_vo.setChannel_type(channel_type);
		s_cont_vo.setChannel_memo(channel_memo);
		s_cont_vo.setChannel_cooperation(channel_cooperation);
		s_cont_vo.setMarket_trench_money(market_trench_money);
		s_cont_vo.setBonus_flag(r_bonus_flag);
		s_cont_vo.setBonus_rate(r_bonus_rate);
		s_cont_vo.setLink_man(r_link_man);
		s_cont_vo.setInput_man(input_operatorCode);

		contract.modiContractModi(s_cont_vo); 
		bSuccess = true;
	}else{
		//�޸�δ��˵��Ϲ���ͬ
		//ͬ���ͻ���Ϣ
		c_vo.setCust_id(r_cust_id);
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
		
		//ͬ���Ŷ���Ϣ
		/*salevo.setSerial_NO(r_serialNo);
		salevo.setTeamID(r_team_id);
		salevo.setProductID(s_product_id);
		salevo.setSerial_no_subscribe(serial_no);
		salevo.setAlreadysale(r_already_sale);
		salevo.setAlready_qualified_num(r_already_qualified_num);
		sale_parameter.modiAlreadysale(salevo,input_operatorCode);*/

		//�����ͬ��Ϣ
		s_cont_vo.setSerial_no(serial_no);
		s_cont_vo.setBook_code(input_bookCode);
		s_cont_vo.setCust_id(r_cust_id);
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
		s_cont_vo.setCity_serialno(r_city_serialno);
		s_cont_vo.setContract_sub_bh(r_contract_sub_bh);
		s_cont_vo.setFee_jk_type(r_fee_jk_type);
		s_cont_vo.setSelf_ben_flag(r_selfbenflag);
		s_cont_vo.setInput_man(input_operatorCode);
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

		contract.save_CRM(s_cont_vo);
		bSuccess = true;
		
		if(file.uploadAttchment(new Integer(5),serial_no,"",null,null,input_operatorCode))
			bSuccess = true; 	
	}
}
//��ȡ�Ϲ���ͬ��Ϣ
String product_name = "";
if(serial_no.intValue()>0){
	List rsList_contract = null;
	Map map_contract = null;
	
	cont_vo.setSerial_no(serial_no);

	if (modi_check_flag==2)	
		rsList_contract = contract.load(cont_vo);
	else
		rsList_contract = contract.loadContractModi(cont_vo);

	if (rsList_contract.size()>0) map_contract = (Map)rsList_contract.get(0);

	if(map_contract!=null){
		cust_id =  Utility.parseInt(Utility.trimNull(map_contract.get("CUST_ID")), new Integer(0));	
		product_id =  Utility.parseInt(Utility.trimNull(map_contract.get("PRODUCT_ID")), new Integer(0));	
		
		product_name =  Utility.trimNull(map_contract.get("PRODUCT_NAME"));
		cust_name = Utility.trimNull(map_contract.get("CUST_NAME"));
		contract_sub_bh = Utility.trimNull(map_contract.get("CONTRACT_SUB_BH"));
		contract_bh = Utility.trimNull(map_contract.get("CONTRACT_BH"));
		bank_id = Utility.trimNull(map_contract.get("BANK_ID"));
		bank_sub_name = Utility.trimNull(map_contract.get("BANK_SUB_NAME"));
		bank_acct_type = Utility.trimNull(map_contract.get("BANK_ACCT_TYPE"));
		bank_acct = Utility.trimNull(map_contract.get("BANK_ACCT"));
		gain_acct = Utility.trimNull(map_contract.get("GAIN_ACCT"));
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
		check_flag = Utility.parseInt(Utility.trimNull(map_contract.get("CHECK_FLAG")),new Integer(0));
		mode_flag = Utility.parseInt(Utility.trimNull(map_contract.get("MODE_FLAG")),null);
		sub_product_id =  Utility.parseInt(Utility.trimNull(map_contract.get("SUB_PRODUCT_ID")),new Integer(0));
		r_channel_id = Utility.parseInt(Utility.trimNull(map_contract.get("CHANNEL_ID")),new Integer(0));
		market_trench_money = Utility.parseDecimal(Utility.trimNull(map_contract.get("MARKET_MONEY")),new BigDecimal(0));
		channel_memo = Utility.trimNull(map_contract.get("CHANNEL_MEMO"));
		channel_cooperation = Utility.trimNull(map_contract.get("CHANNEL_COOPERTYPE"));
		channel_type = Utility.trimNull(map_contract.get("CHANNEL_TYPE"));
		bonus_rate = Utility.parseDecimal(Utility.trimNull(map_contract.get("BONUS_RATE")),new BigDecimal(0));
		with_bank_flag = Utility.parseInt(Utility.trimNull(map_contract.get("WITH_BANK_FLAG")), new Integer(0));
		with_security_flag = Utility.parseInt(Utility.trimNull(map_contract.get("WITH_SECURITY_FLAG")), new Integer(0));
		with_private_flag = Utility.parseInt(Utility.trimNull(map_contract.get("WITH_PRIVATE_FLAG")), new Integer(0));
		prov_flag = Utility.parseInt(Utility.trimNull(map_contract.get("PROV_FLAG")), new Integer(0));
		prov_level = Utility.trimNull(map_contract.get("PROV_LEVEL"));
		ht_bank_id =  Utility.trimNull(map_contract.get("HT_BANK_ID"));
		ht_bank_sub_name =  Utility.trimNull(map_contract.get("HT_BANK_SUB_NAME"));
		bonus_rate = Utility.parseDecimal(Utility.trimNull(map_contract.get("BONUS_RATE")),new BigDecimal(0));
		is_ykgl=Utility.trimNull(map_contract.get("IS_YKGL"));
		xthtyjsyl=Utility.trimNull(map_contract.get("XTHTYJSYL"));
		contact_id=Utility.parseInt(Utility.trimNull(map_contract.get("CONTACT_ID")),null);
		spot_deal = Utility.parseInt(Utility.trimNull(map_contract.get("SPOT_DEAL")),new Integer(2));
			
		//if(check_flag.intValue()==2||check_flag.intValue()==9){
			checkedStyle = "readonly class='edline'";
			checkedStyle2 = "disabled";
		//}
	}
	
	//����Ϲ�����
	AttachmentLocal attachmentLocal = EJBFactory.getAttachment();
	AttachmentVO attachment_vo = new AttachmentVO();
	attachment_vo.setDf_talbe_id(new Integer(5));
	attachment_vo.setDf_serial_no(serial_no);
	
	attachmentList = attachmentLocal.load(attachment_vo);
}

//ҳ��ȡֵ
if(s_flag==1){
	r_channel_id = Utility.parseInt(Utility.trimNull(request.getParameter("channel_id")),new Integer(0));
	market_trench_money = Utility.parseDecimal(Utility.trimNull(request.getParameter("market_trench_money")),new BigDecimal(0));
	channel_type = Utility.trimNull(request.getParameter("channel_type"));
	channel_memo = Utility.trimNull(request.getParameter("channel_memo"));
	channel_cooperation = Utility.trimNull(request.getParameter("channel_cooperation"));
	qs_date = Utility.parseInt(Utility.trimNull(request.getParameter("qs_date")),new Integer(0));
}

//��Ʒ��Ϣ��ʾ
if(product_id.intValue()>0){	
	List rsList_product = null;
	Map map_product = null;
	
	//ȡ������Ʒֵ	 		
	p_vo.setProduct_id(product_id);	
	rsList_product = product.load(p_vo);
	map_product = (Map)rsList_product.get(0);	

	//��Ʒ����������Ϣ
	markList = product.queryMarketTrench(p_vo);		
	
	if(map_product!=null){		
		open_flag = Utility.parseInt(Utility.trimNull(map_product.get("OPEN_FLAG")),new Integer(0));
		intrust_flag1 = Utility.parseInt(Utility.trimNull(map_product.get("INTRUST_FLAG1")),new Integer(0)); // ��һ���ϱ�־
		product_code = Utility.trimNull(map_product.get("PRODUCT_CODE"));	
		product_status = Utility.trimNull(map_product.get("PRODUCT_STATUS"));
		period_unit = Utility.parseInt(Utility.trimNull(map_product.get("PERIOD_UNIT")),new Integer(0));
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
		
		if(period_unit.intValue()>0){
			period_unit_name = Argument.getProductUnitName(period_unit);
		}
		
		if(product_status.equals("110203")){
			fee_type_name = enfo.crm.tools.LocalUtilis.language("class.sgFeeAmount",clientLocale);//�깺��
		}						
	}
}

//�ͻ���Ϣ��ʾ
if(cust_id.intValue()>0){
	List rsList_cust = null;
	Map map_cust = null;	
	
	//�ͻ�����ֵ		
	cust_vo.setCust_id(cust_id);
	cust_vo.setInput_man(input_operatorCode);
	rsList_cust = customer.listByControl(cust_vo);
	
	if(rsList_cust.size()>0){
		map_cust = (Map)rsList_cust.get(0);
	}

	if(map_cust!=null){		
		cust_name = Utility.trimNull(map_cust.get("CUST_NAME"));
		cust_no =  Utility.trimNull(map_cust.get("CUST_NO"));
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
	}		
	
	String inputBank_acct ="";//�������˺�		
	preCodeOptions = Argument.getCustBankAcctOptions(cust_id,bank_id,card_id,inputBank_acct);
}

//��ͬ�ƽ���б�
cityNameOptions = Argument.getCitynameOptions(product_id,citySerialno);

//�Ϲ�������Ĵ���
String rg_money_cn = "";
if (rg_money.intValue()!= 0){
	rg_money_cn = "(" + Utility.numToChinese(rg_money.toString()) + ")";
}

//�鿴�û������Ŷ���Ϣ
team_id = Utility.parseInt(Utility.trimNull(Argument.getTeam(link_man)),new Integer(-1));
Integer team_id2 = Utility.parseInt(Utility.trimNull(Argument.getTeam(link_man2)),new Integer(-1));

salevo.setTeamID(team_id);
salevo.setProductID(product_id);
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

if(team_id.intValue() == team_id2.intValue()){
	already_sale = already_sale.subtract(rg_money);
}
if(cust_type.intValue() ==1){
	int al_qualified_num = already_qualified_num.intValue() - 1;
	already_qualified_num = new Integer(al_qualified_num);
}

Map edMap = new HashMap();
//if (check_flag.intValue()>=2) 
	edMap = Argument.getEditableFields("con_aftercheck_edit");

if (edMap.isEmpty()) { // Ĭ�Ͽɱ༭���ֶ�
	edMap.put("CONTRACT_SUB_BH", new Boolean(true));
	edMap.put("QS_DATE", new Boolean(true));
	edMap.put("CHANNEL_ID", new Boolean(true));
	edMap.put("SUMMARY", new Boolean(true));
	edMap.put("BANK_ACCT", new Boolean(true));
	edMap.put("BANK_ID", new Boolean(true));
	edMap.put("BANK_SUB_NAME", new Boolean(true));
	edMap.put("CHANNEL_TYPE", new Boolean(true));
	edMap.put("JK_DATE", new Boolean(true));
	edMap.put("JK_TYPE", new Boolean(true));
	edMap.put("PROV_FLAG", new Boolean(true));
	edMap.put("PROV_LEVEL", new Boolean(true));
}

%>

<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("menu.editSubscriptionInfo",clientLocale)%>��<%=LocalUtilis.language("message.withoutBooked",clientLocale)%> ��</TITLE><!--�Ϲ���Ϣ�༭--><!--δԤԼ-->
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
/*���ò�Ʒ*/
function setProduct(value){
	var prodid=0;
	
	if (event.keyCode==13&&value!=""){
       var j = value.length;
       var flag = 0;
	   for(i=0;i<document.theform.product_id.options.length;i++){
			if(document.theform.product_id.options[i].text.substring(0,j)==value){
				document.theform.product_id.options[i].selected=true;
				prodid = document.theform.product_id.options[i].value;
					
				flag = 1;
				break;			
			}	
		}
		if(flag==1)
			location.replace('subscribe_info2.jsp?page=<%=sPage%>' + getLastOptions());
		
		if (prodid==0){
			sl_alert('<%=LocalUtilis.language("message.inputProdIDError",clientLocale)%> ��');//����Ĳ�Ʒ��Ų����ڻ��߸ò�Ʒ�����ƽ���
			document.theform.productid.value="";
			document.theform.product_id.options[0].selected=true;
		}
	}
	nextKeyPress(this);
}


/*���ò�Ʒ���ͻ���Ϣ*/
function selectProductAndCustomer(){	
	var url = "contract_aftercheck_edit2.jsp?page=<%=sPage%>" + getLastOptions();	
	window.location.href = url;
}

/*ѡ������*/
function selectBank(value){
	/*if (value!=""){	
		location = 'contract_aftercheck_edit2.jsp?page=<%=sPage%>&bank_id=' + value+ getLastOptions();
	}*/
}

/*ƴ��׺����*/
function getLastOptions(){	
	if(!sl_checkDate(document.theform.qs_date_picker,'<%=LocalUtilis.language("class.qsDate",clientLocale)%> '))return false;//ǩ������
	syncDatePicker(document.theform.qs_date_picker, document.theform.qs_date);
	
	var temp="";	
	var temp = temp + "&product_id="+ document.theform.product_id.value;	
	var temp = temp + "&customer_cust_id="+ document.theform.customer_cust_id.value;	
	var temp = temp + "&bank_id="+ document.theform.bank_id.value;	
	var temp = temp + "&contract_sub_bh="+ document.theform.contract_sub_bh.value;	
	var temp = temp + "&contract_bh=" + document.theform.contract_bh.value;
	var temp = temp + "&jk_type=" + document.theform.jk_type.value;
	var temp = temp +"&link_man="+document.theform.link_man.value;
	
	var temp = temp + "&qs_date=" + document.theform.qs_date.value;
	
	if (document.theform.sub_product_id)
		var temp = temp + "&sub_product_id=" + document.theform.sub_product_id.value;


<%if(product_id!=null&&product_id.intValue()!=0&&open_flag.intValue()==1){%>
	var temp = temp + "&bonus_flag="+document.theform.bonus_flag.value
<%}%>		
<%if(intrust_flag1.intValue()!=1){%>
	var temp = temp +"&prov_flag="+document.theform.prov_flag.value
<%}%>
<%if(intrust_flag1.intValue()!=1&&asfund_flag!=null&&asfund_flag.intValue()==3){%>
	var temp = temp +"&prov_level="+document.theform.prov_level.value
<%}%>
	
<%if(open_flag.intValue()==1){%>
	var temp = temp +"&bonus_flag="+document.theform.bonus_flag.value;
<%}%>
	
	var temp = temp + "&sQuery=<%=sQuery%>";	
	
	return temp;
}

/**************************************************************************************************************************/

/*�ı�����*/
function changeInput(obj){
	if(document.theform.inputflag.value==1){
		obj.innerText='<%=LocalUtilis.language("message.choose",clientLocale)%> ';//ѡ��
		document.theform.bank_acct.style.display="";
		document.theform.bank_acct2.style.display="none";
		document.theform.inputflag.value=2;
	}
	else{
		obj.innerText='<%=LocalUtilis.language("message.input",clientLocale)%> ';//����
		document.theform.bank_acct.style.display="none";
		document.theform.bank_acct2.style.display="";
		document.theform.inputflag.value=1;
	}
}

/*�򿪲�Ʒ��ѯҳ��*/
function openQuery(product_id,item_id){	
	if(product_id!=0){
		alert('<%=LocalUtilis.language("message.prodViewError",clientLocale)%> ��')//��Ʒ��ѯҳ��δ��
	}
}

/*��ʾ�˺�λ��*/
function showAcctNum(value){		
	if (trim(value) == "")
		bank_acct_num.innerText = "";
	else
		bank_acct_num.innerText = "(" + showLength(value) + " λ )";
}

/**************************************************************************************************************************/	
	
/*�ͻ���Ϣ*/
function getMarketingCustomer(prefix,readonly){
	
	var cust_id = getElement(prefix, "cust_id").value;	
	var url = '<%=request.getContextPath()%>/marketing/customerInfo.jsp?prefix='+ prefix+ '&cust_id=' + cust_id+'&readonly='+readonly ;	
	var v = showModalDialog(url,'','dialogWidth:720px;dialogHeight:720px;status:0;help:0;');		
	
	if (v != null){
		/*showMarketingCustomer(prefix, v)*/
		getElement(prefix, "cust_id").value = v[7];
		selectProductAndCustomer();
	}	
	
	return (v != null);
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
	if(result!=""){
		$("contractDIV").innerHTML = result;
	}	
}
/**************************************************************************************************************************/
/*δ��˱���*/
function SaveAction(){
	if(document.theform.onsubmit()){
		disableAllBtn(true);
		document.theform.action="contract_aftercheck_edit2.jsp";
		document.theform.submit();
	}
}
/*����˱���*/
function SaveChannelAction(){		
	if(document.theform.onsubmit()){
		if(intrustType_Flag == '1'){
			document.theform.channel_type.value = comboBoxTree.getValue();
			document.theform.channel_id.value = comboBoxParentTree.getValue();
		}
		disableAllBtn(true);
		document.theform.action="contract_aftercheck_edit2.jsp";
		document.theform.submit();
	}
}
	
/*����*/
function CancelAction(){
	tempArray = '<%=sQuery%>'.split('$');
<%if(mode_flag==null){%>
	location = 'contract_aftercheck_list.jsp?page=1&q_productId='+tempArray[0]+'&q_productCode='+tempArray[1]+'&q_cust_name='+tempArray[2]+'&query_contract_bh='+tempArray[3]+'&q_pre_flag='+tempArray[4]+'&pagesize='+tempArray[5]+'&q_check_flag='+tempArray[6];
<%}else{%>
	location = 'purchase_mode.jsp?page='+ tempArray[7] +'&pagesize=' + tempArray[6] +'&productid='+tempArray[0]+ '&product_id='+ tempArray[1] +'&cust_name='+tempArray[2]+'&query_contract_bh='+ tempArray[3]+'&card_id='+tempArray[4] +'&check_flag='+tempArray[5];
<%}%>
}

//�����������Ϲ���Ϣʱ�õ��ĺ���
function validateForm(form){
	if(form.quota_money.value != 0){
		var sum = parseInt(form.rg_money.value) + <%=already_sale%>;
		if(sum > <%=quota_money%>){
			sl_alert('<%=LocalUtilis.language("message.teamQuota",clientLocale)%>');//�û������Ŷ��ڸò�Ʒ���ۼ������۶�����ֵ
			return false;
		}
	}
	//if(!sl_checkChoice(form.product_id, '<%=LocalUtilis.language("class.productName",clientLocale)%> ')){return false;}//��Ʒ����
	//if(form.product_id.value==0){
		//sl_alert('<%=LocalUtilis.language("message.chooseProdName",clientLocale)%> !');//��ѡ���Ʒ����
		//return false;
	//}
	//�Ϲ���ͬ��ſ���
	<%if(Argument.getSyscontrolValue_intrust("C30902")!=1){%>
	    var contract_bh = form.contract_bh;
		if(!sl_check(contract_bh, '<%=LocalUtilis.language("class.constractBH",clientLocale)%> ', 20,0)){return false;}//��ͬ���
		if(contract_bh.value!= ""&&contract_bh.value!=null){
		  var s=contract_bh.value;
		  var len=contract_bh.value.length;            
		  if(len<3){
		    sl_alert('<%=LocalUtilis.language("message.inputContractBHTip",clientLocale)%> ');//����ĺ�ͬ�������Ϊ3λ
		    return false;
		  }
		  else{		     
		     if((s.charCodeAt(len-1)<48 || s.charCodeAt(len-1)>57) || (s.charCodeAt(len-2)<48 || s.charCodeAt(len-2)>57)|| (s.charCodeAt(len-3)<48 || s.charCodeAt(len-3)>57)){
		        sl_alert('<%=LocalUtilis.language("messgae.contractSerialError",clientLocale)%> ��');
		        return false;
		     }   
		  }
		}
	<%}%>
	
	if(form.customer_cust_id.value==""){
			sl_alert('<%=LocalUtilis.language("message.chooseCustomer",clientLocale)%> ��');//��ѡ��ͻ�
			return false;
	}	
	
    if(!sl_checkChoice(form.bank_id, '<%=LocalUtilis.language("class.bankName3",clientLocale)%> ')){return false;}//��������
	if(form.bank_id.value != ""){
		if(document.theform.inputflag.value==1){
			if(!sl_checkChoice(form.bank_acct2, '<%=LocalUtilis.language("class.bankAcct3",clientLocale)%> '))return false;//�����ʺ�
		}
		else{
			if(!sl_check(form.bank_acct, '<%=LocalUtilis.language("class.bankAcct3",clientLocale)%> ', 80, 1)) return false;//�����ʺ�
		}		
	}
	if(!sl_checkDecimal(form.rg_money, '<%=LocalUtilis.language("class.rg_money",clientLocale)%> ', 13, 3, 1)){return false;}//�Ϲ����
	if(!sl_checkChoice(form.jk_type, '<%=LocalUtilis.language("class.feeType",clientLocale)%> ')){return false;}//�ɿʽ
	
	if(!sl_checkDate(form.qs_date_picker,'<%=LocalUtilis.language("class.qsDate",clientLocale)%> ')){return false;}//ǩ������
	if(!sl_checkDate(form.jk_date_picker,'<%=LocalUtilis.language("class.dzDate",clientLocale)%> ')){return false;}//�ɿ�����
	if(!sl_check(form.summary2, '<%=LocalUtilis.language("class.contractSummary",clientLocale)%> ', 100, 0)){return false;}//��ͬ��ע
	
	syncDatePicker(form.qs_date_picker, form.qs_date);
	syncDatePicker(form.jk_date_picker, form.jk_date);

	if(form.jk_date.value < form.qs_date.value)	{
        //�ɿ�����  //����С��  //ǩ������
		sl_alert('<%=LocalUtilis.language("class.dzDate",clientLocale)%><%=LocalUtilis.language("message.cannotLess",clientLocale)%><%=LocalUtilis.language("class.qsDate",clientLocale)%>');
		return false;
	}

	var userid='<%=user_id.intValue()%>';
	if(form.serial_no.value==null||form.serial_no.value=="null"){
		if(form.self_ben_flag.checked){
			form.selfbenflag.value='1';
		}
		else{
			form.selfbenflag.value='0';
		}
	}	
	
	if(form.intrust_flag1.value=='2'){
		if(form.qs_date.value>form.pre_end_date.value){
            //�Ϲ�����  //����  //�ƽ���ʼ����  //Ҫ����
			if(!confirm('<%=LocalUtilis.language("class.subscriptionDate",clientLocale)%><%=LocalUtilis.language("message.greaterThan",clientLocale)%><%=LocalUtilis.language("class.preStartDate",clientLocale)%>��<%=LocalUtilis.language("message.toContinue",clientLocale)%> ��'))
				return;
		}
		if(form.qs_date.value<form.pre_start_date.value){	
		//ѡ���Ƿ���� 
            //�Ϲ�����  //С��  //�ƽ���ʼ����  //Ҫ����
			if(!confirm('<%=LocalUtilis.language("class.subscriptionDate",clientLocale)%><%=LocalUtilis.language("message.lessThan",clientLocale)%><%=LocalUtilis.language("class.preStartDate",clientLocale)%>��<%=LocalUtilis.language("message.toContinue",clientLocale)%> ��'))
				return;
		}
	}	

	if(intrustType_Flag == '1'){
		document.theform.channel_type.value = comboBoxTree.getValue();
		document.theform.channel_id.value = comboBoxParentTree.getValue();
	}
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
	
	<%if(asfund_flag!=null && asfund_flag.intValue()==3 && intrust_flag1.intValue()!=1){%>
		if(!sl_checkChoice(form.prov_level, '<%=LocalUtilis.language("class.incomeLevel",clientLocale)%>'))return false;//���漶��
	<%}%>

	if(form.quota_qualified_num.value != 0){
		if(form.cust_type.value == 1){
			if(parseInt(form.rg_money.value) < 3000000){
				form.already_qualified_num.value = parseInt(form.already_qualified_num.value) + 1;
				if(form.already_qualified_num.value > form.quota_qualified_num.value){
					sl_alert('�ͻ��ڸò�Ʒ���ۼ������۵ĺ�ͬͶ������������ ���ֵ');
					return false;
				}
			}
		}
	}
	
	return sl_check_update();
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

/*
 *��Ӹ���	
 */
function addline()
{
	n++;
	newline=document.all.test.insertRow()
	newline.id="fileRow"+n;
	newline.insertCell().innerHTML="<input type='file' name=file_info"+n+" size='60'>"+"<button type='button' class='xpbutton3' onclick='javascript:removeline(this)'><%=LocalUtilis.language("messgae.remove",clientLocale)%> </button>";//�Ƴ�
}


/*
 *ɾ������	
 */
function removeline(obj)
{
	var objSourceRow=obj.parentNode.parentNode;
	var objTable=obj.parentNode.parentNode.parentNode.parentNode;
	objTable.lastChild.removeChild(objSourceRow);
}

function confirmRemoveAttach(obj,serial_no)
{
	if(confirm('<%=LocalUtilis.language("message.tAreYouSure",clientLocale)%>��'))
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
       	 	root : new Ext.tree.AsyncTreeNode({id:'-1',text:'<%=LocalUtilis.language("class.partnType",clientLocale)%> '}),//������Ϣ
       	 	listeners:{
       	 		beforeload:function(node){//ѡ���������ֵ֮ǰ���¼�   
       	 			if(node.id!='-1')
       	 				this.loader.dataUrl = '<%=request.getContextPath()%>/client/channel/treeData.jsp?value='+node.id;
       	 		},   
       	 		click:function(node)
       	 		{
       	 			invest_type_name = ' ';
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

	setMartetTrench(document.theform.market_trench);	
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

function getprovlevel(){
	<%if(product_sub_flag!=null && product_sub_flag.intValue() == 1){%>
		utilityService.getProvLevel(<%=product_id%>,document.theform.sub_product_id.value,document.theform.prov_flag.value,0,setprovlevel);
	<%}else{%>
		utilityService.getProvLevel(<%=product_id%>,0,document.theform.prov_flag.value,0,setprovlevel);
	<%}%>
}
/*�鿴�Ŷ���Ϣ*/
function selectTeam(value){
	if (value!=""){	
		syncDatePicker(document.theform.qs_date_picker, document.theform.qs_date);

		location = 'contract_aftercheck_edit2.jsp?check_flag=<%=modi_check_flag%>&s_flag=1&sQuery=<%=sQuery%>&link_man=' + value
					+ '&serial_no='+document.theform.serial_no.value
					+ '&market_trench='+document.theform.market_trench.value
					+ '&market_trench_money='+document.theform.market_trench_money.value
					+ '&qs_date='+document.theform.qs_date.value
					+ '&channel_memo='+document.theform.channel_memo.value
					+ '&channel_type='+document.theform.channel_type.value
					+ '&channel_id='+document.theform.channel_id.value
					+ '&channel_cooperation='+document.theform.channel_cooperation.value;
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

	showCnMoney(document.theform.market_trench_money.value,market_trench_money_cn);
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

//�½���Ʒ����
function newChannel(){
	if(DWRUtil.getValue("product_id")==0){
		alert("����ѡ���Ʒ");return false;
	}else if(DWRUtil.getValue("product_sub_flag")==1&&DWRUtil.getValue("sub_product_id")==0){
		alert("����ѡ���Ӳ�Ʒ");return false;
	}else{
		var rr=showModalDialog('/marketing/base/product_market_trench_add.jsp?productId='+ DWRUtil.getValue("product_id") +'&sub_flag='+DWRUtil.getValue("product_sub_flag") +'&productName=<%=product_name%>&sub_product_id='+ DWRUtil.getValue("sub_product_id"), '', 'dialogWidth:400px;dialogHeight:400px;status:0;help:0');
		if( rr!= null){
			sl_update_ok();
			//��Ʒ����������Ϣ
			utilityService.queryMarketTrench(DWRUtil.getValue("product_id"),DWRUtil.getValue("sub_product_id"),matchCustCallBack);
		}
	}
}

//��������
function matchCustCallBack(data){
	var obj_op = document.getElementById("market_trench");
    DWRUtil.removeAllOptions(obj_op);
    DWRUtil.addOptions(obj_op,{'':'<%=LocalUtilis.language("message.select2",clientLocale)%> '});   //��ѡ��
    DWRUtil.addOptions(obj_op,data);
}
</script>
</HEAD>

<BODY class="BODY body-nox">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="post" onsubmit="javascript:return validateForm(this);" ENCTYPE="multipart/form-data">
<!--�����ɹ���־-->
<input type="hidden" id="bSuccess" value="<%=bSuccess%>"/>
<input type="hidden" name="sQuery" value="<%=sQuery%>">
<input type="hidden" name="book_code" value="<%=input_bookCode%>">
<input type="hidden" name="serial_no" value="<%=serial_no%>">
<input type="hidden" name="inputflag" value="<%=inputflag%>"/>
<input type="hidden" name="check_flag" value="<%=check_flag%>">

<!--�ͻ���Ϣ-->
<input type="hidden" name="cust_name" value="<%=cust_name%>"/>
<input type="hidden" name="customer_cust_id" value="<%=cust_id%>"/>
<input type="hidden" name="cust_no" value="<%=cust_no%>"/>
<input type="hidden" name="cust_type" value="<%=cust_type%>"/>
<!--��Ʒ��Ϣ-->
<input type="hidden" name="period_unit" value="<%=period_unit%>">
<input type="hidden" name="intrust_flag1" value="<%=intrust_flag1%>">
<input type="hidden" name="pre_start_date" value="<%=pre_start_date%>">
<input type="hidden" name="pre_end_date" value="<%=pre_end_date%>">
<input type="hidden" name="selfbenflag" value="<%=selfbenflag%>">
<input type="hidden" name="product_id" value="<%=product_id%>">
<input type="hidden" id="product_sub_flag" name="product_sub_flag" value="<%=product_sub_flag%>"/>
<!-- �Ŷ������Ϣ -->
<input type="hidden" name="quota_money" value="<%=quota_money%>">
<input type="hidden" name="already_sale" value="<%=already_sale%>">
<input type="hidden" name="team_id" value="<%=team_id%>">
<input type="hidden" name="serialNo" value="<%=serialNo%>">
<input type="hidden" name="quota_qualified_num" value="<%=quota_qualified_num%>">
<input type="hidden" name="already_qualified_num" value="<%=already_qualified_num%>">

<input type="hidden" id="channel_type" name="channel_type" value="<%=channel_type%>"/>
<input type="hidden" id="channel_id" name="channel_id" value="<%=r_channel_id%>"/>
<input type="hidden" id="channel_rate" name="channel_rate" value=""/>

<div align="left" class="page-title">
	<font color="#215dc6"><b><%=menu_info%></b></font>
</div>
<br/>

<!--��Ʒѡ��-->
<div>
<table  border="0" width="100%" cellspacing="0" cellpadding="3" class="product-list">
	<tr>   
		<td ><b><%=LocalUtilis.language("menu.prodInfo",clientLocale)%></b> </td><!--��Ʒ��Ϣ-->
		<td></td>
	<tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.productID",clientLocale)%> :</td><!--��Ʒ���-->
		<td align="LEFT">
			<input type="text" <%=checkedStyle%> maxlength="16" name="productcode" value="<%=product_code%>" onkeydown="javascript:setProduct(this.value);" size="20">
		</td>
		<td>&nbsp;&nbsp;</td>
		<td>&nbsp;&nbsp;</td>
	</tr>
	<tr>	
		<td align="right">*<%=LocalUtilis.language("class.productName",clientLocale)%> :</td><!--��Ʒѡ��-->
		<td align=left colspan=3>
			<input class="edline" readonly="readonly" name="product_name" size="70" maxlength=40 onkeydown="javascript:nextKeyPress(this)" value="<%=product_name%>">
		</td>
	</tr>
	
	<%if(product_sub_flag.intValue() == 1){%>
		<tr>	
			<td align="right"><b>*<%=LocalUtilis.language("class.subProductID",clientLocale)%> :</b></td><!--�Ӳ�Ʒѡ��-->
			<td align=left colspan=3>
				<select size="1" <%if(serial_no!= null&&serial_no.intValue()!=0){out.print("disabled");}%> id="sub_product_id" name="sub_product_id" onkeydown="javascript:nextKeyPress(this)" class="productname">
					<%=Argument.getSubProductOptions(product_id, new Integer(0),sub_product_id)%> 
				</select>&nbsp;&nbsp;
				<input type="hidden" name="productId" value="<%= product_id%>"/>
				<button type="button"  class="xpbutton2" name="show_sub_info" title="�鿴�Ӳ�Ʒ��Ϣ" onclick="javascript:openSubProduct();"><%=LocalUtilis.language("message.view",clientLocale)%></button><!-- �鿴 -->
			</td>
		</tr>
	<%}%>
	<tr>
		<!--��ͬ���-->
        <td align="right" <%if(Argument.getSyscontrolValue_intrust("C30902")==1){out.print("style='display:none'");}%>><b><%=LocalUtilis.language("class.constractBH",clientLocale)%> </b></td> 
		<td <%if(Argument.getSyscontrolValue_intrust("C30902")==1){out.print("style='display:none'");}%>><input <%= checkedStyle%> name="contract_bh" size="20" maxlength=20 onkeydown="javascript:nextKeyPress(this)" value="<%=contract_bh%>"></td>
		<td align="right"><%=LocalUtilis.language("class.contractID",clientLocale)%> :</td><!--��ͬ���-->		
        <td>
			<input name="contract_sub_bh" size="40" maxlength=40 onkeydown="javascript:nextKeyPress(this)" 
				value="<%=contract_sub_bh%>">&nbsp;&nbsp;<INPUT type="button" class="xpbutton3" onclick="javascript:check();"
															value='<%=LocalUtilis.language("messgae.CheckBH",clientLocale)%> '><!--��֤���-->
			<div id="contractDIV"></div></td>
	</tr>
</table>
<table  border="0" width="100%" cellspacing="0" cellpadding="3" class="product-list">
	<!--�ͻ�ѡ��-->
	<tr>   
		<td ><b><%=LocalUtilis.language("message.customerInfomation",clientLocale)%> </b></td><!--�ͻ���Ϣ-->
		<td>
			<!--�ͻ���Ϣ--><!--�鿴-->
            <button type="button"  id ="cust_button" class="xpbutton3" accessKey=e name="btnEdit" title='<%=LocalUtilis.language("message.customerInfomation",clientLocale)%>' onclick="javascript:getCustomer(<%=cust_id%>);"><%=LocalUtilis.language("message.view",clientLocale)%> </button>
			<span id="cust_message" style="display:none;color:red;"><%=LocalUtilis.language("message.noCustomerInfo",clientLocale)%> </span><!--�ͻ���Ϣ������-->
		</td>
	<tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.customerName",clientLocale)%> :</td> <!--�ͻ�����-->
		<td ><input maxlength="100" readonly class='edline'  name="customer_cust_name" size="50" onkeydown="javascript:nextKeyPress(this);" value="<%=cust_name%>">&nbsp;&nbsp;&nbsp;
		</td>
		<td align="right"><%=LocalUtilis.language("class.accountManager",clientLocale)%> :</td><!--�ͻ�����-->
		<td ><input maxlength="100" readonly class='edline'  name="customer_service_man" size="50" onkeydown="javascript:nextKeyPress(this);" value="<%=Utility.trimNull(Argument.getManager_Name(service_man))%>">&nbsp;&nbsp;&nbsp;
		</td>
	</tr>   	
	<tr>
		<td align="right"><%=LocalUtilis.language("class.customerType",clientLocale)%> :</td><!--�ͻ����-->
		<td ><INPUT readonly class='edline' name="customer_cust_type_name" size="20" value="<%=cust_type_name%>" onkeydown="javascript:nextKeyPress(this);"></td>
		<td align="right"><%=LocalUtilis.language("class.customerCardID",clientLocale)%> :</td><!--֤������-->
		<td><input readonly class='edline' name="customer_card_id" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%=card_id%>" size="20"></td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.companyPhone",clientLocale)%> :</td> <!--��˾�绰-->
		<td><input readonly class='edline' name="customer_o_tel" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%=o_tel%>" size="20"></td>
		<td align="right"><%=LocalUtilis.language("class.customerMobile",clientLocale)%> :</td><!--�ֻ�-->
		<td><input readonly class='edline' name="customer_mobile" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%=mobile%>" size="20"></td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.customerHTel",clientLocale)%> :</td>  <!--��ͥ�绰-->
		<td><input readonly class='edline' name="customer_h_tel" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%=h_tel%>" size="20"></td>
		<td align="right"><%=LocalUtilis.language("class.email",clientLocale)%> :</td><!--�����ʼ�-->
		<td><input readonly class='edline' name="customer_email" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%=e_mail%>" size="20"></td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.postAddress",clientLocale)%> :</td><!--��ϵ��ַ-->
		<td ><INPUT readonly class='edline' name="customer_post_address" size="50" value="<%=post_address%>" onkeydown="javascript:nextKeyPress(this);"></td>
		<td align="right"><%=LocalUtilis.language("class.postcode",clientLocale)%> :</td><!--��������-->
		<td ><INPUT readonly class='edline' name="customer_post_code" size="20" value="<%=post_code%>" onkeydown="javascript:nextKeyPress(this);"></td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.isLink3",clientLocale)%> :</td><!--��������־-->
		<td><input type="checkbox" disabled name="customer_is_link" value="<%=is_link%>" <%if(is_link.intValue()==1) out.print("checked");%> class="flatcheckbox"></td>
		<td align="right">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>	
</table>
<table  border="0" width="100%" cellspacing="0" cellpadding="3" class="product-list">	
	<!--�Ϲ���ͬ��Ϣ-->
	<tr>
		<td><b><%=LocalUtilis.language("message.contractInfo",clientLocale)%> </b></td>  <!--��ͬ��Ϣ-->
	<tr>
	<tr>
	<td align="right" >*<%=LocalUtilis.language("class.bankName5",clientLocale)%> :</td><!--��������������-->
		<td >
			<select size="1" name="bank_id" <%=edMap.containsKey("BANK_ID")?"":checkedStyle2%> onchange="javascript:selectBank(this.value);" style="WIDTH:180px">
				<%=Argument.getBankOptions(bank_id)%>
			</select>
			<input type="text" name="bank_sub_name" <%=edMap.containsKey("BANK_SUB_NAME")?"":checkedStyle%> size="20" onkeydown="javascript:nextKeyPress(this)" value="<%=bank_sub_name%>">
		</td>
		<td align="right">*<%=LocalUtilis.language("class.bandAcct4",clientLocale)%> :</td><!--�����������ʺ�-->
		<td>
			<input <%=inputflag==1?"style='display:none'":""%> type="text" name="bank_acct" value="<%=bank_acct%>" <%=edMap.containsKey("BANK_ACCT")?"":checkedStyle%> onkeydown="javascript:nextKeyPress(this)" onkeyup="javascript:showAcctNum(this.value)" size="30"/>
			<select <%=inputflag==2?"style='display:none'":""%> size="1" name="bank_acct2" <%= checkedStyle2%> onkeydown="javascript:nextKeyPress(this)"  style="WIDTH: 200px">
				<%=preCodeOptions%>  
			</select>&nbsp;&nbsp;										
			<!--����-->
            <%if(inputflag==2){%><button type="button"  class="xpbutton2" accessKey=x name="btnSelect" title='<%=LocalUtilis.language("message.choose",clientLocale)%>' onclick="javascript:changeInput(this);"><%=LocalUtilis.language("message.choose",clientLocale)%> (<u>X</u>)</button><%}%> 
			<span id="bank_acct_num" class="span"></span> 
		</td>
	</tr>
	<!--��ϴǮ �������-->
	<tr>
		<td align="right"><%=LocalUtilis.language("class.bankAccountType",clientLocale)%> :</td><!--�����˺�����-->
		<td >
			<select size="1" name="bank_acct_type" <%= checkedStyle2%> onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 250px">
				<%=Argument.getBankAcctType(bank_acct_type)%>
			</select>
		</td>
		<td align="right"><%=LocalUtilis.language("class.customerGainAcct",clientLocale)%> :</td><!--�����ʺŻ���-->
		<td>
			<input <%if(user_id.intValue()!=25/*25����*/){%>class="edline" readonly<%}%> name="customer_gain_acct" style="WIDTH: 250px" onkeydown="javascript:nextKeyPress(this)"  value="<%=gain_acct%>">
		</td>				
	</tr>			
	<tr>			
		<td align="right">*<%=LocalUtilis.language("class.feeType",clientLocale)%> :</td><!--�ɿʽ-->
		<td>
			<select <%=edMap.containsKey("JK_TYPE")?"":checkedStyle2%> size="1" name="jk_type" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px">
				<%=Argument.getJkTypeOptions(jk_type)%>
			</select>				
		</td>
		<td align="right"><%if(open_flag.intValue()==1){%><%=LocalUtilis.language("class.bonusFlag",clientLocale)%> :<%}%></td><!--������䷽ʽ-->
		<td><%if(open_flag!=null && open_flag.intValue()==1){%>
				<select size="1" name="bonus_flag" id="bonus_flag" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px">
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
		<td align="right">*<%=LocalUtilis.language("class.rg_money",clientLocale)%> :</td><!--�Ϲ����-->
		<td><input name="rg_money" <%= checkedStyle%> size="20" value="<%=rg_money%>"  onblur="javascript:checkSellInfo();" onkeydown="javascript:nextKeyPress(this)" onkeyup="javascript:showCnMoney(this.value,rg_money_cn)"> 
			<span id="rg_money_cn" class="span"><%=rg_money_cn%></span>
		</td>
		<td align="right"><%if(open_flag.intValue()==1){%><%=fee_type_name%><%=LocalUtilis.language("class.feeTypeName",clientLocale)%> :<%}%></td><!--�۽ɷ�ʽ-->
		<td ><%if(open_flag.intValue()==1){%>
			<select size="1" <%= checkedStyle2%> name="fee_jk_type" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px">
				<%=Argument.getFeeJkTypeOptions(fee_jk_type.intValue())%>
			</select>
			<%}%>
		</td>
	</tr>
	<tr>
		<td align="right">�������� :</td>
		<td>
			<select id="market_trench" name="market_trench" style="width: 280px;" onclick="javascript:setMartetTrench(this);">
				<option value="">��ѡ��</option>
		<%
			if(markList != null) {
				for (int u=0; u<markList.size(); u++){
					markMap = (Map)markList.get(u);
					if((r_channel_id.intValue() == Utility.parseInt(Utility.trimNull(markMap.get("CHANNEL_ID")), new Integer(0)).intValue()) && channel_type.equals(Utility.trimNull(markMap.get("CHANNEL_TYPE"))))
						market_selected = "selected";
					else
						market_selected = "";
		%>
				<option <%=market_selected %> 
					value="<%=Utility.trimNull(markMap.get("CHANNEL_TYPE")) %>@<%=Utility.trimNull(Utility.parseInt(Utility.trimNull(markMap.get("CHANNEL_ID")), new Integer(0))) %>@<%=Utility.trimNull(Utility.parseDecimal(Utility.trimNull(markMap.get("CHANNEL_FARE_RATE")), new BigDecimal(0),4,"1")) %>"
				><%=Utility.trimNull(markMap.get("CHANNEL_TYPE_NAME")) %><%="".equals(Utility.trimNull(markMap.get("CHANNEL_NAME"))) ? "" : "-"%><%=Utility.trimNull(markMap.get("CHANNEL_NAME")) %>[���ʣ�<%=Utility.trimNull(Utility.parseDecimal(Utility.trimNull(markMap.get("CHANNEL_FARE_RATE")), new BigDecimal(0),4,"100")) %>]</option>
		<%		}
			} %>
			</select>&nbsp;&nbsp;
			<button type="button"  class="xpbutton2" id="newChannel1" name="newChannel1" onclick="javascript:newChannel();">�½�</button>
		</td>
		<td align="right">�������� :</td>
		<td><input name="market_trench_money" size="20" value="<%=market_trench_money %>" onkeydown="javascript:nextKeyPress(this)" onkeyup="javascript:showCnMoney(this.value,market_trench_money_cn)"/>
			<span id="market_trench_money_cn" class="span"></span></td>
	</tr>
	<tr>
		<td align="right">*<%=LocalUtilis.language("class.qsDate",clientLocale)%> :</td><!--ǩ������-->
		<td>
			<INPUT TYPE="text" NAME="qs_date_picker" class=selecttext 
			<%if(qs_date.intValue()==0){%> value="<%=Format.formatDateLine(Utility.getCurrentDate())%>"
			<%}else{%>value="<%=Format.formatDateLine(qs_date)%>"<%}%> >
			<INPUT TYPE="button" value="��" class=selectbtn 
			onclick="javascript:CalendarWebControl.show(theform.qs_date_picker,theform.qs_date_picker.value,this);"
			tabindex="13">
			<INPUT TYPE="hidden" NAME="qs_date" value="<%=qs_date%>">
		</td>
	</tr>
	<tr>
		<td align="right">*<%=LocalUtilis.language("class.dzDate",clientLocale)%> :</td><!--�ɿ�����-->
		<td>
			<INPUT TYPE="text" NAME="jk_date_picker" class=selecttext <%=edMap.containsKey("JK_DATE")?"":checkedStyle2%>
			<%if(jk_date.intValue()==0){%> value="<%=Format.formatDateLine(Utility.getCurrentDate())%>"
			<%}else{%>value="<%=Format.formatDateLine(jk_date)%>"<%}%> >
			<INPUT TYPE="button" value="��" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.jk_date_picker,theform.jk_date_picker.value,this);" tabindex="14">
			<INPUT TYPE="hidden" NAME="jk_date" value="<%=jk_date%>">
		</td>
		<td align="right"><%=LocalUtilis.language("class.selfBenFlag",clientLocale)%> :</td><!--�����־-->
		<td><input name="self_ben_flag" class="flatcheckbox" <%= checkedStyle2%> type="checkbox" value="" <%if(selfbenflag.intValue()==1) out.print("checked");%> onkeydown="javascript:nextKeyPress(this)"></td>
	</tr>
	<tr style="display: none;">
		<td  width="100px" align="right"><%=LocalUtilis.language("class.partnType",clientLocale)%> :</td> 
		<td><div id="comboBoxTree"></div></td>
		<td align="right"><%=LocalUtilis.language("class.partnName",clientLocale)%> :</td><!--������Դ-->
		<td><div id="comboBoxParentTree"></div></td>
	</tr>
	<tr >
		<td align="right"><%=LocalUtilis.language("class.partnAttachmentInfo",clientLocale)%> :</td><!--����������Ϣ-->
		<td>
			<input name="channel_memo" size="1" style="WIDTH: 250px" onkeydown="javascript:nextKeyPress(this)"  value="<%=channel_memo%>">
		</td>		
		<td align="right"><%=LocalUtilis.language("class.channelCooperation",clientLocale)%> :</td><!-- ����������ʽ -->
		<td>
			<SELECT size="1" name="channel_cooperation" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px">
					<%=Argument.getChannelCooperationOptions(channel_cooperation)%>
			</SELECT>
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.linkMan",clientLocale)%> :</td><!--��ͬ������Ա-->
		<td>
			<select size="1" name="link_man" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px" onchange="javascript:selectTeam(this.value);">
				<%=Argument.getOperatorOptionsByRoleId(new Integer(2),link_man)%>
			</select>
		</td>
	</tr>
	<%if(!"".equals(Utility.trimNull(cityNameOptions))){%>
		<tr>
			<td align="right"><%=LocalUtilis.language("class.citySerialNO",clientLocale)%> :</td><!--��ͬ�ƽ��-->
			<td colspan="3">
				<select <%= checkedStyle2%> size="1" name="city_serialno" <%= checkedStyle2%> onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px">
					<%=cityNameOptions%>
				</select>
			</td>
		</tr>
	<%}%>		
	<tr>
<%if(intrust_flag1.intValue()!=1){%>
			<td align="right"><%=LocalUtilis.language("class.provLevel",clientLocale)%> :</td><!-- �������ȼ� -->
			<td>					
				<select <%=edMap.containsKey("PROV_FLAG")?"":checkedStyle2%> name="prov_flag" style="WIDTH: 100px" <%if(asfund_flag!=null&&asfund_flag.intValue()==3){%>onchange="javascript:getprovlevel()"<%}%> onkeydown="javascript:nextKeyPress(this)">
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
			<td align="right">*<%=LocalUtilis.language("class.incomeLevel",clientLocale)%> :</td><!-- ���漶�� -->
			<td>
				<div id="div_prov_level">
				<select <%=edMap.containsKey("PROV_LEVEL")?"":checkedStyle2%> name="prov_level" style="width:100px">
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
} %>
			</tr>
			<tr style="display: <%=SUB_COOPER == 2 ? "none" : ""%>;">
				<td align="right"><%=LocalUtilis.language("message.cooperationWay",clientLocale)%> :</td><!--������ʽ-->
				<td>
					<input <%if (with_bank_flag != null && with_bank_flag.intValue()==1)out.print("checked");%>
							onkeydown="javascript:nextKeyPress(this)" type="checkbox" class="flatcheckbox" value="<%=with_bank_flag %>" name="with_bank_flag" onclick="javascript:showHtyh();">
							<%=LocalUtilis.language("intrsut.home.silvertrustcooper",clientLocale)%>&nbsp;&nbsp;&nbsp;&nbsp;<!--���ź���-->
					<span id="htyhmc_v" <%if(with_bank_flag.intValue()!=1) out.print("style='display:none'");%>>
						<%=LocalUtilis.language("class.withBankId",clientLocale)%>:	<!-- �������� -->
						<select <%= checkedStyle2%> size="1" name="ht_bank_id" style="WIDTH: 220px">
							<%=Argument.getBankOptions(Utility.trimNull(ht_bank_id))%>
						</select>
						<input type="text" name="ht_bank_sub_name" size="20" onkeydown="javascript:nextKeyPress(this)" value="<%=Utility.trimNull(ht_bank_sub_name)%>">
					</span>
					<input <%if (with_security_flag != null && with_security_flag.intValue()==1)out.print("checked");%>
						onkeydown="javascript:nextKeyPress(this)" type="checkbox"  
						class="flatcheckbox" value="<%=with_security_flag %>" name="with_security_flag"
						><%=LocalUtilis.language("message.cooperation2",clientLocale)%> &nbsp;&nbsp;<!--֤�ź���-->
					<input <%if (with_private_flag != null && with_private_flag.intValue()==1)out.print("checked");%>
						onkeydown="javascript:nextKeyPress(this)" type="checkbox" 
						class="flatcheckbox" value="<%=with_private_flag %>" name="with_private_flag"
						><%=LocalUtilis.language("class.cooperation4",clientLocale)%> &nbsp;&nbsp;<!--˽ļ�������-->
				</td>
			</tr>
	<tr>
		<td width="120px" align="right">�ÿ���� :</td><!--�ÿ����-->
		<td>   
		<input readonly class="edline" name="is_ykgl" size="40" value="<%if(is_ykgl.equals("1")){out.print("��");}else{out.print("��");}%>">
		</td>
		<td width="120px" align="right">���ֳ�����:</td>
		<td>   
			<input type="checkbox" class="flatcheckbox" name="spot_deal" value="1" <%=spot_deal.intValue() == 1 ? "checked" : ""%> disabled>
		</td>
	</tr>
	<tr>
		<td align="right">��ͬ��ϵ�� :</td>
		<td>
			<select disabled size="1" name="cust_message">
			<%=Argument.getCustOptions(cust_id,contact_id) %>
			</select>
		</td>
	</tr>
	<tr>
		<td align="right" vAlign="top" width="120px">��ͬԤ�������� :</td><!--���к�ͬԤ��������-->
		<td  colspan=3><textarea readonly rows="3" id="xthtyjsyl" name="xthtyjsyl" onkeydown="javascript:nextKeyPress(this)" cols="83"><%=xthtyjsyl %></textarea></td>
	</tr>	
		<tr>
			<td align="right"><%=LocalUtilis.language("class.description",clientLocale)%> :</td><!--��ע-->
			<td colspan="3">
			<textarea rows="3" <%if(check_flag.intValue()==2){out.print("readonly style=edline_textarea");}%> name="summary2" onkeydown="javascript:nextKeyPress(this)" cols="83"><%=summary%></textarea>
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
          	<td class="paramTitle"align="right"><%=LocalUtilis.language("menu.filesAdd",clientLocale)%> :</td><!-- ��Ӹ��� -->
            <td colspan="3">          	
            	<table id="test" width="100%">
            		<tr >
            			<td>
		            	<input type="file" name="file_info" size="60">&nbsp;
		            	<img title="<%=LocalUtilis.language("message.tSelectAdditionalFiles",clientLocale)%> " src="<%=request.getContextPath()%>/images/minihelp.gif"><!--ѡ�񸽼��ļ�-->
		            	</td>
		            </tr>
		        </table>
		        <button type="button"  class="xpbutton6" onclick="addline()"><%=LocalUtilis.language("class.moreMccessories",clientLocale)%> <!--�����˴���Ӹ��฽��--></button> 
            </td>
        </tr>	
</table>
</div>

<div align="right">
	<br>
	<!--δ��˱���-->
    <button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" <%if(check_flag.intValue()>=2){out.print("style=display:none");}%> onclick="javascript:SaveAction();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;
	<%if(check_flag.intValue()>=2){%>
	<!--����˱���-->
    <button type="button"  class="xpbutton4" accessKey=s id="btnSave" name="btnSave" onclick="javascript:SaveChannelAction();"><%=LocalUtilis.language("message.update",clientLocale)+LocalUtilis.language("message.save",clientLocale)%> </button>
	&nbsp;&nbsp;&nbsp;&nbsp;
	<%} %>
    <button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:CancelAction();"><%=LocalUtilis.language("message.back",clientLocale)%> (<u>C</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</div>
</form>
<p>
<%
contract.remove();
customer.remove();
customer_inst.remove();
product.remove();
sale_parameter.remove();
channel.remove();
%>
<script language=javascript>
window.onload = function(){
	var v_bSuccess = document.getElementById("bSuccess").value;
	var v_cust_no = document.getElementsByName("cust_no")[0].value;
	
	if(v_cust_no==""){
		document.getElementById("cust_message").style.display="";
		document.getElementById("cust_button").style.display="none";
	}else{
		document.getElementById("cust_button").style.display="";
	}
	
	if(v_bSuccess=="true"){		
		sl_update_ok();
		tempArray = '<%=sQuery%>'.split('$');
		<%if(mode_flag==null){%>
			location = 'contract_aftercheck_list.jsp?q_check_flag='+91;
		<%}else{%>
			location = 'purchase_mode.jsp?page='+ tempArray[7] +'&pagesize=' + tempArray[6] +'&productid='+tempArray[0]+ '&product_id='+ tempArray[1] +'&cust_name='+tempArray[2]+'&query_contract_bh='+ tempArray[3]+'&card_id='+tempArray[4] +'&check_flag='+tempArray[5];
		<%}%>
	}
}
</script>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>
