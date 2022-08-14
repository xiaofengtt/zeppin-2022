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
//获得页面传递变量
Integer serial_no = null;//认购信息ID 编辑用
Integer product_id = Utility.parseInt(request.getParameter("product_id"),new Integer(0));//产品ID
int pos_flag = Utility.parseInt(request.getParameter("pos_flag"),0); //证件扫描标志
String session_cust_name = Utility.trimNull(session.getAttribute("name"));

int inputflag = Utility.parseInt(request.getParameter("inputflag"), 2);//银行帐号输入 或者 下拉选择 标志
int SUB_COOPER = Argument.getSyscontrolValue_intrust("SUB_COOPER");
int r_selfbenflag = 1;
String is_ykgl="";
String xthtyjsyl="";
boolean bSuccess = false;
DocumentFile2 file = null;

if("POST".equals(request.getMethod())){

	file = new DocumentFile2(pageContext);
	file.parseRequest();

	//获得保存数据
	Integer r_cust_id = Utility.parseInt(file.getParameter("customer_cust_id"), null);
	Integer r_product_id = Utility.parseInt(file.getParameter("product_id"), null);
	BigDecimal r_rg_money = Utility.parseDecimal(file.getParameter("rg_money"),new BigDecimal(0));
	String r_jk_type = file.getParameter("jk_type");
	String r_bank_id = file.getParameter("bank_id");
	String r_bank_sub_name = Utility.trimNull(file.getParameter("bank_sub_name"));
	String r_gain_acct = file.getParameter("customer_gain_acct");
	String r_bank_acct = "";

	inputflag = Utility.parseInt(file.getParameter("inputflag"), 2);//银行帐号输入 或者 下拉选择 标志

	if(inputflag==1){
		r_bank_acct = file.getParameter("bank_acct2");
	}
	else{
		r_bank_acct = file.getParameter("bank_acct");
	}

	Integer r_service_man = Utility.parseInt(file.getParameter("customer_service_man"), input_operatorCode);
	String r_summary = Utility.trimNull(file.getParameter("summary"));
	Integer r_qs_date = Utility.parseInt(file.getParameter("qs_date"), new Integer(Utility.getCurrentDate()));
	Integer r_jk_date = Utility.parseInt(file.getParameter("jk_date"), new Integer(Utility.getCurrentDate()));//改参数已作废，所以随便传入当前时间
	String r_bank_acct_type = Utility.trimNull(file.getParameter("bank_acct_type"));
	String r_contract_sub_bh = file.getParameter("contract_sub_bh");
	//r_selfbenflag = Utility.parseInt(file.getParameter("selfbenflag"),1);
	Integer r_prov_flag = Utility.parseInt(file.getParameter("prov_flag"), new Integer(1));
	String r_prov_level = Utility.trimNull(file.getParameter("prov_level"));

	BigDecimal expect_ror_lower = Utility.parseDecimal(file.getParameter("expect_ror_lower"),new BigDecimal(0.00),4,"0.01");
	BigDecimal expect_ror_upper = Utility.parseDecimal(file.getParameter("expect_ror_upper"),new BigDecimal(0.00),4,"0.01");

	//同步客户信息
	if(r_cust_id.intValue()>0){
		List rsList_cust = null;
		Map map_cust = null;
		//客户单个值
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

	//保存数据
	ContractUnrealVO s_cont_vo = new ContractUnrealVO();

	s_cont_vo.setCustId(r_cust_id);
	s_cont_vo.setPreproductId(r_product_id);
	s_cont_vo.setRgMoney(r_rg_money);
	s_cont_vo.setJkType(r_jk_type);
	s_cont_vo.setBankId(r_bank_id);
	s_cont_vo.setBankSubName(r_bank_sub_name);
	s_cont_vo.setGainAcct(r_gain_acct);
	s_cont_vo.setBankAcct(r_bank_acct);
	s_cont_vo.setServiceMan(r_service_man);
	s_cont_vo.setSummary(r_summary);
	s_cont_vo.setQsDate(r_qs_date);
	s_cont_vo.setJkDate(r_jk_date);
	s_cont_vo.setBankAcctType(r_bank_acct_type);
	s_cont_vo.setContractSubBh(r_contract_sub_bh);
	//s_cont_vo.setSelf_ben_flag(r_selfbenflag);
	s_cont_vo.setInputMan(input_operatorCode);
	s_cont_vo.setProvFlag(r_prov_flag);
	s_cont_vo.setProvLevel(r_prov_level);
	s_cont_vo.setExpectRorLower(expect_ror_lower);
	s_cont_vo.setExpectRorUpper(expect_ror_upper);

	contract.appendContractUnreal(s_cont_vo);
	bSuccess = true;
}
%>

<HTML>
<HEAD>
<TITLE>非正式合同录入</TITLE>
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
var n=1;
var sQuery = "<%=sQuery%>";
var retUrl = "sales_contract_unreal.jsp?"+sQuery;

<%if (bSuccess) { %>
	sl_alert("已成功添加！");
	CancelAction();//返回认购列表
<%}%>

function CancelAction() {	location.href = retUrl;
}

/*过滤定位产品*/
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
			sl_alert('<%=LocalUtilis.language("message.inputProdIDError",clientLocale)%> ！');//输入的产品编号不存在或者该产品不在推介期
			document.theform.product_id.options[0].selected=true;
		}
	}
	nextKeyPress(this);
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

/*客户信息: 证件扫描自动识别时调用*/
function getMarketingCustomerAuto(prefix,readonly){
	var cust_id = getElement(prefix, "cust_id").value;
	var url = '<%=request.getContextPath()%>/marketing/customerInfo.jsp?prefix='+ prefix+ '&cust_id=' + cust_id+'&readonly='+readonly + '&pos_flag=<%=pos_flag%>';
	var v = showModalDialog(url,'','dialogWidth:720px;dialogHeight:720px;status:0;help:0;');
	if (v != null){
		showTransactionCustomer2(prefix, v);
		getElement(prefix, "cust_id").value = v[7];
		showCustOptions(v[7]);
		document.theform.customer_gain_acct.value = v[0];
		link_man = DWRUtil.getValue("link_man");
		if( v[1]=='机构')
			document.theform.cust_type.value = 2;
		else
			document.theform.cust_type.value = 1;
		if(link_man !=link_man){
			queryTeamquota(link_man);
		}
	}
}


function selectBank(value)//选择银行
{
	var cust_id = DWRUtil.getValue("customer_cust_id");

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
	var userid='<%=user_id.intValue()%>';
	if(!sl_checkChoice(form.product_id,"<%=LocalUtilis.language("class.productName",clientLocale)%> ")){return false;}//产品名称
	if(form.product_id.value==0){
		sl_alert("<%=LocalUtilis.language("message.chooseProdName",clientLocale)%> !");//请选择产品名称
		return false;
	}

	if(form.customer_cust_id.value==""){
			sl_alert("<%=LocalUtilis.language("message.chooseCustomer",clientLocale)%> ！");//请选择客户
			return false;
	}
	if(userid == 2 && form.sfpj.value != 1){
		sl_alert("请对客户进行评级处理!");//请选择客户进行评级
		return false;
	}
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
<form name="theform" method="post" action="sales_contract_unreal_add.jsp?<%=sQuery%>" onsubmit="javascript:return validateForm(this);" ENCTYPE="multipart/form-data">
<input type="hidden" name="sQuery" value="<%=sQuery%>">
<input type="hidden" name="customer_cust_id" value="">
<input type="hidden" name="cust_type" value="">
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
		<td align="right"><b><font color="red"><b>*</b></font>产品选择 </b>:&nbsp;&nbsp;</td>
		<td align=left colspan=3>
			<select name="product_id" onkeydown="javascript:nextKeyPress(this)" style="width: 430px;">
				<%=Argument.getCRMPreproductListOptions(product_id, "", "110202", input_operatorCode)%>
			</select>
		</td>
	</tr>
</table>
<br>
<table  border="0" width="100%" cellspacing="4" cellpadding="4" style="border: 1px; border-style: dashed; border-color: blue;margin-top:5px;">
	<!--客户选择-->
	<tr>
		<td ><b><%=LocalUtilis.language("menu.customerInformation",clientLocale)%> </b></td><!--客户信息-->
		<!--请选择-->
        <td colspan="3">
			<button type="button"  class="xpbutton5" accessKey=E name="btnEdit" title="<%=LocalUtilis.language("menu.customerInformation",clientLocale)%> " onclick="javascript:getMarketingCustomer('customer','0');return false;"><%=LocalUtilis.language("message.select2",clientLocale)%> </button>
       </td>
	<tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.customerName",clientLocale)%> :</td> <!--客户名称-->
		<td ><input maxlength="100" readonly class='edline'  name="customer_cust_name" size="50" onkeydown="javascript:nextKeyPress(this);" value="<%//=cust_name%>">&nbsp;&nbsp;&nbsp;
		</td>
		<td align="right"><%=LocalUtilis.language("class.accountManager",clientLocale)%> :</td><!--客户经理-->
		<td ><input maxlength="100" readonly class='edline'  name="customer_service_man" size="50" onkeydown="javascript:nextKeyPress(this);" value="<%//=Utility.trimNull(Argument.getManager_Name(service_man))%>">&nbsp;&nbsp;&nbsp;
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.customerType",clientLocale)%> :</td><!--客户类别-->
		<td ><INPUT readonly class='edline' name="customer_cust_type_name" size="50" value="<%//=cust_type_name%>" onkeydown="javascript:nextKeyPress(this);"></td>
		<td align="right"><%=LocalUtilis.language("class.customerCardID",clientLocale)%> :</td><!--证件号码-->
		<td><input readonly class='edline' name="customer_card_id" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%//=card_id%>" size="50"></td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.companyPhone",clientLocale)%> :</td>  <!--公司电话-->
		<td><input readonly class='edline' name="customer_o_tel" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%//=o_tel%>" size="50"></td>
		<td align="right"><%=LocalUtilis.language("class.customerMobile",clientLocale)%> :</td><!--手机-->
		<td><input readonly class='edline' name="customer_mobile" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%//=mobile%>" size="50"></td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.customerHTel",clientLocale)%> :</td>  <!--家庭电话-->
		<td><input readonly class='edline' name="customer_h_tel" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%//=h_tel%>" size="50"></td>
		<td align="right"><%=LocalUtilis.language("class.email",clientLocale)%> :</td> <!--电子邮件-->
		<td><input readonly class='edline' name="customer_email" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%//=e_mail%>" size="50"></td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.postAddress",clientLocale)%> :</td><!--联系地址-->
		<td ><INPUT readonly class='edline' name="customer_post_address" size="50" value="<%//=post_address%>" onkeydown="javascript:nextKeyPress(this);"></td>
		<td align="right"><%=LocalUtilis.language("class.postcode",clientLocale)%> :</td><!--邮政编码-->
		<td ><INPUT readonly class='edline' name="customer_post_code" size="50" value="<%//=post_code%>" onkeydown="javascript:nextKeyPress(this);"></td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.isLink3",clientLocale)%> :</td><!--关联方标志-->
		<td><input type="checkbox" disabled name="customer_is_link" value="<%//=is_link%>" <%//if(is_link!=null){if(is_link.intValue()==1) out.print("checked");}%> class="flatcheckbox"></td>
		<%if(user_id.intValue() ==2){ %>
		<td align="right"><font color="red"><b>*</b></font><%=LocalUtilis.language("menu.customerRating",clientLocale)%>:&nbsp;&nbsp; </td>
		<td ><button type="button"  class="xpbutton3"  title='<%=LocalUtilis.language("message.newRating",clientLocale)%>' onclick="javascript:newInfo(document.theform.customer_cust_id.value);"><%=LocalUtilis.language("message.rating",clientLocale)%> </button>
		</td>
		<%} %>
	</tr>
</table>
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
			<input name="contract_sub_bh" size="35" maxlength=40 onkeydown="javascript:nextKeyPress(this)" value="">&nbsp;&nbsp;
			<%--INPUT type="button" class="xpbutton3" onclick="javascript:check();" value='<%=LocalUtilis.language("messgae.CheckBH",clientLocale)%> '>
			<div id="contractDIV"></div--%>
		</td>
	</tr>
	<tr>
		<td align="right" >
			<font color="red"><b>*</b></font><%=LocalUtilis.language("class.bankName5",clientLocale)%>:<!-- 受益人银行名称 -->
		</td>
		<td>
			<select size="1" name="bank_id"  onchange="javascript:selectBank(this.value);"  style="WIDTH:150px">
				<%=Argument.getBankOptions("")%>
			</select>
			<input type="text" name="bank_sub_name" size="20" onkeydown="javascript:nextKeyPress(this)" value="">
		</td>
		<td align="right"><font color="red"><b>*</b></font><%=LocalUtilis.language("class.bandAcct4",clientLocale)%>:</td><!--受益人银行帐号-->
		<td>
			<input <%if(inputflag==1) out.print("style=display:none");%> type="text" name="bank_acct" onkeydown="javascript:nextKeyPress(this)" onkeyup="javascript:showAcctNum(this.value)" size="28"
				onblur="javascript:bankAcctView(this.value);">
			<select <%if(inputflag==2) out.print("style=display:none");%> size="1" name="bank_acct2"  onkeydown="javascript:nextKeyPress(this)"  style="WIDTH: 170px">
			</select>&nbsp;&nbsp;
			<button type="button"  class="xpbutton2" accessKey=x name="btnSelect" title="输入" onclick="javascript:changeInput(this);">输入(<u>X</u>)</button>
			<span id="bank_acct_num" class="span"></span>
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.bankAccountType",clientLocale)%>:</td><!--银行账号类型-->
		<td >
			<select size="1" name="bank_acct_type" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 280px">
				<%=Argument.getBankAcctType("")%>
			</select>
		</td>
		<td align="right"><%=LocalUtilis.language("class.customerGainAcct",clientLocale)%>:</td><!--银行帐号户名-->
		<td>
			<input name="customer_gain_acct" <%if(user_id.intValue()==8){%>class="edline" readonly<%} %> style="WIDTH: 233px" onkeydown="javascript:nextKeyPress(this)"  value="">
		</td>
	</tr>
	<tr>
		<td align="right"><font color="red"><b>*</b></font><%=LocalUtilis.language("class.feeType",clientLocale)%>:</td><!--缴款方式-->
		<td>
			<select size="1" name="jk_type" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 280px">
				<%=Argument.getJkTypeOptions("111404")%>
			</select>
		</td>
		<td align="right"><font color="red"><b>*</b></font><%=LocalUtilis.language("class.rg_money",clientLocale)%>:</td><!--认购金额-->
		<td>
			<input name="rg_money" size="42" value="" onkeydown="javascript:nextKeyPress(this)"
				onkeyup="javascript:showCnMoney(this.value,rg_money_cn)" onblur="javascript:checkSellInfo();">
		</td>
	</tr>
	<tr>
		<td colspan="3"></td>
		<td><span id="rg_money_cn" class="span"></span></td>
	</tr>	
	<tr>
		<td align="right"><font color="red"><b>*</b></font><%=LocalUtilis.language("class.qsDate",clientLocale)%>:</td><!--签署日期-->
		<td>
			<INPUT TYPE="text" NAME="qs_date_picker" class=selecttext value="<%=Format.formatDateLine(Utility.getCurrentDate())%>" size="47">
			<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.qs_date_picker,theform.qs_date_picker.value,this);" tabindex="13">
			<INPUT TYPE="hidden" NAME="qs_date" value="">
		</td>
		<td align="right"><font color="red"><b>*</b></font><%=LocalUtilis.language("class.dzDate",clientLocale)%>:</td><!--缴款日期-->
		<td>
			<INPUT TYPE="text" NAME="jk_date_picker" class=selecttext value="<%=Format.formatDateLine(Utility.getCurrentDate())%>" size="38">
			<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.jk_date_picker,theform.jk_date_picker.value,this);" tabindex="14">
			<INPUT TYPE="hidden" NAME="jk_date" value="">
		</td>
	</tr>
	<tr id="tr_prov_flag">
		<td align="right"><font color="red"><b>*</b></font><%=LocalUtilis.language("class.provLevel",clientLocale)%>:</td><!-- 受益优先级 -->
		<td>
			<select name="prov_flag" style="WIDTH: 280px" onkeydown="javascript:nextKeyPress(this)">
				<%=Argument.getProvFlagOptions(new Integer(1))%>
			</select>
		</td>
		<td align="right" id="td_pro_level" style="display:"><font color="red"><b>*</b></font><%=LocalUtilis.language("class.incomeLevel",clientLocale)%>:</td><!-- 收益级别 -->
		<td>
			<div id="div_prov_level" >
			<select name="prov_level" style="display:;width:234px">
				<%=Argument.getProvlevelOptions("")%>
			</select>
			</div>
		</td> 
	</tr>
	<tr>
		<td align="right">预期收益率:</td>
		<td>
			<input name="expect_ror_lower" size="12" value="" onkeydown="javascript:nextKeyPress(this)"><font color="red">%</font> 到 
			<input name="expect_ror_upper" size="12" value="" onkeydown="javascript:nextKeyPress(this)"><font color="red">%</font>
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
		<td colspan="3"><textarea rows="3" name="summary" onkeydown="javascript:nextKeyPress(this)" cols="83"></textarea></td>
	</tr>
</table>
</div>

<div align="right">
	<br>
	<!--保存-->
    <button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:SaveAction();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;
	<!--返回-->
    <button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:CancelAction()"><%=LocalUtilis.language("message.back",clientLocale)%> (<u>C</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</div>
</form>
<p>
<%@ include file="/includes/foot.inc"%>
<script language="javascript" >
<%
if (!"".equals(session_cust_name) && cust_map==null){
%>
	//扫描证件，并在数据库中验证后不存在，新建客户
	getMarketingCustomerAuto('customer','0');
<%}else if (!"".equals(session_cust_name) && cust_map!=null){%>
	//给form赋值
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
<%
	customer.remove();
	customer_inst.remove();
	contract.remove();
%>
