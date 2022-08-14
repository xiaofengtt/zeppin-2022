<%@ page contentType="text/html; charset=GBK" import="enfo.crm.web.*,enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*,enfo.crm.marketing.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<% // û��ԤԼ���Ϲ���ͬ �½�
//user_id = new Integer(22);
Customer_INSTLocal  customer_inst = EJBFactory.getCustomer_INST();//ͬ���ÿͻ�����
SaleParameterLocal sale_parameter = EJBFactory.getSaleParameter();
CustomerLocal customer = EJBFactory.getCustomer();
ContractLocal contract = EJBFactory.getContract();
SaleParameterVO salevo = new SaleParameterVO();
CustomerVO c_vo  = new CustomerVO();

Map cust_map = null;
//���ҳ�洫�ݱ���
String sQuery = request.getParameter("sQuery");
Integer serial_no = null;//�Ϲ���ϢID �༭��
String re_contract_bh = "";
String money_origin = "";
String sub_money_origin = "";
Integer product_id = Utility.parseInt(request.getParameter("product_id"), overall_product_id);//��ƷID
Integer sub_product_id = Utility.parseInt(request.getParameter("sub_product_id"),new Integer(0));//�Ӳ�ƷID
int pos_flag = Utility.parseInt(request.getParameter("pos_flag"),0); //֤��ɨ���־
String session_cust_name = Utility.trimNull(session.getAttribute("name"));

Integer valid_period = null;
Integer period_unit = new Integer(2); //��

int contract_bh_flag = Argument.getSyscontrolValue_intrust("C30902");//�Ϲ���ͬ�ĺ�ͬ����Զ���ȡ��־ 1: �Զ���ȡ 2: ��������
int inputflag = Utility.parseInt(request.getParameter("inputflag"), 2);//�����ʺ����� ���� ����ѡ�� ��־
int SUB_COOPER = Argument.getSyscontrolValue_intrust("SUB_COOPER");
int r_selfbenflag = 1;
String is_ykgl="";
String xthtyjsyl="";
boolean bSuccess = false;
DocumentFile2 file = null;

if("POST".equals(request.getMethod())){
	file = new DocumentFile2(pageContext);
	file.parseRequest();

	//��ñ�������
	sQuery = file.getParameter("sQuery");
	Integer r_cust_id = Utility.parseInt(file.getParameter("customer_cust_id"), null);
	Integer r_product_id = Utility.parseInt(file.getParameter("product_id"), null);
	Integer r_link_man = Utility.parseInt(file.getParameter("link_man"), input_operatorCode);
	BigDecimal r_rg_money = Utility.parseDecimal(file.getParameter("rg_money"),new BigDecimal(0));
	Integer r_bonus_flag = Utility.parseInt(file.getParameter("bonus_flag"),new Integer(1));
	String r_jk_type = file.getParameter("jk_type");
	String r_bank_id = file.getParameter("bank_id");
	String r_bank_sub_name = Utility.trimNull(file.getParameter("bank_sub_name"));
	String r_gain_acct = file.getParameter("customer_gain_acct");

	inputflag = Utility.parseInt(file.getParameter("inputflag"), 2);//�����ʺ����� ���� ����ѡ�� ��־
	String r_bank_acct = file.getParameter(inputflag==1?"bank_acct2":"bank_acct");
	
	Integer r_service_man = Utility.parseInt(file.getParameter("service_man"), input_operatorCode);
	String r_summary = Utility.trimNull(file.getParameter("summary2"));
	String r_contract_bh = file.getParameter("contract_bh");
	valid_period = Utility.parseInt(file.getParameter("valid_period"), null);
	period_unit = Utility.parseInt(file.getParameter("period_unit"), null); // ���ǵ��������ҵļ����ԣ�Ĭ��ֵ��Ϊnull
	Integer r_qs_date = Utility.parseInt(file.getParameter("qs_date"), new Integer(Utility.getCurrentDate()));
	Integer r_jk_date = Utility.parseInt(file.getParameter("jk_date"), new Integer(Utility.getCurrentDate()));//�Ĳ��������ϣ�������㴫�뵱ǰʱ��
	Integer r_check_man = Utility.parseInt(file.getParameter("check_man"), new Integer(0));
	String r_bank_acct_type = Utility.trimNull(file.getParameter("bank_acct_type"));
	//Integer r_city_serialno = Utility.parseInt(file.getParameter("city_serialno"), new Integer(0));
	String r_contract_sub_bh = file.getParameter("contract_sub_bh");
	int r_fee_jk_type = Utility.parseInt(file.getParameter("fee_jk_type"),0);
	r_selfbenflag = Utility.parseInt(file.getParameter("selfbenflag"),1);
	Integer r_sub_product_id = Utility.parseInt(Utility.trimNull(file.getParameter("sub_product_id")),new Integer(0));
	String channel_type = Utility.trimNull(file.getParameter("channel_type"));
	String channel_memo = Utility.trimNull(file.getParameter("channel_memo"));
	String channel_cooperation = Utility.trimNull(file.getParameter("channel_cooperation"));
	Integer r_channel_id = Utility.parseInt(Utility.trimNull(file.getParameter("channel_id")),new Integer(0));
	BigDecimal market_trench_money = Utility.parseDecimal(Utility.trimNull(file.getParameter("market_trench_money")),new BigDecimal(0));
	Integer r_with_bank_flag = Utility.parseInt(file.getParameter("with_bank_flag"), new Integer(0));
	Integer r_with_security_flag = Utility.parseInt(file.getParameter("with_security_flag"), new Integer(0));
	Integer r_with_private_flag = Utility.parseInt(file.getParameter("with_private_flag"), new Integer(0));
	Integer r_prov_flag = Utility.parseInt(file.getParameter("prov_flag"), new Integer(1));
	String r_prov_level = Utility.trimNull(file.getParameter("prov_level"));

	String r_ht_bank_id =  Utility.trimNull(file.getParameter("ht_bank_id"));
	String r_ht_bank_sub_name =  Utility.trimNull(file.getParameter("ht_bank_sub_name"));

	Integer recommend_man = Utility.parseInt(file.getParameter("recommend_man"),new Integer(0));
	String bank_province = Utility.trimNull(file.getParameter("bank_province"));
	String bank_city = Utility.trimNull(file.getParameter("bank_city"));
	Integer city_serialno = Utility.parseInt(file.getParameter("city_serialno"),new Integer(0));
	BigDecimal expect_ror_lower = Utility.parseDecimal(file.getParameter("expect_ror_lower"),new BigDecimal(0.00),4,"0.01");
	BigDecimal expect_ror_upper = Utility.parseDecimal(file.getParameter("expect_ror_upper"),new BigDecimal(0.00),4,"0.01");
	//Integer contact_id=Utility.parseInt(file.getParameter("cust_message"),null);//��ͬ��ϵ��
	String ht_cust_name = Utility.trimNull(file.getParameter("ht_cust_name"));
	String ht_cust_address = Utility.trimNull(file.getParameter("ht_cust_address"));
	String ht_cust_tel = Utility.trimNull(file.getParameter("ht_cust_tel"));

	is_ykgl=Utility.trimNull(file.getParameter("is_ykgl"));//�ÿ������־
	xthtyjsyl=Utility.trimNull(file.getParameter("xthtyjsyl"));//���к�ͬԤ��������
	money_origin=Utility.trimNull(file.getParameter("money_origin"));//�ʽ�/�ʲ���Դ
	sub_money_origin=Utility.trimNull(file.getParameter("sub_money_origin"));//�ʽ�/�ʲ���Դ(����)

	Integer spot_deal = Utility.parseInt(file.getParameter("spot_deal"), new Integer(2));
	String property_souce =Utility.trimNull(file.getParameter("property_souce"));
	String other_explain =Utility.trimNull(file.getParameter("other_explain"));
	Integer contract_type = Utility.parseInt(Utility.trimNull(file.getParameter("contract_type")),new Integer(0));

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
	BigDecimal r_already_sale = Utility.parseDecimal(file.getParameter("already_sale"),new BigDecimal(0));
	Integer r_already_qualified_num = Utility.parseInt(Utility.trimNull(file.getParameter("already_qualified_num")),new Integer(0));

	if (r_already_sale.equals(new BigDecimal(0)))
		r_already_sale = r_rg_money;
	else
		r_already_sale = r_already_sale.add(r_rg_money);

	//ͬ���ͻ���Ϣ
	String cust_name = "";
	if (r_cust_id.intValue()>0) { //�ͻ�����ֵ
		c_vo.setCust_id(r_cust_id);
		c_vo.setInput_man(input_operatorCode);
		List rsList_cust = customer.listByControl(c_vo);
		if (rsList_cust.size()>0) {
			Map map_cust = (Map)rsList_cust.get(0);
			c_vo.setCust_no(Utility.trimNull(map_cust.get("CUST_NO")));
			c_vo.setCust_name(Utility.trimNull(map_cust.get("CUST_NAME")));
			cust_name = Utility.trimNull(map_cust.get("CUST_NAME"));
			c_vo.setCust_type(Utility.parseInt(Utility.trimNull(map_cust.get("CUST_TYPE")),new Integer(0)));
			c_vo.setService_man(Utility.parseInt(Utility.trimNull(map_cust.get("SERVICE_MAN")),new Integer(0)));
			c_vo.setLegal_man(Utility.trimNull(map_cust.get("LEGAL_MAN")));
			c_vo.setContact_man(Utility.trimNull(map_cust.get("CONTACT_MAN")));
			c_vo.setPost_code((Utility.trimNull(map_cust.get("POST_CODE"))));
			c_vo.setPost_address(Utility.trimNull(map_cust.get("POST_ADDRESS")));
			c_vo.setCard_type(Utility.trimNull(map_cust.get("CARD_TYPE")));
			c_vo.setCard_id(Utility.trimNull(map_cust.get("H_CARD_ID")));
			c_vo.setMobile(Utility.trimNull(map_cust.get("H_MOBILE")));
			c_vo.setE_mail(Utility.trimNull(map_cust.get("H_E_MAIL")));
			c_vo.setInput_man(input_operatorCode);
		}

		customer_inst.cope_customers(c_vo);
	}

	//��������
	ContractVO s_cont_vo = new ContractVO();

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
	s_cont_vo.setValid_period(valid_period);
	s_cont_vo.setPeriod_unit(period_unit);
	s_cont_vo.setQs_date(r_qs_date);
	s_cont_vo.setJk_date(r_jk_date);
	s_cont_vo.setCheck_man(r_check_man);
	s_cont_vo.setBank_acct_type(r_bank_acct_type);
	//s_cont_vo.setCity_serialno(r_city_serialno);
	s_cont_vo.setContract_sub_bh(r_contract_sub_bh);
	s_cont_vo.setFee_jk_type(r_fee_jk_type);
	s_cont_vo.setSelf_ben_flag(r_selfbenflag);
	s_cont_vo.setInput_man(input_operatorCode);
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
	s_cont_vo.setSpot_deal(spot_deal);
	s_cont_vo.setMoney_origin(money_origin);
	s_cont_vo.setSub_money_origin(sub_money_origin);
	s_cont_vo.setProperty_source(property_souce);
	s_cont_vo.setOther_explain(other_explain);
	s_cont_vo.setContract_type(contract_type);
	
	Object[] resultSet = contract.appendNoPre(s_cont_vo);

	serial_no = (Integer)resultSet[0];
	re_contract_bh = (String)resultSet[1];
	bSuccess = file.uploadAttchment(new Integer(5),serial_no,"",null,null,input_operatorCode);

	salevo.setSerial_NO(r_serialNo);
	salevo.setTeamID(r_team_id);
	salevo.setProductID(r_product_id);
	salevo.setSerial_no_subscribe(new Integer(0));
	salevo.setAlreadysale(r_already_sale);
	salevo.setAlready_qualified_num(r_already_qualified_num);
	salevo.setSub_product_id(r_sub_product_id);
	sale_parameter.modiAlreadysale(salevo,input_operatorCode);

	bSuccess = true;

	String product_name = Argument.getProductName(r_product_id);
	Argument.addSysMessage(r_cust_id, "¼�����Ϲ���ͬ"
						, "¼�����Ϲ���ͬ���ͻ����ƣ�"+cust_name+"����Ʒ���ƣ�"+product_name
						, input_operatorCode);

} else if (!"".equals(session_cust_name)){//ͨ��֤��ɨ����Ϣ��ѯ�ͻ���Ϣ
	//ͨ���ͻ�������֤����������֤�ͻ��Ƿ���ڣ����������½�
	c_vo.setCust_name((String)session.getAttribute("name"));
	c_vo.setCard_id((String)session.getAttribute("card_id"));
	List cust_list = customer.queryByCustNo(c_vo);
	if (cust_list.size()>0) cust_map = (Map)cust_list.get(0);
}
%>
<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("menu.addSubscriptionInfo",clientLocale)%>��<%=LocalUtilis.language("message.withoutBooked",clientLocale)%> ��</TITLE><!--�Ϲ���Ϣ����--><!--δԤԼ-->
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
<script type="text/javascript" src="<%=request.getContextPath()%>/includes/jQuery/js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/includes/jQuery/js/jquery-ui-1.7.2.custom.min.js"></script>
<script language=javascript>
	var j$ = jQuery.noConflict();
</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/includes/jQuery/LeeDialog/dialog.js"></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/contract.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/utilityService.js'></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/widgets/ext/resources/css/ext-base.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/widgets/ext/resources/css/ext-all.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/widgets/ext/resources/css/ComboBoxTree.js"></script>
<script language="javascript">
var n=1;

window.onload = function() {
		if (document.theform.product_id.value > 0) {
			selectProduct(document.theform.product_id.value);
		}
	};

<%if(bSuccess){
	if(r_selfbenflag == 0){
%>
      //�Ϲ��ɹ� //��ͬ��� //Ϊ//��ͬΪ�������У���Ҫ���иú�ͬ������ά����
	  if (confirm("<%=LocalUtilis.language("message.subscriptionSucc",clientLocale)%>��<%=LocalUtilis.language("class.constractBH",clientLocale)%><%=LocalUtilis.language("message.is",clientLocale)%>:<%=re_contract_bh%>��<%=LocalUtilis.language("message.contractMaintenanceTip",clientLocale)%> ��")){
	  	location = 'benifiter_list.jsp?from_flag_benifitor=1&contract_id=<%=serial_no%>'&page=1&sQuery=<%=sQuery%>';
	  }else{
	  	CancelAction();//�����Ϲ��б�
	  }

	<%}else{%>
			sl_alert("<%=LocalUtilis.language("message.subscriptionSucc",clientLocale)%> ");//�Ϲ��ɹ�
			CancelAction();//�����Ϲ��б�
	<%}%>

	getSellInfo();
<%}%>

/*����*/
function CancelAction(){
	tempArray = '<%=sQuery%>'.split('$');
	location = 'subscribe_list.jsp?page=1&q_productId='+tempArray[0]+'&q_productCode='+tempArray[1]+'&q_cust_name='+tempArray[2]+'&query_contract_bh='+tempArray[3]+'&q_pre_flag='+tempArray[4]+'&pagesize='+tempArray[5]+'&q_check_flag='+tempArray[6];
}
/*������ɸ��*/
function checkBlack(){
	var button_save = document.getElementById("btnSave");
	var button_check_black = document.getElementById("btnCheckBlack");

	//var cust_name = document.theform.cust_name.value;	
	var   cust_name=document.getElementById("kehumingcheng");
	if(cust_name.value==""){
	alert("����ѡ��ͻ����Ʋ��ܽ��к�����ɸ��");
	return false;
	}
	//alert(cust_name.value)	;
	button_save.style.visibility="visible";
	var ret = showModalDialog('<%=request.getContextPath()%>/marketing/purchase_info_check_black.jsp?cust_name=' + cust_name.value, '', 'dialogWidth:1200px;dialogHeight:800px;status:0;help:0');
}
/*���˶�λ��Ʒ*/
function setProduct(value){
	var prodid=0;

	if (event.keyCode==13&&value!=""){
       var j = value.length;

	   for(i=0;i<document.theform.product_id.options.length;i++){
			if(document.theform.product_id.options[i].text.substring(0,j)==value){
				document.theform.product_id.options[i].selected=true;
				prodid = document.theform.product_id.options[i].value;
				selectProduct(prodid);
			}
		}

		if (prodid==0){
			sl_alert('<%=LocalUtilis.language("message.inputProdIDError",clientLocale)%> ��');//����Ĳ�Ʒ��Ų����ڻ��߸ò�Ʒ�����ƽ���
			document.theform.product_id.options[0].selected=true;
		}
	}
	nextKeyPress(this);
} 

/*ͨ����Ʒ��֤����Ӳ�Ʒ��־*/
function selectProduct(product_id){
	if(product_id>0){
		utilityService.getSubProductFlag(product_id,getSubFlag);
		getSellInfo(product_id);
<%if (user_id.intValue()==2/*2����Ͷ*/ || user_id.intValue()==999) {%>
		utilityService.getProductValidPeriod(product_id, 0, gotProductValidPeriod);
<%}%>
	}else{
		sl_alert("��Ʒ������,������ѡ��!");
		return false;
	}
}

function gotProductValidPeriod(data) {
	document.theform.valid_period.value = "";
	if (data[0]) 
		document.theform.valid_period.value = data[0];
	
	// ����Ʒ����period_unit�ֶ�Ϊnull������Ϊ��
	document.getElementById("period_unit").selectedIndex = data[1]? data[1]: 2; 
}

/*��ȡ�Ӳ�Ʒ��Ϣ�����漶�����漶��������Ϣ*/
function getSubFlag(data){
	var is_product_sub_flag;
	var arrayL = data.split("$");
	product_id = DWRUtil.getValue("product_id");
	is_product_sub_flag = arrayL[0];
	document.theform.is_product_sub_flag.value = arrayL[0];
	document.theform.intrust_flag1.value = arrayL[1];
	document.theform.asfund_flag.value = arrayL[2];
	document.theform.open_flag.value = arrayL[3];
	document.theform.is_local.value = arrayL[4];
	document.theform.pre_start_date.value = arrayL[5];
	document.theform.pre_end_date.value = arrayL[6];
	if (arrayL[8].charAt(arrayL[8].length-1)=="��")
		arrayL[8] = arrayL[8].substring(0, arrayL[8].length-1);
	$("contract_sub_bh_prefix").innerText = arrayL[8];

	if(DWRUtil.getValue("intrust_flag1")!= 1){
		document.getElementById("tr_prov_flag").style.display = "";
		if(DWRUtil.getValue("asfund_flag")==3)//���漶�𰴺�ͬ������
			document.getElementById("td_pro_level").style.display
				= document.getElementById("div_prov_level").style.display = "";
		else
			document.getElementById("td_pro_level").style.display 
				= document.getElementById("div_prov_level").style.display = "none";
	}else{
		document.getElementById("tr_prov_flag").style.display = "none";
	}

	if(is_product_sub_flag==1) //���Ӳ�Ʒ
	{
		document.getElementById("sub_product_flag").style.display = "";
		utilityService.getSubProductOptionS(product_id,0,getSubProductOption);//����Ӳ�Ʒ�����
	}else{ //���Ӳ�Ʒ��
		document.getElementById("sub_product_flag").style.display = "none";
		utilityService.getSubProductProvFlag(product_id,0,0,showProvFlag);//�������ȼ������漶��
		utilityService.queryMarketTrench(product_id,0,matchCustCallBack);//��Ʒ����������Ϣ
	}

	//if(DWRUtil.getValue("is_local")==2)//�Ƽ���
	//{
		document.getElementById("td_city_serialno").style.visibility
			= document.getElementById("select_product_city").style.visibility = "";
		setProductCity(product_id);
	//}else{
	//	document.getElementById("td_city_serialno").style.display = "none";
	//	document.getElementById("select_product_city").style.display = "none";
	//}

	//���ʽ,�Ϲ��۷ѷ�ʽ
	if(DWRUtil.getValue("open_flag")==1){
		document.getElementById("bonus_name_view").style.display = "";
		//document.getElementById("bonus_value_view").style.display = "";
	}else{
		document.getElementById("bonus_name_view").style.display = "none";
		//document.getElementById("bonus_value_view").style.display = "none";
	}

}

//����Ӳ�Ʒ����
function getSubProductOption(data){
	$("select_id_2").innerHTML 
		= "<select style='width:430px;' name='sub_product_id' id='sub_product_id' onkeydown='javascript:nextKeyPress(this)'"+
			" onchange='javascript:selectSubProduct(this.value);'>"+data+"</SELECT>";
}

/*���Ӳ�Ʒ������� ����������ȼ�����������������*/
function selectSubProduct(sub_product_id){
	var product_id_v = DWRUtil.getValue("product_id");
	var link_man = DWRUtil.getValue("link_man");
	if (sub_product_id=="") sub_product_id = 0;
	//utilityService.getSubPSylb(sub_product_id,showSylbCallBack);//��ȡ�Ӳ�Ʒ���������,��ͬ��ͽ��,��ͬ��߽��

	//�������ȼ����������
	utilityService.getSubProductProvFlag(product_id_v,sub_product_id,0,showProvFlag);

	//��Ʒ����������Ϣ
	utilityService.queryMarketTrench(product_id_v,sub_product_id,matchCustCallBack);
<%if (user_id.intValue()==2/*2����Ͷ*/ || user_id.intValue()==999) {%>
	utilityService.getProductValidPeriod(product_id_v, sub_product_id, gotProductValidPeriod);
<%}%>
}

//��������
function matchCustCallBack(data){
	var obj_op = document.getElementById("market_trench");
    DWRUtil.removeAllOptions(obj_op);
    DWRUtil.addOptions(obj_op,{'':'<%=LocalUtilis.language("message.select2",clientLocale)%> '});   //��ѡ��
    DWRUtil.addOptions(obj_op,data);
}

//�������� ����
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

/*�ͻ���Ϣ*/
function getMarketingCustomer(prefix,readonly){
	var cust_id = getElement(prefix, "cust_id").value;
	var url = '<%=request.getContextPath()%>/marketing/customerInfo.jsp?prefix='+ prefix+ '&cust_id=' + cust_id+'&readonly='+readonly ;
	var v = showModalDialog(url,'','dialogWidth:720px;dialogHeight:750px;status:0;help:0;');
	if (v){
		//showTransactionCustomer2(prefix, v);
		document.theform.cust_name.value = v[0];

		document.theform.ht_cust_name.value = v[0];

		if (v[4] && trim(v[4])!="")
			document.theform.ht_cust_tel.value = trim(v[4]); // mobile
		else if (v[10] && trim(v[10])!="")
			 document.theform.ht_cust_tel.value = trim(v[10]); // o_tel
		else if (v[3] && trim(v[3])!="") 
			 document.theform.ht_cust_tel.value = trim(v[3]); // h_tel
		else 
			document.theform.ht_cust_tel.value = "";

		document.theform.ht_cust_address.value = v[5];

		getElement(prefix, "cust_id").value = v[7];
		//showCustOptions(v[7]);

		document.theform.customer_gain_acct.value = v[0];
		link_man = DWRUtil.getValue("link_man");
		document.theform.cust_type.value = v[1]=='����'? 2: 1;

		document.getElementById("rg_times").innerText = v[20];
		if (link_man !=link_man) queryTeamquota(link_man);	
		
		document.theform.bank_id.options.selectedIndex = 0;
		selectBank("");	
	}
}

/*�ͻ���Ϣ: ֤��ɨ���Զ�ʶ��ʱ����*/
function getMarketingCustomerAuto(prefix,readonly){
	var cust_id = getElement(prefix, "cust_id").value;
	var url = '<%=request.getContextPath()%>/marketing/customerInfo.jsp?prefix='+ prefix+ '&cust_id=' + cust_id+'&readonly='+readonly + '&pos_flag=<%=pos_flag%>';
	var v = showModalDialog(url,'','dialogWidth:720px;dialogHeight:720px;status:0;help:0;');
	if (v){
		//showTransactionCustomer2(prefix, v);
		document.theform.ht_cust_name.value = v[0];

		if (v[4] && trim(v[4])!="")
			document.theform.ht_cust_tel.value = trim(v[4]); // mobile
		else if (v[10] && trim(v[10])!="")
			 document.theform.ht_cust_tel.value = trim(v[10]); // o_tel
		else if (v[3] && trim(v[3])!="") 
			 document.theform.ht_cust_tel.value = trim(v[3]); // h_tel
		else 
			document.theform.ht_cust_tel.value = "";

		document.theform.ht_cust_address.value = v[5];

		getElement(prefix, "cust_id").value = v[7];
		//showCustOptions(v[7]);

		document.theform.customer_gain_acct.value = v[0];
		link_man = DWRUtil.getValue("link_man");
		document.theform.cust_type.value = v[1]=='����'? 2: 1;

		if (link_man !=link_man) queryTeamquota(link_man);

		document.theform.bank_id.options.selectedIndex = 0;		
		selectBank("");
	}
}

//�Ϲ���ͬ��ϵ��
function showCustOptions(cust_id,contact_id){
/*
	DWRUtil.removeAllOptions("cust_message");
	DWRUtil.addOptions("cust_message",{"":"��ѡ��"});
	contract.getCustOptions(cust_id,function(date){
			var json=date;
			for(i=0;i<json.length;i++){
	    		DWRUtil.addOptions("cust_message",json[i]);    
	    	}
			DWRUtil.addOptions("cust_message",{"0":"�½�"});	
		}				
	);
	DWRUtil.setValue("cust_message",contact_id);*/
}

//������ͬ��ϵ��
function newlyIncreased(value){
	var obj=value;	
	var cust_id = getElement("customer", "cust_id").value;
	var url = "<%=request.getContextPath()%>/client/clientinfo/client_contact_new.jsp?cust_id="+cust_id+"&pos_flag=<%=pos_flag%>";
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
				document.theform.inputflag.value = json.length? 2: 1;
				changeInput(document.theform.btnSelect);	
				obj.options.add(new Option("��ѡ��", ""));
				DWRUtil.addOptions(obj, json, "BANK_ACCT");		
			}
		});
}

//�����ʺ� �ı� ���� ���� ѡ��
function changeInput(obj){
	if(document.theform.inputflag.value==1)	{
		obj.innerText="ѡ��";
		document.theform.bank_acct.style.display="";
		document.theform.bank_acct2.style.display="none";
		document.theform.inputflag.value=2;
	}else{
		obj.innerText="����";
		document.theform.bank_acct.style.display="none";
		document.theform.bank_acct2.style.display="";
		document.theform.inputflag.value=1;
	}
}

//������䷽ʽ���ڲ���ת�ݶ�
function showBonus_rate(bonus_flag){
	document.getElementById("bonus_rate_div").style.display = bonus_flag=='3'? "": "none";
}

/*��֤��ͬ���*/
function check(){
	$("contractDIV").innerHTML = "";
	if(document.theform.contract_sub_bh.value==""){
		sl_alert("<%=LocalUtilis.language("class.contractID",clientLocale)%>����Ϊ��!");//��ͬ���
		return false;
	}
	var book_code=DWRUtil.getValue("book_code");
	var contract_type=new Number(1);
	var product_id=DWRUtil.getValue("product_id");
	var contract_sub_prefix = $("contract_sub_bh_prefix").innerHTML;
	var contract_sub =DWRUtil.getValue("contract_sub_bh");
	if(product_id==0){
		sl_alert("<%=LocalUtilis.language("message.chooseProdTip",clientLocale)%> !");//����ѡ���Ʒ
		return false;
	}
	contract.findIfExistSameBH(book_code,contract_type,product_id,contract_sub_prefix+contract_sub
								, function(result) { if (result!="") $("contractDIV").innerHTML = result;});
	contract.getSameBHContractInfo(contract_type,product_id,contract_sub_prefix+contract_sub,callSameBHContractInfoBack);
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

/*�����������*/
function getSellInfo(product_id){
	if(product_id>0){
		contract.getSaleInfo('<%=input_bookCode%>',product_id
			,{callback: 
				function(data){
					document.theform.org_bal.value = data[0];
					document.theform.contact_num.value = data[1];
					document.theform.current_num.value = data[2];
				}
			 });
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
	if (matched.length==0) { // �Ҳ���ƥ���
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
	} else { // matched.length>1
		document.theform.expect_ror_lower.value = "";
		document.theform.expect_ror_upper.value = document.theform.expect_ror_lower.value;
	}
}

/*�������Ƽ��*/
function checkSellInfo(){
	var channel_rate = document.theform.channel_rate.value;
	var rg_money = DWRUtil.getValue("rg_money");
	if(rg_money != "" && channel_rate != "")
		document.theform.market_trench_money.value = (Number(rg_money) * Number(channel_rate)).toFixed(2);
	else
		document.theform.market_trench_money.value = "";

	var product_id = DWRUtil.getValue("product_id");
	var sub_product_id = DWRUtil.getValue("sub_product_id");
	//alert(product_id + " " + sub_product_id);
	if (document.getElementById("sub_product_flag").style.display == "none") {
		utilityService.getSubProductProvFlag2(product_id, 0, autoSetGainLevel);//�������ȼ������漶��
	} else {
		utilityService.getSubProductProvFlag2(product_id, sub_product_id, autoSetGainLevel);
	}
	return true;
}

//�������
function showProvFlag(data){
	var obj = document.theform.prov_flag;
	var sub_flag = DWRUtil.getValue("sub_flag");

	var product_id_v = DWRUtil.getValue("product_id");
	var sub_product_id = DWRUtil.getValue("sub_product_id");
	var intrust_flag1 = DWRUtil.getValue("intrust_flag1");
	var asfund_flag = DWRUtil.getValue("asfund_flag");
	DWRUtil.removeAllOptions(obj);
	var json = eval(data);	
	for(i=0;i<json.length;i++){
		DWRUtil.addOptions(obj,json[i]);
	}
	var prov_flag = obj.value;
	if (intrust_flag1!=1 && asfund_flag!=null && asfund_flag==3){
		if (sub_product_id=="��ѡ��") sub_product_id = 0;
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
	document.getElementById("div_prov_level").innerHTML
		= "<select name='prov_level' style='width:230px' onchange='javascript:getGainRate()'>"+data+"</select>";
	var rg_money = DWRUtil.getValue("rg_money");
	if (rg_money == "") {
		document.theform.expect_ror_lower.value = "";
		document.theform.expect_ror_upper.value = document.theform.expect_ror_lower.value;
	} else {
		getGainRate();
	}
}

//���к���
function showHtyh(){
	document.getElementById("htyhmc_v").style.display 
		= document.theform.with_bank_flag.checked? "": "none";
}

/*
 *��Ӹ���
 */
function addline() {
	n++;
	newline=document.all.test.insertRow()
	newline.id="fileRow"+n;
	newline.insertCell().innerHTML="<input type='file' name=file_info"+n+" size='60'>"+"<button type='button' class='xpbutton2'   onclick='javascript:removeline(this)'><%=LocalUtilis.language("messgae.remove",clientLocale)%> </button>";//�Ƴ�
}

//ͨ����ͬ������Ա����ƷID���Ӳ�ƷID ��ѯ������Ա�����ŶӵĿ�������������������
function queryTeamquota(link_man) {
	var product_id = DWRUtil.getValue("product_id");
	var sub_product_id = DWRUtil.getValue("sub_product_id");
	if (sub_product_id=="��ѡ��") sub_product_id = 0;
	utilityService.queryTeamquota(product_id,sub_product_id,
		link_man,<%=input_operatorCode%>,{callback:function(data){
			var arrayList = data.split('&');
			document.theform.serialNo.value=arrayList[0];
			document.theform.quota_money.value=arrayList[1];
			document.theform.already_sale.value=arrayList[2];
			document.theform.quota_qualified_num.value=arrayList[3];
			document.theform.already_qualified_num.value=parseInt(arrayList[4])-1;
			document.theform.team_id.value=arrayList[5];
	}});
}

//�Ƽ���
function setProductCity(product_id){
	utilityService.getSelectProductCity(product_id,null
		,{callback:
			function(data){
				$("select_product_city").innerHTML 
					= "<select size='1' style='width:230px;' name='city_serialno' onkeydown='javascript:nextKeyPress(this)'>"+data+"</SELECT>"
						+ " <button type='button'  class='xpbutton4' accessKey=r id='btnSetcity' name='btnSetcity' onclick='javascript:newCity(document.theform.product_id.value);'>"
						+ "<%=LocalUtilis.language("class.promotionSet",clientLocale)%></button>";
			}
		});
}

/*
 *ɾ������
 */
function removeline(obj){
	var objSourceRow=obj.parentNode.parentNode;
	var objTable=obj.parentNode.parentNode.parentNode.parentNode;
	objTable.lastChild.removeChild(objSourceRow);
}

/*����*/
function SaveAction(){
	if(document.theform.onsubmit()){
		disableAllBtn(true);
		document.theform.submit();
	}
}

/*�����������Ϲ���Ϣʱ�õ��ĺ���*/
function validateForm(form){
	var userid = <%=user_id%>;
	if (! sl_checkChoice(form.product_id,"<%=LocalUtilis.language("class.productName",clientLocale)%> ")) return false; //��Ʒ����
	if (form.product_id.value==0) {
		sl_alert("<%=LocalUtilis.language("message.chooseProdName",clientLocale)%> !");//��ѡ���Ʒ����
		return false;
	}

	if (form.customer_cust_id.value==""){
		sl_alert("<%=LocalUtilis.language("message.chooseCustomer",clientLocale)%> ��");//��ѡ��ͻ�
		return false;
	}	
	if(form.ht_cust_name.value==""){
		sl_alert("�ͻ�������Ϊ��!");
		form.ht_cust_name.focus();
		return false;
	}
	if(userid==2 && form.sfpj.value!=1){
		sl_alert("��Կͻ�������������!");//��ѡ��ͻ���������
		return false;
	}

	if (! sl_checkChoice(form.contract_type, "��ͬ����")) return false;
	if(form.contract_sub_bh.value==""){
		sl_alert("<%=LocalUtilis.language("class.contractID",clientLocale)%>����Ϊ��!");//��ͬ���
		return false;
	}
	if(DWRUtil.getValue("same_contractBH")==0){
		sl_alert("��ͬ����ѱ�ʹ��!");
		return false;
	}
	//�Ϲ���ͬ��ſ���
	<%if(contract_bh_flag!=1){%>
	    var contract_bh = form.contract_bh;
		if(!sl_check(contract_bh, '<%=LocalUtilis.language("class.constractBH",clientLocale)%> ', 20,0)){return false;}
		if(contract_bh.value!= ""&&contract_bh.value!=null){
		  var s=contract_bh.value;
		  var len=contract_bh.value.length;
		  if(len<3){
		    sl_alert('<%=LocalUtilis.language("message.inputContractBHTip",clientLocale)%> ');//����ĺ�ͬ�������Ϊ3λ
		    return false;
		  } else {
		     if((s.charCodeAt(len-1)<48 || s.charCodeAt(len-1)>57) || (s.charCodeAt(len-2)<48 || s.charCodeAt(len-2)>57)|| (s.charCodeAt(len-3)<48 || s.charCodeAt(len-3)>57)){
		        sl_alert("<%=LocalUtilis.language("messgae.contractSerialError",clientLocale)%> ��");//��ͬ��������λ���������֣�����������д��
		        return false;
		     }
		  }
		}
	<%}%>
	
    if(!sl_checkChoice(form.bank_id, '<%=LocalUtilis.language("class.bankName3",clientLocale)%> ')){return false;}//��������
	if(form.bank_id.value != ""){
		if(document.theform.inputflag.value==1){
			if(!sl_checkChoice(form.bank_acct2, '<%=LocalUtilis.language("class.bankAcct3",clientLocale)%> '))return false;//�����ʺ�
		}else{
			if(!sl_check(form.bank_acct, '<%=LocalUtilis.language("class.bankAcct3",clientLocale)%> ', 80, 1)) return false;//�����ʺ�
			form.bank_acct.value = trimString(form.bank_acct.value);
		}
	}
	if(!sl_checkDecimal(form.rg_money, '<%=LocalUtilis.language("class.rg_money",clientLocale)%> ', 13, 3, 1)){return false;}//�Ϲ����
	if(!sl_checkChoice(form.jk_type, '<%=LocalUtilis.language("class.feeType",clientLocale)%> ')){return false;}//�ɿʽ
	
	if(!sl_checkDate(form.qs_date_picker,"<%=LocalUtilis.language("class.qsDate",clientLocale)%> ")){return false;}//ǩ������
	if(!sl_checkDate(form.jk_date_picker,"<%=LocalUtilis.language("class.dzDate",clientLocale)%> ")){return false;}//�ɿ�����
	if(!sl_check(form.summary2, '<%=LocalUtilis.language("class.contractSummary",clientLocale)%> ', 100, 0)){return false;}//��ͬ��ע

	syncDatePicker(form.qs_date_picker, form.qs_date);
	syncDatePicker(form.jk_date_picker, form.jk_date);
	//��Ҫ��ǩ��������ɿ����ڵ��Ⱥ�
	//if(form.jk_date.value < form.qs_date.value)	{
        //�ɿ�����//����С��//ǩ������
	//	sl_alert("<%=LocalUtilis.language("class.dzDate'/><fmt:message key='message.cannotLess'/><fmt:message key='class.qsDate",clientLocale)%> ");
	//	return false;
	//}
	
	if (form.self_ben_flag.checked)
		form.selfbenflag.value='1';
	else
		form.selfbenflag.value='0';

	if(userid==8 && DWRUtil.getValue("intrust_flag1")==2){
	    if (! sl_checkChoice(form.market_trench, '�������� ')) return false; //��������
	    if (! sl_checkChoice(form.channel_cooperation, '����������ʽ ')) return false; //����������ʽ
	}
	//if(form.cust_type.value==1&&!checkSellInfo())return false;//�����������

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

	if(form.with_bank_flag.checked){
		form.with_bank_flag.value = 1;
	}
	if(form.with_security_flag.checked){
		form.with_security_flag.value = 1;
	}
	if(form.with_private_flag.checked){
		form.with_private_flag.value = 1;
	}
	if(DWRUtil.getValue("open_flag")==1){
		var bonus_flag = document.getElementById("bonus_flag").value;
		if(bonus_flag=='3'){
			if (! sl_checkDecimal(form.bonus_rate, "ת�ݶ����", 3, 2, 0)) return false;
			if (parseFloat(form.bonus_rate.value) <= 0 || parseFloat(form.bonus_rate.value) >= 100) {
				sl_alert("ת�ݶ����ȡֵ����Ϊ0��100֮��(������0��100)");
				return false;
			}
			//if(!isBetween(form.bonus_rate,0,100,2,"ת�ݶ����ȡֵ����Ϊ0��100֮��(������0��100)")) return false;
		}
	}

	if(DWRUtil.getValue("asfund_flag")==3){
		if (! sl_checkChoice(form.prov_level, '<%=LocalUtilis.language("class.incomeLevel",clientLocale)%>')) return false;//���漶��
	}else{
		form.prov_level.value = '120401 ';
	}

	if(form.quota_money.value != 0){
		var sum = parseInt(form.rg_money.value) + parseInt(form.already_sale.value);
		if(sum > parseInt(form.quota_money.value)){
			sl_alert('<%=LocalUtilis.language("message.teamQuota",clientLocale)%>');//�û������Ŷ��ڸò�Ʒ���ۼ������۶�����ֵ
			return false;
		}
	}
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
	if (userid==22 && !sl_checkChoice(form.city_serialno, '<%=LocalUtilis.language("class.citySerialNO",clientLocale)%>')) return false; //��ͬ�ƽ��
	
	if (! sl_checkDecimal(form.expect_ror_lower, 'Ԥ������������', 5, 4, 0)) return false; //Ԥ������������
	if (! sl_checkDecimal(form.expect_ror_upper, 'Ԥ������������', 5, 4, 0)) return false; //Ԥ������������

	return sl_check_update();
}

/*��ʾ�˺�λ��*/
function showAcctNum(value){
	if (trim(value) == "")
		bank_acct_num.innerText = "";
	else
		bank_acct_num.innerText = "(" + showLength(value) + " λ )";
}

/*ͨ��ʡ�й�����ص���������*/
function setGovRegional(value,flag){
	if(value != "" && value.length > 0){
		DWREngine.setAsync(false); // sync
		utilityService.getGovRegional(9999, value, flag,backGovRegional);
		DWREngine.setAsync(true); // async
	}
}

/*ͨ��ʡ�й�����ص��������� �ص�����*/
function backGovRegional(data){
	document.getElementById("gov_regional_span").innerHTML
		 = "<select size='1' name='bank_city' style='width:150px' onkeydown='javascript:nextKeyPress(this)'>"+data+"</select>";
}

function bankAcctView(bank_acct){
	var acct_view  = '';
	bank_acct = trimString(bank_acct);//ȥ�����пո�
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

//�Ϲ�ʱ����ά����Ʒ���ƽ�أ���������ѡȡ���ʵ��Ϲ���
function newCity(product_id){	
	var ret = showModalDialog('/marketing/sell/product_city_update.jsp?product_id=' + product_id, '', 'dialogWidth:500px;dialogHeight:150px;status:0;help:0');
	if( ret){
		seloption = new Option(ret[1],ret[0]);
		document.all.city_serialno.options[document.all.city_serialno.length] = seloption;
		document.all.city_serialno.options[document.all.city_serialno.length-1].selected = true;
	}
}
//����
function newInfo(cust_id){
	if(cust_id <= 0)
		sl_alert('��ѡ��ͻ�֮���ٽ�������');
	else{
		if (showModalDialog('/marketing/cust_grade_new.jsp?cust_id='+cust_id, '', 'dialogWidth:700px;dialogHeight:600px;status:0;help:0')){
			sl_alert("<%=LocalUtilis.language("message.rating",clientLocale)%><%=LocalUtilis.language("message.success",clientLocale)%> ��");
			document.theform.sfpj.value = 1;
		}
	}
}
//�½���Ʒ����
function newChannel(){
	if(DWRUtil.getValue("product_id")==0){
		alert("����ѡ���Ʒ");
		return false;
	}else if(DWRUtil.getValue("is_product_sub_flag")==1&&DWRUtil.getValue("sub_product_id")==0){
		alert("����ѡ���Ӳ�Ʒ");
		return false;
	}else{
		if (showModalDialog('/marketing/base/product_market_trench_add.jsp?productId='+ DWRUtil.getValue("product_id") 
				+'&sub_flag='+DWRUtil.getValue("is_product_sub_flag") +'&productName='
				+$("product_id").options[$("product_id").selectedIndex].text+'&sub_product_id='
				+ DWRUtil.getValue("sub_product_id"), '', 'dialogWidth:400px;dialogHeight:400px;status:0;help:0')){
			sl_update_ok();
			//��Ʒ����������Ϣ
			utilityService.queryMarketTrench(DWRUtil.getValue("product_id"),DWRUtil.getValue("sub_product_id"),matchCustCallBack);
		}
	}
}

function getGainRate() {
	var product_id = document.theform.product_id.value;
	var sub_product_id = document.theform.sub_product_id.value;
	//alert(product_id + " " + sub_product_id);
	if (document.getElementById("sub_product_flag").style.display == "none") 
		utilityService.getSubProductProvFlag2(product_id, 0, autoSetGainLevel2);//�������ȼ������漶��
	else 
		utilityService.getSubProductProvFlag2(product_id, sub_product_id, autoSetGainLevel2);
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

//var client_file = "";

function readCard(btn) {
	/*var to_pay_money = parseFloat(document.theform.rg_money.value);
	//alert(to_pay_money+" "+isNaN(to_pay_money)); // NaN
	if (!isNaN(to_pay_money) && to_pay_money!=0) {
		btn.disabled = true;
*/
		/*alert($.ajax);
		$.ajax({
		  type: 'POST',
		  url: "pos_read_card.jsp",
		  data: {
				to_pay_money: to_pay_money
		  },
		  success: function() {
			
		  }
		});	*/

/*
		utilityService.readPosCard(<%=input_operatorCode%>, to_pay_money
			, function(bank_acct) {
				//alert(bank_acct);
				if (bank_acct!="" && bank_acct!="E*") {
					if (document.theform.bank_acct.style.display=="none") 
						changeInput(document.theform.btnSelect);
						
					document.theform.bank_acct.value = bank_acct;
				}
			});
		sl_alert("���������Ѿ�������ˢ����");

	} else {
		sl_alert("������д��Ч���Ϲ���");
		document.theform.rg_money.focus();
		document.theform.rg_money.select();
	}*/

	var rg_money = parseFloat(document.theform.rg_money.value);
	var pos_product_id=document.theform.product_id.value;
	var pos_cust_id=document.theform.customer_cust_id.value;
	if (pos_product_id=="" || pos_product_id=="0"){
		alert("����ѡ���Ʒ");
		document.theform.product_id.focus();
		return;
	}
	if (pos_cust_id=="" || pos_cust_id=="0"){
		alert("����ѡ��ͻ�");
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
		sl_alert("������д��Ч���Ϲ���");
		document.theform.rg_money.focus();
		document.theform.rg_money.select();
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
</script>
</HEAD>
<BODY class="BODY body-nox">
<!-- ��Ͷ2014-4-30 -->
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="post" action="subscribe_add2.jsp" onsubmit="javascript:return validateForm(this);" ENCTYPE="multipart/form-data">
<input type="hidden" name="sQuery" value="<%=sQuery%>"/>
<input type="hidden" name="customer_cust_id"/>
<input type="hidden" name="cust_type"/>
<input type="hidden" name="inputflag" value="<%=inputflag%>"/>
<input type="hidden" name="open_flag" value="0">
<input type="hidden" name="asfund_flag"/>
<input type="hidden" name="intrust_flag1"/>
<input type="hidden" name="selfbenflag"/>
<input type="hidden" name="pre_start_date"/>
<input type="hidden" name="pre_end_date"/>
<input type="hidden" name="is_local" value="1">
<input type="hidden" name="is_product_sub_flag"/>
<input type="hidden" name="same_contractBH" value="0"/>

<input type="hidden" name="org_bal"/><!-- 300W ��� -->
<input type="hidden" name="contact_num"/><!-- ��ͬ���� -->
<input type="hidden" name="current_num"/><!-- ���к�ͬ -->
<input type="hidden" id="channel_type" name="channel_type"/>
<input type="hidden" id="channel_id" name="channel_id"/>
<input type="hidden" id="channel_rate" name="channel_rate"/>

<!-- �����Ŷ�����Ƿ񳬹�����֤ -->
<input type="hidden" name="quota_money"/>
<input type="hidden" name="already_sale"/>
<input type="hidden" name="quota_qualified_num"/>
<input type="hidden" name="already_qualified_num"/>
<input type="hidden" name="team_id"/>
<input type="hidden" name="serialNo"/>

<input type="hidden" name="bank_acct_view"/>
<input type="hidden" name="bank_acct_flag"/>
<input type="hidden" name="sfpj" value="0"/>
<input type="hidden" name="property_souce" value=""/>
<input type="hidden" name="other_explain" value=""/>
<div align="left" class="page-title">
	<font color="#215dc6"><b><%=menu_info%></b></font>
</div>
<br/>
<!--��Ʒѡ��-->
<div>
<table border="0" width="100%" cellspacing="4" cellpadding="4" 
	class="product-list">
	<tr>
		<td ><b><%=LocalUtilis.language("menu.prodInfo",clientLocale)%> </b></td><!--��Ʒ��Ϣ-->
		<td></td>
	</tr>
	<tr>
		<td align="right" width="120px"><b><%=LocalUtilis.language("class.productID",clientLocale)%></b>:</td><!--��Ʒ���-->
		<td align="LEFT">
			<input type="text" maxlength="16" name="productcode" 
				value="<%//=product_code%>" onkeydown="javascript:setProduct(this.value);" size="25"/>
		</td>
	</tr>
	<tr>
		<td align="right" width="120px"><b><font color="red"><b>*</b></font><%=LocalUtilis.language("class.productNumber",clientLocale)%></b>:</td><!--��Ʒѡ��-->
		<td align=left>
			<select id="product_id" name="product_id" style="width: 430px;" 
				onkeydown="javascript:nextKeyPress(this)" onchange="javascript:selectProduct(this.value);">
                <option><%=LocalUtilis.language("message.pleaseSelect",clientLocale)%></option><!--��ѡ��-->
				<%=Argument.getCRMProductListOptions(new Integer(2),product_id,"110202",new Integer(0),input_operatorCode)%>
			</select>
		</td>
	</tr>
	<tr id="sub_product_flag" style="display:none">
		<td align="right" width="120px"><b><font color="red"><b>*</b></font><%=LocalUtilis.language("class.subProductID",clientLocale)%></b>:</td><!--�Ӳ�Ʒѡ��-->
		<td align=left id="select_id_2">
		    <select name="sub_product_id" id="sub_product_id">
		        <option value="0">��ѡ��</option>
		    </select>
		</td>
	</tr>
</table>
<br>
<table border="0" width="100%" cellspacing="4" cellpadding="4" 
	class="product-list">
	<tr>
		<td><b><%=LocalUtilis.language("menu.customerInformation",clientLocale)%> </b></td><!--�ͻ���Ϣ-->
		<td></td>
	</tr>
	<tr>
		<td align="right" width="120px"><font color="red"><b>*</b></font>ί����:</td>
		<td align=left>
			<input type="text" size="25" name="cust_name" readonly class="edline" id="kehumingcheng"
				onkeydown="javascript:nextKeyPress(this)"/>
			&nbsp;&nbsp;<button type="button"  class="xpbutton4" accessKey=E name="btnEdit" 
							title="<%=LocalUtilis.language("menu.customerInformation",clientLocale)%> " 
							onclick="javascript:getMarketingCustomer('customer','0');return false;">ѡ��/�鿴</button>
			&nbsp;&nbsp;&nbsp;&nbsp;���Ϲ������ۼ�: <span id="rg_times"></span>
		</td>
	</tr>
	<tr>
		<td align="right" width="120px">��ͬ��ϵ��:</td>
		<td>
			<input type="text" name="ht_cust_name" size="25" onkeydown="javascript:nextKeyPress(this)"/>
			&nbsp;&nbsp;&nbsp;&nbsp;�绰:<input type="text" name="ht_cust_tel" size="20" onkeydown="javascript:nextKeyPress(this)"/>		
			&nbsp;&nbsp;&nbsp;&nbsp;��ַ:<input type="text" name="ht_cust_address" size="50" onkeydown="javascript:nextKeyPress(this)"/>			
		</td>
	</tr>
<%if(user_id.intValue() ==2){//2����Ͷ %>
	<tr>
		<td align="right" width="120px"><font color="red"><b>*</b></font><%=LocalUtilis.language("menu.customerRating",clientLocale)%>:</td>
		<td align=left>
			<button type="button"  class="xpbutton3"  title='<%=LocalUtilis.language("message.newRating",clientLocale)%>' 
				onclick="javascript:newInfo(document.theform.customer_cust_id.value);"><%=LocalUtilis.language("message.rating",clientLocale)%></button>
		</td>		
	</tr>
<%} %>
</table>
<br>
<table  border="0" width="100%" cellspacing="5" cellpadding="5" 
	class="product-list">
	<!--�Ϲ���ͬ��Ϣ-->
	<tr>
		<td colspan="4"><b><%=LocalUtilis.language("message.contractInfo",clientLocale)%> </b></td>  <!--��ͬ��Ϣ-->
	<tr>
	<tr>
		<td align=right><font color="red"><b>*</b></font>��ͬ���� : </td>
		<td colspan="3">
			<select size="1" name="contract_type" onkeydown="javascript:nextKeyPress(this)" style="width:150px;">
				<option value="0">��ѡ��</option>
				<option value="1">ǰ̨������Ա��ͬ</option>
				<option value="2">��Ʒ���ź�ͬ</option>
				<option value="3">������ͬ</option>
			</select>
		</td>
	</tr>
	<tr>
		<!--��ͬ���-->
        <td align="right" width="120px" style="display:<%=contract_bh_flag==1?"none":""%>">
			<b><%=LocalUtilis.language("class.constractBH",clientLocale)%></b>:&nbsp;&nbsp;
	    </td>
		<td style="display:<%=contract_bh_flag==1?"none":""%>">
			<input name="contract_bh" size="20" maxlength=20 onkeydown="javascript:nextKeyPress(this)"/></td>
		<td align="right"><font color="red"><b>*</b></font><%=LocalUtilis.language("class.contractID",clientLocale)%>:</td><!--��ͬ���-->
		<td>
			<div id="contract_sub_bh_view" style="display:none"></div>
<%
String contractSubBhPrefix = Argument.getContractSubBhPrefix(product_id);
if (contractSubBhPrefix.endsWith("��"))
	contractSubBhPrefix = contractSubBhPrefix.substring(0, contractSubBhPrefix.length()-1);
 %>
			<span id="contract_sub_bh_prefix"><%=contractSubBhPrefix%></span>
			<input id="contract_sub_bh" name="contract_sub_bh" size="35" maxlength=40 
				onkeydown="javascript:nextKeyPress(this)" onblur="javascript:check();"/>&nbsp;&nbsp;
			<INPUT type="button" class="xpbutton3" onclick="javascript:check();" 
				value='<%=LocalUtilis.language("messgae.CheckBH",clientLocale)%>'/><!--��֤���-->
			<div id="contractDIV"></div>
		</td>
	</tr>

<%if (user_id.intValue()==2 /*2����Ͷ*/|| user_id.intValue()==999) { %>
	<tr>
		<td align="right">��ͬ����:</td>
		<td colspan="3">
			<input type="text" name="valid_period" size="5"/>
			<select name="period_unit" id="period_unit"><%=Argument.getPeriodValidUnitOptions(period_unit)%></select>
		</td>
	</tr>
<%}%>

	<tr>
		<td align="right"><font color="red"><b>*</b></font><%=LocalUtilis.language("class.bandAcct4",clientLocale)%>:</td><!--�����������ʺ�-->
		<td>
			<input type="text" name="bank_acct" size="28" style="display:<%=inputflag==1?"none":""%>" 
				onkeydown="javascript:nextKeyPress(this)" onkeyup="javascript:showAcctNum(this.value)" 
				onblur="javascript:bankAcctView(this.value);"/>
			<select style="WIDTH:170px;display:<%=inputflag==2?"none":""%>" size="1" name="bank_acct2" 
				onkeydown="javascript:nextKeyPress(this)" onchange="javascript:autoSetAcctInfo(this);">
			</select>&nbsp;&nbsp;
			<button type="button"  class="xpbutton2" accessKey=x name="btnSelect" title="����" onclick="javascript:changeInput(this);">����(<u>X</u>)</button>
			<span id="bank_acct_num" class="span"></span>
		</td>
		<td align="right">
			<font color="red"><b>*</b></font><%=LocalUtilis.language("class.bankName5",clientLocale)%>:<!-- �������������� -->
		</td>
		<td>
			<select size="1" name="bank_id" onchange="javascript:selectBank(this.value);" style="WIDTH:150px">
				<%=Argument.getBankOptions("")%>
			</select>
			<input type="text" name="bank_sub_name" size="20" onkeydown="javascript:nextKeyPress(this)"/>
		</td>			
	</tr>
	<tr>
		<td align="right">���������ڵ�:</td>
		<td>
			<select size="1" name="bank_province" style="width:130px" onkeydown="javascript:nextKeyPress(this)" 
				onchange="javascript:setGovRegional(this.value,'');">
				<%=Argument.getCustodianNameLis(new Integer(9999), "", new Integer(0),"")%>
			</select>
			<span id="gov_regional_span">
				<select size='1' name='bank_city' style='width:150px' onkeydown='javascript:nextKeyPress(this)'>
					<option>��ѡ��</option>
				</select>
			</span><!-- ʡ������������˳������������ -->
		</td>
		<td align="right">�Ƽ���:</td>
		<td>
			<select name="recommend_man" style="width:230px" onkeydown="javascript:nextKeyPress(this)">
				<%=Argument.getIntrustOptions(new Integer(0),new Integer(1)) %>
			</select>
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.bankAccountType",clientLocale)%>:</td><!--�����˺�����-->
		<td >
			<select size="1" name="bank_acct_type" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 280px">
				<%=Argument.getBankAcctType("")%>
			</select>
		</td>
		<td align="right"><%=LocalUtilis.language("class.customerGainAcct",clientLocale)%>:</td><!--�����ʺŻ���-->
		<td>
			<input name="customer_gain_acct" <%=user_id.intValue()==8?"class='edline' readonly":""%> 
				style="WIDTH:233px" onkeydown="javascript:nextKeyPress(this)"/>
		</td>
	</tr>
	<tr>
		<td align="right">
			<font color="red"><b>*</b></font><%=LocalUtilis.language("class.feeType",clientLocale)%>: <!--�ɿʽ-->
		</td>
		<td>
			<select size="1" name="jk_type" onkeydown="javascript:nextKeyPress(this)" style="WIDTH:280px">
				<%=Argument.getJkTypeOptions("111401")%>
			</select>
		</td>
		<td align="right"><font color="red"><b>*</b></font><%=LocalUtilis.language("class.rg_money",clientLocale)%>:</td><!--�Ϲ����-->
		<td>
			<input name="rg_money" size="42" onkeydown="javascript:nextKeyPress(this)"
				onkeyup="javascript:showCnMoney(this.value,rg_money_cn)" onblur="javascript:checkSellInfo();"/>
		<%if (user_id.intValue()==24/*����24*/) { %>
			&nbsp;&nbsp;<button type="button"  class="xpbutton2" onclick="javascript:readCard(this);">ˢ��</button>
		<%} %>
		</td>
	</tr>
	<tr>
		<td colspan="3"></td>
		<td><span id="rg_money_cn" class="span"></span></td>
	</tr>
	<tr id="bonus_name_view"  style='display:none'>
		<td align="right"><%//=fee_type_name%><%=LocalUtilis.language("class.feeTypeName",clientLocale)%>:</td><!--�۽ɷ�ʽ-->
		<td >
			<select size="1" name="fee_jk_type" onkeydown="javascript:nextKeyPress(this)" style="WIDTH:280px">
				<%=Argument.getFeeJkTypeOptions(0)%>
			</select>
		</td>
		<td align="right"><%=LocalUtilis.language("class.bonusFlag",clientLocale)%>:</td><!--������䷽ʽ-->
		<td>
			<select size="1" name="bonus_flag" onkeydown="javascript:nextKeyPress(this)" style="WIDTH:230px" 
				onchange="javascript:showBonus_rate(this.value);">
				<%=Argument.getBonus_flag(null)%>
			</select>
			<span id="bonus_rate_div" style='display:none'>ת�ݶ����:
				<input type="text" name="bonus_rate" size="10" onkeydown="javascript:nextKeyPress(this)"/>%
			</span>
		</td>
	</tr>
	<tr>
		<td align="right"><font color="red"><b>*</b></font><%=LocalUtilis.language("class.qsDate",clientLocale)%>:</td><!--ǩ������-->
		<td>
			<INPUT TYPE="text" NAME="qs_date_picker" class=selecttext size="47"
				value="<%=Format.formatDateLine(Utility.getCurrentDate())%>"/>
			<INPUT TYPE="button" value="��" class=selectbtn tabindex="13"
				onclick="javascript:CalendarWebControl.show(theform.qs_date_picker,theform.qs_date_picker.value,this);"/>
			<INPUT TYPE="hidden" NAME="qs_date"/>
		</td>
		<td align="right"><font color="red"><b>*</b></font><%=LocalUtilis.language("class.dzDate",clientLocale)%>:</td><!--�ɿ�����-->
		<td>
			<INPUT TYPE="text" NAME="jk_date_picker" class=selecttext value="<%=Format.formatDateLine(Utility.getCurrentDate())%>" size="38"/>
			<INPUT TYPE="button" value="��" class=selectbtn tabindex="14"
				onclick="javascript:CalendarWebControl.show(theform.jk_date_picker,theform.jk_date_picker.value,this);"/>
			<INPUT TYPE="hidden" NAME="jk_date"/>
		</td>
	</tr>
	<tr>
		<td align="right"><font color="red"></font>��������:</td>
		<td>
			<select id="market_trench" name="market_trench" style="width:280px" onchange="javascript:setMartetTrench(this);"></select>&nbsp;&nbsp;
			<button type="button"  class="xpbutton2" id="newChannel1" name="newChannel1" onclick="javascript:newChannel();">�½�</button>
		</td>
		<td align="right">��������:</td>
		<td>
			<input name="market_trench_money" size="42" onkeydown="javascript:nextKeyPress(this)" 
				onkeyup="javascript:showCnMoney(this.value,market_trench_money_cn)"/>
			<span id="market_trench_money_cn" class="span"></span>
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.partnAttachmentInfo",clientLocale)%>:</td><!--����������Ϣ-->
		<td>
			<input name="channel_memo" size="51" onkeydown="javascript:nextKeyPress(this)"/>
		</td>
		<td align="right"><font color="red"></font><%=LocalUtilis.language("class.channelCooperation",clientLocale)%>:</td><!-- ����������ʽ -->
		<td>
			<SELECT name="channel_cooperation" onkeydown="javascript:nextKeyPress(this)" style="WIDTH:234px">
				<%=Argument.getChannelCooperationOptions(null)%>
			</SELECT>
		</td>
	</tr>
	<tr id="tr_prov_flag">
		<td align="right"><font color="red"><b>*</b></font><%=LocalUtilis.language("class.provLevel",clientLocale)%>:</td><!-- �������ȼ� -->
		<td>
			<select name="prov_flag" style="WIDTH:280px" 
				onchange="javascript:getprovlevel()" onkeydown="javascript:nextKeyPress(this)">
				<option value="0">��ѡ��</option>
			</select>
		</td>
		<td align="right" id="td_pro_level" style="display:">
			<font color="red"><b>*</b></font><%=LocalUtilis.language("class.incomeLevel",clientLocale)%>:<!-- ���漶�� -->
		</td>
		<td>
			<div id="div_prov_level">
				<select name="prov_level" style="display:;width:234px" onchange="javascript:getGainRate()">
					<option value="0">��ѡ��</option>
				</select>
			</div>
		</td> 
	</tr>
	<tr id="money_origin_id">
				<td align="right"><font color='red'>*</font>�ʽ�/�ʲ���Դ:</td>
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
		<td align="right">��ͬ������Ա:</td>
		<td>
			<select size="1" name="link_man" style="WIDTH:280px" 
				onkeydown="javascript:nextKeyPress(this)" onchange="javascript:queryTeamquota(this.value);">
				<%=Argument.getManager_Value(new Integer(0))%>//=Argument.getRoledOperatorOptions(input_bookCode, 2,input_operatorCode)%>
			</select>
		</td>
		<td align="right">Ԥ��������:</td>
		<td>
			<input name="expect_ror_lower" size="12" 
				onkeydown="javascript:nextKeyPress(this)"/><font color="red">%</font> �� 
			<input name="expect_ror_upper" size="12" 
				onkeydown="javascript:nextKeyPress(this)"/><font color="red">%</font>
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.selfBenFlag",clientLocale)%>:</td><!--�����־-->
		<td colspan="3">
			<input name="self_ben_flag" class="flatcheckbox" type="checkbox" checked onkeydown="javascript:nextKeyPress(this)"/>
		</td>
	</tr>
	<tr>
		<td align="right" style="visibility:<%=/*true||*/SUB_COOPER==2?"hidden":""%>"><%=LocalUtilis.language("message.cooperationWay",clientLocale)%>:</td><!--������ʽ-->
		<td style="visibility:<%=/*true||*/SUB_COOPER==2?"hidden":""%>">
			<input onkeydown="javascript:nextKeyPress(this)" type="checkbox" class="flatcheckbox" 
				value="0" name="with_bank_flag" onclick="javascript:showHtyh();"/>
				<%=LocalUtilis.language("intrsut.home.silvertrustcooper",clientLocale)%>&nbsp;&nbsp;&nbsp;&nbsp;<!--���ź���-->
			<input onkeydown="javascript:nextKeyPress(this)" type="checkbox"
				class="flatcheckbox" value="0" name="with_security_flag"
				/><%=LocalUtilis.language("message.cooperation2",clientLocale)%> &nbsp;&nbsp;<!--֤�ź���-->
			<input onkeydown="javascript:nextKeyPress(this)" type="checkbox"
				class="flatcheckbox" value="0" name="with_private_flag"
				/><%=LocalUtilis.language("class.cooperation4",clientLocale)%> &nbsp;&nbsp;<!--˽ļ�������-->
		</td>
		<td align="right" id="td_city_serialno" style="visibility:hidden">
		<%if(user_id.intValue()==22/*��Ͷ22*/){%>
			<font color="red"><b>*</b></font>
		<%}%><%=LocalUtilis.language("class.citySerialNO",clientLocale)%>:</td><!--��ͬ�ƽ��-->
		<td id="select_product_city" style="visibility:hidden"></td>
	</tr>
	<tr id="htyhmc_v" style='display:none'>
		<td align="right"><%=LocalUtilis.language("class.withBankId",clientLocale)%>:</td>
		<td colspan="3">
			<select name="ht_bank_id" style="WIDTH:220px"><%=Argument.getBankOptions("")%></select>
			<input type="text" name="ht_bank_sub_name" size="35" onkeydown="javascript:nextKeyPress(this)"/>
		</td>
	</tr>
	<tr>
		<td width="120px" align="right">�ÿ����:</td><!--�ÿ����-->
		<td>   
			<INPUT type="radio" class="flatcheckbox" name="is_ykgl" value="1" 
				<%=is_ykgl.equals("1")?"checked":""%>/>��
			<INPUT type="radio" class="flatcheckbox" name="is_ykgl" value="0" 
				<%=!is_ykgl.equals("1")?"checked":""%>/>��
		</td>
		<td width="120px" align="right">���ֳ�����:</td>
		<td>   
			<input type="checkbox" class="flatcheckbox" name="spot_deal" value="1"/>
		</td>
	</tr>	
	<tr>
		<td align="right" vAlign="top" width="120px">��ͬԤ��������:</td><!--���к�ͬԤ��������-->
		<td colspan=1>
			<textarea name="xthtyjsyl" rows="3" cols="83"
				onkeydown="javascript:nextKeyPress(this)"></textarea>
		</td>
		<td colspan="2" align="center"><button type="button"  class="xpbutton6"  onclick="show_property_souce();">���вƲ���Դ</button></td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.description",clientLocale)%>:</td><!--��ע-->
		<td colspan="3"><textarea rows="3" name="summary2" onkeydown="javascript:nextKeyPress(this)" cols="83"></textarea></td>
	</tr>
	<tr id="reader2" style="display:">
      	<td class="paramTitle"align="right"><%=LocalUtilis.language("menu.filesAdd",clientLocale)%>:</td><!-- ��Ӹ��� -->
        <td colspan="3">
        	<table id="test" width="100%">
	    		<tr>
	    			<td>
            			<input type="file" name="file_info" size="60"/>&nbsp;
	            		<img title="<%=LocalUtilis.language("message.tSelectAdditionalFiles",clientLocale)%> " 
							src="<%=request.getContextPath()%>/images/minihelp.gif"/><!--ѡ�񸽼��ļ�-->
	            	</td>
	            </tr>
	        </table>
	        <button type="button" class="xpbutton6"   onclick="addline()"><%=LocalUtilis.language("class.moreMccessories",clientLocale)%> <!--�����˴���Ӹ��฽��--></button>
        </td>
    </tr>
</table>
</div>

<div align="right">
	<br>
	<% if(user_id.intValue()==2){%><!--����ʵ�ʵ��Ǳߵ�ֵ��2 -->
	<button  class="xpbutton3" id="btnCheckBlack" name="btnCheckBlack" onclick="javascript:checkBlack();">������ɸ��</button>
	<!--����-->
    <button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" 
		onclick="javascript:SaveAction();" style="visibility: hidden"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;
	<%}else{%>
	<button class="xpbutton3" accessKey=s id="btnSave" name="btnSave" 
		onclick="javascript:SaveAction();" ><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;
	<%}%>
	<!--����-->
    <button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" 
		onclick="javascript:history.back();"><%=LocalUtilis.language("message.back",clientLocale)%> (<u>C</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</div>
</form>
<p>
<%
%>
<%@ include file="/includes/foot.inc"%>
<script language="javascript" >
<%if (!"".equals(session_cust_name) && cust_map==null){%>
	//ɨ��֤�����������ݿ�����֤�󲻴��ڣ��½��ͻ�
	getMarketingCustomerAuto('customer','0');
<%}else if (!"".equals(session_cust_name) && cust_map!=null){%>
	//��form��ֵ
	document.theform.customer_cust_name.value='<%=Utility.trimNull(session.getAttribute("name"))%>';
	document.theform.customer_service_man.value='<%=Utility.trimNull(cust_map.get("SERVICE_MAN_NAME"))%>';
	document.theform.customer_cust_type_name.value='<%=Utility.trimNull(cust_map.get("CUST_TYPE_NAME"))%>';
	document.theform.customer_card_id.value='<%=Utility.trimNull(session.getAttribute("card_id"))%>';
	document.theform.customer_o_tel.value='<%=Utility.trimNull(cust_map.get("O_TEL"))%>';
	document.theform.customer_mobile.value='<%=Utility.trimNull(cust_map.get("MOBILE"))%>';
	document.theform.customer_h_tel.value='<%=Utility.trimNull(cust_map.get("H_TEL"))%>';
	document.theform.customer_email.value='<%=Utility.trimNull(cust_map.get("E_MAIL"))%>';
	document.theform.customer_post_address.value='<%=Utility.trimNull(cust_map.get("POST_ADDRESS"))%>';
	document.theform.customer_post_code.value='<%=Utility.trimNull(cust_map.get("POST_CODE"))%>';
	document.theform.customer_cust_id.value='<%=Utility.trimNull(cust_map.get("CUST_ID"))%>';
	cust_id_v = getElement("customer", "cust_id").value;
	showCustOptions(cust_id_v);
<%}%>
</script>
</BODY>
</HTML>