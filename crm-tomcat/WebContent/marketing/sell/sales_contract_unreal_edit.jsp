<%@ page contentType="text/html; charset=GBK" import="enfo.crm.web.*,enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*,enfo.crm.marketing.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
String sQuery = "";
sQuery += "qContractSubBh=" + request.getParameter("qContractSubBh");
sQuery += "&qPreproductName="+ request.getParameter("qPreproductName");
sQuery += "&qPreproductId="+request.getParameter("qPreproductId");
sQuery += "&qCustName="+request.getParameter("qCustName");
sQuery += "&qStatus="+request.getParameter("qStatus");
sQuery += "&qQsDate1="+request.getParameter("qQsDate1");
sQuery += "&qQsDate2="+request.getParameter("qQsDate2");

sQuery += "&page="+request.getParameter("page");
sQuery += "&pagesize="+request.getParameter("pagesize");

Customer_INSTLocal  customer_inst = EJBFactory.getCustomer_INST();//同步用客户对象
CustomerLocal customer = EJBFactory.getCustomer();
ContractLocal contract = EJBFactory.getContract();
CustomerVO c_vo  = new CustomerVO();

Map cust_map = null;
Integer serialNo = Utility.parseInt(request.getParameter("serialNo"), new Integer(-1));
ContractUnrealVO vo = new ContractUnrealVO();
vo.setSerialNo(serialNo);
vo.setInputMan(input_operatorCode);
Map map = contract.loadContractUnreal(vo);

int inputflag = Utility.parseInt(request.getParameter("inputflag"), 2);//银行帐号输入 或者 下拉选择 标志
//String purchase_mode_checkedStyle = "";
//String purchase_mode_checkedStyle2 = "";

Integer preproductId = new Integer(-1);
String preproductName = "";
Integer custId = new Integer(-1);
Integer serviceMan = new Integer(-1);
BigDecimal rgMoney = new BigDecimal(0);
String jkType = "";
String bankId = "";
String bankSubName = "";
String gainAcct = ""; // 银行账户户名
String bankAcct = "";

String summary = "";
Integer qsDate = new Integer(0);
Integer jkDate = new Integer(0);
String bankAcctType = "";
String contractSubBh = "";
Integer provFlag = new Integer(1);
String provLevel = "";

BigDecimal expectRorLower = new BigDecimal(0.00);
BigDecimal expectRorUpper = new BigDecimal(0.00);

Integer _status = new Integer(-1);
String setReadOnly = "";

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

if (map!=null) {
	preproductId = (Integer)map.get("PREPRODUCT_ID");
	preproductName = (String)map.get("PREPRODUCT_NAME");
	custId = (Integer)map.get("CUST_ID");
	serviceMan = (Integer)map.get("SERVICE_MAN");
	rgMoney = (BigDecimal)map.get("RG_MONEY");
	jkType = (String)map.get("JK_TYPE");
	bankId = (String)map.get("BANK_ID");
	bankSubName = (String)map.get("BANK_SUB_NAME");
	gainAcct = (String)map.get("GAIN_ACCT"); 
	bankAcct = (String)map.get("BANK_ACCT");

	summary = (String)map.get("SUMMARY");
	qsDate = (Integer)map.get("QS_DATE");
	jkDate = (Integer)map.get("JK_DATE");
	bankAcctType = (String)map.get("BANK_ACCT_TYPE");
	contractSubBh = (String)map.get("CONTRACT_SUB_BH");
	provFlag = (Integer)map.get("PROV_FLAG");
	provLevel = (String)map.get("PROV_LEVEL");

	expectRorLower = (BigDecimal)map.get("EXPECT_ROR_LOWER");
	if (expectRorLower==null) {
		expectRorLower = new BigDecimal(0.00);
	}
	expectRorLower = expectRorLower.multiply(new BigDecimal(100));

	expectRorUpper = (BigDecimal)map.get("EXPECT_ROR_UPPER");
	if (expectRorUpper==null) {
		expectRorUpper = new BigDecimal(0.00);
	}
	expectRorUpper = expectRorUpper.multiply(new BigDecimal(100));

	_status = (Integer)map.get("STATUS");
	if (_status.intValue()==1) {
		setReadOnly = "";
	} else {
		setReadOnly = "readonly='readonly'";
	}

	CustomerVO cust_vo = new CustomerVO();
	cust_vo.setCust_id(custId);
	cust_vo.setInput_man(input_operatorCode);
	List customerList = customer.listByControl(cust_vo);
	//获得客户信息
	if(customerList.size()>0){
		Map map2 = (Map)customerList.get(0);
		cust_name = Utility.trimNull(map2.get("CUST_NAME"));
		service_man = Utility.parseInt(Utility.trimNull(map2.get("SERVICE_MAN")),new Integer(0));
		card_id = Utility.trimNull(map2.get("CARD_ID"));
		o_tel = Utility.trimNull(map2.get("O_TEL"));
		h_tel = Utility.trimNull(map2.get("H_TEL"));
		mobile = Utility.trimNull(map2.get("MOBILE"));
		post_code = Utility.trimNull(map2.get("POST_CODE"));
		post_address = Utility.trimNull(map2.get("POST_ADDRESS"));
		cust_type_name = Utility.trimNull(map2.get("CUST_TYPE_NAME"));
		is_link = Utility.parseInt(Utility.trimNull(map2.get("IS_LINK")),0);
		card_type = Utility.trimNull(map2.get("CARD_TYPE"));
		cust_no = Utility.trimNull(map2.get("CUST_NO"));
		legal_man = Utility.trimNull(map2.get("LEGAL_MAN"));
		contact_man = Utility.trimNull(map2.get("CONTACT_MAN"));
		cust_type = Utility.parseInt(Utility.trimNull(map2.get("CUST_TYPE")),new Integer(0));
	}
}

boolean bSuccess = false;

if("POST".equals(request.getMethod())){
	//获得保存数据
	preproductId = Utility.parseInt(request.getParameter("product_id"),new Integer(-1));
	custId = Utility.parseInt(request.getParameter("cust_id"),new Integer(-1));
	serviceMan = Utility.parseInt(request.getParameter("service_man"),new Integer(-1));
	rgMoney = Utility.parseDecimal(request.getParameter("rg_money"),new BigDecimal(0));
	jkType = request.getParameter("jk_type");
	bankId = request.getParameter("bank_id");
	bankSubName = Utility.trimNull(request.getParameter("bank_sub_name"));
	gainAcct = request.getParameter("customer_gain_acct");

	bankAcct = "";	
	inputflag = Utility.parseInt(request.getParameter("inputflag"), 2); //银行帐号输入 或者 下拉选择 标志
	if (inputflag==1){
		bankAcct = request.getParameter("bank_acct2");
	} else {
		bankAcct = request.getParameter("bank_acct");
	}

	summary = Utility.trimNull(request.getParameter("summary"));
	qsDate = Utility.parseInt(request.getParameter("qs_date"), new Integer(Utility.getCurrentDate()));
	jkDate = Utility.parseInt(request.getParameter("jk_date"), new Integer(Utility.getCurrentDate()));//改参数已作废，所以随便传入当前时间
	bankAcctType = Utility.trimNull(request.getParameter("bank_acct_type"));
	contractSubBh = request.getParameter("contract_sub_bh");
	provFlag = Utility.parseInt(request.getParameter("prov_flag"), new Integer(1));
	provLevel = Utility.trimNull(request.getParameter("prov_level"));

	expectRorLower = Utility.parseDecimal(request.getParameter("expect_ror_lower"),new BigDecimal(0.00),4,"0.01");
	expectRorUpper = Utility.parseDecimal(request.getParameter("expect_ror_upper"),new BigDecimal(0.00),4,"0.01");

	//保存数据
	ContractUnrealVO s_cont_vo = new ContractUnrealVO();
	s_cont_vo.setSerialNo(serialNo);
	s_cont_vo.setCustId(custId);
	s_cont_vo.setPreproductId(preproductId);
	s_cont_vo.setRgMoney(rgMoney);
	s_cont_vo.setJkType(jkType);
	s_cont_vo.setBankId(bankId);
	s_cont_vo.setBankSubName(bankSubName);
	s_cont_vo.setGainAcct(gainAcct);
	s_cont_vo.setBankAcct(bankAcct);
	s_cont_vo.setServiceMan(serviceMan);
	s_cont_vo.setSummary(summary);
	s_cont_vo.setQsDate(qsDate);
	s_cont_vo.setJkDate(jkDate);
	s_cont_vo.setBankAcctType(bankAcctType);
	s_cont_vo.setContractSubBh(contractSubBh);
	s_cont_vo.setInputMan(input_operatorCode);
	s_cont_vo.setProvFlag(provFlag);
	s_cont_vo.setProvLevel(provLevel);
	s_cont_vo.setExpectRorLower(expectRorLower);
	s_cont_vo.setExpectRorUpper(expectRorUpper);

	contract.updateContractUnreal(s_cont_vo);
	bSuccess = true;
}
%>

<HTML>
<HEAD>
<TITLE>非正式合同编辑</TITLE>
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
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/utilityService.js'></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/widgets/ext/resources/css/ext-base.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/widgets/ext/resources/css/ext-all.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/widgets/ext/resources/css/ComboBoxTree.js"></script>

<script language="javascript">
var sQuery = "<%=sQuery%>";
var retUrl = "sales_contract_unreal.jsp?"+sQuery;
var n=1;

<%if (bSuccess) { %>
	sl_alert("修改成功！");
	CancelAction();//返回认购列表
<%}%>

/*返回*/
function CancelAction(){
	location.href = retUrl;
}

/*客户信息*/
function getMarketingCustomer(prefix,readonly){
	var cust_id = getElement(prefix, "cust_id").value;
	var url = '<%=request.getContextPath()%>/marketing/customerInfo.jsp?prefix='+ prefix+ '&cust_id=' + cust_id+'&readonly='+readonly ;
	var v = showModalDialog(url,'','dialogWidth:720px;dialogHeight:720px;status:0;help:0;');
	if (v != null){
		showTransactionCustomer2(prefix, v);
		getElement(prefix, "cust_id").value = v[7];
		//showCustOptions(v[7]);
		document.theform.customer_gain_acct.value = v[0];
		link_man = DWRUtil.getValue("link_man");
		if( v[1]=='机构')
			document.theform.cust_type.value = 2;
		else
			document.theform.cust_type.value = 1;
		/*if(link_man !=link_man){
			queryTeamquota(link_man);
		}*/
	}
}

function getCustomer(cust_id){
	var url = '/marketing/customerInfo2.jsp?cust_id='+ cust_id ;
	var v = showModalDialog(url,'','dialogWidth:720px;dialogHeight:720px;status:0;help:0;');
	if(v!=null){
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

//认购合同联系人
function showCustOptions(cust_id,contact_id){
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
	DWRUtil.setValue("cust_message",contact_id)
}

function selectBank(value)//选择银行
{
	var cust_id = DWRUtil.getValue("cust_id");

	if(cust_id == 0)
	{
		sl_alert("请先选择受益人!");
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

//银行帐号 文本 或者 下拉 选择
function changeInput(obj)
{
	if(document.theform.inputflag.value==1)
	{
		obj.innerText="选择";
		document.theform.bank_acct.style.display="";
		document.theform.bank_acct2.style.display="none";
		document.theform.inputflag.value=2;
	}
	else
	{
		obj.innerText="输入";
		document.theform.bank_acct.style.display="none";
		document.theform.bank_acct2.style.display="";
		document.theform.inputflag.value=1;
	}
}

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

//收益类别
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


//通过合同销售人员，产品ID，子产品ID 查询销售人员所在团队的可销售配额，可销售人数。
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

/*保存*/
function SaveAction(){
	if(document.theform.onsubmit()){
		disableAllBtn(true);
		document.theform.submit();
	}
}

/*下面是新增认购信息时用到的函数*/
function validateForm(form){
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

	if(!sl_checkDate(form.qs_date_picker,"<%=LocalUtilis.language("class.qsDate",clientLocale)%> ")){return false;}//签署日期
	if(!sl_checkDate(form.jk_date_picker,"<%=LocalUtilis.language("class.dzDate",clientLocale)%> ")){return false;}//缴款日期
	if(!sl_check(form.summary2, '<%=LocalUtilis.language("class.contractSummary",clientLocale)%> ', 100, 0)){return false;}//合同备注

	syncDatePicker(form.qs_date_picker, form.qs_date);
	syncDatePicker(form.jk_date_picker, form.jk_date);

	if(form.jk_date.value < form.qs_date.value)	{
        //缴款日期//不能小于//签署日期
		sl_alert("<%=LocalUtilis.language("class.dzDate'/><fmt:message key='message.cannotLess'/><fmt:message key='class.qsDate",clientLocale)%> ");
		return false;
	}

	/*if(form.self_ben_flag.checked){
		form.selfbenflag.value='1';
	} else {
		form.selfbenflag.value='0';
	} */

	/*if(form.intrust_flag1.value=='2'){
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
	}

	if(DWRUtil.getValue("asfund_flag")==3){
		if(!sl_checkChoice(form.prov_level, '<%=LocalUtilis.language("class.incomeLevel",clientLocale)%>'))return false;//收益级别
	}else{
		form.prov_level.value = '120401 ';
	}*/
	
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

//评级
function newInfo(cust_id)
{
	if(cust_id <= 0)
		sl_alert('请选择客户之后再进行评级');
	else{
		if(showModalDialog('/marketing/cust_grade_new.jsp?cust_id='+cust_id, '', 'dialogWidth:700px;dialogHeight:600px;status:0;help:0') != null)
		{
			sl_alert("<%=LocalUtilis.language("message.rating",clientLocale)%><%=LocalUtilis.language("message.success",clientLocale)%> ！");
			document.theform.sfpj.value = 1;
		}
	}
}
</script>
</HEAD>
<BODY class="BODY">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="post" action="sales_contract_unreal_edit.jsp?<%=sQuery%>" onsubmit="javascript:return validateForm(this);">
<input type="hidden" name="serialNo" value="<%=serialNo%>">
<input type="hidden" name="product_id" value="<%=preproductId%>">
<input type="hidden" name="cust_id" value="<%=custId%>">
<input type="hidden" name="service_man" value="<%=service_man%>">
<input type="hidden" name="inputflag" value="<%=inputflag%>">
<%-- 
<input type="hidden" name="open_flag" value="0">
<input type="hidden" name="asfund_flag" value="">
<input type="hidden" name="intrust_flag1" value="">
<input type="hidden" name="selfbenflag" value="">
<input type="hidden" name="pre_start_date" value="">
<input type="hidden" name="pre_end_date" value="">
<input type="hidden" name="is_local" value="1">


<input type="hidden" name="org_bal" value=""><!-- 300W 金额 -->
<input type="hidden" name="contact_num" value=""><!-- 合同上限 -->
<input type="hidden" name="current_num" value=""><!-- 已有合同 -->
<input type="hidden" id="channel_type" name="channel_type" value=""/>
<input type="hidden" id="channel_id" name="channel_id" value=""/>
<input type="hidden" id="channel_rate" name="channel_rate" value=""/>

<!-- 销售团队配额是否超过的验证 -->
<input type="hidden" name="quota_money" value="">
<input type="hidden" name="already_sale" value="">
<input type="hidden" name="quota_qualified_num" value="">
<input type="hidden" name="already_qualified_num" value="">
<input type="hidden" name="team_id" value="">--%>
<input type="hidden" name="serialNo" value="">

<input type="hidden" name="bank_acct_view" value="">
<input type="hidden" name="bank_acct_flag" value="">
<input type="hidden" name="sfpj" value="0">
<div align="left">
	<img border="0" src="<%=request.getContextPath()%>/images/ico_area.gif" width="32" height="28">
	<font color="#215dc6"><b><%=menu_info%></b></font>
	<hr noshade color="#808080" size="1">
</div>

<!--产品选择-->
<div>
<table  border="0" width="100%" cellspacing="4" cellpadding="4" style="border: 1px; border-style: dashed; border-color: blue; margin-top:5px;">
	<tr>
		<td><b>预发行产品信息</b></td>
		<td></td>
	</tr>
	<tr>
		<td align="right" width="10%"><b>产品名称 </b>:&nbsp;&nbsp;</td>
		<td align="LEFT" width="*">
			<input type="text" class="edline" readonly maxlength="16" name="productName" value="<%=preproductName%>" onkeydown="javascript:setProduct(this.value);" size="25" >
		</td>
	</tr>
</table>
<br>
<fieldset style="border-color: background;border-style: solid;border-width: 1px;;margin-left: 4px;margin-right: 4px;">
<legend>
	<b><%=LocalUtilis.language("message.customerInfomation",clientLocale)%> </b><!--客户信息-->
	<button type="button"  id ="cust_button" class="xpbutton3" accessKey=e name="btnEdit" 
		title='<%=LocalUtilis.language("message.customerInfomation",clientLocale)%>' 
		onclick="javascript:getCustomer(<%=custId%>);"><%=LocalUtilis.language("message.view",clientLocale)%> 
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
		<td align="right"><%=LocalUtilis.language("class.companyPhone",clientLocale)%>:</td> <!--公司电话-->
		<td><input readonly class='edline' name="customer_o_tel" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%=o_tel%>" size="50"></td>
		<td align="right"><%=LocalUtilis.language("class.customerMobile",clientLocale)%>:</td><!--手机-->
		<td><input readonly class='edline' name="customer_mobile" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%=mobile%>" size="50"></td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.customerHTel",clientLocale)%>:</td>  <!--家庭电话-->
		<td><input readonly class='edline' name="customer_h_tel" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%=h_tel%>" size="50"></td>
		<td align="right"><%=LocalUtilis.language("class.email",clientLocale)%>:</td><!--电子邮件-->
		<td><input readonly class='edline' name="customer_email" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%=e_mail%>" size="50"></td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.postAddress",clientLocale)%>:</td><!--联系地址-->
		<td ><INPUT readonly class='edline' name="customer_post_address" size="50" value="<%=post_address%>" onkeydown="javascript:nextKeyPress(this);"></td>
		<td align="right"><%=LocalUtilis.language("class.postcode",clientLocale)%>:</td><!--邮政编码-->
		<td ><INPUT readonly class='edline' name="customer_post_code" size="50" value="<%=post_code%>" onkeydown="javascript:nextKeyPress(this);"></td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.isLink3",clientLocale)%>:</td><!--关联方标志-->
		<td><input type="checkbox" disabled name="customer_is_link" value="<%=is_link%>" <%if(is_link==1) out.print("checked");%> class="flatcheckbox"></td>
		<td align="right">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
</table>
</fieldset>
<br>
<table  border="0" width="100%" cellspacing="5" cellpadding="5" style="border: 1px; border-style: dashed; border-color: blue; margin-top:5px;">
	<!--认购合同信息-->
	<tr>
		<td><b><%=LocalUtilis.language("message.contractInfo",clientLocale)%> </b></td>  <!--合同信息-->
	<tr>
	<tr>      
		<td align="right"><font color="red"><b>*</b></font>合同实际编号:</td><!--验证编号-->
		<td >
			<div id="contract_sub_bh_view"></div>
			<input name="contract_sub_bh" size="35" maxlength=40 onkeydown="javascript:nextKeyPress(this)" value="<%=contractSubBh%>" <%=setReadOnly%>>&nbsp;&nbsp;
			<%--INPUT type="button" class="xpbutton3" onclick="javascript:check();" value='<%=LocalUtilis.language("messgae.CheckBH",clientLocale)%> '>
			<div id="contractDIV"></div--%>
		</td>
	</tr>
	<tr>
		<td align="right" >
			<font color="red"><b>*</b></font><%=LocalUtilis.language("class.bankName5",clientLocale)%>:<!-- 受益人银行名称 -->
		</td>
		<td>
			<select size="1" name="bank_id"  onchange="javascript:selectBank(this.value);"  style="WIDTH:150px" <%=setReadOnly%>>
				<%=Argument.getBankOptions(bankId)%>
			</select>
			<input type="text" name="bank_sub_name" size="20" onkeydown="javascript:nextKeyPress(this)" value="<%=bankSubName%>" <%=setReadOnly%>>
		</td>
		<td align="right"><font color="red"><b>*</b></font><%=LocalUtilis.language("class.bandAcct4",clientLocale)%>:</td><!--受益人银行帐号-->
		<td>
			<input <%if(inputflag==1) out.print("style=display:none");%> type="text" name="bank_acct" onkeydown="javascript:nextKeyPress(this)" onkeyup="javascript:showAcctNum(this.value)" size="28"
				onblur="javascript:bankAcctView(this.value);" value="<%=bankAcct%>" <%=setReadOnly%>>
			<select <%if(inputflag==2) out.print("style=display:none");%> size="1" name="bank_acct2"  onkeydown="javascript:nextKeyPress(this)"  style="WIDTH: 170px" <%=setReadOnly%>>
			</select>&nbsp;&nbsp;
			<%--<input <%=inputflag==1?"style=display:none":""%>  type="text" name="bank_acct" 
				value="<%=bankAcct%>" <%= purchase_mode_checkedStyle%> onkeydown="javascript:nextKeyPress(this)" onblur="javscript:bankAcctView(this.value);"
				onkeyup="javascript:showAcctNum(this.value)" size="34"/>
			<select <%=inputflag==2?"style=display:none":""%> size="1" name="bank_acct2" 
				<%= purchase_mode_checkedStyle2%> onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 200px">
				<%=Argument.getCustBankAcctOptions(custId,bankId,card_id,"")%>
			</select>&nbsp;&nbsp;--%>
			<button type="button"  class="xpbutton2" accessKey=x name="btnSelect" title="输入" onclick="javascript:changeInput(this);">输入(<u>X</u>)</button>
			<span id="bank_acct_num" class="span"></span>
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.bankAccountType",clientLocale)%>:</td><!--银行账号类型-->
		<td >
			<select size="1" name="bank_acct_type" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 280px" <%=setReadOnly%>>
				<%=Argument.getBankAcctType(bankAcctType)%>
			</select>
		</td>
		<td align="right"><%=LocalUtilis.language("class.customerGainAcct",clientLocale)%>:</td><!--银行帐号户名-->
		<td>
			<input name="customer_gain_acct" <%if(user_id.intValue()==8){%>class="edline" readonly<%} %> style="WIDTH: 233px" onkeydown="javascript:nextKeyPress(this)"  value="<%=gainAcct%>" <%=setReadOnly%>>
		</td>
	</tr>
	<tr>
		<td align="right"><font color="red"><b>*</b></font><%=LocalUtilis.language("class.feeType",clientLocale)%>:</td><!--缴款方式-->
		<td>
			<select size="1" name="jk_type" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 280px" <%=setReadOnly%>>
				<%=Argument.getJkTypeOptions(jkType)%>
			</select>
		</td>
		<td align="right"><font color="red"><b>*</b></font><%=LocalUtilis.language("class.rg_money",clientLocale)%>:</td><!--认购金额-->
		<td>
			<input name="rg_money" size="42" onkeydown="javascript:nextKeyPress(this)" <%=setReadOnly%>
				onkeyup="javascript:showCnMoney(this.value,rg_money_cn)" onblur="javascript:checkSellInfo();" value="<%=rgMoney%>">
		</td>
	</tr>
	<tr>
		<td colspan="3"></td>
		<td><span id="rg_money_cn" class="span"></span></td>
	</tr>	
	<tr>
		<td align="right"><font color="red"><b>*</b></font><%=LocalUtilis.language("class.qsDate",clientLocale)%>:</td><!--签署日期-->
		<td>
			<INPUT TYPE="text" NAME="qs_date_picker" class=selecttext value="<%=Format.formatDateLine(qsDate)%>" size="47">
			<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.qs_date_picker,theform.qs_date_picker.value,this);" tabindex="13">
			<INPUT TYPE="hidden" NAME="qs_date" value="">
		</td>
		<td align="right"><font color="red"><b>*</b></font><%=LocalUtilis.language("class.dzDate",clientLocale)%>:</td><!--缴款日期-->
		<td>
			<INPUT TYPE="text" NAME="jk_date_picker" class=selecttext value="<%=Format.formatDateLine(jkDate)%>" size="38">
			<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.jk_date_picker,theform.jk_date_picker.value,this);" tabindex="14">
			<INPUT TYPE="hidden" NAME="jk_date" value="">
		</td>
	</tr>
	<tr id="tr_prov_flag">
		<td align="right"><font color="red"><b>*</b></font><%=LocalUtilis.language("class.provLevel",clientLocale)%>:</td><!-- 受益优先级 -->
		<td>
			<select name="prov_flag" style="WIDTH: 280px" onkeydown="javascript:nextKeyPress(this)" <%=setReadOnly%>>
				<%=Argument.getProvFlagOptions(provFlag)%>
			</select>
		</td>
		<td align="right" id="td_pro_level" style="display:"><font color="red"><b>*</b></font><%=LocalUtilis.language("class.incomeLevel",clientLocale)%>:</td><!-- 收益级别 -->
		<td>
			<div id="div_prov_level" >
			<select name="prov_level" style="display:;width:234px" <%=setReadOnly%>>
				<%=Argument.getProvlevelOptions(provLevel)%>
			</select>
			</div>
		</td> 
	</tr>
	<tr>
		<td align="right">预期收益率:</td>
		<td>
			<input name="expect_ror_lower" size="12" value="<%=Format.formatMoney(expectRorLower)%>" onkeydown="javascript:nextKeyPress(this)" <%=setReadOnly%>><font color="red">%</font> 到 
			<input name="expect_ror_upper" size="12" value="<%=Format.formatMoney(expectRorUpper)%>" onkeydown="javascript:nextKeyPress(this)" <%=setReadOnly%>><font color="red">%</font>
		</td>
		<td align="right" style="display:none">合同销售人员:</td>
		<td>
			<select size="1" name="link_man" onkeydown="javascript:nextKeyPress(this)" style="WIDTH:280px;display:none" onchange="javascript:queryTeamquota(this.value);">
				<%=Argument.getRoledOperatorOptions(input_bookCode, 2,input_operatorCode)%>
			</select>
		</td>		
	</tr>
	<%-- tr>
		<td align="right"> <%=LocalUtilis.language("class.selfBenFlag",clientLocale)%>:</td><!--自益标志-->
		<td><input name="self_ben_flag" class="flatcheckbox" type="checkbox" value="" checked onkeydown="javascript:nextKeyPress(this)"></td>
	</tr--%>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.description",clientLocale)%>:</td><!--备注-->
		<td colspan="3"><textarea rows="3" name="summary" onkeydown="javascript:nextKeyPress(this)" cols="83" <%=setReadOnly%>><%=summary%></textarea></td>
	</tr>
</table>
</div>

<div align="right">
	<br>
	<%if (_status.intValue()==1) { %>
	<!--保存-->
    <button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:SaveAction();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;
	<%} %>
	<!--返回-->
    <button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:CancelAction();"><%=LocalUtilis.language("message.back",clientLocale)%> (<u>C</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</div>
</form>
<p>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>
<%
	customer.remove();
	customer_inst.remove();
	contract.remove();
%>
