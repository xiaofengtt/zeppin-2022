<%@ page contentType="text/html; charset=GBK" import="enfo.crm.web.*,enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*,enfo.crm.marketing.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
Customer_INSTLocal  customer_inst = EJBFactory.getCustomer_INST();//ͬ���ÿͻ�����
SaleParameterLocal sale_parameter = EJBFactory.getSaleParameter();
CustomerLocal customer = EJBFactory.getCustomer();
ContractLocal contract = EJBFactory.getContract();
SaleParameterVO salevo = new SaleParameterVO();
CustomerVO c_vo  = new CustomerVO();

//���ҳ�洫�ݱ���
String sQuery = request.getParameter("sQuery");
Integer serial_no = null;//�Ϲ���ϢID �༭��
String re_contract_bh = "";
Integer product_id = Utility.parseInt(request.getParameter("product_id"),new Integer(0));//��ƷID
Integer sub_product_id = Utility.parseInt(request.getParameter("sub_product_id"),new Integer(0));//�Ӳ�ƷID


int contract_bh_flag = Argument.getSyscontrolValue_intrust("C30902");//�Ϲ���ͬ�ĺ�ͬ����Զ���ȡ��־ 1: �Զ���ȡ 2: ��������
int inputflag = Utility.parseInt(request.getParameter("inputflag"), 2);//�����ʺ����� ���� ����ѡ�� ��־
int SUB_COOPER = Argument.getSyscontrolValue_intrust("SUB_COOPER");
int r_selfbenflag = 1;
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
	String r_bank_acct = "";

	inputflag = Utility.parseInt(file.getParameter("inputflag"), 2);//�����ʺ����� ���� ����ѡ�� ��־

	if(inputflag==1){
		r_bank_acct = file.getParameter("bank_acct2");
	}
	else{
		r_bank_acct = file.getParameter("bank_acct");
	}

	Integer r_service_man = Utility.parseInt(file.getParameter("service_man"), input_operatorCode);
	String r_summary = Utility.trimNull(file.getParameter("summary2"));
	String r_contract_bh = file.getParameter("contract_bh");
	Integer r_valid_period = Utility.parseInt(file.getParameter("valid_period"), new Integer(0));
	Integer r_qs_date = Utility.parseInt(file.getParameter("qs_date"), new Integer(Utility.getCurrentDate()));
	Integer r_jk_date = Utility.parseInt(file.getParameter("jk_date"), new Integer(Utility.getCurrentDate()));//�Ĳ��������ϣ�������㴫�뵱ǰʱ��
	Integer r_check_man = Utility.parseInt(file.getParameter("check_man"), new Integer(0));
	String r_bank_acct_type = Utility.trimNull(file.getParameter("bank_acct_type"));
	Integer r_city_serialno = Utility.parseInt(file.getParameter("city_serialno"), new Integer(0));
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

	if(r_already_sale.equals(new BigDecimal(0)))
	{
		r_already_sale = r_rg_money;
	}
	else{
		r_already_sale = r_already_sale.add(r_rg_money);
	}

	//ͬ���ͻ���Ϣ
	if(r_cust_id.intValue()>0){
		List rsList_cust = null;
		Map map_cust = null;
		//�ͻ�����ֵ
		c_vo.setCust_id(r_cust_id);
		c_vo.setInput_man(input_operatorCode);
		rsList_cust = customer.listByControl(c_vo);

		if(rsList_cust.size()>0){
			map_cust = (Map)rsList_cust.get(0);
		}

		if(map_cust!=null){
			c_vo.setCust_no(Utility.trimNull(map_cust.get("CUST_NO")));
			c_vo.setCust_name(Utility.trimNull(map_cust.get("CUST_NAME")));
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
	s_cont_vo.setValid_period(r_valid_period);
	s_cont_vo.setQs_date(r_qs_date);
	s_cont_vo.setJk_date(r_jk_date);
	s_cont_vo.setCheck_man(r_check_man);
	s_cont_vo.setBank_acct_type(r_bank_acct_type);
	s_cont_vo.setCity_serialno(r_city_serialno);
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

	Object[] resultSet = contract.appendNoPre(s_cont_vo);

	serial_no = (Integer)resultSet[0];
	re_contract_bh = (String)resultSet[1];
	if(file.uploadAttchment(new Integer(5),serial_no,"",null,null,input_operatorCode))
		bSuccess = true;

	salevo.setSerial_NO(r_serialNo);
	salevo.setTeamID(r_team_id);
	salevo.setProductID(r_product_id);
	salevo.setSerial_no_subscribe(new Integer(0));
	salevo.setAlreadysale(r_already_sale);
	salevo.setAlready_qualified_num(r_already_qualified_num);
	salevo.setSub_product_id(r_sub_product_id);
	sale_parameter.modiAlreadysale(salevo,input_operatorCode);

	bSuccess = true;

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

<%if(bSuccess){
	if(r_selfbenflag == 0){
%>
      //�Ϲ��ɹ� //��ͬ��� //Ϊ//��ͬΪ�������У���Ҫ���иú�ͬ������ά����
	  if (confirm("<%=LocalUtilis.language("message.subscriptionSucc",clientLocale)%>��<%=LocalUtilis.language("class.constractBH",clientLocale)%><%=LocalUtilis.language("message.is",clientLocale)%>:<%=re_contract_bh%>��<%=LocalUtilis.language("message.contractMaintenanceTip",clientLocale)%> ��")){
	  	location = 'benifiter_list.jsp?from_flag_benifitor=1&contract_id=<%=serial_no%>'&page=1&sQuery=<%=sQuery%>';
	  }
	  else{
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
	}else{
		sl_alert("��Ʒ������,������ѡ��!");
		return false;
	}
}

/*��ȡ�Ӳ�Ʒ��Ϣ�����漶�����漶��������Ϣ*/
function getSubFlag(data){
	var is_product_sub_flag;
	var arrayL = data.split("$");
	product_id = DWRUtil.getValue("product_id");
	is_product_sub_flag = arrayL[0];
	document.theform.intrust_flag1.value = arrayL[1];
	document.theform.asfund_flag.value = arrayL[2];
	document.theform.open_flag.value = arrayL[3];
	document.theform.is_local.value = arrayL[4];
	document.theform.pre_start_date.value = arrayL[5];
	document.theform.pre_end_date.value = arrayL[6];
	$("contract_sub_bh_view").innerHTML = arrayL[8];
	if(DWRUtil.getValue("intrust_flag1")!= 1){
		document.getElementById("tr_prov_flag").style.display = "";
		if(DWRUtil.getValue("asfund_flag")==3)//���漶�𰴺�ͬ������
		{
			document.getElementById("td_pro_level").style.display = "";
			document.getElementById("div_prov_level").style.display = "";
		}else{
			document.getElementById("td_pro_level").style.display = "none";
			document.getElementById("div_prov_level").style.display = "none";
		}
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

	if(DWRUtil.getValue("is_local")==2)//�Ƽ���
	{
		document.getElementById("td_city_serialno").style.display = "";
		document.getElementById("select_product_city").style.display = "";
		setProductCity(product_id);
	}else{
		document.getElementById("td_city_serialno").style.display = "none";
		document.getElementById("select_product_city").style.display = "none";
	}

	//���ʽ,�Ϲ��۷ѷ�ʽ
	if(DWRUtil.getValue("open_flag")==1)
	{
		document.getElementById("bonus_name_view").style.display = "";
		//document.getElementById("bonus_value_view").style.display = "";
	}else{
		document.getElementById("bonus_name_view").style.display = "none";
		//document.getElementById("bonus_value_view").style.display = "none";
	}

}

//����Ӳ�Ʒ����
function getSubProductOption(data)
{
	$("select_id_2").innerHTML = "<select style='width:430px;' name='sub_product_id' onkeydown='javascript:nextKeyPress(this)'"+
			"onchange='javascript:selectSubProduct(this.value);'>"+data+"</SELECT>&nbsp;&nbsp;"+
			"<button type="button"  class='xpbutton2' name='show_sub_info' title='�鿴�Ӳ�Ʒ��Ϣ' "+
		    "onclick='javascript:openSubProduct();'><%=LocalUtilis.language("message.view",clientLocale)%></button><!-- �鿴 -->";
}

/*���Ӳ�Ʒ������� ����������ȼ�����������������*/
function selectSubProduct(sub_product_id)
{
	var product_id_v = DWRUtil.getValue("product_id");
	var link_man = DWRUtil.getValue("link_man");
	if(sub_product_id==""){sub_product_id = 0;}
	//utilityService.getSubPSylb(sub_product_id,showSylbCallBack);//��ȡ�Ӳ�Ʒ���������,��ͬ��ͽ��,��ͬ��߽��

	//�������ȼ����������
	utilityService.getSubProductProvFlag(product_id_v,sub_product_id,0,showProvFlag);

	//��Ʒ����������Ϣ
	utilityService.queryMarketTrench(product_id_v,sub_product_id,matchCustCallBack);

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
	var v = showModalDialog(url,'','dialogWidth:720px;dialogHeight:720px;status:0;help:0;');

	if (v != null){
		showTransactionCustomer2(prefix, v);
		getElement(prefix, "cust_id").value = v[7];
		document.theform.customer_gain_acct.value = v[0];
		link_man = DWRUtil.getValue("link_man");
		if( v[1]=='����')
			document.theform.cust_type.value = 2;
		else
			document.theform.cust_type.value = 1;

		queryTeamquota(link_man);
	}

}

function selectBank(value)//ѡ������
{
	var cust_id = DWRUtil.getValue("customer_cust_id");

	if(cust_id == 0)
	{
		sl_alert("����ѡ��������!");
		return false;
	}
	var obj = document.theform.bank_acct2;
	utilityService.getBankAcctOption(cust_id,value,'','',{callback:function(data){

		DWRUtil.removeAllOptions(obj);
		var json = eval(data);
		if(json.length>0){
			document.theform.inputflag.value = 2;
		}else{
			document.theform.inputflag.value = 1;
		}
		changeInput(document.theform.btnSelect);
		for(i=0;i<json.length;i++){
			DWRUtil.addOptions(obj,json[i]);
		}
	}});
}

//�����ʺ� �ı� ���� ���� ѡ��
function changeInput(obj)
{
	if(document.theform.inputflag.value==1)
	{
		obj.innerText="ѡ��";
		document.theform.bank_acct.style.display="";
		document.theform.bank_acct2.style.display="none";
		document.theform.inputflag.value=2;
	}
	else
	{
		obj.innerText="����";
		document.theform.bank_acct.style.display="none";
		document.theform.bank_acct2.style.display="";
		document.theform.inputflag.value=1;
	}
}

//������䷽ʽ���ڲ���ת�ݶ�
function showBonus_rate(bonus_flag){

	if(bonus_flag=='3'){
		document.getElementById("bonus_rate_div").style.display = "";
	}else{
		document.getElementById("bonus_rate_div").style.display = "none";
	}
}

/*��֤��ͬ���*/
function check(){
	$("contractDIV").innerHTML = "";
	if(!sl_check(document.theform.contract_sub_bh, "<%=LocalUtilis.language("class.contractID",clientLocale)%> "))return false; //��ͬ���

	var book_code=DWRUtil.getValue("book_code");
	var contract_type=new Number(1);
	var product_id=DWRUtil.getValue("product_id");
	var contract_sub =DWRUtil.getValue("contract_sub_bh");
	if(product_id==0){
		sl_alert("<%=LocalUtilis.language("message.chooseProdTip",clientLocale)%> !");//����ѡ���Ʒ
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

/*�����������*/
function getSellInfo(product_id){
	if(product_id>0){
		contract.getSaleInfo('<%=input_bookCode%>',product_id,{callback: function(data){
			document.theform.org_bal.value = data[0];
			document.theform.contact_num.value = data[1];
			document.theform.current_num.value = data[2];
		}});
	}
}

/*�������Ƽ��*/
function checkSellInfo(){
	var channel_rate = document.theform.channel_rate.value
	var rg_money = DWRUtil.getValue("rg_money")
	if(rg_money != "" && channel_rate != "")
		document.theform.market_trench_money.value = (Number(rg_money) * Number(channel_rate)).toFixed(2);
	else
		document.theform.market_trench_money.value = "";
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
	var ret = "<select name='prov_level' style='width:230px'>"+data+"</select>";
	document.getElementById("div_prov_level").innerHTML=ret;
}

//���к���
function showHtyh(){
	if(document.theform.with_bank_flag.checked){
		document.getElementById("htyhmc_v").style.display = "";
	}else{
		document.getElementById("htyhmc_v").style.display = "none";
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
	newline.insertCell().innerHTML="<input type='file' name=file_info"+n+" size='60'>"+"<button type="button"  onclick='javascript:removeline(this)'><%=LocalUtilis.language("messgae.remove",clientLocale)%> </button>";//�Ƴ�
}

//ͨ����ͬ������Ա����ƷID���Ӳ�ƷID ��ѯ������Ա�����ŶӵĿ�������������������
function queryTeamquota(link_man)
{

	var product_id = DWRUtil.getValue("product_id");
	var sub_product_id = DWRUtil.getValue("sub_product_id");
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
function setProductCity(product_id)
{
	utilityService.getSelectProductCity(product_id,null,{callback:function(data){
		$("select_product_city").innerHTML = "<select size='1' style='width:234px;' name='city_serialno' onkeydown='javascript:nextKeyPress(this)'>"+data+"</SELECT>";
	}});
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

/*����*/
function SaveAction(){
	if(document.theform.onsubmit()){
		disableAllBtn(true);
		document.theform.submit();
	}
}

/*�����������Ϲ���Ϣʱ�õ��ĺ���*/
function validateForm(form){

	if(!sl_checkChoice(form.product_id,"<%=LocalUtilis.language("class.productName",clientLocale)%> ")){return false;}//��Ʒ����
	if(form.product_id.value==0){
		sl_alert("<%=LocalUtilis.language("message.chooseProdName",clientLocale)%> !");//��ѡ���Ʒ����
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
		  }
		  else{
		     if((s.charCodeAt(len-1)<48 || s.charCodeAt(len-1)>57) || (s.charCodeAt(len-2)<48 || s.charCodeAt(len-2)>57)|| (s.charCodeAt(len-3)<48 || s.charCodeAt(len-3)>57)){
		        sl_alert("<%=LocalUtilis.language("messgae.contractSerialError",clientLocale)%> ��");//��ͬ��������λ���������֣�����������д��
		        return false;
		     }
		  }
		}
	<%}%>

	if(form.customer_cust_id.value==""){
			sl_alert("<%=LocalUtilis.language("message.chooseCustomer",clientLocale)%> ��");//��ѡ��ͻ�
			return false;
	}

    if(!sl_checkChoice(form.bank_id, '<%=LocalUtilis.language("class.bankName3",clientLocale)%> ')){return false;}//��������
	if(form.bank_id.value != ""){
		if(document.theform.inputflag.value==1){
			if(!sl_checkChoice(form.bank_acct2, '<%=LocalUtilis.language("class.bankAcct3",clientLocale)%> '))return false;//�����ʺ�
		}
		else{
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

	if(form.jk_date.value < form.qs_date.value)	{
        //�ɿ�����//����С��//ǩ������
		sl_alert("<%=LocalUtilis.language("class.dzDate'/><fmt:message key='message.cannotLess'/><fmt:message key='class.qsDate",clientLocale)%> ");
		return false;
	}

	var userid='<%=user_id.intValue()%>';

	if(form.self_ben_flag.checked){
		form.selfbenflag.value='1';
	}
	else{
		form.selfbenflag.value='0';
	}

	if(userid==8 && DWRUtil.getValue("intrust_flag1")==2){
	    if(!sl_checkChoice(form.market_trench, '�������� ')){return false;}//��������
	    if(!sl_checkChoice(form.channel_cooperation, '����������ʽ ')){return false;}//����������ʽ
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
			if(!sl_checkDecimal(form.bonus_rate, "ת�ݶ����", 3, 2, 0))	return false;
			if(parseFloat(form.bonus_rate.value) <= parseFloat('0') || parseFloat(form.bonus_rate.value) >= parseFloat('100')){
				sl_alert("ת�ݶ����ȡֵ����Ϊ0��100֮��(������0��100)");
				return false;
			}
			//if(!isBetween(form.bonus_rate,0,100,2,"ת�ݶ����ȡֵ����Ϊ0��100֮��(������0��100)")) return false;
		}
	}

	if(DWRUtil.getValue("asfund_flag")==3){
		if(!sl_checkChoice(form.prov_level, '<%=LocalUtilis.language("class.incomeLevel",clientLocale)%>'))return false;//���漶��
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
		utilityService.getGovRegional(9999, value, flag,backGovRegional);
	}
}

/*ͨ��ʡ�й�����ص��������� �ص�����*/
function backGovRegional(data){
	document.getElementById("gov_regional_span").innerHTML = "<select size='1'  name='bank_city' style='width: 150px' onkeydown='javascript:nextKeyPress(this)'>"+data+"</select>";
}

function bankAcctView(bank_acct){
	var acct_view  = '';
	bank_acct = trimString(bank_acct);//ȥ�����пո�
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

</script>
</HEAD>
<BODY class="BODY body-nox">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="post" action="subscribe_add2.jsp" onsubmit="javascript:return validateForm(this);" ENCTYPE="multipart/form-data">
<input type="hidden" name="sQuery" value="<%=sQuery%>">
<input type="hidden" name="customer_cust_id" value="">
<input type="hidden" name="cust_type" value="">
<input type="hidden" name="inputflag" value="<%=inputflag%>">
<input type="hidden" name="open_flag" value="0">
<input type="hidden" name="asfund_flag" value="">
<input type="hidden" name="intrust_flag1" value="">
<input type="hidden" name="selfbenflag" value="">
<input type="hidden" name="pre_start_date" value="">
<input type="hidden" name="pre_end_date" value="">
<input type="hidden" name="is_local" value="1">

<input type="hidden" name="org_bal" value=""><!-- 300W ��� -->
<input type="hidden" name="contact_num" value=""><!-- ��ͬ���� -->
<input type="hidden" name="current_num" value=""><!-- ���к�ͬ -->
<input type="hidden" id="channel_type" name="channel_type" value=""/>
<input type="hidden" id="channel_id" name="channel_id" value=""/>
<input type="hidden" id="channel_rate" name="channel_rate" value=""/>

<!-- �����Ŷ�����Ƿ񳬹�����֤ -->
<input type="hidden" name="quota_money" value="">
<input type="hidden" name="already_sale" value="">
<input type="hidden" name="quota_qualified_num" value="">
<input type="hidden" name="already_qualified_num" value="">
<input type="hidden" name="team_id" value="">
<input type="hidden" name="serialNo" value="">

<input type="hidden" name="bank_acct_view" value="">
<input type="hidden" name="bank_acct_flag" value="">

<div align="left" class="page-title">
	<font color="#215dc6"><b><%=menu_info%></b></font>
</div>

<!--��Ʒѡ��-->
<div>
<table  border="0" width="100%" cellspacing="4" cellpadding="4" class="product-list">
	<tr>
		<td ><b><%=LocalUtilis.language("menu.prodInfo",clientLocale)%> </b></td><!--��Ʒ��Ϣ-->
		<td></td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.productID",clientLocale)%> :&nbsp;&nbsp;</td><!--��Ʒ���-->
		<td align="LEFT">
			<input type="text" maxlength="16" name="productcode" value="<%//=product_code%>" onkeydown="javascript:setProduct(this.value);" size="25" >
		</td>
	</tr>
	<tr>
		<td align="right"><font color="red">*</font><%=LocalUtilis.language("class.productNumber",clientLocale)%> :&nbsp;&nbsp;</td><!--��Ʒѡ��-->
		<td align=left colspan=3>
			<select name="product_id" onkeydown="javascript:nextKeyPress(this)" style="width: 430px;" onchange="javascript:selectProduct(this.value);">
				<!--��ѡ��-->
                <option><%=LocalUtilis.language("message.pleaseSelect",clientLocale)%> </option>
				<%=Argument.getCRMProductListOptions(new Integer(2),product_id,"110202",new Integer(0),input_operatorCode)%>
			</select>
		</td>
	</tr>
	<tr id="sub_product_flag" style="display:none;">
		<td align="right"><b><font color="red"><b>*</b></font><%=LocalUtilis.language("class.subProductID",clientLocale)%> </b>:&nbsp;&nbsp;</td><!--�Ӳ�Ʒѡ��-->
		<td align=left colspan=3 id="select_id_2">
		    <select name="sub_product_id">
		        <option value="0">��ѡ��</option>
		    </select>
		</td>
	</tr>
</table>
<br>
<table  border="0" width="100%" cellspacing="4" cellpadding="4" class="product-list">
	<!--�ͻ�ѡ��-->
	<tr>
		<td ><b><%=LocalUtilis.language("menu.customerInformation",clientLocale)%> </b></td><!--�ͻ���Ϣ-->
		<!--��ѡ��-->
        <td  colspan="3"><button type="button"  class="xpbutton5" accessKey=E name="btnEdit" title="<%=LocalUtilis.language("menu.customerInformation",clientLocale)%> " onclick="javascript:getMarketingCustomer('customer','0');return false;"><%=LocalUtilis.language("message.select2",clientLocale)%> </button>
</td>
	   
<tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.customerName",clientLocale)%> :</td> <!--�ͻ�����-->
		<td ><input maxlength="100" readonly class='edline'  name="customer_cust_name" size="50" onkeydown="javascript:nextKeyPress(this);" value="<%//=cust_name%>">&nbsp;&nbsp;&nbsp;
		</td>
		<td align="right"><%=LocalUtilis.language("class.accountManager",clientLocale)%> :</td><!--�ͻ�����-->
		<td ><input maxlength="100" readonly class='edline'  name="customer_service_man" size="50" onkeydown="javascript:nextKeyPress(this);" value="<%//=Utility.trimNull(Argument.getManager_Name(service_man))%>">&nbsp;&nbsp;&nbsp;
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.customerType",clientLocale)%> :</td><!--�ͻ����-->
		<td ><INPUT readonly class='edline' name="customer_cust_type_name" size="50" value="<%//=cust_type_name%>" onkeydown="javascript:nextKeyPress(this);"></td>
		<td align="right"><%=LocalUtilis.language("class.customerCardID",clientLocale)%> :</td><!--֤������-->
		<td><input readonly class='edline' name="customer_card_id" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%//=card_id%>" size="50"></td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.companyPhone",clientLocale)%> :</td>  <!--��˾�绰-->
		<td><input readonly class='edline' name="customer_o_tel" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%//=o_tel%>" size="50"></td>
		<td align="right"><%=LocalUtilis.language("class.customerMobile",clientLocale)%> :</td><!--�ֻ�-->
		<td><input readonly class='edline' name="customer_mobile" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%//=mobile%>" size="50"></td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.customerHTel",clientLocale)%> :</td>  <!--��ͥ�绰-->
		<td><input readonly class='edline' name="customer_h_tel" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%//=h_tel%>" size="50"></td>
		<td align="right"><%=LocalUtilis.language("class.email",clientLocale)%> :</td> <!--�����ʼ�-->
		<td><input readonly class='edline' name="customer_email" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%//=e_mail%>" size="50"></td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.postAddress",clientLocale)%> :</td><!--��ϵ��ַ-->
		<td ><INPUT readonly class='edline' name="customer_post_address" size="50" value="<%//=post_address%>" onkeydown="javascript:nextKeyPress(this);"></td>
		<td align="right"><%=LocalUtilis.language("class.postcode",clientLocale)%> :</td><!--��������-->
		<td ><INPUT readonly class='edline' name="customer_post_code" size="50" value="<%//=post_code%>" onkeydown="javascript:nextKeyPress(this);"></td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.isLink3",clientLocale)%> :</td><!--��������־-->
		<td><input type="checkbox" disabled name="customer_is_link" value="<%//=is_link%>" <%//if(is_link!=null){if(is_link.intValue()==1) out.print("checked");}%> class="flatcheckbox"></td>
		<td align="right">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
</table>
<br>
<table  border="0" width="100%" cellspacing="5" cellpadding="5" class="product-list">
	<!--�Ϲ���ͬ��Ϣ-->
	<tr>
		<td><b><%=LocalUtilis.language("message.contractInfo",clientLocale)%> </b></td>  <!--��ͬ��Ϣ-->
	<tr>
	<tr>
		<!--��ͬ���-->
        <td align="right" width="120px" <%if(contract_bh_flag==1){out.print("style='display:none'");}%>><b><%=LocalUtilis.language("class.constractBH",clientLocale)%></b>:&nbsp;&nbsp;</td>
		<td <%if(contract_bh_flag==1){out.print("style='display:none'");}%>><input name="contract_bh" size="20" maxlength=20 onkeydown="javascript:nextKeyPress(this)" value=""></td>
		<td align="right"><%=LocalUtilis.language("class.contractID",clientLocale)%>:</td><!--��ͬ���--><!--��֤���-->
		<td >
			<div id="contract_sub_bh_view"></div>
			<input name="contract_sub_bh" size="35" maxlength=40 onkeydown="javascript:nextKeyPress(this)" value="" onblur="javascript:check();">&nbsp;&nbsp;
			<INPUT type="button" class="xpbutton3" onclick="javascript:check();" value='<%=LocalUtilis.language("messgae.CheckBH",clientLocale)%> '>
			<div id="contractDIV"></div>
		</td>
	</tr>
	<tr>
		<td align="right" >
			<font color="red"><b>*</b></font><%=LocalUtilis.language("class.bankName5",clientLocale)%>:<!-- �������������� -->
		</td>
		<td>
			<select size="1" name="bank_id"  onchange="javascript:selectBank(this.value);"  style="WIDTH:150px">
				<%=Argument.getBankOptions("")%>
			</select>
			<input type="text" name="bank_sub_name" size="20" onkeydown="javascript:nextKeyPress(this)" value="">
		</td>
		<td align="right"><font color="red"><b>*</b></font><%=LocalUtilis.language("class.bandAcct4",clientLocale)%>:</td><!--�����������ʺ�-->
		<td>
			<input <%if(inputflag==1) out.print("style=display:none");%> type="text" name="bank_acct" onkeydown="javascript:nextKeyPress(this)" onkeyup="javascript:showAcctNum(this.value)" size="28"
				onblur="javascript:bankAcctView(this.value);">
			<select <%if(inputflag==2) out.print("style=display:none");%> size="1" name="bank_acct2"  onkeydown="javascript:nextKeyPress(this)"  style="WIDTH: 170px">
			</select>&nbsp;&nbsp;
			<button type="button"  class="xpbutton2" accessKey=x name="btnSelect" title="����" onclick="javascript:changeInput(this);">����(<u>X</u>)</button>
			<span id="bank_acct_num" class="span"></span>
		</td>
	</tr>
	<tr>
		<td align="right">���������ڵ�:</td>
		<td>
			<select size="1"  name="bank_province" style="width: 130px" onkeydown="javascript:nextKeyPress(this)" onchange="javascript:setGovRegional(this.value,'');">
				<%=Argument.getCustodianNameLis(new Integer(9999), "", new Integer(0),"")%>
			</select>
			<span id="gov_regional_span">
				<select style="WIDTH: 150px" name="bank_city"><option value="">��ѡ��</option></select>
			</span><!-- ʡ������������˳������������ -->
		</td>
		<td align="right">�Ƽ���:</td>
		<td>
			<select name="recommend_man" style="width:230px;" onkeydown="javascript:nextKeyPress(this)" >
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
			<input name="customer_gain_acct" <%if(user_id.intValue()==8){%>class="edline" readonly<%} %> style="WIDTH: 233px" onkeydown="javascript:nextKeyPress(this)"  value="">
		</td>
	</tr>
	<tr>
		<td align="right"><font color="red"><b>*</b></font><%=LocalUtilis.language("class.feeType",clientLocale)%>:</td><!--�ɿʽ-->
		<td>
			<select size="1" name="jk_type" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 280px">
				<%=Argument.getJkTypeOptions(null)%>
			</select>
		</td>
		<td align="right"><font color="red"><b>*</b></font><%=LocalUtilis.language("class.rg_money",clientLocale)%>:</td><!--�Ϲ����-->
		<td>
			<input name="rg_money" size="42" value="" onkeydown="javascript:nextKeyPress(this)"
				onkeyup="javascript:showCnMoney(this.value,rg_money_cn)" onblur="javascript:checkSellInfo();">
		</td>
	</tr>
	<tr>
		<td colspan="3"></td>
		<td><span id="rg_money_cn" class="span"></span></td>
	</tr>
	<tr id="bonus_name_view"  style='display:none'>
		<td align="right"><%//=fee_type_name%><%=LocalUtilis.language("class.feeTypeName",clientLocale)%>:</td><!--�۽ɷ�ʽ-->
		<td >
			<select size="1" name="fee_jk_type" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 280px">
				<%=Argument.getFeeJkTypeOptions(0)%>
			</select>
		</td>
		<td align="right"><%=LocalUtilis.language("class.bonusFlag",clientLocale)%>:</td><!--������䷽ʽ-->
		<td>
			<select size="1" name="bonus_flag" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 230px" onchange="javascript:showBonus_rate(this.value);">
				<%=Argument.getBonus_flag(null)%>
			</select>
			<span id="bonus_rate_div" style='display:none'>ת�ݶ����:
				<input type="text" name="bonus_rate" size="10" onkeydown="javascript:nextKeyPress(this)"value="">%
			</span>
		</td>
	</tr>
	<tr>
		<td align="right"><font color="red"><b>*</b></font><%=LocalUtilis.language("class.qsDate",clientLocale)%>:</td><!--ǩ������-->
		<td>
			<INPUT TYPE="text" NAME="qs_date_picker" class=selecttext value="<%=Format.formatDateLine(Utility.getCurrentDate())%>" size="47">
			<INPUT TYPE="button" value="��" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.qs_date_picker,theform.qs_date_picker.value,this);" tabindex="13">
			<INPUT TYPE="hidden" NAME="qs_date" value="">
		</td>
		<td align="right"><font color="red"><b>*</b></font><%=LocalUtilis.language("class.dzDate",clientLocale)%>:</td><!--�ɿ�����-->
		<td>
			<INPUT TYPE="text" NAME="jk_date_picker" class=selecttext value="<%=Format.formatDateLine(Utility.getCurrentDate())%>" size="38">
			<INPUT TYPE="button" value="��" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.jk_date_picker,theform.jk_date_picker.value,this);" tabindex="14">
			<INPUT TYPE="hidden" NAME="jk_date" value="">
		</td>
	</tr>
	<tr>
		<td align="right"><font color="red"><b>*</b></font>��������:</td>
		<td>
			<select id="market_trench" name="market_trench" style="width: 280px;" onchange="javascript:setMartetTrench(this);">
			</select>
		</td>
		<td align="right">��������:</td>
		<td><input name="market_trench_money" size="42" value="" onkeydown="javascript:nextKeyPress(this)" onkeyup="javascript:showCnMoney(this.value,market_trench_money_cn)"><span id="market_trench_money_cn" class="span"></span></td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.partnAttachmentInfo",clientLocale)%>:</td><!--����������Ϣ-->
		<td>
			<input name="channel_memo" size="51" onkeydown="javascript:nextKeyPress(this)"  value="">
		</td>
		<td align="right"><font color="red"><b>*</b></font><%=LocalUtilis.language("class.channelCooperation",clientLocale)%>:</td><!-- ����������ʽ -->
		<td>
			<SELECT name="channel_cooperation" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 234px">
					<%=Argument.getChannelCooperationOptions(null)%>
			</SELECT>
		</td>
	</tr>
	<tr id="tr_prov_flag">
		<td align="right"><font color="red"><b>*</b></font><%=LocalUtilis.language("class.provLevel",clientLocale)%>:</td><!-- �������ȼ� -->
		<td>
			<select name="prov_flag" style="WIDTH: 280px" onchange="javascript:getprovlevel()" onkeydown="javascript:nextKeyPress(this)">
			</select>
		</td>
		<td align="right" id="td_pro_level" style="display: none"><font color="red"><b>*</b></font><%=LocalUtilis.language("class.incomeLevel",clientLocale)%>:</td><!-- ���漶�� -->
		<td>
			<div id="div_prov_level" >
			<select name="prov_level" style="display: none;width:234px">
				<option value="0">��ѡ��</option>
			</select>
			</div>
		</td> 
	</tr>
	<tr>
		<td align="right">��ͬ������Ա:</td>
		<td>
			<select size="1" name="link_man" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 280px" onchange="javascript:queryTeamquota(this.value);">
				<%=Argument.getRoledOperatorOptions(input_bookCode, 2,input_operatorCode)%>
			</select>
		</td>
		<td align="right"> <%=LocalUtilis.language("class.selfBenFlag",clientLocale)%>:</td><!--�����־-->
		<td><input name="self_ben_flag" class="flatcheckbox" type="checkbox" value="" checked onkeydown="javascript:nextKeyPress(this)"></td>
	</tr>
	<tr style="display: <%=SUB_COOPER == 2 ? "none" : ""%>;">
		<td align="right"><%=LocalUtilis.language("message.cooperationWay",clientLocale)%>:</td><!--������ʽ-->
		<td>
			<input onkeydown="javascript:nextKeyPress(this)" type="checkbox" class="flatcheckbox" value="0" name="with_bank_flag" onclick="javascript:showHtyh();">
				<%=LocalUtilis.language("intrsut.home.silvertrustcooper",clientLocale)%>&nbsp;&nbsp;&nbsp;&nbsp;<!--���ź���-->
			<input onkeydown="javascript:nextKeyPress(this)" type="checkbox"
				class="flatcheckbox" value="0" name="with_security_flag"
				><%=LocalUtilis.language("message.cooperation2",clientLocale)%> &nbsp;&nbsp;<!--֤�ź���-->
			<input onkeydown="javascript:nextKeyPress(this)" type="checkbox"
				class="flatcheckbox" value="0" name="with_private_flag"
				><%=LocalUtilis.language("class.cooperation4",clientLocale)%> &nbsp;&nbsp;<!--˽ļ�������-->
		</td>
		<td align="right" id="td_city_serialno" style="display: none;"><%=LocalUtilis.language("class.citySerialNO",clientLocale)%> :</td><!--��ͬ�ƽ��-->
		<td id="select_product_city" style="display: none;"></td>
	</tr>
	<tr id="htyhmc_v" style='display:none'>
		<td align="right"><%=LocalUtilis.language("class.withBankId",clientLocale)%>:</td>
		<td colspan="3">
			<select name="ht_bank_id" style="WIDTH: 220px">
					<%=Argument.getBankOptions("")%>
			</select>
			<input type="text" name="ht_bank_sub_name" size="35" onkeydown="javascript:nextKeyPress(this)" value="">
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.description",clientLocale)%> :</td><!--��ע-->
		<td colspan="3"><textarea rows="3" name="summary2" onkeydown="javascript:nextKeyPress(this)" cols="83"></textarea></td>
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
</table>
</div>

<div align="right">
	<br>
	<!--����-->
    <button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:SaveAction();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;
	<!--����-->
    <button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:history.back();"><%=LocalUtilis.language("message.back",clientLocale)%> (<u>C</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</div>
</form>
<p>
<%
%>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>
